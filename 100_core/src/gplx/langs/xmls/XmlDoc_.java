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
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class XmlDoc_ {
	public static XmlNdeList Select_tags(XmlNde cur, String tag) {
		XmlNdeList_cls_list rv = new XmlNdeList_cls_list(4); // NOTE: pass in an initial amount; do not pass 0
		Select_tags(rv, cur, tag);
		return rv;
	}
	private static void Select_tags(XmlNdeList_cls_list rv, XmlNde cur, String tag) {
		if (String_.Eq(cur.Name(), tag)) {
			rv.Add(cur);
		}
		XmlNdeList sub_ndes = cur.SubNdes();
		int sub_ndes_len = sub_ndes.Count();
		for (int i = 0; i < sub_ndes_len; i++) {
			XmlNde sub_nde = sub_ndes.Get_at(i);
			Select_tags(rv, sub_nde, tag);
		}
	}
		public static XmlDoc parse(String raw) {return new XmlDoc(doc_(raw));}
	static Document doc_(String raw) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder bldr = null;
		try {
			// NOTE: disable DTD validation else errors for "ldmlSupplemental.dtd" in plurals.xml; DATE:2020-01-01
			// REF:https://stackoverflow.com/questions/24744175/non-validating-documentbuilder-trying-to-read-dtd-file
			// REF:https://stackoverflow.com/questions/6204827/xml-parsing-too-slow
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			factory.setFeature("http://xml.org/sax/features/namespaces", false);
			factory.setFeature("http://xml.org/sax/features/validation", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			bldr = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			throw Err_.new_exc(e, "xml", "failed to create newDocumentBuilder");
		}
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
