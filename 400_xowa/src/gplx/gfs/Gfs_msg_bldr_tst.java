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
package gplx.gfs; import gplx.*;
import org.junit.*;
public class Gfs_msg_bldr_tst {
	@Before public void init() {fxt.Clear();} Gfs_msg_bldr_fxt fxt = new Gfs_msg_bldr_fxt();
	@Test  public void Basic() {
		fxt.Test_build("a;", fxt.msg_("a"));
	}
	@Test  public void Dot() {
		fxt.Test_build("a.b.c;"
		, fxt.msg_("a").Subs_
		(	fxt.msg_("b").Subs_
		(		fxt.msg_("c")
		)));
	}
	@Test  public void Args() {
		fxt.Test_build("a('b', 'c');", fxt.msg_("a", fxt.kv_("", "b"), fxt.kv_("", "c")));
	}
	@Test  public void Args_num() {
		fxt.Test_build("a(1);", fxt.msg_("a", fxt.kv_("", "1")));
	}
	@Test   public void Assign() {
		fxt.Test_build("a = 'b';", fxt.msg_("a_", fxt.kv_("", "b")));
	}
	@Test   public void Assign_num() {
		fxt.Test_build("a = 1;", fxt.msg_("a_", fxt.kv_("", "1")));
	}
}
class Gfs_msg_bldr_fxt {
	public void Clear() {} String_bldr sb = String_bldr_.new_(); Gfs_msg_bldr msg_bldr = Gfs_msg_bldr._;
	public KeyVal kv_(String key, String val) {return KeyVal_.new_(key, val);}
	public GfoMsg msg_(String key, KeyVal... args) {
		GfoMsg rv = GfoMsg_.new_parse_(key);
		int len = args.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = args[i];
			rv.Add(kv.Key(), kv.Val());
		}
		return rv;
	}
	public void Test_build(String raw, GfoMsg... expd) {
		GfoMsg root = msg_bldr.Bld(raw);		
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(Xto_ary(root)));
	}
	GfoMsg[] Xto_ary(GfoMsg msg) {
		int len = msg.Subs_count();
		GfoMsg[] rv = new GfoMsg[len];
		for (int i = 0; i < len; i++)
			rv[i] = msg.Subs_getAt(i);
		return rv;
	}
	String Xto_str(GfoMsg[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add_char_crlf();
			sb.Add(ary[i].XtoStr());
		}
		return sb.Xto_str_and_clear();
	}
}
