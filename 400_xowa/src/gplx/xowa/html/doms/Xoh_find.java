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
package gplx.xowa.html.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_find {
	public int Tag_bgn() {return tag_bgn;} public Xoh_find Tag_bgn_(int v) {tag_bgn = v; return this;} private int tag_bgn;
	public int Tag_end() {return tag_end;} public Xoh_find Tag_end_(int v) {tag_end = v; return this;} private int tag_end;
	public int Key_bgn() {return key_bgn;} public Xoh_find Key_bgn_(int v) {key_bgn = v; return this;} private int key_bgn;
	public int Key_end() {return key_end;} public Xoh_find Key_end_(int v) {key_end = v; return this;} private int key_end;
	public int Val_bgn() {return val_bgn;} public Xoh_find Val_bgn_(int v) {val_bgn = v; return this;} private int val_bgn;
	public int Val_end() {return val_end;} public Xoh_find Val_end_(int v) {val_end = v; return this;} private int val_end;
	public void Set_all(int tag_bgn, int tag_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		this.tag_bgn = tag_bgn; this.tag_end = tag_end; this.key_bgn = key_bgn; this.key_end = key_end; this.val_bgn = val_bgn; this.val_end = val_end;
	}
}
