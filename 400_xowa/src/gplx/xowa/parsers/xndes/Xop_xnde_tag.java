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
import gplx.core.primitives.*;
public class Xop_xnde_tag {
	public Xop_xnde_tag(int id, String name_str) {	// NOTE: should only be used by Xop_xnde_tag_
		this.id = id;
		this.name_bry = Bry_.new_a7(name_str);
		this.name_str = name_str;
		this.name_len = name_bry.length;
		this.xtn_bgn_tag = Bry_.Add(Byte_ascii.Angle_bgn_bry, name_bry);
		this.xtn_end_tag = Bry_.Add(Xop_xnde_tag_.Bry__end_tag_bgn, name_bry);	// always force endtag; needed for <noinclude>
		this.xtn_end_tag_tmp = new byte[xtn_end_tag.length]; Array_.Copy(xtn_end_tag, xtn_end_tag_tmp);
	}
	public int Id() {return id;} private final    int id;
	public byte[] Name_bry() {return name_bry;} private final    byte[] name_bry;
	public String Name_str() {return name_str;} private final    String name_str;
	public int Name_len() {return name_len;} private final    int name_len;
	public byte[] Xtn_bgn_tag() {return xtn_bgn_tag;} private final    byte[] xtn_bgn_tag;
	public byte[] Xtn_end_tag() {return xtn_end_tag;} private final    byte[] xtn_end_tag;
	public byte[] Xtn_end_tag_tmp() {return xtn_end_tag_tmp;} private final    byte[] xtn_end_tag_tmp;
	public boolean Xtn() {return xtn;} public Xop_xnde_tag Xtn_() {xtn = true; return this;} private boolean xtn;
	public boolean Xtn_mw() {return xtn_mw;} public Xop_xnde_tag Xtn_mw_() {xtn_mw = true; xtn = true; return this;} private boolean xtn_mw;	// NOTE: Xtn_mw_() marks both xtn and xtn_mw as true
	public int Bgn_mode() {return bgn_nde_mode;} private int bgn_nde_mode = Xop_xnde_tag_.Bgn_mode__normal;
	public Xop_xnde_tag Bgn_mode__inline_() {bgn_nde_mode = Xop_xnde_tag_.Bgn_mode__inline; return this;}
	public int End_mode() {return end_nde_mode;} private int end_nde_mode = Xop_xnde_tag_.End_mode__normal;
	public Xop_xnde_tag End_mode__inline_() {end_nde_mode = Xop_xnde_tag_.End_mode__inline; return this;}
	public Xop_xnde_tag End_mode__escape_() {end_nde_mode = Xop_xnde_tag_.End_mode__escape; return this;}
	public boolean Single_only() {return single_only;} public Xop_xnde_tag Single_only_() {single_only = true; return this;} private boolean single_only;
	public boolean Tbl_sub() {return tbl_sub;} public Xop_xnde_tag Tbl_sub_() {tbl_sub = true; return this;} private boolean tbl_sub;
	public boolean Restricted() {return restricted;} public Xop_xnde_tag Restricted_() {restricted = true; return this;} private boolean restricted;
	public boolean No_inline() {return no_inline;} public Xop_xnde_tag No_inline_() {no_inline = true; return this;} private boolean no_inline;
	public boolean Inline_by_backslash() {return inline_by_backslash;} public Xop_xnde_tag Inline_by_backslash_() {inline_by_backslash = true; return this;} private boolean inline_by_backslash;
	public boolean Section() {return section;} public Xop_xnde_tag Section_() {section = true; return this;} private boolean section;
	public boolean Repeat_ends() {return repeat_ends;} public Xop_xnde_tag Repeat_ends_() {repeat_ends = true; return this;} private boolean repeat_ends;
	public boolean Repeat_mids() {return repeat_mids;} public Xop_xnde_tag Repeat_mids_() {repeat_mids = true; return this;} private boolean repeat_mids;
	public boolean Empty_ignored() {return empty_ignored;} public Xop_xnde_tag Empty_ignored_() {empty_ignored = true; return this;} private boolean empty_ignored;
	public boolean Single_only_html() {return single_only_html;} public Xop_xnde_tag Single_only_html_() {single_only_html = true; return this;} private boolean single_only_html;
	public boolean Raw() {return raw;} public Xop_xnde_tag Raw_() {raw = true; return this;} private boolean raw;
	public static final byte Block_noop = 0, Block_bgn = 1, Block_end = 2;
	public byte Block_open() {return block_open;} private byte block_open = Block_noop;
	public byte Block_close() {return block_close;} private byte block_close = Block_noop;
	public Xop_xnde_tag Block_open_bgn_() {block_open = Block_bgn; return this;} public Xop_xnde_tag Block_open_end_() {block_open = Block_end; return this;}
	public Xop_xnde_tag Block_close_bgn_() {block_close = Block_bgn; return this;} public Xop_xnde_tag Block_close_end_() {block_close = Block_end; return this;}
	public boolean Xtn_auto_close() {return xtn_auto_close;} public Xop_xnde_tag Xtn_auto_close_() {xtn_auto_close = true; return this;} private boolean xtn_auto_close;
	public boolean Ignore_empty() {return ignore_empty;} public Xop_xnde_tag Ignore_empty_() {ignore_empty = true; return this;} private boolean ignore_empty;
	public boolean Xtn_skips_template_args() {return xtn_skips_template_args;} public Xop_xnde_tag Xtn_skips_template_args_() {xtn_skips_template_args = true; return this;} private boolean xtn_skips_template_args;
	public Ordered_hash Langs() {return langs;} private Ordered_hash langs; private Int_obj_ref langs_key;
	public Xop_xnde_tag Langs_(int lang_code, String name) {
		if (langs == null) {
			langs = Ordered_hash_.New();
			langs_key = Int_obj_ref.New_neg1();
		}
		Xop_xnde_tag_lang lang_tag = new Xop_xnde_tag_lang(lang_code, name);
		langs.Add(lang_tag.Lang_code(), lang_tag);
		return this;
	}
	public Xop_xnde_tag_lang Langs_get(gplx.xowa.langs.cases.Xol_case_mgr case_mgr, int cur_lang, byte[] src, int bgn, int end) {
		if (langs == null) return Xop_xnde_tag_lang.Instance;						// no langs defined; always return true; EX:<b>
		if (Bry_.Eq(src, bgn, end, name_bry)) return Xop_xnde_tag_lang.Instance;	// canonical name (name_bry) is valid in all langs; EX: <section> and cur_lang=de
		synchronized (langs) {
			langs_key.Val_(cur_lang);
		}
		Xop_xnde_tag_lang lang = (Xop_xnde_tag_lang)langs.Get_by(langs_key);
		if (lang == null) return null;										// cur tag is a lang tag, but no tag for this lang; EX: "<trecho>" and cur_lang=de
		return Bry_.Eq_ci_a7(lang.Name_bry(), src, bgn, end)
			? lang
			: null;
	}
}
