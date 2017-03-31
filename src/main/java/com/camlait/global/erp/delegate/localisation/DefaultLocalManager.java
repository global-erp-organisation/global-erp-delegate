package com.camlait.global.erp.delegate.localisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.localisation.LocalisationRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.localization.Localization;

@Transactional
@Component
public class DefaultLocalManager implements LocalisationManager {

    private final LocalisationRepository localRepo;

    @Autowired
    public DefaultLocalManager(LocalisationRepository localRepo) {
        this.localRepo = localRepo;
    }

    @Override
    public Localization addLocalisation(final Localization local) throws DataStorageException {
        return localRepo.save(local);
    }

    @Override
    public Localization updateLocalisation(final Localization local) throws DataStorageException {
        final Localization l = retrieveLocalisation(local.getLocalId());
        return localRepo.saveAndFlush(local.merge(l));
    }

    @Override
    public Localization retrieveLocalisation(final String localId) throws DataStorageException {
        final Localization l = localRepo.findOne(localId);
        return l == null ? null : l.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Localization> T retrieveLocalisation(Class<T> clazz, final String localId) throws DataStorageException {
        final Localization l = retrieveLocalisation(localId);
        return l == null ? null : l.isTypeOf(clazz) ? (T) l : null;
    }

    @Override
    public Boolean removeLocalisation(final String localId) throws DataStorageException {
        final Localization l = retrieveLocalisation(localId);
        if (l == null) {
            return false;
        }
        localRepo.delete(l);
        return true;
    }

    @Override
    public Page<Localization> retriveLocalisations(final String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }
}
