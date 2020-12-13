
//定义layui模块目录
layui
.config({
    base: 'static/layui/lay/modules',
    version: new Date().getTime()		
})
.use(['jquery','layer'],function(){
	var layer = layui.layer;
	//jquery插件 通用&&增强
	(function($){
		
		"use strict";
		
		//ajax默认参数
		$.ajaxSetup({
  			global: false,
  			type: "POST",
  			dataType: "json",
			contentType: "application/json"
		});
		
		//元素默认事件添加
		$('body')
		// 1.模态框关闭
		.on('click','[layer-close]',function(){
			var layerid = $(this).closest('.layui-layer').attr('times');
			layer.close(layerid);
		})
		.on('click','[form-submit]',function(){
			var $form = $(this).closest('form');
			var url = $form.attr('url');
			var method = $form.attr('method') || 'post';
			var async = $form.attr('async') || 'true';
			
			$.ajax({
				type: method,
				url: url,
				async: async,
				data: $form.formToJson(),
				success: function(data){
					if(data.code == SUCCESS_CODE){
						layer.close(layer.index);
						layer.msg(data.msg);
					}
				}
			});
		}).on('click','open-layer-1',function(){
			
		});
		
		
		//jquery扩展函数		
		$.fn.extend({
			//将表单内容转为json字符串
			formToJson : function() {
				var formArray = this.serializeArray();
				var dataArray = {};
				$.each(formArray, function() {
					if (dataArray[this.name]) {
						if (!dataArray[this.name].push) {
							dataArray[this.name] = [ dataArray[this.name] ];
						}
						dataArray[this.name].push(this.value || '');
					} else {
						dataArray[this.name] = this.value || '';
					}
				});
				return JSON.stringify(dataArray);
			},
		
		    formToJsonObj : function() {
		        var formArray = this.serializeArray()
		        var dataArray = {};
		        $.each(formArray, function() {
		            if (dataArray[this.name]) {
		                if (!dataArray[this.name].push) {
		                    dataArray[this.name] = [ dataArray[this.name] ];
		                }
		                dataArray[this.name].push(this.value || '');
		            } else {
		                dataArray[this.name] = this.value || '';
		            }
		        });
		        return dataArray;
		    },
		    
		    //快捷填充数据的函数
			fillFormData : function(jsonValue) {
				var obj = this;
				$.each(jsonValue, function(name, ival) {
					var $oinput = obj.find("input[name=" + name + "]");
					if ($oinput.attr("type") == "checkbox") {
						if (ival !== null) {
							var checkboxObj = $("[name=" + name + "]");
							var checkArray = ival.split(";");
							for (var i = 0; i < checkboxObj.length; i++) {
								for (var j = 0; j < checkArray.length; j++) {
									if (checkboxObj[i].value == checkArray[j]) {
										checkboxObj[i].click();
									}
								}
							}
						}
					} else if ($oinput.attr("type") == "radio") {
						$oinput.each(function() {
							var radioObj = $("[name=" + name + "]");
							for (var i = 0; i < radioObj.length; i++) {
								if (radioObj[i].value == ival) {
									radioObj[i].click();
								}
							}
						});
					} else if ($oinput.attr("type") == "textarea") {
						obj.find("[name=" + name + "]").html(ival);
					} else {
						obj.find("[name=" + name + "]").val(ival);
					}
				})
			}	
			
			
			
			
		});
	})(layui.jquery);
});
