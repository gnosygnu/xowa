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
class Xow_domain_crt_itm__any_wiki implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return true;}
        public static final Xow_domain_crt_itm__any_wiki I = new Xow_domain_crt_itm__any_wiki(); Xow_domain_crt_itm__any_wiki() {}
}
class Xow_domain_crt_itm__in implements Xow_domain_crt_itm {
	private final Xow_domain_crt_itm[] ary;
	public Xow_domain_crt_itm__in(Xow_domain_crt_itm[] ary) {this.ary = ary;}
	public boolean Matches(Xow_domain cur, Xow_domain comp) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xow_domain_crt_itm itm = ary[i];
			if (itm.Matches(cur, comp)) return true;
		}
		return false;
	}
}
class Xow_domain_crt_itm__any_standard implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {
		switch (comp.Domain_tid()) {
			case Xow_domain_.Tid_int_wikipedia:
			case Xow_domain_.Tid_int_wiktionary:
			case Xow_domain_.Tid_int_wikisource:
			case Xow_domain_.Tid_int_wikivoyage:
			case Xow_domain_.Tid_int_wikiquote:
			case Xow_domain_.Tid_int_wikibooks:
			case Xow_domain_.Tid_int_wikiversity:
			case Xow_domain_.Tid_int_wikinews:		return true;
			default:								return false;
		}
	}
        public static final Xow_domain_crt_itm__any_standard I = new Xow_domain_crt_itm__any_standard(); Xow_domain_crt_itm__any_standard() {}
}
class Xow_domain_crt_itm__null implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {throw Err_.not_implemented_msg_("null criteria should not be called");}
        public static final Xow_domain_crt_itm I = new Xow_domain_crt_itm__null(); Xow_domain_crt_itm__null() {}
}
class Xow_domain_crt_itm__self implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return Bry_.Eq(cur.Domain_bry(), comp.Domain_bry());}
        public static final Xow_domain_crt_itm__self I = new Xow_domain_crt_itm__self(); Xow_domain_crt_itm__self() {}
}
class Xow_domain_crt_itm__same_lang implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return cur.Lang_uid() == comp.Lang_uid();}
        public static final Xow_domain_crt_itm__same_lang I = new Xow_domain_crt_itm__same_lang(); Xow_domain_crt_itm__same_lang() {}
}
class Xow_domain_crt_itm__same_type implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return cur.Domain_tid() == comp.Domain_tid();}
        public static final Xow_domain_crt_itm__same_type I = new Xow_domain_crt_itm__same_type(); Xow_domain_crt_itm__same_type() {}
}
class Xow_domain_crt_itm__lang implements Xow_domain_crt_itm {
	private final int lang_uid;
	public Xow_domain_crt_itm__lang(int lang_uid) {this.lang_uid = lang_uid;}
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return comp.Lang_uid() == lang_uid;}
}
class Xow_domain_crt_itm__type implements Xow_domain_crt_itm {
	private final int wiki_tid;
	public Xow_domain_crt_itm__type(int wiki_tid) {this.wiki_tid = wiki_tid;}
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return comp.Domain_tid() == wiki_tid;}
}
class Xow_domain_crt_itm__wiki implements Xow_domain_crt_itm {
	private final byte[] domain;
	public Xow_domain_crt_itm__wiki(byte[] domain) {this.domain = domain;}
	public boolean Matches(Xow_domain cur, Xow_domain comp) {return Bry_.Eq(comp.Domain_bry(), domain);}
}
