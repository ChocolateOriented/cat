package com.cat.web;

import com.cat.module.dto.LoginDto;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.Role;
import com.cat.module.vo.LoginVo;
import com.cat.service.AccountService;
import com.cat.service.RoleService;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jxli on 2018/9/20.
 */
@RestController
@RequestMapping(value = "/cat/v1/account/manager")
public class AccountManagerController extends BaseController {
  @Autowired
  RoleService roleService;

  @RequiresPermissions("role:view")
  @GetMapping("/list_role")
  public PageResponse<Role> Login(@RequestParam("name") String name,@RequestParam("enabled")
      Boolean enabled,){
    PageAble
    Page rolePage = roleService.findPageList(enabled);
  }
}
