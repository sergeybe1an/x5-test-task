package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbelan.x5testproject.model.business.Cluster;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClusterDto {
    private Long id;
    private String name;

    public Cluster toCluster() {
        Cluster cluster = new Cluster();
        cluster.setId(id);
        cluster.setName(name);

        return cluster;
    }

    public static ClusterDto fromCluster(Cluster cluster) {
        ClusterDto clusterDto = new ClusterDto();
        clusterDto.setId(cluster.getId());
        clusterDto.setName(cluster.getName());

        return clusterDto;
    }
}
