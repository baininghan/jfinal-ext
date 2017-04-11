/**
 * 
 */
package com.computech.commons.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

/**
 * 扩展自定义 MetaBuilder,添加指定需要生成的表实例
 * 
 * @author Fancye
 *
 */
public class MetaBuilderExtend extends MetaBuilder {

	/** 指定需要生成的表 */
	protected Set<String> processedTables = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	public MetaBuilderExtend(DataSource dataSource) {
		super(dataSource);
	}

	public void addProcessedTable(String... processedTables) {
		if (processedTables != null) {
			for (String table : processedTables) {
				this.processedTables.add(table);
			}
		}
	}
	
	protected void buildTableNames(List<TableMeta> ret) throws SQLException {
		ResultSet rs = getTablesResultSet();
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			
			if (excludedTables.contains(tableName)) {
				System.out.println("Skip table :" + tableName);
				continue ;
			}
			if (isSkipTable(tableName)) {
				System.out.println("Skip table :" + tableName);
				continue ;
			}
			// 忽略不在指定Set中的表
			if(!processedTables.contains(tableName)) {
				System.out.println("Skip table :" + tableName);
				continue ;
			}
			
			TableMeta tableMeta = new TableMeta();
			tableMeta.name = tableName;
			tableMeta.remarks = rs.getString("REMARKS");
			
			tableMeta.modelName = buildModelName(tableName);
			tableMeta.baseModelName = buildBaseModelName(tableMeta.modelName);
			ret.add(tableMeta);
		}
		rs.close();
	}
}
