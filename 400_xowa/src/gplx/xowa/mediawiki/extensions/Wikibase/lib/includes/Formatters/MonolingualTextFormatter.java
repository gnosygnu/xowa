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
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Interfaces.ValueFormatters.*;
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Common.DataValues.*;
// REF.MW:https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/MonolingualTextFormatter.php
public class MonolingualTextFormatter implements ValueFormatter {
	/**
	* @see ValueFormatter::format
	*
	* @param MonolingualTextValue $value
	*
	* @throws InvalidArgumentException
	* @return String Text
	*/
	public Object format(Object val) {
		// if (!Type_.Eq_by_obj(val, typeof(MonolingualTextValue))) {
		//	throw new InvalidArgumentException( 'Data value type mismatch. Expected a MonolingualTextValue.' );
		// }
		return ((MonolingualTextValue)val).getText();
	}
}
