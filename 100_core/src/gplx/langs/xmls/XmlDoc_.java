/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import gplx.Io_url;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class XmlDoc_ {
	public static XmlDoc parse(String raw) {return new XmlDoc(doc_(raw));}
	static Document doc_(String raw) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder bldr = null;
		try 	{bldr = factory.newDocumentBuilder();}
		catch 	(ParserConfigurationException e) {throw Err_.new_exc(e, "xml", "failed to create newDocumentBuilder");}
		StringReader reader = new StringReader(raw);
		InputSource source = new InputSource(reader);
		Document doc = null;
		try 	{doc = bldr.parse(source);}
		catch 	(SAXException e) 	{throw Err_.new_exc(e, "xml", "failed to parse xml", "raw", raw);} 
		catch 	(IOException e) 	{throw Err_.new_exc(e, "xml", "failed to parse xml", "raw", raw);}
		return doc;		
	}
	public static final String Err_XmlException = "gplx.xmls.XmlException";
}
//#}