/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author Jerry Goodnough
 */
public class DomainBasedSource extends BaseDataSource
{

    @Override
    public boolean isDomainSupported(String domain)
    {
        if (this.domainSource.containsKey(domain))
        {
            return true;
        }
        else if (this.defaultSource!= null)
        {
            return defaultSource.isDomainSupported(domain);
                    
        }
        return false;
    }
    
    private DataSource defaultSource;

    /**
     * Get the value of defaultSource
     *
     * @return the value of defaultSource
     */
    public DataSource getDefaultSource()
    {
        return defaultSource;
    }

    /**
     * Set the value of defaultSource
     *
     * @param defaultSource new value of defaultSource
     */
    public void setDefaultSource(DataSource defaultSource)
    {
        this.defaultSource = defaultSource;
    }

    private Map<String, DataSource> domainSource;

    /**
     * Get the value of domainSource
     *
     * @return the value of domainSource
     */
    public Map<String, DataSource> getDomainSource()
    {
        return domainSource;
    }

    /**
     * Set the value of domainSource
     *
     * @param domainSource new value of domainSource
     */
    public void setDomainSource(Map<String, DataSource> domainSource)
    {
        this.domainSource = domainSource;
    }


    @Override
    public InputStream getData(String domain, String id)
    {
        if (domainSource.containsKey(domain))
        {
            return domainSource.get(domain).getData(domain, id);
        }
        else if (this.defaultSource!=null)
        {
            return defaultSource.getData(domain, id);
        }
        else
        {
            return null;
        }
    }
    
}
