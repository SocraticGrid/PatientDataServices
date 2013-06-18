/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.socraticgrid.patientdataservices;

import junit.framework.TestCase;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 *
 * @author Jerry Goodnough
 */
// in the root of the classpath
@ContextConfiguration(locations = { "classpath:TestSpringXMLConfig.xml" })
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataSourceDomainRemapperTest extends TestCase
{

    @Autowired
    ApplicationContext ctx;

    public DataSourceDomainRemapperTest()
    {
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    private DataSourceDomainRemapper getInstance()
    {
        return (DataSourceDomainRemapper) ctx.getBean(
                "DataSourceDomainRemapper");
    }

    /**
     * Test of getNestedDataSource method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testGetNestedDataSource()
    {
        System.out.println("getNestedDataSource");

        DataSourceDomainRemapper instance = getInstance();
        DataSource result = instance.getNestedDataSource();
        assertNotNull(result);

    }

    /**
     * Test of isUseUpperCase method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testIsUseUpperCase()
    {
        System.out.println("isUseUpperCase");

        DataSourceDomainRemapper instance = getInstance();
        boolean expResult = false;
        boolean result = instance.isUseUpperCase();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUseUpperCase method, of class DataSourceDomainRemapper.
     */
    @Test
    @DirtiesContext
    public void testSetUseUpperCase()
    {
        System.out.println("setUseUpperCase");

        boolean useUpperCase = true;
        DataSourceDomainRemapper instance = getInstance();
        instance.setUseUpperCase(useUpperCase);

        boolean expResult = true;
        boolean result = instance.isUseUpperCase();
        assertEquals(expResult, result);

    }

    /**
     * Test of setNestedDataSource method, of class DataSourceDomainRemapper.
     */
    @Test
    @DirtiesContext
    public void testSetNestedDataSource()
    {
        System.out.println("setNestedDataSource");

        DataSource nestedDataSource = (DataSource) ctx.getBean("StaticSource");
        DataSourceDomainRemapper instance = getInstance();
        instance.setNestedDataSource(nestedDataSource);


    }

    /**
     * Test of isUseMappedDomainsOnly method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testIsUseMappedDomainsOnly()
    {
        System.out.println("isUseMappedDomainsOnly");

        DataSourceDomainRemapper instance = getInstance();
        boolean expResult = false;
        boolean result = instance.isUseMappedDomainsOnly();
        assertEquals(expResult, result);

    }

    /**
     * Test of setUseMappedDomainsOnly method, of class
     * DataSourceDomainRemapper.
     */
    @Test
    @DirtiesContext
    public void testSetUseMappedDomainsOnly()
    {
        System.out.println("setUseMappedDomainsOnly");

        boolean useMappedDomainsOnly = true;
        DataSourceDomainRemapper instance = getInstance();
        instance.setUseMappedDomainsOnly(useMappedDomainsOnly);

        boolean expResult = true;
        boolean result = instance.isUseMappedDomainsOnly();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDomainNameMap method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testGetDomainNameMap()
    {
        System.out.println("getDomainNameMap");

        DataSourceDomainRemapper instance = getInstance();

        Map result = instance.getDomainNameMap();
        assertNotNull(result);


    }

    /**
     * Test of setDomainNameMap method, of class DataSourceDomainRemapper.
     */
    @Test
    @DirtiesContext
    public void testSetDomainNameMap()
    {
        System.out.println("setDomainNameMap");

        Map<String, String> DomainNameMap = new HashMap<String, String>();
        DomainNameMap.put("test", "demo");

        DataSourceDomainRemapper instance = getInstance();
        instance.setDomainNameMap(DomainNameMap);


    }

    /**
     * Test of isDomainSupported method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testIsDomainSupported()
    {
        System.out.println("isDomainSupported");

        String domain = "demo";
        DataSourceDomainRemapper instance = getInstance();

        boolean result = instance.isDomainSupported(domain);
        assertTrue(result);
        domain = "unsupported";
        result = instance.isDomainSupported(domain);
        assertFalse(result);
        domain = "demographics";
        result = instance.isDomainSupported(domain);
        assertTrue(result);
    }

    /**
     * Test of getData method, of class DataSourceDomainRemapper.
     */
    @Test
    public void testGetData()
    {
        System.out.println("getData");

        String domain = "demographics";
        String id = "1";
        Properties props = null;
        DataSourceDomainRemapper instance = getInstance();

        InputStream result = instance.getData(domain, id, props);
        assertNotNull(result);


    }
}
