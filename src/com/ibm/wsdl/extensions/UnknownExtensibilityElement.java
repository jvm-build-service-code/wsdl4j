package com.ibm.wsdl.extensions;

import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class UnknownExtensibilityElement implements ExtensibilityElement,
                                                    java.io.Serializable
{
  protected QName elementType = null;
  // Uses the wrapper type so we can tell if it was set or not.
  protected Boolean required = null;
  protected Element element = null;

  /**
   * Set the type of this extensibility element.
   *
   * @param elementType the type
   */
  public void setElementType(QName elementType)
  {
    this.elementType = elementType;
  }

  /**
   * Get the type of this extensibility element.
   *
   * @return the extensibility element's type
   */
  public QName getElementType()
  {
    return elementType;
  }

  /**
   * Set whether or not the semantics of this extension
   * are required. Relates to the wsdl:required attribute.
   */
  public void setRequired(Boolean required)
  {
    this.required = required;
  }

  /**
   * Get whether or not the semantics of this extension
   * are required. Relates to the wsdl:required attribute.
   */
  public Boolean getRequired()
  {
    return required;
  }

  /**
   * Set the Element for this extensibility element.
   *
   * @param element the unknown element that was encountered
   */
  public void setElement(Element element)
  {
    this.element = element;
  }

  /**
   * Get the Element for this extensibility element.
   *
   * @return the unknown element that was encountered
   */
  public Element getElement()
  {
    return element;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("UnknownExtensibilityElement (" + elementType + "):");
    strBuf.append("\nrequired=" + required);

    if (element != null)
    {
      strBuf.append("\nelement=" + element);
    }

    return strBuf.toString();
  }
}