package com.camlait.global.erp.service.inventaire;

import java.util.Collection;
import java.util.Date;

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
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Inventaire.class, inventaireId));
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

	@Override
	public LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) {
		if (ligne != null) {
			ligneInventaireDao.save(ligne);
		}
		return ligne;
	}

	@Override
	public Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) {
		if (lignes != null) {
			ligneInventaireDao.save(lignes);
		}
		return lignes;
	}

	@Override
	public LigneInventaire modifierLigneInventaire(LigneInventaire ligne) {
		ligneInventaireDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneInventaire trouverLigneInventaire(Long ligneId) {
		LigneInventaire li = ligneInventaireDao.findOne(ligneId);
		if (li != null) {
			return li;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneInventaire.class, ligneId));
		}
	}

	@Override
	public void supprimerLigneInventaire(Long ligneId) {
		ligneInventaireDao.delete(trouverLigneInventaire(ligneId));
	}

}
