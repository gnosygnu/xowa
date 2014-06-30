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
package gplx.xowa.gui.urls.url_macros; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*; import gplx.xowa.gui.urls.*;
public class Xog_url_macro_mgr {
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public Xog_url_macro_mgr() {
		this.Init();
	}
	public void Lang_default_(byte[] v) {lang_default = v;} private byte[] lang_default = Bry_.new_ascii_("en");
	public Xog_url_macro_grp Types_mgr() {return types_mgr;} private Xog_url_macro_grp types_mgr = new Xog_url_macro_grp();
	public Xog_url_macro_grp Custom_mgr() {return custom_mgr;} private Xog_url_macro_grp custom_mgr = new Xog_url_macro_grp();
	public byte[] Fmt_or_null(byte[] raw) {
		int raw_len = raw.length;
		int dot_pos = -1;
		for (int i = 0; i < raw_len; i++) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Dot:
					dot_pos = i;
					break;
				case Byte_ascii.Colon:
					return Fmt_or_null__type(raw, raw_len, dot_pos, i);
			}
		}
		return Unhandled;
	}
	private byte[] Fmt_or_null__type(byte[] raw, int raw_len, int dot_pos, int colon_pos) {
		boolean dot_missing = dot_pos == -1;
		int type_bgn = dot_pos + 1, type_end = colon_pos;	// +1 to start type after dot;
		if (dot_missing) type_bgn = 0;
		Object custom_obj = custom_mgr.Trie().MatchAtCurExact(raw, 0, type_end);	// match entire prefix
		if (custom_obj == null) {
			Object type_obj = types_mgr.Trie().MatchAtCurExact(raw, type_bgn, type_end);
			if (type_obj == null) return Unhandled;	// type abrv is not known; exit; EX: "en.unknown:Page"; "Page"
			byte[] lang_bry = dot_missing ? lang_default : Bry_.Mid(raw, 0, dot_pos);
			Xog_url_macro_itm type_itm = (Xog_url_macro_itm)type_obj;
			return type_itm.Fmtr_exec(bfr, lang_bry, Bry_.Mid(raw, colon_pos + 1, raw_len));
		}
		else {
			Xog_url_macro_itm custom_itm = (Xog_url_macro_itm)custom_obj;
			return custom_itm.Fmtr_exec(bfr, Bry_.Mid(raw, colon_pos + 1, raw_len));
		}
	}
	private void Init() {
		types_mgr.Set("w"		, "~{0}.wikipedia.org/wiki/~{1}");
		types_mgr.Set("d"		, "~{0}.wiktionary.org/wiki/~{1}");
		types_mgr.Set("s"		, "~{0}.wikisource.org/wiki/~{1}");
		types_mgr.Set("v"		, "~{0}.wikivoyage.org/wiki/~{1}");
		types_mgr.Set("q"		, "~{0}.wikiquote.org/wiki/~{1}");
		types_mgr.Set("b"		, "~{0}.wikibooks.org/wiki/~{1}");
		types_mgr.Set("u"		, "~{0}.wikiversity.org/wiki/~{1}");
		types_mgr.Set("n"		, "~{0}.wikinews.org/wiki/~{1}");
		types_mgr.Set("a"		, "~{0}.wikia.com/wiki/~{1}");
		custom_mgr.Set("c"		, "commons.wikimedia.org/wiki/~{0}");
		custom_mgr.Set("wd"		, "www.wikidata.org/wiki/~{0}");
		custom_mgr.Set("wd.q"	, "www.wikidata.org/wiki/Q~{0}");
		custom_mgr.Set("wd.p"	, "www.wikidata.org/wiki/Property:P~{0}");
		custom_mgr.Set("sp"		, "wikispecies.wikimedia.org/wiki/~{0}");
		custom_mgr.Set("meta"	, "meta.wikimedia.org/wiki/~{0}");
		custom_mgr.Set("s.w"	, "simple.wikipedia.org/wiki/~{0}");
		custom_mgr.Set("s.d"	, "simple.wiktionary.org/wiki/~{0}");
		custom_mgr.Set("s.b"	, "simple.wikibooks.org/wiki/~{0}");
		custom_mgr.Set("s.q"	, "simple.wikiquote.org/wiki/~{0}");
		custom_mgr.Set("?"		, "Special:Search/~{0}?fulltext=y");
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lang_default))					return String_.new_utf8_(lang_default);
		else if	(ctx.Match(k, Invk_lang_default_))					lang_default = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_types))							return types_mgr;
		else if	(ctx.Match(k, Invk_custom))							return custom_mgr;
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_lang_default = "lang_default", Invk_lang_default_ = "lang_default_", Invk_types = "types", Invk_custom = "custom";
	public static final byte[] Unhandled = null;
}
