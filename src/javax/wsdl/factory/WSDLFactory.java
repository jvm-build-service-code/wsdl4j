/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl.factory;

import java.io.*;
import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.wsdl.xml.*;

/**
 * This abstract class defines a factory API that enables applications
 * to obtain a WSDLFactory capable of producing new Definitions, new
 * WSDLReaders, and new WSDLWriters.
 * 
 * Some ideas used here have been shamelessly copied from the
 * wonderful JAXP and Xerces work.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public abstract class WSDLFactory
{
  private static final String PROPERTY_NAME =
    "javax.wsdl.factory.WSDLFactory";
  private static final String PROPERTY_FILE_NAME =
    "wsdl.properties";
  private static final String DEFAULT_FACTORY_IMPL_NAME =
    "com.ibm.wsdl.factory.WSDLFactoryImpl";

  private static String fullPropertyFileName = null;

  /**
   * Get a new instance of a WSDLFactory. This method
   * follows (almost) the same basic sequence of steps that JAXP
   * follows to determine the fully-qualified class name of the
   * class which implements WSDLFactory. The steps (in order)
   * are:
   *<pre>
   *  Check the javax.wsdl.factory.WSDLFactory system property.
   *  Check the lib/wsdl.properties file in the JRE directory. The key
   * will have the same name as the above system property.
   *  Use the default value.
   *</pre>
   * Once an instance of a WSDLFactory is obtained, invoke
   * newDefinition(), newWSDLReader(), or newWSDLWriter(), to create
   * the desired instances.
   */
  public static WSDLFactory newInstance() throws WSDLException
  {
    String factoryImplName = findFactoryImplName();

    return newInstance(factoryImplName);
  }

  /**
   * Get a new instance of a WSDLFactory. This method
   * returns an instance of the class factoryImplName.
   * Once an instance of a WSDLFactory is obtained, invoke
   * newDefinition(), newWSDLReader(), or newWSDLWriter(), to create
   * the desired instances.
   *
   * @param factoryImplName the fully-qualified class name of the
   * class which provides a concrete implementation of the abstract
   * class WSDLFactory.
   */
  public static WSDLFactory newInstance(String factoryImplName)
    throws WSDLException
  {
    if (factoryImplName != null)
    {
      try
      {
        Class cl = Class.forName(factoryImplName);

        return (WSDLFactory)cl.newInstance();
      }
      catch (Exception e)
      {
        /*
          Catches:
                   ClassNotFoundException
                   InstantiationException
                   IllegalAccessException
        */
        throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                                "Problem instantiating factory " +
                                "implementation.",
                                e);
      }
    }
    else
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "Unable to find name of factory " +
                              "implementation.");
    }
  }

  /**
   * Create a new instance of a Definition.
   */
  public abstract Definition newDefinition();

  /**
   * Create a new instance of a WSDLReader.
   */
  public abstract WSDLReader newWSDLReader();

  /**
   * Create a new instance of a WSDLWriter.
   */
  public abstract WSDLWriter newWSDLWriter();

  /**
   * Create a new instance of an ExtensionRegistry with pre-registered
   * serializers/deserializers for the SOAP, HTTP and MIME
   * extensions. Java extensionTypes are also mapped for all
   * the SOAP, HTTP and MIME extensions.
   */
  public abstract ExtensionRegistry newPopulatedExtensionRegistry();

  private static String findFactoryImplName()
  {
    String factoryImplName = null;

    // First, check the system property.
    try
    {
      factoryImplName = System.getProperty(PROPERTY_NAME);

      if (factoryImplName != null)
      {
        return factoryImplName;
      }
    }
    catch (SecurityException e)
    {
    }

    // Second, check the properties file.
    String propFileName = getFullPropertyFileName();

    if (propFileName != null)
    {
      try
      {
        Properties properties = new Properties();
        File propFile = new File(propFileName);
        FileInputStream fis = new FileInputStream(propFile);

        properties.load(fis);
        fis.close();

        factoryImplName = properties.getProperty(PROPERTY_NAME);

        if (factoryImplName != null)
        {
          return factoryImplName;
        }
      }
      catch (IOException e)
      {
      }
    }

    // Third, return the default.
    return DEFAULT_FACTORY_IMPL_NAME;
  }

  private static String getFullPropertyFileName()
  {
    if (fullPropertyFileName == null)
    {
      try
      {
        String javaHome = System.getProperty("java.home");

        fullPropertyFileName = javaHome + File.separator + "lib" +
                               File.separator + PROPERTY_FILE_NAME;
      }
      catch (SecurityException e)
      {
      }
    }

    return fullPropertyFileName;
  }
}