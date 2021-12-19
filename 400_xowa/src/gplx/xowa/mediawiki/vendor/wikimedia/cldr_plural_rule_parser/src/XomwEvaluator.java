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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.mediawiki.XomwLog_;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpFloat_;
import gplx.xowa.mediawiki.XophpInt_;
import gplx.xowa.mediawiki.XophpMath_;
import gplx.xowa.mediawiki.XophpRegex_;
import gplx.xowa.mediawiki.XophpString_;
// MW.SRC:1.33.1
public class XomwEvaluator {
	/**
	* Evaluate a number against a set of plural rules. If a rule passes,
	* return the index of plural rule.
	*
	* @param int number The number to be evaluated against the rules
	* @param array rules The associative array of plural rules in pluralform => rule format.
	* @return int The index of the plural form which passed the evaluation
	*/
	public static int evaluate(String number, XophpArray rules) {
		rules = XomwEvaluator.compile(rules);

		return XomwEvaluator.evaluateCompiled(number, rules);
	}

	/**
	* Convert a set of rules to a compiled form which is optimised for
	* fast evaluation. The result will be an array of strings, and may be cached.
	*
	* @param array rules The rules to compile
	* @return array An array of compile rules.
	*/
	public static XophpArray compile(XophpArray rules) {
		XophpArray rv = XophpArray.New();
		// We can't use array_map() for this because it generates a warning if
		// there is an exception.
		int rules_len = rules.Len();
		for (int i = 0; i < rules_len; i++) {
			String rule = rules.Get_at_str(i);
			rule = XomwConverter.convert(rule);
			rv.Add(rule);
		}

		return rv;
	}

	/**
	* Evaluate a compiled set of rules returned by compile(). Do not allow
	* the user to edit the compiled form, or else PHP errors may result.
	*
	* @param String number The number to be evaluated against the rules, in English, or it
	*   may be a type convertible to String.
	* @param array rules The associative array of plural rules in pluralform => rule format.
	* @return int The index of the plural form which passed the evaluation
	*/
	public static int evaluateCompiled(String number_str, XophpArray rules) {
		// Calculate the values of the operand symbols
		// String number_str = XophpInt_.strval(number);

		// NOTE: '/^ -? ( ([0-9]+) (?: \. ([0-9]+) )? )$/x'
		XophpArray m = XophpArray.New();
		if (!XophpRegex_.preg_match_bool(gplx.langs.regxs.Regx_adp_.new_("^-?(([0-9]+)(?:\\.([0-9]+))?)"), number_str, m, 0, 0)) {
			XomwLog_.wfDebug_by_method("evaluateCompiled", ": invalid number input, returning \"other\"\n");
			return XophpArray.count(rules);
		}

		XophpArray operandSymbols = null;			
		if (!XophpArray.isset(m, 3)) {
			operandSymbols = XophpArray.New()
				.Add("n", GfoDecimalUtl.NewByInt(XophpInt_.intval(m.Get_at_str(1))))
				.Add("i", GfoDecimalUtl.NewByInt(XophpInt_.intval(m.Get_at_str(1))))
				.Add("v", GfoDecimalUtl.Zero)
				.Add("w", GfoDecimalUtl.Zero)
				.Add("f", GfoDecimalUtl.Zero)
				.Add("t", GfoDecimalUtl.Zero)
			;
		} else {
			String absValStr = m.Get_at_str(1);
			String intStr = m.Get_at_str(2);
			String fracStr = m.Get_at_str(3);
			operandSymbols = XophpArray.New()
				.Add("n", GfoDecimalUtl.NewByDouble(XophpFloat_.floatval(absValStr)))
				.Add("i", GfoDecimalUtl.NewByInt(XophpInt_.intval(intStr)))
				.Add("v", GfoDecimalUtl.NewByInt(XophpString_.strlen(fracStr)))
				.Add("w", GfoDecimalUtl.NewByInt(XophpString_.strlen(XophpString_.rtrim(fracStr, "0"))))
				.Add("f", GfoDecimalUtl.NewByInt(XophpInt_.intval(fracStr)))
				.Add("t", GfoDecimalUtl.NewByInt(XophpInt_.intval(XophpString_.rtrim(fracStr, "0"))))
			;
		}

		// The compiled form is RPN, with tokens strictly delimited by
		// spaces, so this is a simple RPN evaluator.
		int rules_len = rules.Len();
		for (int i = 0; i < rules_len; i++) {
			String rule = rules.Get_at_str(i);
			XophpArray stack = XophpArray.New();
			int zero = XophpString_.ord("0");
			int nine = XophpString_.ord("9");

			String[] tokens = XophpString_.explode(" ", rule);
			for (String token : tokens) {
				int ord = XophpString_.ord(token);
				if (XophpArray.isset(operandSymbols, token)) {
					stack.Add(XomwStackItem.New__number((GfoDecimal)operandSymbols.Get_by(token)));
				} else if (ord >= zero && ord <= nine) {
					stack.Add(XomwStackItem.New__number(GfoDecimalUtl.NewByInt(XophpInt_.intval(token))));
				} else {
					XomwStackItem right = (XomwStackItem)XophpArray.array_pop(stack);
					XomwStackItem left = (XomwStackItem)XophpArray.array_pop(stack);
					XomwStackItem result = XomwEvaluator.doOperation(token, left, right);
					stack.Add(result);
				}
			}
			if (((XomwStackItem)stack.Get_at(0)).Tid() == XomwStackItem.Tid__bool && ((XomwStackItem)stack.Get_at(0)).As_bool()) {
				return i;
			}
		}
		// None of the provided rules match. The number belongs to category
		// "other", which comes last.
		return XophpArray.count(rules);
	}

