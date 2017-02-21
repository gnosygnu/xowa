/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
		wtr.Kv_str("mainpage", main_page);
		wtr.Nde_end();

		wtr.Doc_nde_end();
		return wtr.To_str_and_clear();
	}

	public static Xowdir_wiki_json New_by_json(Json_parser json_parser, String json) {
		Json_doc jdoc = json_parser.Parse(json);
		String name      = jdoc.Get_val_as_str_or(Bry_.Ary("core", "name"), "");
		String main_page = jdoc.Get_val_as_str_or(Bry_.Ary("core", "mainpage"), "");
		return new Xowdir_wiki_json(name, main_page);
	}
	public static Xowdir_wiki_json New_empty() {
		return new Xowdir_wiki_json("", "Main_Page");
	}
}
