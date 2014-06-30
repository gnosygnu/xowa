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
import gplx.xowa.files.fsdb.*;
public class Xof_qry_wrk_mock implements Xof_qry_wkr {
	private Hash_adp_bry hash = Hash_adp_bry.cs_();
	public byte Tid() {return Xof_qry_wkr_.Tid_mock;}
	public void Clear() {hash.Clear();}
	public Xof_qry_wrk_mock Add_wiki_size(byte[] ttl, byte[] wiki, int w, int h) {
		Xof_fsdb_itm itm = Get_or_new(ttl);
		itm.Orig_wiki_(wiki);
		itm.Orig_size_(w, h);
		return this;
	}
	public Xof_qry_wrk_mock Add_redirect(byte[] ttl, byte[] redirect) {
		Xof_fsdb_itm itm = Get_or_new(ttl);
		itm.Orig_redirect_(redirect);
		return this;
	}
	private Xof_fsdb_itm Get_or_new(byte[] ttl) {
		Xof_fsdb_itm itm = (Xof_fsdb_itm)hash.Fetch(ttl);
		if (itm == null) {
			itm = new Xof_fsdb_itm();
			itm.Lnki_ttl_(ttl);
			hash.Add_bry_obj(ttl, itm);
		}
		return itm;
	}
	public boolean Qry_file(Xof_fsdb_itm itm) {
		byte[] ttl = itm.Lnki_ttl();
		Xof_fsdb_itm reg = (Xof_fsdb_itm)hash.Fetch(ttl); if (reg == null) return false;
		itm.Orig_wiki_(reg.Orig_wiki());
		if (reg.Orig_redirect() != null)
			itm.Orig_redirect_(reg.Orig_redirect());
		itm.Orig_size_(reg.Orig_w(), reg.Orig_h());
		if (itm.Lnki_ext().Id_is_ogg()) Xof_qry_wkr_wmf_api.Coerce_ogg_ext(itm, reg.Orig_w(), reg.Orig_h());
		return true;
	}
}
