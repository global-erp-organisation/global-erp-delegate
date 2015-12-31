package com.camlait.global.erp.service.auth;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Utilisateur;
import static com.camlait.global.erp.domain.config.GlobalAppConstants.*;

@Transactional
public class AuthentificationService implements IAuthentificationService {
    
    @Autowired
    private UtilisateurDao utilisateurDao;
    
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
}
