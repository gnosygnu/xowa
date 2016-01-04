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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*; import gplx.langs.htmls.clses.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.xtns.gallery.*;
public class Xoh_gly_grp_data implements Gfh_class_parser_wkr, Gfh_style_wkr {	// 
	private final List_adp itms_list = List_adp_.new_();
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Gly_tid() {return gly_tid;} private int gly_tid;
	public int Gly_w() {return gly_w;} private int gly_w;
	public int Xtra_atr_bgn() {return xtra_atr_bgn;} private int xtra_atr_bgn;
	public int Xtra_atr_end() {return xtra_atr_end;} private int xtra_atr_end;
	public boolean Xtra_atr_exists() {return xtra_atr_end > xtra_atr_bgn;}
	public int Xtra_cls_bgn() {return xtra_cls_bgn;} private int xtra_cls_bgn;
	public int Xtra_cls_end() {return xtra_cls_end;} private int xtra_cls_end;
	public boolean Xtra_cls_exists() {return xtra_cls_end > xtra_cls_bgn;}
	public int Xtra_style_bgn() {return xtra_style_bgn;} private int xtra_style_bgn;
	public int Xtra_style_end() {return xtra_style_end;} private int xtra_style_end;
	public boolean Xtra_style_exists() {return xtra_style_end > xtra_style_bgn;}
	public int Itms__len() {return itms_list.Count();}
	public Xoh_gly_itm_data Itms__get_at(int i) {return (Xoh_gly_itm_data)itms_list.Get_at(i);}
	private void Clear() {
		this.gly_tid = Byte_.Max_value_127;
		this.gly_w = 0;
		this.xtra_atr_bgn = xtra_atr_end = xtra_cls_bgn = xtra_cls_end = xtra_style_bgn = xtra_style_end = -1;
		itms_list.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Gfh_tag_rdr tag_rdr, Gfh_tag ul_head) {
		this.Clear();
		this.src_bgn = ul_head.Src_bgn();
		if (!Parse_cls  (src, tag_rdr, ul_head)) return false;
		if (!Parse_style(src, tag_rdr, ul_head)) return false;
		Parse_ul_atrs(src, tag_rdr, ul_head);
		Gfh_tag li_head = null;
		while (true) {
			li_head = tag_rdr.Tag__peek_fwd_head();
			if (li_head.Name_id() != Gfh_tag_.Id__li) break;	// no more <li>; break;
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
			if		(Bry_.Eq(hatr.Key(), Gfh_atr_.Bry__class)) {}
			else if (Bry_.Eq(hatr.Key(), Gfh_atr_.Bry__style)) {}
			else {
				if (xtra_atr_bgn == -1) this.xtra_atr_bgn = hatr.Atr_bgn();
				this.xtra_atr_end = hatr.Atr_end();
			}
		}
	}
	public boolean On_cls(byte[] src, int atr_idx, int atr_bgn, int atr_end, int val_bgn, int val_end) {
		if		(Bry_.Match(src, val_bgn, val_end, Atr__cls__gallery)) {}	// ignore "gallery"
		else if (Bry_.Match(src, val_bgn, val_bgn + Atr__cls__mw_gallery.length, Atr__cls__mw_gallery)) {	// starts with 'mw-gallery-'
			int tid_bgn = val_bgn + Atr__cls__mw_gallery.length;
			this.gly_tid = Gallery_mgr_base_.Hash.Get_as_byte_or(src, tid_bgn, val_end, Byte_.Max_value_127);
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
	public boolean On_atr(byte[] src, int atr_idx, int atr_bgn, int atr_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		if		(Bry_.Match(src, key_bgn, key_end, Style__max_width))	// 'max-width'
			gly_w = Bry_.To_int_or__lax(src, val_bgn, val_end, 0);
		else if	(Bry_.Match(src, key_bgn, key_end, Style___width)) {}	// '_width'
		else	{
			if (this.xtra_style_bgn == -1) {
				this.xtra_style_bgn = key_bgn;
				this.xtra_style_end = atr_end;
			}
		}
		return true;
	}
	public static final byte[] Atr__cls__gallery = Bry_.new_a7("gallery");
	private static final byte[] Atr__cls__mw_gallery = Bry_.new_a7("mw-gallery-"), Atr__cls__gallerybox = Bry_.new_a7("gallerybox")
	, Style__max_width = Bry_.new_a7("max-width"), Style___width = Bry_.new_a7("_width")
	;
}
