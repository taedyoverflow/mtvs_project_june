package com.ohgiraffers.mtvsreserve.members;

import com.ohgiraffers.mtvsreserve.members.login.common.argumentresolver.LoginMemberArgumentResolver;
import com.ohgiraffers.mtvsreserve.members.login.common.interceptor.LogInterceptor;
import com.ohgiraffers.mtvsreserve.members.login.common.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                // ** 경로 끝까지 0개 이상의 경로(/) 일치, * 경로(/) 안에서 0개 이상의 문자 일치
                .addPathPatterns("/**") // 모든 경로패턴에 대해 인터셉터 호출
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 여기 있는거 빼고

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("resources/**")
                .addPathPatterns("/post/**")
                .addPathPatterns("/reservation")
                .addPathPatterns("/doreserv")
                .addPathPatterns("/doreserv1")
                .addPathPatterns("/reservCheck")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error", "/main", "/board");
    }
}
