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
var checkcode = false;
var checkpassword= false;
var i = 0;
$(".next").click(function () {
    if ($("#Phone").val() == "" | $("#valid").val() == "") {

    } else {
        if (checked) {
            CheckVcode()
            if (checkcode) {
                $(".list-huakuai").removeClass("list-huakuai").next().addClass("list-huakuai");
                $('.information').hide().eq(++i).show();
                $("#companyphone").val($("#Phone").val());
                $("#pwdcode").val($("#valid").val());
                console.log("qwe");
            }
        }
    }
})
$("#pwd1").on("blur", function () {
    if ($("#pwd1").val() == "") {
        $("#passWord").text("请输入新密码");
    } else {
        $("#passWord").text("");
    }
})

$("#pwd2").on("blur", function () {
    checkpwd();
})

function checkpwd() {
    if ($("#pwd2").val() == "") {
        $("#passWord2").text("请再次输入密码");
    } else {
        $("#passWord2").text("");
    }
    if ($("#pwd1").val() !== $("#pwd2").val()) {
        $("#passWord2").text("两次输入的密码不一致!");
        checkpassword = false;
    } else {
        checkpassword= true;
        $("#passWord2").text("")
    }
}

$("#yemian2").click(function () {
    checkpwd();
    if (checkpassword) {
       // $("#newpassword").submit();
        $.ajax({
            url: "/Company/RetrievePassword",
            type: "post",
            data: $("#newpassword").serialize(),
            success: function (data) {
                if (data.code == 10001) {
                    alert("验证码错误")
                } else if (data.code == 10002) {
                    alert("验证码已过期")
                } else if (data.code == 10000) {
                    $(".list-huakuai").removeClass("list-huakuai").next().addClass("list-huakuai");
                    $('.information').hide().eq(++i).show();
                } else {
                    alert("发生错误！")
                }
            }

        })
    }
})
//========================手机号吗验证===========================
$("#Phone").on("blur", function () {
    $.ajax({
        type: "post",
        url: "/Company/CheckPhoneIsRegisteredForForget",
        data: { "phone": $("#Phone").val() },
        success: function (data) {
            if (data) {
                checked = true;
            } else {
                checked = false;
                $("#Phonecheck").text("该手机号不存在!");
            }
        }
    })
    phone();

})
$("#valid").on("blur", function () {
    if ($("#valid").val() == "") {
        console.log("sd")
        $("#validcheck").text("--请输入您的验证码")
    } else if (!($("#valid").val() == "")) {
        $("#validcheck").text("")
    }
})

var checkphone = false;
function phone() {
    if ($("#Phone").val() == "") {
        console.log("sd")
        $("#Phonecheck").text("--请输入您的手机号码")
        checkphone = false;
    } else if (!($("#Phone").val() == "")) {
        $("#Phonecheck").text("")
        checkphone = true;
    }
}

var count = 120;
var timer;
$("#vcode").on("click", function () {
    phone();
    if (checkphone) {
        $("#vcode").attr("disabled", "disabled")
        if ($("#Phone").val() != "") {
            $.ajax({
                type: "post",
                url: "/Vcode/YanzhenmaForRePwd",
                data: { "phone": $("#Phone").val() },
                success: function (data) {
                    if (data.result == 1) {
                        timer = setInterval(loop, 1000);
                    } else {
                        $("#vcode").removeAttr("disabled");
                        alert("验证码发送失败，请检查手机号码和网络后重新发送！")
                    }
                },
                error: function () {
                    $("#vcode").removeAttr("disabled");
                    alert("验证码发送失败，请检查手机号码和网络后重新发送！")
                }
            })
        } else {
            $("#vcode").removeAttr("disabled");
            alert("请先填写手机号码！")
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

function CheckVcode() {

    $.ajax({
        type: "post",
        url: "/Company/Verfication",
        data: { "phonenum": $("#Phone").val(), "vcodenum": $("#valid").val() },
        success: function (data) {
            if (data.result == 0) {
                //$("#checkcode").text("验证码错误")
                alert("验证码错误")
            } else if (data.result == 1) {
                // $("#checkcode").text("验证码已过期，请重新获取")
                alert("验证码已过期，请重新获取")
            } else {
                checkcode= true;
            }
        },
        error: function () {
            alert("发生错误");
        }
    })
}