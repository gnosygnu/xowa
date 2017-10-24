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
public class Hash_algo__md5__tst {	// REF: https://www.cosic.esat.kuleuven.be/nessie/testvectors/hash/md5/Md5-128.unverified.test-vectors
	private final    Hash_algo__fxt fxt = new Hash_algo__fxt(Hash_algo_.New__md5());
	@Test  public void Empty()				{fxt.Test__hash("d41d8cd98f00b204e9800998ecf8427e", "");}
	@Test  public void a()					{fxt.Test__hash("0cc175b9c0f1b6a831c399e269772661", "a");}
	@Test  public void abc()				{fxt.Test__hash("900150983cd24fb0d6963f7d28e17f72", "abc");}
	@Test  public void message_digest()		{fxt.Test__hash("f96b697d7cb7938d525a2f31aaf161d0", "message digest");}
	@Test  public void a_z()				{fxt.Test__hash("c3fcd3d76192e4007dfb496cca67e13b", "abcdefghijklmnopqrstuvwxyz");}
	@Test  public void a_q__mixed()			{fxt.Test__hash("8215ef0796a20bcaaae116d3876c664a", "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");}
	@Test  public void A_Z__a_z__0_9()		{fxt.Test__hash("d174ab98d277d9f5a5611c2c9f419d9f", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");}
	//@Test  
	public void Num__x_8()					{fxt.Test__hash("57edf4a22be3c955ac49da2e2107b67a", String_.Repeat("1234567890", 8));}
	//@Test  
	public void A__x_1million()				{fxt.Test__hash("7707d6ae4e027c70eea2a935c2296f21", String_.Repeat("a", 1000000));}
}
class Hash_algo__fxt {
	private final    Hash_algo algo;
	public Hash_algo__fxt(Hash_algo algo) {this.algo = algo;}
	public void Test__hash(String expd, String raw) {
		Tfds.Eq(expd, algo.Hash_bry_as_str(Bry_.new_u8(raw)));
		Tfds.Eq(expd, algo.Hash_stream_as_str(gplx.core.consoles.Console_adp_.Noop, gplx.core.ios.streams.IoStream_.mem_txt_(Io_url_.Empty, raw)));
	}
}
