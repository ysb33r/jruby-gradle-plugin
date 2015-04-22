package com.github.jrubygradle

import com.github.jrubygradle.internal.JRubyExecUtils
import groovy.transform.PackageScope
import org.gradle.api.DefaultTask
import org.gradle.api.Incubating
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Prepare embedded jars or all jars used by JRuby by telling the location
 * of those jars via JARS_HOME environment. The directory uses a maven
 * repository layout as per jar-dependencies.
 *
 * @author Schalk W. Cronjé
 * @author R Tyler Croy
 * @author Christian Meier
 */
@Incubating
class JRubyPrepareJars  extends DefaultTask {

    /** Target directory for JARs {@code outputDir + "/jars"}
     */
    @OutputDirectory
    File getOutputDir() { project.file(this.outputDir) }

    /** Sets the output directory
     *
     * @param f Output directory
     */
    void outputDir(Object file) { this.outputDir = file }

    @TaskAction
    void copy() {
        def artifacts = project.configurations.gems.resolvedConfiguration.resolvedArtifacts
        def fileRenameMap = [:]
        def coordinates = []
        def files = artifacts.collect { dep ->

            def group = dep.moduleVersion.id.group
            def groupAsPath = group.replace('.' as char, File.separatorChar)
            def version = dep.moduleVersion.id.version
            // TODO classifier and system-scope
            def newFileName = "${groupAsPath}/${dep.name}/${version}/${dep.name}-${version}.${dep.type}"
            if (group != 'rubygems' ) { coordinates << "${group}:${dep.name}:${version}:" }
            fileRenameMap[dep.file.name] = newFileName
            dep.file
        }

        // create Jars.lock file used by jar-dependencies
        def jarsLock = new File(outputDir, 'Jars.lock')
        jarsLock.parentFile.mkdirs()
        jarsLock.withWriter { writer ->
            coordinates.each { writer.println it }
        }

        project.copy() {
            from files

            include '**/*.jar'
            exclude '**/jruby-complete*.jar'

            rename { oldName -> fileRenameMap[oldName] }
            into(outputDir)
        }
    }

    @PackageScope
    Object outputDir


}

