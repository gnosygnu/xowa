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
package gplx.xowa2.files.metas; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
import gplx.dbs.*;
import gplx.xowa.*; import gplx.xowa.files.wiki_orig.*;
public class Xof_file_meta_wkr__db_orig implements Xof_file_meta_wkr {
	public Xof_orig_regy_tbl Tbl() {return tbl;} private final Xof_orig_regy_tbl tbl = new Xof_orig_regy_tbl();
	public Xof_file_meta_itm Get_or_null(byte[] ttl) {
		Xof_orig_regy_itm itm = tbl.Select_itm(ttl);
		if (itm == null) return null;
		return new Xof_file_meta_itm(itm.Repo_tid() == Xof_repo_itm.Repo_remote, ttl, itm.Orig_w(), itm.Orig_h());
	}
}
