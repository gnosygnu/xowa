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
package gplx.dbs.metas.parsers;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldType;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.rdrs.BryRdr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.errs.ErrUtl;
abstract class Dbmeta_fld_wkr__base {
	private byte[] hook;
	private final Btrie_slim_mgr words_trie = Btrie_slim_mgr.ci_a7();
	private int words_len;
	public int Tid() {return Tid_other;}
	public void Ctor(byte[] hook, byte[]... words_ary) {
		this.hook = hook;
		this.words_len = words_ary.length;
		for (byte i = 0; i < words_len; ++i)
			words_trie.Add_bry_byte(words_ary[i], i);
	}
	public void Reg(Btrie_slim_mgr trie) {
		trie.AddObj(hook, this);
	}
	public void Match(BryRdr rdr, DbmetaFldItm fld) {
		int words_len = words_trie.Count();
		for (int i = 0; i < words_len; ++i) {
			rdr.SkipWs();
			rdr.Chk(words_trie);
		}
		When_match(fld);
	}
	protected abstract void When_match(DbmetaFldItm fld);
	public static final int Tid_end_comma = 1, Tid_end_paren = 2, Tid_other = 3;
}
class Dbmeta_fld_wkr__end_comma extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__end_comma() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_comma;}
	@Override protected void When_match(DbmetaFldItm fld) {}
	private static final byte[] Hook = BryUtl.NewA7(",");
        public static final Dbmeta_fld_wkr__end_comma Instance = new Dbmeta_fld_wkr__end_comma();
}
class Dbmeta_fld_wkr__end_paren extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__end_paren() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_paren;}
	@Override protected void When_match(DbmetaFldItm fld) {}
	private static final byte[] Hook = BryUtl.NewA7(")");
        public static final Dbmeta_fld_wkr__end_paren Instance = new Dbmeta_fld_wkr__end_paren();
}
class Dbmeta_fld_wkr__nullable_null extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__nullable_null() {this.Ctor(Hook);}
	@Override protected void When_match(DbmetaFldItm fld) {
		fld.NullableSet(DbmetaFldItm.NullableNull);
	}
	private static final byte[] Hook = BryUtl.NewA7("null");
        public static final Dbmeta_fld_wkr__nullable_null Instance = new Dbmeta_fld_wkr__nullable_null();
}
class Dbmeta_fld_wkr__nullable_not extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__nullable_not() {this.Ctor(Hook, Bry_null);}
	@Override protected void When_match(DbmetaFldItm fld) {
		fld.NullableSet(DbmetaFldItm.NullableNotNull);
	}
	private static final byte[] Hook = BryUtl.NewA7("not"), Bry_null = BryUtl.NewA7("null");
        public static final Dbmeta_fld_wkr__nullable_not Instance = new Dbmeta_fld_wkr__nullable_not();
}
class Dbmeta_fld_wkr__primary_key extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__primary_key() {this.Ctor(Hook, Bry_key);}
	@Override protected void When_match(DbmetaFldItm fld) {
		fld.PrimarySetY();
	}
	private static final byte[] Hook = BryUtl.NewA7("primary"), Bry_key = BryUtl.NewA7("key");
        public static final Dbmeta_fld_wkr__primary_key Instance = new Dbmeta_fld_wkr__primary_key();
}
class Dbmeta_fld_wkr__autonumber extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__autonumber() {this.Ctor(Hook);}
	@Override protected void When_match(DbmetaFldItm fld) {
		fld.AutonumSetY();
	}
	private static final byte[] Hook = BryUtl.NewA7("autoincrement");
        public static final Dbmeta_fld_wkr__autonumber Instance = new Dbmeta_fld_wkr__autonumber();
}
class Dbmeta_fld_wkr__default extends Dbmeta_fld_wkr__base {
	public Dbmeta_fld_wkr__default() {this.Ctor(Hook);}
	@Override public void Match(BryRdr rdr, DbmetaFldItm fld) {
		Object default_val = null;
		rdr.SkipWs();
		byte[] src = rdr.Src();
		switch (fld.Type().Tid()) {
			case DbmetaFldType.TidBool:
			case DbmetaFldType.TidByte:
			case DbmetaFldType.TidShort:
			case DbmetaFldType.TidInt:		default_val = IntUtl.Parse(Read_str_to_end_of_num(rdr)); break;
			case DbmetaFldType.TidLong:		default_val = LongUtl.Parse(Read_str_to_end_of_num(rdr)); break;
			case DbmetaFldType.TidFloat:		default_val = FloatUtl.Parse(Read_str_to_end_of_num(rdr)); break;
			case DbmetaFldType.TidDouble:	default_val = DoubleUtl.Parse(Read_str_to_end_of_num(rdr)); break;
			case DbmetaFldType.TidDecimal:	default_val = GfoDecimalUtl.Parse(Read_str_to_end_of_num(rdr)); break;
			case DbmetaFldType.TidStr:
			case DbmetaFldType.TidText:
			case DbmetaFldType.TidBry:
				byte b = src[rdr.Pos()];
				int bgn_pos = rdr.Pos() + 1;
				int end_pos = BryFind.FindFwd(src, b, bgn_pos); if (end_pos == BryFind.NotFound) rdr.ErrWkr().Fail("unclosed quote");
				default_val = BryLni.Mid(src, bgn_pos, end_pos);
				rdr.MoveTo(end_pos + 1);
				break;
			case DbmetaFldType.TidDate:		throw ErrUtl.NewUnhandled(fld.Type().Tid());
		}
		fld.DefaultValSet(default_val);
	}
	public String Read_str_to_end_of_num(BryRdr rdr) {
		int bgn = rdr.Pos();
		int pos = bgn, end = bgn;
		int src_end = rdr.SrcEnd();
		byte[] src = rdr.Src();
		boolean loop = true;
		while (loop) {
			if (pos >= src_end) {
				end = src_end;
				break;
			}
			byte b = src[pos]; ++pos;
			switch (b) {
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:
				case AsciiByte.ParenEnd:
				case AsciiByte.Comma:
					end = pos - 1;
					loop = false;
					break;
				default:
					break;
			}
		}
		rdr.MoveTo(end);
		return StringUtl.NewA7(src, bgn, end);
	}
	@Override protected void When_match(DbmetaFldItm fld) {}
	private static final byte[] Hook = BryUtl.NewA7("default");
        public static final Dbmeta_fld_wkr__default Instance = new Dbmeta_fld_wkr__default();
}
