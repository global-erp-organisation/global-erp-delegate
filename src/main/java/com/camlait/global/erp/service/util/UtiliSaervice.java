package com.camlait.global.erp.service.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.commerciaux.vente.DocumentDeVente;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClientComptant;
import com.camlait.global.erp.domain.document.stock.entree.BonDeRetour;
import com.camlait.global.erp.domain.document.stock.entree.BonEntree;
import com.camlait.global.erp.domain.document.stock.entree.DocumentEntree;
import com.camlait.global.erp.domain.document.stock.sortie.Animation;
import com.camlait.global.erp.domain.document.stock.sortie.Avarie;
import com.camlait.global.erp.domain.document.stock.sortie.BonDeSortie;
import com.camlait.global.erp.domain.document.stock.sortie.DocumentDeSortie;
import com.camlait.global.erp.domain.document.stock.sortie.Don;
import com.camlait.global.erp.domain.document.stock.sortie.Echantillon;
import com.camlait.global.erp.domain.enumeration.AutreEnum;
import com.camlait.global.erp.domain.enumeration.document.EnumTypeEntite;
import com.camlait.global.erp.domain.enumeration.document.TypeDocumentEntree;
import com.camlait.global.erp.domain.enumeration.document.TypeDocumentSortie;
import com.camlait.global.erp.domain.enumeration.document.TypeDocumentVente;
import com.camlait.global.erp.domain.enumeration.document.TypeFacture;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.partenaire.Caissier;
import com.camlait.global.erp.domain.partenaire.Client;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.ClientComptant;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Magasinier;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;

public class UtiliSaervice implements IUtilService {

	@Autowired
	private DocumentDao documentDao;

	@Autowired
	private InventaireDao inventaireDao;

	@Autowired
	private PartenaireDao partenaireDao;

	@Override
	public Long obtenirDernierId(Entite entite) {
		return dernierId(entite);
	}

	private Long dernierId(Entite entite) {

		if (entite instanceof Document) {
			if (entite instanceof DocumentDeVente) {
				if (entite instanceof FactureClient)
					return documentDao.maxIdFacture(TypeDocumentVente.FACTURE_CLIENT.getType());
				if (entite instanceof FactureClientComptant)
					return documentDao.maxIdFacture(TypeFacture.FACTURE_COMPTANT.getType());
			}

			if (entite instanceof DocumentDeSortie) {
				if (entite instanceof BonDeSortie)
					return documentDao.maxIdDocumentSortie(TypeDocumentSortie.BON_DE_SORTIE.getType());
				if (entite instanceof Echantillon)
					return documentDao.maxIdDocumentSortie(TypeDocumentSortie.ECHANTILLON.getType());
				if (entite instanceof Don)
					return documentDao.maxIdDocumentSortie(TypeDocumentSortie.DON.getType());
				if (entite instanceof Avarie)
					return documentDao.maxIdDocumentSortie(TypeDocumentSortie.AVARIE.getType());
				if (entite instanceof Animation)
					return documentDao.maxIdDocumentSortie(TypeDocumentSortie.ANIMATION.getType());
			}

			if (entite instanceof DocumentEntree) {
				if (entite instanceof BonEntree)
					return documentDao.maxIdDocumentEntree(TypeDocumentEntree.BON_ENTREE.getType());
				if (entite instanceof BonDeRetour)
					return documentDao.maxIdDocumentEntree(TypeDocumentEntree.BON_RETOUR.getType());
			}
		}

		if (entite instanceof Partenaire) {
			if (entite instanceof Employe)
				return AutreEnum.EMPLOYE;
			if (entite instanceof Client)
				return AutreEnum.CLIENT;
			if (entite instanceof ClientAmarge)
				return AutreEnum.CLIENT_A_MARGE;
			if (entite instanceof ClientComptant)
				return AutreEnum.CLIENT_COMPTANT;
			if (entite instanceof Magasinier)
				return AutreEnum.MAGASINIER;
			if (entite instanceof Vendeur)
				return AutreEnum.VENDEUR;
			if (entite instanceof Caissier)
				return AutreEnum.CAISSIER;
		}
		if (entite instanceof Inventaire)
			return AutreEnum.INVENTAIRE;
		else {
			throw new IllegalArgumentException("L'entité " + entite.getClass().getName() + "n'existe pas");
		}
	}

}
