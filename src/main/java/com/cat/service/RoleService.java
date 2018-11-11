package com.cat.service;

import com.cat.module.dto.PageParam;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.RoleDto;
import com.cat.module.entity.Role;
import com.cat.module.entity.RolePermission;
import com.cat.repository.RolePermissionRepository;
import com.cat.repository.RoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class RoleService extends BaseService {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	RolePermissionRepository permissionRepository;

	public PageResponse<Role> listRole(String name, Boolean enabled, PageParam page) {
		ExampleMatcher matcher = ExampleMatcher.matching().
				withMatcher("name", GenericPropertyMatchers.startsWith());

		Role role = new Role();
		role.setName(name);
		role.setEnabled(enabled);
		Example<Role> roleExample = Example.of(role,matcher);

		Page<Role> result = roleRepository.findAll(roleExample,new
				PageRequest(page.getPageNumForJpa(),page.getPageSize()));
		return new PageResponse(result.getContent(),page.getPageNum(), page.getPageSize(), result.getTotalElements());
	}

	public void creatRole(RoleDto roleDto) {
		Role role = new Role();
		role.setId(null);
		role.setEnabled(true);
		role.setName(roleDto.getName());
		this.saveRoleWithPermisions(role,roleDto.getPermissions());
	}

	private void saveRoleWithPermisions(Role role, List<String> permisions) {
		roleRepository.save(role);
		permissionRepository.delete(role.getPermissions());
		
		if (CollectionUtils.isEmpty(permisions)){
			return;
		}
		List<RolePermission> permissions = permisions.stream().map(permision->(new RolePermission(role
				.getId(), permision))).collect(Collectors.toList());
		permissionRepository.save(permissions);
	}

	public void updateRole(RoleDto roleDto) {
		Role role = roleRepository.getOne(roleDto.getId());
		role.setName(roleDto.getName());
		this.saveRoleWithPermisions(role,roleDto.getPermissions());
	}

	public void updateRoleEnable(Role roleParam) {
		Role role = roleRepository.getOne(roleParam.getId());
		role.setEnabled(roleParam.getEnabled());
		roleRepository.save(role);
	}
}
