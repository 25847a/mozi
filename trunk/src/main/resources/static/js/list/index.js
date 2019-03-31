	$(function() {
		$("[name=inb_S]").each(function(i) {
			$(this).click(function() {
				$("[name=inb_S]").attr("class", "J-no")
				$(this).attr("class", "J-yes")
			})
		})
		
		
		
		
	})
		/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
	$("#update").click(function(){
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '修改密码',
				maxmin: true,
				area: ['25%', '40%'],
				maxmin: false,//最大化按钮
				content:'/admin/updatePasswordPage',
			})
		});
	});
});///	
	
	
	
	/*全屏模式*/
	function launchFullscreen(element) {
		if(element.requestFullscreen) {
			element.requestFullscreen();
		} else if(element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if(element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen();
		} else if(element.msRequestFullscreen) {
			element.msRequestFullscreen();
		}
	} 
