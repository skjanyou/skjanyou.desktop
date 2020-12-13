struct
{
<#list outList as map>
	<#if map.columnRawType == "char">
	char     hc_${map.columnName}[${map.columnPrecision}+1];	
	</#if>
	<#if map.columnRawType == "double">
	double   hd_${map.columnName};	
	</#if>
	<#if map.columnRawType == "long">
	long     hl_${map.columnName};	
	</#if>		 
</#list>
} h_${tablename};
