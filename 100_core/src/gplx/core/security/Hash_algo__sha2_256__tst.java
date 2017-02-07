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
public class Hash_algo__sha2_256__tst {	// REF: https://www.cosic.esat.kuleuven.be/nessie/testvectors/
	private final    Hash_algo__fxt fxt = new Hash_algo__fxt(Hash_algo_.New__sha2_256());
	@Test  public void Empty()				{fxt.Test__hash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "");}
	@Test  public void a()					{fxt.Test__hash("ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb", "a");}
	@Test  public void abc()				{fxt.Test__hash("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "abc");}
	@Test  public void message_digest()		{fxt.Test__hash("f7846f55cf23e14eebeab5b4e1550cad5b509e3348fbc4efa3a1413d393cb650", "message digest");}
	@Test  public void a_z()				{fxt.Test__hash("71c480df93d6ae2f1efad1447c66c9525e316218cf51fc8d9ed832f2daf18b73", "abcdefghijklmnopqrstuvwxyz");}
	@Test  public void a_q__mixed()			{fxt.Test__hash("248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1", "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");}
	@Test  public void A_Z__a_z__0_9()		{fxt.Test__hash("db4bfcbd4da0cd85a60c3c37d3fbd8805c77f15fc6b1fdfe614ee0a7c8fdb4c0", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");}
	// @Test 
	public void Num()						{fxt.Test__hash("f371bc4a311f2b009eef952dd83ca80e2b60026c8e935592d0f9c308453c813e", String_.Repeat("1234567890", 8));}
	//@Test 
	public void A_1Million()				{fxt.Test__hash("cdc76e5c9914fb9281a1c7e284d73e67f1809a48a497200e046d39ccc7112cd0", String_.Repeat("a", 1000000));}
}
