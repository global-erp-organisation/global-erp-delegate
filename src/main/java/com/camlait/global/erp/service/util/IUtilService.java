package com.camlait.global.erp.service.util;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;

public interface IUtilService {
    
    String genererCode(Entite entie) throws GlobalErpServiceException, IllegalArgumentException;
}
