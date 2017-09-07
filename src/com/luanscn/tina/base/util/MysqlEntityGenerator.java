package com.luanscn.tina.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//import com.diasit.base.entity.GblGoods;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class MysqlEntityGenerator {

	public final static String SOURCE_PATH = "D:/svn/eclipseSVN/Comet/src";
//	public final static String SOURCE_PATH = PathKit.getWebRootPath().substring(0, PathKit.getWebRootPath().lastIndexOf("\\")) + "/src";
	public final static String PACKAGE_BASE = "com.diasit.base.entity";

	public final static String JDBCURL = "jdbc:mysql://192.168.9.200/weiyaoling?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public final static String USER_ID = "weiyaoling";
	public final static String PASSWORD = "weiyaoling123";
	
//	public final static String JDBCURL = "jdbc:mysql://localhost/v10db?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//	public final static String USER_ID = "v10db";
//	public final static String PASSWORD = "v10db123";

//	public final static String DINNER_JDBCURL = "jdbc:mysql://192.168.9.200/dinner?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//	public final static String DINNER_USER_ID = "dinner";
//	public final static String DINNER_PASSWORD = "dinner123";
	
	//public final static String SOURCE_PATH = "E:/svnWorkspace/shhwecBO/src";
	/*public final static String JDBCURL = "jdbc:mysql://localhost/shhweccms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public final static String USER_ID = "root";
	public final static String PASSWORD = "mysqlroot";*/
	
//	public final static String OA_JDBCURL = "jdbc:mysql://192.168.9.200/pmms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//	public final static String OA_USER_ID = "pmms";
//	public final static String OA_PASSWORD = "pmms123";
//	
//	public final static String DINNER_JDBCURL = "jdbc:mysql://192.168.9.200/dinner?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//	public final static String DINNER_USER_ID = "dinner";
//	public final static String DINNER_PASSWORD = "dinner123";
	
	public final static boolean IS_FIRST_BIG = false;
	
	private static ArrayList<TableInfo> tableInfoList = new ArrayList<TableInfo>();
	
	public static String currentDBName = "main";

	public static void main(String[] args) {
		// 配置C3p0数据库连接池插件
		String jdbcUrl = JDBCURL;
		String userId = USER_ID;
		String passwd = PASSWORD;
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, userId, passwd);
		c3p0Plugin.start();

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.start();

//		buildAllTables(PACKAGE_BASE);
		
		//alterTableColumnType();
		
		buildOneTables(PACKAGE_BASE, "MblApply");
		
