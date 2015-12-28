package com.camlait.global.erp.service.produit;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.produit.CategorieProduitDao;
import com.camlait.global.erp.dao.produit.ProduitDao;
import com.camlait.global.erp.dao.produit.ProduitTaxeDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class ProduitService implements IProduitService {
    
    @Autowired
    private ProduitDao produitDao;
    
    @Autowired
    private CategorieProduitDao categorieProduitDao;
    
    @Autowired
    private ProduitTaxeDao produitTaxeDao;
    
    @Transactional
    @Override
    public Produit ajouterProduit(Produit produit) {
        if (produit != null) {
            produitDao.save(produit);
        }
        return produit;
    }
    
    @Transactional
    @Override
    public Produit modifierProduit(Produit produit) {
        produit.setDerniereMiseAJour(new Date());
        produitDao.saveAndFlush(produit);
        return produit;
    }
    
    @Override
    public Produit trouverProduit(Long produitId) {
        if (produitId == null) {
            throw new IllegalArgumentException("produitId ne doit pas etre null");
        }
        Produit p = produitDao.findOne(produitId);
        if (p != null) {
            Hibernate.initialize(p.getProduitTaxes());
            return p;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Produit.class, produitId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerProduit(Long produitId) {
        produitDao.delete(trouverProduit(produitId));
    }
    
    @Override
    public Collection<Produit> listerProduit(Long categorieId) {
        return produitDao.listerProduit(categorieId);
    }
    
    @Override
    public Page<Produit> listerProduit(Pageable p) {
        return produitDao.findAll(p);
    }
    
    @Override
    public Collection<Produit> listerProduit(Collection<CategorieProduit> categories) {
        Collection<Produit> produits = new HashSet<>();
        for (CategorieProduit c : categories) {
            produits.addAll(listerProduit(c.getCategorieProduitId()));
        }
        return produits;
    }
    
    @Transactional
    @Override
    public CategorieProduit ajouterCategorieProduit(CategorieProduit categorie) {
        if (categorie != null) {
            categorieProduitDao.save(categorie);
        }
        return categorie;
    }
    
    @Transactional
    @Override
    public CategorieProduit modifierCategorieProduit(CategorieProduit categorie) {
        categorie.setDerniereMiseAJour(new Date());
        categorieProduitDao.save(categorie);
        return categorie;
    }
    
    @Override
    public CategorieProduit trouverCategorieProduit(Long categorieId) {
        if (categorieId == null) {
            throw new IllegalArgumentException("categorieId ne doit pas etre null");
        }
        CategorieProduit c = categorieProduitDao.findOne(categorieId);
        if (c != null) {
            Hibernate.initialize(c.getProduits());
            return c;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFingMessage(CategorieProduit.class, categorieId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerCategorieProduit(Long categorieId) {
        categorieProduitDao.delete(trouverCategorieProduit(categorieId));
    }
    
    @Override
    public Page<CategorieProduit> listerCategorieProduit(Pageable p) {
        return categorieProduitDao.findAll(p);
    }
    
    @Override
    public Page<CategorieProduit> listerCategorie(Long parentId, Pageable p) {
        return categorieProduitDao.listerCategorie(parentId, p);
    }
    
    @Transactional
    @Override
    public void supprimerProduit(CategorieProduit categorie) {
        produitDao.delete(categorie.getProduits());
    }
    
    @Override
    public Page<CategorieProduit> listerCategorieProduit(String motCle, Pageable p) {
        return categorieProduitDao.listerCategorieProduit(motCle, p);
    }
    
    @Override
    public Page<Produit> listerProduit(String motCle, Pageable p) {
        return produitDao.listerProduit(motCle, p);
    }
    
    @Transactional
    @Override
    public ProduitTaxe ajouterProduitTaxe(ProduitTaxe produitTaxe) {
        if (produitTaxe != null) {
            produitTaxeDao.save(produitTaxe);
        }
        return produitTaxe;
    }
    
    @Transactional
    @Override
    public ProduitTaxe modifierProduitTaxe(ProduitTaxe produitTaxe) {
        produitTaxe.setDerniereMiseAJour(new Date());
        produitTaxeDao.saveAndFlush(produitTaxe);
        return produitTaxe;
    }
    
    @Override
    public ProduitTaxe trouverProduitTaxe(Long produitTaxeId) {
        if (produitTaxeId == null) {
            throw new IllegalArgumentException("L'identifiant produitTaxeId ne doit pas etre null");
        }
        ProduitTaxe p = produitTaxeDao.findOne(produitTaxeId);
        if (p != null) {
            return p;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(ProduitTaxe.class, produitTaxeId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerProduitTaxe(Long produitTaxeId) {
        produitTaxeDao.delete(trouverProduitTaxe(produitTaxeId));
    }
    
}
