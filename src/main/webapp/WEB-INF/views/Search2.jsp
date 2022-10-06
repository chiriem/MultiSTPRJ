<%@ page import="kopo.poly.persistance.mongodb.impl.UserInfoMapper" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.SStudioDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>MultiStudio - Multiple Streaming Studio</title>
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
    <script src="https://kit.fontawesome.com/7bdf505d8f.js" crossorigin="anonymous"></script>

    <!-- Libraries Stylesheet -->
    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/page/mic.css">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <%
        String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
    %>

    <!-- Template Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/table_with_div.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script src="/js/jquery-3.6.0.js"></script>
    <script src="/js/annyang.js"></script>
    <script src="/js/speechkitt.js"></script>
    <script>

        // //annyang 라이브러리 실행
        // annyang.start({
        //     autoRestart : true,
        //     continuous : true
        // })
        // //음성인식 값 받아오기위한 객체 생성
        // var recognition = annyang.getSpeechRecognizer();
        // // //최종 음성인식 결과값 저장 변수
        // // var final_transcript = "";
        // //말하는 동안에 인식되는 값 가져오기(허용)
        // recognition.interimResults = true;
        // // //음성인식 가능한 브라어주인지 확인
        // // if ('SpeechRecognition' in window) {
        // //     alert("음성인식 가능함");
        // // }
        //
        // //음성 인식결과 가져오기
        // recognition.onresult = function(event) {
        //     var interim_transcript = "";
        //     final_transcript = "";
        //     for (var i = event.resultIndex; i < event.results.length; ++i) {
        //         if (event.results[i].isFinal) {
        //             final_transcript += event.results[i][0].transcript;
        //         }
        //     }
        //     //$("#view_ing").html("말하는 중 : " + interim_transcript);
        //     $("#mySpeak").html(final_transcript);
        //     $("#search_box").val(final_transcript);
        //     $("#totalMySpeak").after(final_transcript + "<br/>");
        //
        // };

        // if (annyang) {
        //     // Add our commands to annyang
        //     annyang.addCommands({
        //         'hello': function() { alert('Hello world!'); }
        //     });
        //
        //     // Tell KITT to use annyang
        //     SpeechKITT.annyang();
        //
        //     // Define a stylesheet for KITT to use
        //     SpeechKITT.setStylesheet('//cdnjs.cloudflare.com/ajax/libs/SpeechKITT/1.0.0/themes/flat.css');
        //
        //     // Render KITT's interface
        //     SpeechKITT.vroom();
        // }


        function doCopy(link){

            var ytlink = "youtube.com/watch?v=" + link;

            console.log(ytlink);

            var result = $('#yt_result_link');
            result.val(ytlink);
        }

        function fnGetList(sGetToken) {

            var $getval = $("#search_box").val();

            if ($getval == "") {

                alert("검색어를 입력하세요.");

                $("#search_box").focus();

                return;

            }

            $("#get_view").empty();

            $("#nav_view").empty();

            //https://developers.google.com/youtube/v3/docs/search/list

            var order = "relevance";

            var maxResults = "5";

            var key = "AIzaSyAfJQyw0LqcMkaJi0hCw35NUPyjV7Br-4g";


            var sTargetUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=" + order

                + "&q=" + encodeURIComponent($getval) + "&key=" + key + "&maxResults=" + maxResults;

            console.log(sGetToken);

            if (sGetToken != null) {

                sTargetUrl += "&pageToken=" + sGetToken + "";

            }

            console.log(sTargetUrl);

            $.ajax({

                type: "POST",

                url: sTargetUrl,

                dataType: "jsonp",

                success: function (jdata) {

                    console.log(jdata);


                    $(jdata.items).each(function (i) {

                        //console.log(this.snippet.channelId);

                        $("#get_view").append(
                            '<div class="divTableRow">' +
                                '<div class="divTableCell"><img class="tnail" src="http://img.youtube.com/vi/' + this.id.videoId + '/mqdefault.jpg" width="160" height="90"></div>' +
                                '<div class="divTableCell"><a href="https://youtu.be/' + this.id.videoId + '">' + '<span>' + this.snippet.title + '</span></a></div>' +
                                '<div class="divTableCell">' +
                                    // '<input type="text" id="result_link" class="form-control" onclick="this.select();" value="' + this.id.videoId + '" readonly>' +
                                    '<button class="btn btn-primary notranslate" type="button" onclick="doCopy(\''+this.id.videoId+'\')">GetLink!</button>' +
                                '</div>' +
                            '</div>'
                            );

                    }).promise().done(function () {

                        if (jdata.prevPageToken) {

                            $("#nav_view").append('<button class="btn btn-primary m-2" onclick="fnGetList(\'' + jdata.prevPageToken + '\')"><</button>');

                        }

                        if (jdata.nextPageToken) {

                            $("#nav_view").append('<button class="btn btn-primary m-2" onclick="fnGetList(\'' + jdata.nextPageToken + '\')">></button>');

                        }

                    });

                },

                error: function (xhr, textStatus) {

                    console.log(xhr.responseText);

                    alert("에러");

                    return;

                }

            });

        }
        function yt_copytoclip(){
            $('#yt_result_link').select();
            document.execCommand("copy");
            add_toast('Success Info', 'Copied to clipboard.<br>Paste it where you need it with (Ctrl+v).');
            //add_toast('Warning Info', data.error);
        }

        function newytWindow(){

            let option = "width = 1000, height = 600, location=0,toolbar=no,scrollbars=no,resizable=no,status=no,menubar=no";

            window.open('SingleST/SStudioadd', '_blank', option);
        }

    </script>
