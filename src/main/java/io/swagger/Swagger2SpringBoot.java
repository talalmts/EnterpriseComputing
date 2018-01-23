package io.swagger;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.sampler.Sampler;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Encoding;
import zipkin.reporter.okhttp3.OkHttpSender;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "io.swagger")
public class Swagger2SpringBoot implements CommandLineRunner {

	@Bean
	public FilterRegistrationBean xRayServletFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AWSXRayServletFilter());
		registration.setName("XRayServletFilter");
		registration.addUrlPatterns("*");
		registration.addInitParameter("fixedName", "XRayServletFilter");
		return registration;
	}

	@Bean
	public io.opentracing.Tracer zipkinTracer(){
		OkHttpSender okHttpSender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
		AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
		Tracing braveTracer = Tracing.newBuilder()
				.localServiceName("spring-boot")
				.reporter(reporter)
				.build();
		return BraveTracer.create(braveTracer);
	}

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(Swagger2SpringBoot.class).run(args);
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}

	}
}
