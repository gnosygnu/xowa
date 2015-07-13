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
package gplx.xowa2.files.commons; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
public class Xof_commons_image_itm {
	public Xof_commons_image_itm(String name, String media_type, String minor_mime, int size, int width, int height, int bits, int ext_id, String timestamp) {
		this.name = name; this.media_type = media_type; this.minor_mime = minor_mime; this.size = size; this.width = width; this.height = height; this.bits = bits; this.ext_id = ext_id; this.timestamp = timestamp;
	}
	public String Name() {return name;} private final String name;
	public String Media_type() {return media_type;} private final String media_type;
	public String Minor_mime() {return minor_mime;} private final String minor_mime;
	public int Size() {return size;} private final int size;
	public int Width() {return width;} private final int width;
	public int Height() {return height;} private final int height;
	public int Bits() {return bits;} private final int bits;
	public int Ext_id() {return ext_id;} private final int ext_id;
	public String Timestamp() {return timestamp;} private final String timestamp;
}
