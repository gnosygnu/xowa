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
package gplx.xowa.htmls.core.wkrs.bfr_args; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
public class Bfr_arg__hatr_arg implements Bfr_arg_clearable {
	private final    byte[] atr_bgn;
	private Bfr_arg_clearable val_as_arg;
	public Bfr_arg__hatr_arg(byte[] key) {this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key);}
	public Bfr_arg__hatr_arg Set_by_arg(Bfr_arg_clearable v)			{val_as_arg = v; return this;}
	public Bfr_arg__hatr_arg Set_by_arg_empty()							{val_as_arg = Bfr_arg__html_atr__empty.Instance; return this;}
	public void Bfr_arg__clear() {val_as_arg = null;}
	public boolean Bfr_arg__missing() {return val_as_arg == null || val_as_arg.Bfr_arg__missing();}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		val_as_arg.Bfr_arg__add(bfr);
		bfr.Add_byte_quote();
	}
}
class Bfr_arg__html_atr__empty implements Bfr_arg_clearable {
	public void Bfr_arg__clear() {}
	public boolean Bfr_arg__missing() {return false;}
	public void Bfr_arg__add(Bry_bfr bfr) {}
        public static final    Bfr_arg__html_atr__empty Instance = new Bfr_arg__html_atr__empty(); Bfr_arg__html_atr__empty() {}
}
