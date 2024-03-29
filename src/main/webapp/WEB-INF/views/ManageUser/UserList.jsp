<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.controller.ManageUserController" %>
<%@ page import="kopo.poly.dto.ManageUserDTO" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");

    if (SS_USER_ID == null) {
        SS_USER_ID = "null";
    }

    //id가 "admin" 인 사람만 수정 가능하도록 하기(1: admin 아님 / 2: admin 확인 (수정 가능) / 3: 로그인 안함)
    int edit = 2;

    System.out.println("SS_USER_ID : " + SS_USER_ID);
%>

<%

    List<ManageUserDTO> rList = (List<ManageUserDTO>) request.getAttribute("rList");

//게시판 조회 결과 보여주기
    if (rList == null) {
        rList = new ArrayList<ManageUserDTO>();

    }

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
    <link href="/css/table_with_div.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script type="text/javascript">

        //상세보기 이동
        function doDetail(seq) {

            location.href = "/ManageUser/UserInfo?nSeq=" + seq;
        }

        //삭제로 이동
        function doDelete(seq) {

            if (seq == "17") {

            }

            console.log("doDelete")
            location.href = "/ManageUser/DeleteUser?nSeq=" + seq;
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
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i
                            class="fa fa-youtube-play me-2"></i>Main</a>
                    <div class="dropdown-menu bg-transparent border-0">
                        <a href="/index" class="dropdown-item">Youtube</a>
                        <a href="/index2" class="dropdown-item">Youtube LiveStream</a>
                    </div>
                </div>
                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-youtube-play me-2"
                                                                                aria-hidden="false"></i>MultiStudio</a>
<%--                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-twitch me-2"--%>
<%--                                                                                aria-hidden="false"></i>MultiTwitch</a>--%>
                <a href="/notice/NoticeList" class="nav-item nav-link"><i class="fa fa-book me-2"
                                                                          aria-hidden="false"></i>Notice</a>
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
            <div class="bg-light text-center rounded p-4">
                <div class="d-flex align-items-center justify-content-between mb-4">
                    <h6 class="mb-0">User Info</h6>
                </div>
                <div class="table-responsive">
                    <div class="divTable comicGreen">
                        <div class="divTableHeading">
                            <div class="divTableRow">
                                <div class="divTableHead" style="width: 120px">User Sequence</div>
                                <div class="divTableHead">User id</div>
                                <div class="divTableHead" style="width: 100px">User Name</div>
                                <div class="divTableHead" style="width: 100px">User Auth</div>
                                <div class="divTableHead" style="width: 100px">User Age</div>
                                <div class="divTableHead" style="width: 100px">Delete</div>
                            </div>
                        </div>

                        <div class="divTableBody">
                            <%
                                for (int i = 0; i < rList.size(); i++) {
                                    ManageUserDTO rDTO = rList.get(i);

                                    if (rDTO == null) {
                                        rDTO = new ManageUserDTO();
                                    }

                            %>
                            <div class="divTableRow">
                                <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getUser_seq()) %>
                                </div>
                                <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getUser_id()) %>
                                </div>
                                <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getUser_nm()) %>
                                </div>
                                <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getUser_auth()) %>
                                </div>
                                <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getAge()) %>
                                </div>
                                <div class="divTableCell">
                                    <a href="javascript:doDelete('<%=CmmUtil.nvl(rDTO.getUser_seq())%>');">
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