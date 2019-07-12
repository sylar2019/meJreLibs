package me.util.flowable.filter;

import java.util.List;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class HistoricProcessInstanceQuery extends HistoricProcessInstanceFilter {

    private List<String> processInstanceIds;
    private List<QueryVariable> variables;

    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(List<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }

    public List<QueryVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<QueryVariable> variables) {
        this.variables = variables;
    }
}
