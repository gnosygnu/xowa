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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_href_ {
	public static final String
	  Str__file				= "file://"
	, Str__site				= "/site/"
	, Str__wiki				= "/wiki/"
	, Str__anch				= "#"
	;
	public static final byte[] 
	  Bry__file				= Bry_.new_a7(Str__file)
	, Bry__site				= Bry_.new_a7(Str__site)
	, Bry__wiki				= Bry_.new_a7(Str__wiki)
	, Bry__anch				= Bry_.new_a7(Str__anch)
	, Bry__https			= Bry_.new_a7("https://")	// NOTE: must be "https:" or wmf api won't work; DATE:2015-06-17
	, Bry__xcmd				= Bry_.new_a7("/xcmd/")
	;
	public static final int
	  Len__file				= Bry__file.length
	, Len__site				= Bry__site.length
	, Len__wiki				= Bry__wiki.length
	, Len__anch				= Bry__anch.length
	;
}
