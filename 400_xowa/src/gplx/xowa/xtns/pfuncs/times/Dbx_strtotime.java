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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
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
import gplx.*;

public class Dbx_strtotime {

// inside parse.re
	private static final int EOI = 257;
//private static final int TIME	 =258;
//private static final int DATE	 =259;

	private static final int YYMAXFILL = 33;

	public static void DEBUG_OUTPUT(String str) {
		System.out.println(str);
	}
//public static void TIMELIB_HAVE_TZ() { }
//public static void YYFILL(int v) { }

	public static timelib_time timelib_strtotime(byte[] s) {
		int t;
		Dbx_scanner in = new Dbx_scanner(s, YYMAXFILL);

		do {
			t = Scan(in);
		} while (t != EOI);
		/* do funky checking whether the parsed time was valid time */
		if (in.time.have_time > 0 && !Dbx_scan_support.timelib_valid_time(in.time.h, in.time.i, in.time.s)) {
			Dbx_scan_support.add_warning(in, Dbx_scan_support.TIMELIB_WARN_INVALID_TIME, "The parsed time was invalid");
		}
		/* do funky checking whether the parsed date was valid date */
//		if (in.time.have_date && !Dbx_scan_support.timelib_valid_date( in.time.y, in.time.m, in.time.d)) {
//			Dbx_scan_support.add_warning(in, Dbx_scan_support.TIMELIB_WARN_INVALID_DATE, "The parsed date was invalid");
//		}
		if (in.errors.error_count + in.errors.warning_count > 0) {
			throw Err_.new_unhandled(0);
		}
		return in.time;
	}

	private static int Scan(Dbx_scanner s) {
		s.state = 0;
		//s.yych = 0;
		while (true) {
			switch (s.state) {
				case 0:
					s.yyaccept = 0;
					if ((s.lim - s.cursor) < 33) {
						return EOI; // ((YYLIMIT - YYCURSOR) < 33) YYFILL(33);
					}
					s.yych = s.src[s.cursor]; //yych = src[YYCURSOR];

					s.tok = s.cursor;
					s.len = 0;
//line 1011 "parse_date.re"

//line 892 "<stdout>"
//{
					switch (s.yych) {
						case 0x00:
						case '\n':
							s.state = 2;
							break;
						case '\t':
						case ' ':
							case6(s);
							break;
						case '(':
							case8(s);
							break;
						case '+':
						case '-':
							case9(s);
							break;
						case ',':
						case '.':
							++s.cursor;
							s.state = 7;
							break;
						case '0':
							case11(s);
							break;
						case '1':
							case12(s);
							break;
						case '2':
							case13(s);
							break;
						case '3':
							case14(s);
							break;
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							case15(s);
							break;
						case '@':
							case16(s);
							break;
						case 'A':
							case17(s);
							break;
						case 'B':
							case19(s);
							break;
						case 'C':
						case 'H':
						case 'K':
						case 'Q':
						case 'R':
						case 'U':
						case 'Z':
							case20(s);
							break;
						case 'D':
							case21(s);
							break;
						case 'E':
							case22(s);
							break;
						case 'F':
							case23(s);
							break;
						case 'G':
							case24(s);
							break;
						case 'I':
							case25(s);
							break;
						case 'J':
							case26(s);
							break;
						case 'L':
							case27(s);
							break;
						case 'M':
							case28(s);
							break;
						case 'N':
							case29(s);
							break;
						case 'O':
							case30(s);
							break;
						case 'P':
							case31(s);
							break;
						case 'S':
							case32(s);
							break;
						case 'T':
							case33(s);
							break;
						case 'V':
							case34(s);
							break;
						case 'W':
							case35(s);
							break;
						case 'X':
							case36(s);
							break;
						case 'Y':
							case37(s);
							break;
						case 'a':
							case38(s);
							break;
						case 'b':
							case39(s);
							break;
						case 'c':
						case 'g':
						case 'h':
						case 'i':
						case 'k':
						case 'q':
						case 'r':
						case 'u':
						case 'v':
						case 'x':
						case 'z':
							case40(s);
							break;
						case 'd':
							case41(s);
							break;
						case 'e':
							case42(s);
							break;
						case 'f':
							case43(s);
							break;
						case 'j':
							case44(s);
							break;
						case 'l':
							case45(s);
							break;
						case 'm':
							case46(s);
							break;
						case 'n':
							case47(s);
							break;
						case 'o':
							case48(s);
							break;
						case 'p':
							case49(s);
							break;
						case 's':
							case50(s);
							break;
						case 't':
							case51(s);
							break;
						case 'w':
							case52(s);
							break;
						case 'y':
							case53(s);
							break;
						default:
							s.state = 4;
							break;
					}

					break;
				case 2:
					++s.cursor;
//line 1808 "parse_date.re"
					 {
						s.pos = s.cursor;
						s.line++;
						s.state = 0;
						break;
					}
//line 980 "<stdout>"

				case 4:
					++s.cursor;

				case 5: //line 1814 "parse_date.re"
				{
					Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_UNEXPECTED_CHARACTER, "Unexpected character");
					s.state = 0;
					break;
				}
//line 989 "<stdout>"

				case 6:
					case6(s);
					break;
				case 7: //line 1803 "parse_date.re"
				{
					s.state = 0;
					break;
				}
//line 1013 "<stdout>"

				case 8:
					case8(s);
					break;
				case 9:
					case9(s);
					break;
				case 10:
					++s.cursor;
					s.state = 7;
					break;

				case 11:
					case11(s);
					break;
				case 12:
					case12(s);
					break;
				case 13:
					case13(s);
					break;
				case 14:
					case14(s);
					break;
				case 15:
					case15(s);
					break;
				case 16:
					case16(s);
					break;
				case 17:
					case17(s);
					break;
				case 18: //line 1719 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("tzcorrection | tz");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_TZ(s);
					s.time.z = Dbx_scan_support.timelib_parse_zone(s);
					if (s.tz_not_found != 0) {
						Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_TIMEZONE;
				}
//line 1456 "<stdout>"

				case 19:
					case19(s);
					break;
				case 20:
					case20(s);
					break;
				case 21:
					case21(s);
					break;
				case 22:
					case22(s);
					break;
				case 23:
					case23(s);
					break;
				case 24:
					case24(s);
					break;
				case 25:
					case25(s);
					break;
				case 26:
					case26(s);
					break;
				case 27:
					case27(s);
					break;
				case 28:
					case28(s);
					break;
				case 29:
					case29(s);
					break;
				case 30:
					case30(s);
					break;
				case 31:
					case31(s);
					break;
				case 32:
					case32(s);
					break;
				case 33:
					case33(s);
					break;
				case 34:
					case34(s);
					break;
				case 35:
					case35(s);
					break;
				case 36:
					case36(s);
					break;
				case 37:
					case37(s);
					break;
				case 38:
					case38(s);
					break;
				case 39:
					case39(s);
					break;
				case 40:
					case40(s);
					break;
				case 41:
					case41(s);
					break;
				case 42:
					case42(s);
					break;
				case 43:
					case43(s);
					break;
				case 44:
					case44(s);
					break;
				case 45:
					case45(s);
					break;
				case 46:
					case46(s);
					break;
				case 47:
					case47(s);
					break;
				case 48:
					case48(s);
					break;
				case 49:
					case49(s);
					break;
				case 50:
					case50(s);
					break;
				case 51:
					case51(s);
					break;
				case 52:
					case52(s);
					break;
				case 53:
					case53(s);
					break;
				case 54:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case54(s);
					break;
				case 56:
					case56(s);
					break;
				case 57:
					++s.cursor;
					if ((s.lim - s.cursor) < 12) {
						return EOI;
					}
					case57(s);
					break;
				case 59:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case59(s);
					break;
				case 61:
					case61(s);
					break;
				case 62:
					case62(s);
					break;
				case 63:
					case63(s);
					break;
				case 64:
					case64(s);
					break;
				case 65:
					++s.cursor;
					if ((s.lim - s.cursor) < 13) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 66:
					case66(s);
					break;
				case 67:
					case67(s);
					break;
				case 68:
					case68(s);
					break;
				case 69:
					case69(s);
					break;
				case 70:
					case70(s);
					break;
				case 71:
					case71(s);
					break;
				case 72:
					case72(s);
					break;
				case 73:
					case73(s);
					break;
				case 74:
					case74(s);
					break;
				case 75:
					case75(s);
					break;
				case 76:
					case76(s);
					break;
				case 77:
					case77(s);
					break;
				case 78: //line 1469 "parse_date.re"
				{
//		DEBUG_OUTPUT("datenoyearrev");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					Dbx_scan_support.timelib_skip_day_suffix(s);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_TEXT;
				}
//line 4036 "<stdout>"

				case 79:
					case79(s);
					break;
				case 80:
					case80(s);
					break;
				case 81:
					case81(s);
					break;
				case 82:
					case82(s);
					break;
				case 83:
					case83(s);
					break;
				case 84:
					case84(s);
					break;
				case 85:
					case85(s);
					break;
				case 86:
					case86(s);
					break;
				case 87:
					case87(s);
					break;
				case 88:
					case88(s);
					break;
				case 89:
					case89(s);
					break;
				case 90:
					case90(s);
					break;
				case 91:
					case91(s);
					break;
				case 92:
					case92(s);
					break;
				case 93:
					case93(s);
					break;
				case 94:
					case94(s);
					break;
				case 95:
					case95(s);
					break;
				case 96:
					++s.cursor;
					if ((s.lim - s.cursor) < 13) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 97:
					case97(s);
					break;
				case 98:
					case98(s);
					break;
				case 99:
					case99(s);
					break;
				case 100:
					case100(s);
					break;
				case 101:
					case101(s);
					break;
				case 102:
					case102(s);
					break;
				case 103:
					case103(s);
					break;
				case 104:
					case104(s);
					break;
				case 105:
					case105(s);
					break;
				case 106:
					s.yyaccept = 4;
					s.ptr = ++s.cursor;
					if ((s.lim - s.cursor) < 7) {
						return EOI;
					}
					case106(s);
					break;
				case 108: //line 1071 "parse_date.re"
				{
					int i;

					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
					Dbx_scan_support.TIMELIB_UNHAVE_DATE(s);
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);
					Dbx_scan_support.TIMELIB_HAVE_TZ(s);

					i = Dbx_scan_support.timelib_get_unsigned_nr(s, 24);
					s.time.y = 1970;
					s.time.m = 1;
					s.time.d = 1;
					s.time.h = s.time.i = s.time.s = 0;
					s.time.us = 0;
					s.time.relative.s += i;
					s.time.is_localtime = true;
					s.time.zone_type = Dbx_scan_support.TIMELIB_ZONETYPE_OFFSET;
					s.time.z = 0;
					s.time.dst = 0;

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 4429 "<stdout>"

				case 109:
					++s.cursor;
					s.state = 18;
					break;

				case 110:
					case110(s);
					break;
				case 111:
					case111(s);
					break;
				case 112:
					case112(s);
					break;
				case 113:
					case113(s);
					break;
				case 114:
					case114(s);
					break;
				case 115:
					case115(s);
					break;
				case 116:
					case116(s);
					break;
				case 117:
					case117(s);
					break;
				case 118:
					case118(s);
					break;
				case 119:
					case119(s);
					break;
				case 120:
					case120(s);
					break;
				case 121:
					case121(s);
					break;
				case 122:
					case122(s);
					break;
				case 123:
					case123(s);
					break;
				case 124:
					case124(s);
					break;
				case 125:
					case125(s);
					break;
				case 126:
					case126(s);
					break;
				case 127:
					case127(s);
					break;
				case 128:
					case128(s);
					break;
				case 129:
					case129(s);
					break;
				case 130:
					case130(s);
					break;
				case 131:
					case131(s);
					break;
				case 132:
					case132(s);
					break;
				case 133:
					case133(s);
					break;
				case 134:
					case134(s);
					break;
				case 135:
					++s.cursor;
					if ((s.lim - s.cursor) < 23) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 136:
					case136(s);
					break;
				case 137:
					case137(s);
					break;
				case 138:
					case138(s);
					break;
				case 139:
					case139(s);
					break;
				case 140:
					case140(s);
					break;
				case 141:
					case141(s);
					break;
				case 142:
					case142(s);
					break;
				case 143:
					case143(s);
					break;
				case 144:
					case144(s);
					break;
				case 145:
					case145(s);
					break;
				case 146:
					case146(s);
					break;
				case 147:
					case147(s);
					break;
				case 148:
					case148(s);
					break;
				case 149:
					case149(s);
					break;
				case 150:
					case150(s);
					break;
				case 151:
					case151(s);
					break;
				case 152:
					case152(s);
					break;
				case 153:
					case153(s);
					break;
				case 154:
					case154(s);
					break;
				case 155:
					case155(s);
					break;
				case 156:
					case156(s);
					break;
				case 157:
					case157(s);
					break;
				case 158:
					case158(s);
					break;
				case 159:
					case159(s);
					break;
				case 160:
					case160(s);
					break;
				case 161:
					case161(s);
					break;
				case 162:
					case162(s);
					break;
				case 163:
					case163(s);
					break;
				case 164:
					case164(s);
					break;
				case 165:
					case165(s);
					break;
				case 166:
					case166(s);
					break;
				case 167:
					case167(s);
					break;
				case 168:
					case168(s);
					break;
				case 169:
					case169(s);
					break;
				case 170:
					case170(s);
					break;
				case 171:
					case171(s);
					break;
				case 172:
					case172(s);
					break;
				case 173:
					case173(s);
					break;
				case 174:
					case174(s);
					break;
				case 175:
					case175(s);
					break;
				case 176:
					case176(s);
					break;
				case 177:
					case177(s);
					break;
				case 178:
					case178(s);
					break;
				case 179:
					case179(s);
					break;
				case 180:
					case180(s);
					break;
				case 181:
					case181(s);
					break;
				case 182:
					case182(s);
					break;
				case 183:
					case183(s);
					break;
				case 184:
					case184(s);
					break;
				case 185:
					case185(s);
					break;
				case 186:
					++s.cursor;
					if ((s.lim - s.cursor) < 12) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 187:
					case187(s);
					break;
				case 188:
					case188(s);
					break;
				case 189:
					case189(s);
					break;
				case 190:
					case190(s);
					break;
				case 191:
					case191(s);
					break;
				case 192:
					case192(s);
					break;
				case 193:
					case193(s);
					break;
				case 194:
					case194(s);
					break;
				case 195:
					case195(s);
					break;
				case 196:
					++s.cursor;
					if ((s.lim - s.cursor) < 13) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 197:
					case197(s);
					break;
				case 198:
					case198(s);
					break;
				case 199:
					case199(s);
					break;
				case 200:
					case200(s);
					break;
				case 201:
					case201(s);
					break;
				case 202:
					case202(s);
					break;
				case 203:
					case203(s);
					break;
				case 204:
					case204(s);
					break;
				case 205:
					case205(s);
					break;
				case 206:
					case206(s);
					break;
				case 207:
					case207(s);
					break;
				case 208:
					case208(s);
					break;
				case 209: //line 1224 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("timeshort24 | timelong24 | iso8601long");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
						s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);

						if (s.src[s.ptr] == '.') {
							s.time.us = Dbx_scan_support.timelib_get_frac_nr(s, 8);
						}
					}

					if (s.ptr < s.cursor) {
						s.time.z = Dbx_scan_support.timelib_parse_zone(s);
						if (s.tz_not_found != 0) {
							Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
						}
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_TIME24_WITH_ZONE;
				}
//line 9052 "<stdout>"

				case 210:
					case210(s);
					break;
				case 211:
					case211(s);
					break;
				case 212:
					case212(s);
					break;
				case 213:
					case213(s);
					break;
				case 214: //line 1316 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("americanshort | american");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					if (s.src[s.ptr] == '/') {
						s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
						Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_AMERICAN;
				}
//line 9137 "<stdout>"

				case 215:
					case215(s);
					break;
				case 216:
					case216(s);
					break;
				case 217:
					case217(s);
					break;
				case 218:
					case218(s);
					break;
				case 219:
					case219(s);
					break;
				case 220:
					case220(s);
					break;
				case 221:
					case221(s);
					break;
				case 222:
					case222(s);
					break;
				case 223:
					case223(s);
					break;
				case 224:
					case224(s);
					break;
				case 225:
					case225(s);
					break;
				case 226:
					case226(s);
					break;
				case 227:
					case227(s);
					break;
				case 228:
					case228(s);
					break;
				case 229:
					case229(s);
					break;
				case 230:
					case230(s);
					break;
				case 231:
					case231(s);
					break;
				case 232:
					case232(s);
					break;
				case 233:
					case233(s);
					break;
				case 234:
					case234(s);
					break;
				case 235:
					case235(s);
					break;
				case 236:
					case236(s);
					break;
				case 237:
					case237(s);
					break;
				case 238:
					++s.cursor;
					if ((s.lim - s.cursor) < 4) {
						return EOI;
					}
					case238(s);
					break;
				case 240:
					case240(s);
					break;
				case 241: //line 1386 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("datefull");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					Dbx_scan_support.timelib_skip_day_suffix(s);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_FULL;
				}
