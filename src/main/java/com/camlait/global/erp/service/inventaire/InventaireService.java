package com.camlait.global.erp.service.inventaire;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.inventaire.LigneInventaireDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.LigneInventaire;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class InventaireService implements IInventaire {

	@Autowired
	InventaireDao inventaireDao;

	@Autowired
	private LigneInventaireDao ligneInventaireDao;

	@Transactional
	@Override
	public Inventaire ajouterInventaire(Inventaire inventaire) {
		if (inventaire != null) {
			inventaireDao.save(inventaire);
		}
		return inventaire;
	}

	@Transactional
	@Override
	public Inventaire modifierInventaire(Inventaire inventaire) {
	    inventaire.setDerniereMiseAJour(new Date());
		inventaireDao.saveAndFlush(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire obtenirInventaire(Long inventaireId) {
		if (inventaireId == null) {
			throw new IllegalArgumentException("inventaireId ne doit pas etre null");
		}
		Inventaire inv = inventaireDao.findOne(inventaireId);
		if (inv != null) {
			Hibernate.initialize(inv.getLigneInventaires());
			return inv;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Inventaire.class, inventaireId));
		}
	}

	@Transactional
	@Override
	public void supprimerInventaire(Long inventaireId) {
		inventaireDao.delete(obtenirInventaire(inventaireId));
	}

	@Override
	public Page<Inventaire> listerInventaire(Pageable p) {
		return inventaireDao.findAll(p);
	}

	@Override
	public Page<Inventaire> listerInventaire(Date debut, Date fin, Pageable p) {
		return inventaireDao.listerInventaire(debut, fin, p);
	}

	@Override
	public Page<Inventaire> listerInventaire(Long magasinId, Pageable p) {
		return inventaireDao.listerInventaire(magasinId, p);
	}

	@Transactional
	@Override
	public LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) {
		if (ligne != null) {
			ligneInventaireDao.save(ligne);
		}
		return ligne;
	}

	@Transactional
	@Override
	public Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) {
		if (lignes != null) {
			ligneInventaireDao.save(lignes);
		}
		return lignes;
	}

	@Transactional
	@Override
	public LigneInventaire modifierLigneInventaire(LigneInventaire ligne) {
	    ligne.setDerniereMiseAJour(new Date());
		ligneInventaireDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneInventaire obtenirLigneInventaire(Long ligneId) {
		if (ligneId == null) {
			throw new IllegalArgumentException("ligneId ne doit pas etre null");
		}
		LigneInventaire li = ligneInventaireDao.findOne(ligneId);
		if (li != null) {
			return li;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneInventaire.class, ligneId));
		}
	}

	@Transactional
	@Override
	public void supprimerLigneInventaire(Long ligneId) {
		ligneInventaireDao.delete(obtenirLigneInventaire(ligneId));
	}

	@Transactional
	@Override
	public void supprimerLigneInventaire(Inventaire inventaire) {
		ligneInventaireDao.delete(inventaire.getLigneInventaires());
	}
}
