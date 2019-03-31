	var w = 320,
		h = 240; //摄像头配置,创建canvas
	var pos = 0,
		ctx = null,
		saveCB, image = [];
	var base64;
	var canvas = document.createElement("canvas");
	$("#canvasA").append(canvas);
	canvas.setAttribute('width', w);
	canvas.setAttribute('height', h);
	ctx = canvas.getContext("2d");
	image = ctx.getImageData(0, 0, w, h); 
	var imgData; 
	$("#webcam").webcam({
		width: w,
		height: h,
		mode: "callback", //stream,save，回调模式,流模式和保存模式
		swffile: "../../../js/jscam_canvas_only.swf",
		onSave: function(data) { //保存图像
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
				Imagedata = canvas.toDataURL().substring(22); //上传给后台的图片数据 
				base64 = Imagedata;
			}
		},
		onCapture: function() { //捕获图像
			webcam.save();
		},
		debug: function(type, string) { //控制台信息
			console.log(type + ": " + string);
		}, 
	});
	$("#qued").click(function() {
		//$("#base", window.parent.document).attr("src","data:image/jpeg;base64,"+base64);
		$("#base").attr("src", "data:image/jpeg;base64," + base64);
		parent.$("#play").attr("src", "data:image/jpeg;base64," + base64);

	})
	$("#play").click(function() {
		webcam.capture();
	});