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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Bool_obj_val {
	Bool_obj_val(int v) {val = v;} private final int val;
	public boolean Val() {return val == 1;}
	public static final Bool_obj_val
	  Null	= new Bool_obj_val(-1)
	, False = new Bool_obj_val(0)
	, True	= new Bool_obj_val(1)
	;
	public static Bool_obj_val read_(Object o) {String s = String_.as_(o); return s == null ? (Bool_obj_val)o : parse(s);}
	public static Bool_obj_val parse(String raw) {
		if		(String_.Eq(raw, "y"))	return Bool_obj_val.True;
		else if	(String_.Eq(raw, "n"))	return Bool_obj_val.False;
		else if	(String_.Eq(raw, ""))	return Bool_obj_val.Null;
		else	throw Err_.new_parse_type(Bool_obj_val.class, raw);
	}
}
