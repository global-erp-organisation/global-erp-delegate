package com.camlait.global.erp.delegate.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageExcetion;
import com.camlait.global.erp.domain.prix.PriceType;
import com.camlait.global.erp.domain.prix.Tarif;
import com.camlait.global.erp.domain.prix.Tarification;

public interface TarificationManager {
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
	 * Add a tariff in the data storage.
	 * 
	 * @param tarif
	 *            Tariff to store
	 * @return The stored tariff.
	 * @throws DataStorageExcetion
	 */
	Tarif addTarif(Tarif tariff) throws DataStorageExcetion;

	/**
	 * Update a tariff
	 * 
	 * @param tariff
	 *            Tariff to update
	 * @return The updated tariff
	 * @throws DataStorageExcetion
	 */
	Tarif updateTarif(Tarif tariff) throws DataStorageExcetion;

	/**
	 * Retrieves a tariff from the data storage.
	 * 
	 * @param tariffId
	 *            Tariff Identifier
	 * @return The tariff that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	Tarif retrieveTarif(String tariffId) throws DataStorageExcetion;

	/**
	 * Permanently removes a tariff from the data storage.
	 * 
	 * @param tariffId
	 *            Tariff identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removeTarif(String tarifId) throws DataStorageExcetion;

	/**
	 * Retrieves Tariff from the data storage based on the given key word.
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

	/**
	 * Add a tarification in the data storage.
	 * 
	 * @param tarification
	 *            Tarification to store
	 * @return The stored tarification.
	 * @throws DataStorageExcetion
	 */
	Tarification addTarification(Tarification tarification) throws DataStorageExcetion;

	/**
	 * Update a tarification
	 * 
	 * @param tarification
	 *            Tarification to update
	 * @return The updated tarification
	 * @throws DataStorageExcetion
	 */
	Tarification updateTarification(Tarification tarification) throws DataStorageExcetion;

	/**
	 * Retrieves a tarification from the data storage.
	 * 
	 * @param tarificationId
	 *            Tarification Identifier
	 * @return The tarification that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	Tarification retrieveTarification(String tarificationId) throws DataStorageExcetion;

	/**
	 * Permanently removes a tarification from the data storage.
	 * 
	 * @param tarificationId
	 *            Tarification identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removeTarification(String tarificationId) throws DataStorageExcetion;

	/**
	 * Retrieves Tarifications from the data storage based on the given key
	 * word.
	 * 
	 * @param keyWord
	 *            Key word
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Tarification> retrieveTarifications(String keyWord, Pageable p) throws DataStorageExcetion;

	/**
	 * Retrieves the unit price for the given product based on the specific zone
	 * and the price type category.
	 * 
	 * @param priceTypeId
	 *            Price type category identifier.
	 * @param zoneId
	 *            Zone localization identifier.
	 * @param productId
	 *            Product identifier
	 * @return The unit price that belong to the provided product for the
	 *         provided partner
	 * @throws DataStorageExcetion
	 */
	Double retrieveUnitPrice(String priceTypeId, String zoneId, String productId) throws DataStorageExcetion;

}
