package ${package}.entity;

import java.io.Serializable;


/**
 * ${tableName}
 * @author ${author}
 * 
 */
public class ${fixName} implements Serializable {
	private static final long serialVersionUID = "${fixName}".hashCode();
	
	<#list list as x>
	private ${x.columnType} ${x.columnName};//
	</#list>
	
	<#list list as x>
	public ${x.columnType} get${x.columnName?cap_first}() {
		return this.${x.columnName};
	}
	public void set${x.columnName?cap_first}(${x.columnType} ${x.columnName}) {
		this.${x.columnName} = ${x.columnName};
	}
	</#list>

	public ${fixName}() {
	}

	<#if pk?size != 0>
	public ${fixName}(<#list pk as x>${x.columnType} ${x.columnName}<#if x_has_next>, </#if></#list>) {
		<#list pk as x>
		this.${x.columnName} = ${x.columnName};
		</#list>
	}
	</#if>

	<#if list?size != pk?size>
	public ${fixName}(<#list list as x>${x.columnType} ${x.columnName}<#if x_has_next>, </#if></#list>) {
		<#list list as x>
		this.${x.columnName} = ${x.columnName};
		</#list>
	}
	</#if>
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		<#list list as x>
		builder.append("${x.columnName}:").append(${x.columnName}==null ? "" : ${x.columnName}.toString())<#if x_has_next>.append(",")</#if>;
		</#list>
		return builder.toString();
	}

}
