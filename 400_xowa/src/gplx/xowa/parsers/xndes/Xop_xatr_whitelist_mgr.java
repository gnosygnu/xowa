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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.xowa.parsers.htmls.*;
public class Xop_xatr_whitelist_mgr {
	private final    Hash_adp_bry grp_hash = Hash_adp_bry.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	public boolean Chk(int tag_id, byte[] src, Mwh_atr_itm xatr) {
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
		Object o = key_trie.Match_at(trv, chk_bry, chk_bgn, chk_end);
		if (o == null) return false;// unknown atr_key; EX: <b unknown=1/>
		Xop_xatr_whitelist_itm itm = (Xop_xatr_whitelist_itm)o;
		byte itm_key_tid = itm.Key_tid();
		xatr.Key_tid_(itm_key_tid);
		boolean rv	=	itm.Tags()[tag_id] == 1									// is atr allowed for tag
				&& (itm.Exact() ? trv.Pos() == chk_end : true)	// if exact, check for exact; else always true
			;
		switch (itm_key_tid) {
			case Mwh_atr_itm_.Key_tid__style:
				if (!Scrub_style(xatr, src)) return false;
				xatr.Val_bry_(gplx.xowa.parsers.amps.Xop_amp_mgr.Instance.Decode_as_bry(xatr.Val_as_bry()));	// NOTE: must decode style values; "&#amp;#000000" -> "#000000"; see MW:checkCss; PAGE:en.w:Boron DATE:2015-07-29
				break;
			case Mwh_atr_itm_.Key_tid__role:
				if (!Bry_.Eq(Val_role_presentation, xatr.Val_as_bry())) return false; // MW: For now we only support role="presentation"; DATE:2014-04-05
				break;
		}
		return rv;
	}
	public Xop_xatr_whitelist_mgr Ini() {	// REF.MW:Sanitizer.php|setupAttributeWhitelist
		Ini_grp("common"		, null			, "id", "class", "lang", "dir", "title", "style", "role");
		Ini_grp("block" 		, "common"		, "align");
		Ini_grp("tablealign"	, null			, "align", "char", "charoff", "valign");
		Ini_grp("tablecell"		, null			, "abbr", "axis", "headers", "scope", "rowspan", "colspan", "nowrap", "width", "height", "bgcolor");		

		Ini_nde(Xop_xnde_tag_.Tid__div			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__center		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__span			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h1			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h2			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h3			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h4			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h5			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h6			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__em			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__strong		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__cite			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dfn			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__code			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__samp			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__kbd			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__var			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__abbr			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__blockquote	, "common", "cite");
		Ini_nde(Xop_xnde_tag_.Tid__sub			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__sup			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__p			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__br			, "id", "class", "title", "style", "clear");
		Ini_nde(Xop_xnde_tag_.Tid__pre			, "common", "width");
		Ini_nde(Xop_xnde_tag_.Tid__ins			, "common", "cite", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid__del			, "common", "cite", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid__ul			, "common", "type");
		Ini_nde(Xop_xnde_tag_.Tid__ol			, "common", "type", "start");
		Ini_nde(Xop_xnde_tag_.Tid__li			, "common", "type", "value");
		Ini_nde(Xop_xnde_tag_.Tid__dl			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dd			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dt			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__table		, "common", "summary", "width", "border", "frame", "rules", "cellspacing", "cellpadding", "align", "bgcolor");
		Ini_nde(Xop_xnde_tag_.Tid__caption		, "common", "align");
		Ini_nde(Xop_xnde_tag_.Tid__thead		, "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__tfoot		, "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__tbody		, "common", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__colgroup		, "common", "span", "width", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__col			, "common", "span", "width", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__tr			, "common", "bgcolor", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__td			, "common", "tablecell", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__th			, "common", "tablecell", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__a			, "common", "href", "rel", "rev"); 
		Ini_nde(Xop_xnde_tag_.Tid__img			, "common", "alt", "src", "width", "height");
		Ini_nde(Xop_xnde_tag_.Tid__tt			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__b			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__i			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__big			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__small		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__strike		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__s			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__u			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__font			, "common", "size", "color", "face");
		Ini_nde(Xop_xnde_tag_.Tid__hr			, "common", "noshade", "size", "width");
		Ini_nde(Xop_xnde_tag_.Tid__ruby			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__rb			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__rt			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__rp			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__math			, "class", "style", "id", "title");
		Ini_nde(Xop_xnde_tag_.Tid__time			, "class", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid__bdi			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__data			, "common", "value");
		Ini_nde(Xop_xnde_tag_.Tid__mark			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__q			, "common");
		Ini_all_loose("data");
		return this;
	}
	private void Ini_grp(String key_str, String base_grp, String... cur_itms) {
		byte[][] itms = Bry_.Ary(cur_itms);
		if (base_grp != null)
			itms = Bry_.Ary_add(itms, (byte[][])grp_hash.Get_by_bry(Bry_.new_a7(base_grp)));
		byte[] key = Bry_.new_a7(key_str);
		grp_hash.Add_bry_obj(key, itms);
	}
	private void Ini_nde(int tag_tid, String... key_strs) {
		List_adp keys = List_adp_.New();
		int len = key_strs.length;
		for (int i = 0; i < len; i++) {
			byte[] key = Bry_.new_a7(key_strs[i]);
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
			byte[] key_bry = (byte[])keys.Get_at(i);
			Xop_xatr_whitelist_itm itm = (Xop_xatr_whitelist_itm)key_trie.Match_exact(key_bry, 0, key_bry.length);
			if (itm == null) {
				itm = Ini_key_trie_add(key_bry, true);
				key_trie.Add_obj(key_bry, itm);
			}
			itm.Tags()[tag_tid] = 1;
		}
	}
	private void Ini_all_loose(String key_str) {
		byte[] key_bry = Bry_.new_a7(key_str);
		Ini_key_trie_add(key_bry, false);
		Xop_xatr_whitelist_itm itm = Ini_key_trie_add(key_bry, false);
		key_trie.Add_obj(key_bry, itm);
		int len = Xop_xnde_tag_.Tid__len;
		for (int i = 0; i < len; i++)
			itm.Tags()[i] = 1;
	}
	private Xop_xatr_whitelist_itm  Ini_key_trie_add(byte[] key, boolean exact) {
		Object key_tid_obj = tid_hash.Get_by(key);
		byte key_tid = key_tid_obj == null ? Mwh_atr_itm_.Key_tid__generic : ((Byte_obj_val)key_tid_obj).Val();
		Xop_xatr_whitelist_itm rv = new Xop_xatr_whitelist_itm(key, key_tid, exact);
		key_trie.Add_obj(key, rv);
		return rv;
	}
	private Hash_adp_bry tid_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("id", Mwh_atr_itm_.Key_tid__id)
	.Add_str_byte("style", Mwh_atr_itm_.Key_tid__style)
	.Add_str_byte("role", Mwh_atr_itm_.Key_tid__role)
	;
	private Btrie_slim_mgr key_trie = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:HTML.node_name
	public boolean Scrub_style(Mwh_atr_itm xatr, byte[] raw) { // REF:Sanitizer.php|checkCss; '! expression | filter\s*: | accelerator\s*: | url\s*\( !ix'; NOTE: this seems to affect MS IE only; DATE:2013-04-01
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
			Object o = style_trie.Match_at(trv, chk_bry, pos, chk_end);
			if (o == null)
				++pos;
			else {
				pos = trv.Pos();
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
				case Byte_ascii.Cr:
				case Byte_ascii.Nl:					
					break;
				default:
					return b;
			}
		}
		return Byte_ascii.Null; 
	}
	static final byte Style_expression = 0, Style_filter = 1, Style_accelerator = 2, Style_url = 3, Style_urls = 4, Style_comment = 5, Style_image = 6, Style_image_set = 7;
	private static final    Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_a7()	// NOTE:ci.ascii:Javascript
	.Add_str_byte("expression"	, Style_expression)
	.Add_str_byte("filter"		, Style_filter)
	.Add_str_byte("accelerator"	, Style_accelerator)
	.Add_str_byte("url"			, Style_url)
	.Add_str_byte("urls"		, Style_urls)
	.Add_str_byte("image"		, Style_image)
	.Add_str_byte("image-set"	, Style_image_set)
	.Add_str_byte("/*"			, Style_comment)
	;
	private static final    byte[] Val_role_presentation = Bry_.new_a7("presentation");
}
class Xop_xatr_whitelist_itm {
	public Xop_xatr_whitelist_itm(byte[] key, byte key_tid, boolean exact) {this.key = key; this.key_tid = key_tid; this.exact = exact;}
	public byte[] Key()		{return key;} private byte[] key;
	public byte Key_tid()	{return key_tid;} private byte key_tid;
	public boolean Exact()		{return exact;} private boolean exact;
	public byte[] Tags()	{return tags;} private byte[] tags = new byte[Xop_xnde_tag_.Tid__len];
}
