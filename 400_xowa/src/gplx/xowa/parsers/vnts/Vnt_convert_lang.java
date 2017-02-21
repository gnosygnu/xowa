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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
import gplx.xowa.parsers.htmls.*;
public class Vnt_convert_lang {
	private final    Xol_convert_mgr convert_mgr; private final    Xol_vnt_regy vnt_regy;
	private final    Vnt_convert_rule converter_rule; private final    Vnt_html_doc_wkr html_convert_wkr; private final    Mwh_doc_parser doc_parser = new Mwh_doc_parser();
	private final    Bry_bfr bfr = Bry_bfr_.New_w_size(255), tmp_frame_bfr = Bry_bfr_.New_w_size(255), tmp_convert_bfr = Bry_bfr_.New_w_size(255);
	private byte[] src; private int src_len; private int pos;
	private Vnt_log_mgr log_mgr; private int tag_bgn, tag_end;
	public Vnt_convert_lang(Xol_convert_mgr convert_mgr, Xol_vnt_regy vnt_regy) {
		this.convert_mgr = convert_mgr; this.vnt_regy = vnt_regy;
		this.html_convert_wkr = new Vnt_html_doc_wkr(convert_mgr, vnt_regy);
		this.converter_rule = new Vnt_convert_rule(this, vnt_regy, log_mgr);
	}
	public byte[] Converted_title() {return converted_title;} private byte[] converted_title;
	public void Log__init(Db_conn conn) {
		log_mgr = new Vnt_log_mgr();
		log_mgr.Init_by_db(conn, vnt_regy);
	}
	public byte[] Parse_page(Xol_vnt_itm vnt_itm, int page_id, byte[] src) {// REF.MW:/languages/LanguageConverter.php!recursiveConvertTopLevel
		if (log_mgr != null) log_mgr.Init_by_page(page_id);
		this.converted_title = null;
		return Parse_bry(vnt_itm, src);
	}
	public byte[] Parse_bry(Xol_vnt_itm vnt_itm, byte[] src) {
		boolean convert_needed = true; // false for sr lang; SEE:LanguageSr.php !$this->guessVariant(src, vnt);
		this.pos = 0;
		this.src = src; this.src_len = src.length;
		while (pos < src_len) {
			int curly_bgn = Bry_find_.Find_fwd(src, Bry__curly_bgn, pos, src_len);
			if (curly_bgn == Bry_find_.Not_found) {						// No more markup, append final segment
				Add_output(vnt_itm, convert_needed, src, pos, src_len);
				return bfr.To_bry_and_clear();
			}
			boolean inside_tag = Is_inside_tag(pos, curly_bgn);
			if (inside_tag) {
				Add_output(vnt_itm, convert_needed, src, pos, tag_bgn);		// Markup found; append segment
				Auto_convert(bfr, vnt_itm, src, tag_bgn, tag_end);
				pos = tag_end;
			}
			else {
				Add_output(vnt_itm, convert_needed, src, pos, curly_bgn);	// Markup found; append segment
				pos = curly_bgn;											// Advance position
				bfr.Add(Parse_recursive(tmp_frame_bfr, vnt_itm, 1));		// Do recursive conversion
			}
		}
		return bfr.To_bry_and_clear();
	}
	private boolean Is_inside_tag(int prev_pos, int curly_bgn) {
		if (	curly_bgn == 0			// -{ starts at BOS; EX: "-{A}-"
			||	curly_bgn == prev_pos	// -{ starts after last pair; EX: "-{A}--{B}-"
			) return false;
		int cur = curly_bgn - 1;
		tag_bgn = tag_end = -1;
		boolean loop = true;
		while (loop) {	// scan bwd for <
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Angle_bgn:	tag_bgn = cur; loop = false; break;
				case Byte_ascii.Angle_end:	return false;	// ">" found; "-{}-" not inside tag
				default:					--cur; break;
			}
			if (cur == prev_pos - 1) break;
		}
		if (tag_bgn == -1) return false;	// no "<" found;
		loop = true;
		cur = curly_bgn + 1;	// TODO_OLD: resume at }-
		while (loop) {	// scan fwd for >
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Angle_bgn:	return false;	// "<" found; "-{}-" not inside tag
				case Byte_ascii.Angle_end:	tag_end = cur + 1; return true;
				default:					++cur; break;
			}
			if (cur == src_len) break;
		}
		return false;						// no ">" foud
	}
	private byte[] Parse_recursive(Bry_bfr frame_bfr, Xol_vnt_itm vnt_itm, int depth) {
		pos += 2;	// skip "-{"
		boolean warning_done = false;
		boolean frame_bfr_dirty = false;
		int bgn_pos = pos;
		while (pos < src_len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src,pos, src_len);
			if (o == null) {	// char;
				++pos;
				continue;
			}
			switch (((Byte_obj_val)o).Val()) {
				case Tid__curly_bgn:
					frame_bfr.Add_mid(src, bgn_pos, pos);	// add everything from bgn of frame to cur pos; EX: "a" in "-{a-{b}-c}-"
					frame_bfr_dirty = true;
					if (depth >= max_depth) {
						pos += 2;	// skip "-{"
						frame_bfr.Add(Bry__curly_bgn);
						if (!warning_done) {
							frame_bfr.Add_str_a7("<span class=\"error\">max depth");
							// wfMessage('language-converter-depth-warning')->numParams($this->mMaxDepth)->inContentLanguage()->text()
							frame_bfr.Add_str_a7("</span>");
							warning_done = true;
						}
						continue;
					}
					frame_bfr.Add(Parse_recursive(Bry_bfr_.New_w_size(16), vnt_itm, depth + 1)); // Recursively parse another rule
					bgn_pos = pos;
					break;
				case Tid__curly_end:
					if (frame_bfr_dirty) {	// recursive; use frame_bfr
						frame_bfr.Add_mid(src, bgn_pos, pos);	// add everything from bgn of frame to cur pos; EX: "a" in "-{a-{b}-c}-"
						byte[] frame_bry = frame_bfr.To_bry_and_clear();
						converter_rule.Parse(vnt_itm, frame_bry, 0, frame_bry.length);
					}
					else					// not recursive
						converter_rule.Parse(vnt_itm, src, bgn_pos, pos);
					Apply_manual_conv(converter_rule);
					pos += 2;
					return converter_rule.Display();
				default: throw Err_.new_unhandled(-1);		// never happens
			}
		}
		Auto_convert(frame_bfr, vnt_itm, src, bgn_pos, src_len);	// Unclosed rule
		pos = src_len;
		return Bry_.Add(Bry__curly_bgn, frame_bfr.To_bry_and_clear());
	}
	private void Add_output(Xol_vnt_itm vnt_itm, boolean convert_needed, byte[] src, int bgn, int end) {
		if (end - bgn == 0) return;
		if (convert_needed) {
			Auto_convert(bfr, vnt_itm, src, bgn, end);
		}
		else
			bfr.Add_mid(src, bgn, end);
	}
	public byte[] Auto_convert(Xol_vnt_itm vnt_itm, byte[] src) {
		Auto_convert(tmp_convert_bfr, vnt_itm, src, 0, src.length);
		return tmp_convert_bfr.To_bry_and_clear();
	}
	private void Auto_convert(Bry_bfr bfr, Xol_vnt_itm vnt_itm, byte[] src, int bgn, int end) {
		html_convert_wkr.Init(bfr, vnt_itm);
		doc_parser.Parse(html_convert_wkr, src, bgn, end);
	}
	private void Apply_manual_conv(Vnt_convert_rule rule) {
		this.converted_title = rule.Title();
		byte action = rule.Action();
		Vnt_rule_undi_mgr cnv_tbl = rule.Cnv_tbl();
		int len = cnv_tbl.Len();
		for (int i = 0; i < len; ++i) {
			Vnt_rule_undi_grp grp = cnv_tbl.Get_at(i);
			byte[] grp_key = grp.Vnt();
			Xol_vnt_itm vnt_itm = vnt_regy.Get_by(grp_key); if (vnt_itm == null) continue;
			int grp_len = grp.Len();
			Xol_convert_wkr wkr = convert_mgr.Converter_ary()[vnt_itm.Idx()];
			for (int j = 0; j < grp_len; ++j) {
				Vnt_rule_undi_itm itm = grp.Get_at(j);
				if		(action == Byte_ascii.Plus) {
					wkr.Add(itm.Src(), itm.Trg());
				}
				else if (action == Byte_ascii.Dash)
					wkr.Del(itm.Src());
			}
		}
	}
	private static final byte Tid__curly_bgn = 1, Tid__curly_end = 2;
	private static final    byte[] Bry__curly_bgn = Bry_.new_a7("-{"), Bry__curly_end = Bry_.new_a7("}-");
	private static final    Btrie_fast_mgr trie = Btrie_fast_mgr.cs()
	.Add_bry_byte(Bry__curly_bgn, Tid__curly_bgn)
	.Add_bry_byte(Bry__curly_end, Tid__curly_end);
	private static final int max_depth = 32; 
}
