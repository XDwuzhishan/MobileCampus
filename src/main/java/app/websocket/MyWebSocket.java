package app.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by xdcao on 2017/7/19.
 */
@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    private static int onlineCount=0;

    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet=new CopyOnWriteArraySet<MyWebSocket>();

    private Session session;

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session=session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("来自客户端的消息："+message);
        System.out.println("共"+webSocketSet.size()+"个session'");


        for (MyWebSocket item:webSocketSet){
            try{
                if (item!=this){
                    item.sendMessage(message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    @OnClose
    public void onClose(Session session){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("连接关闭，在线人数为："+getOnlineCount());
    }



    private void sendMessage(String message) throws IOException {
        if (this.session!=null){
            if (this.session.getBasicRemote()!=null){
                this.session.getBasicRemote().sendText(message);
            }else {
                System.out.println("basic remote null");
            }

        }else {
            System.out.println("session null");
        }

    }



    private static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        MyWebSocket.onlineCount--;
    }




}
