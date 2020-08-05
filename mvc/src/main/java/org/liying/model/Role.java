package org.liying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "allowed_resource")
    private String allowedResource;   //allowedResource: URIs
    @Column(name = "allowed_create")
    private Boolean allowedCreate;
    @Column(name = "allowed_read")
    private Boolean allowedRead;
    @Column(name = "allowed_update")
    private Boolean allowedUpdate;
    @Column(name = "allowed_delete")
    private Boolean allowedDelete;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles123")
    private Set<User> users;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String  getAllowedResource() {
        return allowedResource;
    }
    public void setAllowedResource(String allowedResource) {
        this.allowedResource = allowedResource;
    }
    public Boolean getAllowedCreate() {
        return allowedCreate;
    }
    public void setAllowedCreate(Boolean allowedCreate) {
        this.allowedCreate = allowedCreate;
    }
    public Boolean getAllowedRead() {
        return allowedRead;
    }
    public void setAllowedRead(Boolean allowedRead) {
        this.allowedRead = allowedRead;
    }
    public Boolean getAllowedUpdate() {
        return allowedUpdate;
    }
    public void setAllowedUpdate(Boolean allowedUpdate) {
        this.allowedUpdate = allowedUpdate;
    }
    public Boolean getAllowedDelete() {
        return allowedDelete;
    }
    public void setAllowedDelete(Boolean allowedDelete) {
        this.allowedDelete = allowedDelete;
    }

    public Set getUsers() {
        return users;
    }
    public void setUsers(Set users) {
        this.users = users;
    }

    public boolean isAllowedCreate() {
        return allowedCreate;
    }
    public boolean isAllowedRead() {
        return allowedRead;
    }
    public boolean isAllowedUpdate() {
        return allowedUpdate;
    }
    public boolean isAllowedDelete() {
        return allowedDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(allowedResource, role.allowedResource) &&
                Objects.equals(allowedCreate, role.allowedCreate) &&
                Objects.equals(allowedRead, role.allowedRead) &&
                Objects.equals(allowedUpdate, role.allowedUpdate) &&
                Objects.equals(allowedDelete, role.allowedDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allowedResource, allowedCreate, allowedRead, allowedUpdate, allowedDelete);
    }
}
