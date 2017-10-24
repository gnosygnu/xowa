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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Gallery_xnde_atrs {
	public static final byte 
	  Tid__mode			= 0
	, Tid__perrow		= 1
	, Tid__widths		= 2
	, Tid__heights		= 3
	, Tid__caption		= 4
	, Tid__showfilename	= 5
	, Tid__style		= 6
	, Tid__class		= 7
	;
	public static final    Hash_adp_bry Key_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("mode"			, Tid__mode)
	.Add_str_byte("perrow"			, Tid__perrow)
	.Add_str_byte("widths"			, Tid__widths)
	.Add_str_byte("heights"			, Tid__heights)
	.Add_str_byte("caption"			, Tid__caption)
	.Add_str_byte("showfilename"	, Tid__showfilename)
	.Add_str_byte("style"			, Tid__style)
	.Add_str_byte("class"			, Tid__class)
	;
}
