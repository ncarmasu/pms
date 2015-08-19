package ro.nca.pms.data.audit;



import lombok.AllArgsConstructor;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

@AllArgsConstructor
public class IntegrationUsernameAuditor implements AuditorAware<String> {
    
    private static final Logger log = Logger.getLogger( IntegrationUsernameAuditor.class );
    
    private Environment env;
    
        
    @Override
    public String getCurrentAuditor() {
        String testUserName = env.getProperty( "testCurrentUsername" );
        if(StringUtils.isEmpty( testUserName)) {
            testUserName = "testUser";
            log.warn( "property 'testCurrentUsername' not set" );
        }
        return testUserName;
    }
}