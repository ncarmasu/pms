package ro.nca.pms.data.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.nca.pms.config.Profiles;
import ro.nca.pms.data.audit.AuditingDateTimeProvider;
import ro.nca.pms.data.audit.IntegrationUsernameAuditor;
import ro.nca.pms.data.audit.UsernameAuditorAware;
import ro.nca.pms.services.DateTimeService;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = { "ro.nca.pms.data.repos" })
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistenceContext {
    private static final String [] ENTITY_PACKAGES = { "ro.nca.pms.data.entities" };

    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
    private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DB_URL = "db.url";
    private static final String PROPERTY_NAME_DB_USER = "db.username";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
   

    /**
     * Creates and configures the HikariCP datasource bean.
     * 
     * @param env
     *            The runtime environment of our application.
     * @return
     */
    @Bean
   // @Profile({ Profiles.DEV, Profiles.PROD })
    public DataSource dataSource( Environment env ){
        MysqlDataSource dataSource = new MysqlDataSource();
        
        dataSource.setUrl( env.getRequiredProperty( PROPERTY_NAME_DB_URL ) );
        dataSource.setPassword( env.getRequiredProperty( PROPERTY_NAME_DB_PASSWORD ) );
        dataSource.setUser( env.getRequiredProperty( PROPERTY_NAME_DB_USER ) );

        return dataSource;
    }

    // /**
    // * Creates and configures the HikariCP datasource bean.
    // *
    // * @param env
    // * The runtime environment of our application.
    // * @return
    // */
//    @Bean(name = "dataSource")
//    // @Profile({Profiles.INTEGRATION, Profiles.TEST})
//    public DataSource integrationDataSource( Environment env ){
//        JdbcDataSource dataSource = new JdbcDataSource();
//        dataSource.setURL( env.getRequiredProperty( "db.url" ) );
//        dataSource.setUser( env.getRequiredProperty( "db.username" ) );
//        dataSource.setPassword( env.getRequiredProperty( "db.password" ) );
//
//        return dataSource;
//    }

    /**
     * Creates the bean that creates the JPA entity manager factory.
     * 
     * @param dataSource
     *            The datasource that provides the database connections.
     * @param env
     *            The runtime environment of our application.
     * @return
     */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory( DataSource dataSource,
                                                                 Environment env ){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource( dataSource );
        entityManagerFactoryBean.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        entityManagerFactoryBean.setPackagesToScan( ENTITY_PACKAGES );

        Properties jpaProperties = new Properties();

        // Configures the used database dialect. This allows Hibernate to create SQL
        // that is optimized for the used database.
        jpaProperties.put( PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty( PROPERTY_NAME_HIBERNATE_DIALECT ) );

        // Specifies the action that is invoked to the database when the Hibernate
        // SessionFactory is created or closed.
        jpaProperties.put( PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty( PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO ) );

        // Configures the naming strategy that is used when Hibernate creates
        // new database objects and schema elements
        jpaProperties.put( PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, env.getRequiredProperty( PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY ) );

        // If the value of this property is true, Hibernate writes all SQL
        // statements to the console.
        jpaProperties.put( PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty( PROPERTY_NAME_HIBERNATE_SHOW_SQL ) );

        // If the value of this property is true, Hibernate will use prettyprint
        // when it writes SQL to the console.
        jpaProperties.put( PROPERTY_NAME_HIBERNATE_FORMAT_SQL, env.getRequiredProperty( PROPERTY_NAME_HIBERNATE_FORMAT_SQL ) );

        entityManagerFactoryBean.setJpaProperties( jpaProperties );

        return entityManagerFactoryBean;
    }

    /**
     * Creates the transaction manager bean that integrates the used JPA provider with the Spring transaction mechanism.
     * 
     * @param entityManagerFactory
     *            The used JPA entity manager factory.
     * @return
     */
    @Bean
    JpaTransactionManager transactionManager( EntityManagerFactory entityManagerFactory ){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory );
        return transactionManager;
    }
}