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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.*;
class Xoud_opt_scope {
	public Xoud_opt_scope(int lang_id, int type_id) {this.lang_id = lang_id; this.type_id = type_id;}
	public int Lang_id() {return lang_id;} private final int lang_id;
	public int Type_id() {return type_id;} private final int type_id;
	public String To_str() {
		String lang_str = lang_id == Lang_id_wildcard ? "*" : String_.new_u8(Xol_lang_itm_.Get_by_id(lang_id).Key());
		String type_str = type_id == Lang_id_wildcard ? "*" : String_.new_u8(Xow_domain_type_.Get_type_as_bry(type_id));
		return lang_str + "." + type_str;
	}
	public static final int Lang_id_wildcard = -1, Type_id_wildcard = -1;
	public static final Xoud_opt_scope App = new Xoud_opt_scope(Lang_id_wildcard, Type_id_wildcard);
}
class Xoud_opt_scope_parser {
	private Gfo_usr_dlg usr_dlg; private final List_adp list = List_adp_.new_();
	public Xoud_opt_scope[] Parse(byte[] src) {
		usr_dlg = Gfo_usr_dlg_.I;
		list.Clear();
		int pos = 0; int src_len = src.length;
		while (pos < src_len) {
			int comma_pos = Bry_finder.Find_fwd(src, Byte_ascii.Comma, pos, src_len); if (comma_pos == Bry_finder.Not_found) comma_pos = src_len;
			Xoud_opt_scope itm = Parse_itm(src, pos, comma_pos);
			if (itm == Xoud_opt_scope.App) return Ary_app;
			list.Add(itm);
			pos = comma_pos + 1;
		}
		return (Xoud_opt_scope[])list.To_ary_and_clear(Xoud_opt_scope.class);
	}
	public Xoud_opt_scope Parse_itm(byte[] src, int bgn, int end) {
		int lang_dot = Bry_finder.Find_fwd(src, Byte_ascii.Dot, bgn, end);					if (lang_dot == Bry_finder.Not_found) return Warn("scope.parse.missing_lang_dot: src=~{0}", src, bgn, end);
		int lang_id = Int_.MinValue;
		if (lang_dot == 1 && src[bgn] == Byte_ascii.Star)
			lang_id = Xoud_opt_scope.Lang_id_wildcard;
		else {
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_null(src, bgn, lang_dot);	if (lang_itm == null) return Warn("scope.parse.invalid_lang: src=~{0}", src, bgn, end);
			lang_id = lang_itm.Id();
		}
		Object type_tid_obj = btrie_by_type.Match_bgn(src, lang_dot + 1, end);				if (type_tid_obj == null) return Warn("scope.parse.invalid_type: src=~{0}", src, bgn, end);
		int type_id = ((Int_obj_val)type_tid_obj).Val();
		return new Xoud_opt_scope(lang_id, type_id);
	}
	private Xoud_opt_scope Warn(String fmt, byte[] src, int bgn, int end) {
		usr_dlg.Warn_many("", "", fmt, String_.new_u8(src, bgn, end));
		return Xoud_opt_scope.App;
	}
	private static final Btrie_slim_mgr btrie_by_type = Btrie_slim_mgr.cs_()
	.Add_str_int("w"			, Xow_domain_type_.Tid_wikipedia)
	.Add_str_int("d"			, Xow_domain_type_.Tid_wiktionary)
	.Add_str_int("s"			, Xow_domain_type_.Tid_wikisource)
	.Add_str_int("v"			, Xow_domain_type_.Tid_wikivoyage)
	.Add_str_int("q"			, Xow_domain_type_.Tid_wikiquote)
	.Add_str_int("b"			, Xow_domain_type_.Tid_wikibooks)
	.Add_str_int("u"			, Xow_domain_type_.Tid_wikiversity)
	.Add_str_int("n"			, Xow_domain_type_.Tid_wikinews)
	.Add_str_int("*"			, Xoud_opt_scope.Type_id_wildcard)
	.Add_str_int("xowa"			, Xow_domain_type_.Tid_home)
	.Add_str_int("wd"			, Xow_domain_type_.Tid_wikidata)
	.Add_str_int("c"			, Xow_domain_type_.Tid_commons)
	.Add_str_int("species"		, Xow_domain_type_.Tid_species)
	.Add_str_int("meta"			, Xow_domain_type_.Tid_meta)
	.Add_str_int("mw"			, Xow_domain_type_.Tid_mediawiki)
	.Add_str_int("wmf"			, Xow_domain_type_.Tid_wmforg)
	;
	private static final Xoud_opt_scope[] Ary_app = new Xoud_opt_scope[] {Xoud_opt_scope.App};
}
