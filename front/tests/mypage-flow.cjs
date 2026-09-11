const { chromium } = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const assert = require('node:assert/strict');
(async()=>{
 const browser=await chromium.launch({headless:true,channel:'msedge'});
 try {
 const page=await browser.newPage();
 const errors=[]; page.on('pageerror',e=>errors.push(e.message));
 await page.addInitScript(()=>sessionStorage.setItem('guildhub.accessToken','test-token'));
 let input=null, saju=null, failCalculate=false, failFortune=false, blocked=true;
 let savedCount=0, calculateCount=0;
 const options=['JA','CHUK','IN','MYO','JIN','SA','O','MI','SIN','YU','SUL','HAE','UNKNOWN'].map(value=>({value,label:value,timeRange:'00:00~01:00'}));
 await page.route('**/api/**',async route=>{
  const req=route.request(), path=new URL(req.url()).pathname;
  if (!path.startsWith('/api/')) return route.continue();
  if (!path.endsWith('birth-time-options')) assert.equal(req.headers().authorization,'Bearer test-token');
  let status=200, data, wrapped=true;
  if(path==='/api/auth/me'){data={userId:1,nickname:'가입닉네임'};wrapped=false;}
  else if(path==='/api/auth/birth-time-options'){data=options;wrapped=false;}
  else if(path==='/api/mypage/summary') data={saju,games:[]};
  else if(path==='/api/saju/input'){data=input;wrapped=false;if(!input)status=404;}
  else if(path==='/api/blocks') data=blocked?[{blockedUserId:2,blockedNickname:'차단유저',createdAt:'2026-09-11T12:00:00'}]:[];
  else if(path==='/api/blocks/2' && req.method()==='DELETE'){blocked=false;data=null;}
  else if(path==='/api/mypage/saju-input') {input=req.postDataJSON();savedCount++;data=input;}
  else if(path==='/api/mypage/saju/calculate') {
    calculateCount++;
    if(failCalculate){status=502;wrapped=false;data={message:'계산 서버 오류'};}
    else { saju={saju:{...input,sajuAnimalName:'푸른 호랑이',woodCount:3,fireCount:2,earthCount:1,metalCount:1,waterCount:1,yearStem:'갑',yearBranch:'자'},elementPercentages:{wood:37.5,fire:25,earth:12.5,metal:12.5,water:12.5},dominantElements:['WOOD']};data=saju; }
  }
  else if(path==='/api/users/me/fortunes/today'){
   if(failFortune){status=409;wrapped=false;data={code:'FORTUNE_GENERATING'};}
   else data={date:'2026-09-11',overallFortune:{score:88,title:'좋은 날',content:'오늘의 설명'},gameFortunes:[],dailyQuest:{title:'협력',mission:'팀원 돕기',reward:'행운'},oneLineMessage:'즐거운 하루'};
  }
  else throw Error(path);
  await route.fulfill({status,contentType:'application/json',body:JSON.stringify(wrapped?{success:true,data}:data)});
 });
 await page.goto('http://127.0.0.1:5173/mypage');
 await page.getByText('아직 계산된 사주가 없습니다.',{exact:false}).waitFor();
 assert.equal(await page.getByRole('heading',{level:1}).innerText(),'가입닉네임');
 assert.equal(await page.getByRole('button',{name:'오늘의 운세 불러오기'}).isDisabled(),true);
 await page.getByLabel('생년월일',{exact:true}).fill('2000-01-02');
 await page.getByLabel('성별',{exact:true}).selectOption('FEMALE');
 assert.equal(await page.locator('select[name=birthTimeBranch] option').count(),13);
 await page.getByRole('button',{name:'저장하고 사주 재계산'}).click();
 await page.getByRole('heading',{name:'푸른 호랑이'}).waitFor();
 assert.equal(savedCount,1);assert.equal(calculateCount,1);
 assert.equal(await page.getByRole('meter',{name:'목 비율'}).getAttribute('aria-valuenow'),'37.5');
 failFortune=true;
 await page.getByRole('button',{name:'오늘의 운세 불러오기'}).click();
 await page.getByRole('alert').filter({hasText:'생성 중'}).waitFor();
 failFortune=false;
 await page.getByRole('button',{name:'오늘의 운세 불러오기'}).click();
 await page.getByRole('heading',{name:'좋은 날'}).waitFor();
 await page.getByRole('button',{name:'차단유저 차단 해제'}).click();
 await page.getByText('차단한 유저가 없습니다.',{exact:true}).waitFor();
 failCalculate=true;
 await page.getByLabel('생년월일',{exact:true}).fill('2001-02-03');
 await page.getByRole('button',{name:'저장하고 사주 재계산'}).click();
 await page.getByRole('alert').filter({hasText:'입력은 저장되었지만'}).waitFor();
 assert.equal(input.birthDate,'2001-02-03');
 await page.reload();
 await page.getByText('저장된 입력과 사주 결과가 다릅니다.',{exact:false}).waitFor();
 assert.equal(await page.getByLabel('생년월일',{exact:true}).inputValue(),'2001-02-03');
 assert.equal(await page.getByRole('button',{name:'오늘의 운세 불러오기'}).isDisabled(),true);
 failCalculate=false;
 await page.getByRole('button',{name:'사주 재계산 다시 시도'}).click();
 await page.getByText('사주 재계산이 완료되었습니다.',{exact:false}).waitFor();
 assert.equal(savedCount,2);assert.equal(calculateCount,3);
 await page.setViewportSize({width:390,height:844});
 assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth),true);
 assert.deepEqual(errors,[]);
 console.log('PASS: empty state, profile, elements, save/calculation, fortune failure/success, unblock, saved-but-failed calculation, stale state after reload, calculation-only retry, mobile overflow.');
 } finally {await browser.close();}
})().catch(e=>{console.error(e);process.exit(1)});
