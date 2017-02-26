package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.LanguageDao;
import com.camlait.global.erp.dao.auth.TermeDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.traduction.Langue;
import com.camlait.global.erp.domain.traduction.Terme;

@Component
@Transactional
public class DefaultRessourceTranslationManager implements RessourceTranslationManager {

    private final TermeDao termeDao;
    private final LanguageDao langueDao;

    @Autowired
    public DefaultRessourceTranslationManager(TermeDao termeDao, LanguageDao langueDao) {
        this.termeDao = termeDao;
        this.langueDao = langueDao;
    }

    @Override
    public Langue addLanguage(final Langue language) throws DataStorageException {
        return langueDao.save(language);
    }

    @Override
    public Langue updateLanguage(final Langue language) throws DataStorageException {
        final Langue l = retrieveLanguage(language.getLangId());
        return langueDao.saveAndFlush(language.merge(l));
    }

    @Override
    public Langue retrieveLanguage(final String languageId) throws DataStorageException {
        final Langue l = langueDao.findOne(languageId);
        if (l == null) {
            throw new DataStorageException("The language that you are trying to retrieve does not exist.");
        }
        return l.lazyInit();
    }

    @Override
    public Boolean removeLanguage(final String languageId) throws DataStorageException {
        final Langue l = retrieveLanguage(languageId);
        langueDao.delete(l);
        return true;
    }

    @Override
    public Page<Langue> RetrieveLanguages(final String keyWord, Pageable p) throws DataStorageException {
        return langueDao.RetrieveLanguages(keyWord, p);
    }

    @Override
    public Terme addTerm(final Terme term) throws DataStorageException {
        return termeDao.save(term);
    }

    @Override
    public Terme updateTerm(final Terme term) throws DataStorageException {
        final Terme t = retrieveTerm(term.getTermeId());
        return termeDao.saveAndFlush(term.merge(t));
    }

    @Override
    public Terme retrieveTerm(final String termId) throws DataStorageException {
        final Terme t = termeDao.findOne(termId);
        if (t == null) {
            throw new DataStorageException("The term that you are trying to retrieve does not exist.");
        }
        return t.lazyInit();
    }

    @Override
    public Boolean removeTerm(final String termId) throws DataStorageException {
        final Terme t = retrieveTerm(termId);
        termeDao.delete(t);
        return true;
    }

    @Override
    public Page<Terme> RetrieveTerms(final String keyWord, Pageable p) throws DataStorageException {
        return termeDao.RetrieveTerms(keyWord, p);
    }
}
