package com.camlait.global.erp.delegate.tax;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.amazonaws.util.StringUtils;
import com.camlait.global.erp.dao.document.TaxRepository;
import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.helper.EntityHelper;

@Transactional
@Component
public class DefaultTaxManager implements TaxManager {

    private final TaxRepository taxRepo;

    public DefaultTaxManager(TaxRepository taxRepo) {
        this.taxRepo = taxRepo;
    }

    @Override
    public Tax addTax(final Tax tax) throws DataStorageException {
        return taxRepo.save(tax).lazyInit();
    }

    @Override
    public Tax updateTax(final Tax tax) throws DataStorageException {
        return taxRepo.saveAndFlush(tax).lazyInit();
    }

    @Override
    public Tax retrieveTax(final String taxId) throws DataStorageException {
        Tax tax = taxRepo.retrieveTax(taxId);
        return tax == null ? null : tax.lazyInit();
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
    public List<Tax> retrieveTaxes(String keyWord) throws DataStorageException {
        if (StringUtils.isNullOrEmpty(keyWord)) {
            return EntityHelper.batchInit(taxRepo.findAll());
        }
        return EntityHelper.batchInit(taxRepo.retrieveTaxes(keyWord));
    }
}
