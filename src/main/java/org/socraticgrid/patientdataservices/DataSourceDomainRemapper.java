/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * The class provides a facade that can be used remap domain names for a nested
 * data source. Depending on the
 *
 * @author Jerry Goodnough
 */
public class DataSourceDomainRemapper implements DataSource
{
    private DataSource nestedDataSource;

    private boolean useUpperCase = false;
    private boolean useMappedDomainsOnly = false;

    private Map<String, String> domainNameMap = new HashMap();

    /**
     * Get the value of nestedDataSource
     *
     * @return the value of nestedDataSource
     */
    public DataSource getNestedDataSource()
    {
        return nestedDataSource;
    }

    /**
     * Get the value of useUpperCase.
     * If true the Input domain is converts to uppercase before the remapping is done.
     *
     * @return the value of useUpperCase
     */
    public boolean isUseUpperCase()
    {
        return useUpperCase;
    }

    /**
     * Set the value of useUpperCase
     *
     * @param useUpperCase new value of useUpperCase
     */
    public void setUseUpperCase(boolean useUpperCase)
    {
        this.useUpperCase = useUpperCase;
    }


    /**
     * Set the value of nestedDataSource
     *
     * @param nestedDataSource new value of nestedDataSource
     */
    public void setNestedDataSource(DataSource nestedDataSource)
    {
        this.nestedDataSource = nestedDataSource;
    }

    /**
     * Get the value of useMappedDomainsOnly
     * When set to true only those domains domains which are mapped
     * will be used.
     *
     * @return the value of useMappedDomainsOnly
     */
    public boolean isUseMappedDomainsOnly()
    {
        return useMappedDomainsOnly;
    }

    /**
     * Set the value of useMappedDomainsOnly
     *
     * @param useMappedDomainsOnly new value of useMappedDomainsOnly
     */
    public void setUseMappedDomainsOnly(boolean useMappedDomainsOnly)
    {
        this.useMappedDomainsOnly = useMappedDomainsOnly;
    }

    /**
     * Get the value of domainNameMap
     *
     * @return the value of domainNameMap
     */
    public Map<String, String> getDomainNameMap()
    {
        return domainNameMap;
    }

    /**
     * Set the value of domainNameMap
     *
     * @param DomainNameMap
     */
    public void setDomainNameMap(Map<String, String> DomainNameMap)
    {
        this.domainNameMap = DomainNameMap;
    }


    /**
     *
     * @param domain
     * @return
     */
    @Override public boolean isDomainSupported(String domain)
    {

        if (this.useUpperCase)
        {
            domain = domain.toUpperCase();
        }

        if (this.useMappedDomainsOnly)
        {

            if (this.domainNameMap.containsKey(domain) == false)
            {
                return false;
            }
            else
            {
                return this.nestedDataSource.isDomainSupported(
                        domainNameMap.get(domain));
            }
        }
        else
        {

            if (this.domainNameMap.containsKey(domain))
            {
                domain = domainNameMap.get(domain);
            }

            return this.nestedDataSource.isDomainSupported(domain);
        }
    }

    /**
     * Delegate to the nested Data Source after renaming the domain
     *
     * @param domain
     * @param id
     * @param props
     * @return
     */
    @Override public InputStream getData(String domain, String id,
        Properties props)
    {

        if (this.useUpperCase)
        {
            domain = domain.toUpperCase();
        }

        if (this.useMappedDomainsOnly)
        {

            if (this.domainNameMap.containsKey(domain) == false)
            {
                return null;
            }
            else
            {
                return this.nestedDataSource.getData(domainNameMap.get(
                            domain), id, props);
            }
        }
        else
        {

            if (this.domainNameMap.containsKey(domain))
            {
                domain = domainNameMap.get(domain);
            }

            return this.nestedDataSource.getData(domain, id, props);
        }
    }

}
