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
package gplx.xowa.xtns.jsonConfigs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.jsonConfigs.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import gplx.xowa.langs.*;
import gplx.langs.jsons.*;
public class Jscfg_scrib_lib_tst {
	private final    Jscfg_scrib_lib_fxt fxt = new Jscfg_scrib_lib_fxt();
	@Test   public void Get() {
		fxt.Init__page("Data:Test.tab", Json_doc.Make_str_by_apos
		( "{"
		, "  'data':"
		, "  ["
		, "    ["
		, "      'Q1'"
		, "    , 'Data:Q1'"
		, "    ]"
		, "  ,"
		, "    ["
		, "      'Q2'"
		, "    , 'Data:Q2'"
		, "    ]"
		, "  ]"
		, "}"
		));
		fxt.Test__get("Test.tab", String_.Concat_lines_nl_skip_last
		( "1="
		, "  data="
		, "    1="
		, "      1=Q1"
		, "      2=Data:Q1"
		, "    2="
		, "      1=Q2"
		, "      2=Data:Q2"
		));
	}
	@Test   public void Get_localize() {
		fxt.Init__page("Data:Test_localize.tab", Json_doc.Make_str_by_apos
		( "{"
		, "    'license': 'CC0-1.0',"
		, "    'description': {"
		, "        'de': 'Objekttabelle',"
		, "        'en': 'Object table'"
		, "    },"
		, "    'sources': 'Objects in Data:Data.tab completed by [https://www.wikidata.org Wikidata]',"
		, "    'schema': {"
		, "        'fields': ["
		, "            {"
		, "                'name': 'wikidataID',"
		, "                'type': 'String',"
		, "                'title': {"
		, "                    'de': 'Wikidata-Item',"
		, "                    'en': 'Wikidata item'"
		, "                }"
		, "            },"
		, "            {"
		, "                'name': 'wikidataLabel',"
		, "                'type': 'localized',"
		, "                'title': {"
		, "                    'de': 'Wikidata-Label',"
		, "                    'en': 'Wikidata label'"
		, "                }"
		, "            }"
		, "        ]"
		, "    },"
		, "    'data': ["
		, "        ["
		, "            'Q183',"
		, "            {"
		, "                'de': 'Deutschland',"
		, "                'en': 'Germany'"
		, "            }"
		, "        ],"
		, "        ["
		, "            'Q61912',"
		, "            {"
		, "                'de': 'Wertheim',"
		, "                'en': 'Wertheim am Main'"
		, "            }"
		, "        ]"
		, "    ]"
		, "}"
		));
		fxt.Test__get( "Test_localize.tab", String_.Concat_lines_nl_skip_last
		( "1="
		, "  license=CC0-1.0"
		, "  description=Object table"
		, "  sources=Objects in Data:Data.tab completed by [https://www.wikidata.org Wikidata]"
		, "  schema="
		, "    fields="
		, "      1="
		, "        name=wikidataID"
		, "        type=String"
		, "        title=Wikidata item"
		, "      2="
		, "        name=wikidataLabel"
		, "        type=localized"
		, "        title=Wikidata label"
		, "  data="
		, "    1="
		, "      1=Q183"
		, "      2=Germany"
		, "    2="
		, "      1=Q61912"
		, "      2=Wertheim am Main"
		));
	}
	@Test   public void pickLocalizedString() {
		Xol_lang_itm lang = fxt.Init__lang("zh-cn", "zh1,zh0");

		// match key
		fxt.Test__pickLocalizedString(lang, fxt.Init__picklocalizedStringKvs("fr", "zh-cn"), "zh-cn");

		// match fallback; note that zh1 is higher in fallback list, but lower in kvs
		fxt.Test__pickLocalizedString(lang, fxt.Init__picklocalizedStringKvs("zh0", "zh1"), "zh1");

		// match en if no key or fallbacks
		fxt.Test__pickLocalizedString(lang, fxt.Init__picklocalizedStringKvs("fr", "en"), "en");

		// pick 1st if no match
		fxt.Test__pickLocalizedString(lang, fxt.Init__picklocalizedStringKvs("fr", "de"), "fr");
	}
}	
class Jscfg_scrib_lib_fxt {
	private final    Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt();
	private final    Jscfg_scrib_lib lib;
	private final    Xowe_wiki commons_wiki;
	public Jscfg_scrib_lib_fxt() {
		fxt.Clear_for_lib();
		lib = new Jscfg_scrib_lib();
		lib.Init();
		lib.Core_(fxt.Core());
		this.commons_wiki = fxt.Parser_fxt().Wiki().Appe().Wiki_mgr().Get_by_or_make(gplx.xowa.wikis.domains.Xow_domain_itm_.Bry__commons).Init_assert();
	}
	public void Init__page(String page, String text) {
		fxt.Parser_fxt().Init_page_create(commons_wiki, page, text);
	}
	public Xol_lang_itm Init__lang(String key, String fallbacks) {
		Xol_lang_itm lang = new Xol_lang_itm(fxt.Core().App().Lang_mgr(), Bry_.new_u8(key));
		lang.Fallback_bry_(Bry_.new_a7(fallbacks));
		return lang;
	}
	public Keyval[] Init__picklocalizedStringKvs(String... vals) {
		int len = vals.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			String val = vals[i];
			rv[i] = Keyval_.new_(val, val);
		}
		return rv;
	}
	public void Test__get(String page, String expd) {
		fxt.Test_scrib_proc_str_ary(lib, Jscfg_scrib_lib.Invk_get, Keyval_.Ary(Keyval_.int_(1, page)), expd);
	}
	public void Test__pickLocalizedString(Xol_lang_itm lang, Keyval[] kv_ary, String expd) {
		Keyval actl_kv = Jscfg_localizer.pickLocalizedString(lang, "key", kv_ary);
		Gftest.Eq__obj_or_null(expd, actl_kv.Val());
	}
}
