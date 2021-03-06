package kopo.poly.controller;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * UserInfo는 사용자의 정보를 관리하며, 사용자 정보를 담당하는 페이지입니다.
 */
@Slf4j
@Controller
public class UserInfoController {

    /**
     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "UserInfoService")
    private IUserInfoService userInfoService;

    // MongoDB 컬렉션 이름
    private String colNm = "UserInfoCollection";

    /**
     * GetMapping은 GET방식을 통해 접속되는 URL 호출에 대해 실행되는 함수로 설정함을 의미함
     * PostMapping은 POST방식을 통해 접속되는 URL 호출에 대해 실행되는 함수로 설정함을 의미함
     * GetMapping(value = "index") =>  GET방식을 통해 접속되는 URL이 index인 경우 아래 함수를 실행함
     */

    /**
     * 회원가입 화면으로 이동
     */
    @GetMapping(value = "user/UserRegForm")
    public String userRegForm() {

        log.info(this.getClass().getName() + ".user/userRegForm ok!");

        return "user/UserRegForm";
    }

    // 아이디 중복 검사
    @RequestMapping(value = "/user/memberIdChk", method = RequestMethod.POST)
    @ResponseBody
    public String memberIdChkPOST(HttpServletRequest request) throws Exception{

        log.info("memberIdChk() 진입");

        String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디

        log.info("user_id : " + user_id);

        int res = 0;

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;



        pDTO = new UserInfoDTO();

        pDTO.setUser_id(user_id);

        /*
         * #######################################################
         *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
         *
         *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
         * #######################################################
         */

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 userInfoService 호출하기
        UserInfoDTO rDTO = userInfoService.getUserIdCheck(pDTO, colNm);

        if (CmmUtil.nvl(rDTO.getUser_id()).length()>0) {
            res = 1;
        }

        log.info("res : " + res);

        String rDTO_user_id = (String) rDTO.getUser_nm();

        log.info("rDTO_user_id : " + rDTO_user_id);
        /*
         * 로그인을 성공했다면, 회원아이디 정보를 session에 저장함
         *
         * 세션은 톰켓(was)의 메모리에 존재하며, 웹사이트에 접속한 사람(연결된 객체)마다 메모리에 값을 올린다.
         *           *
         * 예) 톰켓에 100명의 사용자가 로그인했다면, 사용자 각각 회원아이디를 메모리에 저장하며.
         *    메모리에 저장된 객체의 수는 100개이다.
         *    따라서 과도한 세션은 톰켓의 메모리 부하를 발생시켜 서버가 다운되는 현상이 있을 수 있기때문에,
         *    최소한으로 사용하는 것을 권장한다.
         *
         * 스프링에서 세션을 사용하기 위해서는 함수명의 파라미터에 HttpSession session 존재해야 한다.
         * 세션은 톰켓의 메모리에 저장되기 때문에 url마다 전달하는게 필요하지 않고,
         * 그냥 메모리에서 부르면 되기 때문에 jsp, controller에서 쉽게 불러서 쓸수 있다.
         * */

        if(res == 1) {

            return "fail";	// 중복 아이디가 존재

        } else {

            return "success";	// 중복 아이디 x

        }

    } // memberIdChkPOST() 종료

    /**
     * 회원가입 로직 처리
     */
    @RequestMapping(value = "user/insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");

        //회원가입 결과에 대한 메시지를 전달할 변수
        String msg = "";

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String user_nm = CmmUtil.nvl(request.getParameter("user_nm")); //이름
            String user_pw = CmmUtil.nvl(request.getParameter("user_pw")); //비밀번호
            String age = CmmUtil.nvl(request.getParameter("age")); //생년
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                   반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : " + user_id);
            log.info("password : " + user_pw);
            log.info("age : " + age);


            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */


            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_nm(user_nm);

            //비밀번호,이메일은 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setUser_pw(EncryptUtil.encHashSHA256(user_pw));