//		// OA
//		jdbcUrl = OA_JDBCURL;
//		userId = OA_USER_ID;
//		passwd = OA_PASSWORD;
//		c3p0Plugin = new C3p0Plugin(jdbcUrl, userId, passwd);
//		c3p0Plugin.start();
//
//		// 配置ActiveRecord插件
//		arp = new ActiveRecordPlugin("oa", c3p0Plugin);
//		arp.start();
//
//		currentDBName = "oa";
//		buildAllTables(OA_PACKAGE_BASE);
//		
//		// dinner
//		jdbcUrl = DINNER_JDBCURL;
//		userId = DINNER_USER_ID;
//		passwd = DINNER_PASSWORD;
//		c3p0Plugin = new C3p0Plugin(jdbcUrl, userId, passwd);
//		c3p0Plugin.start();
//
//		// 配置ActiveRecord插件
//		arp = new ActiveRecordPlugin("dinner", c3p0Plugin);
//		arp.start();
//
//		currentDBName = "dinner";
//		buildAllTables(DINNER_PACKAGE_BASE);
	}

	private static void buildAllTables(String packageBase) {
		tableInfoList = new ArrayList<TableInfo>();
		// 获得数据库表清单
		List<Record> tableList = Db.use(currentDBName).find("show tables");
		if (tableList != null) {
			// 如果数据库表不为空，开始创建entity
			int len = tableList.size();
			
			String[] columns = null;
			String tableName = null;
			for (int i = 0; i < len; i++) {
				Record item = tableList.get(i);
				columns = item.getColumnNames();
				// 获得表名
				tableName = item.get(columns[0]);
				// 创建表对应的entity文件
				buildTable(tableName, packageBase);
			}
		}
		
		// 创建表导入类
		buildEntityManager(packageBase);
	}
	
	private static void buildOneTables(String packageBase, String tgTableName) {
		tableInfoList = new ArrayList<TableInfo>();
		// 获得数据库表清单
		List<Record> tableList = Db.use(currentDBName).find("show tables");
		if (tableList != null) {
			// 如果数据库表不为空，开始创建entity
			int len = tableList.size();
			
			String[] columns = null;
			String tableName = null;
			for (int i = 0; i < len; i++) {
				Record item = tableList.get(i);
				columns = item.getColumnNames();
				// 获得表名
				tableName = item.get(columns[0]);
				// 创建表对应的entity文件
				if (tableName.equalsIgnoreCase(tgTableName)){
					buildTable(tableName, packageBase);
				}
				
			}
		}
	}
	
	/**
	 * 创建表导入类
	 */
	protected static void buildEntityManager(String packageBase){
		System.out.println("开始生成EntityManager。");
		String fileName = getEntityFile("EntityManager", packageBase);
		
		File file = new File(fileName);
		try {
			if (file.exists() == true) {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("can not create file [" + fileName + "].");
			e.printStackTrace();
			return;
		}
		
		try{
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter buffer = new OutputStreamWriter(out, "UTF-8");

			String lineSep = System.lineSeparator();
			// package xxx.xxx.xxx;
			buffer.write("package " + packageBase + ";" + lineSep);
			//
			buffer.write(lineSep);
			// import xxxx.xxx.xxx;
			buffer.write("import com.jfinal.plugin.activerecord.ActiveRecordPlugin;" + lineSep);
			
			// public class XXX extends Model<XXXX>{
			writeMethodLog(0, buffer,  "用于导入所有entity的类", null, null);
			buffer.write("public class EntityManager{" + lineSep);
			
			// 导入方法
			writeMethodLog(1, buffer, "导入所有的Entity对象", null, null);
			buffer.write("	public static void addMappings(ActiveRecordPlugin arp){" + lineSep);
			
			int len = tableInfoList.size();
			for(int i = 0; i < len; i++){
				TableInfo table = tableInfoList.get(i);
				String priKey = null;
				for (int j = 0; j < table.columns.length; j++){
					ColumnInfo col = table.columns[j];
					if ("PRI".equals(col.key)){
						if (priKey == null){
							priKey = col.field;
						}else{
							priKey = priKey + "," + col.field;
						}
					}
				}
				String tableName = table.tableName;
				if (priKey != null){
					buffer.write("		arp.addMapping(\"" + tableName + "\", \"" + priKey + "\", " + table.className + ".class);"  + lineSep);
				}else{
					buffer.write("		arp.addMapping(\"" + tableName + "\", " + table.className + ".class);"  + lineSep);
				}
				
			}
			
			buffer.write("	}" + lineSep);

			// }
			buffer.write("}" + lineSep);

			buffer.flush();
			buffer.close();
			out.close();
		}catch(IOException e){
			System.out.println("can not create file [" + fileName + "].");
			e.printStackTrace();
			return;
		}
		
		System.out.println("生成EntityManager已成功。");
	}

	/**
	 * 创建表
	 * @param tableName
	 */
	private static void buildTable(String tableName, String packageBase) {
		List<Record> tableDetail = Db.use(currentDBName).find("DESCRIBE " + tableName);
		// 创建表结构数据对象
		TableInfo table = null;
		// 遍历表结构
		if (tableDetail != null) {
			int len = tableDetail.size();
			table = new TableInfo(len);
			table.tableName = tableName;
			
			System.out.println("开始生成数据库表" + tableName + "。");
			
			for (int i = 0; i < len; i++) {
				Record column = tableDetail.get(i);
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.defaultValue = column.get("Default");
				columnInfo.extra = column.get("Extra");
				columnInfo.key = column.get("Key");
				columnInfo.field = column.get("Field");
				columnInfo.type = column.get("Type");
				columnInfo.canNull = column.get("Null");
				// 保存表信息
				table.columns[i] = columnInfo;
			}
			buildTableFile(table, packageBase);
			// 保存表信息
			tableInfoList.add(table);
			
			System.out.println("生成" + tableName + "已成功。");
		}
	}

	/**
	 * 创建表entity类文件
	 * @param table
	 */
	private static void buildTableFile(TableInfo table, String packageBase) {
		String className = StringUtils.toUpperCaseFirstOne(table.tableName);
		String fileName = getEntityFile(className, packageBase);
		table.className = className;
		
		File file = new File(fileName);
		try {
			if (file.exists() == true) {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("can not create file [" + fileName + "].");
			e.printStackTrace();
			return;
		}

		try {
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter buffer = new OutputStreamWriter(out, "UTF-8");

			String lineSep = System.lineSeparator();
			// package xxx.xxx.xxx;
			buffer.write("package " + packageBase + ";" + lineSep);
			//
			buffer.write(lineSep);
			// import xxxx.xxx.xxx;
			buildImportInfo(buffer, table);
			
			// public class XXX extends Model<XXXX>{
			writeClassLog(buffer, table);
			buffer.write("public class " + className + " extends Model<"
					+ className + ">{" + lineSep);

			// private static final long serialVersionUID =
			writeLog(1, buffer, "设置序列化变量");
			buffer.write("	private static final long serialVersionUID = "
					+ StringUtils.getSerialVersionUID() + ";" + lineSep);

			// public static final XXX me = new XXX();
			writeLog(1, buffer, "创建一个自身对象，确保单例使用");
			buffer.write("	public static final " + className + " me = new "
					+ className + "();" + lineSep);
			
			// 创建列参数
			buildColumnInfo(buffer, table);
			
			// 创建全检索函数
		//	buildListMethod(buffer, table);

			buffer.write("}" + lineSep);

			buffer.flush();
			buffer.close();
			out.close();
		} catch (IOException e) {
			System.out.println("can not create file [" + fileName + "].");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 写一段注释到java文件
	 * @param buffer
	 * @param cmt
	 * @param param
	 * @param returnVal
	 * @throws IOException
	 */
	protected static void writeClassLog(OutputStreamWriter buffer, TableInfo table) throws IOException {
		String lineSep = System.lineSeparator();

		// 写入注释
		buffer.write(lineSep);
		buffer.write("/**" + lineSep);
		buffer.write(" * " + "表[" + table.tableName + "]的Entity类，它的结构如下：<br>" +  lineSep);
		int len = table.columns.length;
		for (int i = 0; i < len; i++){
			ColumnInfo column = table.columns[i];
			buffer.write(" *     " + column.field + "<br>" + lineSep);
			buffer.write(" *         类型＝" + column.type + "; ");
			if (column.key.equals("") == false){
				buffer.write("关键字＝" + column.key + "; ");
			}
			if (column.extra.equals("") == false){
				buffer.write(column.extra + "; ");
			}
			if (column.canNull.equals("") == false){
				buffer.write("nullable＝" + column.canNull + "; ");
			}
			buffer.write("<br>" + lineSep);
		}
		buffer.write(" */" + lineSep);
	}
	
	/**
	 * 写一段注释到java文件
	 * @param lv
	 * @param buffer
	 * @param cmt
	 * @throws IOException
	 */
	protected static void writeLog(int lv, OutputStreamWriter buffer, String cmt) throws IOException{
		String lineSep = System.lineSeparator();
		// 取得头部缩进
		String headStr = "";
		for (int i = 0; i < lv; i++) {
			headStr += "	";
		}
		// 写入注释
		buffer.write(lineSep);
		buffer.write(headStr + "// " + cmt + lineSep);
	}

	/**
	 * 写一段注释到java文件
	 * @param lv
	 * @param buffer
	 * @param cmt
	 * @param param
	 * @param returnVal
	 * @throws IOException
	 */
	protected static void writeMethodLog(int lv, OutputStreamWriter buffer, String cmt,
			String param, String returnVal) throws IOException {
		String lineSep = System.lineSeparator();
		// 取得头部缩进
		String headStr = "";
		for (int i = 0; i < lv; i++) {
			headStr += "	";
		}
		// 写入注释
		buffer.write(lineSep);
		buffer.write(headStr + "/**" + lineSep);
		if (cmt != null){
			buffer.write(headStr + " * " + cmt + lineSep);
		}
		if (param != null){
			buffer.write(headStr + " * @param " + param + lineSep);
		}
		if (returnVal != null){
			buffer.write(headStr + " * @return " + returnVal + lineSep);
		}
		buffer.write(headStr + " */" + lineSep);
	}
	
	/**
	 * 写一段代码到java文件
	 * @param lv
	 * @param buffer
	 * @param cmt
	 * @param param
	 * @param returnVal
	 * @throws IOException
	 */
	protected static void writeLine(int lv, OutputStreamWriter buffer, String cmt) throws IOException {
		String lineSep = System.lineSeparator();
		// 取得头部缩进
		String headStr = "";
		for (int i = 0; i < lv; i++) {
			headStr += "	";
		}
		// 写入代码
		if (cmt != null){
			buffer.write(headStr + cmt + lineSep);
		}
	}

	private static void buildImportInfo(OutputStreamWriter buffer,
			TableInfo table) throws IOException {
		String lineSep = System.lineSeparator();

		// 是否有BigDecimal类型
		boolean hasBigDecimal = false;
		
		int len = table.columns.length;
		for (int i = 0; i < len; i++) {
			ColumnInfo column = table.columns[i];
			if (column.type.indexOf("decimal") == 0){
				hasBigDecimal = true;
			}
		}
		
		buffer.write("import java.util.*;" + lineSep);
		if (hasBigDecimal == true){
			buffer.write("import java.math.*;" + lineSep);
		}
		buffer.write("import com.jfinal.plugin.activerecord.Model;" + lineSep);
		//buffer.write("import com.jfinal.plugin.activerecord.Page;" + lineSep);
		buffer.write("import com.diasit.base.annotation.*;" + lineSep);
		buffer.write("import com.diasit.base.annotation.ItemType.*;" + lineSep);
	}
	
	/*
	private static void buildListMethod(OutputStreamWriter buffer,
			TableInfo table) throws IOException{
		String lineSep = System.lineSeparator();
		
		writeMethodLog(1, buffer, "收集设定数据，创建检索sql的where语句", null, null);
		
		buffer.write("	private void buildWhereSql(StringBuffer sql, Vector<Object> paramVal ){" + lineSep);
		buffer.write("		String[] attrNames = getAttrNames();" + lineSep);
		buffer.write("		if (attrNames.length > 0){" + lineSep);
		buffer.write("			sql.append(\" where \");" + lineSep);
		buffer.write("			int len = attrNames.length;" + lineSep);
		buffer.write("			for (int i = 0; i < len; i++){" + lineSep);
		buffer.write("				if (i != 0){" + lineSep);
		buffer.write("					sql.append(\" and \");" + lineSep);
		buffer.write("				}" + lineSep);
		buffer.write("				String nam = attrNames[i];" + lineSep);
		buffer.write("				Object val = get(nam);" + lineSep);
		buffer.write("				if (val == null){" + lineSep);
		buffer.write("					sql.append(nam + \" is null \");" + lineSep);
		buffer.write("				}else{" + lineSep);
		buffer.write("					sql.append(nam + \" = ? \");" + lineSep);
		buffer.write("					paramVal.add(val);" + lineSep);
		buffer.write("				}" + lineSep);
		buffer.write("			}" + lineSep);
		buffer.write("		}" + lineSep);
		buffer.write("	}" + lineSep);
		
		writeMethodLog(1, buffer, "创建全表检索用函数(带翻页)", null, null);
		
		buffer.write("	public Page<" + table.className + "> list(int pageNum, int pageSize, String columns, String orderBy){" + lineSep);
		buffer.write("		if (columns == null || \"\".equals(columns)){" + lineSep);
		buffer.write("			columns = \"*\";" + lineSep);
		buffer.write("		}" + lineSep);
		buffer.write("		StringBuffer selectSql = new StringBuffer(\"select \" + columns + \" \");" + lineSep);
		buffer.write("		StringBuffer sql = new StringBuffer(\" from " + table.tableName + "\");" + lineSep);
		buffer.write("		Vector<Object> paramVal = new Vector<Object>();" + lineSep);
		buffer.write("		buildWhereSql(sql, paramVal);" + lineSep);
		buffer.write("		if (orderBy != null && \"\".equals(orderBy) == false){" + lineSep);
		buffer.write("			sql.append(\" order by \");" + lineSep);
		buffer.write("			sql.append(orderBy);" + lineSep);
		buffer.write("		}" + lineSep);
		buffer.write("		Page<" + table.className + "> retList = this.paginate(pageNum, pageSize, selectSql.toString(), sql.toString(), paramVal.toArray());" + lineSep);
		buffer.write("		return retList;" + lineSep);
		buffer.write("	}" + lineSep);
		
		writeMethodLog(1, buffer, "创建全表检索用函数", null, null);
		
		buffer.write("	public List<" + table.className + "> list(String columns, String orderBy){" + lineSep);
		buffer.write("		if (columns == null || \"\".equals(columns)){" + lineSep);
		buffer.write("			columns = \"*\";" + lineSep);
		buffer.write("		}" + lineSep);
		buffer.write("		StringBuffer sql = new StringBuffer(\"select \" + columns + \" from " + table.tableName + "\");" + lineSep);
		buffer.write("		Vector<Object> paramVal = new Vector<Object>();" + lineSep);
		buffer.write("		buildWhereSql(sql, paramVal);" + lineSep);
		buffer.write("		if (orderBy != null && \"\".equals(orderBy) == false){" + lineSep);
		buffer.write("			sql.append(\" order by \");" + lineSep);
		buffer.write("			sql.append(orderBy);" + lineSep);
		buffer.write("		}" + lineSep);
		buffer.write("		List<" + table.className + "> retList = this.find(sql.toString(), paramVal.toArray());" + lineSep);
		buffer.write("		return retList;" + lineSep);
		buffer.write("	}" + lineSep);
		
		writeMethodLog(1, buffer, "创建全表检索用函数2", null, null);
		
		buffer.write("	public List<" + table.className + "> list(){" + lineSep);
		buffer.write("		return list(null, null);" + lineSep);
		buffer.write("	}" + lineSep);
	}
	*/
	
	private static void buildColumnInfo(OutputStreamWriter buffer,
			TableInfo table) throws IOException{
		String lineSep = System.lineSeparator();
		
		int len = table.columns.length;
		for (int i = 0; i < len; i++){
			ColumnInfo column = table.columns[i];
			// 取得相应的java方法和类型
			String[] typeLst = getJavaType(column.type);
			String typeStr = typeLst[0];
			String getStr = typeLst[1];
			
			String headerField = StringUtils.toUpperCaseFirstOne(column.field);
			// 追加setter方法
			writeMethodLog(1, buffer, "设置[" + column.field + "]的值", column.field, null);
			if ("PRI".equals(column.key)){
				writeLine(1, buffer, "@ItemPrimaryKey");
			}
			if ("auto_increment".equals(column.extra)){
				writeLine(1, buffer, "@ItemAutoIncrement");
			}
			if ("on update CURRENT_TIMESTAMP".equals(column.extra)){
				writeLine(1, buffer, "@ItemAutoUpdate");
			}
			if ("NO".equals(column.canNull)){
				writeLine(1, buffer, "@ItemRequire");
			}
			String[] sizeInfo = getTypeAndSize(column.type);
			writeLine(1, buffer, "@ItemType(ColType." + sizeInfo[0] + ")");
			if (sizeInfo[1] != null){
				if (sizeInfo[2] != null){
					writeLine(1, buffer, "@ItemSize(l=" + sizeInfo[1] + ", d=" + sizeInfo[2] + ")");
				}else{
					writeLine(1, buffer, "@ItemSize(l=" + sizeInfo[1] + ")");
				}
			}
			
			String setType = typeStr;
			//由于java的自动装箱机制导致int类型不能直接赋值给Long包装类型，因此此处将set方法的包装类型统一设置成基本类型
			if(typeStr.equals("Long")){
				if (column.canNull.equals("NO")){
					setType = "long";
				}else{
					setType = "Long";
				}
			}else if(typeLst.equals("Integer")){
				if (column.canNull.equals("NO")){
					setType = "int";
				}else{
					setType = "Integer";
				}
			}
			
			buffer.write("	public void set" + headerField + "(" + setType + " " + column.field + "){" + lineSep);
			buffer.write("		set(\"" + column.field + "\", " + column.field + ");" + lineSep);
			buffer.write("	}" + lineSep);
			// 追加getter方法
			writeMethodLog(1, buffer, "取得[" + column.field + "]的值", column.field, null);
			buffer.write("	public " + typeStr + " get" + headerField + "(){" + lineSep);
			buffer.write("		return " + getStr + "(\"" + column.field + "\");" + lineSep);
			buffer.write("	}" + lineSep);
		}
	}

	private static String getEntityFile(String tableName, String packageBase) {
		String fileName = tableName;
		StringBuilder folderPath = new StringBuilder(SOURCE_PATH);

		String pathSep = "/";
		folderPath.append(pathSep);
		folderPath.append(packageBase.replaceAll("\\.", pathSep));
		folderPath.append(pathSep);
		folderPath.append(fileName);
		folderPath.append(".java");

		return folderPath.toString();
	}
	
	/**
	 * 转换mysql的类型到java类型
	 * @param typeStr
	 * @return
	 */
	public static String[] getJavaType(String typeStr){
		String[] ret = new String[2];
		if (typeStr.indexOf("int") == 0){
			if (typeStr.indexOf("unsigned") != -1){
				ret[0] = "Long";
				ret[1] = "getLong";
			}else{
				ret[0] = "Integer";
				ret[1] = "getInt";
			}
		}else if(typeStr.indexOf("varchar") == 0 
				|| typeStr.indexOf("text") == 0 
				|| typeStr.indexOf("char") == 0
				|| typeStr.indexOf("mediumtext") == 0){
			ret[0] = "String";
			ret[1] = "getStr";
		}else if(typeStr.indexOf("timestamp") == 0 
				|| typeStr.indexOf("datetime") == 0
				|| typeStr.indexOf("date") == 0){
			ret[0] = "Date";
			ret[1] = "getDate";
		}else if(typeStr.indexOf("blob") == 0 || typeStr.indexOf("bit") == 0 ||  typeStr.indexOf("longblob") == 0){
			ret[0] = "byte[]";
			ret[1] = "getBytes";
		}else if(typeStr.indexOf("decimal") == 0){
			ret[0] = "BigDecimal";
			ret[1] = "getBigDecimal";
		}else if(typeStr.indexOf("tinyint") == 0 || typeStr.indexOf("smallint") == 0){
			ret[0] = "Integer";
			ret[1] = "getInt";
		}else if(typeStr.indexOf("mediumint") == 0){
			ret[0] = "Long";
			ret[1] = "getLong";
		}//java.math.BigInteger
		else if(typeStr.indexOf("bigint") == 0){
			ret[0] = "Long";
			ret[1] = "getLong";
		}
		else if(typeStr.indexOf("double") == 0){
			ret[0] = "double";
			ret[1] = "getDouble";
		}else if(typeStr.indexOf("float") == 0){
			ret[0] = "float";
			ret[1] = "getFloat";
		}else if(typeStr.indexOf("binary") == 0){
			ret[0] = "byte[]";
			ret[1] = "getBytes";
		}else{
			ret[0] = "Object";
			ret[1] = "get";
		}
		return ret;
	}
	
	/**
	 * 修改表类型
	 */
	public static void alterTableColumnType() {
		// 获得数据库表清单
		List<Record> tableList = Db.use(currentDBName).find("show tables");
		if (tableList != null) {
			// 如果数据库表不为空，开始创建entity
			int tblen = tableList.size();
			
			String[] columns = null;
			String tableName = null;
			for (int i = 0; i < tblen; i++) {
				Record item = tableList.get(i);
				columns = item.getColumnNames();
				// 获得表名
				tableName = item.get(columns[0]);
				// 获取表结构信息
				List<Record> tableDetail = Db.use(currentDBName).find("DESCRIBE " + tableName);
				// 遍历表结构
				if (tableDetail != null) {
					int len = tableDetail.size();
					
					for (int j = 0; j < len; j++) {
						Record column = tableDetail.get(j);
						ColumnInfo columnInfo = new ColumnInfo();
						columnInfo.type = column.get("Type");
						
						//如果是以bigint开头的，则修改
						if(columnInfo.type.indexOf("bigint") == 0){
							columnInfo.extra = column.get("Extra");
							columnInfo.key = column.get("Key");
							columnInfo.field = column.get("Field");
							columnInfo.type = column.get("Type");
							columnInfo.canNull = column.get("Null");
							
							StringBuilder sb = new StringBuilder();
							
							sb.append("alter table ").append(tableName).append(" modify ").append(columnInfo.field);
							
							sb.append(" int unsigned ");
							
							if(columnInfo.canNull.equalsIgnoreCase("NO")){
								sb.append(" NOT NULL ");
							}
							
							if(StrKit.notBlank(columnInfo.extra)){
								sb.append(columnInfo.extra);
							}
							
							System.out.println("开始修改表" + tableName + " 的列" + columnInfo.field);
							Db.use(currentDBName).update(sb.toString());
						}						
					}
				}
			}
		}
		System.out.println("执行结束...");
	}
	
	
	/**
	 * 转换mysql的类型到java类型
	 * @param typeStr
	 * @return
	 */
	public static String[] getTypeAndSize(String typeStr){
		String[] ret = new String[3];
		if (typeStr.endsWith(" unsigned")){
			typeStr = typeStr.substring(0, typeStr.length() - " unsigned".length());
		}
		int stIdx = typeStr.indexOf("(");
		int edIdx = typeStr.indexOf(")");
		if (stIdx == -1){
			ret[0] = typeStr.toUpperCase();
			ret[1] = null;
			ret[2] = null;		
		}else{
			ret[0] = typeStr.substring(0, stIdx).toUpperCase();
			String sizeStr = typeStr.substring(stIdx + 1, edIdx);
			int dvIdx = sizeStr.indexOf(",");
			if (dvIdx != -1){
				ret[1] = sizeStr.substring(0, dvIdx);
				ret[2] = sizeStr.substring(dvIdx + 1);
			}else{
				ret[1] = sizeStr;
				ret[2] = null;
			}
		}
		return ret;
	}
}

/**
 * 列情报数据类
 * @author wei
 */
class ColumnInfo {
	public Object defaultValue;

	public String extra;

	public String key;

	public String field;

	public String type;

	public String canNull;
}

/**
 * 表情报数据类
 * @author wei
 */
class TableInfo {

	public TableInfo(int columnLen) {
		columns = new ColumnInfo[columnLen];
	}

	public String tableName;
	
	public String className;

	public ColumnInfo[] columns;
}
