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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.core.ios.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_parse_dump_templates_cmd extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public Xob_parse_dump_templates_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Wkr_key() {return KEY;} public static final String KEY = "parse.dump_templates";
	public static final int FixedLen_page = 1 + 5 + 1 + 5 + 1 + 1 + 1;	// \tid|date|title|text\n
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		Init_dump(KEY);
	}
	public void Wkr_run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Tid__template) return;
		int id = page.Id(); byte[] title = page.Ttl_page_db(), text = page.Text(); int title_len = title.length, text_len = text.length;
		if (FixedLen_page + title_len + text_len + dump_bfr.Len() > dump_fil_len) super.Flush_dump();
		Xotdb_page_itm_.Txt_page_save(dump_bfr, id, page.Modified_on(), title, text, true);
	}
	public void Wkr_end() {super.Flush_dump();}
	public void Wkr_print() {}
}
