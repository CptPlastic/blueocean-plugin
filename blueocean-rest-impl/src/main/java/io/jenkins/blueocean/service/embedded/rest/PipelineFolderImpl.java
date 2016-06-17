package io.jenkins.blueocean.service.embedded.rest;

import hudson.model.ItemGroup;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.rest.model.BluePipeline;
import io.jenkins.blueocean.rest.model.BluePipelineContainer;
import io.jenkins.blueocean.rest.model.BluePipelineFolder;
import io.jenkins.blueocean.service.embedded.util.FavoriteUtil;
import org.kohsuke.stapler.json.JsonBody;

/**
 * @author Vivek Pandey
 */
public class PipelineFolderImpl extends BluePipelineFolder {

    private final ItemGroup folder;
    private final BluePipelineContainer container;
    final OrganizationImpl organization;

    public PipelineFolderImpl(OrganizationImpl organization, ItemGroup folder) {
        this.folder = folder;
        this.container = new PipelineContainerImpl(organization, folder);
        this.organization = organization;
    }

    @Override
    public String getOrganization() {
        return organization.getName();
    }

    @Override
    public String getName() {
        return folder.getDisplayName();
    }

    @Override
    public String getDisplayName() {
        return folder.getDisplayName();
    }

    @Override
    public String getFullName() {
        return folder.getFullName();
    }

    @Override
    public BluePipelineContainer getPipelines() {
        return new PipelineContainerImpl(organization, folder);
    }

    @Override
    public Integer getNumberOfFolders() {
        int count=0;
        for(BluePipeline p:getPipelines ()){
            if(p instanceof BluePipelineFolder){
                count++;
            }
        }
        return count;
    }

    @Override
    public Integer getNumberOfPipelines() {
        int count=0;
        for(BluePipeline p:getPipelines ()){
            if(!(p instanceof BluePipelineFolder)){
                count++;
            }
        }
        return count;
    }


    @Override
    public void favorite(@JsonBody FavoriteAction favoriteAction) {
        if(favoriteAction == null) {
            throw new ServiceException.BadRequestExpception("Must provide pipeline name");
        }

        FavoriteUtil.favoriteJob(folder.getFullName(), favoriteAction.isFavorite());
    }
}
