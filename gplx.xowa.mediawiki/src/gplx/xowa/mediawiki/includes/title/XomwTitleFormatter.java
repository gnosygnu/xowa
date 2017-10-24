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
* A title formatter service for MediaWiki.
*
* This is designed to encapsulate knowledge about conventions for the title
* forms to be used in the database, in urls, in wikitext, etc.
*
* @see https://www.mediawiki.org/wiki/Requests_for_comment/TitleValue
* @since 1.23
*/
public interface XomwTitleFormatter {
//		/**
//		* Returns the title formatted for display.
//		* Per default, this includes the namespace but not the fragment.
//		*
//		* @note Normalization is applied if $title is not in TitleValue::TITLE_FORM.
//		*
//		* @param int|boolean $namespace The namespace ID (or false, if the namespace should be ignored)
//		* @param String $text The page title
//		* @param String $fragment The fragment name (may be empty).
//		* @param String $interwiki The interwiki prefix (may be empty).
//		*
//		* @return String
//		*/
//		public function formatTitle($namespace, $text, $fragment = '', $interwiki = '');
//
//		/**
//		* Returns the title text formatted for display, without namespace of fragment.
//		*
//		* @note Only minimal normalization is applied. Consider using TitleValue::getText() directly.
//		*
//		* @param LinkTarget $title The title to format
//		*
//		* @return String
//		*/
//		public function getText(LinkTarget $title);
//
//		/**
//		* Returns the title formatted for display, including the namespace name.
//		*
//		* @param LinkTarget $title The title to format
//		*
//		* @return String
//		*/
//		public function getPrefixedText(LinkTarget $title);
//
//		/**
//		* Return the title in prefixed database key form, with interwiki
//		* and namespace.
//		*
//		* @since 1.27
//		*
//		* @param LinkTarget $target
//		*
//		* @return String
//		*/
//		public function getPrefixedDBkey(LinkTarget $target);
//
//		/**
//		* Returns the title formatted for display, with namespace and fragment.
//		*
//		* @param LinkTarget $title The title to format
//		*
//		* @return String
//		*/
//		public function getFullText(LinkTarget $title);

	/**
	* Returns the name of the namespace for the given title.
	*
	* @note This must take into account gender sensitive namespace names.
	* @todo Move this to a separate interface
	*
	* @param int $namespace
	* @param String $text
	*
	* @throws InvalidArgumentException
	* @return String
	*/
	byte[] getNamespaceName(int ns, byte[] text);
}
