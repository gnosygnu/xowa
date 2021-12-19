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
import java.awt.Graphics2D;
import gplx.gfui.imgs.*;
public class GfxAdp_ {
	public static GfxAdp new_(Graphics2D graphics) {return GfxAdpBase.new_(graphics);}	
	public static GfxAdp image_(ImageAdp image) {
				Graphics2D graphics = (Graphics2D)((java.awt.Image)image.Under()).getGraphics();
				return GfxAdpBase.new_(graphics);
	}
}
