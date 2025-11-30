package edu.io.player;

import edu.io.token.PickaxeToken;
import edu.io.token.SluiceboxToken;
import edu.io.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Shed {
    private final Stack<Tool> tools = new Stack<>();

    public boolean isEmpty() {
        return tools.isEmpty();
    }

    public void add(Tool tool) {
        if (tool == null) throw new IllegalArgumentException("Cannot add null tool");
        tools.push(tool);
    }

    public Tool getTool() {
        if (isEmpty()) {
            return new NoTool();
        }
        return tools.peek();
    }

    public void dropTool() {
        if (!isEmpty()) {
            tools.pop();
        }
    }

    public List<Tool> getAllTools() {
        return new ArrayList<>(tools);
    }

    public void removeTool(Tool tool) {
        tools.remove(tool);
    }


    public Stack<Tool> tools() {
        return tools;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tool tool : tools) {
            // 1. Append the Label
            if (tool instanceof Token t) {
                sb.append(t.label());
            } else {
                sb.append("?");
            }

            // 2. Append Durability (if the tool has it)
            // We check specifically for classes that we know have a durability() method
            if (tool instanceof PickaxeToken p) {
                sb.append("(").append(p.durability()).append(")");
            } else if (tool instanceof SluiceboxToken s) {
                sb.append("(").append(s.durability()).append(")");
            }

            // 3. Add a space separator
            sb.append(" ");
        }
        return sb.toString();
    }}