package com.camlait.global.erp.service.caisse;

import java.util.Collection;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.operation.OperationDao;
import com.camlait.global.erp.dao.operation.caisse.CaisseDao;
import com.camlait.global.erp.dao.operation.caisse.JournalCaisseDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.operation.Operation;
import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class CaisseService implements ICaisseService {

	@Autowired
	private CaisseDao caisseDao;

	@Autowired
	private JournalCaisseDao journalCaisseDao;

	@Autowired
	private OperationDao operationDao;

	@Transactional
	@Override
	public Caisse ajouterCaisse(Caisse caisse) {
		if (caisse != null) {
			caisseDao.save(caisse);
		}
		return caisse;
	}

	@Transactional
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

	@Transactional
	@Override
	public void supprimerCaisse(Long caisseId) {
		caisseDao.delete(trouverCaisse(caisseId));
	}

	@Override
	public Page<Caisse> listerCaisse(Pageable p) {
		return caisseDao.findAll(p);
	}

	@Transactional
	@Override
	public JournalCaisse ajouterJournalCaisse(JournalCaisse journal) {
		if (journal != null) {
			journalCaisseDao.save(journal);
		}
		return journal;
	}

	@Transactional
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

	@Transactional
	@Override
	public void supprimerJournalCaisse(Long journalId) {
		journalCaisseDao.delete(trouverJournalCaisse(journalId));
	}

	@Override
	public Page<JournalCaisse> listerJournalCaisse(Pageable p) {
		return journalCaisseDao.findAll(p);
	}

	@Transactional
	@Override
	public OperationDeCaisse ajouterOperationDeCaisse(OperationDeCaisse operation) {
		if (operation != null) {
			operationDao.save(operation);
		}
		return operation;
	}

	@Transactional
	@Override
	public OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) {
		operationDao.saveAndFlush(operation);
		return operation;
	}

	@Override
	public OperationDeCaisse trouverOperationDeCaisse(Long operationId) {
		Operation o = operationDao.findOne(operationId);
		if (o != null) {
			return (OperationDeCaisse) o;
		} else {
			throw new GlobalErpServiceException(
					GlobalAppConstants.buildNotFingMessage(OperationDeCaisse.class, operationId));
		}
	}

	@Transactional
	@Override
	public void supprimerOperationDeCaisse(Long operationId) {
		operationDao.delete(trouverOperationDeCaisse(operationId));
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

	@Transactional
	@Override
	public void supprimerOperationDeCaisse(JournalCaisse journal) {
		operationDao.delete(journal.getOpreations());
	}
}
