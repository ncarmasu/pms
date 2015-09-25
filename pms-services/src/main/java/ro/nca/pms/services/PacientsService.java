package ro.nca.pms.services;

import java.util.List;

import ro.nca.pms.data.entities.Pacient;


public interface PacientsService {

    /**
     * Adds a new pacient or returns an existing one    
     * @param pacient
     * @return the new or updated entity
     */
    Pacient saveUpdate(Pacient pacient);
    
    List<Pacient> getAllPacients();
    
}
