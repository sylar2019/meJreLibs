package me.java.library.component.flowable.filter;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class FormDataFilter extends AbstractParamsFilter {
    private String taskId;
    private String processDefinitionId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
}
