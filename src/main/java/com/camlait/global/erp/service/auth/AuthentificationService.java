package com.camlait.global.erp.service.auth;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.RessourceDao;
import com.camlait.global.erp.dao.auth.RessourceUtilisateurDao;
import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Ressource;
import com.camlait.global.erp.domain.auth.RessourceUtilisateur;
import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.*;

@Transactional
public class AuthentificationService implements IAuthentificationService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	@Autowired
	private RessourceDao ressourceDao;

	@Autowired
	private RessourceUtilisateurDao ressourceUtilisateurDao;

	@Override
	public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
		verifyIllegalArgumentException(utilisateur, "utilisateur");
		utilisateurDao.save(utilisateur);
		return utilisateur;
	}

	@Override
	public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
		verifyIllegalArgumentException(utilisateur, "utilisateur");
		utilisateur.setDerniereMiseAJour(new Date());
		utilisateurDao.saveAndFlush(utilisateur);
		return utilisateur;
	}

	@Override
	public Utilisateur obtenirUtilisateur(String codeUtilisateur) {
		verifyIllegalArgumentException(codeUtilisateur, "codeUtilisateur");
		final Utilisateur u = utilisateurDao.findOne(codeUtilisateur);
		verifyObjectNoFindException(u, Utilisateur.class, codeUtilisateur);
		Hibernate.initialize(u.getEmployes());
		//Hibernate.initialize(u.getRessourceUtilsateurs());
		//Hibernate.initialize(u.getGroupeUtilisateurs());
		return u;
	}

	@Override
	public Page<Utilisateur> obtenirUtilisateurParCourriel(String courriel, Pageable p) {
		return utilisateurDao.findUtilisateurByCourriel(courriel, p);
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
		utilisateurDao.delete(obtenirUtilisateur(codeUtilisateur));
	}

	@Override
	public Ressource ajouterRessource(Ressource ressource) throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressource, "ressource");
		ressourceDao.save(ressource);
		return ressource;
	}

	@Override
	public Ressource modifierRessource(Ressource ressource) throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressource, "ressource");
		ressourceDao.saveAndFlush(ressource);
		return ressource;
	}

	@Override
	public Ressource obtenirRessource(Long ressourceId) throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceId, "ressourceId");
		final Ressource r = ressourceDao.findOne(ressourceId);
		verifyObjectNoFindException(r, Ressource.class, ressourceId);
		return r;
	}

	@Override
	public Ressource obtenirRessource(String codeRessource) throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(codeRessource, "codeRessource");
		final List<Ressource> ressources = ressourceDao.findByCodeRessource(codeRessource, new PageRequest(0, 1))
				.getContent();
		final Ressource r = (ressources.isEmpty()) ? null : ressources.get(0);
		verifyObjectNoFindException(r, Ressource.class, codeRessource);
		return r;

	}

	@Override
	public void supprimerRessource(Long ressourceId) throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceId, "ressourceId");
		ressourceDao.delete(obtenirRessource(ressourceId));
	}

	@Override
	public RessourceUtilisateur ajouterRessourceUtilisateur(RessourceUtilisateur ressourceUtilisateur)
			throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceUtilisateur, "ressourceUtilisateur");
		ressourceUtilisateurDao.save(ressourceUtilisateur);
		return ressourceUtilisateur;
	}

	@Override
	public RessourceUtilisateur modifierRessourceUtilisateur(RessourceUtilisateur ressourceUtilisateur)
			throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceUtilisateur, "ressourceUtilisateur");
		ressourceUtilisateurDao.saveAndFlush(ressourceUtilisateur);
		return ressourceUtilisateur;
	}

	@Override
	public RessourceUtilisateur obtenirRessourceUtilisateur(Long ressourceUtilisateurId)
			throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceUtilisateurId, "ressourceUtilisateurId");
		RessourceUtilisateur ru = ressourceUtilisateurDao.findOne(ressourceUtilisateurId);
		verifyObjectNoFindException(ru, RessourceUtilisateur.class, ressourceUtilisateurId);
		return ru;
	}

	@Override
	public void supprimerRessourceUtilisateur(Long ressourceUtilisateurId)
			throws GlobalErpServiceException, IllegalArgumentException {
		verifyIllegalArgumentException(ressourceUtilisateurId, "ressourceUtilisateurId");
		ressourceUtilisateurDao.delete(obtenirRessourceUtilisateur(ressourceUtilisateurId));
	}
}
