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