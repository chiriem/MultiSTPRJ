package kopo.poly.controller;

import kopo.poly.chat.ChatHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping(value = "/chat")
public class ChatController {

    /**
     * 채팅창 입장 전
     */
    @RequestMapping(value = "intro")
    public String intro(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/chat/intro";
    }

    /**
     * 채팅창 접속
     */
    @RequestMapping(value = "room")
    public String room(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/chat/chatroom";
    }

    /**
     * 채팅방 목록
     */
    @RequestMapping(value = "roomList")
    @ResponseBody
    public Set<String> roomList(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        log.info(this.getClass().getName() + ".roomList Start!");

        log.info(this.getClass().getName() + ".roomList Ends!");

        return ChatHandler.roomInfo.keySet();

    }
}
