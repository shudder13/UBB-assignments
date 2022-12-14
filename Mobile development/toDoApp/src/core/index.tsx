export const baseURL = 'localhost:3000';

export const getLogger: (tag: string) => (...args: any) => void = tag => (...args) => console.log(tag, ...args);

const log = getLogger('API');

export interface ResponseProps<T> {
    data: T;
}

export function withLogs<T> (promise: Promise<ResponseProps<T>>, fnName: string): Promise<T> {
    log(`${fnName} - started`);
    return promise
      .then(res => {
        log(`${fnName} - succeeded`);
        return Promise.resolve(res.data);
      })
      .catch(err => {
        log(`${fnName} - failed`);
        return Promise.reject(err);
      });
  }

export const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const authConfig = (token?: string) => ({ // get token and return headers containing that token
    headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
    }
});
