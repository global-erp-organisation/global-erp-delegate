package com.camlait.global.erp.service.partenaire;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class PartenaireService implements IPartenaireService {

	PartenaireDao partenaireDao;

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
		partenaireDao.saveAndFlush(partenaire);
		return partenaire;
	}

	@Override
	public Partenaire trouverPartenaire(Long partenaireId) {
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
		partenaireDao.delete(trouverPartenaire(partenaireId));
	}

	@Override
	public Page<Partenaire> listerPartenaire(Pageable p) {
		return partenaireDao.findAll(p);
	}

}
