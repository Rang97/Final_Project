const { chromium } = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const assert = require('node:assert/strict');

(async () => {
  const browser = await chromium.launch({ headless: true, channel: 'msedge' });
  try {
    const page = await browser.newPage();
    const errors = [];
    page.on('pageerror', error => errors.push(error.message));
    let games = [{ gameId: 7, name: '테스트 게임' }, { gameId: 9, name: '두 번째 게임' }];
    let submitted;
    let gameRequests = 0;
    await page.route('**/api/**', async route => {
      const request = route.request();
      const path = new URL(request.url()).pathname;
      if (!path.startsWith('/api/')) return route.continue();
      assert.equal(request.headers().authorization, 'Bearer party-token');
      let body;
      if (path === '/api/auth/me') body = { userId: 1, nickname: '파티테스터' };
      else if (path === '/api/users/me/games') {
        gameRequests++;
        body = { data: games };
      } else if (path === '/api/party/create') {
        submitted = request.postDataJSON();
        // Keep the form visible so this test does not depend on lobby APIs.
        await route.fulfill({ status: 400, contentType: 'application/json', body: '{}' });
        return;
      } else throw new Error(path);
      await route.fulfill({ contentType: 'application/json', body: JSON.stringify(body) });
    });
    const origin = process.env.TEST_BASE_URL || 'http://127.0.0.1:5173';
    await page.goto(origin + '/party/create');
    await page.waitForURL('**/login');
    await page.evaluate(() => sessionStorage.setItem('guildhub.accessToken', 'party-token'));
    await page.goto(origin + '/party/create');
    await page.getByRole('combobox').waitFor();
    assert.equal(await page.getByRole('combobox').inputValue(), '7');
    await page.getByPlaceholder('예) 다이아 랭크 듀오 구합니다').fill('테스트 파티');
    const requestCount = gameRequests;
    const sent = page.waitForResponse(response => response.url().endsWith('/api/party/create'));
    await page.getByRole('button', { name: '완료', exact: true }).click();
    await sent;
    assert.deepEqual(submitted, { title: '테스트 파티', gameId: 7, maxMemberCount: 4, chemistryType: 'SYNERGY' });
    await page.getByRole('combobox').selectOption('9');
    assert.equal(gameRequests, requestCount);
    games = [];
    await page.reload();
    await page.getByText('등록된 선호 게임이 없습니다.', { exact: true }).waitFor();
    assert.equal(await page.getByRole('button', { name: '완료', exact: true }).isDisabled(), true);
    assert.deepEqual(errors, []);
    console.log('PASS: party route protection, shared token, default game, create payload, empty games');
  } finally {
    await browser.close();
  }
})().catch(error => { console.error(error); process.exit(1); });
