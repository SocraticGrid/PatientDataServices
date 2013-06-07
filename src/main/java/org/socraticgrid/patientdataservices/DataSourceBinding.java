/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.util.Map;

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
       private Map<String, String> transforms;

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

}
