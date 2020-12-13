

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