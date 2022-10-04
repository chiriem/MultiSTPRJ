package kopo.poly.controller;

import kopo.poly.dto.ContactDTO;
import kopo.poly.service.IContactService;
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
 * Contact는 공지사항을 관리하며, 관리가 가능한 페이지입니다.
 */
@Controller
public class ContactController {
    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    /**
     *
     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "ContactService")
    private IContactService ContactService;

    // MongoDB 컬렉션 이름
    private String colNm = "ContactCollection";

    /*
     * 함수명 위의 value="Contact/ContactList" => /Contact/ContactList로 호출되는 url은 무조건 이 함수가 실행된다.
     * method=RequestMethod.GET => 폼 전송방법을 지정하는 것으로 get방식은 GET, post방식은 POST이다.
     * method => 기입안하면 GET, POST 모두 가능하나, 가급적 적어주는 것이 좋다.
     * */

    /**
     * 게시판 리스트 보여주기
     * */
    @GetMapping(value="Contact/ContactList")
    public String ContactList(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        //로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactList start!");

        String user_id = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID")); //아이디

        ContactDTO pDTO = new ContactDTO();

        pDTO.setUser_id(user_id);

        //공지사항 리스트 가져오기
        List<ContactDTO> rList = ContactService.getContactList(pDTO, colNm);

        if (rList==null){
            rList = new ArrayList<ContactDTO>();

        }

//        ContactDTO pDTO = null;
//
//        pDTO = new ContactDTO();

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info("rList 값 보기 :" + rList);

        log.info("user_id : " + user_id);

        pDTO.setUser_id(user_id);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rList = null;

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactList end!");

        //함수 처리가 끝나고 보여줄 링크
        return "/Contact/ContactList";
    }

    @GetMapping(value="Contact/ContactListforadmin")
    public String ContactListforadmin(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        //로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactListforadmin start!");



        ContactDTO pDTO = new ContactDTO();

        //공지사항 리스트 가져오기
        List<ContactDTO> rList = ContactService.getContactListforadmin(colNm);


        if (rList==null){
            rList = new ArrayList<ContactDTO>();

        }

//        ContactDTO pDTO = null;
//
//        pDTO = new ContactDTO();

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);


        String user_id = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID")); //아이디

        log.info("user_id : " + user_id);


        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rList = null;

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactList end!");

