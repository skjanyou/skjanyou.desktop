package ${package}.mapper;

import ${package}.entity.${fixName};

import java.util.List;


/**
 * 表名: ${tableName}
 * 生成时间: ${.now}
 * @author ${author}
 * 
 */
public interface ${fixName}Mapper {
	/**
	 *  表名:${tableName}</br>
	 *  作用:新增方法</br>
	 *  描述:向${tableName}表中插入一条数据</br>
	 **/
	public void insert(${fixName} ${fixName?uncap_first});
	
	/**
	 * 表名:${tableName}</br>
	 * 作用:基于主键的更新方法</br>
	 * 描述:基于主键对${tableName}表进行更新操作</br>
	 **/
	public void update(${fixName} ${fixName?uncap_first});
	
	/**
	 * 表名:${tableName}</br>
	 * 基于主键的删除方法</br>
	 * 描述:基于主键对${tableName}表进行删除操作</br>
	 **/
	public void delete(${fixName} ${fixName?uncap_first});
	
	/**
	 * 表名:${tableName}</br>
	 * 作用:基于主键的查询方法</br>
	 * 描述:基于主键对${tableName}表进行查询操作</br>
	 * 返回:查询到的结果或者null</br>
	 **/
	public ${fixName} detail(${fixName} ${fixName?uncap_first});
	
	/**
	 * 表名:${tableName}</br>
	 * 作用:查询所有数据的方法</br>
	 * 描述:此方法等价于select * from ${tableName}</br>
	 * 返回:查询到的结果集</br>
	 **/
	public List<${fixName}> detailAll();
	
	/**
	 * 表名:${tableName}</br> 
	 * 作用:全字段属性匹配查询方法</br>
	 * 描述:这个是严格查询方法,要求所有字段的值都要对应相等才能查询到值</br>
	 * 返回:查询到得结果或者null</br>
	 **/
	public ${fixName} query(${fixName} ${fixName?uncap_first});
	
}
