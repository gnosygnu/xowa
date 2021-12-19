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
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.GfoDecimal;
import gplx.xowa.mediawiki.*;
// MW.SRC:1.33.1
/**
* Evaluator helper class representing a range list.
*/
class XomwRange {
	/**
	* The parts
	*
	* @var array
	*/
	public XophpArray parts = XophpArray.New();

	/**
	* Initialize a new instance of Range
	*
	* @param int start The start of the range
	* @param int|boolean end The end of the range, or false if the range is not bounded.
	*/
	public XomwRange(GfoDecimal start, GfoDecimal end) {
		if (end == null) {
			this.parts.Add(start);
		} else {
			this.parts.Add(XophpArray.New(start, end));
		}
	}

	/**
	* Determine if the given number is inside the range.
	*
	* @param int number The number to check
	* @param boolean integerConstraint If true, also asserts the number is an integer;
	*   otherwise, number simply has to be inside the range.
	* @return boolean True if the number is inside the range; otherwise, false.
	*/
	public boolean isNumberIn(GfoDecimal number) {return isNumberIn(number, true);}
	public boolean isNumberIn(GfoDecimal number, boolean integerConstraint) {
		int parts_len = parts.Len();
		for (int i = 0; i < parts_len; i++) {
			Object part_obj = this.parts.Get_at(i);
			if (XophpArray.is_array(part_obj)) {
				XophpArray part = (XophpArray)part_obj;
				if ((!integerConstraint || number.Floor().Eq(number))
					&& number.CompGte((GfoDecimal)part.Get_at(0)) && number.CompLte((GfoDecimal)part.Get_at(1))
				) {
					return true;
				}
			} else {
				GfoDecimal part_decimal = (GfoDecimal)part_obj;
				if (part_decimal == null) part_decimal = number; // if "new XomwRange(start, null)", then range is just "start, start"
				if (number.Eq(part_decimal)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	* Readable alias for isNumberIn(number, false), and the implementation
	* of the "within" operator.
	*
	* @param int number The number to check
	* @return boolean True if the number is inside the range; otherwise, false.
	*/
	public boolean isNumberWithin(GfoDecimal number) {
		return this.isNumberIn(number, false);
	}

	/**
	* Add another part to this range.
	*
	* @param Range|int other The part to add, either
	*   a range Object itself or a single number.
	*/
	public void add(Object otherObj) {
		if (ClassUtl.EqByObj(XomwRange.class, otherObj)) {
			this.parts = XophpArray.array_merge(this.parts, ((XomwRange)otherObj).parts);
		} else {
			this.parts.Add(otherObj);
		}
	}

	/**
	* Returns the String representation of the rule evaluator range.
	* The purpose of this method is to help debugging.
	*
	* @return String The String representation of the rule evaluator range
	*/
	@Override public String toString() {
		String s = "Range(";
		int parts_len = this.parts.Len();
		for (int i = 0; i < parts_len; i++) {
			Object part_obj = this.parts.Get_at(i);
			if (i > 0) {
				s += ", ";
			}
			if (XophpArray.is_array(part_obj)) {
				XophpArray part = (XophpArray)part_obj;
				s += IntUtl.ToStr(part.Get_at_int(0)) + ".." + IntUtl.ToStr(part.Get_at_int(1));
			} else {
				s += IntUtl.ToStr(IntUtl.Cast(part_obj));
			}
		}
		s += ")";

		return s;
	}
}
