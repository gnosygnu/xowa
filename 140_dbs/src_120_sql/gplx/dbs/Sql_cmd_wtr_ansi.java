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
package gplx.dbs; import gplx.*;
import gplx.criterias.*; /*Criteria_bool_base*/
class Sql_cmd_wtr_ansi implements Sql_cmd_wtr {
	boolean prepare = false;
	public String XtoSqlQry(Db_qry cmd, boolean prepare) {
		String key = cmd.KeyOfDb_qry(); this.prepare = prepare;
		if		(String_.Eq(key, Db_qry_insert.KeyConst)) return XtoSql_insert((Db_qry_insert)cmd);
		else if (String_.Eq(key, Db_qry_delete.KeyConst)) return XtoSql_delete((Db_qry_delete)cmd);
		else if (String_.Eq(key, Db_qry_update.KeyConst)) return XtoSql_update((Db_qry_update)cmd);
		else if (String_.Eq(key, Db_qry_select.KeyConst)) return XtoSql_select((Db_qry_select)cmd);
		else if (String_.Eq(key, Db_qry_sql.KeyConst))    return ((Db_qry_sql)cmd).XtoSql();
		else throw Err_.unhandled(cmd.KeyOfDb_qry());
	}
	public String XtoSql_delete(Db_qry_delete cmd) {
		String_bldr sb = String_bldr_.new_();
		sb.Add_many("DELETE FROM ", cmd.BaseTable());
		BldWhere(sb, cmd.Where());
		return sb.XtoStr();
	}
	public String XtoSql_insert(Db_qry_insert cmd) {
		String_bldr sb = String_bldr_.new_();
		if (cmd.Select() != null) {
			sb.Add_many("INSERT INTO ", cmd.BaseTable(), " (");
			for (int i = 0; i < cmd.Cols().Count(); i++) {
				Sql_select_fld_base fld = cmd.Cols().FetchAt(i);
				sb.Add(fld.Alias());
				sb.Add(i == cmd.Cols().Count() - 1 ? ") " : ", ");
			}
			sb.Add(XtoSql_select(cmd.Select()));
			return sb.XtoStr();
		}
		int argCount = cmd.Args().Count();
		if (argCount == 0) throw Err_.new_("Db_qry_insert has no columns").Add("baseTable", cmd.BaseTable());
		int last = argCount - 1;
		sb.Add_many("INSERT INTO ", cmd.BaseTable(), " (");
		for (int i = 0; i < argCount; i++) {
			KeyVal pair = cmd.Args().FetchAt(i);
			this.XtoSqlCol(sb, pair.Key_as_obj());
			sb.Add(i == last ? ")" : ", ");
		}
		sb.Add(" VALUES (");
		for (int i = 0; i < argCount; i++) {
			KeyVal pair = cmd.Args().FetchAt(i);
			Db_arg prm = (Db_arg)pair.Val();
			this.BldValStr(sb, prm);
			sb.Add(i == last ? ")" : ", ");
		}
		return sb.XtoStr();
	}
	public String XtoSql_update(Db_qry_update cmd) {
		int argCount = cmd.Args().Count();
		if (argCount == 0) throw Err_.new_("Db_qry_update has no columns").Add("baseTable", cmd.BaseTable());
		String_bldr sb = String_bldr_.new_();
		sb.Add_many("UPDATE ", cmd.BaseTable(), " SET ");
		for (int i = 0; i < argCount; i++) {
			KeyVal pair = cmd.Args().FetchAt(i);
			if (i > 0) sb.Add(", ");
			this.XtoSqlCol(sb, pair.Key_as_obj());
			sb.Add("=");
			this.BldValStr(sb, (Db_arg)pair.Val());
		}
		BldWhere(sb, cmd.Where());
		return sb.XtoStr();
	}
	public String XtoSql_select(Db_qry_select cmd) {
		String_bldr sb = String_bldr_.new_();
		sb.Add("SELECT ");
		if (cmd.Cols().Distinct()) sb.Add("DISTINCT ");
		Sql_select_fld_list flds = cmd.Cols().Flds();
		if (flds.Count() == 0) sb.Add("*");
		for (int i = 0; i < flds.Count(); i++) {
			Sql_select_fld_base fld = (Sql_select_fld_base)flds.FetchAt(i);
			if (i > 0) sb.Add(", ");
			this.XtoSqlCol(sb, fld.XtoSql());
		}
		XtoSql_from(sb, cmd.From());
		BldWhere(sb, cmd.Where());
		XtoSql_group_by(sb, cmd.GroupBy());
		XtoSql_order_by(sb, cmd.OrderBy());
		XtoSql_limit(sb, cmd.Limit());
		return sb.XtoStr();
	}
	void XtoSql_group_by(String_bldr sb, Sql_group_by groupBy) {
		if (groupBy == null) return;
		sb.Add(" GROUP BY ");
		for (int i = 0; i < groupBy.Flds().Count(); i++) {
			String item = (String)groupBy.Flds().FetchAt(i);
			if (i > 0) sb.Add(", ");
			sb.Add(item);
		}
	}
	void XtoSql_order_by(String_bldr sb, Sql_order_by orderBy) {
		if (orderBy == null) return;
		sb.Add(" ORDER BY ");
		for (int i = 0; i < orderBy.Flds().Count(); i++) {
			Sql_order_by_itm item = (Sql_order_by_itm)orderBy.Flds().FetchAt(i);
			if (i > 0) sb.Add(", ");
			sb.Add(item.XtoSql());
		}
	}
	void XtoSql_limit(String_bldr sb, int limit) {
		if (limit == Db_qry_select.Limit_disabled) return;
		sb.Add(" LIMIT ").Add(limit);
	}
	void XtoSql_from(String_bldr sb, Sql_from from) {
		for (Object tblObj : from.Tbls()) {
			Sql_tbl_src tbl = (Sql_tbl_src)tblObj;
			sb.Add_many
				(	" ", String_.Upper(tbl.JoinType().Name()), " ", tbl.TblName(), String_.FormatOrEmptyStrIfNull(" {0}", tbl.Alias())
				);
			String tblAliasForJoin = tbl.Alias() == null ? tbl.TblName() : tbl.Alias();
			for (int i = 0; i < tbl.JoinLinks().Count(); i++) {
				Sql_join_itm joinLink = (Sql_join_itm)tbl.JoinLinks().FetchAt(i);
				String conjunction = i == 0 ? " ON " : " AND ";
				sb.Add_many
					(	conjunction, joinLink.SrcTbl(), ".", joinLink.SrcFld(), "=", tblAliasForJoin, ".", joinLink.TrgFldOrSrcFld()
					);
			}
		}
	}
	public void XtoSqlCol(String_bldr sb, Object obj) {
		if (obj == null) throw Err_.null_("ColName");
		sb.Add_obj(obj);	// FIXME: options for bracketing; ex: [name]
	}
	public void BldValStr(String_bldr sb, Db_arg prm) {
		if (prepare) {
			sb.Add("?");
			return;
		}
		Object val = prm.Val();
		if (val == null)	{
			sb.Add("NULL");
			return;
		}
		Class<?> valType = val.getClass();
				if (valType == Boolean.class)
			sb.Add_obj(Bool_.Xto_int(Bool_.cast_(val)));			// NOTE!: save boolean to 0 or 1, b/c (a) db may not support bit datatype (sqllite) and (b) avoid i18n issues with "true"/"false"
		else if 
			(	valType == Byte.class || valType == Short.class 
			||	valType == Integer.class	|| valType == Long.class
			||	valType == Float.class || valType == Double.class
			)
			sb.Add(Object_.Xto_str_strict_or_null(val));
				else if (valType == DateAdp.class)
			XtoSqlVal_DateAdp(sb, prm, (DateAdp)val);
		else if (valType == DecimalAdp.class) {
			DecimalAdp valDecimal = (DecimalAdp)val;				
			sb.Add(valDecimal.Xto_str());
		}
		//		else if (valType == System.Enum.class)
//			sb.Add_any(Enm_.XtoInt(val));				// save enum as 0 or 1, since (a) no db supports enum datatype; (b) names are fungible; (c) int is less space than name
				else {
			String valString = Object_.Xto_str_strict_or_null(val);
			XtoSqlVal_Str(sb, prm, valString);
		}
	}
	@gplx.Virtual public void XtoSqlVal_Str(String_bldr sb, Db_arg prm, String s) {
		sb.Add_many("'", String_.Replace(s, "'", "''"), "'");	// stupid escaping of '
	}
	@gplx.Virtual public void XtoSqlVal_DateAdp(String_bldr sb, Db_arg prm, DateAdp s) {
		sb.Add_many("'", s.XtoStr_gplx_long(), "'");	// stupid escaping of '
	}
	void BldWhere(String_bldr sb, Sql_where where) {
		if (where == null || where.Crt() == Db_crt_.Wildcard) return;
		sb.Add(" WHERE ");
		this.BldWhere(sb, where.Crt());
	}
	public void BldWhere(String_bldr sb, Criteria crt) {
		Criteria_bool_base boolOp = Criteria_bool_base.as_(crt);
		if (boolOp != null) {
			sb.Add("(");
			BldWhere(sb, boolOp.Lhs());
			sb.Add_many(" ", boolOp.OpLiteral(), " ");
			BldWhere(sb, boolOp.Rhs());
			sb.Add(")");
			return;
		}
		if (crt.Crt_tid() == Criteria_.Tid_db_obj_ary) {
			Append_db_obj_ary(sb, (Db_obj_ary_crt)crt);
		}
		else {
			Criteria_wrapper leaf = Criteria_wrapper.as_(crt); if (leaf == null) throw Err_.invalid_op_(crt.XtoStr());
			sb.Add(leaf.Name_of_GfoFldCrt());
			AppendWhereItem(sb, leaf.Crt_of_GfoFldCrt());
		}
	}
	void AppendWhereItem(String_bldr sb, Criteria crt) {
		switch (crt.Crt_tid()) {
			case Criteria_.Tid_eq:			AppendEqual(sb, Criteria_eq.as_(crt)); break;
			case Criteria_.Tid_comp:		AppendCompare(sb, Criteria_comp.as_(crt)); break;
			case Criteria_.Tid_between:		AppendBetween(sb, Criteria_between.as_(crt)); break;
			case Criteria_.Tid_in:			AppendIn(sb, Criteria_in.as_(crt)); break;
			case Criteria_.Tid_like:		AppendLike(sb, Criteria_like.as_(crt)); break;
			case Criteria_.Tid_iomatch:		AppendIoMatch(sb, Criteria_ioMatch.as_(crt)); break;
			default:						throw Err_.unhandled(crt);
		}
	}
	void AppendEqual(String_bldr sb, Criteria_eq crt) {
		sb.Add(crt.Negated() ? "!=" : "=");
		this.BldValStr(sb, Wrap(crt.Value()));
	}
	void AppendCompare(String_bldr sb, Criteria_comp crt) {
		sb.Add_many(crt.XtoSymbol());
		this.BldValStr(sb, Wrap(crt.Value()));
	}
	void AppendBetween(String_bldr sb, Criteria_between crt) {
		sb.Add(crt.Negated() ? " NOT BETWEEN " : " BETWEEN ");
		this.BldValStr(sb, Wrap(crt.Lhs()));
		sb.Add(" AND ");
		this.BldValStr(sb, Wrap(crt.Rhs()));
	}
	void AppendLike(String_bldr sb, Criteria_like crt) {
		sb.Add(crt.Negated() ? " NOT LIKE " : " LIKE ");
		this.BldValStr(sb, Wrap(crt.Pattern().Raw()));
		sb.Add_fmt(" ESCAPE '{0}'", crt.Pattern().Escape());
	}
	void AppendIn(String_bldr sb, Criteria_in crt) {
		sb.Add(crt.Negated() ? " NOT IN (" : " IN (");
		int last = crt.Values().length - 1;
		for (int i = 0; i < crt.Values().length; i++) {
			Object val = crt.Values()[i];
			this.BldValStr(sb, Wrap(val));
			sb.Add(i == last ? ")" : ", ");
		}
	}
	void AppendIoMatch(String_bldr sb, Criteria_ioMatch crt) {
		sb.Add(crt.Negated() ? " NOT IOMATCH " : " IOMATCH ");
		this.BldValStr(sb, Wrap(crt.Pattern().Raw()));
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
	Db_arg Wrap(Object val) {return new Db_arg("unknown", val);}
}
class Sql_cmd_wtr_ansi_ {
	public static Sql_cmd_wtr default_() {return new Sql_cmd_wtr_ansi();}
	public static Sql_cmd_wtr backslash_sensitive_() {return Sql_cmd_wtr_ansi_backslashSensitive.new_();}
}	
class Sql_cmd_wtr_ansi_backslashSensitive extends Sql_cmd_wtr_ansi {		@Override public void XtoSqlVal_Str(String_bldr sb, Db_arg prm, String s) {
		if (String_.Has(s, "\\")) s = String_.Replace(s, "\\", "\\\\");
		super.XtoSqlVal_Str(sb, prm, s);
	}
	public static Sql_cmd_wtr_ansi_backslashSensitive new_() {return new Sql_cmd_wtr_ansi_backslashSensitive();} Sql_cmd_wtr_ansi_backslashSensitive() {}
}
