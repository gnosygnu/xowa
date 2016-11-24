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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import org.junit.*;
public class Fsd_thm_tbl_tst {
	@Before public void init() {fxt.Clear();} private Fsd_thm_tbl_fxt fxt = new Fsd_thm_tbl_fxt();
	@Test   public void Basic() {
		fxt.Init_list(fxt.Make(100), fxt.Make(200), fxt.Make(400));
		fxt.Test_match_nearest_itm(fxt.Make(400), fxt.Make(400));
		fxt.Test_match_nearest_itm(fxt.Make(200), fxt.Make(200));
		fxt.Test_match_nearest_itm(fxt.Make(100), fxt.Make(100));
		fxt.Test_match_nearest_itm(fxt.Make(350), fxt.Make(400));
		fxt.Test_match_nearest_itm(fxt.Make(150), fxt.Make(200));
		fxt.Test_match_nearest_itm(fxt.Make(999), fxt.Make(400));
	}
	@Test   public void Empty() {
		fxt.Init_list();	// no items
		fxt.Test_match_nearest_itm(fxt.Make(100), Fsd_thm_itm.Null);
	}
}
class Fsd_thm_tbl_fxt {
	private final    List_adp list = List_adp_.New();
	public void Clear() {list.Clear();}
	public Fsd_thm_itm Make(int w) {
		double time = gplx.xowa.files.Xof_lnki_time.Null;
		int page = gplx.xowa.files.Xof_lnki_page.Null;
		Fsd_thm_itm rv = Fsd_thm_itm.new_();
		rv.Init_by_req(w, time, page);
		return rv;
	}
	public void Init_list(Fsd_thm_itm... ary) {list.Add_many((Object[])ary);}
	public void Test_match_nearest_itm(Fsd_thm_itm req, Fsd_thm_itm expd) {
		Fsd_thm_tbl.Match_nearest(list, req, Bool_.Y);
		if (expd == Fsd_thm_itm.Null) {
			Tfds.Eq(req.Req_w(), 0);
		}
		else {
			Tfds.Eq(expd.W(), req.W());
			Tfds.Eq(expd.Time(), req.Time());
			Tfds.Eq(expd.Page(), req.Page());
		}
	}
}
