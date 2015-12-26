package com.camlait.global.erp.service.document;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.document.LigneDocumentDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class DocumentService implements IDocumentService {

	@Autowired
	private DocumentDao documentDao;

	@Autowired
	private LigneDocumentDao ligneDeDocumentDao;

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
		documentDao.saveAndFlush(document);
		return document;
	}

	@Override
	public Document trouverDocument(Long documentId) {
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
		return null;
	}

	@Transactional
	@Override
	public LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne) {
		if (ligne != null) {
			ligneDeDocumentDao.save(ligne);
		}
		return ligne;
	}

	@Transactional
	@Override
	public Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes) {
		if (lignes != null) {
			ligneDeDocumentDao.save(lignes);
		}
		return lignes;
	}

	@Transactional
	@Override
	public LigneDeDocument modifierLigneDocument(LigneDeDocument ligne) {
		ligneDeDocumentDao.saveAndFlush(ligne);
		return ligne;
	}

	@Override
	public LigneDeDocument trouverLigneDocument(Long ligneId) {
		LigneDeDocument ld = ligneDeDocumentDao.findOne(ligneId);
		if (ld != null) {
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
}
