/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.patientdataservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.socraticgrid.documenttransformer.Transformer;

/**
 *
 * @author Jerry Goodnough
 */
public class MainRetriever implements DataRetriever
{

    public MainRetriever()
    {
        DataSources = new HashMap<String, DataSourceBinding>();
    }
    private Transformer transformer;

    /**
     * Get the value of transformer
     *
     * @return the value of transformer
     */
    public Transformer getTransformer()
    {
        return transformer;
    }

    /**
     * Set the value of transformer
     *
     * @param transformer new value of transformer
     */
    public void setTransformer(Transformer transformer)
    {
        this.transformer = transformer;
    }
    private Map<String, DataSourceBinding> DataSources;

    /**
     * Get the value of DataSources
     *
     * @return the value of DataSources
     */
    public Map<String, DataSourceBinding> getDataSources()
    {
        return DataSources;
    }

    /**
     * Set the value of DataSources
     *
     * @param DataSources new value of DataSources
     */
    public void setDataSources(Map<String, DataSourceBinding> DataSources)
    {
        this.DataSources = DataSources;
    }

    public String getData(String Source, String Domain, String Id)
    {
        return this.getStreamAsString(getDataAsStream(Source, Domain, Id));
    }

    public String getRawData(String Source, String Domain, String Id)
    {
        return this.getStreamAsString(getRawDataAsStream(Source, Domain, Id));
    }

    public InputStream getDataAsStream(String Source, String Domain, String Id)
    {
        if (DataSources.containsKey(Source))
        {
            //Valid Source
            DataSourceBinding ds = DataSources.get(Source);
            if (ds.getDataSource().isDomainSupported(Domain))
            {
                //Fetch Data Data by Id form the source
                InputStream data = ds.getDataSource().getData(Domain, Id, null);
                // Apply any transforms
                String pipeline = ds.getTransforms().get(Domain);
                if ((pipeline != null) &&(transformer!=null))
                {
                    Properties props = new Properties();
                    props.setProperty("patientId", Id);
                    return transformer.transformAsStream(pipeline, data, props);
                }
                else
                {
                    //No Transform defined for the path
                    //Give the Data found
                    return data;
                }
            }
            else
            {
                //Error Invalid input
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public InputStream getRawDataAsStream(String Source, String Domain, String Id)
    {
        if (DataSources.containsKey(Source))
        {
            //Valid Source
            DataSourceBinding ds = DataSources.get(Source);
            if (ds.getDataSource().isDomainSupported(Domain))
            {
                //Fetch Data Data by Id form the source
                return ds.getDataSource().getData(Domain, Id, null);
            }
            else
            {
                //Error Invalid input
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected String getStreamAsString(InputStream is)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            org.apache.commons.io.IOUtils.copy(is, out);
        }
        catch (IOException ex)
        {
        }
        return out.toString();
    }
}
