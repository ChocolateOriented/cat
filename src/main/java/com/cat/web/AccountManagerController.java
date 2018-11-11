package com.cat.web;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.PageParam;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.RoleDto;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.Role;
import com.cat.service.RoleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by jxli on 2018/9/20.
 */
@RestController
@RequestMapping(value = "/cat/v1/account/manager")
public class AccountManagerController extends BaseController {

  @Autowired
  RoleService roleService;
  @Autowired
  private RequestMappingHandlerMapping requestMappingHandlerMapping;

  /**
   * @return com.cat.module.dto.PageResponse<com.cat.module.entity.Role>
   * @Description 查看权限码与接口映射关系
   */
  @GetMapping("/list_permissions")
  public Results listPermissions() {
    Map<String, List<String>> permissionMapping = new HashMap<>();

    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
    for (Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
      PatternsRequestCondition p = entry.getKey().getPatternsCondition();
      HandlerMethod handlerMethod = entry.getValue();
      for (String url : p.getPatterns()) {

        RequiresPermissions permissionsAnnotation = handlerMethod.getMethodAnnotation
            (RequiresPermissions.class);
        if (permissionsAnnotation != null) {
          for (String permission : permissionsAnnotation.value()) {
            putPermissionMapping(permissionMapping, permission, url);
          }
        } else {
          putPermissionMapping(permissionMapping, "unlimited", url);
        }
      }
    }
    return Results.ok().putData(permissionMapping);
  }

  private void putPermissionMapping(Map<String, List<String>> permissionMapping,
      String permission, String url) {

    List<String> urls = permissionMapping.get(permission);
    if (urls == null) {
      urls = new ArrayList<>();
      permissionMapping.put(permission, urls);
    }
    urls.add(url);
  }


  @RequiresPermissions("role:view")
  @GetMapping("/list_role")
  public PageResponse<Role> listRole(String name, Boolean enabled, PageParam page) {
    return roleService.listRole(name, enabled, page);
  }

  @RequiresPermissions("role:edit")
  @PostMapping("/create_role")
  public BaseResponse createRole(@Validated @RequestBody RoleDto role,
      BindingResult bindingResult) {
    super.validateField(bindingResult);
    roleService.creatRole(role);
    return new BaseResponse();
  }

  @RequiresPermissions("role:edit")
  @PostMapping("/update_role")
  public Results updateRole(@Validated @RequestBody RoleDto role, BindingResult bindingResult) {
    super.validateField(bindingResult);
    if (role.getId() == null) {
      return new Results(ResultConstant.EMPTY_PARAM);
    }
    roleService.updateRole(role);
    return Results.ok();
  }

  @RequiresPermissions("role:edit")
  @PostMapping("/update_role_enabled")
  public Results updateRoleEnabled(@RequestBody Role role) {
    if (role.getId() == null || role.getEnabled() == null) {
      return new Results(ResultConstant.EMPTY_PARAM);
    }
    roleService.updateRoleEnable(role);
    return Results.ok();
  }
}
