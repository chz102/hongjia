package org.icec.web.cms.controller;

import org.beetl.sql.core.engine.PageQuery;
import org.icec.web.cms.model.CmsImage;
import org.icec.web.cms.service.CmsImageService;
import org.icec.web.shiro.annotation.CurrentUser;
import org.icec.web.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片管理
 * @author jinxx
 *
 */
@Controller
@RequestMapping("cms/image")
public class ImageCtrl {
	@Autowired
	private CmsImageService cmsImageService;
	
	@RequestMapping("add")
	public String add() {
		return "cms/image/add";
	}
	@RequestMapping("save")
	@ResponseBody
	public String save(CmsImage cmsImage, @CurrentUser SysUser currUser) {
		cmsImageService.save(cmsImage, currUser);
		return "1";
	}
	/**
	 * 进入修改界面
	 * @param id
	 * @param model
	 * @return
	 */
	//@RequiresPermissions({"user:edit"})
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable Integer id ,ModelMap model) {
		CmsImage cmsImage = cmsImageService.findById(id);
		model.addAttribute("dict", cmsImage);
		return "cms/image/typeEdit";
	}
	/**
	 * 更新数据逻辑
	 * @param user
	 * @return
	 */
	//@RequiresPermissions({"user:edit"})
	@RequestMapping("update")
	@ResponseBody
	public Integer update(CmsImage cmsImage,@CurrentUser SysUser user) {
		if(cmsImage==null||cmsImage.getId()==null) {
			return 0;
		}
		cmsImageService.update(cmsImage, user);
		return 1;
	}
	@RequestMapping("deleteAll")
	@ResponseBody
	public Integer deleteAll(String ids,@CurrentUser SysUser optuser) {
		if(ids==null) {
			return 0;
		}
		cmsImageService.deleteAll(ids,optuser);
		return 1;
	}
	/**
	 * 进入查询界面
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "cms/image/typeList";
	}
	@RequestMapping("query")
	@ResponseBody
	public PageQuery<CmsImage> query( @RequestParam(defaultValue="1") Integer pageNumber, Integer pageSize, CmsImage cmsImage) {
		PageQuery<CmsImage> query=new PageQuery<CmsImage>();
		query.setPageNumber(pageNumber);
		if(pageSize!=null) {
			query.setPageSize(pageSize);
		}
		query.setParas(cmsImage);
		  query= cmsImageService.queryDict(query);
		   
		return  query;
	}
	
	
	@RequestMapping("getImageType")
	@ResponseBody
	public Map<String,List<CmsImage>> getImageType(Integer imageTypeId){
		Map<String,List<CmsImage>> result=new HashMap<String,List<CmsImage>>();
		result.put("rows", cmsImageService.getImageType(imageTypeId));
		return result;
	}
	
	
	//====================================================//
	//  字典项管理
	//=================================
	@RequestMapping("dictValueAdd")
	public String dictValueAdd(@RequestParam(required=true) Integer dictTypeId,ModelMap model) {
		model.addAttribute("dictTypeId", dictTypeId);
		return "cms/image/imageAdd";
	}
	
	@RequestMapping("dictValueEdit/{id}")
	public String dictValueEdit(@PathVariable Integer id ,ModelMap model) {
		CmsImage cmsImage = cmsImageService.findById(id);
		model.addAttribute("dict", cmsImage);
		return "cms/image/imageEdit";
	}
}
