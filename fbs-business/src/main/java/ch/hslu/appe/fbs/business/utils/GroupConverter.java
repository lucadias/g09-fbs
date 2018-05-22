package ch.hslu.appe.fbs.business.utils;

import ch.hslu.appe.fbs.model.entities.Groups;
import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.remote.dtos.GroupDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for Group entity and DTO.
 *
 * @author Mischa Gruber
 */
public final class GroupConverter {


    /**
     * Converts an Group entity into a DTO.
     * @param group Group to be converted
     * @return converted Group
     */
    public GroupDTO convertToDTO(final Groups group) {
        GroupDTO groupDTO = new GroupDTO(group.getIdGroups());
        groupDTO.setName(group.getName());

        return groupDTO;
    }

    /**
     * Converts a list of group entities into DTOs.
     * @param groupsList list to be converted
     * @return converted list
     */
    public List<GroupDTO> convertToDTO(final List<Groups> groupsList) {
        List<GroupDTO> groupDTOList = new ArrayList<>();
        for (Groups group : groupsList) {
            groupDTOList.add(convertToDTO(group));
        }

        return groupDTOList;
    }

    /**
     * Converts an Group DTO into an entity.
     * @param groupDTO Group to be converted
     * @return converted Group
     */
    public Groups convertToEntity(final GroupDTO groupDTO) {
        Groups groups = new Groups();
        groups.setIdGroups(groupDTO.getId());
        groups.setName(groupDTO.getName());

        return groups;
    }
}
