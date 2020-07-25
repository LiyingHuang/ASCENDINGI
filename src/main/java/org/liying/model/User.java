package org.liying.model;

import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "Last_name")
    private String LastName;
    @Column(name = "email")
    private String email;
    //@JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})

    private Set<Role> roles123 = new HashSet<>(); //   ManyToMany: set better then list

    // add (two direction)
    public void addRole(Role role){
        this.roles123.add(role);
        //role.getUsers().add(this);
    }
    // remove (two direction)
    public void removeRole(Role role){
        this.roles123.remove(role);
        //role.getUsers().remove(this);
    }

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password.trim());
    }
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set getRoles123() {
        return roles123;
    }
    public void setRoles123(Set roles123) {
        this.roles123 = roles123;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(secretKey, user.secretKey) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(LastName, user.LastName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, secretKey, firstName, LastName, email);
    }
}
