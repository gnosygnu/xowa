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
package gplx.criterias; import gplx.*;
public class Criteria_wrapper implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_wrapper;}
	public static Criteria_wrapper as_(Object obj) {return obj instanceof Criteria_wrapper ? (Criteria_wrapper)obj : null;}
	public String Name_of_GfoFldCrt() {return fieldName;} private String fieldName;
	public Criteria Crt_of_GfoFldCrt() {return crt;} Criteria crt;
	public boolean Matches(Object invkObj) {			
		GfoInvkAble invk = (GfoInvkAble)invkObj;
		Object comp = GfoInvkAble_.InvkCmd(invk, fieldName);
		return crt.Matches(comp);
	}
	public String XtoStr() {return String_.Concat(fieldName, " ", crt.XtoStr());}
	public static Criteria_wrapper new_(String fieldName, Criteria criteria) {
		Criteria_wrapper rv = new Criteria_wrapper();
		rv.fieldName = fieldName; rv.crt = criteria;
		return rv;
	}
}
