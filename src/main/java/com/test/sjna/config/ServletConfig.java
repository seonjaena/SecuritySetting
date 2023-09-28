package com.test.sjna.config;

import com.test.sjna.filter.DecryptFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public FilterRegistrationBean<DecryptFilter> decryptFilter() {
        FilterRegistrationBean<DecryptFilter> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new DecryptFilter());
        filterRegBean.addUrlPatterns("");
        filterRegBean.setName("decrypt-filter");
        return filterRegBean;
    }

}
