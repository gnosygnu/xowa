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
public interface SrlMgr {
	boolean		Type_rdr();
	Object		StoreRoot(SrlObj root, String key);

	boolean		SrlBoolOr(String key, boolean v);
	byte		SrlByteOr(String key, byte v);
	int			SrlIntOr(String key, int v);
	long		SrlLongOr(String key, long v);
	String		SrlStrOr(String key, String v);
	double		SrlDoubleOr(String key, double v);
	DecimalAdp	SrlDecimalOr(String key, DecimalAdp v);
	DateAdp		SrlDateOr(String key, DateAdp v);
	Object		SrlObjOr(String key, Object v);
	void		SrlList(String key, ListAdp list, SrlObj proto, String itmKey);
	void		TypeKey_(String v);	
	SrlMgr		SrlMgr_new(Object o);
}
