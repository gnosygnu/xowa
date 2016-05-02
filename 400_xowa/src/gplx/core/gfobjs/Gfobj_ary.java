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
package gplx.core.gfobjs; import gplx.*; import gplx.core.*;
public interface Gfobj_ary extends Gfobj_grp {
	byte				Ary_tid();
	int					Len();
}
class Gfo_ary_str implements Gfobj_ary {
	public Gfo_ary_str(String[] ary) {this.ary = ary;}
	public byte			Grp_tid() {return Gfobj_grp_.Grp_tid__ary;}
	public byte			Ary_tid() {return Gfobj_ary_.Ary_tid__str;}
	public int			Len() {return ary.length;}
	public Object		Get_at(int i) {return ary[i];}
	public String[]		Ary_str() {return ary;} private final    String[] ary;
}
class Gfo_ary_int implements Gfobj_ary {
	public Gfo_ary_int(int[] ary) {this.ary = ary;}
	public byte			Grp_tid() {return Gfobj_grp_.Grp_tid__ary;}
	public byte			Ary_tid() {return Gfobj_ary_.Ary_tid__int;}
	public int			Len() {return ary.length;}
	public Object		Get_at(int i) {return ary[i];}
	public int[]		Ary_int() {return ary;} private final    int[] ary;
}
class Gfo_ary_ary implements Gfobj_ary {
	public Gfo_ary_ary(Gfobj_ary[] ary) {this.ary = ary;}
	public byte			Grp_tid() {return Gfobj_grp_.Grp_tid__ary;}
	public byte			Ary_tid() {return Gfobj_ary_.Ary_tid__ary;}
	public int			Len() {return ary.length;}
	public Object		Get_at(int i) {return ary[i];}
	public Gfobj_ary[]	Ary_ary() {return ary;} private Gfobj_ary[] ary;
}
