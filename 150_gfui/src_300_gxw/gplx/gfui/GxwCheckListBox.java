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
package gplx.gfui; import gplx.*;
public interface GxwCheckListBox extends GxwElem {
	int Items_count();
	List_adp Items_getAll();
	List_adp Items_getChecked();

	void Items_add(Object obj, boolean v);
	void Items_reverse();
	boolean Items_getCheckedAt(int i);
	void Items_setCheckedAt(int i, boolean v);
	void Items_setAll(boolean v);

	void Items_clear();
}
