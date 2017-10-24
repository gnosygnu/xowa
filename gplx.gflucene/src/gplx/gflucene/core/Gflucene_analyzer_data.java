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
package gplx.gflucene.core; import gplx.*; import gplx.gflucene.*;
public class Gflucene_analyzer_data {
	public final    String key;
	public Gflucene_analyzer_data(String key) {
		this.key = key;
	}
	public static Gflucene_analyzer_data New_data_from_locale(String locale) {
		String key = null;
		if 		(String_.Eq(locale, "en"))           key = "standard"; // NOTE: en exists but use standard for now
		else if (String_.EqAny(locale
		, "ar", "bg", "ca", "ckb", "cz", "da", "de", "el", "es", "eu", "fa", "fi", "fr", "ga", "gl", "hi"
		, "hu", "hy", "id", "it", "lt", "lv", "nl", "no", "pt", "ro", "ru", "sv", "th", "tr")
		)                                            key = locale;
		else if (String_.EqAny(locale
		, "zh", "ja", "ko")
		)                                            key = "cjk";
		else                                         key = "standard";
		return new Gflucene_analyzer_data(key);
	}
}
