package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of Groups entity.
 *
 * @author Mischa Gruber
 */
public final class GroupDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private String name;

    /**
     * Constructor of the GroupDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public GroupDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the client.
     * @return id of the group
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the group.
     * @return name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the group.
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        GroupDTO groupDTO = (GroupDTO) obj;

        return groupDTO.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
