package ro.nca.pms.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.nca.pms.config.ServicesConfig;
import ro.nca.pms.data.entities.Pacient;
import ro.nca.pms.data.repos.PacientRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServicesConfig.class )
public class PacientServiceTest {

    @Autowired
    private PacientsService pacientsService;
    
    @Autowired
    private PacientRepository repo;
    
    @Test
    public void testAdd() {
        Pacient pacient = new Pacient();
        pacient.setFirstName( "John" );
        pacient.setLastName( "test" );
        Pacient p = pacientsService.saveUpdate( pacient );
        assertNotNull(p.getId());
        repo.delete( p );        
    }
    @Test
    public void testFindAll() {
        Pacient pacient = new Pacient();
        pacient.setFirstName( "John" );
        pacient.setLastName( "Test" );
        pacient = pacientsService.saveUpdate( pacient );
        List<Pacient> pacients = pacientsService.getAllPacients();
        final Long pacientId = pacient.getId();
        Optional<Pacient> value = pacients
                .stream()
                .filter(a -> a.getId() == pacientId)
                .findFirst();
        assertNotNull( value.get() );
        repo.delete( pacient );        
    }
    
    
    
}
