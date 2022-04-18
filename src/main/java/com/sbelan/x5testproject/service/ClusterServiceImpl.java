package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.business.Cluster;
import com.sbelan.x5testproject.repository.ClusterRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
public class ClusterServiceImpl implements CRUDService<Cluster> {

    private ClusterRepository clusterRepository;
    
    @Override
    public Cluster findById(Long clusterId) {
        Cluster cluster = clusterRepository.findById(clusterId)
            .orElseThrow(() -> new HttpClientErrorException(
                HttpStatus.NOT_FOUND, String.format("Cluster %d didn't found!", clusterId)));

        log.info("ClusterServiceImpl.findById - cluster: {} found by id: {}", cluster, clusterId);

        return cluster;
    }

    @Override
    public List<Cluster> findAll() {
        List<Cluster> clusters = clusterRepository.findAll();

        log.info("ClusterServiceImpl.getAll - clusters list size: {}", clusters.size());

        return clusters;
    }

    @Override
    public Cluster save(Cluster cluster) {
        if (cluster.getId() == null) {
            cluster.setCreated(LocalDateTime.now());
        }
        cluster.setUpdated(LocalDateTime.now());
        cluster = clusterRepository.save(cluster);

        log.info("ClusterServiceImpl.create - cluster: {} successfully saved to db", cluster);

        return cluster;
    }

    @Override
    public void delete(Long clusterId) {
        try {
            clusterRepository.deleteById(clusterId);
            log.info("ClusterServiceImpl.create - cluster: {} successfully removed from db", clusterId);
        } catch (EmptyResultDataAccessException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Cluster %d didn't found!", clusterId));
        }
    }
    
    @Autowired
    public void setClusterRepository(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }
}
