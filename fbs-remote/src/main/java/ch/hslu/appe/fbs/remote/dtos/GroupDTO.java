package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;

/**
 * DTO of Groups entity
 *
 * @author Mischa Gruber
 */
public class GroupDTO implements Serializable {

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
    public void setName(String name) {
        this.name = name;
    }
}
