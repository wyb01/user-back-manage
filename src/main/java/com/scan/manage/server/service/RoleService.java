package com.scan.manage.server.service;

import com.scan.manage.server.dto.RoleDto;

public interface RoleService {

	void saveRole(RoleDto roleDto);

	void deleteRole(Long id);
}
