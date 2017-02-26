package com.camlait.global.erp.delegate.localisation;

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
	public Localisation addLocalisation(final Localisation local) throws DataStorageException {
		return localDao.save(local);
	}

	@Override
	public Localisation updateLocalisation(final Localisation local) throws DataStorageException {
		final Localisation l = retrieveLocalisation(local.getLocalId());
		return localDao.saveAndFlush(local.merge(l));
	}

	@Override
	public Localisation retrieveLocalisation(final String localId) throws DataStorageException {
		final Localisation l = localDao.findOne(localId);
		if (l == null) {
			throw new DataStorageException("The local that you are trying to retrieve does not exist.");
		}
		return l.lazyInit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T retrieveLocalisation(Class<T> clazz, final String localId) throws DataStorageException {
		final Localisation l = retrieveLocalisation(localId);
		return l.isTypeOf(clazz) ? (T) l : null;
	}

	@Override
	public Boolean removeLocalisation(final String localId) throws DataStorageException {
		final Localisation l = retrieveLocalisation(localId);
		localDao.delete(l);
		return true;
	}

	@Override
	public Page<Localisation> retriveLocalisations(final String keyWord, Pageable p) throws DataStorageException {
		// TODO Auto-generated method stub
		return null;
	}
}
