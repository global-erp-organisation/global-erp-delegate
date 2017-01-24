package com.camlait.global.erp.delegate.inventaire;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.inventaire.Stock;

public interface InventoryManager {

    /**
     * Add a warehouse in the data storage.
     * 
     * @param wareHouse warehouse to store.
     * @return The stored record.
     * @throws DataStorageException
     */
    Entrepot addWareHouse(Entrepot wareHouse) throws DataStorageException;

    /**
     * update a warehouse in the data storage.
     * 
     * @param wareHouse warehouse to update.
     * @return The updated record.
     * @throws DataStorageException
     */
    Entrepot updateWareHouse(Entrepot wareHouse) throws DataStorageException;

    /**
     * Retrieve a warehouse from the data storage.
     * 
     * @param wareHouseId warehouse identifier.
     * @return
     * @throws DataStorageException
     */
    Entrepot retrieveWareHouse(String wareHouseId) throws DataStorageException;

    /**
     * remove a warehouse in the data storage.
     * 
     * @param wareHouseId warehouse identifier to remove.
     * @return
     * @throws DataStorageException
     */
    Boolean removeWareHouse(String wareHouseId) throws DataStorageException;

    /**
     * Retrieves warehouses from the data storage based on the given key word.
     * 
     * @param keyWord
     * @return
     * @throws DataStorageException
     */
    Page<Entrepot> retrieveWareHouses(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Add a store in the data storage.
     * 
     * @param store store to store.
     * @return The stored record.
     * @throws DataStorageException
     */
    Magasin addStore(Magasin store) throws DataStorageException;

    /**
     * update a store in the data storage.
     * 
     * @param store store to update.
     * @return The updated record.
     * @throws DataStorageException
     */
    Magasin updateStore(Magasin store) throws DataStorageException;

    /**
     * Retrieve a warehouse from the data storage.
     * 
     * @param storeId store identifier.
     * @return
     * @throws DataStorageException
     */
    Magasin retrieveStore(String storeId) throws DataStorageException;

    /**
     * Retrieve a generic store from the data storage.
     * 
     * @param clazz generic class
     * @param storeId Store identifier.
     * @return
     * @throws DataStorageException
     */
    <T extends Magasin> T retrieveStore(Class<T> clazz, String storeId) throws DataStorageException;

    /**
     * remove a store in the data storage.
     * 
     * @param storeId store identifier to remove.
     * @return
     * @throws DataStorageException
     */
    Boolean removeStore(String storeId) throws DataStorageException;

    /**
     * Retrieves stores from the data storage based on the given key word.
     * 
     * @param keyWord
     * @return
     * @throws DataStorageException
     */
    Page<Magasin> retrieveStores(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves the inventory for the given store.
     * 
     * @param store
     * @return
     * @throws DataStorageException
     */
    Collection<Stock> getInventoryByStore(Magasin store) throws DataStorageException;

}
