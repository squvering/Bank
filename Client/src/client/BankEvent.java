package client;

import pack.Pack;

import java.util.EventObject;
public class BankEvent extends EventObject {

    private Pack pack;
    public BankEvent(Pack source) {
        super(source);
        this.pack = source;
    }
    public Pack getMessage1()
    {
        return pack;
    }
}