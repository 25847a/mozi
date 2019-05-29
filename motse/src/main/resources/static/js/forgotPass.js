/***********canvas*************/
var DEFAULT_VERSION = 8.0;
var ua = navigator.userAgent.toLowerCase();
var isIE = ua.indexOf("msie") > -1;
var safariVersion;
if (isIE) {
    safariVersion = ua.match(/msie ([\d.]+)/)[1];
}
if (safariVersion <= DEFAULT_VERSION) {

} else {
    particlesJS("particles-js", {
        particles: {
            number: {
                value: 120,
                density: {
                    enable: true,
                    value_area: 800
                }
            },
            color: {
                value: "#ffffff"
            },
            shape: {
                type: "circle",
                stroke: {
                    width: 0,
                    color: "#000000"
                },
                polygon: {
                    nb_sides: 5
                },
                image: {
                    src: "img/github.svg",
                    width: 100,
                    height: 100
                }
            },
            opacity: {
                value: .5,
                random: false,
                anim: {
                    enable: false,
                    speed: 1,
                    opacity_min: .1,
                    sync: false
                }
            },
            size: {
                value: 1,
                random: true,
                anim: {
                    enable: false,
                    speed: 20,
                    size_min: .1,
                    sync: false
                }
            },
            line_linked: {
                enable: true,
                distance: 40,
                color: "#fff",
                opacity: 1,
                width: 1
            },
            move: {
                enable: true,
                speed: 3,
                direction: "none",
                random: false,
                straight: false,
                out_mode: "out",
                attract: {
                    enable: false,
                    rotateX: 600,
                    rotateY: 1200
                }
            }
        },
        interactivity: {
            detect_on: "canvas",
            events: {
                onhover: {
                    enable: true,
                    mode: "grab"
                },
                onclick: {
                    enable: true,
                    mode: "push"
                },
                resize: true
            },
            modes: {
                grab: {
                    distance: 120,
                    line_linked: {
                        opacity: 1
                    }
                },
                bubble: {
                    distance: 400,
                    size: 40,
                    duration: 2,
                    opacity: 8,
                    speed: 3
                },
                repulse: {
                    distance: 300
                },
                push: {
                    particles_nb: 4
                },
                remove: {
                    particles_nb: 2
                }
            }
        },
        retina_detect: true,
        config_demo: {
            hide_card: false,
            background_color: "#b61924",
            background_image: "",
            background_position: "50% 50%",
            background_repeat: "no-repeat",
            background_size: "cover"
        }
    });
}
/**********************/
//	===================左侧导航==============s
var checked = false;
var checkpassword= false;
var i = 0;
/**
 * 输入验证码点击下一步
 */
$(".next").click(function () {
    if ($("#Phone").val() != "" || $("#valid").val() != "") {
    	$("#oldphone").val($("#Phone").val());
    	 if (checked) {
    		 $.ajax({
    		        type: "post",
    		        url: "/log/queryCheckingCode",
    		        data: {"phoen": $("#Phone").val(),"code": $("#valid").val()},
    		        success: function (data) {
    		            if (data.code == -1) {
    		            	  $(".list-huakuai").removeClass("list-huakuai").next().addClass("list-huakuai");
    		                  $('.information').hide().eq(++i).show();
    		                  $("#companyphone").val($("#Phone").val());
    		                  $("#pwdcode").val($("#valid").val());
    		            } else{
    		            	tips("验证码错误!");
    		            }
    		        },
    		        error: function () {
    		        	tips("发生错误,请刷新页面重试");
    		        }
    		    });
         }
    	} else {
    		tips("输入框不能为空!!");
    }
});

$("#pwd1").on("blur", function () {
    if ($("#pwd1").val() == "") {
    	tips("请输入新密码");
    }
});

$("#pwd2").on("blur", function () {
    checkpwd();
})

function checkpwd() {
    if ($("#pwd2").val() == "") {
    	tips("请再次输入密码!");
    } else {
    	if ($("#pwd1").val()!== $("#pwd2").val()) {
        	tips("两次输入的密码不一致!");
            checkpassword = false;
        } else{
        	checkpassword= true;
        }
    }
};
/**
 * 输入新密码点击下一步
 */
$("#yemian2").click(function () {
    checkpwd();
    if (checkpassword) {
        $.ajax({
            url: "/log/updatePassWord",
            type: "post",
            data:{"account": $("#oldphone").val(),"passWord": $("#pwd2").val()},
            success: function (data) {
                if (data.code == -1) {
                	 $(".list-huakuai").removeClass("list-huakuai").next().addClass("list-huakuai");
                     $('.information').hide().eq(++i).show();
                }else{
                	tips("两次输入的密码不一致!");
                }
            }

        })
    }
})
//========================手机号吗验证===========================
$("#Phone").on("blur", function () {
    $.ajax({
        type: "post",
        url: GetURLInfo()+"log/checkingPhone",
        data: { "phone": $("#Phone").val() },
        success: function (data) {
            if (data.code==-1) {
                checked = true;
            } else {
                checked = false;
                tips("该手机号不存在!!");
            }
        }
    })
    phone();

})
$("#valid").on("blur", function () {
    if ($("#valid").val() == "") {
        console.log("sd")
        $("#validcheck").text("--请输入您的验证码");
    } else if (!($("#valid").val() == "")) {
        $("#validcheck").text("")
    }
});
var checkphone = false;
function phone() {
    if ($("#Phone").val() == "") {
        tips("请输入您的手机号码!!");
        checkphone = false;
    } else if (!($("#Phone").val() == "")) {
        checkphone = true;
    }
}

var count = 120;
var timer;
$("#vcode").on("click", function () {
    phone();
    if (checkphone) {
        $("#vcode").attr("disabled", "disabled");
        if ($("#Phone").val() != "") {
            $.ajax({
                type: "post",
                url: GetURLInfo()+"log/checkingCode",
                data: { "phone": $("#Phone").val() },
                success: function (data) {
                    if (data.code == -1) {
                    	 tips("验证码已发送,请查收");
                        timer = setInterval(loop, 1000);
                    } else {
                        $("#vcode").removeAttr("disabled");
                        tips(data.message);
                    }
                },
                error: function () {
                    $("#vcode").removeAttr("disabled");
                    tips("验证码发送失败，请检查手机号码和网络后重新发送!")
                }
            });
        } else {
            $("#vcode").removeAttr("disabled");
            tips("请先填写手机号码!");
        }
    }
})


function loop() {
    count--;
    if (count > 0) {
        $("#vcode").val(count + "秒之后可重发");
    } else {
        clearInterval(timer);
        $("#vcode").val("点此发送验证码");
        $("#vcode").removeAttr("disabled");
    }
}	
