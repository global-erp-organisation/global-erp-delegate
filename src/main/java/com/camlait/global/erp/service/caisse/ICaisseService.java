package com.camlait.global.erp.service.caisse;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;

public interface ICaisseService {

	Caisse ajouterCaisse(Caisse caisse);

	Caisse modifierCaisse(Caisse caisse);

	Caisse obtenirCaisse(Long caisseId);

	void supprimerCaisse(Long caisseId);

	Page<Caisse> listerCaisse(Pageable p);

	JournalCaisse ajouterJournalCaisse(JournalCaisse journal);

	JournalCaisse modifierJournalCaisse(JournalCaisse journal);

	JournalCaisse obtenirJournalCaisse(Long journalId);

	void supprimerJournalCaisse(Long journalId);

	Page<JournalCaisse> listerJournalCaisse(Pageable p);

	OperationDeCaisse ajouterOperationDeCaisse(OperationDeCaisse operation);

	OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation);

	OperationDeCaisse obtenirOperationDeCaisse(Long operationId);

	void supprimerOperationDeCaisse(Long operationId);

	void supprimerOperationDeCaisse(JournalCaisse journal);

	Collection<OperationDeCaisse> listerOperationDeCaisse(Long journalId);

	Collection<OperationDeCaisse> listerOperationDeCaisse(Collection<JournalCaisse> journaux);

}
