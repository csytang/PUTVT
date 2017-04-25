package main.graph.events;

import com.intellij.util.messages.Topic;

public interface ZoomAndPanToFitEvent {

    Topic<ZoomAndPanToFitEvent> ZOOM_AND_PAN_TO_FIT_EVENT_TOPIC = Topic.create("GraphDatabaseConsole.ZoomAndPanToFitEvent", ZoomAndPanToFitEvent.class);

    void resetPan();

}
