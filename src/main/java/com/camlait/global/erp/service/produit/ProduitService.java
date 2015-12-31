package com.camlait.global.erp.service.produit;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.produit.CategorieProduitDao;
import com.camlait.global.erp.dao.produit.CategorieProduitTaxeDao;
import com.camlait.global.erp.dao.produit.ProduitDao;
import com.camlait.global.erp.dao.produit.ProduitTaxeDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.CategorieProduitTaxe;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;
import com.camlait.global.erp.domain.util.Utility;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class ProduitService implements IProduitService {
    
    @Autowired
    private ProduitDao produitDao;
    
    @Autowired
    private CategorieProduitDao categorieProduitDao;
    
    @Autowired
    private ProduitTaxeDao produitTaxeDao;
    
    @Autowired
    private CategorieProduitTaxeDao categorieProduitTaxeDao;
    
    @Override
    public Produit ajouterProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produit"));
        }
        produitDao.save(produit);
        return produit;
    }
    
    @Override
    public Produit modifierProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produit"));
        }
        produit.setDerniereMiseAJour(new Date());
        produitDao.saveAndFlush(produit);
        return produit;
    }
    
    @Override
    public Produit obtenirProduit(Long produitId) {
        if (produitId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produitId"));
        }
        final Produit p = produitDao.findOne(produitId);
        if (p != null) {
            Hibernate.initialize(p.getProduitTaxes());
            return p;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Produit.class, produitId));
        }
    }
    
    @Override
    public Produit obtenirProduit(String codeProduit) {
        if (codeProduit == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeProduit"));
        }
        final List<Produit> produits = produitDao.findByCodeProduit(codeProduit, new PageRequest(0, 1)).getContent();
        final Produit p = (produits.isEmpty()) ? null : produits.get(0);
        if (p != null) {
            Hibernate.initialize(p.getProduitTaxes());
            return p;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Produit.class, codeProduit));
        }
    }
    
    @Override
    public void supprimerProduit(Long produitId) {
        produitDao.delete(obtenirProduit(produitId));
    }
    
    @Override
    public Collection<Produit> listerProduit(CategorieProduit categorie) {
        if (Utility.isDetail(categorie)) {
            return produitDao.listerProduit(categorie.getCategorieProduitId());
        } else {
            return listerProduit(listerCategorie(categorie.getCategorieProduitId()));
        }
    }
    
    @Override
    public Page<Produit> listerProduit(Pageable p) {
        return produitDao.findAll(p);
    }
    
    @Override
    public Collection<Produit> listerProduit(Collection<CategorieProduit> categories) {
        final Collection<Produit> produits = new HashSet<>();
        categories.parallelStream().forEach(c -> produits.addAll(listerProduit(c)));
        return produits;
    }
    
    @Override
    public CategorieProduit ajouterCategorieProduit(CategorieProduit categorie) {
        if (categorie == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("categorie"));
        }
        categorieProduitDao.save(categorie);
        return categorie;
    }
    
    @Override
    public CategorieProduit modifierCategorieProduit(CategorieProduit categorie) {
        if (categorie == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("categorie"));
        }
        categorie.setDerniereMiseAJour(new Date());
        categorieProduitDao.save(categorie);
        return categorie;
    }
    
    @Override
    public CategorieProduit obtenirCategorieProduit(Long categorieId) {
        if (categorieId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("categorieId"));
        }
        final CategorieProduit c = categorieProduitDao.findOne(categorieId);
        if (c != null) {
            Hibernate.initialize(c.getProduits());
            Hibernate.initialize(c.getCategorieProduitTaxes());
            return c;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(CategorieProduit.class, categorieId));
        }
    }
    
    @Override
    public CategorieProduit obtenirCategorieProduit(String codeCategorie) {
        if (codeCategorie == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeCategorie"));
        }
        final List<CategorieProduit> categories = categorieProduitDao.findBycodeCategorieProduit(codeCategorie, new PageRequest(0, 1)).getContent();
        final CategorieProduit c = (categories.isEmpty()) ? null : categories.get(0);
        if (c != null) {
            Hibernate.initialize(c.getProduits());
            Hibernate.initialize(c.getCategorieProduitTaxes());
            return c;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(CategorieProduit.class, codeCategorie));
        }
    }
    
    @Override
    public void supprimerCategorieProduit(Long categorieId) {
        categorieProduitDao.delete(obtenirCategorieProduit(categorieId));
    }
    
    @Override
    public Collection<CategorieProduit> listerCategorieProduit() {
        return categorieProduitDao.findAll();
    }
    
    @Override
    public Collection<CategorieProduit> listerCategorie(Long parentId) {
        return categorieProduitDao.listerCategorie(parentId);
    }
    
    @Override
    public void supprimerProduit(CategorieProduit categorie) {
        produitDao.delete(categorie.getProduits());
    }
    
    @Override
    public Collection<CategorieProduit> listerCategorieProduit(String motCle) {
        return categorieProduitDao.listerCategorieProduit(motCle);
    }
    
    @Override
    public Collection<Produit> listerProduit(String motCle) {
        return produitDao.listerProduit(motCle);
    }
    
    @Override
    public ProduitTaxe ajouterProduitTaxe(ProduitTaxe produitTaxe) {
        
        if (produitTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produitTaxe"));
        }
        produitTaxeDao.save(produitTaxe);
        return produitTaxe;
    }
    
    @Override
    public ProduitTaxe modifierProduitTaxe(ProduitTaxe produitTaxe) {
        if (produitTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produitTaxe"));
        }
        produitTaxe.setDerniereMiseAJour(new Date());
        produitTaxeDao.saveAndFlush(produitTaxe);
        return produitTaxe;
    }
    
    @Override
    public ProduitTaxe obtenirProduitTaxe(Long produitTaxeId) {
        if (produitTaxeId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("produitTaxeId"));
        }
        final ProduitTaxe p = produitTaxeDao.findOne(produitTaxeId);
        if (p != null) {
            return p;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(ProduitTaxe.class, produitTaxeId));
        }
    }
    
    @Override
    public void supprimerProduitTaxe(Long produitTaxeId) {
        produitTaxeDao.delete(obtenirProduitTaxe(produitTaxeId));
    }
    
    @Override
    public CategorieProduitTaxe ajouterCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe) {
        if (categorieProduitTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("categorieProduitTaxe"));
        }
        categorieProduitTaxeDao.save(categorieProduitTaxe);
        return categorieProduitTaxe;
    }
    
    @Override
    public CategorieProduitTaxe modifierCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe) {
        if (categorieProduitTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("categorieProduitTaxe"));
        }
        categorieProduitTaxeDao.saveAndFlush(categorieProduitTaxe);
        return categorieProduitTaxe;
    }
    
    @Override
    public CategorieProduitTaxe obtenirCategorieProduitTaxe(Long categorieProduitTaxeId) {
        if (categorieProduitTaxeId == null) {
            throw new IllegalArgumentException(
                    GlobalAppConstants.buildIllegalArgumentMessage("categorieProduitTaxeId"));
        }
        final CategorieProduitTaxe c = categorieProduitTaxeDao.findOne(categorieProduitTaxeId);
        if (c != null) {
            return c;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(CategorieProduitTaxe.class, categorieProduitTaxeId));
        }
    }
    
    @Override
    public void supprimerCategorieProduitTaxe(Long categorieProduitTaxeId) {
        categorieProduitTaxeDao.delete(obtenirCategorieProduitTaxe(categorieProduitTaxeId));
    }
    
    @Override
    public Collection<CategorieProduit> listerCategorie(Portee portee) {
        return listerCategorieProduit().stream().filter(c -> c.getPortee() == portee).collect(Collectors.toList());
    }
}
