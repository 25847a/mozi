$(function() {
    //启动摄像头
    $("#newcamera").click(function () {
        layer.ready(function() {
            layer.open({
                type: 2,
                title: '启动摄像头',
                maxmin: true,
                area: ['750px', '420px'],
                content: '/common/busCamera',
            })
        });
    })
});
function identityValidate(){
    layer.ready(function () {
        layer.open({
            type: 2,
            title: '面部验证',
            maxmin: true,
            area: ['750px', '420px'],
            content: '/common/identityValidate',
        })
    });
}
/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte
    //处理异常,将ascii码小于0的转换为大于0
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob( [ab] , {type : 'image/png'});
}
