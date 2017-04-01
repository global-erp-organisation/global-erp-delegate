package com.camlait.global.erp.delegate.cash;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.operation.cash.Cash;

import lombok.NonNull;

/**
 * Cash manager operations specification interface.
 * 
 * @author Martin Blaise Signe
 */
public interface CashManager {

    Cash addCash(@NonNull Cash cash) throws DataStorageException;

    Cash updateCash(@NonNull Cash cash) throws DataStorageException;

    Cash RetrieveCash(@NonNull String cashId) throws DataStorageException;

    Cash removeCash(@NonNull String cashId) throws DataStorageException;

    Page<Cash> retrieveCashs(@NonNull String keyWord, Pageable p) throws DataStorageException;
}
