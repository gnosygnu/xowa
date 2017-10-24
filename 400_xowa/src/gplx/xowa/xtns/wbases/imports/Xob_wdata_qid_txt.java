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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.bldrs.*;
public class Xob_wdata_qid_txt extends Xob_wdata_qid_base {
	private Wdata_idx_bldr_qid qid_bldr;
	@Override public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_tdb_text_wdata_qid;}		
	@Override public void Qid_bgn() {qid_bldr = new Wdata_idx_bldr_qid().Ctor(this, bldr, wiki, dump_fil_len);}
	@Override public void Qid_add(byte[] wiki_key, int ns_id, byte[] ttl, byte[] qid) {
		qid_bldr.Add(String_.new_u8(wiki_key), Int_.To_str_pad_bgn_zero(ns_id, 3), ttl, qid);
	}
	@Override public void Qid_end() {
		qid_bldr.Flush();
		qid_bldr.Make();
	}
}
class Wdata_idx_bldr_qid extends Wdata_idx_mgr_base {
	public Wdata_idx_bldr_qid Ctor(Xob_wdata_qid_base wkr, Xob_bldr bldr, Xowe_wiki wiki, int dump_fil_len) {super.Ctor(wkr, bldr, wiki, dump_fil_len); return this;}
	public void Add(String wiki_key, String ns_num_str, byte[] ttl, byte[] qid) {
		Wdata_idx_wtr wtr = Get_or_new(wiki_key, ns_num_str);
		wtr.Write(ttl, qid);
	}	
	private Wdata_idx_wtr Get_or_new(String wiki_key, String ns_num_str) {
		String wtr_key = wiki_key + "|" + ns_num_str;
		Object rv = hash.Get_by(wtr_key);
		if (rv == null) {
			Wdata_idx_wtr wtr = Wdata_idx_wtr.new_qid_(wiki, wiki_key, ns_num_str, dump_fil_len);
			hash.Add(wtr_key, wtr);
			return wtr;
		}
		return (Wdata_idx_wtr)rv;
	}
}
