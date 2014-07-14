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
package gplx.xowa.xtns.imageMap; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
interface Imap_itm {
	byte Itm_tid();
	int Itm_idx();
	int Src_bgn();
	int Src_end();
}
class Imap_itm_ {
	public static final byte Tid_invalid = 0, Tid_img = 1, Tid_desc = 2, Tid_comment = 3, Tid_shape_dflt = 4, Tid_shape_rect = 5, Tid_shape_circle = 6, Tid_shape_poly = 7;
	public static final byte[] 
	  Tid_name_default = Bry_.new_ascii_("default")
	, Tid_name_rect = Bry_.new_ascii_("rect")
	, Tid_name_circle = Bry_.new_ascii_("circle")
	, Tid_name_poly = Bry_.new_ascii_("poly")
	;
	public static byte[] Xto_str(byte v) {
		switch (v) {
			case Tid_shape_dflt		: return Tid_name_default;
			case Tid_shape_rect		: return Tid_name_rect;
			case Tid_shape_circle	: return Tid_name_circle;
			case Tid_shape_poly		: return Tid_name_poly;
			default					: throw Err_.unhandled(v);
		}
	}
}
abstract class Imap_itm_base implements Imap_itm {
	public abstract byte Itm_tid();
	public void Ctor(int itm_idx, int src_bgn, int src_end) {this.itm_idx = itm_idx; this.src_bgn = src_bgn; this.src_end = src_end;}
	public int Itm_idx() {return itm_idx;} private int itm_idx;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
}
class Imap_itm_img extends Imap_itm_base {
	public Imap_itm_img(int itm_idx, int src_bgn, int src_end, Xop_tkn_itm img_link) {
		this.Ctor(itm_idx, src_bgn, src_end);
		this.img_link = img_link;
	}
	public Xop_tkn_itm Img_link() {return img_link;} private Xop_tkn_itm img_link;
	@Override public byte Itm_tid() {return Imap_itm_.Tid_img;}
}
class Imap_itm_desc extends Imap_itm_base {
	public Imap_itm_desc(int itm_idx, int src_bgn, int src_end) {this.Ctor(itm_idx, src_bgn, src_end);}
	@Override public byte Itm_tid() {return Imap_itm_.Tid_desc;}
}
//	class Imap_itm_comment : Imap_itm_base {
//		public override byte Itm_tid() {return Imap_itm_.Tid_comment;}
//	}
class Imap_itm_shape extends Imap_itm_base {
	public Imap_itm_shape(int itm_idx, int src_bgn, int src_end, byte shape_tid, Double_obj_val[] shape_pts) {
		this.Ctor(itm_idx, src_bgn, src_end);
		this.shape_tid = shape_tid;
		this.shape_pts = shape_pts;
	}
	@Override public byte Itm_tid() {return shape_tid;} private byte shape_tid;
	public Double_obj_val[] Shape_pts() {return shape_pts;} private Double_obj_val[] shape_pts;
	public Xop_tkn_itm Shape_link() {return shape_link;} private Xop_tkn_itm shape_link;
	public byte[] Shape_link_href() {return shape_link_href;} private byte[] shape_link_href;
	public byte[] Shape_link_caption() {return shape_link_caption;} private byte[] shape_link_caption;
	public void Shape_link_(Xoa_app app, Xow_wiki wiki, byte[] src, Xop_tkn_itm tkn) {
		this.shape_link = tkn;
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_lnki: {
				Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)tkn;
				shape_link_href = app.Href_parser().Build_to_bry(wiki, lnki_tkn.Ttl());
				shape_link_caption = Bry_.Mid(src, lnki_tkn.Src_bgn(), lnki_tkn.Src_end());
				break;
			}
			case Xop_tkn_itm_.Tid_lnke: {
//					gplx.xowa.parsers.lnkes.Xop_lnke_tkn lnke_tkn = (gplx.xowa.parsers.lnkes.Xop_lnke_tkn)tkn;
//					shape_link_href = lnke_tkn.
//					shape_link_caption = Bry_.Mid(src, lnki_tkn.Src_bgn(), lnki_tkn.Src_end());
				break;
			}
		}
 		}
	public static final byte Tid_default = 0, Tid_rect = 4, Tid_circle = 3, Tid_poly = 5;
        public static final Imap_itm_shape Shape_dflt = new Imap_itm_shape(-1, -1, -1, Imap_itm_.Tid_shape_dflt, null);
}
