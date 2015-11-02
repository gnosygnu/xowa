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
package gplx.core.security; import gplx.*; import gplx.core.*;
import org.junit.*;
import gplx.core.consoles.*; import gplx.core.ios.*; /*IoStream*/
public class HashAlgo_md5_tst {
	@Test  public void Empty() {
		tst_CalcBase16FromString("", "d41d8cd98f00b204e9800998ecf8427e");
	}
	@Test  public void A() {
		tst_CalcBase16FromString("a", "0cc175b9c0f1b6a831c399e269772661");
	}
	@Test  public void Abc() {
		tst_CalcBase16FromString("abc", "900150983cd24fb0d6963f7d28e17f72");
	}
	@Test  public void A_Za_z0_9() {
		tst_CalcBase16FromString("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "d174ab98d277d9f5a5611c2c9f419d9f");
	}
	//@Test 
	public void A_1Million() {
		tst_CalcBase16FromString(String_.Repeat("a", 1000000), "7707d6ae4e027c70eea2a935c2296f21");
	}
	void tst_CalcBase16FromString(String raw, String expd) {
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, raw);
		String actl = HashAlgo_.Md5.CalcHash(Console_adp_.Noop, stream);
		Tfds.Eq(expd, actl);
	}
	/*
	https://www.cosic.esat.kuleuven.be/nessie/testvectors/hash/md5/Md5-128.unverified.test-vectors
	Set 1, vector#  0:
	message="" (empty String)
	hash=D41D8CD98F00B204E9800998ECF8427E

	Set 1, vector#  1:
	message="a"
	hash=0CC175B9C0F1B6A831C399E269772661

	Set 1, vector#  2:
	message="abc"
	hash=900150983CD24FB0D6963F7D28E17F72

	Set 1, vector#  3:
	message="message digest"
	hash=F96B697D7CB7938D525A2F31AAF161D0

	Set 1, vector#  4:
	message="abcdefghijklmnopqrstuvwxyz"
	hash=C3FCD3D76192E4007DFB496CCA67E13B

	Set 1, vector#  5:
	message="abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"
	hash=8215EF0796A20BCAAAE116D3876C664A

	Set 1, vector#  6:
	message="A...Za...z0...9"
	hash=D174AB98D277D9F5A5611C2C9F419D9F

	Set 1, vector#  7:
	message=8 times "1234567890"
	hash=57EDF4A22BE3C955AC49DA2E2107B67A

	Set 1, vector#  8:
	message=1 million times "a"
	hash=7707D6AE4E027C70EEA2A935C2296F21
	*/
}
