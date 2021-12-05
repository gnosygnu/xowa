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
package gplx.xowa.xtns.wbases.stores; import gplx.*;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.core.*;
public class Wbase_pid_mgr {	// EX: "en|road_map" -> 15 ("Property:P15")
	private final Wdata_wiki_mgr wbase_mgr;
	private final Hash_adp_bry cache = Hash_adp_bry.cs();
	public Wbase_pid_mgr(Wdata_wiki_mgr wbase_mgr) {this.wbase_mgr = wbase_mgr;}
	public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled;
	public void Clear() {
		synchronized (cache) { // LOCK:app-level
			cache.Clear();
		}
	}
	public void Add(byte[] pid_key, int pid_id) {
		synchronized (cache) { // LOCK:app-level
			if (!cache.Has(pid_key))
				cache.Add_bry_int(pid_key, pid_id);
		}
	}
	public int Get_pid_or_neg1(byte[] lang_key, byte[] pid_name) {
		if (!enabled) return Wbase_pid.Id_null;

		// make key; EX: "en|road_map"
		byte[] pid_key = Bry_.Add(lang_key, AsciiByte.PipeBry, pid_name);

		// get from cache
		synchronized (cache)  {
			int rv = cache.Get_as_int_or(pid_key, -1);
			if (rv == -1) {
				// get from db
				rv = wbase_mgr.Wdata_wiki().Db_mgr().Load_mgr().Load_pid(lang_key, pid_name);
				if (rv == Wbase_pid.Id_null) rv = Wbase_pid.Id_null;
				Add(pid_key, rv);
			}
			return rv;
		}
	}
}
