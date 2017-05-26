package com.camlait.global.erp.delegate.tax;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.exception.DataStorageException;

import lombok.NonNull;

public interface TaxManager {

    /**
     * Add a tax in the data storage.
     * 
     * @param tax Tax to store
     * @return The stored tax.
     * @throws DataStorageException
     */
    Tax addTax(@NonNull Tax tax) throws DataStorageException;

    /**
     * Update a tax
     * 
     * @param tax Tax to update
     * @return The updated tax
     * @throws DataStorageException
     */
    Tax updateTax(@NonNull Tax tax) throws DataStorageException;

    /**
     * Retrieves a tax type from the data storage.
     * 
     * @param taxId Tax Identifier
     * @return The tax that belongs to the given identifier.
     * @throws DataStorageException
     */
    Tax retrieveTax(@NonNull String taxId) throws DataStorageException;

  
    /**
     * Permanently removes a tax from the data storage.
     * 
     * @param taxId Tax identifier.
     * @return true if the operation is performed without error or false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removeTax(@NonNull String taxId) throws DataStorageException;

    /**
     * Retrieves taxes type from the data storage based on the given key word.
     * 
     * @param keyWord Key word
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Tax> retrieveTaxes(@NonNull String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves taxes type from the data storage based on the given key word.
     * 
     * @param keyWord Key word
     * @return
     * @throws DataStorageException
     */
    List<Tax> retrieveTaxes(@NonNull String keyWord) throws DataStorageException;

}
