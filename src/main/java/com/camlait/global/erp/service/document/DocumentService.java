package com.camlait.global.erp.service.document;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.document.LigneDeDocumentTaxeDao;
import com.camlait.global.erp.dao.document.LigneDocumentDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class DocumentService implements IDocumentService {

	@Autowired
	private DocumentDao documentDao;

	@Autowired
	private LigneDocumentDao ligneDeDocumentDao;

	@Autowired
	private LigneDeDocumentTaxeDao ligneDeDocumentTaxeDao;

	@Transactional
	@Override
	public Document ajouterDocument(Document document) {
		if (document != null) {
			documentDao.save(document);
		}
		return document;
	}

	@Transactional
	@Override
	public Document modifierDocument(Document document) {
		document.setDerniereMiseAJour(new Date());
		documentDao.saveAndFlush(document);
		return document;
	}

	@Override
	public Document trouverDocument(Long documentId) {
		if (documentId == null) {
			throw new IllegalArgumentException("documentId ne doit pas etre null");
		}
		Document d = documentDao.findOne(documentId);
		if (d != null) {
			Hibernate.initialize(d.getLigneDocuments());
			if (d instanceof FactureClient) {
				Hibernate.initialize(((FactureClient) d).getFactureReglements());
			}
			return d;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Document.class, documentId));
		}
	}

	@Transactional
	@Override
	public void supprimerDocument(Long documentId) {
		documentDao.delete(trouverDocument(documentId));
	}

	@Override
	public Page<Document> listerDocument(Date debut, Date fin, Pageable p) {
		return documentDao.listerDocument(debut, fin, p);
	}

	@Transactional
	@Override
	public LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne) {
		if (ligne != null) {
			ligneDeDocumentDao.save(ligne);
			ajouterLigneDeDocumentTaxe(ligne);
		}
		return ligne;
	}

	@Transactional
	@Override
	public Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes) {
		if (lignes != null) {
			ligneDeDocumentDao.save(lignes);
			ajouterLigneDeDocumentTaxe(lignes);
		}
		return lignes;
	}

	@Transactional
	@Override
	public LigneDeDocument modifierLigneDocument(LigneDeDocument ligne) {
		ligne.setDerniereMiseAJour(new Date());
		ligneDeDocumentDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneDeDocument trouverLigneDocument(Long ligneId) {
		if (ligneId == null) {
			throw new IllegalArgumentException("ligneId ne doit pas etre null");
		}
		LigneDeDocument ld = ligneDeDocumentDao.findOne(ligneId);
		if (ld != null) {
			Hibernate.initialize(ld.getLigneDeDocumentTaxes());
			return ld;
		} else {
			throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneDeDocument.class, ligneId));
		}
	}

	@Transactional
	@Override
	public void supprimerLigneDocument(Long ligneId) {
		ligneDeDocumentDao.delete(trouverLigneDocument(ligneId));
	}

	@Transactional
	@Override
	public void supprimerLigneDocument(Document document) {
		ligneDeDocumentDao.delete(document.getLigneDocuments());
	}

	@Transactional
	@Override
	public LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) {
		if (ligneDeDocumentTaxe != null) {
			ligneDeDocumentTaxeDao.save(ligneDeDocumentTaxe);
		}
		return ligneDeDocumentTaxe;
	}

	@Transactional
	@Override
	public LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) {
		ligneDeDocumentTaxe.setDerniereMiseAJour(new Date());
		ligneDeDocumentTaxeDao.saveAndFlush(ligneDeDocumentTaxe);
		return ligneDeDocumentTaxe;
	}

	@Override
	public LigneDeDocumentTaxe trouverLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) {
		if (ligneDeDocumentTaxeId == null) {
			throw new IllegalArgumentException("ligneDeDocumentTaxeId ne peut pas etre null");
		}
		LigneDeDocumentTaxe l = ligneDeDocumentTaxeDao.findOne(ligneDeDocumentTaxeId);
		if (l != null) {
			return l;
		} else {
			throw new GlobalErpServiceException(
					GlobalAppConstants.buildNotFingMessage(LigneDeDocumentTaxe.class, ligneDeDocumentTaxeId));
		}
	}

	@Transactional
	@Override
	public void spprimerLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) {
		ligneDeDocumentTaxeDao.delete(trouverLigneDeDocumentTaxe(ligneDeDocumentTaxeId));
	}

	/**
	 * Ajout des taxe a une ligne de document.
	 * 
	 * @param ligneDeDocument
	 */
	private void ajouterLigneDeDocumentTaxe(LigneDeDocument ligneDeDocument) {
		ligneDeDocument.getProduit().getProduitTaxes().stream().forEach(pt -> {
			LigneDeDocumentTaxe l = new LigneDeDocumentTaxe();
			l.setLigneDeDocument(ligneDeDocument);
			l.setTaxe(pt.getTaxe());
			l.setTauxDeTaxe(pt.getTaxe().getValeurPourcentage());
			ajouterLigneDeDocumentTaxe(l);
		});
	}

	/**
	 * Ajout des taxes aux lignes de document de maniere groupee.
	 * 
	 * @param lignes
	 */
	private void ajouterLigneDeDocumentTaxe(Collection<LigneDeDocument> lignes) {
		lignes.stream().forEach(ligne -> ajouterLigneDeDocumentTaxe(ligne));
	}
}
