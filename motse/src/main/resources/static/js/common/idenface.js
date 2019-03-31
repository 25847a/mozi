
  
//// 拍摄快照的元素

        var canvasPreview = document.getElementById('canvasPreview');  
        var canvasUpload = document.getElementById('canvasUpload');  
        var contextPreview = canvasPreview.getContext('2d');  
        var contextUpload = canvasUpload.getContext('2d');  
  
  
  
  
        //#################### Video 来源  #######################3  
        var videoElement = document.querySelector('video');  
        var videoSelect = document.querySelector('select#videoSource');  
  
  
        navigator.mediaDevices.enumerateDevices()  
            .then(gotDevices).then(getStream).catch(handleError);  
  
  
        videoSelect.onchange = getStream;  
  
  
  
  
        function gotDevices(deviceInfos) {  
            for (var i = 0; i < deviceInfos.length; ++i) {  
                var deviceInfo = deviceInfos[i];  
                var option = document.createElement('option');  
                option.value = deviceInfo.deviceId;  
                if (deviceInfo.kind === 'videoinput') {  
                    option.text = deviceInfo.label ||  
                        'camera ' +  
                        (videoSelect.length + 1);  
                    videoSelect.appendChild(option);  
                } else {  
                    console.log('Found ome other kind of source/device: ', deviceInfo);  
                }  
            }  
        }  
  
  
        var _streamCopy = null;  
        function getStream() {  
            if (_streamCopy != null) {  
                try {  
                    _streamCopy.stop(); // 如果此方法不存在，则将执行catch.  
                } catch (e) {  
                    _streamCopy.getVideoTracks()[0].stop(); // 然后停止流的第一个视频轨道  
                }  
            }  
              
            var constraints = {  
                audio:false,  
                video: {  
                    optional: [  
                        {  
                            sourceId: videoSelect.value  
                        }  
                    ]  
                }  
            };  
              
            navigator.mediaDevices.getUserMedia(constraints).then(gotStream).catch(handleError);  
        }  
  
  
        function gotStream(stream) {  
            _streamCopy = stream; // 使控制台可用流  
            videoElement.srcObject = stream;  
        }  
  
  
        function handleError(error) {  
            alert(error.name + ": " + error.message);  
        }  
  
  
        //######################## 终端视频源 #################  
  
  
  
  
        // 进入相机！ 
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {  
            navigator.mediaDevices.getUserMedia({ video: true }).then(function(stream) {  
                videoElement.src = window.URL.createObjectURL(stream);  
                videoElement.play();  
  
  
            });  
        } else {  
            document.getElementById("pnlVideo1").style.display = "none";  
        }  
   
       