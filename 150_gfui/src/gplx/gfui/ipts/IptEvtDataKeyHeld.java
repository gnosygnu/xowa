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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
public class IptEvtDataKeyHeld {
	public char KeyChar() {return c;} char c;
	public boolean Handled() {return handled;} public void Handled_set(boolean v) {handled = v;} private boolean handled;

	public static IptEvtDataKeyHeld as_(Object obj) {return obj instanceof IptEvtDataKeyHeld ? (IptEvtDataKeyHeld)obj : null;}
	public static IptEvtDataKeyHeld cast(Object obj) {try {return (IptEvtDataKeyHeld)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEvtDataKeyHeld.class, obj);}}
	public static final    IptEvtDataKeyHeld Null = char_((char)0);
	public static IptEvtDataKeyHeld char_(char c) {
		IptEvtDataKeyHeld rv = new IptEvtDataKeyHeld();
		rv.c = c;
		return rv;
	}
}
