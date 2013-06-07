/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.springframework.core.io.Resource;

/**
 *
 * @author Jerry Goodnough
 */
public class StaticDataSource implements DataSource
{

    public StaticDataSource()
    {
    }

    private Map<String , Resource> domainResources ;
    /**
     * Get the value of domainResources
     *
     * Maps Domain to Resource
     * 
     * @return the value of domainResources
     */
    public Map<String, Resource> getDomainResources()
    {
        return domainResources;
    }

    /**
     * Set the value of domainResources
     *
     * @param domainResources new value of domainResources
     */
    public void setDomainResources(Map<String , Resource> domainResources) {
        this.domainResources = domainResources;
    }

    public boolean isDomainSupported(String domain)
    {
       return domainResources.containsKey(domain);
    }

    public InputStream getData(String domain, String id)
    {
    
        InputStream out=null;
        try
        {
            out=domainResources.get(domain).getInputStream();
        }
        catch(IOException exp)
        {
            
        }
        return out;
        
    }
    
}
