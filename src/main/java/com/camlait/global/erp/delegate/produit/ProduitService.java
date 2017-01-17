package com.camlait.global.erp.delegate.produit;

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
import com.camlait.global.erp.dao.produit.TarifDao;
import com.camlait.global.erp.dao.produit.TarificationDao;
import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.CategorieProduitTaxe;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;
import com.camlait.global.erp.domain.produit.Tarif;
import com.camlait.global.erp.domain.produit.Tarification;
import com.camlait.global.erp.domain.util.Utility;

import lombok.NonNull;

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

	@Autowired
	private TarifDao tarifDao;

	@Autowired
	private TarificationDao tarificationDao;

	@Override
	public Produit ajouterProduit(@NonNull Produit produit) {
		produitDao.save(produit);
		return produit;
	}

	@Override
	public Produit modifierProduit(@NonNull Produit produit) {
		produit.setDerniereMiseAJour(new Date());
		produitDao.saveAndFlush(produit);
		return produit;
	}

	@Override
	public Produit obtenirProduit(@NonNull Long produitId) {
		final Produit p = produitDao.findOne(produitId);
		verifyObjectNoFindException(p, Produit.class, produitId);
		Hibernate.initialize(p.getProduitTaxes());
		Hibernate.initialize(p.getStocks());
		Hibernate.initialize(p.getFicheDeStocks());
		Hibernate.initialize(p.getTarifications());
		return p;
	}

	@Override
	public Produit obtenirProduit(@NonNull String codeProduit) {
		final List<Produit> produits = produitDao.findByCodeProduit(codeProduit, new PageRequest(0, 1)).getContent();
		final Produit p = (produits.isEmpty()) ? null : produits.get(0);
		verifyObjectNoFindException(p, Produit.class, codeProduit);
		Hibernate.initialize(p.getProduitTaxes());
		return p;
	}

	@Override
	public void supprimerProduit(@NonNull Long produitId) {
		produitDao.delete(obtenirProduit(produitId));
	}

	@Override
	public Set<Produit> listerProduit(@NonNull CategorieProduit categorie) {
		if (Utility.isDetail(categorie)) {
			return produitDao.listerProduit(categorie.getCategorieProduitId());
		} else {
			return listerProduit(listerCategorie(categorie.getCategorieProduitId()));
		}
	}

	@Override
	public Page<Produit> listerProduit(@NonNull Pageable p) {
		return produitDao.findAll(p);
	}

	@Override
	public Set<Produit> listerProduit(@NonNull Collection<CategorieProduit> categories) {
		final Set<Produit> produits = new HashSet<>();
		categories.stream().forEach(c -> produits.addAll(listerProduit(c)));
		return produits;
	}

	@Override
	public CategorieProduit ajouterCategorieProduit(@NonNull CategorieProduit categorie) {
		categorieProduitDao.save(categorie);
		return categorie;
	}

	@Override
	public CategorieProduit modifierCategorieProduit(@NonNull CategorieProduit categorie) {
		categorie.setDerniereMiseAJour(new Date());
		categorieProduitDao.save(categorie);
		return categorie;
	}

	@Override
	public CategorieProduit obtenirCategorieProduit(@NonNull Long categorieId) {
		final CategorieProduit c = categorieProduitDao.findOne(categorieId);
		verifyObjectNoFindException(c, CategorieProduit.class, categorieId);
		Hibernate.initialize(c.getProduits());
		Hibernate.initialize(c.getCategorieProduitTaxes());
		Hibernate.initialize(c.getCategorieFilles());
		return c;
	}

	@Override
	public CategorieProduit obtenirCategorieProduit(@NonNull String codeCategorie) {
		final List<CategorieProduit> categories = categorieProduitDao
				.findBycodeCategorieProduit(codeCategorie, new PageRequest(0, 1)).getContent();
		final CategorieProduit c = (categories.isEmpty()) ? null : categories.get(0);
		verifyObjectNoFindException(c, CategorieProduit.class, codeCategorie);
		Hibernate.initialize(c.getProduits());
		Hibernate.initialize(c.getCategorieProduitTaxes());
		Hibernate.initialize(c.getCategorieFilles());
		return c;
	}

	@Override
	public void supprimerCategorieProduit(@NonNull Long categorieId) {
		categorieProduitDao.delete(obtenirCategorieProduit(categorieId));
	}

	@Override
	public Collection<CategorieProduit> listerCategorieProduit() {
		return categorieProduitDao.findAll();
	}

	@Override
	public Collection<CategorieProduit> listerCategorie(@NonNull Long parentId) {
		return categorieProduitDao.listerCategorie(parentId);
	}

	@Override
	public void supprimerProduit(@NonNull CategorieProduit categorie) {
		produitDao.delete(categorie.getProduits());
	}

	@Override
	public Collection<CategorieProduit> listerCategorieProduit(@NonNull String motCle) {
		return categorieProduitDao.listerCategorieProduit(motCle);
	}

	@Override
	public Collection<Produit> listerProduit(@NonNull String motCle) {
		return produitDao.listerProduit(motCle);
	}

	@Override
	public ProduitTaxe ajouterProduitTaxe(@NonNull ProduitTaxe produitTaxe) {
		produitTaxeDao.save(produitTaxe);
		return produitTaxe;
	}

	@Override
	public ProduitTaxe modifierProduitTaxe(@NonNull ProduitTaxe produitTaxe) {
		produitTaxe.setDerniereMiseAJour(new Date());
		produitTaxeDao.saveAndFlush(produitTaxe);
		return produitTaxe;
	}

	@Override
	public ProduitTaxe obtenirProduitTaxe(@NonNull Long produitTaxeId) {
		final ProduitTaxe p = produitTaxeDao.findOne(produitTaxeId);
		verifyObjectNoFindException(p, ProduitTaxe.class, produitTaxeId);
		return p;
	}

	@Override
	public void supprimerProduitTaxe(@NonNull Long produitTaxeId) {
		produitTaxeDao.delete(obtenirProduitTaxe(produitTaxeId));
	}

	@Override
	public CategorieProduitTaxe ajouterCategorieProduitTaxe(@NonNull CategorieProduitTaxe categorieProduitTaxe) {
		categorieProduitTaxeDao.save(categorieProduitTaxe);
		return categorieProduitTaxe;
	}

	@Override
	public CategorieProduitTaxe modifierCategorieProduitTaxe(@NonNull CategorieProduitTaxe categorieProduitTaxe) {
		categorieProduitTaxeDao.saveAndFlush(categorieProduitTaxe);
		return categorieProduitTaxe;
	}

	@Override
	public CategorieProduitTaxe obtenirCategorieProduitTaxe(@NonNull Long categorieProduitTaxeId) {
		final CategorieProduitTaxe c = categorieProduitTaxeDao.findOne(categorieProduitTaxeId);
		verifyObjectNoFindException(c, CategorieProduitTaxe.class, categorieProduitTaxeId);
		return c;
	}

	@Override
	public void supprimerCategorieProduitTaxe(@NonNull Long categorieProduitTaxeId) {
		categorieProduitTaxeDao.delete(obtenirCategorieProduitTaxe(categorieProduitTaxeId));
	}

	@Override
	public Collection<CategorieProduit> listerCategorie(@NonNull Portee portee) {
		return listerCategorieProduit().stream().filter(c -> c.getPortee() == portee).collect(Collectors.toList());
	}

	/** Gestion de la tarification **/

	@Override
	public Tarif ajouterTarif(@NonNull Tarif tarif) {
		tarifDao.save(tarif);
		return tarif;
	}

	@Override
	public Tarif modifierTarif(@NonNull Tarif tarif) {
		tarifDao.saveAndFlush(tarif);
		return tarif;
	}

	@Override
	public Tarif obtenirTarif(@NonNull Long tarifId) {
		Tarif t = tarifDao.findOne(tarifId);
		return t;
	}

	@Override
	public void supprimerTarif(@NonNull Long tarifId) {
		tarifDao.delete(obtenirTarif(tarifId));
	}

	@Override
	public Tarification ajouterTarification(@NonNull Tarification tarification) {
		tarificationDao.save(tarification);
		return tarification;
	}

	@Override
	public Tarification modifierTarification(@NonNull Tarification tarification) {
		tarificationDao.saveAndFlush(tarification);
		return tarification;
	}

	@Override
	public Tarification obtenirTarification(@NonNull Long tarificationId) {
		Tarification t = tarificationDao.findOne(tarificationId);
		return t;
	}

	@Override
	public void supprimerTarification(@NonNull Long tarificationId) {
		tarificationDao.delete(obtenirTarification(tarificationId));
	}
}
