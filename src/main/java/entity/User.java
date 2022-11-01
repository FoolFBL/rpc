package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shijiu
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//builder 便于赋值
//序列化 Serializable 便于网络传输
public class User implements Serializable {
    private int id;
    private String name;
    private char sex;//M-男 N-女

    public void eat(){
        System.out.println("饿了想吃");
    }
    private void drink(){
        System.out.println("渴了想喝");
    }

}
