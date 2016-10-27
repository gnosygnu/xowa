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
package gplx.xowa.addons.users.wikis.regys.specials.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*;
import gplx.core.ios.*;
import gplx.langs.mustaches.*;
class Xouw_itm_doc implements Mustache_doc_itm {
	private final    int id;
	private final    String domain, name, file;
	public Xouw_itm_doc(int id, String domain, String name, String file) {
		this.id = id;
		this.domain = domain;
		this.name = name;
		this.file = file;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "id"))				bfr.Add_int(id);
		else if	(String_.Eq(key, "domain"))			bfr.Add_str_u8(domain);
		else if	(String_.Eq(key, "name"))			bfr.Add_str_u8(name);
		else if	(String_.Eq(key, "file"))			bfr.Add_str_u8(file);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}
	public static final    Xouw_itm_doc[] Ary_empty = new Xouw_itm_doc[0];
}
