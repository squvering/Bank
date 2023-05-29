package pack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Function;

public class Interaction {
    private  Socket socket;
    private boolean stop;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    public Interaction(Socket socket) throws IOException {
        this.socket = socket;
        outputStream =new ObjectOutputStream(socket.getOutputStream());
    }
    public void send(Pack pack) throws IOException {
        outputStream.writeObject(pack);
        outputStream.flush();
    }
    public void startInteraction(Function<Pack, Void> parser) throws Exception {
        inputStream = new ObjectInputStream(socket.getInputStream());
        stop = false;
        while (!stop){
            Pack pack = (Pack)inputStream.readObject();
            parser.apply(pack);
        }
    }
}
