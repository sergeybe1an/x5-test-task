package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.business.Cluster;
import com.sbelan.x5testproject.model.business.Group;
import com.sbelan.x5testproject.repository.ClusterRepository;
import com.sbelan.x5testproject.repository.GroupRepository;
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
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private ClusterRepository clusterRepository;

    @Override
    public Group findById(Long groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(
                HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        log.info("GroupServiceImpl.findById - group: {} found by id: {}", group, groupId);

        return group;
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = groupRepository.findAll();

        log.info("GroupServiceImpl.getAll - groups list size: {}", groups.size());

        return groups;
    }

    @Override
    public Group save(Group group) {
        if (group.getId() == null) {
            group.setCreated(LocalDateTime.now());
        }
        group.setUpdated(LocalDateTime.now());
        group = groupRepository.save(group);

        log.info("GroupServiceImpl.create - group: {} successfully saved to db", group);

        return group;
    }

    @Override
    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);

        log.info("GroupServiceImpl.create - group: {} successfully removed from db", groupId);
    }

    @Override
    public Set<Cluster> getGroupClusters(Long groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        log.info("GroupServiceImpl.getGroupClusters get group {} clusters {}", group, group.getClusters());

        return group.getClusters();
    }

    @Override
    public Set<Cluster> createGroupClusterRelations(Long groupId, Long clusterId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        Cluster cluster = clusterRepository.findById(clusterId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Cluster %d didn't found!", clusterId)));

        group.getClusters().add(cluster);
        group = groupRepository.save(group);
        log.info("GroupServiceImpl.createGroupClusterRelations created relation between group {} and cluster {}", group, cluster);

        return group.getClusters();
    }

    @Override
    public Set<Cluster> deleteClusterFromGroup(Long groupId, Long clusterId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Group %d didn't found!", groupId)));

        Cluster cluster = clusterRepository.findById(clusterId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Cluster %d didn't found!", clusterId)));

        group.getClusters().remove(cluster);
        group = groupRepository.save(group);
        log.info("GroupServiceImpl.deleteClusterFromGroup removed relation between group {} and cluster {}", group, cluster);

        return group.getClusters();
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    
    @Autowired
    public void setClusterRepository(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }
}
