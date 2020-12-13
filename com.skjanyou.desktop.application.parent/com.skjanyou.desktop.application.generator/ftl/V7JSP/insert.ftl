	<!-- 新增模态框-${insert.name} -->
	<div class="modal fade" id="add-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-lg">
	        <div class="modal-content">
	            <div class="modal-header bg-info">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">${insert.name}</h4>
	            </div>
	          	<form id="add-form" class="form">
		            <div class="modal-body">
<#if (insert.list.in)??>
	<#list insert.list.in?keys as inKey>
		<#if (inKey_index % 2) == 0>
						<div class="row">
							<div class="col-md-12">			
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">${insert.list.in["${inKey}"]}</label>
									<input type="text" class="form-control" name="${inKey}" style="width:50%"></input>
								</div>	
		<#else>
								<div class="col-md-6 form-group">
									<label class="col-md-3 control-label">${insert.list.in["${inKey}"]}</label>
									<input type="text" class="form-control" name="${inKey}" style="width:50%"></input>
								</div>		
							</div>
						</div>								
		</#if>
	</#list>
	<#-- 对于奇数个参数补充标签 -->
	<#if insert.list.in?keys?size % 2 == 1>
							</div>
						</div>		
	</#if>
</#if>	
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