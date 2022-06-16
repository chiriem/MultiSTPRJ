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

@Slf4j
@Controller
public class MainController {

    @Resource(name = "SStudioService")
    private ISStudioService sStudioService;

    // MongoDB 컬렉션 이름
    private String colNm = "SStudioCollection";

//    @GetMapping(value = "index")
//    public String Index() {
//        return "/index";
//
//    }

    @GetMapping(value = "MultiStudio/MultiStudio")
    public String MultiStudio() {
        return "/MultiStudio/MultiStudio";

    }

//    @GetMapping(value = "Setting")
//    public String Setting() {
//        return "Setting";
//
//    }

    @GetMapping(value = "Search2")
    public String Search() {
        return "Search2";

    }

    @GetMapping(value = "flogin")
    public String login() {
        return "flogin";

    }


}
