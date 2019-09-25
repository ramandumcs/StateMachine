package com.statemachine.sample.example.scxml;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.scxml.io.SCXMLParser;
import org.apache.commons.scxml.model.ModelException;
import org.apache.commons.scxml.model.SCXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public final class StateMachineDemo extends AbstractStateMachine {

    private static final String XML_CONFIG_FILENAME = "scxml2.xml";

    /** TRANSITION. */
    private static final String BOOK = "BOOK";

    /** TRANSITION. */
    private static final String PAYMENT = "PAYMENT";

    /** TRANSITION. */
    private static final String CANCEL = "CANCEL";

    /** TRANSITION. */
    private static final String REFUND = "REFUND";

    /** STATE. */
    public static final String RESERVED = "RESERVED";

    /** STATE. */
    public static final String BOOKED = "BOOKED";

    /** STATE. */
    public static final String PAID = "PAID";

    /** STATE. */
    public static final String CANCELLED = "CANCELLED";

    /** STATE. */
    public static final String REFUNDED = "REFUNDED";

    /** instance of model */
    private static SCXML config;
    final static Logger log = LoggerFactory.getLogger(StateMachineDemo.class);

    /*
     * read the state machine configuration, just once though, because it is quite resource intensive.
     */
    static {
        final URL configUrl = StateMachineDemo.class.getClassLoader().getResource(XML_CONFIG_FILENAME);
        try{
            config = SCXMLParser.parse(configUrl, new ErrorHandler(){
                public void error(SAXParseException exception) throws SAXException {
                    log.error("Couldn't parse SCXML Config (" + configUrl + ")", exception);
                }

                /**
                 * @see  ErrorHandler#fatalError(SAXParseException)
                 */
                public void fatalError(SAXParseException exception) throws SAXException {
                    log.error("Couldn't parse SCXML Config (" + configUrl + ")", exception);
                }

                /**
                 * @see  ErrorHandler#warning(SAXParseException)
                 */
                public void warning(SAXParseException exception) throws SAXException {
                    log.warn("Couldn't parse SCXML Config (" + configUrl + ")", exception);
                }
            });
        }catch(ModelException e){
            log.error("Couldn't parse SCXML Config (" + configUrl + ")", e);
        }catch(SAXException e){
            log.error("Couldn't parse SCXML Config (" + configUrl + ")", e);
        }catch(IOException e){
            log.error("Couldn't parse SCXML Config (" + configUrl + ")", e);
        }
    }

    /**
     * creates a state machine, with states and transitions based on the given config.
     *
     * @throws  MalformedURLException
     */
    public StateMachineDemo() {
        this(null);
    }

    /**
     * creates a state machine, with states and transitions based on the given config.
     *
     * @param   idOfPersistedState  id of initial state, for cases where we are reading it from persistence.
     *
     * @throws  MalformedURLException
     */
    public StateMachineDemo(String idOfPersistedState) {
        super(config, idOfPersistedState);
    }

    /**
     * trigger the state transition.
     * 
     * @throws  ModelException
     */
    public void triggerBook() {
        System.out.println("TriggerBook");
        trigger(BOOK);
    }

    public void refunded() {
        System.out.println(" Raman STATE: refunded");
        
       }
    
    public void paid() {
        System.out.println(" Raman STATE: paid");
        
       }
    
    /**
     * trigger the state transition.
     * 
     * @throws  ModelException
     */
    public void triggerPayment() {
        trigger(PAYMENT);
    }

    /**
     * trigger the state transition.
     * 
     * @throws  ModelException
     */
    public void triggerCancel() {
        trigger(CANCEL);
    }

    /**
     * trigger the state transition.
     * 
     * @throws  ModelException
     */
    public void triggerRefund() {
        trigger(REFUND);
    }

}

