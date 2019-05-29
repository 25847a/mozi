var socket;
var page=1;
$("#page").val(page);
	var lockReconnect = false;//避免重复连接
	var adminId = $("#id").val();
	console.log("adminId为>>>>>>>"+adminId+"点开了websocket");
	var url = GetURLInfo().replace("http","ws")+"websocket/"+adminId;
    if(typeof(WebSocket) != "undefined") {
    	createWebSocket(url);//开始连接
    	function createWebSocket(url){
    		try{
    			socket = new WebSocket(url);
    			initEventHandle();
    		}catch(e){
    			reconnect(url);//重新连接
    		}
    	}
    }else{
    	alert("您的浏览器不支持WebSocket");
    }
  //封装websocket的那几个接口函数
    function initEventHandle(){
    	socket.onopen = function() {  
            console.log("Socket 已打开");
      //      socket.send(adminId);//page
         heartCheck.reset().start();//心跳检测重置
        };  
        //获得消息事件  
        socket.onmessage = function(msg) {
        	console.log(msg);
        
        		var data = JSON.parse(msg.data);//console.log("接收到后台返回的数据："+data.data); 
            	tableList.tableConfig.tableData=[];
            	 for(var i=0;i<data.data.length;i++){
            		 tableList.tableConfig.tableData.push(data.data[i]);
                         }
            	//  var limit =parseInt(data.count/10)-2;
            	  var limit =parseInt(Math.ceil(data.count/10));
            	  if(parseInt(page)<parseInt(limit)){
            		  page++;
            	  }else{
            		  page=1;
            	  }
            	  $("#page").val(page);
            	  pie.initPieEchart(data.equipment.offline,data.equipment.online);//设备使用状态饼状图
                  pie.initPie(data.userGender.male,data.userGender.female);//用户男女比例
                  move.initzst(data.heartrateBoy,data.heartrateGirl);//心率折线图
                  funnel.initfunnel(data.blood);//血压漏斗图
                  ring.initPineEchart(data.microcirculation);//微循环统计图
                  tree.inittree(data.bloodoxygen);//血氧柱状图
                  last.initlast(data.respirationrate);//户呼吸扇形图
        	
        	
              heartCheck.reset().start();//如果获取到消息,心跳检测重置
              
        };  
        //关闭事件  
        socket.onclose = function(e) {
        		console.log("Socket已关闭");
                socket.close();
                reconnect(url);//重新连接
        		console.log(WebSocket); 
            
        };  
        //发生了错误事件  
        socket.onerror = function() {
        	console.log("Socket发生了错误");
        	socket.close();
            reconnect(url);//重新连接
        }
    }
	
  function reconnect(url){
	  if(lockReconnect){
		  return;
	  }
	  lockReconnect=true;
	  //没连接上会一直重连,设置延迟避免请求过多
	  setTimeout(function(){
		  console.info("尝试重连..." + new Date().format("yyyy-MM-dd hh:mm:ss"));
		  createWebSocket(url);
		  lockReconnect = false;
	  },5000);
  };
//心跳检测,每5s心跳一次
  var heartCheck = {
		  timeout: 20000,
		  timeoutObj: null,
		  serverTimeoutObj: null,
		  reset: function(){
			    clearTimeout(this.timeoutObj);
			    clearTimeout(this.serverTimeoutObj);
			    return this;
			        },
		  start: function(){
			  var self = this;
			  this.timeoutObj = setTimeout(function(){
		//		  socket.send(adminId);//page
				//这里发送一个心跳，后端收到后，返回一个心跳消息,onmessage拿到返回的心跳就说明连接正常
				  console.info("客户端发送心跳：" + new Date().format("yyyy-MM-dd hh:mm:ss"));
				  self.serverTimeoutObj = setTimeout(function(){
					//如果超过一定时间还没重置，说明后端主动断开了
					  socket.close();
					//如果onclose会执行reconnect，我们执行ws.close()就行了.
					 //如果直接执行reconnect 会触发onclose导致重连两次
				  },self.timeout);
			  },self.timeout);
		  }
}
//js中格式化日期，调用的时候直接：new Date().format("yyyy-MM-dd hh:mm:ss")
  Date.prototype.format = function(fmt) {
  var o = {
      "M+" : this.getMonth()+1,                 //月份 
      "d+" : this.getDate(),                    //日 
      "h+" : this.getHours(),                   //小时 
      "m+" : this.getMinutes(),                 //分 
      "s+" : this.getSeconds(),                 //秒 
      "q+" : Math.floor((this.getMonth()+3)/3), //季度
      "S"  : this.getMilliseconds()             //毫秒 
          }; 
          if(/(y+)/.test(fmt)) {
      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 

                  }

          for(var k in o) {
      if(new RegExp("("+ k +")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
              }
                  }
          return fmt; 
          }