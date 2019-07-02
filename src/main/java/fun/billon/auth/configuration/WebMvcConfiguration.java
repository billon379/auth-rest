package fun.billon.auth.configuration;

import fun.billon.auth.api.feign.IAuthService;
import fun.billon.auth.api.interceptor.InnerKeyInterceptor;
import fun.billon.auth.api.interceptor.InnerRuleIpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webmvc配置
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@SuppressWarnings("all")
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 授权服务
     */
    @Autowired
    private IAuthService authService;

    /**
     * 內部服务id
     */
    @Value("${billon.auth.sid}")
    private String sid;

    /**
     * ip过滤规则拦截器
     */
    @Bean
    public InnerRuleIpInterceptor innerRuleIpInterceptor() {
        InnerRuleIpInterceptor innerRuleIpInterceptor = new InnerRuleIpInterceptor();
        innerRuleIpInterceptor.setAuthService(authService);
        innerRuleIpInterceptor.setSid(sid);
        return innerRuleIpInterceptor;
    }

    /**
     * key过滤规则拦截器
     */
    @Bean
    public InnerKeyInterceptor innerKeyInterceptor() {
        InnerKeyInterceptor innerKeyInterceptor = new InnerKeyInterceptor();
        innerKeyInterceptor.setAuthService(authService);
        return innerKeyInterceptor;
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(innerRuleIpInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/inner/rule/ip/**");

        registry.addInterceptor(innerKeyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/inner/**");
    }

}