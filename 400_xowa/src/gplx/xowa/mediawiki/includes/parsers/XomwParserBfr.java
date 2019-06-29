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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class XomwParserBfr {	// manages 2 bfrs to eliminate multiple calls to new memory allocations ("return bfr.To_bry_and_clear()")
	private final    Bry_bfr bfr_1 = Bry_bfr_.New(), bfr_2 = Bry_bfr_.New();
	private Bry_bfr src, trg;
	public XomwParserBfr() {
		this.src = bfr_1;
		this.trg = bfr_2;
	}		
	public Bry_bfr Src() {return src;}
	public Bry_bfr Trg() {return trg;}
	public Bry_bfr Rslt() {return src;}
	public XomwParserBfr Init(byte[] text) {
		// resize each bfr once by guessing that html_len = text_len * 2
		int text_len = text.length;
		int html_len = text_len * 2;
		src.Resize(html_len);
		trg.Resize(html_len);

		// clear and add
		src.Clear();
		trg.Clear();
		src.Add(text);
		return this;
	}
	public void Switch() {
		Bry_bfr tmp = src;
		this.src = trg;
		this.trg = tmp;
		trg.Clear();
	}
}
