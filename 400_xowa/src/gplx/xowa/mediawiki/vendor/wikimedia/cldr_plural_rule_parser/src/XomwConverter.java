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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.vendor.*; import gplx.xowa.mediawiki.vendor.wikimedia.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.*;
import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.Converter.*;
import gplx.langs.regxs.*;
// MW.SRC:1.33.1
/**
* Helper class for converting rules to reverse polish notation (RPN).
*/
public class XomwConverter {
	/**
	* The input String
	*
	* @var String
	*/
	public String rule;

	/**
	* The current position
	*
	* @var int
	*/
	public int pos;

	/**
	* The past-the-end position
	*
	* @var int
	*/
	public int end;

	/**
	* The operator stack
	*
	* @var array
	*/
	public XophpArray operators = XophpArray.New();

	/**
	* The operand stack
	*
	* @var array
	*/
	public XophpArray operands = XophpArray.New();

	/**
	* Precedence levels. Note that there's no need to worry about associativity
	* for the level 4 operators, since they return boolean and don't accept
	* boolean inputs.
	*/
	private static XophpArray precedence = XophpArray.New()
		.Add("or", 2)
		.Add("and", 3)
		.Add("is", 4)
		.Add("is-not", 4)
		.Add("in", 4)
		.Add("not-in", 4)
		.Add("within", 4)
		.Add("not-within", 4)
		.Add("mod", 5)
		.Add(",", 6)
		.Add("..", 7)
	;

	/**
	* A character list defining whitespace, for use in strspn() etc.
	*/
	private static final    Hash_adp WHITESPACE_CLASS = XophpString_.strspn_hash(" \t\r\n");

	/**
	* Same for digits. Note that the grammar given in UTS #35 doesn't allow
	* negative numbers or decimal separators.
	*/
	private static final    Hash_adp NUMBER_CLASS = XophpString_.strspn_hash("0123456789");

	/**
	* A character list of symbolic operands.
	*/
	private static final String OPERAND_SYMBOLS = "nivwft";

	/**
	* An anchored regular expression which matches a word at the current offset.
	*/
	private static final    Regx_adp WORD_REGEX = Regx_adp_.new_("[a-zA-Z@]+");

	/**
	* Convert a rule to RPN. This is the only public entry point.
	*
	* @param String rule The rule to convert
	* @return String The RPN representation of the rule
	*/
	public static String convert(String rule) {
		XomwConverter parser = new XomwConverter(rule);

		return parser.doConvert();
	}

	/**
	* Private constructor.
	* @param String rule
	*/
	protected XomwConverter(String rule) {
		this.rule = rule;
		this.pos = 0;
		this.end = XophpString_.strlen(rule);
	}

	/**
	* Do the operation.
	*
	* @return String The RPN representation of the rule (e.g. "5 3 mod n is")
	*/
	protected String doConvert() {
		boolean expectOperator = true;

		// Iterate through all tokens, saving the operators and operands to a
		// stack per Dijkstra's shunting yard algorithm.
		/** @var Operator token */
		XomwFragment token;
		while (null != (token = this.nextToken())) {
			// In this grammar, there are only binary operators, so every valid
			// rule String will alternate between operator and operand tokens.
			expectOperator = !expectOperator;

			if (Type_.Is_assignable_from_by_obj(token, XomwExpression.class)) {
				// Operand
				if (expectOperator) {
					token.error("unexpected operand");
				}
				this.operands.Add(token);
				continue;
			} else {
				// Operator
				if (!expectOperator) {
					token.error("unexpected operator");
				}
				// Resolve higher precedence levels
				XomwOperator lastOp = (XomwOperator)this.operators.end();
				while (lastOp != null && Int_.Cast(XomwConverter.precedence.Get_by(((XomwOperator)token).name)) <= Int_.Cast(XomwConverter.precedence.Get_by(((XomwOperator)lastOp).name))) {
					this.doOperation(lastOp, this.operands);
					this.operators.pop();
					lastOp = (XomwOperator)this.operators.end();
				}
				this.operators.Add(token);
			}
		}

		// Finish off the stack
		XomwOperator op = null;
		while (null != (op = (XomwOperator)this.operators.pop())) {
			this.doOperation(op, this.operands);
		}

		// Make sure the result is sane. The first case is possible for an empty
		// String input, the second should be unreachable.
		if (!this.operands.Count_bool()) {
			this.error("condition expected");
		} else if (this.operands.Count() > 1) {
			this.error("missing operator or too many operands");
		}

		XomwExpression value = (XomwExpression)this.operands.Get_at(0);
		if (!String_.Eq(value.type, "boolean")) {
			this.error("the result must have a boolean type");
		}

		return ((XomwExpression)this.operands.Get_at(0)).rpn;
	}

