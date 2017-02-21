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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.mustaches.*;
import gplx.core.gfobjs.*;
public class Xoedit_grp implements Xoedit_nde, Mustache_doc_itm {		
	private String lang, name;
	public Xoedit_grp(int id, String key, int sort) {
		this.id = id;
		this.key = key;
		this.sort = sort;
	}
	public int		Id()		{return id;}	private final    int id;
	public String	Key()		{return key;}	private final    String key;
	public int		Sort()		{return sort;}	private final    int sort;
	public String	Help()		{return help;}	private String help;

	public Xoedit_itm[] Itms() {return itms;} private Xoedit_itm[] itms = new Xoedit_itm[0];
	public void Itms_(Xoedit_itm[] v) {this.itms = v;}
	public void Load_by_i18n(String lang, String name, String help) {
		this.lang = lang;
		this.name = name;
		this.help = help;
	}
	public Gfobj_nde To_nde(Bry_bfr tmp_bfr) {
		Gfobj_nde rv = Gfobj_nde.New();
		rv.Add_int("id", id);
		rv.Add_str("key", key);
		rv.Add_str("lang", lang);
		rv.Add_str("name", name);
		rv.Add_str("help", help);

		List_adp list = List_adp_.New();
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xoedit_itm itm = itms[i];
			list.Add(itm.To_nde(tmp_bfr));
		}
		rv.Add_ary("itms", new Gfobj_ary((Gfobj_nde[])list.To_ary_and_clear(Gfobj_nde.class)));
		return rv;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "id"))		bfr.Add_int(id);
		else if	(String_.Eq(k, "key"))		bfr.Add_str_u8(key);
		else if	(String_.Eq(k, "lang"))		bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "name"))		bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "help"))		bfr.Add_str_u8(help);
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "itms"))		return itms;
		return Mustache_doc_itm_.Ary__empty;
	}
}
