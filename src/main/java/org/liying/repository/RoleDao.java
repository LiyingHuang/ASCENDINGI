package org.liying.repository;

import org.liying.model.Role;
import org.liying.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleDao {

    Role getRoleByName(String name);
    List<Role> findAllRoles();
    Role save(Role role);
    Role findById(Long Id);
    boolean delete (Role role);

}
