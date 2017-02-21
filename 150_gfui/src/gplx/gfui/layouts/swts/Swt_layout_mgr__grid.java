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
package gplx.gfui.layouts.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.layouts.*;
import gplx.gfui.controls.elems.*;
public class Swt_layout_mgr__grid implements Swt_layout_mgr {
	public int Cols() {return cols;} public Swt_layout_mgr__grid Cols_(int v) {cols = v; return this;} private int cols = -1;
	public int Margin_w() {return margin_w;} public Swt_layout_mgr__grid Margin_w_(int v) {margin_w = v; return this;} private int margin_w = -1;
	public int Margin_h() {return margin_h;} public Swt_layout_mgr__grid Margin_h_(int v) {margin_h = v; return this;} private int margin_h = -1;
	public int Spacing_w() {return spacing_w;} public Swt_layout_mgr__grid Spacing_w_(int v) {spacing_w = v; return this;} private int spacing_w = -1;
	public int Spacing_h() {return spacing_h;} public Swt_layout_mgr__grid Spacing_h_(int v) {spacing_h = v; return this;} private int spacing_h = -1;
}
