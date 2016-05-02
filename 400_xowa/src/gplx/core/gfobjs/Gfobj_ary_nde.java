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
public class Gfobj_ary_nde implements Gfobj_ary {
	public Gfobj_ary_nde(Gfobj_nde[] ary) {this.ary = ary;}
	public byte			Grp_tid() {return Gfobj_grp_.Grp_tid__ary;}
	public byte			Ary_tid() {return Gfobj_ary_.Ary_tid__nde;}
	public int			Len() {return ary.length;}
	public Object		Get_at(int i) {return ary[i];}
	public Gfobj_nde[]	Ary_nde() {return ary;} private Gfobj_nde[] ary;
	public void			Ary_nde_(Gfobj_nde[] v) {this.ary = v;}
}
