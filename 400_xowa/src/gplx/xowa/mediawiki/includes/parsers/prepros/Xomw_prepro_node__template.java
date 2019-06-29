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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_prepro_node__template extends Xomw_prepro_node__base {
	public Xomw_prepro_node__template(byte[] title, Xomw_prepro_node__part[] parts, int line_start) {
		this.title = title; this.parts = parts; this.line_start = line_start;
	}
	public byte[] Title() {return title;} private final    byte[] title;
	public Xomw_prepro_node__part[] Parts() {return parts;} private final    Xomw_prepro_node__part[] parts;
	public int Line_start() {return line_start;} private final    int line_start;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<template");
		if (line_start > 0) bfr.Add_str_a7(" lineStart=\"").Add_int_variable(line_start).Add_byte_quote();
		bfr.Add_byte(Byte_ascii.Angle_end);
		bfr.Add_str_a7("<title>").Add(title);
		bfr.Add_str_a7("</title>");
		for (Xomw_prepro_node__part part : parts)
			part.To_xml(bfr);
		bfr.Add_str_a7("</template>");
	}
}
