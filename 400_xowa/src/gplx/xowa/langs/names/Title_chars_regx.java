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
package gplx.xowa.langs.names; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
/**
* Allowed title characters -- regex character class
* Don't change this unless you know what you're doing
*
* Problematic punctuation:
*   -  []{}|#    Are needed for link syntax, never enable these
*   -  <>        Causes problems with HTML escaping, don't use
*   -  %         Enabled by default, minor problems with path to query rewrite rules, see below
*   -  +         Enabled by default, but doesn't work with path to query rewrite rules,
*                corrupted by apache
*   -  ?         Enabled by default, but doesn't work with path to PATH_INFO rewrites
*
* All three of these punctuation problems can be avoided by using an alias,
* instead of a rewrite rule of either variety.
*
* The problem with % is that when using a path to query rewrite rule, URLs are
* double-unescaped: once by Apache's path conversion code, and again by PHP. So
* %253F, for example, becomes "?". Our code does not double-escape to compensate
* for this, indeed double escaping would break if the double-escaped title was
* passed in the query String rather than the path. This is a minor security issue
* because articles can be created such that they are hard to view or edit.
*
* In some rare cases you may wish to remove + for compatibility with old links.
*
* Theoretically 0x80-0x9F of ISO 8859-1 should be disallowed, but
* this breaks interlanguage links
*/
// REF.MW: /includes/DefaultSettings.php
class Title_chars_regx {
	public boolean Matches(byte[] bry) {
		return false;
	}
}
// $wgLegalTitleChars = " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+";	/*

//	*/
