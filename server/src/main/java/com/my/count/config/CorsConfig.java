package com.my.count.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许跨域的域名，如果要携带cookie，不能写*，*代表所有域名都可以跨域访问
//        corsConfiguration.addAllowedOrigin("http://localhost:10008");
        // 本机使用nginx测试
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);    // 允许携带cookies
        corsConfiguration.addAllowedMethod("*");    // 代表所有的请求方法：get、post、put、delete
        corsConfiguration.addAllowedHeader("*");    // 允许携带任何头信息

        // 初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);

        // 返回corsFilter实例，参数：cors配置源对象
        return new CorsFilter(configurationSource);
    }
}
