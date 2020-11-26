package com.accenture.training.config;

//@EnableWebSecurity
public class SpringSecurityConfig {//extends WebSecurityConfigurerAdapter {
/*
	@Autowired
	XsuaaServiceConfiguration xsuaaServiceConfiguration;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//enforce browser login popup with basic authentication
		BasicAuthenticationEntryPoint authEntryPoint = new BasicAuthenticationEntryPoint();
		authEntryPoint.setRealmName("trainingcfrest");

		// @formatter:off
		http.authorizeRequests()
				//.antMatchers("/Product/testToken").hasAuthority("Display")
				.anyRequest().authenticated()
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.exceptionHandling().authenticationEntryPoint(authEntryPoint)
			.and()
				.oauth2ResourceServer()
				.bearerTokenResolver(getTokenBrokerResolver())
				.jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter());

		// @formatter:on
	}

	BearerTokenResolver getTokenBrokerResolver() {
		Cache cache = new CaffeineCache("token",
				Caffeine.newBuilder()
						.expireAfterWrite(15, TimeUnit.MINUTES)
						.maximumSize(100).build(), false);

		return new TokenBrokerResolver(xsuaaServiceConfiguration, cache, AuthenticationMethod.BASIC);
	}


	@Bean
	Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
		TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
		converter.setLocalScopeAsAuthorities(true);
		return converter;
	}
*/
}