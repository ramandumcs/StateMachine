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
    public SampleAkkaFSM () {
        setupFSM(FSMStates.Idle);
    }
    public SampleAkkaFSM(FSMStates state, FSMData data)
    {
    startWith(state, data);
    
    when(FSMStates.Idle, matchEvent(FSMEvent.class, FSMSomeData.class,
            (fsmEvent, someData)->{
                System.out.println("In AKKA Idle");
                try {
                //someData.printa();
                } catch (Exception ex) {
                    throw ex;
                }
                self().tell(new FSMEvent(), self());
            return goTo(FSMStates.Active)
            .using(someData);}
            ));
    when(FSMStates.Active, matchEvent(FSMEvent.class, FSMSomeData.class,
        (fsmEvent, someData)->{
            //someData.printa();
            System.out.println("In AKKA Active");
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

    

    
    
  public void setupFSM(FSMStates state){
      startWith(state, new FSMSomeData());
      
      when(FSMStates.Idle, matchEvent(FSMEvent.class, FSMSomeData.class,
              (fsmEvent, someData)->{
                  System.out.println("In AKKA");
              return goTo(FSMStates.Active)
              .using(new FSMSomeData());}
              ));
      when(FSMStates.Active, matchEvent(FSMEvent.class, FSMSomeData.class,
          (fsmEvent, someData)->{
              System.out.println("In AKKA Active");
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
