package com.kong.rpc7.loadbalance;

import java.util.List;

/**
 * @author shijiu
 */
public class RoundLoadBalance implements LoadBalance{
   private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose=choose%addressList.size();
        return addressList.get(choose);
    }
}
