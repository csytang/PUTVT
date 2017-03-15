package graph.query;





import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import graph.pycharm.console.GraphRelationship;
import graph.query.api.GraphQueryNotification;
import graph.query.api.GraphQueryPlan;
import graph.query.api.GraphQueryResultColumn;
import graph.query.api.GraphQueryResultRow;
import graph.query.graph.GraphQueryResult;
import graph.visualization.api.GraphNode;

/**
 * Created by Cegin on 14.3.2017.
 */
public class Neo4jBoltQueryResult implements GraphQueryResult {

    private final long executionTimeMs;
    private List<GraphNode> nodes = new ArrayList<>();

    public Neo4jBoltQueryResult(long executionTimeMs) {
        this.executionTimeMs=executionTimeMs;
      /*  buffer.getRelationships().forEach((rel) -> {
            Neo4jBoltRelationship boltRel = (Neo4jBoltRelationship) rel;

            boltRel.setStartNode(findNodeById(nodes, boltRel.getStartNodeId()).orElse(null));
            boltRel.setEndNode(findNodeById(nodes, boltRel.getEndNodeId()).orElse(null));
        });*/
    }

    @Override
    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    @Override
    public String getResultSummary() {


        StringBuilder sb = new StringBuilder();

            sb.append(format("Nodes created: %s\n", "node"));

           /* if (counters.nodesDeleted() > 0) {
                sb.append(format("Nodes deleted: %s\n", counters.nodesDeleted()));
            }
            if (counters.labelsAdded() > 0) {
                sb.append(format("Labels added: %s\n", counters.labelsAdded()));
            }
            if (counters.labelsRemoved() > 0) {
                sb.append(format("Labels removed: %s\n", counters.labelsRemoved()));
            }
            if (counters.relationshipsCreated() > 0) {
                sb.append(format("Relationships created: %s\n", counters.relationshipsCreated()));
            }
            if (counters.relationshipsDeleted() > 0) {
                sb.append(format("Relationships deleted: %s\n", counters.relationshipsDeleted()));
            }
            if (counters.propertiesSet() > 0) {
                sb.append(format("Properties set: %s\n", counters.propertiesSet()));
            }*/

                sb.append(format("Indexes added: %s\n", "INDEX"));


      /*  if (summary.hasProfile()) {
            sb.append("Profile:\n");
            profileToString(sb, summary.profile(), 1);
        } else if (summary.hasPlan()) {
            sb.append("Plan:\n");
            planToString(sb, summary.plan(), 1);
        }*/

       /* if (summary.notifications().size() > 0) {
            sb.append("Notifications:\n");
            for (Notification notification : summary.notifications()) {
                sb.append(format("[%s] %s(%s) - %s", notification.severity(),
                        notification.title(), notification.code(), notification.description()));
            }
        }*/
        return sb.toString();
    }

   /* private void planToString(StringBuilder sb, Plan plan, int depth) {
        for (Plan childPlan : plan.children()) {
            planToString(sb, childPlan, depth + 1);
        }

        String line = repeat("-", depth);
        sb.append(line)
                .append(format("> %s {identifiers: %s; arguments: %s}\n", plan.operatorType(), plan.identifiers(), plan.arguments()));
    }

    /*private void profileToString(StringBuilder sb, ProfiledPlan profile, int depth) {
        for (ProfiledPlan childProfile : profile.children()) {
            profileToString(sb, childProfile, depth + 1);
        }

        String line = repeat("-", depth);
        sb.append(line)
                .append(format("> %s[records: %s; dbHits: %s] {identifiers: %s; arguments: %s}\n",
                        profile.operatorType(), profile.records(), profile.dbHits(), profile.identifiers(), profile.arguments()));
    }*/

    @Override
    public List<GraphQueryResultColumn> getColumns() {
        return new ArrayList<>();
    }

    @Override
    public List<GraphQueryResultRow> getRows() {
        return new ArrayList<>();
    }

    @Override
    public List<GraphNode> getNodes() {
        Neo4jBoltNode node1 = new Neo4jBoltNode("1");
        Neo4jBoltNode node2 = new Neo4jBoltNode("2");

        List<GraphNode> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        this.nodes=nodes;
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
        Neo4jBoltRelationship relationship = new Neo4jBoltRelationship("rel");
        relationship.setStartNode(nodes.get(0));
        relationship.setEndNode(nodes.get(1));

        List<GraphRelationship> relatonships = new ArrayList<>();
        relatonships.add(relationship);
        return relatonships;
    }

    @Override
    public List<GraphQueryNotification> getNotifications() {
        return new ArrayList<>();
    }

    @Override
    public boolean hasPlan() {
        return true;
    }

    @Override
    public boolean isProfilePlan() {
        return true;
    }

    @Override
    public Optional<GraphQueryPlan> getPlan() {
        return Optional.of(null);
    }

    private Optional<GraphNode> findNodeById(List<GraphNode> nodes, String id) {
        return nodes.stream().filter((node) -> node.getId().equals(id)).findFirst();
    }

    private String repeat(String part, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(part);
        }
        return sb.toString();
    }
}
