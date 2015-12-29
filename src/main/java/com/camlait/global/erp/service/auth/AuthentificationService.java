package com.camlait.global.erp.service.auth;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Utilisateur;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class AuthentificationService implements IAuthentificationService {
    
    @Autowired
    private UtilisateurDao utilisateurDao;
    
    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new IllegalArgumentException(" utilisateur ne peut pa etre null");
        }
        utilisateurDao.save(utilisateur);
        return utilisateur;
    }
    
    @Override
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("utilisateur"));
        }
        utilisateur.setDerniereMiseAJour(new Date());
        utilisateurDao.saveAndFlush(utilisateur);
        return utilisateur;
    }
    
    @Override
    public Utilisateur obtenirUtilisateur(String codeUtilisateur) {
        if (codeUtilisateur == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeUtilisateur"));
        }
        Utilisateur u = utilisateurDao.findOne(codeUtilisateur);
        if (u != null) {
            Hibernate.initialize(u.getEmployes());
            return u;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(Utilisateur.class, codeUtilisateur));
        }
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
}
