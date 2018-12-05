package com.ai.service.connection;

import com.ai.domain.user.User;
import com.ai.service.LoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.ai.common.CommonConst.USER_CONTEXT;

@Component
public class connectionHandler extends TextWebSocketHandler {

    private static final Map<Integer, UserStatus> socketManager = new ConcurrentHashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(connectionHandler.class);

    @Autowired
    private LoveService loveService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        User user = (User) session.getAttributes().get(USER_CONTEXT);
        UserStatus status = new UserStatus();
        status.setUser(user);
        status.setLastHeartTime(new Date());
        status.setFailNum(0);
        status.setSocketSession(session);
        if (!socketManager.containsKey(user.getUserId())) {
            User loveUser = loveService.getLoveInfo(user.getUserId());
            if (loveUser != null) {
                String message = null;
                if (user.getSex() == 1) {
                    message = "他上线了";
                } else {
                    message = "她上线了";
                }
                sendMessage(loveUser.getUserId(), MessageBuilder.produceMessage(1, message));
            }
        }
        socketManager.put(user.getUserId(), status);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOGGER.error(status.toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        Message bufMessage = MessageBuilder.getMessageFromString(message.getPayload());
        User user = (User) session.getAttributes().get(USER_CONTEXT);
        if (bufMessage.getCode() == 200) {
            UserStatus status = socketManager.get(user.getUserId());
            status.setLastHeartTime(new Date());
            status.setFailNum(0);
        } else {
            processMessage(user.getUserId(), bufMessage);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    public boolean checkUserOnline(int userId) {
        return socketManager.containsKey(userId);
    }

    public void sendMessage(int userId, String message) {
        if (checkUserOnline(userId)) {
            UserStatus status = socketManager.get(userId);
            try {
                status.getSocketSession().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                LOGGER.error("发送websocket信息错误");
            }
        }
    }

    private void processMessage(int userId, Message message) {

    }

    public static Map<Integer, UserStatus> getSocketManager() {
        return socketManager;
    }
}
