package com.camlait.global.erp.service.inventaire;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.LigneInventaire;
import com.camlait.global.erp.domain.inventaire.Stock;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;

public interface IInventaireService {
    
    /**
     * Ajouter un inventaire.
     * 
     * @param inventaire
     *            inventaire à ajouter.
     * @return
     */
    Inventaire ajouterInventaire(Inventaire inventaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Modifier un inventaire.
     * 
     * @param inventaire
     *            inventaire à modifier
     * @return inventaire modifiée.
     */
    Inventaire modifierInventaire(Inventaire inventaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * trouver un inventaire.
     * 
     * @param inventaireId
     *            Identifiant de l'inventaire.
     * @return Inventaire recherchée.
     */
    Inventaire obtenirInventaire(Long inventaireId) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * trouver un inventaire.
     * 
     * @param inventaireId
     *            code de l'inventaire.
     * @return Inventaire recherchée.
     */
    Inventaire obtenirInventaire(String codeInventaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Supprimer un invventaire.
     * 
     * @param inventaireId
     *            Identifiant de l'inventaire
     */
    void supprimerInventaire(Long inventaireId) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Lister les inventaires
     * 
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Lister les inventaire d'une période.
     * 
     * @param debut
     * @param fin
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    /**
     * Lister les inventaires d'un magasin.
     * 
     * @param magasinId
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Long magasinId, Pageable p) throws GlobalErpServiceException, IllegalArgumentException;
    
    LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) throws GlobalErpServiceException, IllegalArgumentException;
    
    LigneInventaire modifierLigneInventaire(LigneInventaire ligne) throws GlobalErpServiceException, IllegalArgumentException;
    
    LigneInventaire obtenirLigneInventaire(Long ligneId) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerLigneInventaire(Long ligneId) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerLigneInventaire(Inventaire inventaire) throws GlobalErpServiceException, IllegalArgumentException;
    
    Stock obtenirStock(Long magasinId, Long produitId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<Stock> listerStockParProduit(Long produitId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<Stock> listerStockParMagasin(Long magasinId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Entrepot ajouterEntrepot(Entrepot entrepot) throws GlobalErpServiceException, IllegalArgumentException;
    
    Entrepot modifierEntrepot(Entrepot entrepot) throws GlobalErpServiceException, IllegalArgumentException;
    
    Entrepot obtenirEntrepot(Long entrepotId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Entrepot obtenirEntrepot(String codeEntrepot) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<Entrepot> listerEntrepot() throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerEntrepot(Long entrepotId) throws GlobalErpServiceException, IllegalArgumentException;
    
    Magasin ajouterMagasin(Magasin magasin) throws GlobalErpServiceException, IllegalArgumentException;
    
    Magasin modifierMagasin(Magasin magasin) throws GlobalErpServiceException, IllegalArgumentException;
    
    <T> T obtenirMagasin(Class<T> entityClass, Long magasinId) throws GlobalErpServiceException, IllegalArgumentException, ClassCastException;
    
    <T> T obtenirMagasin(Class<T> entityClass, String codeMagasin) throws GlobalErpServiceException, IllegalArgumentException, ClassCastException;
    
    Collection<Magasin> listerMagasin(Entrepot entrepot) throws GlobalErpServiceException, IllegalArgumentException;
    
    Collection<Magasin> listerMagasin(String motCle) throws GlobalErpServiceException, IllegalArgumentException;
    
    void supprimerMagasin(Long magasinId) throws GlobalErpServiceException, IllegalArgumentException, ClassCastException;
    
}
