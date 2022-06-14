package kopo.poly.controller;

import kopo.poly.dto.SStudioDTO;
import kopo.poly.service.ISStudioService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * UserInfo는 사용자의 정보를 관리하며, 사용자 정보를 담당하는 페이지입니다.
 */
@Slf4j
@Controller
public class SStudioController {

    /**
     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "SStudioService")
    private ISStudioService sStudioService;

    // MongoDB 컬렉션 이름
    private String colNm = "SStudioCollection";

    /**
     * GetMapping은 GET방식을 통해 접속되는 URL 호출에 대해 실행되는 함수로 설정함을 의미함
     * PostMapping은 POST방식을 통해 접속되는 URL 호출에 대해 실행되는 함수로 설정함을 의미함
     * GetMapping(value = "index") =>  GET방식을 통해 접속되는 URL이 index인 경우 아래 함수를 실행함
     */

    @GetMapping(value = "index")
    public String Index(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{


        return "/index";
    }

    @RequestMapping(value = "user/getYtaddress")
    public String getYtaddress(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                    ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".getYtaddress!! start!");

        //정보를 저장할 변수
        SStudioDTO pDTO = null;

        pDTO = new SStudioDTO();

        String user_id = (String) session.getAttribute("SS_USER_ID");

        log.info("user_id : " + user_id);

        pDTO.setUser_id(user_id);


        //유튜브 리스트 가져오기
        List<SStudioDTO> rList = sStudioService.getYtaddress(pDTO, colNm);


        if (rList==null){
            rList = new ArrayList<SStudioDTO>();

        }

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rList = null;

        log.info(this.getClass().getName() + ".getYtaddress!! end!");

//        return "redirect:/index";
        return "index";
    }

//    @GetMapping(value = "SingleST/SStud")
//    public String SingleStudioview(HttpSession session) {
//
//        log.info(this.getClass().getName() + ".SingleStudioview ok!");
//
//        return "SingleST/SStud";
//    }

    @GetMapping(value="/SingleST/SStud")
    public String SingleStudioview(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".SingleSTview start!");


        /*
         * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
         * */
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); //

        /*
         * #######################################################
         *     반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
         *                   반드시 작성할 것
         * #######################################################
         * */
        log.info("nSeq : "+ nSeq);

        SStudioDTO pDTO = new SStudioDTO();

        pDTO.setYt_seq(nSeq);

        //상세정보 가져오기
        SStudioDTO rDTO = sStudioService.getYoutubeInfo(pDTO, colNm);

        if (rDTO==null){
            rDTO = new SStudioDTO();

        }

        String yt_seq = rDTO.getYt_seq();
        String yt_address = rDTO.getYt_address();

        log.info("yt_seq : " + yt_seq);
        log.info("yt_addrress : " + yt_address);

        log.info("getYoutubeInfo success!!!");

        session.setAttribute("yt_address", yt_address);

        //조회된 리스트 결과값 넣어주기
        model.addAttribute("rDTO", rDTO);

        //변수 초기화(메모리 효율화 시키기 위해 사용함)
        rDTO = null;
        pDTO = null;

        log.info(this.getClass().getName() + ".SingleSTview end!");

        return "/SingleST/SStud";
    }

    /**
     * 주소 입력 화면으로 이동
     */
    @GetMapping(value = "/SingleST/SStudioadd")
    public String SingleStudioadd() {

        log.info(this.getClass().getName() + ".SingleStudiovAddform ok!");

        return "/SingleST/SStudioadd";
    }

    /**
     * 주소 입력 로직 처리
     */
    @RequestMapping(value = "SingleStudio/insertYtaddress")
    public String insertYtaddress(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertYtaddress start!");

        //회원가입 결과에 대한 메시지를 전달할 변수
        String msg = "";

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        SStudioDTO pDTO = null;

        try {

            /*
             * #######################################################
             *        웹에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String yt_address = CmmUtil.nvl(request.getParameter("yt_address")); //유튜브 주소

            String vid = yt_address;
            log.info("vid : " + vid);
            if (vid.contains("youtube.com/watch?v=")) {
                String[] str1 = vid.split("youtube\\.com/watch\\?v=");
                vid = str1[1];
            } else if (vid.contains("youtu.be/")) {
                String[] str1 = vid.split("youtu.be/");
                vid = str1[1];
            }

            log.info("vid : " + vid);
            if (vid.contains("&")) {
                String[] str2 = vid.split("&");
                vid = str2[0];
            }
            yt_address = vid.trim();
            /*
             * #######################################################
             *        웹에서 받는 정보를 String 변수에 저장 끝!!
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
            log.info("yt_address : " + yt_address);


            /*
             * #######################################################
             *        웹에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */


            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new SStudioDTO();

            pDTO.setUser_id(user_id);
            pDTO.setYt_address(yt_address);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 정보 입력
             * */
            int res = sStudioService.insertYtaddress(pDTO, colNm);

            log.info("입력 결과(res) : " + res);

            if (res == 1) {
                msg = "입력되었습니다.";

                //추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복을 체크하길 바람
            } else {
                msg = "오류로 인해 입력이 실패하였습니다.";

            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e.toString();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".insertUserInfo end!");


            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("msg", msg);

            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("pDTO", pDTO);

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }

        return "redirect:/index";
    }
}