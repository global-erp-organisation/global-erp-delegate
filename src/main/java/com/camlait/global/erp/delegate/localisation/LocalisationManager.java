package com.camlait.global.erp.delegate.localisation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.localisation.Localisation;

public interface LocalisationManager {

    /**
     * Add a new locale in the data storage
     * 
     * @param local
     * @return
     * @throws DataStorageException
     */
    Localisation addLocalisation(Localisation local) throws DataStorageException;

    /**
     * Update the provided local information in the data storage
     * 
     * @param local
     * @return
     * @throws DataStorageException
     */
    Localisation updateLocalisation(Localisation local) throws DataStorageException;

    /**
     * Retrieves a Local from the data storage based on the given identifier.
     * 
     * @param localId
     * @return
     * @throws DataStorageException
     */
    Localisation retrieveLocalisation(String localId) throws DataStorageException;

    /**
     * Retrieves a generic Local based on the given identifier.
     * 
     * @param clazz
     * @param localId
     * @return
     * @throws DataStorageException
     */
    <T extends Localisation> T retrieveLocalisation(Class<T> clazz, String localId) throws DataStorageException;

    /**
     * Permanently remove a local from the data storage.
     * 
     * @param localId
     * @return
     * @throws DataStorageException
     */
    Boolean removeLocalisation(String localId) throws DataStorageException;

    /**
     * Retireves som local base on the given key word.
     * 
     * @param keyWord
     * @param p
     * @return
     * @throws DataStorageException
     */
    Page<Localisation> retriveLocalisations(String keyWord, Pageable p) throws DataStorageException;

}
