<#-- 取查询参数部分,按钮部分 -->
<#if (detail.list.in)??>
	<#assign detailString = '' />
	<#list detail.list.in?keys as inKey>
		<#if inKey_index == detail.list.in?size - 1>
			<#assign detailString = detailString + '\\\'\' + row.${inKey} + \'\\\'' />
		<#else>
			<#assign detailString = detailString + '\\\'\' + row.${inKey} + \'\\\',' />
		</#if>
	</#list>
</#if>
${detailString}

<#-- 取查询参数部分,函数参数部分 -->
<#if (detail.list.in)??>
	<#assign detailParaString = '' />
	<#list detail.list.in?keys as inKey>
		<#if inKey_index == detail.list.in?size - 1>
			<#assign detailParaString = detailParaString + '${inKey}' />
		<#else>
			<#assign detailParaString = detailParaString + '${inKey},' />
		</#if>
	</#list>
</#if>
${detailParaString}

<#-- detailJsonString部分 -->
<#if (detail.list.in)??>
	<#assign detailJsonString = '{' />
	<#list detail.list.in?keys as inKey>
		<#if inKey_index == detail.list.in?size - 1>
			<#assign detailJsonString = detailJsonString + '\"${inKey}\"' + ' : ${inKey}}' />
		<#else>
			<#assign detailJsonString = detailJsonString + '\"${inKey}\"' + ' : ${inKey} , ' />
		</#if>
	</#list>
</#if>
${detailJsonString}


<#----------------------------------->
<#--           删除部分                           -->
<#----------------------------------->


<#-- 取删除参数部分,按钮部分 -->
<#if (delete.list.in)??>
	<#assign deleteString = '' />
	<#list delete.list.in?keys as inKey>
		<#if inKey_index == delete.list.in?size - 1>
			<#assign deleteString = deleteString + '\\\'\' + row.${inKey} + \'\\\'' />
		<#else>
			<#assign deleteString = deleteString + '\\\'\' + row.${inKey} + \'\\\',' />
		</#if>
	</#list>
</#if>
${deleteString}

<#-- 取删除参数部分,函数参数部分 -->
<#if (delete.list.in)??>
	<#assign detailParaString = '' />
	<#list delete.list.in?keys as inKey>
		<#if inKey_index == delete.list.in?size - 1>
			<#assign detailParaString = detailParaString + '${inKey}' />
		<#else>
			<#assign detailParaString = detailParaString + '${inKey},' />
		</#if>
	</#list>
</#if>
${detailParaString}

<#-- deleteJsonString部分 -->
<#if (delete.list.in)??>
	<#assign deleteJsonString = '{' />
	<#list delete.list.in?keys as inKey>
		<#if inKey_index == delete.list.in?size - 1>
			<#assign deleteJsonString = deleteJsonString + '\"${inKey}\"' + ' : ${inKey}}' />
		<#else>
			<#assign deleteJsonString = deleteJsonString + '\"${inKey}\"' + ' : ${inKey} , ' />
		</#if>
	</#list>
</#if>
${deleteJsonString}









