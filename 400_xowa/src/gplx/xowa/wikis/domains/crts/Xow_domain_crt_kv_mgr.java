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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
public class Xow_domain_crt_kv_mgr {
	private final ListAdp list = ListAdp_.new_();
	public void Clear() {list.Clear();}
	@gplx.Internal protected void Add(Xow_domain_crt_kv itm) {list.Add(list);}
	public boolean Parse(byte[] raw) {
		Xow_domain_crt_kv[] ary = Xow_domain_crt_itm_parser.I.Parse_as_kv_ary_or_null(raw);
		if (ary == null) return false; // invalid parse; leave current value as is and exit;
		this.Clear();
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			this.Add(ary[i]);
		return true;
	}
	public Xow_domain_crt_itm Find(Xow_domain cur, Xow_domain comp) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xow_domain_crt_kv kv = (Xow_domain_crt_kv)list.FetchAt(i);
			if (kv.Key().Matches(cur, comp)) return kv.Val();
		}
		return Xow_domain_crt_itm__null.I;
	}
}
class Xow_domain_crt_kv {
	public Xow_domain_crt_kv(Xow_domain_crt_itm key, Xow_domain_crt_itm val) {this.key = key; this.val = val;}
	public Xow_domain_crt_itm Key() {return key;} private final Xow_domain_crt_itm key;
	public Xow_domain_crt_itm Val() {return val;} private final Xow_domain_crt_itm val;
}
