NOTE: This page is also in XOWA under home/wiki/Help:Luaj

== Source ==

The luaj_xowa.jar was built using the source at http://sourceforge.net/projects/luaj/files/luaj-3.0/3.0-beta2/luaj-3.0-beta2.zip/download.

Its source is not currently included with XOWA. It is available at the following location: https://sourceforge.net/projects/xowa/files/support/luaj/

== Modification ==

The luaj_xowa.jar was created for the following reasons:

* Backward compatibility: 
: Scribunto is currently using Lua 5.1 whereas luaj 3.0 is designed for Lua 5.2
: Lua 5.2 is not backward-compatible with Lua 5.1; several functions are obsoleted (for example, table.maxn)
: The luaj_xowa.jar was tailored to support Scribunto's 5.1 environment.
* Patches / bug fixes:
: Luaj has a handful of minor issues / defects. They are listed below.

== $engines variable ==
    
Note that the $engines variable refers to /xowa/bin/any/lua/mediawiki/extensions/Scribunto/engines/


== Luaj changes ==
=== Luaj 2.0.3 errors fixed in 3.0 ===
==== os.time doesn't handle dates before 1970 ====
* fix : incorrect Birth / Date; EX: ru.w:Пушкин,_Александр_Сергеевич
* file: /src/core/org/luaj/vm2/lib/OsLib.java

==== pairs.next fails when setting val to null ====
* fix : Finnish declension table; EX:d:Latvia
* file: /src/core/org/luaj/vm2/LuaTable.java

=== Luaj 2.0.3 features removed from 3.0 ===
==== string.gfind deprecated ====
* fix : missing Video_game_reviews; EX: w:Sonic_Heroes
* file: /src/core/org/luaj/vm2/lib/StringLib.java
* code: call
<pre>
      old:
          			"sub"} );
      new:
                "sub", "gfind"} );
</pre>
* code: invoke
<pre>
      add:
                case 4: 
                case 9:	return StringLib.gmatch( args );
</pre>

==== math.log10 deprecated ====
* fix : blank references; EX:w:Earth
* file: /src/jse/org/luaj/vm2/lib/JseMathLib.java
* code:
<pre>
      math.set("log10", new log10());
      
      static final class log10 extends UnaryOp { protected double call(double d) { return Math.log10(d); } }
</pre>

==== math.mod deprecated ====
* fix : missing table; EX:d:աղբիւր
* file: /src/core/org/luaj/vm2/lib/MathLib.java
* code:
<pre>
      fmod fmod_func = new fmod();
      math.set("mod", fmod_func);
      math.set("fmod", fmod_func);
</pre>

==== table.maxn deprecated ====
file: /src/core/org/luaj/vm2/lib/TableLib.java
* code:
<pre>
      public LuaValue getn() {
        int len = length();
        for (int n = len; n > 0; --n )
          if ( !rawget(n).isnil() )
            return LuaInteger.valueOf(n);
        return ZERO;
      }
</pre>

file: /src/core/org/luaj/vm2/lib/TableLib.java
<pre>
      table.set("maxn", new maxn());

      static class maxn extends OneArgFunction {
        public LuaValue call(LuaValue arg) {
          return LuaValue.valueOf(arg.checktable().maxn());
        }
      }
</pre>

==== table.getn deprecated ====
* fix : missing text; EX: d:aceite d:Module:pt-verb-form-of
* file: /src/core/org/luaj/vm2/LuaTable.java
code:
<pre>
      public LuaValue getn() {
        int len = length();
        for (int n = len; n > 0; --n )
          if ( !rawget(n).isnil() )
            return LuaInteger.valueOf(n);
        return ZERO;
      }
</pre>

* file: /src/core/org/luaj/vm2/lib/TableLib.java
* code:
<pre>
      table.set("getn", new getn());
      
      static class getn extends OneArgFunction {
        public LuaValue call(LuaValue arg) {
          return arg.checktable().getn();
        }
      }
</pre>
      
