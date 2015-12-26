package com.camlait.global.erp.service.bmq;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.bmq.BmqDao;
import com.camlait.global.erp.dao.bmq.LigneBmqDao;
import com.camlait.global.erp.dao.operation.RecouvrementDao;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class BmqService implements IBmqService {

	@Autowired
	private BmqDao bmqDao;

	@Autowired
	private LigneBmqDao ligneBmqDao;

	private RecouvrementDao recouvrementDao;

	@Transactional
	@Override
	public Bmq ajouterBmq(Bmq bmq) {
		if (bmq != null) {
			bmqDao.save(bmq);
		}
		return bmq;
	}

	@Transactional
	@Override
	public Bmq modifierBmq(Bmq bmq) {
		bmqDao.saveAndFlush(bmq);
		return bmq;
	}

	@Override
	public Bmq trouverBmq(Long bmqId) {
		final Bmq b = bmqDao.findOne(bmqId);
		if (b != null) {
			Hibernate.initialize(b.getLigneBmqs());
			Hibernate.initialize(b.getDocuments());
			Hibernate.initialize(b.getRecouvrements());
			return b;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Bmq.class, bmqId));
		}
	}

	@Transactional
	@Override
	public void supprimerBmq(Long bmqId) {
		bmqDao.delete(trouverBmq(bmqId));
	}

	@Override
	public Page<Bmq> listerBmq(Pageable p) {
		return bmqDao.findAll(p);
	}

	@Override
	public Page<Bmq> listerBmq(Long vendeurId, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Bmq> listerBmq(Date debut, Date fin, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Bmq> listerBmq(Long VendeurId, Date debut, Date fin, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Collection<LigneBmq> ajouterLigneBmq(Collection<LigneBmq> ligneBmqs) {
		if (ligneBmqs != null) {
			ligneBmqDao.save(ligneBmqs);
		}
		return ligneBmqs;
	}

	@Override
	public LigneBmq trouverLigneBmq(Long ligneBmqId) {
		final LigneBmq lb = ligneBmqDao.findOne(ligneBmqId);
		if (lb != null) {
			return lb;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneBmq.class, ligneBmqId));
		}
	}

	@Transactional
	@Override
	public void supprimerLigneBmq(Long ligneBmqId) {
		ligneBmqDao.delete(trouverLigneBmq(ligneBmqId));
	}

	@Transactional
	@Override
	public Bmq genererBmq(Long bmqId, Collection<Document> documents, Collection<Recouvrement> recouvrements) {
		final Bmq bmq = trouverBmq(bmqId);
		bmq.setLigneBmqs(genererLigneBmq(bmq, documents));
		bmq.setRecouvrements(recouvrements);
		bmq.setDocuments(documents);
		return bmq;
	}

	/**
	 * Genere les ligne du bmq à partir des documents associés.
	 * 
	 * @param bmq
	 *            Bmq concerné.
	 * @param documents
	 *            documents associés.
	 * @return
	 */
	private Collection<LigneBmq> genererLigneBmq(Bmq bmq, Collection<Document> documents) {
		final Collection<LigneBmq> lignes = new HashSet<>();
		for (final Document d : documents) {
			for (final LigneDeDocument ld : d.getLigneDocuments()) {
				LigneBmq lb = new LigneBmq();
				lb.setBmq(bmq);
				lb.setDocument(d);
				lb.setPrixUnitaireLigne(ld.getPrixunitaiteLigne());
				lb.setProduit(ld.getProduit());
				lb.setQuantiteLigne(ld.getQuantiteLigne());
				lignes.add(lb);
			}
		}
		return lignes;
	}

	@Transactional
	@Override
	public Recouvrement ajouterRecouvrement(Recouvrement recouvrement) {
		if (recouvrement != null) {
			recouvrementDao.save(recouvrement);
		}
		return recouvrement;
	}

	@Transactional
	@Override
	public Recouvrement modifierRecouvrement(Recouvrement recouvrement) {
		recouvrementDao.saveAndFlush(recouvrement);
		return recouvrement;
	}

	@Override
	public Recouvrement trouverRecouvrement(Long recouvrementId) {
		Recouvrement r = recouvrementDao.findOne(recouvrementId);
		if (r != null) {
			return r;
		} else {
			throw new GlobalErpServiceException(
					"Le recouvrment ayant l'identifiant " + recouvrementId + " n'existe pas");
		}
	}

	@Transactional
	@Override
	public void supprimerRecouvrement(Long recouvrementId) {
		recouvrementDao.delete(trouverRecouvrement(recouvrementId));
	}

	@Transactional
	@Override
	public void supprimerLigneBmq(Bmq bmq) {
		ligneBmqDao.delete(bmq.getLigneBmqs());
	}
}
