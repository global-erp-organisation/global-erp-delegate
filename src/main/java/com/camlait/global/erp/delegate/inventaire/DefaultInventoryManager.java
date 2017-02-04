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
import com.camlait.global.erp.domain.entrepot.Entrepot;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.inventaire.Stock;

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
    public Entrepot addWareHouse(Entrepot wareHouse) throws DataStorageException {
        return entrepotDao.save(wareHouse);
    }

    @Override
    public Entrepot updateWareHouse(Entrepot wareHouse) throws DataStorageException {
        final Entrepot e = retrieveWareHouse(wareHouse.getEntrepotId());
        return entrepotDao.saveAndFlush(wareHouse.merge(e));
    }

    @Override
    public Entrepot retrieveWareHouse(String wareHouseId) throws DataStorageException {
        final Entrepot e = entrepotDao.findOne(wareHouseId);
        if (e == null) {
            throw new DataStorageException("The warehouse that you are trying to retrieve does not exist.");
        }
        return e.lazyInit();
    }

    @Override
    public Boolean removeWareHouse(String wareHouseId) throws DataStorageException {
        final Entrepot e = retrieveWareHouse(wareHouseId);
        entrepotDao.delete(e);
        return true;
    }

    @Override
    public Page<Entrepot> retrieveWareHouses(String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Magasin addStore(Magasin store) throws DataStorageException {
        return magasinDao.save(store);
    }

    @Override
    public Magasin updateStore(Magasin store) throws DataStorageException {
        final Magasin s = retrieveStore(store.getMagasinId());
        return magasinDao.saveAndFlush(store.merge(s));
    }

    @Override
    public Magasin retrieveStore(String storeId) throws DataStorageException {
        final Magasin s = magasinDao.findOne(storeId);
        if (s == null) {
            throw new DataStorageException("The store that you are trying to retrieve does not exist");
        }
        return s.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Magasin> T retrieveStore(Class<T> clazz, String storeId) throws DataStorageException {
        final Magasin s = retrieveStore(storeId);
        return s.isTypeOf(clazz) ? (T) s : null;
    }

    @Override
    public Boolean removeStore(String storeId) throws DataStorageException {
        final Magasin s = retrieveStore(storeId);
        magasinDao.delete(s);
        return true;
    }

    @Override
    public Page<Magasin> retrieveStores(String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Stock> getInventoryByStore(String storeId) throws DataStorageException {
        final Magasin store = retrieveStore(storeId);
        return store.getStocks();
    }

    @Override
    public Inventaire addInventory(Inventaire inventory) throws DataStorageException {
        return inventaireDao.save(inventory);
    }

    @Override
    public Inventaire updateInventory(Inventaire inventory) throws DataStorageException {
        final Inventaire i = retrieveInventory(inventory.getInventaireId());
        return inventaireDao.saveAndFlush(inventory.merge(i));
    }

    @Override
    public Inventaire retrieveInventory(String inventoryId) throws DataStorageException {
        final Inventaire i = inventaireDao.findOne(inventoryId);
        if (i == null) {
            throw new DataStorageException("The inventory that you are looking for does not exist.");
        }
        return i.lazyInit();
    }

    @Override
    public Boolean removeInventory(String inventoryId) throws DataStorageException {
        final Inventaire i = retrieveInventory(inventoryId);
        inventaireDao.delete(i);
        return true;
    }

    @Override
    public Page<Inventaire> retrieveInventories(String keyWord, Pageable p) throws DataStorageException {
        return null;
    }
}
