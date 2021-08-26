package astro.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:infos.properties")
@EnableJpaRepositories("astro.repositories")
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("dataSource.driver"));
		dataSource.setUrl(env.getProperty("dataSource.url"));
		dataSource.setUsername(env.getProperty("dataSource.username"));
		dataSource.setPassword(env.getProperty("dataSource.password"));
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(BasicDataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("astro.metier");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties properties=new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect", env.getProperty("jpaProperty.dialect"));
		properties.setProperty("hibernate.show_sql",  env.getProperty("jpaProperty.showSql"));
		properties.setProperty("hibernate.format_sql", "true");
		emf.setJpaProperties(properties);
		return emf;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager=new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}	
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
