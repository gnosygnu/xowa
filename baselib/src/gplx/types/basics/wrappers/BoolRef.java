/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.wrappers;
import gplx.types.basics.utls.BoolUtl;
public class BoolRef {
	private BoolRef(boolean val) {
		this.val = val;
	}
	public boolean Val() {return val;} private boolean val;
	public boolean ValY() {return val;}
	public boolean ValN() {return !val;}
	public BoolRef ValSetY() {val = true; return this;}
	public BoolRef ValSetN() {val = false; return this;}
	public BoolRef ValSet(boolean v) {val = v; return this;}
	@Override public String toString() {return BoolUtl.ToStrLower(val);}

	public static BoolRef NewN() {return New(false);}
	public static BoolRef NewY() {return New(true);}
	public static BoolRef New(boolean val) {return new BoolRef(val);}
}
