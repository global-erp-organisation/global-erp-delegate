package com.camlait.global.erp.service.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.Groupe;
import com.camlait.global.erp.domain.auth.Ressource;
import com.camlait.global.erp.domain.auth.RessourceUtilisateur;
import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;

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
	
}
