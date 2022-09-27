package kopo.poly.controller;

import kopo.poly.service.ISStudioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MainController {

    @Resource(name = "SStudioService")
    private ISStudioService sStudioService;

    // MongoDB 컬렉션 이름
    private String colNm = "SStudioCollection";

    @GetMapping(value = "MultiStudio/MultiStudio")
    public String MultiStudio() {
        return "/MultiStudio/MultiStudio";

    }

    @GetMapping(value = "index")
    public String Index(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{


        return "/index";
    }

    @GetMapping(value = "index2")
    public String Index2(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{


        return "/index2";
    }

    @GetMapping(value = "Setting")
    public String Setting() {
        return "Setting";

    }

    @GetMapping(value = "Search2")
    public String Search() {
        return "Search2";

    }

    @GetMapping(value = "flogin")
    public String login() {
        return "flogin";

    }

    @GetMapping(value = "calendar")
    public String calendar() {
        return "calendar";

    }

    @GetMapping(value = "fca")
    public String fca() {

        log.info(this.getClass().getName() + ".fca start");

        return "/fca";

    }

}
