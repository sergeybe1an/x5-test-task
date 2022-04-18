package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.business.Group;
import com.sbelan.x5testproject.model.business.Product;
import com.sbelan.x5testproject.model.dto.GroupDto;
import com.sbelan.x5testproject.model.dto.ProductDto;
import com.sbelan.x5testproject.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products")
@Tag(name = "Product controller", description = "Product controller with crud operations")
public class ProductController {

    private ProductService productService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{productId}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {

        Product product;
        try {
            product = productService.findById(productId);
        } catch (HttpClientErrorException e) {
            log.error("ProductController.getProductById productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ProductController.getProductById productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ProductDto response = ProductDto.fromProduct(product);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<Product> products;
        try {
            products = productService.findAll();
        } catch (Exception e) {
            log.error("ProductController.getAllProducts error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<ProductDto> response = products.stream()
            .filter(Objects::nonNull)
            .map(ProductDto::fromProduct)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/save")
    @Operation(summary = "Save product")
    public ResponseEntity<ProductDto> save(@Valid @RequestBody Product request) {

        ProductDto response;
        try {
            Product product = productService.save(request);
            response = ProductDto.fromProduct(product);
        } catch (Exception e) {
            log.error("ProductController.save error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product")
    public ResponseEntity<Object> delete(@PathVariable Long productId) {

        try {
            productService.delete(productId);
        } catch (Exception e) {
            log.error("ProductController.delete productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{productId}/groups")
    @Operation(summary = "Get product groups")
    public ResponseEntity<Set<GroupDto>> getProductGroups(@PathVariable Long productId) {

        Set<Group> groups;
        try {
            groups = productService.getProductGroups(productId);
        } catch (HttpClientErrorException e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<GroupDto> response = groups.stream()
            .filter(Objects::nonNull)
            .map(GroupDto::fromGroup)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/{productId}/groups/{groupId}")
    @Operation(summary = "Create relations between product and groups")
    public ResponseEntity<Set<GroupDto>> createProductGroupRelations(@PathVariable Long productId,
                                                                     @PathVariable Long groupId) {
        Set<Group> groups;
        try {
            groups = productService.createProductGroupRelations(productId, groupId);
        } catch (HttpClientErrorException e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<GroupDto> response = groups.stream()
            .filter(Objects::nonNull)
            .map(GroupDto::fromGroup)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{productId}/groups/{groupId}")
    @Operation(summary = "Delete group from product by id")
    public ResponseEntity<Set<GroupDto>> deleteGroupFromProduct(@PathVariable Long productId,
                                                                @PathVariable Long groupId) {

        Set<Group> groups;
        try {
            groups = productService.deleteGroupFromProduct(productId, groupId);
        } catch (HttpClientErrorException e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ProductController.getProductGroups productId: {}, error: {}", productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<GroupDto> response = groups.stream()
            .filter(Objects::nonNull)
            .map(GroupDto::fromGroup)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @Autowired
    public void setCrudService(ProductService productService) {
        this.productService = productService;
    }
}
