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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.fsdb.*; import gplx.xowa.files.wiki_orig.*; import gplx.xowa.files.qrys.*; import gplx.xowa.files.bins.*;
import gplx.xowa.files.fsdb.caches.*;
public class Xof_fsdb_mgr_mem implements Xof_fsdb_mgr, Xof_bin_wkr {
	private Hash_adp_bry bin_hash = Hash_adp_bry.cs_(); private Bry_bfr bin_key_bfr = Bry_bfr.new_();
	private Hash_adp_bry reg_hash = Hash_adp_bry.cs_();		
	public boolean Tid_is_mem() {return true;}
	public int Patch_upright() {return Xof_patch_upright_tid_.Tid_all;}
	public Xof_qry_mgr Qry_mgr() {return qry_mgr;} private Xof_qry_mgr qry_mgr = new Xof_qry_mgr();
	public Xof_bin_mgr Bin_mgr() {return bin_mgr;} private Xof_bin_mgr bin_mgr;
	public Xof_bin_wkr Bin_wkr_fsdb() {return this;}
	public boolean Bin_wkr_resize() {return bin_wkr_resize;} public void Bin_wkr_resize_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;
	public void Db_bin_max_(long v) {}
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Null;
	private Io_url fs_dir;
	private Xof_url_bldr url_bldr = new Xof_url_bldr();
	public Cache_mgr Cache_mgr() {return cache_mgr;} private Cache_mgr cache_mgr = new Cache_mgr(null);
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Fsdb_mnt_mgr Mnt_mgr() {throw Err_.not_implemented_();}
	public boolean Init_by_wiki(Xow_wiki wiki) {throw Err_.not_implemented_();}
	public boolean Init_by_wiki__add_bin_wkrs(Xow_wiki wiki) {throw Err_.not_implemented_();}
	public void Reg_select_only(Xoa_page page, byte exec_tid, ListAdp itms, OrderedHash hash) {throw Err_.not_implemented_();}
	public void Init_by_wiki(Xow_wiki wiki, Io_url db_dir, Io_url fs_dir, Xow_repo_mgr repo_mgr) {
		this.fs_dir = fs_dir;
		this.wiki = wiki;
		cache_mgr = wiki.App().File_mgr().Cache_mgr();
		bin_mgr = new Xof_bin_mgr(wiki, null, repo_mgr);	// HACK: pass null fsdb_mgr; fsdb_mgr only needed for factory methods.
	}
	public void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		byte[] key = Key_bld_fil(dir, fil);
		byte[] bin = gplx.ios.Io_stream_rdr_.Load_all_as_bry(bin_rdr);
		Fsdb_fil_itm_mem itm = new Fsdb_fil_itm_mem();
		itm.Init(key, dir, fil, bin);
		bin_hash.Add(key, itm);
	}
	public void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int thm_w, int thm_h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		byte[] key = Key_bld_thm(dir, fil, thm_w, thumbtime);
		byte[] bin = gplx.ios.Io_stream_rdr_.Load_all_as_bry(bin_rdr);
		Fsdb_xtn_thm_itm_mem itm = new Fsdb_xtn_thm_itm_mem();
		itm.Init(key, dir, fil, thm_w, thm_h, thumbtime, bin);
		bin_hash.Add(key, itm);
	}
	public void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		byte[] key = Key_bld_fil(dir, fil);
		byte[] bin = gplx.ios.Io_stream_rdr_.Load_all_as_bry(bin_rdr);
		Fsdb_xtn_img_itm_mem itm = new Fsdb_xtn_img_itm_mem();
		itm.Init(key, dir, fil, img_w, img_h, bin);
		bin_hash.Add(key, itm);
	}
	public void Reg_insert(Xof_fsdb_itm fsdb_itm, byte repo_id, byte status) {
		byte[] fsdb_itm_ttl = fsdb_itm.Orig_ttl();
		if (reg_hash.Has(fsdb_itm_ttl)) return;
		Xof_wiki_orig_itm regy_itm = new Xof_wiki_orig_itm();
		regy_itm.Ttl_(fsdb_itm_ttl).Status_(status).Orig_repo_(repo_id).Orig_redirect_(fsdb_itm.Orig_redirect()).Orig_ext_(fsdb_itm.Lnki_ext().Id()).Orig_w_(fsdb_itm.Orig_w()).Orig_h_(fsdb_itm.Orig_h());
		reg_hash.Add(fsdb_itm_ttl, regy_itm);
	}
	public void Reg_select(Xoa_page page, byte exec_tid, ListAdp itms) {
		int itms_len = itms.Count();
		for (int i = 0; i < itms_len; i++) {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.FetchAt(i);
			Xof_wiki_orig_itm reg_itm = (Xof_wiki_orig_itm)reg_hash.Get_by_bry(itm.Lnki_ttl());
			if (reg_itm == null) {
				itm.Rslt_reg_(Xof_wiki_orig_wkr_.Tid_missing_reg);
				continue;
			}
			byte repo_id = reg_itm.Orig_repo();
			if (repo_id <= Xof_repo_itm.Repo_local) {
				byte[] wiki = bin_mgr.Repo_mgr().Repos_get_at(repo_id).Trg().Wiki_key();
				itm.Orig_wiki_(wiki);
			}
			itm.Orig_size_(reg_itm.Orig_w(), reg_itm.Orig_h());
			itm.Orig_repo_(reg_itm.Orig_repo());
			if (Bry_.Len_gt_0(reg_itm.Orig_redirect()))
				itm.Init_by_redirect(reg_itm.Orig_redirect());
			itm.Rslt_reg_(reg_itm.Status());
		}
		Xof_fsdb_mgr_._.Fsdb_search(this, fs_dir, page, exec_tid, itms, bin_mgr.Repo_mgr(), url_bldr);
	}
	public boolean Reg_select_itm_exists(byte[] ttl) {return reg_hash.Has(ttl);}
	public byte Bin_wkr_tid() {return Xof_bin_wkr_.Tid_fsdb_wiki;}
	public gplx.ios.Io_stream_rdr Bin_wkr_get_as_rdr(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w) {
		byte[] wiki = itm.Orig_wiki();
		byte[] ttl = itm.Lnki_ttl();
		double thumbtime = itm.Lnki_thumbtime(); 
		byte[] key = Gen_key(is_thumb, itm.Lnki_ext(), wiki, ttl, w, thumbtime);
		Fsdb_mem_itm mem_itm = (Fsdb_mem_itm)bin_hash.Get_by_bry(key);
		return mem_itm == null ? gplx.ios.Io_stream_rdr_.Null : gplx.ios.Io_stream_rdr_.mem_(mem_itm.Bin());
	}
	private byte[] Gen_key(boolean is_thumb, Xof_ext ext, byte[] wiki, byte[] ttl, int w, double thumbtime) {
		return ext.Id_is_audio_strict()
			? Key_bld_fil(wiki, ttl)					// use fil for audio b/c it doesn't have w, thumbtime
			: Key_bld_thm(wiki, ttl, w, thumbtime)		// use thm for everything else, even if thumb is not specified; [[File:A.png]] -> enwiki|A.png|-1,-1
			;
	}
	public boolean Bin_wkr_get_to_url(ListAdp temp_files, Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {
		byte[] wiki = itm.Orig_wiki();
		byte[] ttl = itm.Lnki_ttl();
		double thumbtime = itm.Lnki_thumbtime(); 
		byte[] key = Gen_key(is_thumb, itm.Lnki_ext(), wiki, ttl, w, thumbtime);
		Fsdb_mem_itm mem_itm = (Fsdb_mem_itm)bin_hash.Get_by_bry(key);
		if (mem_itm == null) return false;
		Io_mgr._.SaveFilBry(bin_url, mem_itm.Bin());
		return true;
	}
	private byte[] Key_bld_fil(byte[] wiki, byte[] ttl) {
		bin_key_bfr	.Add_byte(Byte_ascii.Ltr_f).Add_byte_pipe()
			.Add(wiki).Add_byte_pipe()
			.Add(ttl);
		return bin_key_bfr.XtoAryAndClear();
	}
	private byte[] Key_bld_thm(byte[] wiki, byte[] ttl, int w, double thumbtime) {
		bin_key_bfr	.Add_byte(Byte_ascii.Ltr_t).Add_byte_pipe()
			.Add(wiki).Add_byte_pipe()
			.Add(ttl).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe()
			.Add_int_variable(Xof_doc_thumb.X_int(thumbtime));
		;
		return bin_key_bfr.XtoAryAndClear();
	}
	public void Rls() {bin_hash.Clear(); reg_hash.Clear();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
}
interface Fsdb_mem_itm {
	byte[] Mem_key();
	byte[] Wiki();
	byte[] Ttl();
	byte[] Bin();
}
class Fsdb_xtn_thm_itm_mem extends Fsdb_xtn_thm_itm implements Fsdb_mem_itm {	public byte[] Mem_key() {return mem_key;} private byte[] mem_key;
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Bin() {return bin;} private byte[] bin;
	public void Init(byte[] mem_key, byte[] wiki, byte[] ttl, int w, int h, double thumbtime, byte[] bin) {
		this.mem_key = mem_key; this.wiki = wiki; this.ttl = ttl; this.bin = bin;
		this.Width_(w); this.Height_(h);
		this.Thumbtime_(thumbtime);
	}	
}
class Fsdb_xtn_img_itm_mem extends Fsdb_xtn_img_itm implements Fsdb_mem_itm {		public byte[] Mem_key() {return mem_key;} private byte[] mem_key;
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Bin() {return bin;} private byte[] bin;
	public void Init(byte[] mem_key, byte[] wiki, byte[] ttl, int w, int h, byte[] bin) {
		this.mem_key = mem_key; this.wiki = wiki; this.ttl = ttl; this.bin = bin;
		this.W_(w); this.H_(h);
	}	
}
class Fsdb_fil_itm_mem extends Fsdb_fil_itm implements Fsdb_mem_itm {		public byte[] Mem_key() {return mem_key;} private byte[] mem_key;
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Bin() {return bin;} private byte[] bin;
	public void Init(byte[] mem_key, byte[] wiki, byte[] ttl, byte[] bin) {
		this.mem_key = mem_key; this.wiki = wiki; this.ttl = ttl; this.bin = bin;
	}	
}
