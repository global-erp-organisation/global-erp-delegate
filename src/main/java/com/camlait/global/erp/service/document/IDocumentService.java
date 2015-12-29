package com.camlait.global.erp.service.document;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IDocumentService {
    
    /**
     * Ajouter un document.
     * 
     * @param document
     *            document à ajouter.
     * @return le document ajouté.
     */
    Document ajouterDocument(Document document) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Modifier un document.
     * 
     * @param document
     *            document à modifier.
     * @return document modifié.
     */
    Document modifierDocument(Document document) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Trouver un document.
     * 
     * @param documentId
     *            Identifiant du document cherché.
     * @return Document cherché.
     */
    Document obtenirDocument(Long documentId) throws GlobalErpServiceException;
    
    /**
     * SUpprimer un document.
     * 
     * @param documentId
     *            Identifiant du document à supprimer.
     */
    void supprimerDocument(Long documentId) throws GlobalErpServiceException;
    
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
    Page<Document> listerDocument(Date debut, Date fin, Pageable p) throws GlobalErpServiceException;
    
    /**
     * Ajouter une ligne de document.
     * 
     * @param ligne
     *            ligne à ajouter
     * @return la ligne ajoutée.
     */
    LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Ajouter une collection de ligne de document.
     * 
     * @param lignes
     *            collection à ajouter
     * @return la collection ajoutée.
     */
    Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * modifier une ligne de document
     * 
     * @param ligne
     *            ligne à modifiée.
     * @return ligne modifiée.
     */
    LigneDeDocument modifierLigneDocument(LigneDeDocument ligne) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Obtenir une ligne de document
     * 
     * @param ligneId
     *            identifiant de la ligne à chercher
     * @return ligne de document trouvée.
     */
    LigneDeDocument obtenirLigneDocument(Long ligneId) throws GlobalErpServiceException;
    
    /**
     * Supprimer une ligne de document.
     * 
     * @param ligneId
     *            Identifiant de la ligne à supprimer.
     */
    void supprimerLigneDocument(Long ligneId) throws GlobalErpServiceException;
    
    /**
     * Supprimer les lignes d'un document.
     * 
     * @param document
     *            document associé.
     */
    void supprimerLigneDocument(Document document) throws GlobalErpServiceException;
    
    /**
     * Ajouter une collection de taxe à une ligne de document.
     * 
     * @param ligneDeDocumentTaxe
     *            collection de taxe a ajouter.
     * @return la collection de taxe ajoutée.
     */
    LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Modifier la collection de taxe liée à une ligne de document.
     * 
     * @param ligneDeDocumentTaxe
     * @return
     */
    LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Obtenir la collection de taxe associée à une ligne de document.
     * 
     * @param ligneDeDocumentTaxeId
     *            Identifiant de la collection.
     * @return
     */
    LigneDeDocumentTaxe obtenirLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) throws GlobalErpServiceException;
    
    /**
     * Supprimer la collection de taxe associée à une ligne de document.
     * 
     * @param ligneDeDocumentTaxeId
     */
    void spprimerLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) throws GlobalErpServiceException;
    
    /**
     * Evalue le chiffre d'affaire hors taxe d'un document
     * 
     * @param document
     * @return
     */
    double chiffreAffaireHorsTaxe(Document document);
    
    /**
     * Evalue la valeur totale des taxes d'un document
     * 
     * @param document
     * @return
     */
    double valeurTotaleTaxe(Document document);
    
    /**
     * Evalue le chiffre d'affaire toutes taxes comprises d'un document.
     * 
     * @param document
     * @return
     */
    double chiffreAffaireTTC(Document document);
    
    /**
     * Evalue la valeur d'une taxe specifique pour un document.
     * 
     * @param taxe
     * @param document
     * @return
     */
    double valeurTaxe(Taxe taxe, Document document);
    
    /**
     * Evalue la part de marge pour un document.
     * 
     * @param document
     * @return
     */
    double valeurMarge(Document document);
    
}
