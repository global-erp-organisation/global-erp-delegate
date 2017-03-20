package com.camlait.global.erp.delegate.partenaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.partner.PartnerRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.partner.Partner;

@Transactional
@Component
public class DefaultPartnerManager implements PartnerManager {

    private final PartnerRepository partenaireDao;

    @Autowired
    public DefaultPartnerManager(PartnerRepository partenaireDao) {
        this.partenaireDao = partenaireDao;
    }

    @Override
    public Partner addPartner(final Partner partner) throws DataStorageException {
        return partenaireDao.save(partner);
    }

    @Override
    public Partner updatePartner(final Partner partner) throws DataStorageException {
        final Partner p = retrievePartner(partner.getPartnerId());
        return partenaireDao.saveAndFlush(p.merge(partner));
    }

    @Override
    public Partner retrievePartner(final String partnerId) throws DataStorageException {
        final Partner p = partenaireDao.findOne(partnerId);
        if (p == null) {
            throw new DataStorageException("The partner you are trying to retrieve does not exist.");
        }
        return p.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Partner> T retrievePartner(Class<T> clazz, final String partnerId) throws DataStorageException {
        final Partner p = retrievePartner(partnerId);
        return p.isTypeOf(clazz) ? (T) p : null;
    }

    @Override
    public Boolean removePartner(final String partnerId) throws DataStorageException {
        final Partner p = retrievePartner(partnerId);
        partenaireDao.delete(p);
        return true;
    }

    @Override
    public Page<Partner> retrievePartners(final String keyWord, Pageable p) throws DataStorageException {
        return null;
    }
}
