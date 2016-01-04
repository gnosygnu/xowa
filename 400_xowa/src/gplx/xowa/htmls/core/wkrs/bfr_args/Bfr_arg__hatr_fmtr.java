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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
public class Bfr_arg__hatr_fmtr implements Bfr_arg {
	private final byte[] atr_bgn;
	private final Bry_fmtr fmtr = Bry_fmtr.new_();
	private Object[] args;
	public Bfr_arg__hatr_fmtr(byte[] key, String fmt, String... keys) {
		this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key);
		this.fmtr.Fmt_(fmt).Keys_(keys);
		this.Clear();
	}
	public void Set_args(Object... args) {this.args = args;}
	public void Clear() {args = null;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return args == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		fmtr.Bld_bfr_many(bfr, args);
		bfr.Add_byte_quote();			
	}
}
