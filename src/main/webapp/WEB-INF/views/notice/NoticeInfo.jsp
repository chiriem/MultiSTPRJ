<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
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
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

    <%
        NoticeDTO rDTO = (NoticeDTO) request.getAttribute("rDTO");

        //공지글 정보를 못불러왔다면, 객체 생성
        if (rDTO == null) {
            rDTO = new NoticeDTO();

        }

        String ss_user_id = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));

        //본인이 작성한 글만 수정 가능하도록 하기(1:작성자 아님 / 2: 본인이 작성한 글 / 3: 로그인안함)
        int edit = 1;

        //로그인 안했다면....
        if (ss_user_id.equals("")) {
            edit = 3;

            //본인이 작성한 글이면 2가 되도록 변경
        } else if (ss_user_id.equals(CmmUtil.nvl(rDTO.getUser_id()))) {
            edit = 2;

        }

        System.out.println("user_id : " + CmmUtil.nvl(rDTO.getUser_id()));
        System.out.println("ss_user_id : " + ss_user_id);

    %>

    <script type="text/javascript">

        //수정하기
        function doEdit() {
            if ("<%=edit%>" == 2) {
                location.href = "/notice/NoticeEditInfo?nSeq=<%=CmmUtil.nvl(rDTO.getNotice_seq())%>";

            } else if ("<%=edit%>" == 3) {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 수정 가능합니다.");

            }
        }


        //삭제하기
        function doDelete() {
            if ("<%=edit%>" == 2) {
                if (confirm("작성한 글을 삭제하시겠습니까?")) {
                    <%--location.href="/notice/NoticeDelete?nSeq=<%=CmmUtil.nvl(rDTO.getNotice_seq())%>";--%>
                    //location.href="/notice/NoticeDelete";

                    let f = document.getElementById("f");


                    f.action = "/notice/NoticeDelete";
                    f.submit();

                }

            } else if ("<%=edit%>" == 3) {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 삭제 가능합니다.");

            }
        }

        //목록으로 이동
        function doList() {
            location.href = "/notice/NoticeList";

        }

    </script>

</head>

<body>
<div class="container-xxl position-relative bg-white d-flex p-0">


    <!-- Sidebar Start -->
    <div class="sidebar pe-4 pb-3">
        <nav class="navbar bg-light navbar-light">
            <a href="index" class="navbar-brand mx-4 mb-3">
                <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>MultiStudio</h3>
            </a>
            <div class="navbar-nav w-100">
                <a href="../index" class="nav-item nav-link"><i class="fa fa-youtube-play" aria-hidden="false"></i>Main</a>
                <a href="../MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-youtube-play"
                                                                                aria-hidden="false"></i>MultiStudio</a>
                <a href="/NoticeList" class="nav-item nav-link active"><i class="fa fa-book" aria-hidden="false"></i>Notice</a>
                <a href="../Search2" class="nav-item nav-link"><i class="fa fa-search" aria-hidden="false"></i>Search</a>
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
                        <% if (SS_USER_ID != null) { %>
                        <a href="/logout" class="dropdown-item">Log out<a>
                                <%} else {%>
                            <a href="/user/loginForm" class="dropdown-item">Sign in<a>
                                <a href="/user/UserRegForm" class="dropdown-item">Sign up</a>
                                    <%} %>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Navbar End -->

        <!-- Info Start -->


        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <form id="f" method="post">
                    <input type="hidden" name="nSeq" value="<%=CmmUtil.nvl(rDTO.getNotice_seq())%>"/>
                </form>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">제목</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title" style="width: 430px;"
                               value="<%=CmmUtil.nvl(rDTO.getTitle())%>" readonly>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">작성일</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title" style="width: 430px;"
                               value="<%=CmmUtil.nvl(rDTO.getReg_dt())%>" readonly>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">조회수</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title" style="width: 430px;"
                               value="<%=CmmUtil.nvl(rDTO.getRead_cnt())%>" readonly>
                    </div>
                </div>
                <div class="form-floating">
                    <textarea class="form-control" name="contents"
                              style="width: 600px; height: 400px"><%=CmmUtil.nvl(rDTO.getContents()).replaceAll("\r\n", "<br/>") %></textarea>
                </div>
                <button type="button" class="btn btn-primary m-2" onclick="doEdit()">수정</button>
                <button type="button" class="btn btn-primary m-2" onclick="doDelete()">삭제</button>
                <button type="button" class="btn btn-primary m-2" onclick="doList()">목록</button>
                <!-- 프로세스 처리용 iframe / form 태그에서 target을 iframe으로 한다. -->
                <iframe name="ifrPrc" style="display:none"></iframe>

            </div>
        </div>
        <!-- Info End -->


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
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/chart/chart.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/waypoints/waypoints.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>
<script src="lib/tempusdominus/js/moment.min.js"></script>
<script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
<script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
</body>

</html>