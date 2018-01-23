package org.icec.common.beetlsql.ext.gen;

import java.io.IOException;

import org.beetl.core.Template;
import org.beetl.sql.core.db.TableDesc;
import org.beetl.sql.core.kit.GenKit;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.ext.gen.CodeGen;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.SourceGen;
 /**
  * service代码自动生成
  * @author xxjin
  *
  */
public class ServiceCodeGen implements CodeGen {
	String CR = System.getProperty("line.separator");
	String pkg = null;

	public ServiceCodeGen() {

	}

	public ServiceCodeGen(String pkg) {
		this.pkg = pkg;
	}

	public static String serviceTemplate = "";
	static {
		serviceTemplate = GenConfig.getTemplate("/org/icec/common/beetlsql/ext/gen/service.btl");
	}

	@Override
	public void genCode(String entityPkg, String entityClass, TableDesc tableDesc, GenConfig config,
			boolean isDisplay) {
		if(pkg==null){
			pkg = entityPkg;
		}
		Template template = SourceGen.gt.getTemplate(serviceTemplate);
		String serviceClass = entityClass+"Service";
		String daoClassType =entityClass+"Dao";
		String daoClassName =StringKit.toLowerCaseFirstOne(entityClass)+"Dao";
		template.binding("className", serviceClass);
		template.binding("package",pkg);
		template.binding("entityClass", entityClass);
		template.binding("entityClassName", StringKit.toLowerCaseFirstOne(entityClass));
		template.binding("daoClassType", daoClassType);
		template.binding("daoClassName", daoClassName);
		String mapperHead = "import "+entityPkg+"."+entityClass+";"+CR;
		String daopkg=pkg.replace("service", "dao");
		  mapperHead+="import "+daopkg+"."+daoClassType+";"+CR;
		template.binding("imports", mapperHead);
		String mapperCode = template.render();
		if(isDisplay){
			System.out.println();
			System.out.println(mapperCode);
		}else{
			try {
				SourceGen.saveSourceFile(GenKit.getJavaSRCPath(), pkg, serviceClass, mapperCode);
			} catch (IOException e) {
				throw new RuntimeException("service代码生成失败",e);
			}
		}
	}

}
