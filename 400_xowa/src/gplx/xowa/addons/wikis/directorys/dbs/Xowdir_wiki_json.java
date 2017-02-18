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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import gplx.langs.jsons.*;
public class Xowdir_wiki_json {
	public Xowdir_wiki_json(String name, String main_page) {
		this.name = name;
		this.main_page = main_page;
	}
	public String Name()          {return name;}          private String name;           public void Name_(String v) {name = v;}
	public String Main_page()     {return main_page;}     private String main_page;      public void Main_page_(String v) {main_page = v;}
	public String To_str(Json_wtr wtr) {
		wtr.Doc_nde_bgn();

		wtr.Nde_bgn("core");
		wtr.Kv_str("name", name);
		wtr.Kv_str("main_page", main_page);
		wtr.Nde_end();

		wtr.Doc_nde_end();
		return wtr.To_str_and_clear();
	}

	public static Xowdir_wiki_json New_by_json(Json_parser json_parser, String json) {
		Json_doc jdoc = json_parser.Parse(json);
		String name      = jdoc.Get_val_as_str_or(Bry_.Ary("core", "name"), "");
		String main_page = jdoc.Get_val_as_str_or(Bry_.Ary("core", "main_page"), "");
		return new Xowdir_wiki_json(name, main_page);
	}
	public static Xowdir_wiki_json New_empty() {
		return new Xowdir_wiki_json("", "Main_Page");
	}
}
