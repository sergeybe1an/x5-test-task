package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.business.Cluster;
import com.sbelan.x5testproject.model.business.Group;
import java.util.Set;

public interface GroupService extends CRUDService<Group> {

    Set<Cluster> getGroupClusters(Long groupId);

    Set<Cluster> createGroupClusterRelations(Long groupId, Long clusterId);

    Set<Cluster> deleteClusterFromGroup(Long groupId, Long clusterId);
}
