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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
interface Expr_tkn {
	int Tid();
	byte[] Val_ary();
	String Val_str();
}
class Expr_tkn_ {
	public static final int Tid_operator = 1, Tid_paren_lhs = 5, Tid_paren_rhs = 6, Tid_space = 7, Tid_number = 8;
}
interface Func_tkn extends Expr_tkn {
	boolean Func_is_const();
	int ArgCount();
	int Precedence();
	Func_tkn GetAlt();
	boolean Calc(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack);
}
class Ws_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_space;}
	public byte[] Val_ary() {return val_ary;} private byte[] val_ary;
	public String Val_str() {return val_str;} private String val_str;
	public Ws_tkn(byte b) {this.val_ary = new byte[] {b}; this.val_str = Char_.XtoStr(Char_.XbyInt(b));}
}
class Paren_bgn_tkn implements Expr_tkn, Func_tkn {
	public int Tid() {return Expr_tkn_.Tid_paren_lhs;}
	public boolean Func_is_const() {return false;}
	public byte[] Val_ary() {return val_ary;} private byte[] val_ary = Bry_.new_u8(val_str);
	public String Val_str() {return val_str;} static final String val_str = "(";
	public int ArgCount() {return 0;}
	public int Precedence() {return -1;}
	public Func_tkn GetAlt() {return this;}
	public boolean Calc(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {throw Exc_.new_unimplemented();}
	public static Paren_bgn_tkn _ = new Paren_bgn_tkn(); Paren_bgn_tkn() {}
}
class Paren_end_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_paren_rhs;}
	public byte[] Val_ary() {return val_ary;} private byte[] val_ary = Bry_.new_u8(val_str);
	public String Val_str() {return val_str;} static final String val_str = ")";
	public static Paren_end_tkn _ = new Paren_end_tkn(); Paren_end_tkn() {}
}
class Num_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_number;}		
	public byte[] Val_ary()	{return val_ary;} private byte[] val_ary;
	public String Val_str()	{return String_.new_u8(val_ary);}
	public Num_tkn(int val_int) {
		this.val_int = val_int;
		this.val_ary = new byte[] {Byte_.By_int(val_int + Byte_ascii.Num_0)};
	}	int val_int;
}
class Dot_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_number;}		
	public byte[] Val_ary()	{return Val_Ary;} static final byte[] Val_Ary = new byte[] {Byte_ascii.Dot};
	public String Val_str()	{return String_.new_u8(Val_Ary);}
	public Dot_tkn() {}
}
class Val_stack {
	public void Clear() {stack_len = 0;}
	public int Len() {return stack_len;}
	public DecimalAdp Pop() {
		int stack_len_new = stack_len - 1;
		DecimalAdp rv = stack[stack_len_new];
		stack_len = stack_len_new;
		return rv;
	}
	public void Push(DecimalAdp v) {
		int stack_len_new = stack_len + 1;
		if (stack_len_new > stack_max) {
			stack_max = stack_len_new * 2;
			stack = (DecimalAdp[])Array_.Resize(stack, stack_max);
		}
		stack[stack_len] = v;
		stack_len = stack_len_new;
	}
	DecimalAdp[] stack = new DecimalAdp[0]; int stack_len = 0, stack_max = 0;
}
class Func_tkn_stack {
	public void Clear() {stack_len = 0;}
	public int Len() {return stack_len;}
	public Func_tkn GetLast() {return stack_len == 0 ? null : stack[stack_len - 1];}
	public Func_tkn Pop() {
		int stack_len_new = stack_len - 1;
		Func_tkn rv = stack[stack_len_new];
		stack_len = stack_len_new;
		return rv;
	}
	public void Push(Func_tkn v) {
		int stack_len_new = stack_len + 1;
		if (stack_len_new > stack_max) {
			stack_max = stack_len_new * 2;
			stack = (Func_tkn[])Array_.Resize(stack, stack_max);
		}
		stack[stack_len] = v;
		stack_len = stack_len_new;
	}
	Func_tkn[] stack = new Func_tkn[0]; int stack_len = 0, stack_max = 0;
}
abstract class Func_tkn_base implements Func_tkn {
	public int Tid() {return Expr_tkn_.Tid_operator;}
	public abstract int Precedence();
	public abstract int ArgCount();
	@gplx.Virtual public boolean Func_is_const() {return false;}
	public void Ctor(String v) {val_ary = Bry_.new_u8(v);}
	public byte[] Val_ary()	{return val_ary;} private byte[] val_ary;
	public String Val_str()	{return String_.new_u8(Val_ary());}
	@gplx.Virtual public Func_tkn GetAlt() {return this;}
	public boolean Calc(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		if (val_stack.Len() < this.ArgCount()) {shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_missing_operand, Val_ary()); return false;}
		return Calc_hook(ctx, shunter, val_stack);
	}
	public abstract boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack);
}
class Func_tkn_plus extends Func_tkn_base {
	public Func_tkn_plus(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 6;}
	@Override public Func_tkn GetAlt() {return Func_tkn_plus_positive._;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Op_add(rhs));
		return true;
	}
	public static final Func_tkn_plus _ = new Func_tkn_plus(); Func_tkn_plus() {}
}
class Func_tkn_plus_positive extends Func_tkn_base {
	Func_tkn_plus_positive(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 10;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {return true;}// effectively a noop
	public static final Func_tkn_plus_positive _ = new Func_tkn_plus_positive("+");
}
class Func_tkn_minus extends Func_tkn_base {
	public Func_tkn_minus(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 6;}
	@Override public Func_tkn GetAlt() {return Func_tkn_minus_negative._;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Op_subtract(rhs));
		return true;
	}
}
class Func_tkn_minus_negative extends Func_tkn_base {
	public Func_tkn_minus_negative(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 10;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(val.Op_mult(DecimalAdp_.Neg1));
		return true;
	}
	public static final Func_tkn_minus_negative _ = new Func_tkn_minus_negative("-");
}
class Func_tkn_divide extends Func_tkn_base {
	public Func_tkn_divide(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 7;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		if (rhs.Eq(0)) {
			shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_division_by_zero);
			return false;
		}
		val_stack.Push(lhs.Op_divide(rhs));
		return true;
	}
}
class Func_tkn_times extends Func_tkn_base {
	public Func_tkn_times(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 7;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Op_mult(rhs));
		return true;
	}
}
class Func_tkn_pow extends Func_tkn_base {
	public Func_tkn_pow(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 8;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		int rhs_int = rhs.Xto_int();	
		if ((double)rhs_int == rhs.Xto_double())	// exponent is integer; use decimal pow which does less casts to double
			val_stack.Push(lhs.Op_pow(rhs_int));
		else {
			double rslt = Math_.Pow(lhs.Xto_double(), rhs.Xto_double());
			if (Double_.IsNaN(rslt)) {
				shunter.Rslt_set(Double_.NaN_bry);
				return false;
			}
			else
				val_stack.Push(DecimalAdp_.double_thru_str_(rslt));
		}
		return true;
	}
}
class Func_tkn_e_op extends Func_tkn_base {
	public Func_tkn_e_op(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 9;}	// NOTE: needs to be < than - sign
	@Override public Func_tkn GetAlt() {return Func_tkn_e_const._;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		int rhs_int = rhs.Xto_int();
		if (	rhs_int > 308
			||	(lhs.Xto_double() >= 1.8f && rhs_int == 308)) {	// PHP:"maximum of ~1.8e308"; verified with {{#expr:1.8e308}} on sandbox; REF:http://php.net/manual/en/language.types.float.php; PAGE:en.w:Factorial; en.w:Astatine; DATE:2015-04-08; DATE:2015-04-21
			shunter.Rslt_set(Double_.Inf_pos_bry);
			return false;
		}
		double rhs_double = rhs.Xto_double();
		if ((double)rhs_int == rhs_double)	// exponent is integer; use pow_10 which does less casts to double
			val_stack.Push(lhs.Op_mult(DecimalAdp_.pow_10_(rhs_int)));
		else
			val_stack.Push(lhs.Op_mult(DecimalAdp_.double_thru_str_(Math_.Pow(10d, rhs_double))));
		return true;
	}
}
class Func_tkn_mod extends Func_tkn_base {
	public Func_tkn_mod(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 7;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		// must convert to int else issues with {{#expr:0.00999999mod10}} and {{USCensusPop|1960=763956|1970=756510}}; REF: http://php.net/manual/en/language.operators.arithmetic.php: "Operands of modulus are converted to integers (by stripping the decimal part) before processing"
		// must convert to long else issues with (39052000900/1) mod 100 which should be 0, not 47; JAVA does not fail int conversion, and instead converts to Int_.MaxValue; EX: de.w:Quijano_(Pi�lagos)
		long rhs = ((DecimalAdp)val_stack.Pop()).Xto_long();
		long lhs = ((DecimalAdp)val_stack.Pop()).Xto_long();
		if (rhs == 0) {
			shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_division_by_zero);
			return false;
		}
		val_stack.Push(DecimalAdp_.long_(lhs % rhs));
		return true;
	}
}
class Func_tkn_eq extends Func_tkn_base {
	public Func_tkn_eq(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Eq(rhs) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_neq extends Func_tkn_base {
	public Func_tkn_neq(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Eq(rhs) ? DecimalAdp_.Zero : DecimalAdp_.One);
		return true;
	}
}
class Func_tkn_gt extends Func_tkn_base {
	public Func_tkn_gt(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Comp_gt(rhs) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_lt extends Func_tkn_base {
	public Func_tkn_lt(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Comp_lt(rhs) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_gte extends Func_tkn_base {
	public Func_tkn_gte(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Comp_gte(rhs) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_lte extends Func_tkn_base {
	public Func_tkn_lte(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 4;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(lhs.Comp_lte(rhs) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_and extends Func_tkn_base {
	public Func_tkn_and(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 3;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(!lhs.Eq(0) && !rhs.Eq(0) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_or extends Func_tkn_base {
	public Func_tkn_or(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 2;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		val_stack.Push(!lhs.Eq(0) || !rhs.Eq(0) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_not extends Func_tkn_base {
	public Func_tkn_not(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(val.Eq(0) ? DecimalAdp_.One : DecimalAdp_.Zero);
		return true;
	}
}
class Func_tkn_e_const extends Func_tkn_base {
	public Func_tkn_e_const(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 0;}
	@Override public int Precedence()	{return 0;}
	@Override public boolean Func_is_const() {return true;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		val_stack.Push(DecimalAdp_.Const_e);
		return true;
	}
	public static final Func_tkn_e_const _ = new Func_tkn_e_const("e");
}
class Func_tkn_pi extends Func_tkn_base {
	public Func_tkn_pi(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 0;}
	@Override public int Precedence()	{return 0;}
	@Override public boolean Func_is_const() {return true;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		val_stack.Push(DecimalAdp_.Const_pi);
		return true;
	}
}
class Func_tkn_ceil extends Func_tkn_base {
	public Func_tkn_ceil(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Ceil(val.Xto_double())));
		return true;
	}
}
class Func_tkn_trunc extends Func_tkn_base {
	public Func_tkn_trunc(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Trunc(val.Xto_double())));
		return true;
	}
}
class Func_tkn_floor extends Func_tkn_base {
	public Func_tkn_floor(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Floor(val.Xto_double())));
		return true;
	}
}
class Func_tkn_abs extends Func_tkn_base {
	public Func_tkn_abs(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(val.Op_abs());
		return true;
	}
}
class Func_tkn_exp extends Func_tkn_base {
	public Func_tkn_exp(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Exp(val.Xto_double())));
		return true;
	}
}
class Func_tkn_ln extends Func_tkn_base {
	public Func_tkn_ln(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		if (val.Comp_lte(0)) {shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_invalid_argument_ln); return false;}		
		val_stack.Push(DecimalAdp_.double_(Math_.Log(val.Xto_double())));
		return true;
	}
}
class Func_tkn_sin extends Func_tkn_base {
	public Func_tkn_sin(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Sin(val.Xto_double())));
		return true;
	}
}
class Func_tkn_cos extends Func_tkn_base {
	public Func_tkn_cos(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Cos(val.Xto_double())));
		return true;
	}
}
class Func_tkn_tan extends Func_tkn_base {
	public Func_tkn_tan(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Tan(val.Xto_double())));
		return true;
	}
}
class Func_tkn_asin extends Func_tkn_base {
	public Func_tkn_asin(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		if (val.Comp_lt(-1) || val.Comp_gt(1)) {shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_invalid_argument, this.Val_ary()); return false;}
		val_stack.Push(DecimalAdp_.double_(Math_.Asin(val.Xto_double())));
		return true;
	}
}
class Func_tkn_acos extends Func_tkn_base {
	public Func_tkn_acos(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		if (val.Comp_lt(-1) || val.Comp_gt(1)) {shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_invalid_argument, this.Val_ary()); return false;}
		val_stack.Push(DecimalAdp_.double_(Math_.Acos(val.Xto_double())));
		return true;
	}
}
class Func_tkn_atan extends Func_tkn_base {
	public Func_tkn_atan(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(DecimalAdp_.double_(Math_.Atan(val.Xto_double())));
		return true;
	}
}
class Func_tkn_round extends Func_tkn_base {
	public Func_tkn_round(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 5;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = val_stack.Pop();
		DecimalAdp lhs = val_stack.Pop();
		if (rhs.Comp_gt(16)) {
			rhs = DecimalAdp_.int_(16);
		}
		else if (rhs.Comp_lt(-16)) {
			rhs = DecimalAdp_.int_(-16);
		}
		DecimalAdp val = lhs.Op_round(rhs.Xto_int());
		if (val.Xto_double() == 0)	// NOTE: must explicitly check for zero, else "0.0" will be pushed onto stack; EXE: {{#expr: 0 round 1}}; DATE:2013-11-09
			val_stack.Push(DecimalAdp_.Zero);
		else
			val_stack.Push(val);
		return true;
	}
}
class Func_tkn_fmod extends Func_tkn_base {
	public Func_tkn_fmod(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 7;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp rhs = (DecimalAdp)val_stack.Pop();
		DecimalAdp lhs = (DecimalAdp)val_stack.Pop();
		if (rhs.Xto_double() == 0) {
			shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_division_by_zero);
			return false;
		}
		val_stack.Push(lhs.Op_mod(rhs));
		return true;
	}
}
class Func_tkn_sqrt extends Func_tkn_base {
	public Func_tkn_sqrt(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		DecimalAdp val = val_stack.Pop();
		val_stack.Push(val.Op_sqrt());
		return true;
	}
}
