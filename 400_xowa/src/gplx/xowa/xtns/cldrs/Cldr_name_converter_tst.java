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
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url_;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import org.junit.Test;
public class Cldr_name_converter_tst {
	private final Cldr_name_converter_fxt fxt = new Cldr_name_converter_fxt();
	@Test public void Extract_key_or_fail() {
		fxt.Test__Extract_key_or_fail("CldrNamesEn.php"	, "En");
		fxt.Test__Extract_key_or_fail("CldrNameEn.php"	, null);
		fxt.Test__Extract_key_or_fail("CldrNamesEn.txt"	, null);
	}
	@Test public void Parse_fil() {
		Cldr_name_file file = fxt.Exec__Parse_fil("En", StringUtl.ConcatLinesNl
		( "$languageNames = ["
		, "  'aa' => 'Afar',"
		, "  'mic' => 'Mi\\'kmaq',"
		, "  'zza' => 'Zaza',"
		, "];"
		, ""
		, "$currencyNames = ["
		, "	'ADP' => 'Andorran Peseta',"
		, "	'ZWR' => 'Zimbabwean Dollar (2008)',"
		, "];"
		, ""
		, "$currencySymbols = ["
		, "	'JPY' => '¥',"
		, "	'USD' => '$',"
		, "];"
		, ""
		, "$countryNames = ["
		, "	'AC' => 'Ascension Island',"
		, "	'ZW' => 'Zimbabwe',"
		, "];"
		, ""
		, "$timeUnits = ["
		, "	'century-one' => '{0} century',"
		, "	'year-short-past-other' => '{0} yr. ago',"
		, "];"
		));
		Assert__parse_fil(file);

		String expd = StringUtl.ConcatLinesNl
		( "{"
		, "  \"languageNames\":"
		, "  {"
		, "    \"aa\":\"Afar\""
		, "  , \"mic\":\"Mi'kmaq\""
		, "  , \"zza\":\"Zaza\""
		, "  }"
		, ", \"currencyNames\":"
		, "  {"
		, "    \"ADP\":\"Andorran Peseta\""
		, "  , \"ZWR\":\"Zimbabwean Dollar (2008)\""
		, "  }"
		, ", \"currencySymbols\":"
		, "  {"
		, "    \"JPY\":\"¥\""
		, "  , \"USD\":\"$\""
		, "  }"
		, ", \"countryNames\":"
		, "  {"
		, "    \"AC\":\"Ascension Island\""
		, "  , \"ZW\":\"Zimbabwe\""
		, "  }"
		, ", \"timeUnits\":"
		, "  {"
		, "    \"century-one\":\"{0} century\""
		, "  , \"year-short-past-other\":\"{0} yr. ago\""
		, "  }"
		, "}"
		);
		fxt.Test__To_json(file, expd);

		file = fxt.Exec__Parse_json("En", expd);
		Assert__parse_fil(file);
	}
	private void Assert__parse_fil(Cldr_name_file file) {
		fxt.Test__node(file.Language_names()
			, KeyVal.NewStr("aa", "Afar")
			, KeyVal.NewStr("mic", "Mi'kmaq")
			, KeyVal.NewStr("zza", "Zaza")
		);
		fxt.Test__node(file.Currency_names()
			, KeyVal.NewStr("ADP", "Andorran Peseta")
			, KeyVal.NewStr("ZWR", "Zimbabwean Dollar (2008)")
		);
		fxt.Test__node(file.Currency_symbols()
			, KeyVal.NewStr("JPY", "¥")
			, KeyVal.NewStr("USD", "$")
		);
		fxt.Test__node(file.Country_names()
			, KeyVal.NewStr("AC", "Ascension Island")
			, KeyVal.NewStr("ZW", "Zimbabwe")
		);
		fxt.Test__node(file.Time_units()
			, KeyVal.NewStr("century-one", "{0} century")
			, KeyVal.NewStr("year-short-past-other", "{0} yr. ago")
		);
	}
//		@Test public void Smoke() {
//			Cldr_name_converter bldr = new Cldr_name_converter();
//			bldr.Convert(Io_url_.new_dir_("C:\\000\\100_bin\\200_server\\200_http\\100_apache\\100_v2.4\\htdocs\\mediawiki\\v1.29.1\\extensions\\cldr\\CldrNames\\"), Io_url_.new_dir_("C:\\xowa\\bin\\any\\xowa\\xtns\\cldr\\"));
//		}
}
class Cldr_name_converter_fxt {
	private final Cldr_name_converter bldr = new Cldr_name_converter();
	private final String dir_name = "mem/CldrNames/";

	public void Init__file(String fil_name, String txt) {
		Io_mgr.Instance.SaveFilStr(Io_url_.new_fil_(dir_name + fil_name), txt);
	}
	public void Test__Extract_key_or_fail(String fil_name, String expd) {
		String actl = null;
		try {
			actl = bldr.Extract_key_or_fail(fil_name);
		}
		catch (Exception exc) {
			actl = null;
		}
		if (expd == null && actl == null) {
		}
		else if (expd == null) {
			GfoTstr.Fail("expecting null; got " + actl);
		}
		else if (actl == null) {
			GfoTstr.Fail("got null; expected " + expd);
		}
		else {
			GfoTstr.Eq(expd, actl);
		}
	}
	public Cldr_name_file[] Exec__Parse_dir() {
		return bldr.Parse_dir(Io_url_.new_fil_(dir_name));
	}
	public Cldr_name_file Exec__Parse_fil(String key, String src) {
		return bldr.Parse_fil(key, BryUtl.NewU8(src));
	}
	public Cldr_name_file Exec__Parse_json(String key, String json) {
		Cldr_name_loader loader = new Cldr_name_loader(Io_url_.mem_dir_("mem/Cldr"));
		return loader.Parse(key, BryUtl.NewU8(json));
	}
//
	public void Test__node(Ordered_hash hash, KeyVal... expd) {
		KeyVal[] actl = (KeyVal[])hash.ToAry(KeyVal.class);
		GfoTstr.EqLines(KeyValUtl.AryToStr(expd), KeyValUtl.AryToStr(actl), "cldr_names_comp_failed");
	}
	public void Test__To_json(Cldr_name_file file, String expd) {
		String actl = bldr.To_json(file);
		GfoTstr.EqLines(expd, actl, "json_failed");
	}
}
