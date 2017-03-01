package com.camlait.global.erp.delegate.caisse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.operation.cash.Cash;

public interface CashManager {

	Cash addCash(Cash cash) throws DataStorageException;

	Cash updateCash(Cash cash) throws DataStorageException;

	Cash RetrieveCash(String cashId) throws DataStorageException;

	Cash removeCash(String cashId) throws DataStorageException;

	Page<Cash> retrieveCashs(String keyWord, Pageable p) throws DataStorageException;
}
