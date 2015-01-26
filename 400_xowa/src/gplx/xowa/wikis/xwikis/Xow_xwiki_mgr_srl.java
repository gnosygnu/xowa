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
import gplx.srls.dsvs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
class Xow_xwiki_mgr_srl extends Dsv_wkr_base {
	private byte[] key, url_fmt, name;
	private final Xow_xwiki_mgr mgr;
	private final Gfo_url_parser url_parser; private final byte cur_wiki_tid; private final Gfo_url tmp_url = new Gfo_url(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Xow_xwiki_mgr_srl(Xow_xwiki_mgr mgr, Gfo_url_parser url_parser, byte cur_wiki_tid) {
		this.mgr = mgr; this.url_parser = url_parser; this.cur_wiki_tid = cur_wiki_tid;
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
		Xow_xwiki_itm itm = Xow_xwiki_itm_.new_mw_(tmp_bfr, url_parser, tmp_url, key, url_fmt, name, cur_wiki_tid);
		mgr.Add_itm(itm);
	}
}
