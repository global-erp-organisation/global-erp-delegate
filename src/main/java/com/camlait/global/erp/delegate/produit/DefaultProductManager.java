package com.camlait.global.erp.delegate.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.product.ProductCategoryRepository;
import com.camlait.global.erp.dao.product.ProductRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.product.Product;
import com.camlait.global.erp.domain.product.ProductCategory;

@Transactional
@Component
public class DefaultProductManager implements ProductManager {

    private final ProductRepository productRepo;
    private final ProductCategoryRepository categoryRepo;

    @Autowired
    public DefaultProductManager(ProductRepository produitRepo, ProductCategoryRepository categorieRepo) {
        this.productRepo = produitRepo;
        this.categoryRepo = categorieRepo;
    }

    @Override
    public Product addProduct(final Product product) throws DataStorageException {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(final Product product) throws DataStorageException {
        final Product p = productRepo.findOne(product.getProductId());
        return productRepo.saveAndFlush(product.merge(p));
    }

    @Override
    public Product retrieveProduct(final String productId) throws DataStorageException {
        final Product p = productRepo.findOne(productId);
        return p == null ? null : p.lazyInit();
    }

    @Override
    public Boolean removeProduct(final String productId) throws DataStorageException {
        final Product p = retrieveProduct(productId);
        if (p == null) {
            return false;
        }
        productRepo.delete(p);
        return true;
    }

    @Override
    public Page<Product> retriveProducts(final String keyWord, Pageable p) throws DataStorageException {
        return productRepo.retriveProducts(keyWord, p);
    }

    @Override
    public ProductCategory addProductCategory(final ProductCategory productCategory) throws DataStorageException {
        return categoryRepo.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(final ProductCategory productCategory) throws DataStorageException {
        final ProductCategory c = retrieveProductCategory(productCategory.getProductcategoryId());
        return categoryRepo.saveAndFlush(productCategory.merge(c));
    }

    @Override
    public ProductCategory retrieveProductCategory(final String productCategoryId) throws DataStorageException {
        final ProductCategory c = categoryRepo.findOne(productCategoryId);
        return c == null ? null : c.lazyInit();
    }

    @Override
    public Boolean removeProductCategory(final String productCategoryId) throws DataStorageException {
        final ProductCategory c = retrieveProductCategory(productCategoryId);
        if (c == null) {
            return false;
        }
        categoryRepo.delete(c);
        return true;
    }

    @Override
    public Page<ProductCategory> retriveProductCategories(final String keyWord, Pageable p) throws DataStorageException {
        return categoryRepo.retriveProductCategories(keyWord, p);
    }
}
