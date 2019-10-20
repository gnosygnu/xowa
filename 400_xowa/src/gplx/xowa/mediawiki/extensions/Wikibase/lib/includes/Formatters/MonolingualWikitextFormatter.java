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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Formatters; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.*;
import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Interfaces.ValueFormatters.*;
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Common.DataValues.*;
// REF.MW:https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/MonolingualWikitextFormatter.php
public class MonolingualWikitextFormatter implements ValueFormatter {
	/**
	* @see ValueFormatter::format
	*
	* @param MonolingualTextValue $value
	*
	* @throws InvalidArgumentException
	* @return String Wikitext
	*/
	public Object format(Object val) {
//			if ( !( $value instanceof MonolingualTextValue ) ) {
//				throw new InvalidArgumentException( '$value must be a MonolingualTextValue' );
//			}
//			MonolingualTextValue monolingualVal = ((MonolingualTextValue)val);
//			String text = monolingualVal.getText();
//			String languageCode = monolingualVal.getLanguageCode();
//			return String_.Concat("<span lang=\"", XomwGlobalFunctions.wfEscapeWikiText(languageCode), "\">", XomwGlobalFunctions.wfEscapeWikiText(text), "</span>");
		return null;
	}
}
