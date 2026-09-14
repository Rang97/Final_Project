const { chromium } = require(process.env.PLAYWRIGHT_MODULE || 'playwright');
const assert = require('node:assert/strict');

(async () => {
  const browser = await chromium.launch({ headless: true, channel: 'msedge' });
  try {
    const page = await browser.newPage();
    const errors = [];
    page.on('pageerror', error => errors.push(error.message));
    page.on('dialog', dialog => dialog.accept());
    const origin = process.env.TEST_BASE_URL || 'http://127.0.0.1:5173';
    const user = { userId: 1, nickname: '게시판테스터' };
    let post = null;
    let expire = false;
    const mutations = [];
    await page.route('**/api/**', async route => {
      const req = route.request();
      const path = new URL(req.url()).pathname;
      if (!path.startsWith('/api/')) return route.continue();
      const method = req.method();
      let data;
      let wrapped = true;
      let status = 200;
      if (path === '/api/auth/login') {
        assert.equal(req.headers().authorization, undefined);
        data = { accessToken: 'board-token', user }; wrapped = false;
      } else if (path === '/api/auth/me') {
        assert.equal(req.headers().authorization, 'Bearer board-token');
        data = user; wrapped = false;
      } else {
        if (method !== 'GET') {
          assert.equal(req.headers().authorization, 'Bearer board-token');
          mutations.push(method + ' ' + path);
        }
        if (expire) {
          assert.equal(req.headers().authorization, 'Bearer board-token');
          status = 401; data = null;
        } else if (path === '/api/posts' && method === 'GET') {
          data = { content: post ? [post] : [], totalPages: post ? 1 : 0, totalCount: post ? 1 : 0 };
        } else if (path === '/api/posts' && method === 'POST') {
          post = { ...req.postDataJSON(), postId: 7, writerNickname: user.nickname, viewCount: 0, createdAt: '2026-09-14T09:00:00', comments: [] };
          data = 7;
        } else if (path === '/api/posts/7' && method === 'GET') data = post;
        else if (path === '/api/posts/7' && method === 'PUT') { Object.assign(post, req.postDataJSON()); data = null; }
        else if (path === '/api/posts/7' && method === 'DELETE') { post = null; data = null; }
        else if (path === '/api/posts/7/comments' && method === 'POST') {
          post.comments.push({ commentId: 8, writerNickname: user.nickname, content: req.postDataJSON().content, createdAt: '2026-09-14T09:00:00' }); data = 8;
        } else if (path === '/api/posts/7/comments/8' && method === 'PUT') { post.comments[0].content = req.postDataJSON().content; data = null; }
        else if (path === '/api/posts/7/comments/8' && method === 'DELETE') { post.comments = []; data = null; }
        else throw Error(method + ' ' + path);
      }
      await route.fulfill({ status, contentType: 'application/json', body: JSON.stringify(wrapped ? { data } : data) });
    });
    await page.goto(origin + '/board');
    await page.getByText('등록된 게시글이 없습니다.').waitFor();
    await page.getByRole('button', { name: '+ 글쓰기', exact: true }).click();
    await page.waitForURL('**/login');
    await page.getByLabel('아이디', { exact: true }).fill('board_user');
    await page.getByLabel('비밀번호', { exact: true }).fill('pass1234');
    await page.locator('button[type=submit]').click();
    await page.waitForURL('**/board/write');
    await page.getByPlaceholder('게시글 제목을 입력하세요').fill('통합 게시글');
    await page.getByPlaceholder('내용을 자유롭게 작성해 주세요...').fill('게시판 본문');
    await page.getByRole('button', { name: '등록', exact: true }).click();
    await page.waitForURL('**/board/7');
    await page.getByRole('heading', { name: '통합 게시글' }).waitFor();
    await page.getByRole('button', { name: '더 보기' }).click();
    await page.getByRole('button', { name: '수정', exact: true }).click();
    await page.waitForURL('**/board/7/edit');
    await page.locator('form input').fill('수정 게시글');
    await page.getByRole('button', { name: '저장', exact: true }).click();
    await page.getByRole('heading', { name: '수정 게시글' }).waitFor();
    await page.getByPlaceholder('댓글을 입력하세요...').fill('통합 댓글');
    await page.getByRole('button', { name: '등록', exact: true }).click();
    await page.getByText('통합 댓글', { exact: true }).waitFor();
    await page.getByRole('button', { name: '더 보기' }).last().click();
    await page.getByRole('button', { name: '수정', exact: true }).click();
    await page.locator('input').last().fill('수정 댓글');
    await page.getByRole('button', { name: '저장', exact: true }).click();
    await page.getByText('수정 댓글', { exact: true }).waitFor();
    await page.getByRole('button', { name: '더 보기' }).last().click();
    await page.getByRole('button', { name: '삭제', exact: true }).click();
    await page.getByText('첫 댓글을 남겨보세요.').waitFor();
    await page.getByRole('button', { name: '더 보기' }).click();
    await page.getByRole('button', { name: '삭제', exact: true }).click();
    await page.waitForURL('**/board');
    await page.getByText('등록된 게시글이 없습니다.').waitFor();
    expire = true;
    await page.getByRole('button', { name: '+ 글쓰기', exact: true }).click();
    await page.getByPlaceholder('게시글 제목을 입력하세요').fill('만료 확인');
    await page.getByPlaceholder('내용을 자유롭게 작성해 주세요...').fill('만료 확인');
    await page.getByRole('button', { name: '등록', exact: true }).click();
    await page.waitForURL('**/login');
    await page.getByRole('status').filter({ hasText: '만료' }).waitFor();
    assert.equal(await page.evaluate(() => sessionStorage.getItem('guildhub.accessToken')), null);
    assert.deepEqual(mutations, ['POST /api/posts', 'PUT /api/posts/7', 'POST /api/posts/7/comments', 'PUT /api/posts/7/comments/8', 'DELETE /api/posts/7/comments/8', 'DELETE /api/posts/7', 'POST /api/posts']);
    assert.deepEqual(errors, []);
    console.log('PASS: public board, protected write and login return, shared token, post/comment CRUD, board 401 clears session.');
  } finally { await browser.close(); }
})().catch(error => { console.error(error); process.exit(1); });