	/**
	* Do a single operation
	*
	* @param String token The token String
	* @param mixed left The left operand. If it is an Object, its state may be destroyed.
	* @param mixed right The right operand
	* @throws Error
	* @return mixed The operation result
	*/
	// XO: left / right can be boolean, Decimal, Range
	private static final XophpArray doOperationTokens = XophpArray.New().Add_as_key_and_val_many("in", "not-in", "within", "not-within");
	private static XomwStackItem doOperation(String token, XomwStackItem left, XomwStackItem right) {
		if (doOperationTokens.Has(token)) {
			if (right.Tid() != XomwStackItem.Tid__range) {
				right = XomwStackItem.New__range(new XomwRange(right.As_num(), null));
			}
		}
		if (StringUtl.Eq(token, "or")) {
			return XomwStackItem.New__bool(left.As_bool() || right.As_bool());
		}
		else if (StringUtl.Eq(token, "and")) {
			return XomwStackItem.New__bool(left.As_bool() && right.As_bool());
		}
		else if (StringUtl.Eq(token, "is")) {
			return XomwStackItem.New__bool(left.As_bool() == right.As_bool());
		}
		else if (StringUtl.Eq(token, "is-not")) {
			return XomwStackItem.New__bool(left.As_bool() != right.As_bool());
		}
		else if (StringUtl.Eq(token, "in")) {
			return XomwStackItem.New__bool(right.As_range().isNumberIn(left.As_num()));
		}
		else if (StringUtl.Eq(token, "not-in")) {
			return XomwStackItem.New__bool(!right.As_range().isNumberIn(left.As_num()));
		}
		else if (StringUtl.Eq(token, "within")) {
			return XomwStackItem.New__bool(right.As_range().isNumberWithin(left.As_num()));
		}
		else if (StringUtl.Eq(token, "not-within")) {
			return XomwStackItem.New__bool(!right.As_range().isNumberWithin(left.As_num()));
		}
		else if (StringUtl.Eq(token, "mod")) {
			if (left.Tid() == XomwStackItem.Tid__number) {
				return XomwStackItem.New__number(XophpMath_.fmod_decimal(left.As_num(), right.As_num()));
			}

			return XomwStackItem.New__number(XophpMath_.fmod_decimal(left.As_num(), right.As_num()));
		}
		else if (StringUtl.Eq(token, ",")) {
			XomwRange range = null;
			if (left.Tid() == XomwStackItem.Tid__range) {
				range = left.As_range();
			} else {
				range = new XomwRange(left.As_num(), null);
			}
			range.add(right.As_obj());

			return XomwStackItem.New__range(range);
		}
		else if (StringUtl.Eq(token, "..")) {
			return XomwStackItem.New__range(new XomwRange(left.As_num(), right.As_num()));
		}
		else {
			throw new XomwError("Invalid RPN token");
		}
	}
}
class XomwStackItem {
	XomwStackItem(int tid, boolean val__bool, GfoDecimal val__number, XomwRange val__range) {
		this.tid = tid;
		this.val__bool = val__bool;
		this.val__number = val__number;
		this.val__range = val__range;
	}
	public int Tid() {return tid;} private final int tid;
	public boolean As_bool() {
		if (tid != Tid__bool) Fail_bc_wrong_type(Tid__bool);
		return val__bool;
	} private final boolean val__bool;
	public GfoDecimal As_num() {
		if (tid != Tid__number) Fail_bc_wrong_type(Tid__number);
		return val__number;
	} private final GfoDecimal val__number;
	public XomwRange As_range() {
		if (tid != Tid__range) Fail_bc_wrong_type(Tid__range);
		return val__range;
	} private final XomwRange val__range;
	public Object As_obj() {
		switch (tid) {
			case Tid__bool: return val__bool;
			case Tid__number: return val__number;
			case Tid__range: return val__range;
			default: throw ErrUtl.NewUnhandled(tid);
		}
	}
	
	private void Fail_bc_wrong_type(int expd) {
		throw new XomwError("wrong type; expd=" + Tid_to_str(expd) + "; actl=" + Tid_to_str(tid));
	}
	private String Tid_to_str(int v) {
		switch (tid) {
			case Tid__bool: return "boolean";
			case Tid__number: return "number";
			case Tid__range: return "range";
			default: throw ErrUtl.NewUnhandled(tid);
		}
	}

	public static XomwStackItem New__bool(boolean v)          {return new XomwStackItem(Tid__bool, v, null, null);}
	public static XomwStackItem New__number(GfoDecimal v) {return new XomwStackItem(Tid__number, false, v, null);}
	public static XomwStackItem New__range(XomwRange v)    {return new XomwStackItem(Tid__range, false, null, v);}
	public static final int
	  Tid__bool = 0
	, Tid__number = 1
	, Tid__range = 2;
}