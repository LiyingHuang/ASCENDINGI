package org.liying.repository;

import org.liying.model.Role;
import java.util.List;
import java.util.Set;


public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    Role save(Role role);
    Role getById(Long Id);
    boolean delete (Role role);
    Role update (Role role);
    Set<Role> getRoleByUserName (String UserName);
}