            pDTO.setAge(age);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 회원가입
             * */
            int res = userInfoService.insertUserInfo(pDTO, colNm);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                model.addAttribute("msg", "회원정보 입력 성공! 로그인 페이지로 이동합니다");
                model.addAttribute("url", "/user/LoginForm");

//                msg = "회원가입되었습니다.";

                //추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복을 체크하길 바람
            } else {
                model.addAttribute("msg", "오류, 혹은 정보 중복으로 인해 회원가입에 실패하였습니다.");
                model.addAttribute("url", "/user/UserRegForm");
            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e.toString();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".insertUserInfo end!");

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }

        return "user/SignUpMsg";
    }


    /**
     * 로그인을 위한 입력 화면으로 이동
     */
    @RequestMapping(value = "user/LoginForm")
    public String loginForm() {
        log.info(this.getClass().getName() + ".user/LoginForm ok!");

        return "/user/LoginForm";
    }

    @RequestMapping(value = "user/UseradjustForm")
    public String adjustForm() {
        log.info(this.getClass().getName() + ".user/adjustForm ok!");

        return "/user/UseradjustForm";
    }

    @RequestMapping(value = "user/UserdeleteForm")
    public String deleteForm() {
        log.info(this.getClass().getName() + ".user/deleteForm ok!");

        return "/user/UserdeleteForm";
    }

    /**
     * 로그인 처리 및 결과 알려주는 화면으로 이동
     */
    @RequestMapping(value = "user/getUserLoginCheck")
    public String getUserLoginCheck(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                    ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".getUserLoginCheck start!");

        //로그인 처리 결과를 저장할 변수 (로그인 성공 : 1, 아이디, 비밀번호 불일치로인한 실패 : 0, 시스템 에러 : 2)
        int res = 0;

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String password = CmmUtil.nvl(request.getParameter("password")); //비밀번호
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                   반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : " + user_id);
//            log.info("password : " + password);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */


            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setUser_pw(EncryptUtil.encHashSHA256(password));

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 userInfoService 호출하기
            UserInfoDTO rDTO = userInfoService.getUserLoginCheck(pDTO, colNm);

            if (CmmUtil.nvl(rDTO.getUser_id()).length()>0) {
                res = 1;
            }

            log.info("res : " + res);

            String user_nm = (String) rDTO.getUser_nm();
            String age = (String) rDTO.getAge();

            log.info("rDTO_user_name : " + user_nm);
            log.info("rDTO_age : " + age);
            /*
             * 로그인을 성공했다면, 회원아이디 정보를 session에 저장함
             *
             * 세션은 톰켓(was)의 메모리에 존재하며, 웹사이트에 접속한 사람(연결된 객체)마다 메모리에 값을 올린다.
             *           *
             * 예) 톰켓에 100명의 사용자가 로그인했다면, 사용자 각각 회원아이디를 메모리에 저장하며.
             *    메모리에 저장된 객체의 수는 100개이다.
             *    따라서 과도한 세션은 톰켓의 메모리 부하를 발생시켜 서버가 다운되는 현상이 있을 수 있기때문에,
             *    최소한으로 사용하는 것을 권장한다.
             *
             * 스프링에서 세션을 사용하기 위해서는 함수명의 파라미터에 HttpSession session 존재해야 한다.
             * 세션은 톰켓의 메모리에 저장되기 때문에 url마다 전달하는게 필요하지 않고,
             * 그냥 메모리에서 부르면 되기 때문에 jsp, controller에서 쉽게 불러서 쓸수 있다.
             * */
            if (res == 1) { //로그인 성공

                /*
                 * 세션에 회원아이디 저장하기, 추후 로그인여부를 체크하기 위해 세션에 값이 존재하는지 체크한다.
                 * 일반적으로 세션에 저장되는 키는 대문자로 입력하며, 앞에 SS를 붙인다.
                 *
                 * Session 단어에서 SS를 가져온 것이다.
                 */
                session.setAttribute("SS_USER_ID", user_id);
                session.setAttribute("SS_USER_NM", user_nm);
                session.setAttribute("SS_AGE", age);

                model.addAttribute("msg", "로그인 성공");
                model.addAttribute("url", "/index");

            } else if (res == 0){

                model.addAttribute("msg", "로그인을 실패하였습니다");
                model.addAttribute("url", "/user/LoginForm");
            }

        } catch (Exception e) {

            res = 2;
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".insertUserInfo end!");

            /* 로그인 처리 결과를 jsp에 전달하기 위해 변수 사용
             * 숫자 유형의 데이터 타입은 값을 전달하고 받는데 불편함이  있어
             * 문자 유형(String)으로 강제 형변환하여 jsp에 전달한다.
             * */
            model.addAttribute("res", String.valueOf(res));


            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;
        }

