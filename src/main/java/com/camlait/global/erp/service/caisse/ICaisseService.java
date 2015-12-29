package com.camlait.global.erp.service.caisse;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface ICaisseService {
    
    Caisse ajouterCaisse(Caisse caisse) throws GlobalErpServiceException, IllegalArgumentException;
    
    Caisse modifierCaisse(Caisse caisse) throws GlobalErpServiceException, IllegalArgumentException;
    
    Caisse obtenirCaisse(Long caisseId) throws GlobalErpServiceException;
    
    void supprimerCaisse(Long caisseId) throws GlobalErpServiceException;
    
    Page<Caisse> listerCaisse(Pageable p) throws GlobalErpServiceException;
    
    JournalCaisse ajouterJournalCaisse(JournalCaisse journal) throws GlobalErpServiceException, IllegalArgumentException;
    
    JournalCaisse modifierJournalCaisse(JournalCaisse journal) throws GlobalErpServiceException, IllegalArgumentException;
    
    JournalCaisse obtenirJournalCaisse(Long journalId) throws GlobalErpServiceException;
    
    void supprimerJournalCaisse(Long journalId) throws GlobalErpServiceException;
    
    Page<JournalCaisse> listerJournalCaisse(Pageable p) throws GlobalErpServiceException;
    
    OperationDeCaisse ajouterOperationDeCaisse(OperationDeCaisse operation) throws GlobalErpServiceException, IllegalArgumentException;
    
    OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) throws GlobalErpServiceException, IllegalArgumentException;
    
    OperationDeCaisse obtenirOperationDeCaisse(Long operationId) throws GlobalErpServiceException;
    
    void supprimerOperationDeCaisse(Long operationId) throws GlobalErpServiceException;
    
    void supprimerOperationDeCaisse(JournalCaisse journal) throws GlobalErpServiceException;
    
    Collection<OperationDeCaisse> listerOperationDeCaisse(Long journalId) throws GlobalErpServiceException;
    
    Collection<OperationDeCaisse> listerOperationDeCaisse(Collection<JournalCaisse> journaux) throws GlobalErpServiceException;
    
}
