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
public class Hash_adp_bry_tst {		
	@Before public void setup() {fxt.Clear();} private Hash_adp_bry_fxt fxt = new Hash_adp_bry_fxt();
	@Test   public void Add_bry() {
		fxt	.New_cs()
			.Add("a0").Add("b0").Add("c0")
			.Get_bry_tst("a0").Get_bry_tst("b0").Get_bry_tst("c0").Get_bry_tst("A0", null)
			;
	}
	@Test   public void Get_mid() {
		fxt	.New_cs()
			.Add("a0").Add("b0").Add("c0")
			.Get_mid_tst("xyza0xyz", 3, 5, "a0")
			.Get_mid_tst("xyza0xyz", 3, 4, null)
			;
	}
	@Test   public void Case_insensitive() {
		fxt	.New_ci()
			.Add("a0").Add("B0").Add("c0")
			.Get_bry_tst("a0", "a0")
			.Get_bry_tst("A0", "a0")
			.Get_bry_tst("b0", "B0")
			.Get_bry_tst("B0", "B0")
			.Get_mid_tst("xyza0xyz", 3, 5, "a0")
			.Get_mid_tst("xyzA0xyz", 3, 5, "a0")
			.Count_tst(3)
			;
	}
}
class Hash_adp_bry_fxt {
	Hash_adp_bry hash; 
	public void Clear() {}
	public Hash_adp_bry_fxt New_cs() {hash = Hash_adp_bry.cs_(); return this;}
	public Hash_adp_bry_fxt New_ci() {hash = Hash_adp_bry.ci_ascii_(); return this;}
	public Hash_adp_bry_fxt Add(String key) {byte[] key_bry = Bry_.new_u8(key); hash.Add(key_bry, key_bry); return this;}
	public Hash_adp_bry_fxt Count_tst(int expd) {Tfds.Eq(expd, hash.Count()); return this;}
	public Hash_adp_bry_fxt Get_bry_tst(String key) {return Get_bry_tst(key, key);}
	public Hash_adp_bry_fxt Get_bry_tst(String key, String expd) {
		byte[] key_bry = Bry_.new_u8(key); 
		byte[] actl_bry = (byte[])hash.Get_by_bry(key_bry);
		Tfds.Eq(expd, String_.new_u8(actl_bry));
		return this;
	}
	public Hash_adp_bry_fxt Get_mid_tst(String key, int bgn, int end, String expd) {
		byte[] key_bry = Bry_.new_u8(key); 
		byte[] actl_bry = (byte[])hash.Get_by_mid(key_bry, bgn, end);
		Tfds.Eq(expd, String_.new_u8(actl_bry));
		return this;
	}
}
