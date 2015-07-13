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
package gplx;
public interface ClassXtn {
	String Key();
	Class<?> UnderClass();
	Object DefaultValue();
	Object ParseOrNull(String raw);
	Object XtoDb(Object obj);
	String XtoUi(Object obj, String fmt);
	boolean MatchesClass(Object obj);
	boolean Eq(Object lhs, Object rhs);
	int compareTo(Object lhs, Object rhs);
}
