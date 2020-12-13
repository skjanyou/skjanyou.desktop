

	<div class="panel-body">
		<!-- 查询条件部分 -->	
		<div class="panel panel-flat">
			<div class="panel-body">
				<form id="query-form" class="form">
<#if (query.list.in)??>
	<#list query.list.in?keys as inKey>
		<#if (inKey_index % 2) == 0>
					<div class="row">
						<div class="col-md-4 col-sm-4 col-xs-6 form-group">			
							<div class="input-group col-md-12 col-sm-12 col-xs-12">
								<span class="input-group-addon">${query.list.in["${inKey}"]}</span>
								<input type="text" class="form-control" name="${inKey}"></input>
							</div>	
		<#else>
							<div class="col-md-4 col-sm-4 col-xs-6 form-group">
								<span class="input-group-addon">${query.list.in["${inKey}"]}</span>
								<input type="text" class="form-control" name="${inKey}"></input>
							</div>		
						</div>
					</div>								
		</#if>
	</#list>
	<#-- 对于奇数个参数补充标签 -->
	<#if query.list.in?keys?size % 2 == 1>
						</div>
					</div>		
	</#if>
</#if>		
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
<#if (query.list.out)??>
	<#list query.list.out?keys as outKey>
					<th>${query.list.out["${outKey}"]}</th>
	</#list>
</#if>				
					<th>操作</th>
				</tr>
			</thead>
		</table>		
	</div>