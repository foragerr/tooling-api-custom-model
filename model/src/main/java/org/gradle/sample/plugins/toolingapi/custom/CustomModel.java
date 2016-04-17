package org.gradle.sample.plugins.toolingapi.custom;

import java.io.File;
import java.util.Set;

public interface CustomModel {
    boolean hasPlugin(Class type);
    abstract String getJavaSrcDir(int i);
}
