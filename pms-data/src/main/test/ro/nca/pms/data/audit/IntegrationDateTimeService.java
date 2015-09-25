package ro.nca.pms.data.audit;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ro.nca.pms.data.services.DateTimeService;


@Service
public class IntegrationDateTimeService implements DateTimeService{

    @Autowired
    private Environment env;
    
    @Override
    public ZonedDateTime getCurrentDateAndTime(){
        return ZonedDateTime.parse( env.getProperty( "testTime" ) );
    }

}
