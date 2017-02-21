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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*; import gplx.langs.htmls.clses.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.xtns.gallery.*;
public class Xoh_gly_grp_data implements Gfh_class_parser_wkr, Gfh_style_wkr {	// FUTURE:add gallerycaption
	private final    List_adp itms_list = List_adp_.New();
	public int				Src_bgn() {return src_bgn;} private int src_bgn;
	public int				Src_end() {return src_end;} private int src_end;
	public int				Gly_tid() {return gly_tid;} private int gly_tid;
	public int				Xnde_w() {return xnde_w;} private int xnde_w;
	public int				Xnde_h() {return xnde_h;} private int xnde_h;
	public int				Xnde_per_row() {return xnde_per_row;} private int xnde_per_row;
	public int				Ul_style_max_w() {return ul_style_max_w;} private int ul_style_max_w;
	public int				Ul_style_w() {return ul_style_w;} private int ul_style_w;
	public int				Xtra_atr_bgn() {return xtra_atr_bgn;} private int xtra_atr_bgn;
	public int				Xtra_atr_end() {return xtra_atr_end;} private int xtra_atr_end;
	public boolean				Xtra_atr_exists() {return xtra_atr_end > xtra_atr_bgn;}
	public int				Xtra_cls_bgn() {return xtra_cls_bgn;} private int xtra_cls_bgn;
	public int				Xtra_cls_end() {return xtra_cls_end;} private int xtra_cls_end;
	public boolean				Xtra_cls_exists() {return xtra_cls_end > xtra_cls_bgn;}
	public int				Xtra_style_bgn() {return xtra_style_bgn;} private int xtra_style_bgn;
	public int				Xtra_style_end() {return xtra_style_end;} private int xtra_style_end;
	public boolean				Xtra_style_exists() {return xtra_style_end > xtra_style_bgn;}
	public int				Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int				Capt_end() {return capt_end;} private int capt_end;
	public int				Itms__len() {return itms_list.Count();}
	public Xoh_gly_itm_data Itms__get_at(int i) {return (Xoh_gly_itm_data)itms_list.Get_at(i);}
	private void Clear() {
		this.gly_tid = Byte_.Max_value_127;
		this.ul_style_max_w = ul_style_w = 0;
		this.xtra_atr_bgn = xtra_atr_end = xtra_cls_bgn = xtra_cls_end = xtra_style_bgn = xtra_style_end = -1;
		this.xnde_per_row = xnde_w = xnde_h = capt_bgn = capt_end = -1;
		itms_list.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		this.Clear();
		this.src_bgn = ul_head.Src_bgn();
		if (!Parse_xogly(src, tag_rdr, ul_head)) return false;
		if (!Parse_cls  (src, tag_rdr, ul_head)) return false;
		if (!Parse_style(src, tag_rdr, ul_head)) return false;
		Parse_ul_atrs(src, tag_rdr, ul_head);
		Gfh_tag li_head = null;
		while (true) {
			li_head = tag_rdr.Tag__peek_fwd_head();
			if (li_head.Name_id() != Gfh_tag_.Id__li) break;	// no more <li>; break;
			// FUTURE: galleries with gallerycaption will cause gallery to write raw; instate code below, but would need to then serialize "gallerycaption"; PAGE:en.d:A DATE:2016-06-24
			if (li_head.Atrs__cls_has(Atr__cls__gallerycaption)) {// skip <li class='gallerycaption'>A</li>
				// extract caption between <li></li>
				li_head = tag_rdr.Tag__move_fwd_head();
				this.capt_bgn = li_head.Src_end();
				Gfh_tag li_tail = tag_rdr.Tag__move_fwd_tail(li_head.Name_id());
				this.capt_end = li_tail.Src_bgn();

				// move tag_rdr to next <li>
				li_head = tag_rdr.Tag__peek_fwd_head();
			}
			if (!li_head.Atrs__cls_has(Atr__cls__gallerybox)) return false;
			tag_rdr.Pos_(li_head.Src_end());
			Xoh_gly_itm_data itm_parser = new Xoh_gly_itm_data();
			if (!itm_parser.Parse1(hdoc_wkr, hctx, src, tag_rdr, li_head)) return false;
			tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__li);
			itms_list.Add(itm_parser);
		}
		Gfh_tag ul_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__ul);
		this.src_end = ul_tail.Src_end();
		hdoc_wkr.On_gly(this);
		return true;
	}
	private boolean Parse_xogly(byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		Gfh_atr atr = ul_head.Atrs__get_by_or_empty(Gallery_mgr_wtr.Bry__data_xogly);
		byte[] val = atr.Val(); int val_len = val.length;
		if (val_len == 0) return true;	// ignore missing "data-xogly"
		int pos = 0;
		for (int i = 0; i < 3; ++i) {
			int bgn = pos;
			int end = Bry_find_.Find_fwd(val, Byte_ascii.Pipe, bgn + 1, val_len);
			if (end == Bry_find_.Not_found) end = val_len;
			int num = Bry_.To_int_or(val, bgn, end, -1);
			pos = end + 1;
			switch (i) {
				case 0: xnde_w = num; break;
				case 1: xnde_h = num; break;
				case 2: xnde_per_row = num; break;
			}
		}
		return true;
	}
	private boolean Parse_cls(byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		Gfh_atr ul_cls = ul_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__class);
		Gfh_class_parser_.Parse(src, ul_cls.Val_bgn(), ul_cls.Val_end(), this);
		if (gly_tid == Byte_.Max_value_127) {
			tag_rdr.Err_wkr().Fail("unable to find gallery tid");
			return false;
		}
		return true;
	}
	private boolean Parse_style(byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		Gfh_atr ul_style = ul_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__style);
		Gfh_style_parser_.Parse(src, ul_style.Val_bgn(), ul_style.Val_end(), this);	// parse for width; note width only appears if items_per_row is specified
		return true;
	}
	private void Parse_ul_atrs(byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		int atrs_len = ul_head.Atrs__len();
		for (int i = 0; i < atrs_len; ++i) {
			Gfh_atr hatr = ul_head.Atrs__get_at(i);
			if (atrs_ignored.Get_by_bry(hatr.Key()) == null) {
				if (xtra_atr_bgn == -1) this.xtra_atr_bgn = hatr.Atr_bgn();
				this.xtra_atr_end = hatr.Atr_end();
			}
		}
	}
	public boolean On_cls(byte[] src, int atr_idx, int atr_bgn, int atr_end, int val_bgn, int val_end) {
            int val_pos = val_bgn - atr_bgn;
		if		(	Bry_.Match(src, val_bgn, val_end, Atr__cls__gallery)	// ignore "gallery"
				&&	val_pos == 0) {}										// only if 1st; EX:'gallery mw-traditional gallery'; PAGE:en.w:Butuan; DATE:2016-01-05
		else if (Bry_.Match(src, val_bgn, val_bgn + Atr__cls__mw_gallery.length, Atr__cls__mw_gallery)	// starts with 'mw-gallery-'
				&&	val_pos == 8) {											// occurs after "gallery "
			int tid_bgn = val_bgn + Atr__cls__mw_gallery.length;
			this.gly_tid = Gallery_mgr_base_.To_tid_or(src, tid_bgn, val_end, Byte_.Max_value_127);
			return true;
		}
		else {
			if (this.xtra_cls_bgn == -1) {
				this.xtra_cls_bgn = val_bgn;
				this.xtra_cls_end = atr_end;
			}
		}
		return true;
	}		
	public boolean On_atr(byte[] src, int atr_idx, int atr_val_bgn, int atr_val_end, int itm_bgn, int itm_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		if		(Bry_.Match(src, key_bgn, key_end, Style__max_width)) {	// 'max-width'
			if (ul_style_max_w == 0) {
				ul_style_max_w = Bry_.To_int_or__lax(src, val_bgn, val_end, 0);
				return true;
			}	// else if already set, fall-thru to below
		}
		else if	(Bry_.Match(src, key_bgn, key_end, Style___width)) {	// '_width'
			if (ul_style_w == 0) {
				ul_style_w = Bry_.To_int_or__lax(src, val_bgn, val_end, 0);
				return true;
			}	// else if already set, fall-thru to below
		}			
		if (this.xtra_style_bgn == -1) {
			this.xtra_style_bgn = itm_bgn;
			this.xtra_style_end = atr_val_end;
		}
		return true;
	}
	public static final    byte[] Atr__cls__gallery = Bry_.new_a7("gallery");
	private static final    byte[] Atr__cls__mw_gallery = Bry_.new_a7("mw-gallery-"), Atr__cls__gallerybox = Bry_.new_a7("gallerybox")		
	, Style__max_width = Bry_.new_a7("max-width"), Style___width = Bry_.new_a7("_width")
	;
	public static final    byte[] Atr__cls__gallerycaption = Bry_.new_a7("gallerycaption");
	private static final    Hash_adp_bry atrs_ignored = Make_atrs_ignored();
	private static Hash_adp_bry Make_atrs_ignored() {
		Hash_adp_bry rv = Hash_adp_bry.ci_a7();
		rv.Add_as_key_and_val(Gfh_atr_.Bry__class);
		rv.Add_as_key_and_val(Gfh_atr_.Bry__style);
		rv.Add_as_key_and_val(Gallery_mgr_wtr.Bry__data_xogly);
		return rv;
	}
}
