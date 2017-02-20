/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
