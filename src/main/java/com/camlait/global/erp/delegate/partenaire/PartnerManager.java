package com.camlait.global.erp.delegate.partenaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.partenaire.Partenaire;

public interface PartnerManager {

	/**
	 * Add a partner in the data storage.
	 * 
	 * @param partner
	 *            Partner to add.
	 * @return
	 * @throws DataStorageException
	 */
	Partenaire addPartner(Partenaire partner) throws DataStorageException;

	/**
	 * Update a partner in the data storage.
	 * 
	 * @param partner
	 * @return
	 * @throws DataStorageException
	 */
	Partenaire updatePartner(Partenaire partner) throws DataStorageException;

	/**
	 * Retrieve a partner based on the given identifier.
	 * 
	 * @param partnerId
	 * @return
	 * @throws DataStorageException
	 */
	Partenaire retrievePartner(String partnerId) throws DataStorageException;

	/**
	 * Retrieve a generic partner based on the given identifier.
	 * 
	 * @param clazz
	 * @param partnerId
	 * @return
	 * @throws DataStorageException
	 */
	<T extends Partenaire> T retrievePartner(Class<T> clazz, String partnerId) throws DataStorageException;

	/**
	 * remove a partner based on the given partner identifier.
	 * 
	 * @param partnerId
	 * @return
	 * @throws DataStorageException
	 */
	Boolean removePartner(String partnerId) throws DataStorageException;

	/**
	 * Retrieves some partners from the data storage based on the given key
	 * word.
	 * 
	 * @param keyWord
	 *            Key word to use.
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return All the partners that belong to the provided key word.
	 * @throws DataStorageException
	 */
	Page<Partenaire> retrievePartners(String keyWord, Pageable p) throws DataStorageException;
}
