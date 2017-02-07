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
public interface Hash_adp extends gplx.core.lists.EnumerAble {
	int		Count();
	boolean	Has(Object key);
	Object	Get_by(Object key);
	Object	Get_by_or_fail(Object key);
	void	Add(Object key, Object val);
	void	Add_as_key_and_val(Object val);
	boolean	Add_if_dupe_use_1st(Object key, Object val);
	void	Add_if_dupe_use_nth(Object key, Object val);
	void	Del(Object key);
	void	Clear();
}
