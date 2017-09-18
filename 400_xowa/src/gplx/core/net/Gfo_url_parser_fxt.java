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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.net.qargs.*;
class Gfo_url_parser_fxt {
	private final    Gfo_url_parser parser = new Gfo_url_parser();
	private Gfo_url actl;
	public Gfo_url_parser_fxt Test__protocol_tid(byte v) 		{Tfds.Eq_byte(v, actl.Protocol_tid(), "protocol_tid"); return this;}
	public Gfo_url_parser_fxt Test__protocol_bry(String v) 		{Tfds.Eq_str(v, actl.Protocol_bry(), "protocol_bry"); return this;}
	public Gfo_url_parser_fxt Test__site(String v) 				{Tfds.Eq_str(v, actl.Segs__get_at_1st(), "site"); return this;}
	public Gfo_url_parser_fxt Test__page(String v) 				{Tfds.Eq_str(v, actl.Segs__get_at_nth(), "page"); return this;}
	public Gfo_url_parser_fxt Test__anch(String v) 				{Tfds.Eq_str(v, actl.Anch(), "anch"); return this;}
	public Gfo_url_parser_fxt Test__segs(String... ary) 	{
		Tfds.Eq_str_lines(String_.Concat_lines_nl(ary), String_.Concat_lines_nl(String_.Ary(actl.Segs())), "segs");
		Tfds.Eq_int(ary.length, actl.Segs().length, "segs_len");
		return this;
	}
	public Gfo_url_parser_fxt Test__qargs(String... ary)	{Tfds.Eq_str_lines(String_.To_str__as_kv_ary(ary), Qargs__To_str(actl.Qargs()), "qargs"); return this;}
	public Gfo_url_parser_fxt Exec__parse(String v) {
		this.actl = parser.Parse(Bry_.new_u8(v), 0, String_.Len(v)); 
		return this;
	}
	public void Test_Parse_site_fast(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		parser.Parse_site_fast(site_data, raw_bry, 0, raw_bry.length);
		String actl = String_.new_u8(raw_bry, site_data.Site_bgn(), site_data.Site_end());
		Tfds.Eq(expd, actl);
	}	private final    Gfo_url_site_data site_data = new Gfo_url_site_data();
	private static String Qargs__To_str(Gfo_qarg_itm[] ary) {
		int len = ary.length;
		Bry_bfr bfr = Bry_bfr_.New();
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm itm = ary[i];
			bfr.Add(itm.Key_bry()).Add_byte_eq();
			if (itm.Val_bry() != null)
				bfr.Add(itm.Val_bry());
			bfr.Add_byte_nl();
		}
		return bfr.To_str_and_clear();
	}
}
