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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
public class ColorAdpCache {
	public java.awt.Color GetNativeColor(ColorAdp color) {
		Object rv = hash.Get_by(color.Value()); if (rv != null) return (java.awt.Color)rv;
		rv = new java.awt.Color(color.Red(), color.Green(), color.Blue(), color.Alpha());
		hash.Add(color.Value(), rv);
		return (java.awt.Color)rv;
	}
	Hash_adp hash = Hash_adp_.New();
	public static final    ColorAdpCache Instance = new ColorAdpCache(); ColorAdpCache() {}
}
