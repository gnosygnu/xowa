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
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
public class XmlNde {
		public XmlAtrList Atrs() {return new XmlAtrList(xnde.getAttributes());}
	public XmlNdeList SubNdes() {return new XmlNdeList_cls_xml(xnde.getChildNodes());}
	public String Name() {return xnde.getNodeName();}
	public String Xml_outer() {
	    Transformer transformer = transformer_();
	    StringWriter writer = new StringWriter();
	    try {transformer.transform(new DOMSource(xnde), new StreamResult(writer));}
	    catch (TransformerException e) {throw Exc_.new_exc(e, "xml", "failed to get xml string");}
	    return writer.toString(); 
	}
	public String Text_inner() {return xnde.getTextContent();}
	public boolean NdeType_element() {return xnde.getNodeType() == Node.ELEMENT_NODE;}
	public boolean NdeType_textOrEntityReference() {return xnde.getNodeType() == Node.TEXT_NODE || xnde.getNodeType() == Node.ENTITY_REFERENCE_NODE;}
	@gplx.Internal protected XmlNde(Node xnde) {this.xnde = xnde;} Node xnde;
	static Transformer transformer_() {
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {transformer = transformerfactory.newTransformer();}
		catch (TransformerConfigurationException e) {throw Exc_.new_exc(e, "xml", "failed to get create transformer");}
	    transformer.setOutputProperty("omit-xml-declaration", "yes");
		return transformer;
	}
	}
