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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import org.junit.*; import gplx.gfui.*; import gplx.gfui.ipts.*; import gplx.xowa.apps.cfgs.old.*;
public class Xog_bnd_itm_srl_tst {
	@Before public void init() {fxt.Reset();} private Xog_bnd_itm_srl_fxt fxt = new Xog_bnd_itm_srl_fxt();
	@Test  public void Src_get() {
		Xog_bnd_itm bnd = fxt.bnd_(Xog_bnd_box_.Tid_browser, IptKey_.A);
		fxt.Test_src_get(bnd, "box='browser';ipt='key.a';");
	}
	@Test  public void Src_set() {
		fxt.Test_src_set("box='browser.html';ipt='key.b';", Xog_bnd_box_.Tid_browser_html, IptKey_.B);
	}
}
class Xog_bnd_itm_srl_fxt {
	private Xoae_app app;
	public void Reset() {
		app = Xoa_app_fxt.Make__app__edit();
	}
	public Xog_bnd_itm bnd_(int box, IptArg ipt) {return new Xog_bnd_itm("test.key", true, "test.cmd", box, ipt);}
	public void Test_src_get(Xog_bnd_itm bnd, String expd) {
		Tfds.Eq(expd, Xocfg_bnd_itm_srl.Src(app, bnd.Box(), bnd.Ipt()));
	}
	public void Test_src_set(String src, int expd_box, IptArg expd_ipt) {
		Xocfg_bnd_itm_srl itm = new Xocfg_bnd_itm_srl(app, "test.key");
		Xocfg_bnd_itm_srl.Src_(app, itm, src);
		Tfds.Eq(expd_box, itm.Box());
		Tfds.Eq(expd_ipt.Key(), itm.Ipt().Key());
	}
}
