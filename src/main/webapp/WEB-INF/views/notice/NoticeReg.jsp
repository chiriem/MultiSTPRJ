<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
%>

<%
    session.setAttribute("SESSION_USER_ID", "USER01"); //세션 강제 적용, 로그인된 상태로 보여주기 위함

    List<NoticeDTO> rList = (List<NoticeDTO>) request.getAttribute("rList");

//게시판 조회 결과 보여주기
    if (rList == null) {
        rList = new ArrayList<NoticeDTO>();

    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="utf-8">
    <title>MultiStudio - Multiple Streaming Studio</title>
    <script type="text/javascript">

        //전송시 유효성 체크
        function doSubmit(f) {
            if (f.title.value == "") {
                alert("제목을 입력하시기 바랍니다.");
                f.title.focus();
                return false;
            }

            if (calBytes(f.title.value) > 200) {
                alert("최대 200Bytes까지 입력 가능합니다.");
                f.title.focus();
                return false;
            }

            // var noticeCheck = false; //체크 여부 확인 변수
            // for (var i = 0; i < f.noticeYn.length; i++) {
            //     if (f.noticeYn[i].checked) {
            //         noticeCheck = true;
            //     }
            // }

            // if (noticeCheck == false) {
            //     alert("공지글 여부를 선택하시기 바랍니다.");
            //     f.noticeYn[0].focus();
            //     return false;
            // }

            if (f.contents.value == "") {
                alert("내용을 입력하시기 바랍니다.");
                f.contents.focus();
                return false;
            }

            if (calBytes(f.contents.value) > 4000) {
                alert("최대 4000Bytes까지 입력 가능합니다.");
                f.contents.focus();
                return false;
            }


        }

        //글자 길이 바이트 단위로 체크하기(바이트값 전달)
        function calBytes(str) {

            var tcount = 0;
            var tmpStr = new String(str);
            var strCnt = tmpStr.length;

            var onechar;
            for (i = 0; i < strCnt; i++) {
                onechar = tmpStr.charAt(i);

                if (escape(onechar).length > 4) {
                    tcount += 2;
                } else {
                    tcount += 1;
                }
            }

            return tcount;
        }

    </script>
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
            location.href = "/notice/NoticeInfo?nSeq=" + seq;
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
            <a href="../index" class="navbar-brand mx-4 mb-3">
                <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>MultiStudio</h3>
            </a>
            <div class="navbar-nav w-100">
                <a href="../index" class="nav-item nav-link"><i class="fa fa-youtube-play" aria-hidden="false"></i>Main</a>
                <a href="../MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-youtube-play"
                                                                                  aria-hidden="false"></i>MultiStudio</a>
                <a href="NoticeList" class="nav-item nav-link active"><i class="fa fa-book" aria-hidden="false"></i>Notice</a>
                <a href="../Search2" class="nav-item nav-link"><i class="fa fa-search"
                                                                  aria-hidden="false"></i>Search</a>
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


        <!-- Form Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <form name="f" method="post" action="/notice/NoticeInsert" target="ifrPrc"
                      onsubmit="return doSubmit(this);">
                    <div class="row mb-3">
                        <label class="col-sm-1 col-form-label">제목</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="title" style="width: 530px;"
                                   placeholder=" Title">
                        </div>
                    </div>
                    <div class="form-floating">
                        <textarea class="form-control" name="contents" style="width: 600px; height: 400px"
                                  placeholder="Leave comment here"></textarea>
                        <label>Comments</label>
                    </div>
                    <button type="submit" class="btn btn-primary m-2">Submit!</button>
                    <button type="reset" class="btn btn-primary m-2">Reset</button>

                </form>
            </div>
        </div>

    </div>
    <!-- Form End -->

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
<!-- 프로세스 처리용 iframe / form 태그에서 target을 iframe으로 한다. -->
<iframe name="ifrPrc" style="display:none"></iframe>
</body>

</html>