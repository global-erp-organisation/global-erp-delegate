package com.camlait.global.erp.service.document;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;

public interface IDocumentService {

	/**
	 * Ajouter un document.
	 * 
	 * @param document
	 *            document à ajouter.
	 * @return le document ajouté.
	 */
	Document ajouterDocument(Document document);

	/**
	 * Modifier un document.
	 * 
	 * @param document
	 *            document à modifier.
	 * @return document modifié.
	 */
	Document modifierDocument(Document document);

	/**
	 * Trouver un document.
	 * 
	 * @param documentId
	 *            Identifiant du document cherché.
	 * @return Document cherché.
	 */
	Document obtenirDocument(Long documentId);

	/**
	 * SUpprimer un document.
	 * 
	 * @param documentId
	 *            Identifiant du document à supprimer.
	 */
	void supprimerDocument(Long documentId);

	/**
	 * Lister les documents
	 * 
	 * @param debut
	 *            debut de la période.
	 * @param fin
	 *            fin de la période.
	 * @param p
	 *            plage de document à retourner.
	 * @return
	 */
	Page<Document> listerDocument(Date debut, Date fin, Pageable p);

	/**
	 * Ajouter une ligne de document.
	 * 
	 * @param ligne
	 *            ligne à ajouter
	 * @return la ligne ajoutée.
	 */
	LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne);

	/**
	 * Ajouter une collection de ligne de document.
	 * 
	 * @param lignes
	 *            collection à ajouter
	 * @return la collection ajoutée.
	 */
	Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes);

	/**
	 * modifier une ligne de document
	 * 
	 * @param ligne
	 *            ligne à modifiée.
	 * @return ligne modifiée.
	 */
	LigneDeDocument modifierLigneDocument(LigneDeDocument ligne);

	/**
	 * Obtenir une ligne de document
	 * 
	 * @param ligneId
	 *            identifiant de la ligne à chercher
	 * @return ligne de document trouvée.
	 */
	LigneDeDocument obtenirLigneDocument(Long ligneId);

	/**
	 * Supprimer une ligne de document.
	 * 
	 * @param ligneId
	 *            Identifiant de la ligne à supprimer.
	 */
	void supprimerLigneDocument(Long ligneId);

	/**
	 * Supprimer les lignes d'un document.
	 * 
	 * @param document
	 *            document associé.
	 */
	void supprimerLigneDocument(Document document);

	/**
	 * Ajouter une collection de taxe à une ligne de document.
	 * 
	 * @param ligneDeDocumentTaxe
	 *            collection de taxe a ajouter.
	 * @return la collection de taxe ajoutée.
	 */
	LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe);

	/**
	 * Modifier la collection de taxe liée à une ligne de document.
	 * 
	 * @param ligneDeDocumentTaxe
	 * @return
	 */
	LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe);

	/**
	 * Obtenir la collection de taxe associée à une ligne de document.
	 * 
	 * @param ligneDeDocumentTaxeId
	 *            Identifiant de la collection.
	 * @return
	 */
	LigneDeDocumentTaxe obtenirLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId);

	/**
	 * Supprimer la collection de taxe associée à une ligne de document.
	 * 
	 * @param ligneDeDocumentTaxeId
	 */
	void spprimerLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId);
}
