package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.LanguageDao;
import com.camlait.global.erp.dao.auth.TermeDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.translation.Language;
import com.camlait.global.erp.domain.translation.Term;

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
    public Language addLanguage(final Language language) throws DataStorageException {
        return langueDao.save(language);
    }

    @Override
    public Language updateLanguage(final Language language) throws DataStorageException {
        final Language l = retrieveLanguage(language.getLangId());
        return langueDao.saveAndFlush(language.merge(l));
    }

    @Override
    public Language retrieveLanguage(final String languageId) throws DataStorageException {
        final Language l = langueDao.findOne(languageId);
        if (l == null) {
            throw new DataStorageException("The language that you are trying to retrieve does not exist.");
        }
        return l.lazyInit();
    }

    @Override
    public Boolean removeLanguage(final String languageId) throws DataStorageException {
        final Language l = retrieveLanguage(languageId);
        langueDao.delete(l);
        return true;
    }

    @Override
    public Page<Language> RetrieveLanguages(final String keyWord, Pageable p) throws DataStorageException {
        return langueDao.RetrieveLanguages(keyWord, p);
    }

    @Override
    public Term addTerm(final Term term) throws DataStorageException {
        return termeDao.save(term);
    }

    @Override
    public Term updateTerm(final Term term) throws DataStorageException {
        final Term t = retrieveTerm(term.getTermId());
        return termeDao.saveAndFlush(term.merge(t));
    }

    @Override
    public Term retrieveTerm(final String termId) throws DataStorageException {
        final Term t = termeDao.findOne(termId);
        if (t == null) {
            throw new DataStorageException("The term that you are trying to retrieve does not exist.");
        }
        return t.lazyInit();
    }

    @Override
    public Boolean removeTerm(final String termId) throws DataStorageException {
        final Term t = retrieveTerm(termId);
        termeDao.delete(t);
        return true;
    }

    @Override
    public Page<Term> RetrieveTerms(final String keyWord, Pageable p) throws DataStorageException {
        return termeDao.RetrieveTerms(keyWord, p);
    }
}