//line 9402 "<stdout>"

				case 242:
					case242(s);
					break;
				case 243:
					case243(s);
					break;
				case 244:
					case244(s);
					break;
				case 245:
					case245(s);
					break;
				case 246:
					case246(s);
					break;
				case 247:
					case247(s);
					break;
				case 248:
					case248(s);
					break;
				case 249:
					case249(s);
					break;
				case 250: //line 1787 "parse_date.re"
				{
					int i;
//		DEBUG_OUTPUT("relative");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

					while (s.ptr < s.cursor) {
						i = Dbx_scan_support.timelib_get_unsigned_nr(s, 24);
						Dbx_scan_support.timelib_eat_spaces(s);
						Dbx_scan_support.timelib_set_relative(s, i, 1);
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 9511 "<stdout>"

				case 251:
					case251(s);
					break;
				case 252:
					case252(s);
					break;
				case 253:
					case253(s);
					break;
				case 254:
					case254(s);
					break;
				case 255:
					case255(s);
					break;
				case 256:
					case256(s);
					break;
				case 257:
					case257(s);
					break;
				case 258:
					case258(s);
					break;
				case 259:
					case259(s);
					break;
				case 260:
					case260(s);
					break;
				case 261:
					case261(s);
					break;
				case 262:
					case262(s);
					break;
				case 263:
					case263(s);
					break;
				case 264:
					case264(s);
					break;
				case 265:
					case265(s);
					break;
				case 266:
					case266(s);
					break;
				case 267:
					case267(s);
					break;
				case 268:
					case268(s);
					break;
				case 269:
					case269(s);
					break;
				case 270:
					case270(s);
					break;
				case 271:
					case271(s);
					break;
				case 272:
					case272(s);
					break;
				case 273:
					case273(s);
					break;
				case 274:
					case274(s);
					break;
				case 275:
					case275(s);
					break;
				case 276:
					case276(s);
					break;
				case 277:
					case277(s);
					break;
				case 278: //line 1629 "parse_date.re"
				{
//		DEBUG_OUTPUT("ago");
					s.cur = s.cursor;
					s.ptr = s.tok;
					s.time.relative.y = 0 - s.time.relative.y;
					s.time.relative.m = 0 - s.time.relative.m;
					s.time.relative.d = 0 - s.time.relative.d;
					s.time.relative.h = 0 - s.time.relative.h;
					s.time.relative.i = 0 - s.time.relative.i;
					s.time.relative.s = 0 - s.time.relative.s;
					s.time.relative.weekday = 0 - s.time.relative.weekday;
					if (s.time.relative.weekday == 0) {
						s.time.relative.weekday = -7;
					}
					if (s.time.relative.have_special_relative && s.time.relative.special_type == Dbx_scan_support.TIMELIB_SPECIAL_WEEKDAY) {
						s.time.relative.special_amount = 0 - s.time.relative.special_amount;
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_AGO;
				}
//line 9940 "<stdout>"

				case 279:
					case279(s);
					break;
				case 280: //line 1709 "parse_date.re"
				{
//		DEBUG_OUTPUT("monthtext");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.m = Dbx_scan_support.timelib_lookup_month(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_TEXT;
				}
//line 10024 "<stdout>"

				case 281:
					case281(s);
					break;
				case 282:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case282(s);
					break;
				case 283:
					case283(s);
					break;
				case 284:
					case284(s);
					break;
				case 285:
					case285(s);
					break;
				case 286:
					case286(s);
					break;
				case 287:
					case287(s);
					break;
				case 288:
					case288(s);
					break;
				case 289:
					case289(s);
					break;
				case 290:
					case290(s);
					break;
				case 291:
					case291(s);
					break;
				case 292:
					case292(s);
					break;
				case 293:
					case293(s);
					break;
				case 294:
					case294(s);
					break;
				case 295:
					case295(s);
					break;
				case 296:
					case296(s);
					break;
				case 297:
					case297(s);
					break;
				case 298:
					case298(s);
					break;
				case 299:
					case299(s);
					break;
				case 300: //line 1650 "parse_date.re"
				{
//		const Dbx_scan_support.timelib_relunit* relunit;
//		DEBUG_OUTPUT("daytext");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
					Dbx_scan_support.TIMELIB_HAVE_WEEKDAY_RELATIVE(s);
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);
//		relunit = Dbx_scan_support.timelib_lookup_relunit((char**) &ptr);
//		s.time.relative.weekday = relunit->multiplier;
					if (s.time.relative.weekday_behavior != 2) {
						s.time.relative.weekday_behavior = 1;
					}

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_WEEKDAY;
				}
//line 11262 "<stdout>"

				case 301:
					case301(s);
					break;
				case 302:
					case302(s);
					break;
				case 303:
					case303(s);
					break;
				case 304:
					case304(s);
					break;
				case 305:
					case305(s);
					break;
				case 306:
					case306(s);
					break;
				case 307:
					case307(s);
					break;
				case 308:
					case308(s);
					break;
				case 309:
					case309(s);
					break;
				case 310: //line 1455 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("datetextual | datenoyear");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_TEXT;
				}
//line 11799 "<stdout>"

				case 311:
					s.yyaccept = 11;
					s.ptr = ++s.cursor;
					if ((s.lim - s.cursor) < 18) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 312:
					case312(s);
					break;
				case 313:
					case313(s);
					break;
				case 314:
					case314(s);
					break;
				case 315:
					case315(s);
					break;
				case 316:
					case316(s);
					break;
				case 317:
					case317(s);
					break;
				case 318:
					case318(s);
					break;
				case 319:
					case319(s);
					break;
				case 320:
					case320(s);
					break;
				case 321:
					case321(s);
					break;
				case 322:
					case322(s);
					break;
				case 323:
					case323(s);
					break;
				case 324:
					case324(s);
					break;
				case 325:
					case325(s);
					break;
				case 326:
					case326(s);
					break;
				case 327:
					case327(s);
					break;
				case 328:
					case328(s);
					break;
				case 329:
					case329(s);
					break;
				case 330:
					case330(s);
					break;
				case 331:
					case331(s);
					break;
				case 332:
					case332(s);
					break;
				case 333:
					case333(s);
					break;
				case 334:
					case334(s);
					break;
				case 335:
					case335(s);
					break;
				case 336: //line 1028 "parse_date.re"
				{
//		DEBUG_OUTPUT("now");
					s.cur = s.cursor;
					s.ptr = s.tok;

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 13128 "<stdout>"

				case 337:
					case337(s);
					break;
				case 338:
					case338(s);
					break;
				case 339:
					case339(s);
					break;
				case 340:
					case340(s);
					break;
				case 341:
					case341(s);
					break;
				case 342:
					case342(s);
					break;
				case 343:
					case343(s);
					break;
				case 344:
					case344(s);
					break;
				case 345:
					case345(s);
					break;
				case 346:
					case346(s);
					break;
				case 347:
					case347(s);
					break;
				case 348:
					case348(s);
					break;
				case 349:
					case349(s);
					break;
				case 350:
					case350(s);
					break;
				case 351:
					case351(s);
					break;
				case 352:
					case352(s);
					break;
				case 353:
					case353(s);
					break;
				case 354:
					case354(s);
					break;
				case 355:
					case355(s);
					break;
				case 356:
					case356(s);
					break;
				case 357:
					case357(s);
					break;
				case 358:
					case358(s);
					break;
				case 359:
					case359(s);
					break;
				case 360:
					case360(s);
					break;
				case 361:
					case361(s);
					break;
				case 362:
					case362(s);
					break;
				case 363:
					case363(s);
					break;
				case 364:
					case364(s);
					break;
				case 365:
					case365(s);
					break;
				case 366:
					case366(s);
					break;
				case 367:
					case367(s);
					break;
				case 368:
					case368(s);
					break;
				case 369:
					case369(s);
					break;
				case 370:
					case370(s);
					break;
				case 371:
					case371(s);
					break;
				case 372:
					case372(s);
					break;
				case 373:
					case373(s);
					break;
				case 374:
					case374(s);
					break;
				case 375:
					case375(s);
					break;
				case 376:
					case376(s);
					break;
				case 377:
					case377(s);
					break;
				case 378:
					case378(s);
					break;
				case 379:
					case379(s);
					break;
				case 380:
					case380(s);
					break;
				case 381:
					case381(s);
					break;
				case 382:
					case382(s);
					break;
				case 383:
					case383(s);
					break;
				case 384:
					case384(s);
					break;
				case 385:
					case385(s);
					break;
				case 386:
					case386(s);
					break;
				case 387:
					case387(s);
					break;
				case 388:
					case388(s);
					break;
				case 389:
					case389(s);
					break;
				case 390:
					case390(s);
					break;
				case 391:
					case391(s);
					break;
				case 392:
					case392(s);
					break;
				case 393:
					case393(s);
					break;
				case 394:
					case394(s);
					break;
				case 395:
					case395(s);
					break;
				case 396:
					case396(s);
					break;
				case 397:
					case397(s);
					break;
				case 398:
					case398(s);
					break;
				case 399:
					case399(s);
					break;
				case 400:
					case400(s);
					break;
				case 401: //line 1250 "parse_date.re"
				{
//		DEBUG_OUTPUT("gnunocolon");
					s.cur = s.cursor;
					s.ptr = s.tok;
					switch (s.time.have_time) {
						case 0:
							s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
							s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
							s.time.s = 0;
							break;
						case 1:
							s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
							break;
						default:
//				Dbx_scan_support.TIMELIB_DEINIT;
							Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_DOUBLE_TIME, "Double time specification");
							return Dbx_scan_support.TIMELIB_ERROR;
					}
					s.time.have_time++;
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_GNU_NOCOLON;
				}
//line 15690 "<stdout>"

				case 402:
					case402(s);
					break;
				case 403: //line 1620 "parse_date.re"
				{
//		DEBUG_OUTPUT("year4");
					s.cur = s.cursor;
					s.ptr = s.tok;
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_CLF;
				}
//line 15752 "<stdout>"

				case 404:
					case404(s);
					break;
				case 405:
					case405(s);
					break;
				case 406:
					case406(s);
					break;
				case 407:
					case407(s);
					break;
				case 408:
					case408(s);
					break;
				case 409:
					case409(s);
					break;
				case 410:
					case410(s);
					break;
				case 411:
					case411(s);
					break;
				case 412:
					case412(s);
					break;
				case 413:
					case413(s);
					break;
				case 414:
					case414(s);
					break;
				case 415:
					case415(s);
					break;
				case 416:
					case416(s);
					break;
				case 417:
					case417(s);
					break;
				case 418:
					case418(s);
					break;
				case 419:
					case419(s);
					break;
				case 420:
					case420(s);
					break;
				case 421:
					case421(s);
					break;
				case 422:
					case422(s);
					break;
				case 423:
					case423(s);
					break;
				case 424:
					case424(s);
					break;
				case 425:
					case425(s);
					break;
				case 426:
					case426(s);
					break;
				case 427:
					case427(s);
					break;
				case 428:
					case428(s);
					break;
				case 429:
					case429(s);
					break;
				case 430:
					case430(s);
					break;
				case 431:
					case431(s);
					break;
				case 432:
					case432(s);
					break;
				case 433:
					case433(s);
					break;
				case 434:
					case434(s);
					break;
				case 435:
					case435(s);
					break;
				case 436:
					++s.cursor;
//line 1187 "parse_date.re"
					 {
//		DEBUG_OUTPUT("timetiny12 | timeshort12 | timelong12");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_TIME(s);
						s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
							s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
							if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
								s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);
							}
						}
						s.time.h += Dbx_scan_support.timelib_meridian(s, s.time.h);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_TIME12;
					}
//line 16213 "<stdout>"

				case 438:
					case438(s);
					break;
				case 439:
					case439(s);
					break;
				case 440:
					case440(s);
					break;
				case 441:
					case441(s);
					break;
				case 442:
					case442(s);
					break;
				case 443:
					case443(s);
					break;
				case 444:
					case444(s);
					break;
				case 445:
					case445(s);
					break;
				case 446:
					case446(s);
					break;
				case 447:
					case447(s);
					break;
				case 448:
					case448(s);
					break;
				case 449:
					case449(s);
					break;
				case 450:
					case450(s);
					break;
				case 451:
					case451(s);
					break;
				case 452:
					case452(s);
					break;
				case 453:
					case453(s);
					break;
				case 454:
					case454(s);
					break;
				case 455:
					s.yyaccept = 2;
					s.ptr = ++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case455(s);
					break;
				case 457:
					case457(s);
					break;
				case 458:
					case458(s);
					break;
				case 459:
					case459(s);
					break;
				case 460:
					case460(s);
					break;
				case 461:
					case461(s);
					break;
				case 462:
					case462(s);
					break;
				case 463:
					case463(s);
					break;
				case 464:
					case464(s);
					break;
				case 465:
					case465(s);
					break;
				case 466:
					case466(s);
					break;
				case 467:
					case467(s);
					break;
				case 468:
					case468(s);
					break;
				case 469:
					case469(s);
					break;
				case 470:
					case470(s);
					break;
				case 471:
					case471(s);
					break;
				case 472:
					case472(s);
					break;
				case 473:
					case473(s);
					break;
				case 474:
					case474(s);
					break;
				case 475:
					case475(s);
					break;
				case 476:
					case476(s);
					break;
				case 477:
					case477(s);
					break;
				case 478:
					case478(s);
					break;
				case 479:
					case479(s);
					break;
				case 480:
					case480(s);
					break;
				case 481:
					case481(s);
					break;
				case 482:
					case482(s);
					break;
				case 483:
					case483(s);
					break;
				case 484:
					case484(s);
					break;
				case 485:
					case485(s);
					break;
				case 486:
					case486(s);
					break;
				case 487:
					case487(s);
					break;
				case 488:
					case488(s);
					break;
				case 489:
					case489(s);
					break;
				case 490:
					case490(s);
					break;
				case 491:
					case491(s);
					break;
				case 492:
					s.yyaccept = 11;
					s.ptr = ++s.cursor;
					s.yych = s.src[s.cursor];
					if (s.yych <= 0x00) {
						if (s.yych <= 0x00) {
							case309(s);
							break;
						}
					}
					case312(s);
					break;

				case 493:
					case493(s);
					break;
				case 494:
					case494(s);
					break;
				case 495:
					case495(s);
					break;
				case 496:
					case496(s);
					break;
				case 497:
					case497(s);
					break;
				case 498:
					case498(s);
					break;
				case 499:
					case499(s);
					break;
				case 500:
					case500(s);
					break;
				case 501:
					case501(s);
					break;
				case 502:
					case502(s);
					break;
				case 503:
					case503(s);
					break;
				case 504:
					case504(s);
					break;
				case 505: //line 1037 "parse_date.re"
				{
//		DEBUG_OUTPUT("noon");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					s.time.h = 12;

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 18963 "<stdout>"

				case 506:
					case506(s);
					break;
				case 507:
					case507(s);
					break;
				case 508:
					case508(s);
					break;
				case 509:
					case509(s);
					break;
				case 510:
					case510(s);
					break;
				case 511:
					case511(s);
					break;
				case 512:
					case512(s);
					break;
				case 513:
					case513(s);
					break;
				case 514:
					case514(s);
					break;
				case 515:
					case515(s);
					break;
				case 516:
					case516(s);
					break;
				case 517:
					case517(s);
					break;
				case 518:
					case518(s);
					break;
				case 519:
					case519(s);
					break;
				case 520:
					case520(s);
					break;
				case 521:
					case521(s);
					break;
				case 522:
					case522(s);
					break;
				case 523:
					case523(s);
					break;
				case 524:
					case524(s);
					break;
				case 525:
					case525(s);
					break;
				case 526:
					case526(s);
					break;
				case 527:
					case527(s);
					break;
				case 528:
					case528(s);
					break;
				case 529:
					case529(s);
					break;
				case 530:
					case530(s);
					break;
				case 531:
					case531(s);
					break;
				case 532:
					case532(s);
					break;
				case 533:
					case533(s);
					break;
				case 534:
					case534(s);
					break;
				case 535:
					case535(s);
					break;
				case 536:
					case536(s);
					break;
				case 537:
					case537(s);
					break;
				case 538:
					case538(s);
					break;
				case 539:
					case539(s);
					break;
				case 540:
					case540(s);
					break;
				case 541:
					case541(s);
					break;
				case 542:
					case542(s);
					break;
				case 543:
					case543(s);
					break;
				case 544:
					case544(s);
					break;
				case 545: //line 1372 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("gnudateshort");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_DATE;
				}
//line 21124 "<stdout>"

				case 546:
					case546(s);
					break;
				case 547:
					case547(s);
					break;
				case 548:
					case548(s);
					break;
				case 549:
					case549(s);
					break;
				case 550:
					case550(s);
					break;
				case 551:
					case551(s);
					break;
				case 552:
					case552(s);
					break;
				case 553:
					case553(s);
					break;
				case 554:
					case554(s);
					break;
				case 555:
					case555(s);
					break;
				case 556:
					case556(s);
					break;
				case 557:
					case557(s);
					break;
				case 558:
					case558(s);
					break;
				case 559:
					case559(s);
					break;
				case 560:
					case560(s);
					break;
				case 561:
					++s.cursor;
					if ((s.lim - s.cursor) < 12) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 562:
					case562(s);
					break;
				case 563:
					case563(s);
					break;
				case 564:
					case564(s);
					break;
				case 565:
					case565(s);
					break;
				case 566:
					case566(s);
					break;
				case 567:
					case567(s);
					break;
				case 568:
					case568(s);
					break;
				case 569:
					case569(s);
					break;
				case 570:
					case570(s);
					break;
				case 571:
					case571(s);
					break;
				case 572:
					case572(s);
					break;
				case 573:
					case573(s);
					break;
				case 574:
					case574(s);
					break;
				case 575:
					case575(s);
					break;
				case 576:
					case576(s);
					break;
				case 577: //line 1441 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("datenodayrev");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.d = 1;
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_NO_DAY;
				}
//line 21562 "<stdout>"

				case 578:
					case578(s);
					break;
				case 579:
					case579(s);
					break;
				case 580:
					case580(s);
					break;
				case 581:
					case581(s);
					break;
				case 582:
					case582(s);
					break;
				case 583:
					case583(s);
					break;
				case 584:
					case584(s);
					break;
				case 585:
					case585(s);
					break;
				case 586:
					case586(s);
					break;
				case 587:
					case587(s);
					break;
				case 588:
					case588(s);
					break;
				case 589:
					case589(s);
					break;
				case 590:
					case590(s);
					break;
				case 591:
					case591(s);
					break;
				case 592:
					++s.cursor;
					s.state = 250;
					break;

				case 593:
					case593(s);
					break;
				case 594:
					case594(s);
					break;
				case 595:
					case595(s);
					break;
				case 596:
					case596(s);
					break;
				case 597:
					case597(s);
					break;
				case 598:
					case598(s);
					break;
				case 599:
					case599(s);
					break;
				case 600:
					case600(s);
					break;
				case 601:
					case601(s);
					break;
				case 602:
					case602(s);
					break;
				case 603:
					case603(s);
					break;
				case 604:
					case604(s);
					break;
				case 605:
					case605(s);
					break;
				case 606:
					case606(s);
					break;
				case 607:
					case607(s);
					break;
				case 608:
					case608(s);
					break;
				case 609:
					case609(s);
					break;
				case 610:
					case610(s);
					break;
				case 611:
					case611(s);
					break;
				case 612:
					case612(s);
					break;
				case 613:
					case613(s);
					break;
				case 614:
					case614(s);
					break;
				case 615:
					case615(s);
					break;
				case 616:
					++s.cursor;
					if ((s.lim - s.cursor) < 5) {
						return EOI;
					}
					case616(s);
					break;
				case 618:
					case618(s);
					break;
				case 619:
					case619(s);
					break;
				case 620:
					case620(s);
					break;
				case 621:
					case621(s);
					break;
				case 622:
					case622(s);
					break;
				case 623:
					case623(s);
					break;
				case 624:
					case624(s);
					break;
				case 625:
					case625(s);
					break;
				case 626:
					case626(s);
					break;
				case 627:
					case627(s);
					break;
				case 628:
					case628(s);
					break;
				case 629:
					case629(s);
					break;
				case 630:
					case630(s);
					break;
				case 631:
					case631(s);
					break;
				case 632:
					case632(s);
					break;
				case 633:
					case633(s);
					break;
				case 634:
					case634(s);
					break;
				case 635:
					case635(s);
					break;
				case 636:
					case636(s);
					break;
				case 637:
					case637(s);
					break;
				case 638:
					case638(s);
					break;
				case 639:
					case639(s);
					break;
				case 640:
					case640(s);
					break;
				case 641:
					case641(s);
					break;
				case 642:
					case642(s);
					break;
				case 643:
					case643(s);
					break;
				case 644:
					case644(s);
					break;
				case 645:
					case645(s);
					break;
				case 646:
					case646(s);
					break;
				case 647:
					case647(s);
					break;
				case 648:
					case648(s);
					break;
				case 649:
					case649(s);
					break;
				case 650:
					case650(s);
					break;
				case 651:
					case651(s);
					break;
				case 652:
					case652(s);
					break;
				case 653:
					case653(s);
					break;
				case 654:
					case654(s);
					break;
				case 655:
					case655(s);
					break;
				case 656:
					case656(s);
					break;
				case 657:
					case657(s);
					break;
				case 658:
					case658(s);
					break;
				case 659:
					case659(s);
					break;
				case 660:
					case660(s);
					break;
				case 661:
					case661(s);
					break;
				case 662:
					case662(s);
					break;
				case 663:
					case663(s);
					break;
				case 664:
					case664(s);
					break;
				case 665:
					case665(s);
					break;
				case 666:
					case666(s);
					break;
				case 667:
					case667(s);
					break;
				case 668:
					case668(s);
					break;
				case 669:
					case669(s);
					break;
				case 670:
					case670(s);
					break;
				case 671:
					case671(s);
					break;
				case 672:
					++s.cursor;
//line 1427 "parse_date.re"
					 {
//						int length = 0;
//		DEBUG_OUTPUT("datenoday");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_DATE(s);
						s.time.m = Dbx_scan_support.timelib_get_month(s);
						s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
						s.time.d = 1;
						Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_DATE_NO_DAY;
					}
//line 23807 "<stdout>"

				case 674:
					case674(s);
					break;
				case 675:
					case675(s);
					break;
				case 676:
					case676(s);
					break;
				case 677:
					++s.cursor;
					if ((s.lim - s.cursor) < 12) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 678:
					case678(s);
					break;
				case 679:
					case679(s);
					break;
				case 680:
					case680(s);
					break;
				case 681:
					case681(s);
					break;
				case 682:
					case682(s);
					break;
				case 683:
					case683(s);
					break;
				case 684:
					case684(s);
					break;
				case 685:
					case685(s);
					break;
				case 686:
					case686(s);
					break;
				case 687:
					case687(s);
					break;
				case 688:
					case688(s);
					break;
				case 689:
					case689(s);
					break;
				case 690:
					case690(s);
					break;
				case 691:
					case691(s);
					break;
				case 692:
					case692(s);
					break;
				case 693:
					case693(s);
					break;
				case 694:
					case694(s);
					break;
				case 695:
					case695(s);
					break;
				case 696: //line 1049 "parse_date.re"
				{
//		DEBUG_OUTPUT("midnight | today");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 24942 "<stdout>"

				case 697:
					case697(s);
					break;
				case 698:
					case698(s);
					break;
				case 699:
					case699(s);
					break;
				case 700:
					case700(s);
					break;
				case 701:
					case701(s);
					break;
				case 702:
					case702(s);
					break;
				case 703:
					case703(s);
					break;
				case 704:
					case704(s);
					break;
				case 705:
					case705(s);
					break;
				case 706:
					case706(s);
					break;
				case 707:
					case707(s);
					break;
				case 708:
					case708(s);
					break;
				case 709:
					case709(s);
					break;
				case 710:
					case710(s);
					break;
				case 711: //line 1413 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("pointed date YY");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_FULL_POINTED;
				}
