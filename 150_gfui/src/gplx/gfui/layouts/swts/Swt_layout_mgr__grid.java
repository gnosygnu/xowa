/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
