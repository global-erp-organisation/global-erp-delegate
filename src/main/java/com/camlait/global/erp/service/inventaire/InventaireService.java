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

import lombok.NonNull;

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
	public Inventaire ajouterInventaire(@NonNull Inventaire inventaire) {
		inventaire.setCodeInventaire(utilService.genererCode(inventaire));
		inventaireDao.save(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire modifierInventaire(@NonNull Inventaire inventaire) {
		inventaire.setDerniereMiseAJour(new Date());
		inventaireDao.saveAndFlush(inventaire);
		return inventaire;
	}

	@Override
	public Inventaire obtenirInventaire(@NonNull Long inventaireId) {
		final Inventaire inv = inventaireDao.findOne(inventaireId);
		verifyObjectNoFindException(inv, Inventaire.class, inventaireId);
		Hibernate.initialize(inv.getLigneInventaires());
		return inv;
	}

	@Override
	public Inventaire obtenirInventaire(@NonNull String codeInventaire) {
		final List<Inventaire> inventaires = inventaireDao.findByCodeInventaire(codeInventaire, new PageRequest(0, 1))
				.getContent();
		final Inventaire inv = (inventaires.isEmpty()) ? null : inventaires.get(0);
		verifyObjectNoFindException(inv, Inventaire.class, codeInventaire);
		Hibernate.initialize(inv.getLigneInventaires());
		return inv;
	}

	@Override
	public void supprimerInventaire(@NonNull Long inventaireId) {
		inventaireDao.delete(obtenirInventaire(inventaireId));
	}

	@Override
	public Page<Inventaire> listerInventaire(@NonNull Pageable p) {
		return inventaireDao.findAll(p);
	}

	@Override
	public Page<Inventaire> listerInventaire(@NonNull Date debut, @NonNull Date fin, @NonNull Pageable p) {
		return inventaireDao.listerInventaire(debut, fin, p);
	}

	@Override
	public Page<Inventaire> listerInventaire(@NonNull Long magasinId, @NonNull Pageable p) {
		return inventaireDao.listerInventaire(magasinId, p);
	}

	@Override
	public LigneInventaire ajouterLigneInventaire(@NonNull LigneInventaire ligne) {
		ligneInventaireDao.save(ligne);
		return ligne;
	}

	@Override
	public Collection<LigneInventaire> ajouterLigneInventaire(@NonNull Collection<LigneInventaire> lignes) {
		ligneInventaireDao.save(lignes);
		return lignes;
	}

	@Override
	public LigneInventaire modifierLigneInventaire(@NonNull LigneInventaire ligne) {
		ligne.setDerniereMiseAJour(new Date());
		ligneInventaireDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneInventaire obtenirLigneInventaire(@NonNull Long ligneId) {
		LigneInventaire li = ligneInventaireDao.findOne(ligneId);
		verifyObjectNoFindException(li, LigneInventaire.class, ligneId);
		return li;
	}

	@Override
	public void supprimerLigneInventaire(@NonNull Long ligneId) {
		ligneInventaireDao.delete(obtenirLigneInventaire(ligneId));
	}

	@Override
	public void supprimerLigneInventaire(@NonNull Inventaire inventaire) {
		ligneInventaireDao.delete(inventaire.getLigneInventaires());
	}

	@Override
	public Stock obtenirStock(@NonNull Long magasinId, @NonNull Long produitId) {
		return stockDao.obtenirStock(magasinId, produitId);
	}

	@Override
	public Collection<Stock> listerStockParProduit(@NonNull Long produitId) {
		return stockDao.listerStockParProduit(produitId);
	}

	@Override
	public Collection<Stock> listerStockParMagasin(@NonNull Long magasinId) {
		return stockDao.listerStockParMagasin(magasinId);
	}

	@Override
	public Entrepot ajouterEntrepot(@NonNull Entrepot entrepot) {
		Function<Object, Entite> f = (e) -> {
			entrepotDao.save(entrepot);
			return entrepot;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepot, "entrepot", f);
	}

	@Override
	public Entrepot modifierEntrepot(@NonNull Entrepot entrepot) {
		Function<Object, Entite> f = (e) -> {
			entrepot.setDerniereMiseAJour(new Date());
			entrepotDao.saveAndFlush(entrepot);
			return entrepot;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepot, "entrepot", f);
	}

	@Override
	public Entrepot obtenirEntrepot(@NonNull Long entrepotId) {
		final Function<Object, Entite> f = (e) -> {
			final Entrepot en = entrepotDao.findOne(entrepotId);
			verifyObjectNoFindException(en, Entrepot.class, entrepotId);
			Hibernate.initialize(en.getMagasins());
			return en;
		};
		return (Entrepot) verifyIllegalArgumentException(entrepotId, "entrepotId", f);
	}

	@Override
	public Entrepot obtenirEntrepot(@NonNull String codeEntrepot) {
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
	public void supprimerEntrepot(@NonNull Long entrepotId) {
		entrepotDao.delete(obtenirEntrepot(entrepotId));
	}

	@Override
	public Magasin ajouterMagasin(@NonNull Magasin magasin) {
		verifyIllegalArgumentException(magasin, "magasin");
		// magasin.setCodeMagasin(utilService.genererCode(magasin));
		magasinDao.save(magasin);
		return magasin;
	}

	@Override
	public Magasin modifierMagasin(@NonNull Magasin magasin) {
		verifyIllegalArgumentException(magasin, "magasin");
		magasin.setDerniereMiseAJour(new Date());
		magasinDao.saveAndFlush(magasin);
		return magasin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirMagasin(@NonNull Class<T> entityClass, @NonNull Long magasinId) {
		verifyIllegalArgumentException(magasinId, "magasinId");
		final Magasin m = magasinDao.findOne(magasinId);
		verifyObjectNoFindException(m, entityClass, magasinId);
		Hibernate.initialize(m.getStocks());
		Hibernate.initialize(m.getFicheDeStocks());
		return (T) m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirMagasin(@NonNull Class<T> entityClass, @NonNull String codeMagasin) {
		verifyIllegalArgumentException(codeMagasin, "codeMagasin");
		final List<Magasin> magasins = magasinDao.findByCodeMagasin(codeMagasin, new PageRequest(0, 1)).getContent();
		final Magasin m = (magasins.isEmpty()) ? null : magasins.get(0);
		verifyObjectNoFindException(m, entityClass, codeMagasin);
		Hibernate.initialize(m.getStocks());
		Hibernate.initialize(m.getFicheDeStocks());
		return (T) m;
	}

	@Override
	public Collection<Magasin> listerMagasin(@NonNull Entrepot entrepot) {
		return magasinDao.listerMagasin(entrepot.getEntrepotId());
	}

	@Override
	public Collection<Magasin> listerMagasin(@NonNull String motCle) {
		return magasinDao.listerMagasin(motCle);
	}

	@Override
	public void supprimerMagasin(@NonNull Long magasinId) {
		magasinDao.delete(obtenirMagasin(Magasin.class, magasinId));
	}
}
