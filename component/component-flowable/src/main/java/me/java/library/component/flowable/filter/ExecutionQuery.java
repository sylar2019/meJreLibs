package me.java.library.component.flowable.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class ExecutionQuery extends ExecutionFilter {

    private List<QueryVariable> variables = new ArrayList<>();

    private List<QueryVariable> processInstanceVariables = new ArrayList<>();

    public List<QueryVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<QueryVariable> variables) {
        this.variables = variables;
    }

    public List<QueryVariable> getProcessInstanceVariables() {
        return processInstanceVariables;
    }

    public void setProcessInstanceVariables(List<QueryVariable> processInstanceVariables) {
        this.processInstanceVariables = processInstanceVariables;
    }

    @Override
    public Map<String, Object> toMap() {
        throw new UnsupportedOperationException();
    }
}
