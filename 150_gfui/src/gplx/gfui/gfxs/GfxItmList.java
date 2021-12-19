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
package gplx.gfui.gfxs;
import gplx.frameworks.objects.New;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp_base;
public class GfxItmList extends List_adp_base {
	@New public GfxItm GetAt(int i) {return (GfxItm)Get_at_base(i);}
	public void Add(GfxItm gfxItm) {Add_base(gfxItm);}
}
class GfxItmListFxt {
	public void tst_SubItm_count(GfxAdpMok gfx, int expd) {GfoTstr.EqObj(expd, gfx.SubItms().Len());}
	public void tst_SubItm(GfxAdpMok gfx, int i, GfxItm expd) {
		GfxItm actl = gfx.SubItms().GetAt(i);
		GfoTstr.EqObj(expd, actl);
	}
	public static GfxItmListFxt new_() {return new GfxItmListFxt();} GfxItmListFxt() {}
}
