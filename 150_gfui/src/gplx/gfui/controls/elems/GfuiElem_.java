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
package gplx.gfui.controls.elems; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public class GfuiElem_ {
	public static final    String 
		  InitKey_focusAble = "focusAble"
		, InitKey_ownerWin = "ownerForm";
	public static GfuiElem as_(Object obj) {return obj instanceof GfuiElem ? (GfuiElem)obj : null;}
	public static GfuiElem cast(Object obj) {try {return (GfuiElem)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiElem.class, obj);}}
	public static GfuiElemBase sub_(String key, GfuiElem owner) {
		GfuiElemBase rv = new_();
		rv.Owner_(owner, key);
		return rv;
	}
	public static GfuiElemBase new_() {
		GfuiElemBase rv = new GfuiElemBase();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
	public static Keyval_hash init_focusAble_true_()			{return new Keyval_hash().Add(GfuiElem_.InitKey_focusAble, true);}
	public static Keyval_hash init_focusAble_false_()		{return new Keyval_hash().Add(GfuiElem_.InitKey_focusAble, false);}
	public static void Y_adj(int adj, GfuiElem... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			GfuiElem itm = ary[i];
			itm.Y_(itm.Y() + adj);
		}
	}
}
