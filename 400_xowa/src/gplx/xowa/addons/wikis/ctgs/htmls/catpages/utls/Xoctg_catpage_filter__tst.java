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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.apps.urls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
public class Xoctg_catpage_filter__tst {
	private final    Xoctg_catpage_filter__fxt fxt = new Xoctg_catpage_filter__fxt();
	private Xoctg_catpage_ctg ctg;
	@Before public void init() {
		this.ctg = fxt.Make__ctg(25, 25, 25);
	}
	@Test   public void Initial() {
		fxt.Exec__filter(5, "A", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0,  5);
	}
	@Test   public void Fwd__page__05() {
		fxt.Exec__filter(5, "A?pagefrom=05", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page,  5, 10);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0,  5);
	}
	@Test   public void Fwd__page__10() {
		fxt.Exec__filter(5, "A?pagefrom=10", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page, 10, 15);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0,  5);
	}
	@Test   public void Fwd__page__23() {
		fxt.Exec__filter(5, "A?pagefrom=23", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page, 23, 25);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0,  5);
	}
	@Test   public void Fwd__full__06() {
		fxt.Exec__filter(5, "A?from=06", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page,  6, 11);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  6, 11);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  6, 11);
	}
	@Test   public void Bwd__page__20() {
		fxt.Exec__filter(5, "A?pageuntil=20", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page, 15, 20);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0,  5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0,  5);
	}
	@Test   public void Bwd__page__2() {
		fxt.Exec__filter(5, "A?pageuntil=01", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page,  0, 1);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  0, 5);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  0, 5);
	}
	@Test   public void Bwd__full__11() {
		fxt.Exec__filter(5, "A?until=11", ctg);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__page,  6, 11);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__subc,  6, 11);
		fxt.Test__cat_grp(ctg, Xoa_ctg_mgr.Tid__file,  6, 11);
	}
}
class Xoctg_catpage_filter__fxt {
	private Xow_url_parser url_parser;
	public Xoctg_catpage_filter__fxt() {
		Xoa_app app = Xoa_app_fxt.Make__app__edit();
		this.url_parser = app.User().Wikii().Utl__url_parser();
	}
	public Xoctg_catpage_ctg Make__ctg(int subc, int page, int file) {
		Xoctg_catpage_ctg ctg = new Xoctg_catpage_ctg(Bry_.new_a7("A"));
		Make__ctg_grp(ctg, Xoa_ctg_mgr.Tid__subc, subc);
		Make__ctg_grp(ctg, Xoa_ctg_mgr.Tid__page, page);
		Make__ctg_grp(ctg, Xoa_ctg_mgr.Tid__file, file);
		return ctg;
	}
	private void Make__ctg_grp(Xoctg_catpage_ctg ctg, byte tid, int count) {
		Xoctg_catpage_grp grp = ctg.Grp_by_tid(tid);
		for (int i = 0; i < count; ++i) {
			Xoctg_catpage_itm itm = new Xoctg_catpage_itm(i * tid, Xoa_ttl.Null, Bry_.new_a7(Int_.To_str_pad_bgn_zero(i, 2)));
			grp.Itms__add(itm);
		}
		grp.Itms__make();
	}
	public void Exec__filter(int limit, String cat_url_str, Xoctg_catpage_ctg ctg) {
		Xoctg_catpage_url cat_url = Xoctg_catpage_url_parser.Parse(url_parser.Parse(Bry_.new_a7(cat_url_str)));
		Xoctg_catpage_filter.Filter(limit, cat_url, ctg);
	}
	public void Test__cat_grp(Xoctg_catpage_ctg ctg, byte tid, int expd_bgn, int expd_end) {
		Xoctg_catpage_grp grp = ctg.Grp_by_tid(tid);
		Gftest.Eq__int(expd_bgn, grp.Bgn(), "bgn failed; tid={0}", tid);
		Gftest.Eq__int(expd_end, grp.End(), "end failed; tid={0}", tid);
	}
}
