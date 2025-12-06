package smarthome.controller;

import smarthome.home.Home;
import java.util.ArrayList;
import java.util.List;

public class AutomationRule {
    private String ruleId;
    private String ruleName;
    private List<Condition> conditions;
    private List<Action> actions;
    private boolean enabled;
    private boolean requireAllConditions; // AND vs OR logic
    
    public AutomationRule(String ruleId, String ruleName) {
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.conditions = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.enabled = true;
        this.requireAllConditions = true; // default to AND
    }
    
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }
    
    public boolean evaluate(Home home) {
        if (!enabled || conditions.isEmpty()) {
            return false;
        }
        
        if (requireAllConditions) {
            // AND logic - all conditions must be true
            for (Condition condition : conditions) {
                if (!condition.evaluate(home)) {
                    return false;
                }
            }
            return true;
        } else {
            // OR logic - at least one condition must be true
            for (Condition condition : conditions) {
                if (condition.evaluate(home)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public void execute(Home home) {
        if (evaluate(home)) {
            System.out.println("[RULE TRIGGERED] " + ruleName);
            for (Action action : actions) {
                action.execute(home);
            }
        }
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setRequireAllConditions(boolean requireAll) {
        this.requireAllConditions = requireAll;
    }
    
    public String getRuleId() {
        return ruleId;
    }
    
    public String getRuleName() {
        return ruleName;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rule: ").append(ruleName).append(" [").append(enabled ? "ENABLED" : "DISABLED").append("]\n");
        
        sb.append("  IF ");
        if (conditions.isEmpty()) {
            sb.append("(no conditions)");
        } else {
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) {
                    sb.append(requireAllConditions ? " AND " : " OR ");
                }
                sb.append(conditions.get(i).describe());
            }
        }
        
        sb.append("\n  THEN ");
        if (actions.isEmpty()) {
            sb.append("(no actions)");
        } else {
            for (int i = 0; i < actions.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(actions.get(i).describe());
            }
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return ruleId + ": " + ruleName + " (" + (enabled ? "ON" : "OFF") + ")";
    }
}
