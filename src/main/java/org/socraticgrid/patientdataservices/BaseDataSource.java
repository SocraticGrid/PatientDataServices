/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.InputStream;

/**
 *
 * @author Jerry Goodnough
 */
public class BaseDataSource implements DataSource
{

    public BaseDataSource()
    {
    }

    public boolean isDomainSupported(String domain)
    {
       return false;
    }

    public InputStream getData(String domain, String id, java.util.Properties props)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Domains 
    //Form by Domain
    
}
