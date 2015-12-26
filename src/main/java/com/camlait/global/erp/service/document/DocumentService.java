package com.camlait.global.erp.service.document;

import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class DocumentService implements IDocumentService {

	@Autowired
	private DocumentDao documentDao;

	@Override
	public Document ajouterDocument(Document document) {
		if (document != null) {
			documentDao.save(document);
		}
		return document;
	}

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
			throw new GlobalErpServiceException("Le document ayant l'identifiant " + documentId + " n'existe pas");
		}
	}

	@Override
	public void supprimerDocument(Long documentId) {
		documentDao.delete(trouverDocument(documentId));
	}

	@Override
	public Page<Document> listerDocument(Date debut, Date fin, Pageable p) {
		return null;
	}

}
