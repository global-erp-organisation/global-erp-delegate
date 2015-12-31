package com.camlait.global.erp.service.inventaire;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.entrepot.EntrepotDao;
import com.camlait.global.erp.dao.entrepot.MagasinDao;
import com.camlait.global.erp.dao.entrepot.StockDao;
import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.inventaire.LigneInventaireDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.LigneInventaire;
import com.camlait.global.erp.domain.inventaire.Stock;
import com.camlait.global.erp.service.GlobalErpServiceException;
import com.camlait.global.erp.service.util.IUtilService;

@Transactional
public class InventaireService implements IInventaireService {
    
    @Autowired
    InventaireDao inventaireDao;
    
    @Autowired
    private StockDao stockDao;
    
    @Autowired
    private LigneInventaireDao ligneInventaireDao;
    
    @Autowired
    private IUtilService utilService;
    
    @Autowired
    private EntrepotDao entrepotDao;
    
    @Autowired
    private MagasinDao magasinDao;
    
    @Override
    public Inventaire ajouterInventaire(Inventaire inventaire) {
        if (inventaire == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("inventaire"));
        }
        inventaire.setCodeInventaire(utilService.genererCode(inventaire));
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
        final Inventaire inv = inventaireDao.findOne(inventaireId);
        if (inv != null) {
            Hibernate.initialize(inv.getLigneInventaires());
            return inv;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Inventaire.class, inventaireId));
        }
    }
    
    @Override
    public Inventaire obtenirInventaire(String codeInventaire) {
        if (codeInventaire == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeInventaire"));
        }
        final List<Inventaire> inventaires = inventaireDao.findByCodeInventaire(codeInventaire, new PageRequest(0, 1)).getContent();
        final Inventaire inv = (inventaires.isEmpty()) ? null : inventaires.get(0);
        if (inv != null) {
            Hibernate.initialize(inv.getLigneInventaires());
            return inv;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Inventaire.class, codeInventaire));
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
    
    @Override
    public Stock obtenirStock(Long magasinId, Long produitId) {
        return stockDao.obtenirStock(magasinId, produitId);
    }
    
    @Override
    public Collection<Stock> listerStockParProduit(Long produitId) {
        return stockDao.listerStockParProduit(produitId);
    }
    
    @Override
    public Collection<Stock> listerStockParMagasin(Long magasinId) {
        return stockDao.listerStockParMagasin(magasinId);
    }
    
    @Override
    public Entrepot ajouterEntrepot(Entrepot entrepot) {
        if (entrepot == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("entrepot"));
        }
        entrepot.setCodeEntrepot(utilService.genererCode(entrepot));
        entrepotDao.save(entrepot);
        return entrepot;
    }
    
    @Override
    public Entrepot modifierEntrepot(Entrepot entrepot) {
        if (entrepot == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("entrepot"));
        }
        entrepot.setDerniereMiseAJour(new Date());
        entrepotDao.saveAndFlush(entrepot);
        return entrepot;
    }
    
    @Override
    public Entrepot obtenirEntrepot(Long entrepotId) {
        if (entrepotId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("entrepotId"));
        }
        final Entrepot e = entrepotDao.findOne(entrepotId);
        if (e != null) {
            Hibernate.initialize(e.getMagasins());
            return e;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Entrepot.class, entrepotId));
        }
    }
    
    @Override
    public Entrepot obtenirEntrepot(String codeEntrepot) {
        if (codeEntrepot == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeEntrepot"));
        }
        final List<Entrepot> entrepots = entrepotDao.findByCodeEntrepot(codeEntrepot, new PageRequest(0, 1)).getContent();
        final Entrepot e = (entrepots.isEmpty()) ? null : entrepots.get(0);
        if (e != null) {
            Hibernate.initialize(e.getMagasins());
            return e;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Entrepot.class, codeEntrepot));
        }
    }
      
    @Override
    public Collection<Entrepot> listerEntrepot() {
        return entrepotDao.findAll();
    }
    
    @Override
    public void supprimerEntrepot(Long entrepotId) {
        entrepotDao.delete(obtenirEntrepot(entrepotId));
    }
    
    @Override
    public Magasin ajouterMagasin(Magasin magasin) {
        if (magasin == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("magasin"));
        }
        magasin.setCodeMagasin(utilService.genererCode(magasin));
        magasinDao.save(magasin);
        return magasin;
    }
    
    @Override
    public Magasin modifierMagasin(Magasin magasin) {
        if (magasin == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("magasin"));
        }
        magasin.setDerniereMiseAJour(new Date());
        magasinDao.saveAndFlush(magasin);
        return magasin;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirMagasin(Class<T> entityClass, Long magasinId) {
        if (magasinId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("magasinId"));
        }
        final Magasin m = magasinDao.findOne(magasinId);
        if (m != null) {
            return (T) m;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Magasin.class, magasinId));
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirMagasin(Class<T> entityClass, String codeMagasin) {
        if (codeMagasin == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeMagasin"));
        }
        final List<Magasin> magasins = magasinDao.findByCodeMagasin(codeMagasin, new PageRequest(0, 1)).getContent();
        final Magasin m = (magasins.isEmpty()) ? null : magasins.get(0);
        if (m != null) {
            return (T) m;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Magasin.class, codeMagasin));
        }
    }
    
    @Override
    public Collection<Magasin> listerMagasin(Entrepot entrepot) {
        return magasinDao.listerMagasin(entrepot.getEntrepotId());
    }
    
    @Override
    public Collection<Magasin> listerMagasin(String motCle) {
        return magasinDao.listerMagasin(motCle);
    }
    
    @Override
    public void supprimerMagasin(Long magasinId) {
        magasinDao.delete(obtenirMagasin(Magasin.class, magasinId));
    }
}
