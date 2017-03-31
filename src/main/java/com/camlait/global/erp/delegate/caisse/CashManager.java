package com.camlait.global.erp.delegate.caisse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.operation.cash.Cash;

import lombok.NonNull;

public interface CashManager {

	Cash addCash(@NonNull Cash cash) throws DataStorageException;

	Cash updateCash(@NonNull Cash cash) throws DataStorageException;

	Cash RetrieveCash(@NonNull String cashId) throws DataStorageException;

	Cash removeCash(@NonNull String cashId) throws DataStorageException;

	Page<Cash> retrieveCashs(@NonNull String keyWord, Pageable p) throws DataStorageException;
}
