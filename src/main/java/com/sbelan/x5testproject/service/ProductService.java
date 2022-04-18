package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.business.Group;
import com.sbelan.x5testproject.model.business.Product;
import java.util.Set;

public interface ProductService extends CRUDService<Product>{

    Set<Group> getProductGroups(Long productId);

    Set<Group> createProductGroupRelations(Long productId, Long groupId);

    Set<Group> deleteGroupFromProduct(Long productId, Long groupId);
}