==== automatic arg variable in varargs function deprecated ====
* fix : Horizontal timeline; EX: w:Cretaceous%E2%80%93Paleogene_extinction_event
* file: /src/core/org/luaj/vm2/LuaClosure.java
code: 
<pre>
      case Lua.OP_GETTABUP: /*	A B C	R(A) := UpValue[B][RK(C)]			*/
        // stack[a] = upValues[i>>>23].getValue().get((c=(i>>14)&0x1ff)>0xff? k[c&0x0ff]: stack[c]);
        // HACK: handle deprecated "arg" for "..."
        int OP_GETTABUP_c = (i>>14)&0x1ff;
        boolean OP_GETTABUP_b = OP_GETTABUP_c>0xff;
        LuaValue OP_GETTABUP_idx = OP_GETTABUP_b ? k[OP_GETTABUP_c&0x0ff]: stack[OP_GETTABUP_c];
        stack[a] = upValues[i>>>23].getValue().get(OP_GETTABUP_idx);
        // HACK: handle deprecated "arg"
        if (	p.is_vararg == 1
          &&	stack[a] == NIL
          && 	OP_GETTABUP_b
          &&  "arg".equals(OP_GETTABUP_idx.tojstring())
          )
          stack[a] = new LuaTable(varargs);
        continue;
</pre>

