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
import gplx.core.btries.*;
import gplx.xowa.langs.vnts.*;
class Vnt_rule_parser implements gplx.core.brys.Bry_split_wkr {
	private final    Btrie_slim_mgr vnt_trie = Btrie_slim_mgr.ci_a7();
	private final    Btrie_rv trv = new Btrie_rv();
	private Vnt_rule_undi_mgr undis; private Vnt_rule_bidi_mgr bidis;
	private int src_end; private byte[] rule_raw;
	public byte[] Raw() {return rule_raw;}
	private Vnt_log_mgr log_mgr;
	public void Init(Vnt_log_mgr log_mgr, Xol_vnt_regy vnt_regy) {
		this.log_mgr = log_mgr;
		this.vnt_trie.Clear();
		int len = vnt_regy.Len();
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = (Xol_vnt_itm)vnt_regy.Get_at(i);
			vnt_trie.Add_obj(itm.Key(), itm);
		}
	}
	public void Clear(Vnt_rule_undi_mgr undis, Vnt_rule_bidi_mgr bidis, byte[] rule_raw) {
		this.undis = undis; this.bidis = bidis;
		undis.Clear(); bidis.Clear();
		this.rule_raw = rule_raw;
	}
	public void Parse(byte[] src, int src_bgn, int src_end) {
		this.src_end = src_end;
		Bry_split_.Split(src, src_bgn, src_end, Byte_ascii.Semic, false, this);	// trim=false for "&#entity;" check below
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {	// macro=>zh-hans:text;
		int html_entity_pos = Bry_find_.Find_bwd_while_alphanum(src, itm_end);
		byte html_entity_byte =  src[html_entity_pos];
		if (html_entity_byte == Byte_ascii.Hash) html_entity_byte = src[html_entity_pos - 2];		// skip #; EX: &#123;
		if (html_entity_byte == Byte_ascii.Amp) return Bry_split_.Rv__extend;						// reject "&#entity;"; EX: "&nbsp;zh-hans;"
		if (itm_end != src_end) {
			int nxt_lang_bgn = Bry_find_.Find_fwd(src, Bry__bidi_dlm, itm_end + 1, src_end);		// look for next "=>"
			if (nxt_lang_bgn == Bry_find_.Not_found)
				nxt_lang_bgn = Bry_find_.Find_fwd_while_ws(src, itm_end + 1, src_end);				// skip any ws after end ";"; EX: "a:1; b:2"; NOTE: +1 to skip semic;
			else
				nxt_lang_bgn += 2;
			int nxt_lang_end = Bry_find_.Find_fwd(src, Byte_ascii.Colon, nxt_lang_bgn, src_end);	// get colon;
			if (nxt_lang_end != Bry_find_.Not_found) {
				nxt_lang_end = Bry_find_.Find_bwd__skip_ws(src, nxt_lang_end, src_end);				// trim
				if (vnt_trie.Match_bgn(src, nxt_lang_bgn, nxt_lang_end) == null) return Bry_split_.Rv__extend;	// reject ";not_variant"; EX: ";border" in "zh-hans:<span style='color:blue;border:1px;'>;zh-hant:"
			}
		}
		int undi_bgn = Bry_find_.Find_fwd_while_ws(src, itm_bgn, itm_end);						// skip any ws after bgn ";"; EX: " a=>b:c;"
		int undi_end = Bry_find_.Find_fwd(src, Bry__bidi_dlm, undi_bgn, itm_end);				// look for "=>"
		int lang_bgn = undi_bgn;				// default lang_bgn to undi_bgn; assumes no bidi found
		if (undi_end != Bry_find_.Not_found) {	// "=>" found; bidi exists
			lang_bgn = Bry_find_.Find_fwd_while_ws(src, undi_end + 2, itm_end);		// set lang_bgn after => and gobble up ws
			undi_end = Bry_find_.Find_bwd__skip_ws(src, undi_end, undi_bgn);		// trim ws from end of bd;
		}
		Object vnt_obj = vnt_trie.Match_at(trv, src, lang_bgn, itm_end);
		if (vnt_obj == null)
			return (itm_bgn == 0) ? Bry_split_.Rv__cancel : Bry_split_.Rv__extend;	// if 1st item; cancel rest; otherwise, extend
		int lang_end = trv.Pos();
		int text_bgn = Bry_find_.Find_fwd_while_ws(src, lang_end, itm_end); if (src[text_bgn] != Byte_ascii.Colon) return Bry_split_.Rv__extend;
		++text_bgn;
		Xol_vnt_itm vnt_itm = (Xol_vnt_itm)vnt_obj;
		byte[] vnt_key = vnt_itm.Key();
		byte[] text_bry = Bry_.Mid_w_trim(src, text_bgn, itm_end);
		if (undi_end == Bry_find_.Not_found) {
			if (log_mgr != null) log_mgr.Log_lang(vnt_itm, Vnt_log_mgr.Scope__bidi);
			bidis.Set(vnt_key, text_bry);
		}
		else {
			byte[] undi_bry = Bry_.Mid(src, undi_bgn, undi_end);
			if (itm_end - text_bgn > 0) {
				if (log_mgr != null) log_mgr.Log_lang(vnt_itm, Vnt_log_mgr.Scope__undi);
				undis.Set(vnt_key, undi_bry, text_bry);
			}
		}
		return Bry_split_.Rv__ok;
	}
	public void To_bry__dbg(Bry_bfr bfr) {
		undis.To_bry__dbg(bfr);
		if (bfr.Len_gt_0()) bfr.Add_byte_nl();
		bidis.To_bry__dbg(bfr);
	}
	private static final    byte[] Bry__bidi_dlm = Bry_.new_a7("=>");
}
