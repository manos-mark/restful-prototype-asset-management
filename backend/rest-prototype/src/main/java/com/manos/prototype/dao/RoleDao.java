package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

	public List<Role> getRoles();
	
}
