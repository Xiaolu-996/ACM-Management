package com.example.demo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Xiaolu
 * @create : 2022-12-28 14:12:00
 * @order :
 * @description :
 * @solution :
 */
@Configuration
public class KeyConfig {

	@Value("${spring.datasource.username}")
	public String name;

	@Value("${spring.datasource.password}")
	public String password;

	@Bean
	public void get() {
		System.out.println("打印数据库用户名：" + name);
		System.out.println("打印数据库密码：" + password);
	}
}