=== Luaj 3.0 defects ===
==== os.date does not accept UTC format ====
* fix : incorrect age in ym; EX:w:Supreme_Court_of_the_United_States
* file: /src/core/org/luaj/vm2/lib/OsLib.java
* proc: invoke.DATE
<pre>
boolean utc = false;
if (s.startsWith("!")) {
  utc = true;
  s = s.substring(1);
}
if (s.equals("*t")) {
  Calendar d = Calendar.getInstance();
  long time_in_ms = (long)(t*1000);
  if (utc) {
    java.util.TimeZone current_tz = d.getTimeZone();
    int offset_from_utc = current_tz.getOffset(time_in_ms);
    time_in_ms += -offset_from_utc;
  }
</pre>

==== string.gsub fails with out_of_bounds error ====
* fix : blank references; EX:w:Earth
* file: /src/core/org/luaj/vm2/lib/StringLib.java
* proc: gsub
<pre>
      old:
          if ( anchor )
            break;
      new:
          if ( anchor )
            break;
          if (soffset >= srclen) break; // assert soffset is in bounds, else will throw ArrayIndexOutOfBounds exception;
</pre>

==== string.gsub fails if string is empty====
* fix : blank references; EX:w:Woburn,_Massachusetts
* file: /src/core/org/luaj/vm2/lib/StringLib.java
* proc: gsub
<pre>
      static Varargs gsub( Varargs args ) {
        LuaString src = args.checkstring( 1 );
        final int srclen = src.length();
        if (srclen == 0) return varargsOf(src, LuaValue.ZERO); // exit early
</pre>

==== string.format ignores precision for double args ====
* fix : Convert calls will show full precision for numbers; EX:w:Tomato
* file: /src/core/org/luaj/vm2/lib/StringLib.java
<pre>
      old:
						case 'G':
							fdsc.format( result, args.checkdouble( arg ) );
      new:
						case 'c':	// merged c with e E f F g G
							fdsc.format( result, fmt.tojstring().substring(i - fdsc.length, i), args.checkdouble( arg ));
</pre>
* proc: format
<pre>
      old: 
            buf.append( v) );
      new:
            try {
              fmtstr = "%" + fmtstr; // % stripped above
              buf.append( String.format(fmtstr, v) );	// call String.format
            }catch (java.util.MissingFormatWidthException e) {
              // HACK: handle invalid format of "%0.1f" -> "%.1f"; %0 should be followed by valid number, but in this case, ignore it;
              String old_fmt = e.getFormatSpecifier();
              if (old_fmt.startsWith("0")) {
                String new_fmt = old_fmt.substring(1);	// remove initial 0
                fmtstr = fmtstr.replace(old_fmt, new_fmt);
                buf.append( String.format(fmtstr, v) );	// call String.format								
              }
              else
                throw e;
            }
</pre>
* note: forces a 1.5 JRE (as opposed to 1.3)

==== string.tonumber should trim all whitespace ====
* fix: Population tables; EX: w:Woburn,_Massachusetts
* file: /src/core/org/luaj/vm2/LuaString.java
* proc: scannumber
<pre>
	// trim ws
	int idx = i;
	while (idx < j) {
		switch (m_bytes[idx]) {
			case 9: case 10: case 13: case 32:
				++idx;
				i = idx;
				break;
			default:
				idx = j;
				break;
		}
	}
	idx = j - 1;
	while (idx >= i) {
		switch (m_bytes[idx]) {
			case 9: case 10: case 13: case 32:
				j = idx;
				--idx;
				break;
			default:
				idx = i -1;
				break;
		}
	}
</pre>

==== multi-byte strings not fully supported ====
* fix : Thai calendar; EX:th.w:เหตุการณ์ปัจจุบัน
* file: /src/core/org/luaj/vm2/LuaString.java
<pre style='overflow:auto'>
	public static LuaString valueOf(char[] chars, int off, int len) {
		// COMMENTED: does not handle 2+ byte chars; assumes 1 char = 1 byte
//		byte[] b = new byte[len];
//		for ( int i=0; i<len; i++ )
//			b[i] = (byte) chars[i + off];
//		return valueOf(b, 0, len);
		int bry_len = 0;
		for (int i = 0; i < len; i++)	// iterate over chars to sum all single / multi-byte chars
			bry_len += LuaString.Utf8_len_by_int((int)(chars[i + off])); 
		byte[] bry = new byte[bry_len];
		int bry_idx = 0;
		for (int i = 0; i < len; i++) {
			int c = (int)(chars[i + off]);
			int c_len_in_bytes = LuaString.Utf8_encode_by_int(c, bry, bry_idx);
			bry_idx += c_len_in_bytes;
		}
		return valueOf(bry, 0, bry_len);
	}
	
	public static String decodeAsUtf8(byte[] bytes, int offset, int length) {
		// COMMENTED: does not handle 3+ byte chars
//		int i,j,n,b;
//		for ( i=offset,j=offset+length,n=0; i<j; ++n ) {
//			switch ( 0xE0 & bytes[i++] ) {
//			case 0xE0: ++i;
//			case 0xC0: ++i;
//			}
//		}
//		char[] chars=new char[n];
//		for ( i=offset,j=offset+length,n=0; i<j; ) {
//			chars[n++] = (char) (
//				((b=bytes[i++])>=0||i>=j)? b:
//				(b<-32||i+1>=j)? (((b&0x3f) << 6) | (bytes[i++]&0x3f)):
//					(((b&0xf) << 12) | ((bytes[i++]&0x3f)<<6) | (bytes[i++]&0x3f)));
//		}
//		return new String(chars);
		return new String(bytes, offset, length, java.nio.charset.Charset.forName("UTF-8"));
	}

	public static int lengthAsUtf8(char[] chars) {
		// COMMENTED: does not handle 3+ byte chars
//		int i,b;
//		char c;
//		for ( i=b=chars.length; --i>=0; )
//			if ( (c=chars[i]) >=0x80 )
//				b += (c>=0x800)? 2: 1;
//		return b;
		int len = chars.length;
		int rv = 0;
		for (int i = 0; i < len; i++)
			rv += LuaString.Utf8_len_by_int(chars[i]);			
		return rv;
	}

	public static int encodeToUtf8(char[] chars, int nchars, byte[] bytes, int off) {
		// COMMENTED: does not handle 4+ byte chars; already using Encode_by_int, so might as well be consistent
//		char c;
//		int j = off;
//		for ( int i=0; i<nchars; i++ ) {
//			if ( (c = chars[i]) < 0x80 ) {
//				bytes[j++] = (byte) c;
//			} else if ( c < 0x800 ) {
//				bytes[j++] = (byte) (0xC0 | ((c>>6)  & 0x1f));
//				bytes[j++] = (byte) (0x80 | ( c      & 0x3f));				
//			} else {
//				bytes[j++] = (byte) (0xE0 | ((c>>12) & 0x0f));
//				bytes[j++] = (byte) (0x80 | ((c>>6)  & 0x3f));
//				bytes[j++] = (byte) (0x80 | ( c      & 0x3f));				
//			}
//		}
//		return j - off;
		int bry_idx = off;
		for (int i = 0; i < nchars; i++ ) {
			int c = chars[i];
			int c_len_in_bytes = LuaString.Utf8_encode_by_int(c, bytes, bry_idx);
			bry_idx += c_len_in_bytes;
		}
		return nchars;	// NOTE: code returned # of bytes which is wrong; Globals.UTF8Stream.read caches rv as j which is used as index to char[] not byte[]; will throw out of bounds exception if bytes returned
	}

	public static int Utf8_len_by_int(int charAsInt) {
		if		(charAsInt < 0x80)				return 1;
		else if (charAsInt < (1 << 11))			return 2;
		else if (charAsInt < (1 << 16))			return 3;
		else if (charAsInt < (1 << 21))			return 4;
		else if (charAsInt < (1 << 26))			return 5;
		else 									return 6;
	}
	public static int Utf8_len_by_byte(byte b) {
		int i = b & 0xff;
		switch (i) {
			case   0: case   1: case   2: case   3: case   4: case   5: case   6: case   7: case   8: case   9: case  10: case  11: case  12: case  13: case  14: case  15: 
			case  16: case  17: case  18: case  19: case  20: case  21: case  22: case  23: case  24: case  25: case  26: case  27: case  28: case  29: case  30: case  31: 
			case  32: case  33: case  34: case  35: case  36: case  37: case  38: case  39: case  40: case  41: case  42: case  43: case  44: case  45: case  46: case  47: 
			case  48: case  49: case  50: case  51: case  52: case  53: case  54: case  55: case  56: case  57: case  58: case  59: case  60: case  61: case  62: case  63: 
			case  64: case  65: case  66: case  67: case  68: case  69: case  70: case  71: case  72: case  73: case  74: case  75: case  76: case  77: case  78: case  79: 
			case  80: case  81: case  82: case  83: case  84: case  85: case  86: case  87: case  88: case  89: case  90: case  91: case  92: case  93: case  94: case  95: 
			case  96: case  97: case  98: case  99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110: case 111: 
			case 112: case 113: case 114: case 115: case 116: case 117: case 118: case 119: case 120: case 121: case 122: case 123: case 124: case 125: case 126: case 127:
			case 128: case 129: case 130: case 131: case 132: case 133: case 134: case 135: case 136: case 137: case 138: case 139: case 140: case 141: case 142: case 143: 
			case 144: case 145: case 146: case 147: case 148: case 149: case 150: case 151: case 152: case 153: case 154: case 155: case 156: case 157: case 158: case 159: 
			case 160: case 161: case 162: case 163: case 164: case 165: case 166: case 167: case 168: case 169: case 170: case 171: case 172: case 173: case 174: case 175: 
			case 176: case 177: case 178: case 179: case 180: case 181: case 182: case 183: case 184: case 185: case 186: case 187: case 188: case 189: case 190: case 191: 
				return 1;
			case 192: case 193: case 194: case 195: case 196: case 197: case 198: case 199: case 200: case 201: case 202: case 203: case 204: case 205: case 206: case 207: 
			case 208: case 209: case 210: case 211: case 212: case 213: case 214: case 215: case 216: case 217: case 218: case 219: case 220: case 221: case 222: case 223: 
				return 2;
			case 224: case 225: case 226: case 227: case 228: case 229: case 230: case 231: case 232: case 233: case 234: case 235: case 236: case 237: case 238: case 239: 
				return 3;
			case 240: case 241: case 242: case 243: case 244: case 245: case 246: case 247:
				return 4;
			case 248: case 249: case 250: case 251:
				return 5;
			case 252: case 253:
				return 6;
			case 254: case 255:
			default:
				return 6;
		}		
	}
	public static int Utf8_decode_to_int(byte[] ary, int pos) {
		byte b0 = ary[pos];
		if 		((b0 & 0x80) == 0) {
			return  b0;			
		}
		else if ((b0 & 0xE0) == 0xC0) {
			return  ( b0           & 0x1f) <<  6
				| 	( ary[pos + 1] & 0x3f)
				;			
		}
		else if ((b0 & 0xF0) == 0xE0) {
			return  ( b0           & 0x0f) << 12
				| 	((ary[pos + 1] & 0x3f) <<  6)
				| 	( ary[pos + 2] & 0x3f)
				;			
		}
		else if ((b0 & 0xF8) == 0xF0) {
			return  ( b0           & 0x07) << 18
				| 	((ary[pos + 1] & 0x3f) << 12)
				| 	((ary[pos + 2] & 0x3f) <<  6)
				| 	( ary[pos + 3] & 0x3f)
				;			
		}
		else if ((b0 & 0xFC) == 0xF8) {
			return  ( b0           & 0x03) << 24
				| 	((ary[pos + 1] & 0x3f) << 18)
				| 	((ary[pos + 2] & 0x3f) << 12)
				| 	((ary[pos + 3] & 0x3f) <<  6)
				| 	( ary[pos + 4] & 0x3f)
				;			
		}
		else if ((b0 & 0xFC) == 0xFC) {
			return  ( b0           & 0x03) << 30
				| 	((ary[pos + 1] & 0x3f) << 24)
				| 	((ary[pos + 2] & 0x3f) << 18)
				| 	((ary[pos + 3] & 0x3f) << 12)
				| 	((ary[pos + 4] & 0x3f) <<  6)
				| 	( ary[pos + 5] & 0x3f)
			;			
		}
		else {
			return b0 & 0xFF;
		}
	}
	public static int Utf8_encode_by_int(int charAsInt, byte[] src, int pos) {
		if (charAsInt < 0x80) {
			src[pos] 	= (byte)charAsInt;
			return 1;
		}
		else if (charAsInt < (1 << 11)) {
			src[pos] 	= (byte)(0xC0 | (charAsInt >> 6));
			src[++pos] 	= (byte)(0x80 | (charAsInt & 0x3F));
			return 2;
		}	
		else if (charAsInt < (1 << 16)) {
			src[pos] 	= (byte)(0xE0 | (charAsInt >> 12));
			src[++pos] 	= (byte)(0x80 | (charAsInt >>  6) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt        & 0x3F));
			return 3;
		}	
		else if (charAsInt < (1 << 21)) {
			src[pos] 	= (byte)(0xF0 | (charAsInt >> 18));
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 12) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >>  6) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt        & 0x3F));
			return 4;
		}
		else if (charAsInt < (1 << 26)) {
			src[pos] 	= (byte)(0xF8 | (charAsInt >> 24));
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 18) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 12) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >>  6) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt        & 0x3F));
			return 5;
		}	
		else if (charAsInt < (1 << 31)) {
			src[pos] 	= (byte)(0xFC | (charAsInt >> 30));
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 24) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 18) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >> 12) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt >>  6) & 0x3F);
			src[++pos] 	= (byte)(0x80 | (charAsInt        & 0x3F));
			return 6;
		}	
		else if (charAsInt == 0) {
			src[pos] 	= (byte)0xC0;
			src[++pos] 	= (byte)0x80;
			return -1;
		}
		return -1;
	}
