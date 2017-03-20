package graph.query;

import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExecuteQueryPayload {

    private final List<String> queries;

    private final Map<String, Object> parameters;

    private final Editor editor;

    private final String fileName;

    public ExecuteQueryPayload() {
        this.queries = Collections.singletonList("");
        this.editor = null;
        this.parameters = Collections.emptyMap();
        this.fileName = null;
    }

    @NotNull
    public List<String> getQueries() {
        return queries;
    }

    public Optional<String> getFileName() {
        return Optional.ofNullable(fileName);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @NotNull
    public Optional<Editor> getEditor() {
        return Optional.ofNullable(editor);
    }

}
