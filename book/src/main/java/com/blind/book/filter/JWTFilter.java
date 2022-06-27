package com.blind.book.filter;

import com.blind.api.Utils.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String header = request.getHeader("Authorization");
        if(!StringUtils.hasText(header)){
            filterChain.doFilter(request,response);
            return;
        }
        //放行：filterChain.doFilter();
        //解析token
        if(!JWTUtil.verify(header)){
            filterChain.doFilter(request,response);
            return;
        }

        String s = JWTUtil.getPayloadByBase64(header);
        s=s.substring(s.indexOf("[")+1,s.indexOf("]"));
        s=s.replace("\"","");
        List<GrantedAuthority> list =new ArrayList<>();
        list.add(new SimpleGrantedAuthority(s));
        //存入securitycontextholder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request, response,list);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}