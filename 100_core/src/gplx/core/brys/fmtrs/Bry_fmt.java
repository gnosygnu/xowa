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
package gplx.core.brys.fmtrs; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bry_fmt {
	private byte[] src;
	private Bry_fmt_itm[] itms; private int itms_len;
	private Bfr_fmt_arg[] args = Bfr_fmt_arg.Ary_empty;
	private byte[][] keys = Bry_.Ary_empty;
	private boolean dirty;
	public Bry_fmt(byte[] src, byte[][] keys, Bfr_fmt_arg[] args) {
		dirty = true;
		this.src = src; this.keys = keys; this.args = args;
	}
	public Bry_fmt Fmt_(String v)						{dirty = true; src = Bry_.new_u8(v); return this;}
	public Bry_fmt Args_(Bfr_fmt_arg... v)		{dirty = true; args = v; return this;}
	public Bry_fmt Keys_(byte[]... v)				{dirty = true; keys = v; return this;}
	public String Bld_many_to_str_auto_bfr(Object... vals) {
		Bry_bfr bfr = Bry_bfr_.Get();
		try {return Bld_many_to_str(bfr, vals);} 
		finally {bfr.Mkr_rls();}
	}
	public String Bld_many_to_str(Bry_bfr bfr, Object... vals) {
		Bld_many(bfr, vals);
		return bfr.To_str_and_clear();
	}
	public void Bld_many(Bry_bfr bfr, Object... vals) {
		if (dirty) Compile();
		int vals_len = vals.length;
		for (int i = 0; i < itms_len; ++i) {
			Bry_fmt_itm itm = itms[i];
			switch (itm.Tid) {
				case Bry_fmt_itm.Tid__txt: bfr.Add_mid(src, itm.Src_bgn, itm.Src_end); break;
				case Bry_fmt_itm.Tid__arg: itm.Arg.Bfr_arg__add(bfr);break;
				case Bry_fmt_itm.Tid__key: 
					int idx = itm.Key_idx;
					if (idx > -1 && idx < vals_len)
						bfr.Add_obj(vals[idx]);
					else
						bfr.Add_mid(src, itm.Src_bgn, itm.Src_end);
					break;
				default: throw Err_.new_unhandled(itm.Tid);
			}
		}
	}
	private void Compile() {
		dirty = false;
		this.itms = Bry_fmt_parser_.Parse(Byte_ascii.Tilde, Byte_ascii.Curly_bgn, Byte_ascii.Curly_end, args, keys, src);
		this.itms_len = itms.length;
	}
	public static Bry_fmt New(String fmt, String... keys) {return new Bry_fmt(Bry_.new_u8(fmt), Bry_.Ary(keys), Bfr_fmt_arg.Ary_empty);}
	public static Bry_fmt New(byte[] fmt, String... keys) {return new Bry_fmt(fmt				, Bry_.Ary(keys), Bfr_fmt_arg.Ary_empty);}
}
class Bry_fmt_itm {
	public Bry_fmt_itm(int tid, int src_bgn, int src_end) {
		this.Tid = tid;
		this.Src_bgn = src_bgn;
		this.Src_end = src_end;
	}
	public int		Tid;
	public int		Src_bgn;
	public int		Src_end;
	public int		Key_idx;
	public Bfr_arg	Arg;

	public static final int Tid__txt = 0, Tid__key = 1, Tid__arg = 2;
}
