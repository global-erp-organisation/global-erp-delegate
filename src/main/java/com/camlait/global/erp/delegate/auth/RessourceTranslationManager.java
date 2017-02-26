package com.camlait.global.erp.delegate.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.traduction.Langue;
import com.camlait.global.erp.domain.traduction.Terme;

/**
 * This interface provides all operations that belong to resources management.
 * 
 * @author Martin Blaise Signe
 */
public interface RessourceTranslationManager {

    /**
     * Add a language in the data storage
     * 
     * @param language Language to store.
     * @return
     * @throws DataStorageException
     */
    Langue addLanguage(Langue language) throws DataStorageException;

    /**
     * Update a language in the data storage
     * 
     * @param language Language to update.
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Langue updateLanguage(Langue language) throws DataStorageException;

    /**
     * Retrieves a language from the data storage.
     * 
     * @param languageId Language identifier
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Langue retrieveLanguage(String languageId) throws DataStorageException;

    /**
     * Permanently remove a language in the data storage.
     * 
     * @param langagueId Language identifier.
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Boolean removeLanguage(String languagId) throws DataStorageException;

    /**
     * Retrieves some languages that match with the provided key word from the data storage.
     * 
     * @param keyWord Key word.
     * @param p Pageable object that indicated how many record need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Langue> RetrieveLanguages(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Add a term in the data storage
     * 
     * @param language Language to store.
     * @return
     * @throws DataStorageException
     */
    Terme addTerm(Terme term) throws DataStorageException;

    /**
     * Update a term in the data storage
     * 
     * @param term Term to update.
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Terme updateTerm(Terme term) throws DataStorageException;

    /**
     * Retrieves a term from the data storage.
     * 
     * @param termId Term identifier
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Terme retrieveTerm(String termId) throws DataStorageException;

    /**
     * Permanently remove a term in the data storage.
     * 
     * @param termId Term identifier.
     * @return
     * @throws DataStorageException throws if the item does not exist.
     */
    Boolean removeTerm(String termId) throws DataStorageException;

    /**
     * Retrieves some terms that match with the provided key word from the data storage.
     * 
     * @param keyWord Key word.
     * @param p Pageable object that indicated how many record need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Terme> RetrieveTerms(String keyWord, Pageable p) throws DataStorageException;

}