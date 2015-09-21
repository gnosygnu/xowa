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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.langs.dsvs.*; import gplx.xowa.wikis.domains.*;
class Xow_xwiki_mgr_srl extends Dsv_wkr_base {
	private byte[] key, url_fmt, name;
	private final Xow_domain_itm cur_domain; private final Xow_xwiki_mgr mgr;
	public Xow_xwiki_mgr_srl(Xow_domain_itm cur_domain, Xow_xwiki_mgr mgr) {
		this.cur_domain = cur_domain; this.mgr = mgr;
	}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: key				= Bry_.Mid(src, bgn, end); return true;
			case 1: url_fmt			= Bry_.Mid(src, bgn, end); return true;
			case 2: name			= Bry_.Mid(src, bgn, end); return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		Xow_xwiki_itm itm = Xow_xwiki_itm_bldr.I.Bld(cur_domain, key, url_fmt, name);
		mgr.Add_itm(itm);
	}
}
