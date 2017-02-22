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
	public Hash_adp_bry_fxt New_cs() {hash = Hash_adp_bry.cs(); return this;}
	public Hash_adp_bry_fxt New_ci() {hash = Hash_adp_bry.ci_a7(); return this;}
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
