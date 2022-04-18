package com.sbelan.x5testproject.model.business;

import com.sbelan.x5testproject.model.BaseEntity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "group_")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Group extends BaseEntity {

    @NotBlank(message = "Group name can't be null or empty")
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Exclude
    private Set<Product> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "group_cluster",
        joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "cluster_id", referencedColumnName = "id"))
    private Set<Cluster> clusters = new HashSet<>();

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
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
