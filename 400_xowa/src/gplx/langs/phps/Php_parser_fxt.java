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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.tests.*; import gplx.core.log_msgs.*;
class Php_parser_fxt {
	Php_tkn_factory tkn_factory = new Php_tkn_factory();
	Php_parser parser = new Php_parser();
	Php_tkn_wkr_tkn tkn_wkr = new Php_tkn_wkr_tkn(); 
	Php_evaluator line_wkr = new Php_evaluator(new Gfo_msg_log("test")); 
	Tst_mgr tst_mgr = new Tst_mgr();
	Gfo_msg_log_chkr log_mgr_chkr = new Gfo_msg_log_chkr(); 
	public void Clear() {log_mgr_chkr.Clear(); tkn_wkr.Clear(); line_wkr.Clear();}
	public Php_tkn_chkr_base tkn_declaration() 						{return Php_tkn_declaration_chkr.Instance;}
	public Php_tkn_chkr_base tkn_txt(int bgn, int end) 				{return new Php_tkn_txt_chkr(bgn, end);}
	public Php_tkn_chkr_base tkn_ws(int bgn, int end) 				{return new Php_tkn_ws_chkr(bgn, end);}
	public Php_tkn_chkr_base tkn_generic(int bgn, int end, byte tid) 	{return new Php_tkn_generic_chkr(bgn, end, tid);}
	public Php_tkn_comment_chkr tkn_comment_mult(int bgn, int end) 	{return new Php_tkn_comment_chkr(bgn, end).Comment_tid_(Php_tkn_comment.Tid_mult);}
	public Php_tkn_comment_chkr tkn_comment_slash(int bgn, int end) {return new Php_tkn_comment_chkr(bgn, end).Comment_tid_(Php_tkn_comment.Tid_slash);}
	public Php_tkn_comment_chkr tkn_comment_hash(int bgn, int end) 	{return new Php_tkn_comment_chkr(bgn, end).Comment_tid_(Php_tkn_comment.Tid_hash);}
	public Php_tkn_quote_chkr tkn_quote_apos(int bgn, int end) 		{return new Php_tkn_quote_chkr(bgn, end).Quote_tid_(Byte_ascii.Apos);}
	public Php_tkn_quote_chkr tkn_quote_quote(int bgn, int end) 	{return new Php_tkn_quote_chkr(bgn, end).Quote_tid_(Byte_ascii.Quote);}
	public Php_parser_fxt Msg(Gfo_msg_itm itm, int bgn, int end) {
		log_mgr_chkr.Add_itm(itm, bgn, end);
		return this;
	}
	public Php_tkn_var_chkr tkn_var(int bgn, int end, String v) 	{return new Php_tkn_var_chkr(bgn, end).Var_name_(v);}
	public Php_tkn_num_chkr tkn_num(int bgn, int end, int v) 		{return new Php_tkn_num_chkr(bgn, end).Num_val_int_(v);}
	public Php_line_assign_chkr line_assign(String key, Php_itm_chkr_base val) 	{return new Php_line_assign_chkr().Key_(key).Val_(val);}
	public Php_line_assign_chkr line_assign_subs(String key, String[] subs, Php_itm_chkr_base val) 	{return new Php_line_assign_chkr().Key_(key).Subs_(subs).Val_(val);}
	public Php_itm_chkr_base itm_bool_true() 									{return new Php_itm_generic_chkr(Php_itm_.Tid_bool_true);}
	public Php_itm_chkr_base itm_bool_false() 									{return new Php_itm_generic_chkr(Php_itm_.Tid_bool_false);}
	public Php_itm_chkr_base itm_null() 										{return new Php_itm_generic_chkr(Php_itm_.Tid_null);}
	public Php_itm_chkr_base itm_quote(String v) 								{return new Php_itm_quote_chkr().Val_obj_str_(v);}
	public Php_itm_chkr_base itm_int(int v) 									{return new Php_itm_int_chkr().Val_obj_int_(v);}
	public Php_itm_chkr_base itm_txt(String v) 									{return new Php_itm_txt_chkr().Val_obj_str_(v);}
	public Php_itm_ary_chkr  itm_ary() 											{return new Php_itm_ary_chkr();}
	public Php_itm_kv_chkr   itm_kv_quote(String k, String v) 					{return new Php_itm_kv_chkr().Key_(k).Val_(itm_quote(v));}
	public Php_itm_kv_chkr   itm_kv_txt(String k, String v) 					{return new Php_itm_kv_chkr().Key_(k).Val_(itm_txt(v));}
	public Php_itm_kv_chkr   itm_kv_int(String k, int v) 						{return new Php_itm_kv_chkr().Key_(k).Val_(itm_int(v));}
	public Php_itm_kv_chkr   itm_kv_itm(String k, Php_itm_chkr_base v) 			{return new Php_itm_kv_chkr().Key_(k).Val_(v);}
	public void tst_tkns(String raw, Php_tkn_chkr_base... expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		parser.Parse_tkns(raw_bry, tkn_wkr);
		Php_tkn[] actl = (Php_tkn[])tkn_wkr.List().To_ary(Php_tkn.class);
		tst_mgr.Vars().Clear().Add("raw_bry", raw_bry);
		tst_mgr.Tst_ary("", expd, actl);
		log_mgr_chkr.tst(tst_mgr, tkn_wkr.Msg_log());
	}
	public void tst_lines(String raw, Php_line_assign_chkr... expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		parser.Parse_tkns(raw_bry, line_wkr);
		Php_line[] actl = (Php_line[])line_wkr.List().To_ary(Php_line.class);
		tst_mgr.Vars().Clear().Add("raw_bry", raw_bry);
		tst_mgr.Tst_ary("", expd, actl);
		log_mgr_chkr.tst(tst_mgr, line_wkr.Msg_log());
	}
}
abstract class Php_tkn_chkr_base implements Tst_chkr {
	public abstract byte Tkn_tid();
	public abstract Class<?> TypeOf();
	public int Src_bgn() {return src_bgn;} private int src_bgn = -1;
	public int Src_end() {return src_end;} private int src_end = -1;
	public void Src_rng_(int src_bgn, int src_end) {this.src_bgn = src_bgn; this.src_end = src_end;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Php_tkn actl = (Php_tkn)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "tkn_tid", this.Tkn_tid(), actl.Tkn_tid());
		rv += mgr.Tst_val(src_bgn == -1, path, "src_bgn", src_bgn, actl.Src_bgn());
		rv += mgr.Tst_val(src_end == -1, path, "src_end", src_end, actl.Src_end());
		rv += Chk_tkn(mgr, path, actl);
		return rv;
	}
	@gplx.Virtual public int Chk_tkn(Tst_mgr mgr, String path, Php_tkn actl_obj) {return 0;}
}
class Php_tkn_declaration_chkr extends Php_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Php_tkn_declaration.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_declaration;}
	public static final    Php_tkn_declaration_chkr Instance = new Php_tkn_declaration_chkr(); 
}
class Php_tkn_txt_chkr extends Php_tkn_chkr_base {
	public Php_tkn_txt_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_txt.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_txt;}
}
class Php_tkn_ws_chkr extends Php_tkn_chkr_base {
	public Php_tkn_ws_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_ws.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_ws;}
}
class Php_tkn_comment_chkr extends Php_tkn_chkr_base {
	public Php_tkn_comment_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_comment.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_comment;}
	public Php_tkn_comment_chkr Comment_tid_(byte v) {this.comment_tid = v; return this;} private byte comment_tid = Php_tkn_comment.Tid_null;
	@Override public int Chk_tkn(Tst_mgr mgr, String path, Php_tkn actl_obj) {
		Php_tkn_comment actl = (Php_tkn_comment)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(comment_tid == Php_tkn_comment.Tid_null, path, "comment_tid", comment_tid, actl.Comment_tid()); 
		return rv;
	}
}
class Php_tkn_quote_chkr extends Php_tkn_chkr_base {
	public Php_tkn_quote_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_quote.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_quote;}
	public Php_tkn_quote_chkr Quote_tid_(byte v) {this.quote_tid = v; return this;} private byte quote_tid = Byte_ascii.Null;
	@Override public int Chk_tkn(Tst_mgr mgr, String path, Php_tkn actl_obj) {
		Php_tkn_quote actl = (Php_tkn_quote)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(quote_tid == Byte_ascii.Null, path, "quote_tid", quote_tid, actl.Quote_tid()); 
		return rv;
	}
}
class Php_tkn_var_chkr extends Php_tkn_chkr_base {
	public Php_tkn_var_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_var.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_var;}
	public Php_tkn_var_chkr Var_name_(String v) {this.var_name = v; return this;} private String var_name;
	@Override public int Chk_tkn(Tst_mgr mgr, String path, Php_tkn actl_obj) {
		Php_tkn_var actl = (Php_tkn_var)actl_obj;
		int rv = 0;
		byte[] raw_bry = (byte[])mgr.Vars_get_by_key("raw_bry"); 
		rv += mgr.Tst_val(var_name == null, path, "var_name", var_name, String_.new_u8(actl.Var_name(raw_bry))); 
		return rv;
	}
}
class Php_tkn_num_chkr extends Php_tkn_chkr_base {
	public Php_tkn_num_chkr(int src_bgn, int src_end) {this.Src_rng_(src_bgn, src_end);}
	@Override public Class<?> TypeOf() {return Php_tkn_num.class;}
	@Override public byte Tkn_tid() {return Php_tkn_.Tid_num;}
	public Php_tkn_num_chkr Num_val_int_(int v) {this.num_val_int = v; return this;} private int num_val_int = Int_.Min_value;
	@Override public int Chk_tkn(Tst_mgr mgr, String path, Php_tkn actl_obj) {
		Php_tkn_num actl = (Php_tkn_num)actl_obj;
		int rv = 0;
		byte[] raw_bry = (byte[])mgr.Vars_get_by_key("raw_bry"); 
		rv += mgr.Tst_val(num_val_int == Int_.Min_value, path, "num_val_int", num_val_int, actl.Num_val_int(raw_bry)); 
		return rv;
	}
}
class Php_tkn_generic_chkr extends Php_tkn_chkr_base {
	public Php_tkn_generic_chkr(int src_bgn, int src_end, byte tkn_tid) {this.Src_rng_(src_bgn, src_end); this.tkn_tid = tkn_tid;}
	@Override public Class<?> TypeOf() {return Php_tkn.class;}
	@Override public byte Tkn_tid() {return tkn_tid;} private byte tkn_tid;
}
class Php_line_assign_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Php_line_assign.class;}
	public Php_line_assign_chkr Key_(String v) {key = v; return this;} private String key;
	public Php_line_assign_chkr Subs_(String[] v) {
		int subs_len = v.length;
		subs = new Php_itm_quote_chkr[subs_len];
		for (int i = 0; i < subs_len; i++)
			subs[i] = new Php_itm_quote_chkr().Val_obj_str_(v[i]);
		return this;
	}	Php_itm_chkr_base[] subs;
	public Php_line_assign_chkr Val_(Php_itm_chkr_base v) {val = v; return this;} Php_itm_chkr_base val;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Php_line_assign actl = (Php_line_assign)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_u8(actl.Key().Val_obj_bry()));
		if (subs != null) rv += mgr.Tst_sub_ary(subs, actl.Key_subs(), "subs", rv);
		rv += mgr.Tst_sub_obj(val, actl.Val(), "val", rv);
		return rv;
	}
}
abstract class Php_itm_chkr_base implements Tst_chkr {
	public abstract byte Itm_tid();
	public abstract Class<?> TypeOf();
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Php_itm actl = (Php_itm)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "tkn_tid", this.Itm_tid(), actl.Itm_tid());
		rv += Chk_itm(mgr, path, actl);
		return rv;
	}
	@gplx.Virtual public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {return 0;}
	public static final    Php_itm_chkr_base[] Ary_empty = new Php_itm_chkr_base[0];
}
class Php_itm_generic_chkr extends Php_itm_chkr_base {
	public Php_itm_generic_chkr(byte itm_tid) {this.itm_tid = itm_tid;} private byte itm_tid;	
	@Override public byte Itm_tid() {return itm_tid;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
}
class Php_itm_int_chkr extends Php_itm_chkr_base {
	@Override public byte Itm_tid() {return Php_itm_.Tid_int;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
	public Php_itm_int_chkr Val_obj_int_(int v) {this.val_obj_int = v; return this;} private int val_obj_int;
	@Override public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {
		Php_itm_int actl = (Php_itm_int)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "val_obj_str", val_obj_int, actl.Val_obj_int());
		return rv;
	}
}
class Php_itm_txt_chkr extends Php_itm_chkr_base {
	@Override public byte Itm_tid() {return Php_itm_.Tid_var;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
	public Php_itm_txt_chkr Val_obj_str_(String v) {this.val_obj_str = v; return this;} private String val_obj_str;
	@Override public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {
		Php_itm_var actl = (Php_itm_var)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "val_obj_str", val_obj_str, String_.new_u8(actl.Val_obj_bry()));
		return rv;
	}
}
class Php_itm_quote_chkr extends Php_itm_chkr_base {
	@Override public byte Itm_tid() {return Php_itm_.Tid_quote;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
	public Php_itm_quote_chkr Val_obj_str_(String v) {this.val_obj_str = v; return this;} private String val_obj_str;
	@Override public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {
		Php_itm_quote actl = (Php_itm_quote)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "val_obj_str", val_obj_str, String_.new_u8(actl.Val_obj_bry()));
		return rv;
	}
}
class Php_itm_ary_chkr extends Php_itm_chkr_base {
	@Override public byte Itm_tid() {return Php_itm_.Tid_ary;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
	public Php_itm_ary_chkr Subs_(Php_itm_chkr_base... v) {this.itms = v; return this;} Php_itm_chkr_base[] itms = Php_itm_chkr_base.Ary_empty;
	@Override public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {
		Php_itm_ary actl = (Php_itm_ary)actl_obj;
		int rv = 0;
		int actl_subs_len = actl.Subs_len();
		Php_itm[] actl_ary = new Php_itm[actl_subs_len];
		for (int i = 0; i < actl_subs_len; i++) {
			actl_ary[i] = (Php_itm)actl.Subs_get(i);
		}
		rv += mgr.Tst_sub_ary(itms, actl_ary, "subs", rv);
		return rv;
	}
}
class Php_itm_kv_chkr extends Php_itm_chkr_base {
	@Override public byte Itm_tid() {return Php_itm_.Tid_kv;}
	@Override public Class<?> TypeOf() {return Php_itm.class;}
	public Php_itm_kv_chkr Key_(String v) {key = v; return this;} private String key;
	public Php_itm_kv_chkr Val_(Php_itm_chkr_base v) {val = v; return this;} Php_itm_chkr_base val;
	@Override public int Chk_itm(Tst_mgr mgr, String path, Php_itm actl_obj) {
		Php_itm_kv actl = (Php_itm_kv)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(false, path, "key", key, String_.new_u8(actl.Key().Val_obj_bry()));
		rv += mgr.Tst_sub_obj(val, actl.Val(), path, rv);
		return rv;
	}
}
class Gfo_msg_log_chkr implements Tst_chkr {
	List_adp itms = List_adp_.New(); 
	public Class<?> TypeOf() {return Gfo_msg_log.class;}
	public void Clear() {itms.Clear();}
	public void Add_itm(Gfo_msg_itm itm, int bgn, int end) {
		Gfo_msg_data_chkr chkr = new Gfo_msg_data_chkr();
		chkr.Itm_(itm).Excerpt_bgn_(bgn).Excerpt_end_(end);
		itms.Add(chkr);
	}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {return 0;}
	public void tst(Tst_mgr mgr, Object actl_obj) {
		Gfo_msg_log actl = (Gfo_msg_log)actl_obj;
		int actl_itms_len = actl.Ary_len();
		Gfo_msg_data[] actl_itms = new Gfo_msg_data[actl_itms_len];		
		for (int i = 0; i < actl_itms_len; i++)
			actl_itms[i] = actl.Ary_get(i);
		mgr.Tst_ary("itms", (Gfo_msg_data_chkr[])itms.To_ary(Gfo_msg_data_chkr.class), actl_itms);
	}
}
class Gfo_msg_data_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Gfo_msg_data.class;}
	public Gfo_msg_data_chkr Itm_(Gfo_msg_itm v) {itm = v; return this;} Gfo_msg_itm itm;
	public Gfo_msg_data_chkr Excerpt_bgn_(int v) {excerpt_bgn = v; return this;} private int excerpt_bgn = -1;
	public Gfo_msg_data_chkr Excerpt_end_(int v) {excerpt_end = v; return this;} private int excerpt_end = -1;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gfo_msg_data actl = (Gfo_msg_data)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(itm == null, path, "itm", itm.Path_str(), actl.Item().Path_str());
		rv += mgr.Tst_val(excerpt_bgn == -1, path, "excerpt_bgn", excerpt_bgn, actl.Src_bgn());
		rv += mgr.Tst_val(excerpt_end == -1, path, "excerpt_end", excerpt_end, actl.Src_end());
		return rv;
	}
}
