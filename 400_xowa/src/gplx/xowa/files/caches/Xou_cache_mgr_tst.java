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
import org.junit.*; import gplx.dbs.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.repos.*;
public class Xou_cache_mgr_tst {
	@Before public void init() {fxt.Clear();} private final    Xou_cache_mgr_fxt fxt = new Xou_cache_mgr_fxt();
	@Test   public void Update() {
		Xof_fsdb_itm itm_1 = fxt.Make_itm("en.w", "1.png", 2);
		fxt.Exec_update(itm_1);
		fxt.Test_get(itm_1, 1, 0);
	}
	@Test   public void Update_mult() {
		Xof_fsdb_itm itm_1 = fxt.Make_itm("en.w", "1.png", 2);
		fxt.Exec_update(itm_1, itm_1, itm_1);
		fxt.Test_get(itm_1, 3, 2);
	}
	@Test   public void Reload() {
		Xof_fsdb_itm itm_1 = fxt.Make_itm("en.w", "1.png", 2);
		fxt.Exec_update(itm_1, itm_1, itm_1);
		fxt.Test_get(itm_1, 3, 2);
		fxt.Exec_save_and_clear();
		fxt.Test_get(itm_1, 3, 2);
	}
	@Test  public void Reduce() {
		fxt.Init_delete(3, 5);
		Xof_fsdb_itm itm_1 = fxt.Make_itm("en.w", "1.png", 2);
		Xof_fsdb_itm itm_2 = fxt.Make_itm("en.w", "2.png", 2);
		Xof_fsdb_itm itm_3 = fxt.Make_itm("en.w", "3.png", 2);
		fxt.Exec_update(itm_1, itm_2, itm_3);
		fxt.Exec_reduce();
		fxt.Test_get_y(itm_3);
		fxt.Test_get_n(itm_1, itm_2);
	}
	@Test  public void Reduce_same() {
		fxt.Init_delete(3, 5);
		Xof_fsdb_itm itm_1 = fxt.Make_itm("en.w", "1.png", 2);
		Xof_fsdb_itm itm_2 = fxt.Make_itm("fr.w", "1.png", 2);
		Xof_fsdb_itm itm_3 = fxt.Make_itm("en.w", "2.png", 2);
		fxt.Exec_update(itm_3, itm_2, itm_1);
		fxt.Exec_reduce();
		fxt.Test_get_y(itm_3);
		fxt.Test_get_n(itm_1, itm_2);
	}
}
class Xou_cache_mgr_fxt {
	private Xou_cache_mgr mgr;
	private long cache_min;
	public void Clear() {
		Datetime_now.Manual_(DateAdp_.new_(1970, 1, 1, 0, 0, 0, 0));
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		Xoae_app app = Xoa_app_fxt.Make__app__edit();			
		app.User().User_db_mgr().Init_by_app(Bool_.N, app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "xowa.user.anonymous.sqlite3"));
		Xoa_app_fxt.repo_(app, Xoa_app_fxt.Make__wiki__edit(app, "en.wikipedia.org"));
		Xoa_app_fxt.repo_(app, Xoa_app_fxt.Make__wiki__edit(app, "fr.wikipedia.org"));
		this.mgr = new Xou_cache_mgr(app.Wiki_mgr(), app.Fsys_mgr().File_dir(), app.User().User_db_mgr().Db_file());
	}
	public void Init_delete(long min, long max) {
		cache_min = min;
		mgr.Fsys_size_(min, max);
	}
	public Xof_fsdb_itm Make_itm(String wiki, String file, int size) {
		Xof_fsdb_itm rv = new Xof_fsdb_itm();
		byte[] wiki_domain = Bry_.new_a7(wiki);
		byte[] file_ttl = Bry_.new_a7(file);
		rv.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, wiki_domain, file_ttl, Byte_.Zero, 1, 1, 1 ,1, 1, 1);
		rv.Init_at_orig(Xof_repo_tid_.Tid__local, wiki_domain, file_ttl, Xof_ext_.new_by_id_(Xof_ext_.Id_png), 120, 120, Bry_.Empty);
		rv.File_size_(size);
		return rv;
	}
	public void Exec_update(Xof_fsdb_itm... ary) {
		for (Xof_fsdb_itm itm : ary)
			mgr.Update(itm);
	}
	public void Exec_save_and_clear() {
		mgr.Db_save();
		mgr.Clear();
	}
	public void Exec_reduce() {
		mgr.Reduce(cache_min);
	}
	public void Test_get(Xof_fsdb_itm fsdb, int expd_view_count, long expd_view_date) {
		Xou_cache_itm cache = mgr.Get_or_null(fsdb);
		Tfds.Eq(expd_view_count, cache.View_count(), "count");
		Tfds.Eq(expd_view_date , (cache.View_date() / 60) - 1, "time");	// Tfds.Now increments by 60 seconds; also -1 b/c Gfo_log now calls Datetime_now.Get once
	}
	public void Test_get_n(Xof_fsdb_itm... ary) {
		for (Xof_fsdb_itm itm : ary) {
			Xou_cache_itm cache = mgr.Get_or_null(itm);
			Tfds.Eq_null(cache, String_.new_u8(itm.Lnki_ttl()));
		}
	}
	public void Test_get_y(Xof_fsdb_itm... ary) {
		for (Xof_fsdb_itm itm : ary) {
			Xou_cache_itm cache = mgr.Get_or_null(itm);
			Tfds.Eq_nullNot(cache, String_.new_u8(itm.Lnki_ttl()));
		}
	}
}
