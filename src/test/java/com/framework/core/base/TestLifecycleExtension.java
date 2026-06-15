package com.framework.core.base;

import com.framework.core.utils.AllureUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestLifecycleExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_");
        AllureUtils.attachScreenshot("Failure Screenshot");
        AllureUtils.attachTrace(testName);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        // no-op — screenshots only on failure
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        // no-op
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        AllureUtils.attachScreenshot("Aborted Screenshot");
    }
}
