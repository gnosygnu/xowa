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
import gplx.xowa.xtns.wbases.claims.enums.*;
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Interfaces.ValueFormatters.*;
public class WikibaseValueFormatterBuilder {
	private final SnakFormat snakFormat = new SnakFormat();

	/**
	* @param String $format The desired target format, see SnakFormatter::FORMAT_XXX
	*
	* @return MonolingualHtmlFormatter|MonolingualWikitextFormatter|MonolingualTextFormatter
	*/
	public ValueFormatter newMonolingualFormatter(Wbase_enum_itm format) {
		switch (snakFormat.getBaseFormat(format).Tid()) {
			case SnakFormatterFormat.Tid__html:
				return new MonolingualHtmlFormatter(); // $this->languageNameLookup
			case SnakFormatterFormat.Tid__wiki:
				return new MonolingualWikitextFormatter();
			default:
				return new MonolingualTextFormatter();
		}
	}
}
