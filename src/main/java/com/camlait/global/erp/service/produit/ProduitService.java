package com.camlait.global.erp.service.produit;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyIllegalArgumentException;
import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.CategorieProduitTaxe;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;
import com.camlait.global.erp.domain.util.Utility;

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
        verifyIllegalArgumentException(produit, "produit");
        produitDao.save(produit);
        return produit;
    }
    
    @Override
    public Produit modifierProduit(Produit produit) {
        verifyIllegalArgumentException(produit, "produit");
        produit.setDerniereMiseAJour(new Date());
        produitDao.saveAndFlush(produit);
        return produit;
    }
    
    @Override
    public Produit obtenirProduit(Long produitId) {
        verifyIllegalArgumentException(produitId, "produitId");
        final Produit p = produitDao.findOne(produitId);
        verifyObjectNoFindException(p, Produit.class, produitId);
        Hibernate.initialize(p.getProduitTaxes());
        return p;
    }
    
    @Override
    public Produit obtenirProduit(String codeProduit) {
        verifyIllegalArgumentException(codeProduit, "codeProduit");
        final List<Produit> produits = produitDao.findByCodeProduit(codeProduit, new PageRequest(0, 1)).getContent();
        final Produit p = (produits.isEmpty()) ? null : produits.get(0);
        verifyObjectNoFindException(p, Produit.class, codeProduit);
        Hibernate.initialize(p.getProduitTaxes());
        return p;
    }
    
    @Override
    public void supprimerProduit(Long produitId) {
        produitDao.delete(obtenirProduit(produitId));
    }
    
    @Override
    public Set<Produit> listerProduit(CategorieProduit categorie) {
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
    public Set<Produit> listerProduit(Collection<CategorieProduit> categories) {
        final Set<Produit> produits = new HashSet<>();
        categories.stream().forEach(c -> produits.addAll(listerProduit(c)));
        return produits;
    }
    
    @Override
    public CategorieProduit ajouterCategorieProduit(CategorieProduit categorie) {
        verifyIllegalArgumentException(categorie, "categorie");
        categorieProduitDao.save(categorie);
        return categorie;
    }
    
    @Override
    public CategorieProduit modifierCategorieProduit(CategorieProduit categorie) {
        verifyIllegalArgumentException(categorie, "categorie");
        categorie.setDerniereMiseAJour(new Date());
        categorieProduitDao.save(categorie);
        return categorie;
    }
    
    @Override
    public CategorieProduit obtenirCategorieProduit(Long categorieId) {
        verifyIllegalArgumentException(categorieId, "categorieId");
        final CategorieProduit c = categorieProduitDao.findOne(categorieId);
        verifyObjectNoFindException(c, CategorieProduit.class, categorieId);
        Hibernate.initialize(c.getProduits());
        Hibernate.initialize(c.getCategorieProduitTaxes());
        return c;
    }
    
    @Override
    public CategorieProduit obtenirCategorieProduit(String codeCategorie) {
        verifyIllegalArgumentException(codeCategorie, "codeCategorie");
        final List<CategorieProduit> categories = categorieProduitDao.findBycodeCategorieProduit(codeCategorie, new PageRequest(0, 1)).getContent();
        final CategorieProduit c = (categories.isEmpty()) ? null : categories.get(0);
        verifyObjectNoFindException(c, CategorieProduit.class, codeCategorie);
        Hibernate.initialize(c.getProduits());
        Hibernate.initialize(c.getCategorieProduitTaxes());
        return c;
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
        verifyIllegalArgumentException(produitTaxe, "produitTaxe");
        produitTaxeDao.save(produitTaxe);
        return produitTaxe;
    }
    
    @Override
    public ProduitTaxe modifierProduitTaxe(ProduitTaxe produitTaxe) {
        verifyIllegalArgumentException(produitTaxe, "produitTaxe");
        produitTaxe.setDerniereMiseAJour(new Date());
        produitTaxeDao.saveAndFlush(produitTaxe);
        return produitTaxe;
    }
    
    @Override
    public ProduitTaxe obtenirProduitTaxe(Long produitTaxeId) {
        verifyIllegalArgumentException(produitTaxeId, "produitTaxeId");
        final ProduitTaxe p = produitTaxeDao.findOne(produitTaxeId);
        verifyObjectNoFindException(p, ProduitTaxe.class, produitTaxeId);
        return p;
    }
    
    @Override
    public void supprimerProduitTaxe(Long produitTaxeId) {
        produitTaxeDao.delete(obtenirProduitTaxe(produitTaxeId));
    }
    
    @Override
    public CategorieProduitTaxe ajouterCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe) {
        verifyIllegalArgumentException(categorieProduitTaxe, "categorieProduitTaxe");
        categorieProduitTaxeDao.save(categorieProduitTaxe);
        return categorieProduitTaxe;
    }
    
    @Override
    public CategorieProduitTaxe modifierCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe) {
        verifyIllegalArgumentException(categorieProduitTaxe, "categorieProduitTaxe");
        categorieProduitTaxeDao.saveAndFlush(categorieProduitTaxe);
        return categorieProduitTaxe;
    }
    
    @Override
    public CategorieProduitTaxe obtenirCategorieProduitTaxe(Long categorieProduitTaxeId) {
        verifyIllegalArgumentException(categorieProduitTaxeId, "categorieProduitTaxeId");
        final CategorieProduitTaxe c = categorieProduitTaxeDao.findOne(categorieProduitTaxeId);
        verifyObjectNoFindException(c, CategorieProduitTaxe.class, categorieProduitTaxeId);
        return c;
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
