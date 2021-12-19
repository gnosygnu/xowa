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
package gplx.xowa.xtns.imaps.itms;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
public class Imap_part_ {
	public static final byte 
	  Tid_invalid		= 0
	, Tid_img			= 1
	, Tid_desc			= 2
	, Tid_comment		= 3
	, Tid_dflt			= 4
	, Tid_shape_rect	= 5
	, Tid_shape_circle	= 6
	, Tid_shape_poly	= 7
	;
	public static final byte[] 
	  Key_dflt				= BryUtl.NewA7("default")
	, Key_shape_rect		= BryUtl.NewA7("rect")
	, Key_shape_circle		= BryUtl.NewA7("circle")
	, Key_shape_poly		= BryUtl.NewA7("poly")
	;

	public static byte[] To_shape_key(byte v) {
		switch (v) {
			case Tid_shape_rect		: return Key_shape_rect;
			case Tid_shape_circle	: return Key_shape_circle;
			case Tid_shape_poly		: return Key_shape_poly;
			default					: throw ErrUtl.NewUnhandled(v);
		}
	}
}
