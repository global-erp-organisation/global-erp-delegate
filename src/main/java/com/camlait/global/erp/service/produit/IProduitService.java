package com.camlait.global.erp.service.produit;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;

public interface IProduitService {

	/**
	 * Ajouter un produit.
	 * 
	 * @param produit
	 *            produit à ajouter
	 * @return le produit ajouté.
	 */
	Produit ajouterProduit(Produit produit);

	/**
	 * Modifier un produit.
	 * 
	 * @param produit
	 *            produit à modifier.
	 * @return le produit modifié.
	 */
	Produit modifierProduit(Produit produit);

	/**
	 * Trouver un produit.
	 * 
	 * @param produitId
	 *            Identifiant du produit à trouver.
	 * @return produit recherché,
	 */
	Produit trouverProduit(Long produitId);

	/**
	 * Supprimer un produit.
	 * 
	 * @param produitId
	 *            Identifiant du produit à supprimer.
	 */
	void supprimerProduit(Long produitId);

	/**
	 * Lister les produits d'une categorie.
	 * 
	 * @param categorieId
	 *            Identifiant de la categorie
	 * @param p
	 * @return
	 */
	Page<Produit> listerProduit(Long categorieId, Pageable p);

	/**
	 * Lister les produit d'une collection de categorie.
	 * 
	 * @param categories
	 *            collection de categorie
	 * @param p
	 * @return
	 */
	Page<Produit> listerProduit(Collection<CategorieProduit> categories, Pageable p);

	Page<Produit> listerProduit(Pageable p);
	
	/**
	 * Ajouter une categorie de produit.
	 * 
	 * @param categorie
	 * @return
	 */
	CategorieProduit ajouterCategorieProduit(CategorieProduit categorie);

	/**
	 * Modifier une categorie de produit
	 * 
	 * @param categorie
	 * @return
	 */
	CategorieProduit modifierCategorieProduit(CategorieProduit categorie);

	/**
	 * Trouver une categorie de produit.
	 * 
	 * @param categorieId
	 * @return
	 */
	CategorieProduit trouverCategorieProduit(Long categorieId);

	/**
	 * suprimer une categorie de produit.
	 * 
	 * @param categorieId
	 */
	void supprimerCategorieProduit(Long categorieId);

	/**
	 * Lister les categorie de produits.
	 * 
	 * @param p
	 * @return
	 */
	Page<CategorieProduit> listerCategorieProduit(Pageable p);

	/**
	 * Lister les categorie fille d'une categorie.
	 * 
	 * @param parentId
	 * @param p
	 * @return
	 */
	Page<CategorieProduit> listerCategorie(Long parentId, Pageable p);
	
	void supprimerProduit(CategorieProduit categorie);
}
