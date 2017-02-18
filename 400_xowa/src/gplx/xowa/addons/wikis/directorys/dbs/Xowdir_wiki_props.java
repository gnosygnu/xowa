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
import gplx.xowa.wikis.data.*;
public class Xowdir_wiki_props {
	public Xowdir_wiki_props() {}
	public Xowdir_wiki_props(String domain, String name, String main_page) {
		this.domain = domain;
		this.name = name;
		this.main_page = main_page;
	}
	public boolean Dirty()         {return dirty;}         private boolean dirty;
	public String Domain()      {return domain;}        private String domain;
	public String Name()        {return name;}          private String name;
	public String Main_page()   {return main_page;}     private String main_page;

	public void Dirty_y_() {
		dirty = true;
	}

	public void Set(String key, String val) {
		if      (String_.Eq(key, Xowd_cfg_key_.Key__wiki__core__domain))      this.domain = val;
		else if (String_.Eq(key, Xowd_cfg_key_.Key__wiki__core__name))        this.name = val;
		else if (String_.Eq(key, Xowd_cfg_key_.Key__init__main_page))   this.main_page = val;
		else throw Err_.new_unhandled_default(key);
	}

	public String To_str() {return String_.Concat(domain, "|", name, "|", main_page );}
}
