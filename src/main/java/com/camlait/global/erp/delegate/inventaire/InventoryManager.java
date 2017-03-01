package com.camlait.global.erp.delegate.inventaire;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.inventory.Inventory;
import com.camlait.global.erp.domain.inventory.Stock;
import com.camlait.global.erp.domain.warehouse.Store;
import com.camlait.global.erp.domain.warehouse.Warehouse;

public interface InventoryManager {

    /**
     * Add a warehouse in the data storage.
     * 
     * @param wareHouse warehouse to store.
     * @return The stored record.
     * @throws DataStorageException
     */
    Warehouse addWareHouse(Warehouse wareHouse) throws DataStorageException;

    /**
     * update a warehouse in the data storage.
     * 
     * @param wareHouse warehouse to update.
     * @return The updated record.
     * @throws DataStorageException
     */
    Warehouse updateWareHouse(Warehouse wareHouse) throws DataStorageException;

    /**
     * Retrieve a warehouse from the data storage.
     * 
     * @param wareHouseId warehouse identifier.
     * @return
     * @throws DataStorageException
     */
    Warehouse retrieveWareHouse(String wareHouseId) throws DataStorageException;

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
    Page<Warehouse> retrieveWareHouses(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Add a store in the data storage.
     * 
     * @param store store to store.
     * @return The stored record.
     * @throws DataStorageException
     */
    Store addStore(Store store) throws DataStorageException;

    /**
     * update a store in the data storage.
     * 
     * @param store store to update.
     * @return The updated record.
     * @throws DataStorageException
     */
    Store updateStore(Store store) throws DataStorageException;

    /**
     * Retrieve a warehouse from the data storage.
     * 
     * @param storeId store identifier.
     * @return
     * @throws DataStorageException
     */
    Store retrieveStore(String storeId) throws DataStorageException;

    /**
     * Retrieve a generic store from the data storage.
     * 
     * @param clazz generic class
     * @param storeId Store identifier.
     * @return
     * @throws DataStorageException
     */
    <T extends Store> T retrieveStore(Class<T> clazz, String storeId) throws DataStorageException;

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
    Page<Store> retrieveStores(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves the inventory for the given store.
     * 
     * @param storeId Store identifier.
     * @return
     * @throws DataStorageException
     */
    Collection<Stock> getInventoryByStore(String storeId) throws DataStorageException;
    
    /**
     * Add an inventory in the data storage.
     * 
     * @param inventory inventory to store.
     * @return The stored record.
     * @throws DataStorageException
     */
    Inventory addInventory(Inventory inventory) throws DataStorageException;

    /**
     * update an inventory in the data storage.
     * 
     * @param inventory Inventory to update.
     * @return The updated record.
     * @throws DataStorageException
     */
    Inventory updateInventory(Inventory inventory) throws DataStorageException;

    /**
     * Retrieve a warehouse from the data storage.
     * 
     * @param inventoryId Inventory identifier.
     * @return
     * @throws DataStorageException
     */
    Inventory retrieveInventory(String inventoryId) throws DataStorageException;

    /**
     * remove an inventory in the data storage.
     * 
     * @param inventoryId Inventory identifier to remove.
     * @return
     * @throws DataStorageException
     */
    Boolean removeInventory(String inventoryId) throws DataStorageException;

    /**
     * Retrieves stores from the data storage based on the given key word.
     * 
     * @param keyWord
     * @return
     * @throws DataStorageException
     */
    Page<Inventory> retrieveInventories(String keyWord, Pageable p) throws DataStorageException;


}
