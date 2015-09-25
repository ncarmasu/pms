package ro.nca.pms.data.services.impl;

import java.time.ZonedDateTime;

import ro.nca.pms.data.services.DateTimeService;



public class CurrentDateTimeService implements DateTimeService {
 
    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}