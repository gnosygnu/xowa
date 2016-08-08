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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.xowa.files.origs.*;
public class Xof_orig_wkr__img_links implements Xof_orig_wkr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public byte			Tid() {return Xof_orig_wkr_.Tid_xowa_img_links;}
	public Xof_orig_itm	Find_as_itm(byte[] ttl, int list_idx, int list_len) {return (Xof_orig_itm)hash.Get_by(ttl);}
	public void			Find_by_list(Ordered_hash rv, List_adp itms) {throw Err_.new_unimplemented();}
	public boolean		Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {return false;}
	public void			Db_txn_save() {}
	public void			Db_rls() {}

	public void Add_by_db(Xof_orig_itm itm) {
		hash.Add(itm.Ttl(), itm);
	}
}
