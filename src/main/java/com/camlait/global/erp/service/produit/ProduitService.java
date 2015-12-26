package com.camlait.global.erp.service.produit;

import java.util.Collection;

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

	@Override
	public Produit ajouterProduit(Produit produit) {
		if (produit != null) {
			produitDao.save(produit);
		}
		return produit;
	}

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

	@Override
	public void supprimerProduit(Long produitId) {
		produitDao.delete(trouverProduit(produitId));
	}

	@Override
	public Page<Produit> listerProduit(Long categorieId, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Produit> listerProduit(Pageable p) {
		return produitDao.findAll(p);
	}

	@Override
	public Page<Produit> listerProduit(Collection<CategorieProduit> categories, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieProduit ajouterCategorieProduit(CategorieProduit categorie) {
		if (categorie != null) {
			categorieProduitDao.save(categorie);
		}
		return categorie;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

}
