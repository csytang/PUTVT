package highlighter;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Taken from https://github.com/oker1/phpunit_codecoverage_display source code
 */
public class ApplicationPlugin implements ApplicationComponent {
    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "highlighter.ApplicationPlugin";
    }
}
