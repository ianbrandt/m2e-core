/*******************************************************************************
 * Copyright (c) 2008 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.m2e.core.ui.internal.lifecyclemapping;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.osgi.util.NLS;

import org.apache.maven.project.MavenProject;

import org.eclipse.m2e.core.internal.lifecyclemapping.LifecycleMappingFactory;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.ILifecycleMappingRequirement;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.LifecycleMappingConfiguration;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.MojoExecutionMappingConfiguration;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.ProjectLifecycleMappingConfiguration;
import org.eclipse.m2e.core.project.configurator.MojoExecutionKey;


/**
 * MojoExecutionMappingLabelProvider
 * 
 * @author igor
 */
@SuppressWarnings("restriction")
public class MojoExecutionMappingLabelProvider implements ILifecycleMappingLabelProvider {

  private final MojoExecutionMappingConfiguration element;

  private final ProjectLifecycleMappingConfiguration prjconf;

  public MojoExecutionMappingLabelProvider(ProjectLifecycleMappingConfiguration prjconf,
      MojoExecutionMappingConfiguration element) {
    this.element = element;
    this.prjconf = prjconf;
  }

  public String getMavenText() {
    MojoExecutionKey execution = element.getExecution();
    if("default".equals(execution.getExecutionId())) {
      return NLS.bind("{0}", prjconf.getRelpath());
    }
    //TODO is execution id actually important or just takes up space
    return NLS.bind("Execution {0}, in {1}", execution.getExecutionId(), prjconf.getRelpath());
  }

  /* (non-Javadoc)
   * @see org.eclipse.m2e.core.ui.internal.lifecyclemapping.ILifecycleMappingLabelProvider#isError()
   */
  public boolean isError(LifecycleMappingConfiguration mappingConfiguration) {
    ILifecycleMappingRequirement requirement = element.getLifecycleMappingRequirement();
    return LifecycleMappingFactory.isInterestingPhase(element.getMojoExecutionKey().getLifecyclePhase())
        && !mappingConfiguration.isRequirementSatisfied(requirement, true);
  }

  /* (non-Javadoc)
   * @see org.eclipse.m2e.core.ui.internal.lifecyclemapping.ILifecycleMappingLabelProvider#getKey()
   */
  public ILifecycleMappingRequirement getKey() {
    return element.getLifecycleMappingRequirement();
  }

  /* (non-Javadoc)
   * @see org.eclipse.m2e.core.ui.internal.lifecyclemapping.ILifecycleMappingLabelProvider#getProjects()
   */
  public Collection<MavenProject> getProjects() {
    return Collections.singleton(prjconf.getMavenProject());
  }
}
