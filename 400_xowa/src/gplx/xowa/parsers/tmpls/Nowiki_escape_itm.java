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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.langs.htmls.entitys.*; import gplx.xowa.parsers.amps.*;
public class Nowiki_escape_itm {
	public Nowiki_escape_itm(byte[] src, byte[] trg) {this.src = src; this.trg = trg; this.src_adj = src.length - 1;}
	private int src_adj;
	public byte[] Src() {return src;} private byte[] src;
	public byte[] Trg() {return trg;} private byte[] trg;
	public static boolean Escape(Bry_bfr tmp_bfr, byte[] src, int bgn, int end) {// <nowiki> works by escaping all wtxt symbols so that wtxt parser does not hook into any of them
		boolean dirty = false;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			Object o = trie.Match_bgn_w_byte(b, src, i, end);
			if (o == null) {
				if (dirty)
					tmp_bfr.Add_byte(b);
			}
			else {
				if (!dirty) {
					tmp_bfr.Add_mid(src, bgn, i);
					dirty = true;
				}
				Nowiki_escape_itm itm = (Nowiki_escape_itm)o;
				tmp_bfr.Add(itm.Trg());
				i += itm.src_adj;
			}
		}
		return dirty;
	}

	private static final    Btrie_slim_mgr trie = New_trie();
	private static Btrie_slim_mgr New_trie() {
		byte[] pre_bry = new byte[] {Byte_ascii.Nl, Byte_ascii.Space};	// NOTE: must go before New_trie
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs();
		New_trie_itm(rv, Byte_ascii.Lt_bry				, Gfh_entity_trie.Str__xowa_lt);
		New_trie_itm(rv, Byte_ascii.Brack_bgn_bry		, Gfh_entity_trie.Str__xowa_brack_bgn);
		New_trie_itm(rv, Byte_ascii.Brack_end_bry		, Gfh_entity_trie.Str__xowa_brack_end);// PAGE:en.w: Tall_poppy_syndrome DATE:2014-07-23
		New_trie_itm(rv, Byte_ascii.Pipe_bry			, Gfh_entity_trie.Str__xowa_pipe);
		New_trie_itm(rv, Byte_ascii.Apos_bry			, Gfh_entity_trie.Str__xowa_apos);		// NOTE: for backward compatibility, use &apos; note that amp_wkr will turn &apos; -> &#39 but &#39 -> '; DATE:2014-07-03
		New_trie_itm(rv, Byte_ascii.Colon_bry			, Gfh_entity_trie.Str__xowa_colon);
		New_trie_itm(rv, Byte_ascii.Underline_bry		, Gfh_entity_trie.Str__xowa_underline);
		New_trie_itm(rv, Byte_ascii.Star_bry			, Gfh_entity_trie.Str__xowa_asterisk);
		New_trie_itm(rv, Byte_ascii.Dash_bry			, Gfh_entity_trie.Str__xowa_dash);		// needed to handle "|<nowiki>-</nowiki>"; PAGE:de.w:Liste_von_Vereinen_und_Vereinigungen_von_Gl�ubigen_(r�misch-katholische_Kirche) DATE:2015-01-08
		New_trie_itm(rv, Byte_ascii.Space_bry			, Gfh_entity_trie.Str__xowa_space);
		New_trie_itm(rv, Byte_ascii.Nl_bry				, Gfh_entity_trie.Str__xowa_nl);
		New_trie_itm(rv, pre_bry						, pre_bry);
		return rv;
	}		
	private static void New_trie_itm(Btrie_slim_mgr rv, byte[] src, String trg) {New_trie_itm(rv, src, Bry_.new_u8(trg));}
	private static void New_trie_itm(Btrie_slim_mgr rv, byte[] src, byte[] trg) {
		Nowiki_escape_itm itm = new Nowiki_escape_itm(src, trg);
		rv.Add_obj(src, itm);			
	}
}
