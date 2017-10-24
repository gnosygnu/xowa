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
package gplx.xowa.mediawiki.includes.title; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* A title parser service for %MediaWiki.
*
* This is designed to encapsulate knowledge about conventions for the title
* forms to be used in the database, in urls, in wikitext, etc.
*
* @see https://www.mediawiki.org/wiki/Requests_for_comment/TitleValue
* @since 1.23
*/
public interface XomwTitleParser {
	/**
	* Parses the given text and constructs a TitleValue. Normalization
	* is applied according to the rules appropriate for the form specified by $form.
	*
	* @note this only parses local page links, interwiki-prefixes etc. are not considered!
	*
	* @param String $text The text to parse
	* @param int $defaultNamespace Namespace to assume per default (usually NS_MAIN)
	*
	* @throws MalformedTitleException If the text is not a valid representation of a page title.
	* @return TitleValue
	*/
	XomwTitle parseTitle(byte[] text, int defaultNamespace);
}
