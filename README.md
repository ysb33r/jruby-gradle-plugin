# JRuby Gradle plugin

[![Build Status](https://travis-ci.org/jruby-gradle/jruby-gradle-plugin.svg?branch=master)](https://travis-ci.org/jruby-gradle/jruby-gradle-plugin) [ ![Download](https://api.bintray.com/packages/jruby-gradle/plugins/jruby-gradle-plugin/images/download.png) ](https://bintray.com/jruby-gradle/plugins/jruby-gradle-plugin/_latestVersion) [![Gitter chat](https://badges.gitter.im/jruby-gradle/jruby-gradle-plugin.png)](https://gitter.im/jruby-gradle/jruby-gradle-plugin)

Join us on the [jruby-gradle mailing list](https://groups.google.com/forum/#!forum/jruby-gradle)!

The purpose of plugin is to encapsulate useful [Gradle](http://www.gradle.org/)
functionality for JRuby projects. Use of this plugin replaces the need for both
[Bundler](http://bundler.io/) and [Warbler](https://github.com/jruby/warbler)
in JRuby projects.


The Ruby gem dependency code for this project relies on the [Rubygems Maven
proxy](http://rubygems-proxy.torquebox.org/) provided by the
[Torquebox](http://torquebox.org) project.

This is the base plugin. If you are interesting in doing the following then consult the pages for the appropriate
plugins:

* Building JRuby-based Java JARs, including making them executable - 
[jruby-gradle-jar-plugin](http://github.com/jruby-gradle/jruby-gradle-jar-plugin)
* Building JRuby-based Java Web Applications (WARs),  - 
[jruby-gradle-war-plugin](http://github.com/jruby-gradle/jruby-gradle-war-plugin)


## Compatilibity

This plugin requires Gradle 2.0 or better

## Getting Started

```groovy
buildscript {
    repositories { jcenter() }

    dependencies {
      classpath group: 'com.github.jruby-gradle', name: 'jruby-gradle-plugin', version: '0.1.2'
    }
}

apply plugin: 'com.github.jruby-gradle.base'
```

### Adding gems

You can also add Ruby gem dependencies in your `build.gradle` file under the
`gem` configuration, e.g.:

```groovy
dependencies {
    gems group: 'rubygems', name: 'sinatra', version: '1.4.5'
    gems group: 'rubygems', name: 'rake', version: '10.3.+'
}
```

## Default Tasks

The plugin provides the following tasks:

 * `jrubyPrepareGems` - Extract GEMs declared as dependencies under `gems` to `jruby.gemInstallDir`. This is as instance
 of `JRubyPrepareGems`.
 * `jrubyPrepare` - Call `jrubyPrepareGems`. Also copies the
   content of Java-based dependencies into `.jarcache/` for interpreted use

## JRubyExec - Task for Executing a Ruby Script 

In a similar vein to ```JavaExec``` and ```RhinoShellExec```, the ```JRubyExec``` allows for Ruby scripts to be executed
in a Gradle script using JRuby.

```groovy
import com.github.jrubygradle.JRubyExec

dependencies {
    jrubyExec 'rubygems:credit_card_validator:1.2.0'
}

task runMyScript( type: JRubyExec ) {
    script 'scripts/runme.rb'
    scriptArgs '-x', '-y'
}
```

Common methods for ```JRubyExec``` for executing a script

* ```script``` - ```Object``` (Usually File or String). Path to the script.
* ```scriptArgs``` - ```List```.  List of arguments to pass to script.
* ```workingDir``` - ```Object``` (Usually File or String).  Working directory for script.
* ```environment``` - ```Map```.  Environment to be set. Do not set ```GEM_HOME``` or ```GEM_PATH``` with this method.
* ```standardInput``` - ```InputStream```.  Set an input stream to be read by the script.
* ```standardOutput``` - ```OutputStream```.  Capture the output of the script.
* ```errorOutput``` - ```OutputStream```.  Capture the error output of the script.
* ```ignoreExitValue``` - ```Boolean```.  Ignore the JVm exit value. Exit values are only effective if the exit value of the Ruby script is correctly communicated back to the JVM.
* ```configuration``` - ```String```.  Configuration to copy gems from. (*) 
* ```classpath``` - ```List```.  Additional Jars/Directories to place on classpath.
* ```jrubyVersion``` - ```String```.  JRuby version to use if not the same as ```project.jruby.execVersion```.
* ```gemWorkDir``` - ```File```. Provide a custom working directory for unpacking GEMs. By default each `JRubyExec` task
  uses it's own work directory. Use this to set a common work directory for a number of tasks.

(*) If ```jRubyVersion``` has not been set, ```jrubyExec``` will used as
configuration. However, if ```jRubyVersion``` has been set, no gems will be used unless an explicit configuration has been provided

Additional ```JRubyExec``` methods for controlling the JVM instance

* ```jvmArgs``` - ```List```. See [jvmArgs](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:jvmArgs)
* ```allJvmArgs``` - ```List```. See [allJvmArgs](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:allJvmArgs)
* ```systemProperties``` - ```Map```. See [systemProperties](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:systemProperties)
* ```bootstrapClassPath``` - ```FileCollection``` or ```List```. See [bootstrapClassPath](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:bootstrapClasspath)
* ```minHeapSize``` - ```String```. See [minHeapSize](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html)
* ```maxHeapSize``` - ```String```. See [maxHeapSize](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:maxHeapSize)
* ```defaultCharacterEncoding``` - ```String```. See [defaultCharacterEncoding](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html)
* ```enableAssertions``` - ```Boolean```. See [enableAssertions](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:enableAssertions)
* ```debug``` - ```Boolean```. See [debug](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:debug)
* ```copyTo``` - ```JavaForkOptions```. See [copyTo](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html)
* ```executable``` - ```Object``` (Usually ```File``` or ```String```). See [executable](http://www.gradle.org/docs/current/dsl/org.gradle.api.tasks.JavaExec.html#org.gradle.api.tasks.JavaExec:executable)

## jrubyexec extension

Similar to ```javaexec``` and ```exec``` it is possible to add the execution of a jruby script within another task

```groovy
task needSomeRubyLove {
  jrubyexec {
    script 'scripts/runme.rb'
    scriptArgs '-x', '-y'  
  }
}
```

The behaviour of `project.jrubyexec` is slightly different to that of `JRubyExec`.

* The version of `jruby-complete` is strictly tied to the `jruby.execVersion`. Therefore trying to set `jrubyVersion`
in the ```jrubyexec``` closure will cause a failure
* GEMs and additional JARs are only taken from the `jrubyExec` configuration. 
* It is not possible to supply a `configuration` parameter to the `jrubyexec` closure.
* GEMs will be installed to `jruby.gemInstallDir`. Existing gems will not be overwritten.

As with `JRubyExec`, `args`, `setArgs` and `main` are illegal within the `jrubyexec` closure.
All other methods should work.

### Running a Ruby PATH command

Because `JRubyExec` checks for the existence of the script, it might look at first whether running Ruby commands from
`PATH` could be difficult. However, this is totally possible by utilising `jrubyArgs` and passing `-S` as one would do
 when using `ruby` or `jruby` on the command-line. Here is an example of running 
`rake` as task.

```groovy
task rake( type : JRubyExec ) {
    jrubyArgs '-S' 
    script 'rake'
    scriptArgs '/path/to/Rakefile', 'target1', 'target2'
}
```

or even

```groovy
ext {
    rake = { String target ->
        jrubyexec {
            jrubyArgs '-S' 
            script 'rake'
            scriptArgs '/path/to/Rakefile', target            
        }
    }
}
```
 
## JRubyPrepareGems - A task for unpacking GEMs

Unpacking occurs using the default `jruby` version as set by `jruby.execVersion`.

```groovy
import com.github.jrubygradle.JRubyPrepareGems

task unpackMyGems( type : JRubyPrepareGems ) {

  // Parent directory for unpacking GEMs.
  // Gems will end up in a subdirectory 'gems/GemName-GemVersion'
  outputDir buildDir
  
  // Add one or more gems
  // Can be String(s), File(s), FileCollection(s) or Configuration(s)
  gems project.configuration.gems
  
}
```


## Advanced Usage

### Using a custom Gem repository

By default the jruby plugin will use
[rubygems-proxy.torquebox.org](http://rubygems-proxy.torquebox.org) as its
source of Ruby gems. This is a server operated by the Torquebox project which
presents [rubygems.org](https://rubygems.org) as a Maven repository.

If you **do not** wish to use this repository, you can run your own Maven
proxy repository for either rubygems.org or your own gem repository by
running the [rubygems-servlets](https://github.com/torquebox/rubygems-servlets)
server.

You can then use that custom Gem repository with:

```groovy
jruby {
    defaultRepositories = false
}

repositories {
    maven { url : 'http://localhost:8989/releases' }
}

dependencies {
    gems group: 'com.lookout', name: 'custom-gem', version: '1.0.+'
}
```

## Using the Ruby interpreter

There are still plenty of cases, such as for local development, when you might
not want to create a full `.war` file to run some tests. In order to use the
same gems and `.jar` based dependencies, add the following to the entry point
for your application:

```ruby
# Hack our GEM_HOME to make sure that the `rubygems` support can find our
# unpacked gems in build/vendor/
vendored_gems = File.expand_path(File.dirname(__FILE__) + '/build/vendor')
if File.exists?(vendored_gems)
  ENV['GEM_HOME'] = vendored_gems
end

jar_cache = File.expand_path(File.dirname(__FILE__) + '/.jarcache/')
if File.exists?(jar_cache)
  # Under JRuby `require`ing a `.jar` file will result in it being added to the
  # classpath for easy importing
  Dir["#{jar_cache}/*.jar"].each { |j| require j }
end
```

**Note:** in the example above, the `.rb` file is assuming it's in the top
level of the source tree, i.e. where `build.gradle` is located
