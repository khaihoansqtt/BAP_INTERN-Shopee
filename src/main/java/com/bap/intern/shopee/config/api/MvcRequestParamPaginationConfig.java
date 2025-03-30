package com.bap.intern.shopee.config.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class MvcRequestParamPaginationConfig implements WebMvcConfigurer {
    private static final String DEFAULT_PAGE_PARAMETER = "_currentPage";
    private static final String DEFAULT_SIZE_PARAMETER = "_pageSize";


    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setSizeParameterName(DEFAULT_SIZE_PARAMETER);
        resolver.setPageParameterName(DEFAULT_PAGE_PARAMETER);
        resolvers.add(resolver);
    }
}
