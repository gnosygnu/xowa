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
package gplx.xowa.xtns.hieros;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.langs.htmls.Gfh_utl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
class Hiero_html_mgr {		
	private BryWtr html_bfr = BryWtr.NewAndReset(IoConsts.LenKB), content_bfr = BryWtr.NewAndReset(255), tbl_content_bfr = BryWtr.NewAndReset(IoConsts.LenKB), temp_bfr = BryWtr.NewAndReset(255);
	private boolean cartouche_opened = false;
	public static int scale = 100;
	private Hiero_prefab_mgr prefab_mgr; private Hiero_file_mgr file_mgr; private Hiero_phoneme_mgr phoneme_mgr;
	private Hiero_html_wtr wtr;
	public Hiero_html_mgr(Hiero_xtn_mgr xtn_mgr) {
		prefab_mgr = xtn_mgr.Prefab_mgr();
		file_mgr = xtn_mgr.File_mgr();
		phoneme_mgr = xtn_mgr.Phoneme_mgr();
		wtr = new Hiero_html_wtr(this, phoneme_mgr);
	}
	public void Render_blocks(BryWtr final_bfr, Xoh_wtr_ctx hctx, Hiero_block[] blocks, int scale, boolean hr_enabled) {
		wtr.Init_for_write(hctx);
		Hiero_html_mgr.scale = scale;
		tbl_content_bfr.Clear(); content_bfr.Clear(); temp_bfr.Clear();
		cartouche_opened = false;
		if (hr_enabled)
			wtr.Hr(html_bfr);
		int blocks_len = blocks.length;
		for (int i = 0; i < blocks_len; i++) {
			Hiero_block block = blocks[i];
			if (block.Len() == 1)
				Render_block_single(content_bfr, hctx, hr_enabled, block);
			else
				Render_block_many(content_bfr, hctx, hr_enabled, block);
			if (content_bfr.HasSome())
				tbl_content_bfr.AddBfrAndClear(content_bfr);	// $tbl_content = $tbl + $content;
		}
		if (tbl_content_bfr.HasSome())
			wtr.Tbl_inner(html_bfr, tbl_content_bfr);
		wtr.Tbl_outer(final_bfr, html_bfr);
	}
	private void Render_block_single(BryWtr content_bfr, Xoh_wtr_ctx hctx, boolean hr_enabled, Hiero_block block) {
		byte[] code = block.Get_at(0);		// block has only one code (hence the proc name: Render_block_single)
		byte b_0 = code[0];
		switch (b_0) {
			case AsciiByte.Bang: {			// new_line
				wtr.Tbl_eol(content_bfr);
				if (hr_enabled)
					wtr.Hr(content_bfr);
				break;
			}
			case AsciiByte.Lt: {			// cartouche bgn
				wtr.Td(content_bfr, Render_glyph(hctx, Tkn_lt));
				cartouche_opened = true;
				wtr.Cartouche_bgn(content_bfr);
				break;
			}
			case AsciiByte.Gt: {			// cartouche end
				wtr.Cartouche_end(content_bfr);
				cartouche_opened = false;
				wtr.Td(content_bfr, Render_glyph(hctx, Tkn_gt));
				break;
			}
			default: {						// glyph or '.'
				byte[] td_height = wtr.Td_height(Resize_glyph(code, cartouche_opened));
				wtr.Td(content_bfr, Render_glyph(hctx, code, td_height));
				break;
			}
		}
	}
	private void Render_block_many(BryWtr content_bfr, Xoh_wtr_ctx hctx, boolean hr_enabled, Hiero_block block) {
		temp_bfr.Clear(); // build prefab_bry: "convert all codes into '&' to test prefabs glyph"
		int block_len = block.Len();
		boolean amp = false;
		for (int i = 0; i < block_len; i++) {
			byte[] v = block.Get_at(i);
			int v_len = v.length;
			amp = false;
			if (v_len == 1) {
				switch (v[0]) {
					case AsciiByte.BrackBgn: case AsciiByte.BrackEnd:
					case AsciiByte.ParenBgn: case AsciiByte.ParenEnd:
					case AsciiByte.Star: case AsciiByte.Colon: case AsciiByte.Bang:
						amp = true;
						break;
				}
			}
			if (amp)
				temp_bfr.AddByte(AsciiByte.Amp);
			else
				temp_bfr.Add(v);
		}
		byte[] prefab_bry = temp_bfr.ToBryAndClear();
		Hiero_prefab_itm prefab_itm = prefab_mgr.Get_by_key(prefab_bry);
		if (prefab_itm != null) {
			byte[] td_height = wtr.Td_height(Resize_glyph(prefab_bry, cartouche_opened));
			wtr.Td(content_bfr, Render_glyph(hctx, prefab_bry, td_height));
		}
		else {
			int line_max = 0, total = 0, height = 0; // get block total height
			byte[] glyph = null;
			for (int i = 0; i < block_len; i++) {
				byte[] v = block.Get_at(i);
				int v_len = v.length;
				if (v_len == 1) {
					switch (v[0]) {
						case AsciiByte.Colon:
							if (height > line_max)
								line_max = height;
							total += line_max;
							line_max = 0;
							continue;
						case AsciiByte.Star:
							if (height > line_max)
								line_max = height;
							continue;
					}
				}
				Hiero_phoneme_itm phoneme_itm = phoneme_mgr.Get_by_key(v);
				if (phoneme_itm != null)
					glyph = phoneme_itm.Gardiner_code();
				else
					glyph = v;
				Hiero_file_itm file_itm = file_mgr.Get_by_key(glyph);
				if (file_itm != null)
					height = 2 + file_itm.File_h();
			}
			if (height > line_max)
				line_max = height;
			total += line_max;

			// render all glyph into the block
			for (int i = 0; i < block_len; i++) {
				byte[] v = block.Get_at(i);
				int v_len = v.length;
				if (v_len == 1) {
					switch (v[0]) {
						case AsciiByte.Colon:
							temp_bfr.AddStrA7("\n            <br/>");
							continue;
						case AsciiByte.Star:
							temp_bfr.AddByteSpace();
							continue;
					}
				}
				// resize the glyph according to the block total height
				byte[] td_height = wtr.Td_height(Resize_glyph(v, cartouche_opened, total));
				temp_bfr.Add(Render_glyph(hctx, v, td_height));
			}
			wtr.Td(content_bfr, temp_bfr.ToBryAndClear());
		}
	}
	private byte[] Render_glyph(Xoh_wtr_ctx hctx, byte[] src)					{return Render_glyph(hctx, src, BryUtl.Empty);}
	private byte[] Render_glyph(Xoh_wtr_ctx hctx, byte[] src, byte[] td_height) {
		int src_len = src.length; if (src_len == 0) return src; // bounds check
		byte byte_n = src[src_len - 1];
		byte[] img_cls = byte_n == AsciiByte.Backslash				// REF.MW:isMirrored
			? Bry_cls_mirrored										// 'class="mw-mirrored" '
			: BryUtl.Empty;
		byte[] glyph = Extract_code(src, src_len);					// trim backslashes from end; REF.MW:extractCode
		if		(BryLni.Eq(glyph, Tkn_dot_dot))						// render void block
			return wtr.Void(BoolUtl.N);
		else if (BryLni.Eq(glyph, Tkn_dot))							// render 1/2 width void block
			return wtr.Void(BoolUtl.Y);
		else if (BryLni.Eq(glyph, Tkn_lt))
			return wtr.Cartouche_img(hctx, BoolUtl.Y, glyph);
		else if (BryLni.Eq(glyph, Tkn_gt))
			return wtr.Cartouche_img(hctx, BoolUtl.N, glyph);

		Hiero_phoneme_itm phoneme_itm = phoneme_mgr.Get_by_key(glyph);
		Hiero_file_itm file_itm = null;
		byte[] glyph_esc = Gfh_utl.Escape_html_as_bry(glyph);
		if (phoneme_itm != null) {
			byte[] code = phoneme_itm.Gardiner_code();
			file_itm = file_mgr.Get_by_key(code);
			if (file_itm != null)
				return wtr.Img_phoneme(hctx, img_cls, td_height, glyph_esc, code);
			else
				return glyph_esc;
		}
		file_itm = file_mgr.Get_by_key(glyph);
		return file_itm != null
			? wtr.Img_file(hctx, img_cls, td_height, glyph_esc)
			: glyph_esc
			;
	}
	private int Resize_glyph(byte[] item, boolean cartouche_opened)	{return Resize_glyph(item, cartouche_opened, 0);}
	private int Resize_glyph(byte[] item, boolean cartouche_opened, int total) {
		item = Extract_code(item, item.length);
		Hiero_phoneme_itm phoneme_itm = phoneme_mgr.Get_by_key(item);
		byte[] glyph = phoneme_itm == null ? item : phoneme_itm.Gardiner_code();
		int margin = 2 * Image_margin;
		if (cartouche_opened)
			margin += 2 * (int)((Cartouche_width * scale) / 100);
		Hiero_file_itm file_itm = file_mgr.Get_by_key(glyph);
		if (file_itm != null) {
			int height = margin + file_itm.File_h();
			if (total > 0) {
				return total > Max_height
				? (int)(((((height * Max_height) / total) - margin) * scale) / 100)
				: (int)(((height - margin) * scale) / 100)
				;
			}
			else {
				return height > Max_height
				? (int)(((((Max_height * Max_height) / height) - margin) * scale) / 100)
				: (int)(((height - margin) * scale) / 100)
				;
			}
		}
		return (int)(((Max_height - margin) * scale) / 100);
	}
	private static byte[] Extract_code(byte[] src, int src_len) { // trim backslashes from end; REF.MW:extractCode
		return BryUtl.TrimEnd(src, AsciiByte.Backslash, src_len);
	}
	public static final int Image_margin = 1;
	public static final int Cartouche_width = 2;
	public static final int Max_height = 44;
	private static final byte[] Bry_cls_mirrored = BryUtl.NewA7("class=\"mw-mirrored\" ");
	private static final byte[]
	  Tkn_lt		= new byte[] {AsciiByte.Lt}
	, Tkn_gt		= new byte[] {AsciiByte.Gt}
	, Tkn_dot		= new byte[] {AsciiByte.Dot}
	, Tkn_dot_dot	= new byte[] {AsciiByte.Dot, AsciiByte.Dot}
	;
}
