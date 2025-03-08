package extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class TestLoggerExtension implements AfterEachCallback {

    private static final Logger logger = LoggerFactory.getLogger(TestLoggerExtension.class);

    @Override
    public void afterEach(ExtensionContext context) {
        context.getTestInstance().ifPresent(instance -> {
            Arrays.stream(instance.getClass().getDeclaredFields()).forEach(field -> {
                if (!containsWatch(field.getDeclaredAnnotations())) {
                    return;
                }
                try {
                    field.setAccessible(true);
                    Object o = field.get(instance);
                    logger.info(() -> String.format("Field's [%s] state after execute test [%s]: %s",
                            field.getName(),
                            context.getDisplayName(),
                            o));
                } catch (IllegalAccessException e) {
                    logger.error(e, () -> "Failed to read field: " + field.getName());
                }
            });
        });
    }

    private boolean containsWatch(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations)
                .anyMatch(declaredAnnotation -> declaredAnnotation instanceof Watch);
    }
}
