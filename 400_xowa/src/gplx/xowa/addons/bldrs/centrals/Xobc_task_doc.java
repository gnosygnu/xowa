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
package gplx.xowa.addons.bldrs.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.langs.mustaches.*;
class Xobc_task_doc implements Mustache_doc_itm {
	private final    boolean app_is_drd;
	private final    String link_help;
	public Xobc_task_doc(boolean app_is_drd, String link_help) {
		this.app_is_drd = app_is_drd; this.link_help = link_help;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "link_help"))		bfr.Add_str_u8(link_help);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "app_is_drd"))		return Mustache_doc_itm_.Ary__bool(app_is_drd);
		return Mustache_doc_itm_.Ary__empty;
	}
}
