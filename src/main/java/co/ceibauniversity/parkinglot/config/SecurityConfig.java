package co.ceibauniversity.parkinglot.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			//.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
			.authorizeRequests()
			.antMatchers("/**/*.html").permitAll()
			.anyRequest().permitAll()
			;
	}
}
