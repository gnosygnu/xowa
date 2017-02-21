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
