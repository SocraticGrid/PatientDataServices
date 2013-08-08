/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.util.HashMap;
import java.util.Map;
import org.socraticgrid.documenttransformer.MergeTransformer;

/**
 *
 * @author Jerry Goodnough
 */
public class DataSourceBinding
{

    public DataSourceBinding()
    {
    }
 
    private DataSource dataSource;

    /**
     * Get the value of dataSource
     *
     * @return the value of dataSource
     */
    public DataSource getDataSource()
    {
        return dataSource;
    }

    /**
     * Set the value of dataSource
     *
     * @param dataSource new value of dataSource
     */
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
       private Map<String, String> transforms = new HashMap<String, String>();

    /**
     * Get the value of transforms (Domain -> Pipeline)
     *
     * @return the value of transforms
     */
    public Map<String, String> getTransforms()
    {
        return transforms;
    }

    /**
     * Set the value of transforms
     *
     * @param transforms new value of transforms
     */
    public void setTransforms(Map<String, String> transforms)
    {
        this.transforms = transforms;
    }
    private MergeTransformer mergeTransformer;

    /**
     * Get the value of mergeTransformer
     *
     * @return the value of mergeTransformer
     */
    public MergeTransformer getMergeTransformer()
    {
        return mergeTransformer;
    }

    /**
     * Set the value of mergeTransformer
     *
     * @param mergeTransformer new value of mergeTransformer
     */
    public void setMergeTransformer(MergeTransformer mergeTransformer)
    {
        this.mergeTransformer = mergeTransformer;
    }
    private String mergePipelineName;

    /**
     * Get the value of mergePipelineName
     *
     * @return the value of mergePipelineName
     */
    public String getMergePipelineName()
    {
        return mergePipelineName;
    }

    /**
     * Set the value of mergePipelineName
     *
     * @param mergePipelineName new value of mergePipelineName
     */
    public void setMergePipelineName(String mergePipelineName)
    {
        this.mergePipelineName = mergePipelineName;
    }

}
