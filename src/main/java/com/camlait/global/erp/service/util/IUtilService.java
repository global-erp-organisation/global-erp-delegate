package com.camlait.global.erp.service.util;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.service.GlobalErpServiceException;

public interface IUtilService {
    
    String genererCode(Entite entie) throws GlobalErpServiceException, IllegalArgumentException;
}
