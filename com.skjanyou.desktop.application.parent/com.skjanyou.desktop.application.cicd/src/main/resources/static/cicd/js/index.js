

//加载jquery
layui.use(['element','jquery','layer'],function(){
	var element = layui.element;
	var $ = layui.jquery;
	var layer = layui.layer;
	
	
	//填充主界面空白部分
	$(window).on('resize', function () {
	    var $content = $('.layui-body .layui-tab-content');
	    $content.height($(this).height() - 157);
	}).resize();
	
	//获取左侧菜单
	
	var data = {
			sys: {
				code : "/cicd/shell/menu"
			},
			data: {
				
			},
			fn: function(data){
				var tree = $("#menu");
				data.forEach(function(item){
					var $li = $('<li class="layui-nav-item"></li>');
					var $topMenu = $('<a href="javascript:;">' +
									'<i class="' + item.icon + '"></i>' +
									'<cute>' + item.title + '</cute>' + 
									'</a>');
					$li.append($topMenu);
					item.children.forEach(function(subItem){
						var $dl = $('<dl class="layui-nav-child"></dl>');
						var $dd = $('<dd></dd>').data({"url":subItem.url,"id":subItem.id});
						var $a = $('<a href="javascript:;">' +
									'<i class="fa fa-address-book"></i>' +
									'<cute>' + subItem.title + '</cute>' + 
									'</a>');
						$dl.append($dd.append($a));
						$li.append($dl);
					});
					
					tree.append($li);
				});
			}
	};
	
	S.syncSend(data);

	//给左侧菜单栏增加效果,绑定事件
	element.on('nav(menu)', function(elem){
		var url = elem.data('url');
		var id = elem.data('id');
		var $tab = $("#container").find("[lay-id='" + id + "']");
		//禁止打开多个相同tab
		if(!$tab.length){
			var loadingIndex = layer.load();
			$.ajax({
				type:"GET",
				url: url,
				dataType: "text",
				contentType: "html/text",
				success: function(html){
					element.tabAdd('container', {
						title: elem.text(),
						content: html,
						id: id
					});	
					element.tabChange('container',id);
				},
				complete: function(){
					layer.close(loadingIndex);
				}
			});
		}else{
			element.tabChange('container',id);
		}
	});
	element.init();
});
