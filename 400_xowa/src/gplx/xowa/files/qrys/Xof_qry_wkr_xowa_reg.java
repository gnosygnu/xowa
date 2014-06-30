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
package gplx.xowa.files.qrys; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.wiki_orig.*;
public class Xof_qry_wkr_xowa_reg implements Xof_qry_wkr {
	private Db_provider provider;
	public Xof_qry_wkr_xowa_reg(Db_provider p) {this.provider = p;}
	public byte Tid() {return Xof_qry_wkr_.Tid_xowa_reg;}
	public boolean Qry_file(Xof_fsdb_itm itm) {
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = Select(provider, itm.Lnki_ttl());
			if (!rdr.MoveNextPeer()) return false;
			// NOTE: no need to set redirect; file_orig stores direct entries; EX: A.png with 20,30 redirects to B.png; file_orig stores B.png,20,30,A.png
			byte[] orig_redirect = rdr.ReadBryByStr(Xof_wiki_orig_tbl.Fld_orig_redirect);
			if (Bry_.Len_gt_0(orig_redirect))
				itm.Orig_redirect_(orig_redirect);
			int orig_w = rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_w);
			int orig_h = rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_h);
			itm.Orig_size_(orig_w, orig_h);
			return true;
		}
		finally {rdr.Rls();}
	}
	private DataRdr Select(Db_provider p, byte[] ttl) {
		Db_stmt stmt = Db_stmt_.new_select_(p,	Xof_wiki_orig_tbl.Tbl_name, String_.Ary(Xof_wiki_orig_tbl.Fld_orig_ttl));
		return stmt.Clear().Val_str_by_bry_(ttl).Exec_select();
	}
}
