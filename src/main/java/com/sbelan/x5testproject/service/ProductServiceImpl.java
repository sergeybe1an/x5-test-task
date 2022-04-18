package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.business.Group;
import com.sbelan.x5testproject.model.business.Product;
import com.sbelan.x5testproject.repository.GroupRepository;
import com.sbelan.x5testproject.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private GroupRepository groupRepository;

    @Override
    public Product findById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Product %d didn't found!", productId)));

        log.info("ProductServiceImpl.findById - product: {} found by id: {}", product, productId);

        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();

        log.info("ProductServiceImpl.getAll - products list size: {}", products.size());

        return products;
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreated(LocalDateTime.now());
        }
        product.setUpdated(LocalDateTime.now());
        product = productRepository.save(product);

        log.info("ProductServiceImpl.create - product: {} successfully saved to db", product);

        return product;
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);

        log.info("ProductServiceImpl.create - product: {} successfully removed from db", productId);
    }

    @Override
    public Set<Group> getProductGroups(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Product %d didn't found!", productId)));

        log.info("ProductServiceImpl.getProductGroups get product {} groups {}", product, product.getGroups());

        return product.getGroups();
    }

    @Override
    public Set<Group> createProductGroupRelations(Long productId, Long groupId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Product %d didn't found!", productId)));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        product.getGroups().add(group);
        product = productRepository.save(product);
        log.info("ProductServiceImpl.createProductGroupRelations created relation between product {} and group {}", product, group);
        return product.getGroups();
    }

    @Override
    public Set<Group> deleteGroupFromProduct(Long productId, Long groupId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Product %d didn't found!", productId)));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        product.getGroups().remove(group);
        product = productRepository.save(product);
        log.info("ProductServiceImpl.deleteGroupFromProduct removed relation between product {} and group {}", product, group);
        return product.getGroups();
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
