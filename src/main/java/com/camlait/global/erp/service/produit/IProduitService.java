package com.camlait.global.erp.service.produit;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;

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
	Produit obtenirProduit(Long produitId);

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
	Collection<Produit> listerProduit(Long categorieId);

	/**
	 * Lister les produit d'une collection de categorie.
	 * 
	 * @param categories
	 *            collection de categorie
	 * @param p
	 * @return
	 */
	Collection<Produit> listerProduit(Collection<CategorieProduit> categories);

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
	CategorieProduit obtenirCategorieProduit(Long categorieId);

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
	 * @return
	 */
	Collection<CategorieProduit> listerCategorie(Long parentId);

	/**
	 * Supprimer tous les produit d'une categorie
	 * 
	 * @param categorie
	 *            categorie associée.
	 */
	void supprimerProduit(CategorieProduit categorie);

	/**
	 * Lister les categorie de produit dont le code ou la description contienne
	 * un mot clé.
	 * 
	 * @param motCle
	 *            Mot clé à utiliser
	 * @param p
	 * @return
	 */
	Collection<CategorieProduit> listerCategorieProduit(String motCle);

	/**
	 * Lister les produits dont le code ou la description contienne un mot clé.
	 * 
	 * @param motCle
	 * @param p
	 * @return
	 */
	Collection<Produit> listerProduit(String motCle);

	/**
	 * Associer une taxe à un produit.
	 * 
	 * @param produitTaxe
	 * @return
	 */
	ProduitTaxe ajouterProduitTaxe(ProduitTaxe produitTaxe);

	/**
	 * modifier une taxe associée à un produit.
	 * 
	 * @param produitTaxe
	 * @return
	 */
	ProduitTaxe modifierProduitTaxe(ProduitTaxe produitTaxe);

	/**
	 * Obtenir la taxe associée à un produit.
	 * 
	 * @param produitTaxeId
	 * @return
	 */
	ProduitTaxe obtenirProduitTaxe(Long produitTaxeId);

	/**
	 * Supprime une taxe associé à un produit.
	 * 
	 * @param produitTaxeId
	 */
	void supprimerProduitTaxe(Long produitTaxeId);

}
