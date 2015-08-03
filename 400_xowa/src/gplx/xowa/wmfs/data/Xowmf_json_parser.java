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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.core.json.*;
class Xowmf_json_parser {
	private byte[] src;
	public void Parse_root(Json_nde root, Xowmf_wiki_data wiki_data) {
		this.src = root.Doc().Src();
		int len = root.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv sub = root.Get_at_as_kv(i);
			byte[] sub_key = sub.Key_as_bry();
			switch (nde_hash.Get_as_int(sub_key)) {
				case Tid_general			: Parse_kv(Json_nde.cast_(sub.Val()), wiki_data.General_list()); break;
				case Tid_namespaces			: Parse_namespaces(Json_nde.cast_(sub.Val()), wiki_data.Namespaces_list()); break;
				case Tid_statistics			: Parse_kv(Json_nde.cast_(sub.Val()), wiki_data.Statistics_list()); break;
				case Tid_interwikimap		: Parse_interwikimap(Json_ary.cast(sub.Val()), wiki_data.Interwikimap_list()); break;
				case Tid_namespacealiases	: Parse_namespacealiases(Json_ary.cast(sub.Val()), wiki_data.Namespacealiases_list()); break;
				case Tid_specialpagealiases	: Parse_specialpagealiases(Json_ary.cast(sub.Val()), wiki_data.Specialpagealiases_list()); break;
				case Tid_libraries			: Parse_libraries(Json_ary.cast(sub.Val()), wiki_data.Libraries_list()); break;
				case Tid_extensions			: Parse_extensions(Json_ary.cast(sub.Val()), wiki_data.Extensions_list()); break;
				case Tid_skins				: Parse_skins(Json_ary.cast(sub.Val()), wiki_data.Skins_list()); break;
				case Tid_magicwords			: Parse_magicwords(Json_ary.cast(sub.Val()), wiki_data.Magicwords_list()); break;
				case Tid_functionhooks		: Parse_val(Json_ary.cast(sub.Val()), wiki_data.Functionhooks_list()); break;
				case Tid_showhooks			: Parse_showhooks(Json_ary.cast(sub.Val()), wiki_data.Showhooks_list()); break;
				case Tid_extensiontags		: Parse_val(Json_ary.cast(sub.Val()), wiki_data.Extensiontags_list()); break;
				case Tid_protocols			: Parse_val(Json_ary.cast(sub.Val()), wiki_data.Protocols_list()); break;
				case Tid_defaultoptions		: Parse_kv(Json_nde.cast_(sub.Val()), wiki_data.Defaultoptions_list()); break;
				case Tid_languages			: Parse_languages(Json_ary.cast(sub.Val()), wiki_data.Languages_list()); break;
			}
		}
	}
//		private void Parse_general(Json_nde nde, Ordered_hash list) {}
	private void Parse_namespaces(Json_nde nde, Ordered_hash list) {}
//		private void Parse_statistics(Json_nde nde, Ordered_hash list) {}
	private void Parse_interwikimap(Json_ary ary, Ordered_hash list) {}
	private void Parse_namespacealiases(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = ary.Get_at_as_nde(i);
			Fail_if_wrong_count(sub, sub.Len(), 2, "namespacealias");
			byte[] id_bry = sub.Get_bry(Atr_namespacealias_id);
			int id = Bry_.To_int_or(id_bry, Int_.MaxValue); if (id == Int_.MaxValue) throw Err_.new_("wmf.data", "invalid id for namespacealias", "id", id_bry, "src", Extract(sub));
			list.Add(id, new Xowmf_namespacealias_itm(id, sub.Get_bry(Atr_namespacealias_alias)));
		}
	}
	private void Parse_specialpagealiases(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = ary.Get_at_as_nde(i);
			Fail_if_wrong_count(sub, sub.Len(), 2, "specialpagealias");
			byte[] key = sub.Get_bry(Atr_specialpagealias_realname);
			Json_ary aliases_ary = Json_ary.cast(sub.Get(Atr_specialpagealias_aliases));
			list.Add(key, new Xowmf_specialpagealias_itm(key, aliases_ary.Xto_bry_ary()));
		}
	}
	private void Parse_libraries(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = ary.Get_at_as_nde(i);
			Fail_if_wrong_count(sub, sub.Len(), 2, "library");
			byte[] key = sub.Get_bry(Atr_libraries_name);
			list.Add(key, new Xowmf_library_itm(key, sub.Get_bry(Atr_libraries_version)));
		}
	}
	private void Parse_extensions(Json_ary ary, Ordered_hash list) {}
	private void Parse_skins(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = ary.Get_at_as_nde(i);
			Fail_if_wrong_count(sub, sub.Len(), 3, "skin");
			byte[] key = sub.Get_bry(Atr_skins_code);
			list.Add(key, new Xowmf_skin_itm(key, sub.Get_bry(Atr_skins_dflt), sub.Get_bry(Atr_skins_name)));
		}
	}
	private void Parse_magicwords(Json_ary ary, Ordered_hash list) {}
