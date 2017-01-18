package com.camlait.global.erp.delegate.produit;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.CategorieProduitTaxe;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.produit.ProduitTaxe;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.prix.Tarif;
import com.camlait.global.erp.domain.prix.Tarification;

public interface IProduitService {

	/**
	 * Ajouter un produit.
	 * 
	 * @param produit
	 *            produit à ajouter
	 * @return le produit ajouté.
	 */
	Produit ajouterProduit(Produit produit) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Modifier un produit.
	 * 
	 * @param produit
	 *            produit à modifier.
	 * @return le produit modifié.
	 */
	Produit modifierProduit(Produit produit) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver un produit.
	 * 
	 * @param produitId
	 *            Identifiant du produit à trouver.
	 * @return produit recherché,
	 */
	Produit obtenirProduit(Long produitId) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver un produit.
	 * 
	 * @param produitId
	 *            code du produit à trouver.
	 * @return produit recherché,
	 */
	Produit obtenirProduit(String codeProduit) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Supprimer un produit.
	 * 
	 * @param produitId
	 *            Identifiant du produit à supprimer.
	 */
	void supprimerProduit(Long produitId) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les produits d'une categorie.
	 * 
	 * @param categorie
	 *            Categorie associe
	 * @return
	 */
	Set<Produit> listerProduit(CategorieProduit categorie) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les produit d'une collection de categorie.
	 * 
	 * @param categories
	 *            collection de categorie
	 * @return
	 */
	Set<Produit> listerProduit(Collection<CategorieProduit> categories)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister tous les produits par page.
	 * 
	 * @param p
	 *            page a retourner
	 * @return
	 */
	Page<Produit> listerProduit(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Ajouter une categorie de produit.
	 * 
	 * @param categorie
	 * @return
	 */
	CategorieProduit ajouterCategorieProduit(CategorieProduit categorie)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Modifier une categorie de produit
	 * 
	 * @param categorie
	 * @return
	 */
	CategorieProduit modifierCategorieProduit(CategorieProduit categorie)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver une categorie de produit.
	 * 
	 * @param categorieId
	 * @return
	 */
	CategorieProduit obtenirCategorieProduit(Long categorieId)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver une categorie de produit.
	 * 
	 * @param codeCategorie
	 * @return
	 */
	CategorieProduit obtenirCategorieProduit(String codeCategorie)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * suprimer une categorie de produit.
	 * 
	 * @param categorieId
	 */
	void supprimerCategorieProduit(Long categorieId) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les categorie de produits.
	 * 
	 * @param p
	 * @return
	 */
	Collection<CategorieProduit> listerCategorieProduit() throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les categorie fille d'une categorie.
	 * 
	 * @param parentId
	 *            Identifiant de la categorie parente
	 * @return
	 */
	Collection<CategorieProduit> listerCategorie(Long parentId)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Supprimer tous les produit d'une categorie
	 * 
	 * @param categorie
	 *            categorie associée.
	 */
	void supprimerProduit(CategorieProduit categorie) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les categorie de produit dont le code ou la description contienne
	 * un mot clé.
	 * 
	 * @param motCle
	 *            Mot clé à utiliser
	 * @return
	 */
	Collection<CategorieProduit> listerCategorieProduit(String motCle)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les produits dont le code ou la description contienne un mot clé.
	 * 
	 * @param motCle
	 * @return
	 */
	Collection<Produit> listerProduit(String motCle) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Associer une taxe à un produit.
	 * 
	 * @param produitTaxe
	 * @return
	 */
	ProduitTaxe ajouterProduitTaxe(ProduitTaxe produitTaxe) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * modifier une taxe associée à un produit.
	 * 
	 * @param produitTaxe
	 * @return
	 */
	ProduitTaxe modifierProduitTaxe(ProduitTaxe produitTaxe) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Obtenir la taxe associée à un produit.
	 * 
	 * @param produitTaxeId
	 * @return
	 */
	ProduitTaxe obtenirProduitTaxe(Long produitTaxeId) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Supprime une taxe associé à un produit.
	 * 
	 * @param produitTaxeId
	 */
	void supprimerProduitTaxe(Long produitTaxeId) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Ajouter une collection de taxe pour une categorie de produit
	 * 
	 * @param categorieProduitTaxe
	 * @return
	 */
	CategorieProduitTaxe ajouterCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Modifier une collection de taxe pour une catégorie de produit
	 * 
	 * @param categorieProduitTaxe
	 * @return
	 */
	CategorieProduitTaxe modifierCategorieProduitTaxe(CategorieProduitTaxe categorieProduitTaxe)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * obtenir une collection de taxe pour une categorie de produit
	 * 
	 * @param categorieProduitTaxeId
	 * @return
	 */
	CategorieProduitTaxe obtenirCategorieProduitTaxe(Long categorieProduitTaxeId)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * supprimer une collection de taxe pour une categorie de produit
	 * 
	 * @param categorieProduitTaxeId
	 */
	void supprimerCategorieProduitTaxe(Long categorieProduitTaxeId)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Lister les catégoiries de produit en fonction de leur portée (DETAIL ou
	 * TOTAL)
	 * 
	 * @param portee
	 *            portée des catégories à lister.
	 * @return
	 */
	Collection<CategorieProduit> listerCategorie(Portee portee)
			throws GlobalErpServiceException, IllegalArgumentException;
	
	//Gestion des tarifs

	Tarif ajouterTarif(Tarif tarif);

	Tarif modifierTarif(Tarif tarif);

	Tarif obtenirTarif(Long tarifId);

	void supprimerTarif(Long tarifId);

	Tarification ajouterTarification(Tarification tarification);

	Tarification modifierTarification(Tarification tarification);

	Tarification obtenirTarification(Long tarificationId);

	void supprimerTarification(Long tarificationId);

}
