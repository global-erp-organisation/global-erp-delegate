package com.camlait.global.erp.service.caisse;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.operation.caisse.CaisseDao;
import com.camlait.global.erp.dao.operation.caisse.JournalCaisseDao;
import com.camlait.global.erp.dao.operation.caisse.OperationDeCaisseDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
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
	private OperationDeCaisseDao operationCaisseDao;

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
		if (caisseId == null) {
			throw new IllegalArgumentException("caisseId ne doit pas etre null");
		}
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
		if (journalId == null) {
			throw new IllegalArgumentException("journalId ne doit pas etre null");
		}
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
			operationCaisseDao.save(operation);
		}
		return operation;
	}

	@Transactional
	@Override
	public OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) {
		operationCaisseDao.saveAndFlush(operation);
		return operation;
	}

	@Override
	public OperationDeCaisse trouverOperationDeCaisse(Long operationId) {
		OperationDeCaisse o = operationCaisseDao.findOne(operationId);
		if (o != null) {
			return o;
		} else {
			throw new GlobalErpServiceException(
					GlobalAppConstants.buildNotFingMessage(OperationDeCaisse.class, operationId));
		}
	}

	@Transactional
	@Override
	public void supprimerOperationDeCaisse(Long operationId) {
		operationCaisseDao.delete(trouverOperationDeCaisse(operationId));
	}

	@Override
	public Collection<OperationDeCaisse> listerOperationDeCaisse(Long journalId) {
		return operationCaisseDao.listerOperationDeCaisse(journalId);
	}

	@Override
	public Collection<OperationDeCaisse> listerOperationDeCaisse(Collection<JournalCaisse> journaux) {
		Collection<OperationDeCaisse> op = new HashSet<>();
		for (JournalCaisse j : journaux) {
			op.addAll(listerOperationDeCaisse(j.getJournalId()));
		}
		return op;
	}

	@Transactional
	@Override
	public void supprimerOperationDeCaisse(JournalCaisse journal) {
		operationCaisseDao.delete(journal.getOpreations());
	}
}
