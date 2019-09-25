package com.statemachine.sample.example;
import java.net.URL;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.statemachine.sample.example.akkafsm.FSMEvent;
import com.statemachine.sample.example.akkafsm.FSMStates;
import com.statemachine.sample.example.akkafsm.SampleAkkaFSM;
import com.statemachine.sample.example.scxml.*;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.flipkart.zjsonpatch.JsonPatch;
import com.statemachine.sample.example.akkafsm.*;



/**
 * Hello world!
 *
 */
public class Main 
{
    /**
     * 
     * @param args
     */
    public static void main( String[] args )
    {
       
        
        //Default scxml state machine
        /*
        AtmStatusFSM fsm = new AtmStatusFSM();
        
        
        fsm.firePreDefinedEvent(AtmStatusEventEnum.CONNECT);
        fsm.firePreDefinedEvent(AtmStatusEventEnum.CONNECTION_CLOSED);
        */
        
        // Akka State Machine
        ActorSystem system = ActorSystem.create();
        ActorRef simpleFSM = system.actorOf(Props.create(SampleAkkaFSM.class, FSMStates.Idle, new FSMSomeData()));
        //ActorRef simpleFSM = system.actorOf(Props.create(SampleAkkaFSM.class));
       
        
        simpleFSM.tell(new FSMEvent(),
           simpleFSM);
         
        /*
        // Implemented State MAchine
        StateMachineDemo demo = new StateMachineDemo("PAID");
        StateMachineDemo demo1 = new StateMachineDemo();
        System.out.println("demo " + demo.getCurrentState());
        System.out.println("demo1 " + demo.getCurrentState());
        demo.triggerRefund();
        System.out.println("demo " + demo.getCurrentState());
        System.out.println("demo1 " + demo.getCurrentState());
        
        
        System.out.println( "Hello World!" );
        */
        //system.shutdown();
        
        
        //CompletionStage<String> cs = askPong("Ping").thenCompose(x -> askPong("Ping"));
        //CompletionStage<String> cs = askPong("Ping");
    }
    
    
    
    public static void askPong(String pong) {
        System.out.println(pong);
        //return pong;
    }
    
    public static void calculateJSONPatch() {
        try{
      
        
            String s="{\"name\":\"sonoo\",\"salary\":600000.0}";  
            String s1="{\"name\":\"sonoo\"}";
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(s);
            JsonNode n1 = mapper.readTree(s1);
            JsonNode sample =  JsonDiff.asJson(n, n1);
            try {
            JsonPatch.validate(n);
            } catch(Exception ex) {
                System.out.println(ex);
            }
               
        System.out.println(sample.toString());
        System.out.println(n1.toString());
        
        
        
        } catch (Exception ex) {
            
        }
        
        
    }
    
}
