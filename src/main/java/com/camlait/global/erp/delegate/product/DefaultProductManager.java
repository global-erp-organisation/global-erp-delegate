package com.camlait.global.erp.delegate.product;

import static com.camlait.global.erp.domain.helper.EntityHelper.batchInit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.util.StringUtils;
import com.camlait.global.erp.dao.product.ProductCategoryRepository;
import com.camlait.global.erp.dao.product.ProductRepository;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.product.Product;
import com.camlait.global.erp.domain.product.ProductCategory;
import com.google.common.collect.Lists;

/**
 * Default implementation of the Product management interface.
 * 
 * @author Martin Blaise Signe.
 */
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
        return productRepo.save(product).lazyInit();
    }

    @Override
    public Product updateProduct(final Product product) throws DataStorageException {
        return productRepo.saveAndFlush(product).lazyInit();
    }

    @Override
    public Product retrieveProduct(final String productId) throws DataStorageException {
        Product p = productRepo.retrieveProduct(productId);
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
    public Page<Product> retrieveProducts(final String keyWord, Pageable p) throws DataStorageException {
        if (StringUtils.isNullOrEmpty(keyWord)) {
            return new PageImpl<>(batchInit(productRepo.findAll()));
        }
        return productRepo.retrieveProducts(keyWord, p);
    }

    @Override
    public ProductCategory addProductCategory(final ProductCategory productCategory) throws DataStorageException {
        return categoryRepo.save(productCategory).lazyInit();
    }

    @Override
    public ProductCategory updateProductCategory(final ProductCategory productCategory) throws DataStorageException {
        return categoryRepo.saveAndFlush(productCategory).lazyInit();
    }

    @Override
    public ProductCategory retrieveProductCategory(final String productCategoryId) throws DataStorageException {
        ProductCategory c = categoryRepo.retrieveProductCategory(productCategoryId);
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
    public Page<ProductCategory> retrieveProductCategories(final String keyWord, Pageable p) throws DataStorageException {
        if (StringUtils.isNullOrEmpty(keyWord)) {
            return new PageImpl<>(batchInit(categoryRepo.findAll()));
        }
        return categoryRepo.retrieveProductCategories(keyWord, p);
    }

    @Override
    public List<Product> retrieveProducts(String keyWord) throws DataStorageException {
        if (StringUtils.isNullOrEmpty(keyWord)) {
            return batchInit(productRepo.findAll());
        }
        return batchInit(productRepo.retrieveProducts(keyWord));
    }

    @Override
    public List<Product> retrieveProductByCategory(String categoryId) throws DataStorageException {
        final ProductCategory c = retrieveProductCategory(categoryId);
        return c == null ? Lists.newArrayList() : retrieveByCategory(Lists.newArrayList(), c);
    }

    @Override
    public List<ProductCategory> retrieveProductCategories(String keyWord) throws DataStorageException {
        if (StringUtils.isNullOrEmpty(keyWord)) {
            return batchInit(categoryRepo.findAll());
        }

        return batchInit(categoryRepo.retrieveProductCategories(keyWord));
    }

    @Override
    public List<ProductCategory> retrieveCategoriesByParent(String parentId) {
        return batchInit(categoryRepo.retrieveCategoriesByParent(parentId));
    }

    /**
     * Recursively retrieve
     * 
     * @param products
     * @param categoryId
     * @return
     */
    private List<Product> retrieveByCategory(List<Product> products, ProductCategory c) {
        if (c.isDetail()) {
            products.addAll(productRepo.retrieveProductByCategory(c.getProductCategoryId()));
        } else {
            if (!c.getCategoryChildren().isEmpty()) {
                c.getCategoryChildren().stream().forEach(cat -> {
                    retrieveByCategory(products, cat);
                });
            }
        }
        return batchInit(products);
    }
}
