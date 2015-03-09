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
import gplx.xowa.files.fsdb.*;
public interface Xof_orig_wkr {
	byte			Tid();
	Xof_orig_itm	Find_as_itm(byte[] ttl);
	void			Find_by_list(OrderedHash rv, ListAdp itms);
	boolean			Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect);
	void			Db_txn_save();
	void			Db_rls();
}
