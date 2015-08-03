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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.core.primitives.*;
public class Xofv_repo_mgr {
	private final Hash_adp_bry key_regy = Hash_adp_bry.cs();
	private final Hash_adp tid_regy = Hash_adp_.new_(); private final Byte_obj_ref tid_key = Byte_obj_ref.zero_();
	public Xofv_repo_mgr Add(Xofv_repo_itm itm) {
		key_regy.Add(itm.Key(), itm);
		tid_regy.Add(Byte_obj_ref.new_(itm.Tid()), itm);
		return this;
	}
	public Xofv_repo_itm Get_by_key(byte[] key) {
		return (Xofv_repo_itm)key_regy.Get_by(key);
	}
	public Xofv_repo_itm Get_by_tid(byte tid) {
		return (Xofv_repo_itm)tid_regy.Get_by(tid_key.Val_(tid));
	}
}