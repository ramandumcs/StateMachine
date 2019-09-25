package com.statemachine.sample.example.scxml;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.scxml.env.AbstractStateMachine;
import org.apache.commons.scxml.model.State;

public class AtmStatusFSM extends AbstractStateMachine {

    /**
     * State Machine uses this scmxml config file
     */
    private static final String SCXML_CONFIG_ATM_STATUS = "StateMachine.xml";
    
    
    

    /* CONSTRUCTOR(S) */
    
    public AtmStatusFSM() {
      super(AtmStatusFSM.class.getClassLoader().getResource(SCXML_CONFIG_ATM_STATUS));
    }
    
    /* HELPER METHOD(S) */

    /*
     * Fire the event
     */
    public void firePreDefinedEvent(AtmStatusEventEnum eventEnum){
     System.out.println("EVENT: " + eventEnum);
     this.fireEvent(eventEnum.getEventName());
    }
    
    public void callState(String name){
     this.invoke(name);
    }
    
    /*
     * Get current state ID as string
     */
    public String getCurrentStateId() {
     Set states = getEngine().getCurrentStatus().getStates();
     State state = (State) states.iterator().next();
     return state.getId();
    }
    
    /*
     * Get current state as apache's State object
     */
    public State getCurrentState() {
     Set states = getEngine().getCurrentStatus().getStates();
     return ( (State) states.iterator().next());
    }
    
    /*
     * Get events belongs to current status of the FSM
     */
    public Collection getCurrentStateEvents() {
     return getEngine().getCurrentStatus().getEvents();
    }
    
    /* STATES */
    // Each method below is the activity corresponding to a state in the
    // SCXML document (see class constructor for pointer to the document).

    public void idle() {
     System.out.println(" Raman STATE: idle");
     
    }
     
    public void loading() {
      System.out.println("Raman STATE: loading");
    }
     
    public void inService() {
      System.out.println("STATE: inService");
    }
     
    public void outOfService() {
      System.out.println("STATE: outOfService");
    }
     
    public void disconnected() {
      System.out.println("STATE: disconnected");
    }
   }
