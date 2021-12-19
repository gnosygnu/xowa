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
package gplx.dbs.cfgs;
import gplx.types.errs.Err;
import gplx.Yn;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoGuid;
import gplx.types.commons.GfoGuidUtl;
public class Db_cfg_itm {
	public Db_cfg_itm(String grp, String key, String val) {this.grp = grp; this.key = key; this.val = val;}
	public String		Grp() {return grp;} private final String grp;
	public String		Key() {return key;} private final String key;
	public String		Val() {return val;} public Db_cfg_itm Val_(String v) {val = v; return this;} private String val;
	public String		To_str_or(String or)			{return val == null ? or : val;}
	public byte[]		To_bry_or(byte[] or)			{try {return val == null ? or : BryUtl.NewU8(val)			;} catch (Exception e) {throw err_parse(e, BryUtl.ClsValName);}}
	public int			To_int_or(int or)				{try {return val == null ? or : IntUtl.ParseOr(val, or)		;} catch (Exception e) {throw err_parse(e, IntUtl.ClsValName);}}
	public long			To_long_or(long or)				{try {return val == null ? or : LongUtl.ParseOr(val, or)	;} catch (Exception e) {throw err_parse(e, LongUtl.ClsValName);}}
	public byte			To_byte_or(byte or)				{try {return val == null ? or : ByteUtl.ParseOr(val, or)	;} catch (Exception e) {throw err_parse(e, ByteUtl.ClsValName);}}
	public boolean		To_yn_or_n()					{return To_yn_or(BoolUtl.N);}
	public boolean		To_yn_or(boolean or)			{try {return val == null ? or : Yn.parse_by_char_or(val, or);} catch (Exception e) {throw err_parse(e, BoolUtl.ClsValName);}}
	public GfoDate To_date_or(GfoDate or)			{try {return val == null ? or : GfoDateUtl.ParseGplx(val)	;} catch (Exception e) {throw err_parse(e, GfoDateUtl.ClsRefName);}}
	public GfoGuid To_guid_or(GfoGuid or)			{try {return val == null ? or : GfoGuidUtl.Parse(val)		;} catch (Exception e) {throw err_parse(e, GfoGuidUtl.Cls_ref_name);}}
	public boolean  	To_bool()						{Fail_if_null(); try {return Yn.parse(val)					;} catch (Exception e) {throw err_parse(e, BoolUtl.ClsValName);}}
	public byte			To_byte()						{Fail_if_null(); try {return ByteUtl.Parse(val)				;} catch (Exception e) {throw err_parse(e, ByteUtl.ClsValName);}}
	public int     		To_int()						{Fail_if_null(); try {return IntUtl.Parse(val)				;} catch (Exception e) {throw err_parse(e, IntUtl.ClsValName);}}
	public String		To_str()						{Fail_if_null(); return val;}
	private void		Fail_if_null()					{if (val == null) throw ErrUtl.NewArgs("cfg.val is empty", "grp", grp, "key", key); }
	private Err			err_parse(Exception e, String type) {return ErrUtl.NewArgs("cfg.val is not parseable", "grp", grp, "key", key, "val", val, "type", type);}
	private static final String Grp_none = "";
	public static		Db_cfg_itm new_str		(String key, String val)						{return new Db_cfg_itm(Grp_none	, key, val);}
	public static		Db_cfg_itm new_str		(String grp, String key, String val)			{return new Db_cfg_itm(grp		, key, val);}
	public static		Db_cfg_itm new_bry		(String key, byte[] val)						{return new Db_cfg_itm(Grp_none	, key, StringUtl.NewU8(val));}
	public static		Db_cfg_itm new_bry		(String grp, String key, byte[] val)			{return new Db_cfg_itm(grp		, key, StringUtl.NewU8(val));}
	public static		Db_cfg_itm new_int		(String key, int val)							{return new Db_cfg_itm(Grp_none	, key, IntUtl.ToStr(val));}
	public static		Db_cfg_itm new_int		(String grp, String key, int val)				{return new Db_cfg_itm(grp		, key, IntUtl.ToStr(val));}
	public static		Db_cfg_itm new_long		(String key, long val)							{return new Db_cfg_itm(Grp_none	, key, LongUtl.ToStr(val));}
	public static		Db_cfg_itm new_long		(String grp, String key, long val)				{return new Db_cfg_itm(grp		, key, LongUtl.ToStr(val));}
	public static		Db_cfg_itm new_byte		(String key, byte val)							{return new Db_cfg_itm(Grp_none	, key, ByteUtl.ToStr(val));}
	public static		Db_cfg_itm new_byte		(String grp, String key, byte val)				{return new Db_cfg_itm(grp		, key, ByteUtl.ToStr(val));}
	public static		Db_cfg_itm new_yn		(String key, boolean val)						{return new Db_cfg_itm(Grp_none	, key, Yn.To_str(val));}
	public static		Db_cfg_itm new_yn		(String grp, String key, boolean val)			{return new Db_cfg_itm(grp		, key, Yn.To_str(val));}
	public static		Db_cfg_itm new_DateAdp	(String key, GfoDate val)						{return new Db_cfg_itm(Grp_none	, key, val.ToStrFmt_yyyyMMdd_HHmmss());}
	public static		Db_cfg_itm new_DateAdp	(String grp, String key, GfoDate val)			{return new Db_cfg_itm(grp		, key, val.ToStrFmt_yyyyMMdd_HHmmss());}
	public static		Db_cfg_itm new_guid		(String key, GfoGuid val)						{return new Db_cfg_itm(Grp_none	, key, val.ToStr());}
	public static		Db_cfg_itm new_guid		(String grp, String key, GfoGuid val)			{return new Db_cfg_itm(grp		, key, val.ToStr());}
	public static final Db_cfg_itm Empty = new Db_cfg_itm("empty", "empty", null);
}
