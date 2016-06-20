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
public class Int_obj_ref {
	Int_obj_ref(int val) {this.val = val;}
	public int Val() {return val;} public Int_obj_ref Val_(int v) {val = v; return this;} int val;
	public int Val_add() {val++; return val;}
	public int Val_add_post() {return val++;}
	public int Val_add(int v) {val += v; return val;}		
	public Int_obj_ref Val_zero_() {val = 0; return this;}
	public Int_obj_ref Val_neg1_() {val = -1; return this;}
	public String Val_as_str() {return Int_.To_str(val);}

	@Override public String toString() {return Int_.To_str(val);}
	@Override public int hashCode() {return val;}
	@Override public boolean equals(Object obj) {return val == ((Int_obj_ref)obj).Val();}

        public static Int_obj_ref New_neg1()	{return new Int_obj_ref(-1);}
        public static Int_obj_ref New_zero()	{return new Int_obj_ref(0);}
        public static Int_obj_ref New(int val)	{return new Int_obj_ref(val);}
}
