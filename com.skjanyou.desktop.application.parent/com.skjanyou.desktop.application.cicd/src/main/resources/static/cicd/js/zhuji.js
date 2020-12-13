

layui.use(['table','layer','jquery'], function(){
  	var table = layui.table;
  	var $ = layui.jquery;
	  
  	table.on('edit(user-table)', function(obj){
		var value = obj.value //得到修改后的值
		,data = obj.data //得到所在行所有键值
		,field = obj.field; //得到字段
		
		console.log(obj);
	});
	
  	table.render({
    	elem: '#userTable',
    	id: 'userReloadTable',		//重载时需要
    	url:  'api/user/list',
    	height: 315,
    	cols: [[
    		{checkbox: true, fixed: true},
      		{field:'userid', width:100, title: '主机名', sort: true},
      		{field:'username', width:100, title: '主机编号',edit: 'text'},
      		{field:'sex', width:100, title: 'IP地址', templet: '#sexTpl', unresize: true},
      		{field:'age', width:80, title: '作用',edit: 'text'},
      		{field:'status',width:120, title: '状态', templet: '#statusTpl', unresize: true},
      		{title:'操作',width:200, fixed: 'right',templet: '#useroperateTpl'}
    	]],
    	page: true
  	});
  	
  	//监听工具条
  	table.on('tool(userTable)', function(obj){
    	var data = obj.data;
    	var userid = data.userid;
    	if(obj.event === 'detail'){
			$.ajax({
				type:"get",
				url:"html/user/user_detail.html",
				dataType: "text",
				contentType: "html/text",			
				success: function(html){
	  				layer.open({
	  					id:'adduserlayer',
	  					title:'查看用户信息',
	    				type: 1,
	    				resize:true,
	    				area:'500px',
	    				content: html ,
	    				success: function(layero, index){

	    				}
	  				});
				}
			});      		
    	} else if(obj.event === 'delete'){
      		layer.confirm('真的删除行么', function(index){
      			$.ajax({
      				type:"get",
      				url:"api/user/delete",
      				data:{"userid":userid},
      				success:function(data){
      					if(data.code == SUCCESS_CODE){
      						obj.del();
      					}
      					layer.msg(data.msg);
      				},
      				complete:function(){
      					layer.close(index);
      				}
      			});
      		});
    	} 
  	});  	  	
	
	//新增用户按钮
	$("#addUserBtn").on('click',function(){
		$.ajax({
			type:"get",
			url:"html/user/user_add.html",
			dataType: "text",
			contentType: "html/text",			
			success: function(html){
  				layer.open({
  					id:'adduserlayer',
  					title:'新增用户',
    				type: 1,
    				area:'500px',
    				content: html ,
    				success: function(layero, index){
						$("#closeBtn").on('click',function(){
							layer.close(index);
						});
    				}
  				});
			}
		});
	});
	
	//批量删除按钮
	$("#batDelUserBtn").on('click',function(){
		var checkStatus = table.checkStatus('userReloadTable');
	    var data = checkStatus.data;
	    $.ajax({
			type: 'get',
			url: 'api/user/batch/delete',
			data: JSON.stringify(data),
			success: function(data){
				if(data.code == 0){
					layer.msg(data.msg);
				}
			}
		});
	});
});
