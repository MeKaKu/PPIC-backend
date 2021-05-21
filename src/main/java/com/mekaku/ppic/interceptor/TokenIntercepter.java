package com.mekaku.ppic.interceptor;

import com.mekaku.ppic.entity.UserInfo;
import com.mekaku.ppic.util.JwtUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenIntercepter implements HandlerInterceptor  {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //response.setHeader("token","token");
        //String name = request.getParameter("name");
        //String pwd = request.getParameter("pwd");
        //String token = jwtUtil.createJWT()
        //System.out.println("/User/Login:name="+name+" pwd="+pwd);
        //主页图片流的请求拦截
        //获取请求头中的令牌
        String token = request.getHeader("token");
        //401-用户没有登录-正常加载图片流
        if (token == null || token.isEmpty()) {
            //response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return true;
        }

        //403-用户的登录信息过期-提示需要重新进行登录
        // 注入工具类
        if (jwtUtil == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            jwtUtil = (JwtUtil) factory.getBean("jwtUtil");
        }
        UserInfo user = jwtUtil.parseJWT(token);
        if (user == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());//403
            return false;
        }
        return true;
    }
}
