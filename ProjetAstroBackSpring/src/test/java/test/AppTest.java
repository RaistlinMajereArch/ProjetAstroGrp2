package test;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import astro.AppSpring;
import astro.config.AppConfig;

public class AppTest {
	public static void main(String[] args) throws BeansException, IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		// spring
		ctx.getBeanFactory().createBean(AppSpring.class).run(args);
		// fin spring
		ctx.close();
	}
}
