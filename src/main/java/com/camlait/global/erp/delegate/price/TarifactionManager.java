package com.camlait.global.erp.delegate.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageExcetion;
import com.camlait.global.erp.domain.prix.PriceType;
import com.camlait.global.erp.domain.prix.Tarif;

public interface TarifactionManager {
	/**
	 * Add a price type in the data storage.
	 * 
	 * @param priceType
	 *            price type to store
	 * @return The stored price type.
	 * @throws DataStorageExcetion
	 */
	PriceType addPriceType(PriceType priceType) throws DataStorageExcetion;

	/**
	 * Update a price type
	 * 
	 * @param pricetype
	 *            Price type to update
	 * @return The updated price type
	 * @throws DataStorageExcetion
	 */
	PriceType updatePriceType(PriceType pricetype) throws DataStorageExcetion;

	/**
	 * Retrieves a price type from the data storage.
	 * 
	 * @param priceTypeId
	 *            Price type Identifier
	 * @return The price type that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	PriceType retrievePricetype(String priceTypeId) throws DataStorageExcetion;

	/**
	 * Permanently removes a price type from the data storage.
	 * 
	 * @param pricetypeId
	 *            Price type identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removePricetype(String pricetypeId) throws DataStorageExcetion;

	/**
	 * Retrieves prices type from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            Key word
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<PriceType> retrievePriceTypes(String keyWord, Pageable p) throws DataStorageExcetion;
	
	/**
	 * Add a tariff  in the data storage.
	 * 
	 * @param tarif
	 *            Tarif to store
	 * @return The stored tarif.
	 * @throws DataStorageExcetion
	 */
	Tarif addTarif(Tarif tarif) throws DataStorageExcetion;

	/**
	 * Update a tarif
	 * 
	 * @param tarif
	 *            Tarif to update
	 * @return The updated tarif
	 * @throws DataStorageExcetion
	 */
	Tarif updateTarif(Tarif tarif) throws DataStorageExcetion;

	/**
	 * Retrieves a tarif from the data storage.
	 * 
	 * @param tarifId
	 *            Price type Identifier
	 * @return The tarif that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	Tarif retrieveTarif(String tarifId) throws DataStorageExcetion;

	/**
	 * Permanently removes a tarif from the data storage.
	 * 
	 * @param tarifId
	 *            Price type identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removeTarif(String tarifId) throws DataStorageExcetion;

	/**
	 * Retrieves Tarif from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            Key word
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Tarif> retrieveTarifs(String keyWord, Pageable p) throws DataStorageExcetion;

	
}
