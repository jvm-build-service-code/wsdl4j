package com.ibm.wsdl.extensions;

import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.wsdl.extensions.soap.*;
import javax.wsdl.extensions.http.*;
import javax.wsdl.extensions.mime.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.extensions.soap.*;
import com.ibm.wsdl.extensions.http.*;
import com.ibm.wsdl.extensions.mime.*;

/**
 * This class extends ExtensionRegistry and pre-registers
 * serializers/deserializers for the SOAP, HTTP and MIME
 * extensions. The defaultSerializer property is set to a
 * new instance of UnknownExtensionSerializer. The
 * defaultDeserializer property is set to a new instance of
 * UnknownExtensionDeserializer. Java extensionTypes are
 * also mapped for all the SOAP, HTTP and MIME extensions.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class PopulatedExtensionRegistry extends ExtensionRegistry
{
  public PopulatedExtensionRegistry()
  {
    setDefaultSerializer(new UnknownExtensionSerializer());
    setDefaultDeserializer(new UnknownExtensionDeserializer());

    SOAPAddressSerializer soapAddressSer = new SOAPAddressSerializer();

    registerSerializer(Port.class,
                       SOAPConstants.Q_ELEM_SOAP_ADDRESS,
                       soapAddressSer);
    registerDeserializer(Port.class,
                         SOAPConstants.Q_ELEM_SOAP_ADDRESS,
                         soapAddressSer);
    mapExtensionTypes(Port.class,
                      SOAPConstants.Q_ELEM_SOAP_ADDRESS,
                      SOAPAddressImpl.class);

    SOAPBindingSerializer soapBindingSer = new SOAPBindingSerializer();

    registerSerializer(Binding.class,
                       SOAPConstants.Q_ELEM_SOAP_BINDING,
                       soapBindingSer);
    registerDeserializer(Binding.class,
                         SOAPConstants.Q_ELEM_SOAP_BINDING,
                         soapBindingSer);
    mapExtensionTypes(Binding.class,
                      SOAPConstants.Q_ELEM_SOAP_BINDING,
                      SOAPBindingImpl.class);

    SOAPHeaderSerializer soapHeaderSer = new SOAPHeaderSerializer();

    registerSerializer(BindingInput.class,
                       SOAPConstants.Q_ELEM_SOAP_HEADER,
                       soapHeaderSer);
    registerDeserializer(BindingInput.class,
                         SOAPConstants.Q_ELEM_SOAP_HEADER,
                         soapHeaderSer);
    mapExtensionTypes(BindingInput.class,
                      SOAPConstants.Q_ELEM_SOAP_HEADER,
                      SOAPHeaderImpl.class);
    registerSerializer(BindingOutput.class,
                       SOAPConstants.Q_ELEM_SOAP_HEADER,
                       soapHeaderSer);
    registerDeserializer(BindingOutput.class,
                         SOAPConstants.Q_ELEM_SOAP_HEADER,
                         soapHeaderSer);
    mapExtensionTypes(BindingOutput.class,
                      SOAPConstants.Q_ELEM_SOAP_HEADER,
                      SOAPHeaderImpl.class);
    mapExtensionTypes(SOAPHeader.class,
                      SOAPConstants.Q_ELEM_SOAP_HEADER_FAULT,
                      SOAPHeaderFaultImpl.class);

    SOAPBodySerializer soapBodySer = new SOAPBodySerializer();

    registerSerializer(BindingInput.class,
                       SOAPConstants.Q_ELEM_SOAP_BODY,
                       soapBodySer);
    registerDeserializer(BindingInput.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);
    mapExtensionTypes(BindingInput.class,
                      SOAPConstants.Q_ELEM_SOAP_BODY,
                      SOAPBodyImpl.class);
    registerSerializer(BindingOutput.class,
                       SOAPConstants.Q_ELEM_SOAP_BODY,
                       soapBodySer);
    registerDeserializer(BindingOutput.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);
    mapExtensionTypes(BindingOutput.class,
                      SOAPConstants.Q_ELEM_SOAP_BODY,
                      SOAPBodyImpl.class);
    registerSerializer(MIMEPart.class,
                       SOAPConstants.Q_ELEM_SOAP_BODY,
                       soapBodySer);
    registerDeserializer(MIMEPart.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);
    mapExtensionTypes(MIMEPart.class,
                      SOAPConstants.Q_ELEM_SOAP_BODY,
                      SOAPBodyImpl.class);

    SOAPFaultSerializer soapFaultSer = new SOAPFaultSerializer();

    registerSerializer(BindingFault.class,
                       SOAPConstants.Q_ELEM_SOAP_FAULT,
                       soapFaultSer);
    registerDeserializer(BindingFault.class,
                         SOAPConstants.Q_ELEM_SOAP_FAULT,
                         soapFaultSer);
    mapExtensionTypes(BindingFault.class,
                      SOAPConstants.Q_ELEM_SOAP_FAULT,
                      SOAPFaultImpl.class);

    SOAPOperationSerializer soapOperationSer = new SOAPOperationSerializer();

    registerSerializer(BindingOperation.class,
                       SOAPConstants.Q_ELEM_SOAP_OPERATION,
                       soapOperationSer);
    registerDeserializer(BindingOperation.class,
                         SOAPConstants.Q_ELEM_SOAP_OPERATION,
                         soapOperationSer);
    mapExtensionTypes(BindingOperation.class,
                      SOAPConstants.Q_ELEM_SOAP_OPERATION,
                      SOAPOperationImpl.class);

    HTTPAddressSerializer httpAddressSer = new HTTPAddressSerializer();

    registerSerializer(Port.class,
                       HTTPConstants.Q_ELEM_HTTP_ADDRESS,
                       httpAddressSer);
    registerDeserializer(Port.class,
                         HTTPConstants.Q_ELEM_HTTP_ADDRESS,
                         httpAddressSer);
    mapExtensionTypes(Port.class,
                      HTTPConstants.Q_ELEM_HTTP_ADDRESS,
                      HTTPAddressImpl.class);

    HTTPOperationSerializer httpOperationSer = new HTTPOperationSerializer();

    registerSerializer(BindingOperation.class,
                       HTTPConstants.Q_ELEM_HTTP_OPERATION,
                       httpOperationSer);
    registerDeserializer(BindingOperation.class,
                         HTTPConstants.Q_ELEM_HTTP_OPERATION,
                         httpOperationSer);
    mapExtensionTypes(BindingOperation.class,
                      HTTPConstants.Q_ELEM_HTTP_OPERATION,
                      HTTPOperationImpl.class);

    HTTPBindingSerializer httpBindingSer = new HTTPBindingSerializer();

    registerSerializer(Binding.class,
                       HTTPConstants.Q_ELEM_HTTP_BINDING,
                       httpBindingSer);
    registerDeserializer(Binding.class,
                         HTTPConstants.Q_ELEM_HTTP_BINDING,
                         httpBindingSer);
    mapExtensionTypes(Binding.class,
                      HTTPConstants.Q_ELEM_HTTP_BINDING,
                      HTTPBindingImpl.class);

    HTTPUrlEncodedSerializer httpUrlEncodedSer =
      new HTTPUrlEncodedSerializer();

    registerSerializer(BindingInput.class,
                       HTTPConstants.Q_ELEM_HTTP_URL_ENCODED,
                       httpUrlEncodedSer);
    registerDeserializer(BindingInput.class,
                         HTTPConstants.Q_ELEM_HTTP_URL_ENCODED,
                         httpUrlEncodedSer);
    mapExtensionTypes(BindingInput.class,
                      HTTPConstants.Q_ELEM_HTTP_URL_ENCODED,
                      HTTPUrlEncodedImpl.class);

    HTTPUrlReplacementSerializer httpUrlReplacementSer =
      new HTTPUrlReplacementSerializer();

    registerSerializer(BindingInput.class,
                       HTTPConstants.Q_ELEM_HTTP_URL_REPLACEMENT,
                       httpUrlReplacementSer);
    registerDeserializer(BindingInput.class,
                         HTTPConstants.Q_ELEM_HTTP_URL_REPLACEMENT,
                         httpUrlReplacementSer);
    mapExtensionTypes(BindingInput.class,
                      HTTPConstants.Q_ELEM_HTTP_URL_REPLACEMENT,
                      HTTPUrlReplacementImpl.class);

    MIMEContentSerializer mimeContentSer = new MIMEContentSerializer();

    registerSerializer(BindingInput.class,
                       MIMEConstants.Q_ELEM_MIME_CONTENT,
                       mimeContentSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);
    mapExtensionTypes(BindingInput.class,
                      MIMEConstants.Q_ELEM_MIME_CONTENT,
                      MIMEContentImpl.class);
    registerSerializer(BindingOutput.class,
                       MIMEConstants.Q_ELEM_MIME_CONTENT,
                       mimeContentSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);
    mapExtensionTypes(BindingOutput.class,
                      MIMEConstants.Q_ELEM_MIME_CONTENT,
                      MIMEContentImpl.class);
    registerSerializer(MIMEPart.class,
                       MIMEConstants.Q_ELEM_MIME_CONTENT,
                       mimeContentSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);
    mapExtensionTypes(MIMEPart.class,
                      MIMEConstants.Q_ELEM_MIME_CONTENT,
                      MIMEContentImpl.class);

    MIMEMultipartRelatedSerializer mimeMultipartRelatedSer =
      new MIMEMultipartRelatedSerializer();

    registerSerializer(BindingInput.class,
                       MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                       mimeMultipartRelatedSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);
    mapExtensionTypes(BindingInput.class,
                      MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                      MIMEMultipartRelatedImpl.class);
    registerSerializer(BindingOutput.class,
                       MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                       mimeMultipartRelatedSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);
    mapExtensionTypes(BindingOutput.class,
                      MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                      MIMEMultipartRelatedImpl.class);
    registerSerializer(MIMEPart.class,
                       MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                       mimeMultipartRelatedSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);
    mapExtensionTypes(MIMEPart.class,
                      MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                      MIMEMultipartRelatedImpl.class);
    mapExtensionTypes(MIMEMultipartRelated.class,
                      MIMEConstants.Q_ELEM_MIME_PART,
                      MIMEPartImpl.class);

    MIMEMimeXmlSerializer mimeMimeXmlSer = new MIMEMimeXmlSerializer();

    registerSerializer(BindingInput.class,
                       MIMEConstants.Q_ELEM_MIME_MIME_XML,
                       mimeMimeXmlSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
    mapExtensionTypes(BindingInput.class,
                      MIMEConstants.Q_ELEM_MIME_MIME_XML,
                      MIMEMimeXmlImpl.class);
    registerSerializer(BindingOutput.class,
                       MIMEConstants.Q_ELEM_MIME_MIME_XML,
                       mimeMimeXmlSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
    mapExtensionTypes(BindingOutput.class,
                      MIMEConstants.Q_ELEM_MIME_MIME_XML,
                      MIMEMimeXmlImpl.class);
    registerSerializer(MIMEPart.class,
                       MIMEConstants.Q_ELEM_MIME_MIME_XML,
                       mimeMimeXmlSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
    mapExtensionTypes(MIMEPart.class,
                      MIMEConstants.Q_ELEM_MIME_MIME_XML,
                      MIMEMimeXmlImpl.class);
  }
}