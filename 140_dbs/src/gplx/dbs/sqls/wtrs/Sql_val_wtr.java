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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_val_wtr {
//		private final    Bry_bfr tmp_bfr = Bry_bfr_.New(32);
	public byte Seq__quote = Byte_ascii.Apos, Seq__escape = Byte_ascii.Backslash;
	public void Bld_val(Bry_bfr bfr, Sql_wtr_ctx ctx, Object val) {
		if (ctx.Mode_is_prep) {
			bfr.Add_byte(Byte_ascii.Question);
			return;
		}
		if (val == null) {
			bfr.Add_str_a7("NULL");
			return;
		}
		int tid_type = Type_adp_.To_tid_type(val.getClass());
		switch (tid_type) {
			case Type_adp_.Tid__bool:			Bld_val__bool		(bfr, Bool_.Cast(val)); break;
			case Type_adp_.Tid__byte:			Bld_val__byte		(bfr, Byte_.cast(val)); break;
			case Type_adp_.Tid__short:			Bld_val__short		(bfr, Short_.cast(val)); break;
			case Type_adp_.Tid__int:			Bld_val__int		(bfr, Int_.cast(val)); break;
			case Type_adp_.Tid__long:			Bld_val__long		(bfr, Long_.cast(val)); break;
			case Type_adp_.Tid__float:			Bld_val__float		(bfr, Float_.cast(val)); break;
			case Type_adp_.Tid__double:			Bld_val__double		(bfr, Double_.cast(val)); break;
			case Type_adp_.Tid__decimal:		Bld_val__decimal	(bfr, Decimal_adp_.cast(val)); break;
			case Type_adp_.Tid__str:			Bld_val__str		(bfr, String_.cast(val)); break;
			case Type_adp_.Tid__date:			Bld_val__date		(bfr, DateAdp_.cast(val)); break;
			case Type_adp_.Tid__obj:			Bld_val__str		(bfr, Object_.Xto_str_strict_or_null(val)); break;
		}
	}
	@gplx.Virtual public void Bld_val__bool		(Bry_bfr bfr, boolean val)			{bfr.Add_int_digits(1, val ? 1 : 0);}	// NOTE: save boolean to 0 or 1 b/c sqlite doesn't support true / false //{bfr.Add_str_a7(val ? "true" : "false");}
	@gplx.Virtual public void Bld_val__byte		(Bry_bfr bfr, byte val)			{bfr.Add_byte_variable(val);}
	@gplx.Virtual public void Bld_val__short		(Bry_bfr bfr, short val)		{bfr.Add_short_variable(val);}
	@gplx.Virtual public void Bld_val__int		(Bry_bfr bfr, int val)			{bfr.Add_int_variable(val);}
	@gplx.Virtual public void Bld_val__long		(Bry_bfr bfr, long val)			{bfr.Add_long_variable(val);}
	@gplx.Virtual public void Bld_val__float		(Bry_bfr bfr, float val)		{bfr.Add_float(val);}
	@gplx.Virtual public void Bld_val__double		(Bry_bfr bfr, double val)		{bfr.Add_double(val);}
	@gplx.Virtual public void Bld_val__date		(Bry_bfr bfr, DateAdp val)		{bfr.Add_str_u8_many("'", val.XtoStr_gplx_long(), "'");}
	@gplx.Virtual public void Bld_val__decimal	(Bry_bfr bfr, Decimal_adp val)	{bfr.Add_str_u8_many("'", val.To_str(), "'");}
	@gplx.Virtual public void Bld_val__str		(Bry_bfr bfr, String val) {
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

		bfr.Add_byte(Seq__quote);
		bfr.Add_str_u8(String_.Replace(val, "'", "''"));
//			if (dirty)
//				bfr.Add_bfr_and_clear(tmp_bfr);
//			else
//				bfr.Add(bry);
		bfr.Add_byte(Seq__quote);
	}
}
class Sql_val_wtr_sqlite extends Sql_val_wtr {	@Override public void Bld_val__bool(Bry_bfr bfr, boolean val) {
		bfr.Add_int_digits(1, val ? 1 : 0);	// NOTE: save boolean to 0 or 1 b/c sqlite doesn't support true / false
	}
}
class Sql_val_wtr_mysql extends Sql_val_wtr {	@Override public void Bld_val__str(Bry_bfr bfr, String val) {
		if (String_.Has(val, "\\")) val = String_.Replace(val, "\\", "\\\\");
		super.Bld_val__str(bfr, val);
	}
}
