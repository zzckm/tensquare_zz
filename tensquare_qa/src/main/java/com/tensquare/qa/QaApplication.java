package com.tensquare.qa;
import com.tensquare.qa.interceptor.JwtInterceptor;
import com.tensquare.util.IdWorker;
import com.tensquare.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
//有以下两个注解，才能有访问别人的前提条件。
@EnableDiscoveryClient
@EnableFeignClients
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	@Bean
	public JwtUtil jwtUtil(){
		return  new JwtUtil();
	}
	@Bean
	public JwtInterceptor jwtInterceptor(){
		return new JwtInterceptor();
	}
}
