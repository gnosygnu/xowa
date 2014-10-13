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
package gplx.xowa; import gplx.*;
import gplx.xowa.files.*;
public class Xop_lnki_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_lnki_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_lnki;}
	public int Ns_id() {return nsId;} public Xop_lnki_tkn_chkr Ns_id_(int v) {nsId = v; return this;} private int nsId = Int_.MinValue;
	public byte ImgType() {return imgType;} public Xop_lnki_tkn_chkr ImgType_(byte v) {imgType = v; return this;} private byte imgType = Byte_.MaxValue_127;
	public int Width() {return width;} public Xop_lnki_tkn_chkr Width_(int v) {width = v; return this;} private int width = Int_.MinValue;
	public int Height() {return height;} public Xop_lnki_tkn_chkr Height_(int v) {height = v; return this;} private int height = Int_.MinValue;
	public byte HAlign() {return hAlign;} public Xop_lnki_tkn_chkr HAlign_(byte v) {hAlign = v; return this;} private byte hAlign = Byte_.MaxValue_127;
	public byte VAlign() {return vAlign;} public Xop_lnki_tkn_chkr VAlign_(byte v) {vAlign = v; return this;} private byte vAlign = Byte_.MaxValue_127;
	public byte Border() {return border;} public Xop_lnki_tkn_chkr Border_(byte v) {border = v; return this;} private byte border = Bool_.__byte;
	public double Upright() {return upright;} public Xop_lnki_tkn_chkr Upright_(double v) {upright = v; return this;} double upright = Xop_lnki_tkn.Upright_null;
	public int Thumbtime() {return thumbtime;} public Xop_lnki_tkn_chkr Thumbtime_(int v) {thumbtime = v; return this;} int thumbtime = Xof_doc_thumb.Null_as_int;
	public int Page() {return page;} public Xop_lnki_tkn_chkr Page_(int v) {page = v; return this;} int page = Xof_doc_page.Null;
	public int Tail_bgn() {return tail_bgn;} public Xop_lnki_tkn_chkr Tail_bgn_(int v) {tail_bgn = v; return this;} private int tail_bgn = String_.Pos_neg1;
	public int Tail_end() {return tail_end;} public Xop_lnki_tkn_chkr Tail_end_(int v) {tail_end = v; return this;} private int tail_end = String_.Pos_neg1;
	public Xop_tkn_chkr_base Trg_tkn() {return trg_tkn;} public Xop_lnki_tkn_chkr Trg_tkn_(Xop_tkn_chkr_base v) {trg_tkn = v; return this;} private Xop_tkn_chkr_base trg_tkn;
	public Xop_tkn_chkr_base Caption_tkn() {return caption_tkn;} public Xop_lnki_tkn_chkr Caption_tkn_(Xop_tkn_chkr_base v) {caption_tkn = v; return this;} private Xop_tkn_chkr_base caption_tkn;
	public Xop_tkn_chkr_base Alt_tkn() {return alt_tkn;} public Xop_lnki_tkn_chkr Alt_tkn_(Xop_tkn_chkr_base v) {alt_tkn = v; return this;} private Xop_tkn_chkr_base alt_tkn;
	public Xop_tkn_chkr_base Link_tkn() {return link_tkn;} public Xop_lnki_tkn_chkr Link_tkn_(Xop_tkn_chkr_base v) {link_tkn = v; return this;} private Xop_tkn_chkr_base link_tkn;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_lnki_tkn actl = (Xop_lnki_tkn)actl_obj;
		err += mgr.Tst_val(nsId == Int_.MinValue, path, "nsId", nsId, actl.Ns_id());
		err += mgr.Tst_val(imgType == Byte_.MaxValue_127, path, "imgType", imgType, actl.Lnki_type());
		err += mgr.Tst_val(width == Int_.MinValue, path, "width", width, actl.Lnki_w());
		err += mgr.Tst_val(height == Int_.MinValue, path, "height", height, actl.Lnki_h());
		err += mgr.Tst_val(hAlign == Byte_.MaxValue_127, path, "halign", hAlign, actl.Align_h());
		err += mgr.Tst_val(vAlign == Byte_.MaxValue_127, path, "valign", vAlign, actl.Align_v());
		err += mgr.Tst_val(border == Bool_.__byte, path, "border", border, actl.Border());
		err += mgr.Tst_val(tail_bgn == String_.Pos_neg1, path, "tail_bgn", tail_bgn, actl.Tail_bgn());
		err += mgr.Tst_val(tail_end == String_.Pos_neg1, path, "tail_end", tail_end, actl.Tail_end());
		err += mgr.Tst_val(upright == Xop_lnki_tkn.Upright_null, path, "upright", upright, actl.Upright());
		err += mgr.Tst_val(thumbtime == Xof_doc_thumb.Null, path, "thumbtime", thumbtime, Xof_doc_thumb.X_int(actl.Thumbtime()));
		err += mgr.Tst_val(page == Xof_doc_page.Null, path, "page", page, actl.Page());
		if (trg_tkn != null) err += mgr.Tst_sub_obj(trg_tkn, actl.Trg_tkn(), path + "." + "trg", err);
		if (caption_tkn != null) err += mgr.Tst_sub_obj(caption_tkn, actl.Caption_tkn(), path + "." + "caption", err);
		if (alt_tkn != null) err += mgr.Tst_sub_obj(alt_tkn, actl.Alt_tkn(), path + "." + "alt", err);
		if (link_tkn != null) err += mgr.Tst_sub_obj(link_tkn, actl.Link_tkn(), path + "." + "link", err);
		return err;
	}
}
