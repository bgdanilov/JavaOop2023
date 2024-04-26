package ru.bgdanilov.csv;

import java.util.ArrayList;

public class Messages {
    private final ArrayList<String> messages = new ArrayList<>();

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void clearMessages() {
        messages.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (String item : messages) {
            sb.append(item).append(lineSeparator);
        }

        sb.setLength(sb.length() - 1);

        return sb.toString();
    }
}