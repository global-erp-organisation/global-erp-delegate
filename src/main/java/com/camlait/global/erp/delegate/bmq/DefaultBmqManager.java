package com.camlait.global.erp.delegate.bmq;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.dm.DailyManagementRepository;
import com.camlait.global.erp.delegate.document.DocumentManager;
import com.camlait.global.erp.delegate.inventaire.InventoryManager;
import com.camlait.global.erp.domain.dm.DailyMovement;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.DocumentDetails;
import com.camlait.global.erp.domain.document.business.sale.CashClientBill;
import com.camlait.global.erp.domain.exception.DataStorageException;

@Transactional
@Component
public class DefaultBmqManager implements BmqManager {

    private final DailyManagementRepository dailyManagementRepository;
    private final DocumentManager documentManager;
    private final InventoryManager inventoryManager;

    @Autowired
    public DefaultBmqManager(DailyManagementRepository dailyManagementRepository, DocumentManager documentManager, InventoryManager inventoryManager) {
        this.dailyManagementRepository = dailyManagementRepository;
        this.documentManager = documentManager;
        this.inventoryManager = inventoryManager;
    }

    @Override
    public DailyMovement addBmq(final DailyMovement dailyMovement) throws DataStorageException {
        return dailyManagementRepository.save(dailyMovement);
    }

    @Override
    public DailyMovement updateBmq(final DailyMovement dailyMovement) throws DataStorageException {
        final DailyMovement b = retrieveBmq(dailyMovement.getDmId());
        return dailyManagementRepository.saveAndFlush(dailyMovement.merge(b));
    }

    @Override
    public DailyMovement retrieveBmq(final String bmqId) throws DataStorageException {
        final DailyMovement b = dailyManagementRepository.findOne(bmqId);
        if (b == null) {
            throw new DataStorageException("The DailyMovement that your are looking for does not exist.");
        }
        return b.lazyInit();
    }

    @Override
    public DailyMovement buildBmqDetails(final String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return dailyManagementRepository.saveAndFlush(b.buildLigne());
    }

    @Override
    public Boolean removeBmq(final String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        dailyManagementRepository.delete(b);
        return true;
    }

    @Override
    public Page<DailyMovement> retrieveBmqs(final String keyWord, Pageable p) throws DataStorageException {
        return dailyManagementRepository.retrieveBmqs(keyWord, p);
    }

    @Override
    public Page<DailyMovement> retrieveBmqs(final Date start, final Date end, Pageable p) throws DataStorageException {
        return dailyManagementRepository.retrieveBmqs(start, end, p);
    }

    @Override
    public void generateCashSales(final String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        final Document d = CashClientBill.createHeaderFromBmq(b);
        final Collection<DocumentDetails> lines = inventoryManager.getInventoryByStore(b.getStore().getStoreId()).parallelStream().map(s -> {
            return DocumentDetails.builder()
                    .document(d)
                    .lineUnitPrice(s.getProduct().getDefaultUnitprice())
                    .product(s.getProduct())
                    .productId(s.getProduct().getProductId())
                    .lineQuantity(s.getAvailableQuantity())
                    .operationDirection(d.getOperationDirection())
                    .build();

        }).collect(Collectors.toList());
        d.setDocumentDetails(lines);
        b.getDocuments().add(d);
        updateBmq(b);
    }

    @Override
    public Double bmqValueWithoutTaxes(String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isClientBill()).mapToDouble(d -> {
            return documentManager.documentValueWithoutTaxes(d.getDocumentId());
        }).sum();
    }

    @Override
    public Double bmqValueWithTaxes(String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isClientBill()).mapToDouble(d -> {
            return documentManager.documentValueWithTaxes(d.getDocumentId());
        }).sum();
    }

    @Override
    public Double bmqTaxesValue(String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isClientBill()).mapToDouble(d -> {
            return documentManager.documentTaxesValue(d.getDocumentId());
        }).sum();
    }

    @Override
    public Double bmqTaxesValue(String taxId, String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isClientBill()).mapToDouble(d -> {
            return documentManager.documentTaxesValue(taxId, d.getDocumentId());
        }).sum();
    }

    @Override
    public Double bmqCashSalesValue(String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isCashBill()).mapToDouble(d -> {
            return documentManager.documentValueWithTaxes(d.getDocumentId());
        }).sum();
    }

    @Override
    public Double bmqMarginSalesValue(String bmqId) throws DataStorageException {
        final DailyMovement b = retrieveBmq(bmqId);
        return b.getDocuments().stream().filter(d -> d.isMarginBill()).mapToDouble(d -> {
            return documentManager.documentValueWithTaxes(d.getDocumentId());
        }).sum();
    }
}
