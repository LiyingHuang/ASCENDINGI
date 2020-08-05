package org.liying.service;
import org.liying.model.Role;
import org.liying.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role getRoleByName(String name){
        return roleDao.getRoleByName(name);
    }
    public List<Role> getAllRoles(){
//        List<Role> roleList = roleDao.findAllRoles();
//        return roleList;
        return roleDao.getAllRoles();
    }
    public Role save(Role role){
        return roleDao.save(role);
    }
    public Role getById(Long Id){
        return roleDao.getById(Id);
    }
    public boolean delete (Role role){
        return roleDao.delete(role);
    }
}

