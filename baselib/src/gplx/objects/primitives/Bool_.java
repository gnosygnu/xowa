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
package gplx.objects.primitives; import gplx.*; import gplx.objects.*;
import gplx.objects.errs.*;
public class Bool_ {
	public static final String Cls_val_name = "bool";
	public static final Class<?> Cls_ref_type = Boolean.class;

	public static final boolean		N		= false	, Y			= true;
	public static final byte		N_byte	= 0		, Y_byte	= 1		, __byte = 127;
	public static final int		N_int	= 0		, Y_int		= 1		, __int  =  -1;
	public static final String		True_str = "true", False_str = "false";


	public static boolean Cast(Object o) {
		try {
			return (Boolean)o; 
		} 
		catch (Exception e) {
			throw Err_.New_fmt(e, "failed to cast to boolean; obj={0}", Object_.To_str(o));
		}
	}
}
