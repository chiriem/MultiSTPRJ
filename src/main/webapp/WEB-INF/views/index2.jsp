<%@ page import="kopo.poly.service.impl.UserInfoService" %>
<%@ page import="kopo.poly.persistance.mongodb.impl.UserInfoMapper" %>
<%@ page import="kopo.poly.controller.UserInfoController" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.dto.SStudioDTO" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");

    List<SStudioDTO> rList = (List<SStudioDTO>) request.getAttribute("rList");

    // 동영상 조회 결과 보여주기
    if (rList == null) {
        rList = new ArrayList<SStudioDTO>();

    }
%>
<%
    String msg = "";
    if (SS_USER_ID != null) {
        msg = SS_USER_ID + "님 환영합니다!";
    } else {
       msg = "로그인에 실패하였습니다.";
    }
%>
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

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/table_with_div.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script src="/js/jquery-3.6.0.js"></script>
    <script type="text/javascript">

        //삭제로 이동
        function doDelete(seq) {

            console.log("doDelete")
            location.href = "/deleteYt?nSeq=" + seq;
        }

        //생방 상세보기 이동
        function doLiveDetail(seq) {
            location.href = "/SingleST/LiveSStud?nSeq=" + seq;
        }

        //생방 삭제로 이동
        function doLiveDelete(seq) {

            console.log("doLiveDelete")
            location.href = "/deleteLiveYt?yt_address=" + seq;
        }

        function newytWindow(){

            let option = "width = 1000, height = 500, location=0,toolbar=no,scrollbars=no,resizable=no,status=no,menubar=no";

            window.open('SingleST/LiveSStudioadd', '_blank', option);
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

            <h6>
            </h6>
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


        <!-- YtList(seng) Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-sm-12 col-xl-12">
                    <div class="bg-light rounded p-4">
                        <form name="f" method="post" action="/user/getLiveYtaddress">
                            <div class="d-flex align-items-center justify-content-between mb-4">
                                <h6 class="mb-0">라이브
                                    &nbsp&nbsp
                                    <% if (SS_USER_ID != null) { %>
                                    <button class="btn btn-primary notranslate" type="button" id="view"
                                            data-toggle="tooltip" data-placement="top" title="Open New Window"
                                            onclick="newytWindow()"><i class="fa-solid fa-plus"></i></button>
                                    <%} %>
                                </h6>
                                <button type="submit" class="btn btn-primary m-2">load!</button>
                            </div>

                            <div class="table-responsive">
                                <div class="divTable minimalistBlack">
                                    <div class="divTableHeading">
                                        <div class="divTableRow">
                                            <div class="divTableHead" style="width: 200px">thumbnail</div>
                                            <div class="divTableHead">Title</div>
                                            <div class="divTableHead" style="width: 100px">Delete</div>
                                        </div>
                                    </div>

                                    <div class="divTableBody">
                                        <%
                                            for (int i = 0; i < rList.size(); i++) {
                                                SStudioDTO rDTO = rList.get(i);

                                                if (rDTO == null) {
                                                    rDTO = new SStudioDTO();
                                                }

                                        %>
                                        <div class="divTableRow">
                                            <div class="divTableCell"><img class="tnail"
                                            <%--                                                                           src="http://img.youtube.com/vi/<%=CmmUtil.nvl(rDTO.getYt_address()) %>/mqdefault.jpg"--%>
                                                                           src="<%=CmmUtil.nvl(rDTO.getThumbnailPath())%>"
                                                                           width="180" height="120">
                                            </div>
                                            <div class="divTableCell" id="<%=CmmUtil.nvl(rDTO.getYt_seq())%>">
                                                <a href="javascript:doLiveDetail('<%=CmmUtil.nvl(rDTO.getYt_seq())%>');">
                                                    <%=CmmUtil.nvl(rDTO.getTitle())%>
                                                </a>
                                            </div>
                                            <div class="divTableCell" style="width: 100px">
                                                <a href="javascript:doLiveDelete('<%=CmmUtil.nvl(rDTO.getYt_address())%>');">
                                                    <%--                                                    <button class="btn btn-square btn-primary m-2"><i--%>
                                                    <%--                                                            class="fa-solid fa-file-circle-minus"></i></button>--%>
                                                    Delete!
                                                </a>
                                            </div>
                                        </div>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
        <!-- YtList(seng) End -->

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
<script src="/js/jquery-3.6.0.js"></script>
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