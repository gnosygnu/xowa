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
package gplx.gfml; import gplx.*;
public interface GfmlItm extends GfmlObj, To_str_able {
	GfmlTkn		KeyTkn(); String Key(); // Key() is alternative to Key().Val()
	GfmlType	Type();
	boolean		KeyedSubObj();
	int			SubObjs_Count();
	GfmlObj		SubObjs_GetAt(int i);
	void		SubObjs_Add(GfmlObj obj);
}
class GfmlItm_ {
	public static GfmlItm as_(Object obj) {return obj instanceof GfmlItm ? (GfmlItm)obj : null;}
}
