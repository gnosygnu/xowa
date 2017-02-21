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
		else if	(String_.Eq(key, "mainpage"))		bfr.Add_str_u8(main_page);
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
