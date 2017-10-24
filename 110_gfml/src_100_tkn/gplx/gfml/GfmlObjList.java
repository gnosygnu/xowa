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
package gplx.gfml; import gplx.*;
public class GfmlObjList extends List_adp_base {
	@gplx.New public GfmlObj Get_at(int idx) {return (GfmlObj)Get_at_base(idx);}
	public void Add(GfmlObj tkn) {Add_base(tkn);}
	public void Add_at(GfmlObj tkn, int idx) {super.AddAt_base(idx, tkn);}
	public void Del(GfmlObj tkn) {Del_base(tkn);}
	public static GfmlObjList new_() {return new GfmlObjList();} GfmlObjList() {}
}
