package com.dojo.services.personne.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

/**
 * Configuration Spring Boot pour installer Metrics dans un service.
 * Usage :
 * Charger en mode JavaConfig après avoir renseigné les propriétés applicatives.
 * Ajouter un bean de type GraphiteSender si nécessaire.
 *
 * Propriétés applicatives attendues :
 * manpower.service.metrics.healtcheck.uri
 * manpower.service.metrics.monitor.uri
 * manpower.service.metrics.reporter.period (minutes ; default 1)
 *
 * see https://github.com/ryantenney/metrics-spring
 * see https://github.com/codahale/metrics
 *
 *
 */
@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

	@Value("${manpower.service.metrics.healthcheck.uri}")
	private String healthCheckUri;
	@Value("${manpower.service.metrics.monitor.uri}")
	private String monitorUri;

	@Autowired(required = false)
	private GraphiteSender graphite;
	@Value("#{systemProperties.getProperty('manpower.service.metrics.monitor.period') ?: 1}")
	private Long graphitePeriod;

	@Bean
	ServletRegistrationBean healthCheckServlet(HealthCheckRegistry registry) {
		return new ServletRegistrationBean(new HealthCheckServlet(registry), this.healthCheckUri);
	}

	@Bean
	ServletRegistrationBean metricsServlet(MetricRegistry registry) {
		return new ServletRegistrationBean(new MetricsServlet(registry), this.monitorUri);
	}

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		if (this.graphite != null) {
			GraphiteReporter.forRegistry(metricRegistry)
			.build(this.graphite)
			.start(this.graphitePeriod, TimeUnit.MINUTES);
		}
	}

}
