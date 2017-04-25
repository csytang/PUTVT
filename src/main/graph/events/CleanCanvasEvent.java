package main.graph.events;

import com.intellij.util.messages.Topic;

/**
 * Clean canvas event used by the internal project message bus
 */
public interface CleanCanvasEvent {

    Topic<CleanCanvasEvent> CLEAN_CANVAS_TOPIC = Topic.create("GraphDatabaseConsole.CleanCanvasTopic", CleanCanvasEvent.class);

    void cleanCanvas();
}
