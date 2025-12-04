package edu.io.player;

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

    @Override
    public String toString() {
        return tools.toString();
    }
}