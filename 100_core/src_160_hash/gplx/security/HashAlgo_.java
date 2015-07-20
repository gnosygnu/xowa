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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import gplx.core.consoles.*;
import gplx.ios.*; /*IoStream*/import gplx.texts.*; /*Base32Converter*/
public class HashAlgo_ {
	public static final HashAlgo Null = new HashAlgo_null();
	public static final HashAlgo Sha1 = HashAlgo_sha1.new_();
	public static final HashAlgo Md5 = HashAlgo_md5.new_();
	public static final HashAlgo Tth192 = HashAlgo_tth192.new_();
	public static HashAlgo as_(Object obj) {return obj instanceof HashAlgo ? (HashAlgo)obj : null;}
	public static HashAlgo cast_(Object obj) {if (obj == null) return null; HashAlgo rv = as_(obj); if (rv == null) throw Err_.new_type_mismatch(HashAlgo.class, obj); return rv;}
	public static HashAlgo fetch_(String key) {
		if		(key == HashAlgo_md5.KEY)		return Md5;
		else if (key == HashAlgo_sha1.KEY)		return Sha1;
		else if (key == HashAlgo_tth192.KEY)	return Tth192;
		else										throw Err_.new_unhandled(key);
	}
	public static HashAlgo store_orSelf_(SrlMgr mgr, String key, HashAlgo or) {
		String algoType = mgr.SrlStrOr(key, or.Key());
		return mgr.Type_rdr() ? HashAlgo_.fetch_(algoType): or;
	}
}
class HashAlgo_null implements HashAlgo {
	public String Key() {return "HashAlgo_null";}
	public byte[] Calc_hash_bry(byte[] v) {return Bry_.new_a7(CalcHash(Console_adp_.Noop, gplx.ios.IoStream_.ary_(v)));}
	public String CalcHash(Console_adp dialog, IoStream stream) {return "NullAlgoHash";}
}
class HashAlgo_md5 implements HashAlgo {
	public String Key() {return KEY;} public static final String KEY = "md5";
	public byte[] Calc_hash_bry(byte[] v) {return Bry_.new_a7(CalcHash(Console_adp_.Noop, gplx.ios.IoStream_.ary_(v)));}
	public String CalcHash(Console_adp dialog, IoStream stream) {return HashAlgoUtl.CalcHashAsString(dialog, stream, "MD5");}
	public static HashAlgo_md5 new_() {return new HashAlgo_md5();} HashAlgo_md5() {}
}
class HashAlgo_sha1 implements HashAlgo {
	public String Key() {return KEY;} public static final String KEY = "sha1";
	public byte[] Calc_hash_bry(byte[] v) {return Bry_.new_a7(CalcHash(Console_adp_.Noop, gplx.ios.IoStream_.ary_(v)));}
	public String CalcHash(Console_adp dialog, IoStream stream) {return HashAlgoUtl.CalcHashAsString(dialog, stream, "SHA1");}
	public static HashAlgo_sha1 new_() {return new HashAlgo_sha1();} HashAlgo_sha1() {}
}
class HashAlgoUtl {
	public static String CalcHashAsString(Console_adp dialog, IoStream stream, String key) {
		MessageDigest md = null;
		try {md = MessageDigest.getInstance(key);}
		catch (NoSuchAlgorithmException e) {throw Err_.new_missing_key(key);}
		byte[] buffer = new byte[8192];
		int read = 0;
		long pos = 0, len = stream.Len();	// pos and len must be long, else will not hash files > 2 GB
		while (true) {
			read = stream.Read(buffer, 0, 8192);
			if (pos >= len) break;
			md.update(buffer, 0, read);
			pos += read;
		}		
		byte[] md5sum = md.digest();
		BigInteger bigInt = new BigInteger(1, md5sum);
		String rv = bigInt.toString(16);
		int rvLen = rv.length();
		while (rvLen < 32) {
			rv = "0" + rv;
			rvLen++;
		}
		return rv;
	}
	public static String XtoStrBase16(byte[] ary) {
		BigInteger bigInt = new BigInteger(1, ary);
		String rv = bigInt.toString(16);
		int rvLen = rv.length();
		while (rvLen < 32) {
			rv = "0" + rv;
			rvLen++;
		}
		return rv;
	}
	public static String XtoStrBase32(byte[] ary) {return Base32Converter.Encode(ary);}
}
