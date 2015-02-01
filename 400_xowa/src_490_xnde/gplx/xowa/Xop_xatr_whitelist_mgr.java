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
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xop_xatr_whitelist_mgr {
	public boolean Chk(int tag_id, byte[] src, Xop_xatr_itm xatr) {
		byte[] key_bry = xatr.Key_bry();
		byte[] chk_bry; int chk_bgn, chk_end;
		if (key_bry == null) {
			chk_bry = src;
			chk_bgn = xatr.Key_bgn();
			chk_end = xatr.Key_end();
			if (chk_end - chk_bgn == 0) return true;	// no key; nothing to whitelist; return true
		}
		else {						// key_bry specified manually; EX: "id<nowiki>=1" has a manual key_bry of "id"
			chk_bry = key_bry;
			chk_bgn = 0;
			chk_end = key_bry.length;
		}
		Object o = key_trie.Match_bgn(chk_bry, chk_bgn, chk_end);
		if (o == null) return false;// unknown atr_key; EX: <b unknown=1/>
		Xop_xatr_whitelist_itm itm = (Xop_xatr_whitelist_itm)o;
		byte itm_key_tid = itm.Key_tid();
		xatr.Key_tid_(itm_key_tid);
		boolean rv	=	itm.Tags()[tag_id] == 1									// is atr allowed for tag
				&& (itm.Exact() ? key_trie.Match_pos() == chk_end : true)	// if exact, check for exact; else always true
			;
		switch (itm_key_tid) {
			case Xop_xatr_itm.Key_tid_style:
				if (!Scrub_style(xatr, src)) return false;
				break;
			case Xop_xatr_itm.Key_tid_role:
				if (!Bry_.Eq(Val_role_presentation, xatr.Val_as_bry(src))) return false; // MW: For now we only support role="presentation"; DATE:2014-04-05
				break;
		}
		return rv;
	}
	public Xop_xatr_whitelist_mgr Ini() {	// REF.MW:Sanitizer.php|setupAttributeWhitelist
		Ini_grp("common"		, null		, "id", "class", "lang", "dir", "title", "style", "role");
		Ini_grp("block" 		, "common"	, "align");
		Ini_grp("tablealign"	, null		, "align", "char", "charoff", "valign");
		Ini_grp("tablecell"		, null		, "abbr", "axis", "headers", "scope", "rowspan", "colspan", "nowrap", "width", "height", "bgcolor");		

		Ini_nde(Xop_xnde_tag_.Tid_div        , "block");
		Ini_nde(Xop_xnde_tag_.Tid_center     , "common");
		Ini_nde(Xop_xnde_tag_.Tid_span       , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h1         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h2         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h3         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h4         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h5         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_h6         , "block");
		Ini_nde(Xop_xnde_tag_.Tid_em         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_strong     , "common");
		Ini_nde(Xop_xnde_tag_.Tid_cite       , "common");
		Ini_nde(Xop_xnde_tag_.Tid_dfn        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_code       , "common");
		Ini_nde(Xop_xnde_tag_.Tid_samp       , "common");
		Ini_nde(Xop_xnde_tag_.Tid_kbd        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_var        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_abbr       , "common");
		Ini_nde(Xop_xnde_tag_.Tid_blockquote , "common", "cite");
		Ini_nde(Xop_xnde_tag_.Tid_sub        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_sup        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_p          , "block");
		Ini_nde(Xop_xnde_tag_.Tid_br         , "id", "class", "title", "style", "clear");
		Ini_nde(Xop_xnde_tag_.Tid_pre        , "common", "width");
		Ini_nde(Xop_xnde_tag_.Tid_ins        , "common", "cite", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid_del        , "common", "cite", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid_ul         , "common", "type");
		Ini_nde(Xop_xnde_tag_.Tid_ol         , "common", "type", "start");
		Ini_nde(Xop_xnde_tag_.Tid_li         , "common", "type", "value");
		Ini_nde(Xop_xnde_tag_.Tid_dl         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_dd         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_dt         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_table      , "common", "summary", "width", "border", "frame", "rules", "cellspacing", "cellpadding", "align", "bgcolor");
		Ini_nde(Xop_xnde_tag_.Tid_caption    , "common", "align");
		Ini_nde(Xop_xnde_tag_.Tid_thead      , "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_tfoot      , "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_tbody      , "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_colgroup   , "common", "span", "width", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_col        , "common", "span", "width", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_tr         , "common", "bgcolor", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_td         , "common", "tablecell", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_th         , "common", "tablecell", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid_a          , "common", "href", "rel", "rev"); 
		Ini_nde(Xop_xnde_tag_.Tid_img        , "common", "alt", "src", "width", "height");
		Ini_nde(Xop_xnde_tag_.Tid_tt         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_b          , "common");
		Ini_nde(Xop_xnde_tag_.Tid_i          , "common");
		Ini_nde(Xop_xnde_tag_.Tid_big        , "common");
		Ini_nde(Xop_xnde_tag_.Tid_small      , "common");
		Ini_nde(Xop_xnde_tag_.Tid_strike     , "common");
		Ini_nde(Xop_xnde_tag_.Tid_s          , "common");
		Ini_nde(Xop_xnde_tag_.Tid_u          , "common");
		Ini_nde(Xop_xnde_tag_.Tid_font       , "common", "size", "color", "face");
		Ini_nde(Xop_xnde_tag_.Tid_hr         , "common", "noshade", "size", "width");
		Ini_nde(Xop_xnde_tag_.Tid_ruby       , "common");
		Ini_nde(Xop_xnde_tag_.Tid_rb         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_rt         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_rp         , "common");
		Ini_nde(Xop_xnde_tag_.Tid_math       , "class", "style", "id", "title");
		Ini_nde(Xop_xnde_tag_.Tid_time		 , "class", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid_bdi		 , "common");
		Ini_nde(Xop_xnde_tag_.Tid_data		 , "common", "value");
		Ini_nde(Xop_xnde_tag_.Tid_mark		 , "common");
		Ini_all_loose("data");
		return this;
	}
	private Hash_adp_bry grp_hash = Hash_adp_bry.cs_();
	private void Ini_grp(String key_str, String base_grp, String... cur_itms) {
		byte[][] itms = Bry_.Ary(cur_itms);
		if (base_grp != null)
			itms = Bry_.Ary_add(itms, (byte[][])grp_hash.Get_by_bry(Bry_.new_ascii_(base_grp)));
		byte[] key = Bry_.new_ascii_(key_str);
		grp_hash.Add_bry_obj(key, itms);
	}
	private void Ini_nde(int tag_tid, String... key_strs) {
		ListAdp keys = ListAdp_.new_();
		int len = key_strs.length;
		for (int i = 0; i < len; i++) {
			byte[] key = Bry_.new_ascii_(key_strs[i]);
			Object grp_obj = grp_hash.Get_by_bry(key);	// is the key a grp? EX: "common"
			if (grp_obj == null)
				keys.Add(key);
			else {
				byte[][] grp_keys = (byte[][])grp_obj;
				int grp_keys_len = grp_keys.length;
				for (int j = 0; j < grp_keys_len; j++)
					keys.Add(grp_keys[j]);
			}
		}
		len = keys.Count();
		for (int i = 0; i < len; i++) {
			byte[] key_bry = (byte[])keys.FetchAt(i);
			Xop_xatr_whitelist_itm itm = (Xop_xatr_whitelist_itm)key_trie.Match_exact(key_bry, 0, key_bry.length);
			if (itm == null) {
				itm = Ini_key_trie_add(key_bry, true);
				key_trie.Add_obj(key_bry, itm);
			}
			itm.Tags()[tag_tid] = 1;
		}
	}
	private void Ini_all_loose(String key_str) {
		byte[] key_bry = Bry_.new_ascii_(key_str);
		Ini_key_trie_add(key_bry, false);
		Xop_xatr_whitelist_itm itm = Ini_key_trie_add(key_bry, false);
		key_trie.Add_obj(key_bry, itm);
		int len = Xop_xnde_tag_._MaxLen;
		for (int i = 0; i < len; i++)
			itm.Tags()[i] = 1;
	}
	private Xop_xatr_whitelist_itm  Ini_key_trie_add(byte[] key, boolean exact) {
		Object key_tid_obj = tid_hash.Fetch(key);
		byte key_tid = key_tid_obj == null ? Xop_xatr_itm.Key_tid_generic : ((Byte_obj_val)key_tid_obj).Val();
		Xop_xatr_whitelist_itm rv = new Xop_xatr_whitelist_itm(key, key_tid, exact);
		key_trie.Add_obj(key, rv);
		return rv;
	}
	private Hash_adp_bry tid_hash = Hash_adp_bry.ci_ascii_()
	.Add_str_byte("id", Xop_xatr_itm.Key_tid_id)
	.Add_str_byte("style", Xop_xatr_itm.Key_tid_style)
	.Add_str_byte("role", Xop_xatr_itm.Key_tid_role)
	;
	private Btrie_slim_mgr key_trie = Btrie_slim_mgr.ci_ascii_();	// NOTE:ci.ascii:HTML.node_name
	public boolean Scrub_style(Xop_xatr_itm xatr, byte[] raw) { // REF:Sanitizer.php|checkCss; '! expression | filter\s*: | accelerator\s*: | url\s*\( !ix'; NOTE: this seems to affect MS IE only; DATE:2013-04-01
		byte[] val_bry = xatr.Val_bry();
		byte[] chk_bry; int chk_bgn, chk_end;
		if (val_bry == null) {
			chk_bry = raw;
			chk_bgn = xatr.Val_bgn();
			chk_end = xatr.Val_end();
			if (chk_end - chk_bgn == 0) return true;	// no val; nothing to scrub; return true
		}
		else {		// val_bry specified manually; EX: "id=<nowiki>1</nowiki>" has a manual val_bry of "1"
			chk_bry = val_bry;
			chk_bgn = 0;
			chk_end = val_bry.length;
		}
		int pos = chk_bgn;
		while (pos < chk_end) {
			Object o = style_trie.Match_bgn(chk_bry, pos, chk_end);
			if (o == null)
				++pos;
			else {
				pos = style_trie.Match_pos();
				byte style_tid = ((Byte_obj_val)o).Val();
				switch (style_tid) {
					case Style_expression:
						xatr.Val_bry_(Bry_.Empty);
						return false;
					case Style_filter: 
					case Style_accelerator:
						if (Next_non_ws_byte(chk_bry, pos, chk_end) == Byte_ascii.Colon) {
							xatr.Val_bry_(Bry_.Empty);
							return false;						
						}
						break;
					case Style_url:
					case Style_urls:
					case Style_image:
					case Style_image_set:
						if (Next_non_ws_byte(chk_bry, pos, chk_end) == Byte_ascii.Paren_bgn) {
							xatr.Val_bry_(Bry_.Empty);
							return false;
						}
						break;
				}
			}
		}
		return true;
	}
	byte Next_non_ws_byte(byte[] raw, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
				case Byte_ascii.CarriageReturn:
				case Byte_ascii.NewLine:					
					break;
				default:
					return b;
			}
		}
		return Byte_ascii.Nil; 
	}
	static final byte Style_expression = 0, Style_filter = 1, Style_accelerator = 2, Style_url = 3, Style_urls = 4, Style_comment = 5, Style_image = 6, Style_image_set = 7;
	private static Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_ascii_()	// NOTE:ci.ascii:Javascript
	.Add_str_byte("expression"	, Style_expression)
	.Add_str_byte("filter"		, Style_filter)
	.Add_str_byte("accelerator"	, Style_accelerator)
	.Add_str_byte("url"			, Style_url)
	.Add_str_byte("urls"		, Style_urls)
	.Add_str_byte("image"		, Style_image)
	.Add_str_byte("image-set"	, Style_image_set)
	.Add_str_byte("/*"			, Style_comment)
	;
	private static final byte[] Val_role_presentation = Bry_.new_ascii_("presentation");
}
class Xop_xatr_whitelist_itm {
	public Xop_xatr_whitelist_itm(byte[] key, byte key_tid, boolean exact) {this.key = key; this.key_tid = key_tid; this.exact = exact;}
	public byte[] Key()		{return key;} private byte[] key;
	public byte Key_tid()	{return key_tid;} private byte key_tid;
	public boolean Exact()		{return exact;} private boolean exact;
	public byte[] Tags()	{return tags;} private byte[] tags = new byte[Xop_xnde_tag_._MaxLen];
}
