package kopo.poly.controller;

import kopo.poly.dto.ManageUserDTO;
import kopo.poly.service.IManageUserService;
import kopo.poly.util.CmmUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Notice는 공지사항을 관리하며, 관리가 가능한 페이지입니다.
 */
@Controller
public class ManageUserController {
    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    /**
     *
     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "ManageUserService")
    private IManageUserService manageUserService;

    // MongoDB 컬렉션 이름
    private String colNm = "UserInfoCollection";

    /*
     * 함수명 위의 value="notice/NoticeList" => /notice/NoticeList로 호출되는 url은 무조건 이 함수가 실행된다.
     * method=RequestMethod.GET => 폼 전송방법을 지정하는 것으로 get방식은 GET, post방식은 POST이다.
     * method => 기입안하면 GET, POST 모두 가능하나, 가급적 적어주는 것이 좋다.
     * */

    /**
     * 세팅 화면 및 사용자 리스트 보여주기
     * */
    @GetMapping(value="ManageUser/UserList")
    public String UserList(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        //로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".UserList start!");

        //공지사항 리스트 가져오기
        List<ManageUserDTO> rList = manageUserService.getUserList(colNm);

        if (rList==null){
            rList = new ArrayList<ManageUserDTO>();

        }

        ManageUserDTO pDTO = null;

        pDTO = new ManageUserDTO();

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        String user_id = (String) session.getAttribute("SS_USER_ID");

        log.info("user_id : " + user_id);

        pDTO.setUser_id(user_id);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rList = null;

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".UserList end!");

        //함수 처리가 끝나고 보여줄 링크
        return "/ManageUser/UserList";
    }

    /**
     * 사용자 상세보기
     * */
    @GetMapping(value="ManageUser/UserInfo")
    public String NoticeInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".UserInfo start!");


        /*
         * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
         * */
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //유저번호(PK)

        /*
         * #######################################################
         *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
         *                   반드시 작성할 것
         * #######################################################
         * */
        log.info("nSeq : "+ nSeq);


        /*
         * 값 전달은 반드시 DTO 객체를 이용해서 처리함
         * 전달 받은 값을 DTO 객체에 넣는다.
         * */
        ManageUserDTO pDTO = new ManageUserDTO();

        pDTO.setUser_seq(nSeq);

        //유저 상세정보 가져오기
        ManageUserDTO rDTO = manageUserService.getUserInfo(pDTO, colNm);

        if (rDTO==null){
            rDTO = new ManageUserDTO();

        }

        log.info("getNoticeInfo success!!!");

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rDTO", rDTO);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rDTO = null;
        pDTO = null;

        log.info(this.getClass().getName() + ".NoticeInfo end!");

        return "/ManageUser/UserInfo";
    }


    /**
     * 유저 삭제
     * */
    @PostMapping(value="ManageUser/DeleteUser")
    public String DeleteUser(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                               ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".DeleteUser start!");

        String msg = "";

        try{

            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //글번호(PK)

            log.info("nSeq : "+ nSeq);

            ManageUserDTO pDTO = new ManageUserDTO();

            pDTO.setUser_seq(nSeq);;

            //게시글 삭제하기 DB
            manageUserService.deleteUserInfo(pDTO, colNm);;

            msg = "삭제되었습니다.";

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }catch(Exception e){
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".NoticeDelete end!");

            //결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/ManageUser/DeleteUser";
    }

}