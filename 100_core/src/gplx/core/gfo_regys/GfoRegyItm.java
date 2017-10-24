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
package gplx.core.gfo_regys; import gplx.*; import gplx.core.*;
public class GfoRegyItm {
	public String Key() {return key;} private String key;
	public Object Val() {return val;} Object val;
	public Io_url Url() {return url;} Io_url url;
	public int ValType() {return valType;} int valType;
	public GfoRegyItm(String key, Object val, int valType, Io_url url) {this.key = key; this.val = val; this.valType = valType; this.url = url;}

	public static final int 
		  ValType_Obj = 1
		, ValType_Url = 2
		, ValType_B64 = 3
		;
}
