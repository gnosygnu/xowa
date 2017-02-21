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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xoh_hzip_dict_ {
	public static final    byte Escape = Byte_.By_int(27);				// SERIALIZED: 27=escape byte
	public static final    byte[] Escape_bry = Bry_.New_by_ints(27);	// SERIALIZED
	public static final int
	  Tid__timeline			= -10
	, Tid__gallery			= -11
	, Tid__root				=  0
	, Tid__escape			=  1
	, Tid__xnde				=  2
	, Tid__lnke				=  3
	, Tid__lnki				=  4
	, Tid__hdr				=  5
	, Tid__img				=  6
	, Tid__thm				=  7
	, Tid__gly				=  8
	, Tid__img_bare			=  9
	, Tid__toc				= 10
	, Tid__pgbnr			= 11
	, Tid__media			= 12
	;
	public static final    String
	  Key__timeline				= "timeline"
	, Key__gallery				= "gallery"
	, Key__escape				= "escape"
	, Key__hdr					= "hdr"
	, Key__lnke					= "lnke"
	, Key__lnki					= "lnki"
	, Key__img					= "img"
	, Key__thm					= "thm"
	, Key__gly					= "gly"
	, Key__xnde					= "xnde"
	, Key__img_bare				= "img_bare"
	, Key__toc					= "toc"
	, Key__pgbnr				= "pgbnr"
	, Key__media				= "media"
	;
	public static final int Hzip__none = 0, Hzip__v1 = 1, Hzip__plain = 2;	// SERIALIZED
}
