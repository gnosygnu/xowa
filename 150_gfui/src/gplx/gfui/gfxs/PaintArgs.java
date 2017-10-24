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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
public class PaintArgs {
	public GfxAdp Graphics() {return graphics;} GfxAdp graphics;
	public RectAdp ClipRect() {return clipRect;} RectAdp clipRect;

	public static PaintArgs cast(Object obj) {try {return (PaintArgs)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, PaintArgs.class, obj);}}
	public static PaintArgs new_(GfxAdp graphics, RectAdp clipRect) {
		PaintArgs rv = new PaintArgs();
		rv.graphics = graphics; rv.clipRect = clipRect;
		return rv;
	}
}
