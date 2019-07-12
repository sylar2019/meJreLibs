package me.util.flowable.model.execution;


/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class ChangeState {
    private String cancelActivityId;
    private String startActivityId;

    public String getCancelActivityId() {
        return cancelActivityId;
    }

    public void setCancelActivityId(String cancelActivityId) {
        this.cancelActivityId = cancelActivityId;
    }

    public String getStartActivityId() {
        return startActivityId;
    }

    public void setStartActivityId(String startActivityId) {
        this.startActivityId = startActivityId;
    }
}
