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
package gplx.xowa.addons.wikis.directorys.specials.items; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*;
import gplx.langs.mustaches.*; import gplx.xowa.addons.wikis.directorys.dbs.*;
public class Xowdir_item_doc implements Mustache_doc_itm {
	private final    boolean mode_is_new;
	private final    int id;
	private final    String domain, name, dir, main_page;
	public Xowdir_item_doc(int id, String domain, String name, String dir, String main_page) {
		this.mode_is_new = id == -1;
		this.id = id;
		this.domain = domain;
		this.name = name;
		this.dir = dir;
		this.main_page = main_page;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "id"))				bfr.Add_int(id);
		else if	(String_.Eq(key, "domain"))			bfr.Add_str_u8(domain);
		else if	(String_.Eq(key, "name"))			bfr.Add_str_u8(name);
		else if	(String_.Eq(key, "dir"))			bfr.Add_str_u8(dir);
		else if	(String_.Eq(key, "main_page"))		bfr.Add_str_u8(main_page);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "mode_is_new"))		return Mustache_doc_itm_.Ary__bool(mode_is_new);
		return Mustache_doc_itm_.Ary__empty;
	}

	public static final    Xowdir_item_doc[] Ary_empty = new Xowdir_item_doc[0];
	public static Xowdir_item_doc New(Xowdir_wiki_itm itm) {
		return new Xowdir_item_doc(itm.Id(), itm.Domain(), itm.Json().Name(), itm.Url().OwnerDir().Xto_api(), itm.Json().Main_page());
	}
	public static Xowdir_item_doc[] New_ary(Xowdir_wiki_itm[] itms_ary) {
		int len = itms_ary.length;
		Xowdir_item_doc[] rv = new Xowdir_item_doc[itms_ary.length];
		for (int i = 0; i < len; i++) {
			rv[i] = New(itms_ary[i]);
		}
		return rv;
	}
}
