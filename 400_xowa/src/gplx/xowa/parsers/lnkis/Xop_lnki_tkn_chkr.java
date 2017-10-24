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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*; import gplx.xowa.files.*;
public class Xop_lnki_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_lnki_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_lnki;}
	public int Ns_id() {return nsId;} public Xop_lnki_tkn_chkr Ns_id_(int v) {nsId = v; return this;} private int nsId = Int_.Min_value;
	public byte ImgType() {return imgType;} public Xop_lnki_tkn_chkr ImgType_(byte v) {imgType = v; return this;} private byte imgType = Byte_.Max_value_127;
	public int Width() {return width;} public Xop_lnki_tkn_chkr Width_(int v) {width = v; return this;} private int width = Int_.Min_value;
	public int Height() {return height;} public Xop_lnki_tkn_chkr Height_(int v) {height = v; return this;} private int height = Int_.Min_value;
	public int HAlign() {return hAlign;} public Xop_lnki_tkn_chkr HAlign_(int v) {hAlign = v; return this;} private int hAlign = Byte_.Max_value_127;
	public byte VAlign() {return vAlign;} public Xop_lnki_tkn_chkr VAlign_(byte v) {vAlign = v; return this;} private byte vAlign = Byte_.Max_value_127;
	public byte Border() {return border;} public Xop_lnki_tkn_chkr Border_(byte v) {border = v; return this;} private byte border = Bool_.__byte;
	public double Upright() {return upright;} public Xop_lnki_tkn_chkr Upright_(double v) {upright = v; return this;} double upright = Xop_lnki_tkn.Upright_null;
	public int Thumbtime() {return thumbtime;} public Xop_lnki_tkn_chkr Thumbtime_(int v) {thumbtime = v; return this;} int thumbtime = Xof_lnki_time.Null_as_int;
	public int Page() {return page;} public Xop_lnki_tkn_chkr Page_(int v) {page = v; return this;} int page = Xof_lnki_page.Null;
	public int Tail_bgn() {return tail_bgn;} public Xop_lnki_tkn_chkr Tail_bgn_(int v) {tail_bgn = v; return this;} private int tail_bgn = String_.Pos_neg1;
	public int Tail_end() {return tail_end;} public Xop_lnki_tkn_chkr Tail_end_(int v) {tail_end = v; return this;} private int tail_end = String_.Pos_neg1;
	public Xop_tkn_chkr_base Trg_tkn() {return trg_tkn;} public Xop_lnki_tkn_chkr Trg_tkn_(Xop_tkn_chkr_base v) {trg_tkn = v; return this;} private Xop_tkn_chkr_base trg_tkn;
	public Xop_tkn_chkr_base Caption_tkn() {return caption_tkn;} public Xop_lnki_tkn_chkr Caption_tkn_(Xop_tkn_chkr_base v) {caption_tkn = v; return this;} private Xop_tkn_chkr_base caption_tkn;
	public Xop_tkn_chkr_base Alt_tkn() {return alt_tkn;} public Xop_lnki_tkn_chkr Alt_tkn_(Xop_tkn_chkr_base v) {alt_tkn = v; return this;} private Xop_tkn_chkr_base alt_tkn;
	public Xop_tkn_chkr_base Link_tkn() {return link_tkn;} public Xop_lnki_tkn_chkr Link_tkn_(Xop_tkn_chkr_base v) {link_tkn = v; return this;} private Xop_tkn_chkr_base link_tkn;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_lnki_tkn actl = (Xop_lnki_tkn)actl_obj;
		err += mgr.Tst_val(nsId == Int_.Min_value, path, "nsId", nsId, actl.Ns_id());
		err += mgr.Tst_val(imgType == Byte_.Max_value_127, path, "imgType", imgType, actl.Lnki_type());
		err += mgr.Tst_val(width == Int_.Min_value, path, "width", width, actl.W());
		err += mgr.Tst_val(height == Int_.Min_value, path, "height", height, actl.H());
		err += mgr.Tst_val(hAlign == Byte_.Max_value_127, path, "halign", hAlign, actl.Align_h());
		err += mgr.Tst_val(vAlign == Byte_.Max_value_127, path, "valign", vAlign, actl.Align_v());
		err += mgr.Tst_val(border == Bool_.__byte, path, "border", border, actl.Border());
		err += mgr.Tst_val(tail_bgn == String_.Pos_neg1, path, "tail_bgn", tail_bgn, actl.Tail_bgn());
		err += mgr.Tst_val(tail_end == String_.Pos_neg1, path, "tail_end", tail_end, actl.Tail_end());
		err += mgr.Tst_val(upright == Xop_lnki_tkn.Upright_null, path, "upright", upright, actl.Upright());
		err += mgr.Tst_val(thumbtime == Xof_lnki_time.Null, path, "thumbtime", thumbtime, Xof_lnki_time.X_int(actl.Time()));
		err += mgr.Tst_val(page == Xof_lnki_page.Null, path, "page", page, actl.Page());
		if (trg_tkn != null) err += mgr.Tst_sub_obj(trg_tkn, actl.Trg_tkn(), path + "." + "trg", err);
		if (caption_tkn != null) err += mgr.Tst_sub_obj(caption_tkn, actl.Caption_tkn(), path + "." + "caption", err);
		if (alt_tkn != null) err += mgr.Tst_sub_obj(alt_tkn, actl.Alt_tkn(), path + "." + "alt", err);
		if (link_tkn != null) err += mgr.Tst_sub_obj(link_tkn, actl.Link_tkn(), path + "." + "link", err);
		return err;
	}
}
