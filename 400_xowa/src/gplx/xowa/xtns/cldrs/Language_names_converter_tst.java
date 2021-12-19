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
package gplx.xowa.xtns.cldrs;
import gplx.core.tests.Gftest;
import gplx.langs.jsons.Json_doc;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class Language_names_converter_tst {
	private final Language_names_converter_fxt fxt = new Language_names_converter_fxt();
	@Test public void Parse_fil() {
		fxt.Exec__Parse(StringUtl.ConcatLinesNl
		( "/*ignore_bgn*/ $names = ["
		, "  'aa' => 'Afar', # comment_a "
		, "  'mic' => 'Mikmaq', # comment_m "
		, "  'zza' => 'Zaza', # comment_z "
		, "]; /*ignore_end*/"
		)
		, new Language_name[]
		{ fxt.Make__language_name("aa", "Afar", "comment_a")
		, fxt.Make__language_name("mic", "Mikmaq", "comment_m")
		, fxt.Make__language_name("zza", "Zaza", "comment_z")
		}, Json_doc.Make_str_by_apos
		( "["
		, "  {"
		, "    'code':'aa'"
		, "  , 'name':'Afar'"
		, "  , 'note':'comment_a'"
		, "  }"
		, ","
		, "  {"
		, "    'code':'mic'"
		, "  , 'name':'Mikmaq'"
		, "  , 'note':'comment_m'"
		, "  }"
		, ","
		, "  {"
		, "    'code':'zza'"
		, "  , 'name':'Zaza'"
		, "  , 'note':'comment_z'"
		, "  }"
		, "]"
		));
	}
//		@Test public void Convert() {
//			Language_names_converter converter = new Language_names_converter();
//			Language_name[] names = converter.Parse_fil(Io_url_.new_dir_("C:\\000\\100_bin\\200_server\\200_http\\100_apache\\100_v2.4\\htdocs\\mediawiki\\v1.29.1\\languages\\data\\Names.php"));
//			String json = converter.To_json(names);
//			Io_mgr.Instance.SaveFilStr(Io_url_.new_fil_("C:\\xowa\\bin\\any\\xowa\\cfg\\lang\\data\\names.json"), json);
//		}
}
class Language_names_converter_fxt {
	private final Language_names_converter converter = new Language_names_converter();
	public Language_name Make__language_name(String code, String name, String note) {return new Language_name(BryUtl.NewU8(code), BryUtl.NewU8(name), BryUtl.NewU8(note));}
	public void Exec__Parse(String src, Language_name[] expd_names, String expd_json) {
		Language_name[] actl = converter.Parse(BryUtl.NewU8(src));
		Gftest.EqAry(expd_names, actl);
		GfoTstr.EqLines(expd_json, converter.To_json(actl));
	}
}
