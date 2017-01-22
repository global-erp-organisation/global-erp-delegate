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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceType updatePriceType(PriceType pricetype) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceType retrievePricetype(String priceTypeId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removePricetype(String pricetypeId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PriceType> retrievePriceTypes(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif addTarif(Tarif tariff) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif updateTarif(Tarif tariff) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif retrieveTarif(String tariffId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeTarif(String tarifId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Tarif> retrieveTarifs(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarification addTarification(Tarification tarification) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarification updateTarification(Tarification tarification) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarification retrieveTarification(String tarificationId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeTarification(String tarificationId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Tarification> retrieveTarifications(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double retrieveUnitPrice(String priceTypeId, String zoneId, String productId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

}
