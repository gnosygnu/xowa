/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.gfobjs; import gplx.*; import gplx.core.*;
public class Gfobj_ary implements Gfobj_grp { // NOTE: items in array can vary in types; EX:['a', 1, false]
	public Gfobj_ary(Object[] ary) {this.ary = ary;}
	public byte				Grp_tid() {return Gfobj_grp_.Grp_tid__ary;}
	public int				Len() {return ary.length;}
	public Object			Get_at(int i) {return ary[i];}
	public Object[]			Ary_obj() {return ary;} private Object[] ary;
	public Gfobj_ary		Ary_(Object[] v) {this.ary = v; return this;}
	public Gfobj_nde		New_nde_at(int i) {
		Gfobj_nde rv = Gfobj_nde.New();
		ary[i] = rv;
		return rv;
	}
}
