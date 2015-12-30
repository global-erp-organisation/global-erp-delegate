package com.camlait.global.erp.service.bmq;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.bmq.LigneBmqTaxe;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IBmqService {

	/**
	 * Ajouter un bmq;
	 * 
	 * @param bmq
	 *            a ajouter.
	 * @return le bmq ajouté.
	 */
	Bmq ajouterBmq(Bmq bmq) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Modifier un bmq.
	 * 
	 * @param bmq
	 *            à modifier.
	 * @return le bmq modifié.
	 */
	Bmq modifierBmq(Bmq bmq) throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver un bmq.
	 * 
	 * @param bmqId
	 *            Identifiant du bmq à trouver.
	 * @return le bmq recherché.
	 */
	Bmq obtenirBmq(Long bmqId) throws GlobalErpServiceException;

	/**
	 * supprimer un bmq.
	 * 
	 * @param bmqId
	 *            identifiant du bmq à supprimer.
	 */
	void supprimerBmq(Long bmqId) throws GlobalErpServiceException;

	/**
	 * lister tous les bmqs.
	 * 
	 * @param p
	 *            page à lister.
	 * @return la page de bmqs.
	 */
	Page<Bmq> listerBmq(Pageable p) throws GlobalErpServiceException;

	/**
	 * Lister les bmq d'un vendeur.
	 * 
	 * @param vendeurId
	 *            identfiant du vendeur.
	 * @param p
	 *            Page à lister.
	 * @return la page de bmqs du vendeur.
	 */
	Page<Bmq> listerBmq(Long vendeurId, Pageable p) throws GlobalErpServiceException;

	/**
	 * Lister tous les bmqs q'une periode.
	 * 
	 * @param debut
	 *            début de la periode.
	 * @param fin
	 *            fin de la période.
	 * @param p
	 *            page à lister.
	 * @return la page de bmqs de la periode.
	 */
	Page<Bmq> listerBmq(Date debut, Date fin, Pageable p) throws GlobalErpServiceException;

	/**
	 * Lister les bmqs d'un vendeur pour une période.
	 * 
	 * @param VendeurId
	 *            identifiant du vendeur.
	 * @param debut
	 *            debut de la période.
	 * @param fin
	 *            fin de la période.
	 * @param p
	 *            page à afficher.
	 * @return la liste des bmqs du vendeur pour la période.
	 */
	Page<Bmq> listerBmq(Long VendeurId, Date debut, Date fin, Pageable p) throws GlobalErpServiceException;

	/**
	 * Ajouter une collection de ligne de bmq.
	 * 
	 * @param ligneBmqs
	 *            collection à ajouter.
	 * @return la collection ajoutée.
	 */
	Collection<LigneBmq> ajouterLigneBmq(Collection<LigneBmq> ligneBmqs)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Trouver une ligne de bmq.
	 * 
	 * @param ligneBmqId
	 *            Identifiant de la ligne à retrouver.
	 * @return La ligne de bmq recherchée.
	 */
	LigneBmq obtenirLigneBmq(Long ligneBmqId) throws GlobalErpServiceException;

	/**
	 * Supprimer une ligne de bmq.
	 * 
	 * @param ligneBmqId
	 *            Identifiant de la ligne de bmq à supprimer.
	 */
	void supprimerLigneBmq(Long ligneBmqId) throws GlobalErpServiceException;

	/**
	 * Générer les ligne du bmq.
	 * 
	 * @param bmq
	 *            bmq concerné.
	 * @return le noveau bmq.
	 */
	Bmq genererBmq(Long bmqId, Collection<Document> documents, Collection<Recouvrement> recouvrements)
			throws GlobalErpServiceException;

	/**
	 * Ajouter un recouvement.
	 * 
	 * @param recouvrement
	 *            recouvrement à ajouter.
	 * @return le recouvrement ajouté.
	 */
	Recouvrement ajouterRecouvrement(Recouvrement recouvrement)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * Modifier un recouvrement.
	 * 
	 * @param recouvrement
	 *            Recouvrment à modifier.
	 * @return le recouvrment modifié.
	 */
	Recouvrement modifierRecouvrement(Recouvrement recouvrement)
			throws GlobalErpServiceException, IllegalArgumentException;

	/**
	 * trouver un recouvrement.
	 * 
	 * @param recouvrementId
	 *            Identifiant du recouvrment à retouver.
	 * @return le recouvrement cherché.
	 */
	Recouvrement obtenirRecouvrement(Long recouvrementId) throws GlobalErpServiceException;

	/**
	 * Supprimer le recouvrement
	 * 
	 * @param recouvrementId
	 *            Identifiant du recouvrement à supprimer.
	 */
	void supprimerRecouvrement(Long recouvrementId) throws GlobalErpServiceException;

	void supprimerLigneBmq(Bmq bmq) throws GlobalErpServiceException;

	LigneBmqTaxe ajouterLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe)
			throws GlobalErpServiceException, IllegalArgumentException;

	LigneBmqTaxe modifierLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe)
			throws GlobalErpServiceException, IllegalArgumentException;

	LigneBmqTaxe trouverLigneBmqTaxe(Long ligneBmqTaxeId) throws GlobalErpServiceException;

	void supprimerLigneBmqTaxe(Long ligneBmqTaxeId) throws GlobalErpServiceException;

	double chiffreAffaireHorsTaxe(Bmq bmq);

	double chiffreAffaireTTC(Bmq bmq);

	double valeurTotaleTaxe(Bmq bmq);

	double valeurTaxe(Taxe taxe, Bmq bmq);

	double venteComptant(Bmq bmq);
	
	double valeurMarge(Bmq bmq);

}
