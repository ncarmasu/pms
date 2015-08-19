package ro.nca.pms.services.impl;

import java.time.ZonedDateTime;

import ro.nca.pms.services.DateTimeService;

public class CurrentTimeDateTimeService implements DateTimeService {
 
    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}