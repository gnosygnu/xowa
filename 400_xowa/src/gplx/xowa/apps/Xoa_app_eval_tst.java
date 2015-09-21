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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.apps.gfss.*;
public class Xoa_app_eval_tst {
	Xoa_app_eval_fxt fxt = new Xoa_app_eval_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Eval_test("[~{<>app.sys_cfg.version;<>}]", "[" + Xoa_app_.Version + "]");
	}
}
class Xoa_app_eval_fxt {
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			fmtr = Bry_fmtr.new_();
			eval = new Xoa_app_eval();
			fmtr.Eval_mgr_(eval);
			Xoa_gfs_mgr.Msg_parser_init();
		}
	}	private Xoae_app app; Bry_fmtr fmtr; Xoa_app_eval eval;
	public void Eval_test(String raw, String expd) {
		Tfds.Eq(fmtr.Fmt_(raw).Bld_str_many(), expd);
	}
}
