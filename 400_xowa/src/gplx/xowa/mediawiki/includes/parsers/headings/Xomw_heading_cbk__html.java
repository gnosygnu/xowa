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
package gplx.xowa.mediawiki.includes.parsers.headings;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_heading_cbk__html implements Xomw_heading_cbk {		
	public BryWtr Bfr() {return bfr;} private BryWtr bfr;
	public Xomw_heading_cbk__html Bfr_(BryWtr bfr) {
		this.bfr = bfr;
		return this;
	}
	public void On_hdr_seen(XomwParserCtx pctx, Xomw_heading_wkr wkr) {
		// add from txt_bgn to hdr_bgn; EX: "abc\n==A==\n"; "\n==" seen -> add "abc"
		byte[] src = wkr.Src();
		int hdr_bgn = wkr.Hdr_bgn(), txt_bgn = wkr.Txt_bgn();
		if (hdr_bgn > txt_bgn)
			bfr.AddMid(src, txt_bgn, hdr_bgn);

		// add "\n" unless BOS
		if (hdr_bgn != XomwParserCtx.Pos__bos) bfr.AddByteNl();

		// add <h2>...</h2>
		int hdr_num = wkr.Hdr_num();
		bfr.Add(Tag__lhs).AddIntDigits(1, hdr_num).Add(AsciiByte.AngleEndBry);	// <h2>
		bfr.AddMid(wkr.Src(), wkr.Hdr_lhs_end(), wkr.Hdr_rhs_bgn());
		bfr.Add(Tag__rhs).AddIntDigits(1, hdr_num).Add(AsciiByte.AngleEndBry);	// </h2>
	}
	public void On_src_done(XomwParserCtx pctx, Xomw_heading_wkr wkr) {
		// add from txt_bgn to EOS;
		byte[] src = wkr.Src();
		int txt_bgn = wkr.Txt_bgn(), src_end = wkr.Src_end();
		if (txt_bgn != src_end)	// PERF: don't call Add_mid() if hdr is at end of EOS
			bfr.AddMid(src, txt_bgn, src_end);
	}
	private static final byte[]
	  Tag__lhs = BryUtl.NewA7("<h")
	, Tag__rhs = BryUtl.NewA7("</h")
	;
}
