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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
import gplx.core.strings.*; import gplx.core.criterias.*;
import gplx.dbs.qrys.*;
public class Sql_qry_wtr_ansi implements Sql_qry_wtr {
	private final String_bldr sb = String_bldr_.new_();
	public boolean prepare = false;
	public String Xto_str(Db_qry cmd, boolean prepare) {
		synchronized (sb) {
			this.prepare = prepare;
			switch (cmd.Tid()) {
				case Db_qry_.Tid_insert:		return Bld_qry_insert((Db_qry_insert)cmd);
				case Db_qry_.Tid_delete:		return Bld_qry_delete((Db_qry_delete)cmd);
				case Db_qry_.Tid_update:		return Bld_qry_update((Db_qry_update)cmd);
				case Db_qry_.Tid_select_in_tbl:
				case Db_qry_.Tid_select:		return Bld_qry_select((Db_qry__select_cmd)cmd);
				case Db_qry_.Tid_sql:			return ((Db_qry_sql)cmd).Xto_sql();
				default:						throw Err_.new_unhandled(cmd.Tid());
			}
		}
	}
	private String Bld_qry_delete(Db_qry_delete cmd) {
		sb.Add_many("DELETE FROM ", cmd.Base_table());
		Bld_where(sb, cmd.Where());
		return sb.Xto_str_and_clear();
	}
	private String Bld_qry_insert(Db_qry_insert cmd) {
		if (cmd.Select() != null) {
			sb.Add_many("INSERT INTO ", cmd.Base_table(), " (");
			for (int i = 0; i < cmd.Cols().Count(); i++) {
				Sql_select_fld_base fld = cmd.Cols().Get_at(i);
				sb.Add(fld.Alias());
				sb.Add(i == cmd.Cols().Count() - 1 ? ") " : ", ");
			}
			sb.Add(Bld_qry_select(cmd.Select()));
			return sb.Xto_str_and_clear();
		}
		int arg_count = cmd.Args().Count(); if (arg_count == 0) throw Err_.new_wo_type("Db_qry_insert has no columns", "base_table", cmd.Base_table());
		int last = arg_count - 1;
		sb.Add_many("INSERT INTO ", cmd.Base_table(), " (");
		for (int i = 0; i < arg_count; i++) {
			KeyVal pair = cmd.Args().Get_at(i);
			this.Xto_sql_col(sb, pair.Key_as_obj());
			sb.Add(i == last ? ")" : ", ");
		}
		sb.Add(" VALUES (");
		for (int i = 0; i < arg_count; i++) {
			KeyVal pair = cmd.Args().Get_at(i);
			Db_arg arg = (Db_arg)pair.Val();
			this.Bld_val(sb, arg);
			sb.Add(i == last ? ")" : ", ");
		}
		return sb.Xto_str_and_clear();
	}
	private String Bld_qry_update(Db_qry_update cmd) {
		int arg_count = cmd.Args().Count(); if (arg_count == 0) throw Err_.new_wo_type("Db_qry_update has no columns", "base_table", cmd.Base_table());
		sb.Add_many("UPDATE ", cmd.Base_table(), " SET ");
		for (int i = 0; i < arg_count; i++) {
			KeyVal pair = cmd.Args().Get_at(i);
			if (i > 0) sb.Add(", ");
			this.Xto_sql_col(sb, pair.Key_as_obj());
			sb.Add("=");
			this.Bld_val(sb, (Db_arg)pair.Val());
		}
		Bld_where(sb, cmd.Where());
		return sb.Xto_str_and_clear();
	}
	private String Bld_qry_select(Db_qry__select_cmd cmd) {
		sb.Add("SELECT ");
		if (cmd.Cols().Distinct()) sb.Add("DISTINCT ");
		Sql_select_fld_list flds = cmd.Cols().Flds();
		if (flds.Count() == 0) sb.Add("*");
		for (int i = 0; i < flds.Count(); i++) {
			Sql_select_fld_base fld = (Sql_select_fld_base)flds.Get_at(i);
			if (i > 0) sb.Add(", ");
			this.Xto_sql_col(sb, fld.XtoSql());
		}
		Bld_clause_from(sb, cmd.From());
		Bld_indexed_by(sb, cmd.Indexed_by());
		Bld_where(sb, cmd.Where());
		Bld_select_group_by(sb, cmd.GroupBy());
		Bld_select_order_by(sb, cmd.OrderBy());
		Bld_select_limit(sb, cmd.Limit());
		return sb.Xto_str_and_clear();
	}
	private void Bld_select_group_by(String_bldr sb, Sql_group_by groupBy) {
		if (groupBy == null) return;
		sb.Add(" GROUP BY ");
		for (int i = 0; i < groupBy.Flds().Count(); i++) {
			String item = (String)groupBy.Flds().Get_at(i);
			if (i > 0) sb.Add(", ");
			sb.Add(item);
		}
	}
	private void Bld_select_order_by(String_bldr sb, Sql_order_by orderBy) {
		if (orderBy == null) return;
		sb.Add(" ORDER BY ");
		for (int i = 0; i < orderBy.Flds().Count(); i++) {
			Sql_order_by_itm item = (Sql_order_by_itm)orderBy.Flds().Get_at(i);
			if (i > 0) sb.Add(", ");
			sb.Add(item.XtoSql());
		}
	}
	private void Bld_select_limit(String_bldr sb, int limit) {
		if (limit == Db_qry__select_cmd.Limit_disabled) return;
		sb.Add(" LIMIT ").Add(limit);
	}
	private void Bld_clause_from(String_bldr sb, Sql_from from) {
		for (Object tblObj : from.Tbls()) {
			Sql_tbl_src tbl = (Sql_tbl_src)tblObj;
			sb.Add_many
				(	" ", String_.Upper(tbl.JoinType().Name()), " ", tbl.TblName(), String_.FormatOrEmptyStrIfNull(" {0}", tbl.Alias())
				);
			String tblAliasForJoin = tbl.Alias() == null ? tbl.TblName() : tbl.Alias();
			for (int i = 0; i < tbl.JoinLinks().Count(); i++) {
				Sql_join_itm joinLink = (Sql_join_itm)tbl.JoinLinks().Get_at(i);
				String conjunction = i == 0 ? " ON " : " AND ";
				sb.Add_many(conjunction, joinLink.SrcTbl(), ".", joinLink.SrcFld(), "=", tblAliasForJoin, ".", joinLink.TrgFldOrSrcFld());
			}
		}
	}
	private void Bld_indexed_by(String_bldr sb, String idx_name) {
		if (idx_name == null) return;
		sb.Add(" INDEXED BY ").Add(idx_name);
	}
	private void Xto_sql_col(String_bldr sb, Object obj) {
		if (obj == null) throw Err_.new_null();
		sb.Add_obj(obj);	// FIXME: options for bracketing; ex: [name]
	}
	public void Bld_val(String_bldr sb, Db_arg arg) {
		if (prepare) {
			sb.Add("?");
			return;
		}
		Object val = arg.Val();
		if (val == null) {
			sb.Add("NULL");
			return;
		}
		Class<?> val_type = val.getClass();
		if (val_type == Bool_.Cls_ref_type)
			sb.Add_obj(Bool_.Xto_int(Bool_.cast_(val)));	// NOTE: save boolean to 0 or 1, b/c (a) db may not support bit datatype (sqllite) and (b) avoid i18n issues with "true"/"false"
		else if 
			(	val_type == Byte_.Cls_ref_type	|| val_type == Short_.Cls_ref_type
			||	val_type == Int_.Cls_ref_type	|| val_type == Long_.Cls_ref_type
			||	val_type == Float_.Cls_ref_type	|| val_type == Double_.Cls_ref_type
			)
			sb.Add(Object_.Xto_str_strict_or_null(val));
		else if (val_type == DateAdp.class)
			Bld_val_date(sb, arg, (DateAdp)val);
		else if (val_type == Decimal_adp.class) {
			Decimal_adp valDecimal = (Decimal_adp)val;				
			sb.Add(valDecimal.To_str());
		}
		else {
			String valString = Object_.Xto_str_strict_or_null(val);
			Bld_val_str(sb, arg, valString);
		}
	}
	@gplx.Virtual public void Bld_val_str(String_bldr sb, Db_arg arg, String s) {
		sb.Add_many("'", String_.Replace(s, "'", "''"), "'");	// stupid escaping of '
	}
	@gplx.Virtual public void Bld_val_date(String_bldr sb, Db_arg arg, DateAdp s) {
		sb.Add_many("'", s.XtoStr_gplx_long(), "'");
	}
	public void Bld_where(String_bldr sb, Criteria crt) {
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
		sb.Add(" WHERE ");
		this.Bld_where_val(sb, crt);
	}
	public void Bld_where_val(String_bldr sb, Criteria crt) {
		if (crt == null) return;	// handle empty crt; EX: SELECT * FROM tbl;
		Criteria_bool_base crt_bool = Criteria_bool_base.as_(crt);
		if (crt_bool != null) {
			sb.Add("(");
			Bld_where_val(sb, crt_bool.Lhs());
			sb.Add_many(" ", crt_bool.Op_literal(), " ");
			Bld_where_val(sb, crt_bool.Rhs());
			sb.Add(")");
			return;
		}
		if (crt.Tid() == Criteria_.Tid_db_obj_ary) {
			Append_db_obj_ary(sb, (Db_obj_ary_crt)crt);
		}
		else {
			Criteria_fld leaf = Criteria_fld.as_(crt); if (leaf == null) throw Err_.new_invalid_op(crt.XtoStr());
			sb.Add(leaf.Key());
			Bld_where_crt(sb, leaf.Crt());
		}
	}
	private void Bld_where_crt(String_bldr sb, Criteria crt) {
		switch (crt.Tid()) {
			case Criteria_.Tid_eq:			Bld_where_eq(sb, Criteria_eq.as_(crt)); break;
			case Criteria_.Tid_comp:		Bld_where_comp(sb, Criteria_comp.as_(crt)); break;
			case Criteria_.Tid_between:		Bld_where_between(sb, Criteria_between.as_(crt)); break;
			case Criteria_.Tid_in:			Bld_where_in(sb, Criteria_in.as_(crt)); break;
			case Criteria_.Tid_like:		Bld_where_like(sb, Criteria_like.as_(crt)); break;
			case Criteria_.Tid_iomatch:		Bld_where_iomatch(sb, Criteria_ioMatch.as_(crt)); break;
			default:						throw Err_.new_unhandled(crt);
		}
	}
	private void Bld_where_eq(String_bldr sb, Criteria_eq crt) {
		sb.Add(crt.Negated() ? "!=" : "=");
		this.Bld_val(sb, Wrap(crt.Val()));
	}
	private void Bld_where_comp(String_bldr sb, Criteria_comp crt) {
		sb.Add_many(crt.XtoSymbol());
		this.Bld_val(sb, Wrap(crt.Val()));
	}
	private void Bld_where_between(String_bldr sb, Criteria_between crt) {
		sb.Add(crt.Negated() ? " NOT BETWEEN " : " BETWEEN ");
		this.Bld_val(sb, Wrap(crt.Lhs()));
		sb.Add(" AND ");
		this.Bld_val(sb, Wrap(crt.Rhs()));
	}
	private void Bld_where_like(String_bldr sb, Criteria_like crt) {
		sb.Add(crt.Negated() ? " NOT LIKE " : " LIKE ");
		this.Bld_val(sb, Wrap(crt.Pattern().Raw()));
		sb.Add_fmt(" ESCAPE '{0}'", crt.Pattern().Escape());
	}
	private void Bld_where_in(String_bldr sb, Criteria_in crt) {
		sb.Add(crt.Negated() ? " NOT IN (" : " IN (");
		Object[] crt_vals = crt.Val_as_obj_ary();
		int len = crt_vals.length;
		int last = len - 1;
		for (int i = 0; i < len; i++) {
			Object val = crt_vals[i];
			this.Bld_val(sb, Wrap(val));
			sb.Add(i == last ? ")" : ", ");
		}
	}
	private void Bld_where_iomatch(String_bldr sb, Criteria_ioMatch crt) {
		sb.Add(crt.Negated() ? " NOT IOMATCH " : " IOMATCH ");
		this.Bld_val(sb, Wrap(crt.Pattern().Raw()));
	}
	public void Append_db_obj_ary(String_bldr sb, Db_obj_ary_crt crt) {
		Object[][] ary = crt.Vals();
		int ary_len = ary.length; 
		Db_fld[] flds = crt.Flds();
		for (int i = 0; i < ary_len; i++) {
			Object[] itm = (Object[])ary[i];
			int itm_len = itm.length;
			if (i != 0) sb.Add(" OR ");
			sb.Add("(");
			for (int j = 0; j < itm_len; j++) {
				if (j != 0) sb.Add(" AND ");
				Db_fld fld = flds[j];
				Object val = itm[j];
				boolean quote = false;
				switch (fld.Type_tid()) {
					case ClassAdp_.Tid_str:
					case ClassAdp_.Tid_char:
					case ClassAdp_.Tid_date:
						quote = true;
						break;
				}
				sb.Add(fld.Name());
				sb.Add("=");
				if (quote) sb.Add("'");
				sb.Add(Object_.Xto_str_strict_or_empty(val));
				if (quote) sb.Add("'");
			}
			sb.Add(")");
		}		
	}
	private Db_arg Wrap(Object val) {return new Db_arg("unknown", val);}
}
class Sql_qry_wtr_ansi_escape_backslash extends Sql_qry_wtr_ansi {		@Override public void Bld_val_str(String_bldr sb, Db_arg arg, String s) {
		if (String_.Has(s, "\\")) s = String_.Replace(s, "\\", "\\\\");
		super.Bld_val_str(sb, arg, s);
	}
}
