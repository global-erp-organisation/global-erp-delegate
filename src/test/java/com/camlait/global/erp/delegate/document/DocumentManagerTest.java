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

import java.util.Collection;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.camlait.global.erp.dao.document.DocumentRepository;
import com.camlait.global.erp.dao.document.TaxRepository;
import com.camlait.global.erp.delegate.price.TarificationManager;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.DocumentDetails;
import com.camlait.global.erp.domain.document.DocumentDetailsTax;
import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.document.business.sale.ClientBill;
import com.camlait.global.erp.domain.document.business.sale.MargingBill;
import com.camlait.global.erp.domain.inventory.Stock;
import com.camlait.global.erp.domain.product.Product;
import com.camlait.global.erp.domain.product.ProductCategory;
import com.camlait.global.erp.domain.warehouse.LandStore;
import com.camlait.global.erp.domain.warehouse.Store;
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class DocumentManagerTest {

    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private TaxRepository taxRepo;
    @Mock
    private TarificationManager tarifManager;

    private DocumentManager manager;

    @Before
    public void setup() {
        manager = new DefaultDocumentManager(documentRepository, tarifManager);
    }

    @Test
    public void testAddDocument() {
        when(documentRepository.save(any(Document.class))).thenReturn(sampleDocument());
        final Document d = manager.addDocument(sampleDocument());
        assertNotNull(d);
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    public void testUpdateExistingDocument() {
        when(documentRepository.findOne(anyString())).thenReturn(sampleDocument());
        final Document toUpdate = sampleDocument();
        when(documentRepository.saveAndFlush(any(Document.class))).thenReturn(toUpdate);
        final Document d = manager.updateDocument(toUpdate);
        assertNotNull(d);
        assertThat(d.toJson(), is(toUpdate.toJson()));
        verify(documentRepository, times(1)).saveAndFlush(any(Document.class));
        verify(documentRepository, times(1)).findOne(eq("id"));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNonExistingDocument() {
        final Document toUpdate = sampleDocument();
        manager.updateDocument(toUpdate);
        verify(documentRepository, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteExistingDocument() {
        final Document toUpdate = new ClientBill();
        toUpdate.setDocumentId("id");
        when(documentRepository.findOne(anyString())).thenReturn(sampleDocument());
        final Boolean result = manager.removeDocument("id");
        assertTrue(result);
        verify(documentRepository, times(1)).delete(any(Document.class));
        verify(documentRepository, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteNonExistingDocument() {
        assertFalse(manager.removeDocument("id"));
        verify(documentRepository, times(1)).findOne(eq("id"));
        verify(documentRepository, never()).delete(any(Document.class));
    }

 
    @Test
    public void testDocumentValueWithoutTaxes() {
        final Document d = sampleDocument(sampleDetails(), sampleStore(), (dd, s) -> {
            final Document f = sampleDocument();
            f.setDocumentDetails(dd);
            f.setStore(s);
            return f;
        });
        when(documentRepository.findOne(anyString())).thenReturn(d);
        final Double value = manager.documentValueWithoutTaxes("FA001");
        assertThat(value, is(20.0));
        verify(documentRepository, times(1)).findOne(eq("FA001"));

    }

    @Test
    public void testTaxesValue() {
        final Document d = sampleDocument(sampleDetails(), sampleStore(), (dd, s) -> {
            final Document f = sampleDocument();
            f.setDocumentDetails(dd);
            f.setStore(s);
            return f;
        });
        when(documentRepository.findOne(anyString())).thenReturn(d);
        final Double value = manager.documentTaxesValue("FA001");
        assertThat(value, is(3.8));

    }

    @Test
    public void testSpecificTaxValue() {
        final Document d = sampleDocument(sampleDetails(), sampleStore(), (dd, s) -> {
            final Document f = sampleDocument();
            f.setDocumentDetails(dd);
            f.setStore(s);
            return f;
        });
        when(documentRepository.findOne(anyString())).thenReturn(d);
        when(taxRepo.findOne(anyString())).thenReturn(Tax.builder().taxId("tva").taxCode("TVA").taxDescription("Tva").percentageValue(0.19).build());
        final Double value = manager.documentTaxesValue("tva", "FA001");
        assertThat(value, is(3.8));

    }

    @Test
    public void testDocumentValueWithTaxes() {
        final Document d = sampleDocument(sampleDetails(), sampleStore(), (dd, s) -> {
            final Document f = sampleDocument();
            f.setDocumentDetails(dd);
            f.setStore(s);
            return f;
        });
        when(documentRepository.findOne(anyString())).thenReturn(d);
        final Double value = manager.documentValueWithTaxes("FA001");
        assertThat(value, is(23.8));
        verify(documentRepository, times(1)).findOne(eq("FA001"));

    }

    @Test
    public void testDocumentMarginValue() {
        final Document d = sampleDocument(sampleDetails(), sampleStore(), (dd, s) -> {
            final Document f = new MargingBill();
            f.setDocumentDetails(dd);
            f.setStore(s);
            return f;
        });
        when(documentRepository.findOne(anyString())).thenReturn(d);
        final Double value = manager.documentMarginValue("FA001");
        assertThat(value, is(0.0));
        verify(documentRepository, times(1)).findOne(eq("FA001"));

    }

    private Document sampleDocument(Collection<DocumentDetails> dd, Store s, BiFunction<Collection<DocumentDetails>, Store, Document> f) {
        return f.apply(dd, s);
    }

    private Document sampleDocument() {
        final Document doc = new ClientBill();
        doc.setDocumentId("id");
        return doc;
    }

    private Store sampleStore() {
        final Store s = new LandStore();
        s.setStoreCode("store");
        s.setStoreId("store");
        return s;
    }

    private Collection<DocumentDetails> sampleDetails() {
        final Tax t = Tax.builder().taxId("tva").taxCode("TVA").taxDescription("Tva").percentageValue(0.19).build();
        final Product p = Product.builder().defaultUnitprice(2.0).category(ProductCategory.builder().categoryDescription("category").build()).build();
        final Store s = sampleStore();
        final Stock st = Stock.builder().availableQuantity(100L).store(s).product(p).build();
        s.setStocks(Sets.newHashSet(st));
        final DocumentDetails dd = DocumentDetails.builder().product(p).lineQuantity(10L).lineUnitPrice(2.0).build();
        final DocumentDetailsTax dt = DocumentDetailsTax.builder().tax(t).documentDetails(dd).build();
        dd.setDocumentDetailsTaxes(Sets.newHashSet(dt));
        return Sets.newHashSet(dd);
    }

}
