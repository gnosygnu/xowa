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
package gplx.gfui.layouts; import gplx.*; import gplx.gfui.*;
public interface GftItem {
	String Key_of_GfuiElem();
	int Gft_h(); GftItem Gft_h_(int v);
	int Gft_w(); GftItem Gft_w_(int v);
	int Gft_x(); GftItem Gft_x_(int v);
	int Gft_y(); GftItem Gft_y_(int v);
	GftItem Gft_rect_(RectAdp rect);
}
class GftItem_mok implements GftItem {
	public String Key_of_GfuiElem() {return "null";}
	public int Gft_h() {return gft_h;} public GftItem Gft_h_(int v) {gft_h = v; return this;} int gft_h;
	public int Gft_w() {return gft_w;} public GftItem Gft_w_(int v) {gft_w = v; return this;} int gft_w;
	public int Gft_x() {return gft_x;} public GftItem Gft_x_(int v) {gft_x = v; return this;} int gft_x;
	public int Gft_y() {return gft_y;} public GftItem Gft_y_(int v) {gft_y = v; return this;} int gft_y;
	public GftItem Gft_rect_(RectAdp rect) {gft_x = rect.X(); gft_y = rect.Y(); gft_w = rect.Width(); gft_h = rect.Height(); return this;}
}
