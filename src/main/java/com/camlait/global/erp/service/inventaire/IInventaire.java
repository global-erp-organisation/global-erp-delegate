package com.camlait.global.erp.service.inventaire;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.inventaire.Inventaire;

public interface IInventaire {

	/**
	 * Ajouter un inventaire.
	 * @param inventaire inventaire à ajouter.
	 * @return 
	 */
	Inventaire ajouterInventaire(Inventaire inventaire);

	/**
	 * Modifier un inventaire.
	 * @param inventaire inventaire à modifier
	 * @return inventaire modifiée.
	 */
	Inventaire modifierInventaire(Inventaire inventaire);

	/**
	 * trouver un inventaire.
	 * @param inventaireId Identifiant de l'inventaire.
	 * @return Inventaire recherchée.
	 */
	Inventaire trouverInventaire(Long inventaireId);

	/**
	 * Supprimer un invventaire.
	 * @param inventaireId Identifiant de l'inventaire
	 */
	void supprimerInventaire(Long inventaireId);

	/**
	 * Lister les inventaires
	 * @param p
	 * @return
	 */
	Page<Inventaire> listerInventaire(Pageable p);

	/**
	 * Lister les inventaire d'une période.
	 * @param debut
	 * @param fin
	 * @param p
	 * @return
	 */
	Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p);

	/**
	 * Lister les inventaires d'un magasin.
	 * @param magasinId
	 * @param p
	 * @return
	 */
	Page<Inventaire> listerInventaire(Long magasinId, Pageable p);	
}
