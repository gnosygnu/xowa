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
public class Xof_qry_wkr_wmf_api implements Xof_qry_wkr {
	private Xow_wiki wiki;
	private Xof_img_wkr_api_size_base api_wkr;
	private Xof_img_wkr_api_size_base_rslts api_rv = new Xof_img_wkr_api_size_base_rslts();
	public Xof_qry_wkr_wmf_api(Xow_wiki wiki, Xof_img_wkr_api_size_base api_wkr) {this.wiki = wiki; this.api_wkr = api_wkr;}
	public byte Tid() {return Xof_qry_wkr_.Tid_wmf_api;}
	public boolean Qry_file(Xof_fsdb_itm itm) {
		byte[] itm_ttl = itm.Lnki_ttl();
		boolean found = api_wkr.Api_query_size(api_rv, wiki, itm_ttl, itm.Lnki_w(), itm.Lnki_h());
		if (!found) return false;	// ttl not found by api; return
		itm.Orig_wiki_(api_rv.Reg_wiki());
		if (!Bry_.Eq(itm_ttl, api_rv.Reg_page()))	// ttl is different; must be redirect
			itm.Orig_redirect_(api_rv.Reg_page());
		int orig_w = api_rv.Orig_w(), orig_h = api_rv.Orig_h();
		itm.Orig_size_(orig_w, orig_h);
		if (itm.Lnki_ext().Id_is_ogg()) Coerce_ogg_ext(itm, orig_w, orig_h);
		return true;
	}
	public static void Coerce_ogg_ext(Xof_fsdb_itm itm, int orig_w, int orig_h) {
		boolean is_audio = orig_w == 0 && orig_h == 0; // wmf returns back w/h of 0 if audio; non-0 if video; DATE:2013-11-11
		int actl_ext_id = is_audio ? Xof_ext_.Id_oga : Xof_ext_.Id_ogv;
		itm.Lnki_ext_(Xof_ext_.new_by_id_(actl_ext_id));
	}
}