</head>

<body>
<%
    UserInfoMapper info = (UserInfoMapper) session.getAttribute("info");
%>
<div class="container-xxl position-relative bg-white d-flex p-0">
    <!-- Spinner Start -->
    <div id="spinner"
         class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->


    <!-- Sidebar Start -->
    <div class="sidebar pe-4 pb-3">
        <nav class="navbar bg-light navbar-light">
            <a href="/index" class="navbar-brand mx-4 mb-3">
                <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>MultiStudio</h3>
            </a>
            <div class="navbar-nav w-100">
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-youtube-play me-2"></i>Main</a>
                    <div class="dropdown-menu bg-transparent border-0">
                        <a href="/index" class="dropdown-item">Youtube</a>
                        <a href="/index2" class="dropdown-item">Youtube LiveStream</a>
                    </div>
                </div>
                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-youtube-play me-2"
                                                                                aria-hidden="false"></i>MultiStudio</a>
<%--                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-twitch me-2"--%>
<%--                                                                                aria-hidden="false"></i>MultiTwitch</a>--%>
                <a href="/notice/NoticeList" class="nav-item nav-link"><i class="fa fa-book me-2" aria-hidden="false"></i>Notice</a>
                <a href="/chat/intro" class="nav-item nav-link"><i class="fa fa-comments me-2" aria-hidden="false"></i>LiveChat</a>
                <a href="/calendar" class="nav-item nav-link"><i class="fa fa-calendar me-2" aria-hidden="false"></i>Calendar</a>
                <a href="/Search2" class="nav-item nav-link"><i class="fa fa-search me-2" aria-hidden="false"></i>Search</a>
            </div>
        </nav>
    </div>
    <!-- Sidebar End -->


    <!-- Content Start -->
    <div class="content">
        <!-- Navbar Start -->
        <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
            <a href="/index" class="navbar-brand d-flex d-lg-none me-4">
                <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
            </a>
            <a href="#" class="sidebar-toggler flex-shrink-0">
                <i class="fa fa-bars"></i>
            </a>
            <form class="d-none d-md-flex ms-4">
                <form name="form1" method="post" onsubmit="return false;">
                    <input class="form-control border-0" type="search" placeholder="Search" id="search_box"
                           target="search_box_t" width="400px">
                    <button type="button" class="btn btn-primary m-2" onclick="fnGetList();">Go!</button>
                    <div id="micButtonBox"></div>
                </form>
            </form>
            <div class="navbar-nav align-items-center ms-auto">

                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                        <i class="fa fa-cog fa-fw"></i>
                        <span class="d-none d-lg-inline-flex">
                                Setting
                            </span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                        <a href="/Setting" class="dropdown-item">Setting</a>
                        <% if (SS_USER_ID != null) { %>
                        <a href="/logout" class="dropdown-item">Log out</a>
                        <a href="/user/UseradjustForm" class="dropdown-item">Adjust up</a>
                        <%} else {%>
                        <a href="/user/LoginForm" class="dropdown-item">Sign in</a>
                        <a href="/user/UserRegForm" class="dropdown-item">Sign up</a>
                        <%} %>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Navbar End -->


        <!-- Form Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row_Search bg-light rounded align-items-center justify-content-center g-4">
                <div class="col-sm-12">
                    <div class="table-responsive">
                        <div class="divTable minimalistBlack">
                            <div class="divTableHeading">
                                <div class="divTableRow">
                                    <div class="divTableHead" style="width: 170px">Thumbnail</div>
                                    <div class="divTableHead">Title</div>
                                    <div class="divTableHead" style="width: 150px">Youtube/live</div>
                                </div>
                            </div>
                            <div class="divTableBody" id="get_view">
