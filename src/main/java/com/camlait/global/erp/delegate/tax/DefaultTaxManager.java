package com.camlait.global.erp.delegate.tax;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.document.TaxRepository;
import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.exception.DataStorageException;

@Transactional
@Component
public class DefaultTaxManager implements TaxManager {

    private final TaxRepository taxRepo;

    public DefaultTaxManager(TaxRepository taxRepo) {
        this.taxRepo = taxRepo;
    }

    @Override
    public Tax addTax(final Tax tax) throws DataStorageException {
        return taxRepo.save(tax);
    }

    @Override
    public Tax updateTax(final Tax tax) throws DataStorageException {
        return taxRepo.saveAndFlush(tax);
    }

    @Override
    public Tax retrieveTax(final String taxId) throws DataStorageException {
        final Tax tax = taxRepo.findOne(taxId);
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

    @Override
    public Tax retrieveTaxByCode(String taxCode) throws DataStorageException {
        final Tax tax = taxRepo.findOneTaxByTaxCode(taxCode);
        return tax == null ? null : tax == null ? null : tax.lazyInit();
    }
}
