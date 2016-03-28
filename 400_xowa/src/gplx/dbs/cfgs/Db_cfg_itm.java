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
package gplx.dbs.cfgs; import gplx.*; import gplx.dbs.*;
public class Db_cfg_itm {
	public Db_cfg_itm(String grp, String key, String val) {this.grp = grp; this.key = key; this.val = val;}
	public String		Grp() {return grp;} private final String grp;
	public String		Key() {return key;} private final String key;
	public String		Val() {return val;} public Db_cfg_itm Val_(String v) {val = v; return this;} private String val;
	public String		To_str_or(String or)			{return val == null ? or : val;}
	public byte[]		To_bry_or(byte[] or)			{try {return val == null ? or : Bry_.new_u8(val)			;} catch (Exception e) {throw err_parse(e, Bry_.Cls_val_name);}}
	public int			To_int_or(int or)				{try {return val == null ? or : Int_.parse_or(val, or)		;} catch (Exception e) {throw err_parse(e, Int_.Cls_val_name);}}
	public long			To_long_or(long or)				{try {return val == null ? or : Long_.parse_or(val, or)	;} catch (Exception e) {throw err_parse(e, Long_.Cls_val_name);}}
	public byte			To_byte_or(byte or)				{try {return val == null ? or : Byte_.parse_or(val, or)	;} catch (Exception e) {throw err_parse(e, Byte_.Cls_val_name);}}
	public boolean		To_yn_or_n()					{return To_yn_or(Bool_.N);}
	public boolean		To_yn_or(boolean or)			{try {return val == null ? or : Yn.parse_by_char_or(val, or);} catch (Exception e) {throw err_parse(e, Bool_.Cls_val_name);}}
	public DateAdp		To_date_or(DateAdp or)			{try {return val == null ? or : DateAdp_.parse_gplx(val)	;} catch (Exception e) {throw err_parse(e, DateAdp_.Cls_ref_name);}}
	public Guid_adp		To_guid_or(Guid_adp or)			{try {return val == null ? or : Guid_adp_.parse(val)		;} catch (Exception e) {throw err_parse(e, Guid_adp_.Cls_ref_name);}}
	public boolean  	To_bool()						{Fail_if_null(); try {return Yn.parse(val)					;} catch (Exception e) {throw err_parse(e, Bool_.Cls_val_name);}}
	public byte			To_byte()						{Fail_if_null(); try {return Byte_.parse(val)				;} catch (Exception e) {throw err_parse(e, Byte_.Cls_val_name);}}
	public int     		To_int()						{Fail_if_null(); try {return Int_.parse(val)				;} catch (Exception e) {throw err_parse(e, Int_.Cls_val_name);}}
	public String		To_str()						{Fail_if_null(); return val;}
	private void		Fail_if_null()					{if (val == null) throw Err_.new_wo_type("cfg.val is empty", "grp", grp, "key", key); }
	private Err			err_parse(Exception e, String type) {return Err_.new_wo_type("cfg.val is not parseable", "grp", grp, "key", key, "val", val, "type", type).Trace_ignore_add_1_();}
	private static final String Grp_none = "";
	public static		Db_cfg_itm new_str		(String key, String val)						{return new Db_cfg_itm(Grp_none	, key, val);}
	public static		Db_cfg_itm new_str		(String grp, String key, String val)			{return new Db_cfg_itm(grp		, key, val);}
	public static		Db_cfg_itm new_bry		(String key, byte[] val)						{return new Db_cfg_itm(Grp_none	, key, String_.new_u8(val));}
	public static		Db_cfg_itm new_bry		(String grp, String key, byte[] val)			{return new Db_cfg_itm(grp		, key, String_.new_u8(val));}
	public static		Db_cfg_itm new_int		(String key, int val)							{return new Db_cfg_itm(Grp_none	, key, Int_.To_str(val));}
	public static		Db_cfg_itm new_int		(String grp, String key, int val)				{return new Db_cfg_itm(grp		, key, Int_.To_str(val));}
	public static		Db_cfg_itm new_long		(String key, long val)							{return new Db_cfg_itm(Grp_none	, key, Long_.To_str(val));}
	public static		Db_cfg_itm new_long		(String grp, String key, long val)				{return new Db_cfg_itm(grp		, key, Long_.To_str(val));}
	public static		Db_cfg_itm new_byte		(String key, byte val)							{return new Db_cfg_itm(Grp_none	, key, Byte_.To_str(val));}
	public static		Db_cfg_itm new_byte		(String grp, String key, byte val)				{return new Db_cfg_itm(grp		, key, Byte_.To_str(val));}
	public static		Db_cfg_itm new_yn		(String key, boolean val)						{return new Db_cfg_itm(Grp_none	, key, Yn.To_str(val));}
	public static		Db_cfg_itm new_yn		(String grp, String key, boolean val)			{return new Db_cfg_itm(grp		, key, Yn.To_str(val));}
	public static		Db_cfg_itm new_DateAdp	(String key, DateAdp val)						{return new Db_cfg_itm(Grp_none	, key, val.XtoStr_fmt_yyyyMMdd_HHmmss());}
	public static		Db_cfg_itm new_DateAdp	(String grp, String key, DateAdp val)			{return new Db_cfg_itm(grp		, key, val.XtoStr_fmt_yyyyMMdd_HHmmss());}
	public static		Db_cfg_itm new_guid		(String key, Guid_adp val)						{return new Db_cfg_itm(Grp_none	, key, val.To_str());}
	public static		Db_cfg_itm new_guid		(String grp, String key, Guid_adp val)			{return new Db_cfg_itm(grp		, key, val.To_str());}
	public static final Db_cfg_itm Empty = new Db_cfg_itm("empty", "empty", null);
}
