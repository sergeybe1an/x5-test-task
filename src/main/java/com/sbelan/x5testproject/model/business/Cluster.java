package com.sbelan.x5testproject.model.business;

import com.sbelan.x5testproject.model.BaseEntity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "cluster")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Cluster extends BaseEntity {

    @NotBlank(message = "Cluster name can't be null or empty")
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "clusters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Exclude
    private Set<Group> groups = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Cluster cluster = (Cluster) o;
        return Objects.equals(name, cluster.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
