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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Scrib_lib_hash_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_hash().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void ListAlgorithms() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_hash.Invk_listAlgorithms, Object_.Ary_empty, String_.Concat_lines_nl
			( "1="
			, "  1=md2"
			, "  2=md4"
			, "  3=md5"
			, "  4=sha1"
			, "  5=sha224"
			, "  6=sha256"
			, "  7=sha384"
			, "  8=sha-512/224"
			, "  9=sha-512/256"
			, "  10=sha512"
			, "  11=sha3-224"
			, "  12=sha3-256"
			, "  13=sha3-384"
			, "  14=sha3-512"
			, "  15=ripemd128"
			, "  16=ripemd160"
			, "  17=ripemd256"
			, "  18=ripemd320"
			, "  19=whirlpool"
			, "  20=tiger-128"
			, "  21=tiger-160"
			, "  22=tiger-192"
			, "  23=gost"
			, "  24=adler32"
			, "  25=crc32"
			, "  26=fnv132"
			, "  27=fnv164"
			, "  28=fnv1a32"
			, "  29=fnv1a64"
			, "  30=haval128,3"
			, "  31=haval160,3"
			, "  32=haval192,3"
			, "  33=haval224,3"
			, "  34=haval256,3"
			, "  35=haval128,4"
			, "  36=haval160,4"
			, "  37=haval192,4"
			, "  38=haval224,4"
			, "  39=haval256,4"
			, "  40=haval128,5"
			, "  41=haval160,5"
			, "  42=haval192,5"
			, "  43=haval224,5"
			, "  44=haval256,5"
			)
			);
	}
		// OPENSGX: https://github.com/sslab-gatech/opensgx/blob/master/libsgx/mbedtls/tests/suites/test_suite_mdx.data
	private static final String[] Test_vectors = new String[]
	{ ""
	, "a"
	, "abc"
	, "message digest"
	, "abcdefghijklmnopqrstuvwxyz"
	};
	@Test  public void Md2() {
		String algo_key = "md2";
		Test__HashValue(algo_key, Test_vectors[0], "8350e5a3e24c153df2275c9f80692773");
		Test__HashValue(algo_key, Test_vectors[1], "32ec01ec4a6dac72c0ab96fb34c0b5d1");
		Test__HashValue(algo_key, Test_vectors[2], "da853b0d3f88d99b30283a69e6ded6bb");
		Test__HashValue(algo_key, Test_vectors[3], "ab4f496bfb2a530b219ff33031fe06b0");
		Test__HashValue(algo_key, Test_vectors[4], "4e8ddff3650292ab5a4108c3aa47940b");
	}
	@Test  public void Md4() {
		String algo_key = "md4";
		Test__HashValue(algo_key, Test_vectors[0], "31d6cfe0d16ae931b73c59d7e0c089c0");
		Test__HashValue(algo_key, Test_vectors[1], "bde52cb31de33e46245e05fbdbd6fb24");
		Test__HashValue(algo_key, Test_vectors[2], "a448017aaf21d8525fc10ae87aa6729d");
		Test__HashValue(algo_key, Test_vectors[3], "d9130a8164549fe818874806e1c7014b");
		Test__HashValue(algo_key, Test_vectors[4], "d79e1c308aa5bbcdeea8ed63df412da9");
	}
	@Test  public void Md5() {
		String algo_key = "md5";
		Test__HashValue(algo_key, Test_vectors[0], "d41d8cd98f00b204e9800998ecf8427e");
		Test__HashValue(algo_key, Test_vectors[1], "0cc175b9c0f1b6a831c399e269772661");
		Test__HashValue(algo_key, Test_vectors[2], "900150983cd24fb0d6963f7d28e17f72");
		Test__HashValue(algo_key, Test_vectors[3], "f96b697d7cb7938d525a2f31aaf161d0");
		Test__HashValue(algo_key, Test_vectors[4], "c3fcd3d76192e4007dfb496cca67e13b");
	}
	@Test  public void SHA1() {
		String algo_key = "sha1";
		Test__HashValue(algo_key, Test_vectors[0], "da39a3ee5e6b4b0d3255bfef95601890afd80709");
		Test__HashValue(algo_key, Test_vectors[1], "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8");
		Test__HashValue(algo_key, Test_vectors[2], "a9993e364706816aba3e25717850c26c9cd0d89d");
		Test__HashValue(algo_key, Test_vectors[3], "c12252ceda8be8994d5fa0290a47231c1d16aae3");
		Test__HashValue(algo_key, Test_vectors[4], "32d10c7b8cf96570ca04ce37f2a19d84240d3a89");
	}
	@Test  public void SHA224() {
		String algo_key = "sha224";
		Test__HashValue(algo_key, Test_vectors[0], "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f");
		Test__HashValue(algo_key, Test_vectors[1], "abd37534c7d9a2efb9465de931cd7055ffdb8879563ae98078d6d6d5");
		Test__HashValue(algo_key, Test_vectors[2], "23097d223405d8228642a477bda255b32aadbce4bda0b3f7e36c9da7");
		Test__HashValue(algo_key, Test_vectors[3], "2cb21c83ae2f004de7e81c3c7019cbcb65b71ab656b22d6d0c39b8eb");
		Test__HashValue(algo_key, Test_vectors[4], "45a5f72c39c5cff2522eb3429799e49e5f44b356ef926bcf390dccc2");
	}
	@Test  public void SHA256() {
		String algo_key = "sha256";
		Test__HashValue(algo_key, Test_vectors[0], "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
		Test__HashValue(algo_key, Test_vectors[1], "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb");
		Test__HashValue(algo_key, Test_vectors[2], "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
		Test__HashValue(algo_key, Test_vectors[3], "f7846f55cf23e14eebeab5b4e1550cad5b509e3348fbc4efa3a1413d393cb650");
		Test__HashValue(algo_key, Test_vectors[4], "71c480df93d6ae2f1efad1447c66c9525e316218cf51fc8d9ed832f2daf18b73");
	}
	@Test  public void SHA384() {
		String algo_key = "sha384";
		Test__HashValue(algo_key, Test_vectors[0], "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b");
		Test__HashValue(algo_key, Test_vectors[1], "54a59b9f22b0b80880d8427e548b7c23abd873486e1f035dce9cd697e85175033caa88e6d57bc35efae0b5afd3145f31");
		Test__HashValue(algo_key, Test_vectors[2], "cb00753f45a35e8bb5a03d699ac65007272c32ab0eded1631a8b605a43ff5bed8086072ba1e7cc2358baeca134c825a7");
		Test__HashValue(algo_key, Test_vectors[3], "473ed35167ec1f5d8e550368a3db39be54639f828868e9454c239fc8b52e3c61dbd0d8b4de1390c256dcbb5d5fd99cd5");
		Test__HashValue(algo_key, Test_vectors[4], "feb67349df3db6f5924815d6c3dc133f091809213731fe5c7b5f4999e463479ff2877f5f2936fa63bb43784b12f3ebb4");
	}
	@Test  public void SHA512_224() {
		String algo_key = "sha512/224";
		Test__HashValue(algo_key, Test_vectors[0], "6ed0dd02806fa89e25de060c19d3ac86cabb87d6a0ddd05c333b84f4");
		Test__HashValue(algo_key, Test_vectors[1], "d5cdb9ccc769a5121d4175f2bfdd13d6310e0d3d361ea75d82108327");
		Test__HashValue(algo_key, Test_vectors[2], "4634270f707b6a54daae7530460842e20e37ed265ceee9a43e8924aa");
		Test__HashValue(algo_key, Test_vectors[3], "ad1a4db188fe57064f4f24609d2a83cd0afb9b398eb2fcaeaae2c564");
		Test__HashValue(algo_key, Test_vectors[4], "ff83148aa07ec30655c1b40aff86141c0215fe2a54f767d3f38743d8");
	}
	@Test  public void SHA512_256() {
		String algo_key = "sha512/256";
		Test__HashValue(algo_key, Test_vectors[0], "c672b8d1ef56ed28ab87c3622c5114069bdd3ad7b8f9737498d0c01ecef0967a");
		Test__HashValue(algo_key, Test_vectors[1], "455e518824bc0601f9fb858ff5c37d417d67c2f8e0df2babe4808858aea830f8");
		Test__HashValue(algo_key, Test_vectors[2], "53048e2681941ef99b2e29b76b4c7dabe4c2d0c634fc6d46e0e2f13107e7af23");
		Test__HashValue(algo_key, Test_vectors[3], "0cf471fd17ed69d990daf3433c89b16d63dec1bb9cb42a6094604ee5d7b4e9fb");
		Test__HashValue(algo_key, Test_vectors[4], "fc3189443f9c268f626aea08a756abe7b726b05f701cb08222312ccfd6710a26");
	}
	@Test  public void SHA512() {
		String algo_key = "sha512";
		Test__HashValue(algo_key, Test_vectors[0], "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e");
		Test__HashValue(algo_key, Test_vectors[1], "1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75");
		Test__HashValue(algo_key, Test_vectors[2], "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f");
		Test__HashValue(algo_key, Test_vectors[3], "107dbf389d9e9f71a3a95f6c055b9251bc5268c2be16d6c13492ea45b0199f3309e16455ab1e96118e8a905d5597b72038ddb372a89826046de66687bb420e7c");
		Test__HashValue(algo_key, Test_vectors[4], "4dbff86cc2ca1bae1e16468a05cb9881c97f1753bce3619034898faa1aabe429955a1bf8ec483d7421fe3c1646613a59ed5441fb0f321389f77f48a879c7b1f1");
	}
	@Test  public void SHA3_224() {
		String algo_key = "sha3-224";
		Test__HashValue(algo_key, Test_vectors[0], "6b4e03423667dbb73b6e15454f0eb1abd4597f9a1b078e3f5b5a6bc7");
		Test__HashValue(algo_key, Test_vectors[1], "9e86ff69557ca95f405f081269685b38e3a819b309ee942f482b6a8b");
		Test__HashValue(algo_key, Test_vectors[2], "e642824c3f8cf24ad09234ee7d3c766fc9a3a5168d0c94ad73b46fdf");
		Test__HashValue(algo_key, Test_vectors[3], "18768bb4c48eb7fc88e5ddb17efcf2964abd7798a39d86a4b4a1e4c8");
		Test__HashValue(algo_key, Test_vectors[4], "5cdeca81e123f87cad96b9cba999f16f6d41549608d4e0f4681b8239");
	}
	@Test  public void SHA3_256() {
		String algo_key = "sha3-256";
		Test__HashValue(algo_key, Test_vectors[0], "a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a");
		Test__HashValue(algo_key, Test_vectors[1], "80084bf2fba02475726feb2cab2d8215eab14bc6bdd8bfb2c8151257032ecd8b");
		Test__HashValue(algo_key, Test_vectors[2], "3a985da74fe225b2045c172d6bd390bd855f086e3e9d525b46bfe24511431532");
		Test__HashValue(algo_key, Test_vectors[3], "edcdb2069366e75243860c18c3a11465eca34bce6143d30c8665cefcfd32bffd");
		Test__HashValue(algo_key, Test_vectors[4], "7cab2dc765e21b241dbc1c255ce620b29f527c6d5e7f5f843e56288f0d707521");
	}
	@Test  public void SHA3_384() {
		String algo_key = "sha3-384";
		Test__HashValue(algo_key, Test_vectors[0], "0c63a75b845e4f7d01107d852e4c2485c51a50aaaa94fc61995e71bbee983a2ac3713831264adb47fb6bd1e058d5f004");
		Test__HashValue(algo_key, Test_vectors[1], "1815f774f320491b48569efec794d249eeb59aae46d22bf77dafe25c5edc28d7ea44f93ee1234aa88f61c91912a4ccd9");
		Test__HashValue(algo_key, Test_vectors[2], "ec01498288516fc926459f58e2c6ad8df9b473cb0fc08c2596da7cf0e49be4b298d88cea927ac7f539f1edf228376d25");
		Test__HashValue(algo_key, Test_vectors[3], "d9519709f44af73e2c8e291109a979de3d61dc02bf69def7fbffdfffe662751513f19ad57e17d4b93ba1e484fc1980d5");
		Test__HashValue(algo_key, Test_vectors[4], "fed399d2217aaf4c717ad0c5102c15589e1c990cc2b9a5029056a7f7485888d6ab65db2370077a5cadb53fc9280d278f");
	}
	@Test  public void SHA3_512() {
		String algo_key = "sha3-512";
		Test__HashValue(algo_key, Test_vectors[0], "a69f73cca23a9ac5c8b567dc185a756e97c982164fe25859e0d1dcc1475c80a615b2123af1f5f94c11e3e9402c3ac558f500199d95b6d3e301758586281dcd26");
		Test__HashValue(algo_key, Test_vectors[1], "697f2d856172cb8309d6b8b97dac4de344b549d4dee61edfb4962d8698b7fa803f4f93ff24393586e28b5b957ac3d1d369420ce53332712f997bd336d09ab02a");
		Test__HashValue(algo_key, Test_vectors[2], "b751850b1a57168a5693cd924b6b096e08f621827444f70d884f5d0240d2712e10e116e9192af3c91a7ec57647e3934057340b4cf408d5a56592f8274eec53f0");
		Test__HashValue(algo_key, Test_vectors[3], "3444e155881fa15511f57726c7d7cfe80302a7433067b29d59a71415ca9dd141ac892d310bc4d78128c98fda839d18d7f0556f2fe7acb3c0cda4bff3a25f5f59");
		Test__HashValue(algo_key, Test_vectors[4], "af328d17fa28753a3c9f5cb72e376b90440b96f0289e5703b729324a975ab384eda565fc92aaded143669900d761861687acdc0a5ffa358bd0571aaad80aca68");
	}
	@Test  public void RipeMD128() {
		String algo_key = "ripemd128";
		Test__HashValue(algo_key, Test_vectors[0], "cdf26213a150dc3ecb610f18f6b38b46");
		Test__HashValue(algo_key, Test_vectors[1], "86be7afa339d0fc7cfc785e72f578d33");
		Test__HashValue(algo_key, Test_vectors[2], "c14a12199c66e4ba84636b0f69144c77");
		Test__HashValue(algo_key, Test_vectors[3], "9e327b3d6e523062afc1132d7df9d1b8");
		Test__HashValue(algo_key, Test_vectors[4], "fd2aa607f71dc8f510714922b371834e");
	}
	@Test  public void RipeMD160() {
		String algo_key = "ripemd160";
		Test__HashValue(algo_key, Test_vectors[0], "9c1185a5c5e9fc54612808977ee8f548b2258d31");
		Test__HashValue(algo_key, Test_vectors[1], "0bdc9d2d256b3ee9daae347be6f4dc835a467ffe");
		Test__HashValue(algo_key, Test_vectors[2], "8eb208f7e05d987a9b044a8e98c6b087f15a0bfc");
		Test__HashValue(algo_key, Test_vectors[3], "5d0689ef49d2fae572b881b123a85ffa21595f36");
		Test__HashValue(algo_key, Test_vectors[4], "f71c27109c692c1b56bbdceb5b9d2865b3708dbc");
	}
	@Test  public void RipeMD256() {
		String algo_key = "ripemd256";
		Test__HashValue(algo_key, Test_vectors[0], "02ba4c4e5f8ecd1877fc52d64d30e37a2d9774fb1e5d026380ae0168e3c5522d");
		Test__HashValue(algo_key, Test_vectors[1], "f9333e45d857f5d90a91bab70a1eba0cfb1be4b0783c9acfcd883a9134692925");
		Test__HashValue(algo_key, Test_vectors[2], "afbd6e228b9d8cbbcef5ca2d03e6dba10ac0bc7dcbe4680e1e42d2e975459b65");
		Test__HashValue(algo_key, Test_vectors[3], "87e971759a1ce47a514d5c914c392c9018c7c46bc14465554afcdf54a5070c0e");
		Test__HashValue(algo_key, Test_vectors[4], "649d3034751ea216776bf9a18acc81bc7896118a5197968782dd1fd97d8d5133");
	}
	@Test  public void RipeMD320() {
		String algo_key = "ripemd320";
		Test__HashValue(algo_key, Test_vectors[0], "22d65d5661536cdc75c1fdf5c6de7b41b9f27325ebc61e8557177d705a0ec880151c3a32a00899b8");
		Test__HashValue(algo_key, Test_vectors[1], "ce78850638f92658a5a585097579926dda667a5716562cfcf6fbe77f63542f99b04705d6970dff5d");
		Test__HashValue(algo_key, Test_vectors[2], "de4c01b3054f8930a79d09ae738e92301e5a17085beffdc1b8d116713e74f82fa942d64cdbc4682d");
		Test__HashValue(algo_key, Test_vectors[3], "3a8e28502ed45d422f68844f9dd316e7b98533fa3f2a91d29f84d425c88d6b4eff727df66a7c0197");
		Test__HashValue(algo_key, Test_vectors[4], "cabdb1810b92470a2093aa6bce05952c28348cf43ff60841975166bb40ed234004b8824463e6b009");
	}
	@Test  public void Whirlpool() {
		String algo_key = "whirlpool";
		Test__HashValue(algo_key, Test_vectors[0], "19fa61d75522a4669b44e39c1d2e1726c530232130d407f89afee0964997f7a73e83be698b288febcf88e3e03c4f0757ea8964e59b63d93708b138cc42a66eb3");
		Test__HashValue(algo_key, Test_vectors[1], "8aca2602792aec6f11a67206531fb7d7f0dff59413145e6973c45001d0087b42d11bc645413aeff63a42391a39145a591a92200d560195e53b478584fdae231a");
		Test__HashValue(algo_key, Test_vectors[2], "4e2448a4c6f486bb16b6562c73b4020bf3043e3a731bce721ae1b303d97e6d4c7181eebdb6c57e277d0e34957114cbd6c797fc9d95d8b582d225292076d4eef5");
		Test__HashValue(algo_key, Test_vectors[3], "378c84a4126e2dc6e56dcc7458377aac838d00032230f53ce1f5700c0ffb4d3b8421557659ef55c106b4b52ac5a4aaa692ed920052838f3362e86dbd37a8903e");
		Test__HashValue(algo_key, Test_vectors[4], "f1d754662636ffe92c82ebb9212a484a8d38631ead4238f5442ee13b8054e41b08bf2a9251c30b6a0b8aae86177ab4a6f68f673e7207865d5d9819a3dba4eb3b");
	}
	@Test  public void Tiger128_3() {
		String algo_key = "tiger128,3";
		Test__HashValue(algo_key, Test_vectors[0], "3293ac630c13f0245f92bbb1766e1616");
		Test__HashValue(algo_key, Test_vectors[1], "77befbef2e7ef8ab2ec8f93bf587a7fc");
		Test__HashValue(algo_key, Test_vectors[2], "2aab1484e8c158f2bfb8c5ff41b57a52");
		Test__HashValue(algo_key, Test_vectors[3], "d981f8cb78201a950dcf3048751e441c");
		Test__HashValue(algo_key, Test_vectors[4], "1714a472eee57d30040412bfcc55032a");
	}
	@Test  public void Tiger160_3() {
		String algo_key = "tiger160,3";
		Test__HashValue(algo_key, Test_vectors[0], "3293ac630c13f0245f92bbb1766e16167a4e5849");
		Test__HashValue(algo_key, Test_vectors[1], "77befbef2e7ef8ab2ec8f93bf587a7fc613e247f");
		Test__HashValue(algo_key, Test_vectors[2], "2aab1484e8c158f2bfb8c5ff41b57a525129131c");
		Test__HashValue(algo_key, Test_vectors[3], "d981f8cb78201a950dcf3048751e441c517fca1a");
		Test__HashValue(algo_key, Test_vectors[4], "1714a472eee57d30040412bfcc55032a0b11602f");
	}
	@Test  public void Tiger192_3() {
		String algo_key = "tiger192,3";
		Test__HashValue(algo_key, Test_vectors[0], "3293ac630c13f0245f92bbb1766e16167a4e58492dde73f3");
		Test__HashValue(algo_key, Test_vectors[1], "77befbef2e7ef8ab2ec8f93bf587a7fc613e247f5f247809");
		Test__HashValue(algo_key, Test_vectors[2], "2aab1484e8c158f2bfb8c5ff41b57a525129131c957b5f93");
		Test__HashValue(algo_key, Test_vectors[3], "d981f8cb78201a950dcf3048751e441c517fca1aa55a29f6");
		Test__HashValue(algo_key, Test_vectors[4], "1714a472eee57d30040412bfcc55032a0b11602ff37beee9");
	}
//  NOTE: NOT YET SUPPORTED
//	@Test  public void Tiger128_4() {
//		String algo_key = "tiger128,4";
//		Test__HashValue(algo_key, Test_vectors[0], "24cc78a7f6ff3546e7984e59695ca13d");
//		Test__HashValue(algo_key, Test_vectors[1], "e2a0e5e38b778421cceafbfe9a37068b");
//		Test__HashValue(algo_key, Test_vectors[2], "538883c8fc5f28250299018e66bdf4fd");
//		Test__HashValue(algo_key, Test_vectors[3], "a310058241bab4fd815e08a5afef6488");
//		Test__HashValue(algo_key, Test_vectors[4], "758fbb6c5ae68a0aa85d2739bcdd9e43");
//	}
//	@Test  public void Tiger160_4() {
//		String algo_key = "tiger160,4";
//		Test__HashValue(algo_key, Test_vectors[0], "24cc78a7f6ff3546e7984e59695ca13d804e0b68");
//		Test__HashValue(algo_key, Test_vectors[1], "e2a0e5e38b778421cceafbfe9a37068b032093fd");
//		Test__HashValue(algo_key, Test_vectors[2], "538883c8fc5f28250299018e66bdf4fdb5ef7b65");
//		Test__HashValue(algo_key, Test_vectors[3], "a310058241bab4fd815e08a5afef648874b91fc8");
//		Test__HashValue(algo_key, Test_vectors[4], "758fbb6c5ae68a0aa85d2739bcdd9e434e2af40f");
//	}
//	@Test  public void Tiger192_4() {
//		String algo_key = "tiger192,4";
//		Test__HashValue(algo_key, Test_vectors[0], "24cc78a7f6ff3546e7984e59695ca13d804e0b686e255194");
//		Test__HashValue(algo_key, Test_vectors[1], "e2a0e5e38b778421cceafbfe9a37068b032093fd36be1635");
//		Test__HashValue(algo_key, Test_vectors[2], "538883c8fc5f28250299018e66bdf4fdb5ef7b65f2e91753");
//		Test__HashValue(algo_key, Test_vectors[3], "a310058241bab4fd815e08a5afef648874b91fc8be4ed87d");
//		Test__HashValue(algo_key, Test_vectors[4], "758fbb6c5ae68a0aa85d2739bcdd9e434e2af40f6aa305ed");
//	}
//	@Test  public void Snefru() {
//		String algo_key = "snefru";
//		Test__HashValue(algo_key, Test_vectors[0], "8617f366566a011837f4fb4ba5bedea2b892f3ed8b894023d16ae344b2be5881");
//		Test__HashValue(algo_key, Test_vectors[1], "45161589ac317be0ceba70db2573ddda6e668a31984b39bf65e4b664b584c63d");
//		Test__HashValue(algo_key, Test_vectors[2], "7d033205647a2af3dc8339f6cb25643c33ebc622d32979c4b612b02c4903031b");
//		Test__HashValue(algo_key, Test_vectors[3], "c5d4ce38daa043bdd59ed15db577500c071b917c1a46cd7b4d30b44a44c86df8");
//		Test__HashValue(algo_key, Test_vectors[4], "9304bb2f876d9c4f54546cf7ec59e0a006bead745f08c642f25a7c808e0bf86e");
//	}
//	@Test  public void Snefru256() {
//		String algo_key = "snefru256";
//		Test__HashValue(algo_key, Test_vectors[0], "8617f366566a011837f4fb4ba5bedea2b892f3ed8b894023d16ae344b2be5881");
//		Test__HashValue(algo_key, Test_vectors[1], "45161589ac317be0ceba70db2573ddda6e668a31984b39bf65e4b664b584c63d");
//		Test__HashValue(algo_key, Test_vectors[2], "7d033205647a2af3dc8339f6cb25643c33ebc622d32979c4b612b02c4903031b");
//		Test__HashValue(algo_key, Test_vectors[3], "c5d4ce38daa043bdd59ed15db577500c071b917c1a46cd7b4d30b44a44c86df8");
//		Test__HashValue(algo_key, Test_vectors[4], "9304bb2f876d9c4f54546cf7ec59e0a006bead745f08c642f25a7c808e0bf86e");
//	}
	@Test  public void Gost() {
		String algo_key = "gost";
		Test__HashValue(algo_key, Test_vectors[0], "ce85b99cc46752fffee35cab9a7b0278abb4c2d2055cff685af4912c49490f8d");
		Test__HashValue(algo_key, Test_vectors[1], "d42c539e367c66e9c88a801f6649349c21871b4344c6a573f849fdce62f314dd");
		Test__HashValue(algo_key, Test_vectors[2], "f3134348c44fb1b2a277729e2285ebb5cb5e0f29c975bc753b70497c06a4d51d");
		Test__HashValue(algo_key, Test_vectors[3], "ad4434ecb18f2c99b60cbe59ec3d2469582b65273f48de72db2fde16a4889a4d");
		Test__HashValue(algo_key, Test_vectors[4], "3b7917937540a4f33ffcb5f37f29e8a9921b0655d7fd568d7cf27291cb897bb4");
	}
//  NOTE: NOT YET SUPPORTED
//	@Test  public void Gost_crypto() {
//		String algo_key = "gost-crypto";
//		Test__HashValue(algo_key, Test_vectors[0], "981e5f3ca30c841487830f84fb433e13ac1101569b9c13584ac483234cd656c0");
//		Test__HashValue(algo_key, Test_vectors[1], "e74c52dd282183bf37af0079c9f78055715a103f17e3133ceff1aacf2f403011");
//		Test__HashValue(algo_key, Test_vectors[2], "b285056dbf18d7392d7677369524dd14747459ed8143997e163b2986f92fd42c");
//		Test__HashValue(algo_key, Test_vectors[3], "bc6041dd2aa401ebfa6e9886734174febdb4729aa972d60f549ac39b29721ba0");
//		Test__HashValue(algo_key, Test_vectors[4], "8cda28cd45ce733e3d0837aed41bdf7fda7d83a6dfe211a0c695259a443250bd");
//	}
	@Test  public void Adler32() {
		String algo_key = "adler32";
		Test__HashValue(algo_key, Test_vectors[0], "00000001");
		Test__HashValue(algo_key, Test_vectors[1], "00620062");
		Test__HashValue(algo_key, Test_vectors[2], "024d0127");
		Test__HashValue(algo_key, Test_vectors[3], "29750586");
		Test__HashValue(algo_key, Test_vectors[4], "90860b20");
	}
//  NOTE: NOT YET SUPPORTED
//	@Test  public void Crc32() {
//		String algo_key = "crc32";
//		Test__HashValue(algo_key, Test_vectors[0], "00000000");
//		Test__HashValue(algo_key, Test_vectors[1], "6b9b9319");
//		Test__HashValue(algo_key, Test_vectors[2], "73bb8c64");
//		Test__HashValue(algo_key, Test_vectors[3], "5703c9bf");
//		Test__HashValue(algo_key, Test_vectors[4], "9693bf77");
//	}
	@Test  public void Crc32b() {
		String algo_key = "crc32b";
		Test__HashValue(algo_key, Test_vectors[0], "00000000");
		Test__HashValue(algo_key, Test_vectors[1], "e8b7be43");
		Test__HashValue(algo_key, Test_vectors[2], "352441c2");
		Test__HashValue(algo_key, Test_vectors[3], "20159d7f");
		Test__HashValue(algo_key, Test_vectors[4], "4c2750bd");
	}
	@Test  public void Fnv132() {
		String algo_key = "fnv132";
		Test__HashValue(algo_key, Test_vectors[0], "811c9dc5");
		Test__HashValue(algo_key, Test_vectors[1], "050c5d7e");
		Test__HashValue(algo_key, Test_vectors[2], "439c2f4b");
		Test__HashValue(algo_key, Test_vectors[3], "62f6de5e");
		Test__HashValue(algo_key, Test_vectors[4], "819dafd8");
	}
	@Test  public void Fnv164() {
		String algo_key = "fnv164";
		Test__HashValue(algo_key, Test_vectors[0], "cbf29ce484222325");
		Test__HashValue(algo_key, Test_vectors[1], "af63bd4c8601b7be");
		Test__HashValue(algo_key, Test_vectors[2], "d8dcca186bafadcb");
		Test__HashValue(algo_key, Test_vectors[3], "028945f18dedb23e");
		Test__HashValue(algo_key, Test_vectors[4], "5367ac9a0a6338d8");
	}
	@Test  public void Fnv1a32() {
		String algo_key = "fnv1a32";
		Test__HashValue(algo_key, Test_vectors[0], "811c9dc5");
		Test__HashValue(algo_key, Test_vectors[1], "e40c292c");
		Test__HashValue(algo_key, Test_vectors[2], "1a47e90b");
		Test__HashValue(algo_key, Test_vectors[3], "b2c0f234");
		Test__HashValue(algo_key, Test_vectors[4], "b0bc0c82");
	}
	@Test  public void Fnv1a64() {
		String algo_key = "fnv1a64";
		Test__HashValue(algo_key, Test_vectors[0], "cbf29ce484222325");
		Test__HashValue(algo_key, Test_vectors[1], "af63dc4c8601ec8c");
		Test__HashValue(algo_key, Test_vectors[2], "e71fa2190541574b");
		Test__HashValue(algo_key, Test_vectors[3], "2dcbcce86fce9934");
		Test__HashValue(algo_key, Test_vectors[4], "8450deb1cdc382a2");
	}
//  NOTE: NOT YET SUPPORTED
//	@Test  public void Joaat() {
//		Test__HashValue(algo_key, Test_vectors[0], "00000000");
//		Test__HashValue(algo_key, Test_vectors[1], "ca2e9442");
//		Test__HashValue(algo_key, Test_vectors[2], "ed131f5b");
//		Test__HashValue(algo_key, Test_vectors[3], "81d3c49b");
//		Test__HashValue(algo_key, Test_vectors[4], "b9f5ed0a");
//	}
	@Test  public void Haval128_3() {
		String algo_key = "haval128,3";
		Test__HashValue(algo_key, Test_vectors[0], "c68f39913f901f3ddf44c707357a7d70");
		Test__HashValue(algo_key, Test_vectors[1], "0cd40739683e15f01ca5dbceef4059f1");
		Test__HashValue(algo_key, Test_vectors[2], "9e40ed883fb63e985d299b40cda2b8f2");
		Test__HashValue(algo_key, Test_vectors[3], "3caf4a79e81adcd6d1716bcc1cef4573");
		Test__HashValue(algo_key, Test_vectors[4], "dc502247fb3eb8376109eda32d361d82");
	}
	@Test  public void Haval160_3() {
		String algo_key = "haval160,3";
		Test__HashValue(algo_key, Test_vectors[0], "d353c3ae22a25401d257643836d7231a9a95f953");
		Test__HashValue(algo_key, Test_vectors[1], "4da08f514a7275dbc4cece4a347385983983a830");
		Test__HashValue(algo_key, Test_vectors[2], "b21e876c4d391e2a897661149d83576b5530a089");
		Test__HashValue(algo_key, Test_vectors[3], "43a47f6f1c016207f08be8115c0977bf155346da");
		Test__HashValue(algo_key, Test_vectors[4], "eba9fa6050f24c07c29d1834a60900ea4e32e61b");
	}
	@Test  public void Haval192_3() {
		String algo_key = "haval192,3";
		Test__HashValue(algo_key, Test_vectors[0], "e9c48d7903eaf2a91c5b350151efcb175c0fc82de2289a4e");
		Test__HashValue(algo_key, Test_vectors[1], "b359c8835647f5697472431c142731ff6e2cddcacc4f6e08");
		Test__HashValue(algo_key, Test_vectors[2], "a7b14c9ef3092319b0e75e3b20b957d180bf20745629e8de");
		Test__HashValue(algo_key, Test_vectors[3], "6c4d9ec368efc96eeea58e132bdb2391c2b3e9d20190f7ea");
		Test__HashValue(algo_key, Test_vectors[4], "a25e1456e6863e7d7c74017bb3e098e086ad4be0580d7056");
	}
	@Test  public void Haval224_3() {
		String algo_key = "haval224,3";
		Test__HashValue(algo_key, Test_vectors[0], "c5aae9d47bffcaaf84a8c6e7ccacd60a0dd1932be7b1a192b9214b6d");
		Test__HashValue(algo_key, Test_vectors[1], "731814ba5605c59b673e4caae4ad28eeb515b3abc2b198336794e17b");
		Test__HashValue(algo_key, Test_vectors[2], "5bc955220ba2346a948d2848eca37bdd5eca6ecca7b594bd32923fab");
		Test__HashValue(algo_key, Test_vectors[3], "edf3e4add009ee89ee8ab03c39a3c749d20a48319c50c3a83861c540");
		Test__HashValue(algo_key, Test_vectors[4], "06ae38ebc43db58bd6b1d477c7b4e01b85a1e7b19b0bd088e33b58d1");
	}
	@Test  public void Haval256_3() {
		String algo_key = "haval256,3";
		Test__HashValue(algo_key, Test_vectors[0], "4f6938531f0bc8991f62da7bbd6f7de3fad44562b8c6f4ebf146d5b4e46f7c17");
		Test__HashValue(algo_key, Test_vectors[1], "47c838fbb4081d9525a0ff9b1e2c05a98f625714e72db289010374e27db021d8");
		Test__HashValue(algo_key, Test_vectors[2], "8699f1e3384d05b2a84b032693e2b6f46df85a13a50d93808d6874bb8fb9e86c");
		Test__HashValue(algo_key, Test_vectors[3], "911d1aad699e1b4a3a0e783bd5e68ba45392cc4915b1a17eca8a49da70879912");
		Test__HashValue(algo_key, Test_vectors[4], "72fad4bde1da8c8332fb60561a780e7f504f21547b98686824fc33fc796afa76");
	}
	@Test  public void Haval128_4() {
		String algo_key = "haval128,4";
		Test__HashValue(algo_key, Test_vectors[0], "ee6bbf4d6a46a679b3a856c88538bb98");
		Test__HashValue(algo_key, Test_vectors[1], "5cd07f03330c3b5020b29ba75911e17d");
		Test__HashValue(algo_key, Test_vectors[2], "6f2132867c9648419adcd5013e532fa2");
		Test__HashValue(algo_key, Test_vectors[3], "faee633871b30771ecda708d66fe6551");
		Test__HashValue(algo_key, Test_vectors[4], "b2a73b99775ffb17cd8781b85ec66221");
	}
	@Test  public void Haval160_4() {
		String algo_key = "haval160,4";
		Test__HashValue(algo_key, Test_vectors[0], "1d33aae1be4146dbaaca0b6e70d7a11f10801525");
		Test__HashValue(algo_key, Test_vectors[1], "e0a5be29627332034d4dd8a910a1a0e6fe04084d");
		Test__HashValue(algo_key, Test_vectors[2], "77aca22f5b12cc09010afc9c0797308638b1cb9b");
		Test__HashValue(algo_key, Test_vectors[3], "429346bb57211af6651060fd02db264fbe9c4365");
		Test__HashValue(algo_key, Test_vectors[4], "1c7884af86d11ac120fe5df75cee792d2dfa48ef");
	}
	@Test  public void Haval192_4() {
		String algo_key = "haval192,4";
		Test__HashValue(algo_key, Test_vectors[0], "4a8372945afa55c7dead800311272523ca19d42ea47b72da");
		Test__HashValue(algo_key, Test_vectors[1], "856c19f86214ea9a8a2f0c4b758b973cce72a2d8ff55505c");
		Test__HashValue(algo_key, Test_vectors[2], "7e29881ed05c915903dd5e24a8e81cde5d910142ae66207c");
		Test__HashValue(algo_key, Test_vectors[3], "e91960a06afbc8bd9f400a16135ed66e2745ef01d6d1cdf7");
		Test__HashValue(algo_key, Test_vectors[4], "2e2e581d725e799fda1948c75e85a28cfe1cf0c6324a1ada");
	}
	@Test  public void Haval224_4() {
		String algo_key = "haval224,4";
		Test__HashValue(algo_key, Test_vectors[0], "3e56243275b3b81561750550e36fcd676ad2f5dd9e15f2e89e6ed78e");
		Test__HashValue(algo_key, Test_vectors[1], "742f1dbeeaf17f74960558b44f08aa98bdc7d967e6c0ab8f799b3ac1");
		Test__HashValue(algo_key, Test_vectors[2], "124c43d2ba4884599d013e8c872bfea4c88b0b6bf6303974cbe04e68");
		Test__HashValue(algo_key, Test_vectors[3], "86e52ecc72ccaec188c17033fafe8b652705fd6a7d9db2e0d10cab92");
		Test__HashValue(algo_key, Test_vectors[4], "a0ac696cdb2030fa67f6cc1d14613b1962a7b69b4378a9a1b9738796");
	}
	@Test  public void Haval256_4() {
		String algo_key = "haval256,4";
		Test__HashValue(algo_key, Test_vectors[0], "c92b2e23091e80e375dadce26982482d197b1a2521be82da819f8ca2c579b99b");
		Test__HashValue(algo_key, Test_vectors[1], "e686d2394a49b44d306ece295cf9021553221db132b36cc0ff5b593d39295899");
		Test__HashValue(algo_key, Test_vectors[2], "8f409f1bb6b30c5016fdce55f652642261575bedca0b9533f32f5455459142b5");
		Test__HashValue(algo_key, Test_vectors[3], "dbcc8e1011df45121d4ff2bb62c6c38949d76084f829c36d5929aee71b261f2f");
		Test__HashValue(algo_key, Test_vectors[4], "124f6eb645dc407637f8f719cc31250089c89903bf1db8fac21ea4614df4e99a");
	}
	@Test  public void Haval128_5() {
		String algo_key = "haval128,5";
		Test__HashValue(algo_key, Test_vectors[0], "184b8482a0c050dca54b59c7f05bf5dd");
		Test__HashValue(algo_key, Test_vectors[1], "f23fbe704be8494bfa7a7fb4f8ab09e5");
		Test__HashValue(algo_key, Test_vectors[2], "d054232fe874d9c6c6dc8e6a853519ea");
		Test__HashValue(algo_key, Test_vectors[3], "c28052dc143c1c70450d3c0504756efe");
		Test__HashValue(algo_key, Test_vectors[4], "0efff71d7d14344cba1f4b25f924a693");
	}
	@Test  public void Haval160_5() {
		String algo_key = "haval160,5";
		Test__HashValue(algo_key, Test_vectors[0], "255158cfc1eed1a7be7c55ddd64d9790415b933b");
		Test__HashValue(algo_key, Test_vectors[1], "f5147df7abc5e3c81b031268927c2b5761b5a2b5");
		Test__HashValue(algo_key, Test_vectors[2], "ae646b04845e3351f00c5161d138940e1fa0c11c");
		Test__HashValue(algo_key, Test_vectors[3], "2ac00ef52871b373eda407d7eafbd225987f33f1");
		Test__HashValue(algo_key, Test_vectors[4], "917836a9d27eed42d406f6002e7d11a0f87c404c");
	}
	@Test  public void Haval192_5() {
		String algo_key = "haval192,5";
		Test__HashValue(algo_key, Test_vectors[0], "4839d0626f95935e17ee2fc4509387bbe2cc46cb382ffe85");
		Test__HashValue(algo_key, Test_vectors[1], "5ffa3b3548a6e2cfc06b7908ceb5263595df67cf9c4b9341");
		Test__HashValue(algo_key, Test_vectors[2], "d12091104555b00119a8d07808a3380bf9e60018915b9025");
		Test__HashValue(algo_key, Test_vectors[3], "8225efabaded623849843546cdf3c8c88e0fdca68f9a5a56");
		Test__HashValue(algo_key, Test_vectors[4], "85f1f1c0eca04330cf2de5c8c83cf85a611b696f793284de");
	}
	@Test  public void Haval224_5() {
		String algo_key = "haval224,5";
		Test__HashValue(algo_key, Test_vectors[0], "4a0513c032754f5582a758d35917ac9adf3854219b39e3ac77d1837e");
		Test__HashValue(algo_key, Test_vectors[1], "67b3cb8d4068e3641fa4f156e03b52978b421947328bfb9168c7655d");
		Test__HashValue(algo_key, Test_vectors[2], "8081027a500147c512e5f1055986674d746d92af4841abeb89da64ad");
		Test__HashValue(algo_key, Test_vectors[3], "877a7b891fce89036fc127756b07923ece3ba7c495922909cc89512e");
		Test__HashValue(algo_key, Test_vectors[4], "1b360acff7806502b5d40c71d237cc0c40343d2000ae2f65cf487c94");
	}
	@Test  public void Haval256_5() {
		String algo_key = "haval256,5";
		Test__HashValue(algo_key, Test_vectors[0], "be417bb4dd5cfb76c7126f4f8eeb1553a449039307b1a3cd451dbfdc0fbbe330");
		Test__HashValue(algo_key, Test_vectors[1], "de8fd5ee72a5e4265af0a756f4e1a1f65c9b2b2f47cf17ecf0d1b88679a3e22f");
		Test__HashValue(algo_key, Test_vectors[2], "976cd6254c337969e5913b158392a2921af16fca51f5601d486e0a9de01156e7");
		Test__HashValue(algo_key, Test_vectors[3], "7ccf22af7f99acd6ac84f176041329e2958fde1419a259d5a4b89d8f4115ad74");
		Test__HashValue(algo_key, Test_vectors[4], "c9c7d8afa159fd9e965cb83ff5ee6f58aeda352c0eff005548153a61551c38ee");
	}
		private void Test__HashValue(String algo, String val, String expd) {
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_hash.Invk_hashValue, Object_.Ary(algo, val), expd);
	}
}
