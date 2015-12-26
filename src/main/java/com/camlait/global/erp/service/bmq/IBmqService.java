package com.camlait.global.erp.service.bmq;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.operation.Recouvrement;

public interface IBmqService {

	/**
	 * Ajouter un bmq;
	 * 
	 * @param bmq
	 *            a ajouter.
	 * @return le bmq ajouté.
	 */
	Bmq ajouterBmq(Bmq bmq);

	/**
	 * Modifier un bmq.
	 * 
	 * @param bmq
	 *            à modifier.
	 * @return le bmq modifié.
	 */
	Bmq modifierBmq(Bmq bmq);

	/**
	 * Trouver un bmq.
	 * 
	 * @param bmqId
	 *            Identifiant du bmq à trouver.
	 * @return le bmq recherché.
	 */
	Bmq trouverBmq(Long bmqId);

	/**
	 * supprimer un bmq.
	 * 
	 * @param bmqId
	 *            identifiant du bmq à supprimer.
	 */
	void supprimerBmq(Long bmqId);

	/**
	 * lister tous les bmqs.
	 * 
	 * @param p
	 *            page à lister.
	 * @return la page de bmqs.
	 */
	Page<Bmq> listerBmq(Pageable p);

	/**
	 * Lister les bmq d'un vendeur.
	 * 
	 * @param vendeurId
	 *            identfiant du vendeur.
	 * @param p
	 *            Page à lister.
	 * @return la page de bmqs du vendeur.
	 */
	Page<Bmq> listerBmq(Long vendeurId, Pageable p);

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
	Page<Bmq> listerBmq(Date debut, Date fin, Pageable p);

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
	Page<Bmq> listerBmq(Long VendeurId, Date debut, Date fin, Pageable p);

	/**
	 * Ajouter une collection de ligne de bmq.
	 * 
	 * @param ligneBmqs
	 *            collection à ajouter.
	 * @return la collection ajoutée.
	 */
	Collection<LigneBmq> ajouterLigneBmq(Collection<LigneBmq> ligneBmqs);

	/**
	 * Trouver une ligne de bmq.
	 * 
	 * @param ligneBmqId
	 *            Identifiant de la ligne à retrouver.
	 * @return La ligne de bmq recherchée.
	 */
	LigneBmq trouverLigneBmq(Long ligneBmqId);

	/**
	 * Supprimer une ligne de bmq.
	 * 
	 * @param ligneBmqId
	 *            Identifiant de la ligne de bmq à supprimer.
	 */
	void supprimerLigneBmq(Long ligneBmqId);

	/**
	 * Générer les ligne du bmq.
	 * 
	 * @param bmq
	 *            bmq concerné.
	 * @return le noveau bmq.
	 */
	Bmq genererBmq(Long bmqId, Collection<Document> documents, Collection<Recouvrement> recouvrements);

	/**
	 * Ajouter un recouvement.
	 * 
	 * @param recouvrement
	 *            recouvrement à ajouter.
	 * @return le recouvrement ajouté.
	 */
	Recouvrement ajouterRecouvrement(Recouvrement recouvrement);

	/**
	 * Modifier un recouvrement.
	 * 
	 * @param recouvrement
	 *            Recouvrment à modifier.
	 * @return le recouvrment modifié.
	 */
	Recouvrement modifierRecouvrement(Recouvrement recouvrement);

	/**
	 * trouver un recouvrement.
	 * 
	 * @param recouvrementId
	 *            Identifiant du recouvrment à retouver.
	 * @return le recouvrement cherché.
	 */
	Recouvrement trouverRecouvrement(Long recouvrementId);

	/**
	 * Supprimer le recouvrement
	 * 
	 * @param recouvrementId
	 *            Identifiant du recouvrement à supprimer.
	 */
	void supprimerRecouvrement(Long recouvrementId);

	void supprimerLigneBmq(Bmq bmq);

}
