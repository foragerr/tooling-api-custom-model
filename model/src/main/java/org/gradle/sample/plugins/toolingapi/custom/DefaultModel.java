package org.gradle.sample.plugins.toolingapi.custom;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DefaultModel implements Serializable {
    private final List<String> pluginClassNames;
    private List<File> javaSrcDirs=null;

    public List<File> getjavaSrcDirs() { return javaSrcDirs; }

    public void setjavaSrcDirs(List<File> javaSrcDirs) {
        this.javaSrcDirs = javaSrcDirs;
    }

    public DefaultModel(List<String> pluginClassNames) {
        this.pluginClassNames = pluginClassNames;        
    }
    
    public boolean hasPlugin(Class type) {              
        return pluginClassNames.contains(type.getName());
    }

    public String getJavaSrcDir(int i) throws IOException { return javaSrcDirs.get(i).getCanonicalPath(); }
}
