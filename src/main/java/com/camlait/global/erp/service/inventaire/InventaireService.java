package com.camlait.global.erp.service.inventaire;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.entrepot.EntrepotDao;
import com.camlait.global.erp.dao.entrepot.MagasinDao;
import com.camlait.global.erp.dao.entrepot.StockDao;
import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.inventaire.LigneInventaireDao;
import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.LigneInventaire;
import com.camlait.global.erp.domain.inventaire.Stock;
import com.camlait.global.erp.service.util.IUtilService;

@Transactional
public class InventaireService implements IInventaireService {

	@Autowired
	InventaireDao inventaireDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private LigneInventaireDao ligneInventaireDao;

	@Autowired
	private IUtilService utilService;

	@Autowired
	private EntrepotDao entrepotDao;

	@Autowired
	private MagasinDao magasinDao;

	@Override
	public Inventaire ajouterInventaire(Inventaire inventaire) {
		verifyIllegalArgumentException(inventaire, "inventaire");
		inventaire.setCodeInventaire(utilService.genererCode(inventaire));
		inventaireDao.save(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire modifierInventaire(Inventaire inventaire) {
		verifyIllegalArgumentException(inventaire, "inventaire");
		inventaire.setDerniereMiseAJour(new Date());
		inventaireDao.saveAndFlush(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire obtenirInventaire(Long inventaireId) {
		verifyIllegalArgumentException(inventaireId, "inventaireId");
		final Inventaire inv = inventaireDao.findOne(inventaireId);
		verifyObjectNoFindException(inv, Inventaire.class, inventaireId);
		Hibernate.initialize(inv.getLigneInventaires());
		return inv;
	}

	@Override
	public Inventaire obtenirInventaire(String codeInventaire) {
		verifyIllegalArgumentException(codeInventaire, "codeInventaire");
		final List<Inventaire> inventaires = inventaireDao.findByCodeInventaire(codeInventaire, new PageRequest(0, 1))
				.getContent();
		final Inventaire inv = (inventaires.isEmpty()) ? null : inventaires.get(0);
		verifyObjectNoFindException(inv, Inventaire.class, codeInventaire);
		Hibernate.initialize(inv.getLigneInventaires());
		return inv;
	}

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

	@Override
	public LigneInventaire ajouterLigneInventaire(LigneInventaire ligne) {
		verifyIllegalArgumentException(ligne, "ligne");
		ligneInventaireDao.save(ligne);
		return ligne;
	}

	@Override
	public Collection<LigneInventaire> ajouterLigneInventaire(Collection<LigneInventaire> lignes) {
		verifyIllegalArgumentException(lignes, "lignes");
		ligneInventaireDao.save(lignes);
		return lignes;
	}

	@Override
	public LigneInventaire modifierLigneInventaire(LigneInventaire ligne) {
		verifyIllegalArgumentException(ligne, "ligne");
		ligne.setDerniereMiseAJour(new Date());
		ligneInventaireDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneInventaire obtenirLigneInventaire(Long ligneId) {
		verifyIllegalArgumentException(ligneId, "ligneId");
		LigneInventaire li = ligneInventaireDao.findOne(ligneId);
		verifyObjectNoFindException(li, LigneInventaire.class, ligneId);
		return li;
	}

	@Override
	public void supprimerLigneInventaire(Long ligneId) {
		ligneInventaireDao.delete(obtenirLigneInventaire(ligneId));
	}

	@Override
	public void supprimerLigneInventaire(Inventaire inventaire) {
		ligneInventaireDao.delete(inventaire.getLigneInventaires());
	}

	@Override
	public Stock obtenirStock(Long magasinId, Long produitId) {
		return stockDao.obtenirStock(magasinId, produitId);
	}

	@Override
	public Collection<Stock> listerStockParProduit(Long produitId) {
		return stockDao.listerStockParProduit(produitId);
	}

	@Override
	public Collection<Stock> listerStockParMagasin(Long magasinId) {
		return stockDao.listerStockParMagasin(magasinId);
	}

	@Override
	public Entrepot ajouterEntrepot(Entrepot entrepot) {
		Function<Object, Entite> f = (e) -> {
			entrepotDao.save(entrepot);
			return entrepot;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepot, "entrepot", f);
	}

	@Override
	public Entrepot modifierEntrepot(Entrepot entrepot) {
		Function<Object, Entite> f = (e) -> {
			entrepot.setDerniereMiseAJour(new Date());
			entrepotDao.saveAndFlush(entrepot);
			return entrepot;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepot, "entrepot", f);
	}

	@Override
	public Entrepot obtenirEntrepot(Long entrepotId) {
		final Function<Object, Entite> f = (e) -> {
			final Entrepot en = entrepotDao.findOne(entrepotId);
			verifyObjectNoFindException(en, Entrepot.class, entrepotId);
			Hibernate.initialize(en.getMagasins());
			return en;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepotId, "entrepotId", f);
	}

	@Override
	public Entrepot obtenirEntrepot(String codeEntrepot) {
		verifyIllegalArgumentException(codeEntrepot, "codeEntrepot");
		final List<Entrepot> entrepots = entrepotDao.findByCodeEntrepot(codeEntrepot, new PageRequest(0, 1))
				.getContent();
		final Entrepot e = (entrepots.isEmpty()) ? null : entrepots.get(0);
		verifyObjectNoFindException(e, Entrepot.class, codeEntrepot);
		Hibernate.initialize(e.getMagasins());
		return e;
	}

	@Override
	public Collection<Entrepot> listerEntrepot() {
		return entrepotDao.findAll();
	}

	@Override
	public void supprimerEntrepot(Long entrepotId) {
		entrepotDao.delete(obtenirEntrepot(entrepotId));
	}

	@Override
	public Magasin ajouterMagasin(Magasin magasin) {
		verifyIllegalArgumentException(magasin, "magasin");
		// magasin.setCodeMagasin(utilService.genererCode(magasin));
		magasinDao.save(magasin);
		return magasin;
	}

	@Override
	public Magasin modifierMagasin(Magasin magasin) {
		verifyIllegalArgumentException(magasin, "magasin");
		magasin.setDerniereMiseAJour(new Date());
		magasinDao.saveAndFlush(magasin);
		return magasin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirMagasin(Class<T> entityClass, Long magasinId) {
		verifyIllegalArgumentException(magasinId, "magasinId");
		final Magasin m = magasinDao.findOne(magasinId);
		verifyObjectNoFindException(m, entityClass, magasinId);
		Hibernate.initialize(m.getStocks());
		Hibernate.initialize(m.getFicheDeStocks());
		return (T) m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirMagasin(Class<T> entityClass, String codeMagasin) {
		verifyIllegalArgumentException(codeMagasin, "codeMagasin");
		final List<Magasin> magasins = magasinDao.findByCodeMagasin(codeMagasin, new PageRequest(0, 1)).getContent();
		final Magasin m = (magasins.isEmpty()) ? null : magasins.get(0);
		verifyObjectNoFindException(m, entityClass, codeMagasin);
		Hibernate.initialize(m.getStocks());
		Hibernate.initialize(m.getFicheDeStocks());
		return (T) m;
	}

	@Override
	public Collection<Magasin> listerMagasin(Entrepot entrepot) {
		return magasinDao.listerMagasin(entrepot.getEntrepotId());
	}

	@Override
	public Collection<Magasin> listerMagasin(String motCle) {
		return magasinDao.listerMagasin(motCle);
	}

	@Override
	public void supprimerMagasin(Long magasinId) {
		magasinDao.delete(obtenirMagasin(Magasin.class, magasinId));
	}
}
