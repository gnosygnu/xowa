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
package gplx.texts; import gplx.*;
public class StringTableColAlign {
	public int Val() {return val;} int val = 0;
	public static StringTableColAlign new_(int v) {
		StringTableColAlign rv = new StringTableColAlign();
		rv.val = v;
		return rv;
	}	StringTableColAlign() {}
	public static final StringTableColAlign Left = new_(0);
	public static final StringTableColAlign Mid = new_(1);
	public static final StringTableColAlign Right = new_(2);
}
