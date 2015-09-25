package ro.nca.pms.data;

import static org.junit.Assert.assertEquals;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import ro.nca.pms.data.config.DataAppConfig;
import ro.nca.pms.data.entities.Pacient;
import ro.nca.pms.data.repos.PacientRepository;
import ro.nca.pms.data.config.Profiles;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataAppConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class })
@Profile(Profiles.INTEGRATION)
public class PacientRepoTest {

    @Autowired
    private PacientRepository repository;

    @Test
    public void search_NoEntriesFound_ShouldReturnEmptyList(){
        List<Pacient> pacientEntries = repository.findAll();
        assertEquals( pacientEntries.size(), 0 );
    }

    @Before
    public void cleanup(){
        repository.deleteAll();
    }

    @Test
    public void addOneEntity(){

        // init
        String name = "Ion";
        String lastname = "Travolta";
        String pic = "1841124270010";
        Integer age = 30;
        Pacient pacient = getPacient( name, lastname, pic, age );
        repository.save( pacient );
        // end init

        List<Pacient> pacientEntries = repository.findAll();

        // test
        assertEquals( pacientEntries.size(), 1 );
        Pacient p = pacientEntries.get( 0 );
        Integer retreivedAge = ZonedDateTime.now().getYear() - p.getBirthDate().getYear();

        assertEquals( name, p.getFirstName() );
        assertEquals( lastname, p.getLastName() );
        assertEquals( pic, p.getPic() );
        assertEquals( age, retreivedAge );
        assertEquals( "admin", p.getCreatedBy() );
    }

    @Test
    public void addMultipleEntities(){

        repository.save( getPacient( "name1", "lName1", "asd", 20 ) );
        repository.save( getPacient( "name2", "lName2", "asd1", 30 ) );
        repository.save( getPacient( "name3", "lName3", "asd2", 40 ) );

        // repository.save( getPacient("Vasile", "Astamatei", "1841124270020", 50) );
        List<Pacient> pacientEntries = repository.findAll();
        assertEquals( pacientEntries.size(), 3 );
    }

    private Pacient getPacient( String name,
                                String lastName,
                                String pic,
                                Integer age ){
        Pacient pacient = new Pacient();
        pacient.setBirthDate( ZonedDateTime.now().minus( Period.ofYears( age ) ) );
        // pacient.setCreatedBy( "admin" );
        // pacient.setModifiedBy( "admin" );
        pacient.setFirstName( name );
        pacient.setLastName( lastName );
        pacient.setPic( pic );
        return pacient;
    }

}
