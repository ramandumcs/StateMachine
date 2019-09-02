package com.statemachine.sample.example;
import java.net.URL;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.statemachine.sample.example.akkafsm.FSMEvent;
import com.statemachine.sample.example.akkafsm.SampleAkkaFSM;
import com.statemachine.sample.example.scxml.*;

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
        AtmStatusFSM fsm = new AtmStatusFSM();
        
        fsm.firePreDefinedEvent(AtmStatusEventEnum.CONNECT);
        fsm.firePreDefinedEvent(AtmStatusEventEnum.CONNECTION_CLOSED);
        
        ActorSystem system = ActorSystem.create("simpleFSMTest");
        ActorRef simpleFSM = system.actorOf(Props.create(SampleAkkaFSM.class));
        simpleFSM.tell(new FSMEvent(),
            simpleFSM); 
        
        System.out.println( "Hello World!" );
        //system.shutdown();
    }
}
