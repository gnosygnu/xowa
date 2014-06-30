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
package gplx.xowa.bldrs.wikis.images; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.*;
public class Xob_wiki_image_itm {
	public byte[] Name() {return name;} public void Name_(byte[] v) {this.name = v;} byte[] name;
	public int Size() {return size;} public void Size_(int v) {this.size = v;} int size;
	public int Width() {return width;} public void Width_(int v) {this.width = v;} int width;
	public int Height() {return height;} public void Height_(int v) {this.height = v;} int height;
	public byte Bits() {return bits;} public void Bits_(byte v) {this.bits = v;} byte bits;
	public static final Xob_wiki_image_itm Null = new Xob_wiki_image_itm();
}
