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
package gplx.xowa.hdumps.loads; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*; import gplx.xowa.files.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.saves.*;
public class Hdump_load_mgr_tst {
	@Before public void init() {fxt.Clear();} private Hdump_load_mgr_fxt fxt = new Hdump_load_mgr_fxt();
	@Test  public void Body() {
		fxt.Init_row_body("<body/>");
		fxt.Expd_body("<body/>");
		fxt.Test_load(0);
	}
	@Test  public void Img() {
		fxt.Init_row_img
		( fxt.Make_img(0, 220, 110, "A.png", "commons.wikimedia.org/thumb/7/0/A.png/220.png")
		, fxt.Make_img(1, 200, 100, "B.png", "commons.wikimedia.org/thumb/7/0/B.png/200.png")
		);
		fxt.Expd_img(0, 220, 110, "A.png", "commons.wikimedia.org/thumb/7/0/A.png/220.png");
		fxt.Expd_img(1, 200, 100, "B.png", "commons.wikimedia.org/thumb/7/0/B.png/200.png");
		fxt.Test_load(0);
	}
}
class Hdump_load_mgr_fxt {
	private Hdump_load_mgr load_mgr;
	private Hdump_page_itm page = new Hdump_page_itm();
	private ListAdp init_rows = ListAdp_.new_();
	private String expd_body;
	private ListAdp expd_imgs = ListAdp_.new_();
	private int page_id = 0; private Xoa_url page_url;
	public void Clear() {
		load_mgr = new Hdump_load_mgr(null);
		load_mgr.Zip_tid_(gplx.ios.Io_stream_.Tid_file);
		init_rows.Clear();
		expd_body = null;
		expd_imgs.Clear();
		page_url = Xoa_url.new_(Bry_.new_ascii_("enwiki"), Bry_.new_ascii_("Page_1"));
	}
	public Xof_xfer_itm Make_img(int uid, int img_w, int img_h, String lnki_ttl, String img_src_rel) {return new Xof_xfer_itm().Init_for_test__hdump(uid, img_w, img_h, Bry_.new_utf8_(lnki_ttl), Bry_.new_utf8_(img_src_rel));}
	public void Init_row_img(Xof_xfer_itm... itms) {
		ListAdp tmp_list = ListAdp_.new_();
		Bry_bfr bfr = Bry_bfr.new_(255);
		tmp_list.AddMany((Object[])itms);
		byte[] imgs_bry = Hdump_save_mgr.Write_imgs(bfr, tmp_list);
		init_rows.Add(new Hdump_text_row(0, Hdump_text_row_tid.Tid_img, imgs_bry));
	}
	public Hdump_load_mgr_fxt Init_row_body(String data)	{init_rows.Add(new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_body, Bry_.new_utf8_(data))); return this;}
	public Hdump_load_mgr_fxt Init_row_img (String data)	{init_rows.Add(new Hdump_text_row(page_id, Hdump_text_row_tid.Tid_img , Bry_.new_utf8_(data))); return this;}
	public Hdump_load_mgr_fxt Expd_body(String v) {this.expd_body = v; return this;}
	public Hdump_load_mgr_fxt Expd_img(int idx, int w, int h, String ttl, String src) {expd_imgs.Add(new Hdump_img_itm(idx, w, h, Bry_.new_utf8_(ttl), Bry_.new_utf8_(src))); return this;}
	public Hdump_load_mgr_fxt Test_load(int page_id) {
		load_mgr.Load_itm(page, page_id, page_url, init_rows);
		if (expd_body != null)			Tfds.Eq(expd_body, String_.new_utf8_(page.Page_body()));
		if (expd_imgs.Count() != 0)		Tfds.Eq_ary_str((Hdump_img_itm[])expd_imgs.XtoAryAndClear(Hdump_img_itm.class), page.Img_itms());
		return this;
	}
}
