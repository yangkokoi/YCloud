import { main } from '@/store/main'

const store = main();

export const login = (data: any) => {
  return store.login(data);
}

export const getUnsafeToken = () => {
  
}