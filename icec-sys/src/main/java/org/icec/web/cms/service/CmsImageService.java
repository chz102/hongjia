package org.icec.web.cms.service;

import org.beetl.sql.core.engine.PageQuery;
import org.icec.web.cms.dao.CmsImageDao;
import org.icec.web.cms.model.CmsImage;
import org.icec.web.sys.model.SysDict;
import org.icec.web.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/*
* 
* gen by icec  2017-11-05
*/
@Service
public class CmsImageService {
	@Autowired
	private CmsImageDao cmsImageDao;
	
	public CmsImage findById(Integer id){
		return cmsImageDao.single(id);
	}
	/**
	*
	*保存
	*/
	@Transactional
	public void save(CmsImage cmsImage,SysUser optUser){
		cmsImage.setCreateBy(optUser.getId());
		cmsImage.setCreateDate(new Date());
		cmsImage.setUpdateBy(optUser.getId());
		cmsImage.setUpdateDate(new Date());
		cmsImageDao.insertTemplate(cmsImage);
	}
	/**
	 * 字典更新
	 * @param cmsImage
	 * @param optuser
	 */
	@Transactional
	public void update(CmsImage cmsImage,SysUser optuser) {
		cmsImage.setUpdateBy(optuser.getId());
		cmsImage.setUpdateDate(new Date());
		 
		cmsImageDao.updateTemplateById(cmsImage);
	}
	
	/**
	 * 字典删除
	 * @param ids
	 * @param optuser
	 */
	@Transactional
	public void deleteAll(String ids,SysUser optuser) {
		String [] idarr=ids.split(",");
		for(String id:idarr) {
			CmsImage cmsImage=new CmsImage();
			cmsImage.setId(Integer.parseInt(id));
			cmsImage.setUpdateBy(optuser.getId());
			cmsImage.setUpdateDate(new Date());
			cmsImage.setDelFlag(SysUser.DEL_FLAG_DELETE);
			cmsImageDao.updateTemplateById(cmsImage);
		}
		 
	}
	
	public PageQuery<CmsImage> queryDict(PageQuery<CmsImage> query) {
		return cmsImageDao.queryDict(query);
	}
	
	
	/**
	 * 查询字典项
	 * @param typeId
	 * @return
	 */
	public List<CmsImage> getImageType(Integer typeId){
		if(typeId==null)return null;
		return cmsImageDao.getDictItems(typeId);
	}
	/**
	 * 查询字典项
	 * @param type
	 * @return
	 */
	public List<CmsImage> getDictByType(String type){
		if(type==null)return null;
		return cmsImageDao.getDictItemsByType(type);
	}
	/**
	 * 获取字典标签值
	 * @param type
	 * @param value
	 * @return
	 */
	public  CmsImage getDictByTypeValue(String type,String value) {
		List<CmsImage> list= cmsImageDao.getDictItemsByTypeValue(type, value);
		 
		return list.size()>0?list.get(0):null;
	}
}