//        return "/index";
        return "user/SignInMsg";
    }

    @RequestMapping("/logout")
    public String UserLogout(HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".Logout start!");

        session.invalidate();

        log.info(this.getClass().getName() + ".Logout end!");

        return "redirect:/index";
    }

    /**
     * 회원수정 로직 처리
     * */
    @GetMapping(value="user/updateUserInfo")
    public String updateUserInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".updateUserInfo start!");

        //회원수정 결과에 대한 메시지를 전달할 변수
        String msg = "";

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try{

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //이름
            String user_nm = CmmUtil.nvl(request.getParameter("user_nm")); //이름
            String user_pw = CmmUtil.nvl(request.getParameter("user_pw")); //비밀번호
            String age = CmmUtil.nvl(request.getParameter("age")); //출생년도
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                   반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : "+ user_id);
            log.info("user_name : "+ user_nm);
            log.info("password : "+ user_pw);
            log.info("age : "+ age);


            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */


            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_nm(user_nm);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setUser_pw(EncryptUtil.encHashSHA256(user_pw));

            pDTO.setAge(age);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 회원가입 수정
             * */
            int res = userInfoService.updateUserInfo(pDTO, colNm);

            log.info("회원가입 결과(res) : " + res);

            if (res==1) {

                model.addAttribute("msg", "회원 정보 수정 성공! 다시 로그인 해주시길 바랍니다");
                model.addAttribute("url", "/user/LoginForm");

            }else {
                model.addAttribute("msg", "수정에 실패하였습니다. 재시도 하거나 관리자에게 문의하시길 바랍니다");
                model.addAttribute("url", "/index");
            }

        }catch(Exception e){
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".updateUserInfo end!");


            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }
        session.invalidate();

        return "user/UseradjustMsg";
    }


    /**
     * 회원삭제 로직 처리
     * */
    @GetMapping(value="user/deleteUserInfo")
    public String deleteUserInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".deleteUserInfo start!");

        //회원수정 결과에 대한 메시지를 전달할 변수
        String msg = "";

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try{

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //이름
            String user_pw = CmmUtil.nvl(request.getParameter("user_pw")); //비밀번호
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                   반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : "+ user_id);
            log.info("password : "+ user_pw);


            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */


            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setUser_pw(EncryptUtil.encHashSHA256(user_pw));

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 회원삭제
             * */
            int res = userInfoService.deleteUserInfo(pDTO, colNm);

            log.info("회원삭제 결과(res) : " + res);

            if (res==1) {
                model.addAttribute("msg", "탈퇴하였습니다. 다음에 또 만날 수 있기를..");
                model.addAttribute("url", "/index");

            }else {
                model.addAttribute("msg", "탈퇴에 실패하였습니다. 관리자에게 문의하시길 바랍니다");
                model.addAttribute("url", "/Setting");
            }

        }catch(Exception e){
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".deleteUserInfo end!");

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }
        session.invalidate();

        return "user/UserDeleteMsg";
    }


}