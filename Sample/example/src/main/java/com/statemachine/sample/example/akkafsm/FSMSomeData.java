package com.statemachine.sample.example.akkafsm;

public class FSMSomeData implements FSMData{
    
    int a = 5;
    @Override
    public int printa(){
        System.out.println("Datat" + a);
        return a;
    }

}
