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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import gplx.core.consoles.*; import gplx.core.ios.streams.*; /*IoStream*/
import gplx.core.texts.*; /*Base32Converter*/ import gplx.core.progs.*;
public class Hash_algo_ {
	public static Hash_algo New__md5()		{return new Hash_algo__md5();}
	public static Hash_algo New__sha1()		{return new Hash_algo__sha1();}
	public static Hash_algo New__sha2_256()	{return new Hash_algo__sha2_256();}
	public static Hash_algo New__tth_192()	{return new Hash_algo__tth_192();}
	public static Hash_algo New_by_tid(byte tid) {
		switch (tid) {
			case Tid__md5:			return New__md5();
			case Tid__sha1:			return New__sha1();
			case Tid__sha2_256:		return New__sha2_256();
			case Tid__tth_192:		return New__tth_192();
			default:				throw Err_.new_unhandled_default(tid);
		}
	}
	public static Hash_algo New(String key) {
		if		(key == Hash_algo__md5.KEY)			return New__md5();
		else if (key == Hash_algo__sha1.KEY)		return New__sha1();
		else if (key == Hash_algo__sha2_256.KEY)	return New__sha2_256();
		else if (key == Hash_algo__tth_192.KEY)		return New__tth_192();
		else										throw Err_.new_unhandled(key);
	}
	public static final byte Tid__md5 = 0, Tid__sha1 = 1, Tid__sha2_256 = 2, Tid__tth_192 = 3;
}
abstract class Hash_algo_base implements Hash_algo {
	private final MessageDigest md;
	private final byte[] trg_bry;
	private byte[] tmp_bfr; private final int tmp_bfr_len = 4096;
	public Hash_algo_base(MessageDigest md, int trg_bry_len) {
		this.md = md; this.trg_bry = new byte[trg_bry_len];
	}
	public String Hash_bry_as_str(byte[] src) {return String_.new_a7(Hash_bry_as_bry(src));}
	public byte[] Hash_bry_as_bry(byte[] src) {
		Hash_algo_utl_.Hash_bry(md, src, src.length, trg_bry);
		return Bry_.Copy(trg_bry);	// NOTE: must copy to return different instances to callers; else callers may hash same instance with different values
	}
	public String Hash_stream_as_str(Console_adp console, IoStream stream) {return String_.new_a7(Hash_stream_as_bry(console, stream));} 
	public byte[] Hash_stream_as_bry(Console_adp console, IoStream stream) {
		if (tmp_bfr == null) tmp_bfr = new byte[4096];
		Hash_algo_utl_.Hash_stream(console, stream, md, tmp_bfr, tmp_bfr_len, trg_bry);
		return trg_bry;
	}
	public byte[] Hash_stream_as_bry(Gfo_prog_ui prog_ui, IoStream stream) {
		if (tmp_bfr == null) tmp_bfr = new byte[4096];
		Hash_algo_utl_.Hash_stream(prog_ui, stream, md, tmp_bfr, tmp_bfr_len, trg_bry);
		return trg_bry;		
	}
	protected static MessageDigest Get_message_digest(String key) {
		try {return MessageDigest.getInstance(key);}
		catch (NoSuchAlgorithmException e) {throw Err_.new_missing_key(key);}		
	}
}
class Hash_algo__md5 extends Hash_algo_base {
	public Hash_algo__md5() {super(Get_message_digest_instance(), 32);}
	public String Key() {return KEY;} public static final String KEY = "md5";
	
	private static MessageDigest Get_message_digest_instance() {
		if (md__md5 == null)
			md__md5 = Get_message_digest(KEY);
		return md__md5;
	}	private static MessageDigest md__md5;
} 
class Hash_algo__sha1 extends Hash_algo_base {
	public Hash_algo__sha1() {super(Get_message_digest_instance(), 40);}
	public String Key() {return KEY;} public static final String KEY = "sha1";

	private static MessageDigest Get_message_digest_instance() {
		if (md__sha1 == null)
			md__sha1 = Get_message_digest(KEY);
		return md__sha1;
	}	private static MessageDigest md__sha1;
}
class Hash_algo__sha2_256 extends Hash_algo_base {
	public Hash_algo__sha2_256() {super(Get_message_digest_instance(), 64);}
	public String Key() {return KEY;} public static final String KEY = "sha-256";

	private static MessageDigest Get_message_digest_instance() {
		if (md__sha2_256 == null)
			md__sha2_256 = Get_message_digest(KEY);
		return md__sha2_256;
	}	private static MessageDigest md__sha2_256;
}
class Hash_algo_utl_ {
	public static void Hash_bry(MessageDigest md, byte[] src_bry, int src_len, byte[] trg_bry) {
		int pos = 0;
		while (true) {
			if (pos == src_len) break;
			int len = 4096;
			if (pos + len > src_len) { 
				len = src_len - pos;
			}
			md.update(src_bry, pos, len);
			pos += len;
		}
		byte[] md_bry = md.digest();
		gplx.core.encoders.Hex_utl_.Encode_bry(md_bry, trg_bry);
	}
	public static void Hash_stream(Console_adp dialog, IoStream stream, MessageDigest md, byte[] tmp_bfr, int tmp_bfr_len, byte[] trg_bry) {
//		long pos = 0, len = stream.Len();						// pos and len must be long, else will not hash files > 2 GB
		while (true) {
			int read = stream.Read(tmp_bfr, 0, tmp_bfr_len);	// read stream into tmp_bfr
			if (read < 1) break;
			md.update(tmp_bfr, 0, read);
//			pos += read;
		}	
		byte[] md_bry = md.digest();
		gplx.core.encoders.Hex_utl_.Encode_bry(md_bry , trg_bry);
	}
	public static void Hash_stream(Gfo_prog_ui prog_ui, IoStream stream, MessageDigest md, byte[] tmp_bfr, int tmp_bfr_len, byte[] trg_bry) {
		long pos = prog_ui.Prog_data_cur(), len = prog_ui.Prog_data_end();					// pos and len must be long, else will not hash files > 2 GB
		try {
			while (true) {
				int read = stream.Read(tmp_bfr, 0, tmp_bfr_len);	// read stream into tmp_bfr
				if (read < 1) break;
				md.update(tmp_bfr, 0, read);
				if (prog_ui.Prog_notify_and_chk_if_suspended(pos, len)) return;
				pos += read;
			}
		}
		finally {stream.Rls();}
		byte[] md_bry = md.digest();
		gplx.core.encoders.Hex_utl_.Encode_bry(md_bry , trg_bry);
	}
	public static String To_base_32_str(byte[] ary) {return Base32Converter.Encode(ary);}
}
