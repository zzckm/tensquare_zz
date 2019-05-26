package com.tensquare.user.interceptor;

import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    /*
    拦截器对数据进行处理后需要和其他类进行交互一般都用request进行—request.setAttribute
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器JwtInterceptor");
        //拦截器只是负责把请求头中包含的toeknde 令牌进行一个解析验证

        //请求头中有Authorization：Bearer xxxxxxxxxxx
        String header = request.getHeader("Authorization");
        if (header!= null &&!"".equals(header)) {//使用lang3包 isEmpty() 等于  xxx==null||"".equals(xxx)
            //startsWith(xxx) 判断是否以xxx开头
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    //如何这一步jwtUtil解析token没有报错，那就代表token是符合规则的，下面就判断该令牌用户的权限了
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");

                    if (roles != null && roles.equals("admin")) {
                        //如果角色有值，直接返回给request域token值
                        request.setAttribute("claims_admin", token);
                    }
                    if (roles != null && roles.equals("user")) {
                        //如果角色有值，直接返回给request域token值
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不符合规则");
                }
            }

        }
        return true;//放行
    }
}
