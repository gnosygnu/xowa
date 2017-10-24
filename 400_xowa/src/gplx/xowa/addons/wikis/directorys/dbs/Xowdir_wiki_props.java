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
