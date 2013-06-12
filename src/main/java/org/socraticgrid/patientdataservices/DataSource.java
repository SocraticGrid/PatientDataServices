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
public interface DataSource
{
    public boolean isDomainSupported(String domain);
    public InputStream getData(String domain, String id, java.util.Properties props);
}
