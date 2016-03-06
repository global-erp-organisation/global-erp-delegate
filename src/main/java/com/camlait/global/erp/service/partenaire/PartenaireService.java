package com.camlait.global.erp.service.partenaire;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.immobilisation.ImmobilisationDao;
import com.camlait.global.erp.dao.immobilisation.PartenaireImmobilisationDao;
import com.camlait.global.erp.dao.partenaire.EmploisDao;
import com.camlait.global.erp.dao.partenaire.EmployeDao;
import com.camlait.global.erp.dao.partenaire.GroupePartenaireDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.immobilisation.Immobilisation;
import com.camlait.global.erp.domain.immobilisation.PartenaireImmobilisation;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.Emplois;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.GroupePartenaire;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;

import lombok.NonNull;

@Transactional
public class PartenaireService implements IPartenaireService {

	@Autowired
	private PartenaireDao partenaireDao;

	@Autowired
	private EmployeDao employeDao;

	// @Autowired
	// private IUtilService utilService;

	@Autowired
	private EmploisDao emploisDao;

	@Autowired
	private ImmobilisationDao immoDao;

	@Autowired
	private PartenaireImmobilisationDao pimmoDao;

	@Autowired
	private GroupePartenaireDao groupePartenaireDao;

	@Override
	public Partenaire ajouterPartenaire(@NonNull Partenaire partenaire) {
		// partenaire.setCodePartenaire(utilService.genererCode(partenaire));
		partenaireDao.save(partenaire);
		return partenaire;
	}

