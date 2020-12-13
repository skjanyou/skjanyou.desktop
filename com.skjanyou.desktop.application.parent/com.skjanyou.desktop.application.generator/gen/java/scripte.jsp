





<!-- js脚本部分 -->
<script type="text/javascript">
	$(function() {
		//字典初始化
		var orgList = $.getDict("${ctx}/rest/dict/local/orgid");
		//yes or no
		var yesorno = $.getDict("${ctx}/rest/dict/local/yesorno");		
		//搜索条件,法人代码带All
		$("#main-page select[name='org_id']").selectRender(orgList,true);
		//修改、明细modal不带All
		$(".modal select[name='org_id']").selectRender(orgList,false);

		var oTable = $('#display-table').DataTable({
            ajax: {
                url: "${ctx}/rest/page/remote/pm0005",
                contentType: "application/json"
            },
			columns: [
				{"data": "busi_org_id" },
				{"data": "product_code" },
				{"data": "product_name" },
				{"data": "product_desc" },
				{"data": "pro_status" },
				{"data": "im_region" },
				{"data": "im_term" },
				{"data": "im_rate" },
				{"render": function (data, type, row, meta) {
						var btn = '<div class="row">';
	                    btn = btn + '<div class="col-md-6"><button type="button"  class="btn btn-info btn-sm" onclick="detail(\'' + row.org_id + '\',\'' + row.busi_org_id + '\',\'' + row.product_code + '\')">详情</button></div>';
						btn = btn + '<div class="col-md-6"><button type="button"  class="btn btn-danger btn-sm" onclick="del(\'' + row.org_id + '\',\'' + row.busi_org_id + '\',\'' + row.product_code + '\')">删除</button></div>';
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
				busi_org_id : {},
				product_code : {},
				product_name : {},
				product_desc : {},
				pro_status : {},
				im_term : {},
				im_rate : {},
				im_region_min : {},
				im_region_max : {},
				cr_region_min : {},
				cr_region_max : {},
				qishu : {}
			},
			submitHandler: function(form){
				$(form).ajaxSubmit({
					url: "${ctx}/rest/core/pm0001/"  + $(form).find("select[name='org_id']").val(),
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
				busi_org_id : {},
				product_code : {},
				product_name : {},
				product_desc : {},
				pro_status : {},
				im_term : {},
				im_rate : {},
				im_region_min : {},
				im_region_max : {},
				cr_region_min : {},
				cr_region_max : {},
				qishu : {}
			},
			submitHandler: function(form){
				$(form).ajaxSubmit({
					url: "${ctx}/rest/core/pm0003/" + $(form).find("select[name='org_id']").val(),
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
	
	//查询分期产品明细
	function detail(org_id,busi_org_id,product_code) {
		$.ajax({
			type: "POST",
			url: "${ctx}/rest/core/pm0004/" + org_id,
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify({"org_id" : org_id , "busi_org_id" : busi_org_id , "product_code" : product_code}),
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
	
	//删除分期产品
	function del(org_id,busi_org_id,product_code) {
		$.ajax({
			type: "POST",
			url: "${ctx}/rest/core/pm0002/" + org_id,
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify({"org_id" : org_id , "busi_org_id" : busi_org_id , "product_code" : product_code}),
			success: function(data) {
				$.alertRemoteMessage(data);	
			},
			complete: function(){
				$("#query-btn").click();
			}
		});		
	}		
</script>
