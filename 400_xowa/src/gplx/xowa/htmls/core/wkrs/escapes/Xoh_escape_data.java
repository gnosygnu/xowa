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
package gplx.xowa.htmls.core.wkrs.escapes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_escape_data implements Gfh_doc_wkr {
	private final Xoh_hdoc_wkr wkr;		
	public Xoh_escape_data(Xoh_hdoc_wkr wkr, byte hook_byte) {this.wkr = wkr; this.hook = Bry_.New_by_byte(hook_byte);}
	public byte[] Hook() {return hook;} private final byte[] hook;	// NOTE: bry with 1 member which is hook to be escaped; EX: "1" or "2" or "27"
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		int rv = pos + 1;
		wkr.On_escape(this);
		return rv;
	}
}
