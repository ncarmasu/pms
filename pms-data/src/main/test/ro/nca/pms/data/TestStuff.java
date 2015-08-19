package ro.nca.pms.data;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;

import org.junit.Test;

public class TestStuff {
    
    @Test
    public void testX() {
        System.out.println(ZonedDateTime.now().minus( Period.ofYears( 30 ) ));
    }

}
