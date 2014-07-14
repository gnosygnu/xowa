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
public class Double_obj_val implements CompareAble {
	public double Val() {return val;} double val;
	@Override public String toString() {return Double_.XtoStr(val);}
	@Override public int hashCode() {return (int)val;}
	@Override public boolean equals(Object obj) {return obj == null ? false : val == ((Double_obj_val)obj).Val();}
	public int compareTo(Object obj) {Double_obj_val comp = (Double_obj_val)obj; return Double_.Compare(val, comp.val);}
        public static Double_obj_val neg1_() {return new_(-1);}
        public static Double_obj_val zero_() {return new_(0);}
        public static Double_obj_val new_(double val) {
		Double_obj_val rv = new Double_obj_val();
		rv.val = val;
		return rv;
	}	Double_obj_val() {}
}
