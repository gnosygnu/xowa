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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import org.junit.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.files.imgs.*;
import gplx.fsdb.meta.*;
public class Fs_root_dir_tst {
	@Before public void init() {fxt.Reset();} private Fs_root_dir_fxt fxt = new Fs_root_dir_fxt();
	@Test   public void Basic() {
		fxt.Init_fs("mem/dir/A.png", 200, 100);
		fxt.Test_get("A.png", fxt.itm_().Url_("mem/dir/A.png").Size_(200, 100));
		fxt.Test_db("A.png", fxt.itm_().Init(1, "mem/dir/A.png", 200, 100));
	}
	@Test   public void Recurse() {
		fxt.Init_fs("mem/dir/sub1/A1.png", 200, 100);
		fxt.Test_get("A1.png", fxt.itm_().Url_("mem/dir/sub1/A1.png").Size_(200, 100));
	}
	@Test  public void Xto_fil_bry() {
		fxt.Test_xto_fil_bry("/dir/A.png"		, "A.png");		// basic
		fxt.Test_xto_fil_bry("/dir/A b.png"		, "A_b.png");	// lower
		fxt.Test_xto_fil_bry("/dir/a.png"		, "A.png");		// title
	}
}
class Fs_root_dir_fxt {
	private Fs_root_dir root_dir = new Fs_root_dir();
	private Orig_fil_tbl orig_fil_tbl;
	private Io_url url;
	public void Reset() {
		Db_conn_bldr.I.Reg_default_mem();
		Io_mgr.I.InitEngine_mem();
		url = Io_url_.mem_dir_("mem/dir/");
		root_dir = new Fs_root_dir();
		orig_fil_tbl = new Orig_fil_tbl();
		Xof_img_wkr_query_img_size img_size_wkr = new Xof_img_wkr_query_img_size_test();
		root_dir.Init(url, orig_fil_tbl, Gfo_usr_dlg_.Noop, img_size_wkr);
	}
	public Orig_fil_mok itm_() {return new Orig_fil_mok();}
	public void Init_fs(String url, int w, int h) {Save_img(url, w, h);}
	public void Test_get(String name, Orig_fil_mok expd) {
		Orig_fil_itm actl = root_dir.Get_by_ttl(Bry_.new_u8(name));
		expd.Test(actl);
	}
	public void Test_db(String ttl, Orig_fil_mok expd) {
		Orig_fil_itm actl = orig_fil_tbl.Select_itm(Bry_.new_u8(ttl));
		expd.Test(actl);
	}
	public static void Save_img(String url, int w, int h) {
		gplx.gfui.SizeAdp img_size = gplx.gfui.SizeAdp_.new_(w, h);
		Io_mgr.I.SaveFilStr(url, img_size.To_str());
	}
	public void Test_xto_fil_bry(String url_str, String expd) {
		Io_url url = Io_url_.new_fil_(url_str);
		Tfds.Eq(expd, String_.new_u8(Fs_root_dir.Xto_fil_bry(url)));
	}
}
class Orig_fil_mok {
	private int uid = -1;
	private int ext_id = -1;
	private String name = null;
	private int w = -1;
	private int h = -1;
	public Orig_fil_mok Url_(String v) {url = v; return this;} private String url = null;
	public Orig_fil_mok Size_(int w, int h) {this.w = w; this.h = h; return this;}
	public Orig_fil_mok Init(int uid, String url, int w, int h) {
		this.uid = uid;
		this.url = url; this.w = w; this.h = h;
		this.name = Io_url_.new_any_(url).NameAndExt();
		this.ext_id = Xof_ext_.new_by_ttl_(Bry_.new_u8(name)).Id();
		return this;
	}
	public void Test(Orig_fil_itm actl) {
		if (actl == null) Tfds.Fail("actl itm is null");
		if (w != -1)			Tfds.Eq(w, actl.Fil_w());
		if (h != -1)			Tfds.Eq(h, actl.Fil_h());
		if (url != null)		Tfds.Eq(url, actl.Fil_url().Raw());
		if (uid != -1)			Tfds.Eq(uid, actl.Fil_uid());
		if (ext_id != -1)		Tfds.Eq(uid, actl.Fil_ext_id());
		if (name != null)		Tfds.Eq(name, String_.new_u8(actl.Fil_name()));
	}
}
