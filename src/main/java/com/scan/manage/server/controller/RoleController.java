package com.scan.manage.server.controller;

import com.google.common.collect.Maps;
import com.scan.manage.server.annotation.LogAnnotation;
import com.scan.manage.server.dao.RoleDao;
import com.scan.manage.server.dto.RoleDto;
import com.scan.manage.server.model.Role;
import com.scan.manage.server.page.PageTableHandler;
import com.scan.manage.server.page.PageTableRequest;
import com.scan.manage.server.page.PageTableResponse;
import com.scan.manage.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色相关接口
 *
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleDao roleDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存角色")
	@PreAuthorize("hasAuthority('sys:role:add')")
	public void saveRole(@RequestBody RoleDto roleDto) {
		roleService.saveRole(roleDto);
	}

	@GetMapping
	@ApiOperation(value = "角色列表")
	@PreAuthorize("hasAuthority('sys:role:query')")
	public PageTableResponse listRoles(PageTableRequest request) {
		return new PageTableHandler(new PageTableHandler.CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return roleDao.count(request.getParams());
			}
		}, new PageTableHandler.ListHandler() {

			@Override
			public List<Role> list(PageTableRequest request) {
				List<Role> list = roleDao.list(request.getParams(), request.getOffset(), request.getLimit());
				return list;
			}
		}).handle(request);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取角色")
	@PreAuthorize("hasAuthority('sys:role:query')")
	public Role get(@PathVariable Long id) {
		return roleDao.getById(id);
	}

	@GetMapping("/all")
	@ApiOperation(value = "所有角色")
	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public List<Role> roles() {
		return roleDao.list(Maps.newHashMap(), null, null);
	}

	@GetMapping(params = "userId")
	@ApiOperation(value = "根据用户id获取拥有的角色")
	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public List<Role> roles(Long userId) {
		return roleDao.listByUserId(userId);
	}

	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除角色")
	@PreAuthorize("hasAuthority('sys:role:del')")
	public void delete(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
}
