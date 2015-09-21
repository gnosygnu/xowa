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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
import gplx.core.primitives.*; import gplx.langs.jsons.*;
class Site_json_parser {
	private final Json_parser json_parser;
	private final Json_parser__list_nde__base parser__basic = new Json_parser__list_nde__base();
	private final Site_meta_parser__general parser__general = new Site_meta_parser__general();
	private final Site_meta_parser__namespace parser__namespace = new Site_meta_parser__namespace();
	private final Site_meta_parser__statistic parser__statistic = new Site_meta_parser__statistic();
	private final Site_meta_parser__interwikimap parser__interwiki = new Site_meta_parser__interwikimap();
	private final Site_meta_parser__namespacealias parser__namespacealias = new Site_meta_parser__namespacealias();
	private final Site_meta_parser__specialpagealias parser__specialpagealias = new Site_meta_parser__specialpagealias();
	private final Site_meta_parser__library parser__library = new Site_meta_parser__library();
	private final Site_meta_parser__extension parser__extension = new Site_meta_parser__extension();
	private final Site_meta_parser__skin parser__skin = new Site_meta_parser__skin();
	private final Site_meta_parser__magicword parser__magicword = new Site_meta_parser__magicword();
	private final Site_meta_parser__showhook parser__showhook = new Site_meta_parser__showhook();
	private final Site_meta_parser__language parser__language = new Site_meta_parser__language();
	public Site_json_parser(Json_parser json_parser) {this.json_parser = json_parser;}
	public void Parse_root(Site_meta_itm rv, String context, byte[] src) {
		Json_doc jdoc = json_parser.Parse(src);
		Parse_root(rv, context, jdoc.Root_nde().Get_at_as_kv(1).Val_as_nde());
	}
	public void Parse_root(Site_meta_itm rv, String context, Json_nde root) {
		int len = root.Len();			
		for (int i = 0; i < len; ++i) {
			Json_kv sub = root.Get_at_as_kv(i);
			byte[] sub_key = sub.Key_as_bry();
			switch (nde_hash.Get_as_int(sub_key)) {
				case Tid_general			: parser__general.Parse(context, rv.General_list(), sub.Val_as_nde()); break;
				case Tid_namespace			: parser__namespace.Parse(context, rv.Namespace_list(), sub.Val_as_nde()); break;
				case Tid_statistic			: parser__statistic.Parse(context, rv.Statistic_itm(), sub.Val_as_nde()); break;
				case Tid_interwikimap		: parser__interwiki.Parse(context, rv.Interwikimap_list(), sub.Val_as_ary()); break;
				case Tid_namespacealias		: parser__namespacealias.Parse(context, rv.Namespacealias_list(), sub.Val_as_ary()); break;
				case Tid_specialpagealias	: parser__specialpagealias.Parse(context, rv.Specialpagealias_list(), sub.Val_as_ary()); break;
				case Tid_library			: parser__library.Parse(context, rv.Library_list(), sub.Val_as_ary()); break;
				case Tid_extension			: parser__extension.Parse(context, rv.Extension_list(), sub.Val_as_ary()); break;
				case Tid_skin				: parser__skin.Parse(context, rv.Skin_list(), sub.Val_as_ary()); break;
				case Tid_magicword			: parser__magicword.Parse(context, rv.Magicword_list(), sub.Val_as_ary()); break;
				case Tid_functionhook		: parser__basic.Parse_to_list_as_bry(context, sub.Val_as_ary(), rv.Functionhook_list()); break;
				case Tid_showhook			: parser__showhook.Parse(context, rv.Showhook_list(), sub.Val_as_ary()); break;
				case Tid_extensiontag		: parser__basic.Parse_to_list_as_bry(context, sub.Val_as_ary(), rv.Extensiontag_list()); break;
				case Tid_protocol			: parser__basic.Parse_to_list_as_bry(context, sub.Val_as_ary(), rv.Protocol_list()); break;
				case Tid_defaultoption		: parser__basic.Parse_to_list_as_kv(context, sub.Val_as_nde(), rv.Defaultoption_list()); break;
				case Tid_language			: parser__language.Parse(context, rv.Language_list(), sub.Val_as_ary()); break;
			}
		}
	}
	private static final int
	  Tid_general				=  0
	, Tid_namespace				=  1
	, Tid_statistic				=  2
	, Tid_interwikimap			=  3
	, Tid_namespacealias		=  4
	, Tid_specialpagealias		=  5
	, Tid_library				=  6
	, Tid_extension				=  7
	, Tid_skin					=  8
	, Tid_magicword				=  9
	, Tid_functionhook			= 10
	, Tid_showhook				= 11
	, Tid_extensiontag			= 12
	, Tid_protocol				= 13
	, Tid_defaultoption			= 14
	, Tid_language				= 15
	;
	private static final Hash_adp_bry nde_hash = Hash_adp_bry.cs()
	.Add_str_int("general"					, Tid_general)
	.Add_str_int("namespaces"				, Tid_namespace)
	.Add_str_int("statistics"				, Tid_statistic)
	.Add_str_int("interwikimap"				, Tid_interwikimap)
	.Add_str_int("namespacealiases"			, Tid_namespacealias)
	.Add_str_int("specialpagealiases"		, Tid_specialpagealias)
	.Add_str_int("libraries"				, Tid_library)
	.Add_str_int("extensions"				, Tid_extension)
	.Add_str_int("skins"					, Tid_skin)
	.Add_str_int("magicwords"				, Tid_magicword)
	.Add_str_int("functionhooks"			, Tid_functionhook)
	.Add_str_int("showhooks"				, Tid_showhook)
	.Add_str_int("extensiontags"			, Tid_extensiontag)
	.Add_str_int("protocols"				, Tid_protocol)
	.Add_str_int("defaultoptions"			, Tid_defaultoption)
	.Add_str_int("languages"				, Tid_language)
	;
}
