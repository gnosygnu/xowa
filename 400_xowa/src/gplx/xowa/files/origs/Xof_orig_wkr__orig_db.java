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
import gplx.dbs.*;
public class Xof_orig_wkr__orig_db implements Xof_orig_wkr {
	private final    boolean addable;
	public Xof_orig_wkr__orig_db(Xof_orig_tbl tbl, boolean addable) {this.tbl = tbl; this.addable = addable;}
	public byte				Tid() {return Xof_orig_wkr_.Tid_xowa_db;}
	public Xof_orig_tbl		Tbl() {return tbl;} private final    Xof_orig_tbl tbl;
	public void				Find_by_list(Ordered_hash rv, List_adp itms) {tbl.Select_by_list(rv, itms);}
	public Xof_orig_itm		Find_as_itm(byte[] ttl, int list_idx, int list_len) {return tbl.Select_itm(ttl);}
	public boolean				Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {
		if (!addable) return false;
		if (tbl.Exists__repo_ttl(repo, page))
			tbl.Update(repo, page, ext_id, w, h, redirect);
		else
			tbl.Insert(repo, page, ext_id, w, h, redirect);
		return true;
	}
	public void				Db_txn_save() {tbl.Conn().Txn_end();}
	public void				Db_rls() {tbl.Conn().Rls_conn();}
}
