import { api } from '../../api/client';

export const inputFields = ['birthDate', 'gender', 'calendarType', 'birthTimeBranch'];
export const blankInput = { birthDate: '', gender: '', calendarType: 'SOLAR', birthTimeBranch: 'UNKNOWN' };
export const sameInput = (a, b) => Boolean(a && b && inputFields.every(key => a[key] === b[key]));
export const inputValues = (value) => Object.fromEntries(inputFields.map(key => [key, value[key]]));
const unwrap = (response) => response.data.data;
export const getSummary = (signal) => api.get('/mypage/summary', { signal }).then(unwrap);
export const getInput = (signal) => api.get('/saju/input', { signal }).then(response => response.data).catch(error => {
  if (error.response?.status === 404) return null;
  throw error;
});
export const getBirthTimes = (signal) => api.get('/auth/birth-time-options', { signal, publicRequest: true }).then(response => response.data);
export const getBlocks = (signal) => api.get('/blocks', { signal }).then(unwrap);
export const unblock = (id) => api.delete(`/blocks/${id}`);
export const saveInput = (value) => api.put('/mypage/saju-input', inputValues(value)).then(unwrap);
export const calculate = () => api.post('/mypage/saju/calculate', null, { timeout: 120000 }).then(unwrap);
export const getFortune = () => api.post('/users/me/fortunes/today', null, { timeout: 120000 }).then(unwrap);
export function errorMessage(error) {
  if (error.code === 'ECONNABORTED') return '응답 대기 시간이 초과되었습니다. 서버에서 처리가 진행 중일 수 있습니다.';
  if (!error.response) return '서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.';
  const data = error.response.data;
  if (data?.code === 'FORTUNE_GENERATING') return '오늘의 운세를 생성 중입니다. 잠시 후 다시 조회해 주세요.';
  if (data?.code === 'FORTUNE_PREVIOUS_ATTEMPT_FAILED') return '오늘의 운세 생성에 실패한 기록이 있어 자동 재생성할 수 없습니다.';
  return data?.message || '요청을 처리하지 못했습니다. 다시 시도해 주세요.';
}
