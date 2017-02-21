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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import org.junit.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.files.imgs.*;
import gplx.fsdb.meta.*;
public class Fs_root_wkr_tst {
	@Before public void init() {fxt.Reset();} private Fs_root_wkr_fxt fxt = new Fs_root_wkr_fxt();
	@Test   public void Basic() {
		fxt.Init_fs("mem/dir/7/70/A.png", 200, 100);
		fxt.Test_get("A.png", fxt.itm_().Url_("mem/dir/7/70/A.png").Size_(200, 100));
		fxt.Test_db("A.png", fxt.itm_().Init(1, "mem/dir/7/70/A.png", 200, 100));
	}
	@Test   public void Recurse() {
		fxt.Init_fs("mem/dir/A/A1.png", 200, 100);
		fxt.Test_get("A1.png", fxt.itm_().Url_("mem/dir/A/A1.png").Size_(200, 100));
	}
}
class Fs_root_wkr_fxt {
	private Fs_root_wkr root_dir = new Fs_root_wkr();
	private Io_url url;
	public void Reset() {
		Db_conn_bldr.Instance.Reg_default_mem();
		Io_mgr.Instance.InitEngine_mem();
		url = Io_url_.mem_dir_("mem/dir/");
		root_dir = new Fs_root_wkr();
		Xof_img_wkr_query_img_size img_size_wkr = new Xof_img_wkr_query_img_size_test();
		root_dir.Init(img_size_wkr, url);
	}
	public Orig_fil_mok itm_() {return new Orig_fil_mok();}
	public void Init_fs(String url, int w, int h) {Save_img(url, w, h);}
	public void Test_get(String name, Orig_fil_mok expd) {
		Orig_fil_row actl = root_dir.Get_by_ttl(Bry_.new_u8(name));
		expd.Test(actl);
	}
	public void Test_db(String ttl, Orig_fil_mok expd) {
		Orig_fil_row actl = root_dir.Orig_tbl().Select_itm_or_null(url, Bry_.new_u8(ttl));
		expd.Test(actl);
	}
	public static void Save_img(String url, int w, int h) {
		gplx.gfui.SizeAdp img_size = gplx.gfui.SizeAdp_.new_(w, h);
		Io_mgr.Instance.SaveFilStr(url, img_size.To_str());
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
	public void Test(Orig_fil_row actl) {
		if (actl == null) Tfds.Fail("actl itm is null");
		if (w != -1)			Tfds.Eq(w, actl.W());
		if (h != -1)			Tfds.Eq(h, actl.H());
		if (url != null)		Tfds.Eq(url, actl.Url().Raw());
		if (uid != -1)			Tfds.Eq(uid, actl.Uid());
		if (ext_id != -1)		Tfds.Eq(uid, actl.Ext_id());
		if (name != null)		Tfds.Eq(name, String_.new_u8(actl.Name()));
	}
}
