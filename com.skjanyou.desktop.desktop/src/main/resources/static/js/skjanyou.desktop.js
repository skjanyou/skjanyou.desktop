


//调用Java方法
//JAVA(JSON)

//供Java调用的函数,发布订阅订阅模式
var S = {
	//调用Java, 同步 √   异步×（待实现）	
	callJava : function(){
		var key = [].shift.call(arguments);
		var data = [].shift.call(arguments);
		
		if(window.JAVA){
			var req = {
				sys  : {},
				data : {}
			};
			
			req.sys['code'] = key;
			req.sys['browser'] = 'main.browser';
			
			for(var i in data){
				req.data[i] = data[i];
			}
			
			console.log("向Java端发送消息:" + JSON.stringify(req));
			var res = JAVA(JSON.stringify(req));
			console.log("从Java端返回消息:" + res);
		}else{
			console.log("您使用的不是客户端,无法使用部分功能。");
		}
	},
	//发送同步消息
	syncSend: function(){
		var input = [].shift.call(arguments);
		console.log(input);
		$.ajax({
			type:"post",
			url: "/json" + input.sys.code,
			async:false,
			data:JSON.stringify(input.data),
			success: function(json){
				console.log(json);
				input.fn(json);
			}
		});		
	},
	//发送异步消息
	asyncSend: function(){
		var input = [].shift.call(arguments);
		console.log(input);
		$.ajax({
			type:"post",
			url: "/json" + input.sys.code,
			data:JSON.stringify(input.data),
			success: function(json){
				console.log(json);
				input.fn(json);
			}
		});			
	},
	list : [],
	on : function(key,fn){
		if(!this.list[key]){
			this.list[key] = [];
		}
		this.list[key].push(fn);
	},
	remove : function(){
		var key = [].shift.call(arguments);
		delete this.list[key];
	},
	emit : function(){
		var key = [].shift.call(arguments);
		var args = [].shift.call(arguments);
		var fns = this.list[key];
		if(!fns || fns.length === 0){
			return false;
		}
		
		fns.forEach(function(fn) {
			fn.apply(this, arguments);
		});
	}	
};


(function($){
	//win10.js 增强
	Win10.__proto__ = {
		//新增应用菜单
		newMenu : function(){
			var data = arguments[0];
			var img = data.img;
			var title = data.title;
			var action = data.action;
			var onclick = data.onClick;
			
			var shortcut = $("<div></div>").attr({"class":"shortcut skjanyou-menu","onclick": onclick})
										   .data("action",action);
			var shortcut_img = $("<img/>").attr({"class":"icon","src":img});
			var shortcut_title = $("<div></div>").attr({"class":"title"}).text(title);
		
			shortcut.append(shortcut_img)
					.append(shortcut_title);
		
			$("#win10-shortcuts").append(shortcut);			
		},
		//移除应用菜单
		removeMenu : function(){
			var data = arguments[0];
		}
	};
	
	Win10.openUrl = function(base){
		return function(){
			var index = base.apply(this,arguments);
			var $iframe = $("#layui-layer-iframe" + index);
			var title = $iframe.closest('.layui-layer-iframe').find('>.layui-layer-title').text();
			var iframe = $iframe[0].contentWindow;
			iframe.S = S;
			iframe.console = window.console;
			console.log("子页面[" + title + "]被打开");
			return index;
		};
	}(Win10.openUrl);
	
	//
	$('#win10-shortcuts').on('click','.skjanyou-menu',function(){
		//Win10.newMsg('官方交流一群', '欢迎各位大侠加入讨论：<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=4Er0u8i">[点击加入]205546163</a>')
	});
	
	// 1.获取菜单数据
	$.ajax({
		type:"post",
		url:"/menu/menu",
		async:false,
		success: function(json){
			// 2.加载绘制菜单
			$.each(json,function(index,data){
				Win10.newMenu(data);
			});
			
		}
	});
	
	
	//关闭退出时的提示
	document.body.onbeforeunload = function(){}
})(jQuery);


Win10.onReady(function () {
	Win10.setContextMenu('#win10>.desktop',[
	    //	菜单项+点击回调
	    ['进入全屏',function () {
	    	Win10.enableFullScreen()
	    	S.callJava('Max');
	    }], 
	    //  菜单项+点击回调
	    ['退出全屏',function () {
	    	Win10.disableFullScreen()
	    	S.callJava('Normal');
	    }]
	 ]);
});
