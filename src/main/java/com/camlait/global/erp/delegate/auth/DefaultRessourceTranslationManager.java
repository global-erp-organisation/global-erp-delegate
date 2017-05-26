package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.LanguageRepository;
import com.camlait.global.erp.dao.auth.TermRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.translation.Language;
import com.camlait.global.erp.domain.translation.Term;

@Component
@Transactional
public class DefaultRessourceTranslationManager implements RessourceTranslationManager {

    private final TermRepository termRepository;
    private final LanguageRepository languageRepo;

    @Autowired
    public DefaultRessourceTranslationManager(TermRepository termRepository, LanguageRepository languageRepo) {
        this.termRepository = termRepository;
        this.languageRepo = languageRepo;
    }

    @Override
    public Language addLanguage(final Language language) throws DataStorageException {
        return languageRepo.save(language).lazyInit();
    }

    @Override
    public Language updateLanguage(final Language language) throws DataStorageException {
        final Language l = retrieveLanguage(language.getLangId());
        return languageRepo.saveAndFlush(language.merge(l)).lazyInit();
    }

    @Override
    public Language retrieveLanguage(final String languageId) throws DataStorageException {
        final Language l = languageRepo.findOne(languageId);
        return l == null ? null : l.lazyInit();
    }

    @Override
    public Boolean removeLanguage(final String languageId) throws DataStorageException {
        final Language l = retrieveLanguage(languageId);
        if (l == null) {
            return false;
        }
        languageRepo.delete(l);
        return true;
    }

    @Override
    public Page<Language> retrieveLanguages(final String keyWord, Pageable p) throws DataStorageException {
        return languageRepo.retrieveLanguages(keyWord, p);
    }

    @Override
    public Term addTerm(final Term term) throws DataStorageException {
        return termRepository.save(term).lazyInit();
    }

    @Override
    public Term updateTerm(final Term term) throws DataStorageException {
        final Term t = retrieveTerm(term.getTermId());
        return termRepository.saveAndFlush(term.merge(t)).lazyInit();
    }

    @Override
    public Term retrieveTerm(final String termId) throws DataStorageException {
        final Term t = termRepository.findOne(termId);
        return t == null ? null : t.lazyInit();
    }

    @Override
    public Boolean removeTerm(final String termId) throws DataStorageException {
        final Term t = retrieveTerm(termId);
        if (t == null) {
            return false;
        }
        termRepository.delete(t);
        return true;
    }

    @Override
    public Page<Term> RetrieveTerms(final String keyWord, Pageable p) throws DataStorageException {
        return termRepository.retrieveTerms(keyWord, p);
    }
}
