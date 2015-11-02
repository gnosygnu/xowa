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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.core.flds.*; import gplx.core.ios.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.bldrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.tbls.*;
public abstract class Xob_ctg_v1_base extends Xob_itm_dump_base implements Xobd_parser_wkr, GfoInvkAble {
	protected Xob_ctg_v1_base() {}	// TEST:needed for fxt
	public Xob_ctg_v1_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Wkr_key();
	public abstract Io_sort_cmd Make_sort_cmd();
	public Ordered_hash Wkr_hooks() {return wkr_hooks;} private Ordered_hash wkr_hooks = Ordered_hash_.New_bry();
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Wkr_key(), wiki.Tdb_fsys_mgr().Site_dir().GenSubDir(Xotdb_dir_info_.Name_category));
		Bry_bfr tmp_bfr = bldr.App().Utl__bfr_mkr().Get_b512();
		Xol_lang_itm lang = wiki.Lang();
		wkr_hooks_add(tmp_bfr, lang.Ns_names());
		wkr_hooks_add(tmp_bfr, lang.Ns_aliases());
		wkr_hooks_add(tmp_bfr, Xow_ns_.Canonical);
		tmp_bfr.Mkr_rls();
		fld_wtr.Bfr_(dump_bfr);
	}
	public int Wkr_run(Xowd_page_itm page, byte[] src, int src_len, int bgn, int end) {
		int ttl_bgn = end, ttl_end = -1;
		int pos = end;
		while (true) {
			if (pos == src_len) {
				Log(Tid_eos, page, src, bgn);
				return end;
			}
			Object o = trie.Match_bgn(src, pos, src_len);
			if (o != null) {
				Btrie_itm_stub stub = (Btrie_itm_stub)o;
				byte[] bry = stub.Val();
				switch (stub.Tid()) {
					case Tid_brack_end: case Tid_pipe:
						ttl_bgn = Move_fwd_while_space(src, src_len, ttl_bgn);
						ttl_end = Move_bwd_while_space(src, src_len, pos - 1);
						if (ttl_end > ttl_bgn)	// NOTE: ignore examples like [[Category: ]]
							Process_ctg(page, src, src_len, ttl_bgn, ttl_end);
						break;
					case Tid_brack_bgn:
						Log(Tid_brack_bgn, page, src, bgn);
						return pos;
					case Tid_nl:
						Log(Tid_nl, page, src, bgn);
						return pos;
				}
				return pos + bry.length;
			}
			++pos;
		}
	}
	@gplx.Virtual public void Log(byte err_tid, Xowd_page_itm page, byte[] src, int ctg_bgn) {
		String title = String_.new_u8(page.Ttl_full_db());
		int ctg_end = ctg_bgn + 40; if (ctg_end > src.length) ctg_end = src.length;
		String ctg_str = String_.Replace(String_.new_u8(src, ctg_bgn, ctg_end), "\n", "");
		String err = "";
		switch (err_tid) {
			case Tid_eos:		err = "eos"; break;
			case Tid_nl:		err = "bad \\n"; break;
			case Tid_brack_bgn:	err = "bad [["; break;
		}
		bldr.Usr_dlg().Log_many(GRP_KEY, "ctg_fail", "~{0}\n>> ~{1}\n~{2}\n~{3}\n\n", LogErr_hdr, err + " " + ctg_str, "http://" + wiki.Domain_str() + "/wiki/" + title, Bry_.MidByLenToStr(src, ctg_bgn, 100));
		log_idx++;
	}	int log_idx = 0; final String LogErr_hdr = String_.Repeat("-", 80);
	@gplx.Virtual public void Process_ctg(Xowd_page_itm page, byte[] src, int src_len, int bgn, int end) {
		Process_ctg_row(fld_wtr, dump_fil_len, dump_url_gen, page.Id(), src, src_len, bgn, end);
	}
	public static void Process_ctg_row(Gfo_fld_wtr fld_wtr, int dump_fil_len, Io_url_gen dump_url_gen, int page_id, byte[] src, int src_len, int bgn, int end) {
		int len = end - bgn;
		Bry_bfr dump_bfr = fld_wtr.Bfr();
		if (dump_bfr.Len() + row_fixed_len + len > dump_fil_len) Io_mgr.Instance.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
		byte[] ttl = Bry_.Mid(src, bgn, end);
		Bry_.Replace_reuse(ttl, Byte_ascii.Space, Byte_ascii.Underline);
		fld_wtr.Write_bry_escape_fld(ttl).Write_int_base85_len5_row(page_id);
	}
	public void Wkr_end() {
		this.Term_dump(this.Make_sort_cmd());
		if (delete_temp) Io_mgr.Instance.DeleteDirDeep(temp_dir);
	}
	private Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	Btrie_fast_mgr trie = Btrie_fast_mgr.cs().Add_stub(Tid_brack_end, "]]").Add_stub(Tid_pipe, "|").Add_stub(Tid_nl, "\n").Add_stub(Tid_brack_bgn, "[[");
	static final int row_fixed_len = 5 + 1 + 1;	// 5=rowId; 1=|; 1=\n
	List_adp category_list = List_adp_.new_(); Int_obj_ref cur_pos = Int_obj_ref.zero_();
	static final byte Tid_eos = 0, Tid_brack_end = 1, Tid_pipe = 2, Tid_nl = 3, Tid_brack_bgn = 4;
	private static int Move_fwd_while_space(byte[] src, int src_len, int pos) {
		while (true) {
			if (pos == src_len) return pos;
			switch (src[pos]) {
				case Byte_ascii.Space:	break;
				default:				return pos;
			}
			++pos;
		}
	}
	private static int Move_bwd_while_space(byte[] src, int src_len, int pos) {
		while (true) {
			if (pos == -1) return 0;
			switch (src[pos]) {
				case Byte_ascii.Space:	break;
				default:				return pos + 1;
			}
			--pos;
		}
	}
	private void wkr_hooks_add(Bry_bfr tmp_bfr, Xow_ns[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ary[i];
			if (ns.Id_ctg()) wkr_hooks_add(tmp_bfr, ns.Name_bry());
		}
	}
	private void wkr_hooks_add(Bry_bfr tmp_bfr, Xol_ns_grp ns_grp) {
		int len = ns_grp.Len();
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ns_grp.Get_at(i);
			if (ns.Id_ctg()) wkr_hooks_add(tmp_bfr, ns.Name_bry());
		}
	}
	private void wkr_hooks_add(Bry_bfr tmp_bfr, byte[] word) {
		tmp_bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Brack_bgn).Add(word).Add_byte(Byte_ascii.Colon);
		byte[] key = tmp_bfr.To_bry_and_clear();
		if (!wkr_hooks.Has(key)) wkr_hooks.Add(key, key);
	}	
	static final String GRP_KEY = "xowa.bldr.make_ctg";
}
