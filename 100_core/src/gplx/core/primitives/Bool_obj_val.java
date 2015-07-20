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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Bool_obj_val {
	Bool_obj_val(int v) {val = v;} private final int val;
	public boolean Val() {return val == 1;}
	public static final Bool_obj_val
	  Null	= new Bool_obj_val(-1)
	, False = new Bool_obj_val(0)
	, True	= new Bool_obj_val(1)
	;
	public static Bool_obj_val read_(Object o) {String s = String_.as_(o); return s == null ? (Bool_obj_val)o : parse_(s);}
	public static Bool_obj_val parse_(String raw) {
		if		(String_.Eq(raw, "y"))	return Bool_obj_val.True;
		else if	(String_.Eq(raw, "n"))	return Bool_obj_val.False;
		else if	(String_.Eq(raw, ""))	return Bool_obj_val.Null;
		else	throw Err_.new_parse_type(Bool_obj_val.class, raw);
	}
}
