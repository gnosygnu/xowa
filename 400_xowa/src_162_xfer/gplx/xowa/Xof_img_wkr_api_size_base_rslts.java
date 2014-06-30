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
public class Xof_img_wkr_api_size_base_rslts {
	public int Orig_w() {return orig_w;} public Xof_img_wkr_api_size_base_rslts Orig_w_(int v) {orig_w = v; return this;} private int orig_w;
	public int Orig_h() {return orig_h;} public Xof_img_wkr_api_size_base_rslts Orig_h_(int v) {orig_h = v; return this;} private int orig_h;
	public byte[] Reg_wiki() {return reg_wiki;} public Xof_img_wkr_api_size_base_rslts Reg_wiki_(byte[] v) {reg_wiki = v; return this;} private byte[] reg_wiki;
	public byte[] Reg_page() {return reg_page;} public Xof_img_wkr_api_size_base_rslts Reg_page_(byte[] v) {reg_page = v; return this;} private byte[] reg_page;
	public void Clear() {
		orig_w = orig_h = 0;
		reg_wiki = reg_page = null;
	}
}
