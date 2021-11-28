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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:NONE
public class XomwPPDPart_DOM extends XomwPPDPart {	private final Bry_bfr bfr;
	private final Xomw_prepro_accum__dom accum = new Xomw_prepro_accum__dom("");
	public XomwPPDPart_DOM(String output) {super(output);
		bfr = accum.Bfr();
		if (output != String_.Empty) {
			bfr.Add_str_u8(output);
		}
	}
	@Override public Xomw_prepro_accum Accum() {return accum;}
	public Bry_bfr Bfr() {return bfr;}
	public int Len() {return bfr.Len();}
	public byte[] To_bry() {return bfr.To_bry();}
	public String To_str() {return bfr.To_str();}

	@Override public XomwPPDPart Make_new(String val) {
		return new XomwPPDPart_DOM(val);
	}
}
