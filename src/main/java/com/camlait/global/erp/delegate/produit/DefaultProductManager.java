package com.camlait.global.erp.delegate.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.produit.CategorieProduitDao;
import com.camlait.global.erp.dao.produit.ProduitDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.product.Product;
import com.camlait.global.erp.domain.product.ProductCategory;

@Transactional
@Component
public class DefaultProductManager implements ProductManager {

    private final ProduitDao produitDao;
    private final CategorieProduitDao categorieDao;

    @Autowired
    public DefaultProductManager(ProduitDao produitDao, CategorieProduitDao categorieDao) {
        this.produitDao = produitDao;
        this.categorieDao = categorieDao;
    }

    @Override
    public Product addProduct(final Product product) throws DataStorageException {
        return produitDao.save(product);
    }

    @Override
    public Product updateProduct(final Product product) throws DataStorageException {
        final Product p = produitDao.findOne(product.getProductId());
        return produitDao.saveAndFlush(product.merge(p));
    }

    @Override
    public Product retrieveProduct(final String productId) throws DataStorageException {
        final Product p = produitDao.findOne(productId);
        if (p == null) {
            throw new DataStorageException("The product you are trying to retrieve does not exist.");
        }
        return p.lazyInit();
    }

    @Override
    public Boolean removeProduct(final String productId) throws DataStorageException {
        final Product p = retrieveProduct(productId);
        produitDao.delete(p);
        return true;
    }

    @Override
    public Page<Product> retriveProducts(final String keyWord, Pageable p) throws DataStorageException {
        return produitDao.retriveProducts(keyWord, p);
    }

    @Override
    public ProductCategory addProductCategory(final ProductCategory productCategory) throws DataStorageException {
        return categorieDao.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(final ProductCategory productCategory) throws DataStorageException {
        final ProductCategory c = retrieveProductCategory(productCategory.getProductcategoryId());
        return categorieDao.saveAndFlush(productCategory.merge(c));
    }

    @Override
    public ProductCategory retrieveProductCategory(final String productCategoryId) throws DataStorageException {
        final ProductCategory c = categorieDao.findOne(productCategoryId);
        if (c == null) {
            throw new DataStorageException("The product category that you are trying to retrieve does not exist.");
        }
        return c.lazyInit();
    }

    @Override
    public Boolean removeProductCategory(final String productCategoryId) throws DataStorageException {
        final ProductCategory c = retrieveProductCategory(productCategoryId);
        categorieDao.delete(c);
        return true;
    }

    @Override
    public Page<ProductCategory> retriveProductCategories(final String keyWord, Pageable p) throws DataStorageException {
        return categorieDao.retriveProductCategories(keyWord, p);
    }

}
