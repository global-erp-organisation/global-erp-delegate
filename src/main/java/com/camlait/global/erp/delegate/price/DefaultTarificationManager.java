package com.camlait.global.erp.delegate.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.tarif.PriceTypeDao;
import com.camlait.global.erp.dao.tarif.TarifDao;
import com.camlait.global.erp.dao.tarif.TarificationDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.tarif.PriceType;
import com.camlait.global.erp.domain.tarif.Tarif;
import com.camlait.global.erp.domain.tarif.Tarification;

@Transactional
@Component
public class DefaultTarificationManager implements TarificationManager {

	private final PriceTypeDao priceDao;
	private final TarifDao tarifDao;
	private final TarificationDao tarificationDao;

	@Autowired
	public DefaultTarificationManager(PriceTypeDao priceDao, TarifDao tarifDao, TarificationDao tarificationDao) {
		this.priceDao = priceDao;
		this.tarifDao = tarifDao;
		this.tarificationDao = tarificationDao;
	}

	@Override
	public PriceType addPriceType(PriceType priceType) throws DataStorageException {
		return priceDao.save(priceType);
	}

	@Override
	public PriceType updatePriceType(PriceType priceType) throws DataStorageException {
		final PriceType p = retrievePricetype(priceType.getPriceTypeId());
		return priceDao.saveAndFlush(priceType.merge(p));
	}

	@Override
	public PriceType retrievePricetype(String priceTypeId) throws DataStorageException {
		final PriceType p = priceDao.findOne(priceTypeId);
		if (p == null) {
			throw new DataStorageException("The price type that you are trying to retrieve does not exist.");
		}
		return p.lazyInit();
	}

	@Override
	public Boolean removePricetype(String priceTypeId) throws DataStorageException {
		final PriceType p = retrievePricetype(priceTypeId);
		priceDao.delete(p);
		return true;
	}

	@Override
	public Page<PriceType> retrievePriceTypes(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif addTarif(Tarif tariff) throws DataStorageException {
		return tarifDao.save(tariff);
	}

	@Override
	public Tarif updateTarif(Tarif tariff) throws DataStorageException {
		final Tarif t = retrieveTarif(tariff.getTarifId());
		return tarifDao.saveAndFlush(tariff.merge(t));
	}

	@Override
	public Tarif retrieveTarif(String tariffId) throws DataStorageException {
		final Tarif t = tarifDao.findOne(tariffId);
		if (t == null) {
			throw new DataStorageException("The tariff that you are trying to retrieve does not exist.");
		}
		return t.lazyInit();
	}

	@Override
	public Boolean removeTarif(String tariffId) throws DataStorageException {
		final Tarif t = retrieveTarif(tariffId);
		tarifDao.delete(t);
		return true;
	}

	@Override
	public Page<Tarif> retrieveTarifs(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarification addTarification(Tarification tarification) throws DataStorageException {
		return tarificationDao.save(tarification);
	}

	@Override
	public Tarification updateTarification(Tarification tarification) throws DataStorageException {
		final Tarification t = retrieveTarification(tarification.getTarificationId());
		return tarificationDao.saveAndFlush(tarification.merge(t));
	}

	@Override
	public Tarification retrieveTarification(String tarificationId) throws DataStorageException {
		final Tarification t = tarificationDao.findOne(tarificationId);
		if (t == null) {
			throw new DataStorageException("The tarification that you are trying to retrieve does not exist.");
		}
		return t.lazyInit();
	}

	@Override
	public Boolean removeTarification(String tarificationId) throws DataStorageException {
		final Tarification t = retrieveTarification(tarificationId);
		tarificationDao.delete(t);
		return true;
	}

	@Override
	public Page<Tarification> retrieveTarifications(String keyWord, Pageable p) throws DataStorageException {
		return tarificationDao.retrieveTarifications(keyWord, p);
	}

	@Override
	public Double retrieveUnitPrice(String tarifId, String zoneId, String productId) throws DataStorageException {
		return tarificationDao.retrieveUnitPrice(tarifId, zoneId, productId);
	}
}
