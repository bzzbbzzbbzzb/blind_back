package com.blind.book.domain;


import com.blind.book.Dto.BindDto;
import com.blind.book.repository.DeviceMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.blind.book.constant.WSMap.*;

//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint(value = "/websocket",subprotocols = {"protocol"})
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
public class WebSocket {

    //实例一个session，这个session是websocket的session
    private Session session;
    private String deviceId;
    private Integer userId;
    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }

    //存放websocket的集合（本次demo不会用到，聊天室的demo会用到）

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        if(!Objects.isNull(notBindSet.get(deviceId))){//ws关闭连接且未绑定
            notBindSet.remove(deviceId);
        }else {
            wsMap.remove(deviceId);
            bindMap.remove(userId);
        }

    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        DeviceMapper deviceMapper = applicationContext.getBean(DeviceMapper.class);
        System.out.println(deviceMapper);
         Integer userId1 = deviceMapper.getBind(message);
        deviceId = message;
       if(!Objects.isNull(userId1)) {
           bindMap.put(userId,message);
           wsMap.put(message,this);
           userId = userId1;
       }else{
           notBindSet.put(message,this);
       }
    }

    //新增一个方法用于主动向客户端发送消息
    public void sendMessage(String message) {
            System.out.println("发送消息");
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
