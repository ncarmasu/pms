package ro.nca.pms.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

import ro.nca.pms.config.Profiles;
import ro.nca.pms.data.audit.AuditingDateTimeProvider;
import ro.nca.pms.data.audit.IntegrationDateTimeService;
import ro.nca.pms.data.audit.IntegrationUsernameAuditor;
import ro.nca.pms.data.audit.UsernameAuditorAware;
import ro.nca.pms.services.DateTimeService;
import ro.nca.pms.services.impl.CurrentTimeDateTimeService;

@Configuration
@Import({ PersistenceContext.class })
public class DataAppConfig {

    @Autowired
    private Environment env;

    @Profile({ Profiles.DEV, Profiles.PROD })
    @Bean
    DateTimeService currentTimeDateTimeService(){
        return new CurrentTimeDateTimeService();
    }

    // @Profile({Profiles.INTEGRATION, Profiles.TEST})
    @Bean
    DateTimeService integrationDateTimeService(){
        return new IntegrationDateTimeService();
    }

    @Bean
    @Profile({ Profiles.DEV, Profiles.PROD })
    AuditorAware<String> auditorProvider(){
        return new UsernameAuditorAware();
    }

    @Bean
    AuditorAware<String> integrationAuditorProvider(){
        return new IntegrationUsernameAuditor( env );
    }

    @Bean
    DateTimeProvider dateTimeProvider( DateTimeService dateTimeService ){
        return new AuditingDateTimeProvider( dateTimeService );
    }
}
