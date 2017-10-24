/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
		page.Modified_on_(Int_flag_bldr_.To_date_short(timestamp));
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
