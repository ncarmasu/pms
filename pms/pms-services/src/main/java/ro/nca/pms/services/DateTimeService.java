package ro.nca.pms.services;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public interface DateTimeService {
 
    ZonedDateTime getCurrentDateAndTime();
}
