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
package gplx.dbs.sqls.wtrs;
import gplx.types.basics.utls.TypeIds;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDate;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.ShortUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimal;
public class Sql_val_wtr {
//		private final Bry_bfr tmp_bfr = Bry_bfr_.New(32);
	public byte Seq__quote = AsciiByte.Apos, Seq__escape = AsciiByte.Backslash;
	public void Bld_val(BryWtr bfr, Sql_wtr_ctx ctx, Object val) {
		if (ctx.Mode_is_prep) {
			bfr.AddByte(AsciiByte.Question);
			return;
		}
		if (val == null) {
			bfr.AddStrA7("NULL");
			return;
		}
		int tid_type = TypeIds.ToIdByCls(val.getClass());
		switch (tid_type) {
			case TypeIds.IdBool:			Bld_val__bool		(bfr, BoolUtl.Cast(val)); break;
			case TypeIds.IdByte:			Bld_val__byte		(bfr, ByteUtl.Cast(val)); break;
			case TypeIds.IdShort:			Bld_val__short		(bfr, ShortUtl.Cast(val)); break;
			case TypeIds.IdInt:			Bld_val__int		(bfr, IntUtl.Cast(val)); break;
			case TypeIds.IdLong:			Bld_val__long		(bfr, LongUtl.Cast(val)); break;
			case TypeIds.IdFloat:			Bld_val__float		(bfr, FloatUtl.Cast(val)); break;
			case TypeIds.IdDouble:			Bld_val__double		(bfr, DoubleUtl.Cast(val)); break;
			case TypeIds.IdDecimal:		Bld_val__decimal	(bfr, (GfoDecimal)val); break;
			case TypeIds.IdStr:			Bld_val__str		(bfr, StringUtl.Cast(val)); break;
			case TypeIds.IdDate:			Bld_val__date		(bfr, ((GfoDate)val)); break;
			case TypeIds.IdObj:			Bld_val__str		(bfr, ObjectUtl.ToStrOrNull(val)); break;
		}
	}
	public void Bld_val__bool		(BryWtr bfr, boolean val)			{bfr.AddIntDigits(1, val ? 1 : 0);}	// NOTE: save boolean to 0 or 1 b/c sqlite doesn't support true / false //{bfr.Add_str_a7(val ? "true" : "false");}
	public void Bld_val__byte		(BryWtr bfr, byte val)			{bfr.AddByteVariable(val);}
	public void Bld_val__short		(BryWtr bfr, short val)		{bfr.AddShortVariable(val);}
	public void Bld_val__int		(BryWtr bfr, int val)			{bfr.AddIntVariable(val);}
	public void Bld_val__long		(BryWtr bfr, long val)			{bfr.AddLongVariable(val);}
	public void Bld_val__float		(BryWtr bfr, float val)		{bfr.AddFloat(val);}
	public void Bld_val__double		(BryWtr bfr, double val)		{bfr.AddDouble(val);}
	public void Bld_val__date		(BryWtr bfr, GfoDate val)		{bfr.AddStrU8Many("'", val.ToStrGplxLong(), "'");}
	public void Bld_val__decimal	(BryWtr bfr, GfoDecimal val)	{bfr.AddStrU8Many("'", val.ToStr(), "'");}
	public void Bld_val__str		(BryWtr bfr, String val) {
//			byte[] bry = Bry_.new_u8(val); int len = bry.length; int pos = 0; int prv = -1; boolean dirty = false;
//			while (true) {
//				if (pos == len) break;
//				byte b = bry[pos];
//				if		(b == Seq__quote) {
//					if (!dirty) {dirty = true; if (prv != -1) {tmp_bfr.Add_mid(bry, prv, pos); prv = -1;}}
//					tmp_bfr.Add_byte(Seq__quote).Add_byte(Seq__quote);		// double-up
//				}
////				else if (b == Seq__escape) {
////					if (!dirty) {dirty = true; if (prv != -1) {tmp_bfr.Add_mid(bry, prv, pos); prv = -1;}}
////					tmp_bfr.Add_byte(Seq__escape).Add_byte(Seq__escape);	// double-up
////				}
//				else
//					if (prv == -1) prv = pos;
//				++pos;
//			}
//			if (dirty && prv != -1) tmp_bfr.Add_mid(bry, prv, len);

		bfr.AddByte(Seq__quote);
		bfr.AddStrU8(StringUtl.Replace(val, "'", "''"));
//			if (dirty)
//				bfr.Add_bfr_and_clear(tmp_bfr);
//			else
//				bfr.Add(bry);
		bfr.AddByte(Seq__quote);
	}
}
class Sql_val_wtr_sqlite extends Sql_val_wtr {	@Override public void Bld_val__bool(BryWtr bfr, boolean val) {
		bfr.AddIntDigits(1, val ? 1 : 0);	// NOTE: save boolean to 0 or 1 b/c sqlite doesn't support true / false
	}
}
class Sql_val_wtr_mysql extends Sql_val_wtr {	@Override public void Bld_val__str(BryWtr bfr, String val) {
		if (StringUtl.Has(val, "\\")) val = StringUtl.Replace(val, "\\", "\\\\");
		super.Bld_val__str(bfr, val);
	}
}
