
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

<!-- js脚本部分 -->
<script type="text/javascript">
	$(function() {
		//字典初始化
		var orgList = $.getDict("${r'${ctx}'}/rest/dict/local/orgid");
		//yes or no
		var yesorno = $.getDict("${r'${ctx}'}/rest/dict/local/yesorno");		
		//搜索条件,法人代码带All
		$("#main-page select[name='org_id']").selectRender(orgList,true);
		//修改、明细modal不带All
		$(".modal select[name='org_id']").selectRender(orgList,false);

		var oTable = $('#display-table').DataTable({
            ajax: {
                url: "${r'${ctx}'}/rest/page/remote/${query.code}",
                contentType: "application/json"
            },
			columns: [
<#if (query.list.out)??>
	<#list query.list.out?keys as outKey>
				{"data": "${outKey}" },
	</#list>
</#if>				
				{"render": function (data, type, row, meta) {
						var btn = '<div class="row">';
	                    btn = btn + '<div class="col-md-6"><button type="button"  class="btn btn-info btn-sm" onclick="detail(${detailString})">详情</button></div>';
						btn = btn + '<div class="col-md-6"><button type="button"  class="btn btn-danger btn-sm" onclick="del(${deleteString})">删除</button></div>';
						return btn + '</div>';
                	},"width":"12%"
                }
            ]
        });

		$("#query-btn").on("click", function() {
			var condition = {"condition" :  $("#query-form").formToJson()};
		    oTable.settings()[0].ajax.data = condition;
		    oTable.ajax.reload();
		});

		// 显示新增modal
		$("#add-btn").on("click", function() {
			$("#add-modal").modal("show");
		});

		//新增表单校验
		$("#add-form").validate({
			rules: {
<#if (insert.list.in)??>
	<#list insert.list.in?keys as inKey>
		<#if inKey_index == insert.list.in?size - 1>
				${inKey} : {}
		<#else>
				${inKey} : {},
		</#if>
	</#list>
</#if>				
			},
			submitHandler: function(form){
				$(form).ajaxSubmit({
					url: "${r'${ctx}'}/rest/core/${insert.code}/"  + $(form).find("select[name='org_id']").val(),
					success: function(data) {
					},
					complete: function(){
						$("#add-modal").modal("hide");
						$("#query-btn").click();
					}
				});
			}
		});

		//修改表单校验
		$("#detail-form").validate({
			rules: {				//表单校验
<#if (update.list.in)??>
	<#list update.list.in?keys as inKey>
		<#if inKey_index == update.list.in?size - 1>
				${inKey} : {}
		<#else>
				${inKey} : {},
		</#if>
	</#list>
</#if>						
			},
			submitHandler: function(form){
				$(form).ajaxSubmit({
					url: "${r'${ctx}'}/rest/core/${update.code}/" + $(form).find("select[name='org_id']").val(),
					success: function(data) {
					},
					complete: function(){
						$("#detail-modal").modal("hide");
						$("#query-btn").click();
					}
				});
			}
		});
	});
	
	/*
	* $(function(){})外部
	*/	
	
	//${detail.name}
	function detail(${detailParaString}) {
		$.ajax({
			type: "POST",
			url: "${r'${ctx}'}/rest/core/${detail.code}/" + org_id,
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify(${detailJsonString}),
			success: function(data) {
				if (data.sys.erorcd == "0000") {
					$("#detail-form").setForm(data.output);
				} else {
					$.alertError(data.sys.erortx);
				}
			},
			complete: function(){
				$("#detail-modal").modal("show");
			}
		});		
	}
	
	//${delete.name}
	function del(${detailParaString}) {
		$.ajax({
			type: "POST",
			url: "${r'${ctx}'}/rest/core/${delete.code}/" + org_id,
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify(${deleteJsonString}),
			success: function(data) {
				$.alertRemoteMessage(data);	
			},
			complete: function(){
				$("#query-btn").click();
			}
		});		
	}		
</script>
