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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.core.log_msgs.*;
import gplx.langs.phps.*; import gplx.langs.dsvs.*;
import gplx.xowa.apps.gfs.*;
public class Hiero_mw_tables_parser {
	private Php_parser parser = new Php_parser(); private Php_evaluator evaluator;
	private Php_text_itm_parser quote_parser = new Php_text_itm_parser();
	public Hiero_mw_tables_parser() {evaluator = new Php_evaluator(Gfo_msg_log.Test());}
	public void Bld_all(Io_url load_fil, Io_url save_fil) {
		Hiero_xtn_mgr xtn_mgr = new Hiero_xtn_mgr();
		xtn_mgr.Clear();	// clear b/c members are actually static; else test will fail
		Load_data(xtn_mgr, load_fil);
		Save_data(xtn_mgr, save_fil);	// prefabs.gfs (key only); files.gfs (key,size); phonemes (key, val)
	}
	public void Load_data(Hiero_xtn_mgr xtn_mgr, Io_url load_fil) {// NOTE: parsing tables.php instead of tables.ser b/c latter is too difficult to read / debug
		evaluator.Clear();
		parser.Parse_tkns(Io_mgr.Instance.LoadFilBry(load_fil), evaluator);
		Php_line[] lines = (Php_line[])evaluator.List().To_ary(Php_line.class);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			Php_line_assign line = (Php_line_assign)lines[i];
			byte[] key = line.Key().Val_obj_bry();
			Object o = Tid_hash.Get_by_bry(key);
			if (o != null) {
				Byte_obj_val stub = (Byte_obj_val)o;
				switch (stub.Val()) {
					case Tid_prefabs:	Parse_prefabs(xtn_mgr.Prefab_mgr(), line); break;
					case Tid_files:		Parse_files(xtn_mgr.File_mgr(), line); break;
					case Tid_phonemes:	Parse_phonemes(xtn_mgr.Phoneme_mgr(), line); break;
				}
			}
		}
	}
	private void Parse_prefabs(Hiero_prefab_mgr mgr, Php_line_assign line) {	// $wh_prefabs = array(v, v ...);
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm itm = (Php_itm)ary.Subs_get(i);
			mgr.Add(Php_itm_.Parse_bry(itm));
		}		
	}
	private void Parse_files(Hiero_file_mgr mgr, Php_line_assign line) {	// $wh_files = array(k => array(w, h), k => array(w, h) ...);
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key = Php_itm_.Parse_bry(kv.Key());
			Php_itm_ary val_ary = (Php_itm_ary)kv.Val();
			int w = Php_itm_.Parse_int_or(val_ary.Subs_get(0), -1);
			int h = Php_itm_.Parse_int_or(val_ary.Subs_get(1), -1);
			mgr.Add(key, w, h);
		}		
	}
	private void Parse_phonemes(Hiero_phoneme_mgr mgr, Php_line_assign line) {	// $wh_phonemes = array(k => v, k => v ...);
		List_adp tmp_list = List_adp_.New(); Byte_obj_ref tmp_rslt = Byte_obj_ref.zero_(); Bry_bfr tmp_bfr = Bry_bfr_.New();
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] kv_key = quote_parser.Parse_as_bry(tmp_list, Php_itm_.Parse_bry(kv.Key()), tmp_rslt, tmp_bfr);
			mgr.Add(kv_key, Php_itm_.Parse_bry(kv.Val()));
		}		
	}
	public void Save_data(Hiero_xtn_mgr xtn_mgr, Io_url save_fil) {
		Xoa_gfs_bldr bldr = new Xoa_gfs_bldr();
		int len = -1;

		Hiero_prefab_mgr prefab_mgr = xtn_mgr.Prefab_mgr();
		len = prefab_mgr.Len();
		bldr.Add_proc_init_many(Hiero_xtn_mgr.Invk_prefabs, Hiero_phoneme_mgr.Invk_srl, Dsv_wkr_base.Invk_load_by_str).Add_quote_xtn_apos_bgn();	// prefabs.srl.load_by_str('\n
		for (int i = 0; i < len; i++) {
			Hiero_prefab_itm itm = prefab_mgr.Get_at(i);
			bldr.Bfr().Add(itm.Key()).Add_byte_nl();	// NOTE: escape not needed
		}
		bldr.Add_quote_xtn_apos_end();	// ');\n
		bldr.Add_nl();

		Hiero_file_mgr file_mgr = xtn_mgr.File_mgr();
		len = file_mgr.Len();
		bldr.Add_proc_init_many(Hiero_xtn_mgr.Invk_files, Hiero_phoneme_mgr.Invk_srl, Dsv_wkr_base.Invk_load_by_str).Add_quote_xtn_apos_bgn();		// files.srl.load_by_str('\n
		for (int i = 0; i < len; i++) {
			Hiero_file_itm itm = file_mgr.Get_at(i);
			bldr.Bfr().Add(itm.Key()).Add_byte_pipe().Add_int_variable(itm.File_w()).Add_byte_pipe().Add_int_variable(itm.File_h()).Add_byte_nl();	// NOTE: escape not needed
		}
		bldr.Add_quote_xtn_apos_end();	// ');\n
		bldr.Add_nl();

		Hiero_phoneme_mgr phoneme_mgr = xtn_mgr.Phoneme_mgr();
		len = phoneme_mgr.Len();
		bldr.Add_proc_init_many(Hiero_xtn_mgr.Invk_phonemes, Hiero_phoneme_mgr.Invk_srl, Dsv_wkr_base.Invk_load_by_str).Add_paren_bgn().Add_nl();	// phonemes.srl.load_by_str(
		bldr.Add_quote_xtn_bgn();
		for (int i = 0; i < len; i++) {
			Hiero_phoneme_itm itm = phoneme_mgr.Get_at(i);
			bldr.Bfr().Add(itm.Key()).Add_byte_pipe().Add(itm.Gardiner_code()).Add_byte_nl();	// NOTE: escape not needed
		}
		bldr.Add_quote_xtn_end();
		bldr.Add_paren_end().Add_term_nl();
		Io_mgr.Instance.SaveFilBfr(save_fil, bldr.Bfr());
	}
	private static final byte Tid_prefabs = 0, Tid_files = 1, Tid_phonemes = 2;
	private static Hash_adp_bry Tid_hash = Hash_adp_bry.cs().Add_str_byte("wh_prefabs", Tid_prefabs).Add_str_byte("wh_files", Tid_files).Add_str_byte("wh_phonemes", Tid_phonemes);
}
