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
