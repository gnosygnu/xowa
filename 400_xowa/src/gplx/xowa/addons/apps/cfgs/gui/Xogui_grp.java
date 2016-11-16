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
package gplx.xowa.addons.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.langs.mustaches.*;
public class Xogui_grp implements Xogui_nde, Mustache_doc_itm {
	private Xogui_itm[] itms = new Xogui_itm[0];
	public Xogui_grp(int id, int sort, String key) {
		this.id = id;
		this.sort = sort;
		this.key = key;
	}
	public int Id() {return id;} private final    int id;
	public int Sort() {return sort;} private final    int sort;

	public String Key() {return key;} private String key;
	
	public String Lang() {return lang;} private String lang;
	public String Name() {return name;} private String name;
	public String Help() {return help;} private String help;
	public void Load_by_i18n(String lang, String name, String help) {
		this.lang = lang;
		this.name = name;
		this.help = help;
	}

	public void Grps__add(Xogui_grp grp) {
	}
	public void Itms_(Xogui_itm[] v) {
		this.itms = v;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "id"))		bfr.Add_int(id);
		else if	(String_.Eq(k, "key"))		bfr.Add_str_u8(key);
		else if	(String_.Eq(k, "lang"))		bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "name"))		bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "help"))		bfr.Add_str_u8(help);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "itms"))		return itms;
		return Mustache_doc_itm_.Ary__empty;
	}
}
