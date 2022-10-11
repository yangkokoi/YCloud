import { main } from '@/store/main'

const store = main();

export const login = (username: string, password: string) => {
  return store.login({ username, password });
}

export const getUnsafeToken = () => {
  
}