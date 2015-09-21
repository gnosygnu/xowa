/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.intls.*; import gplx.core.caches.*;
import gplx.xowa.langs.*;
import gplx.xowa.nss.*;
import gplx.xowa.parsers.tmpls.*;
public class Xow_defn_cache {	// stores compiled Xot_defn
	private Xol_lang lang;		// needed to lowercase names;
	private Bry_bfr upper_1st_bfr = Bry_bfr.reset_(255);
	private Gfo_cache_mgr cache = new Gfo_cache_mgr().Max_size_(64 * 1024 * 1024).Reduce_by_(32 * 1024 * 1024);
	public Xow_defn_cache(Xol_lang lang) {this.lang = lang;}
	public Xot_defn Get_by_key(byte[] name) {return (Xot_defn)cache.Get_by_key(name);}
	public void Free_mem_all()	{cache.Clear();}
	public void Free_mem_some() {cache.Reduce_recent();}
	public void Add(Xot_defn defn, byte case_match) {
		byte[] name = defn.Name();
		int cache_size = defn.Cache_size();			// OBSOLETE: * 2 b/c it has src and root; 
		cache.Add_replace(name, defn, cache_size);
		if (case_match == Xow_ns_case_.Id_1st) {
			name = lang.Case_mgr().Case_build_1st_upper(upper_1st_bfr, name, 0, name.length);
			cache.Add_replace(name, defn, 0);
		}
	}
}
