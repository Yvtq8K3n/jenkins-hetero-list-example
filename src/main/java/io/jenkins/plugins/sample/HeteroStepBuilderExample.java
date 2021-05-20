package io.jenkins.plugins.sample;

import com.google.common.collect.ImmutableList;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.tasks.BuildStepDescriptor;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.tasks.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HeteroStepBuilderExample extends Builder implements SimpleBuildStep {
    private List<SimpleEntry> entries;

    @DataBoundConstructor
    public HeteroStepBuilderExample(Collection<SimpleEntry> entries) {
        this.entries = entries != null ? new ArrayList<>(entries) : Collections.emptyList();
    }

    public List<SimpleEntry> getEntries(){
        return entries;
    }


    public static final class SimpleEntry extends Entry {
        private final String text;
        @DataBoundConstructor public SimpleEntry(String text) { this.text = text; }
        public String getText() { return text; }

        @Extension public static class DescriptorImpl extends Descriptor<Entry> {
            @Override public String getDisplayName() { return "Simple Entry"; }
        }
    }


    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override public boolean isApplicable(Class<? extends AbstractProject> aClass) { return true; }
        @Override public String getDisplayName() { return "HeteroStepBuilderExample"; }

        public List<Descriptor> getEntryDescriptors() {
            Jenkins jenkins=Jenkins.getInstance();
            return ImmutableList.of(jenkins.getDescriptor(SimpleEntry.class));
        }
    }

    public static abstract class Entry extends AbstractDescribableImpl<Entry> {}
}