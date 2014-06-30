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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public interface Xol_lang_transform {
	byte[] Kwd_transform(byte[] lang_key, byte[] kwd_key, byte[] kwd_word);
}
class Xol_lang_transform_null implements Xol_lang_transform {
	public byte[] Kwd_transform(byte[] lang_key, byte[] kwd_key, byte[] kwd_word) {return kwd_word;}
	public static final Xol_lang_transform_null _ = new Xol_lang_transform_null(); Xol_lang_transform_null() {}
}
