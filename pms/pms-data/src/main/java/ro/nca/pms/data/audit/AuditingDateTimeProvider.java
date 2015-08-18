package ro.nca.pms.data.audit;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.DateTimeProvider;

import ro.nca.pms.services.DateTimeService;

public class AuditingDateTimeProvider implements DateTimeProvider {
    
    @Autowired
    private final DateTimeService dateTimeService;
    
    public AuditingDateTimeProvider(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }
 
    @Override
    public Calendar getNow() {
        return GregorianCalendar.from(dateTimeService.getCurrentDateAndTime());
    }
}