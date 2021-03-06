package com.camlait.global.erp.delegate.bmq;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.dm.DailyMovement;
import com.camlait.global.erp.domain.exception.DataStorageException;

import lombok.NonNull;

/**
 * This interface provides operations that belong to The Daily movement
 * management service.
 * 
 * @author Martin Blaise Signe
 */
public interface DailyMovementManager {
    /**
     * Add a DailyMovement to the Data storage.
     * 
     * @param dailyMovement DailyMovement to store
     * @return The stored DailyMovement
     * @throws DataStorageException
     */
    DailyMovement addBmq(@NonNull DailyMovement dailyMovement) throws DataStorageException;

    /**
     * Update the given DailyMovement into the data storage.
     * 
     * @param dailyMovement DailyMovement to update.
     * @return The updated DailyMovement.
     * @throws DataStorageException
     */
    DailyMovement updateBmq(@NonNull DailyMovement dailyMovement) throws DataStorageException;

    /**
     * Retrieves a DailyMovement from the data storage based on the given bmqId.
     * 
     * @param dmId DailyMovement identifier.
     * @return The DailyMovement tha match with the provided identifier.
     * @throws DataStorageException
     */
    DailyMovement retrieveBmq(@NonNull String dmId) throws DataStorageException;

    /**
     * Build the dailyMovement details and store the detail in the data storage.
     * <p>
     * The details include DailyMovement lines generation, Clients payment recovery and
     * associated documents
     * 
     * @param dmId DailyMovement identifier use to generate.
     * @return The DailyMovement including the details.
     * @throws DataStorageException
     */
    DailyMovement buildBmqDetails(@NonNull String dmId) throws DataStorageException;

    /**
     * Permanently removes a DailyMovement.
     * 
     * @param dmId DailyMovement identifier to remove.
     * @return true if the operation is perform without errors and false
     *         otherwise.
     * @throws DataStorageException
     */
    Boolean removeBmq(@NonNull String dmId) throws DataStorageException;

    /**
     * Retrieves Bmqs from the data storage based on the given
     * 
     * @param keyWord
     * @param p Pageable object that indicates how many records can be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<DailyMovement> retrieveBmqs(@NonNull String keyWord, Pageable p) throws DataStorageException;

    /**
     * Retrieves Bmqs from the data storage base on the given inclusion period.
     * 
     * @param start Starting date.
     * @param end Ending date.
     * @param p Pageable object that indicates how many records can be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<DailyMovement> retrieveBmqs(@NonNull Date start, @NonNull Date end, Pageable p) throws DataStorageException;

    /**
     * Automatically generate the cash sales data related to the given DailyMovement.
     * 
     * @param bmqId
     *            Given DailyMovement identifier.
     * @throws DataStorageException
     */
    void generateCashSales(@NonNull String bmqId) throws DataStorageException;

}
