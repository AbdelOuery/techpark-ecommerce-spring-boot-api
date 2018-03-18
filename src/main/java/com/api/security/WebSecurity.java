package com.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// Creer un filtre d'authentification & changer son url de login par defaut
    	JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(authenticationManager());
    	authFilter.setFilterProcessesUrl(SecurityConstants.LOG_IN_URL);
    		
        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
        	.cors().and().csrf().disable().authorizeRequests()
        	// Autoriser des urls
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
            .antMatchers(HttpMethod.GET, "/api/category/all").permitAll()
            .antMatchers(HttpMethod.GET, "/api/category/{id}").permitAll()
            .antMatchers(HttpMethod.GET, "/api/product/all").permitAll()
            .antMatchers(HttpMethod.GET, "/api/product/category/{id}").permitAll()
            .antMatchers(HttpMethod.GET, "/api/product/{id}").permitAll()
            .antMatchers(HttpMethod.GET, "/api/comment/product/{id}").permitAll()
            // Autoriser le reste des URL juste pour les utilisateurs authentifie
            .anyRequest().authenticated()
            .and()
            .addFilter(authFilter)
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // Desactiver les sessions
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// Specifier le crypteur des mots de passes
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
  	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
    }
}
