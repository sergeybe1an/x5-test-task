package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbelan.x5testproject.model.business.Group;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDto {
    private Long id;
    private String name;

    public Group toGroup() {
        Group group = new Group();
        group.setId(id);
        group.setName(name);

        return group;
    }

    public static GroupDto fromGroup(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());

        return groupDto;
    }
}
