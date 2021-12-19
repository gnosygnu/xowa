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
package gplx.xowa.htmls.core.htmls.tidy;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.*;
import gplx.xowa.htmls.core.htmls.tidy.vnus.*;
import nu.validator.htmlparser.common.XmlViolationPolicy;
import nu.validator.htmlparser.sax.HtmlParser;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;
class Xoh_tidy_wkr__vnu implements Xoh_tidy_wkr {
		private byte[] depurate(BryWtr tidy_bfr, boolean compat) throws SAXException, IOException {
		byte[] input = tidy_bfr.ToBryAndClear();
		InputStream stream = new ByteArrayInputStream(input);
		InputSource source = new InputSource(stream);
		ByteArrayOutputStream sink = new ByteArrayOutputStream();
		ContentHandler serializer;
		serializer = new CompatibilitySerializer(sink);
		HtmlParser parser = new HtmlParser(XmlViolationPolicy.ALLOW);
		parser.setContentHandler(serializer);
		source.setEncoding("UTF-8");
		parser.setProperty("http://xml.org/sax/properties/lexical-handler", serializer);
		parser.parse(source);
		return sink.toByteArray();
	}
		public byte Tid() {return Xoh_tidy_wkr_.Tid_vnu;}
	public void Init_by_app(Xoae_app app) {
	}
	public void Indent_(boolean v) {
					}
	public void Exec_tidy(BryWtr bfr, byte[] page_url) {
				try {
			bfr.Add(depurate(bfr, true));
		} 
		catch (SAXException e) { }
		catch (IOException e) {}
			}
}
