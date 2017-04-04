package com.camlait.global.erp.delegate.document;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.camlait.global.erp.dao.document.TaxRepository;
import com.camlait.global.erp.delegate.tax.DefaultTaxManager;
import com.camlait.global.erp.delegate.tax.TaxManager;
import com.camlait.global.erp.domain.document.business.Tax;

@RunWith(MockitoJUnitRunner.class)
public class TaxManagerTest {

    @Mock
    private TaxRepository taxRepo;

    private TaxManager manager;

    @Before
    public void setup() {
        manager = new DefaultTaxManager(taxRepo);
    }

    @Test
    public void testAddTax() {
        when(taxRepo.save(any(Tax.class))).thenReturn(Tax.builder().build());
        final Tax d = manager.addTax(Tax.builder().build());
        assertNotNull(d);
        verify(taxRepo, times(1)).save(any(Tax.class));
    }

    @Test
    public void testUpdateExistingTax() {
        when(taxRepo.findOne(anyString())).thenReturn(Tax.builder().taxId("id").build());
        final Tax toUpdate = Tax.builder().taxId("id").build();
        when(taxRepo.saveAndFlush(any(Tax.class))).thenReturn(toUpdate);
        final Tax d = manager.updateTax(toUpdate);
        assertNotNull(d);
        assertThat(d.toJson(), is(toUpdate.toJson()));
        verify(taxRepo, times(1)).saveAndFlush(any(Tax.class));
        verify(taxRepo, times(1)).findOne(eq("id"));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNonExistingTax() {
        final Tax toUpdate = Tax.builder().taxId("id").build();
        manager.updateTax(toUpdate);
        verify(taxRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteExistingTax() {
        when(taxRepo.findOne(anyString())).thenReturn(Tax.builder().taxId("id").build());
        final Boolean result = manager.removeTax("id");
        assertTrue(result);
        verify(taxRepo, times(1)).delete(any(Tax.class));
        verify(taxRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteNonExistingTax() {
        assertFalse(manager.removeTax("id"));
        verify(taxRepo, times(1)).findOne(eq("id"));
        verify(taxRepo, never()).delete(any(Tax.class));
    }

}
