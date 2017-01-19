package com.camlait.global.erp.delegate.bmq;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.exception.DataStorageExcetion;

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
	 * @throws DataStorageExcetion
	 */
	Bmq addBmq(Bmq bmq) throws DataStorageExcetion;

	/**
	 * Update the given Bmq into the data storage.
	 * 
	 * @param bmq
	 *            Bmq to update.
	 * @return The updated Bmq.
	 * @throws DataStorageExcetion
	 */
	Bmq updateBmq(Bmq bmq) throws DataStorageExcetion;

	/**
	 * Retrieves a Bmq from the data storage based on the given bmqId.
	 * 
	 * @param bmqId
	 *            Bmq identifier.
	 * @return The Bmq tha match with the provided identifier.
	 * @throws DataStorageExcetion
	 */
	Bmq retrieveBmq(String bmqId) throws DataStorageExcetion;

	/**
	 * Build the bmq details and store the detail in the data storage.
	 * <p>
	 * The details include Bmq lines generation, Clients payment recovery and
	 * associated documents
	 * 
	 * @param bmq
	 *            Bmq use to generate.
	 * @return The Bmq including the details.
	 * @throws DataStorageExcetion
	 */
	Bmq buildBmqDetails(Bmq bmq) throws DataStorageExcetion;

	/**
	 * Permanently removes a Bmq.
	 * 
	 * @param bmqId
	 *            Bmq identifier to remove.
	 * @return true if the operation is perform without errors and false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removeBmq(String bmqId) throws DataStorageExcetion;

	/**
	 * Retrieves Bmqs from the data storage based on the given
	 * 
	 * @param keyWord
	 * @param p
	 *            Pageable object that indicates how many records can be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Bmq> retrieveBmqs(String keyWord, Pageable p) throws DataStorageExcetion;

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
	 * @throws DataStorageExcetion
	 */
	Page<Bmq> retrieveBmqs(Date start, Date end, Pageable p) throws DataStorageExcetion;

	/**
	 * Automatically generate the cash sales data related to the given Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq identifier.
	 * @throws DataStorageExcetion
	 */
	void generateCashSales(String bmqId) throws DataStorageExcetion;

	/**
	 * Computes the Bmq value without taxes value.
	 * 
	 * @param bmqid
	 *            Given Bmq identifier.
	 * @return The Bmq value without taxes value.
	 * @throws DataStorageExcetion
	 */
	Double bmqValueWithoutTaxes(String bmqId) throws DataStorageExcetion;

	/**
	 * Computes the Bmq value including taxes value.
	 * 
	 * @param bmqid
	 *            Given Bmq identifier.
	 * @return The Bmq value including taxes value.
	 * @throws DataStorageExcetion
	 */
	Double bmqValueWithTaxes(String bmqId) throws DataStorageExcetion;

	/**
	 * Computes the total taxes values for the given Bmq;
	 * 
	 * @param bmqId
	 *            Given Bmq identifier.
	 * @return The total taxes values for the provided Bmq.
	 * @throws DataStorageExcetion
	 */
	Double bmqTaxesValue(String bmqId) throws DataStorageExcetion;

	/**
	 * Computes the total value for a specific tax ties to the given Bmq.
	 * 
	 * @param taxed
	 *            given taxe identifier
	 * @param bmqId
	 *            Bmq identifier that belongs to the tax
	 * @return The total value for the provided tax and the provide Bmq.
	 * @throws DataStorageExcetion
	 */
	Double bmqTaxesValue(String taxId, String bmqId) throws DataStorageExcetion;

	/**
	 * Computes the cash sales value for the given Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq Identifier.
	 * @return The cash sales value the provided Bmq.
	 * @throws DataStorageExcetion
	 */
	Double bmqCashSalesValue(String bmqId) throws DataStorageExcetion;

	/**
	 * Computes The margin value for the provided Bmq.
	 * 
	 * @param bmqId
	 *            Given Bmq Identifier.
	 * @return The margin value for the provided Bmq.
	 * @throws DataStorageExcetion
	 */
	Double bmqMarginSalesValue(String bmqId) throws DataStorageExcetion;

}
