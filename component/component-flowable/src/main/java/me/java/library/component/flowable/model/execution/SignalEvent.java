package me.java.library.component.flowable.model.execution;


import me.java.library.component.flowable.model.RestVariable;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class SignalEvent {

    private String action;
    private String signalName;
    private String messageName;
    private List<RestVariable> variables = new ArrayList<>();
    private List<RestVariable> transientVariables = new ArrayList<>();


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }

    public List<RestVariable> getTransientVariables() {
        return transientVariables;
    }

    public void setTransientVariables(List<RestVariable> transientVariables) {
        this.transientVariables = transientVariables;
    }
}
