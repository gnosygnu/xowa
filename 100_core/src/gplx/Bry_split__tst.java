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
import org.junit.*;
public class Bry_split__tst {
	private final    Bry_split__fxt fxt = new Bry_split__fxt();
	@Test   public void Split() {
		fxt.Test_split("a"				, Byte_ascii.Pipe, Bool_.N, "a");					// no trim
		fxt.Test_split("a|"				, Byte_ascii.Pipe, Bool_.N, "a");
		fxt.Test_split("|a"				, Byte_ascii.Pipe, Bool_.N, "", "a");
		fxt.Test_split("|"				, Byte_ascii.Pipe, Bool_.N, "");
		fxt.Test_split(""				, Byte_ascii.Pipe, Bool_.N);
		fxt.Test_split("a|b|c"			, Byte_ascii.Pipe, Bool_.N, "a", "b", "c");
		fxt.Test_split(" a "			, Byte_ascii.Pipe, Bool_.Y, "a");					// trim
		fxt.Test_split(" a |"			, Byte_ascii.Pipe, Bool_.Y, "a");
		fxt.Test_split("| a "			, Byte_ascii.Pipe, Bool_.Y, "", "a");
		fxt.Test_split(" | "			, Byte_ascii.Pipe, Bool_.Y, "");
		fxt.Test_split(" "				, Byte_ascii.Pipe, Bool_.Y);
		fxt.Test_split(" a | b | c "	, Byte_ascii.Pipe, Bool_.Y, "a", "b", "c");
		fxt.Test_split(" a b | c d "	, Byte_ascii.Pipe, Bool_.Y, "a b", "c d");
		fxt.Test_split(" a \n b "		, Byte_ascii.Nl  , Bool_.N, " a ", " b ");			// ws as dlm
		fxt.Test_split(" a \n b "		, Byte_ascii.Nl  , Bool_.Y, "a", "b");				// ws as dlm; trim
		fxt.Test_split("a|extend|b"		, Byte_ascii.Pipe, Bool_.Y, "a", "extend|b");		// extend
		fxt.Test_split("extend|a"		, Byte_ascii.Pipe, Bool_.Y, "extend|a");			// extend
		fxt.Test_split("a|cancel|b"		, Byte_ascii.Pipe, Bool_.Y, "a");					// cancel
	}
	@Test   public void Split__bry() {
		fxt.Test_split("a|b|c|d"		, 2, 6, "|", "b", "c");
		fxt.Test_split("a|b|c|d"		, 2, 4, "|", "b");
	}
}
class Bry_split__fxt {
	private final    Bry_split_wkr__example wkr = new Bry_split_wkr__example();
	public void Test_split(String raw_str, byte dlm, boolean trim, String... expd) {
		byte[] src = Bry_.new_a7(raw_str);
		Bry_split_.Split(src, 0, src.length, dlm, trim, wkr);
		byte[][] actl_ary = wkr.To_ary();
		Tfds.Eq_ary_str(expd, String_.Ary(actl_ary));
	}
	public void Test_split(String src, int src_bgn, int src_end, String dlm, String... expd) {
		Tfds.Eq_ary_str(Bry_.Ary(expd), Bry_split_.Split(Bry_.new_u8(src), src_bgn, src_end, Bry_.new_u8(dlm)));
	}
}
class Bry_split_wkr__example implements gplx.core.brys.Bry_split_wkr {
	private final    List_adp list = List_adp_.New();
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] bry = itm_end == itm_bgn ? Bry_.Empty : Bry_.Mid(src, itm_bgn, itm_end);
		if		(Bry_.Eq(bry, Bry_.new_a7("extend"))) return Bry_split_.Rv__extend;
		else if (Bry_.Eq(bry, Bry_.new_a7("cancel"))) return Bry_split_.Rv__cancel;
		list.Add(bry);
		return Bry_split_.Rv__ok;
	}
	public byte[][] To_ary() {
		return (byte[][])list.To_ary_and_clear(byte[].class);
	}
}
