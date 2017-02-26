package com.camlait.global.erp.delegate.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.produit.CategorieProduitDao;
import com.camlait.global.erp.dao.produit.ProduitDao;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.domain.produit.Produit;

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
    public Produit addProduct(final Produit product) throws DataStorageException {
        return produitDao.save(product);
    }

    @Override
    public Produit updateProduct(final Produit product) throws DataStorageException {
        final Produit p = produitDao.findOne(product.getProduitId());
        return produitDao.saveAndFlush(product.merge(p));
    }

    @Override
    public Produit retrieveProduct(final String productId) throws DataStorageException {
        final Produit p = produitDao.findOne(productId);
        if (p == null) {
            throw new DataStorageException("The product you are trying to retrieve does not exist.");
        }
        return p.lazyInit();
    }

    @Override
    public Boolean removeProduct(final String productId) throws DataStorageException {
        final Produit p = retrieveProduct(productId);
        produitDao.delete(p);
        return true;
    }

    @Override
    public Page<Produit> retriveProducts(final String keyWord, Pageable p) throws DataStorageException {
        return produitDao.retriveProducts(keyWord, p);
    }

    @Override
    public CategorieProduit addProductCategory(final CategorieProduit productCategory) throws DataStorageException {
        return categorieDao.save(productCategory);
    }

    @Override
    public CategorieProduit updateProductCategory(final CategorieProduit productCategory) throws DataStorageException {
        final CategorieProduit c = retrieveProductCategory(productCategory.getCategorieProduitId());
        return categorieDao.saveAndFlush(productCategory.merge(c));
    }

    @Override
    public CategorieProduit retrieveProductCategory(final String productCategoryId) throws DataStorageException {
        final CategorieProduit c = categorieDao.findOne(productCategoryId);
        if (c == null) {
            throw new DataStorageException("The product category that you are trying to retrieve does not exist.");
        }
        return c.lazyInit();
    }

    @Override
    public Boolean removeProductCategory(final String productCategoryId) throws DataStorageException {
        final CategorieProduit c = retrieveProductCategory(productCategoryId);
        categorieDao.delete(c);
        return true;
    }

    @Override
    public Page<CategorieProduit> retriveProductCategories(final String keyWord, Pageable p) throws DataStorageException {
        return categorieDao.retriveProductCategories(keyWord, p);
    }

}
