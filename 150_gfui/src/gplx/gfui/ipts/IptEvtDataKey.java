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
public class IptEvtDataKey {
	public IptKey Key() {return key;} IptKey key;
	public boolean Handled() {return handled;} public void Handled_set(boolean v) {handled = v;} private boolean handled;

	public static IptEvtDataKey as_(Object obj) {return obj instanceof IptEvtDataKey ? (IptEvtDataKey)obj : null;}
	public static IptEvtDataKey cast(Object obj) {try {return (IptEvtDataKey)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEvtDataKey.class, obj);}}
	public static final    IptEvtDataKey Null = new_(IptKey_.None);
	public static IptEvtDataKey test_(IptKey keyArg) {return new_(keyArg);}
	public static IptEvtDataKey int_(int val) {
		IptKey keyArg = IptKey_.api_(val);
		return new_(keyArg);
	}
	public static IptEvtDataKey new_(IptKey key) {
		IptEvtDataKey rv = new IptEvtDataKey();
		rv.key = key;
		return rv;
	}
}
