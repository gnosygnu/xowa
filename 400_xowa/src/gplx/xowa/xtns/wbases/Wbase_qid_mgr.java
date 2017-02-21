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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.domains.*;
public class Wbase_qid_mgr {// EX: "enwiki|0|Earth" -> "Q2"
	private final    Wdata_wiki_mgr wbase_mgr;
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	private final    gplx.core.primitives.Int_obj_ref tmp_wiki_tid = gplx.core.primitives.Int_obj_ref.New_zero();	// NOTE: return value is not checked; only used for function signature
	public Wbase_qid_mgr(Wdata_wiki_mgr wbase_mgr) {
		this.wbase_mgr = wbase_mgr;
	}
	public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled;
	public void Clear() {
		synchronized (hash) { // LOCK:app-level
			hash.Clear();
		}
	}
	public void Add(Bry_bfr bfr, byte[] lang_key, int wiki_tid, byte[] ns_num, byte[] ttl, byte[] qid) {	// TEST:
		Xow_abrv_wm_.To_abrv(bfr, lang_key, tmp_wiki_tid.Val_(wiki_tid));
		byte[] qids_key = bfr.Add_byte(Byte_ascii.Pipe).Add(ns_num).Add_byte(Byte_ascii.Pipe).Add(ttl).To_bry();
		this.Add(qids_key, qid);
	}
	public byte[] Get_or_null(Xowe_wiki wiki, Xoa_ttl ttl)		{return Get_or_null(wiki.Wdata_wiki_abrv(), ttl);}
	public byte[] Get_or_null(byte[] wiki_abrv, Xoa_ttl ttl)	{
		if (!enabled) return null;
		if (Bry_.Len_eq_0(wiki_abrv)) return null;					// "other" wikis will never call wikidata
		byte[] key = Bry_.Add(wiki_abrv, Byte_ascii.Pipe_bry, ttl.Ns().Num_bry(), Byte_ascii.Pipe_bry, ttl.Page_db());	// EX: "enwiki|014|Earth"
		byte[] rv = (byte[])hash.Get_by(key);
		if (rv == null) {	// not in cache; load from db
			rv = wbase_mgr.Wdata_wiki().Db_mgr().Load_mgr().Load_qid(wiki_abrv, ttl.Ns().Num_bry(), ttl.Page_db());
			byte[] val_for_hash = rv == null ? Bry_.Empty : rv;		// JAVA: hashtable does not accept null as value; use Bry_.Empty
			this.Add(key, val_for_hash);							// NOTE: if not in db, will insert Bry_.Empty; DATE:2014-07-23
		}
		return Bry_.Len_eq_0(rv) ? null : rv;						// JAVA: convert Bry_.Empty to null which is what callers expect
	}
	private void Add(byte[] key, byte[] val) {
		synchronized (hash) { // LOCK:app-level
			if (!hash.Has(key))
				hash.Add(key, val);
		}
	}
}
