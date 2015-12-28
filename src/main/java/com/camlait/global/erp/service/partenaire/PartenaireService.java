package com.camlait.global.erp.service.partenaire;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.partenaire.EmployeDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class PartenaireService implements IPartenaireService {

	@Autowired
	private PartenaireDao partenaireDao;

	@Autowired
	private EmployeDao employeDao;

	@Transactional
	@Override
	public Partenaire ajouterPartenaire(Partenaire partenaire) {
		if (partenaire != null) {
			partenaireDao.save(partenaire);
		}
		return partenaire;
	}

	@Transactional
	@Override
	public Partenaire modifierPartenaire(Partenaire partenaire) {
	    partenaire.setDerniereMiseAJour(new Date());
		partenaireDao.saveAndFlush(partenaire);
		return partenaire;
	}

	@Override
	public Partenaire obtanirPartenaire(Long partenaireId) {
		if (partenaireId == null) {
			throw new IllegalArgumentException("partenaireId ne doit pas etre null");
		}
		Partenaire p = partenaireDao.findOne(partenaireId);
		if (p != null) {
			Hibernate.initialize(p.getDocuments());
			if (p instanceof ClientAmarge) {
				Hibernate.initialize(((ClientAmarge) p).getMargeClients());
			}
			if (p instanceof Vendeur) {
				Hibernate.initialize(((Vendeur) p).getManquantFinanciers());
				Hibernate.initialize(((Vendeur) p).getPartenaireImmobilisations());
			}
			return p;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Partenaire.class, partenaireId));
		}
	}

	@Transactional
	@Override
	public void supprimerPartenaire(Long partenaireId) {
		partenaireDao.delete(obtanirPartenaire(partenaireId));
	}

	@Override
	public Page<Partenaire> listerPartenaire(Pageable p) {
		return partenaireDao.findAll(p);
	}

	@Override
	public Page<Employe> listerEmploye(String motCle, Pageable p) {
		return employeDao.listerEmploye(motCle, p);
	}

}
