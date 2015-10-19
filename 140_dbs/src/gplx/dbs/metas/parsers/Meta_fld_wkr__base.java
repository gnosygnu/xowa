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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import gplx.core.brys.*; import gplx.core.btries.*;
abstract class Meta_fld_wkr__base {
	private byte[] hook;
	private byte[][] words_ary; private int words_len;
	@gplx.Virtual public int Tid() {return Tid_other;}
	public void Ctor(byte[] hook, byte[]... words_ary) {
		this.hook = hook;
		this.words_ary = words_ary;
		this.words_len = words_ary.length;
	}
	public void Reg(Btrie_slim_mgr trie) {
		trie.Add_obj(hook, this);
	}
	@gplx.Virtual public void Match(Bry_rdr rdr, Meta_fld_itm fld) {
		for (int i = 0; i < words_len; ++i) {
			rdr.Skip_ws();
			byte[] word = words_ary[i];
			rdr.Chk_bry_or_fail(word);
		}
		When_match(fld);
	}
	protected abstract void When_match(Meta_fld_itm fld);
	public static final int Tid_end_comma = 1, Tid_end_paren = 2, Tid_other = 3;
}
class Meta_fld_wkr__end_comma extends Meta_fld_wkr__base {
	public Meta_fld_wkr__end_comma() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_comma;}
	@Override protected void When_match(Meta_fld_itm fld) {}
	private static final byte[] Hook = Bry_.new_a7(",");
        public static final Meta_fld_wkr__end_comma Instance = new Meta_fld_wkr__end_comma();
}
class Meta_fld_wkr__end_paren extends Meta_fld_wkr__base {
	public Meta_fld_wkr__end_paren() {this.Ctor(Hook);}
	@Override public int Tid() {return Tid_end_paren;}
	@Override protected void When_match(Meta_fld_itm fld) {}
	private static final byte[] Hook = Bry_.new_a7(")");
        public static final Meta_fld_wkr__end_paren Instance = new Meta_fld_wkr__end_paren();
}
class Meta_fld_wkr__nullable_null extends Meta_fld_wkr__base {
	public Meta_fld_wkr__nullable_null() {this.Ctor(Hook);}
	@Override protected void When_match(Meta_fld_itm fld) {
		fld.Nullable_tid_(Meta_fld_itm.Nullable_null);
	}
	private static final byte[] Hook = Bry_.new_a7("null");
        public static final Meta_fld_wkr__nullable_null Instance = new Meta_fld_wkr__nullable_null();
}
class Meta_fld_wkr__nullable_not extends Meta_fld_wkr__base {
	public Meta_fld_wkr__nullable_not() {this.Ctor(Hook, Bry_null);}
	@Override protected void When_match(Meta_fld_itm fld) {
		fld.Nullable_tid_(Meta_fld_itm.Nullable_not_null);
	}
	private static final byte[] Hook = Bry_.new_a7("not"), Bry_null = Bry_.new_a7("null");
        public static final Meta_fld_wkr__nullable_not Instance = new Meta_fld_wkr__nullable_not();
}
class Meta_fld_wkr__primary_key extends Meta_fld_wkr__base {
	public Meta_fld_wkr__primary_key() {this.Ctor(Hook, Bry_key);}
	@Override protected void When_match(Meta_fld_itm fld) {
		fld.Primary_key_y_();
	}
	private static final byte[] Hook = Bry_.new_a7("primary"), Bry_key = Bry_.new_a7("key");
        public static final Meta_fld_wkr__primary_key Instance = new Meta_fld_wkr__primary_key();
}
class Meta_fld_wkr__autonumber extends Meta_fld_wkr__base {
	public Meta_fld_wkr__autonumber() {this.Ctor(Hook);}
	@Override protected void When_match(Meta_fld_itm fld) {
		fld.Autonumber_y_();
	}
	private static final byte[] Hook = Bry_.new_a7("autoincrement");
        public static final Meta_fld_wkr__autonumber Instance = new Meta_fld_wkr__autonumber();
}
class Meta_fld_wkr__default extends Meta_fld_wkr__base {
	public Meta_fld_wkr__default() {this.Ctor(Hook);}
	@Override public void Match(Bry_rdr rdr, Meta_fld_itm fld) {
		Object default_val = null;
		rdr.Skip_ws();
		byte[] src = rdr.Src();
		byte b = src[rdr.Pos()];
		switch (b) {
			case Byte_ascii.Quote:
			case Byte_ascii.Apos:
				int bgn_pos = rdr.Pos() + 1;
				int end_pos = Bry_find_.Find_fwd(src, b, bgn_pos); if (end_pos == Bry_find_.Not_found) throw Err_.new_wo_type("unclosed quote", "snip", rdr.Mid_by_len_safe(40));
				default_val = Bry_.Mid(src, bgn_pos, end_pos);
				rdr.Pos_(end_pos + 1);
				break;
			case Byte_ascii.Dash:
			case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
			case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				default_val = rdr.Read_int_to_non_num();
				break;
			default:
				throw Err_.new_wo_type("invalid field_default", "snip", rdr.Mid_by_len_safe(40));
		}
		fld.Default_val_(default_val);
	}
	@Override protected void When_match(Meta_fld_itm fld) {}
	private static final byte[] Hook = Bry_.new_a7("default");
        public static final Meta_fld_wkr__default Instance = new Meta_fld_wkr__default();
}
