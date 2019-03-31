			$(function() {
				$(".jia").click(function() {
					var len = $("#bindgf .row").length;
					$("#bindgf").append("<div class='row'> " +
						"	<div class='coloaivit'> " +
						"		<div class='form-group newformcol'> " +
						"	<label class='control-label col-xs-4 UserCS'><ls>针次数</ls>：</label> " +
						"		<div class='input-group date'> " +
						"			<input value=' ' title='' class='form-control newfrin'> " +
						"				</div> " +
						"		</div> " +
						"</div> " +
						"<div class='coloaivit' style=' margin-left: 2em; float: left;'> " +
						"	<div class='form-group newformcol'> " +
						"	<label class='control-label col-xs-4 UserCS'>奖励金额：</label> " +
						"<div class='input-group date'> " +
						"<input value=' ' title='' class='form-control newfrin' style='width: 7em;'> " +
						"		</div> " +
						"	</div> " +
						"	</div>  " +
						"<div class='jian' onclick='clearone(this)'>-</div> " + 
						"</div> ")
				}) 
			}) 
			function clearone(obj) {
				$(obj).parent().remove();
			}
			
$(function(){
	
	
/*日期时间*/ 
//
//
layui.use('laydate', function(){
  var laydate = layui.laydate; 
  var laydateS = layui.laydate; 
  //单控件
  laydate.render({
    elem: '#startTime' 
    ,format: 'yyyy-MM-dd'
    ,value: '2017-09-10' 
    ,ready: function(date){
      console.log(date);
    }
    ,done: function(value, date, endDate){
      console.log(value, date, endDate);
    }
 })
  
  
    laydate.render({
    elem: '#startTimeimd' 
    ,format: 'yyyy-MM-dd'
    ,value: '2017-09-10' 
    ,ready: function(date){
      console.log(date);
    }
    ,done: function(value, date, endDate){
      console.log(value, date, endDate);
    }
 })
});  
})
