package com.camlait.global.erp.delegate.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.partenaire.Emplois;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.GroupePartenaire;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.immobilisation.Immobilisation;
import com.camlait.global.erp.domain.immobilisation.PartenaireImmobilisation;

public interface IPartenaireService {

	Partenaire ajouterPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;

	Partenaire modifierPartenaire(Partenaire partenaire) throws GlobalErpServiceException, IllegalArgumentException;

	<T> T obtenirPartenaire(Class<T> entityClass, Long partenaireId)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	<T> T obtenirPartenaire(Class<T> entityClass, String codePartenaire)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	void supprimerPartenaire(Long partenaireId)
			throws GlobalErpServiceException, IllegalArgumentException, ClassCastException;

	void supprimerPartenaire(String codePartenaire)
			throws GlobalErpServiceException, IllegalArgumentException, ClassCastException;

	Page<Partenaire> listerPartenaire(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;

	Page<Employe> listerEmploye(String motCle, Pageable p) throws GlobalErpServiceException, IllegalArgumentException;

	Emplois ajouterEmplois(Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException;

	Emplois modifierEmplois(Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException;

	Emplois obtenirEmplois(Long emploisId) throws GlobalErpServiceException, IllegalArgumentException;

	Emplois obtenirEmplois(String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException;

	void supprimerEmplois(Long enmploiId) throws GlobalErpServiceException, IllegalArgumentException;

	void supprimerEmplois(String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException;

	Page<Emplois> listerEmplois(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;

	Immobilisation ajouterImmobilisation(Immobilisation immo)
			throws GlobalErpServiceException, IllegalArgumentException;

	Immobilisation modifierImmobilisation(Immobilisation immo)
			throws GlobalErpServiceException, IllegalArgumentException;

	<T> T obtenirImmobilisation(Class<T> entityClass, Long immoId)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	<T> T obtenirImmobilisation(Class<T> entityClass, String codeImmo)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	void supprimerImmobilisation(Long immoId)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	void supprimerImmobilisation(String codeImmo)
			throws GlobalErpServiceException, ClassCastException, IllegalArgumentException;

	Page<Immobilisation> listerImmobilsation(Pageable p) throws GlobalErpServiceException, IllegalArgumentException;

	PartenaireImmobilisation ajouterPartenaireImmobilisation(PartenaireImmobilisation pimmo)
			throws GlobalErpServiceException, IllegalArgumentException;

	PartenaireImmobilisation modifierPartenaireImmobilisation(PartenaireImmobilisation pimmo)
			throws GlobalErpServiceException, IllegalArgumentException;

	PartenaireImmobilisation obtenirPartenaireImmobilisation(Long pimmoId)
			throws GlobalErpServiceException, IllegalArgumentException;

	void supprimerPartenaireImmobilisation(Long pimmoId) throws GlobalErpServiceException, IllegalArgumentException;

	GroupePartenaire ajouterGroupePartenaire(GroupePartenaire groupePartenaire);

	GroupePartenaire modifierGroupePartenaire(GroupePartenaire groupePartenaire);

	GroupePartenaire obtenirGroupePartenaire(Long groupePartenaireId);

	void supprimerGroupePartenaire(Long groupePartenaireId);
}
