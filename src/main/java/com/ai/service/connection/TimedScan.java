package com.ai.service.connection;

import com.ai.common.CommonUtil;
import com.ai.domain.user.User;
import com.ai.service.LoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import static com.ai.service.connection.UserStatus.MAX_FAIL_NUM;
import static com.ai.service.connection.UserStatus.MAX_HEART;

public class TimedScan {

    @Autowired
    private LoveService loveService;

    @Autowired
    private connectionHandler handler;

    private static final Map<Integer, UserStatus> bufHandler = connectionHandler.getSocketManager();

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedScan.class);

    public void scanHeart() {
        if (!bufHandler.isEmpty()) {
            for (Iterator<Map.Entry<Integer, UserStatus>> it = bufHandler.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Integer, UserStatus> userStatus = it.next();
                Date lastHeart = userStatus.getValue().getLastHeartTime();
                if (CommonUtil.timeCount(lastHeart, new Date(), "second") > MAX_HEART) {
                    LOGGER.info(userStatus.getValue().getUser().getUserName() + "已经失联" + (userStatus.getValue().getFailNum()+1) + "次");
                    int num = userStatus.getValue().getFailNum();
                    if (num == MAX_FAIL_NUM - 1) {
                        User loveUser = loveService.getLoveInfo(userStatus.getKey());
                        UserStatus status = bufHandler.remove(userStatus.getKey());
                        if (loveUser != null) {
                            String message = null;
                            if (status.getUser().getSex() == 1) {
                                message = "他下线了";
                            } else {
                                message = "她下线了";
                            }
                            handler.sendMessage(loveUser.getUserId(), MessageBuilder.produceMessage(2, message));
                        }
                    } else {
                        userStatus.getValue().setFailNum(++num);
                    }
                }
            }
        }
    }
}
