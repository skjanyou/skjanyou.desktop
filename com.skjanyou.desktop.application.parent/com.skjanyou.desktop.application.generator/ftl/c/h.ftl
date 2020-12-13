/************************************************************************
                          SUNCARD                                         
PROGRAM NAME: ${file}.h
DESCRIPTIONS: 定义数据库表${tablename}对应的结构和相关的API
AUTHOR      :                        
CREATE DATE : ${.now}
****************************************************************************/

#ifndef __DB_${tablename? upper_case}_H
#define __DB_${tablename? upper_case}_H

typedef struct
{
	<#list outList as map>
		<#if map.columnRawType == "char">
	char     c_${map.columnName}[${map.columnPrecision}+1];	
		</#if>
		<#if map.columnRawType == "double">
	double   d_${map.columnName};	
		</#if>
		<#if map.columnRawType == "long">
	long     l_${map.columnName};	
		</#if>		 
	</#list>
} T_${tablename};
extern int db_${tablename}(int iDBFunCode, T_${tablename} *pt_${tablename});
extern void db_${tablename}_log(int iDBFunCode, const T_${tablename} *pt_${tablename});
extern void db_${tablename}_init(T_${tablename} *pt_${tablename});
#endif
