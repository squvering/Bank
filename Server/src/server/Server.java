package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private boolean stop;
    ServerSocket ss;
    private int port = 5503;
    public void start() throws Exception {
        ss = new ServerSocket(port);
        stop = false;
        new Thread(()->{
            while (!stop) {
                try {
                    var s = ss.accept();
                    new ConnectedClient(s).start();
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }).start();
    }

}
