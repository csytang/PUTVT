package main.graph.events;

import com.intellij.util.messages.Topic;

public interface ExecuteResultsEvent {

    Topic<ExecuteResultsEvent> EXECUTE_QUERY_TOPIC = Topic.create("GraphDatabase.ExecuteQueryTopic", ExecuteResultsEvent.class);

    void executeQuery();
}
