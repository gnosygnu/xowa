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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wmfs.apis.*; import gplx.xowa.files.fsdb.*;
public class Xof_orig_wkr_ {
	public static final Xof_orig_wkr[] Ary_empty = new Xof_orig_wkr[0];
	public static void Find_by_list(Xof_orig_wkr wkr, Ordered_hash rv, List_adp itms) {
		int len = itms.Count();
		for (int i = 0; i < len; ++i) {
			Xof_fsdb_itm fsdb = (Xof_fsdb_itm)itms.Get_at(i);
			byte[] fsdb_ttl = fsdb.Lnki_ttl();
			if (rv.Has(fsdb_ttl)) continue;
			Xof_orig_itm orig = wkr.Find_as_itm(fsdb_ttl, i, len);
			if (orig == Xof_orig_itm.Null) continue;
			rv.Add(fsdb_ttl, orig);
		}
	}

	public static final byte
	  Tid_xowa_db			= 1
	, Tid_wmf_api			= 2
	, Tid_xowa_meta			= 3
	;
}
