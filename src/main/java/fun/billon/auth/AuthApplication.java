package fun.billon.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * springboot启动类
 * <p>
 * 1.EnableEurekaClient 注册Eureka客户端
 * 2.EnableDiscoveryClient 开启服务发现功能
 * 3.MapperScan 扫描mapper文件
 *
 * @author billon
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("fun.billon.auth.dao")
@EnableFeignClients(basePackages = {"fun.billon.auth.api.feign"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}