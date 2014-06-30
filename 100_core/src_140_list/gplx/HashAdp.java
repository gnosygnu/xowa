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
public interface HashAdp extends gplx.lists.EnumerAble {
	int		Count();
	boolean	Has(Object key);
	Object	Fetch(Object key);
	Object	FetchOrFail(Object key);
	Object	FetchOrNew(Object key, NewAble prototype);
	void	Add(Object key, Object val);
	void	AddKeyVal(Object val);
	void	AddReplace(Object key, Object val);
	boolean	Add_if_new(Object key, Object val);
	void	Del(Object key);
	void	Clear();
}
