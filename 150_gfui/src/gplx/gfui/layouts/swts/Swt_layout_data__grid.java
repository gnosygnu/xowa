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
public class Swt_layout_data__grid implements Swt_layout_data {
	public boolean Grab_excess_h() {return grab_excess_h;} public Swt_layout_data__grid Grab_excess_h_(boolean v) {grab_excess_h = v; return this;} private boolean grab_excess_h;
	public boolean Align_h_fill() {return align_h_fill;} public Swt_layout_data__grid Align_h_fill_(boolean v) {align_h_fill = v; return this;} private boolean align_h_fill;
	public boolean Grab_excess_w() {return grab_excess_w;} public Swt_layout_data__grid Grab_excess_w_(boolean v) {grab_excess_w = v; return this;} private boolean grab_excess_w;
	public boolean Align_w_fill() {return align_w_fill;} public Swt_layout_data__grid Align_w_fill_(boolean v) {align_w_fill = v; return this;} private boolean align_w_fill;
	public int Min_w() {return min_w;} public Swt_layout_data__grid Min_w_(int v) {min_w = v; return this;} private int min_w = -1;
	public int Min_h() {return min_h;} public Swt_layout_data__grid Min_h_(int v) {min_h = v; return this;} private int min_h = -1;
	public int Hint_w() {return hint_w;} public Swt_layout_data__grid Hint_w_(int v) {hint_w = v; return this;} private int hint_w = -1;
	public int Hint_h() {return hint_h;} public Swt_layout_data__grid Hint_h_(int v) {hint_h = v; return this;} private int hint_h = -1;
}
