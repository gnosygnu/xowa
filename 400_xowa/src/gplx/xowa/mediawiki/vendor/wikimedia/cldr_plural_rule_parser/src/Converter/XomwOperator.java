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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.Converter; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.vendor.*; import gplx.xowa.mediawiki.vendor.wikimedia.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.*;
// MW.SRC:1.33.1
/**
* Helper for Converter.
* An operator Object, representing a region of the input String (for error
* messages), and the binary operator at that location.
*/
public class XomwOperator extends XomwFragment { 	/** @var String The name */
	public String name;

	/**
	* Each op type has three characters: left operand type, right operand type and result type
	*
	*   b = boolean
	*   n = number
	*   r = range
	*
	* A number is a kind of range.
	*
	* @var array
	*/
	private static XophpArray opTypes = XophpArray.New()
		.Add("or", "bbb")
		.Add("and", "bbb")
		.Add("is", "nnb")
		.Add("is-not", "nnb")
		.Add("in", "nrb")
		.Add("not-in", "nrb")
		.Add("within", "nrb")
		.Add("not-within", "nrb")
		.Add("mod", "nnn")
		.Add(",", "rrr")
		.Add("..", "nnr")
	;

	/**
	* Map converting from the abbrevation to the full form.
	*
	* @var array
	*/
	private static XophpArray typeSpecMap = XophpArray.New()
		.Add("b", "boolean")
		.Add("n", "number")
		.Add("r", "range")
	;

	/**
	* Map for converting the new operators introduced in Rev 33 to the old forms
	*/
	private static XophpArray aliasMap = XophpArray.New()
		.Add("%", "mod")
		.Add("!=", "not-in")
		.Add("=", "in")
	;

	/**
	* Initialize a new instance of a CLDRPluralRuleConverterOperator Object
	*
	* @param Converter parser The parser
	* @param String name The operator name
	* @param int pos The length
	* @param int length
	*/
	public XomwOperator(XomwConverter parser, String name, int pos, int length) {super(parser, pos, length);
		if (XomwOperator.aliasMap.isset(name)) {
			name = XomwOperator.aliasMap.Get_by_str(name);
		}
		this.name = name;
	}

	/**
	* Compute the operation
	*
	* @param Expression left The left part of the expression
	* @param Expression right The right part of the expression
	* @return Expression The result of the operation
	*/
	public XomwExpression operate(XomwExpression left, XomwExpression right) {
		String typeSpec = XomwOperator.opTypes.Get_by_str(this.name);

		String leftType = XomwOperator.typeSpecMap.Get_by_str(String_.CharAt(typeSpec, 0));
		String rightType = XomwOperator.typeSpecMap.Get_by_str(String_.CharAt(typeSpec, 1));
		String resultType = XomwOperator.typeSpecMap.Get_by_str(String_.CharAt(typeSpec, 2));

		int start = XophpMath_.min_many(this.pos, left.pos, right.pos);
		int end = XophpMath_.max_many(this.end, left.end, right.end);
		int length = end - start;

		XomwExpression newExpr = new XomwExpression(this.parser, resultType,
			left.rpn + " " + right.rpn + " " + this.name,
			start, length);

		if (!left.isType(leftType)) {
			newExpr.error("invalid type for left operand: expected leftType, got {left.type}");
		}

		if (!right.isType(rightType)) {
			newExpr.error("invalid type for right operand: expected rightType, got {right.type}");
		}

		return newExpr;
	}
}