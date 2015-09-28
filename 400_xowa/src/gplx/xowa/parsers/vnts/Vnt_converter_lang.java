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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
import gplx.xowa.parsers.htmls.*;
public class Vnt_converter_lang {
	private final Bry_bfr bfr = Bry_bfr.new_();
	private int max_depth = 32; 
	private byte[] src; private int src_len;
	private boolean convert_needed;
	private int pos;
	private final Vnt_converter_rule converter_rule = new Vnt_converter_rule();
	private Xol_convert_mgr convert_mgr; private Xol_vnt_regy vnt_regy; // private Xol_vnt_mgr vnt_mgr; // private Xol_vnt_itm vnt_itm;
	private final Mwh_doc_parser doc_parser = new Mwh_doc_parser();
	private final Vnt_html_doc_wkr html_convert_wkr;
	private final Bry_bfr tmp_convert_bfr = Bry_bfr.new_();
	public Vnt_converter_lang(Xol_convert_mgr convert_mgr, Xol_vnt_regy vnt_regy) {
		this.html_convert_wkr = new Vnt_html_doc_wkr(convert_mgr);
		this.convert_mgr = convert_mgr; this.vnt_regy = vnt_regy;
	}
	public byte[] Converted_title() {return converted_title;} private byte[] converted_title;
	public byte[] Parse(Xol_vnt_itm vnt_itm, byte[] src) {// REF.MW:/languages/LanguageConverter.php!recursiveConvertTopLevel
		this.converted_title = null;
		converter_rule.Init(this, vnt_regy, vnt_itm);
		this.converted_title = null;
		int markup_count = 0;
		this.pos = 0;
		this.convert_needed = true; // false for sr lang; SEE:LanguageSr.php !$this->guessVariant(src, vnt);
		this.src = src; this.src_len = src.length;
		while (pos < src_len) {
			int curly_bgn = Bry_find_.Find_fwd(src, Bry__curly_bgn, pos, src_len);
			if (curly_bgn == Bry_find_.Not_found) {		// No more markup, append final segment
				if (markup_count == 0) return src;			// no markups found; just return original
				Add_output(vnt_itm, convert_needed, src, pos, src_len);
				return bfr.Xto_bry_and_clear();
			}
			Add_output(vnt_itm, convert_needed, src, pos, curly_bgn); // Markup found; append segment
			pos = curly_bgn;								// Advance position
			++markup_count;
			Parse_recursive(vnt_itm, 1);		// Do recursive conversion
		}
		return bfr.Xto_bry_and_clear();
	}
	private void Parse_recursive(Xol_vnt_itm vnt_itm, int depth) {
		pos += 2;	// skip "-{"
		boolean warning_done = false;
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
					if (depth >= max_depth) {
						bfr.Add(Bry__curly_bgn);
						if (!warning_done) {
							bfr.Add_str("<span class=\"error\">");
							// wfMessage('language-converter-depth-warning')->numParams($this->mMaxDepth)->inContentLanguage()->text()
							bfr.Add_str("</span>");
							warning_done = true;
						}
						pos += 2;	// skip "-{"
						continue;
					}
					bgn_pos = pos;
					Parse_recursive(vnt_itm, depth + 1); // Recursively parse another rule
					break;
				case Tid__curly_end:
					converter_rule.Parse(src, bgn_pos, pos);
					Apply_manual_conv(converter_rule);
					bfr.Add(converter_rule.Display());
					pos += 2;
					return;
				default: throw Err_.new_unhandled(-1);		// never happens
			}
		}
		if (pos < src_len) { // Unclosed rule
			bfr.Add(Bry__curly_bgn);
			Auto_convert(bfr, vnt_itm, src, pos, src_len);
		}
		pos = src_len;
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
		return tmp_convert_bfr.Xto_bry_and_clear();
	}
	private void Auto_convert(Bry_bfr bfr, Xol_vnt_itm vnt_itm, byte[] src, int bgn, int end) {
		html_convert_wkr.Init(bfr, vnt_itm.Idx());
		doc_parser.Parse(html_convert_wkr, src, bgn, end);
	}
	private void Apply_manual_conv(Vnt_converter_rule rule) {
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
	private static final byte[] Bry__curly_bgn = Bry_.new_a7("-{"), Bry__curly_end = Bry_.new_a7("}-");
	private static final Btrie_fast_mgr trie = Btrie_fast_mgr.cs()
	.Add_bry_byte(Bry__curly_bgn, Tid__curly_bgn)
	.Add_bry_byte(Bry__curly_end, Tid__curly_end);
}
