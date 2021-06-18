/**
 * Copyright (c) 2014-2021, R. Tyler Croy <rtyler@brokenco.de>,
 *     Schalk Cronje <ysb33r@gmail.com>, Christian Meier, Lookout, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.jrubygradle.api.gems;

import java.net.URI;
import java.util.List;

/** GEM metadata.
 *
 * @author Schalk W. Cronjé
 *
 * @since 2.0
 */
public interface GemInfo {

    /** GEM name.
     *
     * @return Name of GEM. Never {@code null}.
     */
    String getName();

    /** GEM version.
     *
     * @return Version of GEM.
     */
    String getVersion();

    /** GEM platform.
     *
     * @return Usage platform for GEM.
     */
    String getPlatform();

    /** Required version of Rubygems.
     *
     * @return Version specification.
     */
    String getRubyGemsVersion();

    /** Required version of Ruby
     *
     * @return Version specification.
     */
    String getRubyVersion();

    /** GEM authors.
     *
     * @return List of authors. Can be empty, but not {@code null}.
     */
    List<String> getAuthors();

    /** GEM short description.
     *
     * @return Summary text.
     */
    String getSummary();

    /** GEM long description.
     *
     * @return Informative text.
     */
    String getDescription();

    /** GEM hash.
     *
     * @return SHA
     */
    String getSha();

    /** Project home.
     *
     * @return URI of project
     */
    URI getProjectUri();

    /** Location to download GEM.
     *
     * @return URI to GEM.
     */
    URI getGemUri();

    /** Project website.
     *
     * @return URI to homepage.
     */
    URI getHomepageUri();

    /** Location of documentation.
     *
     * @return Documentation URI.
     */
    URI getDocumentationUri();

    /** Transitive runtime dependencies.
     *
     * @return List of dependencies. Can be empty, but never {@code null}.
     */
    List<GemDependency> getDependencies();

    /** Transitive development dependencies.
     *
     * @return List of dependencies. Can be empty, but never {@code null}.
     */
    List<GemDependency> getDevelopmentDependencies();

    /** Transitive JAR requirements.
     *
     * @return List of JAR requirements. Can be empty, but never {@code null}
     */
    List<JarDependency> getJarRequirements();

    /** Whether the GEM is still a prerelease version.
     *
     * @return {@code true} for prerelease
     */
    boolean isPrerelease();

}
