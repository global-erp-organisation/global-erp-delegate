package com.camlait.global.erp.delegate.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.tarif.PriceType;
import com.camlait.global.erp.domain.tarif.Tarif;
import com.camlait.global.erp.domain.tarif.Tarification;

public interface TarificationManager {
	/**
	 * Add a price type in the data storage.
	 * 
	 * @param priceType
	 *            price type to store
	 * @return The stored price type.
	 * @throws DataStorageException
	 */
	PriceType addPriceType(PriceType priceType) throws DataStorageException;

	/**
	 * Update a price type
	 * 
	 * @param pricetype
	 *            Price type to update
	 * @return The updated price type
	 * @throws DataStorageException
	 */
	PriceType updatePriceType(PriceType pricetype) throws DataStorageException;

	/**
	 * Retrieves a price type from the data storage.
	 * 
	 * @param priceTypeId
	 *            Price type Identifier
	 * @return The price type that belongs to the given identifier.
	 * @throws DataStorageException
	 */
	PriceType retrievePricetype(String priceTypeId) throws DataStorageException;

	/**
	 * Permanently removes a price type from the data storage.
	 * 
	 * @param pricetypeId
	 *            Price type identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageException
	 */
	Boolean removePricetype(String pricetypeId) throws DataStorageException;

	/**
	 * Retrieves prices type from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            Key word
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageException
	 */
	Page<PriceType> retrievePriceTypes(String keyWord, Pageable p) throws DataStorageException;

	/**
	 * Add a tariff in the data storage.
	 * 
	 * @param tarif
	 *            Tariff to store
	 * @return The stored tariff.
	 * @throws DataStorageException
	 */
	Tarif addTarif(Tarif tariff) throws DataStorageException;

	/**
	 * Update a tariff
	 * 
	 * @param tariff
	 *            Tariff to update
	 * @return The updated tariff
	 * @throws DataStorageException
	 */
	Tarif updateTarif(Tarif tariff) throws DataStorageException;

	/**
	 * Retrieves a tariff from the data storage.
	 * 
	 * @param tariffId
	 *            Tariff Identifier
	 * @return The tariff that belongs to the given identifier.
	 * @throws DataStorageException
	 */
	Tarif retrieveTarif(String tariffId) throws DataStorageException;

	/**
	 * Permanently removes a tariff from the data storage.
	 * 
	 * @param tariffId
	 *            Tariff identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageException
	 */
	Boolean removeTarif(String tarifId) throws DataStorageException;

	/**
	 * Retrieves Tariff from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            Key word
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageException
	 */
	Page<Tarif> retrieveTarifs(String keyWord, Pageable p) throws DataStorageException;

	/**
	 * Add a tarification in the data storage.
	 * 
	 * @param tarification
	 *            Tarification to store
	 * @return The stored tarification.
	 * @throws DataStorageException
	 */
	Tarification addTarification(Tarification tarification) throws DataStorageException;

	/**
	 * Update a tarification
	 * 
	 * @param tarification
	 *            Tarification to update
	 * @return The updated tarification
	 * @throws DataStorageException
	 */
	Tarification updateTarification(Tarification tarification) throws DataStorageException;

	/**
	 * Retrieves a tarification from the data storage.
	 * 
	 * @param tarificationId
	 *            Tarification Identifier
	 * @return The tarification that belongs to the given identifier.
	 * @throws DataStorageException
	 */
	Tarification retrieveTarification(String tarificationId) throws DataStorageException;

	/**
	 * Permanently removes a tarification from the data storage.
	 * 
	 * @param tarificationId
	 *            Tarification identifier.
	 * @return true if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageException
	 */
	Boolean removeTarification(String tarificationId) throws DataStorageException;

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
	 * @throws DataStorageException
	 */
	Page<Tarification> retrieveTarifications(String keyWord, Pageable p) throws DataStorageException;

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
	 * @throws DataStorageException
	 */
	Double retrieveUnitPrice(String tarifId, String zoneId, String productId) throws DataStorageException;

}
