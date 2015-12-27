package com.camlait.global.erp.service.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;

public interface IPartenaireService {

	Partenaire ajouterPartenaire(Partenaire partenaire);

	Partenaire modifierPartenaire(Partenaire partenaire);

	Partenaire trouverPartenaire(Long partenaireId);

	void supprimerPartenaire(Long partenaireId);

	Page<Partenaire> listerPartenaire(Pageable p);
	
	Page<Employe> listerEmploye(String motCle, Pageable p);
}