//line 25657 "<stdout>"

				case 712:
					case712(s);
					break;
				case 713:
					case713(s);
					break;
				case 714:
					case714(s);
					break;
				case 715:
					case715(s);
					break;
				case 716:
					case716(s);
					break;
				case 717:
					case717(s);
					break;
				case 718:
					case718(s);
					break;
				case 719:
					case719(s);
					break;
				case 720:
					case720(s);
					break;
				case 721:
					++s.cursor;
					if ((s.lim - s.cursor) < 9) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 722:
					case722(s);
					break;
				case 723:
					case723(s);
					break;
				case 724: //line 1358 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("gnudateshorter");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = 1;
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_DATE;
				}
//line 25835 "<stdout>"

				case 725:
					case725(s);
					break;
				case 726:
					case726(s);
					break;
				case 727:
					case727(s);
					break;
				case 728:
					case728(s);
					break;
				case 729:
					case729(s);
					break;
				case 730:
					case730(s);
					break;
				case 731:
					case731(s);
					break;
				case 732:
					case732(s);
					break;
				case 733:
					case733(s);
					break;
				case 734:
					case734(s);
					break;
				case 735:
					case735(s);
					break;
				case 736:
					case736(s);
					break;
				case 737:
					case737(s);
					break;
				case 738:
					case738(s);
					break;
				case 739:
					case739(s);
					break;
				case 740:
					case740(s);
					break;
				case 741:
					case741(s);
					break;
				case 742:
					case742(s);
					break;
				case 743:
					case743(s);
					break;
				case 744:
					case744(s);
					break;
				case 745:
					case745(s);
					break;
				case 746:
					case746(s);
					break;
				case 747: //line 1296 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("iso8601nocolon");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);

					if (s.ptr < s.cursor) {
						s.time.z = Dbx_scan_support.timelib_parse_zone(s);
						if (s.tz_not_found != 0) {
							Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
						}
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_NOCOLON;
				}
//line 26082 "<stdout>"

				case 748:
					case748(s);
					break;
				case 749:
					case749(s);
					break;
				case 750:
					case750(s);
					break;
				case 751:
					case751(s);
					break;
				case 752:
					case752(s);
					break;
				case 753:
					case753(s);
					break;
				case 754:
					case754(s);
					break;
				case 755:
					case755(s);
					break;
				case 756:
					case756(s);
					break;
				case 757:
					case757(s);
					break;
				case 758:
					case758(s);
					break;
				case 759:
					++s.cursor;
					s.state = 577;
					break;

				case 760:
					case760(s);
					break;
				case 761:
					case761(s);
					break;
				case 762:
					case762(s);
					break;
				case 763:
					case763(s);
					break;
				case 764:
					case764(s);
					break;
				case 765:
					case765(s);
					break;
				case 766:
					case766(s);
					break;
				case 767:
					case767(s);
					break;
				case 768:
					case768(s);
					break;
				case 769:
					case769(s);
					break;
				case 770:
					case770(s);
					break;
				case 771:
					case771(s);
					break;
				case 772:
					case772(s);
					break;
				case 773:
					case773(s);
					break;
				case 774:
					case774(s);
					break;
				case 775:
					case775(s);
					break;
				case 776:
					case776(s);
					break;
				case 777:
					++s.cursor;
					s.state = 241;
					break;

				case 778:
					case778(s);
					break;
				case 779:
					case779(s);
					break;
				case 780:
					case780(s);
					break;
				case 781:
					case781(s);
					break;
				case 782:
					case782(s);
					break;
				case 783:
					case783(s);
					break;
				case 784:
					case784(s);
					break;
				case 785:
					case785(s);
					break;
				case 786:
					case786(s);
					break;
				case 787:
					case787(s);
					break;
				case 788:
					case788(s);
					break;
				case 789:
					case789(s);
					break;
				case 790:
					case790(s);
					break;
				case 791:
					case791(s);
					break;
				case 792:
					case792(s);
					break;
				case 793:
					case793(s);
					break;
				case 794:
					case794(s);
					break;
				case 795:
					case795(s);
					break;
				case 796:
					case796(s);
					break;
				case 797:
					case797(s);
					break;
				case 798:
					case798(s);
					break;
				case 799:
					case799(s);
					break;
				case 800:
					case800(s);
					break;
				case 801:
					case801(s);
					break;
				case 802:
					++s.cursor;
					if ((s.lim - s.cursor) < 12) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 803:
					case803(s);
					break;
				case 804:
					case804(s);
					break;
				case 805:
					case805(s);
					break;
				case 806:
					case806(s);
					break;
				case 807:
					case807(s);
					break;
				case 808:
					case808(s);
					break;
				case 809:
					case809(s);
					break;
				case 810:
					case810(s);
					break;
				case 811:
					case811(s);
					break;
				case 812:
					case812(s);
					break;
				case 813:
					case813(s);
					break;
				case 814: //line 1757 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("dateshortwithtimeshort | dateshortwithtimelong | dateshortwithtimelongtz");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);

					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					if (s.src[s.ptr] == ':') {
						s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);

						if (s.src[s.ptr] == '.') {
							s.time.us = Dbx_scan_support.timelib_get_frac_nr(s, 8);
						}
					}

					if (s.ptr < s.cursor) {
						s.time.z = Dbx_scan_support.timelib_parse_zone(s);
						if (s.tz_not_found != 0) {
							Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
						}
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_SHORTDATE_WITH_TIME;
				}
//line 27107 "<stdout>"

				case 815:
					case815(s);
					break;
				case 816:
					case816(s);
					break;
				case 817:
					case817(s);
					break;
				case 818:
					case818(s);
					break;
				case 819:
					case819(s);
					break;
				case 820:
					case820(s);
					break;
				case 821:
					case821(s);
					break;
				case 822:
					case822(s);
					break;
				case 823:
					case823(s);
					break;
				case 824:
					case824(s);
					break;
				case 825:
					case825(s);
					break;
				case 826:
					case826(s);
					break;
				case 827:
					case827(s);
					break;
				case 828:
					case828(s);
					break;
				case 829:
					case829(s);
					break;
				case 830:
					case830(s);
					break;
				case 831:
					case831(s);
					break;
				case 832:
					case832(s);
					break;
				case 833:
					case833(s);
					break;
				case 834:
					case834(s);
					break;
				case 835:
					case835(s);
					break;
				case 836:
					case836(s);
					break;
				case 837:
					case837(s);
					break;
				case 838:
					case838(s);
					break;
				case 839:
					case839(s);
					break;
				case 840:
					case840(s);
					break;
				case 841:
					case841(s);
					break;
				case 842:
					case842(s);
					break;
				case 843:
					case843(s);
					break;
				case 844:
					case844(s);
					break;
				case 845:
					case845(s);
					break;
				case 846:
					case846(s);
					break;
				case 847:
					case847(s);
					break;
				case 848:
					case848(s);
					break;
				case 849:
					case849(s);
					break;
				case 850:
					case850(s);
					break;
				case 851:
					case851(s);
					break;
				case 852:
					case852(s);
					break;
				case 853:
					case853(s);
					break;
				case 854:
					case854(s);
					break;
				case 855:
					case855(s);
					break;
				case 856:
					case856(s);
					break;
				case 857:
					case857(s);
					break;
				case 858:
					case858(s);
					break;
				case 859:
					++s.cursor;
					s.state = 545;
					break;

				case 860:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case860(s);
					break;
				case 862:
					case862(s);
					break;
				case 863:
					case863(s);
					break;
				case 864:
					case864(s);
					break;
				case 865:
					case865(s);
					break;
				case 866:
					case866(s);
					break;
				case 867:
					case867(s);
					break;
				case 868:
					case868(s);
					break;
				case 869:
					case869(s);
					break;
				case 870:
					case870(s);
					break;
				case 871:
					case871(s);
					break;
				case 872:
					case872(s);
					break;
				case 873:
					case873(s);
					break;
				case 874:
					case874(s);
					break;
				case 875:
					case875(s);
					break;
				case 876:
					case876(s);
					break;
				case 877:
					case877(s);
					break;
				case 878:
					case878(s);
					break;
				case 879:
					case879(s);
					break;
				case 880:
					case880(s);
					break;
				case 881:
					case881(s);
					break;
				case 882:
					case882(s);
					break;
				case 883:
					case883(s);
					break;
				case 884:
					case884(s);
					break;
				case 885:
					case885(s);
					break;
				case 886: //line 1519 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("pgydotd");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 3);
					s.time.m = 1;
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_PG_YEARDAY;
				}
//line 28173 "<stdout>"

				case 887:
					case887(s);
					break;
				case 888:
					case888(s);
					break;
				case 889:
					case889(s);
					break;
				case 890:
					case890(s);
					break;
				case 891:
					case891(s);
					break;
				case 892:
					case892(s);
					break;
				case 893:
					case893(s);
					break;
				case 894:
					case894(s);
					break;
				case 895:
					case895(s);
					break;
				case 896:
					case896(s);
					break;
				case 897:
					case897(s);
					break;
				case 898:
					case898(s);
					break;
				case 899:
					case899(s);
					break;
				case 900:
					case900(s);
					break;
				case 901: //line 1552 "parse_date.re"
				{
					int w, d;
//		DEBUG_OUTPUT("isoweek");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					w = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					d = 1;
					s.time.m = 1;
					s.time.d = 1;
					s.time.relative.d = Dbx_scan_support.timelib_daynr_from_weeknr(s.time.y, w, d);

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_WEEK;
				}
//line 28365 "<stdout>"

				case 902:
					case902(s);
					break;
				case 903:
					case903(s);
					break;
				case 904:
					case904(s);
					break;
				case 905:
					case905(s);
					break;
				case 906:
					case906(s);
					break;
				case 907:
					case907(s);
					break;
				case 908:
					case908(s);
					break;
				case 909:
					case909(s);
					break;
				case 910:
					case910(s);
					break;
				case 911:
					case911(s);
					break;
				case 912:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 913:
					case913(s);
					break;
				case 914:
					case914(s);
					break;
				case 915:
					case915(s);
					break;
				case 916:
					case916(s);
					break;
				case 917:
					case917(s);
					break;
				case 918:
					case918(s);
					break;
				case 919:
					case919(s);
					break;
				case 920:
					case920(s);
					break;
				case 921:
					case921(s);
					break;
				case 922:
					case922(s);
					break;
				case 923:
					case923(s);
					break;
				case 924:
					case924(s);
					break;
				case 925:
					++s.cursor;
					s.state = 310;
					break;

				case 926:
					case926(s);
					break;
				case 927:
					case927(s);
					break;
				case 928:
					case928(s);
					break;
				case 929:
					case929(s);
					break;
				case 930:
					case930(s);
					break;
				case 931:
					case931(s);
					break;
				case 932:
					case932(s);
					break;
				case 933:
					case933(s);
					break;
				case 934:
					case934(s);
					break;
				case 935: //line 1692 "parse_date.re"
				{
					int i;
//					int behavior = 0;
//		DEBUG_OUTPUT("relativetext");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

					while (s.ptr < s.cursor) {
						i = Dbx_scan_support.timelib_get_relative_text(s);
						Dbx_scan_support.timelib_eat_spaces(s);
						Dbx_scan_support.timelib_set_relative(s, i, s.behavior);
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 28757 "<stdout>"

				case 936:
					case936(s);
					break;
				case 937:
					case937(s);
					break;
				case 938:
					case938(s);
					break;
				case 939:
					case939(s);
					break;
				case 940:
					case940(s);
					break;
				case 941:
					case941(s);
					break;
				case 942:
					case942(s);
					break;
				case 943:
					case943(s);
					break;
				case 944:
					case944(s);
					break;
				case 945:
					case945(s);
					break;
				case 946:
					case946(s);
					break;
				case 947:
					case947(s);
					break;
				case 948:
					case948(s);
					break;
				case 949:
					case949(s);
					break;
				case 950:
					case950(s);
					break;
				case 951:
					case951(s);
					break;
				case 952:
					case952(s);
					break;
				case 953:
					case953(s);
					break;
				case 954:
					case954(s);
					break;
				case 955:
					case955(s);
					break;
				case 956:
					++s.cursor;
					s.state = 747;
					break;

				case 957:
					case957(s);
					break;
				case 958:
					++s.cursor;
					s.state = 300;
					break;

				case 959:
					case959(s);
					break;
				case 960:
					case960(s);
					break;
				case 961:
					case961(s);
					break;
				case 962:
					case962(s);
					break;
				case 963:
					case963(s);
					break;
				case 964:
					case964(s);
					break;
				case 965:
					case965(s);
					break;
				case 966:
					case966(s);
					break;
				case 967:
					case967(s);
					break;
				case 968:
					++s.cursor;
//line 1401 "parse_date.re"
					 {
//		DEBUG_OUTPUT("pointed date YYYY");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_DATE(s);
						s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_DATE_FULL_POINTED;
					}
//line 29045 "<stdout>"

				case 970:
					++s.cursor;
					s.state = 214;
					break;

				case 971:
					case971(s);
					break;
				case 972:
					case972(s);
					break;
				case 973: //line 1344 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("iso8601date2");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_DATE;
				}
//line 29098 "<stdout>"

				case 974:
					case974(s);
					break;
				case 975:
					case975(s);
					break;
				case 976:
					case976(s);
					break;
				case 977:
					case977(s);
					break;
				case 978:
					case978(s);
					break;
				case 979:
					case979(s);
					break;
				case 980:
					case980(s);
					break;
				case 981:
					case981(s);
					break;
				case 982:
					case982(s);
					break;
				case 983:
					case983(s);
					break;
				case 984:
					case984(s);
					break;
				case 985:
					case985(s);
					break;
				case 986:
					case986(s);
					break;
				case 987:
					case987(s);
					break;
				case 988:
					case988(s);
					break;
				case 989:
					++s.cursor;
					s.state = 886;
					break;

				case 990:
					case990(s);
					break;
				case 991: //line 1332 "parse_date.re"
				{
//		DEBUG_OUTPUT("iso8601date4 | iso8601date2 | iso8601dateslash | dateslash");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_unsigned_nr(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_ISO_DATE;
				}
//line 29291 "<stdout>"

				case 992:
					case992(s);
					break;
				case 993:
					case993(s);
					break;
				case 994:
					case994(s);
					break;
				case 995:
					case995(s);
					break;
				case 996: //line 1481 "parse_date.re"
				{
//		DEBUG_OUTPUT("datenocolon");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_DATE_NOCOLON;
				}
//line 29378 "<stdout>"

				case 997:
					case997(s);
					break;
				case 998:
					case998(s);
					break;
				case 999:
					case999(s);
					break;
				case 1000:
					case1000(s);
					break;
				case 1001:
					case1001(s);
					break;
				case 1002:
					case1002(s);
					break;
				case 1003:
					case1003(s);
					break;
				case 1004:
					case1004(s);
					break;
				case 1005:
					case1005(s);
					break;
				case 1006:
					++s.cursor;
//line 1533 "parse_date.re"
					 {
						int w, d;
//		DEBUG_OUTPUT("isoweekday");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_DATE(s);
						Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

						s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
						w = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						d = Dbx_scan_support.timelib_get_nr_ex(s, 1);
						s.time.m = 1;
						s.time.d = 1;
						s.time.relative.d = Dbx_scan_support.timelib_daynr_from_weeknr(s.time.y, w, d);

//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_ISO_WEEK;
					}
//line 29470 "<stdout>"

				case 1008:
					case1008(s);
					break;
				case 1009:
					case1009(s);
					break;
				case 1010:
					case1010(s);
					break;
				case 1011:
					case1011(s);
					break;
				case 1012:
					case1012(s);
					break;
				case 1013:
					case1013(s);
					break;
				case 1014:
					case1014(s);
					break;
				case 1015:
					case1015(s);
					break;
				case 1016:
					case1016(s);
					break;
				case 1017: //line 1571 "parse_date.re"
				{
//					int length = 0;
//		DEBUG_OUTPUT("pgtextshort");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_PG_TEXT;
				}
//line 29591 "<stdout>"

				case 1018:
					case1018(s);
					break;
				case 1019:
					case1019(s);
					break;
				case 1020:
					case1020(s);
					break;
				case 1021:
					case1021(s);
					break;
				case 1022:
					++s.cursor;
					s.state = 814;
					break;

				case 1023:
					case1023(s);
					break;
				case 1024:
					case1024(s);
					break;
				case 1025:
					++s.cursor;
					if ((s.lim - s.cursor) < 5) {
						return EOI;
					}
					case1025(s);
					break;
				case 1027:
					case1027(s);
					break;
				case 1028:
					case1028(s);
					break;
				case 1029:
					case1029(s);
					break;
				case 1030:
					case1030(s);
					break;
				case 1031:
					case1031(s);
					break;
				case 1032:
					case1032(s);
					break;
				case 1033:
					case1033(s);
					break;
				case 1034:
					case1034(s);
					break;
				case 1035:
					case1035(s);
					break;
				case 1036:
					case1036(s);
					break;
				case 1037:
					case1037(s);
					break;
				case 1038:
					case1038(s);
					break;
				case 1039:
					case1039(s);
					break;
				case 1040:
					case1040(s);
					break;
				case 1041:
					case1041(s);
					break;
				case 1042:
					case1042(s);
					break;
				case 1043:
					case1043(s);
					break;
				case 1044:
					++s.cursor;
					s.state = 696;
					break;

				case 1045:
					case1045(s);
					break;
				case 1046:
					case1046(s);
					break;
				case 1047:
					case1047(s);
					break;
				case 1048:
					++s.cursor;

				case 1049: //line 1059 "parse_date.re"
				{
//		DEBUG_OUTPUT("tomorrow");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);

					s.time.relative.d = 1;
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 29888 "<stdout>"

				case 1050:
					case1050(s);
					break;
				case 1051:
					case1051(s);
					break;
				case 1052:
					case1052(s);
					break;
				case 1053:
					case1053(s);
					break;
				case 1054:
					case1054(s);
					break;
				case 1055:
					case1055(s);
					break;
				case 1056:
					case1056(s);
					break;
				case 1057:
					case1057(s);
					break;
				case 1058:
					case1058(s);
					break;
				case 1059:
					case1059(s);
					break;
				case 1060:
					case1060(s);
					break;
				case 1061:
					case1061(s);
					break;
				case 1062:
					case1062(s);
					break;
				case 1063:
					case1063(s);
					break;
				case 1064:
					case1064(s);
					break;
				case 1065:
					case1065(s);
					break;
				case 1066:
					case1066(s);
					break;
				case 1067:
					case1067(s);
					break;
				case 1068:
					case1068(s);
					break;
				case 1069:
					case1069(s);
					break;
				case 1070:
					case1070(s);
					break;
				case 1071:
					case1071(s);
					break;
				case 1072:
					case1072(s);
					break;
				case 1073:
					case1073(s);
					break;
				case 1074:
					case1074(s);
					break;
				case 1075:
					s.yyaccept = 5;
					s.ptr = ++s.cursor;
					if ((s.lim - s.cursor) < 5) {
						return EOI;
					}
					case1075(s);
					break;
				case 1077:
					++s.cursor;
					if ((s.lim - s.cursor) < 5) {
						return EOI;
					}
					case1077(s);
					break;
				case 1079:
					++s.cursor;
//line 1585 "parse_date.re"
					 {
//						int length = 0;
//		DEBUG_OUTPUT("pgtextreverse");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_DATE(s);
						s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
						s.time.m = Dbx_scan_support.timelib_get_month(s);
						s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						Dbx_scan_support.TIMELIB_PROCESS_YEAR(s);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_PG_TEXT;
					}
//line 30232 "<stdout>"

				case 1081:
					++s.cursor;
//line 1097 "parse_date.re"
					 {
						int i, us;

						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
						Dbx_scan_support.TIMELIB_UNHAVE_DATE(s);
						Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);
						Dbx_scan_support.TIMELIB_HAVE_TZ(s);

						i = Dbx_scan_support.timelib_get_unsigned_nr(s, 24);
						us = Dbx_scan_support.timelib_get_unsigned_nr(s, 24);
						s.time.y = 1970;
						s.time.m = 1;
						s.time.d = 1;
						s.time.h = s.time.i = s.time.s = 0;
						s.time.us = 0;
						s.time.relative.s += i;
						s.time.relative.us = us;
						s.time.is_localtime = true;
						s.time.zone_type = Dbx_scan_support.TIMELIB_ZONETYPE_OFFSET;
						s.time.z = 0;
						s.time.dst = 0;

//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_RELATIVE;
					}
//line 30262 "<stdout>"

				case 1083:
					case1083(s);
					break;
				case 1084:
					case1084(s);
					break;
				case 1085: //line 1142 "parse_date.re"
				{
//		DEBUG_OUTPUT("backof | frontof");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);

					if (s.src[s.ptr] == 'b') {
						s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						s.time.i = 15;
					} else {
						s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2) - 1;
						s.time.i = 45;
					}
					if (s.ptr < s.cursor) {
						Dbx_scan_support.timelib_eat_spaces(s);
						s.time.h += Dbx_scan_support.timelib_meridian(s, s.time.h);
					}

//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_LF_DAY_OF_MONTH;
				}
