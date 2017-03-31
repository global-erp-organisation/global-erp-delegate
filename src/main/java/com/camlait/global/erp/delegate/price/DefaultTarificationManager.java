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
import com.camlait.global.erp.domain.tarif.Tariff;

@Transactional
@Component
public class DefaultTarificationManager implements TarificationManager {

    private final PriceTypeRepository priceTypeRepo;
    private final TarificationRepository tarificationRepo;
    private final TarifRepository tarifRepo;

    @Autowired
    public DefaultTarificationManager(PriceTypeRepository priceDao, TarificationRepository tarificationDao, TarifRepository tarifRepo) {
        this.priceTypeRepo = priceDao;
        this.tarificationRepo = tarificationDao;
        this.tarifRepo = tarifRepo;
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
        return p == null ? null : p.lazyInit();
    }

    @Override
    public Boolean removePricetype(final String priceTypeId) throws DataStorageException {
        final PriceType p = retrievePricetype(priceTypeId);
        if (p == null) {
            return false;
        }
        priceTypeRepo.delete(p);
        return true;
    }

    @Override
    public Page<PriceType> retrievePriceTypes(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }

    @Override
    public Tariff addTariff(final Tariff tariff) throws DataStorageException {
        return tarifRepo.save(tariff);
    }

    @Override
    public Tariff updateTariff(final Tariff tariff) throws DataStorageException {
        final Tariff t = retrieveTariff(tariff.getTarifId());
        return tarifRepo.saveAndFlush(tariff.merge(t));
    }

    @Override
    public Tariff retrieveTariff(final String tariffId) throws DataStorageException {
        final Tariff t = tarifRepo.findOne(tariffId);
        return t == null ? null : t.lazyInit();
    }

    @Override
    public Boolean removeTariff(final String tariffId) throws DataStorageException {
        final Tariff t = retrieveTariff(tariffId);
        if (t == null) {
            return false;
        }
        tarifRepo.delete(t);
        return true;
    }

    @Override
    public Page<Tariff> retrieveTariffs(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }

    @Override
    public Double retrieveUnitPrice(final String tarifId, final String zoneId, final String productId, String tariffId) throws DataStorageException {
        return tarificationRepo.retrieveUnitPrice(tarifId, zoneId, productId, tariffId);
    }
}
