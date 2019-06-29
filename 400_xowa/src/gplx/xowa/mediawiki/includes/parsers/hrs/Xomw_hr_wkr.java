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
package gplx.xowa.mediawiki.includes.parsers.hrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_hr_wkr {// THREAD.UNSAFE: caching for repeated calls
	private Bry_bfr bfr;
	public void replaceHrs(XomwParserCtx pctx, XomwParserBfr pbfr) {	// REF.MW: text = preg_replace('/(^|\n)-----*/', '\\1<hr />', text);
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		this.bfr = pbfr.Trg();

		boolean dirty = false;

		// do separate check for "-----" at start of String;
		int cur = 0;
		if (Bry_.Eq(src, 0, Len__wtxt__hr__bos, Bry__wtxt__hr__bos)) {
			cur = Replace_hr(Bool_.N, src, src_bgn, src_end, 0, Len__wtxt__hr__bos);
			dirty = true;
		}

		// loop
		while (true) {
			// find next "\n-----"
			int find_bgn = Bry_find_.Find_fwd(src, Bry__wtxt__hr__mid, cur, src_end);

			// nothing found; exit
			if (find_bgn == Bry_find_.Not_found) {
				if (dirty) {
					bfr.Add_mid(src, cur, src_end);
				}
				break;
			}
			
			// something found
			cur = Replace_hr(Bool_.Y, src, cur, src_end, find_bgn, Len__wtxt__hr__mid);
			dirty = true;
		}
		if (dirty)
			pbfr.Switch();
	}
	private int Replace_hr(boolean mid, byte[] src, int cur, int src_end, int find_bgn, int tkn_len) {
		// something found; add to bfr
		if (mid) {
			bfr.Add_mid(src, cur, find_bgn);	// add everything before "\n-----"
			bfr.Add_byte_nl();
		}
		bfr.Add(Bry__html__hr);

		// set dirty / cur and continue
		cur = find_bgn + tkn_len;
		cur = Bry_find_.Find_fwd_while(src, cur, src_end, Byte_ascii.Dash);	// gobble up trailing "-"; the "*" in "-----*" from the regex above
		return cur;
	}
	private static final    byte[] 
	  Bry__wtxt__hr__mid = Bry_.new_a7("\n-----")
	, Bry__wtxt__hr__bos = Bry_.new_a7("-----")
	, Bry__html__hr      = Bry_.new_a7("<hr />")
	;
	private static final    int 
	  Len__wtxt__hr__mid = Bry__wtxt__hr__mid.length
	, Len__wtxt__hr__bos = Bry__wtxt__hr__bos.length
	;
}
