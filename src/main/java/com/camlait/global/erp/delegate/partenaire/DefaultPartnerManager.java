package com.camlait.global.erp.delegate.partenaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.partenaire.Partenaire;

@Transactional
@Component
public class DefaultPartnerManager implements PartnerManager {

	private final PartenaireDao partenaireDao;

	@Autowired
	public DefaultPartnerManager(PartenaireDao partenaireDao) {
		this.partenaireDao = partenaireDao;
	}

	@Override
	public Partenaire addPartner(Partenaire partner) throws DataStorageException {
		return partenaireDao.save(partner);
	}

	@Override
	public Partenaire updatePartner(Partenaire partner) throws DataStorageException {
		final Partenaire p = retrievePartner(partner.getPartenaireId());
		return partenaireDao.saveAndFlush(p.merge(partner));
	}

	@Override
	public Partenaire retrievePartner(String partnerId) throws DataStorageException {
		final Partenaire p = partenaireDao.findOne(partnerId);
		if (p == null) {
			throw new DataStorageException("The partner you are trying to retrieve does not exist.");
		}
		return p.lazyInit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Partenaire> T retrievePartner(Class<T> clazz, String partnerId) throws DataStorageException {
		final Partenaire p = retrievePartner(partnerId);
		return p.isTypeOf(clazz) ? (T) p : null;
	}

	@Override
	public Boolean removePartner(String partnerId) throws DataStorageException {
		final Partenaire p = retrievePartner(partnerId);
		partenaireDao.delete(p);
		return true;
	}

	@Override
	public Page<Partenaire> retrievePartners(String keyWord, Pageable p) throws DataStorageException {
		return null;
	}
}
