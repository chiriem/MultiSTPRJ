package kopo.poly.controller;

import kopo.poly.service.IYouTubeService;
import kopo.poly.dto.YouTubeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YouTubeController {

    private final kopo.poly.service.IYouTubeService IYouTubeService;

    @Autowired
    public YouTubeController(
            final IYouTubeService IYouTubeService
    ) {
        this.IYouTubeService = IYouTubeService;
    }

//    @GetMapping("youtube")
//    public YouTubeDTO Index() {
//        return IYouTubeService.get();
//    }

}
