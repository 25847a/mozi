$(function() {
    $("#newcamera").click(function () {
        //每次调用人脸识别先清空 验证成功后的值
        layer.ready(function () {
            layer.open({
                type: 2,
                title: '面部验证',
                maxmin: true,
                area: ['550px', '370px'],
                content: '/common/jumpFace',
                success: function (layero) {
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    //调用验证的方法（idCard 为身份证图片的 id，idCardResult是页面上 input 的id，验证成功后，改变 input的值， idCardResult 默认为 0，验证成功后 为 1）
                    //imageFace 为 base64 后的图片,需要保存到数据库
                    iframeWin.returnResult("idCard", "isIdentity", "imageFace");
                },
                end: function () {
                    chest();
                }
            })
        });
    });
})


/**
 * 身份证 base64 编码
 * @returns
 */
function getBase64Image(idCard) {
	var img = window.parent.document.getElementById(idCard);
    var canvas = document.createElement("canvas");
    canvas.width = 390;
    canvas.height = 567;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0, 390, 567);
    var dataURL = canvas.toDataURL("image/png");
    $("#ids").val(dataURL);
} 
/**
 * 验证并返回结果
 * @param idCard 身份证图片地址
 * @param returnInput 页面上面隐藏的 input ，用于接收人脸识别结果
 * @param imageFace 返回到父页面的 base64 之后人脸图片
 * @returns
 */
function returnResult(idCard,returnInput,imageFace){
	//先默认人脸识别的值，成功后改变值为 1
	window.parent.document.getElementById(returnInput).value=0;
	getBase64Image(idCard);
	var w = 320,
    h = 240; //摄像头配置,创建canvas
var pos = 0,
    ctx = null,
    saveCB, image = [];
var Imagedata;
var canvas = document.createElement("canvas");
//$("#canvasA").append(canvas);
canvas.setAttribute('width', w);
canvas.setAttribute('height', h);
ctx = canvas.getContext("2d");
image = ctx.getImageData(0, 0, w, h);
var imgData;
$(document).ready(
	function() {
		jQuery("#newreg_sitraNewyaz").webcam(
				{
					width : 320,
					height : 240,
					mode : "callback",
					swffile : "../../../js/jscam_canvas_only.swf",// 这里引入swf文件，注意路径
					onTick : function(remain) {
					},
					onSave : function(data) {
						var col = data.split(";");
				        var img = image;
				        for(var i = 0; i < w; i++) {
				            var tmp = parseInt(col[i]);
				            img.data[pos + 0] = (tmp >> 16) & 0xff;
				            img.data[pos + 1] = (tmp >> 8) & 0xff;
				            img.data[pos + 2] = tmp & 0xff;
				            img.data[pos + 3] = 0xff;
				            pos += 4;
				        }
				        if(pos >= 4 * w * h) {
				            ctx.putImageData(img, 0, 0); //转换图像数据，渲染canvas
				            pos = 0;
				            Imagedata = canvas.toDataURL().substring(22);
				        }
					},
					onCapture : function() {
						webcam.save();
						// Show a flash for example
					},
					debug : function(type, string) {
						console.log('type:' + type + ',string:' + string);
						// Write debug information to console.log() or a div
					},
					onLoad : function() {
						// Page load
					}
				});
		$('button').on('click', function() {
			webcam.capture();
		});
		
		$("#submit").click(function() {
			webcam.capture();
			$("#play").val(Imagedata);
			parent.$("#play").attr("src", "data:image/jpeg;base64," + Imagedata);
			$("#faceForm").ajaxSubmit({
		        type : 'POST',
		        url : "/common/face", 
		        success : function(res) { 
		        	if(null!=res.data){
		        		//parent.layer.alert
		        		parent.layer.alert('人脸识别成功',function(){
		        			var index = parent.layer.getFrameIndex(window.name);
	                        parent.layer.closeAll();
	                        //验证成功后，改变父页面的值
	                        window.parent.document.getElementById(returnInput).value=1;
	                        //返回 人脸图片到父页面，确定的时候提交保存到数据库
	                        window.parent.document.getElementById(imageFace).value=Imagedata;
			            });
		        	}else{
		        		parent.layer.msg(res.message);
		        	}
		        },
		        error : function() {
		            alert("上传失败，请检查网络后重试,上传文件太大");
		        }
		    });
			return false;
		})
	});

}

