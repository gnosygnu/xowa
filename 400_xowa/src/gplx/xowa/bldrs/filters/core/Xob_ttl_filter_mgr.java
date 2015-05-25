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
package gplx.xowa.bldrs.filters.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.xowa.wikis.ttls.*;
public class Xob_ttl_filter_mgr {
	private boolean exclude_is_empty = true, include_is_empty = true;
	private final Xob_ttl_filter_mgr_srl srl = new Xob_ttl_filter_mgr_srl();
	private Hash_adp_bry exclude_hash = Hash_adp_bry.cs_(), include_hash = Hash_adp_bry.cs_();
	public void Clear() {
		exclude_hash.Clear();
		include_hash.Clear();
		exclude_is_empty = include_is_empty = true;
	}
	public boolean Match_include(byte[] src) {return include_is_empty ? false : include_hash.Has(src);}
	public boolean Match_exclude(byte[] src) {return exclude_is_empty ? false : exclude_hash.Has(src);}
	public void Load(boolean exclude, Io_url url) {
		byte[] src = Io_mgr.I.LoadFilBry_loose(url);
		if (Bry_.Len_gt_0(src)) Load(exclude, src);
	}
	public void Load(boolean exclude, byte[] src) {
		Hash_adp_bry hash = exclude ? exclude_hash : include_hash;
		srl.Init(hash).Load_by_bry(src);
		if (exclude)
			exclude_is_empty = exclude_hash.Count() == 0;
		else
			include_is_empty = include_hash.Count() == 0;
	}
}
