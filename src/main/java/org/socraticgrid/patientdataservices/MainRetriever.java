/*-
 *
 * *************************************************************************************************************
 *  Copyright (C) 2013 by Cognitive Medical Systems, Inc
 *  (http://www.cognitivemedciine.com) * * Licensed under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in compliance *
 *  with the License. You may obtain a copy of the License at * *
 *  http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 *  law or agreed to in writing, software distributed under the License is *
 *  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. * See the License for the specific language
 *  governing permissions and limitations under the License. *
 * *************************************************************************************************************
 *
 * *************************************************************************************************************
 *  Socratic Grid contains components to which third party terms apply. To comply
 *  with these terms, the following * notice is provided: * * TERMS AND
 *  CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION * Copyright (c) 2008,
 *  Nationwide Health Information Network (NHIN) Connect. All rights reserved. *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that * the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the *     following disclaimer. * - Redistributions in
 *  binary form must reproduce the above copyright notice, this list of
 *  conditions and the *     following disclaimer in the documentation and/or
 *  other materials provided with the distribution. * - Neither the name of the
 *  NHIN Connect Project nor the names of its contributors may be used to endorse
 *  or *     promote products derived from this software without specific prior
 *  written permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 *  AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED * WARRANTIES, INCLUDING,
 *  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, *
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION HOWEVER * CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, * EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. * * END OF TERMS AND CONDITIONS *
 * *************************************************************************************************************/
package org.socraticgrid.patientdataservices;

import org.apache.commons.io.IOUtils;

import org.socraticgrid.documenttransformer.Transformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamSource;
import org.socraticgrid.documenttransformer.TransformInput;


/**
 * Main Patient Information Retriever.
 *
 * @author  Jerry Goodnough
 */
public class MainRetriever implements DataRetriever
{
    private static final Logger logger = Logger.getLogger(MainRetriever.class
            .getName());
    private Map<String, DataSourceBinding> DataSources;
    private Transformer transformer;

    public MainRetriever()
    {
    }

    @Override
    public String getData(String Source, String Domain, String Id,
        Properties srcProps)
    {
        return this.getStreamAsString(getDataAsStream(Source, Domain, Id, srcProps));
    }

    @Override
    public InputStream getDataAsStream(String Source, String Domain, String Id,
        Properties srcProps)
    {

        if (DataSources.containsKey(Source))
        {

            if (Domain.contains(";") == false)
            {
                return this.internalGetDomainAsStream(Source, Domain, Id, srcProps);
            }
            else
            {

                // Valid Source
                DataSourceBinding ds = DataSources.get(Source);

                if (ds.getMergeTransformer() == null)
                {   
                    //Since now merge transformer is defined delgate this call
                    return this.internalGetDomainAsStream(Source, Domain, Id, srcProps);
                }
                else
                {
                  //First loop each domain and collect the results
                  StringTokenizer tok = new StringTokenizer(Domain,";");
                  TransformInput in = new  TransformInput();
                  boolean  baseFnd = false;
                  while (tok.hasMoreTokens())
                  {
                      String dom = tok.nextToken();
                      if (baseFnd == false)
                      {
                          in.setBaseStreamName(dom);
                          baseFnd=true;
                      }
                      InputStream res = this.internalGetDomainAsStream(Source, dom, Id, srcProps);
                      StreamSource src = new StreamSource(res);
                      in.setStream(dom, src);
                  }
                  return ds.getMergeTransformer().transformAsStream(ds.getMergePipelineName(), in, srcProps);
                }
            }
        }

        throw new UnsupportedOperationException("Source [" + Source +
            "] is not supported");
    }

    /**
     * Get the value of DataSources.
     *
     * @return  the value of DataSources
     */
    public Map<String, DataSourceBinding> getDataSources()
    {
        return DataSources;
    }

    @Override
    public String getRawData(String Source, String Domain, String Id,
        Properties srcProps)
    {
        return this.getStreamAsString(getRawDataAsStream(Source, Domain, Id,
                    srcProps));
    }

    @Override
    public InputStream getRawDataAsStream(String Source, String Domain, String Id,
        Properties srcProps)
    {

        if (DataSources.containsKey(Source))
        {

            // Valid Source
            DataSourceBinding ds = DataSources.get(Source);

            if (ds.getDataSource().isDomainSupported(Domain))
            {

                // Fetch Data Data by Id form the source
                return ds.getDataSource().getData(Domain, Id, srcProps);
            }
            else
            {
                // Error Invalid input
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the value of transformer.
     *
     * @return  the value of transformer
     */
    public Transformer getTransformer()
    {
        return transformer;
    }

    public InputStream internalGetDomainAsStream(String Source, String Domain,
        String Id, Properties srcProps)
    {

        if (DataSources.containsKey(Source))
        {

            // Valid Source
            DataSourceBinding ds = DataSources.get(Source);

            if (ds.getDataSource().isDomainSupported(Domain))
            {

                // Fetch Data Data by Id form the source
                InputStream data = ds.getDataSource().getData(Domain, Id, srcProps);

                // Apply any transforms
                String pipeline = ds.getTransforms().get(Domain);

                if ((pipeline != null) && (transformer != null))
                {
                    Properties props = new Properties();
                    props.setProperty("patientId", Id);

                    if (logger.isLoggable(Level.FINE))
                    {
                        logger.log(Level.FINE, "Transfroming on pipeline {0}",
                            pipeline);

                        if (data.markSupported())
                        {

                            try
                            {
                                String out = IOUtils.toString(data, "UTF-8");
                                data.reset();
                                logger.fine("Stream prior to Transform");
                                logger.fine(out);
                            }
                            catch (IOException exp)
                            {
                                logger.log(Level.FINE,
                                    "Error converting Stream to string for diagnosistic output",
                                    exp);
                            }
                        }
                        else
                        {
                            logger.fine(
                                "Stream does not support role back, can not dump to log");
                        }
                    }

                    return transformer.transformAsStream(pipeline, data, props);
                }
                else
                {

                    // No Transform defined for the path
                    // Give the Data found
                    return data;
                }
            }
            else
            {
                // Error Invalid input
            }
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Set the value of DataSources.
     *
     * @param  DataSources  new value of DataSources
     */
    public void setDataSources(Map<String, DataSourceBinding> DataSources)
    {
        this.DataSources = DataSources;
    }

    /**
     * Set the value of transformer.
     *
     * @param  transformer  new value of transformer
     */
    public void setTransformer(Transformer transformer)
    {
        this.transformer = transformer;
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
