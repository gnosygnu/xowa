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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
import gplx.xowa.files.bins.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.gui.*;
public interface Xof_fsdb_mgr extends RlsAble {
	Xof_orig_mgr Orig_mgr();
	Xof_bin_mgr Bin_mgr();
	Fsm_mnt_mgr Mnt_mgr();
	void Init_by_wiki(Xow_wiki wiki);
	void Fsdb_search_by_list(byte exec_tid, ListAdp itms, Xoa_page page, Xog_js_wkr js_wkr);
}
