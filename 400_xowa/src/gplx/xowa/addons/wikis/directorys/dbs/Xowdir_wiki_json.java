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
	public Xowdir_wiki_json(String domain, String name, String mainpage) {
		this.domain = domain;
		this.name = name;
		this.mainpage = mainpage;
	}
	public String Domain()        {return domain;}        private String domain;         public void Domain_(String v) {domain = v;}
	public String Name()          {return name;}          private String name;           public void Name_(String v) {name = v;}
	public String Mainpage()      {return mainpage;}      private String mainpage;       public void Mainpage_(String v) {mainpage = v;}
	public String To_str(Json_wtr wtr) {
		wtr.Doc_nde_bgn();

		wtr.Nde_bgn("core");
		wtr.Kv_str("domain", domain);
		wtr.Kv_str("name", name);
		wtr.Kv_str("mainpage", mainpage);
		wtr.Nde_end();

		wtr.Doc_nde_end();
		return wtr.To_str_and_clear();
	}

	public static Xowdir_wiki_json New_by_json(Json_parser json_parser, String json) {
		Json_doc jdoc = json_parser.Parse(json);
		String domain    = jdoc.Get_val_as_str_or(Bry_.Ary("core", "domain"), "");
		String name      = jdoc.Get_val_as_str_or(Bry_.Ary("core", "name"), "");
		String mainpage  = jdoc.Get_val_as_str_or(Bry_.Ary("core", "mainpage"), "");
		return new Xowdir_wiki_json(domain, name, mainpage);
	}
	public static Xowdir_wiki_json New_empty() {
		return new Xowdir_wiki_json("", "", "Main_Page");
	}
}
