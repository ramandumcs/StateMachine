package com.statemachine.sample.example.akkafsm;

import java.util.Set;

import akka.actor.AbstractFSM;
import akka.actor.FSM;
import akka.event.LoggingAdapter;
import scala.PartialFunction;
import scala.collection.Iterator;
import scala.collection.mutable.Map;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxedUnit;


public class SampleAkkaFSM extends AbstractFSM<States, FSMData> {
    {
    startWith(FSMStates.Idle, new FSMSomeData());
    
    when(FSMStates.Idle, matchEvent(FSMEvent.class, FSMSomeData.class,
            (fsmEvent, someData)->{
                System.out.println("In AKKA");
            return stay()
            .using(new FSMSomeData());}
            ));
    
    /*onTermination(
        matchStop(Normal(),
                (state, data) -> {
                    goTo(FSMStates.Idle)
                    .using(new FSMSomeData());
                }).
                stop(Shutdown(),
                        (state, data) -> {
                            goTo(FSMStates.Idle)
                            .using(new FSMSomeData());
                        }).
                        stop(Failure.class,
                                (reason, state, data) -> {
                                    goTo(FSMStates.Idle)
                                    .using(new FSMSomeData());
                                })
        );*/

    
    
    initialize();
    }

    
}
