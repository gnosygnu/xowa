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
public class IptEvtDataMouse {
	public IptMouseBtn		Button() {return button;} IptMouseBtn button;
	public IptMouseWheel	Wheel() {return wheel;} IptMouseWheel wheel;
	public PointAdp			Pos() {return location;} PointAdp location;

	public static IptEvtDataMouse as_(Object obj) {return obj instanceof IptEvtDataMouse ? (IptEvtDataMouse)obj : null;}
	public static IptEvtDataMouse cast(Object obj) {try {return (IptEvtDataMouse)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEvtDataMouse.class, obj);}}
	public static final    IptEvtDataMouse Null = IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, 0, 0);
	public static IptEvtDataMouse new_(IptMouseBtn button, IptMouseWheel wheel, int x, int y) {
		IptEvtDataMouse rv = new IptEvtDataMouse();
		rv.button = button;
		rv.wheel = wheel;
		rv.location = PointAdp_.new_(x, y);
		return rv;
	}
}
