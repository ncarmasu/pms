package ro.nca.pms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.nca.pms.data.entities.Pacient;
import ro.nca.pms.data.repos.PacientRepository;
import ro.nca.pms.services.PacientsService;

@Component
public class PacientServiceImpl implements PacientsService {

    @Autowired
    private PacientRepository pacientRepository;

    @Override
    public List<Pacient> getAllPacients(){
        return pacientRepository.findAll();
    }

    @Override
    public Pacient saveUpdate( Pacient pacient ){
        return pacientRepository.save( pacient );
    }
    
}
