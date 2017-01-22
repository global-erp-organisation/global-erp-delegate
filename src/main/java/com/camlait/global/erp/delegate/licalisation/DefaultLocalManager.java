package com.camlait.global.erp.delegate.licalisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.localisation.LocalisationDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.localisation.Localisation;

@Transactional
@Component
public class DefaultLocalManager implements LocalisationManager {

	private final LocalisationDao localDao;

	@Autowired
	public DefaultLocalManager(LocalisationDao localDao) {
		this.localDao = localDao;
	}

	@Override
	public Localisation addLocalisation(Localisation local) throws DataStorageException {
		return localDao.save(local);
	}

	@Override
	public Localisation updateLocalisation(Localisation local) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Localisation retrieveLocalisation(String localId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T retrieveLocalisation(Class<T> clazz, String localId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeLocalisation(String localId) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Localisation> retriveLocalisations(String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}
}
