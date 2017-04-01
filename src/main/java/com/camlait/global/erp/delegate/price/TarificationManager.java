package com.camlait.global.erp.delegate.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.tarif.PriceType;
import com.camlait.global.erp.domain.tarif.Tariff;

import lombok.NonNull;

/**
 * Price catalog management interface.
 * 
 * @author Martin Blaise Signe.
 */
public interface TarificationManager {
    /**
     * Add a price type in the data storage.
     * 
     * @param priceType price type to store
     * @return The stored price type.
     * @throws DataStorageException
     */
    PriceType addPriceType(@NonNull PriceType priceType) throws DataStorageException;

    /**
     * Update a price type
     * 
     * @param pricetype Price type to update
     * @return The updated price type
     * @throws DataStorageException
     */
    PriceType updatePriceType(@NonNull PriceType pricetype) throws DataStorageException;

    /**
     * Retrieves a price type from the data storage.
     * 
     * @param priceTypeId Price type Identifier
     * @return The price type that belongs to the given identifier.
     * @throws DataStorageException
     */
    PriceType retrievePricetype(@NonNull String priceTypeId) throws DataStorageException;

    /**
     * Permanently removes a price type from the data storage.
     * 
     * @param pricetypeId Price type identifier.
     * @return true if the operation is performed without error or false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removePricetype(@NonNull String pricetypeId) throws DataStorageException;

    /**
     * Retrieves prices type from the data storage based on the given key word.
     * 
     * @param keyWord Key word
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<PriceType> retrievePriceTypes(@NonNull String keyWord, Pageable p) throws DataStorageException;

    /**
     * Add a tariff in the data storage.
     * 
     * @param tarif Tariff to store
     * @return The stored tariff.
     * @throws DataStorageException
     */
    Tariff addTariff(@NonNull Tariff tariff) throws DataStorageException;

    /**
     * Update a tariff
     * 
     * @param tariff Tariff to update
     * @return The updated tariff
     * @throws DataStorageException
     */
    Tariff updateTariff(@NonNull Tariff tariff) throws DataStorageException;

    /**
     * Retrieves a tariff from the data storage.
     * 
     * @param tariffId Tariff Identifier
     * @return The tariff that belongs to the given identifier.
     * @throws DataStorageException
     */
    Tariff retrieveTariff(@NonNull String tariffId) throws DataStorageException;

    /**
     * Permanently removes a tariff from the data storage.
     * 
     * @param tariffId Tariff identifier.
     * @return true if the operation is performed without error or false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removeTariff(@NonNull String tarifId) throws DataStorageException;

    /**
     * Retrieves Tariff from the data storage based on the given key word.
     * 
     * @param keyWord Key word
     * @param p Pageable object that indicated how many records need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Tariff> retrieveTariffs(@NonNull String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves the unit price for the given product based on the specific zone
     * and the price type category.
     * 
     * @param priceTypeId Price type category identifier.
     * @param zoneId Zone localization identifier.
     * @param productId Product identifier
     * @param tarrifId Tariff identifier.
     * @return The unit price that belong to the provided product for the
     *         provided partner
     * @throws DataStorageException
     */
    Double retrieveUnitPrice(@NonNull String priceTypeId, @NonNull String zoneId, @NonNull String productId,
                             @NonNull String tariffId) throws DataStorageException;

}
