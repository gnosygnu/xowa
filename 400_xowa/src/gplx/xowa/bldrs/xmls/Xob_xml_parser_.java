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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.btries.*;
public class Xob_xml_parser_ {
	public static Btrie_fast_mgr trie_() {
		Btrie_fast_mgr rv = Btrie_fast_mgr.cs();
		trie_add(rv, Bry_page_bgn, Id_page_bgn); trie_add(rv, Bry_page_bgn_frag, Id_page_bgn_frag); trie_add(rv, Bry_page_end, Id_page_end);
		trie_add(rv, Bry_id_bgn, Id_id_bgn); trie_add(rv, Bry_id_bgn_frag, Id_id_bgn_frag); trie_add(rv, Bry_id_end, Id_id_end);
		trie_add(rv, Bry_title_bgn, Id_title_bgn); trie_add(rv, Bry_title_bgn_frag, Id_title_bgn_frag); trie_add(rv, Bry_title_end, Id_title_end);
		trie_add(rv, Bry_timestamp_bgn, Id_timestamp_bgn); trie_add(rv, Bry_timestamp_bgn_frag, Id_timestamp_bgn_frag); trie_add(rv, Bry_timestamp_end, Id_timestamp_end);
		trie_add(rv, Bry_text_bgn, Id_text_bgn); trie_add(rv, Bry_text_bgn_frag, Id_text_bgn_frag); trie_add(rv, Bry_text_end, Id_text_end);
		trie_add(rv, Bry_amp, Id_amp, Byte_ascii.Amp); trie_add(rv, Bry_quot, Id_quot, Byte_ascii.Quote); trie_add(rv, Bry_gt, Id_gt, Byte_ascii.Gt); trie_add(rv, Bry_lt, Id_lt, Byte_ascii.Lt);
		trie_add(rv, Bry_tab, Id_tab, Bry_tab_ent); trie_add(rv, Bry_cr_nl, Id_cr_nl, Byte_ascii.Nl); trie_add(rv, Bry_cr, Id_cr, Byte_ascii.Nl);
		return rv;
	}
	public static final    byte[]
		  Bry_page_bgn = Bry_.new_a7("<page>"), Bry_page_bgn_frag = Bry_.new_a7("<page"), Bry_page_end = Bry_.new_a7("</page>")
		, Bry_title_bgn = Bry_.new_a7("<title>"), Bry_title_bgn_frag = Bry_.new_a7("<title"), Bry_title_end = Bry_.new_a7("</title>")
		, Bry_id_bgn = Bry_.new_a7("<id>"), Bry_id_bgn_frag = Bry_.new_a7("<id"), Bry_id_end = Bry_.new_a7("</id>")
		, Bry_redirect_bgn = Bry_.new_a7("<redirect>"), Bry_redirect_bgn_frag = Bry_.new_a7("<redirect"), Bry_redirect_end = Bry_.new_a7("</redirect>")
		, Bry_revision_bgn = Bry_.new_a7("<revision>"), Bry_revision_bgn_frag = Bry_.new_a7("<revision"), Bry_revision_end = Bry_.new_a7("</revision>")
		, Bry_timestamp_bgn = Bry_.new_a7("<timestamp>"), Bry_timestamp_bgn_frag = Bry_.new_a7("<timestamp"), Bry_timestamp_end = Bry_.new_a7("</timestamp>")
		, Bry_contributor_bgn = Bry_.new_a7("<contributor>"), Bry_contributor_bgn_frag = Bry_.new_a7("<contributor"), Bry_contributor_end = Bry_.new_a7("</contributor>")
		, Bry_username_bgn = Bry_.new_a7("<username>"), Bry_username_bgn_frag = Bry_.new_a7("<username"), Bry_username_end = Bry_.new_a7("</username>")
		, Bry_minor_bgn = Bry_.new_a7("<minor>"), Bry_minor_bgn_frag = Bry_.new_a7("<minor"), Bry_minor_end = Bry_.new_a7("</minor>")
		, Bry_comment_bgn = Bry_.new_a7("<comment>"), Bry_comment_bgn_frag = Bry_.new_a7("<comment"), Bry_comment_end = Bry_.new_a7("</comment>")
		, Bry_text_bgn = Bry_.new_a7("<text>"), Bry_text_bgn_frag = Bry_.new_a7("<text"), Bry_text_end = Bry_.new_a7("</text>")
		, Bry_amp = Bry_.new_a7("&amp;"), Bry_quot = Bry_.new_a7("&quot;"), Bry_gt = Bry_.new_a7("&gt;"), Bry_lt = Bry_.new_a7("&lt;")
		, Bry_tab_ent = Bry_.new_a7("&#09;"), Bry_tab = Bry_.new_a7("\t"), Bry_cr_nl = Bry_.new_a7("\r\n"), Bry_cr = Bry_.new_a7("\r")			
		;
	public static final byte
		  Id_page_bgn = 0, Id_page_bgn_frag = 1, Id_page_end = 2
		, Id_title_bgn = 3, Id_title_bgn_frag = 4, Id_title_end = 5
		, Id_id_bgn = 6, Id_id_bgn_frag = 7, Id_id_end = 8
		, Id_redirect_bgn = 9, Id_redirect_bgn_frag = 10, Id_redirect_end = 11
		, Id_revision_bgn = 12, Id_revision_bgn_frag = 13, Id_revision_end = 14
		, Id_timestamp_bgn = 15, Id_timestamp_bgn_frag = 16, Id_timestamp_end = 17
		, Id_contributor_bgn = 18, Id_contributor_bgn_frag = 19, Id_contributor_end = 20
		, Id_username_bgn = 21, Id_username_bgn_frag = 22, Id_username_end = 23
		, Id_minor_bgn = 24, Id_minor_bgn_frag = 25, Id_minor_end = 26
		, Id_comment_bgn = 27, Id_comment_bgn_frag = 28, Id_comment_end = 29
		, Id_text_bgn = 30, Id_text_bgn_frag = 31, Id_text_end = 32
		, Id_amp = 33, Id_quot = 34, Id_gt = 35, Id_lt = 36
		, Id_tab = 37, Id_cr_nl = 38, Id_cr = 39
		;
	private static void trie_add(Btrie_fast_mgr rv, byte[] hook, byte id)						{rv.Add(hook, new Xob_xml_parser_itm(hook, id, Byte_.Zero	, Bry_.Empty));}
	private static void trie_add(Btrie_fast_mgr rv, byte[] hook, byte id, byte subst_byte)	{rv.Add(hook, new Xob_xml_parser_itm(hook, id, subst_byte	, Bry_.Empty));}
	private static void trie_add(Btrie_fast_mgr rv, byte[] hook, byte id, byte[] subst_ary)	{rv.Add(hook, new Xob_xml_parser_itm(hook, id, Byte_.Zero	, subst_ary));}
}
class Xob_xml_parser_itm {
	public Xob_xml_parser_itm(byte[] hook, byte tid, byte subst_byte, byte[] subst_ary) {this.hook = hook; this.hook_len = hook.length; this.tid = tid; this.subst_byte = subst_byte; this.subst_ary = subst_ary;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Hook() {return hook;} private byte[] hook;
	public int Hook_len() {return hook_len;} private int hook_len;
	public byte Subst_byte() {return subst_byte;} private byte subst_byte;
	public byte[] Subst_ary() {return subst_ary;} private byte[] subst_ary;
}
