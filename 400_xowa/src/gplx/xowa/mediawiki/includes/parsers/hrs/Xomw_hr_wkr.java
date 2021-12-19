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
package gplx.xowa.mediawiki.includes.parsers.hrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.mediawiki.includes.parsers.XomwParserBfr;
import gplx.xowa.mediawiki.includes.parsers.XomwParserCtx;
public class Xomw_hr_wkr {// THREAD.UNSAFE: caching for repeated calls
	private BryWtr bfr;
	public void replaceHrs(XomwParserCtx pctx, XomwParserBfr pbfr) {	// REF.MW: text = preg_replace('/(^|\n)-----*/', '\\1<hr />', text);
		// XO.PBFR
		BryWtr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bry();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		this.bfr = pbfr.Trg();

		boolean dirty = false;

		// do separate check for "-----" at start of String;
		int cur = 0;
		if (BryLni.Eq(src, 0, Len__wtxt__hr__bos, Bry__wtxt__hr__bos)) {
			cur = Replace_hr(BoolUtl.N, src, src_bgn, src_end, 0, Len__wtxt__hr__bos);
			dirty = true;
		}

		// loop
		while (true) {
			// find next "\n-----"
			int find_bgn = BryFind.FindFwd(src, Bry__wtxt__hr__mid, cur, src_end);

			// nothing found; exit
			if (find_bgn == BryFind.NotFound) {
				if (dirty) {
					bfr.AddMid(src, cur, src_end);
				}
				break;
			}
			
			// something found
			cur = Replace_hr(BoolUtl.Y, src, cur, src_end, find_bgn, Len__wtxt__hr__mid);
			dirty = true;
		}
		if (dirty)
			pbfr.Switch();
	}
	private int Replace_hr(boolean mid, byte[] src, int cur, int src_end, int find_bgn, int tkn_len) {
		// something found; add to bfr
		if (mid) {
			bfr.AddMid(src, cur, find_bgn);	// add everything before "\n-----"
			bfr.AddByteNl();
		}
		bfr.Add(Bry__html__hr);

		// set dirty / cur and continue
		cur = find_bgn + tkn_len;
		cur = BryFind.FindFwdWhile(src, cur, src_end, AsciiByte.Dash);	// gobble up trailing "-"; the "*" in "-----*" from the regex above
		return cur;
	}
	private static final byte[]
	  Bry__wtxt__hr__mid = BryUtl.NewA7("\n-----")
	, Bry__wtxt__hr__bos = BryUtl.NewA7("-----")
	, Bry__html__hr      = BryUtl.NewA7("<hr />")
	;
	private static final int
	  Len__wtxt__hr__mid = Bry__wtxt__hr__mid.length
	, Len__wtxt__hr__bos = Bry__wtxt__hr__bos.length
	;
}
