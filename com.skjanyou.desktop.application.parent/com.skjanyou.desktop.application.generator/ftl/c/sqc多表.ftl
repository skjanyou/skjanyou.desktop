/*****************************************************************************
                           

 PROGRAM NAME: db_${tablename}.sqc
 DESCRIPTIONS: cnt.sql对应的标准SQL API
 AUTHOR      :      
 CREATE DATE :2016-09-06
****************************************************************************/

#include "pbl/pbl_global.h"
#include "db/sql/db_cnt.h"

EXEC SQL include sqlca;



/*****************************************************************************

Function for SQL ${tablename} Begin 
DESC:   
*****************************************************************************/

typedef struct
{
	struct {
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
	} out;
	
	struct {
	<#list inList as map>
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
	} in;
} HT_${tablename};

void db_${tablename}_init(T_${tablename} *pt)
{

	memset(pt, 0,sizeof(T_${tablename}));

}
void db_${tablename}_log(int iDBFunCode, const T_${tablename} *pt_${tablename})
{
	FILE *fp;
	if ((fp = pbl_openDBLogFile()) != NULL)
	{
		<#list outList as map>
		<#if map.columnRawType == "char">
		db_recordLogC(fp, "pt_${tablename}->out.c_${map.columnName}",pt_${tablename}->out.c_${map.columnName});
		</#if>
		<#if map.columnRawType == "double">
		db_recordLogC(fp, "pt_${tablename}->out.d_${map.columnName}",pt_${tablename}->out.d_${map.columnName});
		</#if>
		<#if map.columnRawType == "long">
		db_recordLogC(fp, "pt_${tablename}->out.l_${map.columnName}",pt_${tablename}->out.l_${map.columnName});
		</#if>		 
		</#list>
		<#list inList as map>
		<#if map.columnRawType == "char">
		db_recordLogC(fp, "pt_${tablename}->in.c_${map.columnName}",pt_${tablename}->in.c_${map.columnName});
		</#if>
		<#if map.columnRawType == "double">
		db_recordLogC(fp, "pt_${tablename}->in.d_${map.columnName}",pt_${tablename}->in.d_${map.columnName});
		</#if>
		<#if map.columnRawType == "long">
		db_recordLogC(fp, "pt_${tablename}->in.l_${map.columnName}",pt_${tablename}->in.l_${map.columnName});
		</#if>		 
		</#list>
		db_recordLogFin(fp);

	}
}

