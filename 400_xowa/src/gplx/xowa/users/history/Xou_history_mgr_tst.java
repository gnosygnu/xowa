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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*;
public class Xou_history_mgr_tst {
	private Xou_history_mgr_fxt fxt = new Xou_history_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Archive() {
		Tfds.Now_enabled_y_();	// NOTE: each DateTime_.Now() advances clock by 1 min; adding a new DateAdp_.Now() anywhere will throw off times on this test; DATE:2014-04-01
		fxt.Invk(Xou_history_mgr.Invk_current_itms_max_, 4).Invk(Xou_history_mgr.Invk_current_itms_reset_, 2);
		fxt.Add_many("A", "B", "C", "D", "E");
		fxt.Save();
		fxt.List_tst("E", "D");
		fxt.Fil_tst("mem/xowa/user/test_user/app/data/history/20010101_001000.000.csv", String_.Concat_lines_nl
			(	"20010101 000500.000|20010101 000500.000|1|en.wikipedia.org|C"
			,	"20010101 000300.000|20010101 000300.000|1|en.wikipedia.org|B"
			,	"20010101 000100.000|20010101 000100.000|1|en.wikipedia.org|A"
			));
	}
	@Test   public void Normalize() {
		fxt.Clear();
		fxt.Add_many("Category:A_B", "Category:A B", "Category:a B", "Category:_A B_");
		fxt.List_tst("Category:A_B");
	}
	@Test   public void Args() {
		fxt.Clear();
		fxt.Add_one("Special:AllPages", "?from=A");
		fxt.List_tst("Special:AllPages?from=A");
	}
}
class Xou_history_mgr_fxt {
	Xoae_app app; Xowe_wiki wiki;
	Xou_history_mgr under;
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			under = app.Usere().History_mgr();
		}
		Io_mgr.Instance.DeleteDirDeep(Io_url_.new_dir_("mem/xowa/user/test_user/app/data/history/"));
		under.Clear();
	}
	public Xou_history_mgr_fxt Add_many(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			Add_one(itm, null);
		}
		return this;
	}
	public Xou_history_mgr_fxt Add_one(String ttl_str, String arg_str) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		Xoa_ttl ttl = Xoa_ttl.parse(wiki, ttl_bry);
		Xoae_page page = Xoae_page.New_test(wiki, ttl);
		page.Revision_data().Modified_on_(DateAdp_.Now());
		byte[] url_bry = ttl_bry;
		if (arg_str != null) url_bry = Bry_.Add(url_bry, Bry_.new_u8(arg_str));
		Xoa_url url = wiki.Utl__url_parser().Parse(url_bry);
		page.Url_(url);  // set url b/c history_mgr.Add uses url
		under.Add(page);
		return this;
	}
	public Xou_history_mgr_fxt List_tst(String... expd) {
		int actl_len = under.Len();
		String[] actl = new String[actl_len];
		for (int i = 0; i < actl_len; i++) {
			Xou_history_itm itm = under.Get_at(i);
			actl[i] = String_.new_u8(itm.Page());
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	public Xou_history_mgr_fxt Invk(String key, Object v) {GfoInvkAble_.InvkCmd_val(under, key, v); return this;}
	public Xou_history_mgr_fxt Save() {under.Save(app); return this;}
	public Xou_history_mgr_fxt Fil_tst(String expd_url, String expd) {
		String actl = Io_mgr.Instance.LoadFilStr(expd_url);
		Tfds.Eq_str_lines(expd, actl);
		return this;
	}
}
