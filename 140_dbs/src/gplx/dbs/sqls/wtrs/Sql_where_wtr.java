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
import gplx.core.criterias.*; import gplx.dbs.sqls.itms.*; 
public class Sql_where_wtr {
	private final Sql_core_wtr qry_wtr;
	private final Sql_val_wtr val_wtr;
	public Sql_where_wtr(Sql_core_wtr qry_wtr, Sql_val_wtr val_wtr) {this.qry_wtr = qry_wtr; this.val_wtr = val_wtr;}
	public void Bld_where(Bry_bfr bfr, Sql_wtr_ctx ctx, Sql_where_clause where_itm) {
		if (where_itm == Sql_where_clause.Where__null) return;
		Bld_where(bfr, ctx, where_itm.Root);
	}
	public void Bld_where(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria crt) {
		if (crt == null) return;
		if (crt.Tid() == Criteria_.Tid_wrapper) {
			Criteria_fld crt_fld = (Criteria_fld)crt;
			Criteria crt_inner = crt_fld.Crt();
			switch (crt_inner.Tid()) {
				case Criteria_.Tid_const:
				case Criteria_.Tid_not:
				case Criteria_.Tid_and:
				case Criteria_.Tid_or:		crt = crt_inner; break;
				default:					break;
			}
		}
		if (crt.Tid() == Criteria_.Tid_const) return;
		bfr.Add_str_a7(" WHERE ");
		Bld_where_elem(bfr, ctx, crt);
	}
	public void Bld_where_elem(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria crt) {
		if (crt == null) return;	// handle empty crt; EX: SELECT * FROM tbl;
		Criteria_bool_base crt_bool = Criteria_bool_base.as_(crt);
		if (crt_bool != null) {
			bfr.Add_str_a7("(");
			Bld_where_elem(bfr, ctx, crt_bool.Lhs());
			bfr.Add_str_u8_many(" ", crt_bool.Op_literal(), " ");
			Bld_where_elem(bfr, ctx, crt_bool.Rhs());
			bfr.Add_str_a7(")");
			return;
		}
		if (crt.Tid() == Criteria_.Tid_db_obj_ary) {
			Bld_where__db_obj(bfr, ctx, (Db_obj_ary_crt)crt);
		}
		else {
			Criteria_fld leaf = Criteria_fld.as_(crt); if (leaf == null) throw Err_.new_invalid_op(crt.To_str());
			String leaf_pre = leaf.Pre(); if (leaf_pre != Criteria_fld.Pre_null) bfr.Add_str_u8(leaf_pre).Add_byte_dot();
			qry_wtr.Bld_col_name(bfr, leaf.Key());
			Criteria leaf_crt = leaf.Crt();
			switch (leaf_crt.Tid()) {
				case Criteria_.Tid_eq:				Bld_where__eq		(bfr, ctx, (Criteria_eq)leaf_crt); break;
				case Criteria_.Tid_comp:			Bld_where__comp		(bfr, ctx, (Criteria_comp)leaf_crt); break;
				case Criteria_.Tid_between:			Bld_where__between	(bfr, ctx, (Criteria_between)leaf_crt); break;
				case Criteria_.Tid_in:				Bld_where__in		(bfr, ctx, (Criteria_in)leaf_crt); break;
				case Criteria_.Tid_like:			Bld_where__like		(bfr, ctx, (Criteria_like)leaf_crt); break;
				case Criteria_.Tid_iomatch:			Bld_where__iomatch	(bfr, ctx, (Criteria_ioMatch)leaf_crt); break;
				default:							throw Err_.new_unhandled(leaf_crt);
			}
		}
	}
	private void Bld_where__eq(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_eq crt) {
		bfr.Add_str_a7(crt.Neg() ? " != " : " = ");
		val_wtr.Bld_val(bfr, ctx, crt.Val());
	}
	private void Bld_where__comp(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_comp crt) {
		int comp_tid = crt.Comp_mode();
		bfr.Add_byte_space();
		bfr.Add_byte(comp_tid < CompareAble_.Same ? Byte_ascii.Angle_bgn : Byte_ascii.Angle_end);
		if (comp_tid % 2 == CompareAble_.Same) bfr.Add_byte_eq();
		bfr.Add_byte_space();
		val_wtr.Bld_val(bfr, ctx, crt.Val());
	}
	private void Bld_where__between(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_between crt) {
		bfr.Add_str_a7(crt.Neg() ? " NOT BETWEEN " : " BETWEEN ");
		val_wtr.Bld_val(bfr, ctx, crt.Lo());
		bfr.Add_str_a7(" AND ");
		val_wtr.Bld_val(bfr, ctx, crt.Hi());
	}
	private void Bld_where__like(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_like crt) {
		bfr.Add_str_a7(crt.Neg() ? " NOT LIKE " : " LIKE ");
		val_wtr.Bld_val(bfr, ctx, crt.Pattern().Raw());
		bfr.Add_str_u8(String_.Format(" ESCAPE '{0}'", crt.Pattern().Escape()));
	}
	private void Bld_where__in(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_in crt) {
		bfr.Add_str_a7(crt.Neg() ? " NOT IN " : " IN ");
		Object[] ary = crt.Ary();
		int len = crt.Ary_len();
		for (int i = 0; i < len; ++i) {
			if (i == 0)
				bfr.Add_byte(Byte_ascii.Paren_bgn);
			else
				bfr.Add_byte(Byte_ascii.Comma).Add_byte_space();
			val_wtr.Bld_val(bfr, ctx, ary[i]);
		}
		bfr.Add_byte(Byte_ascii.Paren_end);
	}
	private void Bld_where__iomatch(Bry_bfr bfr, Sql_wtr_ctx ctx, Criteria_ioMatch crt) {
		bfr.Add_str_a7(crt.Neg() ? " NOT IOMATCH " : " IOMATCH ");
		val_wtr.Bld_val(bfr, ctx, crt.Pattern().Raw());
	}
	public void Bld_where__db_obj(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_obj_ary_crt crt) {
		Object[][] ary = crt.Vals();
		int ary_len = ary.length; 
		Db_obj_ary_fld[] flds = crt.Flds();
		for (int i = 0; i < ary_len; i++) {
			Object[] itm = (Object[])ary[i];
			int itm_len = itm.length;
			if (i != 0) bfr.Add_str_a7(" OR ");
			bfr.Add_str_a7("(");
			for (int j = 0; j < itm_len; j++) {
				if (j != 0) bfr.Add_str_a7(" AND ");
				Db_obj_ary_fld fld = flds[j];
				Object val = itm[j];
				boolean quote = false;
				switch (fld.Type_tid()) {
					case Type_adp_.Tid__str:
					case Type_adp_.Tid__char:
					case Type_adp_.Tid__date:
						quote = true;
						break;
				}
				bfr.Add_str_a7(fld.Name());
				bfr.Add_str_a7("=");
				if (quote) bfr.Add_str_a7("'");
				bfr.Add_str_a7(Object_.Xto_str_strict_or_empty(val));
				if (quote) bfr.Add_str_a7("'");
			}
			bfr.Add_str_a7(")");
		}		
	}
}
