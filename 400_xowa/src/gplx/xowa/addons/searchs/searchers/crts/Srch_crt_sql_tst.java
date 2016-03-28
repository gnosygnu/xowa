/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.searchers.*;
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
