package com.camlait.global.erp.service.auth;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.Langue;
import com.camlait.global.erp.domain.auth.Ressource;
import com.camlait.global.erp.domain.auth.RessourceUtilisateur;
import com.camlait.global.erp.domain.auth.Terme;
import com.camlait.global.erp.domain.auth.TermeLangue;
import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.model.json.auth.LangueModel;

public interface IAuthentificationService {
    
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur) throws GlobalErpServiceException, IllegalArgumentException;
    
    Utilisateur modifierUtilisateur(Utilisateur utilisateur) throws GlobalErpServiceException, IllegalArgumentException;
    
    Utilisateur obtenirUtilisateur(String codeUtilisateur) throws GlobalErpServiceException;
    
    Page<Utilisateur> obtenirUtilisateurParCourriel(String courriel, Pageable p)
            throws GlobalErpServiceException, IllegalArgumentException;
            
    Page<Utilisateur> listerUtilisateur(String motCle, Pageable p) throws GlobalErpServiceException;
    
    Page<Utilisateur> listerUtilisateur(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerUtilisateur(String codeUtilisateur) throws GlobalErpServiceException, IllegalArgumentException;
    
    Ressource ajouterRessource(Ressource ressource) throws GlobalErpServiceException, IllegalArgumentException;
    
    Ressource modifierRessource(Ressource ressource) throws GlobalErpServiceException, IllegalArgumentException;
    
    Ressource obtenirRessource(Long ressourceId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Ressource obtenirRessource(String codeRessource) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerRessource(Long ressourceId) throws GlobalErpServiceException, IllegalArgumentException;
    
    RessourceUtilisateur ajouterRessourceUtilisateur(RessourceUtilisateur ressourceUtilisateur)
            throws GlobalErpServiceException, IllegalArgumentException;
            
    RessourceUtilisateur modifierRessourceUtilisateur(RessourceUtilisateur ressourceUtilisateur)
            throws GlobalErpServiceException, IllegalArgumentException;
            
    RessourceUtilisateur obtenirRessourceUtilisateur(Long ressourceUtilisateurId)
            throws GlobalErpServiceException, IllegalArgumentException;
            
    void supprimerRessourceUtilisateur(Long ressourceUtilisateurId)
            throws GlobalErpServiceException, IllegalArgumentException;
            
    Langue ajouterLangue(Langue langue) throws GlobalErpServiceException, IllegalArgumentException;
    
    Langue modifierLangue(Langue langue) throws GlobalErpServiceException, IllegalArgumentException;
    
    Langue obtenirLangue(Long langueId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Langue obtenirLangue(String codeLangue) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerLangue(Long langueId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<LangueModel> listerLangue();
    
    Terme ajouterTerme(Terme terme);
    
    Terme modifierTerme(Terme terme);
    
    Terme obtenirTerme(Long termeId);
    
    Terme obtenirTerme(String descriptionTerme);
    
    void supprimerTerme(Long termeId);
    
    Page<Terme> listerTerme(Pageable p);
    
    Map<String, String> listerTerme(Long langueId);
    
    Map<String, String> listerTerme(String codeLangue);
    
    TermeLangue ajouterTermeLangue(TermeLangue termeLangue);
    
    TermeLangue modifierTermeLangue(TermeLangue termeLangue);
    
    TermeLangue obtenirTermeLangue(Long termeLangueId);
    
    void supprimerTermeLangue(Long termeLangueId);
    
    boolean termeNonCharge(String descriptionTerme, Langue langue);
}