</pre>

* file: /src/core/org/luaj/vm2/compiler/LexState.java
* proc: read_string
<pre style='overflow:auto'>
						if (c > UCHAR_MAX)
							lexerror("escape sequence too large", TK_STRING);
						save(c, false);	// NOTE: specify that c is integer and does not need conversion; EX: \128 -> 128 -> (char)128, not Utf8_encode(128)
</pre>

* file: /src/core/org/luaj/vm2/compiler/LexState.java
<pre style='overflow:auto'>
	void save(int c) {save(c, true);}
	void save(int c, boolean c_might_be_utf8) {
		int bytes_len = c_might_be_utf8 ? LuaString.Utf8_len_by_byte((byte)c) : 1;
		if (bytes_len > 1) {	// c is 1st byte of utf8 multi-byte sequence; read required number of bytes and convert to char; EX: ← is serialized in z as 226,134,144; c is currently 226; read 134 and 144 and convert to ←
			temp_bry[0] = (byte)c;
			for (int i = 1; i < bytes_len; i++) {
				nextChar();
				temp_bry[i] = (byte)current; 
			}
			c = LuaString.Utf8_decode_to_int(temp_bry, 0);			
		}
		if ( buff == null || nbuff + 1 > buff.length )
			buff = LuaC.realloc( buff, nbuff*2+1 );
		buff[nbuff++] = (char)c;
	}
	private static byte[] temp_bry = new byte[6];
