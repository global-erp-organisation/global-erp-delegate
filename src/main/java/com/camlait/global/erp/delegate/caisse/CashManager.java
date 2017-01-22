package com.camlait.global.erp.delegate.caisse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.operation.caisse.Caisse;

public interface CashManager {

	Caisse addCash(Caisse cash) throws DataStorageException;

	Caisse updateCash(Caisse cash) throws DataStorageException;

	Caisse RetrieveCash(String cashId) throws DataStorageException;

	Caisse removeCash(String cashId) throws DataStorageException;

	Page<Caisse> retrieveCashs(String keyWord, Pageable p) throws DataStorageException;
}
