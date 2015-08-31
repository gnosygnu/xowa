/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xmls; import gplx.*;
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