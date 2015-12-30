package com.camlait.global.erp.service.document;

import com.camlait.global.erp.domain.enumeration.document.EnumTypeEntite;

public interface IDocumentUtilService {

	Long maxIdFacture(EnumTypeEntite type);

	Long maxIdDocumentSortie(EnumTypeEntite type);

	Long maxIdDocumentEntree(EnumTypeEntite type);

	Long maxIdDocumentTransfert(EnumTypeEntite type);
}
