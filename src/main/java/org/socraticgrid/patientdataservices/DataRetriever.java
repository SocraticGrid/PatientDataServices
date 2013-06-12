/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jerry Goodnough
 */
public interface DataRetriever
{
 
      /* Normal use case */ 
    public InputStream getDataAsStream(String Source, String Domain, String Id, Properties srcProps);
    
    /* Special Escape / Internal */
    public InputStream getRawDataAsStream(String Source, String Domain, String Id, Properties srcProps);
    
    /* Normal use case */ 
    public String getData(String Source, String Domain, String Id, Properties srcProps);
    
    /* Special Escape / Internal */
    public String getRawData(String Source, String Domain, String Id, Properties srcProps);
    
    /* Get and apply specific Transform */
    // public String getDataInForm(String Id, String Domain, String Source, String form);
            
    
    //Common Channel speific specific data 
    //
    //   Name (External - Mapping in Data Retiever)
    //   Domains availble
    //   Form by domain
    
    
    //   Whats Transforms are support on domains
    //   Default Transform on Domain
    
    
}
