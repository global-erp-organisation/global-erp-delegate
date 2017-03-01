package com.camlait.global.erp.delegate.bmq;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.dm.DailyMovement;
import com.camlait.global.erp.domain.exception.DataStorageException;

/**
 * This interface provides operations that belong to The Daily movement
 * management service.
 * 
 * @author Martin Blaise Signe
 */
public interface BmqManager {
    /**
     * Add a DailyMovement to the Data storage.
     * 
     * @param dailyMovement DailyMovement to store
     * @return The stored DailyMovement
     * @throws DataStorageException
     */
    DailyMovement addBmq(DailyMovement dailyMovement) throws DataStorageException;

    /**
     * Update the given DailyMovement into the data storage.
     * 
     * @param dailyMovement DailyMovement to update.
     * @return The updated DailyMovement.
     * @throws DataStorageException
     */
    DailyMovement updateBmq(DailyMovement dailyMovement) throws DataStorageException;

    /**
     * Retrieves a DailyMovement from the data storage based on the given bmqId.
     * 
     * @param bmqId DailyMovement identifier.
     * @return The DailyMovement tha match with the provided identifier.
     * @throws DataStorageException
     */
    DailyMovement retrieveBmq(String bmqId) throws DataStorageException;

    /**
     * Build the bmq details and store the detail in the data storage.
     * <p>
     * The details include DailyMovement lines generation, Clients payment recovery and
     * associated documents
     * 
     * @param bmqId DailyMovement identifier use to generate.
     * @return The DailyMovement including the details.
     * @throws DataStorageException
     */
    DailyMovement buildBmqDetails(String bmqId) throws DataStorageException;

    /**
     * Permanently removes a DailyMovement.
     * 
     * @param bmqId DailyMovement identifier to remove.
     * @return true if the operation is perform without errors and false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removeBmq(String bmqId) throws DataStorageException;

    /**
     * Retrieves Bmqs from the data storage based on the given
     * 
     * @param keyWord
     * @param p Pageable object that indicates how many records can be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<DailyMovement> retrieveBmqs(String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves Bmqs from the data storage base on the given inclusion period.
     * 
     * @param start Starting date.
     * @param end Ending date.
     * @param p Pageable object that indicates how many records can be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<DailyMovement> retrieveBmqs(Date start, Date end, Pageable p) throws DataStorageException;

    /**
     * Automatically generate the cash sales data related to the given DailyMovement.
     * 
     * @param bmqId
     *            Given DailyMovement identifier.
     * @throws DataStorageException
     */
    void generateCashSales(String bmqId) throws DataStorageException;

    /**
     * Computes the DailyMovement value without taxes value.
     * 
     * @param bmqid Given DailyMovement identifier.
     * @return The DailyMovement value without taxes value.
     * @throws DataStorageException
     */
    Double bmqValueWithoutTaxes(String bmqId) throws DataStorageException;

    /**
     * Computes the DailyMovement value including taxes value.
     * 
     * @param bmqid Given DailyMovement identifier.
     * @return The DailyMovement value including taxes value.
     * @throws DataStorageException
     */
    Double bmqValueWithTaxes(String bmqId) throws DataStorageException;

    /**
     * Computes the total taxes values for the given DailyMovement;
     * 
     * @param bmqId Given DailyMovement identifier.
     * @return The total taxes values for the provided DailyMovement.
     * @throws DataStorageException
     */
    Double bmqTaxesValue(String bmqId) throws DataStorageException;

    /**
     * Computes the total value for a specific tax ties to the given DailyMovement.
     * 
     * @param taxed given taxe identifier
     * @param bmqId DailyMovement identifier that belongs to the tax
     * @return The total value for the provided tax and the provide DailyMovement.
     * @throws DataStorageException
     */
    Double bmqTaxesValue(String taxId, String bmqId) throws DataStorageException;

    /**
     * Computes the cash sales value for the given DailyMovement.
     * 
     * @param bmqId Given DailyMovement Identifier.
     * @return The cash sales value the provided DailyMovement.
     * @throws DataStorageException
     */
    Double bmqCashSalesValue(String bmqId) throws DataStorageException;

    /**
     * Computes The margin value for the provided DailyMovement.
     * 
     * @param bmqId Given DailyMovement Identifier.
     * @return The margin value for the provided DailyMovement.
     * @throws DataStorageException
     */
    Double bmqMarginSalesValue(String bmqId) throws DataStorageException;

}
