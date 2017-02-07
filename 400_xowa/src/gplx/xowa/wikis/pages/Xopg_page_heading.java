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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.pages.htmls.*;
public class Xopg_page_heading implements Bfr_arg {
	private Xowe_wiki wiki;
	private Xopg_html_data html_data;
	private byte[] ttl_full_db;
	private byte[] display_title;
	private boolean mode_is_read;
	public Xopg_page_heading Init(Xowe_wiki wiki, boolean mode_is_read, Xopg_html_data html_data, byte[] ttl_full_db, byte[] display_title) {
		this.wiki = wiki;
		this.mode_is_read = mode_is_read;
		this.ttl_full_db = ttl_full_db;
		this.html_data = html_data;
		this.display_title = display_title;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (html_data.Xtn_pgbnr() != null) return;	// pgbnr exists; don't add title
		byte[] edit_lead_section = Bry_.Empty;
		if (	wiki.Parser_mgr().Hdr__section_editable__mgr().Enabled()
			&&	mode_is_read) {
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			wiki.Parser_mgr().Hdr__section_editable__mgr().Write_html(tmp_bfr, ttl_full_db, Bry_.Empty, Bry__lead_section_hint);
			edit_lead_section = tmp_bfr.To_bry_and_clear();
		}

		fmtr.Bld_many(bfr, display_title, edit_lead_section);
	}
	private static final    byte[] Bry__lead_section_hint = Bry_.new_u8("(Lead)");
	private final    Bry_fmt fmtr = Bry_fmt.Auto_nl_apos("<h1 id='firstHeading' class='firstHeading'>~{page_title}~{edit_lead_section}</h1>");	// <span>~{page_title}</span>
}
