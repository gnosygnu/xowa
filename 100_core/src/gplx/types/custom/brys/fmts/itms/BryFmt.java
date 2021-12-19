/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.fmts.itms;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.errs.ErrUtl;
public class BryFmt {
	private final Object thread_lock = new Object();
	private byte[] src;
	private BryFmtItm[] itms; private int itms_len;
	private BfrFmtArg[] args = BfrFmtArg.Ary_empty;
	private byte[][] keys = BryUtl.AryEmpty;
	private Object[] vals = null;
	private boolean dirty;
	public BryFmt(byte[] src, byte[][] keys, BfrFmtArg[] args) {
		dirty = true;
		this.src = src; this.keys = keys; this.args = args;
	}
	public byte[] Fmt()                                    {return src;}
	public BryFmt Fmt_(String v)                        {return Fmt_(BryUtl.NewU8(v));}
	public BryFmt Fmt_(byte[] v)                        {dirty = true; src = v; return this;}
	public BryFmt Args_(BfrFmtArg... v)        {dirty = true; args = v; return this;}
	public BryFmt Keys_(String... v)                {return Keys_(BryUtl.Ary(v));}
	public BryFmt Keys_(byte[]... v)                {dirty = true; keys = v; return this;}
	public BryFmt Vals_(Object... v)                {vals = v; return this;}
	public String Bld_many_to_str_auto_bfr(Object... vals_ary) {
		BryWtr bfr = BryBfrUtl.Get();
		try {return Bld_many_to_str(bfr, vals_ary);} 
		finally {bfr.MkrRls();}
	}
	public String Bld_many_to_str(BryWtr bfr, Object... vals_ary) {
		Bld_many(bfr, vals_ary);
		return bfr.ToStrAndClear();
	}
	public byte[] Bld_many_to_bry(BryWtr bfr, Object... vals_ary) {
		Bld_many(bfr, vals_ary);
		return bfr.ToBryAndClear();
	}
	public void Bld_many(BryWtr bfr, Object... vals_ary) {
		if (dirty) Compile();
		int vals_len = vals_ary.length;
		for (int i = 0; i < itms_len; ++i) {
			BryFmtItm itm = itms[i];
			switch (itm.Tid) {
				case BryFmtItm.Tid__txt: bfr.AddMid(src, itm.Src_bgn, itm.Src_end); break;
				case BryFmtItm.Tid__arg: itm.Arg.AddToBfr(bfr);break;
				case BryFmtItm.Tid__key:
					int idx = itm.Key_idx;
					if (idx > -1 && idx < vals_len)
						bfr.AddObj(vals_ary[idx]);
					else
						bfr.AddMid(src, itm.Src_bgn, itm.Src_end);
					break;
				default: throw ErrUtl.NewUnhandled(itm.Tid);
			}
		}
	}
	public String To_str() {return Bld_many_to_str_auto_bfr(vals);}
	private void Compile() {
		synchronized (thread_lock) {
			dirty = false;
			this.itms = BryFmtParserUtl.Parse(AsciiByte.Tilde, AsciiByte.CurlyBgn, AsciiByte.CurlyEnd, args, keys, src);
			this.itms_len = itms.length;
		}
	}
	public static BryFmt New(String fmt, String... keys) {return new BryFmt(BryUtl.NewU8(fmt), BryUtl.Ary(keys), BfrFmtArg.Ary_empty);}
	public static BryFmt New(byte[] fmt, String... keys) {return new BryFmt(fmt                , BryUtl.Ary(keys), BfrFmtArg.Ary_empty);}
	public static String Make_str(String fmt_str, Object... vals) {return Auto(fmt_str).Vals_(vals).To_str();}
	public static BryFmt Auto_nl_apos(String... lines) {return Auto(BryUtlByWtr.NewU8NlSwapApos(lines));}
	public static BryFmt Auto_nl_skip_last(String... lines) {return Auto(BryUtl.NewU8(StringUtl.ConcatLinesNlSkipLast(lines)));}
	public static BryFmt Auto(String fmt_str) {return Auto(BryUtl.NewU8(fmt_str));}
	public static BryFmt Auto(byte[] fmt_bry) {
		byte[][] keys_bry = BryFmtParserUtl.ParseKeys(fmt_bry);
		return new BryFmt(fmt_bry, keys_bry, BfrFmtArg.Ary_empty);
	}
}
