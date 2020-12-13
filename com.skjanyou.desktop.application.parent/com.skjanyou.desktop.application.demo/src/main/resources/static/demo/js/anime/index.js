$(function() {
	$('#keyword').bind('keypress', function(event) {
		if(event.keyCode == "13") {
			var keyword = $('#keyword').val();
			dingwei();
		}
	});
	
	function dingwei(){
		var c = $(".cmsmsSlideCaptionContainer");
		
		if(c.length == 0){
			return;
		}
		for(var i = 0;i < c.length;i++){
			$(c[i]).stop();
			console.log($(c[i]));
			$(c[i]).css("left",350 * i + 300 + "px");
		}
		console.log(c);
	}

	
	dingwei();
	
});

