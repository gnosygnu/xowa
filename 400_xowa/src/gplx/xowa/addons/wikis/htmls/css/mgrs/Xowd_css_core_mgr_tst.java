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
package gplx.xowa.addons.wikis.htmls.css.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import org.junit.*; import gplx.core.ios.*; import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.htmls.css.dbs.*;
public class Xowd_css_core_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xowd_css_core_mgr_fxt fxt = new Xowd_css_core_mgr_fxt();
	@Test   public void Basic() {
		Xowd_css_core_itm[] skin_ary = fxt.Make_skin_ary
		( fxt.Make_skin_itm(1, "desktop", "20010101_050200")
		);
		Xowd_css_file_itm[] file_ary = fxt.Make_file_ary
		( fxt.Make_file_itm(1, "a.css", "a_data")
		, fxt.Make_file_itm(1, "b/b.png", "b/b_data")
		);
		Io_url src_dir = Io_url_.mem_dir_("mem/src/");
		fxt.Init_fs(src_dir, file_ary);
		fxt.Exec_set(src_dir, "desktop");
		fxt.Test_skin_tbl(skin_ary);
		fxt.Test_file_tbl(file_ary);

		Io_url trg_dir = Io_url_.mem_dir_("mem/trg/");
		fxt.Exec_get(trg_dir, "desktop");
		fxt.Test_fs(trg_dir, file_ary);
	}
	@Test   public void Update() {	// update css files; keep same skin_id; insert new files
		Xowd_css_core_itm[] skin_ary = fxt.Make_skin_ary
		( fxt.Make_skin_itm(1, "desktop", "20010101_050500")
		);
		Xowd_css_file_itm[] file_ary = fxt.Make_file_ary
		( fxt.Make_file_itm(1, "a.css", "a_data")
		, fxt.Make_file_itm(1, "b/b.png", "b/b_data")
		);
		Io_url src_dir = Io_url_.mem_dir_("mem/src/");
		fxt.Init_fs(src_dir, file_ary);
		fxt.Exec_set(src_dir, "desktop");

		file_ary = fxt.Make_file_ary
		( fxt.Make_file_itm(1, "a1.css", "a1_data")
		, fxt.Make_file_itm(1, "b/b1.png", "b/b1_data")
		);
		Io_mgr.Instance.DeleteDirDeep(src_dir);
		fxt.Init_fs(src_dir, file_ary);
		fxt.Exec_set(src_dir, "desktop");
		fxt.Test_skin_tbl(skin_ary);
		fxt.Test_file_tbl(file_ary);
	}
}
class Xowd_css_core_mgr_fxt {
	private final    Bry_bfr bfr = Bry_bfr_.Reset(32);
	private Xowd_css_core_tbl core_tbl; private Xowd_css_file_tbl file_tbl;
	public void Clear() {
		Datetime_now.Manual_y_();
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/css.sqlite3"));
		this.core_tbl = new Xowd_css_core_tbl(conn);
		this.file_tbl = new Xowd_css_file_tbl(conn);
		core_tbl.Create_tbl();
		file_tbl.Create_tbl();
	}
	public Xowd_css_core_itm Make_skin_itm(int id, String key, String updated_on) {return new Xowd_css_core_itm(id, key, DateAdp_.parse_gplx(updated_on));}
	public Xowd_css_file_itm Make_file_itm(int skin_id, String path, String data) {return new Xowd_css_file_itm(skin_id, path, Bry_.new_u8(data));}
	public Xowd_css_file_itm[] Make_file_ary(Xowd_css_file_itm... ary) {return ary;}
	public Xowd_css_core_itm[] Make_skin_ary(Xowd_css_core_itm... ary) {return ary;}
	public void Init_fs(Io_url css_dir, Xowd_css_file_itm[] file_ary) {
		for (Xowd_css_file_itm itm : file_ary)
			Io_mgr.Instance.SaveFilBry(css_dir.GenSubFil(itm.Path()), itm.Data());
	}
	public void Exec_set(Io_url css_dir, String key) {Xowd_css_core_mgr.Set(core_tbl, file_tbl, css_dir, key);}
	public void Exec_get(Io_url css_dir, String key) {Xowd_css_core_mgr.Get(core_tbl, file_tbl, css_dir, key);}
	public void Test_skin_tbl(Xowd_css_core_itm[] expd) {
		Xowd_css_core_itm[] actl = core_tbl.Select_all();
		Tfds.Eq_str_lines(To_str(expd), To_str(actl));
	}
	public void Test_file_tbl(Xowd_css_file_itm[] expd) {
		Xowd_css_file_itm[] actl = file_tbl.Select_all();
		Tfds.Eq_str_lines(To_str(expd), To_str(actl));
	}
	public void Test_fs(Io_url css_dir, Xowd_css_file_itm[] expd) {
		Io_url[] actl = Io_mgr.Instance.QueryDir_args(css_dir).Recur_().ExecAsUrlAry();
		int len = expd.length;
		Tfds.Eq(len, actl.length);
		for (int i = 0; i < len; ++i) {
			Xowd_css_file_itm expd_itm = expd[i];
			Tfds.Eq_bry(expd_itm.Data(), Io_mgr.Instance.LoadFilBry(actl[i]));
		}
	}
	private String To_str(Xowd_css_file_itm[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xowd_css_file_itm itm = ary[i];
			bfr.Add_int_variable(itm.Css_id()).Add_byte_pipe().Add_str_u8(itm.Path()).Add_byte_pipe().Add(itm.Data()).Add_byte_nl();
		}			
		return bfr.To_str_and_clear();
	}
	private String To_str(Xowd_css_core_itm[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xowd_css_core_itm itm = ary[i];
			bfr.Add_int_variable(itm.Id()).Add_byte_pipe().Add_str_u8(itm.Key()).Add_byte_pipe().Add_str_u8(itm.Updated_on().XtoStr_fmt_yyyyMMdd_HHmmss()).Add_byte_nl();
		}			
		return bfr.To_str_and_clear();
	}
}