</pre>


=== build.xml ===
* note: this change is needed to get luaj to compile with the String.format(String, double) call
* file: build.xml
<pre>
      old: 
        <javac destdir="build/jme/classes" encoding="utf-8" source="1.3" target="1.2" bootclasspathref="wtk-libs"
          srcdir="build/jme/src"/>
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.3" target="1.3"
          classpath="lib/bcel-5.2.jar"
          srcdir="build/jse/src"
          excludes="**/script/*,**/Lua2Java*,lua*"/>
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.5" target="1.5"
          classpath="build/jse/classes"
          srcdir="build/jse/src"
          includes="**/script/*,**/Lua2Java*"/>
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.3" target="1.3"
          classpath="build/jse/classes"
          srcdir="build/jse/src"
          includes="lua*"/>
      new:
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.5" target="1.5"
          classpath="lib/bcel-5.2.jar"
          srcdir="build/jse/src"
          excludes="**/script/*,**/Lua2Java*,lua*"/>
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.5" target="1.5"
          classpath="build/jse/classes"
          srcdir="build/jse/src"
          includes="**/script/*,**/Lua2Java*"/>
        <javac destdir="build/jse/classes" encoding="utf-8" source="1.5" target="1.5"
          classpath="build/jse/classes"
          srcdir="build/jse/src"
          includes="lua*"/>
