package com.camlait.global.erp.delegate.bmq;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.exception.DataStorageException;

/**
 * This interface provides operations that belong to The Daily movement
 * management service.
 * 
 * @author Martin Blaise Signe
 *
 */
public interface BmqManager {
	/**
	 * Add a Bmq to the Data storage.
	 * 
	 * @param bmq
	 *            Bmq to store
	 * @return The stored Bmq
	 * @throws DataStorageException
	 */
	Bmq addBmq(Bmq bmq) throws DataStorageException;

	/**
	 * Update the given Bmq into the data storage.
	 * 
	 * @param bmq
	 *            Bmq to update.
	 * @return The updated Bmq.
	 * @throws DataStorageException
	 */
	Bmq updateBmq(Bmq bmq) throws DataStorageException;

	/**
	 * Retrieves a Bmq from the data storage based on the given bmqId.
	 * 
	 * @param bmqId
	 *            Bmq identifier.
	 * @return The Bmq tha match with the provided identifier.
	 * @throws DataStorageException
	 */
	Bmq retrieveBmq(String bmqId) throws DataStorageException;

	/**
	 * Build the bmq details and store the detail in the data storage.
	 * <p>
	 * The details include Bmq lines generation, Clients payment recovery and
	 * associated documents
	 * 
	 * @param bmqId
	 *            Bmq identifier use to generate.
	 * @return The Bmq including the details.
	 * @throws DataStorageException
	 */
	Bmq buildBmqDetails(String bmqId) throws DataStorageException;

	/**
	 * Permanently removes a Bmq.
	 * 
	 * @param bmqId
	 *            Bmq identifier to remove.
	 * @return true if the operation is perform without errors and false
	 *         otherwise.
	 * @throws DataStorageException
	 */
	Boolean removeBmq(String bmqId) throws DataStorageException;

	/**
	 * Retrieves Bmqs from the data storage based on the given
	 * 
	 * @param keyWord
	 * @param p
	 *            Pageable object that indicates how many records can be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageException
	 */
	Page<Bmq> retrieveBmqs(String keyWord, Pageable p) throws DataStorageException;

	/**
	 * Retrieves Bmqs from the data storage base on the given inclusion period.
	 * 
	 * @param start
	 *            Starting date.
	 * @param end
	 *            Ending date.
	 * @param p
	 *            Pageable object that indicates how many records can be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageException
	 */
	Page<Bmq> retrieveBmqs(Date start, Date end, Pageable p) throws DataStorageException;

	/**
	 * Automatically generate the cash sales data related to the given Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq identifier.
	 * @throws DataStorageException
	 */
	void generateCashSales(String bmqId) throws DataStorageException;

	/**
	 * Computes the Bmq value without taxes value.
	 * 
	 * @param bmqid
	 *            Given Bmq identifier.
	 * @return The Bmq value without taxes value.
	 * @throws DataStorageException
	 */
	Double bmqValueWithoutTaxes(String bmqId) throws DataStorageException;

	/**
	 * Computes the Bmq value including taxes value.
	 * 
	 * @param bmqid
	 *            Given Bmq identifier.
	 * @return The Bmq value including taxes value.
	 * @throws DataStorageException
	 */
	Double bmqValueWithTaxes(String bmqId) throws DataStorageException;

	/**
	 * Computes the total taxes values for the given Bmq;
	 * 
	 * @param bmqId
	 *            Given Bmq identifier.
	 * @return The total taxes values for the provided Bmq.
	 * @throws DataStorageException
	 */
	Double bmqTaxesValue(String bmqId) throws DataStorageException;

	/**
	 * Computes the total value for a specific tax ties to the given Bmq.
	 * 
	 * @param taxed
	 *            given taxe identifier
	 * @param bmqId
	 *            Bmq identifier that belongs to the tax
	 * @return The total value for the provided tax and the provide Bmq.
	 * @throws DataStorageException
	 */
	Double bmqTaxesValue(String taxId, String bmqId) throws DataStorageException;

	/**
	 * Computes the cash sales value for the given Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq Identifier.
	 * @return The cash sales value the provided Bmq.
	 * @throws DataStorageException
	 */
	Double bmqCashSalesValue(String bmqId) throws DataStorageException;

	/**
	 * Computes The margin value for the provided Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq Identifier.
	 * @return The margin value for the provided Bmq.
	 * @throws DataStorageException
	 */
	Double bmqMarginSalesValue(String bmqId) throws DataStorageException;

}
