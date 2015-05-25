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
package gplx.security; import gplx.*;
import org.junit.*;
import gplx.ios.*; /*IoStream*/
public class HashAlgo_sha1_tst {
	@Test  public void Empty() {
		tst_CalcBase16FromString("", "da39a3ee5e6b4b0d3255bfef95601890afd80709");
	}
	@Test  public void A() {
		tst_CalcBase16FromString("a", "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8");
	}
	@Test  public void Abc() {
		tst_CalcBase16FromString("abc", "a9993e364706816aba3e25717850c26c9cd0d89d");
	}
	@Test  public void A_Za_z0_9() {
		tst_CalcBase16FromString("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "761c457bf73b14d27e9e9265c46f4b4dda11f940");
	}
	//@Test 
	public void A_1Million() {
		tst_CalcBase16FromString(String_.Repeat("a", 1000000), "34aa973cd4c4daa4f61eeb2bdbad27316534016f");
	}
	void tst_CalcBase16FromString(String raw, String expd) {
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, raw);
		String actl = HashAlgo_.Sha1.CalcHash(ConsoleDlg_.Null, stream);
		Tfds.Eq(expd, actl);
	}
	/*
	https://www.cosic.esat.kuleuven.be/nessie/testvectors/
	Set 1, vector#  0:
	message="" (empty String)
	hash=DA39A3EE5E6B4B0D3255BFEF95601890AFD80709

	Set 1, vector#  1:
	message="a"
	hash=86F7E437FAA5A7FCE15D1DDCB9EAEAEA377667B8

	Set 1, vector#  2:
	message="abc"
	hash=A9993E364706816ABA3E25717850C26C9CD0D89D

	Set 1, vector#  3:
	message="message digest"
	hash=C12252CEDA8BE8994D5FA0290A47231C1D16AAE3

	Set 1, vector#  4:
	message="abcdefghijklmnopqrstuvwxyz"
	hash=32D10C7B8CF96570CA04CE37F2A19D84240D3A89

	Set 1, vector#  5:
	message="abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"
	hash=84983E441C3BD26EBAAE4AA1F95129E5E54670F1

	Set 1, vector#  6:
	message="A...Za...z0...9"
	hash=761C457BF73B14D27E9E9265C46F4B4DDA11F940

	Set 1, vector#  7:
	message=8 times "1234567890"
	hash=50ABF5706A150990A08B2C5EA40FA0E585554732

	Set 1, vector#  8:
	message=1 million times "a"
	hash=34AA973CD4C4DAA4F61EEB2BDBAD27316534016F
	*/
}
