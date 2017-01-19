package com.camlait.global.erp.delegate.produit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.exception.DataStorageExcetion;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;

public interface ProductManager {

	/**
	 * Add a product in to the data storage.
	 * 
	 * @param product
	 *            Product to store
	 * @return The stored product.
	 * @throws DataStorageExcetion
	 */
	Produit addProduct(Produit product) throws DataStorageExcetion;

	/**
	 * Update the given product information in the data store.
	 * 
	 * @param product
	 *            Given product.
	 * @return The updated product.
	 * @throws DataStorageExcetion
	 */
	Produit updateProduct(Produit product) throws DataStorageExcetion;

	/**
	 * Retrieves a product from the data storage based on the given identifier.
	 * 
	 * @param productId
	 *            Product Identifier.
	 * @return The product informations that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	Produit retrieveProduct(String productId) throws DataStorageExcetion;

	/**
	 * Permanently remove a product from the data storage.
	 * 
	 * @param productId
	 *            product Identifier.
	 * @return True if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	Boolean removeProduct(String productId) throws DataStorageExcetion;

	/**
	 * Retrieves products from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            provided key word.
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return All products that belong to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<Produit> retriveProducts(String keyWord, Pageable p) throws DataStorageExcetion;
	
	
	/**
	 * Add a product category in to the data storage.
	 * 
	 * @param productCategory
	 *            Product category to store
	 * @return The stored product category.
	 * @throws DataStorageExcetion
	 */
	CategorieProduit addProductCategory(CategorieProduit productCategory) throws DataStorageExcetion;

	/**
	 * Update the given product category informations in the data store.
	 * 
	 * @param productCategory
	 *            Given product.
	 * @return The updated product category.
	 * @throws DataStorageExcetion
	 */
	CategorieProduit updateProductCategory(CategorieProduit productCategory) throws DataStorageExcetion;

	/**
	 * Retrieves a product category from the data storage based on the given identifier.
	 * 
	 * @param productCategoryId
	 *            Product category Identifier.
	 * @return The product category informations that belongs to the given identifier.
	 * @throws DataStorageExcetion
	 */
	CategorieProduit retrieveProductCategory(String productCategoryId) throws DataStorageExcetion;

	/**
	 * Permanently remove a product category from the data storage.
	 * 
	 * @param productCategoryId
	 *            product category Identifier.
	 * @return True if the operation is performed without error or false
	 *         otherwise.
	 * @throws DataStorageExcetion
	 */
	CategorieProduit removeProductCategory(String productCategoryId) throws DataStorageExcetion;

	/**
	 * Retrieves product categories from the data storage based on the given key word.
	 * 
	 * @param keyWord
	 *            provided key word.
	 * @param p
	 *            Pageable object that indicated how many records need to be
	 *            extracted per page.
	 * @return All product categories that belong to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<CategorieProduit> retriveProductCategories(String keyWord, Pageable p) throws DataStorageExcetion;


}
