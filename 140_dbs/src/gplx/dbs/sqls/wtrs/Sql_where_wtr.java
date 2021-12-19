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
import gplx.core.criterias.Criteria;
import gplx.core.criterias.Criteria_;
import gplx.core.criterias.Criteria_between;
import gplx.core.criterias.Criteria_bool_base;
import gplx.core.criterias.Criteria_comp;
import gplx.core.criterias.Criteria_eq;
import gplx.core.criterias.Criteria_fld;
import gplx.core.criterias.Criteria_in;
import gplx.core.criterias.Criteria_ioMatch;
import gplx.core.criterias.Criteria_like;
import gplx.dbs.sqls.itms.Db_obj_ary_crt;
import gplx.dbs.sqls.itms.Db_obj_ary_fld;
import gplx.dbs.sqls.itms.Sql_where_clause;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.errs.ErrUtl;
public class Sql_where_wtr {
	private final Sql_core_wtr qry_wtr;
	private final Sql_val_wtr val_wtr;
	public Sql_where_wtr(Sql_core_wtr qry_wtr, Sql_val_wtr val_wtr) {this.qry_wtr = qry_wtr; this.val_wtr = val_wtr;}
	public void Bld_where(BryWtr bfr, Sql_wtr_ctx ctx, Sql_where_clause where_itm) {
		if (where_itm == Sql_where_clause.Where__null) return;
		Bld_where(bfr, ctx, where_itm.Root);
	}
	public void Bld_where(BryWtr bfr, Sql_wtr_ctx ctx, Criteria crt) {
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
		bfr.AddStrA7(" WHERE ");
		Bld_where_elem(bfr, ctx, crt);
	}
	public void Bld_where_elem(BryWtr bfr, Sql_wtr_ctx ctx, Criteria crt) {
		if (crt == null) return;	// handle empty crt; EX: SELECT * FROM tbl;
		Criteria_bool_base crt_bool = Criteria_bool_base.as_(crt);
		if (crt_bool != null) {
			bfr.AddStrA7("(");
			Bld_where_elem(bfr, ctx, crt_bool.Lhs());
			bfr.AddStrU8Many(" ", crt_bool.Op_literal(), " ");
			Bld_where_elem(bfr, ctx, crt_bool.Rhs());
			bfr.AddStrA7(")");
			return;
		}
		if (crt.Tid() == Criteria_.Tid_db_obj_ary) {
			Bld_where__db_obj(bfr, ctx, (Db_obj_ary_crt)crt);
		}
		else {
			Criteria_fld leaf = Criteria_fld.as_(crt); if (leaf == null) throw ErrUtl.NewInvalidOp(crt.ToStr());
			String leaf_pre = leaf.Pre(); if (leaf_pre != Criteria_fld.Pre_null) bfr.AddStrU8(leaf_pre).AddByteDot();
			qry_wtr.Bld_col_name(bfr, leaf.Key());
			Criteria leaf_crt = leaf.Crt();
			switch (leaf_crt.Tid()) {
				case Criteria_.Tid_eq:				Bld_where__eq		(bfr, ctx, (Criteria_eq)leaf_crt); break;
				case Criteria_.Tid_comp:			Bld_where__comp		(bfr, ctx, (Criteria_comp)leaf_crt); break;
				case Criteria_.Tid_between:			Bld_where__between	(bfr, ctx, (Criteria_between)leaf_crt); break;
				case Criteria_.Tid_in:				Bld_where__in		(bfr, ctx, (Criteria_in)leaf_crt); break;
				case Criteria_.Tid_like:			Bld_where__like		(bfr, ctx, (Criteria_like)leaf_crt); break;
				case Criteria_.Tid_iomatch:			Bld_where__iomatch	(bfr, ctx, (Criteria_ioMatch)leaf_crt); break;
				default:							throw ErrUtl.NewUnhandled(leaf_crt);
			}
		}
	}
	private void Bld_where__eq(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_eq crt) {
		bfr.AddStrA7(crt.Neg() ? " != " : " = ");
		val_wtr.Bld_val(bfr, ctx, crt.Val());
	}
	private void Bld_where__comp(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_comp crt) {
		int comp_tid = crt.Comp_mode();
		bfr.AddByteSpace();
		bfr.AddByte(comp_tid < CompareAbleUtl.Same ? AsciiByte.AngleBgn : AsciiByte.AngleEnd);
		if (comp_tid % 2 == CompareAbleUtl.Same) bfr.AddByteEq();
		bfr.AddByteSpace();
		val_wtr.Bld_val(bfr, ctx, crt.Val());
	}
	private void Bld_where__between(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_between crt) {
		bfr.AddStrA7(crt.Neg() ? " NOT BETWEEN " : " BETWEEN ");
		val_wtr.Bld_val(bfr, ctx, crt.Lo());
		bfr.AddStrA7(" AND ");
		val_wtr.Bld_val(bfr, ctx, crt.Hi());
	}
	private void Bld_where__like(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_like crt) {
		bfr.AddStrA7(crt.Neg() ? " NOT LIKE " : " LIKE ");
		val_wtr.Bld_val(bfr, ctx, crt.Pattern().Raw());
		bfr.AddStrU8(StringUtl.Format(" ESCAPE '{0}'", crt.Pattern().Escape()));
	}
	private void Bld_where__in(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_in crt) {
		bfr.AddStrA7(crt.Neg() ? " NOT IN " : " IN ");
		Object[] ary = crt.Ary();
		int len = crt.Ary_len();
		for (int i = 0; i < len; ++i) {
			if (i == 0)
				bfr.AddByte(AsciiByte.ParenBgn);
			else
				bfr.AddByte(AsciiByte.Comma).AddByteSpace();
			val_wtr.Bld_val(bfr, ctx, ary[i]);
		}
		bfr.AddByte(AsciiByte.ParenEnd);
	}
	private void Bld_where__iomatch(BryWtr bfr, Sql_wtr_ctx ctx, Criteria_ioMatch crt) {
		bfr.AddStrA7(crt.Neg() ? " NOT IOMATCH " : " IOMATCH ");
		val_wtr.Bld_val(bfr, ctx, crt.Pattern().Raw());
	}
	public void Bld_where__db_obj(BryWtr bfr, Sql_wtr_ctx ctx, Db_obj_ary_crt crt) {
		Object[][] ary = crt.Vals();
		int ary_len = ary.length; 
		Db_obj_ary_fld[] flds = crt.Flds();
		for (int i = 0; i < ary_len; i++) {
			Object[] itm = (Object[])ary[i];
			int itm_len = itm.length;
			if (i != 0) bfr.AddStrA7(" OR ");
			bfr.AddStrA7("(");
			for (int j = 0; j < itm_len; j++) {
				if (j != 0) bfr.AddStrA7(" AND ");
				Db_obj_ary_fld fld = flds[j];
				Object val = itm[j];
				boolean quote = false;
				switch (fld.Type_tid()) {
					case TypeIds.IdStr:
					case TypeIds.IdChar:
					case TypeIds.IdDate:
						quote = true;
						break;
				}
				bfr.AddStrA7(fld.Name());
				bfr.AddStrA7("=");
				if (quote) bfr.AddStrA7("'");
				bfr.AddStrA7(ObjectUtl.ToStrOrEmpty(val));
				if (quote) bfr.AddStrA7("'");
			}
			bfr.AddStrA7(")");
		}		
	}
}
