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
    Document trouverDocument(Long documentId);
    
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
    
    LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne);
    
    Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes);
    
    LigneDeDocument modifierLigneDocument(LigneDeDocument ligne);
    
    LigneDeDocument trouverLigneDocument(Long ligneId);
    
    void supprimerLigneDocument(Long ligneId);
    
    void supprimerLigneDocument(Document document);
    
    LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe);
    
    LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe);
    
    LigneDeDocumentTaxe trouverLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId);
    
    void spprimerLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId);
}
