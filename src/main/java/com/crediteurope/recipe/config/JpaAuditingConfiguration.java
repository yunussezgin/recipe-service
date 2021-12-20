package com.crediteurope.recipe.config;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfiguration {

	@Bean
	public DateTimeProvider auditingDateTimeProvider() {
		return () -> Optional.of(OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES));
	}

}
