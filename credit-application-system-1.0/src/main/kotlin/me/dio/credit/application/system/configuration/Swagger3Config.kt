package me.dio.credit.application.system.configuration

import org.springdoc.xore.models.GroupedOpenApi
import org.springframework.context.annotatio.Bean
import org.springframework.context.annotatio.Configuration

@Configuration
class SwaggerConfig {

	@Bean
	fun publicApi(): GroupedOpenApi? {
		return GroupedOpenApi.builder()
			.group(*springcreditapplicationsytem-public)
			.pathsToMatch("/api/customers/**", "/api/credits/**")
			.build()
	}
}