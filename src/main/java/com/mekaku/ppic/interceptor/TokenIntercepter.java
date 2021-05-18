package com.mekaku.ppic.interceptor;

import com.mekaku.ppic.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenIntercepter implements HandlerInterceptor  {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setHeader("token","token");
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        //String token = jwtUtil.createJWT()
        System.out.println("/User/Login:name="+name+" pwd="+pwd);
        return true;
    }
}
