package com.camlait.global.erp.service.organisation;

import java.util.Collection;

import com.camlait.global.erp.domain.organisation.Centre;
import com.camlait.global.erp.domain.organisation.Localisation;
import com.camlait.global.erp.domain.organisation.Region;
import com.camlait.global.erp.domain.organisation.Secteur;
import com.camlait.global.erp.domain.organisation.Zone;

public interface ILocalisationService {
    
    Localisation ajouterLocalisation(Localisation local);
    
    Localisation modifierLocalisation(Localisation local);
    
    Localisation obtenirLocalisation(Long localId);
    
    Collection<Centre> listerCentre();
    
    Collection<Region> listerRegion(Centre centre);
    
    Collection<Secteur> listerSecteur(Region region);
    
    Collection<Zone> listerZone(Secteur secteur);
    
    void supprimerLocalisation(Long localId);
}
