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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.strings.*;
public class Xot_defn_trace_brief_tst {
	Xot_defn_trace_fxt fxt = new Xot_defn_trace_fxt();
	@Before public void init() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("leaf_a", "{{{1}}}");
		fxt.Init_defn_add("leaf_b", "{{{1}}}");
		fxt.Ctx().Defn_trace_(new Xot_defn_trace_brief());
	}
	@Test  public void Basic_a_1()				{fxt.tst_("{{leaf_a}}"						, "0001 leaf_a");}
	@Test  public void Basic_a_2()				{fxt.tst_("{{leaf_a}} {{leaf_a}}"			, "0002 leaf_a");}
	@Test  public void Basic_a_b()				{fxt.tst_("{{leaf_a}} {{leaf_b}}"			, "0001 leaf_a", "0001 leaf_b");}
}
class Xot_defn_trace_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	public Xop_ctx Ctx() {return fxt.Ctx();}
	public void Init_defn_clear() {fxt.Init_defn_clear();}
	public void Init_defn_add(String name, String raw) {fxt.Init_defn_add(name, raw);}
	public void tst_(String raw, String... expd_ary) {
		Xop_ctx ctx = fxt.Ctx();
		ctx.Defn_trace().Clear();
		byte[] src = Bry_.new_u8(raw);
		ctx.Page().Ttl_(Xoa_ttl.Parse(fxt.Wiki(), Bry_.new_a7("test")));
		Xop_root_tkn root = ctx.Tkn_mkr().Root(src);
		fxt.Parser().Parse_page_all_clear(root, ctx, ctx.Tkn_mkr(), src);
		ctx.Defn_trace().Print(src, tmp);
		String[] actl_ary = String_.Split(tmp.To_str_and_clear(), (char)Byte_ascii.Nl);
		Tfds.Eq_ary(expd_ary, actl_ary);
	}	private Bry_bfr tmp = Bry_bfr_.New();
	String[] To_str(Xot_defn_trace_itm_brief[] ary) {
		String[] rv = new String[ary.length];
		for (int i = 0; i < rv.length; i++) {
			Xot_defn_trace_itm_brief itm = ary[i];
			sb.Add(String_.new_u8(itm.Name())).Add("|").Add(itm.Count());
			rv[i] = sb.To_str_and_clear();
		}
		return rv;
	}
	String_bldr sb = String_bldr_.new_();
}
