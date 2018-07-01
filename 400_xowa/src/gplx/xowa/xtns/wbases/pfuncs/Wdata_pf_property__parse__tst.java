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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wdata_pf_property__parse__tst {
	@Before public void init() {fxt.Init();} private final    Wdata_pf_property_data_fxt fxt = new Wdata_pf_property_data_fxt();
	@Test  public void Q()				{fxt.Init().Run__parse("{{#property:p1|q=q2}}").Chk_q("q2");}
	@Test  public void Of()				{fxt.Init().Run__parse("{{#property:p1|of=Earth}}").Chk_of("Earth");}
	@Test  public void From()			{fxt.Init().Run__parse("{{#property:p1|from=p2}}").Chk_from("p2");}
}
class Wdata_pf_property_data_fxt {
	private Xoae_app app; private Wdata_wiki_mgr wdata_mgr; private Xop_fxt parser_fxt;
	public Wdata_pf_property_data_fxt Init() {
		if (app == null) {
			parser_fxt = new Xop_fxt();
			app = parser_fxt.App();
			wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		}
		Io_mgr.Instance.InitEngine_mem();
		wdata_mgr.Clear();
		parser_fxt.Reset();
		actl = null;
		return this;
	}
	private Wdata_pf_property_data actl;
	public Wdata_pf_property_data_fxt Run__parse(String raw) {			
		byte[] raw_bry = Bry_.new_u8(raw);
		Xowe_wiki wiki = parser_fxt.Wiki(); Xop_ctx ctx = wiki.Parser_mgr().Ctx();
		Xop_tkn_mkr tkn_mkr = app.Parser_mgr().Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		wiki.Parser_mgr().Main().Expand_tmpl(root, ctx, tkn_mkr, raw_bry);
		Xot_invk tkn = (Xot_invk)root.Subs_get(0);
		this.actl = Wdata_pf_property_data.Parse(ctx, raw_bry, Xot_invk_mock.Null, tkn);
		return this;
	}
	public Wdata_pf_property_data_fxt Chk_q(String v)		{Tfds.Eq_bry(v, actl.Q); return this;}
	public Wdata_pf_property_data_fxt Chk_of(String v)		{Tfds.Eq_bry(v, actl.Of); return this;}
	public Wdata_pf_property_data_fxt Chk_from(String v)	{Tfds.Eq_bry(v, actl.From); return this;}
}
