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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Entrepot retrieveWareHouse(String wareHouseId) throws DataStorageException {
        final Entrepot e = entrepotDao.findOne(wareHouseId);
        return e == null ? null : e.lazyInit();
    }

    @Override
    public Boolean removeWareHouse(String wareHouseId) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Magasin retrieveStore(String storeId) throws DataStorageException {
        final Magasin s = magasinDao.findOne(storeId);
        return s == null ? null : s.lazyInit();
    }

    @Override
    public <T extends Magasin> T retrieveStore(Class<T> clazz, String storeId) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean removeStore(String storeId) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Magasin> retrieveStores(String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Stock> getInventoryByStore(Magasin store) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }
}
