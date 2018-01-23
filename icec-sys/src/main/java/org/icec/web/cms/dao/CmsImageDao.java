package org.icec.web.cms.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;
import org.icec.web.cms.model.CmsImage;
import org.icec.web.sys.model.SysDict;

import java.util.List;

/*
* 
* gen by beetlsql mapper 2017-11-05
*/
public interface CmsImageDao extends BaseMapper<CmsImage> {
	public List<CmsImage> getDictItems(@Param("parentId") Integer parentId);
	public List<CmsImage> getDictItemsByType(@Param("type") String type);
	public List<CmsImage> getDictItemsByTypeValue(@Param("type") String type, @Param("value") String value);
	
	public PageQuery<CmsImage> queryDict(PageQuery<CmsImage> query);
}
