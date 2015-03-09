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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.xowa.files.*;
public class Xofo_lnki_parser_tst {
	Xofo_lnki_parser_fxt fxt = new Xofo_lnki_parser_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Basic() {
		fxt.Raw_("8,90,80").Expd_(fxt.lnki_(8, 90, 80)).tst();
	}
	@Test  public void Upright() {
		fxt.Raw_("8,90,80,upright=.8").Expd_(fxt.lnki_(8, 90, 80).Lnki_upright_(.8d)).tst();
	}
	@Test  public void Thumbtime() {
		fxt.Raw_("8,90,80,thumbtime=8").Expd_(fxt.lnki_(8, 90, 80).Lnki_thumbtime_(8)).tst();
	}
	@Test  public void Many() {
		fxt.Raw_("8,20,30;8,90,80,upright=.8,thumbtime=8;8,30,40").Expd_(fxt.lnki_(8, 20, 30), fxt.lnki_(8, 90, 80).Lnki_upright_(.8d).Lnki_thumbtime_(8), fxt.lnki_(8, 30, 40)).tst();
	}
}
class Xofo_lnki_parser_fxt {
	Tst_mgr tst_mgr = new Tst_mgr();
	Xofo_lnki_parser parser = new Xofo_lnki_parser();
	public void Reset() {raw = null; expd = null;}
	public Xofo_lnki_chkr lnki_(int t, int w, int h) {return new Xofo_lnki_chkr().Lnki_type_((byte)t).Lnki_w_(w).Lnki_h_(h);}
	public Xofo_lnki_parser_fxt Raw_(String v) {raw = v; return this;} private String raw;
	public Xofo_lnki_parser_fxt Expd_(Xofo_lnki_chkr... v) {expd = v; return this;} private Xofo_lnki_chkr[] expd;
	public Xofo_lnki_parser_fxt tst() {
		byte[] bry = Bry_.new_utf8_(raw);
		Xofo_lnki[] actl = parser.Parse_ary(bry, 0, bry.length);
		tst_mgr.Tst_ary("", expd, actl);
		return this;
	}
}
class Xofo_lnki_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Xofo_lnki.class;}
	public Xofo_lnki_chkr Lnki_type_(byte v) {lnki_type = v; return this;} private byte lnki_type = Byte_.Max_value_127;
	public Xofo_lnki_chkr Lnki_w_(int v) {lnki_w = v; return this;} private int lnki_w = -1;
	public Xofo_lnki_chkr Lnki_h_(int v) {lnki_h = v; return this;} private int lnki_h = -1;
	public Xofo_lnki_chkr Lnki_upright_(double v) {lnki_upright = v; return this;} double lnki_upright = -1;
	public Xofo_lnki_chkr Lnki_thumbtime_(int v) {lnki_thumbtime = v; return this;} private int lnki_thumbtime = -1;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xofo_lnki actl = (Xofo_lnki)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(lnki_type == Byte_.Max_value_127, path, "lnki_type", lnki_type, actl.Lnki_type());
		rv += mgr.Tst_val(lnki_w == -1, path, "lnki_w", lnki_w, actl.Lnki_w());
		rv += mgr.Tst_val(lnki_h == -1, path, "lnki_h", lnki_h, actl.Lnki_h());
		rv += mgr.Tst_val(lnki_upright == -1, path, "lnki_upright", lnki_upright, actl.Lnki_upright());
		rv += mgr.Tst_val(lnki_thumbtime == -1, path, "lnki_thumbtime", lnki_thumbtime, Xof_lnki_time.X_int(actl.Lnki_thumbtime()));
		return rv;
	}
}
