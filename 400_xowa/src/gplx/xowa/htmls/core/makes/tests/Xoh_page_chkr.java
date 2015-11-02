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
package gplx.xowa.htmls.core.makes.tests; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.makes.*;
import gplx.xowa.htmls.sections.*;
public class Xoh_page_chkr {
	private final Xoh_section_mgr expd_section_mgr = new Xoh_section_mgr();
	private final Bry_bfr bfr = Bry_bfr.new_();
	public Xoh_page_chkr Body_(String v) {this.expd_body = v; return this;} private String expd_body;
	public Xoh_page_chkr Sections__add(int uid, int level, String anchor, String display, String content) {
		expd_section_mgr.Add(uid, level, Bry_.new_u8(anchor), Bry_.new_u8(display)).Content_(Bry_.new_u8(content));
		return this;
	}
	public void Check(Xoh_page actl) {
		if (expd_body != null) Tfds.Eq_str_lines(expd_body, String_.new_u8(actl.Body()));
		if (expd_section_mgr.Len() > 0)
			Tfds.Eq_str_lines(To_str__section_mgr(expd_section_mgr), To_str__section_mgr(actl.Section_mgr()));
	}
	private String To_str__section_mgr(Xoh_section_mgr expd_section_mgr) {
		expd_section_mgr.To_bfr(bfr);
		return bfr.To_str_and_clear();
	}
}
