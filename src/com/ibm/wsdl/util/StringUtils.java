package com.ibm.wsdl.util;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.beans.Introspector;

/**
 * Deals with strings (probably need to elaborate some more).
 *
 * @author   Matthew J. Duftler
 */
public class StringUtils
{
  public static final String lineSeparator =
    System.getProperty("line.separator", "\n");
  public static final String lineSeparatorStr = cleanString(lineSeparator);

  // Handles multi-line strings.
  public static String getSafeString(String scriptStr)
  {
    BufferedReader in           = new BufferedReader(new StringReader(scriptStr));
    StringBuffer   strBuf       = new StringBuffer();
    String         tempLine,
                   previousLine = null;

    try
    {
      while ((tempLine = in.readLine()) != null)
      {
        if (previousLine != null)
        {
          strBuf.append("\"" + previousLine + lineSeparatorStr + "\" +" +
                        lineSeparator);
        }

        previousLine = cleanString(tempLine);
      }
    }
    catch (IOException e)
    {
    }      

    strBuf.append("\"" + (previousLine != null ? previousLine : "") + "\"" +
                  lineSeparator);

    return strBuf.toString();
  }

  // Ensure that escape sequences are passed through properly.
  public static String cleanString(String str)
  {
    if (str == null)
      return null;
    else
    {
      char[]       charArray = str.toCharArray();
      StringBuffer sBuf      = new StringBuffer();
      
      for (int i = 0; i < charArray.length; i++)
        switch (charArray[i])
        {
          case '\"' : sBuf.append("\\\"");
                      break;
          case '\\' : sBuf.append("\\\\");
                      break;
          case '\n' : sBuf.append("\\n");
                      break;
          case '\r' : sBuf.append("\\r");
                      break;
          default   : sBuf.append(charArray[i]);
                      break;
        }
      
      return sBuf.toString();
    }
  }

  /*
    This method will return the correct name for a class object representing
    a primitive, a single instance of a class, as well as n-dimensional arrays
    of primitives or instances. This logic is needed to handle the string returned
    from Class.getName(). If the class object represents a single instance (or
    a primitive), Class.getName() returns the fully-qualified name of the class
    and no further work is needed. However, if the class object represents an
    array (of n dimensions), Class.getName() returns a Descriptor (the Descriptor
    grammar is defined in section 4.3 of the Java VM Spec). This method will
    parse the Descriptor if necessary.
  */
  public static String getClassName(Class targetClass)
  {
    String className = targetClass.getName();

    return targetClass.isArray() ? parseDescriptor(className) : className;
  }

  /*
    See the comment above for getClassName(targetClass)...
  */
  private static String parseDescriptor(String className)
  {
    char[] classNameChars = className.toCharArray();
    int    arrayDim       = 0;
    int    i              = 0;

    while (classNameChars[i] == '[')
    {
      arrayDim++;
      i++;
    }

    StringBuffer classNameBuf = new StringBuffer();

    switch (classNameChars[i++])
    {
      case 'B' : classNameBuf.append("byte");
                 break;
      case 'C' : classNameBuf.append("char");
                 break;
      case 'D' : classNameBuf.append("double");
                 break;
      case 'F' : classNameBuf.append("float");
                 break;
      case 'I' : classNameBuf.append("int");
                 break;
      case 'J' : classNameBuf.append("long");
                 break;
      case 'S' : classNameBuf.append("short");
                 break;
      case 'Z' : classNameBuf.append("boolean");
                 break;
      case 'L' : classNameBuf.append(classNameChars,
                                     i, classNameChars.length - i - 1);
                 break;
    }

    for (i = 0; i < arrayDim; i++)
      classNameBuf.append("[]");

    return classNameBuf.toString();
  }

  public static String getCommaListFromVector(Vector sourceVector)
  {
    StringBuffer strBuf = new StringBuffer();

    for (int i = 0; i < sourceVector.size(); i++)
    {
      strBuf.append((i > 0 ? ", " : "") +
                    sourceVector.elementAt(i));
    }

    return strBuf.toString();
  }

  /**
   * Get a string consisting of <code>numberOfChars</code> theChars.
   *
   * @return a string consisting of <code>numberOfChars</code> theChars.
   */
  public static String getChars(int numberOfChars, char theChar)
  {
    if (numberOfChars <= 0)
      return "";

    StringBuffer sRet = new StringBuffer(numberOfChars);

    for (int i = 0; i < numberOfChars; i++)
      sRet.append(theChar);     

    return sRet.toString();
  }

