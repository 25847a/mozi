var avatar;
document.getElementById('chen').innerHTML=$("#agentName").val();
$("#dropdownMenu1").text($("#adminName").val());

//判断一个url是否可以访问
console.log(imageGudge($("#avatar").val()));
if(imageGudge($("#avatar").val())){
	document.getElementById('avatarImage').innerHTML="<img src="+$("#avatar").val()+" alt=''/>"
}

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

function windowHeight() {
//窗口高度
var iframe = document.getElementById("iframe");
iframe.style.height = window.innerHeight + 'px';
var row = document.getElementById("row-height");
//导航栏高度
row.style.height = window.innerHeight - 60 + 'px';
};
window.onload = function () {
windowHeight();
}
window.onresize = function () {
windowHeight();
}


$('.upload').click(function () {
    $('#files').click();
});
$(function () {
    $("#files").change(function (e) {
        var imgBox = e.target;
        uploadImg($('#bcd'), imgBox)
        $('#bcd img').remove();
    });

    function uploadImg(element, tag) {
        var file = tag.files[0];
        var imgSrc;
        if (!/image\/\w+/.test(file.type)) {
        	tips("看清楚，这个需要图片！")
            return false;
        }
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function () {
        	avatar=this.result;
            console.log(this.result);
            imgSrc = this.result;
            var imgs = document.createElement("img");
            $(imgs).attr("src", imgSrc);
            element.append(imgs);
        };
    }

    $("#a").click(function () {
        $('#bcd img').remove();
    });
    $(".close").click(function () {
        $('#bcd img').remove();
    });
});
/**
 * 修改头像
 * @returns
 */
function agentImage(){
	avatar=avatar.substring(23,avatar.length);
	$.ajax({
		type: "post",
        url:GetURLInfo()+"admin/updateAgentImage",
        data: {"avatar":avatar},
        dataType: "json",
        success: function (data) {
        	if(data.code==-1){
        		tips(data.message);
               location.reload();
       	}else{
       		tips(data.message);
       	}
        },
        error: function (data) {
        	tips('error');
        }
    });
};
/**
 * 修改密码
 * @returns
 */
function confirmPassword(){
	var oldPassword = $("#oldPassword").val();
	var newPass = $("#newPass").val();
	var newWord = $("#newWord").val();
	if(newPass!=newWord){
		tips("两次新密码不相同,请核实后输入");
		return;
	}
	if(oldPassword=="" || newPass=="" || newWord==""){
		tips("密码不能为空");
		return;
	}
	$.ajax({
		type: "post",
        url:GetURLInfo()+"admin/updateAgentPassword",
        data: {"oldPassword":oldPassword,"newPass":newPass,"newWord":newWord},
        dataType: "json",
        success: function (data) {
        	if(data.code==-1){
        		tips(data.message);
                 location.reload();
        	}else{
        		tips(data.message);
        	}
        },
        error: function (data) {
        	tips('error');
        }
    });
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
//用户分布图页面
$(function () {
    $('#distribution').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/admin/distributionPage')
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
//机构信息
$(function () {
    $('#adminInfo').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/admin/adminInfoPage')
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
        $(window.parent.document).find('#iframe').attr('src', '/uploaddownload/versionPage')
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
//护士页面
$(function () {
    $('#nurse').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/nurse/nursePage')
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
//床位页面
$(function () {
    $('#bed').on('click', function () {
        $(window.parent.document).find('#iframe').attr('src', '/bedNumber/bedPage')
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