package com.camlait.global.erp.service.caisse;

import java.util.Collection;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.operation.caisse.CaisseDao;
import com.camlait.global.erp.dao.operation.caisse.JournalCaisseDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class CaisseService implements ICaisseService {

	@Autowired
	CaisseDao caisseDao;

	@Autowired
	JournalCaisseDao journalCaisseDao;

	@Override
	public Caisse ajouterCaisse(Caisse caisse) {
		if (caisse != null) {
			caisseDao.save(caisse);
		}
		return caisse;
	}

	@Override
	public Caisse modifierCaisse(Caisse caisse) {
		caisseDao.saveAndFlush(caisse);
		return caisse;
	}

	@Override
	public Caisse trouverCaisse(Long caisseId) {
		Caisse c = caisseDao.findOne(caisseId);
		if (c != null) {
			return c;
		} else {
			throw new GlobalErpServiceException("La caisse ayant l'identifiant " + caisseId + " n'existe pas");
		}
	}

	@Override
	public void supprimerCaisse(Long caisseId) {
		caisseDao.delete(trouverCaisse(caisseId));
	}

	@Override
	public Page<Caisse> listerCaisse(Pageable p) {
		return caisseDao.findAll(p);
	}

	@Override
	public JournalCaisse ajouterJournalCaisse(JournalCaisse journal) {
		if (journal != null) {
			journalCaisseDao.save(journal);
		}
		return journal;
	}

	@Override
	public JournalCaisse modifierJournalCaisse(JournalCaisse journal) {
		journalCaisseDao.saveAndFlush(journal);
		return journal;
	}

	@Override
	public JournalCaisse trouverJournalCaisse(Long journalId) {
		JournalCaisse j = journalCaisseDao.findOne(journalId);
		if (j != null) {
			Hibernate.initialize(j.getOpreations());
			return j;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(JournalCaisse.class, journalId));
		}
	}

	@Override
	public void supprimerJournalCaisse(Long journalId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<JournalCaisse> listerJournalCaisse(Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationDeCaisse ajouterOperationDeCaisse(OperationDeCaisse operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationDeCaisse trouverOperationDeCaisse(Long operationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void supprimerOperationDeCaisse(Long operationId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<OperationDeCaisse> listerOperationDeCaisse(Long journalId, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OperationDeCaisse> listerOperationDeCaisse(Collection<JournalCaisse> journaux, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

}
