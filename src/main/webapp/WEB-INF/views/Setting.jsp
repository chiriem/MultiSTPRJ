<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
%>
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

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script type="text/javascript">
        function getVideoId() {


            var $gvideo = $('#youtube_url').val();
            //get video ID
            var vid;
            if ($gvideo.indexOf('youtube.com/channel/') != -1) {
                var str1 = $gvideo.split('youtube.com/channel/');
                vid = str1[1];
            } else if ($gvideo.indexOf('youtube.com/c/') != -1) {
                var str1 = $gvideo.split('youtube.com/c/');
                vid = str1[1];
            } else if ($gvideo.indexOf('youtu.be/c/') != -1) {
                var str1 = $gvideo.split('youtu.be/c/');
                vid = str1[1];
            }
            if (vid.indexOf('&') != -1) {
                var str2 = vid.split('&');
                vid = str2[0];
            }
            var t_videoId = vid.trim();
            t_videoId = "yt notify add " + t_videoId;
            var result = $('#result_link');
            result.val(t_videoId);

            // $("#result_code").append('<input type="text" id="result_link" class="form-control" onclick="this.select();" readonly>');
        }
        function copytoclip(){
            $('#result_link').select();
            document.execCommand("copy");
            add_toast('Success Info', 'Copied to clipboard.<br>Paste it where you need it with (Ctrl+v).');
            //add_toast('Warning Info', data.error);
        }
    </script>
    <link href="/youtube_tool/css/multi_view.css" rel="stylesheet">
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


    <!-- Sidebar Start -->
    <div class="sidebar pe-4 pb-3">
        <nav class="navbar bg-light navbar-light">
            <a href="index" class="navbar-brand mx-4 mb-3">
                <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>MultiStudio</h3>
            </a>
            <div class="navbar-nav w-100">
                <a href="/index" class="nav-item nav-link"><i class="fa fa-youtube-play"
                                                              aria-hidden="false"></i>Main</a>
                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-youtube-play"
                                                                                aria-hidden="false"></i>MultiStudio</a>
                <a href="/notice/NoticeList" class="nav-item nav-link"><i class="fa fa-book" aria-hidden="false"></i>Notice</a>
                <a href="/Search2" class="nav-item nav-link"><i class="fa fa-search" aria-hidden="false"></i>Search</a>
            </div>
        </nav>
    </div>
    <!-- Sidebar End -->


    <!-- Content Start -->
    <div class="content">
        <!-- Navbar Start -->
        <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
            <a href="index" class="navbar-brand d-flex d-lg-none me-4">
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
                        <a href="/Setting" class="dropdown-item">My Profile</a>
                        <% if(SS_USER_ID != null){ %>
                        <a href="/logout" class="dropdown-item">Log out<a>
                                <%} else {%>
                            <a href ="/user/loginForm" class="dropdown-item">Sign in<a>
                                <a href="/user/UserRegForm" class="dropdown-item">Sign up</a>
                                    <%} %>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Navbar End -->


        <!-- Form Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-sm-12 col-xl-6">
                    <div class="bg-light rounded h-100 p-4">
                        <h6 class="mb-4">Getting Key</h6>
                        <h6 class="mb-4">Getting YouTube Channel Key</h6>
                        <form>
                            <div class="mb-3">
                                <label class="form-label">Youtube address</label>
                                <input id="youtube_url" type="text" class="form-control"
                                       placeholder="예: https://www.youtube.com/watch?v=WMweEpGlu_U" aria-label=""
                                       aria-describedby="button-addon2" >
                                <button class="btn btn-primary notranslate" type="button" onclick="getVideoId()">
                                    GetKey!
                                </button>

                                <div id="keyHelp" class="form-text">Can get youtube channel key
                                </div>
                            </div>
                            <div class="mb-3">
                                <input type="text" id="result_link" class="form-control" onclick="this.select();" readonly>
                                <div class="input-group-append">
                                    <button class="btn btn-primary notranslate" type="button" id="copy"
                                            data-toggle="tooltip" data-placement="top" title="Copy to clipboard"
                                            onclick="copytoclip()"><i class="fas fa-copy"></i></button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
                <div class="col-sm-12 col-xl-6 text-center">
                    <div class="bg-light rounded h-100 p-4">
                        <iframe src="https://titanembeds.com/embed/979011041608474694" height="600" width="90%"
                                frameborder="0"></iframe>
                    </div>
                </div>

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
</body>

</html>