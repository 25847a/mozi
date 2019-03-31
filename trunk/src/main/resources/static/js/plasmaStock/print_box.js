var layer;
$(function(){
	layui.use('layer', function(){
	  layer = layui.layer;
	}); 
	$("#print").click(function(){
		var start=$("#start").val();
		var end = $("#end").val();
		if(start.length<1){
			layer.alert("请输入箱号");
		}else{
			if(end.length<1 ){
				$.post("/common/printBox",{"boxId":start},function(result){
					if(result.code!=-1){
						layer.alert(result.message);
					}
				});
			}else{
				var boxIdStart=start.substring(0,6);
				var boxIdEnd=end.substring(0,6);
				if(boxIdStart!=boxIdEnd){
					layer.alert('请输入相同的类型的血浆');
				}else{
					start= start.substring(6,start.length);
					end = end.substring(6,end.length);
					start = parseInt(start);
					end = parseInt(end);
					for(var i=start;i<=end;i++){
						var time =new Date().getTime();
						while(true){
							var time1 =new Date().getTime();
							if((time1-time)>1500){
								break;
							}
						}
						$.post("/common/printBox",{"boxId":(boxIdStart+pad(i,5))},function(result){
							if(result.code!=-1){
								layer.alert(result.message);
								return;
							}
						});
					}
				}
			}
		}
	});
	
})


function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}