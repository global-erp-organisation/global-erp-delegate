package com.camlait.global.erp.delegate.inventaire;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.entrepot.EntrepotDao;
import com.camlait.global.erp.dao.entrepot.MagasinDao;
import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.inventory.Inventory;
import com.camlait.global.erp.domain.inventory.Stock;
import com.camlait.global.erp.domain.warehouse.Store;
import com.camlait.global.erp.domain.warehouse.Warehouse;

@Transactional
@Component
public class DefaultInventoryManager implements InventoryManager {

    private final EntrepotDao entrepotDao;
    private final MagasinDao magasinDao;
    private final InventaireDao inventaireDao;

    @Autowired
    public DefaultInventoryManager(EntrepotDao entrepotDao, MagasinDao magasinDao, InventaireDao inventaireDao) {
        this.entrepotDao = entrepotDao;
        this.magasinDao = magasinDao;
        this.inventaireDao = inventaireDao;

    }

    @Override
    public Warehouse addWareHouse(final Warehouse wareHouse) throws DataStorageException {
        return entrepotDao.save(wareHouse);
    }

    @Override
    public Warehouse updateWareHouse(final Warehouse wareHouse) throws DataStorageException {
        final Warehouse e = retrieveWareHouse(wareHouse.getWarehouseId());
        return entrepotDao.saveAndFlush(wareHouse.merge(e));
    }

    @Override
    public Warehouse retrieveWareHouse(final String wareHouseId) throws DataStorageException {
        final Warehouse e = entrepotDao.findOne(wareHouseId);
        if (e == null) {
            throw new DataStorageException("The warehouse that you are trying to retrieve does not exist.");
        }
        return e.lazyInit();
    }

    @Override
    public Boolean removeWareHouse(final String wareHouseId) throws DataStorageException {
        final Warehouse e = retrieveWareHouse(wareHouseId);
        entrepotDao.delete(e);
        return true;
    }

    @Override
    public Page<Warehouse> retrieveWareHouses(final String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Store addStore(final Store store) throws DataStorageException {
        return magasinDao.save(store);
    }

    @Override
    public Store updateStore(final Store store) throws DataStorageException {
        final Store s = retrieveStore(store.getStoreId());
        return magasinDao.saveAndFlush(store.merge(s));
    }

    @Override
    public Store retrieveStore(final String storeId) throws DataStorageException {
        final Store s = magasinDao.findOne(storeId);
        if (s == null) {
            throw new DataStorageException("The store that you are trying to retrieve does not exist");
        }
        return s.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Store> T retrieveStore(Class<T> clazz, final String storeId) throws DataStorageException {
        final Store s = retrieveStore(storeId);
        return s.isTypeOf(clazz) ? (T) s : null;
    }

    @Override
    public Boolean removeStore(final String storeId) throws DataStorageException {
        final Store s = retrieveStore(storeId);
        magasinDao.delete(s);
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
        return inventaireDao.save(inventory);
    }

    @Override
    public Inventory updateInventory(final Inventory inventory) throws DataStorageException {
        final Inventory i = retrieveInventory(inventory.getInventoryId());
        return inventaireDao.saveAndFlush(inventory.merge(i));
    }

    @Override
    public Inventory retrieveInventory(final String inventoryId) throws DataStorageException {
        final Inventory i = inventaireDao.findOne(inventoryId);
        if (i == null) {
            throw new DataStorageException("The inventory that you are looking for does not exist.");
        }
        return i.lazyInit();
    }

    @Override
    public Boolean removeInventory(final String inventoryId) throws DataStorageException {
        final Inventory i = retrieveInventory(inventoryId);
        inventaireDao.delete(i);
        return true;
    }

    @Override
    public Page<Inventory> retrieveInventories(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }
}
