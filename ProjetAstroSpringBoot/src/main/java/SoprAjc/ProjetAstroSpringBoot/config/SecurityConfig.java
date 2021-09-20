package SoprAjc.ProjetAstroSpringBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/img/**", "/musiques/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.antMatcher("/**").authorizeRequests().anyRequest().permitAll().and().csrf().ignoringAntMatchers("/**");
		// @formatter:off
		http.antMatcher("/api/**")
				.csrf().ignoringAntMatchers("/api/**")
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/inscription", "/save").anonymous()
				.antMatchers(HttpMethod.OPTIONS,"/api/**").anonymous()
				.antMatchers(HttpMethod.POST, "/api/compte").anonymous()
				.antMatchers(HttpMethod.POST, "/api/compte/inscription").anonymous()
				.antMatchers(HttpMethod.POST, "/api/CorpsCeleste").hasRole("USER")
			.and()
			.httpBasic();
//			.and()
//			.authorizeRequests().anyRequest().authenticated();
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