        //함수 처리가 끝나고 보여줄 링크
        return "/Contact/ContactListforadmin";
    }

    /**
     * 게시판 작성 페이지 이동
     *
     * 이 함수는 게시판 작성 페이지로 접근하기 위해 만듬.
     * WEB-INF 밑에 존재하는 JSP는 직접 호출할 수 없음
     * 따라서 WEB-INF 밑에 존재하는 JSP를 호출하기 위해서는 반드시 Controller 등록해야함
     * */
    @GetMapping(value="Contact/ContactReg")
    public String ContactReg(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactReg start!");

        //로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ContactReg end!");

        return "/Contact/ContactReg";
    }


    /**
     * 게시판 글 등록
     * */
    @PostMapping(value="Contact/ContactInsert")
    public String ContactInsert(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".ContactInsert start!");

        String msg = "";

        try{
            /*
             * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
             * */
            String user_id = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID")); //아이디
            String title = CmmUtil.nvl(request.getParameter("title")); //제목
            String contents = CmmUtil.nvl(request.getParameter("contents")); //내용

            /*
             * #######################################################
             *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             *                   반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : "+ user_id);
            log.info("title : "+ title);
            log.info("contents : "+ contents);

            ContactDTO pDTO = new ContactDTO();

            pDTO.setUser_id(user_id);
            pDTO.setTitle(title);
            pDTO.setContents(contents);


            /*
             * 게시글 등록하기위한 비즈니스 로직을 호출
             * */
            ContactService.insertContactInfo(pDTO, colNm);


            //저장이 완료되면 사용자에게 보여줄 메시지
            msg = "등록되었습니다.";


            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }catch(Exception e){

            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".ContactInsert end!");

            //결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/Contact/MsgToList";
    }


    /**
     * 게시판 상세보기
     * */
    @GetMapping(value="Contact/ContactInfo")
    public String ContactInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".ContactInfo start!");


        /*
         * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
         * */
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //공지글번호(PK)

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
        ContactDTO pDTO = new ContactDTO();

        pDTO.setContact_seq(nSeq);

        log.info("read_cnt update success!!!");

        //공지사항 상세정보 가져오기
        ContactDTO rDTO = ContactService.getContactInfo(pDTO, colNm);

        if (rDTO==null){
            rDTO = new ContactDTO();

        }

        log.info("getContactInfo success!!!");

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rDTO", rDTO);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rDTO = null;
        pDTO = null;

        log.info(this.getClass().getName() + ".ContactInfo end!");

        return "/Contact/ContactInfo";
    }


    /**
     * 게시판 수정 보기
     * */
    @GetMapping(value="Contact/ContactEditInfo")
    public String ContactEditInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                 ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".ContactEditInfo start!");


        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //공지글번호(PK)
        String user_id = (String) session.getAttribute("SS_USER_ID");

        log.info("nSeq : "+ nSeq);
        log.info("user_id : "+ user_id);


        ContactDTO pDTO = new ContactDTO();

        pDTO.setContact_seq(nSeq);
        pDTO.setUser_id(user_id);

        /*
         * #######################################################
         *    공지사항 수정정보 가져오기(상세보기 쿼리와 동일하여, 같은 서비스 쿼리 사용함)
         * #######################################################
         */
        ContactDTO rDTO = ContactService.getContactInfo(pDTO, colNm);

        if (rDTO==null){
            rDTO = new ContactDTO();

        }

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rDTO", rDTO);


        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rDTO = null;
        pDTO = null;

        log.info(this.getClass().getName() + ".ContactEditInfo end!");

        return "/Contact/ContactEditInfo";
    }


    /**
     * 게시판 글 수정
     * */
    @PostMapping(value="Contact/ContactUpdate")
    public String ContactUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                               ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".ContactUpdate start!");

        String msg = "";

        try{

            String user_id = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID")); //아이디
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //글번호(PK)
            String title = CmmUtil.nvl(request.getParameter("title")); //제목
            String contents = CmmUtil.nvl(request.getParameter("contents")); //내용

            log.info("user_id : "+ user_id);
            log.info("nSeq : "+ nSeq);
            log.info("title : "+ title);
            log.info("contents : "+ contents);

            ContactDTO pDTO = new ContactDTO();

            pDTO.setUser_id(user_id);
            pDTO.setContact_seq(nSeq);;
            pDTO.setTitle(title);
            pDTO.setContents(contents);

            //게시글 수정하기 DB
            ContactService.updateContactInfo(pDTO, colNm);

            msg = "수정되었습니다.";

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }catch(Exception e){
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".ContactUpdate end!");

            //결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/Contact/MsgToList";
    }


    /**
     * 게시판 글 삭제
     * */
    @PostMapping(value="Contact/ContactDelete")
    public String ContactDelete(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                               ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".ContactDelete start!");

        String msg = "";

        try{

            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //글번호(PK)

            log.info("nSeq : "+ nSeq);

            ContactDTO pDTO = new ContactDTO();

            pDTO.setContact_seq(nSeq);;

            //게시글 삭제하기 DB
            ContactService.deleteContactInfo(pDTO, colNm);;

            msg = "삭제되었습니다.";

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }catch(Exception e){
            msg = "실패하였습니다. : "+ e.toString();
            log.info(e.toString());
            e.printStackTrace();

        }finally{
            log.info(this.getClass().getName() + ".ContactDelete end!");

            //결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/Contact/MsgToList";
    }

}