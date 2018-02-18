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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
import gplx.xowa.langs.*;
import gplx.xowa.mediawiki.*;
class JCUtils {
	/**
	* Find a message in a dictionary for the given language,
	* or use language fallbacks if message is not defined.
	* @param stdClass map Dictionary of languageCode => String
	* @param Language|StubUserLang lang language Object
	* @param boolean|String $defaultValue if non-false, use this value in case no fallback and no 'en'
	* @return String message from the dictionary or "" if nothing found
	*/
	public static String pickLocalizedString(XophpStdClass map, Xol_lang_itm lang) {return pickLocalizedString(map, lang, null);}
	public static String pickLocalizedString(XophpStdClass map, Xol_lang_itm lang, String defaultValue) {
		String langCode = lang.Key_str();
		if (map.Has(langCode)) {
			return map.Get_by_as_str(langCode);
		}
		/*
		for+each (lang.getFallbackLanguages() as l) {
			if (property_exists(map, l)) {
				return map.l;
			}
		}
		*/

		// If fallbacks fail, check if english is defined
		if (map.Has("en") ) {
			return map.Get_by_as_str("en");
		}

		// We have a custom default, return that
		if (defaultValue != null) {
			return null;
		}

		// Return first available value, or an empty String
		// There might be a better way to get the first value from an Object
		return map.Len() == 0 ? "" : map.Get_at_as_str(0);
	}
}
