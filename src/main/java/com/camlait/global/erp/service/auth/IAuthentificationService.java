package com.camlait.global.erp.service.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IAuthentificationService {
    
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur) throws GlobalErpServiceException, IllegalArgumentException;
    
    Utilisateur modifierUtilisateur(Utilisateur utilisateur) throws GlobalErpServiceException, IllegalArgumentException;
    
    Utilisateur obtenirUtilisateur(String codeUtilisateur) throws GlobalErpServiceException;
    
    Page<Utilisateur> obtenirUtilisateurParCourriel(String courriel, Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    Page<Utilisateur> listerUtilisateur(String motCle, Pageable p) throws GlobalErpServiceException;
    
    Page<Utilisateur> listerUtilisateur(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerUtilisateur(String codeUtilisateur) throws GlobalErpServiceException, IllegalArgumentException;
}