	/**
	* Fetch the next token from the input String.
	*
	* @return Fragment The next token
	*/
	protected XomwFragment nextToken() {
		if (this.pos >= this.end) {
			return null;
		}

		// Whitespace
		int length = XophpString_.strspn(this.rule, XomwConverter.WHITESPACE_CLASS, this.pos);
		this.pos += length;

		if (this.pos >= this.end) {
			return null;
		}

		// Number
		length = XophpString_.strspn(this.rule, XomwConverter.NUMBER_CLASS, this.pos);
		if (length != 0) {
			XomwFragment token = this.newNumber(XophpString_.substr(this.rule, this.pos, length), this.pos);
			this.pos += length;

			return token;
		}

		// Two-character operators
		String op2 = XophpString_.substr(this.rule, this.pos, 2);
		if (String_.Eq(op2, "..") || String_.Eq(op2, "!=")) {
			XomwFragment token = this.newOperator(op2, this.pos, 2);
			this.pos += 2;

			return token;
		}

		// Single-character operators
		String op1 = Char_.To_str(String_.CharAt(this.rule, this.pos));
		if (String_.Eq(op1, ",") || String_.Eq(op1, "=") || String_.Eq(op1, "%")) {
			XomwFragment token = this.newOperator(op1, this.pos, 1);
			this.pos++;

			return token;
		}

		// Word
		XophpArray m = XophpArray.New();
		if (!XophpRegex_.preg_match_bool(XomwConverter.WORD_REGEX, this.rule, m, 0, this.pos)) {
			this.error("unexpected character \"" + String_.CharAt(this.rule, this.pos) + "\"");
		}
		String word1 = XophpString_.strtolower(m.Get_at_str(0));
		String word2 = "";
		int nextTokenPos = this.pos + XophpString_.strlen(word1);
		if (String_.Eq(word1, "not") || String_.Eq(word1, "is")) {
			// Look ahead one word
			nextTokenPos += XophpString_.strspn(this.rule, XomwConverter.WHITESPACE_CLASS, nextTokenPos);
			m = XophpArray.New();
			if (nextTokenPos < this.end
				&& XophpRegex_.preg_match_bool(XomwConverter.WORD_REGEX, this.rule, m, 0, nextTokenPos)
			) {
				word2 = XophpString_.strtolower(m.Get_at_str(0));
				nextTokenPos += XophpString_.strlen(word2);
			}
		}

		// Two-word operators like "is not" take precedence over single-word operators like "is"
		if (String_.Eq(word2, "")) {
			String bothWords = word1 + "-" + word2;
			if (XomwConverter.precedence.isset(bothWords)) {
				XomwFragment token = this.newOperator(bothWords, this.pos, nextTokenPos - this.pos);
				this.pos = nextTokenPos;

				return token;
			}
		}

		// Single-word operators
		if (XomwConverter.precedence.isset(word1)) {
			XomwFragment token = this.newOperator(word1, this.pos, XophpString_.strlen(word1));
			this.pos += XophpString_.strlen(word1);

			return token;
		}

		// The single-character operand symbols
		if (XophpString_.strpos(XomwConverter.OPERAND_SYMBOLS, word1) != String_.Pos_neg1) {
			XomwFragment token = this.newNumber(word1, this.pos);
			this.pos++;

			return token;
		}

		// Samples
		if (String_.Eq(word1, "@integer") || String_.Eq(word1, "@decimal")) {
			// Samples are like comments, they have no effect on rule evaluation.
			// They run from the first sample indicator to the end of the String.
			this.pos = this.end;

			return null;
		}

		this.error("unrecognised word");
		return null;
	}

	/**
	* For the binary operator op, pop its operands off the stack and push
	* a fragment with rpn and type members describing the result of that
	* operation.
	*
	* @param Operator op
	*/
	protected void doOperation(XomwOperator op, Object ignore) { // NOTE: MW passes 2 args, but method only has 1
		if (this.operands.Count() < 2) {
			op.error("missing operand");
		}
		XomwExpression right = (XomwExpression)this.operands.pop();
		XomwExpression left = (XomwExpression)this.operands.pop();
		XomwExpression result = op.operate(left, right);
		this.operands.Add(result);
	}

	/**
	* Create a numerical expression Object
	*
	* @param String text
	* @param int pos
	* @return Expression The numerical expression
	*/
	protected XomwExpression newNumber(String text, int pos) {
		return new XomwExpression(this, "number", text, pos, XophpString_.strlen(text));
	}

	/**
	* Create a binary operator
	*
	* @param String type
	* @param int pos
	* @param int length
	* @return Operator The operator
	*/
	protected XomwOperator newOperator(String type, int pos, int length) {
		return new XomwOperator(this, type, pos, length);
	}

	/**
	* Throw an error
	* @param String message
	*/
	private void error(String message) {
		throw new XomwError(message);
	}
}