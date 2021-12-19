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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
public interface Xoctg_collation_wkr {
	String Type_name();
	String Wm_name();
	byte[] Get_sortkey(byte[] src);
}
class Xoctg_collation_wkr__uppercase implements Xoctg_collation_wkr {
	private final Xow_wiki wiki;
	public Xoctg_collation_wkr__uppercase(Xow_wiki wiki) {this.wiki = wiki;}
	public String Type_name() {return "uppercase";}
	public String Wm_name() {return this.Type_name();}
	public byte[] Get_sortkey(byte[] src) {
		return wiki.Lang().Case_mgr().Case_build_upper(src);
	}
}
class Xoctg_collation_wkr__identity implements Xoctg_collation_wkr {
	public String Type_name() {return "identity";}
	public String Wm_name() {return this.Type_name();}
	public byte[] Get_sortkey(byte[] src) {
		return src;
	}
}
class Xoctg_collation_wkr__uca implements Xoctg_collation_wkr {
	private gplx.core.intls.ucas.Uca_collator collator;
	public Xoctg_collation_wkr__uca(String wm_name, String icu_locale) {
		// REF:"includes/collation/Collation.php|factory" "includes/collation/IcuCollation.php|__construct"
		this.wm_name = wm_name;
		// remove anything after "@"; EX: 'svwikisource' => 'uca-sv@collation=standard', // T48058
		int at_pos = StringUtl.FindFwd(icu_locale, "@");
		if (at_pos != StringUtl.FindNone)
			icu_locale = StringUtl.Mid(icu_locale, 0, at_pos);

		// handle "default-u-kn"
		if (StringUtl.Eq(icu_locale, "default-u-kn"))
			this.icu_locale = "en";
		else if (StringUtl.Eq(icu_locale, "root"))
			this.icu_locale = "en";
		else
			this.icu_locale = icu_locale;
		this.numeric_sorting = StringUtl.HasAtEnd(icu_locale, "-u-kn");
	}
	public String Type_name() {return wm_name;}	private final String wm_name;
	public String Wm_name() {return this.Type_name();}
	public String Icu_locale() {return icu_locale;} private final String icu_locale;
	public boolean Numeric_sorting() {return numeric_sorting;} private final boolean numeric_sorting;
	public byte[] Get_sortkey(byte[] src) {
		if (collator == null) collator = gplx.core.intls.ucas.Uca_collator_.New(icu_locale, numeric_sorting);
		return collator.Get_sortkey(StringUtl.NewU8(src));
	}
}