  public static boolean isValidIdentifierName(String identifierName)
  {
    if (identifierName == null || identifierName.length() == 0)
      return false;

    char[] chars = identifierName.toCharArray();

    if (!Character.isJavaIdentifierStart(chars[0]))
      return false;

    for (int i = 1; i < chars.length; i++)
      if (!Character.isJavaIdentifierPart(chars[i]))
        return false;

    return true;
  }

  public static boolean isValidPackageName(String packageName)
  {
    if (packageName == null)
      return false;
    else if (packageName.length() == 0)
      // Empty is ok.
      return true;

    StringTokenizer strTok = new StringTokenizer(packageName, ".", true);

    // Should have an odd number of tokens (including '.' delimiters).
    if (strTok.countTokens() % 2 != 1)
      return false;

    // Must start with a valid identifier name.
    if (!isValidIdentifierName(strTok.nextToken()))
      return false;

    // ... followed by 0 or more of ".ValidIdentifier".
    while (strTok.hasMoreTokens())
    {
      // Must be a '.'.
      if (!strTok.nextToken().equals("."))
        return false;

      // Must be a valid identifier name.
      if (strTok.hasMoreTokens())
      {
        if (!isValidIdentifierName(strTok.nextToken()))
          return false;
      }
      else
        return false;
    }

    return true;
  }

  public static String classNameToVarName(String className)
  {
    // Might represent an array.
    int arrayDim = 0;

    while (className.endsWith("[]"))
    {
      className = className.substring(0, className.length() - 2);
      arrayDim++;
    }

    int    iLastPeriod = className.lastIndexOf('.');
    String varName     = Introspector.decapitalize(
                                         iLastPeriod != -1
                                         ? className.substring(iLastPeriod + 1)
                                         : className);

    if (arrayDim > 0)
    {
      varName += "_" + arrayDim + "D";
    }

    return getValidIdentifierName(varName);
  }

  public static String getValidIdentifierName(String identifierName)
  {
    if (identifierName == null || identifierName.length() == 0)
      return null;

    StringBuffer strBuf = new StringBuffer();

    char[] chars = identifierName.toCharArray();

    strBuf.append(Character.isJavaIdentifierStart(chars[0])
                  ? chars[0]
                  : '_'
                 );

    for (int i = 1; i < chars.length; i++)
    {
      strBuf.append(Character.isJavaIdentifierPart(chars[i])
                    ? chars[i]
                    : '_'
                   );
    }

    return strBuf.toString();
  }

  /*
  @param contextURL the context in which to attempt to resolve the spec.
  Effectively a document base.
  */
  public static URL getURL(URL contextURL, String spec)
    throws MalformedURLException
  {
    if (contextURL != null)
    {
      File tempFile = new File(spec);

      if (tempFile.isAbsolute())
      {
        return tempFile.toURL();
      }
    }

    try
    {
      return new URL(contextURL, spec);
    }
    catch (MalformedURLException e)
    {
      if (contextURL == null)
      {
        return new File(spec).toURL();
      }
      else
      {
        throw e;
      }
    }
  }

  /*
    Returns an InputStream for reading from the specified resource, if the
    resource points to a stream.
  */
  public static InputStream getContentAsInputStream(URL url)
    throws SecurityException,
           IllegalArgumentException,
           IOException
  {
    if (url == null)
    {
      throw new IllegalArgumentException("URL cannot be null.");
    }

    try
    {
      Object content = url.getContent();

      if (content == null)
      {
        throw new IllegalArgumentException("No content.");
      }

      if (content instanceof InputStream)
      {
        return (InputStream)content;
      }
      else
      {
        throw new IllegalArgumentException((content instanceof String)
                                           ? (String)content
                                           : "This URL points to a: " +
                                             StringUtils.getClassName(content.getClass()));
      }
    }
    catch (SecurityException e)
    {
      throw new SecurityException("Your JVM's SecurityManager has " +
                                  "disallowed this.");
    }
    catch (FileNotFoundException e)
    {
      throw new FileNotFoundException("This file was not found: " + url);
    }
  }

  public static List parseNMTokens(String nmTokens)
  {
    StringTokenizer strTok = new StringTokenizer(nmTokens, " ");
    List tokens = new Vector();

    while (strTok.hasMoreTokens())
    {
      tokens.add(strTok.nextToken());
    }

    return tokens;
  }

  public static String getNMTokens(List list)
  {
    if (list != null)
    {
      StringBuffer strBuf = new StringBuffer();
      int size = list.size();

      for (int i = 0; i < size; i++)
      {
        String token = (String)list.get(i);

        strBuf.append((i > 0 ? " " : "") + token);
      }

      return strBuf.toString();
    }
    else
    {
      return null;
    }
  }
}