import http from "./XMLHttpRequest"
import API from "./API"
import { main } from '@/store/index'

export const login = (data: any) => {
  return http.post(API("/login"), JSON.stringify(data)).then(res => {
    // 添加Token信息
    main().setToken(res.token);
    http.message.success("登录成功!");
  });
}