//line 30323 "<stdout>"

				case 1086:
					case1086(s);
					break;
				case 1087:
					case1087(s);
					break;
				case 1088:
					case1088(s);
					break;
				case 1089:
					case1089(s);
					break;
				case 1090:
					case1090(s);
					break;
				case 1091:
					case1091(s);
					break;
				case 1092:
					case1092(s);
					break;
				case 1093:
					++s.cursor;
					s.state = 935;
					break;

				case 1094:
					case1094(s);
					break;
				case 1095:
					++s.cursor;
					if ((s.lim - s.cursor) < 2) {
						return EOI;
					}
					case1095(s);
					break;
				case 1097:
					case1097(s);
					break;
				case 1098:
					case1098(s);
					break;
				case 1099:
					case1099(s);
					break;
				case 1100:
					case1100(s);
					break;
				case 1101:
					case1101(s);
					break;
				case 1102:
					case1102(s);
					break;
				case 1103:
					case1103(s);
					break;
				case 1104:
					case1104(s);
					break;
				case 1105:
					case1105(s);
					break;
				case 1106:
					case1106(s);
					break;
				case 1107:
					case1107(s);
					break;
				case 1108: //line 1668 "parse_date.re"
				{
					int i;
//					int behavior = 0;
//		DEBUG_OUTPUT("relativetextweek");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

					while (s.ptr < s.cursor) {
						i = Dbx_scan_support.timelib_get_relative_text(s);
						Dbx_scan_support.timelib_eat_spaces(s);
						Dbx_scan_support.timelib_set_relative(s, i, s.behavior);
						s.time.relative.weekday_behavior = 2;

						if (s.time.relative.have_weekday_relative == false) {
							Dbx_scan_support.TIMELIB_HAVE_WEEKDAY_RELATIVE(s);
							s.time.relative.weekday = 1;
						}
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 30629 "<stdout>"

				case 1109:
					case1109(s);
					break;
				case 1110:
					++s.cursor;

				case 1111: //line 1016 "parse_date.re"
				{
//		DEBUG_OUTPUT("yesterday");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
					Dbx_scan_support.TIMELIB_UNHAVE_TIME(s);

					s.time.relative.d = -1;
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_RELATIVE;
				}
//line 30651 "<stdout>"

				case 1112:
					case1112(s);
					break;
				case 1113:
					case1113(s);
					break;
				case 1114:
					case1114(s);
					break;
				case 1115:
					case1115(s);
					break;
				case 1116:
					case1116(s);
					break;
				case 1117:
					case1117(s);
					break;
				case 1118:
					case1118(s);
					break;
				case 1119:
					case1119(s);
					break;
				case 1120:
					case1120(s);
					break;
				case 1121:
					++s.cursor;
					s.state = 991;
					break;

				case 1122:
					case1122(s);
					break;
				case 1123:
					case1123(s);
					break;
				case 1124:
					case1124(s);
					break;
				case 1125:
					case1125(s);
					break;
				case 1126:
					case1126(s);
					break;
				case 1127:
					case1127(s);
					break;
				case 1128:
					case1128(s);
					break;
				case 1129:
					case1129(s);
					break;
				case 1130:
					case1130(s);
					break;
				case 1131:
					case1131(s);
					break;
				case 1132:
					case1132(s);
					break;
				case 1133:
					case1133(s);
					break;
				case 1134:
					++s.cursor;
					if ((s.lim - s.cursor) < 5) {
						return EOI;
					}
					case1134(s);
					break;
				case 1136:
					case1136(s);
					break;
				case 1137:
					case1137(s);
					break;
				case 1138:
					++s.cursor;
					if ((s.lim - s.cursor) < 9) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 1139:
					case1139(s);
					break;
				case 1140:
					case1140(s);
					break;
				case 1141:
					case1141(s);
					break;
				case 1142:
					case1142(s);
					break;
				case 1143:
					case1143(s);
					break;
				case 1144:
					case1144(s);
					break;
				case 1145:
					++s.cursor;
					if ((s.lim - s.cursor) < 9) {
						return EOI;
					}
					s.yych = s.src[s.cursor];

				case 1146:
					case1146(s);
					break;
				case 1147:
					case1147(s);
					break;
				case 1148:
					case1148(s);
					break;
				case 1149:
					++s.cursor;
//line 1733 "parse_date.re"
					 {
//		DEBUG_OUTPUT("dateshortwithtimeshort12 | dateshortwithtimelong12");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_DATE(s);
						s.time.m = Dbx_scan_support.timelib_get_month(s);
						s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);

						Dbx_scan_support.TIMELIB_HAVE_TIME(s);
						s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
							s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);

							if (s.src[s.ptr] == '.') {
								s.time.us = Dbx_scan_support.timelib_get_frac_nr(s, 8);
							}
						}

						s.time.h += Dbx_scan_support.timelib_meridian(s, s.time.h);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_SHORTDATE_WITH_TIME;
					}
//line 31451 "<stdout>"

				case 1151:
					case1151(s);
					break;
				case 1152:
					case1152(s);
					break;
				case 1153:
					case1153(s);
					break;
				case 1154:
					case1154(s);
					break;
				case 1155:
					case1155(s);
					break;
				case 1156:
					case1156(s);
					break;
				case 1157:
					case1157(s);
					break;
				case 1158:
					case1158(s);
					break;
				case 1159:
					case1159(s);
					break;
				case 1160:
					case1160(s);
					break;
				case 1161:
					case1161(s);
					break;
				case 1162:
					case1162(s);
					break;
				case 1163:
					case1163(s);
					break;
				case 1164:
					case1164(s);
					break;
				case 1165:
					case1165(s);
					break;
				case 1166:
					case1166(s);
					break;
				case 1167:
					case1167(s);
					break;
				case 1168:
					case1168(s);
					break;
				case 1169:
					case1169(s);
					break;
				case 1170:
					++s.cursor;
					s.state = 1017;
					break;

				case 1171:
					case1171(s);
					break;
				case 1172:
					case1172(s);
					break;
				case 1173:
					case1173(s);
					break;
				case 1174:
					case1174(s);
					break;
				case 1175:
					case1175(s);
					break;
				case 1176:
					case1176(s);
					break;
				case 1177:
					case1177(s);
					break;
				case 1178:
					case1178(s);
					break;
				case 1179:
					case1179(s);
					break;
				case 1180:
					case1180(s);
					break;
				case 1181:
					case1181(s);
					break;
				case 1182:
					++s.cursor;
//line 1165 "parse_date.re"
					 {
						int i;
//						int behavior = 0;
//		DEBUG_OUTPUT("weekdayof");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);
						Dbx_scan_support.TIMELIB_HAVE_SPECIAL_RELATIVE(s);

						i = Dbx_scan_support.timelib_get_relative_text(s);
						Dbx_scan_support.timelib_eat_spaces(s);
						if (i > 0) {
							s.time.relative.special_type = Dbx_scan_support.TIMELIB_SPECIAL_DAY_OF_WEEK_IN_MONTH;
							Dbx_scan_support.timelib_set_relative(s, i, 1);
						} else {
							s.time.relative.special_type = Dbx_scan_support.TIMELIB_SPECIAL_LAST_DAY_OF_WEEK_IN_MONTH;
							Dbx_scan_support.timelib_set_relative(s, i, s.behavior);
						}
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_WEEK_DAY_OF_MONTH;
					}
//line 32039 "<stdout>"

				case 1184:
					case1184(s);
					break;
				case 1185:
					case1185(s);
					break;
				case 1186:
					++s.cursor;
//line 1125 "parse_date.re"
					 {
//		DEBUG_OUTPUT("firstdayof | lastdayof");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_RELATIVE(s);

						if (s.src[s.ptr] == 'l' || s.src[s.ptr] == 'L') {
							s.time.relative.first_last_day_of = Dbx_scan_support.TIMELIB_SPECIAL_LAST_DAY_OF_MONTH;
						} else {
							s.time.relative.first_last_day_of = Dbx_scan_support.TIMELIB_SPECIAL_FIRST_DAY_OF_MONTH;
						}

//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_LF_DAY_OF_MONTH;
					}
//line 32073 "<stdout>"

				case 1188:
					case1188(s);
					break;
				case 1189:
					case1189(s);
					break;
				case 1190:
					case1190(s);
					break;
				case 1191:
					case1191(s);
					break;
				case 1192:
					case1192(s);
					break;
				case 1193:
					case1193(s);
					break;
				case 1194:
					case1194(s);
					break;
				case 1195:
					case1195(s);
					break;
				case 1196:
					case1196(s);
					break;
				case 1197:
					case1197(s);
					break;
				case 1198:
					case1198(s);
					break;
				case 1199:
					++s.cursor;
//line 1204 "parse_date.re"
					 {
//		DEBUG_OUTPUT("mssqltime");
						s.cur = s.cursor;
						s.ptr = s.tok;
						Dbx_scan_support.TIMELIB_HAVE_TIME(s);
						s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
						if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
							s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);

							if (s.src[s.ptr] == ':' || s.src[s.ptr] == '.') {
								s.time.us = Dbx_scan_support.timelib_get_frac_nr(s, 8);
							}
						}
						Dbx_scan_support.timelib_eat_spaces(s);
						s.time.h += Dbx_scan_support.timelib_meridian(s, s.time.h);
//		Dbx_scan_support.TIMELIB_DEINIT;
						return Dbx_scan_support.TIMELIB_TIME24_WITH_ZONE;
					}
//line 32236 "<stdout>"

				case 1201:
					case1201(s);
					break;
				case 1202:
					++s.cursor;
					s.state = 1085;
					break;

				case 1203:
					case1203(s);
					break;
				case 1204:
					case1204(s);
					break;
				case 1205:
					case1205(s);
					break;
				case 1206:
					case1206(s);
					break;
				case 1207:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case1207(s);
					break;
				case 1208:
					case1208(s);
					break;
				case 1209:
					case1209(s);
					break;
				case 1210:
					case1210(s);
					break;
				case 1211:
					case1211(s);
					break;
				case 1212:
					case1212(s);
					break;
				case 1213:
					case1213(s);
					break;
				case 1214:
					case1214(s);
					break;
				case 1215:
					case1215(s);
					break;
				case 1216:
					case1216(s);
					break;
				case 1217:
					case1217(s);
					break;
				case 1218:
					case1218(s);
					break;
				case 1219:
					case1219(s);
					break;
				case 1220:
					case1220(s);
					break;
				case 1221:
					s.yyaccept = 21;
					s.ptr = ++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case1221(s);
					break;
				case 1223:
					case1223(s);
					break;
				case 1224:
					case1224(s);
					break;
				case 1225:
					case1225(s);
					break;
				case 1226:
					case1226(s);
					break;
				case 1227:
					case1227(s);
					break;
				case 1228: //line 1493 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("xmlrpc | xmlrpcnocolon | soap | wddx | exif");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.m = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					if (s.src[s.ptr] == '.') {
						s.time.us = Dbx_scan_support.timelib_get_frac_nr(s, 9);
						if (s.ptr < s.cursor) {
							s.time.z = Dbx_scan_support.timelib_parse_zone(s);
							if (s.tz_not_found != 0) {
								Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
							}
						}
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_XMLRPC_SOAP;
				}
//line 32861 "<stdout>"

				case 1229:
					case1229(s);
					break;
				case 1230:
					++s.cursor;
					s.state = 1228;
					break;

				case 1231:
					case1231(s);
					break;
				case 1232:
					case1232(s);
					break;
				case 1233:
					case1233(s);
					break;
				case 1234:
					case1234(s);
					break;
				case 1235:
					case1235(s);
					break;
				case 1236:
					case1236(s);
					break;
				case 1237:
					case1237(s);
					break;
				case 1238:
					case1238(s);
					break;
				case 1239:
					case1239(s);
					break;
				case 1240:
					++s.cursor;
					if (s.lim <= s.cursor) {
						return EOI;
					}
					case1240(s);
					break;
				case 1242:
					case1242(s);
					break;
				case 1243:
					case1243(s);
					break;
				case 1244:
					case1244(s);
					break;
				case 1245:
					case1245(s);
					break;
				case 1246:
					case1246(s);
					break;
				case 1247:
					case1247(s);
					break;
				case 1248:
					case1248(s);
					break;
				case 1249:
					case1249(s);
					break;
				case 1250:
					++s.cursor;
					if ((s.lim - s.cursor) < 9) {
						return EOI;
					}
					case1250(s);
					break;
				case 1252:
					case1252(s);
					break;
				case 1253:
					case1253(s);
					break;
				case 1254:
					case1254(s);
					break;
				case 1255:
					s.yyaccept = 33;
					s.ptr = ++s.cursor;
					if ((s.lim - s.cursor) < 9) {
						return EOI;
					}
					case1255(s);
					break;
				case 1257:
					case1257(s);
					break;
				case 1258: //line 1599 "parse_date.re"
				{
//					int tz_not_found;
//		DEBUG_OUTPUT("clf");
					s.cur = s.cursor;
					s.ptr = s.tok;
					Dbx_scan_support.TIMELIB_HAVE_TIME(s);
					Dbx_scan_support.TIMELIB_HAVE_DATE(s);
					s.time.d = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.m = Dbx_scan_support.timelib_get_month(s);
					s.time.y = Dbx_scan_support.timelib_get_nr_ex(s, 4);
					s.time.h = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.i = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.s = Dbx_scan_support.timelib_get_nr_ex(s, 2);
					s.time.z = Dbx_scan_support.timelib_parse_zone(s);
					if (s.tz_not_found != 0) {
						Dbx_scan_support.add_error(s, Dbx_scan_support.TIMELIB_ERR_TZID_NOT_FOUND, "The timezone could not be found in the database");
					}
//		Dbx_scan_support.TIMELIB_DEINIT;
					return Dbx_scan_support.TIMELIB_CLF;
				}
//line 33316 "<stdout>"

				case 1259:
					case1259(s);
					break;
				case 1260:
					case1260(s);
					break;
				case 1261:
					case1261(s);
					break;
				case 1262:
					case1262(s);
					break;
				case 1263:
					case1263(s);
					break;
				case 1264:
					case1264(s);
					break;
				case 1265:
					case1265(s);
					break;
				case 1266:
					++s.cursor;
					s.state = 1258;
					break;

				case 1267:
					case1267(s);
					break;
				case 1268:
					case1268(s);
					break;
				case 1269:
					case1269(s);
					break;
				case 1270:
					case1270(s);
					break;
				case 1271:
					case1271(s);
					break;
				case 1272:
					case1272(s);
					break;
				case 1273:
					s.yych = s.src[++s.cursor];
					switch (s.yych) {
						case '+':
						case '-':
							case1262(s);
							break;
						default:
							case56(s);
							break;
					}
			}
