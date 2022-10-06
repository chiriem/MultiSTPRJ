<%@ page import="kopo.poly.util.CmmUtil" %>
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
    <link href="/css/calendar.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <script src="/js/jquery-3.6.0.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            calendarInit();
        });
        /*
            달력 렌더링 할 때 필요한 정보 목록

            현재 월(초기값 : 현재 시간)
            금월 마지막일 날짜와 요일
            전월 마지막일 날짜와 요일
        */

        function calendarInit() {

            // 날짜 정보 가져오기
            var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
            var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
            var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
            var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)

            var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
            // 달력에서 표기하는 날짜 객체


            var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
            var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
            var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

            // kst 기준 현재시간
            // console.log(thisMonth);

            // 캘린더 렌더링
            renderCalender(thisMonth);

            function renderCalender(thisMonth) {

                // 렌더링을 위한 데이터 정리
                currentYear = thisMonth.getFullYear();
                currentMonth = thisMonth.getMonth();
                currentDate = thisMonth.getDate();

                // 이전 달의 마지막 날 날짜와 요일 구하기
                var startDay = new Date(currentYear, currentMonth, 0);
                var prevDate = startDay.getDate();
                var prevDay = startDay.getDay();

                // 이번 달의 마지막날 날짜와 요일 구하기
                var endDay = new Date(currentYear, currentMonth + 1, 0);
                var nextDate = endDay.getDate();
                var nextDay = endDay.getDay();

                // console.log(prevDate, prevDay, nextDate, nextDay);

                // 현재 월 표기
                $('.year-month').text(currentYear + '.' + (currentMonth + 1));

                // 렌더링 html 요소 생성
                calendar = document.querySelector('.dates')
                calendar.innerHTML = '';

                // 지난달
                for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
                    calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + i + '</div>'
                }
                // 이번달
                for (var i = 1; i <= nextDate; i++) {
                    calendar.innerHTML = calendar.innerHTML + '<div class="day current">' + i + '</div>'
                }
                // 다음달
                for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
                    calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + i + '</div>'
                }

                // 오늘 날짜 표기
                if (today.getMonth() == currentMonth) {
                    todayDate = today.getDate();
                    var currentMonthDate = document.querySelectorAll('.dates .current');
                    currentMonthDate[todayDate -1].classList.add('today');
                }
            }

            // 이전달로 이동
            $('.go-prev').on('click', function() {
                thisMonth = new Date(currentYear, currentMonth - 1, 1);
                renderCalender(thisMonth);
            });

            // 다음달로 이동
            $('.go-next').on('click', function() {
                thisMonth = new Date(currentYear, currentMonth + 1, 1);
                renderCalender(thisMonth);
            });
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
            &nbsp&nbsp
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

        <!-- YtList Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="row g-4">
                <div class="col-sm-12 col-xl-12">
                    <div class="bg-light rounded p-4">
                        <div class="sec_cal">
                            <div class="cal_nav">
                                <a href="javascript:;" class="nav-btn go-prev">prev</a>
                                <div class="year-month"></div>
                                <a href="javascript:;" class="nav-btn go-next">next</a>
                            </div>
                            <div class="cal_wrap">
                                <div class="days">
                                    <div class="day">월</div>
                                    <div class="day">화</div>
                                    <div class="day">수</div>
                                    <div class="day">목</div>
                                    <div class="day">금</div>
                                    <div class="day">토</div>
                                    <div class="day">일</div>
                                </div>
                                <div class="dates"></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- YtList End -->


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