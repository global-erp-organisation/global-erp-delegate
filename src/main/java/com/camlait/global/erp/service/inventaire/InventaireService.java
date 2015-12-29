package com.camlait.global.erp.service.inventaire;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.inventaire.LigneInventaireDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.LigneInventaire;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class InventaireService implements IInventaire {
    
    @Autowired
    InventaireDao inventaireDao;
    
    @Autowired
    private LigneInventaireDao ligneInventaireDao;
    
    @Override
    public Inventaire ajouterInventaire(Inventaire inventaire) {
        if (inventaire == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("inventaire"));
        }
        inventaireDao.save(inventaire);
        return inventaire;
    }
    
    @Override
    public Inventaire modifierInventaire(Inventaire inventaire) {
        if (inventaire == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("inventaire"));
        }
        inventaire.setDerniereMiseAJour(new Date());
        inventaireDao.saveAndFlush(inventaire);
        return inventaire;
    }
    
    @Override
    public Inventaire obtenirInventaire(Long inventaireId) {
        if (inventaireId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("inventaireId"));
        }
        Inventaire inv = inventaireDao.findOne(inventaireId);
        if (inv != null) {
            Hibernate.initialize(inv.getLigneInventaires());
            return inv;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Inventaire.class, inventaireId));
        }
    }
    
    @Override
    public void supprimerInventaire(Long inventaireId) {
        inventaireDao.delete(obtenirInventaire(inventaireId));
    }
    
    @Override
    public Page<Inventaire> listerInventaire(Pageable p) {
        return inventaireDao.findAll(p);
    }
    
    @Override
    public Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p) {
        return inventaireDao.listerInventaire(debut, fin, p);
    }
    
    @Override
    public Page<Inventaire> listerInventaire(Long magasinId, Pageable p) {
        return inventaireDao.listerInventaire(magasinId, p);
    }
    
    @Override
    public LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligne"));
        }
        ligneInventaireDao.save(ligne);
        return ligne;
    }
    
    @Override
    public Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) {
        if (lignes == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("lignes"));
        }
        ligneInventaireDao.save(lignes);
        return lignes;
    }
    
    @Override
    public LigneInventaire modifierLigneInventaire(LigneInventaire ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligne"));
        }
        ligne.setDerniereMiseAJour(new Date());
        ligneInventaireDao.saveAndFlush(ligne);
        return ligne;
    }
    
    @Override
    public LigneInventaire obtenirLigneInventaire(Long ligneId) {
        if (ligneId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneId"));
        }
        LigneInventaire li = ligneInventaireDao.findOne(ligneId);
        if (li != null) {
            return li;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(LigneInventaire.class, ligneId));
        }
    }
    
    @Override
    public void supprimerLigneInventaire(Long ligneId) {
        ligneInventaireDao.delete(obtenirLigneInventaire(ligneId));
    }
    
    @Override
    public void supprimerLigneInventaire(Inventaire inventaire) {
        ligneInventaireDao.delete(inventaire.getLigneInventaires());
    }
}