//line 1818 "parse_date.re"

		}

		//return EOI;
	}

	private static void case6(Dbx_scanner s) {
		s.yyaccept = 0;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 54;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 7;
				break;
		}
	}

	private static void case8(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 40;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case9(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 54;
				break;
			case '+':
			case '-':
				s.state = 59;
				break;
			case '0':
			case '1':
				s.state = 61;
				break;
			case '2':
				s.state = 62;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 63;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case11(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 64;
				break;
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'o':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 66;
				break;
			case '-':
				s.state = 67;
				break;
			case '.':
				s.state = 68;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
				s.state = 70;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 71;
				break;
			case ':':
				s.state = 72;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case12(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 95;
				break;
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'o':
			case 'p':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 97;
				break;
			case '-':
				s.state = 67;
				break;
			case '.':
				s.state = 98;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 71;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 99;
				break;
			case ':':
				s.state = 100;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case13(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 95;
				break;
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'o':
			case 'p':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 97;
				break;
			case '-':
				s.state = 67;
				break;
			case '.':
				s.state = 98;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 99;
				break;
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 103;
				break;
			case ':':
				s.state = 100;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case14(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 95;
				break;
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'o':
			case 'p':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 97;
				break;
			case '-':
				s.state = 67;
				break;
			case '.':
				s.state = 98;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
				s.state = 103;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 104;
				break;
			case ':':
				s.state = 100;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case15(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 95;
				break;
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'o':
			case 'p':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 97;
				break;
			case '-':
				s.state = 67;
				break;
			case '.':
				s.state = 98;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 104;
				break;
			case ':':
				s.state = 100;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case16(Dbx_scanner s) {
		s.yyaccept = 1;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 105;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 106;
				break;
			default:
				s.state = 5;
				break;
		}
	}

	private static void case17(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'G':
				s.state = 111;
				break;
			case 'P':
				s.state = 112;
				break;
			case 'U':
				s.state = 113;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'g':
				s.state = 115;
				break;
			case 'p':
				s.state = 116;
				break;
			case 'u':
				s.state = 117;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case19(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
				s.state = 118;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'a':
				s.state = 119;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case20(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case21(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 120;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 121;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case22(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'I':
				s.state = 122;
				break;
			case 'L':
				s.state = 123;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'i':
				s.state = 124;
				break;
			case 'l':
				s.state = 125;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case23(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 126;
				break;
			case 'I':
				s.state = 127;
				break;
			case 'O':
				s.state = 128;
				break;
			case 'R':
				s.state = 129;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 130;
				break;
			case 'i':
				s.state = 131;
				break;
			case 'o':
				s.state = 132;
				break;
			case 'r':
				s.state = 133;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case24(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'M':
				s.state = 134;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case25(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'I':
				s.state = 140;
				break;
			case 'V':
			case 'X':
				s.state = 141;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case26(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
				s.state = 142;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'U':
				s.state = 143;
				break;
			case 'a':
				s.state = 144;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'u':
				s.state = 145;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case27(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
				s.state = 146;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'a':
				s.state = 147;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case28(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
				s.state = 148;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'I':
				s.state = 149;
				break;
			case 'O':
				s.state = 150;
				break;
			case 'a':
				s.state = 151;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'i':
				s.state = 152;
				break;
			case 'o':
				s.state = 153;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case29(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 154;
				break;
			case 'I':
				s.state = 155;
				break;
			case 'O':
				s.state = 156;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 157;
				break;
			case 'i':
				s.state = 158;
				break;
			case 'o':
				s.state = 159;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case30(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'C':
				s.state = 160;
				break;
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'c':
				s.state = 161;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case31(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'R':
				s.state = 162;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'r':
				s.state = 163;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case32(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
				s.state = 164;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 165;
				break;
			case 'I':
				s.state = 166;
				break;
			case 'U':
				s.state = 150;
				break;
			case 'a':
				s.state = 167;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 168;
				break;
			case 'i':
				s.state = 169;
				break;
			case 'u':
				s.state = 153;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case33(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '0':
			case '1':
				s.state = 170;
				break;
			case '2':
				s.state = 171;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 172;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 155;
				break;
			case 'H':
				s.state = 173;
				break;
			case 'O':
				s.state = 174;
				break;
			case 'U':
				s.state = 175;
				break;
			case 'W':
				s.state = 176;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 158;
				break;
			case 'h':
				s.state = 177;
				break;
			case 'o':
				s.state = 178;
				break;
			case 'u':
				s.state = 179;
				break;
			case 'w':
				s.state = 180;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case34(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'I':
				s.state = 181;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case35(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 182;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 183;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case36(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'I':
				s.state = 140;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case37(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 110;
				break;
			case 'E':
				s.state = 184;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 114;
				break;
			case 'e':
				s.state = 185;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case38(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'G':
			case 'g':
				s.state = 111;
				break;
			case 'P':
			case 'p':
				s.state = 112;
				break;
			case 'U':
			case 'u':
				s.state = 113;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case39(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 118;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case40(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case41(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 120;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case42(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'I':
			case 'i':
				s.state = 122;
				break;
			case 'L':
			case 'l':
				s.state = 123;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case43(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 126;
				break;
			case 'I':
			case 'i':
				s.state = 127;
				break;
			case 'O':
			case 'o':
				s.state = 128;
				break;
			case 'R':
			case 'r':
				s.state = 129;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case44(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 142;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'U':
			case 'u':
				s.state = 143;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case45(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 146;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case46(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 148;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'I':
			case 'i':
				s.state = 149;
				break;
			case 'O':
			case 'o':
				s.state = 150;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case47(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 154;
				break;
			case 'I':
			case 'i':
				s.state = 155;
				break;
			case 'O':
			case 'o':
				s.state = 156;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case48(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'C':
			case 'c':
				s.state = 160;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case49(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'R':
			case 'r':
				s.state = 162;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case50(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 164;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 165;
				break;
			case 'I':
			case 'i':
				s.state = 166;
				break;
			case 'U':
			case 'u':
				s.state = 150;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case51(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '0':
			case '1':
				s.state = 170;
				break;
			case '2':
				s.state = 171;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 172;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 155;
				break;
			case 'H':
			case 'h':
				s.state = 173;
				break;
			case 'O':
			case 'o':
				s.state = 174;
				break;
			case 'U':
			case 'u':
				s.state = 175;
				break;
			case 'W':
			case 'w':
				s.state = 176;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case52(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 182;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case53(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 110;
				break;
			case 'E':
			case 'e':
				s.state = 184;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case54(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 54;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case56(Dbx_scanner s) {
		s.cursor = s.ptr;
		switch (s.yyaccept) {
			case 0:
				s.state = 7;
				break;
			case 1:
				s.state = 5;
				break;
			case 2:
				s.state = 18;
				break;
			case 3:
				s.state = 78;
				break;
			case 4:
				s.state = 108;
				break;
			case 5:
				s.state = 209;
				break;
			case 6:
				s.state = 214;
				break;
			case 7:
				s.state = 250;
				break;
			case 8:
				s.state = 280;
				break;
			case 9:
				s.state = 278;
				break;
			case 10:
				s.state = 300;
				break;
			case 11:
				s.state = 310;
				break;
			case 12:
				s.state = 336;
				break;
			case 13:
				s.state = 401;
				break;
			case 14:
				s.state = 403;
				break;
			case 15:
				s.state = 505;
				break;
			case 16:
				s.state = 545;
				break;
			case 17:
				s.state = 696;
				break;
			case 18:
				s.state = 711;
				break;
			case 19:
				s.state = 724;
				break;
			case 20:
				s.state = 747;
				break;
			case 21:
				s.state = 814;
				break;
			case 22:
				s.state = 886;
				break;
			case 23:
				s.state = 577;
				break;
			case 24:
				s.state = 901;
				break;
			case 25:
				s.state = 935;
				break;
			case 26:
				s.state = 973;
				break;
			case 27:
				s.state = 991;
				break;
			case 28:
				s.state = 996;
				break;
			case 29:
				s.state = 1049;
				break;
			case 30:
				s.state = 1085;
				break;
			case 31:
				s.state = 1108;
				break;
			case 32:
				s.state = 1111;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case57(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 186;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			case 'D':
			case 'd':
				s.state = 188;
				break;
			case 'F':
			case 'f':
				s.state = 189;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'M':
			case 'm':
				s.state = 190;
				break;
			case 'S':
			case 's':
				s.state = 191;
				break;
			case 'T':
			case 't':
				s.state = 84;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case59(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 54;
				break;
			case '+':
			case '-':
				s.state = 59;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case61(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 192;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case62(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 192;
				break;
			case '5':
				s.state = 194;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 195;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case63(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 194;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 195;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case64(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 198;
				break;
			case '1':
				s.state = 199;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 200;
				break;
			default:
				s.state = 66;
				break;
		}
	}

	private static void case66(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 65;
				break;
			case '-':
			case '.':
				s.state = 196;
				break;
			case 'A':
			case 'a':
				s.state = 73;
				break;
			case 'D':
			case 'd':
				s.state = 74;
				break;
			case 'F':
			case 'f':
				s.state = 75;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'I':
				s.state = 77;
				break;
			case 'J':
			case 'j':
				s.state = 79;
				break;
			case 'M':
			case 'm':
				s.state = 80;
				break;
			case 'N':
			case 'n':
				s.state = 81;
				break;
			case 'O':
			case 'o':
				s.state = 82;
				break;
			case 'S':
			case 's':
				s.state = 83;
				break;
			case 'T':
			case 't':
				s.state = 84;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'V':
				s.state = 86;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'X':
				s.state = 88;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case67(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 201;
				break;
			case '1':
				s.state = 202;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 203;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case68(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 208;
				break;
			case '1':
				s.state = 210;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 211;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 212;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case69(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 213;
				break;
			case '3':
				s.state = 215;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 216;
				break;
			case 'A':
			case 'a':
				s.state = 217;
				break;
			case 'D':
			case 'd':
				s.state = 218;
				break;
			case 'F':
			case 'f':
				s.state = 219;
				break;
			case 'J':
			case 'j':
				s.state = 220;
				break;
			case 'M':
			case 'm':
				s.state = 221;
				break;
			case 'N':
			case 'n':
				s.state = 222;
				break;
			case 'O':
			case 'o':
				s.state = 223;
				break;
			case 'S':
			case 's':
				s.state = 224;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case70(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 64;
				break;
			case '-':
				s.state = 225;
				break;
			case '.':
				s.state = 68;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 226;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 227;
				break;
			case ':':
				s.state = 72;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 66;
				break;
		}
	}

	private static void case71(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 95;
				break;
			case '-':
				s.state = 225;
				break;
			case '.':
				s.state = 98;
				break;
			case '/':
				s.state = 69;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 226;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 227;
				break;
			case ':':
				s.state = 100;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 97;
				break;
		}
	}

	private static void case72(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 228;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 229;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case73(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 230;
				break;
			case 'U':
			case 'u':
				s.state = 231;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case74(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 232;
				break;
			case 'E':
			case 'e':
				s.state = 233;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case75(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 234;
				break;
			case 'O':
			case 'o':
				s.state = 235;
				break;
			case 'R':
			case 'r':
				s.state = 236;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case76(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 237;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case77(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'I':
				s.state = 242;
				break;
			case 'V':
			case 'X':
				s.state = 243;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case79(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 244;
				break;
			case 'U':
			case 'u':
				s.state = 245;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case80(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 246;
				break;
			case 'I':
			case 'i':
				s.state = 247;
				break;
			case 'O':
			case 'o':
				s.state = 248;
				break;
			case 'S':
			case 's':
				s.state = 249;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case81(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 251;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case82(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 252;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case83(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 253;
				break;
			case 'E':
			case 'e':
				s.state = 254;
				break;
			case 'U':
			case 'u':
				s.state = 255;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case84(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 256;
				break;
			case 'U':
			case 'u':
				s.state = 257;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case85(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 258;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case86(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'I':
				s.state = 88;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case87(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 259;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case88(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'I':
				s.state = 242;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case89(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 260;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case90(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 251;
				break;
			case 'd':
				s.state = 261;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case91(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'd':
				s.state = 261;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case92(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 253;
				break;
			case 'E':
			case 'e':
				s.state = 254;
				break;
			case 'U':
			case 'u':
				s.state = 255;
				break;
			case 't':
				s.state = 261;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case93(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
				s.state = 256;
				break;
			case 'U':
			case 'u':
				s.state = 257;
				break;
			case 'h':
				s.state = 262;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case94(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0xB5:
				s.state = 263;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case95(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 198;
				break;
			case '1':
				s.state = 199;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 200;
				break;
			default:
				s.state = 97;
				break;
		}
	}

	private static void case97(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 96;
				break;
			case '-':
			case '.':
				s.state = 196;
				break;
			case 'A':
			case 'a':
				s.state = 101;
				break;
			case 'D':
			case 'd':
				s.state = 74;
				break;
			case 'F':
			case 'f':
				s.state = 75;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'I':
				s.state = 77;
				break;
			case 'J':
			case 'j':
				s.state = 79;
				break;
			case 'M':
			case 'm':
				s.state = 80;
				break;
			case 'N':
			case 'n':
				s.state = 81;
				break;
			case 'O':
			case 'o':
				s.state = 82;
				break;
			case 'P':
			case 'p':
				s.state = 102;
				break;
			case 'S':
			case 's':
				s.state = 83;
				break;
			case 'T':
			case 't':
				s.state = 84;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'V':
				s.state = 86;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'X':
				s.state = 88;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case98(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 264;
				break;
			case '1':
				s.state = 265;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 266;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 267;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case99(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 64;
				break;
			case '-':
				s.state = 225;
				break;
			case '.':
				s.state = 68;
				break;
			case '/':
				s.state = 268;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 226;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 227;
				break;
			case ':':
				s.state = 72;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 66;
				break;
		}
	}

	private static void case100(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 269;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 270;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case101(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 271;
				break;
			case 'M':
			case 'm':
				s.state = 272;
				break;
			case 'P':
			case 'p':
				s.state = 230;
				break;
			case 'U':
			case 'u':
				s.state = 231;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case102(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 271;
				break;
			case 'M':
			case 'm':
				s.state = 272;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case103(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 64;
				break;
			case '-':
				s.state = 225;
				break;
			case '.':
				s.state = 273;
				break;
			case '/':
				s.state = 268;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 227;
				break;
			case 'n':
				s.state = 90;
				break;
			case 'r':
				s.state = 91;
				break;
			case 's':
				s.state = 92;
				break;
			case 't':
				s.state = 93;
				break;
			default:
				s.state = 66;
				break;
		}
	}

	private static void case104(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 274;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 227;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case105(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 106;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case106(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 275;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 106;
				break;
			default:
				s.state = 108;
				break;
		}
	}

	private static void case110(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case111(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'O':
			case 'o':
				s.state = 277;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case112(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'R':
			case 'r':
				s.state = 279;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case113(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'G':
			case 'g':
				s.state = 281;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case114(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case115(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'O':
				s.state = 277;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'o':
				s.state = 284;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case116(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'R':
				s.state = 279;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'r':
				s.state = 285;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case117(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'G':
				s.state = 281;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'g':
				s.state = 286;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case118(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'C':
			case 'c':
				s.state = 287;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case119(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'C':
				s.state = 287;
				break;
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'c':
				s.state = 288;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case120(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'C':
			case 'c':
				s.state = 289;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case121(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'C':
				s.state = 289;
				break;
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'c':
				s.state = 290;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case122(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'G':
			case 'g':
				s.state = 291;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case123(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'E':
			case 'e':
				s.state = 292;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case124(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'G':
				s.state = 291;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'g':
				s.state = 293;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case125(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'E':
				s.state = 292;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'e':
				s.state = 294;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case126(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'B':
			case 'b':
				s.state = 295;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case127(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'F':
			case 'f':
				s.state = 296;
				break;
			case 'R':
			case 'r':
				s.state = 297;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case128(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'U':
			case 'u':
				s.state = 298;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case129(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'I':
			case 'i':
				s.state = 299;
				break;
			case 'O':
			case 'o':
				s.state = 301;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case130(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'B':
				s.state = 295;
				break;
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'b':
				s.state = 302;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case131(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'F':
				s.state = 296;
				break;
			case 'R':
				s.state = 297;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'f':
				s.state = 303;
				break;
			case 'r':
				s.state = 304;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case132(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'U':
				s.state = 298;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'u':
				s.state = 305;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case133(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'I':
				s.state = 299;
				break;
			case 'O':
				s.state = 301;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'i':
				s.state = 306;
				break;
			case 'o':
				s.state = 307;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case134(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'T':
				s.state = 308;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case136(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 135;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 137;
				break;
			case '3':
				s.state = 138;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 139;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case137(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 313;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case138(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
				s.state = 313;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 317;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case139(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 317;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case140(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'I':
				s.state = 318;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case141(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case142(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'N':
			case 'n':
				s.state = 319;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case143(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'L':
			case 'l':
				s.state = 320;
				break;
			case 'N':
			case 'n':
				s.state = 321;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case144(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'N':
				s.state = 319;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'n':
				s.state = 322;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case145(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'L':
				s.state = 320;
				break;
			case 'N':
				s.state = 321;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'l':
				s.state = 323;
				break;
			case 'n':
				s.state = 324;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case146(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'S':
			case 's':
				s.state = 325;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case147(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'S':
				s.state = 325;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 's':
				s.state = 326;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case148(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 276;
				break;
			case 'R':
			case 'r':
				s.state = 327;
				break;
			case 'Y':
			case 'y':
				s.state = 328;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case149(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'D':
			case 'd':
				s.state = 329;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case150(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'N':
			case 'n':
				s.state = 299;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case151(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
				s.state = 276;
				break;
			case 'R':
				s.state = 327;
				break;
			case 'Y':
				s.state = 328;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 283;
				break;
			case 'r':
				s.state = 330;
				break;
			case 'y':
				s.state = 331;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case152(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'D':
				s.state = 329;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'd':
				s.state = 332;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case153(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'N':
				s.state = 299;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'n':
				s.state = 306;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case154(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'X':
			case 'x':
				s.state = 333;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case155(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'N':
			case 'n':
				s.state = 296;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case156(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'O':
			case 'o':
				s.state = 334;
				break;
			case 'V':
			case 'v':
				s.state = 289;
				break;
			case 'W':
			case 'w':
				s.state = 335;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case157(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'X':
				s.state = 333;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'x':
				s.state = 337;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case158(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'N':
				s.state = 296;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'n':
				s.state = 303;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case159(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'O':
				s.state = 334;
				break;
			case 'V':
				s.state = 289;
				break;
			case 'W':
				s.state = 335;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'o':
				s.state = 338;
				break;
			case 'v':
				s.state = 290;
				break;
			case 'w':
				s.state = 339;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case160(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'T':
			case 't':
				s.state = 340;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case161(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'T':
				s.state = 340;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 't':
				s.state = 341;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case162(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'E':
			case 'e':
				s.state = 342;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case163(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'E':
				s.state = 342;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'e':
				s.state = 343;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case164(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'T':
			case 't':
				s.state = 344;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case165(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'C':
			case 'c':
				s.state = 345;
				break;
			case 'P':
			case 'p':
				s.state = 346;
				break;
			case 'V':
			case 'v':
				s.state = 347;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case166(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'X':
			case 'x':
				s.state = 296;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case167(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'T':
				s.state = 344;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 't':
				s.state = 348;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case168(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'C':
				s.state = 345;
				break;
			case 'P':
				s.state = 346;
				break;
			case 'V':
				s.state = 347;
				break;
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'c':
				s.state = 349;
				break;
			case 'p':
				s.state = 350;
				break;
			case 'v':
				s.state = 351;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case169(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'X':
				s.state = 296;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'x':
				s.state = 303;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case170(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 72;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 352;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case171(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 72;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 352;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case172(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 72;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case173(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'I':
			case 'i':
				s.state = 353;
				break;
			case 'U':
			case 'u':
				s.state = 354;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case174(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'D':
			case 'd':
				s.state = 355;
				break;
			case 'M':
			case 'm':
				s.state = 356;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case175(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'E':
			case 'e':
				s.state = 357;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case176(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'E':
			case 'e':
				s.state = 358;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case177(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'I':
				s.state = 353;
				break;
			case 'U':
				s.state = 354;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'i':
				s.state = 359;
				break;
			case 'u':
				s.state = 360;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case178(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'D':
				s.state = 355;
				break;
			case 'M':
				s.state = 356;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'd':
				s.state = 361;
				break;
			case 'm':
				s.state = 362;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case179(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'E':
				s.state = 357;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'e':
				s.state = 363;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case180(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'E':
				s.state = 358;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'e':
				s.state = 364;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case181(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'I':
				s.state = 365;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case182(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'D':
			case 'd':
				s.state = 366;
				break;
			case 'E':
			case 'e':
				s.state = 367;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case183(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'D':
				s.state = 366;
				break;
			case 'E':
				s.state = 367;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 'd':
				s.state = 368;
				break;
			case 'e':
				s.state = 369;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case184(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 276;
				break;
			case 'S':
			case 's':
				s.state = 370;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case185(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 276;
				break;
			case 'S':
				s.state = 370;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 283;
				break;
			case 's':
				s.state = 371;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case187(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 186;
				break;
			case 'D':
			case 'd':
				s.state = 188;
				break;
			case 'F':
			case 'f':
				s.state = 189;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'M':
			case 'm':
				s.state = 190;
				break;
			case 'S':
			case 's':
				s.state = 191;
				break;
			case 'T':
			case 't':
				s.state = 84;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case188(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 232;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case189(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 235;
				break;
			case 'R':
			case 'r':
				s.state = 236;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case190(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 247;
				break;
			case 'O':
			case 'o':
				s.state = 248;
				break;
			case 'S':
			case 's':
				s.state = 249;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case191(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 253;
				break;
			case 'E':
			case 'e':
				s.state = 372;
				break;
			case 'U':
			case 'u':
				s.state = 255;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case192(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 373;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 374;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case193(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 375;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case194(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 374;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case195(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 376;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case197(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 196;
				break;
			case 'A':
			case 'a':
				s.state = 73;
				break;
			case 'D':
			case 'd':
				s.state = 204;
				break;
			case 'F':
			case 'f':
				s.state = 205;
				break;
			case 'I':
				s.state = 77;
				break;
			case 'J':
			case 'j':
				s.state = 79;
				break;
			case 'M':
			case 'm':
				s.state = 206;
				break;
			case 'N':
			case 'n':
				s.state = 81;
				break;
			case 'O':
			case 'o':
				s.state = 82;
				break;
			case 'S':
			case 's':
				s.state = 207;
				break;
			case 'V':
				s.state = 86;
				break;
			case 'X':
				s.state = 88;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case198(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 378;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 200;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case199(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 378;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 200;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case200(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 378;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case201(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 379;
				break;
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 203;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case202(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 379;
				break;
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 203;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case203(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 379;
				break;
			case '.':
				s.state = 377;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case204(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 233;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case205(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 234;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case206(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 246;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case207(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 380;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case208(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 381;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 212;
				break;
			case ':':
				s.state = 382;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case210(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 381;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 212;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 229;
				break;
			case ':':
				s.state = 382;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case211(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 381;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 229;
				break;
			case ':':
				s.state = 382;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case212(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 381;
				break;
			case ':':
				s.state = 382;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case213(Dbx_scanner s) {
		s.yyaccept = 6;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 383;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 216;
				break;
			case 'n':
			case 'r':
				s.state = 384;
				break;
			case 's':
				s.state = 385;
				break;
			case 't':
				s.state = 386;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case215(Dbx_scanner s) {
		s.yyaccept = 6;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 383;
				break;
			case '0':
			case '1':
				s.state = 216;
				break;
			case 'n':
			case 'r':
				s.state = 384;
				break;
			case 's':
				s.state = 385;
				break;
			case 't':
				s.state = 386;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case216(Dbx_scanner s) {
		s.yyaccept = 6;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 383;
				break;
			case 'n':
			case 'r':
				s.state = 384;
				break;
			case 's':
				s.state = 385;
				break;
			case 't':
				s.state = 386;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case217(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 387;
				break;
			case 'U':
			case 'u':
				s.state = 388;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case218(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 389;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case219(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 390;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case220(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 391;
				break;
			case 'U':
			case 'u':
				s.state = 392;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case221(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 393;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case222(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 394;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case223(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 395;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case224(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 396;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case225(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 397;
				break;
			case '1':
				s.state = 398;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 203;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case226(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 399;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 400;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case227(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 399;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 402;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case228(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 382;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 229;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case229(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 382;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case230(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 404;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case231(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 405;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case232(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case233(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 407;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case234(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 408;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case235(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 409;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case236(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 410;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case237(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 411;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case238(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case240(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 412;
				break;
			default:
				s.state = 241;
				break;
		}
	}

	private static void case242(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'I':
				s.state = 243;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case243(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case244(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 413;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case245(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 414;
				break;
			case 'N':
			case 'n':
				s.state = 415;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case246(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 416;
				break;
			case 'Y':
			case 'y':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case247(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 417;
				break;
			case 'L':
			case 'l':
				s.state = 418;
				break;
			case 'N':
			case 'n':
				s.state = 419;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case248(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 420;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case249(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 421;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case251(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'V':
			case 'v':
				s.state = 407;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case252(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 422;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case253(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 423;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case254(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 424;
				break;
			case 'P':
			case 'p':
				s.state = 425;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case255(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 410;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case256(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 426;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case257(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 427;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case258(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 421;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case259(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 428;
				break;
			case 'E':
			case 'e':
				s.state = 429;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case260(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 411;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case261(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case '.':
				s.state = 273;
				break;
			case '-':
				s.state = 430;
				break;
			case '/':
				s.state = 268;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case262(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case '.':
				s.state = 273;
				break;
			case '-':
				s.state = 430;
				break;
			case '/':
				s.state = 268;
				break;
			case 'U':
			case 'u':
				s.state = 426;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case263(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 249;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case264(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 431;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 432;
				break;
			case ':':
				s.state = 433;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case265(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 431;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 432;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 434;
				break;
			case ':':
				s.state = 433;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case266(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 431;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 434;
				break;
			case ':':
				s.state = 433;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case267(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 431;
				break;
			case ':':
				s.state = 433;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case268(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 217;
				break;
			case 'D':
			case 'd':
				s.state = 218;
				break;
			case 'F':
			case 'f':
				s.state = 219;
				break;
			case 'J':
			case 'j':
				s.state = 220;
				break;
			case 'M':
			case 'm':
				s.state = 221;
				break;
			case 'N':
			case 'n':
				s.state = 222;
				break;
			case 'O':
			case 'o':
				s.state = 223;
				break;
			case 'S':
			case 's':
				s.state = 224;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case269(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 433;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 435;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case270(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 433;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case271(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 272;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case272(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 436;
				break;
			case '.':
				s.state = 438;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case273(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 198;
				break;
			case '1':
				s.state = 199;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 200;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case274(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 439;
				break;
			case '1':
				s.state = 440;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 441;
				break;
			case 'A':
			case 'a':
				s.state = 442;
				break;
			case 'D':
			case 'd':
				s.state = 443;
				break;
			case 'F':
			case 'f':
				s.state = 444;
				break;
			case 'J':
			case 'j':
				s.state = 445;
				break;
			case 'M':
			case 'm':
				s.state = 446;
				break;
			case 'N':
			case 'n':
				s.state = 447;
				break;
			case 'O':
			case 'o':
				s.state = 448;
				break;
			case 'S':
			case 's':
				s.state = 449;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case275(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 450;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case276(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case277(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 278;
				break;
		}
	}

	private static void case279(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'I':
			case 'i':
				s.state = 453;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case281(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'U':
			case 'u':
				s.state = 454;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case282(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 455;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case283(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case284(Dbx_scanner s) {
		s.yyaccept = 9;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			default:
				s.state = 278;
				break;
		}
	}

	private static void case285(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'I':
				s.state = 453;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'i':
				s.state = 459;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case286(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'U':
				s.state = 454;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'u':
				s.state = 460;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case287(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'K':
			case 'k':
				s.state = 461;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case288(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'K':
				s.state = 461;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'k':
				s.state = 462;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case289(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'E':
			case 'e':
				s.state = 463;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case290(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'E':
				s.state = 463;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'e':
				s.state = 464;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case291(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'H':
			case 'h':
				s.state = 465;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case292(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'V':
			case 'v':
				s.state = 466;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case293(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'H':
				s.state = 465;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'h':
				s.state = 467;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case294(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'V':
				s.state = 466;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'v':
				s.state = 468;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case295(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'R':
			case 'r':
				s.state = 469;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case296(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'T':
			case 't':
				s.state = 470;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case297(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'S':
			case 's':
				s.state = 471;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case298(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'R':
			case 'r':
				s.state = 472;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case299(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'D':
			case 'd':
				s.state = 473;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case301(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'N':
			case 'n':
				s.state = 474;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case302(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'R':
				s.state = 469;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'r':
				s.state = 475;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case303(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'T':
				s.state = 470;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 't':
				s.state = 476;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case304(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'S':
				s.state = 471;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 's':
				s.state = 477;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case305(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'R':
				s.state = 472;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'r':
				s.state = 478;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case306(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'D':
				s.state = 473;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'd':
				s.state = 479;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case307(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'N':
				s.state = 474;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'n':
				s.state = 480;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case308(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '+':
			case '-':
				s.state = 481;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case309(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 482;
				break;
			case '1':
				s.state = 483;
				break;
			case '2':
				s.state = 484;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 485;
				break;
			case 'T':
			case 't':
				s.state = 486;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case312(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
			case 'n':
			case 'r':
			case 's':
			case 't':
				s.state = 311;
				break;
			case '0':
				s.state = 487;
				break;
			case '1':
				s.state = 488;
				break;
			case '2':
				s.state = 489;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 490;
				break;
			case 'T':
				s.state = 486;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case313(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 491;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case314(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'd':
				s.state = 492;
				break;
			default:
				s.state = 312;
				break;
		}
	}

	private static void case315(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 't':
				s.state = 492;
				break;
			default:
				s.state = 312;
				break;
		}
	}

	private static void case316(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'h':
				s.state = 492;
				break;
			default:
				s.state = 312;
				break;
		}
	}

	private static void case317(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 491;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case318(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case319(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'U':
			case 'u':
				s.state = 493;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case320(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 451;
				break;
			case 'Y':
			case 'y':
				s.state = 494;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case321(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'E':
			case 'e':
				s.state = 494;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case322(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'U':
				s.state = 493;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'u':
				s.state = 495;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case323(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
				s.state = 451;
				break;
			case 'Y':
				s.state = 494;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 457;
				break;
			case 'y':
				s.state = 496;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case324(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'E':
				s.state = 494;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'e':
				s.state = 496;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case325(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'T':
			case 't':
				s.state = 497;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case326(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'T':
				s.state = 497;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 't':
				s.state = 498;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case327(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'C':
			case 'c':
				s.state = 499;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case328(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case329(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'N':
			case 'n':
				s.state = 500;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case330(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'C':
				s.state = 499;
				break;
			case 'a':
			case 'b':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'c':
				s.state = 501;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case331(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case332(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'N':
				s.state = 500;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'n':
				s.state = 502;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case333(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'T':
			case 't':
				s.state = 503;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case334(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'N':
			case 'n':
				s.state = 504;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case335(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 336;
				break;
		}
	}

	private static void case337(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'T':
				s.state = 503;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 't':
				s.state = 506;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case338(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'N':
				s.state = 504;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'n':
				s.state = 507;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case339(Dbx_scanner s) {
		s.yyaccept = 12;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			default:
				s.state = 336;
				break;
		}
	}

	private static void case340(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'O':
			case 'o':
				s.state = 508;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case341(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'O':
				s.state = 508;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'o':
				s.state = 509;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case342(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'V':
			case 'v':
				s.state = 510;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case343(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'V':
				s.state = 510;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'v':
				s.state = 511;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case344(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'U':
			case 'u':
				s.state = 512;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case345(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'O':
			case 'o':
				s.state = 513;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case346(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'T':
			case 't':
				s.state = 514;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case347(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'E':
			case 'e':
				s.state = 515;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case348(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'U':
				s.state = 512;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'u':
				s.state = 516;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case349(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'O':
				s.state = 513;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'o':
				s.state = 517;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case350(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'T':
				s.state = 514;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 't':
				s.state = 518;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case351(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'E':
				s.state = 515;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'e':
				s.state = 519;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case352(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 72;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 520;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case353(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'R':
			case 'r':
				s.state = 521;
				break;
			case 'S':
			case 's':
				s.state = 503;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case354(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'R':
			case 'r':
				s.state = 522;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case355(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 523;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case356(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'O':
			case 'o':
				s.state = 524;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case357(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'S':
			case 's':
				s.state = 525;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case358(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'L':
			case 'l':
				s.state = 526;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case359(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'R':
				s.state = 521;
				break;
			case 'S':
				s.state = 503;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'r':
				s.state = 527;
				break;
			case 's':
				s.state = 506;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case360(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'R':
				s.state = 522;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'r':
				s.state = 528;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case361(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 523;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'a':
				s.state = 529;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case362(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'O':
				s.state = 524;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'o':
				s.state = 530;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case363(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'S':
				s.state = 525;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 's':
				s.state = 531;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case364(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'L':
				s.state = 526;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'l':
				s.state = 532;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case365(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'I':
				s.state = 533;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case366(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'N':
			case 'n':
				s.state = 534;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case367(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'K':
			case 'k':
				s.state = 535;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case368(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'N':
				s.state = 534;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'n':
				s.state = 536;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case369(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'K':
				s.state = 535;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 'k':
				s.state = 537;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case370(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 451;
				break;
			case 'T':
			case 't':
				s.state = 538;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case371(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 451;
				break;
			case 'T':
				s.state = 538;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 457;
				break;
			case 't':
				s.state = 539;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case372(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 424;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case373(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 540;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case374(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 541;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case375(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case376(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 541;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case377(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 542;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case378(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 543;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case379(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 544;
				break;
			case '3':
				s.state = 546;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 547;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case380(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 425;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case381(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 548;
				break;
			case '6':
				s.state = 549;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 550;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case382(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 551;
				break;
			case '6':
				s.state = 552;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 553;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case383(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 554;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case384(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'd':
				s.state = 555;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case385(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 't':
				s.state = 555;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case386(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'h':
				s.state = 555;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case387(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case388(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case389(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case390(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case391(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case392(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'N':
			case 'l':
			case 'n':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case393(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'Y':
			case 'r':
			case 'y':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case394(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'V':
			case 'v':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case395(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case396(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 557;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case397(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 379;
				break;
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 558;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case398(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 379;
				break;
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 558;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case399(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 559;
				break;
			case '1':
				s.state = 560;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 441;
				break;
			case 'A':
			case 'a':
				s.state = 442;
				break;
			case 'D':
			case 'd':
				s.state = 443;
				break;
			case 'F':
			case 'f':
				s.state = 444;
				break;
			case 'J':
			case 'j':
				s.state = 445;
				break;
			case 'M':
			case 'm':
				s.state = 446;
				break;
			case 'N':
			case 'n':
				s.state = 447;
				break;
			case 'O':
			case 'o':
				s.state = 448;
				break;
			case 'S':
			case 's':
				s.state = 449;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case400(Dbx_scanner s) {
		s.yyaccept = 13;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'n':
			case 'o':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 562;
				break;
			case '-':
				s.state = 563;
				break;
			case '.':
				s.state = 564;
				break;
			case '/':
				s.state = 565;
				break;
			case '0':
				s.state = 566;
				break;
			case '1':
				s.state = 567;
				break;
			case '2':
				s.state = 568;
				break;
			case '3':
				s.state = 569;
				break;
			case '4':
			case '5':
				s.state = 570;
				break;
			case '6':
				s.state = 571;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			case ':':
				s.state = 572;
				break;
			case 'W':
				s.state = 584;
				break;
			default:
				s.state = 401;
				break;
		}
	}

	private static void case402(Dbx_scanner s) {
		s.yyaccept = 14;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'A':
			case 'D':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'M':
			case 'N':
			case 'O':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'X':
			case 'Y':
			case 'a':
			case 'd':
			case 'f':
			case 'h':
			case 'j':
			case 'm':
			case 'n':
			case 'o':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 562;
				break;
			case '-':
				s.state = 563;
				break;
			case '.':
				s.state = 564;
				break;
			case '/':
				s.state = 565;
				break;
			case '0':
				s.state = 586;
				break;
			case '1':
				s.state = 587;
				break;
			case '2':
				s.state = 588;
				break;
			case '3':
				s.state = 589;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			case ':':
				s.state = 572;
				break;
			case 'W':
				s.state = 584;
				break;
			default:
				s.state = 403;
				break;
		}
	}

	private static void case404(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'I':
			case 'i':
				s.state = 590;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case405(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'U':
			case 'u':
				s.state = 591;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case406(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				++s.cursor;
				s.state = 250;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case407(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'E':
			case 'e':
				s.state = 593;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case408(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'R':
			case 'r':
				s.state = 594;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case409(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 595;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case410(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 596;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case411(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case412(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 597;
				break;
			default:
				s.state = 241;
				break;
		}
	}

	private static void case413(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'U':
			case 'u':
				s.state = 598;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case414(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'Y':
			case 'y':
				s.state = 243;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case415(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'E':
			case 'e':
				s.state = 243;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case416(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'C':
			case 'c':
				s.state = 599;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case417(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 600;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case418(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 601;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case419(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				++s.cursor;
				s.state = 250;
				break;
			case 'U':
			case 'u':
				s.state = 602;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case420(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 596;
				break;
			case 'T':
			case 't':
				s.state = 603;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case421(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case422(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'O':
			case 'o':
				s.state = 604;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case423(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 605;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case424(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 606;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 250;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case425(Dbx_scanner s) {
		s.yyaccept = 3;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 238;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 240;
				break;
			case 'T':
			case 't':
				s.state = 407;
				break;
			default:
				s.state = 78;
				break;
		}
	}

	private static void case426(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 607;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case427(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 608;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case428(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 609;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case429(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'K':
			case 'k':
				s.state = 610;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case430(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 611;
				break;
			case '1':
				s.state = 612;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 613;
				break;
			default:
				s.state = 197;
				break;
		}
	}

	private static void case431(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 614;
				break;
			case '6':
				s.state = 615;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 550;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case432(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '-':
				s.state = 377;
				break;
			case '.':
				s.state = 431;
				break;
			case ':':
				s.state = 433;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case433(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 618;
				break;
			case '6':
				s.state = 619;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 553;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case434(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '.':
			case ':':
				s.state = 433;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case435(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '.':
				s.state = 433;
				break;
			case ':':
				s.state = 620;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case438(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 436;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case439(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 621;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 622;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case440(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 621;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 622;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case441(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 621;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case442(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 623;
				break;
			case 'U':
			case 'u':
				s.state = 624;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case443(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 625;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case444(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 626;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case445(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 627;
				break;
			case 'U':
			case 'u':
				s.state = 628;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case446(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 629;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case447(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 630;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case448(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 631;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case449(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 632;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case450(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 633;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case451(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case452(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 635;
				break;
			case '3':
				s.state = 636;
				break;
			default:
				s.state = 136;
				break;
		}
	}

	private static void case453(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'L':
			case 'l':
				s.state = 637;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case454(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'S':
			case 's':
				s.state = 638;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case455(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 455;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case457(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case458(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 635;
				break;
			case '3':
				s.state = 636;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 455;
				break;
			default:
				s.state = 136;
				break;
		}
	}

	private static void case459(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'L':
				s.state = 637;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'l':
				s.state = 640;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case460(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'S':
				s.state = 638;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 's':
				s.state = 641;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case461(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 642;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case462(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 642;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case463(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'M':
			case 'm':
				s.state = 643;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case464(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'M':
				s.state = 643;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'm':
				s.state = 644;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case465(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'T':
			case 't':
				s.state = 645;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case466(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'E':
			case 'e':
				s.state = 646;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case467(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'T':
				s.state = 645;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 't':
				s.state = 647;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case468(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'E':
				s.state = 646;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'e':
				s.state = 648;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case469(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'U':
			case 'u':
				s.state = 649;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case470(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'H':
			case 'h':
				s.state = 650;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case471(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'T':
			case 't':
				s.state = 651;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case472(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'T':
			case 't':
				s.state = 652;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case473(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 653;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case474(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'T':
			case 't':
				s.state = 654;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case475(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'U':
				s.state = 649;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'u':
				s.state = 655;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case476(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'H':
				s.state = 650;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'h':
				s.state = 656;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case477(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'T':
				s.state = 651;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 't':
				s.state = 657;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case478(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'T':
				s.state = 652;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 't':
				s.state = 658;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case479(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 653;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
				s.state = 659;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case480(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'T':
				s.state = 654;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 't':
				s.state = 660;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case481(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 661;
				break;
			case '2':
				s.state = 662;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 663;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case482(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			case '0':
				s.state = 665;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 485;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case483(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 485;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 665;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case484(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 665;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case485(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case486(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 667;
				break;
			case '2':
				s.state = 668;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 665;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case487(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			case '0':
				s.state = 669;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 670;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case488(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 670;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 669;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case489(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 669;
				break;
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 671;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case490(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 671;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case491(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 672;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case493(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 674;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case494(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case495(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 674;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
				s.state = 675;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case496(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 676;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case497(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 677;
				break;
			case ' ':
				s.state = 679;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case498(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 677;
				break;
			case ' ':
				s.state = 679;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case499(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'H':
			case 'h':
				s.state = 637;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case500(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'I':
			case 'i':
				s.state = 680;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case501(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'H':
				s.state = 637;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'h':
				s.state = 640;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case502(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'I':
				s.state = 680;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'i':
				s.state = 681;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case503(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 677;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case504(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 505;
				break;
		}
	}

	private static void case506(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 677;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case507(Dbx_scanner s) {
		s.yyaccept = 15;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			default:
				s.state = 505;
				break;
		}
	}

	private static void case508(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'B':
			case 'b':
				s.state = 682;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case509(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'B':
				s.state = 682;
				break;
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'b':
				s.state = 683;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case510(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'I':
			case 'i':
				s.state = 684;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case511(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'I':
				s.state = 684;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'i':
				s.state = 685;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case512(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'R':
			case 'r':
				s.state = 686;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case513(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'N':
			case 'n':
				s.state = 687;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case514(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 452;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'E':
			case 'e':
				s.state = 688;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case515(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'N':
			case 'n':
				s.state = 689;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case516(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'R':
				s.state = 686;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'r':
				s.state = 690;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case517(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'N':
				s.state = 687;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'n':
				s.state = 691;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case518(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 458;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'E':
				s.state = 688;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'e':
				s.state = 692;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case519(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'N':
				s.state = 689;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'n':
				s.state = 693;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case520(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 694;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case521(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'D':
			case 'd':
				s.state = 650;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case522(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'S':
			case 's':
				s.state = 686;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case523(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 634;
				break;
			case 'Y':
			case 'y':
				s.state = 695;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case524(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'R':
			case 'r':
				s.state = 697;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case525(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'D':
			case 'd':
				s.state = 698;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case526(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'F':
			case 'f':
				s.state = 689;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case527(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'D':
				s.state = 650;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'd':
				s.state = 656;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case528(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'S':
				s.state = 686;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 's':
				s.state = 690;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case529(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
				s.state = 634;
				break;
			case 'Y':
				s.state = 695;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 639;
				break;
			case 'y':
				s.state = 699;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case530(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'R':
				s.state = 697;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'r':
				s.state = 700;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case531(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'D':
				s.state = 698;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'd':
				s.state = 701;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case532(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'F':
				s.state = 689;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'f':
				s.state = 693;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case533(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case534(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'E':
			case 'e':
				s.state = 702;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case535(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'D':
			case 'd':
				s.state = 703;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case536(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'E':
				s.state = 702;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'e':
				s.state = 704;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case537(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'D':
				s.state = 703;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'd':
				s.state = 705;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case538(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 634;
				break;
			case 'E':
			case 'e':
				s.state = 706;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case539(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 634;
				break;
			case 'E':
				s.state = 706;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 639;
				break;
			case 'e':
				s.state = 707;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case540(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '-':
				s.state = 708;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case541(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 708;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case542(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 709;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case543(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 710;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case544(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 712;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case546(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 712;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 709;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case547(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 709;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case548(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 717;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case549(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
				s.state = 717;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 710;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case550(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 710;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case551(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 553;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case552(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
				s.state = 553;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case553(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case554(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 718;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case555(Dbx_scanner s) {
		s.yyaccept = 6;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 383;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case556(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 719;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case557(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 719;
				break;
			case 'T':
			case 't':
				s.state = 556;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case558(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 720;
				break;
			case '.':
				s.state = 377;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case559(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 621;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 441;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case560(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 621;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 441;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case562(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 561;
				break;
			case '-':
			case '.':
				s.state = 721;
				break;
			case 'A':
			case 'a':
				s.state = 573;
				break;
			case 'D':
			case 'd':
				s.state = 574;
				break;
			case 'F':
			case 'f':
				s.state = 575;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'I':
				s.state = 576;
				break;
			case 'J':
			case 'j':
				s.state = 578;
				break;
			case 'M':
			case 'm':
				s.state = 579;
				break;
			case 'N':
			case 'n':
				s.state = 580;
				break;
			case 'O':
			case 'o':
				s.state = 581;
				break;
			case 'S':
			case 's':
				s.state = 582;
				break;
			case 'T':
			case 't':
				s.state = 84;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'V':
				s.state = 583;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'X':
				s.state = 585;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case563(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 723;
				break;
			case '1':
				s.state = 725;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 726;
				break;
			case 'A':
			case 'a':
				s.state = 727;
				break;
			case 'D':
			case 'd':
				s.state = 728;
				break;
			case 'F':
			case 'f':
				s.state = 729;
				break;
			case 'J':
			case 'j':
				s.state = 730;
				break;
			case 'M':
			case 'm':
				s.state = 731;
				break;
			case 'N':
			case 'n':
				s.state = 732;
				break;
			case 'O':
			case 'o':
				s.state = 733;
				break;
			case 'S':
			case 's':
				s.state = 734;
				break;
			case 'W':
				s.state = 735;
				break;
			default:
				s.state = 722;
				break;
		}
	}

	private static void case564(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 736;
				break;
			case '1':
			case '2':
				s.state = 737;
				break;
			case '3':
				s.state = 738;
				break;
			default:
				s.state = 722;
				break;
		}
	}

	private static void case565(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 743;
				break;
			case '1':
				s.state = 744;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 745;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case566(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 746;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 748;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case567(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 748;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 749;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case568(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 749;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case569(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 749;
				break;
			case '6':
				s.state = 750;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 751;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case570(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 751;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case571(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 751;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case572(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 752;
				break;
			case '1':
				s.state = 753;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case573(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 754;
				break;
			case 'U':
			case 'u':
				s.state = 755;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case574(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 232;
				break;
			case 'E':
			case 'e':
				s.state = 756;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case575(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 757;
				break;
			case 'O':
			case 'o':
				s.state = 235;
				break;
			case 'R':
			case 'r':
				s.state = 236;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case576(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
				s.state = 758;
				break;
			case 'V':
			case 'X':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case578(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 760;
				break;
			case 'U':
			case 'u':
				s.state = 761;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case579(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 762;
				break;
			case 'I':
			case 'i':
				s.state = 247;
				break;
			case 'O':
			case 'o':
				s.state = 248;
				break;
			case 'S':
			case 's':
				s.state = 249;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case580(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 763;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case581(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 764;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case582(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 253;
				break;
			case 'E':
			case 'e':
				s.state = 765;
				break;
			case 'U':
			case 'u':
				s.state = 255;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case583(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
				s.state = 585;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case584(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 766;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 767;
				break;
			case '5':
				s.state = 768;
				break;
			case 'E':
			case 'e':
				s.state = 259;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case585(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
				s.state = 758;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case586(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 769;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 770;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case587(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 770;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 771;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case588(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 771;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case589(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 771;
				break;
			case '6':
				s.state = 772;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case590(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case591(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 773;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case593(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 604;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case594(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 598;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case595(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 774;
				break;
			case 'N':
			case 'n':
				s.state = 775;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case596(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 776;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case597(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 241;
				break;
			default:
				s.state = 241;
				break;
		}
	}

	private static void case598(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 778;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case599(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case600(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 779;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case601(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 779;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case602(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 780;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case603(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case604(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 781;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case605(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 608;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case606(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 782;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case607(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 608;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case608(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 596;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case609(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 607;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case610(Dbx_scanner s) {
		s.yyaccept = 7;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 188;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 250;
				break;
			default:
				s.state = 250;
				break;
		}
	}

	private static void case611(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 613;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case612(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
			case '.':
				s.state = 377;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 613;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case613(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
			case '.':
				s.state = 377;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case614(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 783;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case615(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
				s.state = 783;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 710;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case616(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case618(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 784;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case619(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
				s.state = 784;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case620(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 785;
				break;
			case '6':
				s.state = 786;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 553;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case621(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 787;
				break;
			case '3':
				s.state = 788;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 789;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case622(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 790;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case623(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case624(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case625(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case626(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case627(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case628(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'N':
			case 'l':
			case 'n':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case629(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'Y':
			case 'r':
			case 'y':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case630(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'V':
			case 'v':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case631(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case632(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 792;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case633(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 793;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case634(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case635(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 795;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case636(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '0':
			case '1':
				s.state = 795;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 317;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case637(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case638(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'T':
			case 't':
				s.state = 796;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case639(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case640(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 676;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case641(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'T':
				s.state = 796;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 't':
				s.state = 798;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case642(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 799;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case643(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'B':
			case 'b':
				s.state = 800;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case644(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'B':
				s.state = 800;
				break;
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'b':
				s.state = 801;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case645(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'H':
			case 'h':
				s.state = 804;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case646(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'N':
			case 'n':
				s.state = 805;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case647(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'H':
				s.state = 804;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'h':
				s.state = 806;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case648(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'N':
				s.state = 805;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'n':
				s.state = 807;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case649(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 808;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case650(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case651(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 802;
				break;
			case ' ':
				s.state = 809;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case652(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'H':
			case 'h':
				s.state = 804;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case653(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 794;
				break;
			case 'Y':
			case 'y':
				s.state = 810;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case654(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 642;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case655(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 808;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
				s.state = 811;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case656(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case657(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
				s.state = 802;
				break;
			case ' ':
				s.state = 809;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case658(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'H':
				s.state = 804;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'h':
				s.state = 806;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case659(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Z':
				s.state = 794;
				break;
			case 'Y':
				s.state = 810;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 797;
				break;
			case 'y':
				s.state = 812;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case660(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 642;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case661(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 663;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case662(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 663;
				break;
			case '5':
				s.state = 375;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 18;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case663(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 375;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 18;
				break;
			case ':':
				s.state = 193;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case664(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 813;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 815;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case665(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case666(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 816;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 817;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case667(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 665;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case668(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 665;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case669(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 664;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 818;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case670(Dbx_scanner s) {
		s.yyaccept = 11;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 666;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 818;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case671(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 818;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case674(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'R':
			case 'r':
				s.state = 819;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case675(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'R':
				s.state = 819;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'r':
				s.state = 820;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case676(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 455;
				break;
			default:
				s.state = 136;
				break;
		}
	}

	private static void case678(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 677;
				break;
			case 'D':
			case 'd':
				s.state = 821;
				break;
			case 'F':
			case 'f':
				s.state = 822;
				break;
			case 'H':
			case 'h':
				s.state = 823;
				break;
			case 'M':
			case 'm':
				s.state = 824;
				break;
			case 'S':
			case 's':
				s.state = 825;
				break;
			case 'T':
			case 't':
				s.state = 826;
				break;
			case 'U':
			case 'u':
				s.state = 827;
				break;
			case 'W':
			case 'w':
				s.state = 828;
				break;
			case 'Y':
			case 'y':
				s.state = 829;
				break;
			case 0xC2:
				s.state = 830;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case679(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 831;
				break;
			default:
				s.state = 678;
				break;
		}
	}

	private static void case680(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'G':
			case 'g':
				s.state = 832;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case681(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'G':
				s.state = 832;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'g':
				s.state = 833;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case682(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'E':
			case 'e':
				s.state = 834;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case683(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'E':
				s.state = 834;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'e':
				s.state = 835;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case684(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'O':
			case 'o':
				s.state = 836;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case685(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'O':
				s.state = 836;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'o':
				s.state = 837;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case686(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'D':
			case 'd':
				s.state = 838;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case687(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'D':
			case 'd':
				s.state = 804;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case688(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'M':
			case 'm':
				s.state = 839;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case689(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'T':
			case 't':
				s.state = 840;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case690(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'D':
				s.state = 838;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'd':
				s.state = 841;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case691(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'D':
				s.state = 804;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'd':
				s.state = 806;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case692(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'M':
				s.state = 839;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'm':
				s.state = 842;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case693(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'T':
				s.state = 840;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 't':
				s.state = 843;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case694(Dbx_scanner s) {
		s.yyaccept = 13;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 844;
				break;
			case '6':
				s.state = 845;
				break;
			default:
				s.state = 401;
				break;
		}
	}

	private static void case695(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 696;
				break;
		}
	}

	private static void case697(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'R':
			case 'r':
				s.state = 846;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case698(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 847;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case699(Dbx_scanner s) {
		s.yyaccept = 17;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 696;
				break;
		}
	}

	private static void case700(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'R':
				s.state = 846;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'r':
				s.state = 848;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case701(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 847;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
				s.state = 849;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case702(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'S':
			case 's':
				s.state = 850;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case703(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 851;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case704(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'S':
				s.state = 850;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 's':
				s.state = 852;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case705(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 851;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'a':
				s.state = 853;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case706(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 794;
				break;
			case 'R':
			case 'r':
				s.state = 854;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case707(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 794;
				break;
			case 'R':
				s.state = 854;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 797;
				break;
			case 'r':
				s.state = 855;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case708(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 856;
				break;
			case '1':
				s.state = 857;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case709(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case710(Dbx_scanner s) {
		s.yyaccept = 18;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			default:
				s.state = 711;
				break;
		}
	}

	private static void case712(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case713(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'd':
				++s.cursor;
				s.state = 545;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case714(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 't':
				++s.cursor;
				s.state = 545;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case715(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'h':
				++s.cursor;
				s.state = 545;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case716(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 860;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case717(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case718(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 862;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case719(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 863;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case720(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 864;
				break;
			case '3':
				s.state = 865;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 547;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case722(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
				s.state = 721;
				break;
			case 'A':
			case 'a':
				s.state = 573;
				break;
			case 'D':
			case 'd':
				s.state = 739;
				break;
			case 'F':
			case 'f':
				s.state = 740;
				break;
			case 'I':
				s.state = 576;
				break;
			case 'J':
			case 'j':
				s.state = 578;
				break;
			case 'M':
			case 'm':
				s.state = 741;
				break;
			case 'N':
			case 'n':
				s.state = 580;
				break;
			case 'O':
			case 'o':
				s.state = 581;
				break;
			case 'S':
			case 's':
				s.state = 742;
				break;
			case 'V':
				s.state = 583;
				break;
			case 'X':
				s.state = 585;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case723(Dbx_scanner s) {
		s.yyaccept = 19;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 866;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 867;
				break;
			default:
				s.state = 724;
				break;
		}
	}

	private static void case725(Dbx_scanner s) {
		s.yyaccept = 19;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 866;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 867;
				break;
			default:
				s.state = 724;
				break;
		}
	}

	private static void case726(Dbx_scanner s) {
		s.yyaccept = 19;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 866;
				break;
			default:
				s.state = 724;
				break;
		}
	}

	private static void case727(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 868;
				break;
			case 'U':
			case 'u':
				s.state = 869;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case728(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 870;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case729(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 871;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case730(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 872;
				break;
			case 'U':
			case 'u':
				s.state = 873;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case731(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 874;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case732(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 875;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case733(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 876;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case734(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 877;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case735(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 766;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 767;
				break;
			case '5':
				s.state = 768;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case736(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 878;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 879;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case737(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 879;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case738(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 879;
				break;
			case '6':
				s.state = 880;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case739(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 756;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case740(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 757;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case741(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 762;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case742(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 881;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case743(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 882;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 883;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case744(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 882;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 883;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case745(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 882;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case746(Dbx_scanner s) {
		s.yyaccept = 20;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
				s.state = 884;
				break;
			case '1':
			case '2':
				s.state = 885;
				break;
			case '3':
				s.state = 887;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 747;
				break;
		}
	}

	private static void case748(Dbx_scanner s) {
		s.yyaccept = 20;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
				s.state = 885;
				break;
			case '3':
				s.state = 887;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 747;
				break;
		}
	}

	private static void case749(Dbx_scanner s) {
		s.yyaccept = 20;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 747;
				break;
		}
	}

	private static void case750(Dbx_scanner s) {
		s.yyaccept = 20;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
				s.state = 888;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 747;
				break;
		}
	}

	private static void case751(Dbx_scanner s) {
		s.yyaccept = 20;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 747;
				break;
		}
	}

	private static void case752(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 889;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case753(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 889;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case754(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 890;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case755(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 891;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case756(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 892;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case757(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 893;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case758(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case760(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 894;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case761(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 895;
				break;
			case 'N':
			case 'n':
				s.state = 896;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case762(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 897;
				break;
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case763(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'V':
			case 'v':
				s.state = 892;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case764(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 898;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case765(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 424;
				break;
			case 'P':
			case 'p':
				s.state = 899;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case766(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 900;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case767(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 900;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case768(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
				s.state = 900;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case769(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 884;
				break;
			case '1':
			case '2':
				s.state = 885;
				break;
			case '3':
				s.state = 887;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case770(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 885;
				break;
			case '3':
				s.state = 887;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case771(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 888;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case772(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
				s.state = 888;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case773(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case774(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 775;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case775(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 902;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case776(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 250;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case778(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 903;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case779(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 904;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case780(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case781(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 905;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case782(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case783(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case784(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '.':
				s.state = 716;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case785(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 906;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case786(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 716;
				break;
			case '0':
				s.state = 906;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case787(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 789;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case788(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 789;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case789(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case790(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 907;
				break;
			case '3':
				s.state = 908;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 789;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case791(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case792(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'T':
			case 't':
				s.state = 791;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case793(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 910;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case794(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case795(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
				s.state = 309;
				break;
			case '\t':
			case ' ':
			case ',':
			case '.':
			case 'd':
			case 'h':
				s.state = 311;
				break;
			case '-':
				s.state = 911;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 491;
				break;
			case 'n':
			case 'r':
				s.state = 314;
				break;
			case 's':
				s.state = 315;
				break;
			case 't':
				s.state = 316;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case796(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case797(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case798(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
				s.state = 676;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case799(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'F':
			case 'f':
				s.state = 914;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case800(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'E':
			case 'e':
				s.state = 915;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case801(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'E':
				s.state = 915;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'e':
				s.state = 916;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case803(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case 'D':
			case 'd':
				s.state = 821;
				break;
			case 'F':
			case 'f':
				s.state = 822;
				break;
			case 'H':
			case 'h':
				s.state = 823;
				break;
			case 'M':
			case 'm':
				s.state = 824;
				break;
			case 'S':
			case 's':
				s.state = 825;
				break;
			case 'T':
			case 't':
				s.state = 826;
				break;
			case 'U':
			case 'u':
				s.state = 827;
				break;
			case 'W':
			case 'w':
				s.state = 917;
				break;
			case 'Y':
			case 'y':
				s.state = 829;
				break;
			case 0xC2:
				s.state = 830;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case804(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case805(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'T':
			case 't':
				s.state = 918;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case806(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case807(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'T':
				s.state = 918;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 't':
				s.state = 919;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case808(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'R':
			case 'r':
				s.state = 920;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case809(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 831;
				break;
			default:
				s.state = 803;
				break;
		}
	}

	private static void case810(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case811(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'R':
				s.state = 920;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'r':
				s.state = 921;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case812(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case813(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 922;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 815;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case815(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 922;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case816(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 923;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 924;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case817(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
			case ':':
				s.state = 923;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case818(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 310;
				break;
			default:
				s.state = 310;
				break;
		}
	}

	private static void case819(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'Y':
			case 'y':
				s.state = 926;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case820(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'Y':
				s.state = 926;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 912;
				break;
			case 'y':
				s.state = 927;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case821(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 928;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case822(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 929;
				break;
			case 'R':
			case 'r':
				s.state = 930;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case823(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 931;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case824(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 932;
				break;
			case 'O':
			case 'o':
				s.state = 933;
				break;
			case 'S':
			case 's':
				s.state = 934;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case825(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 936;
				break;
			case 'E':
			case 'e':
				s.state = 937;
				break;
			case 'U':
			case 'u':
				s.state = 938;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case826(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 939;
				break;
			case 'U':
			case 'u':
				s.state = 940;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case827(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 941;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case828(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 942;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case829(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 943;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case830(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0xB5:
				s.state = 944;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case831(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 945;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case832(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'H':
			case 'h':
				s.state = 946;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case833(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'H':
				s.state = 946;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'h':
				s.state = 947;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case834(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'R':
			case 'r':
				s.state = 926;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case835(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'R':
				s.state = 926;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'r':
				s.state = 927;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case836(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'U':
			case 'u':
				s.state = 948;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case837(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'U':
				s.state = 948;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'u':
				s.state = 949;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case838(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'A':
			case 'a':
				s.state = 950;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case839(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'B':
			case 'b':
				s.state = 951;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case840(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'H':
			case 'h':
				s.state = 952;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case841(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'A':
				s.state = 950;
				break;
			case 'a':
				s.state = 953;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case842(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'B':
				s.state = 951;
				break;
			case 'a':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'b':
				s.state = 954;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case843(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'H':
				s.state = 952;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'h':
				s.state = 955;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case844(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 747;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case845(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				++s.cursor;
				s.state = 747;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case846(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'O':
			case 'o':
				s.state = 957;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case847(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 300;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case848(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'O':
				s.state = 957;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'o':
				s.state = 959;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case849(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'Y':
				++s.cursor;
				s.state = 300;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 912;
				break;
			case 'y':
				s.state = 960;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case850(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'D':
			case 'd':
				s.state = 961;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case851(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'Y':
			case 'y':
				s.state = 962;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case852(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'D':
				s.state = 961;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'd':
				s.state = 963;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case853(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'Y':
				s.state = 962;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'z':
				s.state = 912;
				break;
			case 'y':
				s.state = 964;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case854(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case 'D':
			case 'd':
				s.state = 965;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case855(Dbx_scanner s) {
		s.yyaccept = 2;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 18;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'D':
				s.state = 965;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 'd':
				s.state = 966;
				break;
			default:
				s.state = 18;
				break;
		}
	}

	private static void case856(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 967;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case857(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 967;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case858(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 968;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case860(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 860;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case862(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 214;
				break;
			default:
				s.state = 214;
				break;
		}
	}

	private static void case863(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 971;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case864(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 972;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case865(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 972;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 709;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case866(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 974;
				break;
			case '3':
				s.state = 975;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 976;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case867(Dbx_scanner s) {
		s.yyaccept = 19;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 977;
				break;
			default:
				s.state = 724;
				break;
		}
	}

	private static void case868(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 978;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case869(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 979;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case870(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 980;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case871(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 981;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case872(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 982;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case873(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 983;
				break;
			case 'N':
			case 'n':
				s.state = 984;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case874(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 985;
				break;
			case 'Y':
			case 'y':
				s.state = 986;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case875(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'V':
			case 'v':
				s.state = 980;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case876(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 987;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case877(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 988;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case878(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 886;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case879(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 886;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case880(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
				++s.cursor;
				s.state = 886;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case881(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'P':
			case 'p':
				s.state = 899;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case882(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 990;
				break;
			case '3':
				s.state = 992;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 993;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case883(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '/':
				s.state = 994;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case884(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 995;
				break;
			default:
				s.state = 187;
				break;
		}
	}

	private static void case885(Dbx_scanner s) {
		s.yyaccept = 22;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 995;
				break;
			default:
				s.state = 886;
				break;
		}
	}

	private static void case887(Dbx_scanner s) {
		s.yyaccept = 22;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
				s.state = 995;
				break;
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 886;
				break;
		}
	}

	private static void case888(Dbx_scanner s) {
		s.yyaccept = 22;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case 'D':
			case 'F':
			case 'H':
			case 'M':
			case 'S':
			case 'T':
			case 'U':
			case 'W':
			case 'Y':
			case 'd':
			case 'f':
			case 'h':
			case 'm':
			case 's':
			case 't':
			case 'u':
			case 'w':
			case 'y':
			case 0xC2:
				s.state = 187;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			default:
				s.state = 886;
				break;
		}
	}

	private static void case889(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 997;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case890(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 998;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case891(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 999;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case892(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1000;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case893(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1001;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case894(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 1002;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case895(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case896(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case897(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1003;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case898(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 1004;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case899(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 892;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case900(Dbx_scanner s) {
		s.yyaccept = 24;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 1005;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
				s.state = 1006;
				break;
			default:
				s.state = 901;
				break;
		}
	}

	private static void case902(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 1008;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case903(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case904(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1009;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case905(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case906(Dbx_scanner s) {
		s.yyaccept = 5;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 616;
				break;
			case '.':
				s.state = 1010;
				break;
			case ':':
				s.state = 1011;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 102;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case907(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1012;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case908(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1012;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case909(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 1013;
				break;
			case '3':
				s.state = 1014;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case910(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1015;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case911(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1016;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case913(Dbx_scanner s) {
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case914(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 1018;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case915(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 926;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case916(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
				s.state = 926;
				break;
			case 'r':
				s.state = 927;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case917(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1019;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case918(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 952;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case919(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
				s.state = 952;
				break;
			case 'h':
				s.state = 955;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case920(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 926;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case921(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
				s.state = 926;
				break;
			case 'y':
				s.state = 927;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case922(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1020;
				break;
			case '6':
				s.state = 1021;
				break;
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case923(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1023;
				break;
			case '6':
				s.state = 1024;
				break;
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case924(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1025;
				break;
			case '.':
			case ':':
				s.state = 923;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1027;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case926(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '-':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case927(Dbx_scanner s) {
		s.yyaccept = 8;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '.':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 136;
				break;
			case '-':
				s.state = 676;
				break;
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 280;
				break;
		}
	}

	private static void case928(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case929(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1029;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case930(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 1030;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case931(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 1031;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case932(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1032;
				break;
			case 'L':
			case 'l':
				s.state = 1033;
				break;
			case 'N':
			case 'n':
				s.state = 1034;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case933(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 1035;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case934(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1036;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case936(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 1037;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case937(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1038;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case938(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 1030;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case939(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 1039;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case940(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1040;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case941(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1036;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case942(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 1041;
				break;
			case 'E':
			case 'e':
				s.state = 1042;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case943(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 1031;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case944(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 934;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case945(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 1043;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case946(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				++s.cursor;
				s.state = 696;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case947(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
				++s.cursor;
				s.state = 696;
				break;
			case 't':
				s.state = 1045;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case948(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 1046;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case949(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
				s.state = 1046;
				break;
			case 's':
				s.state = 1047;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case950(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 300;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case951(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 915;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case952(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case953(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
				++s.cursor;
				s.state = 300;
				break;
			case 'y':
				s.state = 960;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case954(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
				s.state = 915;
				break;
			case 'e':
				s.state = 916;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case955(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 802;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case957(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'W':
			case 'w':
				s.state = 1048;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case959(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'W':
				s.state = 1048;
				break;
			case 'w':
				s.state = 1050;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case960(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case961(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 950;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case962(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				++s.cursor;
				s.state = 300;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case963(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
				s.state = 950;
				break;
			case 'a':
				s.state = 953;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case964(Dbx_scanner s) {
		s.yyaccept = 10;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'S':
				++s.cursor;
				s.state = 300;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			case 's':
				s.state = 960;
				break;
			default:
				s.state = 300;
				break;
		}
	}

	private static void case965(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 1051;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case966(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
				s.state = 1051;
				break;
			case 'a':
				s.state = 1052;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case967(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 1053;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case971(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1054;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case972(Dbx_scanner s) {
		s.yyaccept = 26;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 858;
				break;
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 973;
				break;
		}
	}

	private static void case974(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 976;
				break;
			case 'T':
				s.state = 1055;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case975(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 976;
				break;
			case 'T':
				s.state = 1055;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case976(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'T':
				s.state = 1055;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case977(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 1059;
				break;
			case '3':
				s.state = 1060;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 976;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case978(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'I':
			case 'i':
				s.state = 998;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case979(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'U':
			case 'u':
				s.state = 999;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case980(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'E':
			case 'e':
				s.state = 1000;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case981(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'R':
			case 'r':
				s.state = 1001;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case982(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'U':
			case 'u':
				s.state = 1002;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case983(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case984(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'E':
			case 'e':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case985(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'C':
			case 'c':
				s.state = 1003;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case986(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case987(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'O':
			case 'o':
				s.state = 1004;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case988(Dbx_scanner s) {
		s.yyaccept = 23;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
				s.state = 909;
				break;
			case 'T':
			case 't':
				s.state = 980;
				break;
			default:
				s.state = 577;
				break;
		}
	}

	private static void case990(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 993;
				break;
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case992(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 993;
				break;
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case993(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case994(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 1064;
				break;
			case '3':
				s.state = 1065;
				break;
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 993;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case995(Dbx_scanner s) {
		s.yyaccept = 28;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 186;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 57;
				break;
			case 'D':
			case 'd':
				s.state = 188;
				break;
			case 'F':
			case 'f':
				s.state = 189;
				break;
			case 'H':
			case 'h':
				s.state = 76;
				break;
			case 'M':
			case 'm':
				s.state = 190;
				break;
			case 'S':
			case 's':
				s.state = 191;
				break;
			case 'T':
				s.state = 1066;
				break;
			case 'U':
			case 'u':
				s.state = 85;
				break;
			case 'W':
			case 'w':
				s.state = 87;
				break;
			case 'Y':
			case 'y':
				s.state = 89;
				break;
			case 't':
				s.state = 1067;
				break;
			case 0xC2:
				s.state = 94;
				break;
			default:
				s.state = 996;
				break;
		}
	}

	private static void case997(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 1068;
				break;
			case '3':
				s.state = 1069;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case998(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case999(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 1070;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1000(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 1004;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1001(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'U':
			case 'u':
				s.state = 1002;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1002(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 1071;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1003(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1004(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'B':
			case 'b':
				s.state = 1072;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1005(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
				s.state = 1006;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1008(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 1073;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1009(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1074;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1010(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1075;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1011(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1077;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1012(Dbx_scanner s) {
		s.yyaccept = 26;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'n':
			case 'r':
				s.state = 713;
				break;
			case 's':
				s.state = 714;
				break;
			case 't':
				s.state = 715;
				break;
			default:
				s.state = 973;
				break;
		}
	}

	private static void case1013(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1079;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1014(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1079;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1015(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1081;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1016(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1083;
				break;
			default:
				s.state = 1017;
				break;
		}
	}

	private static void case1018(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1084;
				break;
			case '2':
				s.state = 1086;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1087;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1019(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 1041;
				break;
			case 'E':
			case 'e':
				s.state = 1088;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1020(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1089;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1021(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 1089;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1023(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1090;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1024(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 1090;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1025(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1025;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1027;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1027(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 1091;
				break;
			case 'M':
			case 'm':
				s.state = 1092;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1028(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1029(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 1094;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1030(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'D':
			case 'd':
				s.state = 1097;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1031(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1032(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1098;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1033(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'L':
			case 'l':
				s.state = 1099;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1034(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			case 'U':
			case 'u':
				s.state = 1100;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1035(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'D':
			case 'd':
				s.state = 1097;
				break;
			case 'T':
			case 't':
				s.state = 1101;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1036(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1037(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'U':
			case 'u':
				s.state = 1102;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1038(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 1103;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1039(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'R':
			case 'r':
				s.state = 1104;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1040(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'S':
			case 's':
				s.state = 1105;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1041(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'N':
			case 'n':
				s.state = 1106;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1042(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'K':
			case 'k':
				s.state = 1107;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1043(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 1109;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1045(Dbx_scanner s) {
		s.yyaccept = 17;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 696;
				break;
		}
	}

	private static void case1046(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 677;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1047(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 677;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case1050(Dbx_scanner s) {
		s.yyaccept = 29;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 1049;
				break;
		}
	}

	private static void case1051(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 1110;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1052(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
				s.state = 1110;
				break;
			case 'y':
				s.state = 1112;
				break;
			default:
				s.state = 913;
				break;
		}
	}

	private static void case1053(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
				s.state = 1113;
				break;
			case '3':
				s.state = 1114;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1054(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1115;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1055(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1116;
				break;
			case '2':
				s.state = 1117;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1118;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1056(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'd':
				s.state = 1119;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1057(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 't':
				s.state = 1119;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1058(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'h':
				s.state = 1119;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1059(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1120;
				break;
			case 'T':
				s.state = 1055;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case1060(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1120;
				break;
			case 'T':
				s.state = 1055;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case1061(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'd':
				++s.cursor;
				s.state = 991;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1062(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 't':
				++s.cursor;
				s.state = 991;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1063(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'h':
				++s.cursor;
				s.state = 991;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1064(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1122;
				break;
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case1065(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1122;
				break;
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case1066(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1123;
				break;
			case '2':
				s.state = 1124;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1125;
				break;
			case 'H':
			case 'h':
				s.state = 256;
				break;
			case 'U':
			case 'u':
				s.state = 257;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1067(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1126;
				break;
			case '2':
				s.state = 1127;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1128;
				break;
			case 'H':
			case 'h':
				s.state = 256;
				break;
			case 'U':
			case 'u':
				s.state = 257;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1068(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1129;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1069(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1129;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1070(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1071(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1130;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1072(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1131;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1073(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 406;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1074(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 606;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1075(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1075;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1132;
				break;
			default:
				s.state = 209;
				break;
		}
	}

	private static void case1077(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1077;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1132;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1083(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1133;
				break;
			default:
				s.state = 1017;
				break;
		}
	}

	private static void case1084(Dbx_scanner s) {
		s.yyaccept = 30;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1134;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1087;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1136;
				break;
			default:
				s.state = 1085;
				break;
		}
	}

	private static void case1086(Dbx_scanner s) {
		s.yyaccept = 30;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1134;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1087;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1136;
				break;
			default:
				s.state = 1085;
				break;
		}
	}

	private static void case1087(Dbx_scanner s) {
		s.yyaccept = 30;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1134;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1136;
				break;
			default:
				s.state = 1085;
				break;
		}
	}

	private static void case1088(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'K':
			case 'k':
				s.state = 1137;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1089(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '(':
			case '+':
			case '-':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1139;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1090(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
			case '(':
			case '+':
			case '-':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1146;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1091(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 1092;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1092(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1149;
				break;
			case '.':
				s.state = 1151;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1094(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 1152;
				break;
			case 'N':
			case 'n':
				s.state = 1153;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1095(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			case 'O':
			case 'o':
				s.state = 1154;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1097(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'a':
				s.state = 1155;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1098(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 1156;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1099(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 1156;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1100(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 1157;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1101(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1102(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				s.state = 1105;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1103(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 1158;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1104(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 1105;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1105(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 1097;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1106(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1104;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1107(Dbx_scanner s) {
		s.yyaccept = 31;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 821;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			default:
				s.state = 1108;
				break;
		}
	}

	private static void case1109(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 1159;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1112(Dbx_scanner s) {
		s.yyaccept = 32;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 282;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 912;
				break;
			default:
				s.state = 1111;
				break;
		}
	}

	private static void case1113(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 991;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1114(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				++s.cursor;
				s.state = 991;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1115(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1160;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1116(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1118;
				break;
			case ':':
				s.state = 1161;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1117(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1118;
				break;
			case ':':
				s.state = 1161;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1118(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1161;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1119(Dbx_scanner s) {
		s.yyaccept = 16;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'T':
				s.state = 1055;
				break;
			default:
				s.state = 545;
				break;
		}
	}

	private static void case1120(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'T':
				s.state = 1162;
				break;
			case 'n':
			case 'r':
				s.state = 1056;
				break;
			case 's':
				s.state = 1057;
				break;
			case 't':
				s.state = 1058;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case1122(Dbx_scanner s) {
		s.yyaccept = 27;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '/':
				++s.cursor;
				s.state = 991;
				break;
			case 'n':
			case 'r':
				s.state = 1061;
				break;
			case 's':
				s.state = 1062;
				break;
			case 't':
				s.state = 1063;
				break;
			default:
				s.state = 991;
				break;
		}
	}

	private static void case1123(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1163;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1125;
				break;
			case ':':
				s.state = 1164;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1124(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1163;
				break;
			case '5':
				s.state = 1165;
				break;
			case ':':
				s.state = 1164;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1125(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1165;
				break;
			case ':':
				s.state = 1164;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1126(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1166;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1128;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1127(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1166;
				break;
			case '5':
				s.state = 1165;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1128(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1165;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1129(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ' ':
				s.state = 1167;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1130(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1131(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'R':
			case 'r':
				++s.cursor;
				s.state = 577;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1132(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 1168;
				break;
			case 'M':
			case 'm':
				s.state = 1169;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1133(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1017;
				break;
			default:
				s.state = 1017;
				break;
		}
	}

	private static void case1134(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1134;
				break;
			case 'A':
			case 'P':
			case 'a':
			case 'p':
				s.state = 1136;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1136(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 1171;
				break;
			case 'M':
			case 'm':
				s.state = 1172;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1137(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 821;
				break;
			case 'S':
			case 's':
				++s.cursor;
				s.state = 935;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1139(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1138;
				break;
			case '(':
				s.state = 1140;
				break;
			case '+':
			case '-':
				s.state = 1141;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1142;
				break;
			case 'G':
				s.state = 1143;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1144;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1140(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1144;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1141(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1173;
				break;
			case '2':
				s.state = 1174;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1175;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1142(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1176;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1177;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1143(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1176;
				break;
			case 'M':
				s.state = 1178;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1177;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1144(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1176;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1146(Dbx_scanner s) {
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1145;
				break;
			case '(':
				s.state = 1140;
				break;
			case '+':
			case '-':
				s.state = 1141;
				break;
			case 'A':
			case 'P':
				s.state = 1147;
				break;
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1142;
				break;
			case 'G':
				s.state = 1143;
				break;
			case 'a':
			case 'p':
				s.state = 1148;
				break;
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1144;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1147(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '.':
				s.state = 1091;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1176;
				break;
			case 'M':
				s.state = 1179;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1177;
				break;
			case 'm':
				s.state = 1180;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1148(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '.':
				s.state = 1091;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1176;
				break;
			case 'M':
			case 'm':
				s.state = 1179;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1151(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1149;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1152(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'N':
			case 'n':
				s.state = 1153;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1153(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'I':
			case 'i':
				s.state = 1181;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1154(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'F':
			case 'f':
				s.state = 1182;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1155(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'Y':
			case 'y':
				s.state = 1184;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1156(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'S':
			case 's':
				s.state = 1185;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1157(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1158(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'D':
			case 'd':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1159(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'F':
			case 'f':
				s.state = 1186;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1160(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1188;
				break;
			case '2':
				s.state = 1189;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1161(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1190;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1191;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1162(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1192;
				break;
			case '2':
				s.state = 1193;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1118;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1163(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1194;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1195;
				break;
			case ':':
				s.state = 1164;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1164(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1196;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1165(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1195;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1166(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1194;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1195;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1167(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1197;
				break;
			case '2':
				s.state = 1198;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1168(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 1169;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1169(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1199;
				break;
			case '.':
				s.state = 1201;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1171(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
			case 'm':
				s.state = 1172;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1172(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				++s.cursor;
				s.state = 1085;
				break;
			case '.':
				s.state = 1203;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1173(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1175;
				break;
			case ':':
				s.state = 1204;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1174(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1175;
				break;
			case '5':
				s.state = 1205;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			case ':':
				s.state = 1204;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1175(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1205;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			case ':':
				s.state = 1204;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1176(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1206;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1177(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1206;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1208;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1178(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1206;
				break;
			case 'T':
				s.state = 1209;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1179(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1149;
				break;
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '.':
				s.state = 1151;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1206;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1180(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1149;
				break;
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case '.':
				s.state = 1151;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1206;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1208;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1181(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'G':
			case 'g':
				s.state = 1210;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1184(Dbx_scanner s) {
		s.yyaccept = 25;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1095;
				break;
			default:
				s.state = 935;
				break;
		}
	}

	private static void case1185(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'E':
			case 'e':
				s.state = 1211;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1188(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1212;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1189(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1212;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1190(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1191;
				break;
			case ':':
				s.state = 1213;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1191(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1213;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1192(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1214;
				break;
			case ':':
				s.state = 1161;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1193(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1214;
				break;
			case ':':
				s.state = 1161;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1194(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1213;
				break;
			case '6':
				s.state = 1215;
				break;
			case '7':
			case '8':
			case '9':
				s.state = 1195;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1195(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1216;
				break;
			case '6':
				s.state = 1217;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1196(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1218;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1197(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1219;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1198(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1219;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1201(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				s.state = 1199;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1203(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 0x00:
			case '\t':
			case ' ':
				++s.cursor;
				s.state = 1085;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1204(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1205;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1205(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 814;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1206(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1220;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1207(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1221;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1208(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1220;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1223;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1209(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '+':
			case '-':
				s.state = 1141;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1220;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1210(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'H':
			case 'h':
				s.state = 1224;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1211(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'C':
			case 'c':
				s.state = 1225;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1212(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1226;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1213(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1227;
				break;
			case '6':
				s.state = 1229;
				break;
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1214(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1231;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1215(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 1227;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1216;
				break;
			case '6':
				s.state = 1217;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1216(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1217(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1218(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1195;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1219(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1164;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1220(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1232;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1221(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1221;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1223(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1232;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1233;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1224(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
			case 't':
				s.state = 1028;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1225(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'O':
			case 'o':
				s.state = 1103;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1226(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1234;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1227(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1229(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1231(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1235;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1191;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1232(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1236;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1233(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				s.state = 1236;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1237;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1234(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1238;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1235(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1239;
				break;
			case ':':
				s.state = 1213;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1236(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1237(Dbx_scanner s) {
		s.yyaccept = 21;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case ')':
				++s.cursor;
				s.state = 814;
				break;
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1240;
				break;
			default:
				s.state = 814;
				break;
		}
	}

	private static void case1238(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1242;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1239(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case ':':
				s.state = 1243;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1240(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '-':
			case '/':
			case '_':
				s.state = 1207;
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				s.state = 1240;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1242(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1244;
				break;
			case '6':
				s.state = 1245;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1243(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1246;
				break;
			case '6':
				s.state = 1247;
				break;
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1244(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1248;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1245(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 1248;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1246(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1249;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1247(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
				s.state = 1249;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1248(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1250;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1249(Dbx_scanner s) {
		s.yyaccept = 33;
		s.ptr = ++s.cursor;
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '.':
				s.state = 1252;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1250(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '\t':
			case ' ':
				s.state = 1250;
				break;
			case '+':
			case '-':
				s.state = 1253;
				break;
			case 'G':
				s.state = 1254;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1252(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1255;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1253(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1257;
				break;
			case '2':
				s.state = 1259;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1260;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1254(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
				s.state = 1261;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1255(Dbx_scanner s) {
		s.yych = s.src[s.cursor];
		switch (s.yych) {
			case '+':
			case '-':
				s.state = 1262;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1255;
				break;
			case 'G':
				s.state = 1263;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1257(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1260;
				break;
			case ':':
				s.state = 1264;
				break;
			default:
				s.state = 1258;
				break;
		}
	}

	private static void case1259(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1260;
				break;
			case '5':
				s.state = 1265;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1258;
				break;
			case ':':
				s.state = 1264;
				break;
			default:
				s.state = 1258;
				break;
		}
	}

	private static void case1260(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1265;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1258;
				break;
			case ':':
				s.state = 1264;
				break;
			default:
				s.state = 1258;
				break;
		}
	}

	private static void case1261(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
				s.state = 1267;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1262(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
				s.state = 1268;
				break;
			case '2':
				s.state = 1269;
				break;
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1270;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1263(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'M':
				s.state = 1271;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1264(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1265;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1258;
				break;
			default:
				s.state = 1258;
				break;
		}
	}

	private static void case1265(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1258;
				break;
			default:
				s.state = 1258;
				break;
		}
	}

	private static void case1267(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '+':
			case '-':
				s.state = 1253;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1268(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				s.state = 1270;
				break;
			case ':':
				s.state = 1272;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1269(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
				s.state = 1270;
				break;
			case '5':
				s.state = 1227;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			case ':':
				s.state = 1272;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1270(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1227;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			case ':':
				s.state = 1272;
				break;
			default:
				s.state = 1228;
				break;
		}
	}

	private static void case1271(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case 'T':
				s.state = 1273;
				break;
			default:
				s.state = 56;
				break;
		}
	}

	private static void case1272(Dbx_scanner s) {
		s.yych = s.src[++s.cursor];
		switch (s.yych) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				s.state = 1227;
				break;
			case '6':
			case '7':
			case '8':
			case '9':
				++s.cursor;
				s.state = 1228;
				break;
			default:
				s.state = 1228;
				break;
		}
	}
}
