<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.dto.SStudioDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%

    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
    String yt_address = (String) session.getAttribute("yt_address");

%>
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

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script src="/js/jquery-3.6.0.js"></script>
    <%--    <script>--%>

    <%--        var playlist = 'CuklIb9d3fI';--%>
    <%--        //https://www.youtube.com/watch?v=유튜브 영상 고유번호--%>
    <%--        //playlist만 원하는 재생목록에 따라 가져오면 됨--%>

    <%--        //maxResult는 50 이하--%>
    <%--        $(document).ready(function () {--%>
    <%--            $.get(--%>
    <%--                "https://www.googleapis.com/youtube/v3/videos", {--%>
    <%--                    part: 'snippet',--%>
    <%--                    maxResults: 5,--%>
    <%--                    id: playlist,--%>
    <%--                    key: 'AIzaSyAfJQyw0LqcMkaJi0hCw35NUPyjV7Br-4g'--%>
    <%--                },--%>

    <%--                function (data) {--%>
    <%--                    var output;--%>
    <%--                    $.each(data.items, function (i, item) {--%>
    <%--                        console.log(item);--%>
    <%--                        vTitle = item.snippet.title;--%>
    <%--                        vId = item.snippet.channelId;--%>
    <%--                        vDe = item.snippet.description;--%>
    <%--                        vTh = item.snippet.channelTitle;--%>
    <%--                        vaaa = item.snippet.thumbnails.standard.url;--%>
    <%--                        output = '<li>' + vTitle + '<br>--videodescription: ' + vDe + '<br>--videothumbnails: ' + vTh + '<br></li>';--%>
    <%--                        /*output= '<li>'+vTitle+'<iframe src=\"//www.youtube.com/embed/'+vId+'\"></iframe></li>';*/--%>
    <%--                        $("#results").append(output);--%>
    <%--                    })--%>
    <%--                }--%>
    <%--            );--%>

    <%--        });--%>

    <%--    </script>--%>
    <script type="text/javascript">
        <%

        System.out.println("yt_address : " + yt_address);

    %>

        var playlist = "<%=yt_address%>";
        // var playlist = 'CuklIb9d3fI';
        //https://www.youtube.com/watch?v=유튜브 영상 고유번호
        //playlist만 원하는 재생목록에 따라 가져오면 됨

        //maxResult는 50 이하
        $(document).ready(function () {
            $.get(
                "https://www.googleapis.com/youtube/v3/videos", {
                    part: 'snippet',
                    maxResults: 5,
                    id: playlist,
                    key: 'AIzaSyAfJQyw0LqcMkaJi0hCw35NUPyjV7Br-4g'
                },

                function (data) {
                    var output;
                    $.each(data.items, function (i, item) {
                        console.log(item);
                        vTitle = item.snippet.title;
                        vId = item.snippet.channelId;
                        vDe = item.snippet.description;
                        vTh = item.snippet.channelTitle;

                        $("#youtube_title").append(vTitle);
                        $("#youtube_chname").append(vTh);
                        $("#youtube_desc").append(vDe);
                    })
                }
            );

        });
    </script>

</head>

<body>
<div class="container-xxl position-relative bg-white d-flex p-0">


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
                <a href="/notice/NoticeList" class="nav-item nav-link"><i class="fa fa-book me-2" aria-hidden="false"></i>Notice</a>
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
                        <a href="/user/loginForm" class="dropdown-item">Sign in</a>
                        <a href="/user/UserRegForm" class="dropdown-item">Sign up</a>
                        <%} %>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Navbar End -->


        <!-- Blank Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row bg-light rounded mx-0">
                <iframe width="100%" height="480" src="https://www.youtube.com/embed/<%=yt_address%>"
                        title="YouTube video player" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen></iframe>
            </div>
        </div>
        <div class="container-fluid pt-4 px-4">
            <div class="row bg-light rounded mx-0">
                <div>
                    <form id="youtube_title"></form>
                    <hr>
                    <form id="youtube_chname"></form>
                    <hr>
                    <form id="youtube_desc"></form>
                </div>
            </div>
        </div>
        <!-- Blank End -->


        <!-- Footer Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded-top p-4">
                <div class="row">
                    <div class="col-12 col-sm-6 text-center text-sm-start">
                        &copy; <a href="#">Your Site Name</a>, All Right Reserved.
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
</body>

</html>