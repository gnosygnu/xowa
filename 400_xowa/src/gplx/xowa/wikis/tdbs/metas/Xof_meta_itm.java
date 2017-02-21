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
package gplx.xowa.wikis.tdbs.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.flds.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.xowa.parsers.utils.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_meta_itm {
	public Xof_meta_itm(Xof_meta_fil owner_fil, byte[] ttl) {this.owner_fil = owner_fil; this.ttl = ttl;}
	public Xof_meta_fil Owner_fil() {return owner_fil;} private Xof_meta_fil owner_fil;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Ptr_ttl() {return ptr_ttl;} public Xof_meta_itm Ptr_ttl_(byte[] v) {this.ptr_ttl = v; return this;} private byte[] ptr_ttl = Xop_redirect_mgr.Redirect_null_bry;
	public boolean Ptr_ttl_exists() {return ptr_ttl != Xop_redirect_mgr.Redirect_null_bry;}
	public boolean State_new() {return state_new;} private boolean state_new = true;
	public int Vrtl_repo() {return vrtl_repo;} private int vrtl_repo = Repo_unknown;
	public void Vrtl_repo_(int v) {
//			if (vrtl_repo == Xof_meta_itm.Repo_unknown) {	// NOTE: only update vrtl_repo when unknown; this assumes that orig and all thumbs sit in same repo (which they should)
			vrtl_repo = v;
			Dirty();
//			}
	} 
	public Xof_repo_itm Repo_itm(Xowe_wiki wiki) {
		switch (vrtl_repo) {
			case Xof_meta_itm.Repo_missing  : //return null;	// DELETE: used to return null, but this caused Redownload_missing to fail; no reason why missing shouldn't return a default repo; DATE:2013-01-26
			case Xof_meta_itm.Repo_unknown	:
			case Xof_meta_itm.Repo_same		: return wiki.Appe().File_mgr().Repo_mgr().Get_by_primary(wiki.Domain_bry());
			default							: return wiki.File_mgr().Repo_mgr().Repos_get_at(vrtl_repo).Trg();
		}
	}
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public byte Orig_exists() {return orig_exists;} private byte orig_exists = Exists_unknown;
	public void Orig_exists_(byte v) {if (this.orig_exists == v) return; this.orig_exists = v; Dirty();}
	public Xof_meta_thumb[] Thumbs() {return thumbs;} private Xof_meta_thumb[] thumbs = Xof_meta_thumb.Ary_empty;
	public boolean Thumbs_indicates_oga() {return Thumbs_get_by_w(0, 0, Xof_lnki_time.Null_as_int) != null;}	// if 0,0 exists, then assume no thumbs; needed for oga/ogg
	public boolean Thumbs_del(int w) {
		int del_idx = -1;
		int thumbs_len = thumbs.length;
		for (int i = 0; i < thumbs_len; i++) {
			Xof_meta_thumb thumb = thumbs[i];
			if (thumb.Width() == w) {del_idx = i; break;}
		}
		if (del_idx == -1) return false;
		Xof_meta_thumb[] thumbs_new = new Xof_meta_thumb[thumbs_len - 1];
		int new_idx = 0;
		for (int i = 0; i < thumbs_len; i++) {
			Xof_meta_thumb thumb = thumbs[i];
			if (thumb.Width() == w) continue;
			thumbs_new[new_idx++] = thumbs[i];
		}
		thumbs = thumbs_new;
		return true;
	}
	public Xof_meta_thumb Thumbs_get_img(int w, int w_adj)	{return Thumbs_get_by_w(w, w_adj, Xof_lnki_time.Null_as_int);}
	public Xof_meta_thumb Thumbs_get_vid(int s)				{return Thumbs_get_by_w(Xop_lnki_tkn.Width_null, 0, s);}
	Xof_meta_thumb Thumbs_get_by_w(int w, int w_adj, int seek) {
		int thumbs_len = thumbs.length;
		for (int i = 0; i < thumbs_len; i++) {
			Xof_meta_thumb thumb = thumbs[i];
			int tmp_adj = w_adj;// thumb.Width() < 50 ? 0 : w_adj;
			if (	(Xof_lnki_time.Null_y(seek) 
				&&	Int_.Between(thumb.Width(), w - tmp_adj, w + tmp_adj))
				||	(w == Xop_lnki_tkn.Width_null
					&& (Xof_lnki_time.Null_y(seek) || Int_.In(seek, thumb.Seeks()))))
				return thumb;
		}
		return null;
	}
	public Xof_meta_thumb Thumbs_get_by_h(int h, int thumbs_len) {
		for (int i = 0; i < thumbs_len; i++) {
			Xof_meta_thumb thumb = thumbs[i];
			int tmp_adj = 1;//thumb.Height() < 50 ? 0 : 1;
			if (Int_.Between(thumb.Height(), h - tmp_adj, h + tmp_adj)) return thumb;
		}
		return null;
	}
	public Xof_meta_thumb Thumbs_get_largest(int thumbs_len) {
		if (thumbs_len == 0) return null;
		int rv_idx = -1, cur_largest = -1;
		for (int i = 0; i < thumbs_len; i++) {
			Xof_meta_thumb thumb = thumbs[i];
			if (thumb.Width() > cur_largest) {
				rv_idx = i;
				cur_largest = thumb.Width();
			}
		}
		return thumbs[rv_idx];
	}
	public void Update_all(byte[] ptr_ttl, int w, int h, byte orig_exists, Xof_meta_thumb[] thumbs) {
		this.ptr_ttl = ptr_ttl; orig_w = w; orig_h = h; this.orig_exists = orig_exists; this.thumbs = thumbs; Dirty();
	}
	public void Update_orig_size(int w, int h) {this.orig_w = w; this.orig_h = h; Dirty();}
	public Xof_meta_itm Update_thumb_oga_() {
		Update_thumb_add(0, 0).Exists_n_();
		return this;
	}
	public Xof_meta_thumb Update_thumb_add(int w, int h) {	// WARNING: h may not match existing val; will be discarded; EX: 10,20 stored in file; 10,21 passed in; 21 effectively discarded
		Xof_meta_thumb thumb = Thumbs_get_img(w, 0);
		if (thumb == null) {
			int thumbs_len = thumbs.length;
			Xof_meta_thumb[] thumbs_new = new Xof_meta_thumb[thumbs_len + 1];
			for (int i = 0; i < thumbs_len; i++)
				thumbs_new[i] = thumbs[i];
			thumb = new Xof_meta_thumb().Width_(w).Height_(h).Exists_y_().State_new_();
			thumbs_new[thumbs_len] = thumb;
			thumbs = thumbs_new;
			Dirty();
		}
		return thumb;
	}
	private void Dirty() {if (owner_fil != null) owner_fil.Dirty_();}
	public void Save(Gfo_fld_wtr wtr) {
		wtr.Write_bry_escape_fld(ttl);
		byte vrtl_repo_byte = Byte_.Max_value_127;
		switch (vrtl_repo) {
			case Repo_unknown:		vrtl_repo_byte = Byte_ascii.Ltr_z; break;
			case Repo_same:			vrtl_repo_byte = Byte_ascii.Ltr_y; break;
			case Repo_missing:		vrtl_repo_byte = Byte_ascii.Ltr_x; break;
			default:				vrtl_repo_byte = (byte)(vrtl_repo + Byte_ascii.Num_0); break;
		}
		wtr.Write_byte_fld(vrtl_repo_byte);
		wtr.Write_bry_escape_fld(ptr_ttl);
		wtr.Bfr()	.Add_int_fixed(orig_exists, 1)	.Add_byte(Xof_meta_thumb_parser.Dlm_exists)
					.Add_int_variable(orig_w)		.Add_byte(Xof_meta_thumb_parser.Dlm_width)
					.Add_int_variable(orig_h)		.Add_byte_pipe();
		int thumbs_len = thumbs.length;
		Bry_bfr bfr = wtr.Bfr();
		for (int i = 0; i < thumbs_len; i++) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Xof_meta_thumb thumb = thumbs[i];
			thumb.Save(bfr);
		}
		wtr.Write_dlm_row();
		state_new = false;
	}
	public void Load_orig_(int w, int h) {this.orig_w = w; this.orig_h = h;}
	public void Load(Gfo_fld_rdr rdr, Xof_meta_thumb_parser parser) {
		ttl = rdr.Read_bry_escape();
		byte vrtl_repo_byte = rdr.Read_byte();
		switch (vrtl_repo_byte) {
			case Byte_ascii.Ltr_z:	vrtl_repo = Repo_unknown; break;
			case Byte_ascii.Ltr_y:	vrtl_repo = Repo_same; break;
			case Byte_ascii.Ltr_x:	vrtl_repo = Repo_missing; break;
			default:				vrtl_repo = (byte)(vrtl_repo_byte - Byte_ascii.Num_0); break;
		}
		ptr_ttl = rdr.Read_bry_escape();
		rdr.Move_next_simple_fld();
		Xof_meta_thumb orig_itm = parser.Parse_one(rdr.Data(), rdr.Fld_bgn(), rdr.Fld_end());
		orig_exists = orig_itm.Exists();
		orig_w		= orig_itm.Width();
		orig_h		= orig_itm.Height();
		rdr.Move_next_simple_fld();
		parser.Parse_ary(rdr.Data(), rdr.Fld_bgn(), rdr.Fld_end());
		int thumbs_len = parser.Len();
		thumbs = new Xof_meta_thumb[thumbs_len];
		for (int i = 0; i < thumbs_len; i++)
			thumbs[i] = parser.Ary()[i];
		parser.Clear();
		state_new = false;
	}
	public static final byte Exists_n = 0, Exists_y = 1, Exists_unknown = 2;
	public static final byte Repo_unknown = (byte)127, Repo_same = (byte)126, Repo_missing = (byte)125;
	public static final byte 
		  Tid_main		= 0
		, Tid_ptr		= 1
		, Tid_vrtl		= 2
		, Tid_null		= Byte_.Max_value_127
		;
	public static final byte[] Ptr_ttl_null = Bry_.Empty;
}
