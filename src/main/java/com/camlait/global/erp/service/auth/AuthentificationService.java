package com.camlait.global.erp.service.auth;

import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class AuthentificationService implements IAuthentificationService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	@Override
	public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
		if (utilisateur != null) {
			utilisateurDao.save(utilisateur);
		}
		return utilisateur;
	}

	@Override
	public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
	    utilisateur.setDerniereMiseAJour(new Date());
		utilisateurDao.saveAndFlush(utilisateur);
		return utilisateur;
	}

	@Override
	public Utilisateur trouverUtilisateur(String codeUtilisateur) {
		if(codeUtilisateur==null){
			throw new IllegalArgumentException("Le code utilisateur ne doit pas etre null");
		}
		Utilisateur u = utilisateurDao.findOne(codeUtilisateur);
		if (u != null) {
			Hibernate.initialize(u.getEmployes());
			return u;
		} else {
			throw new GlobalErpServiceException(
					GlobalAppConstants.buildNotFingMessage(Utilisateur.class, codeUtilisateur));
		}
	}

	@Override
	public Page<Utilisateur> trouverUtilisateurParCourriel(String courriel,Pageable p) {
		return utilisateurDao.findUtilisateurByCourriel(courriel,p);
	}

	@Override
	public Page<Utilisateur> listerUtilisateur(String motCle, Pageable p) {
		return utilisateurDao.listerUtilisateur(motCle, p);
	}

	@Override
	public Page<Utilisateur> listerUtilisateur(Pageable p) {
		return utilisateurDao.findAll(p);
	}

	@Override
	public void supprimerUtilisateur(String codeUtilisateur) {
		utilisateurDao.delete(trouverUtilisateur(codeUtilisateur));
	}
}
