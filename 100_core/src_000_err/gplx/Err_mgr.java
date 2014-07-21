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
package gplx;
public class Err_mgr {
	Err_mgr(Gfo_msg_root msg_root) {this.msg_root = msg_root;} Gfo_msg_root msg_root;
	public Err not_implemented_()												{return Err_.new_(Msg_not_implemented.Gen_str_none());}
	public Err unhandled_(Object obj)											{return Err_.new_(Msg_unhandled.Gen_str_one(obj));}
	public Err cast_(Exception e, Class<?> obj_class, Object obj)		{return Err_.new_(Msg_cast.Gen_str_many(ClassAdp_.NameOf_type(obj_class), Object_.Xto_str_strict_or_null_mark(obj)));}
	public Err parse_(Class<?> type		, byte[] raw)						{return Err_.new_(Msg_parse.Gen_str_many(ClassAdp_.NameOf_type(type), String_.new_utf8_len_safe_(raw, 0, 255)));}
	public Err parse_obj_(Object o			, byte[] raw)						{return Err_.new_(Msg_parse.Gen_str_many(ClassAdp_.NameOf_obj(o), String_.new_utf8_len_safe_(raw, 0, 255)));}
	public Err parse_(String type_name, byte[] raw)								{return Err_.new_(Msg_parse.Gen_str_many(type_name, String_.new_utf8_len_safe_(raw, 0, 255)));}
	public Err parse_(String type_name, String raw)								{return Err_.new_(Msg_parse.Gen_str_many(type_name, String_.MidByLenSafe(raw, 0, 255)));}
	public Err fmt_auto_(String grp, String key, Object... vals) {return fmt_(grp, key, Bry_fmtr.New_fmt_str(key, vals), vals);}
	public Err fmt_(String grp, String key, String fmt, Object... vals)	{
		Gfo_msg_data data = msg_root.Data_new_many(Gfo_msg_itm_.Cmd_fail, grp, key, fmt, vals);
		return Err_.new_(data.Gen_str_ary());
	}
	
        public static final Err_mgr _ = new Err_mgr(Gfo_msg_root._);
	static final Gfo_msg_grp GRP_OBJ = Gfo_msg_grp_.new_(Gfo_msg_grp_.Root_gplx, "Object");
	static final Gfo_msg_itm
		  Msg_unhandled							= Gfo_msg_itm_.new_fail_(GRP_OBJ, "unhandled"			, "unhandled value: '~{0}'")
		, Msg_cast								= Gfo_msg_itm_.new_fail_(GRP_OBJ, "cast"				, "cast failed; expd:'~{0}' actl:'~{1}'")
		, Msg_generic							= Gfo_msg_itm_.new_fail_(GRP_OBJ, "generic"				, "generic error; expd:'~{0}' actl:'~{1}'")
		, Msg_parse								= Gfo_msg_itm_.new_fail_(GRP_OBJ, "parse"				, "parse error; type:'~{0}' raw:'~{1}'")
		, Msg_not_implemented					= Gfo_msg_itm_.new_fail_(GRP_OBJ, "not_implemented"		, "not implemented")
		;
}
