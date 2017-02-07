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
package gplx.xowa.addons.wikis.registrys.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.registrys.*;
import gplx.langs.mustaches.*;
class Xow_list_doc implements Mustache_doc_itm {
	private final    byte[] import_root;
	private final    Xow_list_doc_wiki[] subs;
	public Xow_list_doc(byte[] import_root, Xow_list_doc_wiki[] subs) {this.import_root = import_root; this.subs = subs;}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "import_root"))	bfr.Add_bry(import_root);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "subs"))			return subs;
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xow_list_doc_wiki implements Mustache_doc_itm {
	private final    byte[] domain;
	private final    String date;
	public Xow_list_doc_wiki(byte[] domain, String date) {
		this.domain = domain; this.date = date;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "domain"))			bfr.Add_bry(domain);
		else if	(String_.Eq(key, "date"))			bfr.Add_str_u8(date);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
}
