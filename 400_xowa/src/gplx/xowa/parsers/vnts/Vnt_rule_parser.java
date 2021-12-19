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
package gplx.xowa.parsers.vnts;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.BrySplitWkr;
import gplx.core.btries.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.langs.vnts.*;
class Vnt_rule_parser implements BrySplitWkr {
	private final Btrie_slim_mgr vnt_trie = Btrie_slim_mgr.ci_a7();
	private final Btrie_rv trv = new Btrie_rv();
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
			vnt_trie.AddObj(itm.Key(), itm);
		}
	}
	public void Clear(Vnt_rule_undi_mgr undis, Vnt_rule_bidi_mgr bidis, byte[] rule_raw) {
		this.undis = undis; this.bidis = bidis;
		undis.Clear(); bidis.Clear();
		this.rule_raw = rule_raw;
	}
	public void Parse(byte[] src, int src_bgn, int src_end) {
		this.src_end = src_end;
		BrySplit.Split(src, src_bgn, src_end, AsciiByte.Semic, false, this);	// trim=false for "&#entity;" check below
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {	// macro=>zh-hans:text;
		int html_entity_pos = BryFind.FindBwdWhileAlphanum(src, itm_end);
		byte html_entity_byte =  src[html_entity_pos];
		if (html_entity_byte == AsciiByte.Hash) html_entity_byte = src[html_entity_pos - 2];		// skip #; EX: &#123;
		if (html_entity_byte == AsciiByte.Amp) return BrySplit.Rv__extend;						// reject "&#entity;"; EX: "&nbsp;zh-hans;"
		if (itm_end != src_end) {
			int nxt_lang_bgn = BryFind.FindFwd(src, Bry__bidi_dlm, itm_end + 1, src_end);		// look for next "=>"
			if (nxt_lang_bgn == BryFind.NotFound)
				nxt_lang_bgn = BryFind.FindFwdWhileWs(src, itm_end + 1, src_end);				// skip any ws after end ";"; EX: "a:1; b:2"; NOTE: +1 to skip semic;
			else
				nxt_lang_bgn += 2;
			int nxt_lang_end = BryFind.FindFwd(src, AsciiByte.Colon, nxt_lang_bgn, src_end);	// get colon;
			if (nxt_lang_end != BryFind.NotFound) {
				nxt_lang_end = BryFind.FindBwdSkipWs(src, nxt_lang_end, src_end);				// trim
				if (vnt_trie.MatchBgn(src, nxt_lang_bgn, nxt_lang_end) == null) return BrySplit.Rv__extend;	// reject ";not_variant"; EX: ";border" in "zh-hans:<span style='color:blue;border:1px;'>;zh-hant:"
			}
		}
		int undi_bgn = BryFind.FindFwdWhileWs(src, itm_bgn, itm_end);						// skip any ws after bgn ";"; EX: " a=>b:c;"
		int undi_end = BryFind.FindFwd(src, Bry__bidi_dlm, undi_bgn, itm_end);				// look for "=>"
		int lang_bgn = undi_bgn;				// default lang_bgn to undi_bgn; assumes no bidi found
		if (undi_end != BryFind.NotFound) {	// "=>" found; bidi exists
			lang_bgn = BryFind.FindFwdWhileWs(src, undi_end + 2, itm_end);		// set lang_bgn after => and gobble up ws
			undi_end = BryFind.FindBwdSkipWs(src, undi_end, undi_bgn);		// trim ws from end of bd;
		}
		Object vnt_obj = vnt_trie.MatchAt(trv, src, lang_bgn, itm_end);
		if (vnt_obj == null)
			return (itm_bgn == 0) ? BrySplit.Rv__cancel : BrySplit.Rv__extend;	// if 1st item; cancel rest; otherwise, extend
		int lang_end = trv.Pos();
		int text_bgn = BryFind.FindFwdWhileWs(src, lang_end, itm_end); if (src[text_bgn] != AsciiByte.Colon) return BrySplit.Rv__extend;
		++text_bgn;
		Xol_vnt_itm vnt_itm = (Xol_vnt_itm)vnt_obj;
		byte[] vnt_key = vnt_itm.Key();
		byte[] text_bry = BryUtl.MidWithTrim(src, text_bgn, itm_end);
		if (undi_end == BryFind.NotFound) {
			if (log_mgr != null) log_mgr.Log_lang(vnt_itm, Vnt_log_mgr.Scope__bidi);
			bidis.Set(vnt_key, text_bry);
		}
		else {
			byte[] undi_bry = BryLni.Mid(src, undi_bgn, undi_end);
			if (itm_end - text_bgn > 0) {
				if (log_mgr != null) log_mgr.Log_lang(vnt_itm, Vnt_log_mgr.Scope__undi);
				undis.Set(vnt_key, undi_bry, text_bry);
			}
		}
		return BrySplit.Rv__ok;
	}
	public void To_bry__dbg(BryWtr bfr) {
		undis.To_bry__dbg(bfr);
		if (bfr.HasSome()) bfr.AddByteNl();
		bidis.To_bry__dbg(bfr);
	}
	private static final byte[] Bry__bidi_dlm = BryUtl.NewA7("=>");
}
