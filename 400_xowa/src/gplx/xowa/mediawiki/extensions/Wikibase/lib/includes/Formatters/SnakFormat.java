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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Formatters;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_enum_itm;
// REF.MW: https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/SnakFormat.php
public class SnakFormat {
	/**
	* Get the format to fallback to, in case a given format is not available.
	*
	* @param String $format One of the SnakFormatter::FORMAT_... constants.
	*
	* @throws InvalidArgumentException
	* @return String One of the SnakFormatter::FORMAT_* constants.
	*/
	public Wbase_enum_itm getFallbackFormat(Wbase_enum_itm format) {
		switch (format.Tid()) {
			case SnakFormatterFormat.Tid__plain:
				break;
		}
		throw ErrUtl.NewArgs("");
	}

	
//	public function getFallbackFormat( $format ) {
//		switch ( $format ) {
//			case SnakFormatter::FORMAT_HTML:
//			case SnakFormatter::FORMAT_HTML_DIFF:
//			case SnakFormatter::FORMAT_HTML_VERBOSE:
//				return SnakFormatter::FORMAT_HTML;
//			case SnakFormatter::FORMAT_HTML_VERBOSE_PREVIEW:
//				return SnakFormatter::FORMAT_HTML_VERBOSE;
//			case SnakFormatter::FORMAT_WIKI:
//			case SnakFormatter::FORMAT_PLAIN:
//				return $format;
//		}
//		throw new InvalidArgumentException( 'Unsupported output format: ' . $format );
//	}
	public Wbase_enum_itm getBaseFormat(Wbase_enum_itm format) {
		switch (format.Tid()) {
			case SnakFormatterFormat.Tid__plain:
				break;
		}
		throw ErrUtl.NewArgs("");
	}

}
