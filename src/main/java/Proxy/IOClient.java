package Proxy;

import config.RPCRequest;
import config.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author shijiu
 */
public class IOClient {

    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println(request);
            oos.writeObject(request);
            oos.flush();
            RPCResponse response = (RPCResponse) ois.readObject();
            return response;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
