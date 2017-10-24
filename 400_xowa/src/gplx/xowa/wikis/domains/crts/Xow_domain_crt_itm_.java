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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
class Xow_domain_crt_itm_ {
        public static final    Xow_domain_crt_itm Null = null;
}
class Xow_domain_crt_itm__in implements Xow_domain_crt_itm {
	private final    Xow_domain_crt_itm[] ary;
	public Xow_domain_crt_itm__in(Xow_domain_crt_itm[] ary) {this.ary = ary;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xow_domain_crt_itm itm = ary[i];
			if (itm.Matches(cur, comp)) return true;
		}
		return false;
	}
}
class Xow_domain_crt_itm__any_standard implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {
		switch (comp.Domain_type_id()) {
			case Xow_domain_tid_.Tid__wikipedia:
			case Xow_domain_tid_.Tid__wiktionary:
			case Xow_domain_tid_.Tid__wikisource:
			case Xow_domain_tid_.Tid__wikivoyage:
			case Xow_domain_tid_.Tid__wikiquote:
			case Xow_domain_tid_.Tid__wikibooks:
			case Xow_domain_tid_.Tid__wikiversity:
			case Xow_domain_tid_.Tid__wikinews:		return true;
			default:									return false;
		}
	}
        public static final    Xow_domain_crt_itm__any_standard Instance = new Xow_domain_crt_itm__any_standard(); Xow_domain_crt_itm__any_standard() {}
}
class Xow_domain_crt_itm__lang implements Xow_domain_crt_itm {
	private final    byte[] lang_key;
	public Xow_domain_crt_itm__lang(byte[] lang_key) {this.lang_key = lang_key;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(comp.Lang_orig_key(), lang_key);}
}
class Xow_domain_crt_itm__type implements Xow_domain_crt_itm {
	private final    int wiki_tid;
	public Xow_domain_crt_itm__type(int wiki_tid) {this.wiki_tid = wiki_tid;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return comp.Domain_type_id() == wiki_tid;}
}
class Xow_domain_crt_itm__wiki implements Xow_domain_crt_itm {
	private final    byte[] domain;
	public Xow_domain_crt_itm__wiki(byte[] domain) {this.domain = domain;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(comp.Domain_bry(), domain);}
}
