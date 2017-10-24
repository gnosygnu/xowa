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
public class Wbase_pid_mgr {	// EX: "en|road_map" -> 15 ("Property:P15")
	private final    Wdata_wiki_mgr wbase_mgr;
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Wbase_pid_mgr(Wdata_wiki_mgr wbase_mgr) {this.wbase_mgr = wbase_mgr;}
	public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled;
	public void Clear() {
		synchronized (hash) { // LOCK:app-level
			hash.Clear();
		}
	}
	public void Add(byte[] pid_key, int pid_id) {
		synchronized (hash) { // LOCK:app-level
			if (!hash.Has(pid_key))
				hash.Add_bry_int(pid_key, pid_id);
		}
	}
	public int Get_or_null(byte[] lang_key, byte[] pid_name) {
		if (!enabled) return Wdata_wiki_mgr.Pid_null;
		byte[] pid_key = Bry_.Add(lang_key, Byte_ascii.Pipe_bry, pid_name);		// EX: "en|road_map"
		int rv = hash.Get_as_int_or(pid_key, -1);
		if (rv == -1) {
			synchronized (hash) {	// LOCK:app-level; DATE:2016-12-03
				rv = wbase_mgr.Wdata_wiki().Db_mgr().Load_mgr().Load_pid(lang_key, pid_name); if (rv == Wdata_wiki_mgr.Pid_null) return rv;
				Add(pid_key, rv);
			}
		}
		return rv;
	}
	public static int To_int_or_null(byte[] pid_ttl) {	// EX: "p123" -> "123"
		int len = pid_ttl.length; if (len == 0) return Wdata_wiki_mgr.Pid_null;
		byte ltr_p = pid_ttl[0];	// make sure 1st char is "P" or "p"
		switch (ltr_p) {
			case Byte_ascii.Ltr_P:
			case Byte_ascii.Ltr_p:	break;
			default:				return Wdata_wiki_mgr.Pid_null;
		}
		return Bry_.To_int_or(pid_ttl, 1, len, Wdata_wiki_mgr.Pid_null);
	}
}
