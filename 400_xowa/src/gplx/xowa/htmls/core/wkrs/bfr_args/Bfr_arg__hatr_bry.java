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
import gplx.core.brys.*; import gplx.core.primitives.*;
public class Bfr_arg__hatr_bry implements Bfr_arg_clearable {
	private final byte[] atr_bgn;		
	public Bfr_arg__hatr_bry(byte[] key) {this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key); this.Bfr_arg__clear();}
	private Bfr_arg_clearable arg;
	public byte[] Src() {return src;} private byte[] src;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Bfr_arg__hatr_bry Set_by_bry(byte[] bry)						{src = bry; return this;}
	public Bfr_arg__hatr_bry Set_by_mid(byte[] bry, int bgn, int end)	{src = bry; src_bgn = bgn; src_end = end; return this;}
	public Bfr_arg__hatr_bry Set_by_arg(Bfr_arg_clearable v)			{arg = v; return this;}
	public Bfr_arg__hatr_bry Set_by_mid_or_empty(byte[] bry, int bgn, int end)	{
		if (end == -1)	this.Set_by_bry(Bry_.Empty);
		else			this.Set_by_mid(bry, bgn, end);
		return this;
	}
	public Bfr_arg__hatr_bry Set_by_mid_or_null(byte[] bry, int bgn, int end) {
		if (end != -1)	this.Set_by_mid(bry, bgn, end);
		return this;
	}
	public void Bfr_arg__clear() {arg = null; src = null; src_bgn = src_end = -1;}
	public boolean Bfr_arg__missing() {return src == null && (arg == null || arg.Bfr_arg__missing());}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		if		(src == null)	arg.Bfr_arg__add(bfr);
		else if	(src_bgn == -1)	bfr.Add(src);
		else					bfr.Add_mid(src, src_bgn, src_end);
		bfr.Add_byte_quote();
	}
}
