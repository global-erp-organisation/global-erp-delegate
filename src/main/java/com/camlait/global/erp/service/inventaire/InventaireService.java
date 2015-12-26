package com.camlait.global.erp.service.inventaire;

import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class InventaireService implements IInventaire {

	@Autowired
	InventaireDao inventaireDao;

	@Override
	public Inventaire ajouterInventaire(Inventaire inventaire) {
		if (inventaire != null) {
			inventaireDao.save(inventaire);
		}
		return inventaire;
	}

	@Override
	public Inventaire modifierInventaire(Inventaire inventaire) {
		inventaireDao.saveAndFlush(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire trouverInventaire(Long inventaireId) {
		Inventaire inv = inventaireDao.findOne(inventaireId);
		if (inv != null) {
			Hibernate.initialize(inv.getLigneInventaires());
			return inv;
		} else {
			throw new GlobalErpServiceException("L'inventaire ayant l'identifiant " + inventaireId + " n'existe pas");
		}
	}

	@Override
	public void supprimerInventaire(Long inventaireId) {
		inventaireDao.delete(trouverInventaire(inventaireId));
	}

	@Override
	public Page<Inventaire> listerInventaire(Pageable p) {
		return inventaireDao.findAll(p);
	}

	@Override
	public Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Inventaire> listerInventaire(Long magasinId, Pageable p) {
		// TODO Auto-generated method stub
		return null;
	}

}