</pre>

=== Luaj tests ===
<pre>
package org.luaj.vm2;
import org.luaj.vm2.lib.StringLib;
import junit.framework.*;
public class Xowa_tst extends TestCase {
	private Xowa_fxt fxt = new Xowa_fxt();
	public void test_tonumber_ws() {
		fxt.Test_tonumber_int("123"						, 123);
		fxt.Test_tonumber_int("\t\n\r 123\t\n\r"		, 123);
		fxt.Test_tonumber_nil("1a");
		fxt.Test_tonumber_nil("1 2");
		fxt.Test_tonumber_nil("");
		fxt.Test_tonumber_nil("\t\n\r \t\n\r");
	}
	public void test_gsub() {
		fxt.Test_gsub("abc", "a", "A", "Abc");	// basic
		fxt.Test_gsub("a#b", "#", "", "ab");	// match() fails when shortening string 
		fxt.Test_gsub("", "%b<>", "A", "");		// balance() fails with out of index when find is blank
	}
	public void test_format() {
		fxt.Test_format("%.1f"	, "1.23", "1.2");	// apply precision; 1 decimal place
		fxt.Test_format("(%.1f)", "1.23", "(1.2)");	// handle substring; format_string should be "%.1f" not "(%.1f)"
		fxt.Test_format("%0.1f"	, "1.23", "1.2");	// handle invalid precision of 0 
	}
}
class Xowa_fxt {
	public void Test_tonumber_int(String raw, int expd) {
		LuaString actl_str = LuaString.valueOf(raw);
		LuaInteger actl_int = (LuaInteger)actl_str.tonumber();		
		Assert.assertEquals(expd, actl_int.v);
	}
	public void Test_tonumber_nil(String raw) {
		LuaString actl_str = LuaString.valueOf(raw);
		Assert.assertEquals(LuaValue.NIL, actl_str.tonumber());
	}
	public void Test_gsub(String text, String regx, String repl, String expd) {
		Varargs actl_args = StringLib.gsub_test(LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(text), LuaValue.valueOf(regx), LuaValue.valueOf(repl)}));
		Assert.assertEquals(expd, actl_args.checkstring(1).tojstring());
	}
	public void Test_format(String fmt, String val, String expd) {
		Varargs actl_args = StringLib.format_test(LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(fmt), LuaValue.valueOf(val)}));
		Assert.assertEquals(expd, actl_args.checkstring(1).tojstring());
	}
}
</pre>

== Scribunto related ==
None of these changes affect the luaj_xowa.jar. They are noted for comprehensiveness's sake.

=== getfenv/setfenv deprecated ===
* add lua-compat-env
: source: https://github.com/davidm/lua-compat-env 
: target: $engines/LuaCommon/lualib/lua-compat-env .lua
* add alias to $engines/Luaj/mw_main.lua
:	_G.getfenv = require 'compat_env'.getfenv
:	_G.setfenv = require 'compat_env'.setfenv
* change xowa.jar to load debugLibrary
:	Globals.load(new DebugLib());

=== loadString deprecated ===
* add alias to $engines/Luaj/mw_main.lua
:	_G.loadstring = load

=== table.unpack deprecated ===
* add alias to $engines/Luaj/mw_main.lua
:	_G.unpack = table.unpack