//		private void Parse_functionhooks(Json_ary ary, Ordered_hash list) {}
	private void Parse_showhooks(Json_ary ary, Ordered_hash list) {}
//		private void Parse_extensiontags(Json_ary ary, Ordered_hash list) {}
	private void Parse_languages(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = ary.Get_at_as_nde(i);
			Fail_if_wrong_count(sub, sub.Len(), 2, "language");
			byte[] key = sub.Get_bry(Atr_languages_code);
			list.Add(key, new Xowmf_language_itm(key, sub.Get_bry(Atr_languages_name)));
		}
	}
	private void Parse_val(Json_ary ary, Ordered_hash list) {
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			byte[] val = ary.Get_at(i).Data_bry();
			list.Add(val, new Xowmf_val_itm(val));
		}
	}
	private void Parse_kv(Json_nde nde, Ordered_hash list) {
		int len = nde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv sub = nde.Get_at_as_kv(i);
			byte[] key = sub.Key_as_bry();
			list.Add(key, new Xowmf_kv_itm(key, sub.Val_as_bry()));
		}
	}
	private byte[] Extract(Json_itm itm) {return Bry_.Mid(src, itm.Src_bgn(), itm.Src_end());}
	private void Fail_if_wrong_count(Json_itm itm, int expd, int actl, String type) {
		if (expd != actl) throw Err_.new_("wmf.data", "node does not have expected attribute count", "type", type, "expd", expd, "actl", actl, "src", Extract(itm));
	}
	private static final int
	  Tid_general				=  0
	, Tid_namespaces			=  1
	, Tid_statistics			=  2
	, Tid_interwikimap			=  3
	, Tid_namespacealiases		=  4
	, Tid_specialpagealiases	=  5
	, Tid_libraries				=  6
	, Tid_extensions			=  7
	, Tid_skins					=  8
	, Tid_magicwords			=  9
	, Tid_functionhooks			= 10
	, Tid_showhooks				= 11
	, Tid_extensiontags			= 12
	, Tid_protocols				= 13
	, Tid_defaultoptions		= 14
	, Tid_languages				= 15
	;
	private static final byte[]
	  Nde_general				= Bry_.new_a7("general")
	, Nde_namespaces			= Bry_.new_a7("namespaces")
	, Nde_statistics			= Bry_.new_a7("statistics")
	, Nde_interwikimap			= Bry_.new_a7("interwikimap")
	, Nde_namespacealiases		= Bry_.new_a7("namespacealiases")
	, Nde_specialpagealiases	= Bry_.new_a7("specialpagealiases")
	, Nde_libraries				= Bry_.new_a7("libraries")
	, Nde_extensions			= Bry_.new_a7("extensions")
	, Nde_skins					= Bry_.new_a7("skins")
	, Nde_magicwords			= Bry_.new_a7("magicwords")
	, Nde_functionhooks			= Bry_.new_a7("functionhooks")
	, Nde_showhooks				= Bry_.new_a7("showhooks")
	, Nde_extensiontags			= Bry_.new_a7("extensiontags")
	, Nde_protocols				= Bry_.new_a7("protocols")
	, Nde_defaultoptions		= Bry_.new_a7("defaultoptions")
	, Nde_languages				= Bry_.new_a7("languages")
	, Atr_namespacealias_id	= Bry_.new_a7("id"), Atr_namespacealias_alias = Bry_.new_a7("*")
	, Atr_specialpagealias_realname	= Bry_.new_a7("realname"), Atr_specialpagealias_aliases = Bry_.new_a7("aliases")
	, Atr_libraries_name	= Bry_.new_a7("name"), Atr_libraries_version = Bry_.new_a7("version")
	, Atr_skins_code		= Bry_.new_a7("code"), Atr_skins_dflt = Bry_.new_a7("de"+"fault"), Atr_skins_name = Bry_.new_a7("*")
	, Atr_languages_code	= Bry_.new_a7("code"), Atr_languages_name = Bry_.new_a7("*")
	;
	private static final Hash_adp_bry nde_hash = Hash_adp_bry.cs()
	.Add_bry_int(Nde_general				, Tid_general)
	.Add_bry_int(Nde_namespaces				, Tid_namespaces)
	.Add_bry_int(Nde_statistics				, Tid_statistics)
	.Add_bry_int(Nde_interwikimap			, Tid_interwikimap)
	.Add_bry_int(Nde_namespacealiases		, Tid_namespacealiases)
	.Add_bry_int(Nde_specialpagealiases		, Tid_specialpagealiases)
	.Add_bry_int(Nde_libraries				, Tid_libraries)
	.Add_bry_int(Nde_extensions				, Tid_extensions)
	.Add_bry_int(Nde_skins					, Tid_skins)
	.Add_bry_int(Nde_magicwords				, Tid_magicwords)
	.Add_bry_int(Nde_functionhooks			, Tid_functionhooks)
	.Add_bry_int(Nde_showhooks				, Tid_showhooks)
	.Add_bry_int(Nde_extensiontags			, Tid_extensiontags)
	.Add_bry_int(Nde_protocols				, Tid_protocols)
	.Add_bry_int(Nde_defaultoptions			, Tid_defaultoptions)
	.Add_bry_int(Nde_languages				, Tid_languages)
	;
}
/*
"general": {
    "mainpage": "Main Page",
    "super": "https://en.wikipedia.org/wiki/Main_Page",
    "sitename": "Wikipedia",
    "logo": "//en.wikipedia.org/static/images/project-logos/enwiki.png",
    "generator": "MediaWiki 1.26wmf6",
    "phpversion": "5.6.99-hhvm",
    "phpsapi": "srv",
    "hhvmversion": "3.6.1",
    "dbtype": "mysql",
    "dbversion": "10.0.16-MariaDB-log",
    "imagewhitelistenabled": "",
    "langconversion": "",
    "titleconversion": "",
    "linkprefixcharset": "",
    "linkprefix": "",
    "linktrail": "/^([a-z]+)(.*)$/sD",
    "legaltitlechars": " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+",
    "invalidusernamechars": "@:",
    "git-hash": "84635a11fc9b739d84d9d108565d53d4ff4342b9",
    "git-branch": "wmf/1.26wmf6",
    "case": "first-letter",
    "lang": "en",
    "fallback": [],
    "fallback8bitEncoding": "windows-1252",
    "writeapi": "",
    "timezone": "UTC",
    "timeoffset": 0,
    "articlepath": "/wiki/$1",
    "scriptpath": "/w",
    "script": "/w/index.php",
    "variantarticlepath": false,
    "server": "//en.wikipedia.org",
    "servername": "en.wikipedia.org",
    "wikiid": "enwiki",
    "time": "2015-05-25T10:52:53Z",
    "misermode": "",
    "maxuploadsize": 1048576000,
    "thumblimits": [
        120,
        150,
        180,
        200,
        220,
        250,
        300
    ],
    "imagelimits": [
        {
            "width": 320,
            "height": 240
        },
        {
            "width": 640,
            "height": 480
        },
        {
            "width": 800,
            "height": 600
        },
        {
            "width": 1024,
            "height": 768
        },
        {
            "width": 1280,
            "height": 1024
        }
    ],
    "favicon": "//en.wikipedia.org/static/favicon/wikipedia.ico"
},

"namespaces": {
"-2": {
    "id": -2,
    "case": "first-letter",
    "canonical": "Media",
    "*": "Media"
},
interwikimap
{
    "prefix": "zh-classical",
    "local": "",
    "language": "\u6587\u8a00",
    "url": "https://zh-classical.wikipedia.org/wiki/$1",
    "protorel": ""
},

extension
{
    "type": "other",
    "name": "CirrusSearch",
    "descriptionmsg": "cirrussearch-desc",
    "author": "Nik Everett, Chad Horohoe",
    "url": "https://www.mediawiki.org/wiki/Extension:CirrusSearch",
    "version": "0.2",
    "vcs-system": "git",
    "vcs-version": "841147fa90ceafc64fb1573d6a6c010b04df2f64",
    "vcs-url": "https://git.wikimedia.org/tree/mediawiki%2Fextensions%2FCirrusSearch.git/841147fa90ceafc64fb1573d6a6c010b04df2f64",
    "vcs-date": "2015-05-13T16:18:35Z",
    "license-name": "GPL-2.0+",
    "license": "/wiki/Special:Version/License/CirrusSearch",
    "credits": "/wiki/Special:Version/Credits/CirrusSearch"
},
		
magicword
{
    "name": "revisionday",
    "aliases": [
        "REVISIONDAY"
    ],
    "case-sensitive": ""
},

showhook
{
    "name": "APIGetAllowedParams",
    "subscribers": [
        "FlaggedRevsApiHooks::addApiRevisionParams",
        "ConfirmEditHooks::APIGetAllowedParams",
        "CentralAuthHooks::onAPIGetAllowedParams",
        "ApiParseExtender::onAPIGetAllowedParams"
    ]
},
*/
