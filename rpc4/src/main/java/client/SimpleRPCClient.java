package client;

import config.RPCRequest;
import config.RPCResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @author shijiu
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SimpleRPCClient implements RPCClient{
    private String host;
    private int port;
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try{
            //发起一次socket连接
            Socket socket = new Socket(host,port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            System.out.println(response.getData());
            return response;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
