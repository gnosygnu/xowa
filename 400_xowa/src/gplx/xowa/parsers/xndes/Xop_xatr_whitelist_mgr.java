/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.xndes;

import gplx.Bry_;
import gplx.Byte_ascii;
import gplx.Hash_adp_bry;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.primitives.Byte_obj_val;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_;

public class Xop_xatr_whitelist_mgr {
	private final Hash_adp_bry grp_hash = Hash_adp_bry.cs();
	private final Btrie_rv trv = new Btrie_rv();
	public boolean Chk(int tag_id, Mwh_atr_itm xatr) {
		byte[] src = xatr.Src();
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
				// 2020-08-17|ISSUE#:785|TOMBSTONE: originally put in for vertical whitespace issues in en.w:Supreme_Court_of_the_United_States, but could not reproduce
				//	if (!Bry_.Eq(Val_role_presentation, xatr.Val_as_bry()))
				//		return false; // MW: For now we only support role="presentation"; DATE:2014-04-05
				break;
		}
		return rv;
	}

	// REF.MW:Sanitizer.php|setupAttributeWhitelist
	public Xop_xatr_whitelist_mgr Ini() {
		// REF.MW: https://github.com/wikimedia/mediawiki/blob/master/includes/parser/Sanitizer.php#L1767
		Ini_grp("common", null,
			// HTML
			"id",
			"class",
			"style",
			"lang",
			"dir",
			"title",

			// WAI-ARIA
			"aria-describedby",
			"aria-flowto",
			"aria-hidden",
			"aria-label",
			"aria-labelledby",
			"aria-owns",
			"role",

			// RDFa
			// These attributes are specified in section 9 of
			// https://www.w3.org/TR/2008/REC-rdfa-syntax-20081014
			"about",
			"property",
			"resource",
			"datatype",
			"type"+"of",

			// Microdata. These are specified by
			// https://html.spec.whatwg.org/multipage/microdata.html#the-microdata-model
			"itemid",
			"itemprop",
			"itemref",
			"itemscope",
			"itemtype"
		);
		Ini_grp("block" 		, "common"		, "align");
		Ini_grp("tablealign"	, null			, "align", "valign");
		Ini_grp("tablecell"		, null,
			"abbr",
			"axis",
			"headers",
			"scope",
			"rowspan",
			"colspan",
			"nowrap", // deprecated
			"width", // deprecated
			"height", // deprecated
			"bgcolor" // deprecated
		);

		// Numbers refer to sections in HTML 4.01 standard describing the element.
		// See: https://www.w3.org/TR/html4/
		// 7.5.4
		Ini_nde(Xop_xnde_tag_.Tid__div			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__center		, "common"); // deprecated
		Ini_nde(Xop_xnde_tag_.Tid__span			, "block");

		// 7.5.5
		Ini_nde(Xop_xnde_tag_.Tid__h1			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h2			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h3			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h4			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h5			, "block");
		Ini_nde(Xop_xnde_tag_.Tid__h6			, "block");

		// 7.5.6
		// address

		// 8.2.4
		Ini_nde(Xop_xnde_tag_.Tid__bdo			, "common");

		// 9.2.1
		Ini_nde(Xop_xnde_tag_.Tid__em			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__strong		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__cite			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dfn			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__code			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__samp			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__kbd			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__var			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__abbr			, "common");
		// acronym

		// 9.2.2
		Ini_nde(Xop_xnde_tag_.Tid__blockquote	, "common", "cite");
		Ini_nde(Xop_xnde_tag_.Tid__q            , "common", "cite");

		// 9.2.3
		Ini_nde(Xop_xnde_tag_.Tid__sub			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__sup			, "common");

		// 9.3.1
		Ini_nde(Xop_xnde_tag_.Tid__p			, "block");

		// 9.3.2
		Ini_nde(Xop_xnde_tag_.Tid__br			, "common", "clear");

		// https://www.w3.org/TR/html5/text-level-semantics.html#the-wbr-element
		Ini_nde(Xop_xnde_tag_.Tid__wbr			, "common");

		// 9.3.4
		Ini_nde(Xop_xnde_tag_.Tid__pre			, "common", "width");

		// 9.4
		Ini_nde(Xop_xnde_tag_.Tid__ins			, "common", "cite", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid__del			, "common", "cite", "datetime");

		// 10.2
		Ini_nde(Xop_xnde_tag_.Tid__ul			, "common", "type");
		Ini_nde(Xop_xnde_tag_.Tid__ol			, "common", "type", "start", "reversed");
		Ini_nde(Xop_xnde_tag_.Tid__li			, "common", "type", "value");

		// 10.3
		Ini_nde(Xop_xnde_tag_.Tid__dl			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dd			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__dt			, "common");

		// 11.2.1
		Ini_nde(Xop_xnde_tag_.Tid__table		, "common", "summary", "width", "border", "frame", "rules", "cellspacing", "cellpadding", "align", "bgcolor");

		// 11.2.2
		Ini_nde(Xop_xnde_tag_.Tid__caption		, "block");

		// 11.2.3
		Ini_nde(Xop_xnde_tag_.Tid__thead		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__tfoot		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__tbody		, "common");

		// 11.2.4
		Ini_nde(Xop_xnde_tag_.Tid__colgroup		, "common", "span");
		Ini_nde(Xop_xnde_tag_.Tid__col			, "common", "span");

		// 11.2.5
		Ini_nde(Xop_xnde_tag_.Tid__tr			, "common", "bgcolor", "tablealign");

		// 11.2.6
		Ini_nde(Xop_xnde_tag_.Tid__td			, "common", "tablecell", "tablealign");
		Ini_nde(Xop_xnde_tag_.Tid__th			, "common", "tablecell", "tablealign");

		// 12.2
		// NOTE: <a> is not allowed directly, but the attrib
		// whitelist is used from the Parser Object
		Ini_nde(Xop_xnde_tag_.Tid__a			, "common", "href", "rel", "rev"); // # rel/rev esp. for RDFa

		// 13.2
		// Not usually allowed, but may be used for extension-style hooks
		// such as <math> when it is rasterized, or if $wgAllowImageTag is
		// true
		Ini_nde(Xop_xnde_tag_.Tid__img			, "common", "alt", "src", "width", "height", "srcset");
		// Attributes for A/V tags added in T163583 / T133673
		Ini_nde(Xop_xnde_tag_.Tid__audio		, "common", "controls", "preload", "width", "height");
		Ini_nde(Xop_xnde_tag_.Tid__video		, "common", "poster", "controls", "preload", "width", "height");
		Ini_nde(Xop_xnde_tag_.Tid__source		, "common", "type", "src");
		Ini_nde(Xop_xnde_tag_.Tid__track		, "common", "type", "src", "srclang", "kind", "label");

		// 15.2.1
		Ini_nde(Xop_xnde_tag_.Tid__tt			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__b			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__i			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__big			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__small		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__strike		, "common");
		Ini_nde(Xop_xnde_tag_.Tid__s			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__u			, "common");

		// 15.2.2
		Ini_nde(Xop_xnde_tag_.Tid__font			, "common", "size", "color", "face");
		// basefont

		// 15.3
		Ini_nde(Xop_xnde_tag_.Tid__hr			, "common", "width");


		// HTML Ruby annotation text module, simple ruby only.
		// https://www.w3.org/TR/html5/text-level-semantics.html#the-ruby-element
		Ini_nde(Xop_xnde_tag_.Tid__ruby			, "common");
		// rbc
		Ini_nde(Xop_xnde_tag_.Tid__rb			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__rp			, "common");
		Ini_nde(Xop_xnde_tag_.Tid__rt			, "common"); // $merge( $common, [ 'rbspan' ] ),
		Ini_nde(Xop_xnde_tag_.Tid__rtc			, "common");

		// MathML root element, where used for extensions
		// 'title' may not be 100% valid here; it's XHTML
		// https://www.w3.org/TR/REC-MathML/
		Ini_nde(Xop_xnde_tag_.Tid__math			, "class", "style", "id", "title");

		// HTML 5 section 4.5
		Ini_nde(Xop_xnde_tag_.Tid__figure       , "common");
		Ini_nde(Xop_xnde_tag_.Tid__figure_inline, "common"); // T118520
		Ini_nde(Xop_xnde_tag_.Tid__figcaption   , "common");

		// HTML 5 section 4.6
		Ini_nde(Xop_xnde_tag_.Tid__bdi			, "common");

		// HTML5 elements, defined by:
		// https://html.spec.whatwg.org/multipage/semantics.html#the-data-element
		Ini_nde(Xop_xnde_tag_.Tid__data			, "common", "value");
		Ini_nde(Xop_xnde_tag_.Tid__time			, "common", "datetime");
		Ini_nde(Xop_xnde_tag_.Tid__mark			, "common");

		// meta and link are only permitted by removeHTMLtags when Microdata
		// is enabled so we don't bother adding a conditional to hide these
		// Also meta and link are only valid in WikiText as Microdata elements
		// (ie: validateTag rejects tags missing the attributes needed for Microdata)
		// So we don't bother including $common attributes that have no purpose.
		Ini_nde(Xop_xnde_tag_.Tid__meta			, "itemprop", "content");
		Ini_nde(Xop_xnde_tag_.Tid__link			, "itemprop", "href", "title");

		// NOTE: not in MW, but needed for "data-sort-type"; check if needed later; DATE:2020-03-08
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
		len = keys.Len();
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
		Object key_tid_obj = tid_hash.GetByOrNull(key);
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
	private static final Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_a7()	// NOTE:ci.ascii:Javascript
	.Add_str_byte("expression"	, Style_expression)
	.Add_str_byte("filter"		, Style_filter)
	.Add_str_byte("accelerator"	, Style_accelerator)
	.Add_str_byte("url"			, Style_url)
	.Add_str_byte("urls"		, Style_urls)
	.Add_str_byte("image"		, Style_image)
	.Add_str_byte("image-set"	, Style_image_set)
	.Add_str_byte("/*"			, Style_comment)
	;
}
class Xop_xatr_whitelist_itm {
	public Xop_xatr_whitelist_itm(byte[] key, byte key_tid, boolean exact) {this.key = key; this.key_tid = key_tid; this.exact = exact;}
	public byte[] Key()		{return key;} private byte[] key;
	public byte Key_tid()	{return key_tid;} private byte key_tid;
	public boolean Exact()		{return exact;} private boolean exact;
	public byte[] Tags()	{return tags;} private byte[] tags = new byte[Xop_xnde_tag_.Tid__len];
}
