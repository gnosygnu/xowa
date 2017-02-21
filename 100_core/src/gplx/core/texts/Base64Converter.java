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
package gplx.core.texts; import gplx.*; import gplx.core.*;
public class Base64Converter {
	private final static char[] ALPHABET = String_.XtoCharAry("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
	private static int[]  toInt   = null;//new int[128];
    static void Init() {
		toInt = new int[128];
        for(int i=0; i< ALPHABET.length; i++){
            toInt[ALPHABET[i]]= i;
        }
    }
	public static String EncodeString(String orig) {return Encode(Bry_.new_u8(orig));}
	public static String Encode(byte[] buf){
		if (toInt == null) Init();
		int size = buf.length;
		char[] ar = new char[((size + 2) / 3) * 4];
		int a = 0;
		int i=0;
		while(i < size){
			byte b0 = buf[i++];
			byte b1 = (i < size) ? buf[i++] : (byte)0;
			byte b2 = (i < size) ? buf[i++] : (byte)0;

			int mask = 0x3F;
			ar[a++] = ALPHABET[(b0 >> 2) & mask];
			ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
			ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
			ar[a++] = ALPHABET[b2 & mask];
		}
		switch(size % 3){
			case 1: ar[--a]  = '='; 
				ar[--a]  = '='; 
				break;
			case 2: ar[--a]  = '='; break;
		}
		return new String(ar);
	}
	public static String DecodeString(String orig) {return String_.new_u8(Decode(orig));}
	public static byte[] Decode(String s){
		if (toInt == null) Init();
		int sLen = String_.Len(s);
		int delta = String_.Has_at_end(s, "==") ? 2 : String_.Has_at_end(s, "=") ? 1 : 0;
		byte[] buffer = new byte[sLen *3/4 - delta];
		int mask = 0xFF;
		int index = 0;
		for(int i=0; i< sLen; i+=4){
			int c0 = toInt[String_.CharAt(s, i)];
			int c1 = toInt[String_.CharAt(s, i + 1)];
			buffer[index++]= (byte)(((c0 << 2) | (c1 >> 4)) & mask);
			if(index >= buffer.length){
				return buffer;
			}
			int c2 = toInt[String_.CharAt(s, i + 2)];
			buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);
			if(index >= buffer.length){
				return buffer;
			}
			int c3 = toInt[String_.CharAt(s, i + 3)];
			buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
		}
		return buffer;
	} 
}
