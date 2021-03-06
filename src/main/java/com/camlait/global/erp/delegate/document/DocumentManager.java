package com.camlait.global.erp.delegate.document;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.exception.DataStorageException;

import lombok.NonNull;

/**
 * This interface specify all the oprations that belong to document management.
 * All CRUD operation for document management and related are defined here.
 * 
 * @author Martin Blaise Signe
 */
public interface DocumentManager {

    /**
     * Add the provided document to the data storage
     * 
     * @param document Document to store.
     * @return The stored document.
     * @throws DataStorageException
     */
    Document addDocument(@NonNull Document document) throws DataStorageException;

    /**
     * Update the provided document to the data storage.
     * 
     * @param document Document the need to be updated.
     * @return The updated document.
     * @throws DataStorageException
     */
    Document updateDocument(@NonNull Document document) throws DataStorageException;

    /**
     * Retrieves the document from the data storage based on the given
     * doumentId.
     * 
     * @param documentId Provided documentId.
     * @return The document that belongs to the given documentId.
     * @throws DataStorageException
     */
    Document retrieveDocument(@NonNull String documentId) throws DataStorageException;

    /**
     * Retrieves a generic type of document
     * 
     * @param clazz Concrete document class to retrieve.
     * @param documentId Document identifier.
     * @return Return the document that belongs to the given identifier.
     * @throws DataStorageException
     */
    <T extends Document> T retrieveDocument(@NonNull Class<T> clazz, @NonNull String documentId) throws DataStorageException;

    /**
     * Permanently remove a document from the data storage.
     * 
     * @param documentId Document to remove identifier.
     * @return true if the operation is performed without error or false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removeDocument(@NonNull String documentId) throws DataStorageException;

    /**
     * Retrieves some documents from the data storage based on the given key
     * word.
     * 
     * @param keyWord Key word to use.
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return All the documents that belong to the provided key word.
     * @throws DataStorageException
     */
    Page<Document> retrieveDocuments(@NonNull String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves documents base on the given period.
     * 
     * @param start Beginning date
     * @param end Ending date.
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return All The documents within the given period that match with the given pagination rule.
     * @throws DataStorageException
     */
    Page<Document> retrieveDocuments(@NonNull Date start, @NonNull Date end, Pageable p) throws DataStorageException;

    /**
     * Computes the document margin value
     * 
     * @param documentId Document identifier.
     * @return The document margin value
     *         <p>
     *         The returned value already include taxes value.
     * @throws DataStorageException
     */
    Double documentMarginValue(@NonNull String documentId) throws DataStorageException;
}
