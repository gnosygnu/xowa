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
package gplx.gfui.envs; import gplx.*; import gplx.gfui.*;
public class ScreenAdp {
	public int Index() {return index;} int index;
	public RectAdp Rect() {return bounds;} RectAdp bounds = RectAdp_.Zero;
	public SizeAdp Size() {return bounds.Size();}
	public int Width() {return bounds.Width();}
	public int Height() {return bounds.Height();}		
	public PointAdp Pos() {return bounds.Pos();}
	public int X() {return bounds.X();}
	public int Y() {return bounds.Y();}
			
	@gplx.Internal protected ScreenAdp(int index, RectAdp bounds) {
		this.index = index; this.bounds = bounds;
	}
}
