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

function loginSubmit() {
     /******号码错误提示*******/
	   $("#loginCheck").addClass("errorIcon");
    if ($("#namePhoneCheck").html() != "") {
        $("#namePhoneCheck").addClass("errorIcon");
    } else {
        $("#namePhoneCheck").removeClass("errorIcon");
    }
    /*******密码提示********/
    if ($("#passWordCheck").html() != "") {
        $("#passWordCheck").addClass("errorIcon");
    } else {
        $("#passWordCheck").removeClass("errorIcon");
    }
    /*****************/
    if ($("#account").val() != "") {
        if ($("#passWord").val() != "") {
                    $.ajax({
                        type: "post",
                        url:  GetURLInfo()+"log/queryAdminLogin",//  url放的是当前页面请求的后台地址。
                        data: {"account":$("#account").val(),"passWord":$("#passWord").val()},
                     //   data: { "username": $("#namePhone").val(), "password": $("#passWord").val(), "vcodenum": $("#vcodecheck").val() },
                        beforeSend: function () {
                            $("#login").attr("disabled", "disabled")
                        },
                        success: function (data) {
                            if (data.code == 1) {	
                              $("#loginCheck").text(data.message);
                            }else{
                            	window.location.href='/admin/index';
                            }
                        },
                        error: function () {
                        	$("#loginCheck").text('操作错误');
                        },
                        complete: function () {
                            $("#login").removeAttr("disabled");
                        }
                    })
        
        }
    }
}
$("#login").on("click", function () {
    loginSubmit();
})
$(function () {
    $(document).keypress(function (e) {
        if (e.keyCode == 13)
            loginSubmit();
     })
});

$("#account").on("blur",function(){
    if ($("#account").val() == "") {
        $("#namePhoneCheck").text("请输入登录账号").addClass("errorIcon");
    } else if (!($("#account").val() == "")) {
        $("#namePhoneCheck").text("").removeClass("errorIcon");
	}
})
$("#passWord").on("blur",function(){
	if($("#passWord").val() == ""){
        $("#passWordCheck").text("请输入您的密码").addClass("errorIcon");
	}else if(!($("#passWord").val() == "")){
        $("#passWordCheck").text("").removeClass("errorIcon");
	}
})

