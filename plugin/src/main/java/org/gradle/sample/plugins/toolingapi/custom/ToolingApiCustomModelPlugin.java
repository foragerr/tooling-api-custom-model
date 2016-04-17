package org.gradle.sample.plugins.toolingapi.custom;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.tooling.provider.model.ToolingModelBuilder;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToolingApiCustomModelPlugin implements Plugin<Project> {
    private final ToolingModelBuilderRegistry registry;
   
    @Inject
    public ToolingApiCustomModelPlugin(ToolingModelBuilderRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void apply(Project project) {
        registry.register(new CustomToolingModelBuilder());
    }

    private static class CustomToolingModelBuilder implements ToolingModelBuilder {
        @Override
        public boolean canBuild(String modelName) {
            return modelName.equals(CustomModel.class.getName());
        }

        @Override
        public Object buildAll(String modelName, Project project) {
            List<String> pluginClassNames = new ArrayList<String>();

            for(Plugin plugin : project.getPlugins()) {
                pluginClassNames.add(plugin.getClass().getName());
            }
            DefaultModel model =  new DefaultModel(pluginClassNames);

            if (pluginClassNames.contains(JavaPlugin.class.getName())){
                JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
                Map<String, SourceSet> map = (Map<String, SourceSet>)javaConvention.getSourceSets().getAsMap();
                List<File> srcDirList = new ArrayList<File>();
                srcDirList.addAll(map.get("main").getJava().getSrcDirs());
                model.setjavaSrcDirs(srcDirList);
            }
            return model;
        }
    }
}
