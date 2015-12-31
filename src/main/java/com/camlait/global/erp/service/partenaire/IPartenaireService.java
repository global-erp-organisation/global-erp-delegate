package com.camlait.global.erp.service.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IPartenaireService {
    
    Partenaire ajouterPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    Partenaire modifierPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    <T> T obtanirPartenaire(Class<T> entityClass, Long partenaireId) throws GlobalErpServiceException,ClassCastException,IllegalArgumentException;
    <T> T obtanirPartenaire(Class<T> entityClass, String codePartenaire) throws GlobalErpServiceException,ClassCastException,IllegalArgumentException;
    
    void supprimerPartenaire(Long partenaireId) throws GlobalErpServiceException,IllegalArgumentException,ClassCastException;
    
    Page<Partenaire> listerPartenaire(Pageable p) throws GlobalErpServiceException,IllegalArgumentException;
    
    Page<Employe> listerEmploye(String motCle, Pageable p) throws GlobalErpServiceException,IllegalArgumentException;
}
