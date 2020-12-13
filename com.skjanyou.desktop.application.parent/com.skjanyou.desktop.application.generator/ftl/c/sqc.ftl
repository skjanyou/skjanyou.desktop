/*****************************************************************************
                           

 PROGRAM NAME: db_${tablename}.sqc
 DESCRIPTIONS: 数据库表${tablename}对应的标准SQL API
 AUTHOR      :      
 CREATE DATE : ${.now}
****************************************************************************/

#include "pbl/pbl_global.h"
#include "db/acd/db_${tablename}.h"

EXEC SQL include sqlca;

/******************************************************************************
  FUNC:   int db_${tablename}(int iFuncCode, T_${tablename} *pt_${tablename})                        
  PARAMS: iFuncCode  - 功能代码 (I)                                   
          pt_${tablename} - 表对应的一条记录的数据 (I/O)                        
  RETURN: 0    - 成功                                                  
          其他 - SQLCODE                                               
  DESC:   表${tablename}的标准SQL操作                                            
******************************************************************************/
int db_${tablename}(int iDBFunCode, T_${tablename} *pt_${tablename})
{
    EXEC SQL BEGIN DECLARE SECTION;
    EXEC SQL INCLUDE "db_${tablename}.sqh";
    short hnInd;
    EXEC SQL END DECLARE SECTION;
    memset(&h_${tablename}, 0, sizeof(h_${tablename}));
    memcpy(&h_${tablename},pt_${tablename}, sizeof(h_${tablename}));
    switch (iDBFunCode)
    {
    case PBL_DBSELECT:
    case PBL_DBSELECT_NO_TRIM:
    	<#list inList as map>
		<#if map.columnRawType == "char">
		memcpy(h_${tablename}.hc_${map.columnName}, pt_${tablename}->c_${map.columnName}, sizeof(pt_${tablename}->c_${map.columnName}));
		</#if>
		<#if map.columnRawType == "double">
	    memcpy(h_${tablename}.hd_${map.columnName}, pt_${tablename}->d_${map.columnName}, sizeof(pt_${tablename}->d_${map.columnName}));
		</#if>
		<#if map.columnRawType == "long">
		memcpy(h_${tablename}.hl_${map.columnName}, pt_${tablename}->l_${map.columnName}, sizeof(pt_${tablename}->l_${map.columnName}));
		</#if>		 
		</#list>

        EXEC SQL
            select
            <#list outList as map>
            	<#if map.columnDbRawType != "DATE">
            	   ${map.columnName}<#if map_has_next>,</#if>
            	</#if>
            	<#if map.columnDbRawType == "DATE">
            	   to_char(${map.columnName},'yyyymmddhh24miss')<#if map_has_next>,</#if>
            	</#if>
            </#list>
            into
            <#list outList as map>
            	<#if map.columnRawType == "char">
            	   :h_${tablename}.hc_${map.columnName} :hnInd<#if map_has_next>,</#if>	
				</#if>
				<#if map.columnRawType == "double">
				   :h_${tablename}.hd_${map.columnName} :hnInd<#if map_has_next>,</#if>
				</#if>
				<#if map.columnRawType == "long">
				   :h_${tablename}.hl_${map.columnName} :hnInd<#if map_has_next>,</#if>
				</#if>	
            </#list>
                   
            from   ${tablename}
            <#list inList as map>
            <#if map_index == 0>
 			where  <#else><#rt>
 			and	   </#if><#rt>
 				   <#if map.columnRawType == "char">
 				   <#lt>${map.columnName}=:h_${tablename}.hc_${map.columnName}<#if map_has_next><#else>;</#if>
				   </#if>
            	   <#if map.columnRawType == "double">
				   <#lt>${map.columnName}=:h_${tablename}.hd_${map.columnName}<#if map_has_next><#else>;</#if>
				   </#if>				   
            	   <#if map.columnRawType == "long">
				   <#lt>${map.columnName}=:h_${tablename}.hl_${map.columnName}<#if map_has_next><#else>;</#if>
				   </#if>
            	</#list>
            	

        if (sqlca.sqlcode == 0)
        {
           if(iDBFunCode!=PBL_DBSELECT_NO_TRIM)
           {
            <#list outList as map>
			<#if map.columnRawType == "char">
			pbl_rTrim(h_${tablename}.hc_${map.columnName}, sizeof(h_${tablename}.hc_${map.columnName}));
			</#if>
			<#if map.columnRawType == "double">
			pbl_rTrim(h_${tablename}.hd_${map.columnName}, sizeof(h_${tablename}.hd_${map.columnName}));
			</#if>
			<#if map.columnRawType == "long">
			pbl_rTrim(h_${tablename}.hl_${map.columnName}, sizeof(h_${tablename}.hl_${map.columnName}));
			</#if>		 
			</#list>
           }
            memcpy(pt_${tablename}, &h_${tablename}, sizeof(h_${tablename}));
        }
        break;

    case PBL_DBLOCK:
    case PBL_DBLOCK_NO_TRIM:
        /* copy key value to host variable */
        <#list outList as map>
		<#if map.columnRawType == "char">
		memcpy(h_${tablename}.hc_${map.columnName}, pt_${tablename}->c_${map.columnName}, sizeof(pt_${tablename}->c_${map.columnName}));
		</#if>
		<#if map.columnRawType == "double">
		memcpy(h_${tablename}.hd_${map.columnName}, pt_${tablename}->d_${map.columnName}, sizeof(pt_${tablename}->d_${map.columnName}));
		</#if>
		<#if map.columnRawType == "long">
		memcpy(h_${tablename}.hl_${map.columnName}, pt_${tablename}->l_${map.columnName}, sizeof(pt_${tablename}->l_${map.columnName}));
		</#if>		 
		</#list>
        memcpy(h_${tablename}.hc_card_no, pt_${tablename}->c_card_no, sizeof(pt_${tablename}->c_card_no));
        memcpy(h_${tablename}.hc_card_seq, pt_${tablename}->c_card_seq, sizeof(pt_${tablename}->c_card_seq));

        EXEC SQL
            declare ${tablename}_cur cursor  for
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
            from   ${tablename}
            where  card_no=:h_${tablename}.hc_card_no
            and    card_seq=:h_${tablename}.hc_card_seq
            for update;

        EXEC SQL open ${tablename}_cur;
        if (sqlca.sqlcode != PBL_DBOK)
        {
            break;
        }

        EXEC SQL
            fetch ${tablename}_cur
            into
                   :h_${tablename}.hc_card_no :hnInd,
                   :h_${tablename}.hc_card_seq :hnInd,
                   :h_${tablename}.hc_card_product_id :hnInd,
                   :h_${tablename}.hc_card_valid :hnInd,
                   :h_${tablename}.hc_usage_ind :hnInd,
                   :h_${tablename}.hc_customer_id :hnInd,
                   :h_${tablename}.hc_br_branch_id :hnInd,
                   :h_${tablename}.hc_appl_no :hnInd,
                   :h_${tablename}.hc_customer_id_type :hnInd,
                   :h_${tablename}.hc_customer_id_no :hnInd,
                   :h_${tablename}.hc_assign_user :hnInd,
                   :h_${tablename}.hc_assign_date :hnInd,
                   :h_${tablename}.hc_old_cvv :hnInd,
                   :h_${tablename}.hc_modi_date :hnInd,
                   :h_${tablename}.hc_modi_user :hnInd,
                   :h_${tablename}.hl_version :hnInd,
                   :h_${tablename}.hc_pboc_version :hnInd;

        {
            int iTmpSqlcode = sqlca.sqlcode;

            EXEC SQL close ${tablename}_cur;
            sqlca.sqlcode = iTmpSqlcode;
        }
        if (sqlca.sqlcode == 0)
        {
           if(iDBFunCode!=PBL_DBLOCK_NO_TRIM)
           {
            pbl_rTrim(h_${tablename}.hc_card_no, sizeof(h_${tablename}.hc_card_no));
            pbl_rTrim(h_${tablename}.hc_card_seq, sizeof(h_${tablename}.hc_card_seq));
            pbl_rTrim(h_${tablename}.hc_card_product_id, sizeof(h_${tablename}.hc_card_product_id));
            pbl_rTrim(h_${tablename}.hc_card_valid, sizeof(h_${tablename}.hc_card_valid));
            pbl_rTrim(h_${tablename}.hc_usage_ind, sizeof(h_${tablename}.hc_usage_ind));
            pbl_rTrim(h_${tablename}.hc_customer_id, sizeof(h_${tablename}.hc_customer_id));
            pbl_rTrim(h_${tablename}.hc_br_branch_id, sizeof(h_${tablename}.hc_br_branch_id));
            pbl_rTrim(h_${tablename}.hc_appl_no, sizeof(h_${tablename}.hc_appl_no));
            pbl_rTrim(h_${tablename}.hc_customer_id_type, sizeof(h_${tablename}.hc_customer_id_type));
            pbl_rTrim(h_${tablename}.hc_customer_id_no, sizeof(h_${tablename}.hc_customer_id_no));
            pbl_rTrim(h_${tablename}.hc_assign_user, sizeof(h_${tablename}.hc_assign_user));
            pbl_rTrim(h_${tablename}.hc_assign_date, sizeof(h_${tablename}.hc_assign_date));
            pbl_rTrim(h_${tablename}.hc_old_cvv, sizeof(h_${tablename}.hc_old_cvv));
            pbl_rTrim(h_${tablename}.hc_modi_date, sizeof(h_${tablename}.hc_modi_date));
            pbl_rTrim(h_${tablename}.hc_modi_user, sizeof(h_${tablename}.hc_modi_user));
            pbl_rTrim(h_${tablename}.hc_pboc_version, sizeof(h_${tablename}.hc_pboc_version));
           }
            memcpy(pt_${tablename},&h_${tablename},sizeof(h_${tablename}));
        }
        break;
    case PBL_DBINSERT:
        /* copy all fields to host variable */
        memcpy(&h_${tablename}, pt_${tablename}, sizeof(h_${tablename}));
        /* execute SQL */
        EXEC SQL
            insert into ${tablename}
            values ( 
                  :h_${tablename}.hc_card_no ,
                  :h_${tablename}.hc_card_seq ,
                  :h_${tablename}.hc_card_product_id ,
                  :h_${tablename}.hc_card_valid ,
                  :h_${tablename}.hc_usage_ind ,
                  :h_${tablename}.hc_customer_id ,
                  :h_${tablename}.hc_br_branch_id ,
                  :h_${tablename}.hc_appl_no ,
                  :h_${tablename}.hc_customer_id_type ,
                  :h_${tablename}.hc_customer_id_no ,
                  :h_${tablename}.hc_assign_user ,
                  to_date(:h_${tablename}.hc_assign_date,'yyyymmddhh24miss') ,
                  :h_${tablename}.hc_old_cvv ,
                  to_date(:h_${tablename}.hc_modi_date,'yyyymmddhh24miss') ,
                  :h_${tablename}.hc_modi_user ,
                  :h_${tablename}.hl_version ,
                  :h_${tablename}.hc_pboc_version
				  			   );

        break;
    case PBL_DBUPDATE:
        /* copy all fields to host variable */
        memcpy(&h_${tablename}, pt_${tablename}, sizeof(h_${tablename}));
        /* execute SQL */
        EXEC SQL
            update ${tablename}
            set 
                   card_no=:h_${tablename}.hc_card_no,
                   card_seq=:h_${tablename}.hc_card_seq,
                   card_product_id=:h_${tablename}.hc_card_product_id,
                   card_valid=:h_${tablename}.hc_card_valid,
                   usage_ind=:h_${tablename}.hc_usage_ind,
                   customer_id=:h_${tablename}.hc_customer_id,
                   br_branch_id=:h_${tablename}.hc_br_branch_id,
                   appl_no=:h_${tablename}.hc_appl_no,
                   customer_id_type=:h_${tablename}.hc_customer_id_type,
                   customer_id_no=:h_${tablename}.hc_customer_id_no,
                   assign_user=:h_${tablename}.hc_assign_user,
                   assign_date=to_date(:h_${tablename}.hc_assign_date,'yyyymmddhh24miss'),
                   old_cvv=:h_${tablename}.hc_old_cvv,
                   modi_date=to_date(:h_${tablename}.hc_modi_date,'yyyymmddhh24miss'),
                   modi_user=:h_${tablename}.hc_modi_user,
                   version=:h_${tablename}.hl_version,
                   pboc_version=:h_${tablename}.hc_pboc_version
            where  card_no=:h_${tablename}.hc_card_no
            and    card_seq=:h_${tablename}.hc_card_seq;
        break;
    case PBL_DBDELETE:
        /* copy key value to host variable */
        memcpy(h_${tablename}.hc_card_no, pt_${tablename}->c_card_no, sizeof(pt_${tablename}->c_card_no));
        memcpy(h_${tablename}.hc_card_seq, pt_${tablename}->c_card_seq, sizeof(pt_${tablename}->c_card_seq));
        EXEC SQL
            delete
            from   ${tablename}
            where  card_no=:h_${tablename}.hc_card_no
            and    card_seq=:h_${tablename}.hc_card_seq;
        break;
    case PBL_DBDELETEALL:
        sqlca.sqlcode = 0;
        EXEC SQL
              truncate table ${tablename} ;
        break;
    default:
        PBL_LOG(PBL_LOGERROR, 0, 0, "TABLE[${tablename}]:FUN[%d]:不支持的数据库操作功能", iDBFunCode);
        return -1;
    }
    db_${tablename}_log(iDBFunCode, pt_${tablename});
    if ((sqlca.sqlcode != PBL_DBOK) && (sqlca.sqlcode != PBL_DBNOTFOUND))
    {
        PBL_LOG(PBL_LOGERROR, sqlca.sqlcode, 0, "TABLE[${tablename}]");
    }
    return sqlca.sqlcode;
}

