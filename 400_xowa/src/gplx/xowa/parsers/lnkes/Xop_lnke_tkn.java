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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
public class Xop_lnke_tkn extends Xop_tkn_itm_base {//20111222
	public static final byte Lnke_typ_null = 0, Lnke_typ_brack = 1, Lnke_typ_text = 2, Lnke_typ_brack_dangling = 3;
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_lnke;}
	public boolean Lnke_relative() {return lnke_relative;} public Xop_lnke_tkn Lnke_relative_(boolean v) {lnke_relative = v; return this;} private boolean lnke_relative;
	public byte Lnke_typ() {return lnke_typ;} public Xop_lnke_tkn Lnke_typ_(byte v) {lnke_typ = v; return this;} private byte lnke_typ = Lnke_typ_null;
	public byte[] Lnke_site() {return lnke_site;} public Xop_lnke_tkn Lnke_site_(byte[] v) {lnke_site = v; return this;} private byte[] lnke_site;
	public byte[] Lnke_xwiki_wiki() {return lnke_xwiki_wiki;} private byte[] lnke_xwiki_wiki;
	public byte[] Lnke_xwiki_page() {return lnke_xwiki_page;} private byte[] lnke_xwiki_page;
	public Gfo_qarg_itm[] Lnke_xwiki_qargs() {return lnke_xwiki_qargs;} Gfo_qarg_itm[] lnke_xwiki_qargs;
	public void Lnke_xwiki_(byte[] wiki, byte[] page, Gfo_qarg_itm[] args) {this.lnke_xwiki_wiki = wiki; this.lnke_xwiki_page = page; this.lnke_xwiki_qargs = args;}
	public int Lnke_href_bgn() {return lnke_href_bgn;} private int lnke_href_bgn;
	public int Lnke_href_end() {return lnke_href_end;} private int lnke_href_end;
	public byte[] Protocol() {return protocol;} private byte[] protocol;
	public byte Proto_tid() {return proto_tid;} private byte proto_tid;
	public Xop_lnke_tkn Subs_add_ary(Xop_tkn_itm... ary) {for (Xop_tkn_itm itm : ary) super.Subs_add(itm); return this;}

	public Xop_lnke_tkn(int bgn, int end, byte[] protocol, byte proto_tid, byte lnke_typ, int lnke_href_bgn, int lnke_href_end) {
		this.Tkn_ini_pos(false, bgn, end); this.protocol = protocol; this.proto_tid = proto_tid; this.lnke_typ = lnke_typ; this.lnke_href_bgn = lnke_href_bgn; this.lnke_href_end = lnke_href_end;
	}	Xop_lnke_tkn() {}
}
