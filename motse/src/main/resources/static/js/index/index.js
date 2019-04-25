document.getElementById('chen').innerHTML=$("#name").val();

window.onload = windowHeight; //页面载入完毕执行函数
		        function windowHeight() {
		            //窗口高度
		            var iframe = document.getElementById("iframe");
		            iframe.style.height = window.innerHeight + 'px';
		            var row = document.getElementById("row-height");
		            row.style.height = window.innerHeight + 'px';
		        };
//导航栏图标旋转
$(function () {
		            $(".panel-heading").click(function (e) {
		                /*切换折叠指示图标*/
		                $(this).find("span").toggleClass("glyphicon-chevron-right");
		                $(this).find("span").toggleClass("glyphicon-chevron-down");
		            });
		        });
window.onload = windowHeight; //页面载入完毕执行函数
function windowHeight() {
    //窗口高度
    var iframe = document.getElementById("iframe");
    iframe.style.height = window.innerHeight + 'px';
    var row = document.getElementById("row-height");
    row.style.height = window.innerHeight + 'px';
};
//养老院
$(function () {
    $('#beadhouse').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/admin/beadhousePage')
        $(".index-home").addClass('adds')
        $(".history").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
// 历史健康数据
$(function () {
    $('#changeToSet').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/health/historyPage')
        $(".history").addClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
        
    })
});
/*   // 重点关爱管理
$(function () {
    $('#changelove').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/health/lovePage')
        $(".love").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
}); */
//添加用户
$(function () {
    $('#addUser').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/user/addUserPage')
        $(".version").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
	//代理商管理
$(function () {
    $('#agent').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/admin/agentPage')
        $(".agent").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
//设备管理
$(function () {
    $('#equipment').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/equipment/equipmentPage')
        $(".equipment").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
//版本管理
$(function () {
    $('#version').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/versionhistory/versionPage')
        $(".version").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
//健康数据管理
$(function () {
    $('#health').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/health/healthPage')
        $(".health").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    
    })
});
//角色管理
$(function () {
    $('#role').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/role/rolePage')
        $(".role").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
//用户管理
$(function () {
    $('#admin').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/admin/adminPage')
        $(".user").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".menu").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
//菜单管理
$(function () {
    $('#menu').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/auth/authPage')
        $(".menu").addClass('adds')
        $(".history").removeClass('adds')
        $(".index-home").removeClass('adds')
        $(".love").removeClass('adds')
        $(".version").removeClass('adds')
        $(".agent").removeClass('adds')
        $(".health").removeClass('adds')
        $(".user").removeClass('adds')
        $(".role").removeClass('adds')
        $(".equipment").removeClass('adds')
        $(".add").removeClass('adds')
    })
});
$(function() {
    $("#files").change(function(e) {
        var imgBox = e.target;
        uploadImg($('#bcd'), imgBox)
        $('#bcd img').remove();  
    });

    function uploadImg(element, tag) {
        var file = tag.files[0];
        var imgSrc;
        if (!/image\/\w+/.test(file.type)) {
            alert("看清楚，这个需要图片！");
            return false;
        }
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function() {
            console.log(this.result);
            imgSrc = this.result;
            var imgs = document.createElement("img");
            $(imgs).attr("src", imgSrc);
            element.append(imgs);
        };
    }

    $("#submit").click(function(){
        $.ajax({
            url:"",
            method:"post",
            cache: false,
            data:new FormData($('#uploadForm')[0]),
            contentType: false,
            processData: false,
            dataType:"json",
            success:function(data){
                alert('success');
                $('#a').trigger('click');
                $('#bcd img').remove(); 
                // console.log(data);
            },
            error:function(data){
                alert(data,'error');
                $('#a').trigger('click');
                $('#bcd img').remove(); 
            }
        })
    });
    $("#a").click(function(){
        $('#bcd img').remove();
    });
    $(".close").click(function(){
        $('#bcd img').remove();
    });
})