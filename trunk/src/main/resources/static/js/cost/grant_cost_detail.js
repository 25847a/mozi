$(function(){
	//从url中获取f_cost_grant中的id（参数）
	var objParam = location.search.substring(location.search.indexOf("=")+1,location.search.length);
	$.ajax({
		url:"../costGrant/grantCostDetailData?id="+objParam,
		type:"post",
		dataType:"json",
		success:function(result){
			console.log(result);
			if(result.code == -1){
				var rd = result.data;
				var html = "";
				if(rd.length == 1){
					//对应一个活动费用
					html += "<tr><td>采浆费用</td><td>"+rd[0].colMoney+"</td></tr>";
					html += "<tr><td>路费</td><td>"+rd[0].roadMoney+"</td></tr>";
					html += "<tr><td>活动费用</td><td>"+rd[0].activityMoney+"</td></tr>";
				}else{
					//对应多个活动费用
					html += "<tr><td>采浆费用</td><td>"+rd[0].colMoney+"</td></tr>";
					html += "<tr><td>路费</td><td>"+rd[0].roadMoney+"</td></tr>";
					for(var i = 0; i<rd.length;i++){
						html += "<tr><td>活动费用</td><td>"+rd[i].activityMoney+"</td></tr>";
					}
				}
				$("#form").append(html);
			}else{
				layer.alert("查询失败");
			}
		}
	});
});