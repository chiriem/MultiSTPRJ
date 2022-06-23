<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>MultiStudio</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet"/>

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/join.css" rel="stylesheet">
    <meta charset="EUC-KR">
    <title>Sign Up</title>
    <script src="/js/jquery-3.6.0.js">

    </script>
    <script type="text/javascript">
        // 회원가입 정보의 유효성 체크하기
        function doRegUserCheck(f) {

            if (f.user_id.value == "") {
                alert("아이디를 입력하세요.");
                f.user_id.focus();
                return false;
            }

            if (f.user_nm.value == "") {
                alert("이름을 입력하세요.");
                f.user_nm.focus();
                return false;
            }

            if (f.user_pw.value == "") {
                alert("비밀번호를 입력하세요.");
                f.user_pw.focus();
                return false;
            }

            if (f.user_pw2.value == "") {
                alert("비밀번호확인을 입력하세요.");
                f.user_pw2.focus();
                return false;
            }

            if (f.user_pw2.value == f.user_pw.value){
                alert("비밀번호를 다시 확인해주세요.")
                f.user_pw2.focus();
                return false;
            }

            if (f.age.value == "") {
                alert("출생년도를 입력하세요.");
                f.age.focus();
                return false;
            }
        }
    </script>

</head>
<body>
<div class="container-xxl position-relative bg-white d-flex p-0">
    <!-- Spinner Start -->
    <div id="spinner"
         class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->


    <!-- Sign Up Start -->
    <div class="container-fluid">
        <div class="row h-100 align-items-center justify-content-center" style="min-height: 100vh;">
            <div class="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
                <div class="bg-light rounded p-4 p-sm-5 my-4 mx-3">
                    <form name="f" method="post" action="/user/insertUserInfo" onsubmit="return doRegUserCheck(this);">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <a href="/index" class="">
                                <h3 class="text-primary"><i class="fa fa-hashtag"></i>MultiStudio</h3>
                            </a>
                            <h3>Sign Up</h3>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" name="user_nm" placeholder="User Name" id="user_nm">
                            <label>Username</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="text" class="id_input" name="user_id" placeholder="user_id" id="user_id">
                            <label>User id</label>
                            <span class="id_input_re_1">사용 가능한 아이디입니다.</span>
                            <span class="id_input_re_2">아이디가 이미 존재합니다.</span>
                            <span class="id_input_re_3">특수문자를 빼주세요</span>
                        </div>
                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" name="user_pw" placeholder="Password"
                                   id="password">
                            <label>Password</label>
                        </div>
                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" name="user_pw2" placeholder="Password Check"
                                   id="password2">
                            <label>Password Check</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" name="age" placeholder="2000" id="age">
                            <label>Birth year</label>
                        </div>
                        <button type="submit" class="btn btn-primary py-3 w-100 mb-4">Sign Up
                        </button>
                        <p class="text-center mb-0">Already have an Account? <a href="/user/LoginForm">Sign In</a></p>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Sign Up End -->
</div>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/lib/chart/chart.min.js"></script>
<script src="/lib/easing/easing.min.js"></script>
<script src="/lib/waypoints/waypoints.min.js"></script>
<script src="/lib/owlcarousel/owl.carousel.min.js"></script>
<script src="/lib/tempusdominus/js/moment.min.js"></script>
<script src="/lib/tempusdominus/js/moment-timezone.min.js"></script>
<script src="/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- Template Javascript -->
<script src="/js/main.js"></script>

<script type="text/javascript">

    $('#user_id').on("propertychange change keyup paste input", function(){


        var user_id = document.getElementById("user_id");
        console.log(user_id.value);

        var data = {"user_id": user_id.value};

        console.log(data);


        // var regExp = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;

        $.ajax({
            type: "post",
            url: "/user/memberIdChk",
            data: data,
            success: function (result) {

                // if (regExp.test(document.getElementById("user_id"))){
                //     $('.id_input_re_3').css("display", "none");
                // } else {
                //     $('.id_input_re_3').css("display", "inline-block");
                // }

                if (result != 'fail') {
                    $('.id_input_re_1').css("display", "inline-block");
                    $('.id_input_re_2').css("display", "none");
                    idckCheck = true;
                } else {
                    $('.id_input_re_2').css("display", "inline-block");
                    $('.id_input_re_1').css("display", "none");
                    idckCheck = false;
                }

            }// success 종료
        }); // ajax 종료

    });

    $('#user_id').on("propertychange change keyup paste input", function(){


        var user_id = document.getElementById("user_id");
        console.log(user_id.value);

        var data = {"user_id": user_id.value};

        console.log(data);



        $.ajax({
            type: "post",
            url: "/user/memberIdChk",
            data: data,
            success: function (result) {

                if (result != 'fail') {
                    $('.id_input_re_1').css("display", "inline-block");
                    $('.id_input_re_2').css("display", "none");
                    idckCheck = true;
                } else {
                    $('.id_input_re_2').css("display", "inline-block");
                    $('.id_input_re_1').css("display", "none");
                    idckCheck = false;
                }



            }// success 종료
        }); // ajax 종료

    });


    //
    // function fn_validateId(str)
    // {
    //     var id = str;
    //
    //     //특수문자가 있는지 확인
    //     var spe = id.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    //     // 한글이 있는지 확인
    //     var korean = id.search(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi);
    //
    //     if ((id.length < 6) || (id.length > 20)) {
    //         alert("아이디를 6자리 ~ 20자리 이내로 입력해주세요.");
    //         $("#alertIdValidate").show();
    //         return false;
    //     }
    //     if (id.search(/₩s/) != -1) {
    //         alert("아이디는 공백없이 입력해주세요.");
    //         return false;
    //     }
    //     if (spe > 0 || korean > 0) {
    //         alert("아이디는 영문,숫자만 입력해주세요.");
    //         return false;
    //     }
    //
    //     return true;
    // }


</script>
</body>
</html>

