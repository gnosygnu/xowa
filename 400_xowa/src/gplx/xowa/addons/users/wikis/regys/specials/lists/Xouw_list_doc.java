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
package gplx.xowa.addons.users.wikis.regys.specials.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*;
import gplx.core.ios.*;
import gplx.langs.mustaches.*;
import gplx.xowa.addons.users.wikis.regys.dbs.*; import gplx.xowa.addons.users.wikis.regys.specials.itms.*;
class Xouw_list_doc implements Mustache_doc_itm {
	private final    Xouw_itm_doc[] itms_ary;
	public Xouw_list_doc(Xouw_itm_doc[] itms_ary) {
		this.itms_ary = itms_ary;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "itms"))		return itms_ary;
		return Mustache_doc_itm_.Ary__empty;
	}
}
