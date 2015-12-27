package com.camlait.global.erp.service.produit;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.produit.CategorieProduitDao;
import com.camlait.global.erp.dao.produit.ProduitDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class ProduitService implements IProduitService {

	@Autowired
	private ProduitDao produitDao;

	@Autowired
	private CategorieProduitDao categorieProduitDao;

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
		produitDao.saveAndFlush(produit);
		return produit;
	}

	@Override
	public Produit trouverProduit(Long produitId) {
		Produit p = produitDao.findOne(produitId);
		if (p != null) {
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
	public Collection<Produit> listerProduit(Long categorieId, Pageable p) {
		return produitDao.listerProduit(categorieId, p);
	}

	@Override
	public Page<Produit> listerProduit(Pageable p) {
		return produitDao.findAll(p);
	}

	@Override
	public Collection<Produit> listerProduit(Collection<CategorieProduit> categories, Pageable p) {
		Collection<Produit> produits = new HashSet<>();
		
		for(CategorieProduit c:categories){
			produits.addAll(listerProduit(c.getCategorieProduitId(), p));
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
		categorieProduitDao.save(categorie);
		return categorie;
	}

	@Override
	public CategorieProduit trouverCategorieProduit(Long categorieId) {
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
		// TODO Auto-generated method stub
		return null;
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

}
