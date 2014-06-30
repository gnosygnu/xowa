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
package gplx.xowa; import gplx.*;
public class Xof_meta_thumb {
	public Xof_meta_thumb() {}
	public Xof_meta_thumb(byte exists, int width, int height, int[] seeks) {this.exists = exists; this.width = width; this.height = height; this.seeks = seeks; if (seeks == null) seeks = Int_.Ary_empty;}
	public byte Exists() {return exists;} private byte exists = Xof_meta_itm.Exists_unknown;	// default to y, b/c thumbs are usually added if they exist; handle n when it occurs; unknown should never happen;
	public boolean State_new() {return state_new;} public Xof_meta_thumb State_new_() {state_new = true; return this;} private boolean state_new;
	public Xof_meta_thumb Exists_(byte v)	{exists = v; return this;} 
	public Xof_meta_thumb Exists_y_()		{exists = Xof_meta_itm.Exists_y; return this;} 
	public Xof_meta_thumb Exists_n_()		{exists = Xof_meta_itm.Exists_n; return this;} 
	public int Width() {return width;} public Xof_meta_thumb Width_(int v) {width = v; return this;} private int width;
	public int Height() {return height;} public Xof_meta_thumb Height_(int v) {height = v; return this;} private int height;
	public int[] Seeks() {return seeks;} public Xof_meta_thumb Seeks_(int[] v) {seeks = v; return this;} private int[] seeks = Int_.Ary_empty;
	public Xof_meta_thumb Seeks_add(int v) {
		int seeks_len = seeks.length;
		seeks = (int[])Array_.Resize(seeks, seeks_len + 1);
		seeks[seeks_len] = v;
		return this;
	}
	public void Save(Bry_bfr bfr) {
		bfr		.Add_int_fixed(exists, 1)	.Add_byte(Xof_meta_thumb_parser.Dlm_exists)
				.Add_int_variable(width)	.Add_byte(Xof_meta_thumb_parser.Dlm_width)
				.Add_int_variable(height);
		if (seeks != Int_.Ary_empty) {
			bfr.Add_byte(Xof_meta_thumb_parser.Dlm_seek);
			int seeks_len = seeks.length;
			for (int i = 0; i < seeks_len; i++) {
				if (i != 0) bfr.Add_byte(Xof_meta_thumb_parser.Dlm_width);
				bfr.Add_int_variable(seeks[i]);
			}
		}
		state_new = false;
	}
	public static final Xof_meta_thumb[] Ary_empty = new Xof_meta_thumb[0];
}
