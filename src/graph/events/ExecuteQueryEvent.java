package graph.events;

import com.intellij.util.messages.Topic;
import graph.pycharm.api.DataSourceApi;
import graph.query.ExecuteQueryPayload;

public interface ExecuteQueryEvent {

    Topic<ExecuteQueryEvent> EXECUTE_QUERY_TOPIC = Topic.create("GraphDatabase.ExecuteQueryTopic", ExecuteQueryEvent.class);

    void executeQuery(DataSourceApi dataSource, ExecuteQueryPayload payload);
}
