package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.business.Cluster;
import com.sbelan.x5testproject.model.business.Group;
import com.sbelan.x5testproject.model.dto.ClusterDto;
import com.sbelan.x5testproject.model.dto.GroupDto;
import com.sbelan.x5testproject.service.GroupService;
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
@RequestMapping(value = "/api/v1/groups")
@Tag(name = "Group controller", description = "Group controller with crud operations")
public class GroupController {
    
    private GroupService groupService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{groupId}")
    @Operation(summary = "Get group by id")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) {

        Group group;
        try {
            group = groupService.findById(groupId);
        } catch (HttpClientErrorException e) {
            log.error("GroupController.getGroupById groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("GroupController.getGroupById groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        GroupDto response = GroupDto.fromGroup(group);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    @Operation(summary = "Get all groups")
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<Group> groups;
        try {
            groups = groupService.findAll();
        } catch (Exception e) {
            log.error("GroupController.getAllGroups error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<GroupDto> response = groups.stream()
            .filter(Objects::nonNull)
            .map(GroupDto::fromGroup)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/save")
    @Operation(summary = "Save group")
    public ResponseEntity<GroupDto> create(@Valid @RequestBody Group request) {

        GroupDto response;
        try {
            Group product = groupService.save(request);
            response = GroupDto.fromGroup(product);
        } catch (Exception e) {
            log.error("GroupController.save error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group")
    public ResponseEntity<Object> delete(@PathVariable Long groupId) {
        try {
            groupService.delete(groupId);
        } catch (Exception e) {
            log.error("GroupController.delete groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{groupId}/clusters")
    @Operation(summary = "Get product groups")
    public ResponseEntity<Set<ClusterDto>> getGroupClusters(@PathVariable Long groupId) {

        Set<Cluster> clusters;
        try {
            clusters = groupService.getGroupClusters(groupId);
        } catch (HttpClientErrorException e) {
            log.error("GroupController.getGroupClusters groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("GroupController.getGroupClusters groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<ClusterDto> response = clusters.stream()
            .filter(Objects::nonNull)
            .map(ClusterDto::fromCluster)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/{groupId}/clusters/{clusterId}")
    @Operation(summary = "Create relations between group and clusters")
    public ResponseEntity<Set<ClusterDto>> createGroupClusterRelations(@PathVariable Long groupId,
        @PathVariable Long clusterId) {

        Set<Cluster> clusters;
        try {
            clusters = groupService.createGroupClusterRelations(groupId, clusterId);
        } catch (HttpClientErrorException e) {
            log.error("GroupController.createGroupClusterRelations groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("GroupController.createGroupClusterRelations groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<ClusterDto> response = clusters.stream()
            .filter(Objects::nonNull)
            .map(ClusterDto::fromCluster)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{groupId}/clusters/{clusterId}")
    @Operation(summary = "Delete cluster from group by id")
    public ResponseEntity<Set<ClusterDto>> deleteClusterFromGroup(@PathVariable Long groupId,
        @PathVariable Long clusterId) {

        Set<Cluster> clusters;
        try {
            clusters = groupService.deleteClusterFromGroup(groupId, clusterId);
        } catch (HttpClientErrorException e) {
            log.error("GroupController.deleteClusterFromGroup groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("GroupController.deleteClusterFromGroup groupId: {}, error: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Set<ClusterDto> response = clusters.stream()
            .filter(Objects::nonNull)
            .map(ClusterDto::fromCluster)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }
    
    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
