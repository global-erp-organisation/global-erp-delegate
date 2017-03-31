package com.camlait.global.erp.delegate.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.partner.Partner;

import lombok.NonNull;

public interface PartnerManager {

    /**
     * Add a partner in the data storage.
     * 
     * @param partner Partner to add.
     * @return
     * @throws DataStorageException
     */
    Partner addPartner(@NonNull Partner partner) throws DataStorageException;

    /**
     * Update a partner in the data storage.
     * 
     * @param partner
     * @return
     * @throws DataStorageException
     */
    Partner updatePartner(@NonNull Partner partner) throws DataStorageException;

    /**
     * Retrieve a partner based on the given identifier.
     * 
     * @param partnerId
     * @return
     * @throws DataStorageException
     */
    Partner retrievePartner(@NonNull String partnerId) throws DataStorageException;

    /**
     * Retrieve a generic partner based on the given identifier.
     * 
     * @param clazz
     * @param partnerId
     * @return
     * @throws DataStorageException
     */
    <T extends Partner> T retrievePartner(@NonNull Class<T> clazz, @NonNull String partnerId) throws DataStorageException;

    /**
     * remove a partner based on the given partner identifier.
     * 
     * @param partnerId
     * @return
     * @throws DataStorageException
     */
    Boolean removePartner(@NonNull String partnerId) throws DataStorageException;

    /**
     * Retrieves some partners from the data storage based on the given key
     * word.
     * 
     * @param keyWord Key word to use.
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return All the partners that belong to the provided key word.
     * @throws DataStorageException
     */
    Page<Partner> retrievePartners(@NonNull String keyWord, Pageable p) throws DataStorageException;
}
