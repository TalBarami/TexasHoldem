package TexasHoldem.AcceptanceTests;

/**
 * Created by אחיעד on 05/04/2017.
 */
public abstract class Driver {

    public static Bridge getBridge(){
        ProxyBridge bridge = new ProxyBridge ();
        bridge.setRealBridge(null);
        return bridge;
    }

}
