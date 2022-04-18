package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.business.Cluster;
import com.sbelan.x5testproject.model.dto.ClusterDto;
import com.sbelan.x5testproject.service.CRUDService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
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
@RequestMapping(value = "/api/v1/clusters")
@Tag(name = "Cluster controller", description = "Cluster controller with crud operations")
public class ClusterController {

    private CRUDService<Cluster> clusterService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{clusterId}")
    @Operation(summary = "Get cluster by id")
    public ResponseEntity<ClusterDto> getClusterById(@PathVariable Long clusterId) {

        Cluster cluster;
        try {
            cluster = clusterService.findById(clusterId);
        } catch (HttpClientErrorException e) {
            log.error("ClusterController.getClusterById clusterId: {}, error: {}", clusterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ClusterController.getClusterById clusterId: {}, error: {}", clusterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ClusterDto response = ClusterDto.fromCluster(cluster);


        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    @Operation(summary = "Get all clusters")
    public ResponseEntity<List<ClusterDto>> getAllClusters() {

        List<Cluster> clusters;
        try {
            clusters = clusterService.findAll();
        } catch (Exception e) {
            log.error("ClusterController.getAllClusters error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<ClusterDto> response = clusters.stream()
            .filter(Objects::nonNull)
            .map(ClusterDto::fromCluster)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/save")
    @Operation(summary = "Save cluster")
    public ResponseEntity<ClusterDto> create(@Valid @RequestBody Cluster request) {

        ClusterDto response;
        try {
            Cluster product = clusterService.save(request);
            response = ClusterDto.fromCluster(product);
        } catch (Exception e) {
            log.error("ClusterController.save error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{clusterId}")
    @Operation(summary = "Delete cluster")
    public ResponseEntity<Object> delete(@PathVariable Long clusterId) {

        try {
            clusterService.delete(clusterId);
        } catch (HttpClientErrorException e) {
            log.error("ClusterController.delete clusterId: {}, error: {}", clusterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("ClusterController.delete clusterId: {}, error: {}", clusterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
    
    @Autowired
    public void setClusterService(CRUDService<Cluster> clusterService) {
        this.clusterService = clusterService;
    }
}
