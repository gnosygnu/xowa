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
package gplx.xowa.htmls.core.wkrs.addons.medias; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_media_data implements Xoh_data_itm, Gfh_style_wkr {
	public int				Tid()			{return Xoh_hzip_dict_.Tid__media;}
	public int				Src_bgn()		{return src_bgn;} private int src_bgn;
	public int				Src_end()		{return src_end;} private int src_end;
	public boolean			Rng_valid()		{return rng_valid;} private boolean rng_valid;
	public boolean			Is_audio()		{return is_audio;} private boolean is_audio;
	public boolean			Aud_noicon()	{return aud_noicon;} private boolean aud_noicon;
	public int				Aud_width()		{return aud_width;} private int aud_width;
	public int				Lnki_ttl_bgn()	{return lnki_ttl_bgn;} private int lnki_ttl_bgn;
	public int				Lnki_ttl_end()	{return lnki_ttl_end;} private int lnki_ttl_end;
	public Xoh_img_data		Img_data() {return img_data;} private final    Xoh_img_data img_data = new Xoh_img_data();
	public void Clear() {
		this.src_bgn = this.src_end = this.lnki_ttl_bgn = this.lnki_ttl_end = aud_width = -1;
		this.rng_valid = true;
		this.is_audio = this.aud_noicon = false;
		img_data.Clear();
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag media_div_head, Gfh_tag unused) {
		this.Clear();
		this.src_bgn = media_div_head.Src_bgn();

		// peek media_div_tail; needed b/c audio div may have 1 or 2 divs
		Gfh_tag media_div_tail = tag_rdr.Tag__peek_fwd_tail(Gfh_tag_.Id__div);
		this.src_end = media_div_tail.Src_end();

		// move div_0_head and get anch_0_head; EX: <div class='xowa_media_div'> ... <div><a>
		Gfh_tag div_0_head = tag_rdr.Tag__move_fwd_head(); if (div_0_head.Name_id() != Gfh_tag_.Id__div) return Log_failure(tag_rdr, "hzip.media:missing.div_0");
		Gfh_tag anch_0_head = tag_rdr.Tag__move_fwd_head(); if (anch_0_head.Name_id() != Gfh_tag_.Id__a) return Log_failure(tag_rdr, "hzip.media:missing.anch_0");

		// check cls; if "xowa_media_play", then audio; else video
		if (anch_0_head.Atrs__cls_eq(Xoh_anch_cls_.Bry__media_play)) {
			if (!Parse_aud(tag_rdr, src, anch_0_head)) return false;
		}
		else {
			if (!Parse_vid(hdoc_wkr, hctx, tag_rdr, src, anch_0_head)) return false;
		}

		return true;
	}
	private boolean Parse_aud(Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag anch_0_head) {
		this.is_audio = true;

		// get lnki_ttl from xowa_title; EX: xowa_title="A.png"
		Gfh_atr xowa_title_atr = anch_0_head.Atrs__get_by_or_fail(Xoh_img_data.Bry__atr__xowa_title);
		this.lnki_ttl_bgn = xowa_title_atr.Val_bgn();
		this.lnki_ttl_end = xowa_title_atr.Val_end();

		// get width from style='max-width:'
		Gfh_atr style_atr = anch_0_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__style);
		Gfh_style_parser_.Parse(src, style_atr.Val_bgn(), style_atr.Val_end(), this);	// parse for width; note width only appears if items_per_row is specified

		// move to div_0_tail
		tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);

		// check if div_1 exists (info icon)
		Gfh_tag div_1_head = tag_rdr.Tag__peek_fwd_head(Gfh_tag_.Id__div);
		if (	div_1_head.Name_id() == Gfh_tag_.Id__eos	// <div> not found
			||	div_1_head.Src_bgn() > src_end				// <div> is after </div.xowa_media_div>
			) {
			aud_noicon = true;
		}

		// move rdr to media_div_tail.end
		tag_rdr.Pos_(src_end);
		return true;
	}
	private boolean Parse_vid(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag anch_0_head) {
		this.is_audio = false;

		// get lnki_ttl from xowa_title; EX: xowa_title="A.png"
		img_data.Init_by_parse(hdoc_wkr, hctx, tag_rdr, src, anch_0_head, null);

		// move to div_0_tail
		tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);

		// check if div_1 exists (info icon)
		tag_rdr.Tag__move_fwd_head(Gfh_tag_.Id__div);
		// move rdr to media_div_tail.end
		tag_rdr.Pos_(src_end);
		return true;
	}
	public void Init_by_decode(boolean is_audio, int aud_width, boolean aud_noicon, int lnki_ttl_bgn, int lnki_ttl_end) {
		this.is_audio = is_audio; this.aud_width = aud_width; this.aud_noicon = aud_noicon;
		this.lnki_ttl_bgn = lnki_ttl_bgn; this.lnki_ttl_end = lnki_ttl_end;
	}
	public boolean On_atr(byte[] src, int atr_idx, int atr_val_bgn, int atr_val_end, int itm_bgn, int itm_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		if		(Bry_.Match(src, key_bgn, key_end, Style__max_width)) {	// 'max-width'
			if (aud_width == -1) {
				aud_width = Bry_.To_int_or__lax(src, val_bgn, val_end, -1);
				return true;
			}	// else if already set, fall-thru to below
		}
		return true;
	}

	private boolean Log_failure(Gfh_tag_rdr tag_rdr, String msg, Object... args) {
		tag_rdr.Err_wkr().Warn(msg, args);
		return false;
	}
	public static final    byte[] Hook_bry = Bry_.new_a7(" class=\"media mw-media");
	private static final    byte[] Style__max_width = Bry_.new_a7("max-width");

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_media_data rv = new Xoh_media_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
