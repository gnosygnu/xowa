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
package gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Interfaces.ValueFormatters; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.*; import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Interfaces.*;
// REF: https://github.com/DataValues/Interfaces/blob/master/src/ValueFormatters/ValueFormatter.php
public interface ValueFormatter {
	// public static final String OPT_LANG = "lang";
	/**
	* @since 0.1
	*
	* @param mixed $value
	*
	* @return mixed
	* @throws FormattingException
	* @throws \InvalidArgumentException when value is of wrong type or out of accepted range/pattern
	*/
	Object format(Object val);
}
