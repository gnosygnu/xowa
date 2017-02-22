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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import org.junit.*;
public class Srch_crt_sql_tst {
	private final    Srch_crt_sql_fxt fxt = new Srch_crt_sql_fxt();
	@Test   public void Eq()				{fxt.Exec__new_or_null("a").Test__tid(Srch_crt_sql.Tid__eq).Test__eq("a");}
	@Test   public void Rng()				{fxt.Exec__new_or_null("a*").Test__tid(Srch_crt_sql.Tid__rng).Test__rng_bgn("a").Test__rng_end("b").Test__pattern("a*");}
	@Test   public void Like()				{fxt.Exec__new_or_null("a*b").Test__tid(Srch_crt_sql.Tid__like).Test__like("a%b").Test__pattern("a*b");}
	@Test   public void Like__escape()		{fxt.Exec__new_or_null("a|\\*b").Test__tid(Srch_crt_sql.Tid__like).Test__like("a|\\%b").Test__pattern("a\\*b");}	// "a\*b"
	@Test   public void Quote()				{fxt.Exec__new_or_null("\"a b\"").Test__tid(Srch_crt_sql.Tid__eq).Test__eq("\"a b\"");}
}
class Srch_crt_sql_fxt {
	private Srch_crt_sql actl;
	public Srch_crt_sql_fxt Exec__new_or_null(String src_str) {
		this.actl = Srch_crt_sql.New_or_null(Bry_.new_u8(src_str), Srch_search_addon.Wildcard__star);
		return this;
	}
	public Srch_crt_sql_fxt Test__tid(int expd)			{Tfds.Eq(expd, actl.Tid); return this;}
	public Srch_crt_sql_fxt Test__eq(String expd)		{Tfds.Eq(expd, actl.Eq); return this;}
	public Srch_crt_sql_fxt Test__rng_bgn(String expd)	{Tfds.Eq(expd, actl.Rng_bgn); return this;}
	public Srch_crt_sql_fxt Test__rng_end(String expd)	{Tfds.Eq(expd, actl.Rng_end); return this;}
	public Srch_crt_sql_fxt Test__like(String expd)		{Tfds.Eq(expd, actl.Like); return this;}
	public Srch_crt_sql_fxt Test__pattern(String expd)	{Tfds.Eq(expd, String_.new_u8(actl.Pattern.Raw()), "pattern"); return this;}
}
