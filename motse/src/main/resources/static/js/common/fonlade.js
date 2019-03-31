var Imagedata;
var video = document.getElementById('video');
var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
window.onload = function() {
    var tracker = new tracking.ObjectTracker('face');//只捕捉面部
    tracker.setInitialScale(4);
    tracker.setStepSize(2);
    tracker.setEdgesDensity(0.1);
    tracking.track('#video', tracker, {
        camera: true
    });
    tracker.on('track', function(event){
        context.clearRect(0, 0, canvas.width, canvas.height);
        event.data.forEach(function(rect) {//面部捕捉框
            context.strokeStyle = 'red';
            context.strokeRect(rect.x, rect.y, rect.width, rect.height);
        });
    });
}
/*点击确定进行面部验证*/
    $("#quedes").click(function() {
        var xc ="data:image/jpeg;base64,"+Imagedata;
        var form=document.forms[0];
        var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
        var base64Codes=$("#imgsr").attr('src');
        formData.append("files",convertBase64UrlToBlob(base64Codes));
        formData.append("files",convertBase64UrlToBlob(xc));
        $.ajax({
            url : "/common/face",
            type : "POST",
            data : formData,
            processData : false,         // 告诉jQuery不要去处理发送的数据
            contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
            success:function(data){
               if(data.code==-1){
                   if(data.data.result=='True'){
                	   layui.use('layer', function() {
                           parent.$("#result").val(0);
                           //把现场照片返回到父页面
                           parent.$("#imageFace").val(Imagedata);
                           //图片显示在页面
                           parent.$("#imageFace_show").attr("src", xc);
                           layer.alert("验证成功",function(){
                        	   parent.layer.closeAll();
                        	   parent.submit();
                           });
                	   });
                   }else{
                       layui.use('layer', function() {
                           layer.alert("验证失败");
                       })
                   }
               }else{
                   layui.use('layer', function() {
                       layer.alert(data.message);
                   })
               }
            }
        });
	})
/*拍照*/
$("#play").click(function() {
    context.drawImage(video, 0, 0, 330, 250);
    Imagedata = canvas.toDataURL().substring(22); //上传给后台的图片数据
    $("#canvasA").show();
    $("#canvasA").attr("src", "data:image/jpeg;base64," + Imagedata);
})
/*取消*/
$("#delBtn").click(function() {
    $("#canvasA").hide();
})

/*取消*/
$("#qued").click(function() {
    parent.$("#play").attr("src", "data:image/jpeg;base64," + Imagedata);
    parent.layer.closeAll();
})
