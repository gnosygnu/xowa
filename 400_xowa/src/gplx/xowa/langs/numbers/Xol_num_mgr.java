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
package gplx.xowa.langs.numbers;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimal;
public class Xol_num_mgr implements Gfo_invk {
	private boolean digits_translate;
	protected BryWtr tmp_bfr = BryWtr.NewAndReset(32);
	private static final byte[] Comma_bry = BryUtl.NewA7(",");
	public Xol_num_grp_fmtr Num_grp_fmtr() {return num_grp_fmtr;} private Xol_num_grp_fmtr num_grp_fmtr = new Xol_num_grp_fmtr();
	public Xol_transform_mgr Separators_mgr() {return separators_mgr;} private Xol_transform_mgr separators_mgr = new Xol_transform_mgr();
	public Xol_transform_mgr Digits_mgr() {return digits_mgr;} private Xol_transform_mgr digits_mgr = new Xol_transform_mgr();		
	public byte[] Raw(byte[] num) {
		if (digits_translate)
			num = digits_mgr.Replace(tmp_bfr, num, false);
		num = separators_mgr.Replace(tmp_bfr, num, false);
		num = BryUtlByWtr.ReplaceSafe(tmp_bfr, num, Comma_bry, BryUtl.Empty);
		return num;
	}
	public byte[] Format_num_no_separators(byte[] num) {return Format_num(num, true);}
	public byte[] Format_num_by_long(long val)			{return Format_num(BryUtl.NewA7(LongUtl.ToStr(val)));}
	public byte[] Format_num_by_decimal(GfoDecimal val){return Format_num(BryUtl.NewA7(val.ToStr()));}
	public byte[] Format_num(int val)					{return Format_num(BryUtl.NewA7(IntUtl.ToStr(val)));}
	public byte[] Format_num(byte[] num)				{return Format_num(num, false);}
	public byte[] Format_num(byte[] num, boolean skip_commafy) {
		if (!skip_commafy) {
			num = Commafy(num);
			num = separators_mgr.Replace(tmp_bfr, num, true);
		}
		if (digits_translate)
			num = digits_mgr.Replace(tmp_bfr, num, true);
		return num;
	}
	public byte[] Commafy(byte[] num_bry) {
		if (num_bry == null) return BryUtl.Empty;	// MW: if ( $number === null ) return '';
		if (num_grp_fmtr.Mode_is_regx())
			return num_grp_fmtr.Fmt_regx(tmp_bfr, num_bry);
		else	// NOTE: for now, return same as ###,###,###; only affects 12 languages; current implementation is bad; https://bugzilla.wikimedia.org/show_bug.cgi?id=63977
			return num_grp_fmtr.Fmt_regx(tmp_bfr, num_bry);
	}
	public Xol_num_mgr Clear() {
		digits_mgr.Clear();
		separators_mgr.Clear();
		num_grp_fmtr.Clear();
		return this;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_clear))						this.Clear();
		else if	(ctx.Match(k, Invk_separators))					return separators_mgr;
		else if	(ctx.Match(k, Invk_digits))						{digits_translate = true; return digits_mgr;}	// NOTE: only langes with a digit_transform_table will call digits; DATE:2014-05-28
		else if	(ctx.Match(k, Invk_digit_grouping_pattern))		return StringUtl.NewU8(num_grp_fmtr.Digit_grouping_pattern());
		else if	(ctx.Match(k, Invk_digit_grouping_pattern_))	num_grp_fmtr.Digit_grouping_pattern_(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_clear = "clear", Invk_separators = "separators"
	, Invk_digits = "digits", Invk_digit_grouping_pattern = "digit_grouping_pattern", Invk_digit_grouping_pattern_ = "digit_grouping_pattern_";
	public static final byte[]
	  Separators_key__grp = new byte[]{AsciiByte.Comma}
	, Separators_key__dec = new byte[]{AsciiByte.Dot}
	;
}
