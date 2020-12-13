<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.mapper.${fixName}Mapper">
	<!--下面这一句放mybatis-config.xml的typeAliases节点下面
	<typeAlias alias="${fixName}" type="${package}.entity.${fixName}" />
	-->
	<insert id="insert" parameterType="${fixName}">
		insert into ${tableName} (<#list list as x>${x.columnName}<#if x_has_next>, </#if></#list>)
		values (<#list list as x>${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next>, </#if></#list>)
	</insert>

	<select id="detail" parameterType="${fixName}" resultType="${fixName}">
		select <#list list as x>${x.columnName}<#if x_has_next>, </#if></#list>
			from ${tableName}
			where 
			<#if pk?size != 0>
			<#list pk as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
			<#if pk?size == 0>
			<#list list as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
	</select>
	
	<delete id="delete" parameterType="${fixName}">
		delete ${tableName}
			where 
			<#if pk?size != 0>
			<#list pk as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
			<#if pk?size == 0>
			<#list list as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
	</delete>

	<update id="update" parameterType="${fixName}">
		update ${tableName} set
				<#list list as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next>, </#if>
				</#list>
			where
			<#if pk?size != 0>
			<#list pk as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
			<#if pk?size == 0>
			<#list list as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
			</#if>
	</update>
	
	<select id="detailAll" resultType="java.util.List">
		select <#list list as x>${x.columnName}<#if x_has_next>, </#if></#list>
			from ${tableName}
	</select>	

	<select id="query" parameterType="${fixName}" resultType="java.util.List">
		select <#list list as x>${x.columnName}<#if x_has_next>, </#if></#list>
			from ${tableName}
			where 
			<#list list as x>
				${x.columnName} = ${r"#{"}${x.columnName},jdbcType=${x.columnJdbcType}${r"}"}<#if x_has_next> and </#if>
			</#list>
	</select>	
	
</mapper>
