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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import gplx.core.brys.*; import gplx.core.btries.*;
abstract class Dbmeta_fld_wkr__base {
	private byte[] hook;
	private final    Btrie_slim_mgr words_trie = Btrie_slim_mgr.ci_a7();
	private int words_len;
	@gplx.Virtual public int Tid() {return Tid_other;}
	public void Ctor(byte[] hook, byte[]... words_ary) {
		this.hook = hook;
		this.words_len = words_ary.length;
		for (byte i = 0; i < words_len; ++i)
			words_trie.Add_bry_byte(words_ary[i], i);
	}
	public void Reg(Btrie_slim_mgr trie) {
		trie.Add_obj(hook, this);
	}
	@gplx.Virtual public void Match(Bry_rdr rdr, Dbmeta_fld_itm fld) {
		int words_len = words_trie.Count();
		for (int i = 0; i < words_len; ++i) {
			rdr.Skip_ws();
			rdr.Chk(words_trie);
		}
		When_match(fld);
	}
	protected abstract void When_match(Dbmeta_fld_itm fld);
	public static final int Tid_end_comma = 1, Tid_end_paren = 2, Tid_other = 3;
}
class Dbmeta_fld_wkr__end_comma extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__end_comma() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_comma;}
	@Override protected void When_match(Dbmeta_fld_itm fld) {}
	private static final    byte[] Hook = Bry_.new_a7(",");
        public static final    Dbmeta_fld_wkr__end_comma Instance = new Dbmeta_fld_wkr__end_comma();
}
class Dbmeta_fld_wkr__end_paren extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__end_paren() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_paren;}
	@Override protected void When_match(Dbmeta_fld_itm fld) {}
	private static final    byte[] Hook = Bry_.new_a7(")");
        public static final    Dbmeta_fld_wkr__end_paren Instance = new Dbmeta_fld_wkr__end_paren();
}
class Dbmeta_fld_wkr__nullable_null extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__nullable_null() {this.Ctor(Hook);}
	@Override protected void When_match(Dbmeta_fld_itm fld) {
		fld.Nullable_tid_(Dbmeta_fld_itm.Nullable_null);
	}
	private static final    byte[] Hook = Bry_.new_a7("null");
        public static final    Dbmeta_fld_wkr__nullable_null Instance = new Dbmeta_fld_wkr__nullable_null();
}
class Dbmeta_fld_wkr__nullable_not extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__nullable_not() {this.Ctor(Hook, Bry_null);}
	@Override protected void When_match(Dbmeta_fld_itm fld) {
		fld.Nullable_tid_(Dbmeta_fld_itm.Nullable_not_null);
	}
	private static final    byte[] Hook = Bry_.new_a7("not"), Bry_null = Bry_.new_a7("null");
        public static final    Dbmeta_fld_wkr__nullable_not Instance = new Dbmeta_fld_wkr__nullable_not();
}
class Dbmeta_fld_wkr__primary_key extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__primary_key() {this.Ctor(Hook, Bry_key);}
	@Override protected void When_match(Dbmeta_fld_itm fld) {
		fld.Primary_y_();
	}
	private static final    byte[] Hook = Bry_.new_a7("primary"), Bry_key = Bry_.new_a7("key");
        public static final    Dbmeta_fld_wkr__primary_key Instance = new Dbmeta_fld_wkr__primary_key();
}
class Dbmeta_fld_wkr__autonumber extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__autonumber() {this.Ctor(Hook);}
	@Override protected void When_match(Dbmeta_fld_itm fld) {
		fld.Autonum_y_();
	}
	private static final    byte[] Hook = Bry_.new_a7("autoincrement");
        public static final    Dbmeta_fld_wkr__autonumber Instance = new Dbmeta_fld_wkr__autonumber();
}
class Dbmeta_fld_wkr__default extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__default() {this.Ctor(Hook);}
	@Override public void Match(Bry_rdr rdr, Dbmeta_fld_itm fld) {
		Object default_val = null;
		rdr.Skip_ws();
		byte[] src = rdr.Src();
		switch (fld.Type().Tid_ansi()) {
			case Dbmeta_fld_tid.Tid__bool:
			case Dbmeta_fld_tid.Tid__byte:
			case Dbmeta_fld_tid.Tid__short:
			case Dbmeta_fld_tid.Tid__int:		default_val = Int_.parse(Read_str_to_end_of_num(rdr)); break;
			case Dbmeta_fld_tid.Tid__long:		default_val = Long_.parse(Read_str_to_end_of_num(rdr)); break;
			case Dbmeta_fld_tid.Tid__float:		default_val = Float_.parse(Read_str_to_end_of_num(rdr)); break;
			case Dbmeta_fld_tid.Tid__double:	default_val = Double_.parse(Read_str_to_end_of_num(rdr)); break;
			case Dbmeta_fld_tid.Tid__decimal:	default_val = Decimal_adp_.parse(Read_str_to_end_of_num(rdr)); break;
			case Dbmeta_fld_tid.Tid__str:
			case Dbmeta_fld_tid.Tid__text:
			case Dbmeta_fld_tid.Tid__bry:
				byte b = src[rdr.Pos()];
				int bgn_pos = rdr.Pos() + 1;
				int end_pos = Bry_find_.Find_fwd(src, b, bgn_pos); if (end_pos == Bry_find_.Not_found) rdr.Err_wkr().Fail("unclosed quote");
				default_val = Bry_.Mid(src, bgn_pos, end_pos);
				rdr.Move_to(end_pos + 1);
				break;
			case Dbmeta_fld_tid.Tid__date:		throw Err_.new_unhandled_default(fld.Type().Tid_ansi());
		}
		fld.Default_(default_val);
	}
	public String Read_str_to_end_of_num(Bry_rdr rdr) {
		int bgn = rdr.Pos();
		int pos = bgn, end = bgn;
		int src_end = rdr.Src_end();
		byte[] src = rdr.Src();
		boolean loop = true;
		while (loop) {
			if (pos >= src_end) {
				end = src_end;
				break;
			}
			byte b = src[pos]; ++pos;
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
				case Byte_ascii.Paren_end:
				case Byte_ascii.Comma:
					end = pos - 1;
					loop = false;
					break;
				default:
					break;
			}
		}
		rdr.Move_to(end);
		return String_.new_a7(src, bgn, end);
	}
	@Override protected void When_match(Dbmeta_fld_itm fld) {}
	private static final    byte[] Hook = Bry_.new_a7("default");
        public static final    Dbmeta_fld_wkr__default Instance = new Dbmeta_fld_wkr__default();
}
