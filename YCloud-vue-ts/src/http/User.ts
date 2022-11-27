import { user } from '@/store/user'

const store = user();

export const login = (username: string, password: string) => {
  return store.login({ username, password });
}

export const getUnsafeToken = () => {
  
}

export function info() {
  return store.info();
}