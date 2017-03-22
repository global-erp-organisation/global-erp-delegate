package com.camlait.global.erp.delegate.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.tarif.PriceTypeRepository;
import com.camlait.global.erp.dao.tarif.TarifRepository;
import com.camlait.global.erp.dao.tarif.TarificationRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.tarif.PriceType;
import com.camlait.global.erp.domain.tarif.Tarif;

@Transactional
@Component
public class DefaultTarificationManager implements TarificationManager {

    private final PriceTypeRepository priceTypeRepo;
    private final TarifRepository tarifRepo;
    private final TarificationRepository tarificationRepo;

    @Autowired
    public DefaultTarificationManager(PriceTypeRepository priceDao, TarifRepository tarifDao, TarificationRepository tarificationDao) {
        this.priceTypeRepo = priceDao;
        this.tarifRepo = tarifDao;
        this.tarificationRepo = tarificationDao;
    }

    @Override
    public PriceType addPriceType(final PriceType priceType) throws DataStorageException {
        return priceTypeRepo.save(priceType);
    }

    @Override
    public PriceType updatePriceType(final PriceType priceType) throws DataStorageException {
        final PriceType p = retrievePricetype(priceType.getPriceTypeId());
        return priceTypeRepo.saveAndFlush(priceType.merge(p));
    }

    @Override
    public PriceType retrievePricetype(final String priceTypeId) throws DataStorageException {
        final PriceType p = priceTypeRepo.findOne(priceTypeId);
        if (p == null) {
            throw new DataStorageException("The price type that you are trying to retrieve does not exist.");
        }
        return p.lazyInit();
    }

    @Override
    public Boolean removePricetype(final String priceTypeId) throws DataStorageException {
        final PriceType p = retrievePricetype(priceTypeId);
        priceTypeRepo.delete(p);
        return true;
    }

    @Override
    public Page<PriceType> retrievePriceTypes(final String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tarif addTarif(final Tarif tariff) throws DataStorageException {
        return tarifRepo.save(tariff);
    }

    @Override
    public Tarif updateTarif(final Tarif tariff) throws DataStorageException {
        final Tarif t = retrieveTarif(tariff.getTarifId());
        return tarifRepo.saveAndFlush(tariff.merge(t));
    }

    @Override
    public Tarif retrieveTarif(final String tariffId) throws DataStorageException {
        final Tarif t = tarifRepo.findOne(tariffId);
        if (t == null) {
            throw new DataStorageException("The tariff that you are trying to retrieve does not exist.");
        }
        return t.lazyInit();
    }

    @Override
    public Boolean removeTarif(final String tariffId) throws DataStorageException {
        final Tarif t = retrieveTarif(tariffId);
        tarifRepo.delete(t);
        return true;
    }

    @Override
    public Page<Tarif> retrieveTarifs(final String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double retrieveUnitPrice(final String tarifId, final String zoneId, final String productId) throws DataStorageException {
        return tarificationRepo.retrieveUnitPrice(tarifId, zoneId, productId);
    }
}
