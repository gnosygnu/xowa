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
package gplx;
public class Short_ {
	public static final String Cls_val_name = "short";
	public static final    Class<?> Cls_ref_type = Short.class; 
	public static short cast(Object obj) {try {return (Short)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, short.class, obj);}}
	public static short By_int(int v) {return (short)v;}
}
