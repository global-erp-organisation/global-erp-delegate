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
    public Produit addProduct(Produit product) throws DataStorageException {
        return produitDao.save(product);
    }

    @Override
    public Produit updateProduct(Produit product) throws DataStorageException {
        return null;
    }

    @Override
    public Produit retrieveProduct(String productId) throws DataStorageException {
        final Produit p = produitDao.findOne(productId);
        return p == null ? null : p.lazyInit();
    }

    @Override
    public Boolean removeProduct(String productId) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Produit> retriveProducts(String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CategorieProduit addProductCategory(CategorieProduit productCategory) throws DataStorageException {
        return categorieDao.save(productCategory);
    }

    @Override
    public CategorieProduit updateProductCategory(CategorieProduit productCategory) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CategorieProduit retrieveProductCategory(String productCategoryId) throws DataStorageException {
        final CategorieProduit c = categorieDao.findOne(productCategoryId);
        return c == null ? null : c.lazyInit();
    }

    @Override
    public CategorieProduit removeProductCategory(String productCategoryId) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<CategorieProduit> retriveProductCategories(String keyWord, Pageable p) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

}
