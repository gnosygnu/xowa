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
package gplx.xowa.addons.wikis.fulltexts.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.htmls.*;
public class Xofulltext_extractor implements Mwh_doc_wkr {
	private final    Mwh_doc_parser doc_parser = new Mwh_doc_parser();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Btrie_slim_mgr punct_trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	public Xofulltext_extractor() {
		punct_trie.Add_many_str(Xofulltext_punct_.Punct_bgn_ary);
		punct_trie.Add_many_str("/", ")", "]", ">", "�");
	}
	public Hash_adp_bry    Nde_regy() {return nde_regy;} private final    Hash_adp_bry nde_regy = Mwh_doc_wkr_.Nde_regy__mw();
	public void            On_nde_head_bgn (Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end) {}
	public void            On_nde_head_end (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline) {}
	public void            On_nde_tail_end (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void            On_comment_end  (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void            On_entity_end   (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {}
	public void            On_atr_each     (Mwh_atr_parser mgr, byte[] src, int nde_tid, boolean valid, boolean repeated, boolean key_exists, byte[] key_bry, byte[] val_bry_manual, int[] itm_ary, int itm_idx) {}
	public void            On_txt_end      (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end) {
		// trim flanking ws
		itm_bgn = Bry_find_.Find_fwd_while_ws(src, itm_bgn, itm_end);
		itm_end = Bry_find_.Find_bwd__skip_ws(src, itm_end, itm_bgn);

		// add ws between entries
		if (bfr.Len_gt_0()) { // ignore if 1st entry
			// identify punct at start of String
			int punct_end = itm_bgn;
			while (true) {
				// exit if at end
				if (punct_end >= itm_end) break;

				// check if punct
				Object o = punct_trie.Match_at(trv, src, punct_end, itm_end);

				// b is not punct; exit
				if (o == null) {
					break;
				}
				// b is punct; keep going
				else {
					punct_end++;
				}					
			}

			// only add space if no punct at start; prevents building strings like "a <i>b</i>. c d" -> "a b . c d"
			if (itm_bgn == punct_end)
				bfr.Add_byte_space();
		}

		// add to bfr
		bfr.Add_mid(src, itm_bgn, itm_end);
	}
	public byte[] Extract(byte[] src) {
		doc_parser.Parse(this, src, 0, src.length);
		return bfr.To_bry_and_clear();
	}
}
