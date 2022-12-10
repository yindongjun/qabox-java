package yi.master.websocket;

import cn.hutool.core.collection.CollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 未读邮件数量推送
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/11/25 11:17
 */

@ServerEndpoint(value = "/push/mail/{userId}")
public class MailDataPush {
    private static final Logger logger = LoggerFactory.getLogger(MailDataPush.class);

    private static Map<String, List<Session>> routeSessions = new ConcurrentHashMap();

    @OnOpen
    public void onOpen (@PathParam("userId") String userId, Session session) {
        logger.info("/push/mail 新接入client：userId={}", userId);
        if (routeSessions.get(userId) == null) {
            routeSessions.put(userId, new CopyOnWriteArrayList<Session>());
        }
        routeSessions.get(userId).add(session);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session ) {
        logger.info("/push/mail client已关闭:userId={}", userId);
        routeSessions.get(userId).remove(session);
    }


    @OnMessage
    public void onMessage(String msg, Session session) throws Exception{
        logger.info("/push/mail 接受到信息：{}", msg);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("/push/mail 出错", error);
    }


    public static void sendMessage (String message, String userId) {
        List<Session> sessions = routeSessions.get(userId);
        if (CollUtil.isNotEmpty(sessions)) {
            for (Session s:sessions) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        logger.error("/push/mail 推送消息出错，userId={}, message={}", userId, message);
                        logger.error("Push Data Exception", e);
                    }
                }
            }
        }
    }
}
