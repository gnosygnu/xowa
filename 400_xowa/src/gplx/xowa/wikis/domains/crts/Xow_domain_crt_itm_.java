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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
class Xow_domain_crt_itm_ {
        public static final Xow_domain_crt_itm Null = null;
}
class Xow_domain_crt_itm__any_wiki implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return true;}
        public static final Xow_domain_crt_itm__any_wiki I = new Xow_domain_crt_itm__any_wiki(); Xow_domain_crt_itm__any_wiki() {}
}
class Xow_domain_crt_itm__in implements Xow_domain_crt_itm {
	private final Xow_domain_crt_itm[] ary;
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
			case Xow_domain_type_.Int__wikipedia:
			case Xow_domain_type_.Int__wiktionary:
			case Xow_domain_type_.Int__wikisource:
			case Xow_domain_type_.Int__wikivoyage:
			case Xow_domain_type_.Int__wikiquote:
			case Xow_domain_type_.Int__wikibooks:
			case Xow_domain_type_.Int__wikiversity:
			case Xow_domain_type_.Int__wikinews:		return true;
			default:								return false;
		}
	}
        public static final Xow_domain_crt_itm__any_standard I = new Xow_domain_crt_itm__any_standard(); Xow_domain_crt_itm__any_standard() {}
}
class Xow_domain_crt_itm__none implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return false;}
        public static final Xow_domain_crt_itm__none I = new Xow_domain_crt_itm__none(); Xow_domain_crt_itm__none() {}
}
class Xow_domain_crt_itm__self implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(cur.Domain_bry(), comp.Domain_bry());}
        public static final Xow_domain_crt_itm__self I = new Xow_domain_crt_itm__self(); Xow_domain_crt_itm__self() {}
}
class Xow_domain_crt_itm__same_lang implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(cur.Lang_orig_key(), comp.Lang_orig_key());}
        public static final Xow_domain_crt_itm__same_lang I = new Xow_domain_crt_itm__same_lang(); Xow_domain_crt_itm__same_lang() {}
}
class Xow_domain_crt_itm__same_type implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return cur.Domain_type_id() == comp.Domain_type_id();}
        public static final Xow_domain_crt_itm__same_type I = new Xow_domain_crt_itm__same_type(); Xow_domain_crt_itm__same_type() {}
}
class Xow_domain_crt_itm__lang implements Xow_domain_crt_itm {
	private final byte[] lang_key;
	public Xow_domain_crt_itm__lang(byte[] lang_key) {this.lang_key = lang_key;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(comp.Lang_orig_key(), lang_key);}
}
class Xow_domain_crt_itm__type implements Xow_domain_crt_itm {
	private final int wiki_tid;
	public Xow_domain_crt_itm__type(int wiki_tid) {this.wiki_tid = wiki_tid;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return comp.Domain_type_id() == wiki_tid;}
}
class Xow_domain_crt_itm__wiki implements Xow_domain_crt_itm {
	private final byte[] domain;
	public Xow_domain_crt_itm__wiki(byte[] domain) {this.domain = domain;}
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(comp.Domain_bry(), domain);}
}
