package ro.nca.pms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ro.nca.pms.data.config.DataAppConfig;

@Configuration
@Import({ DataAppConfig.class })
@ComponentScan("ro.nca.pms")
public class ServicesConfig {

}
