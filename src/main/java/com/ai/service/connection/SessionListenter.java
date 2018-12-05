package com.ai.service.connection;

import com.ai.domain.user.User;
import com.ai.service.LoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import static com.ai.common.CommonConst.USER_CONTEXT;
import static com.ai.service.connection.Message.EXCEED_TIME;
import static com.ai.service.connection.Message.UN_LOGIN;

public class SessionListenter implements HttpSessionListener {

    @Autowired
    private LoveService loveService;

    @Autowired
    private connectionHandler handler;

    private static final Map<Integer, UserStatus> bufHandler = connectionHandler.getSocketManager();

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionListenter.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = (User) session.getAttribute(USER_CONTEXT);
        if(!bufHandler.containsKey(user.getUserId())) return;
        WebSocketSession webSocketSession = bufHandler.get(user.getUserId()).getSocketSession();
        handler.sendMessage(user.getUserId(), MessageBuilder.produceMessage(EXCEED_TIME, "会话已失效"));
        bufHandler.remove(user.getUserId());
        LOGGER.info(user.getUserName() + "会话已失效，清除所有记录");
        User loveUser = loveService.getLoveInfo(user.getUserId());
        if (loveUser != null) {
            String message = null;
            if (user.getSex() == 1) {
                message = "他下线了";
            } else {
                message = "她下线了";
            }
            handler.sendMessage(loveUser.getUserId(), MessageBuilder.produceMessage(UN_LOGIN, message));
        }
    }
}
