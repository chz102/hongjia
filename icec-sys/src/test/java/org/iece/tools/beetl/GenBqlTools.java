package org.iece.tools.beetl;

import java.io.IOException;
import java.util.Properties;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.AbstractDBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.OracleStyle;
import org.beetl.sql.core.db.PostgresStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;
import org.icec.common.beetlsql.ext.gen.ServiceCodeGen;

public class GenBqlTools {
		
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//PropKit.use("a_little_config.txt", "UTF-8");
		Properties pro=new Properties();
		pro.load(GenBqlTools.class.getResourceAsStream("/jdbc.properties"));
		ConnectionSource source = ConnectionSourceHelper.getSimple(
				(String)pro.get("datasource.driver"),
				(String)pro.get("datasource.url"),
				(String)pro.get("datasource.username"),
				(String)pro.get("datasource.password"));
		// 采用Postgres习俗
		AbstractDBStyle style = new MySqlStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("/sql");
		NameConversion nc = new  UnderlinedNameConversion();
		SQLManager sqlManager = new SQLManager(style,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
		GenConfig config = new GenConfig();
		config.codeGens.add(new MapperCodeGen("org.icec.web.sys.dao"));
		config.codeGens.add(new ServiceCodeGen("org.icec.web.sys.service"));
		try {
			sqlManager.genPojoCode("sys_file", "org.icec.web.sys.model",config);
			sqlManager.genSQLFile("sys_file");
			//sqlManager.genSQLTemplateToConsole("sys_office");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
