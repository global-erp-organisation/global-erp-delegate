package com.camlait.global.erp.service.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IPartenaireService {
    
    Partenaire ajouterPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    Partenaire modifierPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    Partenaire obtanirPartenaire(Long partenaireId) throws GlobalErpServiceException;
    
    void supprimerPartenaire(Long partenaireId) throws GlobalErpServiceException;
    
    Page<Partenaire> listerPartenaire(Pageable p) throws GlobalErpServiceException;
    
    Page<Employe> listerEmploye(String motCle, Pageable p) throws GlobalErpServiceException;
}
