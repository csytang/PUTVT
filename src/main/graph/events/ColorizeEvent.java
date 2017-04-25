package main.graph.events;

import com.intellij.util.messages.Topic;

public interface ColorizeEvent {
    Topic<ColorizeEvent> COLORIZE_EVENT_TOPIC = Topic.create("GraphDatabaseConsole.ColorizeEvent", ColorizeEvent.class);

    void colorize();
}
