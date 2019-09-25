package com.statemachine.sample.example.scxml;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.scxml.Context;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.SCXMLExpressionException;
import org.apache.commons.scxml.TriggerEvent;
import org.apache.commons.scxml.env.SimpleContext;
import org.apache.commons.scxml.env.SimpleDispatcher;
import org.apache.commons.scxml.env.Tracer;
import org.apache.commons.scxml.model.ModelException;
import org.apache.commons.scxml.model.SCXML;
import org.apache.commons.scxml.model.State;

import org.w3c.dom.Node;

/**
 * uses the SCXML from Apache to implement state charts.
 * 
 * @author Ant Kutschera
 */
public abstract class AbstractStateMachine {

    /** the state chart engine. */
    private SCXMLExecutor exec;

    /**
     * creates a state machine, with states and transitions based on the given
     * config.
     * 
     * @param config
     *            the state machine config
     */
    public AbstractStateMachine(SCXML config) {
        this(config, null);
    }

    /**
     * creates a state machine, with states and transitions based on the given
     * config. called when for example the state has been read from persistence,
     * and we need to set the initial state of this machine.
     * 
     * @param config
     *            the state machine config
     * @param idOfPersistedState
     *            the id of the state whihc this instance should currently have.
     */
    public AbstractStateMachine(SCXML config, String idOfPersistedState) {

        // configure the parser and executor (engine)...

        Tracer tracer = new Tracer();
        exec = new SCXMLExecutor(new Evaluator(), new SimpleDispatcher(),
                tracer);

        exec.setStateMachine(config);

        // exec.addListener(scxml, tracer);

        Context context = new SimpleContext();
        exec.setRootContext(context);

        exec.setErrorReporter(tracer);

        // ok, ready to go! initialise the state machine
        try {
            exec.go();
        } catch (ModelException e) {
            throw new IllegalArgumentException(
                    "failed to create state machine instance", e);
        }

        setInitialState(exec, idOfPersistedState);
    }

    /**
     * sets the initial state on the config in order to 
     * get the correct state when instantiating the 
     * machine instance.
     * 
     * @param idOfPersistedState
     * @param config the state machine config
     */
    @SuppressWarnings("unchecked")
    private static void setInitialState(SCXMLExecutor exec, String idOfPersistedState) {

        if(idOfPersistedState != null){
            Map<String, State> allStates = exec.getStateMachine().getTargets();
            
            State state = allStates.get(idOfPersistedState);
            if (state == null) {
                throw new IllegalStateException("Unknown state "
                        + idOfPersistedState);
            }

            //got the state, now set it
            
            @SuppressWarnings("rawtypes")
            Set states = exec.getCurrentStatus().getStates();
            states.clear();
            states.add(state);
        }
    }

    /**
     * @return the ID of the current state, as a string.
     */
    public String getCurrentState() {
        return ((State) exec.getCurrentStatus().getStates().iterator().next())
                .getId();
    }

    /**
     * subclasses should call this in order to change state, as it performs
     * checks if the state change is actually allowed. if its not allowed, an
     * {@link IllegalStateException} is thrown.
     * 
     * @param requiredTransitionEvent
     * 
     * @throws ModelException
     */
    protected void trigger(String requiredTransitionEvent) {
        checkIfAbleToChangeState(requiredTransitionEvent);

        int eventType = TriggerEvent.SIGNAL_EVENT; // because this is what is in
                                                    // the examples. sadly not
                                                    // documented.
        TriggerEvent evt = new TriggerEvent(requiredTransitionEvent, eventType,
                null);
        try {
            exec.triggerEvent(evt);
        } catch (ModelException e) {
            throw new IllegalStateException("Unable to perform transition "
                    + requiredTransitionEvent, e);
        }
    }

    /**
     * called when attempting to change state because the SCXML engine doesnt
     * complain if the requested state transition is illegal.
     * 
     * @param transitionEventName
     *            the name of the event being triggered
     * 
     * @throws IllegalStateException
     *             if the state being changed to is illegal
     */
    public void checkIfAbleToChangeState(String transitionEventName)
            throws IllegalStateException {

        @SuppressWarnings("rawtypes")
        List potentialTransitions = ((State) exec.getCurrentStatus()
                .getStates().iterator().next())
                .getTransitionsList(transitionEventName);

        if ((potentialTransitions == null)
                || (potentialTransitions.size() == 0)) {
            throw new IllegalStateException("Illegal State Transition "
                    + transitionEventName + " from state " + getCurrentState());
        }
    }

    /**
     * @return true, if the current state is a final one.
     */
    public boolean isCurrentStateFinal() {
        return exec.getCurrentStatus().isFinal();
    }

    /**
     * doesnt actually need to do anything, except provide a context! in more
     * complex cases, could be used for evaluating expressions.
     * 
     * @author Ant Kutschera
     */
    private static class Evaluator implements
            org.apache.commons.scxml.Evaluator {

        public Object eval(Context ctx, String expr)
                throws SCXMLExpressionException {

            // no op
            return null;
        }

        public Boolean evalCond(Context ctx, String expr)
                throws SCXMLExpressionException {

            // no op
            return null;
        }

        public Node evalLocation(Context ctx, String expr)
                throws SCXMLExpressionException {

            // no op
            return null;
        }

        public Context newContext(Context parent) {
            return new SimpleContext(parent);
        }

    }

}
