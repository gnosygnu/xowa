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
package gplx.core.security; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Hash_algo__sha1__tst {	// REF: https://www.cosic.esat.kuleuven.be/nessie/testvectors/
	private final    Hash_algo__fxt fxt = new Hash_algo__fxt(Hash_algo_.New__sha1());
	@Test  public void Empty()				{fxt.Test__hash("da39a3ee5e6b4b0d3255bfef95601890afd80709", "");}
	@Test  public void a()					{fxt.Test__hash("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8", "a");}
	@Test  public void abc()				{fxt.Test__hash("a9993e364706816aba3e25717850c26c9cd0d89d", "abc");}
	@Test  public void message_digest()		{fxt.Test__hash("c12252ceda8be8994d5fa0290a47231c1d16aae3", "message digest");}
	@Test  public void a_z()				{fxt.Test__hash("32d10c7b8cf96570ca04ce37f2a19d84240d3a89", "abcdefghijklmnopqrstuvwxyz");}
	@Test  public void a_q__mixed()			{fxt.Test__hash("84983e441c3bd26ebaae4aa1f95129e5e54670f1", "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");}
	@Test  public void A_Z__a_z__0_9()		{fxt.Test__hash("761c457bf73b14d27e9e9265c46f4b4dda11f940", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");}
	// @Test 
	public void Num()						{fxt.Test__hash("50abf5706a150990a08b2c5ea40fa0e585554732", String_.Repeat("1234567890", 8));}
	//@Test 
	public void A_1Million()				{fxt.Test__hash("34aa973cd4c4daa4f61eeb2bdbad27316534016f", String_.Repeat("a", 1000000));}
}
