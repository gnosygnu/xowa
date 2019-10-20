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
// REF.MW: https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/SnakFormatter.php
public class SnakFormatterFormat {
	public static final byte
	  Tid__plain                   =  1
	, Tid__wiki                    =  2
	, Tid__html                    =  3
	, Tid__html_diff               =  4
	, Tid__html_verbose            =  5
	, Tid__html_verbose_preview    =  6
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("snak_format", 6);
	public static final    Wbase_enum_itm
	  Itm__plain                   = Reg.Add(Tid__plain                , "text/plain")
	, Itm__wiki                    = Reg.Add(Tid__wiki                 , "text/x-wiki")
	, Itm__html                    = Reg.Add(Tid__html                 , "text/html")
	, Itm__html_diff               = Reg.Add(Tid__html_diff            , "text/html; disposition=diff")
	, Itm__html_verbose            = Reg.Add(Tid__html_verbose         , "text/html; disposition=verbose")
	, Itm__html_verbose_preview    = Reg.Add(Tid__html_verbose_preview , "text/html; disposition=verbose-preview")
	;
}