/******************************************************************************
  FUNC:   void db_${tablename}_init(T_${tablename} *pt_${tablename})                                       
  PARAMS: pt_${tablename} - Data of one record (I/O)                                   
  RETURN: none                                                               
  DESC:   Initialize struct                                                  
*****************************************************************************/
void db_${tablename}_init(T_${tablename} *pt_${tablename})
{
    memset(pt_${tablename},0,sizeof(T_${tablename}));
}

/*******************************************************************************
  FUNC:   void db_${tablename}_log(int iDBFunCode, const T_${tablename} *pt_${tablename})                    
  PARAMS: pt_${tablename} - Data of one record (I/O)                                     
  RETURN: none                                                                 
  DESC:   Log data to file                                                     
******************************************************************************/
void db_${tablename}_log(int iDBFunCode, const T_${tablename} *pt_${tablename})
{
    FILE *fp;

			if(sqlca.sqlcode!= PBL_DBOK)
			{
				memcpy(gcDBErrMsg,sqlca.sqlerrm.sqlerrmc,sizeof(gcDBErrMsg));
			}
			else
			{
				gcDBErrMsg[0]=0;
			}
    if ((fp = pbl_openDBLogFile()) != NULL)
    {
        db_recordLogHeader(fp, iDBFunCode, "${tablename}");
        if(sqlca.sqlcode!= PBL_DBOK)
        {
            fprintf(fp,"[%d][%.140s]\n",sqlca.sqlcode,gcDBErrMsg);
        }
        db_recordLogC(fp, "pt_${tablename}->c_card_no", pt_${tablename}->c_card_no);
        db_recordLogC(fp, "pt_${tablename}->c_card_seq", pt_${tablename}->c_card_seq);
        db_recordLogC(fp, "pt_${tablename}->c_card_product_id", pt_${tablename}->c_card_product_id);
        db_recordLogC(fp, "pt_${tablename}->c_card_valid", pt_${tablename}->c_card_valid);
        db_recordLogC(fp, "pt_${tablename}->c_usage_ind", pt_${tablename}->c_usage_ind);
        db_recordLogC(fp, "pt_${tablename}->c_customer_id", pt_${tablename}->c_customer_id);
        db_recordLogC(fp, "pt_${tablename}->c_br_branch_id", pt_${tablename}->c_br_branch_id);
        db_recordLogC(fp, "pt_${tablename}->c_appl_no", pt_${tablename}->c_appl_no);
        db_recordLogC(fp, "pt_${tablename}->c_customer_id_type", pt_${tablename}->c_customer_id_type);
        db_recordLogC(fp, "pt_${tablename}->c_customer_id_no", pt_${tablename}->c_customer_id_no);
        db_recordLogC(fp, "pt_${tablename}->c_assign_user", pt_${tablename}->c_assign_user);
        db_recordLogC(fp, "pt_${tablename}->c_assign_date", pt_${tablename}->c_assign_date);
        db_recordLogC(fp, "pt_${tablename}->c_old_cvv", pt_${tablename}->c_old_cvv);
        db_recordLogC(fp, "pt_${tablename}->c_modi_date", pt_${tablename}->c_modi_date);
        db_recordLogC(fp, "pt_${tablename}->c_modi_user", pt_${tablename}->c_modi_user);
        db_recordLogL(fp, "pt_${tablename}->l_version", pt_${tablename}->l_version);
        db_recordLogC(fp, "pt_${tablename}->c_pboc_version", pt_${tablename}->c_pboc_version);
        db_recordLogFin(fp);
    }
}
