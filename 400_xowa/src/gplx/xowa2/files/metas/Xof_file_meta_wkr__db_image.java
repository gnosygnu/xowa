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
import gplx.dbs.*; import gplx.xowa.files.wiki_orig.*;
import gplx.xowa2.files.commons.*;
public class Xof_file_meta_wkr__db_image implements Xof_file_meta_wkr {
	private final Xof_commons_image_tbl tbl_wiki = new Xof_commons_image_tbl(), tbl_comm = new Xof_commons_image_tbl();
	public Xof_file_meta_wkr__db_image(Db_conn conn_wiki, Db_conn conn_comm) {
		tbl_wiki.Conn_(conn_wiki);
		tbl_comm.Conn_(conn_comm);
	}
	public Xof_file_meta_itm Get_or_null(byte[] ttl) {
		Xof_commons_image_itm itm = tbl_wiki.Select(ttl);
		boolean repo_is_commons = false;
		if (itm == null) {
			itm = tbl_comm.Select(ttl);
			if (itm == null) return null;
			repo_is_commons = true;
		}
		return new Xof_file_meta_itm(repo_is_commons, ttl, itm.Width(), itm.Height());
	}
}
