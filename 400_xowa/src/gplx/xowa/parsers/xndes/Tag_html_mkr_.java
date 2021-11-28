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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Tag_html_mkr_ {
	// This maker is being applied to:
	// * mapframe and maplink: currently unsupported by XOWA, but some pages will pass in HTML which will break tag; EX:{{#tag:mapframe||body='<div id="a">'}} PAGE:fr.v:France; DATE:2017-06-01
	public static final Tag_html_mkr Noop = new Tag_html_mkr_noop();

	// This maker is being applied to:
	// - built-in xndes like b,i,li as specified by "/includes/parser/CoreParserFunctions.php|tagObj"
	// - built-in xndes like pre,nowiki,gallery,indicator with explicit setHook calls in "/includes/parser/CoreTagHooks.php"
	// - extension xndes like ref,poem with setHook calls from "/includes/parser/Parser.php|extensionSubstitution"
	// The latter two should be redirected to new mkrs
	public static Tag_html_mkr Basic(boolean atrs_encode) {
		return new Tag_html_mkr_basic(atrs_encode);
	}
}
