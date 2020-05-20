package me.java.library.component.flowable.model.history;


import me.java.library.component.flowable.model.RestVariable;


/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class HistoricVariableInstance {

    protected String id;
    protected String processInstanceId;
    protected String processInstanceUrl;
    protected String taskId;
    protected RestVariable variable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceUrl() {
        return processInstanceUrl;
    }

    public void setProcessInstanceUrl(String processInstanceUrl) {
        this.processInstanceUrl = processInstanceUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public RestVariable getVariable() {
        return variable;
    }

    public void setVariable(RestVariable variable) {
        this.variable = variable;
    }
}