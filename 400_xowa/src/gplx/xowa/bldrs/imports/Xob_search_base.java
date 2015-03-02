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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.primitives.*;
import gplx.ios.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.dbs.*; import gplx.xowa.tdbs.*;
public abstract class Xob_search_base extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public abstract String Wkr_key();
	public abstract Io_make_cmd Make_cmd_site();
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		make_dir = wiki.Tdb_fsys_mgr().Ns_dir();
		this.Init_dump(this.Wkr_key(), make_dir);
		lang = wiki.Lang(); // wiki.Appe().Lang_mgr().Lang_en();	// NOTE: was .Lang_en which is wrong (should match lang of wiki); DATE:2013-05-11
		tmp_wtr_mgr = new Xob_tmp_wtr_mgr(new Xob_tmp_wtr_wkr__ttl(temp_dir, dump_fil_len));
		if (wiki.Db_mgr().Tid() == Xodb_mgr_sql.Tid_sql)	// if sqlite, hard-code to ns_main; aggregates all ns into one
			ns_main = wiki.Ns_mgr().Ns_main();
	}	private Xob_tmp_wtr_mgr tmp_wtr_mgr; private Xow_ns ns_main;
	public void Wkr_run(Xodb_page page) {
//			if (page.Ns_id() != Xow_ns_.Id_main) return; // limit to main ns for now
		try {
		byte[] ttl = page.Ttl_wo_ns();
		byte[][] words = Split(lang, list, dump_bfr, ttl);
		Xob_tmp_wtr wtr = tmp_wtr_mgr.Get_or_new(ns_main == null ? page.Ns() : ns_main);
		int words_len = words.length;
		int row_len = 0;
		for (int i = 0; i < words_len; i++) {
			byte[] word = words[i];	
			row_len += word.length + 13;	// 13=5(id) + 5(page_len) + 3(dlms)
		}
		if (wtr.FlushNeeded(row_len)) wtr.Flush(bldr.Usr_dlg());
		for (int i = 0; i < words_len; i++) {
			byte[] word = words[i];				
			wtr.Bfr()	.Add(word)								.Add_byte(Byte_ascii.Pipe)
						.Add_base85_len_5(page.Id())			.Add_byte(Byte_ascii.Semic)
						.Add_base85_len_5(page.Text().length)	.Add_byte(Byte_ascii.NewLine);
		}
		} catch (Exception e) {bldr.Usr_dlg().Warn_many("", "", "search_index:fatal error: err=~{0}", Err_.Message_gplx_brief(e));}	// never let single page crash entire import
	}
	public void Wkr_end() {
		tmp_wtr_mgr.Flush_all(bldr.Usr_dlg());
		dump_bfr.ClearAndReset();
		Xobdc_merger.Ns(bldr.Usr_dlg(), tmp_wtr_mgr.Regy(), Xotdb_dir_info_.Name_search_ttl, temp_dir, make_dir, sort_mem_len, Io_line_rdr_key_gen_.first_pipe, this.Make_cmd_site());
		tmp_wtr_mgr.Rls_all();
		if (delete_temp) Io_mgr._.DeleteDirDeep(temp_dir);
		if (wiki.Db_mgr().Tid() == Xodb_mgr_sql.Tid_sql) {
			Xowe_core_data_mgr core_data_mgr = (Xowe_core_data_mgr)wiki.Data_mgr__core_mgr();
			core_data_mgr.Dbs__save();	// always save files now; need to commit created search_db_idx to xowa_db, else will be reused by ctg v2; DATE:2014-02-07
		}
	}
	public void Wkr_print() {}
	OrderedHash list = OrderedHash_.new_(); Xol_lang lang;
	static final int row_fixed_len = 5 + 1 + 1 + 1;	// 5=rowId; 1=|; 1=NmsOrd; 1=|		
	public static byte[][] Split(Xol_lang lang, OrderedHash list, Bry_bfr bfr, byte[] ttl) {
		if (lang != null)	// null lang passed in by searcher
			ttl = lang.Case_mgr().Case_build_lower(ttl);
		int ttl_len = ttl.length; Bry_obj_ref word_ref = Bry_obj_ref.new_(Bry_.Empty);
		int i = 0; boolean word_done = false;
		while (true) {
			if (word_done || i == ttl_len) {
				if (bfr.Len() > 0) {
					byte[] word = bfr.Xto_bry();
					word_ref.Val_(word);
					if (!list.Has(word_ref)) list.Add(word_ref, word);
					bfr.ClearAndReset();
				}
				if (i == ttl_len) break;
				word_done = false;
			}
			byte b = ttl[i];
			switch (b) {
				case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn: case Byte_ascii.Space: case Byte_ascii.Underline:
				case Byte_ascii.Dash:	// treat hypenated words separately
				case Byte_ascii.Dot:	// treat abbreviations as separate words; EX: A.B.C.
				case Byte_ascii.Bang: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent:
				case Byte_ascii.Amp: case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Asterisk:
				case Byte_ascii.Comma: case Byte_ascii.Slash:
				case Byte_ascii.Colon: case Byte_ascii.Semic: case Byte_ascii.Gt:
				case Byte_ascii.Question: case Byte_ascii.At: case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end:
				case Byte_ascii.Pow: case Byte_ascii.Tick:
				case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe: case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
				case Byte_ascii.Quote:	case Byte_ascii.Apos: // FUTURE: apos will split "Earth's" to Earth and s; should remove latter
					++i;
					word_done = true;
					break;
				default:
					bfr.Add_byte(b);
					++i;
					break;
			}
		}
		bfr.ClearAndReset();
		byte[][] rv = (byte[][])list.Xto_ary(byte[].class);
		list.Clear(); list.ResizeBounds(16);
		return rv;
	}
}
