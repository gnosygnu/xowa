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
package gplx.xowa.xtns.wbases.stores; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.lists.caches.*;
import gplx.core.logs.*;
public interface Wbase_doc_cache {
	void Add(byte[] qid, Wdata_doc doc);
	Wdata_doc Get_or_null(byte[] qid);
	void Clear();
	void Term();
}
class Wbase_doc_cache__null implements Wbase_doc_cache {
	public void Add(byte[] qid, Wdata_doc doc) {}
	public Wdata_doc Get_or_null(byte[] qid) {return null;}
	public void Clear() {}
	public void Term() {}
}
class Wbase_doc_cache__hash implements Wbase_doc_cache {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Add(byte[] qid, Wdata_doc doc) {hash.Add(qid, doc);}
	public Wdata_doc Get_or_null(byte[] qid) {return (Wdata_doc)hash.Get_by_bry(qid);}
	public void Clear() {hash.Clear();}
	public void Term() {hash.Clear();}
}
class Wbase_doc_cache__mru implements Wbase_doc_cache {
	private final Mru_cache_mgr cache;
	public Wbase_doc_cache__mru(long cache_max, long compress_size, long used_weight) {
		this.cache = Mru_cache_mgr.New_by_mb_secs(Gfo_log_wtr.New_dflt("wbase", "cache_mru_{0}.csv"), cache_max, compress_size, used_weight);
	}
	public void Add(byte[] qid, Wdata_doc doc) {cache.Add(String_.new_a7(qid), doc, doc.Jdoc_size());}
	public Wdata_doc Get_or_null(byte[] qid) {return (Wdata_doc)cache.Get_or_null(String_.new_a7(qid));}
	public void Clear() {}
	public void Term() {
		cache.Flush();
		cache.Print();
	}
}
