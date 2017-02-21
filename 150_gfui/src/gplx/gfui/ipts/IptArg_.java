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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
public class IptArg_ {
	public static final    IptArg[] Ary_empty = new IptArg[0];
	public static final    IptArg Null = null;
	public static final String Wildcard_key = "wildcard";
	public static IptArg Wildcard = new IptKey(Int_.Max_value, Wildcard_key);
	public static boolean Is_null_or_none(IptArg arg) {return arg == Null || arg == IptKey_.None;}
	public static IptArg[] Ary(IptArg... v) {return v;}
	public static IptArg[] parse_ary_or_empty(String v) {
		IptArg[] rv = IptArg_.parse_ary_(v);
		int len = rv.length;
		for (int i = 0; i < len; i++)
			if (rv[i] == null) return Ary_empty;// indicates failed parse
		return rv;
	}
	public static IptArg[] parse_ary_(String raw) {
		String[] args = String_.Split(raw, "|");
		int args_len = args.length; if (args_len == 0) return Ary_empty;
		IptArg[] rv = new IptArg[args_len];
		for (int i = 0; i < args_len; i++)
			rv[i] = parse(String_.Trim(args[i]));
		return rv;
	}
	public static IptArg parse_chain_(String raw) {return IptKeyChain.parse(raw);}
	public static IptArg parse_or_none_(String raw) {
		try {
			return String_.Eq(raw, String_.Empty)
				? IptKey_.None
				: parse(raw);
		}
		catch (Exception exc) {	// as an "or" proc, handle errors; note that it may accept raw values from cfg files, so invalid input is possible; DATE:2014-06-04
			Err_.Noop(exc);
			return IptKey_.None;
		}
	}
	public static IptArg parse(String raw) {
		if		(String_.Has(raw, ","))				return IptKeyChain.parse(raw);
		String bgn = String_.GetStrBefore(raw, ".");
		if		(String_.Eq(bgn, "wheel"))			return IptMouseWheel_.parse(raw);
		else if (String_.Eq(bgn, "mouse"))			return IptMouseBtn_.parse(raw);
		else if (String_.Eq(bgn, "key"))			return IptKey_.parse(raw);
		else										return IptMacro.Instance.parse(raw);
	}
	// NOTE: the following two methods should theoretically be interface methods, but since they are only used by two procs, they will be handled with if/else
	@gplx.Internal protected static IptEventType EventType_default(IptArg arg) {
		Class<?> type = arg.getClass();
		if		(	type == IptKey.class
				||	type == IptKeyChain.class)	return IptEventType_.KeyDown;
		else if (type == IptMouseBtn.class)		return IptEventType_.MouseUp;	// changed from MouseDown; confirmed against Firefox, Eclipse; DATE:2014-05-16
		else if (type == IptMouseWheel.class)		return IptEventType_.MouseWheel;
		else if (type == IptMouseMove.class)		return IptEventType_.MouseMove;
		else										throw Err_.new_unhandled(type);
	}
	@gplx.Internal protected static boolean EventType_match(IptArg arg, IptEventType match) {
		Class<?> type = arg.getClass();
		if		(	type == IptKey.class
				||	type == IptKeyChain.class)	return match == IptEventType_.KeyDown || match == IptEventType_.KeyUp || match == IptEventType_.KeyDown;
		else if (type == IptMouseBtn.class)		return match == IptEventType_.MouseDown || match == IptEventType_.MouseUp || match == IptEventType_.MousePress;
		else if (type == IptMouseWheel.class)		return match == IptEventType_.MouseWheel;
		else if (type == IptMouseMove.class)		return match == IptEventType_.MouseMove;
		else										throw Err_.new_unhandled(type);
	}
}
class IptMacro {
	public void Reg(String prefix, String alias, IptArg arg) {
		if (regy == null) Init();
		Ordered_hash list = (Ordered_hash)regy.Get_by(prefix);
		if (list == null) {
			list = Ordered_hash_.New();
			regy.Add(prefix, list);
		}
		list.Add_if_dupe_use_nth(alias, arg);
	}
	void Init() {
		regy = Ordered_hash_.New();
		Reg("mod", "c", IptKey_.add_(IptKey_.Ctrl));
		Reg("mod", "a", IptKey_.add_(IptKey_.Alt));
		Reg("mod", "s", IptKey_.add_(IptKey_.Shift));
		Reg("mod", "ca", IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt));
		Reg("mod", "cs", IptKey_.add_(IptKey_.Ctrl, IptKey_.Shift));
		Reg("mod", "as", IptKey_.add_(IptKey_.Alt, IptKey_.Shift));
		Reg("mod", "cas", IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.Shift));
	}
	public IptArg parse(String raw) {
		if (regy == null) Init();
		String[] plusAry = String_.Split(raw, "+");
		String[] dotAry	= String_.Split(plusAry[0], ".");
		String bgn = dotAry[0], end = dotAry[1];
		Ordered_hash list = (Ordered_hash)regy.Get_by(bgn);
		if (list == null) throw parse_err(raw, "list not found").Args_add("list", bgn);
		IptKey rv = (IptKey)list.Get_by(end);
		if (rv == null) throw parse_err(raw, "arg not found").Args_add("arg", end);
		for (int i = 1; i < plusAry.length; i++) {
			rv = rv.Add((IptKey)IptKey_.parse(plusAry[i]));
		}
		return rv;
	}
	Ordered_hash regy;
	static Err parse_err(String raw, String loc) {return Err_.new_("gfui", "could not parse IptArg", "raw", raw, "loc", loc).Trace_ignore_add_1_();}
	public static final    IptMacro Instance = new IptMacro(); IptMacro() {}
}
