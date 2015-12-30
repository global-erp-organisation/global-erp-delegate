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
import com.camlait.global.erp.service.GlobalErpServiceException;

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
    Inventaire obtenirInventaire(Long inventaireId) throws GlobalErpServiceException;
    
    /**
     * Supprimer un invventaire.
     * 
     * @param inventaireId
     *            Identifiant de l'inventaire
     */
    void supprimerInventaire(Long inventaireId) throws GlobalErpServiceException;
    
    /**
     * Lister les inventaires
     * 
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Pageable p) throws GlobalErpServiceException;
    
    /**
     * Lister les inventaire d'une période.
     * 
     * @param debut
     * @param fin
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p) throws GlobalErpServiceException;
    
    /**
     * Lister les inventaires d'un magasin.
     * 
     * @param magasinId
     * @param p
     * @return
     */
    Page<Inventaire> listerInventaire(Long magasinId, Pageable p) throws GlobalErpServiceException;
    
    LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) throws GlobalErpServiceException;
    
    Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) throws GlobalErpServiceException, IllegalArgumentException;
    
    LigneInventaire modifierLigneInventaire(LigneInventaire ligne) throws GlobalErpServiceException, IllegalArgumentException;
    
    LigneInventaire obtenirLigneInventaire(Long ligneId) throws GlobalErpServiceException;
    
    void supprimerLigneInventaire(Long ligneId) throws GlobalErpServiceException;
    
    void supprimerLigneInventaire(Inventaire inventaire) throws GlobalErpServiceException;
    
    Stock obtenirStock(Long magasinId, Long produitId);
    
    Collection<Stock> listerStockParProduit(Long produitId);
    
    Collection<Stock> listerStockParMagasin(Long magasinId);
    
    Entrepot ajouterEntrepot(Entrepot entrepot);
    
    Entrepot modifierEntrepot(Entrepot entrepot);
    
    Entrepot obtenirEntrepot(Long entrepotId);
    
    Collection<Entrepot> listerEntrepot();
    
    void supprimerEntrepot(Long entrepotId);
    
    Magasin ajouterMagasin(Magasin magasin);
    
    Magasin modifierMagasin(Magasin magasin);
    
    Magasin obtenirMagasin(Long magasinId);
    
    Collection<Magasin> listerMagasin(Entrepot entrepot);
    
    Collection<Magasin> listerMagasin(String motCle);
    
    void supprimerMagasin(Long magasinId);
    
}
