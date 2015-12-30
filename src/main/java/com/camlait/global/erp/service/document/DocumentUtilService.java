package com.camlait.global.erp.service.document;

import org.springframework.beans.factory.annotation.Autowired;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.domain.enumeration.document.EnumTypeEntite;

public class DocumentUtilService implements IDocumentUtilService {

	@Autowired
	private DocumentDao documentDao;

	@Override
	public Long maxIdFacture(EnumTypeEntite type) {
		return documentDao.maxIdFacture(type.getType());
	}

	@Override
	public Long maxIdDocumentSortie(EnumTypeEntite type) {
		return documentDao.maxIdDocumentSortie(type.getType());
	}

	@Override
	public Long maxIdDocumentEntree(EnumTypeEntite type) {
		return documentDao.maxIdDocumentEntree(type.getType());
	}

	@Override
	public Long maxIdDocumentTransfert(EnumTypeEntite type) {
		return documentDao.maxIdDocumentTransfert(type.getType());
	}

}
