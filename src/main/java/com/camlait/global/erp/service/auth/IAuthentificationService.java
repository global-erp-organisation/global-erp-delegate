package com.camlait.global.erp.service.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.Utilisateur;

public interface IAuthentificationService {

	Utilisateur ajouterUtilisateur(Utilisateur utilisateur);

	Utilisateur modifierUtilisateur(Utilisateur utilisateur);

	Utilisateur obtenirUtilisateur(String codeUtilisateur);

	Page<Utilisateur> obtenirUtilisateurParCourriel(String courriel,Pageable p);

	Page<Utilisateur> listerUtilisateur(String motCle, Pageable p);

	Page<Utilisateur> listerUtilisateur(Pageable p);

	void supprimerUtilisateur(String codeUtilisateur);
}
