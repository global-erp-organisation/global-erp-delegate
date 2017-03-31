package com.camlait.global.erp.delegate.document;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.document.DocumentRepository;
import com.camlait.global.erp.dao.document.TaxRepository;
import com.camlait.global.erp.delegate.price.TarificationManager;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.document.business.sale.MargingBill;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.product.Product;
import com.camlait.global.erp.domain.tarif.PriceType;

/**
 * Default implementation of the Document manager service interface.
 * 
 * @author Martin Blaise Signe
 */
@Component
@Transactional
public class DefaultDocumentManager implements DocumentManager {

    private final DocumentRepository documentRepository;
    private final TaxRepository taxRepo;
    private final TarificationManager tarifManager;

    @Autowired
    public DefaultDocumentManager(DocumentRepository documentRepository, TaxRepository taxRepo, TarificationManager tarifManager) {
        this.documentRepository = documentRepository;
        this.taxRepo = taxRepo;
        this.tarifManager = tarifManager;
    }

    @Override
    public Document addDocument(final Document document) throws DataStorageException {
        return documentRepository.save(document);
    }

    @Override
    public Document updateDocument(final Document document) throws DataStorageException {
        final Document stored = retrieveDocument(document.getDocumentId());
        return documentRepository.saveAndFlush(document.merge(stored));
    }

    @Override
    public Document retrieveDocument(final String documentId) throws DataStorageException {
        final Document d = documentRepository.findOne(documentId);
        return d == null ? null : d.lazyInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Document> T retrieveDocument(final Class<T> clazz, final String documentId) throws DataStorageException {
        final Document d = retrieveDocument(documentId);
        return d == null ? null : d.isTypeOf(clazz) ? (T) d : null;
    }

    @Override
    public Boolean removeDocument(final String documentId) throws DataStorageException {
        final Document d = retrieveDocument(documentId);
        if (d == null) {
            return false;
        }
        documentRepository.delete(d);
        return true;
    }

    @Override
    public Page<Document> retrieveDocuments(final String keyWord, Pageable p) throws DataStorageException {
        return documentRepository.retrieveDocuments(keyWord, p);
    }

    @Override
    public Page<Document> retrieveDocuments(final Date start, final Date end, Pageable p) throws DataStorageException {
        return documentRepository.retrieveDocuments(start, end, p);
    }

    @Override
    public Double documentValueWithoutTaxes(final String documentId) throws DataStorageException {
        final Document d = retrieveDocument(documentId);
        return d == null ? 0.0 : d.getDocumentDetails().stream().mapToDouble(l -> {
            return l.getLineUnitPrice() * l.getLineQuantity();
        }).sum();
    }

    @Override
    public Double documentTaxesValue(final String documentId) throws DataStorageException {
        final Document d = retrieveDocument(documentId);
        return d == null ? 0.0 : d.getDocumentDetails().stream().mapToDouble(l -> {
            return l.getDocumentDetailsTaxes().stream().mapToDouble(ldt -> {
                return l.getLineUnitPrice() * l.getLineQuantity() * ldt.getTaxRate();
            }).sum();
        }).sum();
    }

    @Override
    public Double documentTaxesValue(final String taxId, final String documentId) throws DataStorageException {
        final Document d = retrieveDocument(documentId);
        return d == null ? 0.0 : d.getDocumentDetails().stream().mapToDouble(ld -> {
            return ld.getDocumentDetailsTaxes().stream().filter(ldt -> ldt.getTax().getTaxId().equals(taxId)).mapToDouble(ldt -> {
                return ld.getLineUnitPrice() * ld.getLineQuantity() * ldt.getTaxRate();
            }).sum();
        }).sum();
    }

    @Override
    public Double documentValueWithTaxes(final String documentId) throws DataStorageException {
        return documentValueWithoutTaxes(documentId) + documentTaxesValue(documentId);
    }

    @Override
    public Double documentMarginValue(final String documentId) throws DataStorageException {
        final MargingBill f = retrieveDocument(MargingBill.class, documentId);
        if (f == null) {
            return 0.0;
        }
        final PriceType priceType = f.getPriceType();
        return f.getDocumentDetails().stream().mapToDouble(l -> {
            final Product p = l.getProduct();
            final Double regularPrice = priceType == null ? p.getDefaultUnitprice() : tarifManager.retrieveUnitPrice(priceType.getPriceTypeId(),
                    f.getZone().getLocalId(), p.getProductId(), f.getClient().getTarif().getTarifId());
            final Double currentValue = l.getLineQuantity() * l.getLineUnitPrice();
            final Double regularValue = l.getLineQuantity() * regularPrice;
            return (currentValue - regularValue);
        }).sum();
    }

    @Override
    public Tax addTax(final Tax tax) throws DataStorageException {
        return taxRepo.save(tax);
    }

    @Override
    public Tax updateTax(final Tax tax) throws DataStorageException {
        final Tax t = retrieveTax(tax.getTaxId());
        return taxRepo.saveAndFlush(tax.merge(t));
    }

    @Override
    public Tax retrieveTax(final String taxId) throws DataStorageException {
        final Tax tax = retrieveTax(taxId);
        return tax == null ? null : tax == null ? null : tax.lazyInit();
    }

    @Override
    public Boolean removeTax(final String taxId) throws DataStorageException {
        final Tax tax = retrieveTax(taxId);
        if (tax == null) {
            return false;
        }
        taxRepo.delete(tax);
        return true;
    }

    @Override
    public Page<Tax> retrieveTaxes(final String keyWord, Pageable p) throws DataStorageException {
        return taxRepo.retrieveTaxes(keyWord, p);
    }
}