int db_${tablename}(int iDBFunCode, T_${tablename} *pt_${tablename})
{
	int iSqlcode;
	EXEC SQL BEGIN DECLARE SECTION;
	<#list inList as map>
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
	HT_${tablename} ht_${tablename};
	short    hnInd;
	char cQueryString_${tablename}[504];

	EXEC SQL END DECLARE SECTION;


	memcpy(&ht_${tablename}.in,&pt_${tablename}->in,sizeof(ht_${tablename}.in));

	memset(&ht_${tablename}.out,0,sizeof(ht_${tablename}.out));

	switch(iDBFunCode)
	{
	case PBL_DBCUROPEN:
			memset(cQueryString_${tablename}, 0, sizeof(cQueryString_${tablename}));
			strcpy(cQueryString_${tablename},
				"select " 
				"card_no, " 
				"card_seq, " 
				"card_product_id, " 
				"card_valid, " 
				"usage_ind, " 
				"customer_id, " 
				"br_branch_id, " 
				"appl_no, " 
				"customer_id_type, " 
				"customer_id_no, " 
				"assign_user, " 
				"to_char(assign_date,'yyyymmddhh24miss'), " 
				"old_cvv, " 
				"to_char(modi_date,'yyyymmddhh24miss'), " 
				"modi_user, " 
				"version, " 
				"pboc_version " 
				" from acd_cardnumberassign where card_no = :c_card_no and usage_ind = :c_usage_ind "
			);

			EXEC SQL prepare sql_${tablename} from :cQueryString_${tablename};

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}
			db_sqlScriptLog(iDBFunCode, "${tablename}","prepare sql_${tablename} from :",cQueryString_${tablename},1,sqlca.sqlcode,gcDBErrMsg);

			if (sqlca.sqlcode != 0)
			{
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:prepare SQL错误");
				return sqlca.sqlcode;
			}

			EXEC SQL declare ${tablename}_cur cursor for sql_${tablename};
			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}

			db_sqlScriptLog(iDBFunCode, "${tablename}","declare ${tablename}_cur cursor  for sql_${tablename}","",1,sqlca.sqlcode,gcDBErrMsg);

			if (sqlca.sqlcode != 0)
			{
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:declare cursor错误");
				return sqlca.sqlcode;
			}

			EXEC SQL open ${tablename}_cur using
			:ht_${tablename}.in.c_card_no,
			:ht_${tablename}.in.c_usage_ind;

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}

			db_sqlScriptLog(iDBFunCode, "${tablename}","open ${tablename}_cur using","",0,sqlca.sqlcode,gcDBErrMsg);

			db_${tablename}_log(iDBFunCode, pt_${tablename});

			if (sqlca.sqlcode != 0)
			{
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:open cursor错误");
				return sqlca.sqlcode;
			}

			return 0;

	case PBL_DBCURFETCH:
	case PBL_DBCURFETCH_NO_TRIM:

			EXEC SQL fetch ${tablename}_cur into
				:ht_${tablename}.out.c_card_no :hnInd, 
				:ht_${tablename}.out.c_card_seq :hnInd, 
				:ht_${tablename}.out.c_card_product_id :hnInd, 
				:ht_${tablename}.out.c_card_valid :hnInd, 
				:ht_${tablename}.out.c_usage_ind :hnInd, 
				:ht_${tablename}.out.c_customer_id :hnInd, 
				:ht_${tablename}.out.c_br_branch_id :hnInd, 
				:ht_${tablename}.out.c_appl_no :hnInd, 
				:ht_${tablename}.out.c_customer_id_type :hnInd, 
				:ht_${tablename}.out.c_customer_id_no :hnInd, 
				:ht_${tablename}.out.c_assign_user :hnInd, 
				:ht_${tablename}.out.c_assign_date :hnInd, 
				:ht_${tablename}.out.c_old_cvv :hnInd, 
				:ht_${tablename}.out.c_modi_date :hnInd, 
				:ht_${tablename}.out.c_modi_user :hnInd, 
				:ht_${tablename}.out.l_version :hnInd, 
				:ht_${tablename}.out.c_pboc_version :hnInd;

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}

			db_sqlScriptLog(iDBFunCode, "${tablename}","fetch ${tablename}_cur into","",1,sqlca.sqlcode,gcDBErrMsg);
			if ((sqlca.sqlcode != PBL_DBOK) && (sqlca.sqlcode != PBL_DBNOTFOUND))
			{
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:fetch cursor错误");
				iSqlcode = sqlca.sqlcode;
				EXEC SQL close ${tablename}_cur;
				return iSqlcode;
			}

			if (sqlca.sqlcode == PBL_DBOK)
			{
				if(iDBFunCode!=PBL_DBCURFETCH_NO_TRIM)
				{
				pbl_rTrim(ht_${tablename}.out.c_card_no,sizeof(ht_${tablename}.out.c_card_no));
				pbl_rTrim(ht_${tablename}.out.c_card_seq,sizeof(ht_${tablename}.out.c_card_seq));
				pbl_rTrim(ht_${tablename}.out.c_card_product_id,sizeof(ht_${tablename}.out.c_card_product_id));
				pbl_rTrim(ht_${tablename}.out.c_card_valid,sizeof(ht_${tablename}.out.c_card_valid));
				pbl_rTrim(ht_${tablename}.out.c_usage_ind,sizeof(ht_${tablename}.out.c_usage_ind));
				pbl_rTrim(ht_${tablename}.out.c_customer_id,sizeof(ht_${tablename}.out.c_customer_id));
				pbl_rTrim(ht_${tablename}.out.c_br_branch_id,sizeof(ht_${tablename}.out.c_br_branch_id));
				pbl_rTrim(ht_${tablename}.out.c_appl_no,sizeof(ht_${tablename}.out.c_appl_no));
				pbl_rTrim(ht_${tablename}.out.c_customer_id_type,sizeof(ht_${tablename}.out.c_customer_id_type));
				pbl_rTrim(ht_${tablename}.out.c_customer_id_no,sizeof(ht_${tablename}.out.c_customer_id_no));
				pbl_rTrim(ht_${tablename}.out.c_assign_user,sizeof(ht_${tablename}.out.c_assign_user));
				pbl_rTrim(ht_${tablename}.out.c_assign_date,sizeof(ht_${tablename}.out.c_assign_date));
				pbl_rTrim(ht_${tablename}.out.c_old_cvv,sizeof(ht_${tablename}.out.c_old_cvv));
				pbl_rTrim(ht_${tablename}.out.c_modi_date,sizeof(ht_${tablename}.out.c_modi_date));
				pbl_rTrim(ht_${tablename}.out.c_modi_user,sizeof(ht_${tablename}.out.c_modi_user));
				pbl_rTrim(ht_${tablename}.out.c_pboc_version,sizeof(ht_${tablename}.out.c_pboc_version));
				}

				memcpy(&pt_${tablename}->out,&ht_${tablename}.out,sizeof(pt_${tablename}->out));

			}

			db_${tablename}_log(iDBFunCode, pt_${tablename});

			return sqlca.sqlcode;

	case PBL_DBCURCLOSE:

			EXEC SQL close ${tablename}_cur;

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}

			db_sqlScriptLog(iDBFunCode, "${tablename}","close ${tablename}_cur ","",2,sqlca.sqlcode,gcDBErrMsg);
			if (sqlca.sqlcode != 0)
			{
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:close cursor错误");
			}

			return sqlca.sqlcode;

	case PBL_DBFETCHONEROW:
	case PBL_DBFETCHONEROW_NO_TRIM:

			EXEC SQL  
				select 
				card_no, 
				card_seq, 
				card_product_id, 
				card_valid, 
				usage_ind, 
				customer_id, 
				br_branch_id, 
				appl_no, 
				customer_id_type, 
				customer_id_no, 
				assign_user, 
				to_char(assign_date,'yyyymmddhh24miss'), 
				old_cvv, 
				to_char(modi_date,'yyyymmddhh24miss'), 
				modi_user, 
				version, 
				pboc_version 
				 into  
				:ht_${tablename}.out.c_card_no :hnInd, 
				:ht_${tablename}.out.c_card_seq :hnInd, 
				:ht_${tablename}.out.c_card_product_id :hnInd, 
				:ht_${tablename}.out.c_card_valid :hnInd, 
				:ht_${tablename}.out.c_usage_ind :hnInd, 
				:ht_${tablename}.out.c_customer_id :hnInd, 
				:ht_${tablename}.out.c_br_branch_id :hnInd, 
				:ht_${tablename}.out.c_appl_no :hnInd, 
				:ht_${tablename}.out.c_customer_id_type :hnInd, 
				:ht_${tablename}.out.c_customer_id_no :hnInd, 
				:ht_${tablename}.out.c_assign_user :hnInd, 
				:ht_${tablename}.out.c_assign_date :hnInd, 
				:ht_${tablename}.out.c_old_cvv :hnInd, 
				:ht_${tablename}.out.c_modi_date :hnInd, 
				:ht_${tablename}.out.c_modi_user :hnInd, 
				:ht_${tablename}.out.l_version :hnInd, 
				:ht_${tablename}.out.c_pboc_version :hnInd 
				 from acd_cardnumberassign where card_no = :ht_${tablename}.in.c_card_no and usage_ind = :ht_${tablename}.in.c_usage_ind ;

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}

			db_sqlScriptLog(iDBFunCode, "${tablename}","Fetch 1 row only  ","select card_no, card_seq, card_product_id, card_valid, usage_ind, customer_id, br_branch_id, appl_no, customer_id_type, customer_id_no, assign_user, to_char(assign_date,'yyyymmddhh24miss'), old_cvv, to_char(modi_date,'yyyymmddhh24miss'), modi_user, version, pboc_version into :ht_${tablename}.out.c_card_no :hnInd, :ht_${tablename}.out.c_card_seq :hnInd, :ht_${tablename}.out.c_card_product_id :hnInd, :ht_${tablename}.out.c_card_valid :hnInd, :ht_${tablename}.out.c_usage_ind :hnInd, :ht_${tablename}.out.c_customer_id :hnInd, :ht_${tablename}.out.c_br_branch_id :hnInd, :ht_${tablename}.out.c_appl_no :hnInd, :ht_${tablename}.out.c_customer_id_type :hnInd, :ht_${tablename}.out.c_customer_id_no :hnInd, :ht_${tablename}.out.c_assign_user :hnInd, :ht_${tablename}.out.c_assign_date :hnInd, :ht_${tablename}.out.c_old_cvv :hnInd, :ht_${tablename}.out.c_modi_date :hnInd, :ht_${tablename}.out.c_modi_user :hnInd, :ht_${tablename}.out.l_version :hnInd, :ht_${tablename}.out.c_pboc_version :hnInd from acd_cardnumberassign where card_no = :ht_${tablename}.in.c_card_no and usage_ind = :ht_${tablename}.in.c_usage_ind",1,sqlca.sqlcode,gcDBErrMsg);
			if ((sqlca.sqlcode != PBL_DBOK) && (sqlca.sqlcode != PBL_DBNOTFOUND))
			{
				db_${tablename}_log(iDBFunCode, pt_${tablename});
				PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "db_${tablename}:fetch first 1 row错误");
				return sqlca.sqlcode;
			}

			if (sqlca.sqlcode == PBL_DBOK)
			{
				if(iDBFunCode!=PBL_DBFETCHONEROW_NO_TRIM)
				{
				pbl_rTrim(ht_${tablename}.out.c_card_no,sizeof(ht_${tablename}.out.c_card_no));
				pbl_rTrim(ht_${tablename}.out.c_card_seq,sizeof(ht_${tablename}.out.c_card_seq));
				pbl_rTrim(ht_${tablename}.out.c_card_product_id,sizeof(ht_${tablename}.out.c_card_product_id));
				pbl_rTrim(ht_${tablename}.out.c_card_valid,sizeof(ht_${tablename}.out.c_card_valid));
				pbl_rTrim(ht_${tablename}.out.c_usage_ind,sizeof(ht_${tablename}.out.c_usage_ind));
				pbl_rTrim(ht_${tablename}.out.c_customer_id,sizeof(ht_${tablename}.out.c_customer_id));
				pbl_rTrim(ht_${tablename}.out.c_br_branch_id,sizeof(ht_${tablename}.out.c_br_branch_id));
				pbl_rTrim(ht_${tablename}.out.c_appl_no,sizeof(ht_${tablename}.out.c_appl_no));
				pbl_rTrim(ht_${tablename}.out.c_customer_id_type,sizeof(ht_${tablename}.out.c_customer_id_type));
				pbl_rTrim(ht_${tablename}.out.c_customer_id_no,sizeof(ht_${tablename}.out.c_customer_id_no));
				pbl_rTrim(ht_${tablename}.out.c_assign_user,sizeof(ht_${tablename}.out.c_assign_user));
				pbl_rTrim(ht_${tablename}.out.c_assign_date,sizeof(ht_${tablename}.out.c_assign_date));
				pbl_rTrim(ht_${tablename}.out.c_old_cvv,sizeof(ht_${tablename}.out.c_old_cvv));
				pbl_rTrim(ht_${tablename}.out.c_modi_date,sizeof(ht_${tablename}.out.c_modi_date));
				pbl_rTrim(ht_${tablename}.out.c_modi_user,sizeof(ht_${tablename}.out.c_modi_user));
				pbl_rTrim(ht_${tablename}.out.c_pboc_version,sizeof(ht_${tablename}.out.c_pboc_version));
				}

				memcpy(&pt_${tablename}->out,&ht_${tablename}.out,sizeof(pt_${tablename}->out));

			}

			db_${tablename}_log(iDBFunCode, pt_${tablename});

			return sqlca.sqlcode;

	default:
			PBL_LOG(PBL_LOGERROR, 0, 0,  "db_${tablename}:DBFunctionCode[iDBFunCode]:不支持的数据库操作功能");

			return -1;
	}
}
/*Function for SQL ${tablename} End                       */
