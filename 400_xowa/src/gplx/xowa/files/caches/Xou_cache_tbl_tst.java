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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import org.junit.*; import gplx.dbs.*;
public class Xou_cache_tbl_tst {
	@Before public void init() {fxt.Clear();} private final    Xou_cache_tbl_fxt fxt = new Xou_cache_tbl_fxt();
	@Test  public void Orig_ttl__same()			{fxt.Test_save_orig_ttl("A.png", "A.png", "");}
	@Test  public void Orig_ttl__redirect()		{fxt.Test_save_orig_ttl("A.png", "B.png", "B.png");}
}
class Xou_cache_tbl_fxt {
	private final    Bry_bfr lnki_key_bfr = Bry_bfr_.New_w_size(255);
	private Xou_cache_tbl tbl;
	public void Clear() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn_bldr_data conn_data = Db_conn_bldr.Instance.Get_or_new(Io_url_.mem_fil_("mem/test.xowa"));
		this.tbl = new Xou_cache_tbl(conn_data.Conn());
		tbl.Create_tbl();
	}
	public Xou_cache_itm Make_itm(String lnki_wiki_abrv_xo, String lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page, int user_thumb_w
	, int orig_repo_id, String orig_ttl, int orig_ext_id, int orig_w, int orig_h
	, int html_w, int html_h, double html_time, int html_page
	, boolean file_is_orig, int file_w, double file_time, int file_page, long file_size
	, int view_count, long view_date) {
		return new Xou_cache_itm(lnki_key_bfr, Db_cmd_mode.Tid_create
		, Bry_.new_u8(lnki_wiki_abrv_xo), Bry_.new_u8(lnki_ttl), lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, user_thumb_w
		, orig_repo_id, Bry_.new_u8(orig_ttl), orig_ext_id, orig_w, orig_h
		, html_w, html_h, html_time, html_page
		, file_is_orig, file_w, file_time, file_page, file_size
		, view_count, view_date
		);
	}
	public Xou_cache_itm Exec_select_one(String lnki_wiki_abrv_xo, String lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page, int user_thumb_w) {
		return tbl.Select_one(Bry_.new_u8(lnki_wiki_abrv_xo), Bry_.new_u8(lnki_ttl), 1, 1, 1, 1, 1, 1, 1);
	}
	public void Test_save_orig_ttl(String lnki_ttl, String orig_ttl, String expd_orig_ttl) {
		Xou_cache_itm itm = Make_itm("en.w", lnki_ttl, 1, 1, 1, 1, 1, 1, 1, 1, orig_ttl, 1, 1, 1, 1, 1, 1, 1, Bool_.Y, 1, 1, 1, 1, 1, 1);
		tbl.Db_save(itm);
		Db_rdr rdr = tbl.Select_all_for_test();
		try {
			Tfds.Eq_true(rdr.Move_next());
			Tfds.Eq(expd_orig_ttl, rdr.Read_str(tbl.Fld_orig_ttl()));
		}
		finally {rdr.Rls();}
	}
}
