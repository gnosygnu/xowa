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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Meta_fld_itm {
	public Meta_fld_itm(String name, Meta_type_itm type) {
		this.name = name; this.type = type;
	}
	public String Name()		{return name;} private final String name;
	public Meta_type_itm Type() {return type;} private final Meta_type_itm type;
	public int Nullable_tid()	{return nullable_tid;} public void Nullable_tid_(int v) {nullable_tid = v;} private int nullable_tid;
	public boolean Autonumber()	{return autonumber;} public void Autonumber_y_() {autonumber = true;} private boolean autonumber;
	public boolean Primary_key()	{return primary_key;} public void Primary_key_y_() {primary_key = true;} private boolean primary_key;
	public Object Default_val() {return default_val;} private Object default_val;
	public void Default_val_(Object v) {this.default_val = v;}
	public static final int Nullable_unknown = 0, Nullable_null = 1, Nullable_not_null = 2;
}
