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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.encoders.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.tdbs.xdats.*;
public class Xow_hive_mgr_fxt {
	public void Clear() {
		if (hive_mgr == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			hive_mgr = new Xob_hive_mgr(wiki);
		}
		hive_mgr.Clear();
		Io_mgr.Instance.InitEngine_mem();
	}	private Xob_hive_mgr hive_mgr; Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Find_nearby(String key, int count, boolean include_redirects, String... expd) {
		List_adp list = List_adp_.New();
		wiki.Hive_mgr().Find_bgn(list, wiki.Ns_mgr().Ns_main(), Bry_.new_a7(key), count, include_redirects);
		int actl_len = list.Count();
		String[] actl = new String[actl_len];
		for (int i = 0; i < actl_len; i++) {
			Xowd_page_itm itm = (Xowd_page_itm)list.Get_at(i);
			actl[i] = String_.new_a7(itm.Ttl_page_db());
		}
		Tfds.Eq_ary_str(expd, actl);
	}
	public static void Ttls_create_rng(Xowe_wiki wiki, int files, int ttls_per_file) {Ttls_create_rng(wiki, wiki.Ns_mgr().Ns_main(), files, ttls_per_file);}
	public static void Ttls_create_rng(Xowe_wiki wiki, Xow_ns ns, int files, int ttls_per_file) {
		Xob_reg_wtr reg_wtr = new Xob_reg_wtr();
		byte dir_tid = Xotdb_dir_info_.Tid_ttl;
		int id = 0;
		int ttl_bry_len = Int_.DigitCount(ttls_per_file);
		Xob_xdat_file_wtr xdat_wtr = Xob_xdat_file_wtr.new_file_(ttls_per_file * 8, wiki.Tdb_fsys_mgr().Url_ns_dir(ns.Num_str(), Xotdb_dir_info_.Tid_ttl));
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		byte ltr = Byte_ascii.Ltr_A; byte[] ttl_0 = Bry_.Empty, ttl_n = Bry_.Empty;
		for (int fil_idx = 0; fil_idx < files; fil_idx++) {
			for (int ttl_idx = 0; ttl_idx < ttls_per_file; ttl_idx++) {
				tmp_bfr.Add_byte(ltr).Add_int_fixed(ttl_idx, ttl_bry_len);
				byte[] ttl_bry = tmp_bfr.To_bry_and_clear(); 
				if 		(ttl_idx == 0) 					ttl_0 = ttl_bry;
				else if (ttl_idx == ttls_per_file - 1) 	ttl_n = ttl_bry;
				Xotdb_page_itm_.Txt_ttl_save(xdat_wtr.Bfr(), id++, 0, ttl_idx, ttl_idx % 2 == 1, 1, ttl_bry);
				xdat_wtr.Add_idx(Byte_ascii.Null);
			}
			xdat_wtr.Flush(wiki.Appe().Usr_dlg());
			reg_wtr.Add(ttl_0, ttl_n, ttls_per_file);
			++ltr;
		}
		reg_wtr.Flush(wiki.Tdb_fsys_mgr().Url_ns_reg(ns.Num_str(), dir_tid));
	}
	public Xow_hive_mgr_fxt Create_ctg(String key_str, int... pages) {Create_ctg(app, hive_mgr, key_str, pages); return this;}
	public static void Create_ctg(Xoae_app app, Xob_hive_mgr hive_mgr, String key_str, int... pages) {
		byte[] key_bry = Bry_.new_a7(key_str);
		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b512();
		bfr.Add(key_bry);
		int pages_len = pages.length;
		for (int i = 0; i < pages_len; i++)				
			bfr.Add_byte_pipe().Add_base85_len_5(pages[i]);
		bfr.Add_byte_nl();
		byte[] row = bfr.To_bry_and_rls();
		hive_mgr.Create(Xotdb_dir_info_.Tid_category, key_bry, row);
	}
	public Xow_hive_mgr_fxt Create_id(int id, int fil_idx, int row_idx, boolean type_redirect, int itm_len, int ns_id, String ttl) {Create_id(app, hive_mgr, id, fil_idx, row_idx, type_redirect, itm_len, ns_id, ttl); return this;}
	public static void Create_id(Xoae_app app, Xob_hive_mgr hive_mgr, int id, int fil_idx, int row_idx, boolean type_redirect, int itm_len, int ns_id, String ttl) {
		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b512();
		byte[] key_bry = Base85_.To_bry(id, 5);
		bfr	.Add(key_bry)						.Add_byte_pipe()
			.Add_base85_len_5(fil_idx)			.Add_byte_pipe()
			.Add_base85_len_5(row_idx)			.Add_byte_pipe()
			.Add_byte(type_redirect	? Byte_ascii.Num_1 : Byte_ascii.Num_0).Add_byte_pipe()
			.Add_base85_len_5(itm_len)			.Add_byte_pipe()
			.Add_base85_len_5(ns_id)			.Add_byte_pipe()
			.Add_str_u8(ttl)					.Add_byte_nl();
		byte[] row = bfr.To_bry_and_clear();
		bfr.Mkr_rls();
		hive_mgr.Create(Xotdb_dir_info_.Tid_id, key_bry, row);
	}
	public Xow_hive_mgr_fxt Load(String url, String... expd) {
		String actl = Io_mgr.Instance.LoadFilStr(url);
		Tfds.Eq_ary_str(expd, String_.SplitLines_nl(actl));
		return this;
	}
}
class Xob_reg_wtr {
	Bry_bfr bfr = Bry_bfr_.New(); int fil_count = 0;
	public void Add(byte[] bgn, byte[] end, int itm_count) {
		bfr
		.Add_int_variable(fil_count++).Add_byte(Byte_ascii.Pipe)
		.Add(bgn).Add_byte(Byte_ascii.Pipe)
		.Add(end).Add_byte(Byte_ascii.Pipe)
		.Add_int_variable(itm_count).Add_byte(Byte_ascii.Nl);		
	}
	public void Flush(Io_url url) {
		Io_mgr.Instance.SaveFilBfr(url, bfr);
//			Tfds.Dbg(url.Raw() + "\n" + Io_mgr.Instance.LoadFilStr(url));
	}
}
