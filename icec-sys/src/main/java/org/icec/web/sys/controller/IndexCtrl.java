package org.icec.web.sys.controller;


import java.util.List;

import org.icec.common.exception.IcecException;
import org.icec.common.model.TreeModel;
import org.icec.common.utils.TreeBuild;
import org.icec.web.shiro.annotation.CurrentUser;
import org.icec.web.sys.model.SysMenu;
import org.icec.web.sys.model.SysUser;
import org.icec.web.sys.service.SysMenuService;
import org.icec.web.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexCtrl {
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysMenuService sysMenuService;
	@GetMapping({"/","/index"})
	public String index(@CurrentUser SysUser user,ModelMap model) {
		model.addAttribute("user", user);
		List<SysMenu> menuList= sysMenuService.findMenuByUserId(user.getId());
		 List<TreeModel> tree=TreeBuild.buildTree(menuList);
		 model.addAttribute("menuList", tree);
		return "sys/index";
	}
	@GetMapping({"/home"})
	public String home(@CurrentUser SysUser user,ModelMap model) {
		model.addAttribute("user", user);
		 
		return "sys/home";
	}
	
	@RequestMapping({"/error2"})
	public String error() {
		throw new IcecException(500, "Sam 错误");
	}
}
