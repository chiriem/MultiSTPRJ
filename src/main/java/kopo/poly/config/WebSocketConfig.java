package kopo.poly.config;

import kopo.poly.chat.ChatHandler;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Slf4j
@EnableWebSocket
@RequiredArgsConstructor
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("websocket execute");

        registry.addHandler(chatHandler, "/ws/*/*")
                .setAllowedOrigins("*")
                .addInterceptors(
                        new HttpSessionHandshakeInterceptor() {

                            @Override
                            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                                String path = CmmUtil.nvl(request.getURI().getPath());
                                log.info("path : " + path);

                                String[] urlInfo = path.split("/");

                                String roomName = CmmUtil.nvl(urlInfo[2]); // URI Path를 통해 채팅방 이름 가져오기
                                String userName = CmmUtil.nvl(urlInfo[3]); // URI Path를 통해 채팅방 이름 가져오기

                                // 채팅룸 이름이 한글 및 특수문자로 입력될 수 있기에 데이터 처리는 영문이나 숫자로 변환해야함
                                // 해시 함수르 이용해 영문으로 변경
                                String roomNameHash = EncryptUtil.encHashSHA256(roomName);

                                log.info("roomName : " + roomName);
                                log.info("userName : " + userName);
                                log.info("roomNameHash : " + roomNameHash);

                                attributes.put("roomName", roomName);
                                attributes.put("userName", userName);
                                attributes.put("roomNameHash", roomNameHash);

                                return super.beforeHandshake(request, response, wsHandler, attributes);
                            }
                        }
                );
    }

}
