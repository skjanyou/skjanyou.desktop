
layui.use(['jquery'],function(){
	window.$ = layui.jquery;
	$(function(){
		$("#submit").on('click',function(){
			var data = {
					sys: {
						code : "/cicd/shell/do"
					},
					data: {
						cmd : $("input[name='cmd']").val()
					},
					fn: function(json){
						allPrpos(json);
					}
			};
			
			S.asyncSend(data);
		});
		
		
		function allPrpos(obj) { 
			// 用来保存所有的属性名称和值
			var props = "";
			// 开始遍历
			for(var p in obj){ 
				// 方法
				if(typeof(obj[p])=="function"){ 
					obj[p]();
				}else{ 
					// p 为属性名称，obj[p]为对应属性的值
					props+= p + "=" + obj[p] + "\t";
				} 
			} 
			// 最后显示所有的属性
			var text = $("#output").text();
			$("#output").text(text + "\r\n" + props);
		}
	});
});
