<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");

    // 채팅방 명
    String roomname = CmmUtil.nvl(request.getParameter("roomname"));

    // 채팅방 입장전 입력한 별명
    String nickname = CmmUtil.nvl(request.getParameter("nickname"));

%>
<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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
    <script src="/js/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script type="text/javascript">

        let data = {};//전송 데이터(JSON)
        let ws; // 웹소켓 객체
        const roomname = "<%=roomname%>"; // 채팅룸 이름
        const nickname = "<%=nickname%>"; // 채팅유저 이름

        $(document).ready(function () {

            let btnSend = document.getElementById("btnSend");
            btnSend.onclick = function () {
                send(); //Send 버튼 누르면, Send함수 호출하기
            }

            //웹소켓 객체를 생성하는중
            console.log("openSocket");
            if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
                console.log("WebSocket is already opened.");
                return;

            }

            // 접속 URL 예 : ws://localhost:11000/ws/테스트방/별명
            ws = new WebSocket("ws://" + location.host + "/ws/" + roomname + "/" + nickname);

            // 웹소켓 열기
            ws.onopen = function (event) {
                if (event.data === undefined)
                    return;

                console.log(event.data)
            };

            //웹소캣으로부터 메세지를 받을 때마다 실행됨
            ws.onmessage = function (msg) {

                // 웹소켓으로부터 받은 데이터를 JSON 구조로 변환하기
                let data = JSON.parse(msg.data);

                if (data.name === nickname) { // 내가 발송한 채팅 메시지는 파란색 글씩
                    $("#chat").append("<div>");
                    $("#chat").append("<span style='color: blue'><b>[보낸 사람] : </b></span>");
                    $("#chat").append("<span style='color: blue'> 나 </span>");
                    $("#chat").append("<span style='color: blue'><b>[발송 메시지] : </b></span>");
                    $("#chat").append("<span style='color: blue'>" + data.msg + " </span>");
                    $("#chat").append("<span style='color: blue'><b>[발송시간] : </b></span>");
                    $("#chat").append("<span style='color: blue'>" + data.date + " </span>");
                    $("#chat").append("</div>");

                } else if (data.name === "관리자") { // 관리자가 발송한 채팅 메시지는 빨간색 글씩
                    $("#chat").append("<div>");
                    $("#chat").append("<span style='color: red'><b>[보낸 사람] : </b></span>");
                    $("#chat").append("<span style='color: red'>" + data.name + "</span>");
                    $("#chat").append("<span style='color: red'><b>[발송 메시지] : </b></span>");
                    $("#chat").append("<span style='color: red'>" + data.msg + " </span>");
                    $("#chat").append("<span style='color: red'><b>[발송시간] : </b></span>");
                    $("#chat").append("<span style='color: red'>" + data.date + " </span>");
                    $("#chat").append("</div>");

                } else {
                    $("#chat").append("<div>"); // 그 외 채팅참여자들이 발송한 채팅 메시지는 검정색
                    $("#chat").append("<span><b>[보낸 사람] : </b></span>");
                    $("#chat").append("<span>" + data.name + " </span>");
                    $("#chat").append("<span><b>[수신 메시지] : </b></span>");
                    $("#chat").append("<span>" + data.msg + " </span>");
                    $("#chat").append("<span><b>[발송시간] : </b></span>");
                    $("#chat").append("<span>" + data.date + " </span>");
                    $("#chat").append("</div>");

                }
            }
        });

        // 채팅 메시지 보내기
        function send() {

            let msgObj = $("#msg"); // Object

            if (msgObj.value !== "") {
                data.name = nickname; // 별명
                data.msg = msgObj.val();  // 입력한 메시지

                // 데이터 구조를 JSON 형태로 변경하기
                let temp = JSON.stringify(data);

                // 채팅 메시지 전송하기
                ws.send(temp);
            }

            // 채팅 메시지 전송 후, 입력한 채팅내용 지우기
            msgObj.val("");
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
                <a href="/MultiStudio/MultiStudio" class="nav-item nav-link"><i class="fa fa-twitch me-2"
                                                                                aria-hidden="false"></i>MultiTwitch</a>
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
            <div class="row g-4" style="min-height: 50vh;">
                <div class="col-12">
                    <div class="bg-light rounded p-4 ">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <b>ChatRoom : <%=roomname%> </b>
                        </div>
                        <hr/>
                        <div id="chat"></div>
                        <hr/>
                        <div>
                            <label for="msg">전달할 메시지 : </label><input type="text" class="form-control" id="msg">
                            <button class="btn btn-primary m-2" id="btnSend">Send!</button>
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
