package com.camlait.global.erp.delegate.inventory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.inventory.InventoryRepository;
import com.camlait.global.erp.dao.warehouse.WarehouseRepository;
import com.camlait.global.erp.dao.warehouse.StoreRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.inventory.Inventory;
import com.camlait.global.erp.domain.inventory.Stock;
import com.camlait.global.erp.domain.warehouse.Store;
import com.camlait.global.erp.domain.warehouse.Warehouse;

/**
 * Default implementation of the Inventory management operations specification interface.
 * 
 * @author Martin Blaise Signe.
 */
@Transactional
@Component
public class DefaultInventoryManager implements InventoryManager {

    private final WarehouseRepository warehouseRepo;
    private final StoreRepository storeRepo;
    private final InventoryRepository inventoryRepo;

    @Autowired
    public DefaultInventoryManager(WarehouseRepository warehouseRepo, StoreRepository storeRepo, InventoryRepository inventoryRepo) {
        this.warehouseRepo = warehouseRepo;
        this.storeRepo = storeRepo;
        this.inventoryRepo = inventoryRepo;

    }

    @Override
    public Warehouse addWareHouse(final Warehouse wareHouse) throws DataStorageException {
        return warehouseRepo.save(wareHouse);
    }

    @Override
    public Warehouse updateWareHouse(final Warehouse wareHouse) throws DataStorageException {
        final Warehouse e = retrieveWareHouse(wareHouse.getWarehouseId());
        return warehouseRepo.saveAndFlush(wareHouse.merge(e));
    }

    @Override
    public Warehouse retrieveWareHouse(final String wareHouseId) throws DataStorageException {
        final Warehouse e = warehouseRepo.findOne(wareHouseId);
        return e == null ? null : e.lazyInit();
    }

    @Override
    public Boolean removeWareHouse(final String wareHouseId) throws DataStorageException {
        final Warehouse e = retrieveWareHouse(wareHouseId);
        if (e == null) {
            return false;
        }
        warehouseRepo.delete(e);
        return true;
    }

    @Override
    public Page<Warehouse> retrieveWareHouses(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }

    @Override
    public Store addStore(final Store store) throws DataStorageException {
        return storeRepo.save(store);
    }

    @Override
    public Store updateStore(final Store store) throws DataStorageException {
        final Store s = retrieveStore(store.getStoreId());
        return storeRepo.saveAndFlush(store.merge(s));
    }

    @Override
    public Store retrieveStore(final String storeId) throws DataStorageException {
        final Store s = storeRepo.findOne(storeId);
        return s == null ? null : s.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Store> T retrieveStore(Class<T> clazz, final String storeId) throws DataStorageException {
        final Store s = retrieveStore(storeId);
        return s == null ? null : s.isTypeOf(clazz) ? (T) s : null;
    }

    @Override
    public Boolean removeStore(final String storeId) throws DataStorageException {
        final Store s = retrieveStore(storeId);
        if (s == null) {
            return false;
        }
        storeRepo.delete(s);
        return true;
    }

    @Override
    public Page<Store> retrieveStores(final String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Stock> getInventoryByStore(final String storeId) throws DataStorageException {
        final Store store = retrieveStore(storeId);
        return store.getStocks();
    }

    @Override
    public Inventory addInventory(final Inventory inventory) throws DataStorageException {
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory updateInventory(final Inventory inventory) throws DataStorageException {
        final Inventory i = retrieveInventory(inventory.getInventoryId());
        return inventoryRepo.saveAndFlush(inventory.merge(i));
    }

    @Override
    public Inventory retrieveInventory(final String inventoryId) throws DataStorageException {
        final Inventory i = inventoryRepo.findOne(inventoryId);
        return i == null ? null : i.lazyInit();
    }

    @Override
    public Boolean removeInventory(final String inventoryId) throws DataStorageException {
        final Inventory i = retrieveInventory(inventoryId);
        if (i == null) {
            return false;
        }
        inventoryRepo.delete(i);
        return true;
    }

    @Override
    public Page<Inventory> retrieveInventories(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }
}
