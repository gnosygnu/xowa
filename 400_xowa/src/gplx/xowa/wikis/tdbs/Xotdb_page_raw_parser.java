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
package gplx.xowa.wikis.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.*; import gplx.core.ios.*; import gplx.core.encoders.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.bldrs.*;
public class Xotdb_page_raw_parser {
	public void Init(Gfo_usr_dlg usr_dlg, Xowe_wiki wiki, int load_len) {
		this.wiki = wiki; ns_mgr = wiki.Ns_mgr();
		rdr = new Io_line_rdr(usr_dlg, new Io_url[1]);
		rdr.Line_dlm_(Byte_ascii.Tab).Load_len_(load_len).Key_gen_(Io_line_rdr_key_gen_all.Instance);
	}
	public void Init_ns(Xow_ns ns_itm) {this.ns_itm = ns_itm;}
	public void Reset_one(Io_url url) {
		rdr.Reset_one(url);
	}
	public void Load(Gfo_usr_dlg usr_dlg, Xowe_wiki wiki, Xow_ns ns_itm, Io_url[] urls, int load_len) {
		this.wiki = wiki; ns_mgr = wiki.Ns_mgr(); this.ns_itm = ns_itm;
		rdr = new Io_line_rdr(usr_dlg, urls);
		rdr.Line_dlm_(Byte_ascii.Tab).Load_len_(load_len).Key_gen_(Io_line_rdr_key_gen_all.Instance);
	}	Io_line_rdr rdr; Xowe_wiki wiki; Xow_ns_mgr ns_mgr; Xow_ns ns_itm;
	public void Skip_first_line() {
		rdr.Read_next();
		int pos = Bry_find_.Find_fwd(rdr.Bfr(), Byte_ascii.Nl);
//			rdr.Move(pos + 1);
		rdr.Truncate(pos + 1);
	}
	public boolean Read(Xowd_page_itm page) {
		boolean read = false;
		read = rdr.Read_next(); if (!read) return false;
		int id = Base85_.To_int_by_bry(rdr.Bfr(), rdr.Key_pos_bgn(), rdr.Key_pos_end() - 2);
		page.Id_(id);
		read = rdr.Read_next(); if (!read) throw Err_.new_wo_type("could not read timestamp");
		int timestamp = Base85_.To_int_by_bry(rdr.Bfr(), rdr.Key_pos_bgn(), rdr.Key_pos_end() - 1);
		page.Modified_on_(Bit_.Xto_date_short(timestamp));
		read = rdr.Read_next(); if (!read) throw Err_.new_wo_type("could not read ttl");
		byte[] ttl = Bry_.Mid(rdr.Bfr(), rdr.Key_pos_bgn(), rdr.Key_pos_end() - 1);
		page.Ttl_(ttl, ns_mgr);
		read = rdr.Read_next(); if (!read) throw Err_.new_wo_type("could not read text");
		byte[] text = Bry_.Mid(rdr.Bfr(), rdr.Key_pos_bgn(), rdr.Key_pos_end() - 1);
		page.Text_(text);
		rdr.Bfr_last_read_add(1);
		return true;
	}
}
