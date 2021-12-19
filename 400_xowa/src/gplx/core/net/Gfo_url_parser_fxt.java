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
package gplx.core.net;
import gplx.core.net.qargs.Gfo_qarg_itm;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
class Gfo_url_parser_fxt {
	private final Gfo_url_parser parser = new Gfo_url_parser();
	private Gfo_url actl;
	public Gfo_url_parser_fxt Test__protocol_tid(byte v) 		{GfoTstr.EqByte(v, actl.Protocol_tid(), "protocol_tid"); return this;}
	public Gfo_url_parser_fxt Test__protocol_bry(String v) 		{GfoTstr.Eq(v, actl.Protocol_bry(), "protocol_bry"); return this;}
	public Gfo_url_parser_fxt Test__site(String v) 				{GfoTstr.Eq(v, actl.Segs__get_at_1st(), "site"); return this;}
	public Gfo_url_parser_fxt Test__page(String v) 				{GfoTstr.Eq(v, actl.Segs__get_at_nth(), "page"); return this;}
	public Gfo_url_parser_fxt Test__anch(String v) 				{GfoTstr.Eq(v, actl.Anch(), "anch"); return this;}
	public Gfo_url_parser_fxt Test__segs(String... ary) 	{
		GfoTstr.EqLines(StringUtl.ConcatLinesNl(ary), StringUtl.ConcatLinesNl(StringUtl.Ary(actl.Segs())), "segs");
		GfoTstr.Eq(ary.length, actl.Segs().length, "segs_len");
		return this;
	}
	public Gfo_url_parser_fxt Test__qargs(String... ary)	{GfoTstr.EqLines(StringUtl_ToStrByKvAry(ary), Qargs__To_str(actl.Qargs()), "qargs"); return this;}
	public Gfo_url_parser_fxt Exec__parse(String v) {
		this.actl = parser.Parse(BryUtl.NewU8(v), 0, StringUtl.Len(v));
		return this;
	}
	public void Test_Parse_site_fast(String raw, String expd) {
		byte[] raw_bry = BryUtl.NewU8(raw);
		parser.Parse_site_fast(site_data, raw_bry, 0, raw_bry.length);
		String actl = StringUtl.NewU8(raw_bry, site_data.Site_bgn(), site_data.Site_end());
		GfoTstr.EqObj(expd, actl);
	}	private final Gfo_url_site_data site_data = new Gfo_url_site_data();
	private static String Qargs__To_str(Gfo_qarg_itm[] ary) {
		int len = ary.length;
		BryWtr bfr = BryWtr.New();
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm itm = ary[i];
			bfr.Add(itm.Key_bry()).AddByteEq();
			if (itm.Val_bry() != null)
				bfr.Add(itm.Val_bry());
			bfr.AddByteNl();
		}
		return bfr.ToStrAndClear();
	}
	private static String StringUtl_ToStrByKvAry(String... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		int len = ary.length;
		for (int i = 0; i < len; i += 2) {
			sb.Add(ary[i]).AddChar('=');
			String val = i + 1 < len ? ary[i + 1] : null;
			if (val != null) sb.Add(val);
			sb.AddCharNl();
		}
		return sb.ToStrAndClear();
	}
}
