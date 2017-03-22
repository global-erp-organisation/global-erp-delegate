package com.camlait.global.erp.delegate.auth;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.LanguageRepository;
import com.camlait.global.erp.dao.auth.TermRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.translation.Language;
import com.camlait.global.erp.domain.translation.Term;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class ResourceTranslationManagerTest {

    @Mock
    private TermRepository termRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private Pageable page;

    private RessourceTranslationManager manager;

    @Before
    public void setup() {
        manager = new DefaultRessourceTranslationManager(termRepository, languageRepository);
    }

    @Test
    public void testAddLanguage() {
        when(languageRepository.save(any(Language.class))).thenReturn(Language.builder().build());
        final Language l = manager.addLanguage(Language.builder().build());
        assertNotNull(l);
        verify(languageRepository, times(1)).save(any(Language.class));
    }

    @Test
    public void testUpdateExistingLanguage() {
        when(languageRepository.findOne(anyString())).thenReturn(Language.builder().langId("lang-id").build());
        final Language toUpdate = Language.builder().langId("lang-id").title("en").build();
        when(languageRepository.saveAndFlush(any(Language.class))).thenReturn(toUpdate);
        final Language l = manager.updateLanguage(toUpdate);
        assertNotNull(l);
        assertThat(l.toJson(), is(toUpdate.toJson()));
        verify(languageRepository, times(1)).saveAndFlush(any(Language.class));
        verify(languageRepository, times(1)).findOne(eq("lang-id"));
    }

    @Test(expected = DataStorageException.class)
    public void testUpdateNonExistingLanguage() {
        final Language toUpdate = Language.builder().langId("lang-id").title("en").build();
        manager.updateLanguage(toUpdate);
        verify(languageRepository, times(1)).findOne(eq("lang-id"));
    }

    @Test
    public void testDeleteExistingLanguage() {
        when(languageRepository.findOne(anyString())).thenReturn(Language.builder().langId("lang-id").build());
        final Boolean result = manager.removeLanguage("lang-id");
        assertTrue(result);
        verify(languageRepository, times(1)).delete(any(Language.class));
        verify(languageRepository, times(1)).findOne(eq("lang-id"));
    }

    @Test(expected = DataStorageException.class)
    public void testDeleteNonExistingLanguage() {
        manager.removeLanguage("lang-id");
        verify(languageRepository, times(1)).findOne(eq("lang-id"));
        verify(languageRepository, never()).delete(any(Language.class));
    }

    @Test
    public void testAddTerm() {
        when(termRepository.save(any(Term.class))).thenReturn(Term.builder().build());
        final Term t = manager.addTerm(Term.builder().build());
        assertNotNull(t);
        verify(termRepository, times(1)).save(any(Term.class));
    }

    @Test
    public void testUpdateExistingTerm() {
        when(termRepository.findOne(anyString())).thenReturn(Term.builder().termId("term-id").build());
        final Term toUpdate = Term.builder().termId("term-id").build();
        when(termRepository.saveAndFlush(any(Term.class))).thenReturn(toUpdate);
        final Term t = manager.updateTerm(toUpdate);
        assertNotNull(t);
        assertThat(t.toJson(), is(toUpdate.toJson()));
        verify(termRepository, times(1)).saveAndFlush(any(Term.class));
        verify(termRepository, times(1)).findOne(eq("term-id"));
    }

    @Test(expected = DataStorageException.class)
    public void testUpdateNonExistingTerm() {
        final Term toUpdate = Term.builder().termId("term-id").build();
        manager.updateTerm(toUpdate);
        verify(termRepository, times(1)).findOne(eq("term-id"));
    }

    @Test
    public void testDeleteExistingTerm() {
        when(termRepository.findOne(anyString())).thenReturn(Term.builder().termId("term-id").build());
        final Boolean result = manager.removeTerm("term-id");
        assertTrue(result);
        verify(termRepository, times(1)).delete(any(Term.class));
        verify(termRepository, times(1)).findOne(eq("term-id"));
    }

    @Test(expected = DataStorageException.class)
    public void testDeleteNonExistingTerm() {
        manager.removeTerm("term-id");
        verify(termRepository, times(1)).findOne(eq("term-id"));
        verify(termRepository, never()).delete(any(Term.class));
    }

    @Test
    public void testRetrieveLanguages() {
        Page<Language> p = mock(Page.class);
        when(languageRepository.retrieveLanguages(anyString(), any())).thenReturn(p);
        manager.retrieveLanguages("lang-id", page);
        verify(languageRepository, times(1)).retrieveLanguages(anyString(), any());
    }

    @Test
    public void testRetrieveTerms() {
        Page<Term> p = mock(Page.class);
        when(termRepository.retrieveTerms(anyString(), any())).thenReturn(p);
        manager.RetrieveTerms("term-id", page);
        verify(termRepository, times(1)).retrieveTerms(anyString(), any());
    }
}