<%--                                <form class="borderless" id="get_view"></form>--%>
                            </div>
                        </div>

                    </div>
                    <div id="nav_view"></div>
                </div>
                <br>
                <% if (SS_USER_ID != null) { %>
                <div class="col-sm-12">
                    <div class="bg-light rounded h-100 p-4">
                        <form name="f" method="post" action="/user/getMultiviewYtaddress">
                            <div class="d-flex align-items-center justify-content-between mb-4">
                                <h6 class="mb-0">Add to BookMark</h6>
                            </div>
                            <div class="result_multiview_link">
                                <div class="input-group mb-3">
                                    <input type="text" id="yt_result_link" name="ytaddress" class="form-control" onclick="this.select();"
                                           readonly>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary notranslate" type="button" id="ytcopy"
                                                data-toggle="tooltip" data-placement="top" title="Copy to clipboard"
                                                onclick="yt_copytoclip()"><i class="fas fa-copy"></i></button>
                                        <button class="btn btn-primary notranslate" type="button" id="view"
                                                data-toggle="tooltip" data-placement="top" title="Open New Window"
                                                onclick="newytWindow()"><i class="fa-solid fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <% } %>

            </div>
        </div>
        <!-- Form End -->


        <!-- Footer Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded-top p-4">
                <div class="row">
                    <div class="col-12 col-sm-6 text-center text-sm-start">
                        &copy; <a href="#">MultiST</a>, All Right Reserved.
                    </div>
                    <div class="col-12 col-sm-6 text-center text-sm-end">
                        <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                        Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Footer End -->
    </div>
    <!-- Content End -->


    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
</div>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/lib/chart/chart.min.js"></script>
<script src="/lib/easing/easing.min.js"></script>
<script src="/lib/waypoints/waypoints.min.js"></script>
<script src="/lib/owlcarousel/owl.carousel.min.js"></script>
<script src="/lib/tempusdominus/js/moment.min.js"></script>
<script src="/lib/tempusdominus/js/moment-timezone.min.js"></script>
<script src="/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
<script>
    /* ##### 음성 인식 Annyang.js start ##### */
    if (annyang) {
        // Add our commands to annyang
        annyang.addCommands({});

        //음성인식 값 받아오기위한 객체 생성
        let recognition = annyang.getSpeechRecognizer();
        //최종 음성인식 결과값 저장 변수
        let final_transcript = "";
        //말하는 동안에 인식되는 값 가져오기(허용)
        recognition.interimResults = false;
        //음성 인식결과 가져오기
        recognition.onresult = function (event) {
            final_transcript = "";
            for (let i = event.resultIndex; i < event.results.length; ++i) {
                if (event.results[i].isFinal) {
                    final_transcript += event.results[i][0].transcript;
                }
            }
            console.log("final :" + final_transcript);

            // 사용자 음성 html append

            let resHTML = "";
            $("#search_box").val(final_transcript);

        };

        // Tell KITT to use annyang
        SpeechKITT.annyang();

        // Define a stylesheet for KITT to use
        SpeechKITT.setStylesheet('/css/flat.css');

        // Render KITT's interface
        SpeechKITT.vroom();
    }
    /* ##### 음성 인식 Annyang.js end ##### */
</script>

<!-- Template Javascript -->
<script src="/js/main.js"></script>
</body>

</html>