	@Override
	public Partenaire modifierPartenaire(@NonNull Partenaire partenaire) {
		partenaire.setDerniereMiseAJour(new Date());
		partenaireDao.saveAndFlush(partenaire);
		return partenaire;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirPartenaire(@NonNull Class<T> entityClass, @NonNull Long partenaireId) {
		final Partenaire p = partenaireDao.findOne(partenaireId);
		verifyObjectNoFindException(p, entityClass, partenaireId);
		Hibernate.initialize(p.getDocuments());
		Hibernate.initialize(p.getModeleDeReglements());
		if (p instanceof ClientAmarge) {
			Hibernate.initialize(((ClientAmarge) p).getMargeClients());
		}
		if (p instanceof Vendeur) {
			Hibernate.initialize(((Vendeur) p).getManquantFinanciers());
			Hibernate.initialize(((Vendeur) p).getPartenaireImmobilisations());
		}
		return (T) p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirPartenaire(@NonNull Class<T> entityClass, @NonNull String codePartenaire) {
		final List<Partenaire> partenaires = partenaireDao.findByCodePartenaire(codePartenaire, new PageRequest(0, 1))
				.getContent();
		final Partenaire p = (partenaires.isEmpty()) ? null : partenaires.get(0);
		verifyObjectNoFindException(p, entityClass, codePartenaire);
		Hibernate.initialize(p.getDocuments());
		if (p instanceof ClientAmarge) {
			Hibernate.initialize(((ClientAmarge) p).getMargeClients());
		}
		if (p instanceof Vendeur) {
			Hibernate.initialize(((Vendeur) p).getManquantFinanciers());
			Hibernate.initialize(((Vendeur) p).getPartenaireImmobilisations());
		}
		return (T) p;
	}

	@Override
	public void supprimerPartenaire(@NonNull Long partenaireId) {
		partenaireDao.delete(obtenirPartenaire(Partenaire.class, partenaireId));
	}

	@Override
	public Page<Partenaire> listerPartenaire(@NonNull Pageable p) {
		return partenaireDao.findAll(p);
	}

	@Override
	public Page<Employe> listerEmploye(@NonNull String motCle, @NonNull Pageable p) {
		return employeDao.listerEmploye(motCle, p);
	}

	@Override
	public Emplois ajouterEmplois(@NonNull Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException {
		emploisDao.save(emplois);
		return emplois;
	}

	@Override
	public Emplois modifierEmplois(@NonNull Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException {
		emploisDao.saveAndFlush(emplois);
		return emplois;
	}

	@Override
	public Emplois obtenirEmplois(@NonNull Long emploisId) throws GlobalErpServiceException, IllegalArgumentException {
		Emplois e = emploisDao.findOne(emploisId);
		verifyObjectNoFindException(e, Emplois.class, emploisId);
		Hibernate.initialize(e.getEmployes());
		return e;
	}

	@Override
	public Emplois obtenirEmplois(@NonNull String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException {
		List<Emplois> emplois = emploisDao.findByCodeEmplois(codeEmplois, new PageRequest(0, 1)).getContent();
		Emplois e = (emplois.isEmpty()) ? null : emplois.get(0);
		verifyObjectNoFindException(e, Emplois.class, codeEmplois);
		Hibernate.initialize(e.getEmployes());
		return e;
	}

	@Override
	public Page<Emplois> listerEmplois(@NonNull Pageable p) throws GlobalErpServiceException, IllegalArgumentException {
		return emploisDao.findAll(p);
	}

	@Override
	public Immobilisation ajouterImmobilisation(@NonNull Immobilisation immo)
			throws GlobalErpServiceException, IllegalArgumentException {
		immoDao.save(immo);
		return immo;
	}

	@Override
	public Immobilisation modifierImmobilisation(@NonNull Immobilisation immo)
			throws GlobalErpServiceException, IllegalArgumentException {
		immoDao.saveAndFlush(immo);
		return immo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirImmobilisation(@NonNull Class<T> entityClass, @NonNull Long immoId)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
		Immobilisation i = immoDao.findOne(immoId);
		verifyObjectNoFindException(i, entityClass, immoId);
		return (T) i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T obtenirImmobilisation(@NonNull Class<T> entityClass, @NonNull String codeImmo)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
		List<Immobilisation> immos = immoDao.findByCodeImmo(codeImmo, new PageRequest(0, 1)).getContent();
		Immobilisation i = (immos.isEmpty()) ? null : immos.get(0);
		verifyObjectNoFindException(i, entityClass, codeImmo);
		return (T) i;
	}

	@Override
	public Page<Immobilisation> listerImmobilsation(@NonNull Pageable p)
			throws GlobalErpServiceException, IllegalArgumentException {
		return immoDao.findAll(p);
	}

	@Override
	public PartenaireImmobilisation ajouterPartenaireImmobilisation(@NonNull PartenaireImmobilisation pimmo)
			throws GlobalErpServiceException, IllegalArgumentException {
		pimmoDao.save(pimmo);
		return pimmo;
	}

	@Override
	public PartenaireImmobilisation modifierPartenaireImmobilisation(@NonNull PartenaireImmobilisation pimmo)
			throws GlobalErpServiceException, IllegalArgumentException {
		pimmoDao.saveAndFlush(pimmo);
		return pimmo;
	}

	@Override
	public PartenaireImmobilisation obtenirPartenaireImmobilisation(@NonNull Long pimmoId)
			throws GlobalErpServiceException, IllegalArgumentException {
		PartenaireImmobilisation p = pimmoDao.findOne(pimmoId);
		verifyObjectNoFindException(p, PartenaireImmobilisation.class, pimmoId);
		return p;
	}

	@Override
	public void supprimerPartenaireImmobilisation(@NonNull Long pimmoId)
			throws GlobalErpServiceException, IllegalArgumentException {
		pimmoDao.delete(obtenirPartenaireImmobilisation(pimmoId));
	}

	@Override
	public void supprimerPartenaire(@NonNull String codePartenaire)
			throws GlobalErpServiceException, IllegalArgumentException, ClassCastException {
		partenaireDao.delete(obtenirPartenaire(Partenaire.class, codePartenaire));
	}

	@Override
	public void supprimerEmplois(@NonNull Long emploisId) throws GlobalErpServiceException, IllegalArgumentException {
		emploisDao.delete(obtenirEmplois(emploisId));
	}

	@Override
	public void supprimerEmplois(@NonNull String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException {
		emploisDao.delete(obtenirEmplois(codeEmplois));
	}

	@Override
	public void supprimerImmobilisation(@NonNull Long immoId)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
		immoDao.delete(obtenirImmobilisation(Immobilisation.class, immoId));
	}

	@Override
	public void supprimerImmobilisation(@NonNull String codeImmo)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
		immoDao.delete(obtenirImmobilisation(Immobilisation.class, codeImmo));
	}

	@Override
	public GroupePartenaire ajouterGroupePartenaire(@NonNull GroupePartenaire groupePartenaire) {
		groupePartenaireDao.save(groupePartenaire);
		return groupePartenaire;
	}

	@Override
	public GroupePartenaire modifierGroupePartenaire(@NonNull GroupePartenaire groupePartenaire) {
		groupePartenaireDao.saveAndFlush(groupePartenaire);
		return groupePartenaire;
	}

	@Override
	public GroupePartenaire obtenirGroupePartenaire(@NonNull Long groupePartenaireId) {
		GroupePartenaire g = groupePartenaireDao.findOne(groupePartenaireId);
		return g;
	}

	@Override
	public void supprimerGroupePartenaire(@NonNull Long groupePartenaireId) {
		groupePartenaireDao.delete(obtenirGroupePartenaire(groupePartenaireId));
	}
}
