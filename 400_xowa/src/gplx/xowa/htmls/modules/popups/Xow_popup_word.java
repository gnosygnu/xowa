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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.xowa.parsers.*;
public class Xow_popup_word {
	public Xow_popup_word(int tid, int bfr_bgn, int idx, int bgn, int end, Xop_tkn_itm tkn) {this.tid = tid; this.bfr_bgn = bfr_bgn; this.idx = idx; this.bgn = bgn; this.end = end; this.tkn = tkn;}
	public int Tid() {return tid;} private int tid;
	public int Bfr_bgn() {return bfr_bgn;} private int bfr_bgn;
	public int Bfr_end() {return bfr_bgn + this.Len();}
	public int Idx() {return idx;} private int idx;
	public int Bgn() {return bgn;} private int bgn;
	public int End() {return end;} private int end;
	public int Len() {return end - bgn;}
	public Xop_tkn_itm Tkn() {return tkn;} private Xop_tkn_itm tkn;
}
