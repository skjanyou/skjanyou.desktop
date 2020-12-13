<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglib.jsp"%>

<div id="main-page">


	<div class="panel-body">
		<!-- 查询条件部分 -->	
		<div class="panel panel-flat">
			<div class="panel-body">
				<form id="query-form" class="form">
					<div class="row">
						<div class="col-md-4 col-sm-4 col-xs-6 form-group">			
							<div class="input-group col-md-12 col-sm-12 col-xs-12">
								<span class="input-group-addon">业务法人</span>
								<input type="text" class="form-control" name="busi_org_id"></input>
							</div>	
							<div class="col-md-4 col-sm-4 col-xs-6 form-group">
								<span class="input-group-addon">产品代码</span>
								<input type="text" class="form-control" name="product_code"></input>
							</div>		
						</div>
					</div>								
					<div class = "row">
						<div class="col-md-3 col-md-offset-5">
							<button type="button" class="btn btn-success" id="query-btn"><i class="icon-search4 position-left"></i>查询</button>
							<button type="button" class="btn bg-orange" id="add-btn"><i class="icon-plus3 position-left"></i>新增</button>
						</div>
					</div>		
				</form>
			</div>
		</div>
		<!-- 查询结果部分 -->
		<table id="display-table" class="compact table datatable-basic dataTable no-footer table-bordered table-hover" cellspacing="0" cellpadding="0" width="100%">
			<thead>
				<tr>
					<th>业务法人</th>
					<th>产品代码</th>
					<th>产品简称</th>
					<th>产品描述</th>
					<th>产品状态</th>
					<th>分期额度区间</th>
					<th>分期期限</th>
					<th>分期费率</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>		
	</div>
	<!-- 新增模态框-新增分期产品 -->
	<div class="modal fade" id="add-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-lg">
	        <div class="modal-content">
	            <div class="modal-header bg-info">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">新增分期产品</h4>
	            </div>
	          	<form id="add-form" class="form">
		            <div class="modal-body">
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">业务法人</label>
									<input type="text" class="form-control" name="busi_org_id" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品代码</label>
									<input type="text" class="form-control" name="product_code" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品简称</label>
									<input type="text" class="form-control" name="product_name" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品描述</label>
									<input type="text" class="form-control" name="product_desc" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品状态</label>
									<input type="text" class="form-control" name="pro_status" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期期限</label>
									<input type="text" class="form-control" name="im_term" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期费率</label>
									<input type="text" class="form-control" name="im_rate" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期额度最小值</label>
									<input type="text" class="form-control" name="im_region_min" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期额度最大值</label>
									<input type="text" class="form-control" name="im_region_max" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">信用额度最小值</label>
									<input type="text" class="form-control" name="cr_region_min" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">信用额度最大值</label>
									<input type="text" class="form-control" name="cr_region_max" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期期数</label>
									<input type="text" class="form-control" name="qishu" style="width:50%"></input>
								</div>		
							</div>
						</div>								
		            </div>
					<hr/>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="submit" class="btn btn-primary">新增</button>
		            </div>
				</form>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	<!-- 明细模态框-查询分期产品明细  -->
	<div class="modal fade" id="detail-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-lg">
	        <div class="modal-content">
	            <div class="modal-header bg-info">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">查询分期产品明细</h4>
	            </div>
				<form id="detail-form" class="form">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">业务法人</label>
									<input type="text" class="form-control" name="busi_org_id" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品代码</label>
									<input type="text" class="form-control" name="product_code" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品简称</label>
									<input type="text" class="form-control" name="product_name" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品描述</label>
									<input type="text" class="form-control" name="product_desc" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">产品状态</label>
									<input type="text" class="form-control" name="pro_status" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期期限</label>
									<input type="text" class="form-control" name="im_term" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期费率</label>
									<input type="text" class="form-control" name="im_rate" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期额度最小值</label>
									<input type="text" class="form-control" name="im_region_min" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期额度最大值</label>
									<input type="text" class="form-control" name="im_region_max" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">信用额度最小值</label>
									<input type="text" class="form-control" name="cr_region_min" style="width:50%"></input>
								</div>		
							</div>
						</div>								
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">信用额度最大值</label>
									<input type="text" class="form-control" name="cr_region_max" style="width:50%"></input>
								</div>	
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">分期期数</label>
									<input type="text" class="form-control" name="qishu" style="width:50%"></input>
								</div>		
							</div>
						</div>								
					</div>
					<hr/>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary">更新</button>
					</div>
				</form>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>


</div>







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
