/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2020 https://github.com/desb42

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/

package gplx.xowa.langs.cases;

import gplx.Bry_;

class Xol_case_cvt_ {
	public static final byte[] byte_NOCHANGE = null;

	public static byte[] Upper(byte[] src, int pos, int b_len) {
		byte b1, b2, b3, b4;

		switch (b_len) {
			case 1:
				b1 = src[pos++];
				switch (b1) {
					case 97: return byte_65;	// a -> A -- LATIN CAPITAL LETTER A
					case 98: return byte_66;	// b -> B -- LATIN CAPITAL LETTER B
					case 99: return byte_67;	// c -> C -- LATIN CAPITAL LETTER C
					case 100: return byte_68;	// d -> D -- LATIN CAPITAL LETTER D
					case 101: return byte_69;	// e -> E -- LATIN CAPITAL LETTER E
					case 102: return byte_70;	// f -> F -- LATIN CAPITAL LETTER F
					case 103: return byte_71;	// g -> G -- LATIN CAPITAL LETTER G
					case 104: return byte_72;	// h -> H -- LATIN CAPITAL LETTER H
					case 105: return byte_73;	// i -> I -- LATIN CAPITAL LETTER I
					case 106: return byte_74;	// j -> J -- LATIN CAPITAL LETTER J
					case 107: return byte_75;	// k -> K -- LATIN CAPITAL LETTER K
					case 108: return byte_76;	// l -> L -- LATIN CAPITAL LETTER L
					case 109: return byte_77;	// m -> M -- LATIN CAPITAL LETTER M
					case 110: return byte_78;	// n -> N -- LATIN CAPITAL LETTER N
					case 111: return byte_79;	// o -> O -- LATIN CAPITAL LETTER O
					case 112: return byte_80;	// p -> P -- LATIN CAPITAL LETTER P
					case 113: return byte_81;	// q -> Q -- LATIN CAPITAL LETTER Q
					case 114: return byte_82;	// r -> R -- LATIN CAPITAL LETTER R
					case 115: return byte_83;	// s -> S -- LATIN CAPITAL LETTER S
					case 116: return byte_84;	// t -> T -- LATIN CAPITAL LETTER T
					case 117: return byte_85;	// u -> U -- LATIN CAPITAL LETTER U
					case 118: return byte_86;	// v -> V -- LATIN CAPITAL LETTER V
					case 119: return byte_87;	// w -> W -- LATIN CAPITAL LETTER W
					case 120: return byte_88;	// x -> X -- LATIN CAPITAL LETTER X
					case 121: return byte_89;	// y -> Y -- LATIN CAPITAL LETTER Y
					case 122: return byte_90;	// z -> Z -- LATIN CAPITAL LETTER Z
				}
				break;
			case 2:
				b1 = src[pos++];
				b2 = src[pos++];
				switch (b1) {
					case -62:
						if (b2 == -75) return byte_206_156;	// µ -> Μ -- MICRO SIGN; SWAPPED
						break;
					case -61:
						switch (b2) {
							case -96: return byte_195_128;	// à -> À -- LATIN CAPITAL LETTER A GRAVE
							case -95: return byte_195_129;	// á -> Á -- LATIN CAPITAL LETTER A ACUTE
							case -94: return byte_195_130;	// â -> Â -- LATIN CAPITAL LETTER A CIRCUMFLEX
							case -93: return byte_195_131;	// ã -> Ã -- LATIN CAPITAL LETTER A TILDE
							case -92: return byte_195_132;	// ä -> Ä -- LATIN CAPITAL LETTER A DIAERESIS
							case -91: return byte_195_133;	// å -> Å -- LATIN CAPITAL LETTER A RING
							case -90: return byte_195_134;	// æ -> Æ -- LATIN CAPITAL LETTER A E
							case -89: return byte_195_135;	// ç -> Ç -- LATIN CAPITAL LETTER C CEDILLA
							case -88: return byte_195_136;	// è -> È -- LATIN CAPITAL LETTER E GRAVE
							case -87: return byte_195_137;	// é -> É -- LATIN CAPITAL LETTER E ACUTE
							case -86: return byte_195_138;	// ê -> Ê -- LATIN CAPITAL LETTER E CIRCUMFLEX
							case -85: return byte_195_139;	// ë -> Ë -- LATIN CAPITAL LETTER E DIAERESIS
							case -84: return byte_195_140;	// ì -> Ì -- LATIN CAPITAL LETTER I GRAVE
							case -83: return byte_195_141;	// í -> Í -- LATIN CAPITAL LETTER I ACUTE
							case -82: return byte_195_142;	// î -> Î -- LATIN CAPITAL LETTER I CIRCUMFLEX
							case -81: return byte_195_143;	// ï -> Ï -- LATIN CAPITAL LETTER I DIAERESIS
							case -80: return byte_195_144;	// ð -> Ð -- LATIN CAPITAL LETTER ETH
							case -79: return byte_195_145;	// ñ -> Ñ -- LATIN CAPITAL LETTER N TILDE
							case -78: return byte_195_146;	// ò -> Ò -- LATIN CAPITAL LETTER O GRAVE
							case -77: return byte_195_147;	// ó -> Ó -- LATIN CAPITAL LETTER O ACUTE
							case -76: return byte_195_148;	// ô -> Ô -- LATIN CAPITAL LETTER O CIRCUMFLEX
							case -75: return byte_195_149;	// õ -> Õ -- LATIN CAPITAL LETTER O TILDE
							case -74: return byte_195_150;	// ö -> Ö -- LATIN CAPITAL LETTER O DIAERESIS
							case -72: return byte_195_152;	// ø -> Ø -- LATIN CAPITAL LETTER O SLASH
							case -71: return byte_195_153;	// ù -> Ù -- LATIN CAPITAL LETTER U GRAVE
							case -70: return byte_195_154;	// ú -> Ú -- LATIN CAPITAL LETTER U ACUTE
							case -69: return byte_195_155;	// û -> Û -- LATIN CAPITAL LETTER U CIRCUMFLEX
							case -68: return byte_195_156;	// ü -> Ü -- LATIN CAPITAL LETTER U DIAERESIS
							case -67: return byte_195_157;	// ý -> Ý -- LATIN CAPITAL LETTER Y ACUTE
							case -66: return byte_195_158;	// þ -> Þ -- LATIN CAPITAL LETTER THORN
							case -65: return byte_197_184;	// ÿ -> Ÿ -- LATIN SMALL LETTER Y DIAERESIS
						}
						break;
					case -60:
						switch (b2) {
							case -127: return byte_196_128;	// ā -> Ā -- LATIN CAPITAL LETTER A MACRON
							case -125: return byte_196_130;	// ă -> Ă -- LATIN CAPITAL LETTER A BREVE
							case -123: return byte_196_132;	// ą -> Ą -- LATIN CAPITAL LETTER A OGONEK
							case -121: return byte_196_134;	// ć -> Ć -- LATIN CAPITAL LETTER C ACUTE
							case -119: return byte_196_136;	// ĉ -> Ĉ -- LATIN CAPITAL LETTER C CIRCUMFLEX
							case -117: return byte_196_138;	// ċ -> Ċ -- LATIN CAPITAL LETTER C DOT
							case -115: return byte_196_140;	// č -> Č -- LATIN CAPITAL LETTER C HACEK
							case -113: return byte_196_142;	// ď -> Ď -- LATIN CAPITAL LETTER D HACEK
							case -111: return byte_196_144;	// đ -> Đ -- LATIN CAPITAL LETTER D BAR
							case -109: return byte_196_146;	// ē -> Ē -- LATIN CAPITAL LETTER E MACRON
							case -107: return byte_196_148;	// ĕ -> Ĕ -- LATIN CAPITAL LETTER E BREVE
							case -105: return byte_196_150;	// ė -> Ė -- LATIN CAPITAL LETTER E DOT
							case -103: return byte_196_152;	// ę -> Ę -- LATIN CAPITAL LETTER E OGONEK
							case -101: return byte_196_154;	// ě -> Ě -- LATIN CAPITAL LETTER E HACEK
							case -99: return byte_196_156;	// ĝ -> Ĝ -- LATIN CAPITAL LETTER G CIRCUMFLEX
							case -97: return byte_196_158;	// ğ -> Ğ -- LATIN CAPITAL LETTER G BREVE
							case -95: return byte_196_160;	// ġ -> Ġ -- LATIN CAPITAL LETTER G DOT
							case -93: return byte_196_162;	// ģ -> Ģ -- LATIN CAPITAL LETTER G CEDILLA
							case -91: return byte_196_164;	// ĥ -> Ĥ -- LATIN CAPITAL LETTER H CIRCUMFLEX
							case -89: return byte_196_166;	// ħ -> Ħ -- LATIN CAPITAL LETTER H BAR
							case -87: return byte_196_168;	// ĩ -> Ĩ -- LATIN CAPITAL LETTER I TILDE
							case -85: return byte_196_170;	// ī -> Ī -- LATIN CAPITAL LETTER I MACRON
							case -83: return byte_196_172;	// ĭ -> Ĭ -- LATIN CAPITAL LETTER I BREVE
							case -81: return byte_196_174;	// į -> Į -- LATIN CAPITAL LETTER I OGONEK
							case -79: return byte_73;	// ı -> I -- LATIN SMALL LETTER DOTLESS I
							case -77: return byte_196_178;	// ĳ -> Ĳ -- LATIN CAPITAL LETTER I J
							case -75: return byte_196_180;	// ĵ -> Ĵ -- LATIN CAPITAL LETTER J CIRCUMFLEX
							case -73: return byte_196_182;	// ķ -> Ķ -- LATIN CAPITAL LETTER K CEDILLA
							case -70: return byte_196_185;	// ĺ -> Ĺ -- LATIN CAPITAL LETTER L ACUTE
							case -68: return byte_196_187;	// ļ -> Ļ -- LATIN CAPITAL LETTER L CEDILLA
							case -66: return byte_196_189;	// ľ -> Ľ -- LATIN CAPITAL LETTER L HACEK
						}
						break;
					case -59:
						switch (b2) {
							case -128: return byte_196_191;	// ŀ -> Ŀ -- LATIN CAPITAL LETTER L WITH MIDDLE DOT
							case -126: return byte_197_129;	// ł -> Ł -- LATIN CAPITAL LETTER L SLASH
							case -124: return byte_197_131;	// ń -> Ń -- LATIN CAPITAL LETTER N ACUTE
							case -122: return byte_197_133;	// ņ -> Ņ -- LATIN CAPITAL LETTER N CEDILLA
							case -120: return byte_197_135;	// ň -> Ň -- LATIN CAPITAL LETTER N HACEK
							case -117: return byte_197_138;	// ŋ -> Ŋ -- LATIN CAPITAL LETTER ENG
							case -115: return byte_197_140;	// ō -> Ō -- LATIN CAPITAL LETTER O MACRON
							case -113: return byte_197_142;	// ŏ -> Ŏ -- LATIN CAPITAL LETTER O BREVE
							case -111: return byte_197_144;	// ő -> Ő -- LATIN CAPITAL LETTER O DOUBLE ACUTE
							case -109: return byte_197_146;	// œ -> Œ -- LATIN CAPITAL LETTER O E
							case -107: return byte_197_148;	// ŕ -> Ŕ -- LATIN CAPITAL LETTER R ACUTE
							case -105: return byte_197_150;	// ŗ -> Ŗ -- LATIN CAPITAL LETTER R CEDILLA
							case -103: return byte_197_152;	// ř -> Ř -- LATIN CAPITAL LETTER R HACEK
							case -101: return byte_197_154;	// ś -> Ś -- LATIN CAPITAL LETTER S ACUTE
							case -99: return byte_197_156;	// ŝ -> Ŝ -- LATIN CAPITAL LETTER S CIRCUMFLEX
							case -97: return byte_197_158;	// ş -> Ş -- LATIN CAPITAL LETTER S CEDILLA
							case -95: return byte_197_160;	// š -> Š -- LATIN CAPITAL LETTER S HACEK
							case -93: return byte_197_162;	// ţ -> Ţ -- LATIN CAPITAL LETTER T CEDILLA
							case -91: return byte_197_164;	// ť -> Ť -- LATIN CAPITAL LETTER T HACEK
							case -89: return byte_197_166;	// ŧ -> Ŧ -- LATIN CAPITAL LETTER T BAR
							case -87: return byte_197_168;	// ũ -> Ũ -- LATIN CAPITAL LETTER U TILDE
							case -85: return byte_197_170;	// ū -> Ū -- LATIN CAPITAL LETTER U MACRON
							case -83: return byte_197_172;	// ŭ -> Ŭ -- LATIN CAPITAL LETTER U BREVE
							case -81: return byte_197_174;	// ů -> Ů -- LATIN CAPITAL LETTER U RING
							case -79: return byte_197_176;	// ű -> Ű -- LATIN CAPITAL LETTER U DOUBLE ACUTE
							case -77: return byte_197_178;	// ų -> Ų -- LATIN CAPITAL LETTER U OGONEK
							case -75: return byte_197_180;	// ŵ -> Ŵ -- LATIN CAPITAL LETTER W CIRCUMFLEX
							case -73: return byte_197_182;	// ŷ -> Ŷ -- LATIN CAPITAL LETTER Y CIRCUMFLEX
							case -70: return byte_197_185;	// ź -> Ź -- LATIN CAPITAL LETTER Z ACUTE
							case -68: return byte_197_187;	// ż -> Ż -- LATIN CAPITAL LETTER Z DOT
							case -66: return byte_197_189;	// ž -> Ž -- LATIN CAPITAL LETTER Z HACEK
							case -65: return byte_83;	// ſ -> S -- LATIN SMALL LETTER LONG S
						}
						break;
					case -58:
						switch (b2) {
							case -128: return byte_201_131;	// ƀ -> Ƀ -- LATIN SMALL LETTER B BAR
							case -125: return byte_198_130;	// ƃ -> Ƃ -- LATIN CAPITAL LETTER B TOPBAR
							case -123: return byte_198_132;	// ƅ -> Ƅ -- LATIN CAPITAL LETTER TONE SIX
							case -120: return byte_198_135;	// ƈ -> Ƈ -- LATIN CAPITAL LETTER C HOOK
							case -116: return byte_198_139;	// ƌ -> Ƌ -- LATIN CAPITAL LETTER D TOPBAR
							case -110: return byte_198_145;	// ƒ -> Ƒ -- LATIN CAPITAL LETTER F HOOK
							case -107: return byte_199_182;	// ƕ -> Ƕ -- LATIN SMALL LETTER H V
							case -103: return byte_198_152;	// ƙ -> Ƙ -- LATIN CAPITAL LETTER K HOOK
							case -102: return byte_200_189;	// ƚ -> Ƚ -- LATIN SMALL LETTER BARRED L
							case -98: return byte_200_160;	// ƞ -> Ƞ -- LATIN SMALL LETTER N WITH LONG RIGHT LEG
							case -95: return byte_198_160;	// ơ -> Ơ -- LATIN CAPITAL LETTER O HORN
							case -93: return byte_198_162;	// ƣ -> Ƣ -- LATIN CAPITAL LETTER O I
							case -91: return byte_198_164;	// ƥ -> Ƥ -- LATIN CAPITAL LETTER P HOOK
							case -88: return byte_198_167;	// ƨ -> Ƨ -- LATIN CAPITAL LETTER TONE TWO
							case -83: return byte_198_172;	// ƭ -> Ƭ -- LATIN CAPITAL LETTER T HOOK
							case -80: return byte_198_175;	// ư -> Ư -- LATIN CAPITAL LETTER U HORN
							case -76: return byte_198_179;	// ƴ -> Ƴ -- LATIN CAPITAL LETTER Y HOOK
							case -74: return byte_198_181;	// ƶ -> Ƶ -- LATIN CAPITAL LETTER Z BAR
							case -71: return byte_198_184;	// ƹ -> Ƹ -- LATIN CAPITAL LETTER REVERSED YOGH
							case -67: return byte_198_188;	// ƽ -> Ƽ -- LATIN CAPITAL LETTER TONE FIVE
							case -65: return byte_199_183;	// ƿ -> Ƿ -- LATIN LETTER WYNN
						}
						break;
					case -57:
						switch (b2) {
							case -122: return byte_199_132;	// ǆ -> Ǆ -- LATIN CAPITAL LETTER D Z HACEK
							case -119: return byte_199_135;	// ǉ -> Ǉ -- LATIN CAPITAL LETTER L J
							case -116: return byte_199_138;	// ǌ -> Ǌ -- LATIN CAPITAL LETTER N J
							case -114: return byte_199_141;	// ǎ -> Ǎ -- LATIN CAPITAL LETTER A HACEK
							case -112: return byte_199_143;	// ǐ -> Ǐ -- LATIN CAPITAL LETTER I HACEK
							case -110: return byte_199_145;	// ǒ -> Ǒ -- LATIN CAPITAL LETTER O HACEK
							case -108: return byte_199_147;	// ǔ -> Ǔ -- LATIN CAPITAL LETTER U HACEK
							case -106: return byte_199_149;	// ǖ -> Ǖ -- LATIN CAPITAL LETTER U DIAERESIS MACRON
							case -104: return byte_199_151;	// ǘ -> Ǘ -- LATIN CAPITAL LETTER U DIAERESIS ACUTE
							case -102: return byte_199_153;	// ǚ -> Ǚ -- LATIN CAPITAL LETTER U DIAERESIS HACEK
							case -100: return byte_199_155;	// ǜ -> Ǜ -- LATIN CAPITAL LETTER U DIAERESIS GRAVE
							case -99: return byte_198_142;	// ǝ -> Ǝ -- LATIN CAPITAL LETTER TURNED E
							case -97: return byte_199_158;	// ǟ -> Ǟ -- LATIN CAPITAL LETTER A DIAERESIS MACRON
							case -95: return byte_199_160;	// ǡ -> Ǡ -- LATIN CAPITAL LETTER A DOT MACRON
							case -93: return byte_199_162;	// ǣ -> Ǣ -- LATIN CAPITAL LETTER A E MACRON
							case -91: return byte_199_164;	// ǥ -> Ǥ -- LATIN CAPITAL LETTER G BAR
							case -89: return byte_199_166;	// ǧ -> Ǧ -- LATIN CAPITAL LETTER G HACEK
							case -87: return byte_199_168;	// ǩ -> Ǩ -- LATIN CAPITAL LETTER K HACEK
							case -85: return byte_199_170;	// ǫ -> Ǫ -- LATIN CAPITAL LETTER O OGONEK
							case -83: return byte_199_172;	// ǭ -> Ǭ -- LATIN CAPITAL LETTER O OGONEK MACRON
							case -81: return byte_199_174;	// ǯ -> Ǯ -- LATIN CAPITAL LETTER YOGH HACEK
							case -77: return byte_199_177;	// ǳ -> Ǳ -- LATIN CAPITAL LETTER DZ
							case -75: return byte_199_180;	// ǵ -> Ǵ -- LATIN CAPITAL LETTER G WITH ACUTE
							case -71: return byte_199_184;	// ǹ -> Ǹ -- LATIN CAPITAL LETTER N WITH GRAVE
							case -69: return byte_199_186;	// ǻ -> Ǻ -- LATIN CAPITAL LETTER A WITH RING ABOVE AND ACUTE
							case -67: return byte_199_188;	// ǽ -> Ǽ -- LATIN CAPITAL LETTER AE WITH ACUTE
							case -65: return byte_199_190;	// ǿ -> Ǿ -- LATIN CAPITAL LETTER O WITH STROKE AND ACUTE
						}
						break;
					case -56:
						switch (b2) {
							case -127: return byte_200_128;	// ȁ -> Ȁ -- LATIN CAPITAL LETTER A WITH DOUBLE GRAVE
							case -125: return byte_200_130;	// ȃ -> Ȃ -- LATIN CAPITAL LETTER A WITH INVERTED BREVE
							case -123: return byte_200_132;	// ȅ -> Ȅ -- LATIN CAPITAL LETTER E WITH DOUBLE GRAVE
							case -121: return byte_200_134;	// ȇ -> Ȇ -- LATIN CAPITAL LETTER E WITH INVERTED BREVE
							case -119: return byte_200_136;	// ȉ -> Ȉ -- LATIN CAPITAL LETTER I WITH DOUBLE GRAVE
							case -117: return byte_200_138;	// ȋ -> Ȋ -- LATIN CAPITAL LETTER I WITH INVERTED BREVE
							case -115: return byte_200_140;	// ȍ -> Ȍ -- LATIN CAPITAL LETTER O WITH DOUBLE GRAVE
							case -113: return byte_200_142;	// ȏ -> Ȏ -- LATIN CAPITAL LETTER O WITH INVERTED BREVE
							case -111: return byte_200_144;	// ȑ -> Ȑ -- LATIN CAPITAL LETTER R WITH DOUBLE GRAVE
							case -109: return byte_200_146;	// ȓ -> Ȓ -- LATIN CAPITAL LETTER R WITH INVERTED BREVE
							case -107: return byte_200_148;	// ȕ -> Ȕ -- LATIN CAPITAL LETTER U WITH DOUBLE GRAVE
							case -105: return byte_200_150;	// ȗ -> Ȗ -- LATIN CAPITAL LETTER U WITH INVERTED BREVE
							case -103: return byte_200_152;	// ș -> Ș -- LATIN CAPITAL LETTER S WITH COMMA BELOW
							case -101: return byte_200_154;	// ț -> Ț -- LATIN CAPITAL LETTER T WITH COMMA BELOW
							case -99: return byte_200_156;	// ȝ -> Ȝ -- LATIN CAPITAL LETTER YOGH
							case -97: return byte_200_158;	// ȟ -> Ȟ -- LATIN CAPITAL LETTER H WITH CARON
							case -93: return byte_200_162;	// ȣ -> Ȣ -- LATIN CAPITAL LETTER OU
							case -91: return byte_200_164;	// ȥ -> Ȥ -- LATIN CAPITAL LETTER Z WITH HOOK
							case -89: return byte_200_166;	// ȧ -> Ȧ -- LATIN CAPITAL LETTER A WITH DOT ABOVE
							case -87: return byte_200_168;	// ȩ -> Ȩ -- LATIN CAPITAL LETTER E WITH CEDILLA
							case -85: return byte_200_170;	// ȫ -> Ȫ -- LATIN CAPITAL LETTER O WITH DIAERESIS AND MACRON
							case -83: return byte_200_172;	// ȭ -> Ȭ -- LATIN CAPITAL LETTER O WITH TILDE AND MACRON
							case -81: return byte_200_174;	// ȯ -> Ȯ -- LATIN CAPITAL LETTER O WITH DOT ABOVE
							case -79: return byte_200_176;	// ȱ -> Ȱ -- LATIN CAPITAL LETTER O WITH DOT ABOVE AND MACRON
							case -77: return byte_200_178;	// ȳ -> Ȳ -- LATIN CAPITAL LETTER Y WITH MACRON
							case -68: return byte_200_187;	// ȼ -> Ȼ -- LATIN CAPITAL LETTER C WITH STROKE
							case -65: return byte_226_177_190;	// ȿ -> Ȿ -- LATIN SMALL LETTER S WITH SWASH TAIL
						}
						break;
					case -55:
						switch (b2) {
							case -128: return byte_226_177_191;	// ɀ -> Ɀ -- LATIN SMALL LETTER Z WITH SWASH TAIL
							case -126: return byte_201_129;	// ɂ -> Ɂ -- LATIN CAPITAL LETTER GLOTTAL STOP
							case -121: return byte_201_134;	// ɇ -> Ɇ -- LATIN CAPITAL LETTER E WITH STROKE
							case -119: return byte_201_136;	// ɉ -> Ɉ -- LATIN CAPITAL LETTER J WITH STROKE
							case -117: return byte_201_138;	// ɋ -> Ɋ -- LATIN CAPITAL LETTER SMALL Q WITH HOOK TAIL
							case -115: return byte_201_140;	// ɍ -> Ɍ -- LATIN CAPITAL LETTER R WITH STROKE
							case -113: return byte_201_142;	// ɏ -> Ɏ -- LATIN CAPITAL LETTER Y WITH STROKE
							case -112: return byte_226_177_175;	// ɐ -> Ɐ -- LATIN SMALL LETTER TURNED A
							case -111: return byte_226_177_173;	// ɑ -> Ɑ -- LATIN SMALL LETTER SCRIPT A
							case -110: return byte_226_177_176;	// ɒ -> Ɒ -- LATIN SMALL LETTER TURNED SCRIPT A
							case -109: return byte_198_129;	// ɓ -> Ɓ -- LATIN CAPITAL LETTER B HOOK
							case -108: return byte_198_134;	// ɔ -> Ɔ -- LATIN CAPITAL LETTER OPEN O
							case -106: return byte_198_137;	// ɖ -> Ɖ -- LATIN CAPITAL LETTER AFRICAN D
							case -105: return byte_198_138;	// ɗ -> Ɗ -- LATIN CAPITAL LETTER D HOOK
							case -103: return byte_198_143;	// ə -> Ə -- LATIN CAPITAL LETTER SCHWA
							case -101: return byte_198_144;	// ɛ -> Ɛ -- LATIN CAPITAL LETTER EPSILON
							case -96: return byte_198_147;	// ɠ -> Ɠ -- LATIN CAPITAL LETTER G HOOK
							case -93: return byte_198_148;	// ɣ -> Ɣ -- LATIN CAPITAL LETTER GAMMA
							case -91: return byte_234_158_141;	// ɥ -> Ɥ -- LATIN SMALL LETTER TURNED H
							case -90: return byte_234_158_170;	// ɦ -> Ɦ -- LATIN SMALL LETTER H HOOK
							case -88: return byte_198_151;	// ɨ -> Ɨ -- LATIN CAPITAL LETTER BARRED I
							case -87: return byte_198_150;	// ɩ -> Ɩ -- LATIN CAPITAL LETTER IOTA
							case -85: return byte_226_177_162;	// ɫ -> Ɫ -- LATIN SMALL LETTER L WITH MIDDLE TILDE
							case -81: return byte_198_156;	// ɯ -> Ɯ -- LATIN CAPITAL LETTER TURNED M
							case -79: return byte_226_177_174;	// ɱ -> Ɱ -- LATIN SMALL LETTER M HOOK
							case -78: return byte_198_157;	// ɲ -> Ɲ -- LATIN CAPITAL LETTER N HOOK
							case -75: return byte_198_159;	// ɵ -> Ɵ -- LATIN CAPITAL LETTER BARRED O
							case -67: return byte_226_177_164;	// ɽ -> Ɽ -- LATIN SMALL LETTER R HOOK
						}
						break;
					case -54:
						switch (b2) {
							case -128: return byte_198_166;	// ʀ -> Ʀ -- LATIN LETTER Y R
							case -125: return byte_198_169;	// ʃ -> Ʃ -- LATIN CAPITAL LETTER ESH
							case -120: return byte_198_174;	// ʈ -> Ʈ -- LATIN CAPITAL LETTER T RETROFLEX HOOK
							case -119: return byte_201_132;	// ʉ -> Ʉ -- LATIN CAPITAL LETTER U BAR
							case -118: return byte_198_177;	// ʊ -> Ʊ -- LATIN CAPITAL LETTER UPSILON
							case -117: return byte_198_178;	// ʋ -> Ʋ -- LATIN CAPITAL LETTER SCRIPT V
							case -116: return byte_201_133;	// ʌ -> Ʌ -- LATIN CAPITAL LETTER TURNED V
							case -110: return byte_198_183;	// ʒ -> Ʒ -- LATIN CAPITAL LETTER YOGH
						}
						break;
					case -51:
						switch (b2) {
							case -123: return byte_206_153;	//  ͅ  -> Ι -- GREEK NON-SPACING IOTA BELOW
							case -79: return byte_205_176;	// ͱ -> Ͱ -- GREEK CAPITAL LETTER HETA
							case -77: return byte_205_178;	// ͳ -> Ͳ -- GREEK CAPITAL LETTER ARCHAIC SAMPI
							case -73: return byte_205_182;	// ͷ -> Ͷ -- GREEK CAPITAL LETTER PAMPHYLIAN DIGAMMA
							case -69: return byte_207_189;	// ͻ -> Ͻ -- GREEK SMALL REVERSED LUNATE SIGMA SYMBOL
							case -68: return byte_207_190;	// ͼ -> Ͼ -- GREEK SMALL DOTTED LUNATE SIGMA SYMBOL
							case -67: return byte_207_191;	// ͽ -> Ͽ -- GREEK SMALL REVERSED DOTTED LUNATE SIGMA SYMBOL
						}
						break;
					case -50:
						switch (b2) {
							case -84: return byte_206_134;	// ά -> Ά -- GREEK CAPITAL LETTER ALPHA TONOS
							case -83: return byte_206_136;	// έ -> Έ -- GREEK CAPITAL LETTER EPSILON TONOS
							case -82: return byte_206_137;	// ή -> Ή -- GREEK CAPITAL LETTER ETA TONOS
							case -81: return byte_206_138;	// ί -> Ί -- GREEK CAPITAL LETTER IOTA TONOS
							case -79: return byte_206_145;	// α -> Α -- GREEK CAPITAL LETTER ALPHA
							case -78: return byte_206_146;	// β -> Β -- GREEK CAPITAL LETTER BETA
							case -77: return byte_206_147;	// γ -> Γ -- GREEK CAPITAL LETTER GAMMA
							case -76: return byte_206_148;	// δ -> Δ -- GREEK CAPITAL LETTER DELTA
							case -75: return byte_206_149;	// ε -> Ε -- GREEK CAPITAL LETTER EPSILON
							case -74: return byte_206_150;	// ζ -> Ζ -- GREEK CAPITAL LETTER ZETA
							case -73: return byte_206_151;	// η -> Η -- GREEK CAPITAL LETTER ETA
							case -72: return byte_206_152;	// θ -> Θ -- GREEK CAPITAL LETTER THETA
							case -71: return byte_206_153;	// ι -> Ι -- GREEK SMALL LETTER IOTA
							case -70: return byte_206_154;	// κ -> Κ -- GREEK CAPITAL LETTER KAPPA
							case -69: return byte_206_155;	// λ -> Λ -- GREEK CAPITAL LETTER LAMBDA
							case -68: return byte_206_156;	// μ -> Μ -- GREEK CAPITAL LETTER MU; CONSOLIDATED
							case -67: return byte_206_157;	// ν -> Ν -- GREEK CAPITAL LETTER NU
							case -66: return byte_206_158;	// ξ -> Ξ -- GREEK CAPITAL LETTER XI
							case -65: return byte_206_159;	// ο -> Ο -- GREEK CAPITAL LETTER OMICRON
						}
						break;
					case -49:
						switch (b2) {
							case -128: return byte_206_160;	// π -> Π -- GREEK CAPITAL LETTER PI
							case -127: return byte_206_161;	// ρ -> Ρ -- GREEK CAPITAL LETTER RHO
							case -126: return byte_206_163;	// ς -> Σ -- GREEK SMALL LETTER FINAL SIGMA
							case -125: return byte_206_163;	// σ -> Σ -- GREEK CAPITAL LETTER SIGMA
							case -124: return byte_206_164;	// τ -> Τ -- GREEK CAPITAL LETTER TAU
							case -123: return byte_206_165;	// υ -> Υ -- GREEK CAPITAL LETTER UPSILON
							case -122: return byte_206_166;	// φ -> Φ -- GREEK CAPITAL LETTER PHI
							case -121: return byte_206_167;	// χ -> Χ -- GREEK CAPITAL LETTER CHI
							case -120: return byte_206_168;	// ψ -> Ψ -- GREEK CAPITAL LETTER PSI
							case -119: return byte_206_169;	// ω -> Ω -- GREEK CAPITAL LETTER OMEGA
							case -118: return byte_206_170;	// ϊ -> Ϊ -- GREEK CAPITAL LETTER IOTA DIAERESIS
							case -117: return byte_206_171;	// ϋ -> Ϋ -- GREEK CAPITAL LETTER UPSILON DIAERESIS
							case -116: return byte_206_140;	// ό -> Ό -- GREEK CAPITAL LETTER OMICRON TONOS
							case -115: return byte_206_142;	// ύ -> Ύ -- GREEK CAPITAL LETTER UPSILON TONOS
							case -114: return byte_206_143;	// ώ -> Ώ -- GREEK CAPITAL LETTER OMEGA TONOS
							case -112: return byte_206_146;	// ϐ -> Β -- GREEK SMALL LETTER CURLED BETA
							case -111: return byte_206_152;	// ϑ -> Θ -- GREEK SMALL LETTER SCRIPT THETA
							case -107: return byte_206_166;	// ϕ -> Φ -- GREEK SMALL LETTER SCRIPT PHI
							case -106: return byte_206_160;	// ϖ -> Π -- GREEK SMALL LETTER OMEGA PI
							case -105: return byte_207_143;	// ϗ -> Ϗ -- GREEK CAPITAL KAI SYMBOL
							case -103: return byte_207_152;	// ϙ -> Ϙ -- GREEK LETTER ARCHAIC KOPPA
							case -101: return byte_207_154;	// ϛ -> Ϛ -- GREEK CAPITAL LETTER STIGMA
							case -99: return byte_207_156;	// ϝ -> Ϝ -- GREEK CAPITAL LETTER DIGAMMA
							case -97: return byte_207_158;	// ϟ -> Ϟ -- GREEK CAPITAL LETTER KOPPA
							case -95: return byte_207_160;	// ϡ -> Ϡ -- GREEK CAPITAL LETTER SAMPI
							case -93: return byte_207_162;	// ϣ -> Ϣ -- GREEK CAPITAL LETTER SHEI
							case -91: return byte_207_164;	// ϥ -> Ϥ -- GREEK CAPITAL LETTER FEI
							case -89: return byte_207_166;	// ϧ -> Ϧ -- GREEK CAPITAL LETTER KHEI
							case -87: return byte_207_168;	// ϩ -> Ϩ -- GREEK CAPITAL LETTER HORI
							case -85: return byte_207_170;	// ϫ -> Ϫ -- GREEK CAPITAL LETTER GANGIA
							case -83: return byte_207_172;	// ϭ -> Ϭ -- GREEK CAPITAL LETTER SHIMA
							case -81: return byte_207_174;	// ϯ -> Ϯ -- GREEK CAPITAL LETTER DEI
							case -80: return byte_206_154;	// ϰ -> Κ -- GREEK SMALL LETTER SCRIPT KAPPA
							case -79: return byte_206_161;	// ϱ -> Ρ -- GREEK SMALL LETTER TAILED RHO
							case -78: return byte_207_185;	// ϲ -> Ϲ -- GREEK SMALL LETTER LUNATE SIGMA
							case -75: return byte_206_149;	// ϵ -> Ε -- GREEK LUNATE EPSILON SYMBOL
							case -72: return byte_207_183;	// ϸ -> Ϸ -- GREEK CAPITAL LETTER SHO
							case -69: return byte_207_186;	// ϻ -> Ϻ -- GREEK CAPITAL LETTER SAN
						}
						break;
					case -48:
						switch (b2) {
							case -80: return byte_208_144;	// а -> А -- CYRILLIC CAPITAL LETTER A
							case -79: return byte_208_145;	// б -> Б -- CYRILLIC CAPITAL LETTER BE
							case -78: return byte_208_146;	// в -> В -- CYRILLIC CAPITAL LETTER VE
							case -77: return byte_208_147;	// г -> Г -- CYRILLIC CAPITAL LETTER GE
							case -76: return byte_208_148;	// д -> Д -- CYRILLIC CAPITAL LETTER DE
							case -75: return byte_208_149;	// е -> Е -- CYRILLIC CAPITAL LETTER IE
							case -74: return byte_208_150;	// ж -> Ж -- CYRILLIC CAPITAL LETTER ZHE
							case -73: return byte_208_151;	// з -> З -- CYRILLIC CAPITAL LETTER ZE
							case -72: return byte_208_152;	// и -> И -- CYRILLIC CAPITAL LETTER II
							case -71: return byte_208_153;	// й -> Й -- CYRILLIC CAPITAL LETTER SHORT II
							case -70: return byte_208_154;	// к -> К -- CYRILLIC CAPITAL LETTER KA
							case -69: return byte_208_155;	// л -> Л -- CYRILLIC CAPITAL LETTER EL
							case -68: return byte_208_156;	// м -> М -- CYRILLIC CAPITAL LETTER EM
							case -67: return byte_208_157;	// н -> Н -- CYRILLIC CAPITAL LETTER EN
							case -66: return byte_208_158;	// о -> О -- CYRILLIC CAPITAL LETTER O
							case -65: return byte_208_159;	// п -> П -- CYRILLIC CAPITAL LETTER PE
						}
						break;
					case -47:
						switch (b2) {
							case -128: return byte_208_160;	// р -> Р -- CYRILLIC CAPITAL LETTER ER
							case -127: return byte_208_161;	// с -> С -- CYRILLIC CAPITAL LETTER ES
							case -126: return byte_208_162;	// т -> Т -- CYRILLIC CAPITAL LETTER TE
							case -125: return byte_208_163;	// у -> У -- CYRILLIC CAPITAL LETTER U
							case -124: return byte_208_164;	// ф -> Ф -- CYRILLIC CAPITAL LETTER EF
							case -123: return byte_208_165;	// х -> Х -- CYRILLIC CAPITAL LETTER KHA
							case -122: return byte_208_166;	// ц -> Ц -- CYRILLIC CAPITAL LETTER TSE
							case -121: return byte_208_167;	// ч -> Ч -- CYRILLIC CAPITAL LETTER CHE
							case -120: return byte_208_168;	// ш -> Ш -- CYRILLIC CAPITAL LETTER SHA
							case -119: return byte_208_169;	// щ -> Щ -- CYRILLIC CAPITAL LETTER SHCHA
							case -118: return byte_208_170;	// ъ -> Ъ -- CYRILLIC CAPITAL LETTER HARD SIGN
							case -117: return byte_208_171;	// ы -> Ы -- CYRILLIC CAPITAL LETTER YERI
							case -116: return byte_208_172;	// ь -> Ь -- CYRILLIC CAPITAL LETTER SOFT SIGN
							case -115: return byte_208_173;	// э -> Э -- CYRILLIC CAPITAL LETTER REVERSED E
							case -114: return byte_208_174;	// ю -> Ю -- CYRILLIC CAPITAL LETTER IU
							case -113: return byte_208_175;	// я -> Я -- CYRILLIC CAPITAL LETTER IA
							case -112: return byte_208_128;	// ѐ -> Ѐ -- CYRILLIC CAPITAL LETTER IE WITH GRAVE
							case -111: return byte_208_129;	// ё -> Ё -- CYRILLIC CAPITAL LETTER IO
							case -110: return byte_208_130;	// ђ -> Ђ -- CYRILLIC CAPITAL LETTER DJE
							case -109: return byte_208_131;	// ѓ -> Ѓ -- CYRILLIC CAPITAL LETTER GJE
							case -108: return byte_208_132;	// є -> Є -- CYRILLIC CAPITAL LETTER E
							case -107: return byte_208_133;	// ѕ -> Ѕ -- CYRILLIC CAPITAL LETTER DZE
							case -106: return byte_208_134;	// і -> І -- CYRILLIC CAPITAL LETTER I
							case -105: return byte_208_135;	// ї -> Ї -- CYRILLIC CAPITAL LETTER YI
							case -104: return byte_208_136;	// ј -> Ј -- CYRILLIC CAPITAL LETTER JE
							case -103: return byte_208_137;	// љ -> Љ -- CYRILLIC CAPITAL LETTER LJE
							case -102: return byte_208_138;	// њ -> Њ -- CYRILLIC CAPITAL LETTER NJE
							case -101: return byte_208_139;	// ћ -> Ћ -- CYRILLIC CAPITAL LETTER TSHE
							case -100: return byte_208_140;	// ќ -> Ќ -- CYRILLIC CAPITAL LETTER KJE
							case -99: return byte_208_141;	// ѝ -> Ѝ -- CYRILLIC CAPITAL LETTER I WITH GRAVE
							case -98: return byte_208_142;	// ў -> Ў -- CYRILLIC CAPITAL LETTER SHORT U
							case -97: return byte_208_143;	// џ -> Џ -- CYRILLIC CAPITAL LETTER DZHE
							case -95: return byte_209_160;	// ѡ -> Ѡ -- CYRILLIC CAPITAL LETTER OMEGA
							case -93: return byte_209_162;	// ѣ -> Ѣ -- CYRILLIC CAPITAL LETTER YAT
							case -91: return byte_209_164;	// ѥ -> Ѥ -- CYRILLIC CAPITAL LETTER IOTIFIED E
							case -89: return byte_209_166;	// ѧ -> Ѧ -- CYRILLIC CAPITAL LETTER LITTLE YUS
							case -87: return byte_209_168;	// ѩ -> Ѩ -- CYRILLIC CAPITAL LETTER IOTIFIED LITTLE YUS
							case -85: return byte_209_170;	// ѫ -> Ѫ -- CYRILLIC CAPITAL LETTER BIG YUS
							case -83: return byte_209_172;	// ѭ -> Ѭ -- CYRILLIC CAPITAL LETTER IOTIFIED BIG YUS
							case -81: return byte_209_174;	// ѯ -> Ѯ -- CYRILLIC CAPITAL LETTER KSI
							case -79: return byte_209_176;	// ѱ -> Ѱ -- CYRILLIC CAPITAL LETTER PSI
							case -77: return byte_209_178;	// ѳ -> Ѳ -- CYRILLIC CAPITAL LETTER FITA
							case -75: return byte_209_180;	// ѵ -> Ѵ -- CYRILLIC CAPITAL LETTER IZHITSA
							case -73: return byte_209_182;	// ѷ -> Ѷ -- CYRILLIC CAPITAL LETTER IZHITSA DOUBLE GRAVE
							case -71: return byte_209_184;	// ѹ -> Ѹ -- CYRILLIC CAPITAL LETTER UK DIGRAPH
							case -69: return byte_209_186;	// ѻ -> Ѻ -- CYRILLIC CAPITAL LETTER ROUND OMEGA
							case -67: return byte_209_188;	// ѽ -> Ѽ -- CYRILLIC CAPITAL LETTER OMEGA TITLO
							case -65: return byte_209_190;	// ѿ -> Ѿ -- CYRILLIC CAPITAL LETTER OT
						}
						break;
					case -46:
						switch (b2) {
							case -127: return byte_210_128;	// ҁ -> Ҁ -- CYRILLIC CAPITAL LETTER KOPPA
							case -117: return byte_210_138;	// ҋ -> Ҋ -- CYRILLIC CAPITAL LETTER SHORT I WITH TAIL
							case -115: return byte_210_140;	// ҍ -> Ҍ -- CYRILLIC CAPITAL LETTER SEMISOFT SIGN
							case -113: return byte_210_142;	// ҏ -> Ҏ -- CYRILLIC CAPITAL LETTER ER WITH TICK
							case -111: return byte_210_144;	// ґ -> Ґ -- CYRILLIC CAPITAL LETTER GE WITH UPTURN
							case -109: return byte_210_146;	// ғ -> Ғ -- CYRILLIC CAPITAL LETTER GE BAR
							case -107: return byte_210_148;	// ҕ -> Ҕ -- CYRILLIC CAPITAL LETTER GE HOOK
							case -105: return byte_210_150;	// җ -> Җ -- CYRILLIC CAPITAL LETTER ZHE WITH RIGHT DESCENDER
							case -103: return byte_210_152;	// ҙ -> Ҙ -- CYRILLIC CAPITAL LETTER ZE CEDILLA
							case -101: return byte_210_154;	// қ -> Қ -- CYRILLIC CAPITAL LETTER KA WITH RIGHT DESCENDER
							case -99: return byte_210_156;	// ҝ -> Ҝ -- CYRILLIC CAPITAL LETTER KA VERTICAL BAR
							case -97: return byte_210_158;	// ҟ -> Ҟ -- CYRILLIC CAPITAL LETTER KA BAR
							case -95: return byte_210_160;	// ҡ -> Ҡ -- CYRILLIC CAPITAL LETTER REVERSED GE KA
							case -93: return byte_210_162;	// ң -> Ң -- CYRILLIC CAPITAL LETTER EN WITH RIGHT DESCENDER
							case -91: return byte_210_164;	// ҥ -> Ҥ -- CYRILLIC CAPITAL LETTER EN GE
							case -89: return byte_210_166;	// ҧ -> Ҧ -- CYRILLIC CAPITAL LETTER PE HOOK
							case -87: return byte_210_168;	// ҩ -> Ҩ -- CYRILLIC CAPITAL LETTER O HOOK
							case -85: return byte_210_170;	// ҫ -> Ҫ -- CYRILLIC CAPITAL LETTER ES CEDILLA
							case -83: return byte_210_172;	// ҭ -> Ҭ -- CYRILLIC CAPITAL LETTER TE WITH RIGHT DESCENDER
							case -81: return byte_210_174;	// ү -> Ү -- CYRILLIC CAPITAL LETTER STRAIGHT U
							case -79: return byte_210_176;	// ұ -> Ұ -- CYRILLIC CAPITAL LETTER STRAIGHT U BAR
							case -77: return byte_210_178;	// ҳ -> Ҳ -- CYRILLIC CAPITAL LETTER KHA WITH RIGHT DESCENDER
							case -75: return byte_210_180;	// ҵ -> Ҵ -- CYRILLIC CAPITAL LETTER TE TSE
							case -73: return byte_210_182;	// ҷ -> Ҷ -- CYRILLIC CAPITAL LETTER CHE WITH RIGHT DESCENDER
							case -71: return byte_210_184;	// ҹ -> Ҹ -- CYRILLIC CAPITAL LETTER CHE VERTICAL BAR
							case -69: return byte_210_186;	// һ -> Һ -- CYRILLIC CAPITAL LETTER H
							case -67: return byte_210_188;	// ҽ -> Ҽ -- CYRILLIC CAPITAL LETTER IE HOOK
							case -65: return byte_210_190;	// ҿ -> Ҿ -- CYRILLIC CAPITAL LETTER IE HOOK OGONEK
						}
						break;
					case -45:
						switch (b2) {
							case -126: return byte_211_129;	// ӂ -> Ӂ -- CYRILLIC CAPITAL LETTER SHORT ZHE
							case -124: return byte_211_131;	// ӄ -> Ӄ -- CYRILLIC CAPITAL LETTER KA HOOK
							case -122: return byte_211_133;	// ӆ -> Ӆ -- CYRILLIC CAPITAL LETTER EL WITH TAIL
							case -120: return byte_211_135;	// ӈ -> Ӈ -- CYRILLIC CAPITAL LETTER EN HOOK
							case -118: return byte_211_137;	// ӊ -> Ӊ -- CYRILLIC CAPITAL LETTER EN WITH TAIL
							case -116: return byte_211_139;	// ӌ -> Ӌ -- CYRILLIC CAPITAL LETTER CHE WITH LEFT DESCENDER
							case -114: return byte_211_141;	// ӎ -> Ӎ -- CYRILLIC CAPITAL LETTER EM WITH TAIL
							case -113: return byte_211_128;	// ӏ -> Ӏ -- CYRILLIC LETTER I
							case -111: return byte_211_144;	// ӑ -> Ӑ -- CYRILLIC CAPITAL LETTER A WITH BREVE
							case -109: return byte_211_146;	// ӓ -> Ӓ -- CYRILLIC CAPITAL LETTER A WITH DIAERESIS
							case -107: return byte_211_148;	// ӕ -> Ӕ -- CYRILLIC CAPITAL LIGATURE A IE
							case -105: return byte_211_150;	// ӗ -> Ӗ -- CYRILLIC CAPITAL LETTER IE WITH BREVE
							case -103: return byte_211_152;	// ә -> Ә -- CYRILLIC CAPITAL LETTER SCHWA
							case -101: return byte_211_154;	// ӛ -> Ӛ -- CYRILLIC CAPITAL LETTER SCHWA WITH DIAERESIS
							case -99: return byte_211_156;	// ӝ -> Ӝ -- CYRILLIC CAPITAL LETTER ZHE WITH DIAERESIS
							case -97: return byte_211_158;	// ӟ -> Ӟ -- CYRILLIC CAPITAL LETTER ZE WITH DIAERESIS
							case -95: return byte_211_160;	// ӡ -> Ӡ -- CYRILLIC CAPITAL LETTER ABKHASIAN DZE
							case -93: return byte_211_162;	// ӣ -> Ӣ -- CYRILLIC CAPITAL LETTER I WITH MACRON
							case -91: return byte_211_164;	// ӥ -> Ӥ -- CYRILLIC CAPITAL LETTER I WITH DIAERESIS
							case -89: return byte_211_166;	// ӧ -> Ӧ -- CYRILLIC CAPITAL LETTER O WITH DIAERESIS
							case -87: return byte_211_168;	// ө -> Ө -- CYRILLIC CAPITAL LETTER BARRED O
							case -85: return byte_211_170;	// ӫ -> Ӫ -- CYRILLIC CAPITAL LETTER BARRED O WITH DIAERESIS
							case -83: return byte_211_172;	// ӭ -> Ӭ -- CYRILLIC CAPITAL LETTER E WITH DIAERESIS
							case -81: return byte_211_174;	// ӯ -> Ӯ -- CYRILLIC CAPITAL LETTER U WITH MACRON
							case -79: return byte_211_176;	// ӱ -> Ӱ -- CYRILLIC CAPITAL LETTER U WITH DIAERESIS
							case -77: return byte_211_178;	// ӳ -> Ӳ -- CYRILLIC CAPITAL LETTER U WITH DOUBLE ACUTE
							case -75: return byte_211_180;	// ӵ -> Ӵ -- CYRILLIC CAPITAL LETTER CHE WITH DIAERESIS
							case -73: return byte_211_182;	// ӷ -> Ӷ -- CYRILLIC CAPITAL LETTER GHE WITH DESCENDER
							case -71: return byte_211_184;	// ӹ -> Ӹ -- CYRILLIC CAPITAL LETTER YERU WITH DIAERESIS
							case -69: return byte_211_186;	// ӻ -> Ӻ -- CYRILLIC CAPITAL LETTER GHE WITH STROKE AND HOOK
							case -67: return byte_211_188;	// ӽ -> Ӽ -- CYRILLIC CAPITAL LETTER HA WITH HOOK
							case -65: return byte_211_190;	// ӿ -> Ӿ -- CYRILLIC CAPITAL LETTER HA WITH STROKE
						}
						break;
					case -44:
						switch (b2) {
							case -127: return byte_212_128;	// ԁ -> Ԁ -- CYRILLIC CAPITAL LETTER KOMI DE
							case -125: return byte_212_130;	// ԃ -> Ԃ -- CYRILLIC CAPITAL LETTER KOMI DJE
							case -123: return byte_212_132;	// ԅ -> Ԅ -- CYRILLIC CAPITAL LETTER KOMI ZJE
							case -121: return byte_212_134;	// ԇ -> Ԇ -- CYRILLIC CAPITAL LETTER KOMI DZJE
							case -119: return byte_212_136;	// ԉ -> Ԉ -- CYRILLIC CAPITAL LETTER KOMI LJE
							case -117: return byte_212_138;	// ԋ -> Ԋ -- CYRILLIC CAPITAL LETTER KOMI NJE
							case -115: return byte_212_140;	// ԍ -> Ԍ -- CYRILLIC CAPITAL LETTER KOMI SJE
							case -113: return byte_212_142;	// ԏ -> Ԏ -- CYRILLIC CAPITAL LETTER KOMI TJE
							case -111: return byte_212_144;	// ԑ -> Ԑ -- CYRILLIC CAPITAL LETTER REVERSED ZE
							case -109: return byte_212_146;	// ԓ -> Ԓ -- CYRILLIC CAPITAL LETTER EL WITH HOOK
							case -107: return byte_212_148;	// ԕ -> Ԕ -- CYRILLIC CAPITAL LETTER LHA
							case -105: return byte_212_150;	// ԗ -> Ԗ -- CYRILLIC CAPITAL LETTER RHA
							case -103: return byte_212_152;	// ԙ -> Ԙ -- CYRILLIC CAPITAL LETTER YAE
							case -101: return byte_212_154;	// ԛ -> Ԛ -- CYRILLIC CAPITAL LETTER QA
							case -99: return byte_212_156;	// ԝ -> Ԝ -- CYRILLIC CAPITAL LETTER WE
							case -97: return byte_212_158;	// ԟ -> Ԟ -- CYRILLIC CAPITAL LETTER ALEUT KA
							case -95: return byte_212_160;	// ԡ -> Ԡ -- CYRILLIC CAPITAL LETTER EL WITH MIDDLE HOOK
							case -93: return byte_212_162;	// ԣ -> Ԣ -- CYRILLIC CAPITAL LETTER EN WITH MIDDLE HOOK
							case -91: return byte_212_164;	// ԥ -> Ԥ -- CYRILLIC CAPITAL LETTER PE WITH DESCENDER
							case -89: return byte_212_166;	// ԧ -> Ԧ -- CYRILLIC CAPITAL LETTER SHHA WITH DESCENDER
						}
						break;
					case -43:
						switch (b2) {
							case -95: return byte_212_177;	// ա -> Ա -- ARMENIAN CAPITAL LETTER AYB
							case -94: return byte_212_178;	// բ -> Բ -- ARMENIAN CAPITAL LETTER BEN
							case -93: return byte_212_179;	// գ -> Գ -- ARMENIAN CAPITAL LETTER GIM
							case -92: return byte_212_180;	// դ -> Դ -- ARMENIAN CAPITAL LETTER DA
							case -91: return byte_212_181;	// ե -> Ե -- ARMENIAN CAPITAL LETTER ECH
							case -90: return byte_212_182;	// զ -> Զ -- ARMENIAN CAPITAL LETTER ZA
							case -89: return byte_212_183;	// է -> Է -- ARMENIAN CAPITAL LETTER EH
							case -88: return byte_212_184;	// ը -> Ը -- ARMENIAN CAPITAL LETTER ET
							case -87: return byte_212_185;	// թ -> Թ -- ARMENIAN CAPITAL LETTER TO
							case -86: return byte_212_186;	// ժ -> Ժ -- ARMENIAN CAPITAL LETTER ZHE
							case -85: return byte_212_187;	// ի -> Ի -- ARMENIAN CAPITAL LETTER INI
							case -84: return byte_212_188;	// լ -> Լ -- ARMENIAN CAPITAL LETTER LIWN
							case -83: return byte_212_189;	// խ -> Խ -- ARMENIAN CAPITAL LETTER XEH
							case -82: return byte_212_190;	// ծ -> Ծ -- ARMENIAN CAPITAL LETTER CA
							case -81: return byte_212_191;	// կ -> Կ -- ARMENIAN CAPITAL LETTER KEN
							case -80: return byte_213_128;	// հ -> Հ -- ARMENIAN CAPITAL LETTER HO
							case -79: return byte_213_129;	// ձ -> Ձ -- ARMENIAN CAPITAL LETTER JA
							case -78: return byte_213_130;	// ղ -> Ղ -- ARMENIAN CAPITAL LETTER LAD
							case -77: return byte_213_131;	// ճ -> Ճ -- ARMENIAN CAPITAL LETTER CHEH
							case -76: return byte_213_132;	// մ -> Մ -- ARMENIAN CAPITAL LETTER MEN
							case -75: return byte_213_133;	// յ -> Յ -- ARMENIAN CAPITAL LETTER YI
							case -74: return byte_213_134;	// ն -> Ն -- ARMENIAN CAPITAL LETTER NOW
							case -73: return byte_213_135;	// շ -> Շ -- ARMENIAN CAPITAL LETTER SHA
							case -72: return byte_213_136;	// ո -> Ո -- ARMENIAN CAPITAL LETTER VO
							case -71: return byte_213_137;	// չ -> Չ -- ARMENIAN CAPITAL LETTER CHA
							case -70: return byte_213_138;	// պ -> Պ -- ARMENIAN CAPITAL LETTER PEH
							case -69: return byte_213_139;	// ջ -> Ջ -- ARMENIAN CAPITAL LETTER JHEH
							case -68: return byte_213_140;	// ռ -> Ռ -- ARMENIAN CAPITAL LETTER RA
							case -67: return byte_213_141;	// ս -> Ս -- ARMENIAN CAPITAL LETTER SEH
							case -66: return byte_213_142;	// վ -> Վ -- ARMENIAN CAPITAL LETTER VEW
							case -65: return byte_213_143;	// տ -> Տ -- ARMENIAN CAPITAL LETTER TIWN
						}
						break;
					case -42:
						switch (b2) {
							case -128: return byte_213_144;	// ր -> Ր -- ARMENIAN CAPITAL LETTER REH
							case -127: return byte_213_145;	// ց -> Ց -- ARMENIAN CAPITAL LETTER CO
							case -126: return byte_213_146;	// ւ -> Ւ -- ARMENIAN CAPITAL LETTER YIWN
							case -125: return byte_213_147;	// փ -> Փ -- ARMENIAN CAPITAL LETTER PIWR
							case -124: return byte_213_148;	// ք -> Ք -- ARMENIAN CAPITAL LETTER KEH
							case -123: return byte_213_149;	// օ -> Օ -- ARMENIAN CAPITAL LETTER OH
							case -122: return byte_213_150;	// ֆ -> Ֆ -- ARMENIAN CAPITAL LETTER FEH
						}
				}
				break;
			case 3:
				b1 = src[pos++];
				b2 = src[pos++];
				b3 = src[pos++];
				switch (b1) {
					case -31:
						switch (b2) {
							case -75:
								switch (b3) {
									case -71: return byte_234_157_189;	// ᵹ -> Ᵹ -- LATIN SMALL LETTER INSULAR G
									case -67: return byte_226_177_163;	// ᵽ -> Ᵽ -- LATIN SMALL LETTER P WITH STROKE
								}
								break;
							case -72:
								switch (b3) {
									case -127: return byte_225_184_128;	// ḁ -> Ḁ -- LATIN CAPITAL LETTER A WITH RING BELOW
									case -125: return byte_225_184_130;	// ḃ -> Ḃ -- LATIN CAPITAL LETTER B WITH DOT ABOVE
									case -123: return byte_225_184_132;	// ḅ -> Ḅ -- LATIN CAPITAL LETTER B WITH DOT BELOW
									case -121: return byte_225_184_134;	// ḇ -> Ḇ -- LATIN CAPITAL LETTER B WITH LINE BELOW
									case -119: return byte_225_184_136;	// ḉ -> Ḉ -- LATIN CAPITAL LETTER C WITH CEDILLA AND ACUTE
									case -117: return byte_225_184_138;	// ḋ -> Ḋ -- LATIN CAPITAL LETTER D WITH DOT ABOVE
									case -115: return byte_225_184_140;	// ḍ -> Ḍ -- LATIN CAPITAL LETTER D WITH DOT BELOW
									case -113: return byte_225_184_142;	// ḏ -> Ḏ -- LATIN CAPITAL LETTER D WITH LINE BELOW
									case -111: return byte_225_184_144;	// ḑ -> Ḑ -- LATIN CAPITAL LETTER D WITH CEDILLA
									case -109: return byte_225_184_146;	// ḓ -> Ḓ -- LATIN CAPITAL LETTER D WITH CIRCUMFLEX BELOW
									case -107: return byte_225_184_148;	// ḕ -> Ḕ -- LATIN CAPITAL LETTER E WITH MACRON AND GRAVE
									case -105: return byte_225_184_150;	// ḗ -> Ḗ -- LATIN CAPITAL LETTER E WITH MACRON AND ACUTE
									case -103: return byte_225_184_152;	// ḙ -> Ḙ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX BELOW
									case -101: return byte_225_184_154;	// ḛ -> Ḛ -- LATIN CAPITAL LETTER E WITH TILDE BELOW
									case -99: return byte_225_184_156;	// ḝ -> Ḝ -- LATIN CAPITAL LETTER E WITH CEDILLA AND BREVE
									case -97: return byte_225_184_158;	// ḟ -> Ḟ -- LATIN CAPITAL LETTER F WITH DOT ABOVE
									case -95: return byte_225_184_160;	// ḡ -> Ḡ -- LATIN CAPITAL LETTER G WITH MACRON
									case -93: return byte_225_184_162;	// ḣ -> Ḣ -- LATIN CAPITAL LETTER H WITH DOT ABOVE
									case -91: return byte_225_184_164;	// ḥ -> Ḥ -- LATIN CAPITAL LETTER H WITH DOT BELOW
									case -89: return byte_225_184_166;	// ḧ -> Ḧ -- LATIN CAPITAL LETTER H WITH DIAERESIS
									case -87: return byte_225_184_168;	// ḩ -> Ḩ -- LATIN CAPITAL LETTER H WITH CEDILLA
									case -85: return byte_225_184_170;	// ḫ -> Ḫ -- LATIN CAPITAL LETTER H WITH BREVE BELOW
									case -83: return byte_225_184_172;	// ḭ -> Ḭ -- LATIN CAPITAL LETTER I WITH TILDE BELOW
									case -81: return byte_225_184_174;	// ḯ -> Ḯ -- LATIN CAPITAL LETTER I WITH DIAERESIS AND ACUTE
									case -79: return byte_225_184_176;	// ḱ -> Ḱ -- LATIN CAPITAL LETTER K WITH ACUTE
									case -77: return byte_225_184_178;	// ḳ -> Ḳ -- LATIN CAPITAL LETTER K WITH DOT BELOW
									case -75: return byte_225_184_180;	// ḵ -> Ḵ -- LATIN CAPITAL LETTER K WITH LINE BELOW
									case -73: return byte_225_184_182;	// ḷ -> Ḷ -- LATIN CAPITAL LETTER L WITH DOT BELOW
									case -71: return byte_225_184_184;	// ḹ -> Ḹ -- LATIN CAPITAL LETTER L WITH DOT BELOW AND MACRON
									case -69: return byte_225_184_186;	// ḻ -> Ḻ -- LATIN CAPITAL LETTER L WITH LINE BELOW
									case -67: return byte_225_184_188;	// ḽ -> Ḽ -- LATIN CAPITAL LETTER L WITH CIRCUMFLEX BELOW
									case -65: return byte_225_184_190;	// ḿ -> Ḿ -- LATIN CAPITAL LETTER M WITH ACUTE
								}
								break;
							case -71:
								switch (b3) {
									case -127: return byte_225_185_128;	// ṁ -> Ṁ -- LATIN CAPITAL LETTER M WITH DOT ABOVE
									case -125: return byte_225_185_130;	// ṃ -> Ṃ -- LATIN CAPITAL LETTER M WITH DOT BELOW
									case -123: return byte_225_185_132;	// ṅ -> Ṅ -- LATIN CAPITAL LETTER N WITH DOT ABOVE
									case -121: return byte_225_185_134;	// ṇ -> Ṇ -- LATIN CAPITAL LETTER N WITH DOT BELOW
									case -119: return byte_225_185_136;	// ṉ -> Ṉ -- LATIN CAPITAL LETTER N WITH LINE BELOW
									case -117: return byte_225_185_138;	// ṋ -> Ṋ -- LATIN CAPITAL LETTER N WITH CIRCUMFLEX BELOW
									case -115: return byte_225_185_140;	// ṍ -> Ṍ -- LATIN CAPITAL LETTER O WITH TILDE AND ACUTE
									case -113: return byte_225_185_142;	// ṏ -> Ṏ -- LATIN CAPITAL LETTER O WITH TILDE AND DIAERESIS
									case -111: return byte_225_185_144;	// ṑ -> Ṑ -- LATIN CAPITAL LETTER O WITH MACRON AND GRAVE
									case -109: return byte_225_185_146;	// ṓ -> Ṓ -- LATIN CAPITAL LETTER O WITH MACRON AND ACUTE
									case -107: return byte_225_185_148;	// ṕ -> Ṕ -- LATIN CAPITAL LETTER P WITH ACUTE
									case -105: return byte_225_185_150;	// ṗ -> Ṗ -- LATIN CAPITAL LETTER P WITH DOT ABOVE
									case -103: return byte_225_185_152;	// ṙ -> Ṙ -- LATIN CAPITAL LETTER R WITH DOT ABOVE
									case -101: return byte_225_185_154;	// ṛ -> Ṛ -- LATIN CAPITAL LETTER R WITH DOT BELOW
									case -99: return byte_225_185_156;	// ṝ -> Ṝ -- LATIN CAPITAL LETTER R WITH DOT BELOW AND MACRON
									case -97: return byte_225_185_158;	// ṟ -> Ṟ -- LATIN CAPITAL LETTER R WITH LINE BELOW
									case -95: return byte_225_185_160;	// ṡ -> Ṡ -- LATIN CAPITAL LETTER S WITH DOT ABOVE
									case -93: return byte_225_185_162;	// ṣ -> Ṣ -- LATIN CAPITAL LETTER S WITH DOT BELOW
									case -91: return byte_225_185_164;	// ṥ -> Ṥ -- LATIN CAPITAL LETTER S WITH ACUTE AND DOT ABOVE
									case -89: return byte_225_185_166;	// ṧ -> Ṧ -- LATIN CAPITAL LETTER S WITH CARON AND DOT ABOVE
									case -87: return byte_225_185_168;	// ṩ -> Ṩ -- LATIN CAPITAL LETTER S WITH DOT BELOW AND DOT ABOVE
									case -85: return byte_225_185_170;	// ṫ -> Ṫ -- LATIN CAPITAL LETTER T WITH DOT ABOVE
									case -83: return byte_225_185_172;	// ṭ -> Ṭ -- LATIN CAPITAL LETTER T WITH DOT BELOW
									case -81: return byte_225_185_174;	// ṯ -> Ṯ -- LATIN CAPITAL LETTER T WITH LINE BELOW
									case -79: return byte_225_185_176;	// ṱ -> Ṱ -- LATIN CAPITAL LETTER T WITH CIRCUMFLEX BELOW
									case -77: return byte_225_185_178;	// ṳ -> Ṳ -- LATIN CAPITAL LETTER U WITH DIAERESIS BELOW
									case -75: return byte_225_185_180;	// ṵ -> Ṵ -- LATIN CAPITAL LETTER U WITH TILDE BELOW
									case -73: return byte_225_185_182;	// ṷ -> Ṷ -- LATIN CAPITAL LETTER U WITH CIRCUMFLEX BELOW
									case -71: return byte_225_185_184;	// ṹ -> Ṹ -- LATIN CAPITAL LETTER U WITH TILDE AND ACUTE
									case -69: return byte_225_185_186;	// ṻ -> Ṻ -- LATIN CAPITAL LETTER U WITH MACRON AND DIAERESIS
									case -67: return byte_225_185_188;	// ṽ -> Ṽ -- LATIN CAPITAL LETTER V WITH TILDE
									case -65: return byte_225_185_190;	// ṿ -> Ṿ -- LATIN CAPITAL LETTER V WITH DOT BELOW
								}
								break;
							case -70:
								switch (b3) {
									case -127: return byte_225_186_128;	// ẁ -> Ẁ -- LATIN CAPITAL LETTER W WITH GRAVE
									case -125: return byte_225_186_130;	// ẃ -> Ẃ -- LATIN CAPITAL LETTER W WITH ACUTE
									case -123: return byte_225_186_132;	// ẅ -> Ẅ -- LATIN CAPITAL LETTER W WITH DIAERESIS
									case -121: return byte_225_186_134;	// ẇ -> Ẇ -- LATIN CAPITAL LETTER W WITH DOT ABOVE
									case -119: return byte_225_186_136;	// ẉ -> Ẉ -- LATIN CAPITAL LETTER W WITH DOT BELOW
									case -117: return byte_225_186_138;	// ẋ -> Ẋ -- LATIN CAPITAL LETTER X WITH DOT ABOVE
									case -115: return byte_225_186_140;	// ẍ -> Ẍ -- LATIN CAPITAL LETTER X WITH DIAERESIS
									case -113: return byte_225_186_142;	// ẏ -> Ẏ -- LATIN CAPITAL LETTER Y WITH DOT ABOVE
									case -111: return byte_225_186_144;	// ẑ -> Ẑ -- LATIN CAPITAL LETTER Z WITH CIRCUMFLEX
									case -109: return byte_225_186_146;	// ẓ -> Ẓ -- LATIN CAPITAL LETTER Z WITH DOT BELOW
									case -107: return byte_225_186_148;	// ẕ -> Ẕ -- LATIN CAPITAL LETTER Z WITH LINE BELOW
									case -101: return byte_225_185_160;	// ẛ -> Ṡ -- LATIN SMALL LETTER LONG S WITH DOT ABOVE
									case -95: return byte_225_186_160;	// ạ -> Ạ -- LATIN CAPITAL LETTER A WITH DOT BELOW
									case -93: return byte_225_186_162;	// ả -> Ả -- LATIN CAPITAL LETTER A WITH HOOK ABOVE
									case -91: return byte_225_186_164;	// ấ -> Ấ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND ACUTE
									case -89: return byte_225_186_166;	// ầ -> Ầ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND GRAVE
									case -87: return byte_225_186_168;	// ẩ -> Ẩ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND HOOK ABOVE
									case -85: return byte_225_186_170;	// ẫ -> Ẫ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND TILDE
									case -83: return byte_225_186_172;	// ậ -> Ậ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND DOT BELOW
									case -81: return byte_225_186_174;	// ắ -> Ắ -- LATIN CAPITAL LETTER A WITH BREVE AND ACUTE
									case -79: return byte_225_186_176;	// ằ -> Ằ -- LATIN CAPITAL LETTER A WITH BREVE AND GRAVE
									case -77: return byte_225_186_178;	// ẳ -> Ẳ -- LATIN CAPITAL LETTER A WITH BREVE AND HOOK ABOVE
									case -75: return byte_225_186_180;	// ẵ -> Ẵ -- LATIN CAPITAL LETTER A WITH BREVE AND TILDE
									case -73: return byte_225_186_182;	// ặ -> Ặ -- LATIN CAPITAL LETTER A WITH BREVE AND DOT BELOW
									case -71: return byte_225_186_184;	// ẹ -> Ẹ -- LATIN CAPITAL LETTER E WITH DOT BELOW
									case -69: return byte_225_186_186;	// ẻ -> Ẻ -- LATIN CAPITAL LETTER E WITH HOOK ABOVE
									case -67: return byte_225_186_188;	// ẽ -> Ẽ -- LATIN CAPITAL LETTER E WITH TILDE
									case -65: return byte_225_186_190;	// ế -> Ế -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND ACUTE
								}
								break;
							case -69:
								switch (b3) {
									case -127: return byte_225_187_128;	// ề -> Ề -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND GRAVE
									case -125: return byte_225_187_130;	// ể -> Ể -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND HOOK ABOVE
									case -123: return byte_225_187_132;	// ễ -> Ễ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND TILDE
									case -121: return byte_225_187_134;	// ệ -> Ệ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND DOT BELOW
									case -119: return byte_225_187_136;	// ỉ -> Ỉ -- LATIN CAPITAL LETTER I WITH HOOK ABOVE
									case -117: return byte_225_187_138;	// ị -> Ị -- LATIN CAPITAL LETTER I WITH DOT BELOW
									case -115: return byte_225_187_140;	// ọ -> Ọ -- LATIN CAPITAL LETTER O WITH DOT BELOW
									case -113: return byte_225_187_142;	// ỏ -> Ỏ -- LATIN CAPITAL LETTER O WITH HOOK ABOVE
									case -111: return byte_225_187_144;	// ố -> Ố -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND ACUTE
									case -109: return byte_225_187_146;	// ồ -> Ồ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND GRAVE
									case -107: return byte_225_187_148;	// ổ -> Ổ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND HOOK ABOVE
									case -105: return byte_225_187_150;	// ỗ -> Ỗ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND TILDE
									case -103: return byte_225_187_152;	// ộ -> Ộ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND DOT BELOW
									case -101: return byte_225_187_154;	// ớ -> Ớ -- LATIN CAPITAL LETTER O WITH HORN AND ACUTE
									case -99: return byte_225_187_156;	// ờ -> Ờ -- LATIN CAPITAL LETTER O WITH HORN AND GRAVE
									case -97: return byte_225_187_158;	// ở -> Ở -- LATIN CAPITAL LETTER O WITH HORN AND HOOK ABOVE
									case -95: return byte_225_187_160;	// ỡ -> Ỡ -- LATIN CAPITAL LETTER O WITH HORN AND TILDE
									case -93: return byte_225_187_162;	// ợ -> Ợ -- LATIN CAPITAL LETTER O WITH HORN AND DOT BELOW
									case -91: return byte_225_187_164;	// ụ -> Ụ -- LATIN CAPITAL LETTER U WITH DOT BELOW
									case -89: return byte_225_187_166;	// ủ -> Ủ -- LATIN CAPITAL LETTER U WITH HOOK ABOVE
									case -87: return byte_225_187_168;	// ứ -> Ứ -- LATIN CAPITAL LETTER U WITH HORN AND ACUTE
									case -85: return byte_225_187_170;	// ừ -> Ừ -- LATIN CAPITAL LETTER U WITH HORN AND GRAVE
									case -83: return byte_225_187_172;	// ử -> Ử -- LATIN CAPITAL LETTER U WITH HORN AND HOOK ABOVE
									case -81: return byte_225_187_174;	// ữ -> Ữ -- LATIN CAPITAL LETTER U WITH HORN AND TILDE
									case -79: return byte_225_187_176;	// ự -> Ự -- LATIN CAPITAL LETTER U WITH HORN AND DOT BELOW
									case -77: return byte_225_187_178;	// ỳ -> Ỳ -- LATIN CAPITAL LETTER Y WITH GRAVE
									case -75: return byte_225_187_180;	// ỵ -> Ỵ -- LATIN CAPITAL LETTER Y WITH DOT BELOW
									case -73: return byte_225_187_182;	// ỷ -> Ỷ -- LATIN CAPITAL LETTER Y WITH HOOK ABOVE
									case -71: return byte_225_187_184;	// ỹ -> Ỹ -- LATIN CAPITAL LETTER Y WITH TILDE
									case -69: return byte_225_187_186;	// ỻ -> Ỻ -- LATIN CAPITAL LETTER MIDDLE-WELSH LL
									case -67: return byte_225_187_188;	// ỽ -> Ỽ -- LATIN CAPITAL LETTER MIDDLE-WELSH V
									case -65: return byte_225_187_190;	// ỿ -> Ỿ -- LATIN CAPITAL LETTER Y WITH LOOP
								}
								break;
							case -68:
								switch (b3) {
									case -128: return byte_225_188_136;	// ἀ -> Ἀ -- GREEK SMALL LETTER ALPHA WITH PSILI
									case -127: return byte_225_188_137;	// ἁ -> Ἁ -- GREEK SMALL LETTER ALPHA WITH DASIA
									case -126: return byte_225_188_138;	// ἂ -> Ἂ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA
									case -125: return byte_225_188_139;	// ἃ -> Ἃ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA
									case -124: return byte_225_188_140;	// ἄ -> Ἄ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA
									case -123: return byte_225_188_141;	// ἅ -> Ἅ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA
									case -122: return byte_225_188_142;	// ἆ -> Ἆ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI
									case -121: return byte_225_188_143;	// ἇ -> Ἇ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI
									case -112: return byte_225_188_152;	// ἐ -> Ἐ -- GREEK SMALL LETTER EPSILON WITH PSILI
									case -111: return byte_225_188_153;	// ἑ -> Ἑ -- GREEK SMALL LETTER EPSILON WITH DASIA
									case -110: return byte_225_188_154;	// ἒ -> Ἒ -- GREEK SMALL LETTER EPSILON WITH PSILI AND VARIA
									case -109: return byte_225_188_155;	// ἓ -> Ἓ -- GREEK SMALL LETTER EPSILON WITH DASIA AND VARIA
									case -108: return byte_225_188_156;	// ἔ -> Ἔ -- GREEK SMALL LETTER EPSILON WITH PSILI AND OXIA
									case -107: return byte_225_188_157;	// ἕ -> Ἕ -- GREEK SMALL LETTER EPSILON WITH DASIA AND OXIA
									case -96: return byte_225_188_168;	// ἠ -> Ἠ -- GREEK SMALL LETTER ETA WITH PSILI
									case -95: return byte_225_188_169;	// ἡ -> Ἡ -- GREEK SMALL LETTER ETA WITH DASIA
									case -94: return byte_225_188_170;	// ἢ -> Ἢ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA
									case -93: return byte_225_188_171;	// ἣ -> Ἣ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA
									case -92: return byte_225_188_172;	// ἤ -> Ἤ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA
									case -91: return byte_225_188_173;	// ἥ -> Ἥ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA
									case -90: return byte_225_188_174;	// ἦ -> Ἦ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI
									case -89: return byte_225_188_175;	// ἧ -> Ἧ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI
									case -80: return byte_225_188_184;	// ἰ -> Ἰ -- GREEK SMALL LETTER IOTA WITH PSILI
									case -79: return byte_225_188_185;	// ἱ -> Ἱ -- GREEK SMALL LETTER IOTA WITH DASIA
									case -78: return byte_225_188_186;	// ἲ -> Ἲ -- GREEK SMALL LETTER IOTA WITH PSILI AND VARIA
									case -77: return byte_225_188_187;	// ἳ -> Ἳ -- GREEK SMALL LETTER IOTA WITH DASIA AND VARIA
									case -76: return byte_225_188_188;	// ἴ -> Ἴ -- GREEK SMALL LETTER IOTA WITH PSILI AND OXIA
									case -75: return byte_225_188_189;	// ἵ -> Ἵ -- GREEK SMALL LETTER IOTA WITH DASIA AND OXIA
									case -74: return byte_225_188_190;	// ἶ -> Ἶ -- GREEK SMALL LETTER IOTA WITH PSILI AND PERISPOMENI
									case -73: return byte_225_188_191;	// ἷ -> Ἷ -- GREEK SMALL LETTER IOTA WITH DASIA AND PERISPOMENI
								}
								break;
							case -67:
								switch (b3) {
									case -128: return byte_225_189_136;	// ὀ -> Ὀ -- GREEK SMALL LETTER OMICRON WITH PSILI
									case -127: return byte_225_189_137;	// ὁ -> Ὁ -- GREEK SMALL LETTER OMICRON WITH DASIA
									case -126: return byte_225_189_138;	// ὂ -> Ὂ -- GREEK SMALL LETTER OMICRON WITH PSILI AND VARIA
									case -125: return byte_225_189_139;	// ὃ -> Ὃ -- GREEK SMALL LETTER OMICRON WITH DASIA AND VARIA
									case -124: return byte_225_189_140;	// ὄ -> Ὄ -- GREEK SMALL LETTER OMICRON WITH PSILI AND OXIA
									case -123: return byte_225_189_141;	// ὅ -> Ὅ -- GREEK SMALL LETTER OMICRON WITH DASIA AND OXIA
									case -111: return byte_225_189_153;	// ὑ -> Ὑ -- GREEK SMALL LETTER UPSILON WITH DASIA
									case -109: return byte_225_189_155;	// ὓ -> Ὓ -- GREEK SMALL LETTER UPSILON WITH DASIA AND VARIA
									case -107: return byte_225_189_157;	// ὕ -> Ὕ -- GREEK SMALL LETTER UPSILON WITH DASIA AND OXIA
									case -105: return byte_225_189_159;	// ὗ -> Ὗ -- GREEK SMALL LETTER UPSILON WITH DASIA AND PERISPOMENI
									case -96: return byte_225_189_168;	// ὠ -> Ὠ -- GREEK SMALL LETTER OMEGA WITH PSILI
									case -95: return byte_225_189_169;	// ὡ -> Ὡ -- GREEK SMALL LETTER OMEGA WITH DASIA
									case -94: return byte_225_189_170;	// ὢ -> Ὢ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA
									case -93: return byte_225_189_171;	// ὣ -> Ὣ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA
									case -92: return byte_225_189_172;	// ὤ -> Ὤ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA
									case -91: return byte_225_189_173;	// ὥ -> Ὥ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA
									case -90: return byte_225_189_174;	// ὦ -> Ὦ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI
									case -89: return byte_225_189_175;	// ὧ -> Ὧ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI
									case -80: return byte_225_190_186;	// ὰ -> Ὰ -- GREEK SMALL LETTER ALPHA WITH VARIA
									case -79: return byte_225_190_187;	// ά -> Ά -- GREEK SMALL LETTER ALPHA WITH OXIA
									case -78: return byte_225_191_136;	// ὲ -> Ὲ -- GREEK SMALL LETTER EPSILON WITH VARIA
									case -77: return byte_225_191_137;	// έ -> Έ -- GREEK SMALL LETTER EPSILON WITH OXIA
									case -76: return byte_225_191_138;	// ὴ -> Ὴ -- GREEK SMALL LETTER ETA WITH VARIA
									case -75: return byte_225_191_139;	// ή -> Ή -- GREEK SMALL LETTER ETA WITH OXIA
									case -74: return byte_225_191_154;	// ὶ -> Ὶ -- GREEK SMALL LETTER IOTA WITH VARIA
									case -73: return byte_225_191_155;	// ί -> Ί -- GREEK SMALL LETTER IOTA WITH OXIA
									case -72: return byte_225_191_184;	// ὸ -> Ὸ -- GREEK SMALL LETTER OMICRON WITH VARIA
									case -71: return byte_225_191_185;	// ό -> Ό -- GREEK SMALL LETTER OMICRON WITH OXIA
									case -70: return byte_225_191_170;	// ὺ -> Ὺ -- GREEK SMALL LETTER UPSILON WITH VARIA
									case -69: return byte_225_191_171;	// ύ -> Ύ -- GREEK SMALL LETTER UPSILON WITH OXIA
									case -68: return byte_225_191_186;	// ὼ -> Ὼ -- GREEK SMALL LETTER OMEGA WITH VARIA
									case -67: return byte_225_191_187;	// ώ -> Ώ -- GREEK SMALL LETTER OMEGA WITH OXIA
								}
								break;
							case -66:
								switch (b3) {
									case -128: return byte_225_190_136;	// ᾀ -> ᾈ -- GREEK SMALL LETTER ALPHA WITH PSILI AND YPOGEGRAMMENI
									case -127: return byte_225_190_137;	// ᾁ -> ᾉ -- GREEK SMALL LETTER ALPHA WITH DASIA AND YPOGEGRAMMENI
									case -126: return byte_225_190_138;	// ᾂ -> ᾊ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -125: return byte_225_190_139;	// ᾃ -> ᾋ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -124: return byte_225_190_140;	// ᾄ -> ᾌ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -123: return byte_225_190_141;	// ᾅ -> ᾍ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -122: return byte_225_190_142;	// ᾆ -> ᾎ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -121: return byte_225_190_143;	// ᾇ -> ᾏ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -112: return byte_225_190_152;	// ᾐ -> ᾘ -- GREEK SMALL LETTER ETA WITH PSILI AND YPOGEGRAMMENI
									case -111: return byte_225_190_153;	// ᾑ -> ᾙ -- GREEK SMALL LETTER ETA WITH DASIA AND YPOGEGRAMMENI
									case -110: return byte_225_190_154;	// ᾒ -> ᾚ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -109: return byte_225_190_155;	// ᾓ -> ᾛ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -108: return byte_225_190_156;	// ᾔ -> ᾜ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -107: return byte_225_190_157;	// ᾕ -> ᾝ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -106: return byte_225_190_158;	// ᾖ -> ᾞ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -105: return byte_225_190_159;	// ᾗ -> ᾟ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -96: return byte_225_190_168;	// ᾠ -> ᾨ -- GREEK SMALL LETTER OMEGA WITH PSILI AND YPOGEGRAMMENI
									case -95: return byte_225_190_169;	// ᾡ -> ᾩ -- GREEK SMALL LETTER OMEGA WITH DASIA AND YPOGEGRAMMENI
									case -94: return byte_225_190_170;	// ᾢ -> ᾪ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -93: return byte_225_190_171;	// ᾣ -> ᾫ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -92: return byte_225_190_172;	// ᾤ -> ᾬ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -91: return byte_225_190_173;	// ᾥ -> ᾭ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -90: return byte_225_190_174;	// ᾦ -> ᾮ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -89: return byte_225_190_175;	// ᾧ -> ᾯ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -80: return byte_225_190_184;	// ᾰ -> Ᾰ -- GREEK SMALL LETTER ALPHA WITH VRACHY
									case -79: return byte_225_190_185;	// ᾱ -> Ᾱ -- GREEK SMALL LETTER ALPHA WITH MACRON
									case -77: return byte_225_190_188;	// ᾳ -> ᾼ -- GREEK SMALL LETTER ALPHA WITH YPOGEGRAMMENI
									case -66: return byte_206_153;	// ι -> Ι -- GREEK PROSGEGRAMMENI
								}
								break;
							case -65:
								switch (b3) {
									case -125: return byte_225_191_140;	// ῃ -> ῌ -- GREEK SMALL LETTER ETA WITH YPOGEGRAMMENI
									case -112: return byte_225_191_152;	// ῐ -> Ῐ -- GREEK SMALL LETTER IOTA WITH VRACHY
									case -111: return byte_225_191_153;	// ῑ -> Ῑ -- GREEK SMALL LETTER IOTA WITH MACRON
									case -96: return byte_225_191_168;	// ῠ -> Ῠ -- GREEK SMALL LETTER UPSILON WITH VRACHY
									case -95: return byte_225_191_169;	// ῡ -> Ῡ -- GREEK SMALL LETTER UPSILON WITH MACRON
									case -91: return byte_225_191_172;	// ῥ -> Ῥ -- GREEK SMALL LETTER RHO WITH DASIA
									case -77: return byte_225_191_188;	// ῳ -> ῼ -- GREEK SMALL LETTER OMEGA WITH YPOGEGRAMMENI
								}
						}
						break;
					case -30:
						switch (b2) {
							case -123:
								switch (b3) {
									case -114: return byte_226_132_178;	// ⅎ -> Ⅎ -- TURNED F
									case -80: return byte_226_133_160;	// ⅰ -> Ⅰ -- ROMAN NUMERAL ONE
									case -79: return byte_226_133_161;	// ⅱ -> Ⅱ -- ROMAN NUMERAL TWO
									case -78: return byte_226_133_162;	// ⅲ -> Ⅲ -- ROMAN NUMERAL THREE
									case -77: return byte_226_133_163;	// ⅳ -> Ⅳ -- ROMAN NUMERAL FOUR
									case -76: return byte_226_133_164;	// ⅴ -> Ⅴ -- ROMAN NUMERAL FIVE
									case -75: return byte_226_133_165;	// ⅵ -> Ⅵ -- ROMAN NUMERAL SIX
									case -74: return byte_226_133_166;	// ⅶ -> Ⅶ -- ROMAN NUMERAL SEVEN
									case -73: return byte_226_133_167;	// ⅷ -> Ⅷ -- ROMAN NUMERAL EIGHT
									case -72: return byte_226_133_168;	// ⅸ -> Ⅸ -- ROMAN NUMERAL NINE
									case -71: return byte_226_133_169;	// ⅹ -> Ⅹ -- ROMAN NUMERAL TEN
									case -70: return byte_226_133_170;	// ⅺ -> Ⅺ -- ROMAN NUMERAL ELEVEN
									case -69: return byte_226_133_171;	// ⅻ -> Ⅻ -- ROMAN NUMERAL TWELVE
									case -68: return byte_226_133_172;	// ⅼ -> Ⅼ -- ROMAN NUMERAL FIFTY
									case -67: return byte_226_133_173;	// ⅽ -> Ⅽ -- ROMAN NUMERAL ONE HUNDRED
									case -66: return byte_226_133_174;	// ⅾ -> Ⅾ -- ROMAN NUMERAL FIVE HUNDRED
									case -65: return byte_226_133_175;	// ⅿ -> Ⅿ -- ROMAN NUMERAL ONE THOUSAND
								}
								break;
							case -122:
								if (b3 == -124) return byte_226_134_131;	// ↄ -> Ↄ -- ROMAN NUMERAL REVERSED ONE HUNDRED
								break;
							case -109:
								switch (b3) {
									case -112: return byte_226_146_182;	// ⓐ -> Ⓐ -- CIRCLED LATIN CAPITAL LETTER A
									case -111: return byte_226_146_183;	// ⓑ -> Ⓑ -- CIRCLED LATIN CAPITAL LETTER B
									case -110: return byte_226_146_184;	// ⓒ -> Ⓒ -- CIRCLED LATIN CAPITAL LETTER C
									case -109: return byte_226_146_185;	// ⓓ -> Ⓓ -- CIRCLED LATIN CAPITAL LETTER D
									case -108: return byte_226_146_186;	// ⓔ -> Ⓔ -- CIRCLED LATIN CAPITAL LETTER E
									case -107: return byte_226_146_187;	// ⓕ -> Ⓕ -- CIRCLED LATIN CAPITAL LETTER F
									case -106: return byte_226_146_188;	// ⓖ -> Ⓖ -- CIRCLED LATIN CAPITAL LETTER G
									case -105: return byte_226_146_189;	// ⓗ -> Ⓗ -- CIRCLED LATIN CAPITAL LETTER H
									case -104: return byte_226_146_190;	// ⓘ -> Ⓘ -- CIRCLED LATIN CAPITAL LETTER I
									case -103: return byte_226_146_191;	// ⓙ -> Ⓙ -- CIRCLED LATIN CAPITAL LETTER J
									case -102: return byte_226_147_128;	// ⓚ -> Ⓚ -- CIRCLED LATIN CAPITAL LETTER K
									case -101: return byte_226_147_129;	// ⓛ -> Ⓛ -- CIRCLED LATIN CAPITAL LETTER L
									case -100: return byte_226_147_130;	// ⓜ -> Ⓜ -- CIRCLED LATIN CAPITAL LETTER M
									case -99: return byte_226_147_131;	// ⓝ -> Ⓝ -- CIRCLED LATIN CAPITAL LETTER N
									case -98: return byte_226_147_132;	// ⓞ -> Ⓞ -- CIRCLED LATIN CAPITAL LETTER O
									case -97: return byte_226_147_133;	// ⓟ -> Ⓟ -- CIRCLED LATIN CAPITAL LETTER P
									case -96: return byte_226_147_134;	// ⓠ -> Ⓠ -- CIRCLED LATIN CAPITAL LETTER Q
									case -95: return byte_226_147_135;	// ⓡ -> Ⓡ -- CIRCLED LATIN CAPITAL LETTER R
									case -94: return byte_226_147_136;	// ⓢ -> Ⓢ -- CIRCLED LATIN CAPITAL LETTER S
									case -93: return byte_226_147_137;	// ⓣ -> Ⓣ -- CIRCLED LATIN CAPITAL LETTER T
									case -92: return byte_226_147_138;	// ⓤ -> Ⓤ -- CIRCLED LATIN CAPITAL LETTER U
									case -91: return byte_226_147_139;	// ⓥ -> Ⓥ -- CIRCLED LATIN CAPITAL LETTER V
									case -90: return byte_226_147_140;	// ⓦ -> Ⓦ -- CIRCLED LATIN CAPITAL LETTER W
									case -89: return byte_226_147_141;	// ⓧ -> Ⓧ -- CIRCLED LATIN CAPITAL LETTER X
									case -88: return byte_226_147_142;	// ⓨ -> Ⓨ -- CIRCLED LATIN CAPITAL LETTER Y
									case -87: return byte_226_147_143;	// ⓩ -> Ⓩ -- CIRCLED LATIN CAPITAL LETTER Z
								}
								break;
							case -80:
								switch (b3) {
									case -80: return byte_226_176_128;	// ⰰ -> Ⰰ -- GLAGOLITIC CAPITAL LETTER AZU
									case -79: return byte_226_176_129;	// ⰱ -> Ⰱ -- GLAGOLITIC CAPITAL LETTER BUKY
									case -78: return byte_226_176_130;	// ⰲ -> Ⰲ -- GLAGOLITIC CAPITAL LETTER VEDE
									case -77: return byte_226_176_131;	// ⰳ -> Ⰳ -- GLAGOLITIC CAPITAL LETTER GLAGOLI
									case -76: return byte_226_176_132;	// ⰴ -> Ⰴ -- GLAGOLITIC CAPITAL LETTER DOBRO
									case -75: return byte_226_176_133;	// ⰵ -> Ⰵ -- GLAGOLITIC CAPITAL LETTER YESTU
									case -74: return byte_226_176_134;	// ⰶ -> Ⰶ -- GLAGOLITIC CAPITAL LETTER ZHIVETE
									case -73: return byte_226_176_135;	// ⰷ -> Ⰷ -- GLAGOLITIC CAPITAL LETTER DZELO
									case -72: return byte_226_176_136;	// ⰸ -> Ⰸ -- GLAGOLITIC CAPITAL LETTER ZEMLJA
									case -71: return byte_226_176_137;	// ⰹ -> Ⰹ -- GLAGOLITIC CAPITAL LETTER IZHE
									case -70: return byte_226_176_138;	// ⰺ -> Ⰺ -- GLAGOLITIC CAPITAL LETTER INITIAL IZHE
									case -69: return byte_226_176_139;	// ⰻ -> Ⰻ -- GLAGOLITIC CAPITAL LETTER I
									case -68: return byte_226_176_140;	// ⰼ -> Ⰼ -- GLAGOLITIC CAPITAL LETTER DJERVI
									case -67: return byte_226_176_141;	// ⰽ -> Ⰽ -- GLAGOLITIC CAPITAL LETTER KAKO
									case -66: return byte_226_176_142;	// ⰾ -> Ⰾ -- GLAGOLITIC CAPITAL LETTER LJUDIJE
									case -65: return byte_226_176_143;	// ⰿ -> Ⰿ -- GLAGOLITIC CAPITAL LETTER MYSLITE
								}
								break;
							case -79:
								switch (b3) {
									case -128: return byte_226_176_144;	// ⱀ -> Ⱀ -- GLAGOLITIC CAPITAL LETTER NASHI
									case -127: return byte_226_176_145;	// ⱁ -> Ⱁ -- GLAGOLITIC CAPITAL LETTER ONU
									case -126: return byte_226_176_146;	// ⱂ -> Ⱂ -- GLAGOLITIC CAPITAL LETTER POKOJI
									case -125: return byte_226_176_147;	// ⱃ -> Ⱃ -- GLAGOLITIC CAPITAL LETTER RITSI
									case -124: return byte_226_176_148;	// ⱄ -> Ⱄ -- GLAGOLITIC CAPITAL LETTER SLOVO
									case -123: return byte_226_176_149;	// ⱅ -> Ⱅ -- GLAGOLITIC CAPITAL LETTER TVRIDO
									case -122: return byte_226_176_150;	// ⱆ -> Ⱆ -- GLAGOLITIC CAPITAL LETTER UKU
									case -121: return byte_226_176_151;	// ⱇ -> Ⱇ -- GLAGOLITIC CAPITAL LETTER FRITU
									case -120: return byte_226_176_152;	// ⱈ -> Ⱈ -- GLAGOLITIC CAPITAL LETTER HERU
									case -119: return byte_226_176_153;	// ⱉ -> Ⱉ -- GLAGOLITIC CAPITAL LETTER OTU
									case -118: return byte_226_176_154;	// ⱊ -> Ⱊ -- GLAGOLITIC CAPITAL LETTER PE
									case -117: return byte_226_176_155;	// ⱋ -> Ⱋ -- GLAGOLITIC CAPITAL LETTER SHTA
									case -116: return byte_226_176_156;	// ⱌ -> Ⱌ -- GLAGOLITIC CAPITAL LETTER TSI
									case -115: return byte_226_176_157;	// ⱍ -> Ⱍ -- GLAGOLITIC CAPITAL LETTER CHRIVI
									case -114: return byte_226_176_158;	// ⱎ -> Ⱎ -- GLAGOLITIC CAPITAL LETTER SHA
									case -113: return byte_226_176_159;	// ⱏ -> Ⱏ -- GLAGOLITIC CAPITAL LETTER YERU
									case -112: return byte_226_176_160;	// ⱐ -> Ⱐ -- GLAGOLITIC CAPITAL LETTER YERI
									case -111: return byte_226_176_161;	// ⱑ -> Ⱑ -- GLAGOLITIC CAPITAL LETTER YATI
									case -110: return byte_226_176_162;	// ⱒ -> Ⱒ -- GLAGOLITIC CAPITAL LETTER SPIDERY HA
									case -109: return byte_226_176_163;	// ⱓ -> Ⱓ -- GLAGOLITIC CAPITAL LETTER YU
									case -108: return byte_226_176_164;	// ⱔ -> Ⱔ -- GLAGOLITIC CAPITAL LETTER SMALL YUS
									case -107: return byte_226_176_165;	// ⱕ -> Ⱕ -- GLAGOLITIC CAPITAL LETTER SMALL YUS WITH TAIL
									case -106: return byte_226_176_166;	// ⱖ -> Ⱖ -- GLAGOLITIC CAPITAL LETTER YO
									case -105: return byte_226_176_167;	// ⱗ -> Ⱗ -- GLAGOLITIC CAPITAL LETTER IOTATED SMALL YUS
									case -104: return byte_226_176_168;	// ⱘ -> Ⱘ -- GLAGOLITIC CAPITAL LETTER BIG YUS
									case -103: return byte_226_176_169;	// ⱙ -> Ⱙ -- GLAGOLITIC CAPITAL LETTER IOTATED BIG YUS
									case -102: return byte_226_176_170;	// ⱚ -> Ⱚ -- GLAGOLITIC CAPITAL LETTER FITA
									case -101: return byte_226_176_171;	// ⱛ -> Ⱛ -- GLAGOLITIC CAPITAL LETTER IZHITSA
									case -100: return byte_226_176_172;	// ⱜ -> Ⱜ -- GLAGOLITIC CAPITAL LETTER SHTAPIC
									case -99: return byte_226_176_173;	// ⱝ -> Ⱝ -- GLAGOLITIC CAPITAL LETTER TROKUTASTI A
									case -98: return byte_226_176_174;	// ⱞ -> Ⱞ -- GLAGOLITIC CAPITAL LETTER LATINATE MYSLITE
									case -95: return byte_226_177_160;	// ⱡ -> Ⱡ -- LATIN CAPITAL LETTER L WITH DOUBLE BAR
									case -91: return byte_200_186;	// ⱥ -> Ⱥ -- LATIN CAPITAL LETTER A WITH STROKE
									case -90: return byte_200_190;	// ⱦ -> Ⱦ -- LATIN CAPITAL LETTER T WITH DIAGONAL STROKE
									case -88: return byte_226_177_167;	// ⱨ -> Ⱨ -- LATIN CAPITAL LETTER H WITH DESCENDER
									case -86: return byte_226_177_169;	// ⱪ -> Ⱪ -- LATIN CAPITAL LETTER K WITH DESCENDER
									case -84: return byte_226_177_171;	// ⱬ -> Ⱬ -- LATIN CAPITAL LETTER Z WITH DESCENDER
									case -77: return byte_226_177_178;	// ⱳ -> Ⱳ -- LATIN CAPITAL LETTER W WITH HOOK
									case -74: return byte_226_177_181;	// ⱶ -> Ⱶ -- LATIN CAPITAL LETTER HALF H
								}
								break;
							case -78:
								switch (b3) {
									case -127: return byte_226_178_128;	// ⲁ -> Ⲁ -- COPTIC CAPITAL LETTER ALFA
									case -125: return byte_226_178_130;	// ⲃ -> Ⲃ -- COPTIC CAPITAL LETTER VIDA
									case -123: return byte_226_178_132;	// ⲅ -> Ⲅ -- COPTIC CAPITAL LETTER GAMMA
									case -121: return byte_226_178_134;	// ⲇ -> Ⲇ -- COPTIC CAPITAL LETTER DALDA
									case -119: return byte_226_178_136;	// ⲉ -> Ⲉ -- COPTIC CAPITAL LETTER EIE
									case -117: return byte_226_178_138;	// ⲋ -> Ⲋ -- COPTIC CAPITAL LETTER SOU
									case -115: return byte_226_178_140;	// ⲍ -> Ⲍ -- COPTIC CAPITAL LETTER ZATA
									case -113: return byte_226_178_142;	// ⲏ -> Ⲏ -- COPTIC CAPITAL LETTER HATE
									case -111: return byte_226_178_144;	// ⲑ -> Ⲑ -- COPTIC CAPITAL LETTER THETHE
									case -109: return byte_226_178_146;	// ⲓ -> Ⲓ -- COPTIC CAPITAL LETTER IAUDA
									case -107: return byte_226_178_148;	// ⲕ -> Ⲕ -- COPTIC CAPITAL LETTER KAPA
									case -105: return byte_226_178_150;	// ⲗ -> Ⲗ -- COPTIC CAPITAL LETTER LAULA
									case -103: return byte_226_178_152;	// ⲙ -> Ⲙ -- COPTIC CAPITAL LETTER MI
									case -101: return byte_226_178_154;	// ⲛ -> Ⲛ -- COPTIC CAPITAL LETTER NI
									case -99: return byte_226_178_156;	// ⲝ -> Ⲝ -- COPTIC CAPITAL LETTER KSI
									case -97: return byte_226_178_158;	// ⲟ -> Ⲟ -- COPTIC CAPITAL LETTER O
									case -95: return byte_226_178_160;	// ⲡ -> Ⲡ -- COPTIC CAPITAL LETTER PI
									case -93: return byte_226_178_162;	// ⲣ -> Ⲣ -- COPTIC CAPITAL LETTER RO
									case -91: return byte_226_178_164;	// ⲥ -> Ⲥ -- COPTIC CAPITAL LETTER SIMA
									case -89: return byte_226_178_166;	// ⲧ -> Ⲧ -- COPTIC CAPITAL LETTER TAU
									case -87: return byte_226_178_168;	// ⲩ -> Ⲩ -- COPTIC CAPITAL LETTER UA
									case -85: return byte_226_178_170;	// ⲫ -> Ⲫ -- COPTIC CAPITAL LETTER FI
									case -83: return byte_226_178_172;	// ⲭ -> Ⲭ -- COPTIC CAPITAL LETTER KHI
									case -81: return byte_226_178_174;	// ⲯ -> Ⲯ -- COPTIC CAPITAL LETTER PSI
									case -79: return byte_226_178_176;	// ⲱ -> Ⲱ -- COPTIC CAPITAL LETTER OOU
									case -77: return byte_226_178_178;	// ⲳ -> Ⲳ -- COPTIC CAPITAL LETTER DIALECT-P ALEF
									case -75: return byte_226_178_180;	// ⲵ -> Ⲵ -- COPTIC CAPITAL LETTER OLD COPTIC AIN
									case -73: return byte_226_178_182;	// ⲷ -> Ⲷ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC EIE
									case -71: return byte_226_178_184;	// ⲹ -> Ⲹ -- COPTIC CAPITAL LETTER DIALECT-P KAPA
									case -69: return byte_226_178_186;	// ⲻ -> Ⲻ -- COPTIC CAPITAL LETTER DIALECT-P NI
									case -67: return byte_226_178_188;	// ⲽ -> Ⲽ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC NI
									case -65: return byte_226_178_190;	// ⲿ -> Ⲿ -- COPTIC CAPITAL LETTER OLD COPTIC OOU
								}
								break;
							case -77:
								switch (b3) {
									case -127: return byte_226_179_128;	// ⳁ -> Ⳁ -- COPTIC CAPITAL LETTER SAMPI
									case -125: return byte_226_179_130;	// ⳃ -> Ⳃ -- COPTIC CAPITAL LETTER CROSSED SHEI
									case -123: return byte_226_179_132;	// ⳅ -> Ⳅ -- COPTIC CAPITAL LETTER OLD COPTIC SHEI
									case -121: return byte_226_179_134;	// ⳇ -> Ⳇ -- COPTIC CAPITAL LETTER OLD COPTIC ESH
									case -119: return byte_226_179_136;	// ⳉ -> Ⳉ -- COPTIC CAPITAL LETTER AKHMIMIC KHEI
									case -117: return byte_226_179_138;	// ⳋ -> Ⳋ -- COPTIC CAPITAL LETTER DIALECT-P HORI
									case -115: return byte_226_179_140;	// ⳍ -> Ⳍ -- COPTIC CAPITAL LETTER OLD COPTIC HORI
									case -113: return byte_226_179_142;	// ⳏ -> Ⳏ -- COPTIC CAPITAL LETTER OLD COPTIC HA
									case -111: return byte_226_179_144;	// ⳑ -> Ⳑ -- COPTIC CAPITAL LETTER L-SHAPED HA
									case -109: return byte_226_179_146;	// ⳓ -> Ⳓ -- COPTIC CAPITAL LETTER OLD COPTIC HEI
									case -107: return byte_226_179_148;	// ⳕ -> Ⳕ -- COPTIC CAPITAL LETTER OLD COPTIC HAT
									case -105: return byte_226_179_150;	// ⳗ -> Ⳗ -- COPTIC CAPITAL LETTER OLD COPTIC GANGIA
									case -103: return byte_226_179_152;	// ⳙ -> Ⳙ -- COPTIC CAPITAL LETTER OLD COPTIC DJA
									case -101: return byte_226_179_154;	// ⳛ -> Ⳛ -- COPTIC CAPITAL LETTER OLD COPTIC SHIMA
									case -99: return byte_226_179_156;	// ⳝ -> Ⳝ -- COPTIC CAPITAL LETTER OLD NUBIAN SHIMA
									case -97: return byte_226_179_158;	// ⳟ -> Ⳟ -- COPTIC CAPITAL LETTER OLD NUBIAN NGI
									case -95: return byte_226_179_160;	// ⳡ -> Ⳡ -- COPTIC CAPITAL LETTER OLD NUBIAN NYI
									case -93: return byte_226_179_162;	// ⳣ -> Ⳣ -- COPTIC CAPITAL LETTER OLD NUBIAN WAU
									case -84: return byte_226_179_171;	// ⳬ -> Ⳬ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC SHEI
									case -82: return byte_226_179_173;	// ⳮ -> Ⳮ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC GANGIA
									case -77: return byte_226_179_178;	// ⳳ -> Ⳳ -- COPTIC CAPITAL LETTER BOHAIRIC KHEI
								}
								break;
							case -76:
								switch (b3) {
									case -128: return byte_225_130_160;	// ⴀ -> Ⴀ -- GEORGIAN CAPITAL LETTER AN
									case -127: return byte_225_130_161;	// ⴁ -> Ⴁ -- GEORGIAN CAPITAL LETTER BAN
									case -126: return byte_225_130_162;	// ⴂ -> Ⴂ -- GEORGIAN CAPITAL LETTER GAN
									case -125: return byte_225_130_163;	// ⴃ -> Ⴃ -- GEORGIAN CAPITAL LETTER DON
									case -124: return byte_225_130_164;	// ⴄ -> Ⴄ -- GEORGIAN CAPITAL LETTER EN
									case -123: return byte_225_130_165;	// ⴅ -> Ⴅ -- GEORGIAN CAPITAL LETTER VIN
									case -122: return byte_225_130_166;	// ⴆ -> Ⴆ -- GEORGIAN CAPITAL LETTER ZEN
									case -121: return byte_225_130_167;	// ⴇ -> Ⴇ -- GEORGIAN CAPITAL LETTER TAN
									case -120: return byte_225_130_168;	// ⴈ -> Ⴈ -- GEORGIAN CAPITAL LETTER IN
									case -119: return byte_225_130_169;	// ⴉ -> Ⴉ -- GEORGIAN CAPITAL LETTER KAN
									case -118: return byte_225_130_170;	// ⴊ -> Ⴊ -- GEORGIAN CAPITAL LETTER LAS
									case -117: return byte_225_130_171;	// ⴋ -> Ⴋ -- GEORGIAN CAPITAL LETTER MAN
									case -116: return byte_225_130_172;	// ⴌ -> Ⴌ -- GEORGIAN CAPITAL LETTER NAR
									case -115: return byte_225_130_173;	// ⴍ -> Ⴍ -- GEORGIAN CAPITAL LETTER ON
									case -114: return byte_225_130_174;	// ⴎ -> Ⴎ -- GEORGIAN CAPITAL LETTER PAR
									case -113: return byte_225_130_175;	// ⴏ -> Ⴏ -- GEORGIAN CAPITAL LETTER ZHAR
									case -112: return byte_225_130_176;	// ⴐ -> Ⴐ -- GEORGIAN CAPITAL LETTER RAE
									case -111: return byte_225_130_177;	// ⴑ -> Ⴑ -- GEORGIAN CAPITAL LETTER SAN
									case -110: return byte_225_130_178;	// ⴒ -> Ⴒ -- GEORGIAN CAPITAL LETTER TAR
									case -109: return byte_225_130_179;	// ⴓ -> Ⴓ -- GEORGIAN CAPITAL LETTER UN
									case -108: return byte_225_130_180;	// ⴔ -> Ⴔ -- GEORGIAN CAPITAL LETTER PHAR
									case -107: return byte_225_130_181;	// ⴕ -> Ⴕ -- GEORGIAN CAPITAL LETTER KHAR
									case -106: return byte_225_130_182;	// ⴖ -> Ⴖ -- GEORGIAN CAPITAL LETTER GHAN
									case -105: return byte_225_130_183;	// ⴗ -> Ⴗ -- GEORGIAN CAPITAL LETTER QAR
									case -104: return byte_225_130_184;	// ⴘ -> Ⴘ -- GEORGIAN CAPITAL LETTER SHIN
									case -103: return byte_225_130_185;	// ⴙ -> Ⴙ -- GEORGIAN CAPITAL LETTER CHIN
									case -102: return byte_225_130_186;	// ⴚ -> Ⴚ -- GEORGIAN CAPITAL LETTER CAN
									case -101: return byte_225_130_187;	// ⴛ -> Ⴛ -- GEORGIAN CAPITAL LETTER JIL
									case -100: return byte_225_130_188;	// ⴜ -> Ⴜ -- GEORGIAN CAPITAL LETTER CIL
									case -99: return byte_225_130_189;	// ⴝ -> Ⴝ -- GEORGIAN CAPITAL LETTER CHAR
									case -98: return byte_225_130_190;	// ⴞ -> Ⴞ -- GEORGIAN CAPITAL LETTER XAN
									case -97: return byte_225_130_191;	// ⴟ -> Ⴟ -- GEORGIAN CAPITAL LETTER JHAN
									case -96: return byte_225_131_128;	// ⴠ -> Ⴠ -- GEORGIAN CAPITAL LETTER HAE
									case -95: return byte_225_131_129;	// ⴡ -> Ⴡ -- GEORGIAN CAPITAL LETTER HE
									case -94: return byte_225_131_130;	// ⴢ -> Ⴢ -- GEORGIAN CAPITAL LETTER HIE
									case -93: return byte_225_131_131;	// ⴣ -> Ⴣ -- GEORGIAN CAPITAL LETTER WE
									case -92: return byte_225_131_132;	// ⴤ -> Ⴤ -- GEORGIAN CAPITAL LETTER HAR
									case -91: return byte_225_131_133;	// ⴥ -> Ⴥ -- GEORGIAN CAPITAL LETTER HOE
									case -89: return byte_225_131_135;	// ⴧ -> Ⴧ -- GEORGIAN CAPITAL LETTER YN
									case -83: return byte_225_131_141;	// ⴭ -> Ⴭ -- GEORGIAN CAPITAL LETTER AEN
								}
						}
						break;
					case -22:
						switch (b2) {
							case -103:
								switch (b3) {
									case -127: return byte_234_153_128;	// ꙁ -> Ꙁ -- CYRILLIC CAPITAL LETTER ZEMLYA
									case -125: return byte_234_153_130;	// ꙃ -> Ꙃ -- CYRILLIC CAPITAL LETTER DZELO
									case -123: return byte_234_153_132;	// ꙅ -> Ꙅ -- CYRILLIC CAPITAL LETTER REVERSED DZE
									case -121: return byte_234_153_134;	// ꙇ -> Ꙇ -- CYRILLIC CAPITAL LETTER IOTA
									case -119: return byte_234_153_136;	// ꙉ -> Ꙉ -- CYRILLIC CAPITAL LETTER DJERV
									case -117: return byte_234_153_138;	// ꙋ -> Ꙋ -- CYRILLIC CAPITAL LETTER MONOGRAPH UK
									case -115: return byte_234_153_140;	// ꙍ -> Ꙍ -- CYRILLIC CAPITAL LETTER BROAD OMEGA
									case -113: return byte_234_153_142;	// ꙏ -> Ꙏ -- CYRILLIC CAPITAL LETTER NEUTRAL YER
									case -111: return byte_234_153_144;	// ꙑ -> Ꙑ -- CYRILLIC CAPITAL LETTER YERU WITH BACK YER
									case -109: return byte_234_153_146;	// ꙓ -> Ꙓ -- CYRILLIC CAPITAL LETTER IOTIFIED YAT
									case -107: return byte_234_153_148;	// ꙕ -> Ꙕ -- CYRILLIC CAPITAL LETTER REVERSED YU
									case -105: return byte_234_153_150;	// ꙗ -> Ꙗ -- CYRILLIC CAPITAL LETTER IOTIFIED A
									case -103: return byte_234_153_152;	// ꙙ -> Ꙙ -- CYRILLIC CAPITAL LETTER CLOSED LITTLE YUS
									case -101: return byte_234_153_154;	// ꙛ -> Ꙛ -- CYRILLIC CAPITAL LETTER BLENDED YUS
									case -99: return byte_234_153_156;	// ꙝ -> Ꙝ -- CYRILLIC CAPITAL LETTER IOTIFIED CLOSED LITTLE YUS
									case -97: return byte_234_153_158;	// ꙟ -> Ꙟ -- CYRILLIC CAPITAL LETTER YN
									case -95: return byte_234_153_160;	// ꙡ -> Ꙡ -- CYRILLIC CAPITAL LETTER REVERSED TSE
									case -93: return byte_234_153_162;	// ꙣ -> Ꙣ -- CYRILLIC CAPITAL LETTER SOFT DE
									case -91: return byte_234_153_164;	// ꙥ -> Ꙥ -- CYRILLIC CAPITAL LETTER SOFT EL
									case -89: return byte_234_153_166;	// ꙧ -> Ꙧ -- CYRILLIC CAPITAL LETTER SOFT EM
									case -87: return byte_234_153_168;	// ꙩ -> Ꙩ -- CYRILLIC CAPITAL LETTER MONOCULAR O
									case -85: return byte_234_153_170;	// ꙫ -> Ꙫ -- CYRILLIC CAPITAL LETTER BINOCULAR O
									case -83: return byte_234_153_172;	// ꙭ -> Ꙭ -- CYRILLIC CAPITAL LETTER DOUBLE MONOCULAR O
								}
								break;
							case -102:
								switch (b3) {
									case -127: return byte_234_154_128;	// ꚁ -> Ꚁ -- CYRILLIC CAPITAL LETTER DWE
									case -125: return byte_234_154_130;	// ꚃ -> Ꚃ -- CYRILLIC CAPITAL LETTER DZWE
									case -123: return byte_234_154_132;	// ꚅ -> Ꚅ -- CYRILLIC CAPITAL LETTER ZHWE
									case -121: return byte_234_154_134;	// ꚇ -> Ꚇ -- CYRILLIC CAPITAL LETTER CCHE
									case -119: return byte_234_154_136;	// ꚉ -> Ꚉ -- CYRILLIC CAPITAL LETTER DZZE
									case -117: return byte_234_154_138;	// ꚋ -> Ꚋ -- CYRILLIC CAPITAL LETTER TE WITH MIDDLE HOOK
									case -115: return byte_234_154_140;	// ꚍ -> Ꚍ -- CYRILLIC CAPITAL LETTER TWE
									case -113: return byte_234_154_142;	// ꚏ -> Ꚏ -- CYRILLIC CAPITAL LETTER TSWE
									case -111: return byte_234_154_144;	// ꚑ -> Ꚑ -- CYRILLIC CAPITAL LETTER TSSE
									case -109: return byte_234_154_146;	// ꚓ -> Ꚓ -- CYRILLIC CAPITAL LETTER TCHE
									case -107: return byte_234_154_148;	// ꚕ -> Ꚕ -- CYRILLIC CAPITAL LETTER HWE
									case -105: return byte_234_154_150;	// ꚗ -> Ꚗ -- CYRILLIC CAPITAL LETTER SHWE
								}
								break;
							case -100:
								switch (b3) {
									case -93: return byte_234_156_162;	// ꜣ -> Ꜣ -- LATIN CAPITAL LETTER EGYPTOLOGICAL ALEF
									case -91: return byte_234_156_164;	// ꜥ -> Ꜥ -- LATIN CAPITAL LETTER EGYPTOLOGICAL AIN
									case -89: return byte_234_156_166;	// ꜧ -> Ꜧ -- LATIN CAPITAL LETTER HENG
									case -87: return byte_234_156_168;	// ꜩ -> Ꜩ -- LATIN CAPITAL LETTER TZ
									case -85: return byte_234_156_170;	// ꜫ -> Ꜫ -- LATIN CAPITAL LETTER TRESILLO
									case -83: return byte_234_156_172;	// ꜭ -> Ꜭ -- LATIN CAPITAL LETTER CUATRILLO
									case -81: return byte_234_156_174;	// ꜯ -> Ꜯ -- LATIN CAPITAL LETTER CUATRILLO WITH COMMA
									case -77: return byte_234_156_178;	// ꜳ -> Ꜳ -- LATIN CAPITAL LETTER AA
									case -75: return byte_234_156_180;	// ꜵ -> Ꜵ -- LATIN CAPITAL LETTER AO
									case -73: return byte_234_156_182;	// ꜷ -> Ꜷ -- LATIN CAPITAL LETTER AU
									case -71: return byte_234_156_184;	// ꜹ -> Ꜹ -- LATIN CAPITAL LETTER AV
									case -69: return byte_234_156_186;	// ꜻ -> Ꜻ -- LATIN CAPITAL LETTER AV WITH HORIZONTAL BAR
									case -67: return byte_234_156_188;	// ꜽ -> Ꜽ -- LATIN CAPITAL LETTER AY
									case -65: return byte_234_156_190;	// ꜿ -> Ꜿ -- LATIN CAPITAL LETTER REVERSED C WITH DOT
								}
								break;
							case -99:
								switch (b3) {
									case -127: return byte_234_157_128;	// ꝁ -> Ꝁ -- LATIN CAPITAL LETTER K WITH STROKE
									case -125: return byte_234_157_130;	// ꝃ -> Ꝃ -- LATIN CAPITAL LETTER K WITH DIAGONAL STROKE
									case -123: return byte_234_157_132;	// ꝅ -> Ꝅ -- LATIN CAPITAL LETTER K WITH STROKE AND DIAGONAL STROKE
									case -121: return byte_234_157_134;	// ꝇ -> Ꝇ -- LATIN CAPITAL LETTER BROKEN L
									case -119: return byte_234_157_136;	// ꝉ -> Ꝉ -- LATIN CAPITAL LETTER L WITH HIGH STROKE
									case -117: return byte_234_157_138;	// ꝋ -> Ꝋ -- LATIN CAPITAL LETTER O WITH LONG STROKE OVERLAY
									case -115: return byte_234_157_140;	// ꝍ -> Ꝍ -- LATIN CAPITAL LETTER O WITH LOOP
									case -113: return byte_234_157_142;	// ꝏ -> Ꝏ -- LATIN CAPITAL LETTER OO
									case -111: return byte_234_157_144;	// ꝑ -> Ꝑ -- LATIN CAPITAL LETTER P WITH STROKE THROUGH DESCENDER
									case -109: return byte_234_157_146;	// ꝓ -> Ꝓ -- LATIN CAPITAL LETTER P WITH FLOURISH
									case -107: return byte_234_157_148;	// ꝕ -> Ꝕ -- LATIN CAPITAL LETTER P WITH SQUIRREL TAIL
									case -105: return byte_234_157_150;	// ꝗ -> Ꝗ -- LATIN CAPITAL LETTER Q WITH STROKE THROUGH DESCENDER
									case -103: return byte_234_157_152;	// ꝙ -> Ꝙ -- LATIN CAPITAL LETTER Q WITH DIAGONAL STROKE
									case -101: return byte_234_157_154;	// ꝛ -> Ꝛ -- LATIN CAPITAL LETTER R ROTUNDA
									case -99: return byte_234_157_156;	// ꝝ -> Ꝝ -- LATIN CAPITAL LETTER RUM ROTUNDA
									case -97: return byte_234_157_158;	// ꝟ -> Ꝟ -- LATIN CAPITAL LETTER V WITH DIAGONAL STROKE
									case -95: return byte_234_157_160;	// ꝡ -> Ꝡ -- LATIN CAPITAL LETTER VY
									case -93: return byte_234_157_162;	// ꝣ -> Ꝣ -- LATIN CAPITAL LETTER VISIGOTHIC Z
									case -91: return byte_234_157_164;	// ꝥ -> Ꝥ -- LATIN CAPITAL LETTER THORN WITH STROKE
									case -89: return byte_234_157_166;	// ꝧ -> Ꝧ -- LATIN CAPITAL LETTER THORN WITH STROKE THROUGH DESCENDER
									case -87: return byte_234_157_168;	// ꝩ -> Ꝩ -- LATIN CAPITAL LETTER VEND
									case -85: return byte_234_157_170;	// ꝫ -> Ꝫ -- LATIN CAPITAL LETTER ET
									case -83: return byte_234_157_172;	// ꝭ -> Ꝭ -- LATIN CAPITAL LETTER IS
									case -81: return byte_234_157_174;	// ꝯ -> Ꝯ -- LATIN CAPITAL LETTER CON
									case -70: return byte_234_157_185;	// ꝺ -> Ꝺ -- LATIN CAPITAL LETTER INSULAR D
									case -68: return byte_234_157_187;	// ꝼ -> Ꝼ -- LATIN CAPITAL LETTER INSULAR F
									case -65: return byte_234_157_190;	// ꝿ -> Ꝿ -- LATIN CAPITAL LETTER TURNED INSULAR G
								}
								break;
							case -98:
								switch (b3) {
									case -127: return byte_234_158_128;	// ꞁ -> Ꞁ -- LATIN CAPITAL LETTER TURNED L
									case -125: return byte_234_158_130;	// ꞃ -> Ꞃ -- LATIN CAPITAL LETTER INSULAR R
									case -123: return byte_234_158_132;	// ꞅ -> Ꞅ -- LATIN CAPITAL LETTER INSULAR S
									case -121: return byte_234_158_134;	// ꞇ -> Ꞇ -- LATIN CAPITAL LETTER INSULAR T
									case -116: return byte_234_158_139;	// ꞌ -> Ꞌ -- LATIN CAPITAL LETTER SALTILLO
									case -111: return byte_234_158_144;	// ꞑ -> Ꞑ -- LATIN CAPITAL LETTER N WITH DESCENDER
									case -109: return byte_234_158_146;	// ꞓ -> Ꞓ -- LATIN CAPITAL LETTER C WITH BAR
									case -95: return byte_234_158_160;	// ꞡ -> Ꞡ -- LATIN CAPITAL LETTER G WITH OBLIQUE STROKE
									case -93: return byte_234_158_162;	// ꞣ -> Ꞣ -- LATIN CAPITAL LETTER K WITH OBLIQUE STROKE
									case -91: return byte_234_158_164;	// ꞥ -> Ꞥ -- LATIN CAPITAL LETTER N WITH OBLIQUE STROKE
									case -89: return byte_234_158_166;	// ꞧ -> Ꞧ -- LATIN CAPITAL LETTER R WITH OBLIQUE STROKE
									case -87: return byte_234_158_168;	// ꞩ -> Ꞩ -- LATIN CAPITAL LETTER S WITH OBLIQUE STROKE
								}
						}
						break;
					case -17:
						switch (b2) {
							case -67:
								switch (b3) {
									case -127: return byte_239_188_161;	// ａ -> Ａ -- FULLWIDTH LATIN CAPITAL LETTER A
									case -126: return byte_239_188_162;	// ｂ -> Ｂ -- FULLWIDTH LATIN CAPITAL LETTER B
									case -125: return byte_239_188_163;	// ｃ -> Ｃ -- FULLWIDTH LATIN CAPITAL LETTER C
									case -124: return byte_239_188_164;	// ｄ -> Ｄ -- FULLWIDTH LATIN CAPITAL LETTER D
									case -123: return byte_239_188_165;	// ｅ -> Ｅ -- FULLWIDTH LATIN CAPITAL LETTER E
									case -122: return byte_239_188_166;	// ｆ -> Ｆ -- FULLWIDTH LATIN CAPITAL LETTER F
									case -121: return byte_239_188_167;	// ｇ -> Ｇ -- FULLWIDTH LATIN CAPITAL LETTER G
									case -120: return byte_239_188_168;	// ｈ -> Ｈ -- FULLWIDTH LATIN CAPITAL LETTER H
									case -119: return byte_239_188_169;	// ｉ -> Ｉ -- FULLWIDTH LATIN CAPITAL LETTER I
									case -118: return byte_239_188_170;	// ｊ -> Ｊ -- FULLWIDTH LATIN CAPITAL LETTER J
									case -117: return byte_239_188_171;	// ｋ -> Ｋ -- FULLWIDTH LATIN CAPITAL LETTER K
									case -116: return byte_239_188_172;	// ｌ -> Ｌ -- FULLWIDTH LATIN CAPITAL LETTER L
									case -115: return byte_239_188_173;	// ｍ -> Ｍ -- FULLWIDTH LATIN CAPITAL LETTER M
									case -114: return byte_239_188_174;	// ｎ -> Ｎ -- FULLWIDTH LATIN CAPITAL LETTER N
									case -113: return byte_239_188_175;	// ｏ -> Ｏ -- FULLWIDTH LATIN CAPITAL LETTER O
									case -112: return byte_239_188_176;	// ｐ -> Ｐ -- FULLWIDTH LATIN CAPITAL LETTER P
									case -111: return byte_239_188_177;	// ｑ -> Ｑ -- FULLWIDTH LATIN CAPITAL LETTER Q
									case -110: return byte_239_188_178;	// ｒ -> Ｒ -- FULLWIDTH LATIN CAPITAL LETTER R
									case -109: return byte_239_188_179;	// ｓ -> Ｓ -- FULLWIDTH LATIN CAPITAL LETTER S
									case -108: return byte_239_188_180;	// ｔ -> Ｔ -- FULLWIDTH LATIN CAPITAL LETTER T
									case -107: return byte_239_188_181;	// ｕ -> Ｕ -- FULLWIDTH LATIN CAPITAL LETTER U
									case -106: return byte_239_188_182;	// ｖ -> Ｖ -- FULLWIDTH LATIN CAPITAL LETTER V
									case -105: return byte_239_188_183;	// ｗ -> Ｗ -- FULLWIDTH LATIN CAPITAL LETTER W
									case -104: return byte_239_188_184;	// ｘ -> Ｘ -- FULLWIDTH LATIN CAPITAL LETTER X
									case -103: return byte_239_188_185;	// ｙ -> Ｙ -- FULLWIDTH LATIN CAPITAL LETTER Y
									case -102: return byte_239_188_186;	// ｚ -> Ｚ -- FULLWIDTH LATIN CAPITAL LETTER Z
								}
						}
				}
				break;
			case 4:
				b1 = src[pos++];
				b2 = src[pos++];
				b3 = src[pos++];
				b4 = src[pos++];
				switch (b1) {
					case -16:
						switch (b2) {
							case -112:
								switch (b3) {
									case -112:
										switch (b4) {
											case -88: return byte_240_144_144_128;	// 𐐨 -> 𐐀 -- DESERET CAPITAL LETTER LONG I
											case -87: return byte_240_144_144_129;	// 𐐩 -> 𐐁 -- DESERET CAPITAL LETTER LONG E
											case -86: return byte_240_144_144_130;	// 𐐪 -> 𐐂 -- DESERET CAPITAL LETTER LONG A
											case -85: return byte_240_144_144_131;	// 𐐫 -> 𐐃 -- DESERET CAPITAL LETTER LONG AH
											case -84: return byte_240_144_144_132;	// 𐐬 -> 𐐄 -- DESERET CAPITAL LETTER LONG O
											case -83: return byte_240_144_144_133;	// 𐐭 -> 𐐅 -- DESERET CAPITAL LETTER LONG OO
											case -82: return byte_240_144_144_134;	// 𐐮 -> 𐐆 -- DESERET CAPITAL LETTER SHORT I
											case -81: return byte_240_144_144_135;	// 𐐯 -> 𐐇 -- DESERET CAPITAL LETTER SHORT E
											case -80: return byte_240_144_144_136;	// 𐐰 -> 𐐈 -- DESERET CAPITAL LETTER SHORT A
											case -79: return byte_240_144_144_137;	// 𐐱 -> 𐐉 -- DESERET CAPITAL LETTER SHORT AH
											case -78: return byte_240_144_144_138;	// 𐐲 -> 𐐊 -- DESERET CAPITAL LETTER SHORT O
											case -77: return byte_240_144_144_139;	// 𐐳 -> 𐐋 -- DESERET CAPITAL LETTER SHORT OO
											case -76: return byte_240_144_144_140;	// 𐐴 -> 𐐌 -- DESERET CAPITAL LETTER AY
											case -75: return byte_240_144_144_141;	// 𐐵 -> 𐐍 -- DESERET CAPITAL LETTER OW
											case -74: return byte_240_144_144_142;	// 𐐶 -> 𐐎 -- DESERET CAPITAL LETTER WU
											case -73: return byte_240_144_144_143;	// 𐐷 -> 𐐏 -- DESERET CAPITAL LETTER YEE
											case -72: return byte_240_144_144_144;	// 𐐸 -> 𐐐 -- DESERET CAPITAL LETTER H
											case -71: return byte_240_144_144_145;	// 𐐹 -> 𐐑 -- DESERET CAPITAL LETTER PEE
											case -70: return byte_240_144_144_146;	// 𐐺 -> 𐐒 -- DESERET CAPITAL LETTER BEE
											case -69: return byte_240_144_144_147;	// 𐐻 -> 𐐓 -- DESERET CAPITAL LETTER TEE
											case -68: return byte_240_144_144_148;	// 𐐼 -> 𐐔 -- DESERET CAPITAL LETTER DEE
											case -67: return byte_240_144_144_149;	// 𐐽 -> 𐐕 -- DESERET CAPITAL LETTER CHEE
											case -66: return byte_240_144_144_150;	// 𐐾 -> 𐐖 -- DESERET CAPITAL LETTER JEE
											case -65: return byte_240_144_144_151;	// 𐐿 -> 𐐗 -- DESERET CAPITAL LETTER KAY
										}
										break;
									case -111:
										switch (b4) {
											case -128: return byte_240_144_144_152;	// 𐑀 -> 𐐘 -- DESERET CAPITAL LETTER GAY
											case -127: return byte_240_144_144_153;	// 𐑁 -> 𐐙 -- DESERET CAPITAL LETTER EF
											case -126: return byte_240_144_144_154;	// 𐑂 -> 𐐚 -- DESERET CAPITAL LETTER VEE
											case -125: return byte_240_144_144_155;	// 𐑃 -> 𐐛 -- DESERET CAPITAL LETTER ETH
											case -124: return byte_240_144_144_156;	// 𐑄 -> 𐐜 -- DESERET CAPITAL LETTER THEE
											case -123: return byte_240_144_144_157;	// 𐑅 -> 𐐝 -- DESERET CAPITAL LETTER ES
											case -122: return byte_240_144_144_158;	// 𐑆 -> 𐐞 -- DESERET CAPITAL LETTER ZEE
											case -121: return byte_240_144_144_159;	// 𐑇 -> 𐐟 -- DESERET CAPITAL LETTER ESH
											case -120: return byte_240_144_144_160;	// 𐑈 -> 𐐠 -- DESERET CAPITAL LETTER ZHEE
											case -119: return byte_240_144_144_161;	// 𐑉 -> 𐐡 -- DESERET CAPITAL LETTER ER
											case -118: return byte_240_144_144_162;	// 𐑊 -> 𐐢 -- DESERET CAPITAL LETTER EL
											case -117: return byte_240_144_144_163;	// 𐑋 -> 𐐣 -- DESERET CAPITAL LETTER EM
											case -116: return byte_240_144_144_164;	// 𐑌 -> 𐐤 -- DESERET CAPITAL LETTER EN
											case -115: return byte_240_144_144_165;	// 𐑍 -> 𐐥 -- DESERET CAPITAL LETTER ENG
											case -114: return byte_240_144_144_166;	// 𐑎 -> 𐐦 -- DESERET CAPITAL LETTER OI
											case -113: return byte_240_144_144_167;	// 𐑏 -> 𐐧 -- DESERET CAPITAL LETTER EW
										}
								}
						}
				}
				break;
		}

		return byte_NOCHANGE;
	}



	public static byte[] Lower(byte[] src, int pos, int b_len) {
		byte b1, b2, b3, b4;

		switch (b_len) {
			case 1:
				b1 = src[pos++];
				switch (b1) {
					case 65: return byte_97;	// a -> A -- LATIN CAPITAL LETTER A
					case 66: return byte_98;	// b -> B -- LATIN CAPITAL LETTER B
					case 67: return byte_99;	// c -> C -- LATIN CAPITAL LETTER C
					case 68: return byte_100;	// d -> D -- LATIN CAPITAL LETTER D
					case 69: return byte_101;	// e -> E -- LATIN CAPITAL LETTER E
					case 70: return byte_102;	// f -> F -- LATIN CAPITAL LETTER F
					case 71: return byte_103;	// g -> G -- LATIN CAPITAL LETTER G
					case 72: return byte_104;	// h -> H -- LATIN CAPITAL LETTER H
					case 73: return byte_105;	// i -> I -- LATIN CAPITAL LETTER I
					case 74: return byte_106;	// j -> J -- LATIN CAPITAL LETTER J
					case 75: return byte_107;	// k -> K -- LATIN CAPITAL LETTER K
					case 76: return byte_108;	// l -> L -- LATIN CAPITAL LETTER L
					case 77: return byte_109;	// m -> M -- LATIN CAPITAL LETTER M
					case 78: return byte_110;	// n -> N -- LATIN CAPITAL LETTER N
					case 79: return byte_111;	// o -> O -- LATIN CAPITAL LETTER O
					case 80: return byte_112;	// p -> P -- LATIN CAPITAL LETTER P
					case 81: return byte_113;	// q -> Q -- LATIN CAPITAL LETTER Q
					case 82: return byte_114;	// r -> R -- LATIN CAPITAL LETTER R
					case 83: return byte_115;	// s -> S -- LATIN CAPITAL LETTER S
					case 84: return byte_116;	// t -> T -- LATIN CAPITAL LETTER T
					case 85: return byte_117;	// u -> U -- LATIN CAPITAL LETTER U
					case 86: return byte_118;	// v -> V -- LATIN CAPITAL LETTER V
					case 87: return byte_119;	// w -> W -- LATIN CAPITAL LETTER W
					case 88: return byte_120;	// x -> X -- LATIN CAPITAL LETTER X
					case 89: return byte_121;	// y -> Y -- LATIN CAPITAL LETTER Y
					case 90: return byte_122;	// z -> Z -- LATIN CAPITAL LETTER Z
				}
				break;
			case 2:
				b1 = src[pos++];
				b2 = src[pos++];
				switch (b1) {
					case -61:
						switch (b2) {
							case -128: return byte_195_160;	// à -> À -- LATIN CAPITAL LETTER A GRAVE
							case -127: return byte_195_161;	// á -> Á -- LATIN CAPITAL LETTER A ACUTE
							case -126: return byte_195_162;	// â -> Â -- LATIN CAPITAL LETTER A CIRCUMFLEX
							case -125: return byte_195_163;	// ã -> Ã -- LATIN CAPITAL LETTER A TILDE
							case -124: return byte_195_164;	// ä -> Ä -- LATIN CAPITAL LETTER A DIAERESIS
							case -123: return byte_195_165;	// å -> Å -- LATIN CAPITAL LETTER A RING
							case -122: return byte_195_166;	// æ -> Æ -- LATIN CAPITAL LETTER A E
							case -121: return byte_195_167;	// ç -> Ç -- LATIN CAPITAL LETTER C CEDILLA
							case -120: return byte_195_168;	// è -> È -- LATIN CAPITAL LETTER E GRAVE
							case -119: return byte_195_169;	// é -> É -- LATIN CAPITAL LETTER E ACUTE
							case -118: return byte_195_170;	// ê -> Ê -- LATIN CAPITAL LETTER E CIRCUMFLEX
							case -117: return byte_195_171;	// ë -> Ë -- LATIN CAPITAL LETTER E DIAERESIS
							case -116: return byte_195_172;	// ì -> Ì -- LATIN CAPITAL LETTER I GRAVE
							case -115: return byte_195_173;	// í -> Í -- LATIN CAPITAL LETTER I ACUTE
							case -114: return byte_195_174;	// î -> Î -- LATIN CAPITAL LETTER I CIRCUMFLEX
							case -113: return byte_195_175;	// ï -> Ï -- LATIN CAPITAL LETTER I DIAERESIS
							case -112: return byte_195_176;	// ð -> Ð -- LATIN CAPITAL LETTER ETH
							case -111: return byte_195_177;	// ñ -> Ñ -- LATIN CAPITAL LETTER N TILDE
							case -110: return byte_195_178;	// ò -> Ò -- LATIN CAPITAL LETTER O GRAVE
							case -109: return byte_195_179;	// ó -> Ó -- LATIN CAPITAL LETTER O ACUTE
							case -108: return byte_195_180;	// ô -> Ô -- LATIN CAPITAL LETTER O CIRCUMFLEX
							case -107: return byte_195_181;	// õ -> Õ -- LATIN CAPITAL LETTER O TILDE
							case -106: return byte_195_182;	// ö -> Ö -- LATIN CAPITAL LETTER O DIAERESIS
							case -104: return byte_195_184;	// ø -> Ø -- LATIN CAPITAL LETTER O SLASH
							case -103: return byte_195_185;	// ù -> Ù -- LATIN CAPITAL LETTER U GRAVE
							case -102: return byte_195_186;	// ú -> Ú -- LATIN CAPITAL LETTER U ACUTE
							case -101: return byte_195_187;	// û -> Û -- LATIN CAPITAL LETTER U CIRCUMFLEX
							case -100: return byte_195_188;	// ü -> Ü -- LATIN CAPITAL LETTER U DIAERESIS
							case -99: return byte_195_189;	// ý -> Ý -- LATIN CAPITAL LETTER Y ACUTE
							case -98: return byte_195_190;	// þ -> Þ -- LATIN CAPITAL LETTER THORN
						}
						break;
					case -60:
						switch (b2) {
							case -128: return byte_196_129;	// ā -> Ā -- LATIN CAPITAL LETTER A MACRON
							case -126: return byte_196_131;	// ă -> Ă -- LATIN CAPITAL LETTER A BREVE
							case -124: return byte_196_133;	// ą -> Ą -- LATIN CAPITAL LETTER A OGONEK
							case -122: return byte_196_135;	// ć -> Ć -- LATIN CAPITAL LETTER C ACUTE
							case -120: return byte_196_137;	// ĉ -> Ĉ -- LATIN CAPITAL LETTER C CIRCUMFLEX
							case -118: return byte_196_139;	// ċ -> Ċ -- LATIN CAPITAL LETTER C DOT
							case -116: return byte_196_141;	// č -> Č -- LATIN CAPITAL LETTER C HACEK
							case -114: return byte_196_143;	// ď -> Ď -- LATIN CAPITAL LETTER D HACEK
							case -112: return byte_196_145;	// đ -> Đ -- LATIN CAPITAL LETTER D BAR
							case -110: return byte_196_147;	// ē -> Ē -- LATIN CAPITAL LETTER E MACRON
							case -108: return byte_196_149;	// ĕ -> Ĕ -- LATIN CAPITAL LETTER E BREVE
							case -106: return byte_196_151;	// ė -> Ė -- LATIN CAPITAL LETTER E DOT
							case -104: return byte_196_153;	// ę -> Ę -- LATIN CAPITAL LETTER E OGONEK
							case -102: return byte_196_155;	// ě -> Ě -- LATIN CAPITAL LETTER E HACEK
							case -100: return byte_196_157;	// ĝ -> Ĝ -- LATIN CAPITAL LETTER G CIRCUMFLEX
							case -98: return byte_196_159;	// ğ -> Ğ -- LATIN CAPITAL LETTER G BREVE
							case -96: return byte_196_161;	// ġ -> Ġ -- LATIN CAPITAL LETTER G DOT
							case -94: return byte_196_163;	// ģ -> Ģ -- LATIN CAPITAL LETTER G CEDILLA
							case -92: return byte_196_165;	// ĥ -> Ĥ -- LATIN CAPITAL LETTER H CIRCUMFLEX
							case -90: return byte_196_167;	// ħ -> Ħ -- LATIN CAPITAL LETTER H BAR
							case -88: return byte_196_169;	// ĩ -> Ĩ -- LATIN CAPITAL LETTER I TILDE
							case -86: return byte_196_171;	// ī -> Ī -- LATIN CAPITAL LETTER I MACRON
							case -84: return byte_196_173;	// ĭ -> Ĭ -- LATIN CAPITAL LETTER I BREVE
							case -82: return byte_196_175;	// į -> Į -- LATIN CAPITAL LETTER I OGONEK
							case -80: return byte_105;	// İ -> i -- LATIN CAPITAL LETTER I DOT
							case -78: return byte_196_179;	// ĳ -> Ĳ -- LATIN CAPITAL LETTER I J
							case -76: return byte_196_181;	// ĵ -> Ĵ -- LATIN CAPITAL LETTER J CIRCUMFLEX
							case -74: return byte_196_183;	// ķ -> Ķ -- LATIN CAPITAL LETTER K CEDILLA
							case -71: return byte_196_186;	// ĺ -> Ĺ -- LATIN CAPITAL LETTER L ACUTE
							case -69: return byte_196_188;	// ļ -> Ļ -- LATIN CAPITAL LETTER L CEDILLA
							case -67: return byte_196_190;	// ľ -> Ľ -- LATIN CAPITAL LETTER L HACEK
							case -65: return byte_197_128;	// ŀ -> Ŀ -- LATIN CAPITAL LETTER L WITH MIDDLE DOT
						}
						break;
					case -59:
						switch (b2) {
							case -127: return byte_197_130;	// ł -> Ł -- LATIN CAPITAL LETTER L SLASH
							case -125: return byte_197_132;	// ń -> Ń -- LATIN CAPITAL LETTER N ACUTE
							case -123: return byte_197_134;	// ņ -> Ņ -- LATIN CAPITAL LETTER N CEDILLA
							case -121: return byte_197_136;	// ň -> Ň -- LATIN CAPITAL LETTER N HACEK
							case -118: return byte_197_139;	// ŋ -> Ŋ -- LATIN CAPITAL LETTER ENG
							case -116: return byte_197_141;	// ō -> Ō -- LATIN CAPITAL LETTER O MACRON
							case -114: return byte_197_143;	// ŏ -> Ŏ -- LATIN CAPITAL LETTER O BREVE
							case -112: return byte_197_145;	// ő -> Ő -- LATIN CAPITAL LETTER O DOUBLE ACUTE
							case -110: return byte_197_147;	// œ -> Œ -- LATIN CAPITAL LETTER O E
							case -108: return byte_197_149;	// ŕ -> Ŕ -- LATIN CAPITAL LETTER R ACUTE
							case -106: return byte_197_151;	// ŗ -> Ŗ -- LATIN CAPITAL LETTER R CEDILLA
							case -104: return byte_197_153;	// ř -> Ř -- LATIN CAPITAL LETTER R HACEK
							case -102: return byte_197_155;	// ś -> Ś -- LATIN CAPITAL LETTER S ACUTE
							case -100: return byte_197_157;	// ŝ -> Ŝ -- LATIN CAPITAL LETTER S CIRCUMFLEX
							case -98: return byte_197_159;	// ş -> Ş -- LATIN CAPITAL LETTER S CEDILLA
							case -96: return byte_197_161;	// š -> Š -- LATIN CAPITAL LETTER S HACEK
							case -94: return byte_197_163;	// ţ -> Ţ -- LATIN CAPITAL LETTER T CEDILLA
							case -92: return byte_197_165;	// ť -> Ť -- LATIN CAPITAL LETTER T HACEK
							case -90: return byte_197_167;	// ŧ -> Ŧ -- LATIN CAPITAL LETTER T BAR
							case -88: return byte_197_169;	// ũ -> Ũ -- LATIN CAPITAL LETTER U TILDE
							case -86: return byte_197_171;	// ū -> Ū -- LATIN CAPITAL LETTER U MACRON
							case -84: return byte_197_173;	// ŭ -> Ŭ -- LATIN CAPITAL LETTER U BREVE
							case -82: return byte_197_175;	// ů -> Ů -- LATIN CAPITAL LETTER U RING
							case -80: return byte_197_177;	// ű -> Ű -- LATIN CAPITAL LETTER U DOUBLE ACUTE
							case -78: return byte_197_179;	// ų -> Ų -- LATIN CAPITAL LETTER U OGONEK
							case -76: return byte_197_181;	// ŵ -> Ŵ -- LATIN CAPITAL LETTER W CIRCUMFLEX
							case -74: return byte_197_183;	// ŷ -> Ŷ -- LATIN CAPITAL LETTER Y CIRCUMFLEX
							case -72: return byte_195_191;	// ÿ -> Ÿ -- LATIN SMALL LETTER Y DIAERESIS
							case -71: return byte_197_186;	// ź -> Ź -- LATIN CAPITAL LETTER Z ACUTE
							case -69: return byte_197_188;	// ż -> Ż -- LATIN CAPITAL LETTER Z DOT
							case -67: return byte_197_190;	// ž -> Ž -- LATIN CAPITAL LETTER Z HACEK
						}
						break;
					case -58:
						switch (b2) {
							case -127: return byte_201_147;	// ɓ -> Ɓ -- LATIN CAPITAL LETTER B HOOK
							case -126: return byte_198_131;	// ƃ -> Ƃ -- LATIN CAPITAL LETTER B TOPBAR
							case -124: return byte_198_133;	// ƅ -> Ƅ -- LATIN CAPITAL LETTER TONE SIX
							case -122: return byte_201_148;	// ɔ -> Ɔ -- LATIN CAPITAL LETTER OPEN O
							case -121: return byte_198_136;	// ƈ -> Ƈ -- LATIN CAPITAL LETTER C HOOK
							case -119: return byte_201_150;	// ɖ -> Ɖ -- LATIN CAPITAL LETTER AFRICAN D
							case -118: return byte_201_151;	// ɗ -> Ɗ -- LATIN CAPITAL LETTER D HOOK
							case -117: return byte_198_140;	// ƌ -> Ƌ -- LATIN CAPITAL LETTER D TOPBAR
							case -114: return byte_199_157;	// ǝ -> Ǝ -- LATIN CAPITAL LETTER TURNED E
							case -113: return byte_201_153;	// ə -> Ə -- LATIN CAPITAL LETTER SCHWA
							case -112: return byte_201_155;	// ɛ -> Ɛ -- LATIN CAPITAL LETTER EPSILON
							case -111: return byte_198_146;	// ƒ -> Ƒ -- LATIN CAPITAL LETTER F HOOK
							case -109: return byte_201_160;	// ɠ -> Ɠ -- LATIN CAPITAL LETTER G HOOK
							case -108: return byte_201_163;	// ɣ -> Ɣ -- LATIN CAPITAL LETTER GAMMA
							case -106: return byte_201_169;	// ɩ -> Ɩ -- LATIN CAPITAL LETTER IOTA
							case -105: return byte_201_168;	// ɨ -> Ɨ -- LATIN CAPITAL LETTER BARRED I
							case -104: return byte_198_153;	// ƙ -> Ƙ -- LATIN CAPITAL LETTER K HOOK
							case -100: return byte_201_175;	// ɯ -> Ɯ -- LATIN CAPITAL LETTER TURNED M
							case -99: return byte_201_178;	// ɲ -> Ɲ -- LATIN CAPITAL LETTER N HOOK
							case -97: return byte_201_181;	// ɵ -> Ɵ -- LATIN CAPITAL LETTER BARRED O
							case -96: return byte_198_161;	// ơ -> Ơ -- LATIN CAPITAL LETTER O HORN
							case -94: return byte_198_163;	// ƣ -> Ƣ -- LATIN CAPITAL LETTER O I
							case -92: return byte_198_165;	// ƥ -> Ƥ -- LATIN CAPITAL LETTER P HOOK
							case -90: return byte_202_128;	// ʀ -> Ʀ -- LATIN LETTER Y R
							case -89: return byte_198_168;	// ƨ -> Ƨ -- LATIN CAPITAL LETTER TONE TWO
							case -87: return byte_202_131;	// ʃ -> Ʃ -- LATIN CAPITAL LETTER ESH
							case -84: return byte_198_173;	// ƭ -> Ƭ -- LATIN CAPITAL LETTER T HOOK
							case -82: return byte_202_136;	// ʈ -> Ʈ -- LATIN CAPITAL LETTER T RETROFLEX HOOK
							case -81: return byte_198_176;	// ư -> Ư -- LATIN CAPITAL LETTER U HORN
							case -79: return byte_202_138;	// ʊ -> Ʊ -- LATIN CAPITAL LETTER UPSILON
							case -78: return byte_202_139;	// ʋ -> Ʋ -- LATIN CAPITAL LETTER SCRIPT V
							case -77: return byte_198_180;	// ƴ -> Ƴ -- LATIN CAPITAL LETTER Y HOOK
							case -75: return byte_198_182;	// ƶ -> Ƶ -- LATIN CAPITAL LETTER Z BAR
							case -73: return byte_202_146;	// ʒ -> Ʒ -- LATIN CAPITAL LETTER YOGH
							case -72: return byte_198_185;	// ƹ -> Ƹ -- LATIN CAPITAL LETTER REVERSED YOGH
							case -68: return byte_198_189;	// ƽ -> Ƽ -- LATIN CAPITAL LETTER TONE FIVE
						}
						break;
					case -57:
						switch (b2) {
							case -124: return byte_199_134;	// ǆ -> Ǆ -- LATIN CAPITAL LETTER D Z HACEK
							case -123: return byte_199_134;	// ǅ -> ǆ -- LATIN LETTER CAPITAL D SMALL Z HACEK
							case -121: return byte_199_137;	// ǉ -> Ǉ -- LATIN CAPITAL LETTER L J
							case -120: return byte_199_137;	// ǈ -> ǉ -- LATIN LETTER CAPITAL L SMALL J
							case -118: return byte_199_140;	// ǌ -> Ǌ -- LATIN CAPITAL LETTER N J
							case -117: return byte_199_140;	// ǋ -> ǌ -- LATIN LETTER CAPITAL N SMALL J
							case -115: return byte_199_142;	// ǎ -> Ǎ -- LATIN CAPITAL LETTER A HACEK
							case -113: return byte_199_144;	// ǐ -> Ǐ -- LATIN CAPITAL LETTER I HACEK
							case -111: return byte_199_146;	// ǒ -> Ǒ -- LATIN CAPITAL LETTER O HACEK
							case -109: return byte_199_148;	// ǔ -> Ǔ -- LATIN CAPITAL LETTER U HACEK
							case -107: return byte_199_150;	// ǖ -> Ǖ -- LATIN CAPITAL LETTER U DIAERESIS MACRON
							case -105: return byte_199_152;	// ǘ -> Ǘ -- LATIN CAPITAL LETTER U DIAERESIS ACUTE
							case -103: return byte_199_154;	// ǚ -> Ǚ -- LATIN CAPITAL LETTER U DIAERESIS HACEK
							case -101: return byte_199_156;	// ǜ -> Ǜ -- LATIN CAPITAL LETTER U DIAERESIS GRAVE
							case -98: return byte_199_159;	// ǟ -> Ǟ -- LATIN CAPITAL LETTER A DIAERESIS MACRON
							case -96: return byte_199_161;	// ǡ -> Ǡ -- LATIN CAPITAL LETTER A DOT MACRON
							case -94: return byte_199_163;	// ǣ -> Ǣ -- LATIN CAPITAL LETTER A E MACRON
							case -92: return byte_199_165;	// ǥ -> Ǥ -- LATIN CAPITAL LETTER G BAR
							case -90: return byte_199_167;	// ǧ -> Ǧ -- LATIN CAPITAL LETTER G HACEK
							case -88: return byte_199_169;	// ǩ -> Ǩ -- LATIN CAPITAL LETTER K HACEK
							case -86: return byte_199_171;	// ǫ -> Ǫ -- LATIN CAPITAL LETTER O OGONEK
							case -84: return byte_199_173;	// ǭ -> Ǭ -- LATIN CAPITAL LETTER O OGONEK MACRON
							case -82: return byte_199_175;	// ǯ -> Ǯ -- LATIN CAPITAL LETTER YOGH HACEK
							case -79: return byte_199_179;	// ǳ -> Ǳ -- LATIN CAPITAL LETTER DZ
							case -78: return byte_199_179;	// ǲ -> ǳ -- LATIN CAPITAL LETTER D WITH SMALL LETTER Z
							case -76: return byte_199_181;	// ǵ -> Ǵ -- LATIN CAPITAL LETTER G WITH ACUTE
							case -74: return byte_198_149;	// ƕ -> Ƕ -- LATIN SMALL LETTER H V
							case -73: return byte_198_191;	// ƿ -> Ƿ -- LATIN LETTER WYNN
							case -72: return byte_199_185;	// ǹ -> Ǹ -- LATIN CAPITAL LETTER N WITH GRAVE
							case -70: return byte_199_187;	// ǻ -> Ǻ -- LATIN CAPITAL LETTER A WITH RING ABOVE AND ACUTE
							case -68: return byte_199_189;	// ǽ -> Ǽ -- LATIN CAPITAL LETTER AE WITH ACUTE
							case -66: return byte_199_191;	// ǿ -> Ǿ -- LATIN CAPITAL LETTER O WITH STROKE AND ACUTE
						}
						break;
					case -56:
						switch (b2) {
							case -128: return byte_200_129;	// ȁ -> Ȁ -- LATIN CAPITAL LETTER A WITH DOUBLE GRAVE
							case -126: return byte_200_131;	// ȃ -> Ȃ -- LATIN CAPITAL LETTER A WITH INVERTED BREVE
							case -124: return byte_200_133;	// ȅ -> Ȅ -- LATIN CAPITAL LETTER E WITH DOUBLE GRAVE
							case -122: return byte_200_135;	// ȇ -> Ȇ -- LATIN CAPITAL LETTER E WITH INVERTED BREVE
							case -120: return byte_200_137;	// ȉ -> Ȉ -- LATIN CAPITAL LETTER I WITH DOUBLE GRAVE
							case -118: return byte_200_139;	// ȋ -> Ȋ -- LATIN CAPITAL LETTER I WITH INVERTED BREVE
							case -116: return byte_200_141;	// ȍ -> Ȍ -- LATIN CAPITAL LETTER O WITH DOUBLE GRAVE
							case -114: return byte_200_143;	// ȏ -> Ȏ -- LATIN CAPITAL LETTER O WITH INVERTED BREVE
							case -112: return byte_200_145;	// ȑ -> Ȑ -- LATIN CAPITAL LETTER R WITH DOUBLE GRAVE
							case -110: return byte_200_147;	// ȓ -> Ȓ -- LATIN CAPITAL LETTER R WITH INVERTED BREVE
							case -108: return byte_200_149;	// ȕ -> Ȕ -- LATIN CAPITAL LETTER U WITH DOUBLE GRAVE
							case -106: return byte_200_151;	// ȗ -> Ȗ -- LATIN CAPITAL LETTER U WITH INVERTED BREVE
							case -104: return byte_200_153;	// ș -> Ș -- LATIN CAPITAL LETTER S WITH COMMA BELOW
							case -102: return byte_200_155;	// ț -> Ț -- LATIN CAPITAL LETTER T WITH COMMA BELOW
							case -100: return byte_200_157;	// ȝ -> Ȝ -- LATIN CAPITAL LETTER YOGH
							case -98: return byte_200_159;	// ȟ -> Ȟ -- LATIN CAPITAL LETTER H WITH CARON
							case -96: return byte_198_158;	// ƞ -> Ƞ -- LATIN SMALL LETTER N WITH LONG RIGHT LEG
							case -94: return byte_200_163;	// ȣ -> Ȣ -- LATIN CAPITAL LETTER OU
							case -92: return byte_200_165;	// ȥ -> Ȥ -- LATIN CAPITAL LETTER Z WITH HOOK
							case -90: return byte_200_167;	// ȧ -> Ȧ -- LATIN CAPITAL LETTER A WITH DOT ABOVE
							case -88: return byte_200_169;	// ȩ -> Ȩ -- LATIN CAPITAL LETTER E WITH CEDILLA
							case -86: return byte_200_171;	// ȫ -> Ȫ -- LATIN CAPITAL LETTER O WITH DIAERESIS AND MACRON
							case -84: return byte_200_173;	// ȭ -> Ȭ -- LATIN CAPITAL LETTER O WITH TILDE AND MACRON
							case -82: return byte_200_175;	// ȯ -> Ȯ -- LATIN CAPITAL LETTER O WITH DOT ABOVE
							case -80: return byte_200_177;	// ȱ -> Ȱ -- LATIN CAPITAL LETTER O WITH DOT ABOVE AND MACRON
							case -78: return byte_200_179;	// ȳ -> Ȳ -- LATIN CAPITAL LETTER Y WITH MACRON
							case -70: return byte_226_177_165;	// ⱥ -> Ⱥ -- LATIN CAPITAL LETTER A WITH STROKE
							case -69: return byte_200_188;	// ȼ -> Ȼ -- LATIN CAPITAL LETTER C WITH STROKE
							case -67: return byte_198_154;	// ƚ -> Ƚ -- LATIN SMALL LETTER BARRED L
							case -66: return byte_226_177_166;	// ⱦ -> Ⱦ -- LATIN CAPITAL LETTER T WITH DIAGONAL STROKE
						}
						break;
					case -55:
						switch (b2) {
							case -127: return byte_201_130;	// ɂ -> Ɂ -- LATIN CAPITAL LETTER GLOTTAL STOP
							case -125: return byte_198_128;	// ƀ -> Ƀ -- LATIN SMALL LETTER B BAR
							case -124: return byte_202_137;	// ʉ -> Ʉ -- LATIN CAPITAL LETTER U BAR
							case -123: return byte_202_140;	// ʌ -> Ʌ -- LATIN CAPITAL LETTER TURNED V
							case -122: return byte_201_135;	// ɇ -> Ɇ -- LATIN CAPITAL LETTER E WITH STROKE
							case -120: return byte_201_137;	// ɉ -> Ɉ -- LATIN CAPITAL LETTER J WITH STROKE
							case -118: return byte_201_139;	// ɋ -> Ɋ -- LATIN CAPITAL LETTER SMALL Q WITH HOOK TAIL
							case -116: return byte_201_141;	// ɍ -> Ɍ -- LATIN CAPITAL LETTER R WITH STROKE
							case -114: return byte_201_143;	// ɏ -> Ɏ -- LATIN CAPITAL LETTER Y WITH STROKE
						}
						break;
					case -51:
						switch (b2) {
							case -80: return byte_205_177;	// ͱ -> Ͱ -- GREEK CAPITAL LETTER HETA
							case -78: return byte_205_179;	// ͳ -> Ͳ -- GREEK CAPITAL LETTER ARCHAIC SAMPI
							case -74: return byte_205_183;	// ͷ -> Ͷ -- GREEK CAPITAL LETTER PAMPHYLIAN DIGAMMA
						}
						break;
					case -50:
						switch (b2) {
							case -122: return byte_206_172;	// ά -> Ά -- GREEK CAPITAL LETTER ALPHA TONOS
							case -120: return byte_206_173;	// έ -> Έ -- GREEK CAPITAL LETTER EPSILON TONOS
							case -119: return byte_206_174;	// ή -> Ή -- GREEK CAPITAL LETTER ETA TONOS
							case -118: return byte_206_175;	// ί -> Ί -- GREEK CAPITAL LETTER IOTA TONOS
							case -116: return byte_207_140;	// ό -> Ό -- GREEK CAPITAL LETTER OMICRON TONOS
							case -114: return byte_207_141;	// ύ -> Ύ -- GREEK CAPITAL LETTER UPSILON TONOS
							case -113: return byte_207_142;	// ώ -> Ώ -- GREEK CAPITAL LETTER OMEGA TONOS
							case -111: return byte_206_177;	// α -> Α -- GREEK CAPITAL LETTER ALPHA
							case -110: return byte_206_178;	// β -> Β -- GREEK CAPITAL LETTER BETA
							case -109: return byte_206_179;	// γ -> Γ -- GREEK CAPITAL LETTER GAMMA
							case -108: return byte_206_180;	// δ -> Δ -- GREEK CAPITAL LETTER DELTA
							case -107: return byte_206_181;	// ε -> Ε -- GREEK CAPITAL LETTER EPSILON
							case -106: return byte_206_182;	// ζ -> Ζ -- GREEK CAPITAL LETTER ZETA
							case -105: return byte_206_183;	// η -> Η -- GREEK CAPITAL LETTER ETA
							case -104: return byte_206_184;	// θ -> Θ -- GREEK CAPITAL LETTER THETA
							case -103: return byte_206_185;	// Ι -> ι -- GREEK CAPITAL LETTER IOTA; NOTE: reversed; PAGE:en.d:ἀρχιερεύς DATE:2014-09-02
							case -102: return byte_206_186;	// κ -> Κ -- GREEK CAPITAL LETTER KAPPA
							case -101: return byte_206_187;	// λ -> Λ -- GREEK CAPITAL LETTER LAMBDA
							case -100: return byte_206_188;	// μ -> Μ -- GREEK CAPITAL LETTER MU; CONSOLIDATED
							case -99: return byte_206_189;	// ν -> Ν -- GREEK CAPITAL LETTER NU
							case -98: return byte_206_190;	// ξ -> Ξ -- GREEK CAPITAL LETTER XI
							case -97: return byte_206_191;	// ο -> Ο -- GREEK CAPITAL LETTER OMICRON
							case -96: return byte_207_128;	// π -> Π -- GREEK CAPITAL LETTER PI
							case -95: return byte_207_129;	// ρ -> Ρ -- GREEK CAPITAL LETTER RHO
							case -93: return byte_207_131;	// σ -> Σ -- GREEK CAPITAL LETTER SIGMA
							case -92: return byte_207_132;	// τ -> Τ -- GREEK CAPITAL LETTER TAU
							case -91: return byte_207_133;	// υ -> Υ -- GREEK CAPITAL LETTER UPSILON
							case -90: return byte_207_134;	// φ -> Φ -- GREEK CAPITAL LETTER PHI
							case -89: return byte_207_135;	// χ -> Χ -- GREEK CAPITAL LETTER CHI
							case -88: return byte_207_136;	// ψ -> Ψ -- GREEK CAPITAL LETTER PSI
							case -87: return byte_207_137;	// ω -> Ω -- GREEK CAPITAL LETTER OMEGA
							case -86: return byte_207_138;	// ϊ -> Ϊ -- GREEK CAPITAL LETTER IOTA DIAERESIS
							case -85: return byte_207_139;	// ϋ -> Ϋ -- GREEK CAPITAL LETTER UPSILON DIAERESIS
						}
						break;
					case -49:
						switch (b2) {
							case -113: return byte_207_151;	// ϗ -> Ϗ -- GREEK CAPITAL KAI SYMBOL
							case -104: return byte_207_153;	// ϙ -> Ϙ -- GREEK LETTER ARCHAIC KOPPA
							case -102: return byte_207_155;	// ϛ -> Ϛ -- GREEK CAPITAL LETTER STIGMA
							case -100: return byte_207_157;	// ϝ -> Ϝ -- GREEK CAPITAL LETTER DIGAMMA
							case -98: return byte_207_159;	// ϟ -> Ϟ -- GREEK CAPITAL LETTER KOPPA
							case -96: return byte_207_161;	// ϡ -> Ϡ -- GREEK CAPITAL LETTER SAMPI
							case -94: return byte_207_163;	// ϣ -> Ϣ -- GREEK CAPITAL LETTER SHEI
							case -92: return byte_207_165;	// ϥ -> Ϥ -- GREEK CAPITAL LETTER FEI
							case -90: return byte_207_167;	// ϧ -> Ϧ -- GREEK CAPITAL LETTER KHEI
							case -88: return byte_207_169;	// ϩ -> Ϩ -- GREEK CAPITAL LETTER HORI
							case -86: return byte_207_171;	// ϫ -> Ϫ -- GREEK CAPITAL LETTER GANGIA
							case -84: return byte_207_173;	// ϭ -> Ϭ -- GREEK CAPITAL LETTER SHIMA
							case -82: return byte_207_175;	// ϯ -> Ϯ -- GREEK CAPITAL LETTER DEI
							case -76: return byte_206_184;	// ϴ -> θ -- GREEK CAPITAL THETA SYMBOL
							case -73: return byte_207_184;	// ϸ -> Ϸ -- GREEK CAPITAL LETTER SHO
							case -71: return byte_207_178;	// ϲ -> Ϲ -- GREEK SMALL LETTER LUNATE SIGMA
							case -70: return byte_207_187;	// ϻ -> Ϻ -- GREEK CAPITAL LETTER SAN
							case -67: return byte_205_187;	// ͻ -> Ͻ -- GREEK SMALL REVERSED LUNATE SIGMA SYMBOL
							case -66: return byte_205_188;	// ͼ -> Ͼ -- GREEK SMALL DOTTED LUNATE SIGMA SYMBOL
							case -65: return byte_205_189;	// ͽ -> Ͽ -- GREEK SMALL REVERSED DOTTED LUNATE SIGMA SYMBOL
						}
						break;
					case -48:
						switch (b2) {
							case -128: return byte_209_144;	// ѐ -> Ѐ -- CYRILLIC CAPITAL LETTER IE WITH GRAVE
							case -127: return byte_209_145;	// ё -> Ё -- CYRILLIC CAPITAL LETTER IO
							case -126: return byte_209_146;	// ђ -> Ђ -- CYRILLIC CAPITAL LETTER DJE
							case -125: return byte_209_147;	// ѓ -> Ѓ -- CYRILLIC CAPITAL LETTER GJE
							case -124: return byte_209_148;	// є -> Є -- CYRILLIC CAPITAL LETTER E
							case -123: return byte_209_149;	// ѕ -> Ѕ -- CYRILLIC CAPITAL LETTER DZE
							case -122: return byte_209_150;	// і -> І -- CYRILLIC CAPITAL LETTER I
							case -121: return byte_209_151;	// ї -> Ї -- CYRILLIC CAPITAL LETTER YI
							case -120: return byte_209_152;	// ј -> Ј -- CYRILLIC CAPITAL LETTER JE
							case -119: return byte_209_153;	// љ -> Љ -- CYRILLIC CAPITAL LETTER LJE
							case -118: return byte_209_154;	// њ -> Њ -- CYRILLIC CAPITAL LETTER NJE
							case -117: return byte_209_155;	// ћ -> Ћ -- CYRILLIC CAPITAL LETTER TSHE
							case -116: return byte_209_156;	// ќ -> Ќ -- CYRILLIC CAPITAL LETTER KJE
							case -115: return byte_209_157;	// ѝ -> Ѝ -- CYRILLIC CAPITAL LETTER I WITH GRAVE
							case -114: return byte_209_158;	// ў -> Ў -- CYRILLIC CAPITAL LETTER SHORT U
							case -113: return byte_209_159;	// џ -> Џ -- CYRILLIC CAPITAL LETTER DZHE
							case -112: return byte_208_176;	// а -> А -- CYRILLIC CAPITAL LETTER A
							case -111: return byte_208_177;	// б -> Б -- CYRILLIC CAPITAL LETTER BE
							case -110: return byte_208_178;	// в -> В -- CYRILLIC CAPITAL LETTER VE
							case -109: return byte_208_179;	// г -> Г -- CYRILLIC CAPITAL LETTER GE
							case -108: return byte_208_180;	// д -> Д -- CYRILLIC CAPITAL LETTER DE
							case -107: return byte_208_181;	// е -> Е -- CYRILLIC CAPITAL LETTER IE
							case -106: return byte_208_182;	// ж -> Ж -- CYRILLIC CAPITAL LETTER ZHE
							case -105: return byte_208_183;	// з -> З -- CYRILLIC CAPITAL LETTER ZE
							case -104: return byte_208_184;	// и -> И -- CYRILLIC CAPITAL LETTER II
							case -103: return byte_208_185;	// й -> Й -- CYRILLIC CAPITAL LETTER SHORT II
							case -102: return byte_208_186;	// к -> К -- CYRILLIC CAPITAL LETTER KA
							case -101: return byte_208_187;	// л -> Л -- CYRILLIC CAPITAL LETTER EL
							case -100: return byte_208_188;	// м -> М -- CYRILLIC CAPITAL LETTER EM
							case -99: return byte_208_189;	// н -> Н -- CYRILLIC CAPITAL LETTER EN
							case -98: return byte_208_190;	// о -> О -- CYRILLIC CAPITAL LETTER O
							case -97: return byte_208_191;	// п -> П -- CYRILLIC CAPITAL LETTER PE
							case -96: return byte_209_128;	// р -> Р -- CYRILLIC CAPITAL LETTER ER
							case -95: return byte_209_129;	// с -> С -- CYRILLIC CAPITAL LETTER ES
							case -94: return byte_209_130;	// т -> Т -- CYRILLIC CAPITAL LETTER TE
							case -93: return byte_209_131;	// у -> У -- CYRILLIC CAPITAL LETTER U
							case -92: return byte_209_132;	// ф -> Ф -- CYRILLIC CAPITAL LETTER EF
							case -91: return byte_209_133;	// х -> Х -- CYRILLIC CAPITAL LETTER KHA
							case -90: return byte_209_134;	// ц -> Ц -- CYRILLIC CAPITAL LETTER TSE
							case -89: return byte_209_135;	// ч -> Ч -- CYRILLIC CAPITAL LETTER CHE
							case -88: return byte_209_136;	// ш -> Ш -- CYRILLIC CAPITAL LETTER SHA
							case -87: return byte_209_137;	// щ -> Щ -- CYRILLIC CAPITAL LETTER SHCHA
							case -86: return byte_209_138;	// ъ -> Ъ -- CYRILLIC CAPITAL LETTER HARD SIGN
							case -85: return byte_209_139;	// ы -> Ы -- CYRILLIC CAPITAL LETTER YERI
							case -84: return byte_209_140;	// ь -> Ь -- CYRILLIC CAPITAL LETTER SOFT SIGN
							case -83: return byte_209_141;	// э -> Э -- CYRILLIC CAPITAL LETTER REVERSED E
							case -82: return byte_209_142;	// ю -> Ю -- CYRILLIC CAPITAL LETTER IU
							case -81: return byte_209_143;	// я -> Я -- CYRILLIC CAPITAL LETTER IA
						}
						break;
					case -47:
						switch (b2) {
							case -96: return byte_209_161;	// ѡ -> Ѡ -- CYRILLIC CAPITAL LETTER OMEGA
							case -94: return byte_209_163;	// ѣ -> Ѣ -- CYRILLIC CAPITAL LETTER YAT
							case -92: return byte_209_165;	// ѥ -> Ѥ -- CYRILLIC CAPITAL LETTER IOTIFIED E
							case -90: return byte_209_167;	// ѧ -> Ѧ -- CYRILLIC CAPITAL LETTER LITTLE YUS
							case -88: return byte_209_169;	// ѩ -> Ѩ -- CYRILLIC CAPITAL LETTER IOTIFIED LITTLE YUS
							case -86: return byte_209_171;	// ѫ -> Ѫ -- CYRILLIC CAPITAL LETTER BIG YUS
							case -84: return byte_209_173;	// ѭ -> Ѭ -- CYRILLIC CAPITAL LETTER IOTIFIED BIG YUS
							case -82: return byte_209_175;	// ѯ -> Ѯ -- CYRILLIC CAPITAL LETTER KSI
							case -80: return byte_209_177;	// ѱ -> Ѱ -- CYRILLIC CAPITAL LETTER PSI
							case -78: return byte_209_179;	// ѳ -> Ѳ -- CYRILLIC CAPITAL LETTER FITA
							case -76: return byte_209_181;	// ѵ -> Ѵ -- CYRILLIC CAPITAL LETTER IZHITSA
							case -74: return byte_209_183;	// ѷ -> Ѷ -- CYRILLIC CAPITAL LETTER IZHITSA DOUBLE GRAVE
							case -72: return byte_209_185;	// ѹ -> Ѹ -- CYRILLIC CAPITAL LETTER UK DIGRAPH
							case -70: return byte_209_187;	// ѻ -> Ѻ -- CYRILLIC CAPITAL LETTER ROUND OMEGA
							case -68: return byte_209_189;	// ѽ -> Ѽ -- CYRILLIC CAPITAL LETTER OMEGA TITLO
							case -66: return byte_209_191;	// ѿ -> Ѿ -- CYRILLIC CAPITAL LETTER OT
						}
						break;
					case -46:
						switch (b2) {
							case -128: return byte_210_129;	// ҁ -> Ҁ -- CYRILLIC CAPITAL LETTER KOPPA
							case -118: return byte_210_139;	// ҋ -> Ҋ -- CYRILLIC CAPITAL LETTER SHORT I WITH TAIL
							case -116: return byte_210_141;	// ҍ -> Ҍ -- CYRILLIC CAPITAL LETTER SEMISOFT SIGN
							case -114: return byte_210_143;	// ҏ -> Ҏ -- CYRILLIC CAPITAL LETTER ER WITH TICK
							case -112: return byte_210_145;	// ґ -> Ґ -- CYRILLIC CAPITAL LETTER GE WITH UPTURN
							case -110: return byte_210_147;	// ғ -> Ғ -- CYRILLIC CAPITAL LETTER GE BAR
							case -108: return byte_210_149;	// ҕ -> Ҕ -- CYRILLIC CAPITAL LETTER GE HOOK
							case -106: return byte_210_151;	// җ -> Җ -- CYRILLIC CAPITAL LETTER ZHE WITH RIGHT DESCENDER
							case -104: return byte_210_153;	// ҙ -> Ҙ -- CYRILLIC CAPITAL LETTER ZE CEDILLA
							case -102: return byte_210_155;	// қ -> Қ -- CYRILLIC CAPITAL LETTER KA WITH RIGHT DESCENDER
							case -100: return byte_210_157;	// ҝ -> Ҝ -- CYRILLIC CAPITAL LETTER KA VERTICAL BAR
							case -98: return byte_210_159;	// ҟ -> Ҟ -- CYRILLIC CAPITAL LETTER KA BAR
							case -96: return byte_210_161;	// ҡ -> Ҡ -- CYRILLIC CAPITAL LETTER REVERSED GE KA
							case -94: return byte_210_163;	// ң -> Ң -- CYRILLIC CAPITAL LETTER EN WITH RIGHT DESCENDER
							case -92: return byte_210_165;	// ҥ -> Ҥ -- CYRILLIC CAPITAL LETTER EN GE
							case -90: return byte_210_167;	// ҧ -> Ҧ -- CYRILLIC CAPITAL LETTER PE HOOK
							case -88: return byte_210_169;	// ҩ -> Ҩ -- CYRILLIC CAPITAL LETTER O HOOK
							case -86: return byte_210_171;	// ҫ -> Ҫ -- CYRILLIC CAPITAL LETTER ES CEDILLA
							case -84: return byte_210_173;	// ҭ -> Ҭ -- CYRILLIC CAPITAL LETTER TE WITH RIGHT DESCENDER
							case -82: return byte_210_175;	// ү -> Ү -- CYRILLIC CAPITAL LETTER STRAIGHT U
							case -80: return byte_210_177;	// ұ -> Ұ -- CYRILLIC CAPITAL LETTER STRAIGHT U BAR
							case -78: return byte_210_179;	// ҳ -> Ҳ -- CYRILLIC CAPITAL LETTER KHA WITH RIGHT DESCENDER
							case -76: return byte_210_181;	// ҵ -> Ҵ -- CYRILLIC CAPITAL LETTER TE TSE
							case -74: return byte_210_183;	// ҷ -> Ҷ -- CYRILLIC CAPITAL LETTER CHE WITH RIGHT DESCENDER
							case -72: return byte_210_185;	// ҹ -> Ҹ -- CYRILLIC CAPITAL LETTER CHE VERTICAL BAR
							case -70: return byte_210_187;	// һ -> Һ -- CYRILLIC CAPITAL LETTER H
							case -68: return byte_210_189;	// ҽ -> Ҽ -- CYRILLIC CAPITAL LETTER IE HOOK
							case -66: return byte_210_191;	// ҿ -> Ҿ -- CYRILLIC CAPITAL LETTER IE HOOK OGONEK
						}
						break;
					case -45:
						switch (b2) {
							case -128: return byte_211_143;	// ӏ -> Ӏ -- CYRILLIC LETTER I
							case -127: return byte_211_130;	// ӂ -> Ӂ -- CYRILLIC CAPITAL LETTER SHORT ZHE
							case -125: return byte_211_132;	// ӄ -> Ӄ -- CYRILLIC CAPITAL LETTER KA HOOK
							case -123: return byte_211_134;	// ӆ -> Ӆ -- CYRILLIC CAPITAL LETTER EL WITH TAIL
							case -121: return byte_211_136;	// ӈ -> Ӈ -- CYRILLIC CAPITAL LETTER EN HOOK
							case -119: return byte_211_138;	// ӊ -> Ӊ -- CYRILLIC CAPITAL LETTER EN WITH TAIL
							case -117: return byte_211_140;	// ӌ -> Ӌ -- CYRILLIC CAPITAL LETTER CHE WITH LEFT DESCENDER
							case -115: return byte_211_142;	// ӎ -> Ӎ -- CYRILLIC CAPITAL LETTER EM WITH TAIL
							case -112: return byte_211_145;	// ӑ -> Ӑ -- CYRILLIC CAPITAL LETTER A WITH BREVE
							case -110: return byte_211_147;	// ӓ -> Ӓ -- CYRILLIC CAPITAL LETTER A WITH DIAERESIS
							case -108: return byte_211_149;	// ӕ -> Ӕ -- CYRILLIC CAPITAL LIGATURE A IE
							case -106: return byte_211_151;	// ӗ -> Ӗ -- CYRILLIC CAPITAL LETTER IE WITH BREVE
							case -104: return byte_211_153;	// ә -> Ә -- CYRILLIC CAPITAL LETTER SCHWA
							case -102: return byte_211_155;	// ӛ -> Ӛ -- CYRILLIC CAPITAL LETTER SCHWA WITH DIAERESIS
							case -100: return byte_211_157;	// ӝ -> Ӝ -- CYRILLIC CAPITAL LETTER ZHE WITH DIAERESIS
							case -98: return byte_211_159;	// ӟ -> Ӟ -- CYRILLIC CAPITAL LETTER ZE WITH DIAERESIS
							case -96: return byte_211_161;	// ӡ -> Ӡ -- CYRILLIC CAPITAL LETTER ABKHASIAN DZE
							case -94: return byte_211_163;	// ӣ -> Ӣ -- CYRILLIC CAPITAL LETTER I WITH MACRON
							case -92: return byte_211_165;	// ӥ -> Ӥ -- CYRILLIC CAPITAL LETTER I WITH DIAERESIS
							case -90: return byte_211_167;	// ӧ -> Ӧ -- CYRILLIC CAPITAL LETTER O WITH DIAERESIS
							case -88: return byte_211_169;	// ө -> Ө -- CYRILLIC CAPITAL LETTER BARRED O
							case -86: return byte_211_171;	// ӫ -> Ӫ -- CYRILLIC CAPITAL LETTER BARRED O WITH DIAERESIS
							case -84: return byte_211_173;	// ӭ -> Ӭ -- CYRILLIC CAPITAL LETTER E WITH DIAERESIS
							case -82: return byte_211_175;	// ӯ -> Ӯ -- CYRILLIC CAPITAL LETTER U WITH MACRON
							case -80: return byte_211_177;	// ӱ -> Ӱ -- CYRILLIC CAPITAL LETTER U WITH DIAERESIS
							case -78: return byte_211_179;	// ӳ -> Ӳ -- CYRILLIC CAPITAL LETTER U WITH DOUBLE ACUTE
							case -76: return byte_211_181;	// ӵ -> Ӵ -- CYRILLIC CAPITAL LETTER CHE WITH DIAERESIS
							case -74: return byte_211_183;	// ӷ -> Ӷ -- CYRILLIC CAPITAL LETTER GHE WITH DESCENDER
							case -72: return byte_211_185;	// ӹ -> Ӹ -- CYRILLIC CAPITAL LETTER YERU WITH DIAERESIS
							case -70: return byte_211_187;	// ӻ -> Ӻ -- CYRILLIC CAPITAL LETTER GHE WITH STROKE AND HOOK
							case -68: return byte_211_189;	// ӽ -> Ӽ -- CYRILLIC CAPITAL LETTER HA WITH HOOK
							case -66: return byte_211_191;	// ӿ -> Ӿ -- CYRILLIC CAPITAL LETTER HA WITH STROKE
						}
						break;
					case -44:
						switch (b2) {
							case -128: return byte_212_129;	// ԁ -> Ԁ -- CYRILLIC CAPITAL LETTER KOMI DE
							case -126: return byte_212_131;	// ԃ -> Ԃ -- CYRILLIC CAPITAL LETTER KOMI DJE
							case -124: return byte_212_133;	// ԅ -> Ԅ -- CYRILLIC CAPITAL LETTER KOMI ZJE
							case -122: return byte_212_135;	// ԇ -> Ԇ -- CYRILLIC CAPITAL LETTER KOMI DZJE
							case -120: return byte_212_137;	// ԉ -> Ԉ -- CYRILLIC CAPITAL LETTER KOMI LJE
							case -118: return byte_212_139;	// ԋ -> Ԋ -- CYRILLIC CAPITAL LETTER KOMI NJE
							case -116: return byte_212_141;	// ԍ -> Ԍ -- CYRILLIC CAPITAL LETTER KOMI SJE
							case -114: return byte_212_143;	// ԏ -> Ԏ -- CYRILLIC CAPITAL LETTER KOMI TJE
							case -112: return byte_212_145;	// ԑ -> Ԑ -- CYRILLIC CAPITAL LETTER REVERSED ZE
							case -110: return byte_212_147;	// ԓ -> Ԓ -- CYRILLIC CAPITAL LETTER EL WITH HOOK
							case -108: return byte_212_149;	// ԕ -> Ԕ -- CYRILLIC CAPITAL LETTER LHA
							case -106: return byte_212_151;	// ԗ -> Ԗ -- CYRILLIC CAPITAL LETTER RHA
							case -104: return byte_212_153;	// ԙ -> Ԙ -- CYRILLIC CAPITAL LETTER YAE
							case -102: return byte_212_155;	// ԛ -> Ԛ -- CYRILLIC CAPITAL LETTER QA
							case -100: return byte_212_157;	// ԝ -> Ԝ -- CYRILLIC CAPITAL LETTER WE
							case -98: return byte_212_159;	// ԟ -> Ԟ -- CYRILLIC CAPITAL LETTER ALEUT KA
							case -96: return byte_212_161;	// ԡ -> Ԡ -- CYRILLIC CAPITAL LETTER EL WITH MIDDLE HOOK
							case -94: return byte_212_163;	// ԣ -> Ԣ -- CYRILLIC CAPITAL LETTER EN WITH MIDDLE HOOK
							case -92: return byte_212_165;	// ԥ -> Ԥ -- CYRILLIC CAPITAL LETTER PE WITH DESCENDER
							case -90: return byte_212_167;	// ԧ -> Ԧ -- CYRILLIC CAPITAL LETTER SHHA WITH DESCENDER
							case -79: return byte_213_161;	// ա -> Ա -- ARMENIAN CAPITAL LETTER AYB
							case -78: return byte_213_162;	// բ -> Բ -- ARMENIAN CAPITAL LETTER BEN
							case -77: return byte_213_163;	// գ -> Գ -- ARMENIAN CAPITAL LETTER GIM
							case -76: return byte_213_164;	// դ -> Դ -- ARMENIAN CAPITAL LETTER DA
							case -75: return byte_213_165;	// ե -> Ե -- ARMENIAN CAPITAL LETTER ECH
							case -74: return byte_213_166;	// զ -> Զ -- ARMENIAN CAPITAL LETTER ZA
							case -73: return byte_213_167;	// է -> Է -- ARMENIAN CAPITAL LETTER EH
							case -72: return byte_213_168;	// ը -> Ը -- ARMENIAN CAPITAL LETTER ET
							case -71: return byte_213_169;	// թ -> Թ -- ARMENIAN CAPITAL LETTER TO
							case -70: return byte_213_170;	// ժ -> Ժ -- ARMENIAN CAPITAL LETTER ZHE
							case -69: return byte_213_171;	// ի -> Ի -- ARMENIAN CAPITAL LETTER INI
							case -68: return byte_213_172;	// լ -> Լ -- ARMENIAN CAPITAL LETTER LIWN
							case -67: return byte_213_173;	// խ -> Խ -- ARMENIAN CAPITAL LETTER XEH
							case -66: return byte_213_174;	// ծ -> Ծ -- ARMENIAN CAPITAL LETTER CA
							case -65: return byte_213_175;	// կ -> Կ -- ARMENIAN CAPITAL LETTER KEN
						}
						break;
					case -43:
						switch (b2) {
							case -128: return byte_213_176;	// հ -> Հ -- ARMENIAN CAPITAL LETTER HO
							case -127: return byte_213_177;	// ձ -> Ձ -- ARMENIAN CAPITAL LETTER JA
							case -126: return byte_213_178;	// ղ -> Ղ -- ARMENIAN CAPITAL LETTER LAD
							case -125: return byte_213_179;	// ճ -> Ճ -- ARMENIAN CAPITAL LETTER CHEH
							case -124: return byte_213_180;	// մ -> Մ -- ARMENIAN CAPITAL LETTER MEN
							case -123: return byte_213_181;	// յ -> Յ -- ARMENIAN CAPITAL LETTER YI
							case -122: return byte_213_182;	// ն -> Ն -- ARMENIAN CAPITAL LETTER NOW
							case -121: return byte_213_183;	// շ -> Շ -- ARMENIAN CAPITAL LETTER SHA
							case -120: return byte_213_184;	// ո -> Ո -- ARMENIAN CAPITAL LETTER VO
							case -119: return byte_213_185;	// չ -> Չ -- ARMENIAN CAPITAL LETTER CHA
							case -118: return byte_213_186;	// պ -> Պ -- ARMENIAN CAPITAL LETTER PEH
							case -117: return byte_213_187;	// ջ -> Ջ -- ARMENIAN CAPITAL LETTER JHEH
							case -116: return byte_213_188;	// ռ -> Ռ -- ARMENIAN CAPITAL LETTER RA
							case -115: return byte_213_189;	// ս -> Ս -- ARMENIAN CAPITAL LETTER SEH
							case -114: return byte_213_190;	// վ -> Վ -- ARMENIAN CAPITAL LETTER VEW
							case -113: return byte_213_191;	// տ -> Տ -- ARMENIAN CAPITAL LETTER TIWN
							case -112: return byte_214_128;	// ր -> Ր -- ARMENIAN CAPITAL LETTER REH
							case -111: return byte_214_129;	// ց -> Ց -- ARMENIAN CAPITAL LETTER CO
							case -110: return byte_214_130;	// ւ -> Ւ -- ARMENIAN CAPITAL LETTER YIWN
							case -109: return byte_214_131;	// փ -> Փ -- ARMENIAN CAPITAL LETTER PIWR
							case -108: return byte_214_132;	// ք -> Ք -- ARMENIAN CAPITAL LETTER KEH
							case -107: return byte_214_133;	// օ -> Օ -- ARMENIAN CAPITAL LETTER OH
							case -106: return byte_214_134;	// ֆ -> Ֆ -- ARMENIAN CAPITAL LETTER FEH
						}
				}
				break;
			case 3:
				b1 = src[pos++];
				b2 = src[pos++];
				b3 = src[pos++];
				switch (b1) {
					case -31:
						switch (b2) {
							case -126:
								switch (b3) {
									case -96: return byte_226_180_128;	// ⴀ -> Ⴀ -- GEORGIAN CAPITAL LETTER AN
									case -95: return byte_226_180_129;	// ⴁ -> Ⴁ -- GEORGIAN CAPITAL LETTER BAN
									case -94: return byte_226_180_130;	// ⴂ -> Ⴂ -- GEORGIAN CAPITAL LETTER GAN
									case -93: return byte_226_180_131;	// ⴃ -> Ⴃ -- GEORGIAN CAPITAL LETTER DON
									case -92: return byte_226_180_132;	// ⴄ -> Ⴄ -- GEORGIAN CAPITAL LETTER EN
									case -91: return byte_226_180_133;	// ⴅ -> Ⴅ -- GEORGIAN CAPITAL LETTER VIN
									case -90: return byte_226_180_134;	// ⴆ -> Ⴆ -- GEORGIAN CAPITAL LETTER ZEN
									case -89: return byte_226_180_135;	// ⴇ -> Ⴇ -- GEORGIAN CAPITAL LETTER TAN
									case -88: return byte_226_180_136;	// ⴈ -> Ⴈ -- GEORGIAN CAPITAL LETTER IN
									case -87: return byte_226_180_137;	// ⴉ -> Ⴉ -- GEORGIAN CAPITAL LETTER KAN
									case -86: return byte_226_180_138;	// ⴊ -> Ⴊ -- GEORGIAN CAPITAL LETTER LAS
									case -85: return byte_226_180_139;	// ⴋ -> Ⴋ -- GEORGIAN CAPITAL LETTER MAN
									case -84: return byte_226_180_140;	// ⴌ -> Ⴌ -- GEORGIAN CAPITAL LETTER NAR
									case -83: return byte_226_180_141;	// ⴍ -> Ⴍ -- GEORGIAN CAPITAL LETTER ON
									case -82: return byte_226_180_142;	// ⴎ -> Ⴎ -- GEORGIAN CAPITAL LETTER PAR
									case -81: return byte_226_180_143;	// ⴏ -> Ⴏ -- GEORGIAN CAPITAL LETTER ZHAR
									case -80: return byte_226_180_144;	// ⴐ -> Ⴐ -- GEORGIAN CAPITAL LETTER RAE
									case -79: return byte_226_180_145;	// ⴑ -> Ⴑ -- GEORGIAN CAPITAL LETTER SAN
									case -78: return byte_226_180_146;	// ⴒ -> Ⴒ -- GEORGIAN CAPITAL LETTER TAR
									case -77: return byte_226_180_147;	// ⴓ -> Ⴓ -- GEORGIAN CAPITAL LETTER UN
									case -76: return byte_226_180_148;	// ⴔ -> Ⴔ -- GEORGIAN CAPITAL LETTER PHAR
									case -75: return byte_226_180_149;	// ⴕ -> Ⴕ -- GEORGIAN CAPITAL LETTER KHAR
									case -74: return byte_226_180_150;	// ⴖ -> Ⴖ -- GEORGIAN CAPITAL LETTER GHAN
									case -73: return byte_226_180_151;	// ⴗ -> Ⴗ -- GEORGIAN CAPITAL LETTER QAR
									case -72: return byte_226_180_152;	// ⴘ -> Ⴘ -- GEORGIAN CAPITAL LETTER SHIN
									case -71: return byte_226_180_153;	// ⴙ -> Ⴙ -- GEORGIAN CAPITAL LETTER CHIN
									case -70: return byte_226_180_154;	// ⴚ -> Ⴚ -- GEORGIAN CAPITAL LETTER CAN
									case -69: return byte_226_180_155;	// ⴛ -> Ⴛ -- GEORGIAN CAPITAL LETTER JIL
									case -68: return byte_226_180_156;	// ⴜ -> Ⴜ -- GEORGIAN CAPITAL LETTER CIL
									case -67: return byte_226_180_157;	// ⴝ -> Ⴝ -- GEORGIAN CAPITAL LETTER CHAR
									case -66: return byte_226_180_158;	// ⴞ -> Ⴞ -- GEORGIAN CAPITAL LETTER XAN
									case -65: return byte_226_180_159;	// ⴟ -> Ⴟ -- GEORGIAN CAPITAL LETTER JHAN
								}
								break;
							case -125:
								switch (b3) {
									case -128: return byte_226_180_160;	// ⴠ -> Ⴠ -- GEORGIAN CAPITAL LETTER HAE
									case -127: return byte_226_180_161;	// ⴡ -> Ⴡ -- GEORGIAN CAPITAL LETTER HE
									case -126: return byte_226_180_162;	// ⴢ -> Ⴢ -- GEORGIAN CAPITAL LETTER HIE
									case -125: return byte_226_180_163;	// ⴣ -> Ⴣ -- GEORGIAN CAPITAL LETTER WE
									case -124: return byte_226_180_164;	// ⴤ -> Ⴤ -- GEORGIAN CAPITAL LETTER HAR
									case -123: return byte_226_180_165;	// ⴥ -> Ⴥ -- GEORGIAN CAPITAL LETTER HOE
									case -121: return byte_226_180_167;	// ⴧ -> Ⴧ -- GEORGIAN CAPITAL LETTER YN
									case -115: return byte_226_180_173;	// ⴭ -> Ⴭ -- GEORGIAN CAPITAL LETTER AEN
								}
								break;
							case -72:
								switch (b3) {
									case -128: return byte_225_184_129;	// ḁ -> Ḁ -- LATIN CAPITAL LETTER A WITH RING BELOW
									case -126: return byte_225_184_131;	// ḃ -> Ḃ -- LATIN CAPITAL LETTER B WITH DOT ABOVE
									case -124: return byte_225_184_133;	// ḅ -> Ḅ -- LATIN CAPITAL LETTER B WITH DOT BELOW
									case -122: return byte_225_184_135;	// ḇ -> Ḇ -- LATIN CAPITAL LETTER B WITH LINE BELOW
									case -120: return byte_225_184_137;	// ḉ -> Ḉ -- LATIN CAPITAL LETTER C WITH CEDILLA AND ACUTE
									case -118: return byte_225_184_139;	// ḋ -> Ḋ -- LATIN CAPITAL LETTER D WITH DOT ABOVE
									case -116: return byte_225_184_141;	// ḍ -> Ḍ -- LATIN CAPITAL LETTER D WITH DOT BELOW
									case -114: return byte_225_184_143;	// ḏ -> Ḏ -- LATIN CAPITAL LETTER D WITH LINE BELOW
									case -112: return byte_225_184_145;	// ḑ -> Ḑ -- LATIN CAPITAL LETTER D WITH CEDILLA
									case -110: return byte_225_184_147;	// ḓ -> Ḓ -- LATIN CAPITAL LETTER D WITH CIRCUMFLEX BELOW
									case -108: return byte_225_184_149;	// ḕ -> Ḕ -- LATIN CAPITAL LETTER E WITH MACRON AND GRAVE
									case -106: return byte_225_184_151;	// ḗ -> Ḗ -- LATIN CAPITAL LETTER E WITH MACRON AND ACUTE
									case -104: return byte_225_184_153;	// ḙ -> Ḙ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX BELOW
									case -102: return byte_225_184_155;	// ḛ -> Ḛ -- LATIN CAPITAL LETTER E WITH TILDE BELOW
									case -100: return byte_225_184_157;	// ḝ -> Ḝ -- LATIN CAPITAL LETTER E WITH CEDILLA AND BREVE
									case -98: return byte_225_184_159;	// ḟ -> Ḟ -- LATIN CAPITAL LETTER F WITH DOT ABOVE
									case -96: return byte_225_184_161;	// ḡ -> Ḡ -- LATIN CAPITAL LETTER G WITH MACRON
									case -94: return byte_225_184_163;	// ḣ -> Ḣ -- LATIN CAPITAL LETTER H WITH DOT ABOVE
									case -92: return byte_225_184_165;	// ḥ -> Ḥ -- LATIN CAPITAL LETTER H WITH DOT BELOW
									case -90: return byte_225_184_167;	// ḧ -> Ḧ -- LATIN CAPITAL LETTER H WITH DIAERESIS
									case -88: return byte_225_184_169;	// ḩ -> Ḩ -- LATIN CAPITAL LETTER H WITH CEDILLA
									case -86: return byte_225_184_171;	// ḫ -> Ḫ -- LATIN CAPITAL LETTER H WITH BREVE BELOW
									case -84: return byte_225_184_173;	// ḭ -> Ḭ -- LATIN CAPITAL LETTER I WITH TILDE BELOW
									case -82: return byte_225_184_175;	// ḯ -> Ḯ -- LATIN CAPITAL LETTER I WITH DIAERESIS AND ACUTE
									case -80: return byte_225_184_177;	// ḱ -> Ḱ -- LATIN CAPITAL LETTER K WITH ACUTE
									case -78: return byte_225_184_179;	// ḳ -> Ḳ -- LATIN CAPITAL LETTER K WITH DOT BELOW
									case -76: return byte_225_184_181;	// ḵ -> Ḵ -- LATIN CAPITAL LETTER K WITH LINE BELOW
									case -74: return byte_225_184_183;	// ḷ -> Ḷ -- LATIN CAPITAL LETTER L WITH DOT BELOW
									case -72: return byte_225_184_185;	// ḹ -> Ḹ -- LATIN CAPITAL LETTER L WITH DOT BELOW AND MACRON
									case -70: return byte_225_184_187;	// ḻ -> Ḻ -- LATIN CAPITAL LETTER L WITH LINE BELOW
									case -68: return byte_225_184_189;	// ḽ -> Ḽ -- LATIN CAPITAL LETTER L WITH CIRCUMFLEX BELOW
									case -66: return byte_225_184_191;	// ḿ -> Ḿ -- LATIN CAPITAL LETTER M WITH ACUTE
								}
								break;
							case -71:
								switch (b3) {
									case -128: return byte_225_185_129;	// ṁ -> Ṁ -- LATIN CAPITAL LETTER M WITH DOT ABOVE
									case -126: return byte_225_185_131;	// ṃ -> Ṃ -- LATIN CAPITAL LETTER M WITH DOT BELOW
									case -124: return byte_225_185_133;	// ṅ -> Ṅ -- LATIN CAPITAL LETTER N WITH DOT ABOVE
									case -122: return byte_225_185_135;	// ṇ -> Ṇ -- LATIN CAPITAL LETTER N WITH DOT BELOW
									case -120: return byte_225_185_137;	// ṉ -> Ṉ -- LATIN CAPITAL LETTER N WITH LINE BELOW
									case -118: return byte_225_185_139;	// ṋ -> Ṋ -- LATIN CAPITAL LETTER N WITH CIRCUMFLEX BELOW
									case -116: return byte_225_185_141;	// ṍ -> Ṍ -- LATIN CAPITAL LETTER O WITH TILDE AND ACUTE
									case -114: return byte_225_185_143;	// ṏ -> Ṏ -- LATIN CAPITAL LETTER O WITH TILDE AND DIAERESIS
									case -112: return byte_225_185_145;	// ṑ -> Ṑ -- LATIN CAPITAL LETTER O WITH MACRON AND GRAVE
									case -110: return byte_225_185_147;	// ṓ -> Ṓ -- LATIN CAPITAL LETTER O WITH MACRON AND ACUTE
									case -108: return byte_225_185_149;	// ṕ -> Ṕ -- LATIN CAPITAL LETTER P WITH ACUTE
									case -106: return byte_225_185_151;	// ṗ -> Ṗ -- LATIN CAPITAL LETTER P WITH DOT ABOVE
									case -104: return byte_225_185_153;	// ṙ -> Ṙ -- LATIN CAPITAL LETTER R WITH DOT ABOVE
									case -102: return byte_225_185_155;	// ṛ -> Ṛ -- LATIN CAPITAL LETTER R WITH DOT BELOW
									case -100: return byte_225_185_157;	// ṝ -> Ṝ -- LATIN CAPITAL LETTER R WITH DOT BELOW AND MACRON
									case -98: return byte_225_185_159;	// ṟ -> Ṟ -- LATIN CAPITAL LETTER R WITH LINE BELOW
									case -96: return byte_225_185_161;	// ṡ -> Ṡ -- LATIN CAPITAL LETTER S WITH DOT ABOVE
									case -94: return byte_225_185_163;	// ṣ -> Ṣ -- LATIN CAPITAL LETTER S WITH DOT BELOW
									case -92: return byte_225_185_165;	// ṥ -> Ṥ -- LATIN CAPITAL LETTER S WITH ACUTE AND DOT ABOVE
									case -90: return byte_225_185_167;	// ṧ -> Ṧ -- LATIN CAPITAL LETTER S WITH CARON AND DOT ABOVE
									case -88: return byte_225_185_169;	// ṩ -> Ṩ -- LATIN CAPITAL LETTER S WITH DOT BELOW AND DOT ABOVE
									case -86: return byte_225_185_171;	// ṫ -> Ṫ -- LATIN CAPITAL LETTER T WITH DOT ABOVE
									case -84: return byte_225_185_173;	// ṭ -> Ṭ -- LATIN CAPITAL LETTER T WITH DOT BELOW
									case -82: return byte_225_185_175;	// ṯ -> Ṯ -- LATIN CAPITAL LETTER T WITH LINE BELOW
									case -80: return byte_225_185_177;	// ṱ -> Ṱ -- LATIN CAPITAL LETTER T WITH CIRCUMFLEX BELOW
									case -78: return byte_225_185_179;	// ṳ -> Ṳ -- LATIN CAPITAL LETTER U WITH DIAERESIS BELOW
									case -76: return byte_225_185_181;	// ṵ -> Ṵ -- LATIN CAPITAL LETTER U WITH TILDE BELOW
									case -74: return byte_225_185_183;	// ṷ -> Ṷ -- LATIN CAPITAL LETTER U WITH CIRCUMFLEX BELOW
									case -72: return byte_225_185_185;	// ṹ -> Ṹ -- LATIN CAPITAL LETTER U WITH TILDE AND ACUTE
									case -70: return byte_225_185_187;	// ṻ -> Ṻ -- LATIN CAPITAL LETTER U WITH MACRON AND DIAERESIS
									case -68: return byte_225_185_189;	// ṽ -> Ṽ -- LATIN CAPITAL LETTER V WITH TILDE
									case -66: return byte_225_185_191;	// ṿ -> Ṿ -- LATIN CAPITAL LETTER V WITH DOT BELOW
								}
								break;
							case -70:
								switch (b3) {
									case -128: return byte_225_186_129;	// ẁ -> Ẁ -- LATIN CAPITAL LETTER W WITH GRAVE
									case -126: return byte_225_186_131;	// ẃ -> Ẃ -- LATIN CAPITAL LETTER W WITH ACUTE
									case -124: return byte_225_186_133;	// ẅ -> Ẅ -- LATIN CAPITAL LETTER W WITH DIAERESIS
									case -122: return byte_225_186_135;	// ẇ -> Ẇ -- LATIN CAPITAL LETTER W WITH DOT ABOVE
									case -120: return byte_225_186_137;	// ẉ -> Ẉ -- LATIN CAPITAL LETTER W WITH DOT BELOW
									case -118: return byte_225_186_139;	// ẋ -> Ẋ -- LATIN CAPITAL LETTER X WITH DOT ABOVE
									case -116: return byte_225_186_141;	// ẍ -> Ẍ -- LATIN CAPITAL LETTER X WITH DIAERESIS
									case -114: return byte_225_186_143;	// ẏ -> Ẏ -- LATIN CAPITAL LETTER Y WITH DOT ABOVE
									case -112: return byte_225_186_145;	// ẑ -> Ẑ -- LATIN CAPITAL LETTER Z WITH CIRCUMFLEX
									case -110: return byte_225_186_147;	// ẓ -> Ẓ -- LATIN CAPITAL LETTER Z WITH DOT BELOW
									case -108: return byte_225_186_149;	// ẕ -> Ẕ -- LATIN CAPITAL LETTER Z WITH LINE BELOW
									case -98: return byte_195_159;	// ẞ -> ß -- LATIN CAPITAL LETTER SHARP S
									case -96: return byte_225_186_161;	// ạ -> Ạ -- LATIN CAPITAL LETTER A WITH DOT BELOW
									case -94: return byte_225_186_163;	// ả -> Ả -- LATIN CAPITAL LETTER A WITH HOOK ABOVE
									case -92: return byte_225_186_165;	// ấ -> Ấ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND ACUTE
									case -90: return byte_225_186_167;	// ầ -> Ầ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND GRAVE
									case -88: return byte_225_186_169;	// ẩ -> Ẩ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND HOOK ABOVE
									case -86: return byte_225_186_171;	// ẫ -> Ẫ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND TILDE
									case -84: return byte_225_186_173;	// ậ -> Ậ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND DOT BELOW
									case -82: return byte_225_186_175;	// ắ -> Ắ -- LATIN CAPITAL LETTER A WITH BREVE AND ACUTE
									case -80: return byte_225_186_177;	// ằ -> Ằ -- LATIN CAPITAL LETTER A WITH BREVE AND GRAVE
									case -78: return byte_225_186_179;	// ẳ -> Ẳ -- LATIN CAPITAL LETTER A WITH BREVE AND HOOK ABOVE
									case -76: return byte_225_186_181;	// ẵ -> Ẵ -- LATIN CAPITAL LETTER A WITH BREVE AND TILDE
									case -74: return byte_225_186_183;	// ặ -> Ặ -- LATIN CAPITAL LETTER A WITH BREVE AND DOT BELOW
									case -72: return byte_225_186_185;	// ẹ -> Ẹ -- LATIN CAPITAL LETTER E WITH DOT BELOW
									case -70: return byte_225_186_187;	// ẻ -> Ẻ -- LATIN CAPITAL LETTER E WITH HOOK ABOVE
									case -68: return byte_225_186_189;	// ẽ -> Ẽ -- LATIN CAPITAL LETTER E WITH TILDE
									case -66: return byte_225_186_191;	// ế -> Ế -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND ACUTE
								}
								break;
							case -69:
								switch (b3) {
									case -128: return byte_225_187_129;	// ề -> Ề -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND GRAVE
									case -126: return byte_225_187_131;	// ể -> Ể -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND HOOK ABOVE
									case -124: return byte_225_187_133;	// ễ -> Ễ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND TILDE
									case -122: return byte_225_187_135;	// ệ -> Ệ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND DOT BELOW
									case -120: return byte_225_187_137;	// ỉ -> Ỉ -- LATIN CAPITAL LETTER I WITH HOOK ABOVE
									case -118: return byte_225_187_139;	// ị -> Ị -- LATIN CAPITAL LETTER I WITH DOT BELOW
									case -116: return byte_225_187_141;	// ọ -> Ọ -- LATIN CAPITAL LETTER O WITH DOT BELOW
									case -114: return byte_225_187_143;	// ỏ -> Ỏ -- LATIN CAPITAL LETTER O WITH HOOK ABOVE
									case -112: return byte_225_187_145;	// ố -> Ố -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND ACUTE
									case -110: return byte_225_187_147;	// ồ -> Ồ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND GRAVE
									case -108: return byte_225_187_149;	// ổ -> Ổ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND HOOK ABOVE
									case -106: return byte_225_187_151;	// ỗ -> Ỗ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND TILDE
									case -104: return byte_225_187_153;	// ộ -> Ộ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND DOT BELOW
									case -102: return byte_225_187_155;	// ớ -> Ớ -- LATIN CAPITAL LETTER O WITH HORN AND ACUTE
									case -100: return byte_225_187_157;	// ờ -> Ờ -- LATIN CAPITAL LETTER O WITH HORN AND GRAVE
									case -98: return byte_225_187_159;	// ở -> Ở -- LATIN CAPITAL LETTER O WITH HORN AND HOOK ABOVE
									case -96: return byte_225_187_161;	// ỡ -> Ỡ -- LATIN CAPITAL LETTER O WITH HORN AND TILDE
									case -94: return byte_225_187_163;	// ợ -> Ợ -- LATIN CAPITAL LETTER O WITH HORN AND DOT BELOW
									case -92: return byte_225_187_165;	// ụ -> Ụ -- LATIN CAPITAL LETTER U WITH DOT BELOW
									case -90: return byte_225_187_167;	// ủ -> Ủ -- LATIN CAPITAL LETTER U WITH HOOK ABOVE
									case -88: return byte_225_187_169;	// ứ -> Ứ -- LATIN CAPITAL LETTER U WITH HORN AND ACUTE
									case -86: return byte_225_187_171;	// ừ -> Ừ -- LATIN CAPITAL LETTER U WITH HORN AND GRAVE
									case -84: return byte_225_187_173;	// ử -> Ử -- LATIN CAPITAL LETTER U WITH HORN AND HOOK ABOVE
									case -82: return byte_225_187_175;	// ữ -> Ữ -- LATIN CAPITAL LETTER U WITH HORN AND TILDE
									case -80: return byte_225_187_177;	// ự -> Ự -- LATIN CAPITAL LETTER U WITH HORN AND DOT BELOW
									case -78: return byte_225_187_179;	// ỳ -> Ỳ -- LATIN CAPITAL LETTER Y WITH GRAVE
									case -76: return byte_225_187_181;	// ỵ -> Ỵ -- LATIN CAPITAL LETTER Y WITH DOT BELOW
									case -74: return byte_225_187_183;	// ỷ -> Ỷ -- LATIN CAPITAL LETTER Y WITH HOOK ABOVE
									case -72: return byte_225_187_185;	// ỹ -> Ỹ -- LATIN CAPITAL LETTER Y WITH TILDE
									case -70: return byte_225_187_187;	// ỻ -> Ỻ -- LATIN CAPITAL LETTER MIDDLE-WELSH LL
									case -68: return byte_225_187_189;	// ỽ -> Ỽ -- LATIN CAPITAL LETTER MIDDLE-WELSH V
									case -66: return byte_225_187_191;	// ỿ -> Ỿ -- LATIN CAPITAL LETTER Y WITH LOOP
								}
								break;
							case -68:
								switch (b3) {
									case -120: return byte_225_188_128;	// ἀ -> Ἀ -- GREEK SMALL LETTER ALPHA WITH PSILI
									case -119: return byte_225_188_129;	// ἁ -> Ἁ -- GREEK SMALL LETTER ALPHA WITH DASIA
									case -118: return byte_225_188_130;	// ἂ -> Ἂ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA
									case -117: return byte_225_188_131;	// ἃ -> Ἃ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA
									case -116: return byte_225_188_132;	// ἄ -> Ἄ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA
									case -115: return byte_225_188_133;	// ἅ -> Ἅ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA
									case -114: return byte_225_188_134;	// ἆ -> Ἆ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI
									case -113: return byte_225_188_135;	// ἇ -> Ἇ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI
									case -104: return byte_225_188_144;	// ἐ -> Ἐ -- GREEK SMALL LETTER EPSILON WITH PSILI
									case -103: return byte_225_188_145;	// ἑ -> Ἑ -- GREEK SMALL LETTER EPSILON WITH DASIA
									case -102: return byte_225_188_146;	// ἒ -> Ἒ -- GREEK SMALL LETTER EPSILON WITH PSILI AND VARIA
									case -101: return byte_225_188_147;	// ἓ -> Ἓ -- GREEK SMALL LETTER EPSILON WITH DASIA AND VARIA
									case -100: return byte_225_188_148;	// ἔ -> Ἔ -- GREEK SMALL LETTER EPSILON WITH PSILI AND OXIA
									case -99: return byte_225_188_149;	// ἕ -> Ἕ -- GREEK SMALL LETTER EPSILON WITH DASIA AND OXIA
									case -88: return byte_225_188_160;	// ἠ -> Ἠ -- GREEK SMALL LETTER ETA WITH PSILI
									case -87: return byte_225_188_161;	// ἡ -> Ἡ -- GREEK SMALL LETTER ETA WITH DASIA
									case -86: return byte_225_188_162;	// ἢ -> Ἢ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA
									case -85: return byte_225_188_163;	// ἣ -> Ἣ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA
									case -84: return byte_225_188_164;	// ἤ -> Ἤ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA
									case -83: return byte_225_188_165;	// ἥ -> Ἥ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA
									case -82: return byte_225_188_166;	// ἦ -> Ἦ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI
									case -81: return byte_225_188_167;	// ἧ -> Ἧ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI
									case -72: return byte_225_188_176;	// ἰ -> Ἰ -- GREEK SMALL LETTER IOTA WITH PSILI
									case -71: return byte_225_188_177;	// ἱ -> Ἱ -- GREEK SMALL LETTER IOTA WITH DASIA
									case -70: return byte_225_188_178;	// ἲ -> Ἲ -- GREEK SMALL LETTER IOTA WITH PSILI AND VARIA
									case -69: return byte_225_188_179;	// ἳ -> Ἳ -- GREEK SMALL LETTER IOTA WITH DASIA AND VARIA
									case -68: return byte_225_188_180;	// ἴ -> Ἴ -- GREEK SMALL LETTER IOTA WITH PSILI AND OXIA
									case -67: return byte_225_188_181;	// ἵ -> Ἵ -- GREEK SMALL LETTER IOTA WITH DASIA AND OXIA
									case -66: return byte_225_188_182;	// ἶ -> Ἶ -- GREEK SMALL LETTER IOTA WITH PSILI AND PERISPOMENI
									case -65: return byte_225_188_183;	// ἷ -> Ἷ -- GREEK SMALL LETTER IOTA WITH DASIA AND PERISPOMENI
								}
								break;
							case -67:
								switch (b3) {
									case -120: return byte_225_189_128;	// ὀ -> Ὀ -- GREEK SMALL LETTER OMICRON WITH PSILI
									case -119: return byte_225_189_129;	// ὁ -> Ὁ -- GREEK SMALL LETTER OMICRON WITH DASIA
									case -118: return byte_225_189_130;	// ὂ -> Ὂ -- GREEK SMALL LETTER OMICRON WITH PSILI AND VARIA
									case -117: return byte_225_189_131;	// ὃ -> Ὃ -- GREEK SMALL LETTER OMICRON WITH DASIA AND VARIA
									case -116: return byte_225_189_132;	// ὄ -> Ὄ -- GREEK SMALL LETTER OMICRON WITH PSILI AND OXIA
									case -115: return byte_225_189_133;	// ὅ -> Ὅ -- GREEK SMALL LETTER OMICRON WITH DASIA AND OXIA
									case -103: return byte_225_189_145;	// ὑ -> Ὑ -- GREEK SMALL LETTER UPSILON WITH DASIA
									case -101: return byte_225_189_147;	// ὓ -> Ὓ -- GREEK SMALL LETTER UPSILON WITH DASIA AND VARIA
									case -99: return byte_225_189_149;	// ὕ -> Ὕ -- GREEK SMALL LETTER UPSILON WITH DASIA AND OXIA
									case -97: return byte_225_189_151;	// ὗ -> Ὗ -- GREEK SMALL LETTER UPSILON WITH DASIA AND PERISPOMENI
									case -88: return byte_225_189_160;	// ὠ -> Ὠ -- GREEK SMALL LETTER OMEGA WITH PSILI
									case -87: return byte_225_189_161;	// ὡ -> Ὡ -- GREEK SMALL LETTER OMEGA WITH DASIA
									case -86: return byte_225_189_162;	// ὢ -> Ὢ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA
									case -85: return byte_225_189_163;	// ὣ -> Ὣ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA
									case -84: return byte_225_189_164;	// ὤ -> Ὤ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA
									case -83: return byte_225_189_165;	// ὥ -> Ὥ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA
									case -82: return byte_225_189_166;	// ὦ -> Ὦ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI
									case -81: return byte_225_189_167;	// ὧ -> Ὧ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI
								}
								break;
							case -66:
								switch (b3) {
									case -120: return byte_225_190_128;	// ᾀ -> ᾈ -- GREEK SMALL LETTER ALPHA WITH PSILI AND YPOGEGRAMMENI
									case -119: return byte_225_190_129;	// ᾁ -> ᾉ -- GREEK SMALL LETTER ALPHA WITH DASIA AND YPOGEGRAMMENI
									case -118: return byte_225_190_130;	// ᾂ -> ᾊ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -117: return byte_225_190_131;	// ᾃ -> ᾋ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -116: return byte_225_190_132;	// ᾄ -> ᾌ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -115: return byte_225_190_133;	// ᾅ -> ᾍ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -114: return byte_225_190_134;	// ᾆ -> ᾎ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -113: return byte_225_190_135;	// ᾇ -> ᾏ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -104: return byte_225_190_144;	// ᾐ -> ᾘ -- GREEK SMALL LETTER ETA WITH PSILI AND YPOGEGRAMMENI
									case -103: return byte_225_190_145;	// ᾑ -> ᾙ -- GREEK SMALL LETTER ETA WITH DASIA AND YPOGEGRAMMENI
									case -102: return byte_225_190_146;	// ᾒ -> ᾚ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -101: return byte_225_190_147;	// ᾓ -> ᾛ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -100: return byte_225_190_148;	// ᾔ -> ᾜ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -99: return byte_225_190_149;	// ᾕ -> ᾝ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -98: return byte_225_190_150;	// ᾖ -> ᾞ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -97: return byte_225_190_151;	// ᾗ -> ᾟ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -88: return byte_225_190_160;	// ᾠ -> ᾨ -- GREEK SMALL LETTER OMEGA WITH PSILI AND YPOGEGRAMMENI
									case -87: return byte_225_190_161;	// ᾡ -> ᾩ -- GREEK SMALL LETTER OMEGA WITH DASIA AND YPOGEGRAMMENI
									case -86: return byte_225_190_162;	// ᾢ -> ᾪ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA AND YPOGEGRAMMENI
									case -85: return byte_225_190_163;	// ᾣ -> ᾫ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA AND YPOGEGRAMMENI
									case -84: return byte_225_190_164;	// ᾤ -> ᾬ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA AND YPOGEGRAMMENI
									case -83: return byte_225_190_165;	// ᾥ -> ᾭ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA AND YPOGEGRAMMENI
									case -82: return byte_225_190_166;	// ᾦ -> ᾮ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
									case -81: return byte_225_190_167;	// ᾧ -> ᾯ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
									case -72: return byte_225_190_176;	// ᾰ -> Ᾰ -- GREEK SMALL LETTER ALPHA WITH VRACHY
									case -71: return byte_225_190_177;	// ᾱ -> Ᾱ -- GREEK SMALL LETTER ALPHA WITH MACRON
									case -70: return byte_225_189_176;	// ὰ -> Ὰ -- GREEK SMALL LETTER ALPHA WITH VARIA
									case -69: return byte_225_189_177;	// ά -> Ά -- GREEK SMALL LETTER ALPHA WITH OXIA
									case -68: return byte_225_190_179;	// ᾳ -> ᾼ -- GREEK SMALL LETTER ALPHA WITH YPOGEGRAMMENI
								}
								break;
							case -65:
								switch (b3) {
									case -120: return byte_225_189_178;	// ὲ -> Ὲ -- GREEK SMALL LETTER EPSILON WITH VARIA
									case -119: return byte_225_189_179;	// έ -> Έ -- GREEK SMALL LETTER EPSILON WITH OXIA
									case -118: return byte_225_189_180;	// ὴ -> Ὴ -- GREEK SMALL LETTER ETA WITH VARIA
									case -117: return byte_225_189_181;	// ή -> Ή -- GREEK SMALL LETTER ETA WITH OXIA
									case -116: return byte_225_191_131;	// ῃ -> ῌ -- GREEK SMALL LETTER ETA WITH YPOGEGRAMMENI
									case -104: return byte_225_191_144;	// ῐ -> Ῐ -- GREEK SMALL LETTER IOTA WITH VRACHY
									case -103: return byte_225_191_145;	// ῑ -> Ῑ -- GREEK SMALL LETTER IOTA WITH MACRON
									case -102: return byte_225_189_182;	// ὶ -> Ὶ -- GREEK SMALL LETTER IOTA WITH VARIA
									case -101: return byte_225_189_183;	// ί -> Ί -- GREEK SMALL LETTER IOTA WITH OXIA
									case -88: return byte_225_191_160;	// ῠ -> Ῠ -- GREEK SMALL LETTER UPSILON WITH VRACHY
									case -87: return byte_225_191_161;	// ῡ -> Ῡ -- GREEK SMALL LETTER UPSILON WITH MACRON
									case -86: return byte_225_189_186;	// ὺ -> Ὺ -- GREEK SMALL LETTER UPSILON WITH VARIA
									case -85: return byte_225_189_187;	// ύ -> Ύ -- GREEK SMALL LETTER UPSILON WITH OXIA
									case -84: return byte_225_191_165;	// ῥ -> Ῥ -- GREEK SMALL LETTER RHO WITH DASIA
									case -72: return byte_225_189_184;	// ὸ -> Ὸ -- GREEK SMALL LETTER OMICRON WITH VARIA
									case -71: return byte_225_189_185;	// ό -> Ό -- GREEK SMALL LETTER OMICRON WITH OXIA
									case -70: return byte_225_189_188;	// ὼ -> Ὼ -- GREEK SMALL LETTER OMEGA WITH VARIA
									case -69: return byte_225_189_189;	// ώ -> Ώ -- GREEK SMALL LETTER OMEGA WITH OXIA
									case -68: return byte_225_191_179;	// ῳ -> ῼ -- GREEK SMALL LETTER OMEGA WITH YPOGEGRAMMENI
								}
						}
						break;
					case -30:
						switch (b2) {
							case -124:
								switch (b3) {
									case -90: return byte_207_137;	// Ω -> ω -- OHM
									case -86: return byte_107;	// K -> k -- DEGREES KELVIN
									case -85: return byte_195_165;	// Å -> å -- ANGSTROM UNIT
									case -78: return byte_226_133_142;	// ⅎ -> Ⅎ -- TURNED F
								}
								break;
							case -123:
								switch (b3) {
									case -96: return byte_226_133_176;	// ⅰ -> Ⅰ -- ROMAN NUMERAL ONE
									case -95: return byte_226_133_177;	// ⅱ -> Ⅱ -- ROMAN NUMERAL TWO
									case -94: return byte_226_133_178;	// ⅲ -> Ⅲ -- ROMAN NUMERAL THREE
									case -93: return byte_226_133_179;	// ⅳ -> Ⅳ -- ROMAN NUMERAL FOUR
									case -92: return byte_226_133_180;	// ⅴ -> Ⅴ -- ROMAN NUMERAL FIVE
									case -91: return byte_226_133_181;	// ⅵ -> Ⅵ -- ROMAN NUMERAL SIX
									case -90: return byte_226_133_182;	// ⅶ -> Ⅶ -- ROMAN NUMERAL SEVEN
									case -89: return byte_226_133_183;	// ⅷ -> Ⅷ -- ROMAN NUMERAL EIGHT
									case -88: return byte_226_133_184;	// ⅸ -> Ⅸ -- ROMAN NUMERAL NINE
									case -87: return byte_226_133_185;	// ⅹ -> Ⅹ -- ROMAN NUMERAL TEN
									case -86: return byte_226_133_186;	// ⅺ -> Ⅺ -- ROMAN NUMERAL ELEVEN
									case -85: return byte_226_133_187;	// ⅻ -> Ⅻ -- ROMAN NUMERAL TWELVE
									case -84: return byte_226_133_188;	// ⅼ -> Ⅼ -- ROMAN NUMERAL FIFTY
									case -83: return byte_226_133_189;	// ⅽ -> Ⅽ -- ROMAN NUMERAL ONE HUNDRED
									case -82: return byte_226_133_190;	// ⅾ -> Ⅾ -- ROMAN NUMERAL FIVE HUNDRED
									case -81: return byte_226_133_191;	// ⅿ -> Ⅿ -- ROMAN NUMERAL ONE THOUSAND
								}
								break;
							case -122:
								if (b3 == -125) return byte_226_134_132;	// ↄ -> Ↄ -- ROMAN NUMERAL REVERSED ONE HUNDRED
								break;
							case -110:
								switch (b3) {
									case -74: return byte_226_147_144;	// ⓐ -> Ⓐ -- CIRCLED LATIN CAPITAL LETTER A
									case -73: return byte_226_147_145;	// ⓑ -> Ⓑ -- CIRCLED LATIN CAPITAL LETTER B
									case -72: return byte_226_147_146;	// ⓒ -> Ⓒ -- CIRCLED LATIN CAPITAL LETTER C
									case -71: return byte_226_147_147;	// ⓓ -> Ⓓ -- CIRCLED LATIN CAPITAL LETTER D
									case -70: return byte_226_147_148;	// ⓔ -> Ⓔ -- CIRCLED LATIN CAPITAL LETTER E
									case -69: return byte_226_147_149;	// ⓕ -> Ⓕ -- CIRCLED LATIN CAPITAL LETTER F
									case -68: return byte_226_147_150;	// ⓖ -> Ⓖ -- CIRCLED LATIN CAPITAL LETTER G
									case -67: return byte_226_147_151;	// ⓗ -> Ⓗ -- CIRCLED LATIN CAPITAL LETTER H
									case -66: return byte_226_147_152;	// ⓘ -> Ⓘ -- CIRCLED LATIN CAPITAL LETTER I
									case -65: return byte_226_147_153;	// ⓙ -> Ⓙ -- CIRCLED LATIN CAPITAL LETTER J
								}
								break;
							case -109:
								switch (b3) {
									case -128: return byte_226_147_154;	// ⓚ -> Ⓚ -- CIRCLED LATIN CAPITAL LETTER K
									case -127: return byte_226_147_155;	// ⓛ -> Ⓛ -- CIRCLED LATIN CAPITAL LETTER L
									case -126: return byte_226_147_156;	// ⓜ -> Ⓜ -- CIRCLED LATIN CAPITAL LETTER M
									case -125: return byte_226_147_157;	// ⓝ -> Ⓝ -- CIRCLED LATIN CAPITAL LETTER N
									case -124: return byte_226_147_158;	// ⓞ -> Ⓞ -- CIRCLED LATIN CAPITAL LETTER O
									case -123: return byte_226_147_159;	// ⓟ -> Ⓟ -- CIRCLED LATIN CAPITAL LETTER P
									case -122: return byte_226_147_160;	// ⓠ -> Ⓠ -- CIRCLED LATIN CAPITAL LETTER Q
									case -121: return byte_226_147_161;	// ⓡ -> Ⓡ -- CIRCLED LATIN CAPITAL LETTER R
									case -120: return byte_226_147_162;	// ⓢ -> Ⓢ -- CIRCLED LATIN CAPITAL LETTER S
									case -119: return byte_226_147_163;	// ⓣ -> Ⓣ -- CIRCLED LATIN CAPITAL LETTER T
									case -118: return byte_226_147_164;	// ⓤ -> Ⓤ -- CIRCLED LATIN CAPITAL LETTER U
									case -117: return byte_226_147_165;	// ⓥ -> Ⓥ -- CIRCLED LATIN CAPITAL LETTER V
									case -116: return byte_226_147_166;	// ⓦ -> Ⓦ -- CIRCLED LATIN CAPITAL LETTER W
									case -115: return byte_226_147_167;	// ⓧ -> Ⓧ -- CIRCLED LATIN CAPITAL LETTER X
									case -114: return byte_226_147_168;	// ⓨ -> Ⓨ -- CIRCLED LATIN CAPITAL LETTER Y
									case -113: return byte_226_147_169;	// ⓩ -> Ⓩ -- CIRCLED LATIN CAPITAL LETTER Z
								}
								break;
							case -80:
								switch (b3) {
									case -128: return byte_226_176_176;	// ⰰ -> Ⰰ -- GLAGOLITIC CAPITAL LETTER AZU
									case -127: return byte_226_176_177;	// ⰱ -> Ⰱ -- GLAGOLITIC CAPITAL LETTER BUKY
									case -126: return byte_226_176_178;	// ⰲ -> Ⰲ -- GLAGOLITIC CAPITAL LETTER VEDE
									case -125: return byte_226_176_179;	// ⰳ -> Ⰳ -- GLAGOLITIC CAPITAL LETTER GLAGOLI
									case -124: return byte_226_176_180;	// ⰴ -> Ⰴ -- GLAGOLITIC CAPITAL LETTER DOBRO
									case -123: return byte_226_176_181;	// ⰵ -> Ⰵ -- GLAGOLITIC CAPITAL LETTER YESTU
									case -122: return byte_226_176_182;	// ⰶ -> Ⰶ -- GLAGOLITIC CAPITAL LETTER ZHIVETE
									case -121: return byte_226_176_183;	// ⰷ -> Ⰷ -- GLAGOLITIC CAPITAL LETTER DZELO
									case -120: return byte_226_176_184;	// ⰸ -> Ⰸ -- GLAGOLITIC CAPITAL LETTER ZEMLJA
									case -119: return byte_226_176_185;	// ⰹ -> Ⰹ -- GLAGOLITIC CAPITAL LETTER IZHE
									case -118: return byte_226_176_186;	// ⰺ -> Ⰺ -- GLAGOLITIC CAPITAL LETTER INITIAL IZHE
									case -117: return byte_226_176_187;	// ⰻ -> Ⰻ -- GLAGOLITIC CAPITAL LETTER I
									case -116: return byte_226_176_188;	// ⰼ -> Ⰼ -- GLAGOLITIC CAPITAL LETTER DJERVI
									case -115: return byte_226_176_189;	// ⰽ -> Ⰽ -- GLAGOLITIC CAPITAL LETTER KAKO
									case -114: return byte_226_176_190;	// ⰾ -> Ⰾ -- GLAGOLITIC CAPITAL LETTER LJUDIJE
									case -113: return byte_226_176_191;	// ⰿ -> Ⰿ -- GLAGOLITIC CAPITAL LETTER MYSLITE
									case -112: return byte_226_177_128;	// ⱀ -> Ⱀ -- GLAGOLITIC CAPITAL LETTER NASHI
									case -111: return byte_226_177_129;	// ⱁ -> Ⱁ -- GLAGOLITIC CAPITAL LETTER ONU
									case -110: return byte_226_177_130;	// ⱂ -> Ⱂ -- GLAGOLITIC CAPITAL LETTER POKOJI
									case -109: return byte_226_177_131;	// ⱃ -> Ⱃ -- GLAGOLITIC CAPITAL LETTER RITSI
									case -108: return byte_226_177_132;	// ⱄ -> Ⱄ -- GLAGOLITIC CAPITAL LETTER SLOVO
									case -107: return byte_226_177_133;	// ⱅ -> Ⱅ -- GLAGOLITIC CAPITAL LETTER TVRIDO
									case -106: return byte_226_177_134;	// ⱆ -> Ⱆ -- GLAGOLITIC CAPITAL LETTER UKU
									case -105: return byte_226_177_135;	// ⱇ -> Ⱇ -- GLAGOLITIC CAPITAL LETTER FRITU
									case -104: return byte_226_177_136;	// ⱈ -> Ⱈ -- GLAGOLITIC CAPITAL LETTER HERU
									case -103: return byte_226_177_137;	// ⱉ -> Ⱉ -- GLAGOLITIC CAPITAL LETTER OTU
									case -102: return byte_226_177_138;	// ⱊ -> Ⱊ -- GLAGOLITIC CAPITAL LETTER PE
									case -101: return byte_226_177_139;	// ⱋ -> Ⱋ -- GLAGOLITIC CAPITAL LETTER SHTA
									case -100: return byte_226_177_140;	// ⱌ -> Ⱌ -- GLAGOLITIC CAPITAL LETTER TSI
									case -99: return byte_226_177_141;	// ⱍ -> Ⱍ -- GLAGOLITIC CAPITAL LETTER CHRIVI
									case -98: return byte_226_177_142;	// ⱎ -> Ⱎ -- GLAGOLITIC CAPITAL LETTER SHA
									case -97: return byte_226_177_143;	// ⱏ -> Ⱏ -- GLAGOLITIC CAPITAL LETTER YERU
									case -96: return byte_226_177_144;	// ⱐ -> Ⱐ -- GLAGOLITIC CAPITAL LETTER YERI
									case -95: return byte_226_177_145;	// ⱑ -> Ⱑ -- GLAGOLITIC CAPITAL LETTER YATI
									case -94: return byte_226_177_146;	// ⱒ -> Ⱒ -- GLAGOLITIC CAPITAL LETTER SPIDERY HA
									case -93: return byte_226_177_147;	// ⱓ -> Ⱓ -- GLAGOLITIC CAPITAL LETTER YU
									case -92: return byte_226_177_148;	// ⱔ -> Ⱔ -- GLAGOLITIC CAPITAL LETTER SMALL YUS
									case -91: return byte_226_177_149;	// ⱕ -> Ⱕ -- GLAGOLITIC CAPITAL LETTER SMALL YUS WITH TAIL
									case -90: return byte_226_177_150;	// ⱖ -> Ⱖ -- GLAGOLITIC CAPITAL LETTER YO
									case -89: return byte_226_177_151;	// ⱗ -> Ⱗ -- GLAGOLITIC CAPITAL LETTER IOTATED SMALL YUS
									case -88: return byte_226_177_152;	// ⱘ -> Ⱘ -- GLAGOLITIC CAPITAL LETTER BIG YUS
									case -87: return byte_226_177_153;	// ⱙ -> Ⱙ -- GLAGOLITIC CAPITAL LETTER IOTATED BIG YUS
									case -86: return byte_226_177_154;	// ⱚ -> Ⱚ -- GLAGOLITIC CAPITAL LETTER FITA
									case -85: return byte_226_177_155;	// ⱛ -> Ⱛ -- GLAGOLITIC CAPITAL LETTER IZHITSA
									case -84: return byte_226_177_156;	// ⱜ -> Ⱜ -- GLAGOLITIC CAPITAL LETTER SHTAPIC
									case -83: return byte_226_177_157;	// ⱝ -> Ⱝ -- GLAGOLITIC CAPITAL LETTER TROKUTASTI A
									case -82: return byte_226_177_158;	// ⱞ -> Ⱞ -- GLAGOLITIC CAPITAL LETTER LATINATE MYSLITE
								}
								break;
							case -79:
								switch (b3) {
									case -96: return byte_226_177_161;	// ⱡ -> Ⱡ -- LATIN CAPITAL LETTER L WITH DOUBLE BAR
									case -94: return byte_201_171;	// ɫ -> Ɫ -- LATIN SMALL LETTER L WITH MIDDLE TILDE
									case -93: return byte_225_181_189;	// ᵽ -> Ᵽ -- LATIN SMALL LETTER P WITH STROKE
									case -92: return byte_201_189;	// ɽ -> Ɽ -- LATIN SMALL LETTER R HOOK
									case -89: return byte_226_177_168;	// ⱨ -> Ⱨ -- LATIN CAPITAL LETTER H WITH DESCENDER
									case -87: return byte_226_177_170;	// ⱪ -> Ⱪ -- LATIN CAPITAL LETTER K WITH DESCENDER
									case -85: return byte_226_177_172;	// ⱬ -> Ⱬ -- LATIN CAPITAL LETTER Z WITH DESCENDER
									case -83: return byte_201_145;	// ɑ -> Ɑ -- LATIN SMALL LETTER SCRIPT A
									case -82: return byte_201_177;	// ɱ -> Ɱ -- LATIN SMALL LETTER M HOOK
									case -81: return byte_201_144;	// ɐ -> Ɐ -- LATIN SMALL LETTER TURNED A
									case -80: return byte_201_146;	// ɒ -> Ɒ -- LATIN SMALL LETTER TURNED SCRIPT A
									case -78: return byte_226_177_179;	// ⱳ -> Ⱳ -- LATIN CAPITAL LETTER W WITH HOOK
									case -75: return byte_226_177_182;	// ⱶ -> Ⱶ -- LATIN CAPITAL LETTER HALF H
									case -66: return byte_200_191;	// ȿ -> Ȿ -- LATIN SMALL LETTER S WITH SWASH TAIL
									case -65: return byte_201_128;	// ɀ -> Ɀ -- LATIN SMALL LETTER Z WITH SWASH TAIL
								}
								break;
							case -78:
								switch (b3) {
									case -128: return byte_226_178_129;	// ⲁ -> Ⲁ -- COPTIC CAPITAL LETTER ALFA
									case -126: return byte_226_178_131;	// ⲃ -> Ⲃ -- COPTIC CAPITAL LETTER VIDA
									case -124: return byte_226_178_133;	// ⲅ -> Ⲅ -- COPTIC CAPITAL LETTER GAMMA
									case -122: return byte_226_178_135;	// ⲇ -> Ⲇ -- COPTIC CAPITAL LETTER DALDA
									case -120: return byte_226_178_137;	// ⲉ -> Ⲉ -- COPTIC CAPITAL LETTER EIE
									case -118: return byte_226_178_139;	// ⲋ -> Ⲋ -- COPTIC CAPITAL LETTER SOU
									case -116: return byte_226_178_141;	// ⲍ -> Ⲍ -- COPTIC CAPITAL LETTER ZATA
									case -114: return byte_226_178_143;	// ⲏ -> Ⲏ -- COPTIC CAPITAL LETTER HATE
									case -112: return byte_226_178_145;	// ⲑ -> Ⲑ -- COPTIC CAPITAL LETTER THETHE
									case -110: return byte_226_178_147;	// ⲓ -> Ⲓ -- COPTIC CAPITAL LETTER IAUDA
									case -108: return byte_226_178_149;	// ⲕ -> Ⲕ -- COPTIC CAPITAL LETTER KAPA
									case -106: return byte_226_178_151;	// ⲗ -> Ⲗ -- COPTIC CAPITAL LETTER LAULA
									case -104: return byte_226_178_153;	// ⲙ -> Ⲙ -- COPTIC CAPITAL LETTER MI
									case -102: return byte_226_178_155;	// ⲛ -> Ⲛ -- COPTIC CAPITAL LETTER NI
									case -100: return byte_226_178_157;	// ⲝ -> Ⲝ -- COPTIC CAPITAL LETTER KSI
									case -98: return byte_226_178_159;	// ⲟ -> Ⲟ -- COPTIC CAPITAL LETTER O
									case -96: return byte_226_178_161;	// ⲡ -> Ⲡ -- COPTIC CAPITAL LETTER PI
									case -94: return byte_226_178_163;	// ⲣ -> Ⲣ -- COPTIC CAPITAL LETTER RO
									case -92: return byte_226_178_165;	// ⲥ -> Ⲥ -- COPTIC CAPITAL LETTER SIMA
									case -90: return byte_226_178_167;	// ⲧ -> Ⲧ -- COPTIC CAPITAL LETTER TAU
									case -88: return byte_226_178_169;	// ⲩ -> Ⲩ -- COPTIC CAPITAL LETTER UA
									case -86: return byte_226_178_171;	// ⲫ -> Ⲫ -- COPTIC CAPITAL LETTER FI
									case -84: return byte_226_178_173;	// ⲭ -> Ⲭ -- COPTIC CAPITAL LETTER KHI
									case -82: return byte_226_178_175;	// ⲯ -> Ⲯ -- COPTIC CAPITAL LETTER PSI
									case -80: return byte_226_178_177;	// ⲱ -> Ⲱ -- COPTIC CAPITAL LETTER OOU
									case -78: return byte_226_178_179;	// ⲳ -> Ⲳ -- COPTIC CAPITAL LETTER DIALECT-P ALEF
									case -76: return byte_226_178_181;	// ⲵ -> Ⲵ -- COPTIC CAPITAL LETTER OLD COPTIC AIN
									case -74: return byte_226_178_183;	// ⲷ -> Ⲷ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC EIE
									case -72: return byte_226_178_185;	// ⲹ -> Ⲹ -- COPTIC CAPITAL LETTER DIALECT-P KAPA
									case -70: return byte_226_178_187;	// ⲻ -> Ⲻ -- COPTIC CAPITAL LETTER DIALECT-P NI
									case -68: return byte_226_178_189;	// ⲽ -> Ⲽ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC NI
									case -66: return byte_226_178_191;	// ⲿ -> Ⲿ -- COPTIC CAPITAL LETTER OLD COPTIC OOU
								}
								break;
							case -77:
								switch (b3) {
									case -128: return byte_226_179_129;	// ⳁ -> Ⳁ -- COPTIC CAPITAL LETTER SAMPI
									case -126: return byte_226_179_131;	// ⳃ -> Ⳃ -- COPTIC CAPITAL LETTER CROSSED SHEI
									case -124: return byte_226_179_133;	// ⳅ -> Ⳅ -- COPTIC CAPITAL LETTER OLD COPTIC SHEI
									case -122: return byte_226_179_135;	// ⳇ -> Ⳇ -- COPTIC CAPITAL LETTER OLD COPTIC ESH
									case -120: return byte_226_179_137;	// ⳉ -> Ⳉ -- COPTIC CAPITAL LETTER AKHMIMIC KHEI
									case -118: return byte_226_179_139;	// ⳋ -> Ⳋ -- COPTIC CAPITAL LETTER DIALECT-P HORI
									case -116: return byte_226_179_141;	// ⳍ -> Ⳍ -- COPTIC CAPITAL LETTER OLD COPTIC HORI
									case -114: return byte_226_179_143;	// ⳏ -> Ⳏ -- COPTIC CAPITAL LETTER OLD COPTIC HA
									case -112: return byte_226_179_145;	// ⳑ -> Ⳑ -- COPTIC CAPITAL LETTER L-SHAPED HA
									case -110: return byte_226_179_147;	// ⳓ -> Ⳓ -- COPTIC CAPITAL LETTER OLD COPTIC HEI
									case -108: return byte_226_179_149;	// ⳕ -> Ⳕ -- COPTIC CAPITAL LETTER OLD COPTIC HAT
									case -106: return byte_226_179_151;	// ⳗ -> Ⳗ -- COPTIC CAPITAL LETTER OLD COPTIC GANGIA
									case -104: return byte_226_179_153;	// ⳙ -> Ⳙ -- COPTIC CAPITAL LETTER OLD COPTIC DJA
									case -102: return byte_226_179_155;	// ⳛ -> Ⳛ -- COPTIC CAPITAL LETTER OLD COPTIC SHIMA
									case -100: return byte_226_179_157;	// ⳝ -> Ⳝ -- COPTIC CAPITAL LETTER OLD NUBIAN SHIMA
									case -98: return byte_226_179_159;	// ⳟ -> Ⳟ -- COPTIC CAPITAL LETTER OLD NUBIAN NGI
									case -96: return byte_226_179_161;	// ⳡ -> Ⳡ -- COPTIC CAPITAL LETTER OLD NUBIAN NYI
									case -94: return byte_226_179_163;	// ⳣ -> Ⳣ -- COPTIC CAPITAL LETTER OLD NUBIAN WAU
									case -85: return byte_226_179_172;	// ⳬ -> Ⳬ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC SHEI
									case -83: return byte_226_179_174;	// ⳮ -> Ⳮ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC GANGIA
									case -78: return byte_226_179_179;	// ⳳ -> Ⳳ -- COPTIC CAPITAL LETTER BOHAIRIC KHEI
								}
						}
						break;
					case -22:
						switch (b2) {
							case -103:
								switch (b3) {
									case -128: return byte_234_153_129;	// ꙁ -> Ꙁ -- CYRILLIC CAPITAL LETTER ZEMLYA
									case -126: return byte_234_153_131;	// ꙃ -> Ꙃ -- CYRILLIC CAPITAL LETTER DZELO
									case -124: return byte_234_153_133;	// ꙅ -> Ꙅ -- CYRILLIC CAPITAL LETTER REVERSED DZE
									case -122: return byte_234_153_135;	// ꙇ -> Ꙇ -- CYRILLIC CAPITAL LETTER IOTA
									case -120: return byte_234_153_137;	// ꙉ -> Ꙉ -- CYRILLIC CAPITAL LETTER DJERV
									case -118: return byte_234_153_139;	// ꙋ -> Ꙋ -- CYRILLIC CAPITAL LETTER MONOGRAPH UK
									case -116: return byte_234_153_141;	// ꙍ -> Ꙍ -- CYRILLIC CAPITAL LETTER BROAD OMEGA
									case -114: return byte_234_153_143;	// ꙏ -> Ꙏ -- CYRILLIC CAPITAL LETTER NEUTRAL YER
									case -112: return byte_234_153_145;	// ꙑ -> Ꙑ -- CYRILLIC CAPITAL LETTER YERU WITH BACK YER
									case -110: return byte_234_153_147;	// ꙓ -> Ꙓ -- CYRILLIC CAPITAL LETTER IOTIFIED YAT
									case -108: return byte_234_153_149;	// ꙕ -> Ꙕ -- CYRILLIC CAPITAL LETTER REVERSED YU
									case -106: return byte_234_153_151;	// ꙗ -> Ꙗ -- CYRILLIC CAPITAL LETTER IOTIFIED A
									case -104: return byte_234_153_153;	// ꙙ -> Ꙙ -- CYRILLIC CAPITAL LETTER CLOSED LITTLE YUS
									case -102: return byte_234_153_155;	// ꙛ -> Ꙛ -- CYRILLIC CAPITAL LETTER BLENDED YUS
									case -100: return byte_234_153_157;	// ꙝ -> Ꙝ -- CYRILLIC CAPITAL LETTER IOTIFIED CLOSED LITTLE YUS
									case -98: return byte_234_153_159;	// ꙟ -> Ꙟ -- CYRILLIC CAPITAL LETTER YN
									case -96: return byte_234_153_161;	// ꙡ -> Ꙡ -- CYRILLIC CAPITAL LETTER REVERSED TSE
									case -94: return byte_234_153_163;	// ꙣ -> Ꙣ -- CYRILLIC CAPITAL LETTER SOFT DE
									case -92: return byte_234_153_165;	// ꙥ -> Ꙥ -- CYRILLIC CAPITAL LETTER SOFT EL
									case -90: return byte_234_153_167;	// ꙧ -> Ꙧ -- CYRILLIC CAPITAL LETTER SOFT EM
									case -88: return byte_234_153_169;	// ꙩ -> Ꙩ -- CYRILLIC CAPITAL LETTER MONOCULAR O
									case -86: return byte_234_153_171;	// ꙫ -> Ꙫ -- CYRILLIC CAPITAL LETTER BINOCULAR O
									case -84: return byte_234_153_173;	// ꙭ -> Ꙭ -- CYRILLIC CAPITAL LETTER DOUBLE MONOCULAR O
								}
								break;
							case -102:
								switch (b3) {
									case -128: return byte_234_154_129;	// ꚁ -> Ꚁ -- CYRILLIC CAPITAL LETTER DWE
									case -126: return byte_234_154_131;	// ꚃ -> Ꚃ -- CYRILLIC CAPITAL LETTER DZWE
									case -124: return byte_234_154_133;	// ꚅ -> Ꚅ -- CYRILLIC CAPITAL LETTER ZHWE
									case -122: return byte_234_154_135;	// ꚇ -> Ꚇ -- CYRILLIC CAPITAL LETTER CCHE
									case -120: return byte_234_154_137;	// ꚉ -> Ꚉ -- CYRILLIC CAPITAL LETTER DZZE
									case -118: return byte_234_154_139;	// ꚋ -> Ꚋ -- CYRILLIC CAPITAL LETTER TE WITH MIDDLE HOOK
									case -116: return byte_234_154_141;	// ꚍ -> Ꚍ -- CYRILLIC CAPITAL LETTER TWE
									case -114: return byte_234_154_143;	// ꚏ -> Ꚏ -- CYRILLIC CAPITAL LETTER TSWE
									case -112: return byte_234_154_145;	// ꚑ -> Ꚑ -- CYRILLIC CAPITAL LETTER TSSE
									case -110: return byte_234_154_147;	// ꚓ -> Ꚓ -- CYRILLIC CAPITAL LETTER TCHE
									case -108: return byte_234_154_149;	// ꚕ -> Ꚕ -- CYRILLIC CAPITAL LETTER HWE
									case -106: return byte_234_154_151;	// ꚗ -> Ꚗ -- CYRILLIC CAPITAL LETTER SHWE
								}
								break;
							case -100:
								switch (b3) {
									case -94: return byte_234_156_163;	// ꜣ -> Ꜣ -- LATIN CAPITAL LETTER EGYPTOLOGICAL ALEF
									case -92: return byte_234_156_165;	// ꜥ -> Ꜥ -- LATIN CAPITAL LETTER EGYPTOLOGICAL AIN
									case -90: return byte_234_156_167;	// ꜧ -> Ꜧ -- LATIN CAPITAL LETTER HENG
									case -88: return byte_234_156_169;	// ꜩ -> Ꜩ -- LATIN CAPITAL LETTER TZ
									case -86: return byte_234_156_171;	// ꜫ -> Ꜫ -- LATIN CAPITAL LETTER TRESILLO
									case -84: return byte_234_156_173;	// ꜭ -> Ꜭ -- LATIN CAPITAL LETTER CUATRILLO
									case -82: return byte_234_156_175;	// ꜯ -> Ꜯ -- LATIN CAPITAL LETTER CUATRILLO WITH COMMA
									case -78: return byte_234_156_179;	// ꜳ -> Ꜳ -- LATIN CAPITAL LETTER AA
									case -76: return byte_234_156_181;	// ꜵ -> Ꜵ -- LATIN CAPITAL LETTER AO
									case -74: return byte_234_156_183;	// ꜷ -> Ꜷ -- LATIN CAPITAL LETTER AU
									case -72: return byte_234_156_185;	// ꜹ -> Ꜹ -- LATIN CAPITAL LETTER AV
									case -70: return byte_234_156_187;	// ꜻ -> Ꜻ -- LATIN CAPITAL LETTER AV WITH HORIZONTAL BAR
									case -68: return byte_234_156_189;	// ꜽ -> Ꜽ -- LATIN CAPITAL LETTER AY
									case -66: return byte_234_156_191;	// ꜿ -> Ꜿ -- LATIN CAPITAL LETTER REVERSED C WITH DOT
								}
								break;
							case -99:
								switch (b3) {
									case -128: return byte_234_157_129;	// ꝁ -> Ꝁ -- LATIN CAPITAL LETTER K WITH STROKE
									case -126: return byte_234_157_131;	// ꝃ -> Ꝃ -- LATIN CAPITAL LETTER K WITH DIAGONAL STROKE
									case -124: return byte_234_157_133;	// ꝅ -> Ꝅ -- LATIN CAPITAL LETTER K WITH STROKE AND DIAGONAL STROKE
									case -122: return byte_234_157_135;	// ꝇ -> Ꝇ -- LATIN CAPITAL LETTER BROKEN L
									case -120: return byte_234_157_137;	// ꝉ -> Ꝉ -- LATIN CAPITAL LETTER L WITH HIGH STROKE
									case -118: return byte_234_157_139;	// ꝋ -> Ꝋ -- LATIN CAPITAL LETTER O WITH LONG STROKE OVERLAY
									case -116: return byte_234_157_141;	// ꝍ -> Ꝍ -- LATIN CAPITAL LETTER O WITH LOOP
									case -114: return byte_234_157_143;	// ꝏ -> Ꝏ -- LATIN CAPITAL LETTER OO
									case -112: return byte_234_157_145;	// ꝑ -> Ꝑ -- LATIN CAPITAL LETTER P WITH STROKE THROUGH DESCENDER
									case -110: return byte_234_157_147;	// ꝓ -> Ꝓ -- LATIN CAPITAL LETTER P WITH FLOURISH
									case -108: return byte_234_157_149;	// ꝕ -> Ꝕ -- LATIN CAPITAL LETTER P WITH SQUIRREL TAIL
									case -106: return byte_234_157_151;	// ꝗ -> Ꝗ -- LATIN CAPITAL LETTER Q WITH STROKE THROUGH DESCENDER
									case -104: return byte_234_157_153;	// ꝙ -> Ꝙ -- LATIN CAPITAL LETTER Q WITH DIAGONAL STROKE
									case -102: return byte_234_157_155;	// ꝛ -> Ꝛ -- LATIN CAPITAL LETTER R ROTUNDA
									case -100: return byte_234_157_157;	// ꝝ -> Ꝝ -- LATIN CAPITAL LETTER RUM ROTUNDA
									case -98: return byte_234_157_159;	// ꝟ -> Ꝟ -- LATIN CAPITAL LETTER V WITH DIAGONAL STROKE
									case -96: return byte_234_157_161;	// ꝡ -> Ꝡ -- LATIN CAPITAL LETTER VY
									case -94: return byte_234_157_163;	// ꝣ -> Ꝣ -- LATIN CAPITAL LETTER VISIGOTHIC Z
									case -92: return byte_234_157_165;	// ꝥ -> Ꝥ -- LATIN CAPITAL LETTER THORN WITH STROKE
									case -90: return byte_234_157_167;	// ꝧ -> Ꝧ -- LATIN CAPITAL LETTER THORN WITH STROKE THROUGH DESCENDER
									case -88: return byte_234_157_169;	// ꝩ -> Ꝩ -- LATIN CAPITAL LETTER VEND
									case -86: return byte_234_157_171;	// ꝫ -> Ꝫ -- LATIN CAPITAL LETTER ET
									case -84: return byte_234_157_173;	// ꝭ -> Ꝭ -- LATIN CAPITAL LETTER IS
									case -82: return byte_234_157_175;	// ꝯ -> Ꝯ -- LATIN CAPITAL LETTER CON
									case -71: return byte_234_157_186;	// ꝺ -> Ꝺ -- LATIN CAPITAL LETTER INSULAR D
									case -69: return byte_234_157_188;	// ꝼ -> Ꝼ -- LATIN CAPITAL LETTER INSULAR F
									case -67: return byte_225_181_185;	// ᵹ -> Ᵹ -- LATIN SMALL LETTER INSULAR G
									case -66: return byte_234_157_191;	// ꝿ -> Ꝿ -- LATIN CAPITAL LETTER TURNED INSULAR G
								}
								break;
							case -98:
								switch (b3) {
									case -128: return byte_234_158_129;	// ꞁ -> Ꞁ -- LATIN CAPITAL LETTER TURNED L
									case -126: return byte_234_158_131;	// ꞃ -> Ꞃ -- LATIN CAPITAL LETTER INSULAR R
									case -124: return byte_234_158_133;	// ꞅ -> Ꞅ -- LATIN CAPITAL LETTER INSULAR S
									case -122: return byte_234_158_135;	// ꞇ -> Ꞇ -- LATIN CAPITAL LETTER INSULAR T
									case -117: return byte_234_158_140;	// ꞌ -> Ꞌ -- LATIN CAPITAL LETTER SALTILLO
									case -115: return byte_201_165;	// ɥ -> Ɥ -- LATIN SMALL LETTER TURNED H
									case -112: return byte_234_158_145;	// ꞑ -> Ꞑ -- LATIN CAPITAL LETTER N WITH DESCENDER
									case -110: return byte_234_158_147;	// ꞓ -> Ꞓ -- LATIN CAPITAL LETTER C WITH BAR
									case -96: return byte_234_158_161;	// ꞡ -> Ꞡ -- LATIN CAPITAL LETTER G WITH OBLIQUE STROKE
									case -94: return byte_234_158_163;	// ꞣ -> Ꞣ -- LATIN CAPITAL LETTER K WITH OBLIQUE STROKE
									case -92: return byte_234_158_165;	// ꞥ -> Ꞥ -- LATIN CAPITAL LETTER N WITH OBLIQUE STROKE
									case -90: return byte_234_158_167;	// ꞧ -> Ꞧ -- LATIN CAPITAL LETTER R WITH OBLIQUE STROKE
									case -88: return byte_234_158_169;	// ꞩ -> Ꞩ -- LATIN CAPITAL LETTER S WITH OBLIQUE STROKE
									case -86: return byte_201_166;	// ɦ -> Ɦ -- LATIN SMALL LETTER H HOOK
								}
						}
						break;
					case -17:
						switch (b2) {
							case -68:
								switch (b3) {
									case -95: return byte_239_189_129;	// ａ -> Ａ -- FULLWIDTH LATIN CAPITAL LETTER A
									case -94: return byte_239_189_130;	// ｂ -> Ｂ -- FULLWIDTH LATIN CAPITAL LETTER B
									case -93: return byte_239_189_131;	// ｃ -> Ｃ -- FULLWIDTH LATIN CAPITAL LETTER C
									case -92: return byte_239_189_132;	// ｄ -> Ｄ -- FULLWIDTH LATIN CAPITAL LETTER D
									case -91: return byte_239_189_133;	// ｅ -> Ｅ -- FULLWIDTH LATIN CAPITAL LETTER E
									case -90: return byte_239_189_134;	// ｆ -> Ｆ -- FULLWIDTH LATIN CAPITAL LETTER F
									case -89: return byte_239_189_135;	// ｇ -> Ｇ -- FULLWIDTH LATIN CAPITAL LETTER G
									case -88: return byte_239_189_136;	// ｈ -> Ｈ -- FULLWIDTH LATIN CAPITAL LETTER H
									case -87: return byte_239_189_137;	// ｉ -> Ｉ -- FULLWIDTH LATIN CAPITAL LETTER I
									case -86: return byte_239_189_138;	// ｊ -> Ｊ -- FULLWIDTH LATIN CAPITAL LETTER J
									case -85: return byte_239_189_139;	// ｋ -> Ｋ -- FULLWIDTH LATIN CAPITAL LETTER K
									case -84: return byte_239_189_140;	// ｌ -> Ｌ -- FULLWIDTH LATIN CAPITAL LETTER L
									case -83: return byte_239_189_141;	// ｍ -> Ｍ -- FULLWIDTH LATIN CAPITAL LETTER M
									case -82: return byte_239_189_142;	// ｎ -> Ｎ -- FULLWIDTH LATIN CAPITAL LETTER N
									case -81: return byte_239_189_143;	// ｏ -> Ｏ -- FULLWIDTH LATIN CAPITAL LETTER O
									case -80: return byte_239_189_144;	// ｐ -> Ｐ -- FULLWIDTH LATIN CAPITAL LETTER P
									case -79: return byte_239_189_145;	// ｑ -> Ｑ -- FULLWIDTH LATIN CAPITAL LETTER Q
									case -78: return byte_239_189_146;	// ｒ -> Ｒ -- FULLWIDTH LATIN CAPITAL LETTER R
									case -77: return byte_239_189_147;	// ｓ -> Ｓ -- FULLWIDTH LATIN CAPITAL LETTER S
									case -76: return byte_239_189_148;	// ｔ -> Ｔ -- FULLWIDTH LATIN CAPITAL LETTER T
									case -75: return byte_239_189_149;	// ｕ -> Ｕ -- FULLWIDTH LATIN CAPITAL LETTER U
									case -74: return byte_239_189_150;	// ｖ -> Ｖ -- FULLWIDTH LATIN CAPITAL LETTER V
									case -73: return byte_239_189_151;	// ｗ -> Ｗ -- FULLWIDTH LATIN CAPITAL LETTER W
									case -72: return byte_239_189_152;	// ｘ -> Ｘ -- FULLWIDTH LATIN CAPITAL LETTER X
									case -71: return byte_239_189_153;	// ｙ -> Ｙ -- FULLWIDTH LATIN CAPITAL LETTER Y
									case -70: return byte_239_189_154;	// ｚ -> Ｚ -- FULLWIDTH LATIN CAPITAL LETTER Z
								}
						}
				}
				break;
			case 4:
				b1 = src[pos++];
				b2 = src[pos++];
				b3 = src[pos++];
				b4 = src[pos++];
				switch (b1) {
					case -16:
						switch (b2) {
							case -112:
								switch (b3) {
									case -112:
										switch (b4) {
											case -128: return byte_240_144_144_168;	// 𐐨 -> 𐐀 -- DESERET CAPITAL LETTER LONG I
											case -127: return byte_240_144_144_169;	// 𐐩 -> 𐐁 -- DESERET CAPITAL LETTER LONG E
											case -126: return byte_240_144_144_170;	// 𐐪 -> 𐐂 -- DESERET CAPITAL LETTER LONG A
											case -125: return byte_240_144_144_171;	// 𐐫 -> 𐐃 -- DESERET CAPITAL LETTER LONG AH
											case -124: return byte_240_144_144_172;	// 𐐬 -> 𐐄 -- DESERET CAPITAL LETTER LONG O
											case -123: return byte_240_144_144_173;	// 𐐭 -> 𐐅 -- DESERET CAPITAL LETTER LONG OO
											case -122: return byte_240_144_144_174;	// 𐐮 -> 𐐆 -- DESERET CAPITAL LETTER SHORT I
											case -121: return byte_240_144_144_175;	// 𐐯 -> 𐐇 -- DESERET CAPITAL LETTER SHORT E
											case -120: return byte_240_144_144_176;	// 𐐰 -> 𐐈 -- DESERET CAPITAL LETTER SHORT A
											case -119: return byte_240_144_144_177;	// 𐐱 -> 𐐉 -- DESERET CAPITAL LETTER SHORT AH
											case -118: return byte_240_144_144_178;	// 𐐲 -> 𐐊 -- DESERET CAPITAL LETTER SHORT O
											case -117: return byte_240_144_144_179;	// 𐐳 -> 𐐋 -- DESERET CAPITAL LETTER SHORT OO
											case -116: return byte_240_144_144_180;	// 𐐴 -> 𐐌 -- DESERET CAPITAL LETTER AY
											case -115: return byte_240_144_144_181;	// 𐐵 -> 𐐍 -- DESERET CAPITAL LETTER OW
											case -114: return byte_240_144_144_182;	// 𐐶 -> 𐐎 -- DESERET CAPITAL LETTER WU
											case -113: return byte_240_144_144_183;	// 𐐷 -> 𐐏 -- DESERET CAPITAL LETTER YEE
											case -112: return byte_240_144_144_184;	// 𐐸 -> 𐐐 -- DESERET CAPITAL LETTER H
											case -111: return byte_240_144_144_185;	// 𐐹 -> 𐐑 -- DESERET CAPITAL LETTER PEE
											case -110: return byte_240_144_144_186;	// 𐐺 -> 𐐒 -- DESERET CAPITAL LETTER BEE
											case -109: return byte_240_144_144_187;	// 𐐻 -> 𐐓 -- DESERET CAPITAL LETTER TEE
											case -108: return byte_240_144_144_188;	// 𐐼 -> 𐐔 -- DESERET CAPITAL LETTER DEE
											case -107: return byte_240_144_144_189;	// 𐐽 -> 𐐕 -- DESERET CAPITAL LETTER CHEE
											case -106: return byte_240_144_144_190;	// 𐐾 -> 𐐖 -- DESERET CAPITAL LETTER JEE
											case -105: return byte_240_144_144_191;	// 𐐿 -> 𐐗 -- DESERET CAPITAL LETTER KAY
											case -104: return byte_240_144_145_128;	// 𐑀 -> 𐐘 -- DESERET CAPITAL LETTER GAY
											case -103: return byte_240_144_145_129;	// 𐑁 -> 𐐙 -- DESERET CAPITAL LETTER EF
											case -102: return byte_240_144_145_130;	// 𐑂 -> 𐐚 -- DESERET CAPITAL LETTER VEE
											case -101: return byte_240_144_145_131;	// 𐑃 -> 𐐛 -- DESERET CAPITAL LETTER ETH
											case -100: return byte_240_144_145_132;	// 𐑄 -> 𐐜 -- DESERET CAPITAL LETTER THEE
											case -99: return byte_240_144_145_133;	// 𐑅 -> 𐐝 -- DESERET CAPITAL LETTER ES
											case -98: return byte_240_144_145_134;	// 𐑆 -> 𐐞 -- DESERET CAPITAL LETTER ZEE
											case -97: return byte_240_144_145_135;	// 𐑇 -> 𐐟 -- DESERET CAPITAL LETTER ESH
											case -96: return byte_240_144_145_136;	// 𐑈 -> 𐐠 -- DESERET CAPITAL LETTER ZHEE
											case -95: return byte_240_144_145_137;	// 𐑉 -> 𐐡 -- DESERET CAPITAL LETTER ER
											case -94: return byte_240_144_145_138;	// 𐑊 -> 𐐢 -- DESERET CAPITAL LETTER EL
											case -93: return byte_240_144_145_139;	// 𐑋 -> 𐐣 -- DESERET CAPITAL LETTER EM
											case -92: return byte_240_144_145_140;	// 𐑌 -> 𐐤 -- DESERET CAPITAL LETTER EN
											case -91: return byte_240_144_145_141;	// 𐑍 -> 𐐥 -- DESERET CAPITAL LETTER ENG
											case -90: return byte_240_144_145_142;	// 𐑎 -> 𐐦 -- DESERET CAPITAL LETTER OI
											case -89: return byte_240_144_145_143;	// 𐑏 -> 𐐧 -- DESERET CAPITAL LETTER EW
										}
								}
						}
				}
				break;
		}

		return byte_NOCHANGE;
	}


	private static final byte[]
		  byte_65 = Bry_.New_by_ints(65)
		, byte_66 = Bry_.New_by_ints(66)
		, byte_67 = Bry_.New_by_ints(67)
		, byte_68 = Bry_.New_by_ints(68)
		, byte_69 = Bry_.New_by_ints(69)
		, byte_70 = Bry_.New_by_ints(70)
		, byte_71 = Bry_.New_by_ints(71)
		, byte_72 = Bry_.New_by_ints(72)
		, byte_73 = Bry_.New_by_ints(73)
		, byte_74 = Bry_.New_by_ints(74)
		, byte_75 = Bry_.New_by_ints(75)
		, byte_76 = Bry_.New_by_ints(76)
		, byte_77 = Bry_.New_by_ints(77)
		, byte_78 = Bry_.New_by_ints(78)
		, byte_79 = Bry_.New_by_ints(79)
		, byte_80 = Bry_.New_by_ints(80)
		, byte_81 = Bry_.New_by_ints(81)
		, byte_82 = Bry_.New_by_ints(82)
		, byte_83 = Bry_.New_by_ints(83)
		, byte_84 = Bry_.New_by_ints(84)
		, byte_85 = Bry_.New_by_ints(85)
		, byte_86 = Bry_.New_by_ints(86)
		, byte_87 = Bry_.New_by_ints(87)
		, byte_88 = Bry_.New_by_ints(88)
		, byte_89 = Bry_.New_by_ints(89)
		, byte_90 = Bry_.New_by_ints(90)
		, byte_206_156 = Bry_.New_by_ints(206,156)
		, byte_195_128 = Bry_.New_by_ints(195,128)
		, byte_195_129 = Bry_.New_by_ints(195,129)
		, byte_195_130 = Bry_.New_by_ints(195,130)
		, byte_195_131 = Bry_.New_by_ints(195,131)
		, byte_195_132 = Bry_.New_by_ints(195,132)
		, byte_195_133 = Bry_.New_by_ints(195,133)
		, byte_195_134 = Bry_.New_by_ints(195,134)
		, byte_195_135 = Bry_.New_by_ints(195,135)
		, byte_195_136 = Bry_.New_by_ints(195,136)
		, byte_195_137 = Bry_.New_by_ints(195,137)
		, byte_195_138 = Bry_.New_by_ints(195,138)
		, byte_195_139 = Bry_.New_by_ints(195,139)
		, byte_195_140 = Bry_.New_by_ints(195,140)
		, byte_195_141 = Bry_.New_by_ints(195,141)
		, byte_195_142 = Bry_.New_by_ints(195,142)
		, byte_195_143 = Bry_.New_by_ints(195,143)
		, byte_195_144 = Bry_.New_by_ints(195,144)
		, byte_195_145 = Bry_.New_by_ints(195,145)
		, byte_195_146 = Bry_.New_by_ints(195,146)
		, byte_195_147 = Bry_.New_by_ints(195,147)
		, byte_195_148 = Bry_.New_by_ints(195,148)
		, byte_195_149 = Bry_.New_by_ints(195,149)
		, byte_195_150 = Bry_.New_by_ints(195,150)
		, byte_195_152 = Bry_.New_by_ints(195,152)
		, byte_195_153 = Bry_.New_by_ints(195,153)
		, byte_195_154 = Bry_.New_by_ints(195,154)
		, byte_195_155 = Bry_.New_by_ints(195,155)
		, byte_195_156 = Bry_.New_by_ints(195,156)
		, byte_195_157 = Bry_.New_by_ints(195,157)
		, byte_195_158 = Bry_.New_by_ints(195,158)
		, byte_197_184 = Bry_.New_by_ints(197,184)
		, byte_196_128 = Bry_.New_by_ints(196,128)
		, byte_196_130 = Bry_.New_by_ints(196,130)
		, byte_196_132 = Bry_.New_by_ints(196,132)
		, byte_196_134 = Bry_.New_by_ints(196,134)
		, byte_196_136 = Bry_.New_by_ints(196,136)
		, byte_196_138 = Bry_.New_by_ints(196,138)
		, byte_196_140 = Bry_.New_by_ints(196,140)
		, byte_196_142 = Bry_.New_by_ints(196,142)
		, byte_196_144 = Bry_.New_by_ints(196,144)
		, byte_196_146 = Bry_.New_by_ints(196,146)
		, byte_196_148 = Bry_.New_by_ints(196,148)
		, byte_196_150 = Bry_.New_by_ints(196,150)
		, byte_196_152 = Bry_.New_by_ints(196,152)
		, byte_196_154 = Bry_.New_by_ints(196,154)
		, byte_196_156 = Bry_.New_by_ints(196,156)
		, byte_196_158 = Bry_.New_by_ints(196,158)
		, byte_196_160 = Bry_.New_by_ints(196,160)
		, byte_196_162 = Bry_.New_by_ints(196,162)
		, byte_196_164 = Bry_.New_by_ints(196,164)
		, byte_196_166 = Bry_.New_by_ints(196,166)
		, byte_196_168 = Bry_.New_by_ints(196,168)
		, byte_196_170 = Bry_.New_by_ints(196,170)
		, byte_196_172 = Bry_.New_by_ints(196,172)
		, byte_196_174 = Bry_.New_by_ints(196,174)
		, byte_196_178 = Bry_.New_by_ints(196,178)
		, byte_196_180 = Bry_.New_by_ints(196,180)
		, byte_196_182 = Bry_.New_by_ints(196,182)
		, byte_196_185 = Bry_.New_by_ints(196,185)
		, byte_196_187 = Bry_.New_by_ints(196,187)
		, byte_196_189 = Bry_.New_by_ints(196,189)
		, byte_196_191 = Bry_.New_by_ints(196,191)
		, byte_197_129 = Bry_.New_by_ints(197,129)
		, byte_197_131 = Bry_.New_by_ints(197,131)
		, byte_197_133 = Bry_.New_by_ints(197,133)
		, byte_197_135 = Bry_.New_by_ints(197,135)
		, byte_197_138 = Bry_.New_by_ints(197,138)
		, byte_197_140 = Bry_.New_by_ints(197,140)
		, byte_197_142 = Bry_.New_by_ints(197,142)
		, byte_197_144 = Bry_.New_by_ints(197,144)
		, byte_197_146 = Bry_.New_by_ints(197,146)
		, byte_197_148 = Bry_.New_by_ints(197,148)
		, byte_197_150 = Bry_.New_by_ints(197,150)
		, byte_197_152 = Bry_.New_by_ints(197,152)
		, byte_197_154 = Bry_.New_by_ints(197,154)
		, byte_197_156 = Bry_.New_by_ints(197,156)
		, byte_197_158 = Bry_.New_by_ints(197,158)
		, byte_197_160 = Bry_.New_by_ints(197,160)
		, byte_197_162 = Bry_.New_by_ints(197,162)
		, byte_197_164 = Bry_.New_by_ints(197,164)
		, byte_197_166 = Bry_.New_by_ints(197,166)
		, byte_197_168 = Bry_.New_by_ints(197,168)
		, byte_197_170 = Bry_.New_by_ints(197,170)
		, byte_197_172 = Bry_.New_by_ints(197,172)
		, byte_197_174 = Bry_.New_by_ints(197,174)
		, byte_197_176 = Bry_.New_by_ints(197,176)
		, byte_197_178 = Bry_.New_by_ints(197,178)
		, byte_197_180 = Bry_.New_by_ints(197,180)
		, byte_197_182 = Bry_.New_by_ints(197,182)
		, byte_197_185 = Bry_.New_by_ints(197,185)
		, byte_197_187 = Bry_.New_by_ints(197,187)
		, byte_197_189 = Bry_.New_by_ints(197,189)
		, byte_201_131 = Bry_.New_by_ints(201,131)
		, byte_198_130 = Bry_.New_by_ints(198,130)
		, byte_198_132 = Bry_.New_by_ints(198,132)
		, byte_198_135 = Bry_.New_by_ints(198,135)
		, byte_198_139 = Bry_.New_by_ints(198,139)
		, byte_198_145 = Bry_.New_by_ints(198,145)
		, byte_199_182 = Bry_.New_by_ints(199,182)
		, byte_198_152 = Bry_.New_by_ints(198,152)
		, byte_200_189 = Bry_.New_by_ints(200,189)
		, byte_200_160 = Bry_.New_by_ints(200,160)
		, byte_198_160 = Bry_.New_by_ints(198,160)
		, byte_198_162 = Bry_.New_by_ints(198,162)
		, byte_198_164 = Bry_.New_by_ints(198,164)
		, byte_198_167 = Bry_.New_by_ints(198,167)
		, byte_198_172 = Bry_.New_by_ints(198,172)
		, byte_198_175 = Bry_.New_by_ints(198,175)
		, byte_198_179 = Bry_.New_by_ints(198,179)
		, byte_198_181 = Bry_.New_by_ints(198,181)
		, byte_198_184 = Bry_.New_by_ints(198,184)
		, byte_198_188 = Bry_.New_by_ints(198,188)
		, byte_199_183 = Bry_.New_by_ints(199,183)
		, byte_199_132 = Bry_.New_by_ints(199,132)
		, byte_199_135 = Bry_.New_by_ints(199,135)
		, byte_199_138 = Bry_.New_by_ints(199,138)
		, byte_199_141 = Bry_.New_by_ints(199,141)
		, byte_199_143 = Bry_.New_by_ints(199,143)
		, byte_199_145 = Bry_.New_by_ints(199,145)
		, byte_199_147 = Bry_.New_by_ints(199,147)
		, byte_199_149 = Bry_.New_by_ints(199,149)
		, byte_199_151 = Bry_.New_by_ints(199,151)
		, byte_199_153 = Bry_.New_by_ints(199,153)
		, byte_199_155 = Bry_.New_by_ints(199,155)
		, byte_198_142 = Bry_.New_by_ints(198,142)
		, byte_199_158 = Bry_.New_by_ints(199,158)
		, byte_199_160 = Bry_.New_by_ints(199,160)
		, byte_199_162 = Bry_.New_by_ints(199,162)
		, byte_199_164 = Bry_.New_by_ints(199,164)
		, byte_199_166 = Bry_.New_by_ints(199,166)
		, byte_199_168 = Bry_.New_by_ints(199,168)
		, byte_199_170 = Bry_.New_by_ints(199,170)
		, byte_199_172 = Bry_.New_by_ints(199,172)
		, byte_199_174 = Bry_.New_by_ints(199,174)
		, byte_199_177 = Bry_.New_by_ints(199,177)
		, byte_199_180 = Bry_.New_by_ints(199,180)
		, byte_199_184 = Bry_.New_by_ints(199,184)
		, byte_199_186 = Bry_.New_by_ints(199,186)
		, byte_199_188 = Bry_.New_by_ints(199,188)
		, byte_199_190 = Bry_.New_by_ints(199,190)
		, byte_200_128 = Bry_.New_by_ints(200,128)
		, byte_200_130 = Bry_.New_by_ints(200,130)
		, byte_200_132 = Bry_.New_by_ints(200,132)
		, byte_200_134 = Bry_.New_by_ints(200,134)
		, byte_200_136 = Bry_.New_by_ints(200,136)
		, byte_200_138 = Bry_.New_by_ints(200,138)
		, byte_200_140 = Bry_.New_by_ints(200,140)
		, byte_200_142 = Bry_.New_by_ints(200,142)
		, byte_200_144 = Bry_.New_by_ints(200,144)
		, byte_200_146 = Bry_.New_by_ints(200,146)
		, byte_200_148 = Bry_.New_by_ints(200,148)
		, byte_200_150 = Bry_.New_by_ints(200,150)
		, byte_200_152 = Bry_.New_by_ints(200,152)
		, byte_200_154 = Bry_.New_by_ints(200,154)
		, byte_200_156 = Bry_.New_by_ints(200,156)
		, byte_200_158 = Bry_.New_by_ints(200,158)
		, byte_200_162 = Bry_.New_by_ints(200,162)
		, byte_200_164 = Bry_.New_by_ints(200,164)
		, byte_200_166 = Bry_.New_by_ints(200,166)
		, byte_200_168 = Bry_.New_by_ints(200,168)
		, byte_200_170 = Bry_.New_by_ints(200,170)
		, byte_200_172 = Bry_.New_by_ints(200,172)
		, byte_200_174 = Bry_.New_by_ints(200,174)
		, byte_200_176 = Bry_.New_by_ints(200,176)
		, byte_200_178 = Bry_.New_by_ints(200,178)
		, byte_200_187 = Bry_.New_by_ints(200,187)
		, byte_226_177_190 = Bry_.New_by_ints(226,177,190)
		, byte_226_177_191 = Bry_.New_by_ints(226,177,191)
		, byte_201_129 = Bry_.New_by_ints(201,129)
		, byte_201_134 = Bry_.New_by_ints(201,134)
		, byte_201_136 = Bry_.New_by_ints(201,136)
		, byte_201_138 = Bry_.New_by_ints(201,138)
		, byte_201_140 = Bry_.New_by_ints(201,140)
		, byte_201_142 = Bry_.New_by_ints(201,142)
		, byte_226_177_175 = Bry_.New_by_ints(226,177,175)
		, byte_226_177_173 = Bry_.New_by_ints(226,177,173)
		, byte_226_177_176 = Bry_.New_by_ints(226,177,176)
		, byte_198_129 = Bry_.New_by_ints(198,129)
		, byte_198_134 = Bry_.New_by_ints(198,134)
		, byte_198_137 = Bry_.New_by_ints(198,137)
		, byte_198_138 = Bry_.New_by_ints(198,138)
		, byte_198_143 = Bry_.New_by_ints(198,143)
		, byte_198_144 = Bry_.New_by_ints(198,144)
		, byte_198_147 = Bry_.New_by_ints(198,147)
		, byte_198_148 = Bry_.New_by_ints(198,148)
		, byte_234_158_141 = Bry_.New_by_ints(234,158,141)
		, byte_234_158_170 = Bry_.New_by_ints(234,158,170)
		, byte_198_151 = Bry_.New_by_ints(198,151)
		, byte_198_150 = Bry_.New_by_ints(198,150)
		, byte_226_177_162 = Bry_.New_by_ints(226,177,162)
		, byte_198_156 = Bry_.New_by_ints(198,156)
		, byte_226_177_174 = Bry_.New_by_ints(226,177,174)
		, byte_198_157 = Bry_.New_by_ints(198,157)
		, byte_198_159 = Bry_.New_by_ints(198,159)
		, byte_226_177_164 = Bry_.New_by_ints(226,177,164)
		, byte_198_166 = Bry_.New_by_ints(198,166)
		, byte_198_169 = Bry_.New_by_ints(198,169)
		, byte_198_174 = Bry_.New_by_ints(198,174)
		, byte_201_132 = Bry_.New_by_ints(201,132)
		, byte_198_177 = Bry_.New_by_ints(198,177)
		, byte_198_178 = Bry_.New_by_ints(198,178)
		, byte_201_133 = Bry_.New_by_ints(201,133)
		, byte_198_183 = Bry_.New_by_ints(198,183)
		, byte_206_153 = Bry_.New_by_ints(206,153)
		, byte_205_176 = Bry_.New_by_ints(205,176)
		, byte_205_178 = Bry_.New_by_ints(205,178)
		, byte_205_182 = Bry_.New_by_ints(205,182)
		, byte_207_189 = Bry_.New_by_ints(207,189)
		, byte_207_190 = Bry_.New_by_ints(207,190)
		, byte_207_191 = Bry_.New_by_ints(207,191)
		, byte_206_134 = Bry_.New_by_ints(206,134)
		, byte_206_136 = Bry_.New_by_ints(206,136)
		, byte_206_137 = Bry_.New_by_ints(206,137)
		, byte_206_138 = Bry_.New_by_ints(206,138)
		, byte_206_145 = Bry_.New_by_ints(206,145)
		, byte_206_146 = Bry_.New_by_ints(206,146)
		, byte_206_147 = Bry_.New_by_ints(206,147)
		, byte_206_148 = Bry_.New_by_ints(206,148)
		, byte_206_149 = Bry_.New_by_ints(206,149)
		, byte_206_150 = Bry_.New_by_ints(206,150)
		, byte_206_151 = Bry_.New_by_ints(206,151)
		, byte_206_152 = Bry_.New_by_ints(206,152)
		, byte_206_154 = Bry_.New_by_ints(206,154)
		, byte_206_155 = Bry_.New_by_ints(206,155)
		, byte_206_157 = Bry_.New_by_ints(206,157)
		, byte_206_158 = Bry_.New_by_ints(206,158)
		, byte_206_159 = Bry_.New_by_ints(206,159)
		, byte_206_160 = Bry_.New_by_ints(206,160)
		, byte_206_161 = Bry_.New_by_ints(206,161)
		, byte_206_163 = Bry_.New_by_ints(206,163)
		, byte_206_164 = Bry_.New_by_ints(206,164)
		, byte_206_165 = Bry_.New_by_ints(206,165)
		, byte_206_166 = Bry_.New_by_ints(206,166)
		, byte_206_167 = Bry_.New_by_ints(206,167)
		, byte_206_168 = Bry_.New_by_ints(206,168)
		, byte_206_169 = Bry_.New_by_ints(206,169)
		, byte_206_170 = Bry_.New_by_ints(206,170)
		, byte_206_171 = Bry_.New_by_ints(206,171)
		, byte_206_140 = Bry_.New_by_ints(206,140)
		, byte_206_142 = Bry_.New_by_ints(206,142)
		, byte_206_143 = Bry_.New_by_ints(206,143)
		, byte_207_143 = Bry_.New_by_ints(207,143)
		, byte_207_152 = Bry_.New_by_ints(207,152)
		, byte_207_154 = Bry_.New_by_ints(207,154)
		, byte_207_156 = Bry_.New_by_ints(207,156)
		, byte_207_158 = Bry_.New_by_ints(207,158)
		, byte_207_160 = Bry_.New_by_ints(207,160)
		, byte_207_162 = Bry_.New_by_ints(207,162)
		, byte_207_164 = Bry_.New_by_ints(207,164)
		, byte_207_166 = Bry_.New_by_ints(207,166)
		, byte_207_168 = Bry_.New_by_ints(207,168)
		, byte_207_170 = Bry_.New_by_ints(207,170)
		, byte_207_172 = Bry_.New_by_ints(207,172)
		, byte_207_174 = Bry_.New_by_ints(207,174)
		, byte_207_185 = Bry_.New_by_ints(207,185)
		, byte_207_183 = Bry_.New_by_ints(207,183)
		, byte_207_186 = Bry_.New_by_ints(207,186)
		, byte_208_144 = Bry_.New_by_ints(208,144)
		, byte_208_145 = Bry_.New_by_ints(208,145)
		, byte_208_146 = Bry_.New_by_ints(208,146)
		, byte_208_147 = Bry_.New_by_ints(208,147)
		, byte_208_148 = Bry_.New_by_ints(208,148)
		, byte_208_149 = Bry_.New_by_ints(208,149)
		, byte_208_150 = Bry_.New_by_ints(208,150)
		, byte_208_151 = Bry_.New_by_ints(208,151)
		, byte_208_152 = Bry_.New_by_ints(208,152)
		, byte_208_153 = Bry_.New_by_ints(208,153)
		, byte_208_154 = Bry_.New_by_ints(208,154)
		, byte_208_155 = Bry_.New_by_ints(208,155)
		, byte_208_156 = Bry_.New_by_ints(208,156)
		, byte_208_157 = Bry_.New_by_ints(208,157)
		, byte_208_158 = Bry_.New_by_ints(208,158)
		, byte_208_159 = Bry_.New_by_ints(208,159)
		, byte_208_160 = Bry_.New_by_ints(208,160)
		, byte_208_161 = Bry_.New_by_ints(208,161)
		, byte_208_162 = Bry_.New_by_ints(208,162)
		, byte_208_163 = Bry_.New_by_ints(208,163)
		, byte_208_164 = Bry_.New_by_ints(208,164)
		, byte_208_165 = Bry_.New_by_ints(208,165)
		, byte_208_166 = Bry_.New_by_ints(208,166)
		, byte_208_167 = Bry_.New_by_ints(208,167)
		, byte_208_168 = Bry_.New_by_ints(208,168)
		, byte_208_169 = Bry_.New_by_ints(208,169)
		, byte_208_170 = Bry_.New_by_ints(208,170)
		, byte_208_171 = Bry_.New_by_ints(208,171)
		, byte_208_172 = Bry_.New_by_ints(208,172)
		, byte_208_173 = Bry_.New_by_ints(208,173)
		, byte_208_174 = Bry_.New_by_ints(208,174)
		, byte_208_175 = Bry_.New_by_ints(208,175)
		, byte_208_128 = Bry_.New_by_ints(208,128)
		, byte_208_129 = Bry_.New_by_ints(208,129)
		, byte_208_130 = Bry_.New_by_ints(208,130)
		, byte_208_131 = Bry_.New_by_ints(208,131)
		, byte_208_132 = Bry_.New_by_ints(208,132)
		, byte_208_133 = Bry_.New_by_ints(208,133)
		, byte_208_134 = Bry_.New_by_ints(208,134)
		, byte_208_135 = Bry_.New_by_ints(208,135)
		, byte_208_136 = Bry_.New_by_ints(208,136)
		, byte_208_137 = Bry_.New_by_ints(208,137)
		, byte_208_138 = Bry_.New_by_ints(208,138)
		, byte_208_139 = Bry_.New_by_ints(208,139)
		, byte_208_140 = Bry_.New_by_ints(208,140)
		, byte_208_141 = Bry_.New_by_ints(208,141)
		, byte_208_142 = Bry_.New_by_ints(208,142)
		, byte_208_143 = Bry_.New_by_ints(208,143)
		, byte_209_160 = Bry_.New_by_ints(209,160)
		, byte_209_162 = Bry_.New_by_ints(209,162)
		, byte_209_164 = Bry_.New_by_ints(209,164)
		, byte_209_166 = Bry_.New_by_ints(209,166)
		, byte_209_168 = Bry_.New_by_ints(209,168)
		, byte_209_170 = Bry_.New_by_ints(209,170)
		, byte_209_172 = Bry_.New_by_ints(209,172)
		, byte_209_174 = Bry_.New_by_ints(209,174)
		, byte_209_176 = Bry_.New_by_ints(209,176)
		, byte_209_178 = Bry_.New_by_ints(209,178)
		, byte_209_180 = Bry_.New_by_ints(209,180)
		, byte_209_182 = Bry_.New_by_ints(209,182)
		, byte_209_184 = Bry_.New_by_ints(209,184)
		, byte_209_186 = Bry_.New_by_ints(209,186)
		, byte_209_188 = Bry_.New_by_ints(209,188)
		, byte_209_190 = Bry_.New_by_ints(209,190)
		, byte_210_128 = Bry_.New_by_ints(210,128)
		, byte_210_138 = Bry_.New_by_ints(210,138)
		, byte_210_140 = Bry_.New_by_ints(210,140)
		, byte_210_142 = Bry_.New_by_ints(210,142)
		, byte_210_144 = Bry_.New_by_ints(210,144)
		, byte_210_146 = Bry_.New_by_ints(210,146)
		, byte_210_148 = Bry_.New_by_ints(210,148)
		, byte_210_150 = Bry_.New_by_ints(210,150)
		, byte_210_152 = Bry_.New_by_ints(210,152)
		, byte_210_154 = Bry_.New_by_ints(210,154)
		, byte_210_156 = Bry_.New_by_ints(210,156)
		, byte_210_158 = Bry_.New_by_ints(210,158)
		, byte_210_160 = Bry_.New_by_ints(210,160)
		, byte_210_162 = Bry_.New_by_ints(210,162)
		, byte_210_164 = Bry_.New_by_ints(210,164)
		, byte_210_166 = Bry_.New_by_ints(210,166)
		, byte_210_168 = Bry_.New_by_ints(210,168)
		, byte_210_170 = Bry_.New_by_ints(210,170)
		, byte_210_172 = Bry_.New_by_ints(210,172)
		, byte_210_174 = Bry_.New_by_ints(210,174)
		, byte_210_176 = Bry_.New_by_ints(210,176)
		, byte_210_178 = Bry_.New_by_ints(210,178)
		, byte_210_180 = Bry_.New_by_ints(210,180)
		, byte_210_182 = Bry_.New_by_ints(210,182)
		, byte_210_184 = Bry_.New_by_ints(210,184)
		, byte_210_186 = Bry_.New_by_ints(210,186)
		, byte_210_188 = Bry_.New_by_ints(210,188)
		, byte_210_190 = Bry_.New_by_ints(210,190)
		, byte_211_129 = Bry_.New_by_ints(211,129)
		, byte_211_131 = Bry_.New_by_ints(211,131)
		, byte_211_133 = Bry_.New_by_ints(211,133)
		, byte_211_135 = Bry_.New_by_ints(211,135)
		, byte_211_137 = Bry_.New_by_ints(211,137)
		, byte_211_139 = Bry_.New_by_ints(211,139)
		, byte_211_141 = Bry_.New_by_ints(211,141)
		, byte_211_128 = Bry_.New_by_ints(211,128)
		, byte_211_144 = Bry_.New_by_ints(211,144)
		, byte_211_146 = Bry_.New_by_ints(211,146)
		, byte_211_148 = Bry_.New_by_ints(211,148)
		, byte_211_150 = Bry_.New_by_ints(211,150)
		, byte_211_152 = Bry_.New_by_ints(211,152)
		, byte_211_154 = Bry_.New_by_ints(211,154)
		, byte_211_156 = Bry_.New_by_ints(211,156)
		, byte_211_158 = Bry_.New_by_ints(211,158)
		, byte_211_160 = Bry_.New_by_ints(211,160)
		, byte_211_162 = Bry_.New_by_ints(211,162)
		, byte_211_164 = Bry_.New_by_ints(211,164)
		, byte_211_166 = Bry_.New_by_ints(211,166)
		, byte_211_168 = Bry_.New_by_ints(211,168)
		, byte_211_170 = Bry_.New_by_ints(211,170)
		, byte_211_172 = Bry_.New_by_ints(211,172)
		, byte_211_174 = Bry_.New_by_ints(211,174)
		, byte_211_176 = Bry_.New_by_ints(211,176)
		, byte_211_178 = Bry_.New_by_ints(211,178)
		, byte_211_180 = Bry_.New_by_ints(211,180)
		, byte_211_182 = Bry_.New_by_ints(211,182)
		, byte_211_184 = Bry_.New_by_ints(211,184)
		, byte_211_186 = Bry_.New_by_ints(211,186)
		, byte_211_188 = Bry_.New_by_ints(211,188)
		, byte_211_190 = Bry_.New_by_ints(211,190)
		, byte_212_128 = Bry_.New_by_ints(212,128)
		, byte_212_130 = Bry_.New_by_ints(212,130)
		, byte_212_132 = Bry_.New_by_ints(212,132)
		, byte_212_134 = Bry_.New_by_ints(212,134)
		, byte_212_136 = Bry_.New_by_ints(212,136)
		, byte_212_138 = Bry_.New_by_ints(212,138)
		, byte_212_140 = Bry_.New_by_ints(212,140)
		, byte_212_142 = Bry_.New_by_ints(212,142)
		, byte_212_144 = Bry_.New_by_ints(212,144)
		, byte_212_146 = Bry_.New_by_ints(212,146)
		, byte_212_148 = Bry_.New_by_ints(212,148)
		, byte_212_150 = Bry_.New_by_ints(212,150)
		, byte_212_152 = Bry_.New_by_ints(212,152)
		, byte_212_154 = Bry_.New_by_ints(212,154)
		, byte_212_156 = Bry_.New_by_ints(212,156)
		, byte_212_158 = Bry_.New_by_ints(212,158)
		, byte_212_160 = Bry_.New_by_ints(212,160)
		, byte_212_162 = Bry_.New_by_ints(212,162)
		, byte_212_164 = Bry_.New_by_ints(212,164)
		, byte_212_166 = Bry_.New_by_ints(212,166)
		, byte_212_177 = Bry_.New_by_ints(212,177)
		, byte_212_178 = Bry_.New_by_ints(212,178)
		, byte_212_179 = Bry_.New_by_ints(212,179)
		, byte_212_180 = Bry_.New_by_ints(212,180)
		, byte_212_181 = Bry_.New_by_ints(212,181)
		, byte_212_182 = Bry_.New_by_ints(212,182)
		, byte_212_183 = Bry_.New_by_ints(212,183)
		, byte_212_184 = Bry_.New_by_ints(212,184)
		, byte_212_185 = Bry_.New_by_ints(212,185)
		, byte_212_186 = Bry_.New_by_ints(212,186)
		, byte_212_187 = Bry_.New_by_ints(212,187)
		, byte_212_188 = Bry_.New_by_ints(212,188)
		, byte_212_189 = Bry_.New_by_ints(212,189)
		, byte_212_190 = Bry_.New_by_ints(212,190)
		, byte_212_191 = Bry_.New_by_ints(212,191)
		, byte_213_128 = Bry_.New_by_ints(213,128)
		, byte_213_129 = Bry_.New_by_ints(213,129)
		, byte_213_130 = Bry_.New_by_ints(213,130)
		, byte_213_131 = Bry_.New_by_ints(213,131)
		, byte_213_132 = Bry_.New_by_ints(213,132)
		, byte_213_133 = Bry_.New_by_ints(213,133)
		, byte_213_134 = Bry_.New_by_ints(213,134)
		, byte_213_135 = Bry_.New_by_ints(213,135)
		, byte_213_136 = Bry_.New_by_ints(213,136)
		, byte_213_137 = Bry_.New_by_ints(213,137)
		, byte_213_138 = Bry_.New_by_ints(213,138)
		, byte_213_139 = Bry_.New_by_ints(213,139)
		, byte_213_140 = Bry_.New_by_ints(213,140)
		, byte_213_141 = Bry_.New_by_ints(213,141)
		, byte_213_142 = Bry_.New_by_ints(213,142)
		, byte_213_143 = Bry_.New_by_ints(213,143)
		, byte_213_144 = Bry_.New_by_ints(213,144)
		, byte_213_145 = Bry_.New_by_ints(213,145)
		, byte_213_146 = Bry_.New_by_ints(213,146)
		, byte_213_147 = Bry_.New_by_ints(213,147)
		, byte_213_148 = Bry_.New_by_ints(213,148)
		, byte_213_149 = Bry_.New_by_ints(213,149)
		, byte_213_150 = Bry_.New_by_ints(213,150)
		, byte_234_157_189 = Bry_.New_by_ints(234,157,189)
		, byte_226_177_163 = Bry_.New_by_ints(226,177,163)
		, byte_225_184_128 = Bry_.New_by_ints(225,184,128)
		, byte_225_184_130 = Bry_.New_by_ints(225,184,130)
		, byte_225_184_132 = Bry_.New_by_ints(225,184,132)
		, byte_225_184_134 = Bry_.New_by_ints(225,184,134)
		, byte_225_184_136 = Bry_.New_by_ints(225,184,136)
		, byte_225_184_138 = Bry_.New_by_ints(225,184,138)
		, byte_225_184_140 = Bry_.New_by_ints(225,184,140)
		, byte_225_184_142 = Bry_.New_by_ints(225,184,142)
		, byte_225_184_144 = Bry_.New_by_ints(225,184,144)
		, byte_225_184_146 = Bry_.New_by_ints(225,184,146)
		, byte_225_184_148 = Bry_.New_by_ints(225,184,148)
		, byte_225_184_150 = Bry_.New_by_ints(225,184,150)
		, byte_225_184_152 = Bry_.New_by_ints(225,184,152)
		, byte_225_184_154 = Bry_.New_by_ints(225,184,154)
		, byte_225_184_156 = Bry_.New_by_ints(225,184,156)
		, byte_225_184_158 = Bry_.New_by_ints(225,184,158)
		, byte_225_184_160 = Bry_.New_by_ints(225,184,160)
		, byte_225_184_162 = Bry_.New_by_ints(225,184,162)
		, byte_225_184_164 = Bry_.New_by_ints(225,184,164)
		, byte_225_184_166 = Bry_.New_by_ints(225,184,166)
		, byte_225_184_168 = Bry_.New_by_ints(225,184,168)
		, byte_225_184_170 = Bry_.New_by_ints(225,184,170)
		, byte_225_184_172 = Bry_.New_by_ints(225,184,172)
		, byte_225_184_174 = Bry_.New_by_ints(225,184,174)
		, byte_225_184_176 = Bry_.New_by_ints(225,184,176)
		, byte_225_184_178 = Bry_.New_by_ints(225,184,178)
		, byte_225_184_180 = Bry_.New_by_ints(225,184,180)
		, byte_225_184_182 = Bry_.New_by_ints(225,184,182)
		, byte_225_184_184 = Bry_.New_by_ints(225,184,184)
		, byte_225_184_186 = Bry_.New_by_ints(225,184,186)
		, byte_225_184_188 = Bry_.New_by_ints(225,184,188)
		, byte_225_184_190 = Bry_.New_by_ints(225,184,190)
		, byte_225_185_128 = Bry_.New_by_ints(225,185,128)
		, byte_225_185_130 = Bry_.New_by_ints(225,185,130)
		, byte_225_185_132 = Bry_.New_by_ints(225,185,132)
		, byte_225_185_134 = Bry_.New_by_ints(225,185,134)
		, byte_225_185_136 = Bry_.New_by_ints(225,185,136)
		, byte_225_185_138 = Bry_.New_by_ints(225,185,138)
		, byte_225_185_140 = Bry_.New_by_ints(225,185,140)
		, byte_225_185_142 = Bry_.New_by_ints(225,185,142)
		, byte_225_185_144 = Bry_.New_by_ints(225,185,144)
		, byte_225_185_146 = Bry_.New_by_ints(225,185,146)
		, byte_225_185_148 = Bry_.New_by_ints(225,185,148)
		, byte_225_185_150 = Bry_.New_by_ints(225,185,150)
		, byte_225_185_152 = Bry_.New_by_ints(225,185,152)
		, byte_225_185_154 = Bry_.New_by_ints(225,185,154)
		, byte_225_185_156 = Bry_.New_by_ints(225,185,156)
		, byte_225_185_158 = Bry_.New_by_ints(225,185,158)
		, byte_225_185_160 = Bry_.New_by_ints(225,185,160)
		, byte_225_185_162 = Bry_.New_by_ints(225,185,162)
		, byte_225_185_164 = Bry_.New_by_ints(225,185,164)
		, byte_225_185_166 = Bry_.New_by_ints(225,185,166)
		, byte_225_185_168 = Bry_.New_by_ints(225,185,168)
		, byte_225_185_170 = Bry_.New_by_ints(225,185,170)
		, byte_225_185_172 = Bry_.New_by_ints(225,185,172)
		, byte_225_185_174 = Bry_.New_by_ints(225,185,174)
		, byte_225_185_176 = Bry_.New_by_ints(225,185,176)
		, byte_225_185_178 = Bry_.New_by_ints(225,185,178)
		, byte_225_185_180 = Bry_.New_by_ints(225,185,180)
		, byte_225_185_182 = Bry_.New_by_ints(225,185,182)
		, byte_225_185_184 = Bry_.New_by_ints(225,185,184)
		, byte_225_185_186 = Bry_.New_by_ints(225,185,186)
		, byte_225_185_188 = Bry_.New_by_ints(225,185,188)
		, byte_225_185_190 = Bry_.New_by_ints(225,185,190)
		, byte_225_186_128 = Bry_.New_by_ints(225,186,128)
		, byte_225_186_130 = Bry_.New_by_ints(225,186,130)
		, byte_225_186_132 = Bry_.New_by_ints(225,186,132)
		, byte_225_186_134 = Bry_.New_by_ints(225,186,134)
		, byte_225_186_136 = Bry_.New_by_ints(225,186,136)
		, byte_225_186_138 = Bry_.New_by_ints(225,186,138)
		, byte_225_186_140 = Bry_.New_by_ints(225,186,140)
		, byte_225_186_142 = Bry_.New_by_ints(225,186,142)
		, byte_225_186_144 = Bry_.New_by_ints(225,186,144)
		, byte_225_186_146 = Bry_.New_by_ints(225,186,146)
		, byte_225_186_148 = Bry_.New_by_ints(225,186,148)
		, byte_225_186_160 = Bry_.New_by_ints(225,186,160)
		, byte_225_186_162 = Bry_.New_by_ints(225,186,162)
		, byte_225_186_164 = Bry_.New_by_ints(225,186,164)
		, byte_225_186_166 = Bry_.New_by_ints(225,186,166)
		, byte_225_186_168 = Bry_.New_by_ints(225,186,168)
		, byte_225_186_170 = Bry_.New_by_ints(225,186,170)
		, byte_225_186_172 = Bry_.New_by_ints(225,186,172)
		, byte_225_186_174 = Bry_.New_by_ints(225,186,174)
		, byte_225_186_176 = Bry_.New_by_ints(225,186,176)
		, byte_225_186_178 = Bry_.New_by_ints(225,186,178)
		, byte_225_186_180 = Bry_.New_by_ints(225,186,180)
		, byte_225_186_182 = Bry_.New_by_ints(225,186,182)
		, byte_225_186_184 = Bry_.New_by_ints(225,186,184)
		, byte_225_186_186 = Bry_.New_by_ints(225,186,186)
		, byte_225_186_188 = Bry_.New_by_ints(225,186,188)
		, byte_225_186_190 = Bry_.New_by_ints(225,186,190)
		, byte_225_187_128 = Bry_.New_by_ints(225,187,128)
		, byte_225_187_130 = Bry_.New_by_ints(225,187,130)
		, byte_225_187_132 = Bry_.New_by_ints(225,187,132)
		, byte_225_187_134 = Bry_.New_by_ints(225,187,134)
		, byte_225_187_136 = Bry_.New_by_ints(225,187,136)
		, byte_225_187_138 = Bry_.New_by_ints(225,187,138)
		, byte_225_187_140 = Bry_.New_by_ints(225,187,140)
		, byte_225_187_142 = Bry_.New_by_ints(225,187,142)
		, byte_225_187_144 = Bry_.New_by_ints(225,187,144)
		, byte_225_187_146 = Bry_.New_by_ints(225,187,146)
		, byte_225_187_148 = Bry_.New_by_ints(225,187,148)
		, byte_225_187_150 = Bry_.New_by_ints(225,187,150)
		, byte_225_187_152 = Bry_.New_by_ints(225,187,152)
		, byte_225_187_154 = Bry_.New_by_ints(225,187,154)
		, byte_225_187_156 = Bry_.New_by_ints(225,187,156)
		, byte_225_187_158 = Bry_.New_by_ints(225,187,158)
		, byte_225_187_160 = Bry_.New_by_ints(225,187,160)
		, byte_225_187_162 = Bry_.New_by_ints(225,187,162)
		, byte_225_187_164 = Bry_.New_by_ints(225,187,164)
		, byte_225_187_166 = Bry_.New_by_ints(225,187,166)
		, byte_225_187_168 = Bry_.New_by_ints(225,187,168)
		, byte_225_187_170 = Bry_.New_by_ints(225,187,170)
		, byte_225_187_172 = Bry_.New_by_ints(225,187,172)
		, byte_225_187_174 = Bry_.New_by_ints(225,187,174)
		, byte_225_187_176 = Bry_.New_by_ints(225,187,176)
		, byte_225_187_178 = Bry_.New_by_ints(225,187,178)
		, byte_225_187_180 = Bry_.New_by_ints(225,187,180)
		, byte_225_187_182 = Bry_.New_by_ints(225,187,182)
		, byte_225_187_184 = Bry_.New_by_ints(225,187,184)
		, byte_225_187_186 = Bry_.New_by_ints(225,187,186)
		, byte_225_187_188 = Bry_.New_by_ints(225,187,188)
		, byte_225_187_190 = Bry_.New_by_ints(225,187,190)
		, byte_225_188_136 = Bry_.New_by_ints(225,188,136)
		, byte_225_188_137 = Bry_.New_by_ints(225,188,137)
		, byte_225_188_138 = Bry_.New_by_ints(225,188,138)
		, byte_225_188_139 = Bry_.New_by_ints(225,188,139)
		, byte_225_188_140 = Bry_.New_by_ints(225,188,140)
		, byte_225_188_141 = Bry_.New_by_ints(225,188,141)
		, byte_225_188_142 = Bry_.New_by_ints(225,188,142)
		, byte_225_188_143 = Bry_.New_by_ints(225,188,143)
		, byte_225_188_152 = Bry_.New_by_ints(225,188,152)
		, byte_225_188_153 = Bry_.New_by_ints(225,188,153)
		, byte_225_188_154 = Bry_.New_by_ints(225,188,154)
		, byte_225_188_155 = Bry_.New_by_ints(225,188,155)
		, byte_225_188_156 = Bry_.New_by_ints(225,188,156)
		, byte_225_188_157 = Bry_.New_by_ints(225,188,157)
		, byte_225_188_168 = Bry_.New_by_ints(225,188,168)
		, byte_225_188_169 = Bry_.New_by_ints(225,188,169)
		, byte_225_188_170 = Bry_.New_by_ints(225,188,170)
		, byte_225_188_171 = Bry_.New_by_ints(225,188,171)
		, byte_225_188_172 = Bry_.New_by_ints(225,188,172)
		, byte_225_188_173 = Bry_.New_by_ints(225,188,173)
		, byte_225_188_174 = Bry_.New_by_ints(225,188,174)
		, byte_225_188_175 = Bry_.New_by_ints(225,188,175)
		, byte_225_188_184 = Bry_.New_by_ints(225,188,184)
		, byte_225_188_185 = Bry_.New_by_ints(225,188,185)
		, byte_225_188_186 = Bry_.New_by_ints(225,188,186)
		, byte_225_188_187 = Bry_.New_by_ints(225,188,187)
		, byte_225_188_188 = Bry_.New_by_ints(225,188,188)
		, byte_225_188_189 = Bry_.New_by_ints(225,188,189)
		, byte_225_188_190 = Bry_.New_by_ints(225,188,190)
		, byte_225_188_191 = Bry_.New_by_ints(225,188,191)
		, byte_225_189_136 = Bry_.New_by_ints(225,189,136)
		, byte_225_189_137 = Bry_.New_by_ints(225,189,137)
		, byte_225_189_138 = Bry_.New_by_ints(225,189,138)
		, byte_225_189_139 = Bry_.New_by_ints(225,189,139)
		, byte_225_189_140 = Bry_.New_by_ints(225,189,140)
		, byte_225_189_141 = Bry_.New_by_ints(225,189,141)
		, byte_225_189_153 = Bry_.New_by_ints(225,189,153)
		, byte_225_189_155 = Bry_.New_by_ints(225,189,155)
		, byte_225_189_157 = Bry_.New_by_ints(225,189,157)
		, byte_225_189_159 = Bry_.New_by_ints(225,189,159)
		, byte_225_189_168 = Bry_.New_by_ints(225,189,168)
		, byte_225_189_169 = Bry_.New_by_ints(225,189,169)
		, byte_225_189_170 = Bry_.New_by_ints(225,189,170)
		, byte_225_189_171 = Bry_.New_by_ints(225,189,171)
		, byte_225_189_172 = Bry_.New_by_ints(225,189,172)
		, byte_225_189_173 = Bry_.New_by_ints(225,189,173)
		, byte_225_189_174 = Bry_.New_by_ints(225,189,174)
		, byte_225_189_175 = Bry_.New_by_ints(225,189,175)
		, byte_225_190_186 = Bry_.New_by_ints(225,190,186)
		, byte_225_190_187 = Bry_.New_by_ints(225,190,187)
		, byte_225_191_136 = Bry_.New_by_ints(225,191,136)
		, byte_225_191_137 = Bry_.New_by_ints(225,191,137)
		, byte_225_191_138 = Bry_.New_by_ints(225,191,138)
		, byte_225_191_139 = Bry_.New_by_ints(225,191,139)
		, byte_225_191_154 = Bry_.New_by_ints(225,191,154)
		, byte_225_191_155 = Bry_.New_by_ints(225,191,155)
		, byte_225_191_184 = Bry_.New_by_ints(225,191,184)
		, byte_225_191_185 = Bry_.New_by_ints(225,191,185)
		, byte_225_191_170 = Bry_.New_by_ints(225,191,170)
		, byte_225_191_171 = Bry_.New_by_ints(225,191,171)
		, byte_225_191_186 = Bry_.New_by_ints(225,191,186)
		, byte_225_191_187 = Bry_.New_by_ints(225,191,187)
		, byte_225_190_136 = Bry_.New_by_ints(225,190,136)
		, byte_225_190_137 = Bry_.New_by_ints(225,190,137)
		, byte_225_190_138 = Bry_.New_by_ints(225,190,138)
		, byte_225_190_139 = Bry_.New_by_ints(225,190,139)
		, byte_225_190_140 = Bry_.New_by_ints(225,190,140)
		, byte_225_190_141 = Bry_.New_by_ints(225,190,141)
		, byte_225_190_142 = Bry_.New_by_ints(225,190,142)
		, byte_225_190_143 = Bry_.New_by_ints(225,190,143)
		, byte_225_190_152 = Bry_.New_by_ints(225,190,152)
		, byte_225_190_153 = Bry_.New_by_ints(225,190,153)
		, byte_225_190_154 = Bry_.New_by_ints(225,190,154)
		, byte_225_190_155 = Bry_.New_by_ints(225,190,155)
		, byte_225_190_156 = Bry_.New_by_ints(225,190,156)
		, byte_225_190_157 = Bry_.New_by_ints(225,190,157)
		, byte_225_190_158 = Bry_.New_by_ints(225,190,158)
		, byte_225_190_159 = Bry_.New_by_ints(225,190,159)
		, byte_225_190_168 = Bry_.New_by_ints(225,190,168)
		, byte_225_190_169 = Bry_.New_by_ints(225,190,169)
		, byte_225_190_170 = Bry_.New_by_ints(225,190,170)
		, byte_225_190_171 = Bry_.New_by_ints(225,190,171)
		, byte_225_190_172 = Bry_.New_by_ints(225,190,172)
		, byte_225_190_173 = Bry_.New_by_ints(225,190,173)
		, byte_225_190_174 = Bry_.New_by_ints(225,190,174)
		, byte_225_190_175 = Bry_.New_by_ints(225,190,175)
		, byte_225_190_184 = Bry_.New_by_ints(225,190,184)
		, byte_225_190_185 = Bry_.New_by_ints(225,190,185)
		, byte_225_190_188 = Bry_.New_by_ints(225,190,188)
		, byte_225_191_140 = Bry_.New_by_ints(225,191,140)
		, byte_225_191_152 = Bry_.New_by_ints(225,191,152)
		, byte_225_191_153 = Bry_.New_by_ints(225,191,153)
		, byte_225_191_168 = Bry_.New_by_ints(225,191,168)
		, byte_225_191_169 = Bry_.New_by_ints(225,191,169)
		, byte_225_191_172 = Bry_.New_by_ints(225,191,172)
		, byte_225_191_188 = Bry_.New_by_ints(225,191,188)
		, byte_226_132_178 = Bry_.New_by_ints(226,132,178)
		, byte_226_133_160 = Bry_.New_by_ints(226,133,160)
		, byte_226_133_161 = Bry_.New_by_ints(226,133,161)
		, byte_226_133_162 = Bry_.New_by_ints(226,133,162)
		, byte_226_133_163 = Bry_.New_by_ints(226,133,163)
		, byte_226_133_164 = Bry_.New_by_ints(226,133,164)
		, byte_226_133_165 = Bry_.New_by_ints(226,133,165)
		, byte_226_133_166 = Bry_.New_by_ints(226,133,166)
		, byte_226_133_167 = Bry_.New_by_ints(226,133,167)
		, byte_226_133_168 = Bry_.New_by_ints(226,133,168)
		, byte_226_133_169 = Bry_.New_by_ints(226,133,169)
		, byte_226_133_170 = Bry_.New_by_ints(226,133,170)
		, byte_226_133_171 = Bry_.New_by_ints(226,133,171)
		, byte_226_133_172 = Bry_.New_by_ints(226,133,172)
		, byte_226_133_173 = Bry_.New_by_ints(226,133,173)
		, byte_226_133_174 = Bry_.New_by_ints(226,133,174)
		, byte_226_133_175 = Bry_.New_by_ints(226,133,175)
		, byte_226_134_131 = Bry_.New_by_ints(226,134,131)
		, byte_226_146_182 = Bry_.New_by_ints(226,146,182)
		, byte_226_146_183 = Bry_.New_by_ints(226,146,183)
		, byte_226_146_184 = Bry_.New_by_ints(226,146,184)
		, byte_226_146_185 = Bry_.New_by_ints(226,146,185)
		, byte_226_146_186 = Bry_.New_by_ints(226,146,186)
		, byte_226_146_187 = Bry_.New_by_ints(226,146,187)
		, byte_226_146_188 = Bry_.New_by_ints(226,146,188)
		, byte_226_146_189 = Bry_.New_by_ints(226,146,189)
		, byte_226_146_190 = Bry_.New_by_ints(226,146,190)
		, byte_226_146_191 = Bry_.New_by_ints(226,146,191)
		, byte_226_147_128 = Bry_.New_by_ints(226,147,128)
		, byte_226_147_129 = Bry_.New_by_ints(226,147,129)
		, byte_226_147_130 = Bry_.New_by_ints(226,147,130)
		, byte_226_147_131 = Bry_.New_by_ints(226,147,131)
		, byte_226_147_132 = Bry_.New_by_ints(226,147,132)
		, byte_226_147_133 = Bry_.New_by_ints(226,147,133)
		, byte_226_147_134 = Bry_.New_by_ints(226,147,134)
		, byte_226_147_135 = Bry_.New_by_ints(226,147,135)
		, byte_226_147_136 = Bry_.New_by_ints(226,147,136)
		, byte_226_147_137 = Bry_.New_by_ints(226,147,137)
		, byte_226_147_138 = Bry_.New_by_ints(226,147,138)
		, byte_226_147_139 = Bry_.New_by_ints(226,147,139)
		, byte_226_147_140 = Bry_.New_by_ints(226,147,140)
		, byte_226_147_141 = Bry_.New_by_ints(226,147,141)
		, byte_226_147_142 = Bry_.New_by_ints(226,147,142)
		, byte_226_147_143 = Bry_.New_by_ints(226,147,143)
		, byte_226_176_128 = Bry_.New_by_ints(226,176,128)
		, byte_226_176_129 = Bry_.New_by_ints(226,176,129)
		, byte_226_176_130 = Bry_.New_by_ints(226,176,130)
		, byte_226_176_131 = Bry_.New_by_ints(226,176,131)
		, byte_226_176_132 = Bry_.New_by_ints(226,176,132)
		, byte_226_176_133 = Bry_.New_by_ints(226,176,133)
		, byte_226_176_134 = Bry_.New_by_ints(226,176,134)
		, byte_226_176_135 = Bry_.New_by_ints(226,176,135)
		, byte_226_176_136 = Bry_.New_by_ints(226,176,136)
		, byte_226_176_137 = Bry_.New_by_ints(226,176,137)
		, byte_226_176_138 = Bry_.New_by_ints(226,176,138)
		, byte_226_176_139 = Bry_.New_by_ints(226,176,139)
		, byte_226_176_140 = Bry_.New_by_ints(226,176,140)
		, byte_226_176_141 = Bry_.New_by_ints(226,176,141)
		, byte_226_176_142 = Bry_.New_by_ints(226,176,142)
		, byte_226_176_143 = Bry_.New_by_ints(226,176,143)
		, byte_226_176_144 = Bry_.New_by_ints(226,176,144)
		, byte_226_176_145 = Bry_.New_by_ints(226,176,145)
		, byte_226_176_146 = Bry_.New_by_ints(226,176,146)
		, byte_226_176_147 = Bry_.New_by_ints(226,176,147)
		, byte_226_176_148 = Bry_.New_by_ints(226,176,148)
		, byte_226_176_149 = Bry_.New_by_ints(226,176,149)
		, byte_226_176_150 = Bry_.New_by_ints(226,176,150)
		, byte_226_176_151 = Bry_.New_by_ints(226,176,151)
		, byte_226_176_152 = Bry_.New_by_ints(226,176,152)
		, byte_226_176_153 = Bry_.New_by_ints(226,176,153)
		, byte_226_176_154 = Bry_.New_by_ints(226,176,154)
		, byte_226_176_155 = Bry_.New_by_ints(226,176,155)
		, byte_226_176_156 = Bry_.New_by_ints(226,176,156)
		, byte_226_176_157 = Bry_.New_by_ints(226,176,157)
		, byte_226_176_158 = Bry_.New_by_ints(226,176,158)
		, byte_226_176_159 = Bry_.New_by_ints(226,176,159)
		, byte_226_176_160 = Bry_.New_by_ints(226,176,160)
		, byte_226_176_161 = Bry_.New_by_ints(226,176,161)
		, byte_226_176_162 = Bry_.New_by_ints(226,176,162)
		, byte_226_176_163 = Bry_.New_by_ints(226,176,163)
		, byte_226_176_164 = Bry_.New_by_ints(226,176,164)
		, byte_226_176_165 = Bry_.New_by_ints(226,176,165)
		, byte_226_176_166 = Bry_.New_by_ints(226,176,166)
		, byte_226_176_167 = Bry_.New_by_ints(226,176,167)
		, byte_226_176_168 = Bry_.New_by_ints(226,176,168)
		, byte_226_176_169 = Bry_.New_by_ints(226,176,169)
		, byte_226_176_170 = Bry_.New_by_ints(226,176,170)
		, byte_226_176_171 = Bry_.New_by_ints(226,176,171)
		, byte_226_176_172 = Bry_.New_by_ints(226,176,172)
		, byte_226_176_173 = Bry_.New_by_ints(226,176,173)
		, byte_226_176_174 = Bry_.New_by_ints(226,176,174)
		, byte_226_177_160 = Bry_.New_by_ints(226,177,160)
		, byte_200_186 = Bry_.New_by_ints(200,186)
		, byte_200_190 = Bry_.New_by_ints(200,190)
		, byte_226_177_167 = Bry_.New_by_ints(226,177,167)
		, byte_226_177_169 = Bry_.New_by_ints(226,177,169)
		, byte_226_177_171 = Bry_.New_by_ints(226,177,171)
		, byte_226_177_178 = Bry_.New_by_ints(226,177,178)
		, byte_226_177_181 = Bry_.New_by_ints(226,177,181)
		, byte_226_178_128 = Bry_.New_by_ints(226,178,128)
		, byte_226_178_130 = Bry_.New_by_ints(226,178,130)
		, byte_226_178_132 = Bry_.New_by_ints(226,178,132)
		, byte_226_178_134 = Bry_.New_by_ints(226,178,134)
		, byte_226_178_136 = Bry_.New_by_ints(226,178,136)
		, byte_226_178_138 = Bry_.New_by_ints(226,178,138)
		, byte_226_178_140 = Bry_.New_by_ints(226,178,140)
		, byte_226_178_142 = Bry_.New_by_ints(226,178,142)
		, byte_226_178_144 = Bry_.New_by_ints(226,178,144)
		, byte_226_178_146 = Bry_.New_by_ints(226,178,146)
		, byte_226_178_148 = Bry_.New_by_ints(226,178,148)
		, byte_226_178_150 = Bry_.New_by_ints(226,178,150)
		, byte_226_178_152 = Bry_.New_by_ints(226,178,152)
		, byte_226_178_154 = Bry_.New_by_ints(226,178,154)
		, byte_226_178_156 = Bry_.New_by_ints(226,178,156)
		, byte_226_178_158 = Bry_.New_by_ints(226,178,158)
		, byte_226_178_160 = Bry_.New_by_ints(226,178,160)
		, byte_226_178_162 = Bry_.New_by_ints(226,178,162)
		, byte_226_178_164 = Bry_.New_by_ints(226,178,164)
		, byte_226_178_166 = Bry_.New_by_ints(226,178,166)
		, byte_226_178_168 = Bry_.New_by_ints(226,178,168)
		, byte_226_178_170 = Bry_.New_by_ints(226,178,170)
		, byte_226_178_172 = Bry_.New_by_ints(226,178,172)
		, byte_226_178_174 = Bry_.New_by_ints(226,178,174)
		, byte_226_178_176 = Bry_.New_by_ints(226,178,176)
		, byte_226_178_178 = Bry_.New_by_ints(226,178,178)
		, byte_226_178_180 = Bry_.New_by_ints(226,178,180)
		, byte_226_178_182 = Bry_.New_by_ints(226,178,182)
		, byte_226_178_184 = Bry_.New_by_ints(226,178,184)
		, byte_226_178_186 = Bry_.New_by_ints(226,178,186)
		, byte_226_178_188 = Bry_.New_by_ints(226,178,188)
		, byte_226_178_190 = Bry_.New_by_ints(226,178,190)
		, byte_226_179_128 = Bry_.New_by_ints(226,179,128)
		, byte_226_179_130 = Bry_.New_by_ints(226,179,130)
		, byte_226_179_132 = Bry_.New_by_ints(226,179,132)
		, byte_226_179_134 = Bry_.New_by_ints(226,179,134)
		, byte_226_179_136 = Bry_.New_by_ints(226,179,136)
		, byte_226_179_138 = Bry_.New_by_ints(226,179,138)
		, byte_226_179_140 = Bry_.New_by_ints(226,179,140)
		, byte_226_179_142 = Bry_.New_by_ints(226,179,142)
		, byte_226_179_144 = Bry_.New_by_ints(226,179,144)
		, byte_226_179_146 = Bry_.New_by_ints(226,179,146)
		, byte_226_179_148 = Bry_.New_by_ints(226,179,148)
		, byte_226_179_150 = Bry_.New_by_ints(226,179,150)
		, byte_226_179_152 = Bry_.New_by_ints(226,179,152)
		, byte_226_179_154 = Bry_.New_by_ints(226,179,154)
		, byte_226_179_156 = Bry_.New_by_ints(226,179,156)
		, byte_226_179_158 = Bry_.New_by_ints(226,179,158)
		, byte_226_179_160 = Bry_.New_by_ints(226,179,160)
		, byte_226_179_162 = Bry_.New_by_ints(226,179,162)
		, byte_226_179_171 = Bry_.New_by_ints(226,179,171)
		, byte_226_179_173 = Bry_.New_by_ints(226,179,173)
		, byte_226_179_178 = Bry_.New_by_ints(226,179,178)
		, byte_225_130_160 = Bry_.New_by_ints(225,130,160)
		, byte_225_130_161 = Bry_.New_by_ints(225,130,161)
		, byte_225_130_162 = Bry_.New_by_ints(225,130,162)
		, byte_225_130_163 = Bry_.New_by_ints(225,130,163)
		, byte_225_130_164 = Bry_.New_by_ints(225,130,164)
		, byte_225_130_165 = Bry_.New_by_ints(225,130,165)
		, byte_225_130_166 = Bry_.New_by_ints(225,130,166)
		, byte_225_130_167 = Bry_.New_by_ints(225,130,167)
		, byte_225_130_168 = Bry_.New_by_ints(225,130,168)
		, byte_225_130_169 = Bry_.New_by_ints(225,130,169)
		, byte_225_130_170 = Bry_.New_by_ints(225,130,170)
		, byte_225_130_171 = Bry_.New_by_ints(225,130,171)
		, byte_225_130_172 = Bry_.New_by_ints(225,130,172)
		, byte_225_130_173 = Bry_.New_by_ints(225,130,173)
		, byte_225_130_174 = Bry_.New_by_ints(225,130,174)
		, byte_225_130_175 = Bry_.New_by_ints(225,130,175)
		, byte_225_130_176 = Bry_.New_by_ints(225,130,176)
		, byte_225_130_177 = Bry_.New_by_ints(225,130,177)
		, byte_225_130_178 = Bry_.New_by_ints(225,130,178)
		, byte_225_130_179 = Bry_.New_by_ints(225,130,179)
		, byte_225_130_180 = Bry_.New_by_ints(225,130,180)
		, byte_225_130_181 = Bry_.New_by_ints(225,130,181)
		, byte_225_130_182 = Bry_.New_by_ints(225,130,182)
		, byte_225_130_183 = Bry_.New_by_ints(225,130,183)
		, byte_225_130_184 = Bry_.New_by_ints(225,130,184)
		, byte_225_130_185 = Bry_.New_by_ints(225,130,185)
		, byte_225_130_186 = Bry_.New_by_ints(225,130,186)
		, byte_225_130_187 = Bry_.New_by_ints(225,130,187)
		, byte_225_130_188 = Bry_.New_by_ints(225,130,188)
		, byte_225_130_189 = Bry_.New_by_ints(225,130,189)
		, byte_225_130_190 = Bry_.New_by_ints(225,130,190)
		, byte_225_130_191 = Bry_.New_by_ints(225,130,191)
		, byte_225_131_128 = Bry_.New_by_ints(225,131,128)
		, byte_225_131_129 = Bry_.New_by_ints(225,131,129)
		, byte_225_131_130 = Bry_.New_by_ints(225,131,130)
		, byte_225_131_131 = Bry_.New_by_ints(225,131,131)
		, byte_225_131_132 = Bry_.New_by_ints(225,131,132)
		, byte_225_131_133 = Bry_.New_by_ints(225,131,133)
		, byte_225_131_135 = Bry_.New_by_ints(225,131,135)
		, byte_225_131_141 = Bry_.New_by_ints(225,131,141)
		, byte_234_153_128 = Bry_.New_by_ints(234,153,128)
		, byte_234_153_130 = Bry_.New_by_ints(234,153,130)
		, byte_234_153_132 = Bry_.New_by_ints(234,153,132)
		, byte_234_153_134 = Bry_.New_by_ints(234,153,134)
		, byte_234_153_136 = Bry_.New_by_ints(234,153,136)
		, byte_234_153_138 = Bry_.New_by_ints(234,153,138)
		, byte_234_153_140 = Bry_.New_by_ints(234,153,140)
		, byte_234_153_142 = Bry_.New_by_ints(234,153,142)
		, byte_234_153_144 = Bry_.New_by_ints(234,153,144)
		, byte_234_153_146 = Bry_.New_by_ints(234,153,146)
		, byte_234_153_148 = Bry_.New_by_ints(234,153,148)
		, byte_234_153_150 = Bry_.New_by_ints(234,153,150)
		, byte_234_153_152 = Bry_.New_by_ints(234,153,152)
		, byte_234_153_154 = Bry_.New_by_ints(234,153,154)
		, byte_234_153_156 = Bry_.New_by_ints(234,153,156)
		, byte_234_153_158 = Bry_.New_by_ints(234,153,158)
		, byte_234_153_160 = Bry_.New_by_ints(234,153,160)
		, byte_234_153_162 = Bry_.New_by_ints(234,153,162)
		, byte_234_153_164 = Bry_.New_by_ints(234,153,164)
		, byte_234_153_166 = Bry_.New_by_ints(234,153,166)
		, byte_234_153_168 = Bry_.New_by_ints(234,153,168)
		, byte_234_153_170 = Bry_.New_by_ints(234,153,170)
		, byte_234_153_172 = Bry_.New_by_ints(234,153,172)
		, byte_234_154_128 = Bry_.New_by_ints(234,154,128)
		, byte_234_154_130 = Bry_.New_by_ints(234,154,130)
		, byte_234_154_132 = Bry_.New_by_ints(234,154,132)
		, byte_234_154_134 = Bry_.New_by_ints(234,154,134)
		, byte_234_154_136 = Bry_.New_by_ints(234,154,136)
		, byte_234_154_138 = Bry_.New_by_ints(234,154,138)
		, byte_234_154_140 = Bry_.New_by_ints(234,154,140)
		, byte_234_154_142 = Bry_.New_by_ints(234,154,142)
		, byte_234_154_144 = Bry_.New_by_ints(234,154,144)
		, byte_234_154_146 = Bry_.New_by_ints(234,154,146)
		, byte_234_154_148 = Bry_.New_by_ints(234,154,148)
		, byte_234_154_150 = Bry_.New_by_ints(234,154,150)
		, byte_234_156_162 = Bry_.New_by_ints(234,156,162)
		, byte_234_156_164 = Bry_.New_by_ints(234,156,164)
		, byte_234_156_166 = Bry_.New_by_ints(234,156,166)
		, byte_234_156_168 = Bry_.New_by_ints(234,156,168)
		, byte_234_156_170 = Bry_.New_by_ints(234,156,170)
		, byte_234_156_172 = Bry_.New_by_ints(234,156,172)
		, byte_234_156_174 = Bry_.New_by_ints(234,156,174)
		, byte_234_156_178 = Bry_.New_by_ints(234,156,178)
		, byte_234_156_180 = Bry_.New_by_ints(234,156,180)
		, byte_234_156_182 = Bry_.New_by_ints(234,156,182)
		, byte_234_156_184 = Bry_.New_by_ints(234,156,184)
		, byte_234_156_186 = Bry_.New_by_ints(234,156,186)
		, byte_234_156_188 = Bry_.New_by_ints(234,156,188)
		, byte_234_156_190 = Bry_.New_by_ints(234,156,190)
		, byte_234_157_128 = Bry_.New_by_ints(234,157,128)
		, byte_234_157_130 = Bry_.New_by_ints(234,157,130)
		, byte_234_157_132 = Bry_.New_by_ints(234,157,132)
		, byte_234_157_134 = Bry_.New_by_ints(234,157,134)
		, byte_234_157_136 = Bry_.New_by_ints(234,157,136)
		, byte_234_157_138 = Bry_.New_by_ints(234,157,138)
		, byte_234_157_140 = Bry_.New_by_ints(234,157,140)
		, byte_234_157_142 = Bry_.New_by_ints(234,157,142)
		, byte_234_157_144 = Bry_.New_by_ints(234,157,144)
		, byte_234_157_146 = Bry_.New_by_ints(234,157,146)
		, byte_234_157_148 = Bry_.New_by_ints(234,157,148)
		, byte_234_157_150 = Bry_.New_by_ints(234,157,150)
		, byte_234_157_152 = Bry_.New_by_ints(234,157,152)
		, byte_234_157_154 = Bry_.New_by_ints(234,157,154)
		, byte_234_157_156 = Bry_.New_by_ints(234,157,156)
		, byte_234_157_158 = Bry_.New_by_ints(234,157,158)
		, byte_234_157_160 = Bry_.New_by_ints(234,157,160)
		, byte_234_157_162 = Bry_.New_by_ints(234,157,162)
		, byte_234_157_164 = Bry_.New_by_ints(234,157,164)
		, byte_234_157_166 = Bry_.New_by_ints(234,157,166)
		, byte_234_157_168 = Bry_.New_by_ints(234,157,168)
		, byte_234_157_170 = Bry_.New_by_ints(234,157,170)
		, byte_234_157_172 = Bry_.New_by_ints(234,157,172)
		, byte_234_157_174 = Bry_.New_by_ints(234,157,174)
		, byte_234_157_185 = Bry_.New_by_ints(234,157,185)
		, byte_234_157_187 = Bry_.New_by_ints(234,157,187)
		, byte_234_157_190 = Bry_.New_by_ints(234,157,190)
		, byte_234_158_128 = Bry_.New_by_ints(234,158,128)
		, byte_234_158_130 = Bry_.New_by_ints(234,158,130)
		, byte_234_158_132 = Bry_.New_by_ints(234,158,132)
		, byte_234_158_134 = Bry_.New_by_ints(234,158,134)
		, byte_234_158_139 = Bry_.New_by_ints(234,158,139)
		, byte_234_158_144 = Bry_.New_by_ints(234,158,144)
		, byte_234_158_146 = Bry_.New_by_ints(234,158,146)
		, byte_234_158_160 = Bry_.New_by_ints(234,158,160)
		, byte_234_158_162 = Bry_.New_by_ints(234,158,162)
		, byte_234_158_164 = Bry_.New_by_ints(234,158,164)
		, byte_234_158_166 = Bry_.New_by_ints(234,158,166)
		, byte_234_158_168 = Bry_.New_by_ints(234,158,168)
		, byte_239_188_161 = Bry_.New_by_ints(239,188,161)
		, byte_239_188_162 = Bry_.New_by_ints(239,188,162)
		, byte_239_188_163 = Bry_.New_by_ints(239,188,163)
		, byte_239_188_164 = Bry_.New_by_ints(239,188,164)
		, byte_239_188_165 = Bry_.New_by_ints(239,188,165)
		, byte_239_188_166 = Bry_.New_by_ints(239,188,166)
		, byte_239_188_167 = Bry_.New_by_ints(239,188,167)
		, byte_239_188_168 = Bry_.New_by_ints(239,188,168)
		, byte_239_188_169 = Bry_.New_by_ints(239,188,169)
		, byte_239_188_170 = Bry_.New_by_ints(239,188,170)
		, byte_239_188_171 = Bry_.New_by_ints(239,188,171)
		, byte_239_188_172 = Bry_.New_by_ints(239,188,172)
		, byte_239_188_173 = Bry_.New_by_ints(239,188,173)
		, byte_239_188_174 = Bry_.New_by_ints(239,188,174)
		, byte_239_188_175 = Bry_.New_by_ints(239,188,175)
		, byte_239_188_176 = Bry_.New_by_ints(239,188,176)
		, byte_239_188_177 = Bry_.New_by_ints(239,188,177)
		, byte_239_188_178 = Bry_.New_by_ints(239,188,178)
		, byte_239_188_179 = Bry_.New_by_ints(239,188,179)
		, byte_239_188_180 = Bry_.New_by_ints(239,188,180)
		, byte_239_188_181 = Bry_.New_by_ints(239,188,181)
		, byte_239_188_182 = Bry_.New_by_ints(239,188,182)
		, byte_239_188_183 = Bry_.New_by_ints(239,188,183)
		, byte_239_188_184 = Bry_.New_by_ints(239,188,184)
		, byte_239_188_185 = Bry_.New_by_ints(239,188,185)
		, byte_239_188_186 = Bry_.New_by_ints(239,188,186)
		, byte_240_144_144_128 = Bry_.New_by_ints(240,144,144,128)
		, byte_240_144_144_129 = Bry_.New_by_ints(240,144,144,129)
		, byte_240_144_144_130 = Bry_.New_by_ints(240,144,144,130)
		, byte_240_144_144_131 = Bry_.New_by_ints(240,144,144,131)
		, byte_240_144_144_132 = Bry_.New_by_ints(240,144,144,132)
		, byte_240_144_144_133 = Bry_.New_by_ints(240,144,144,133)
		, byte_240_144_144_134 = Bry_.New_by_ints(240,144,144,134)
		, byte_240_144_144_135 = Bry_.New_by_ints(240,144,144,135)
		, byte_240_144_144_136 = Bry_.New_by_ints(240,144,144,136)
		, byte_240_144_144_137 = Bry_.New_by_ints(240,144,144,137)
		, byte_240_144_144_138 = Bry_.New_by_ints(240,144,144,138)
		, byte_240_144_144_139 = Bry_.New_by_ints(240,144,144,139)
		, byte_240_144_144_140 = Bry_.New_by_ints(240,144,144,140)
		, byte_240_144_144_141 = Bry_.New_by_ints(240,144,144,141)
		, byte_240_144_144_142 = Bry_.New_by_ints(240,144,144,142)
		, byte_240_144_144_143 = Bry_.New_by_ints(240,144,144,143)
		, byte_240_144_144_144 = Bry_.New_by_ints(240,144,144,144)
		, byte_240_144_144_145 = Bry_.New_by_ints(240,144,144,145)
		, byte_240_144_144_146 = Bry_.New_by_ints(240,144,144,146)
		, byte_240_144_144_147 = Bry_.New_by_ints(240,144,144,147)
		, byte_240_144_144_148 = Bry_.New_by_ints(240,144,144,148)
		, byte_240_144_144_149 = Bry_.New_by_ints(240,144,144,149)
		, byte_240_144_144_150 = Bry_.New_by_ints(240,144,144,150)
		, byte_240_144_144_151 = Bry_.New_by_ints(240,144,144,151)
		, byte_240_144_144_152 = Bry_.New_by_ints(240,144,144,152)
		, byte_240_144_144_153 = Bry_.New_by_ints(240,144,144,153)
		, byte_240_144_144_154 = Bry_.New_by_ints(240,144,144,154)
		, byte_240_144_144_155 = Bry_.New_by_ints(240,144,144,155)
		, byte_240_144_144_156 = Bry_.New_by_ints(240,144,144,156)
		, byte_240_144_144_157 = Bry_.New_by_ints(240,144,144,157)
		, byte_240_144_144_158 = Bry_.New_by_ints(240,144,144,158)
		, byte_240_144_144_159 = Bry_.New_by_ints(240,144,144,159)
		, byte_240_144_144_160 = Bry_.New_by_ints(240,144,144,160)
		, byte_240_144_144_161 = Bry_.New_by_ints(240,144,144,161)
		, byte_240_144_144_162 = Bry_.New_by_ints(240,144,144,162)
		, byte_240_144_144_163 = Bry_.New_by_ints(240,144,144,163)
		, byte_240_144_144_164 = Bry_.New_by_ints(240,144,144,164)
		, byte_240_144_144_165 = Bry_.New_by_ints(240,144,144,165)
		, byte_240_144_144_166 = Bry_.New_by_ints(240,144,144,166)
		, byte_240_144_144_167 = Bry_.New_by_ints(240,144,144,167)
		, byte_97 = Bry_.New_by_ints(97)
		, byte_98 = Bry_.New_by_ints(98)
		, byte_99 = Bry_.New_by_ints(99)
		, byte_100 = Bry_.New_by_ints(100)
		, byte_101 = Bry_.New_by_ints(101)
		, byte_102 = Bry_.New_by_ints(102)
		, byte_103 = Bry_.New_by_ints(103)
		, byte_104 = Bry_.New_by_ints(104)
		, byte_105 = Bry_.New_by_ints(105)
		, byte_106 = Bry_.New_by_ints(106)
		, byte_107 = Bry_.New_by_ints(107)
		, byte_108 = Bry_.New_by_ints(108)
		, byte_109 = Bry_.New_by_ints(109)
		, byte_110 = Bry_.New_by_ints(110)
		, byte_111 = Bry_.New_by_ints(111)
		, byte_112 = Bry_.New_by_ints(112)
		, byte_113 = Bry_.New_by_ints(113)
		, byte_114 = Bry_.New_by_ints(114)
		, byte_115 = Bry_.New_by_ints(115)
		, byte_116 = Bry_.New_by_ints(116)
		, byte_117 = Bry_.New_by_ints(117)
		, byte_118 = Bry_.New_by_ints(118)
		, byte_119 = Bry_.New_by_ints(119)
		, byte_120 = Bry_.New_by_ints(120)
		, byte_121 = Bry_.New_by_ints(121)
		, byte_122 = Bry_.New_by_ints(122)
		, byte_195_160 = Bry_.New_by_ints(195,160)
		, byte_195_161 = Bry_.New_by_ints(195,161)
		, byte_195_162 = Bry_.New_by_ints(195,162)
		, byte_195_163 = Bry_.New_by_ints(195,163)
		, byte_195_164 = Bry_.New_by_ints(195,164)
		, byte_195_165 = Bry_.New_by_ints(195,165)
		, byte_195_166 = Bry_.New_by_ints(195,166)
		, byte_195_167 = Bry_.New_by_ints(195,167)
		, byte_195_168 = Bry_.New_by_ints(195,168)
		, byte_195_169 = Bry_.New_by_ints(195,169)
		, byte_195_170 = Bry_.New_by_ints(195,170)
		, byte_195_171 = Bry_.New_by_ints(195,171)
		, byte_195_172 = Bry_.New_by_ints(195,172)
		, byte_195_173 = Bry_.New_by_ints(195,173)
		, byte_195_174 = Bry_.New_by_ints(195,174)
		, byte_195_175 = Bry_.New_by_ints(195,175)
		, byte_195_176 = Bry_.New_by_ints(195,176)
		, byte_195_177 = Bry_.New_by_ints(195,177)
		, byte_195_178 = Bry_.New_by_ints(195,178)
		, byte_195_179 = Bry_.New_by_ints(195,179)
		, byte_195_180 = Bry_.New_by_ints(195,180)
		, byte_195_181 = Bry_.New_by_ints(195,181)
		, byte_195_182 = Bry_.New_by_ints(195,182)
		, byte_195_184 = Bry_.New_by_ints(195,184)
		, byte_195_185 = Bry_.New_by_ints(195,185)
		, byte_195_186 = Bry_.New_by_ints(195,186)
		, byte_195_187 = Bry_.New_by_ints(195,187)
		, byte_195_188 = Bry_.New_by_ints(195,188)
		, byte_195_189 = Bry_.New_by_ints(195,189)
		, byte_195_190 = Bry_.New_by_ints(195,190)
		, byte_196_129 = Bry_.New_by_ints(196,129)
		, byte_196_131 = Bry_.New_by_ints(196,131)
		, byte_196_133 = Bry_.New_by_ints(196,133)
		, byte_196_135 = Bry_.New_by_ints(196,135)
		, byte_196_137 = Bry_.New_by_ints(196,137)
		, byte_196_139 = Bry_.New_by_ints(196,139)
		, byte_196_141 = Bry_.New_by_ints(196,141)
		, byte_196_143 = Bry_.New_by_ints(196,143)
		, byte_196_145 = Bry_.New_by_ints(196,145)
		, byte_196_147 = Bry_.New_by_ints(196,147)
		, byte_196_149 = Bry_.New_by_ints(196,149)
		, byte_196_151 = Bry_.New_by_ints(196,151)
		, byte_196_153 = Bry_.New_by_ints(196,153)
		, byte_196_155 = Bry_.New_by_ints(196,155)
		, byte_196_157 = Bry_.New_by_ints(196,157)
		, byte_196_159 = Bry_.New_by_ints(196,159)
		, byte_196_161 = Bry_.New_by_ints(196,161)
		, byte_196_163 = Bry_.New_by_ints(196,163)
		, byte_196_165 = Bry_.New_by_ints(196,165)
		, byte_196_167 = Bry_.New_by_ints(196,167)
		, byte_196_169 = Bry_.New_by_ints(196,169)
		, byte_196_171 = Bry_.New_by_ints(196,171)
		, byte_196_173 = Bry_.New_by_ints(196,173)
		, byte_196_175 = Bry_.New_by_ints(196,175)
		, byte_196_179 = Bry_.New_by_ints(196,179)
		, byte_196_181 = Bry_.New_by_ints(196,181)
		, byte_196_183 = Bry_.New_by_ints(196,183)
		, byte_196_186 = Bry_.New_by_ints(196,186)
		, byte_196_188 = Bry_.New_by_ints(196,188)
		, byte_196_190 = Bry_.New_by_ints(196,190)
		, byte_197_128 = Bry_.New_by_ints(197,128)
		, byte_197_130 = Bry_.New_by_ints(197,130)
		, byte_197_132 = Bry_.New_by_ints(197,132)
		, byte_197_134 = Bry_.New_by_ints(197,134)
		, byte_197_136 = Bry_.New_by_ints(197,136)
		, byte_197_139 = Bry_.New_by_ints(197,139)
		, byte_197_141 = Bry_.New_by_ints(197,141)
		, byte_197_143 = Bry_.New_by_ints(197,143)
		, byte_197_145 = Bry_.New_by_ints(197,145)
		, byte_197_147 = Bry_.New_by_ints(197,147)
		, byte_197_149 = Bry_.New_by_ints(197,149)
		, byte_197_151 = Bry_.New_by_ints(197,151)
		, byte_197_153 = Bry_.New_by_ints(197,153)
		, byte_197_155 = Bry_.New_by_ints(197,155)
		, byte_197_157 = Bry_.New_by_ints(197,157)
		, byte_197_159 = Bry_.New_by_ints(197,159)
		, byte_197_161 = Bry_.New_by_ints(197,161)
		, byte_197_163 = Bry_.New_by_ints(197,163)
		, byte_197_165 = Bry_.New_by_ints(197,165)
		, byte_197_167 = Bry_.New_by_ints(197,167)
		, byte_197_169 = Bry_.New_by_ints(197,169)
		, byte_197_171 = Bry_.New_by_ints(197,171)
		, byte_197_173 = Bry_.New_by_ints(197,173)
		, byte_197_175 = Bry_.New_by_ints(197,175)
		, byte_197_177 = Bry_.New_by_ints(197,177)
		, byte_197_179 = Bry_.New_by_ints(197,179)
		, byte_197_181 = Bry_.New_by_ints(197,181)
		, byte_197_183 = Bry_.New_by_ints(197,183)
		, byte_195_191 = Bry_.New_by_ints(195,191)
		, byte_197_186 = Bry_.New_by_ints(197,186)
		, byte_197_188 = Bry_.New_by_ints(197,188)
		, byte_197_190 = Bry_.New_by_ints(197,190)
		, byte_201_147 = Bry_.New_by_ints(201,147)
		, byte_198_131 = Bry_.New_by_ints(198,131)
		, byte_198_133 = Bry_.New_by_ints(198,133)
		, byte_201_148 = Bry_.New_by_ints(201,148)
		, byte_198_136 = Bry_.New_by_ints(198,136)
		, byte_201_150 = Bry_.New_by_ints(201,150)
		, byte_201_151 = Bry_.New_by_ints(201,151)
		, byte_198_140 = Bry_.New_by_ints(198,140)
		, byte_199_157 = Bry_.New_by_ints(199,157)
		, byte_201_153 = Bry_.New_by_ints(201,153)
		, byte_201_155 = Bry_.New_by_ints(201,155)
		, byte_198_146 = Bry_.New_by_ints(198,146)
		, byte_201_160 = Bry_.New_by_ints(201,160)
		, byte_201_163 = Bry_.New_by_ints(201,163)
		, byte_201_169 = Bry_.New_by_ints(201,169)
		, byte_201_168 = Bry_.New_by_ints(201,168)
		, byte_198_153 = Bry_.New_by_ints(198,153)
		, byte_201_175 = Bry_.New_by_ints(201,175)
		, byte_201_178 = Bry_.New_by_ints(201,178)
		, byte_201_181 = Bry_.New_by_ints(201,181)
		, byte_198_161 = Bry_.New_by_ints(198,161)
		, byte_198_163 = Bry_.New_by_ints(198,163)
		, byte_198_165 = Bry_.New_by_ints(198,165)
		, byte_202_128 = Bry_.New_by_ints(202,128)
		, byte_198_168 = Bry_.New_by_ints(198,168)
		, byte_202_131 = Bry_.New_by_ints(202,131)
		, byte_198_173 = Bry_.New_by_ints(198,173)
		, byte_202_136 = Bry_.New_by_ints(202,136)
		, byte_198_176 = Bry_.New_by_ints(198,176)
		, byte_202_138 = Bry_.New_by_ints(202,138)
		, byte_202_139 = Bry_.New_by_ints(202,139)
		, byte_198_180 = Bry_.New_by_ints(198,180)
		, byte_198_182 = Bry_.New_by_ints(198,182)
		, byte_202_146 = Bry_.New_by_ints(202,146)
		, byte_198_185 = Bry_.New_by_ints(198,185)
		, byte_198_189 = Bry_.New_by_ints(198,189)
		, byte_199_134 = Bry_.New_by_ints(199,134)
		, byte_199_137 = Bry_.New_by_ints(199,137)
		, byte_199_140 = Bry_.New_by_ints(199,140)
		, byte_199_142 = Bry_.New_by_ints(199,142)
		, byte_199_144 = Bry_.New_by_ints(199,144)
		, byte_199_146 = Bry_.New_by_ints(199,146)
		, byte_199_148 = Bry_.New_by_ints(199,148)
		, byte_199_150 = Bry_.New_by_ints(199,150)
		, byte_199_152 = Bry_.New_by_ints(199,152)
		, byte_199_154 = Bry_.New_by_ints(199,154)
		, byte_199_156 = Bry_.New_by_ints(199,156)
		, byte_199_159 = Bry_.New_by_ints(199,159)
		, byte_199_161 = Bry_.New_by_ints(199,161)
		, byte_199_163 = Bry_.New_by_ints(199,163)
		, byte_199_165 = Bry_.New_by_ints(199,165)
		, byte_199_167 = Bry_.New_by_ints(199,167)
		, byte_199_169 = Bry_.New_by_ints(199,169)
		, byte_199_171 = Bry_.New_by_ints(199,171)
		, byte_199_173 = Bry_.New_by_ints(199,173)
		, byte_199_175 = Bry_.New_by_ints(199,175)
		, byte_199_179 = Bry_.New_by_ints(199,179)
		, byte_199_181 = Bry_.New_by_ints(199,181)
		, byte_198_149 = Bry_.New_by_ints(198,149)
		, byte_198_191 = Bry_.New_by_ints(198,191)
		, byte_199_185 = Bry_.New_by_ints(199,185)
		, byte_199_187 = Bry_.New_by_ints(199,187)
		, byte_199_189 = Bry_.New_by_ints(199,189)
		, byte_199_191 = Bry_.New_by_ints(199,191)
		, byte_200_129 = Bry_.New_by_ints(200,129)
		, byte_200_131 = Bry_.New_by_ints(200,131)
		, byte_200_133 = Bry_.New_by_ints(200,133)
		, byte_200_135 = Bry_.New_by_ints(200,135)
		, byte_200_137 = Bry_.New_by_ints(200,137)
		, byte_200_139 = Bry_.New_by_ints(200,139)
		, byte_200_141 = Bry_.New_by_ints(200,141)
		, byte_200_143 = Bry_.New_by_ints(200,143)
		, byte_200_145 = Bry_.New_by_ints(200,145)
		, byte_200_147 = Bry_.New_by_ints(200,147)
		, byte_200_149 = Bry_.New_by_ints(200,149)
		, byte_200_151 = Bry_.New_by_ints(200,151)
		, byte_200_153 = Bry_.New_by_ints(200,153)
		, byte_200_155 = Bry_.New_by_ints(200,155)
		, byte_200_157 = Bry_.New_by_ints(200,157)
		, byte_200_159 = Bry_.New_by_ints(200,159)
		, byte_198_158 = Bry_.New_by_ints(198,158)
		, byte_200_163 = Bry_.New_by_ints(200,163)
		, byte_200_165 = Bry_.New_by_ints(200,165)
		, byte_200_167 = Bry_.New_by_ints(200,167)
		, byte_200_169 = Bry_.New_by_ints(200,169)
		, byte_200_171 = Bry_.New_by_ints(200,171)
		, byte_200_173 = Bry_.New_by_ints(200,173)
		, byte_200_175 = Bry_.New_by_ints(200,175)
		, byte_200_177 = Bry_.New_by_ints(200,177)
		, byte_200_179 = Bry_.New_by_ints(200,179)
		, byte_226_177_165 = Bry_.New_by_ints(226,177,165)
		, byte_200_188 = Bry_.New_by_ints(200,188)
		, byte_198_154 = Bry_.New_by_ints(198,154)
		, byte_226_177_166 = Bry_.New_by_ints(226,177,166)
		, byte_201_130 = Bry_.New_by_ints(201,130)
		, byte_198_128 = Bry_.New_by_ints(198,128)
		, byte_202_137 = Bry_.New_by_ints(202,137)
		, byte_202_140 = Bry_.New_by_ints(202,140)
		, byte_201_135 = Bry_.New_by_ints(201,135)
		, byte_201_137 = Bry_.New_by_ints(201,137)
		, byte_201_139 = Bry_.New_by_ints(201,139)
		, byte_201_141 = Bry_.New_by_ints(201,141)
		, byte_201_143 = Bry_.New_by_ints(201,143)
		, byte_205_177 = Bry_.New_by_ints(205,177)
		, byte_205_179 = Bry_.New_by_ints(205,179)
		, byte_205_183 = Bry_.New_by_ints(205,183)
		, byte_206_172 = Bry_.New_by_ints(206,172)
		, byte_206_173 = Bry_.New_by_ints(206,173)
		, byte_206_174 = Bry_.New_by_ints(206,174)
		, byte_206_175 = Bry_.New_by_ints(206,175)
		, byte_207_140 = Bry_.New_by_ints(207,140)
		, byte_207_141 = Bry_.New_by_ints(207,141)
		, byte_207_142 = Bry_.New_by_ints(207,142)
		, byte_206_177 = Bry_.New_by_ints(206,177)
		, byte_206_178 = Bry_.New_by_ints(206,178)
		, byte_206_179 = Bry_.New_by_ints(206,179)
		, byte_206_180 = Bry_.New_by_ints(206,180)
		, byte_206_181 = Bry_.New_by_ints(206,181)
		, byte_206_182 = Bry_.New_by_ints(206,182)
		, byte_206_183 = Bry_.New_by_ints(206,183)
		, byte_206_184 = Bry_.New_by_ints(206,184)
		, byte_206_185 = Bry_.New_by_ints(206,185)
		, byte_206_186 = Bry_.New_by_ints(206,186)
		, byte_206_187 = Bry_.New_by_ints(206,187)
		, byte_206_188 = Bry_.New_by_ints(206,188)
		, byte_206_189 = Bry_.New_by_ints(206,189)
		, byte_206_190 = Bry_.New_by_ints(206,190)
		, byte_206_191 = Bry_.New_by_ints(206,191)
		, byte_207_128 = Bry_.New_by_ints(207,128)
		, byte_207_129 = Bry_.New_by_ints(207,129)
		, byte_207_131 = Bry_.New_by_ints(207,131)
		, byte_207_132 = Bry_.New_by_ints(207,132)
		, byte_207_133 = Bry_.New_by_ints(207,133)
		, byte_207_134 = Bry_.New_by_ints(207,134)
		, byte_207_135 = Bry_.New_by_ints(207,135)
		, byte_207_136 = Bry_.New_by_ints(207,136)
		, byte_207_137 = Bry_.New_by_ints(207,137)
		, byte_207_138 = Bry_.New_by_ints(207,138)
		, byte_207_139 = Bry_.New_by_ints(207,139)
		, byte_207_151 = Bry_.New_by_ints(207,151)
		, byte_207_153 = Bry_.New_by_ints(207,153)
		, byte_207_155 = Bry_.New_by_ints(207,155)
		, byte_207_157 = Bry_.New_by_ints(207,157)
		, byte_207_159 = Bry_.New_by_ints(207,159)
		, byte_207_161 = Bry_.New_by_ints(207,161)
		, byte_207_163 = Bry_.New_by_ints(207,163)
		, byte_207_165 = Bry_.New_by_ints(207,165)
		, byte_207_167 = Bry_.New_by_ints(207,167)
		, byte_207_169 = Bry_.New_by_ints(207,169)
		, byte_207_171 = Bry_.New_by_ints(207,171)
		, byte_207_173 = Bry_.New_by_ints(207,173)
		, byte_207_175 = Bry_.New_by_ints(207,175)
		, byte_207_184 = Bry_.New_by_ints(207,184)
		, byte_207_178 = Bry_.New_by_ints(207,178)
		, byte_207_187 = Bry_.New_by_ints(207,187)
		, byte_205_187 = Bry_.New_by_ints(205,187)
		, byte_205_188 = Bry_.New_by_ints(205,188)
		, byte_205_189 = Bry_.New_by_ints(205,189)
		, byte_209_144 = Bry_.New_by_ints(209,144)
		, byte_209_145 = Bry_.New_by_ints(209,145)
		, byte_209_146 = Bry_.New_by_ints(209,146)
		, byte_209_147 = Bry_.New_by_ints(209,147)
		, byte_209_148 = Bry_.New_by_ints(209,148)
		, byte_209_149 = Bry_.New_by_ints(209,149)
		, byte_209_150 = Bry_.New_by_ints(209,150)
		, byte_209_151 = Bry_.New_by_ints(209,151)
		, byte_209_152 = Bry_.New_by_ints(209,152)
		, byte_209_153 = Bry_.New_by_ints(209,153)
		, byte_209_154 = Bry_.New_by_ints(209,154)
		, byte_209_155 = Bry_.New_by_ints(209,155)
		, byte_209_156 = Bry_.New_by_ints(209,156)
		, byte_209_157 = Bry_.New_by_ints(209,157)
		, byte_209_158 = Bry_.New_by_ints(209,158)
		, byte_209_159 = Bry_.New_by_ints(209,159)
		, byte_208_176 = Bry_.New_by_ints(208,176)
		, byte_208_177 = Bry_.New_by_ints(208,177)
		, byte_208_178 = Bry_.New_by_ints(208,178)
		, byte_208_179 = Bry_.New_by_ints(208,179)
		, byte_208_180 = Bry_.New_by_ints(208,180)
		, byte_208_181 = Bry_.New_by_ints(208,181)
		, byte_208_182 = Bry_.New_by_ints(208,182)
		, byte_208_183 = Bry_.New_by_ints(208,183)
		, byte_208_184 = Bry_.New_by_ints(208,184)
		, byte_208_185 = Bry_.New_by_ints(208,185)
		, byte_208_186 = Bry_.New_by_ints(208,186)
		, byte_208_187 = Bry_.New_by_ints(208,187)
		, byte_208_188 = Bry_.New_by_ints(208,188)
		, byte_208_189 = Bry_.New_by_ints(208,189)
		, byte_208_190 = Bry_.New_by_ints(208,190)
		, byte_208_191 = Bry_.New_by_ints(208,191)
		, byte_209_128 = Bry_.New_by_ints(209,128)
		, byte_209_129 = Bry_.New_by_ints(209,129)
		, byte_209_130 = Bry_.New_by_ints(209,130)
		, byte_209_131 = Bry_.New_by_ints(209,131)
		, byte_209_132 = Bry_.New_by_ints(209,132)
		, byte_209_133 = Bry_.New_by_ints(209,133)
		, byte_209_134 = Bry_.New_by_ints(209,134)
		, byte_209_135 = Bry_.New_by_ints(209,135)
		, byte_209_136 = Bry_.New_by_ints(209,136)
		, byte_209_137 = Bry_.New_by_ints(209,137)
		, byte_209_138 = Bry_.New_by_ints(209,138)
		, byte_209_139 = Bry_.New_by_ints(209,139)
		, byte_209_140 = Bry_.New_by_ints(209,140)
		, byte_209_141 = Bry_.New_by_ints(209,141)
		, byte_209_142 = Bry_.New_by_ints(209,142)
		, byte_209_143 = Bry_.New_by_ints(209,143)
		, byte_209_161 = Bry_.New_by_ints(209,161)
		, byte_209_163 = Bry_.New_by_ints(209,163)
		, byte_209_165 = Bry_.New_by_ints(209,165)
		, byte_209_167 = Bry_.New_by_ints(209,167)
		, byte_209_169 = Bry_.New_by_ints(209,169)
		, byte_209_171 = Bry_.New_by_ints(209,171)
		, byte_209_173 = Bry_.New_by_ints(209,173)
		, byte_209_175 = Bry_.New_by_ints(209,175)
		, byte_209_177 = Bry_.New_by_ints(209,177)
		, byte_209_179 = Bry_.New_by_ints(209,179)
		, byte_209_181 = Bry_.New_by_ints(209,181)
		, byte_209_183 = Bry_.New_by_ints(209,183)
		, byte_209_185 = Bry_.New_by_ints(209,185)
		, byte_209_187 = Bry_.New_by_ints(209,187)
		, byte_209_189 = Bry_.New_by_ints(209,189)
		, byte_209_191 = Bry_.New_by_ints(209,191)
		, byte_210_129 = Bry_.New_by_ints(210,129)
		, byte_210_139 = Bry_.New_by_ints(210,139)
		, byte_210_141 = Bry_.New_by_ints(210,141)
		, byte_210_143 = Bry_.New_by_ints(210,143)
		, byte_210_145 = Bry_.New_by_ints(210,145)
		, byte_210_147 = Bry_.New_by_ints(210,147)
		, byte_210_149 = Bry_.New_by_ints(210,149)
		, byte_210_151 = Bry_.New_by_ints(210,151)
		, byte_210_153 = Bry_.New_by_ints(210,153)
		, byte_210_155 = Bry_.New_by_ints(210,155)
		, byte_210_157 = Bry_.New_by_ints(210,157)
		, byte_210_159 = Bry_.New_by_ints(210,159)
		, byte_210_161 = Bry_.New_by_ints(210,161)
		, byte_210_163 = Bry_.New_by_ints(210,163)
		, byte_210_165 = Bry_.New_by_ints(210,165)
		, byte_210_167 = Bry_.New_by_ints(210,167)
		, byte_210_169 = Bry_.New_by_ints(210,169)
		, byte_210_171 = Bry_.New_by_ints(210,171)
		, byte_210_173 = Bry_.New_by_ints(210,173)
		, byte_210_175 = Bry_.New_by_ints(210,175)
		, byte_210_177 = Bry_.New_by_ints(210,177)
		, byte_210_179 = Bry_.New_by_ints(210,179)
		, byte_210_181 = Bry_.New_by_ints(210,181)
		, byte_210_183 = Bry_.New_by_ints(210,183)
		, byte_210_185 = Bry_.New_by_ints(210,185)
		, byte_210_187 = Bry_.New_by_ints(210,187)
		, byte_210_189 = Bry_.New_by_ints(210,189)
		, byte_210_191 = Bry_.New_by_ints(210,191)
		, byte_211_143 = Bry_.New_by_ints(211,143)
		, byte_211_130 = Bry_.New_by_ints(211,130)
		, byte_211_132 = Bry_.New_by_ints(211,132)
		, byte_211_134 = Bry_.New_by_ints(211,134)
		, byte_211_136 = Bry_.New_by_ints(211,136)
		, byte_211_138 = Bry_.New_by_ints(211,138)
		, byte_211_140 = Bry_.New_by_ints(211,140)
		, byte_211_142 = Bry_.New_by_ints(211,142)
		, byte_211_145 = Bry_.New_by_ints(211,145)
		, byte_211_147 = Bry_.New_by_ints(211,147)
		, byte_211_149 = Bry_.New_by_ints(211,149)
		, byte_211_151 = Bry_.New_by_ints(211,151)
		, byte_211_153 = Bry_.New_by_ints(211,153)
		, byte_211_155 = Bry_.New_by_ints(211,155)
		, byte_211_157 = Bry_.New_by_ints(211,157)
		, byte_211_159 = Bry_.New_by_ints(211,159)
		, byte_211_161 = Bry_.New_by_ints(211,161)
		, byte_211_163 = Bry_.New_by_ints(211,163)
		, byte_211_165 = Bry_.New_by_ints(211,165)
		, byte_211_167 = Bry_.New_by_ints(211,167)
		, byte_211_169 = Bry_.New_by_ints(211,169)
		, byte_211_171 = Bry_.New_by_ints(211,171)
		, byte_211_173 = Bry_.New_by_ints(211,173)
		, byte_211_175 = Bry_.New_by_ints(211,175)
		, byte_211_177 = Bry_.New_by_ints(211,177)
		, byte_211_179 = Bry_.New_by_ints(211,179)
		, byte_211_181 = Bry_.New_by_ints(211,181)
		, byte_211_183 = Bry_.New_by_ints(211,183)
		, byte_211_185 = Bry_.New_by_ints(211,185)
		, byte_211_187 = Bry_.New_by_ints(211,187)
		, byte_211_189 = Bry_.New_by_ints(211,189)
		, byte_211_191 = Bry_.New_by_ints(211,191)
		, byte_212_129 = Bry_.New_by_ints(212,129)
		, byte_212_131 = Bry_.New_by_ints(212,131)
		, byte_212_133 = Bry_.New_by_ints(212,133)
		, byte_212_135 = Bry_.New_by_ints(212,135)
		, byte_212_137 = Bry_.New_by_ints(212,137)
		, byte_212_139 = Bry_.New_by_ints(212,139)
		, byte_212_141 = Bry_.New_by_ints(212,141)
		, byte_212_143 = Bry_.New_by_ints(212,143)
		, byte_212_145 = Bry_.New_by_ints(212,145)
		, byte_212_147 = Bry_.New_by_ints(212,147)
		, byte_212_149 = Bry_.New_by_ints(212,149)
		, byte_212_151 = Bry_.New_by_ints(212,151)
		, byte_212_153 = Bry_.New_by_ints(212,153)
		, byte_212_155 = Bry_.New_by_ints(212,155)
		, byte_212_157 = Bry_.New_by_ints(212,157)
		, byte_212_159 = Bry_.New_by_ints(212,159)
		, byte_212_161 = Bry_.New_by_ints(212,161)
		, byte_212_163 = Bry_.New_by_ints(212,163)
		, byte_212_165 = Bry_.New_by_ints(212,165)
		, byte_212_167 = Bry_.New_by_ints(212,167)
		, byte_213_161 = Bry_.New_by_ints(213,161)
		, byte_213_162 = Bry_.New_by_ints(213,162)
		, byte_213_163 = Bry_.New_by_ints(213,163)
		, byte_213_164 = Bry_.New_by_ints(213,164)
		, byte_213_165 = Bry_.New_by_ints(213,165)
		, byte_213_166 = Bry_.New_by_ints(213,166)
		, byte_213_167 = Bry_.New_by_ints(213,167)
		, byte_213_168 = Bry_.New_by_ints(213,168)
		, byte_213_169 = Bry_.New_by_ints(213,169)
		, byte_213_170 = Bry_.New_by_ints(213,170)
		, byte_213_171 = Bry_.New_by_ints(213,171)
		, byte_213_172 = Bry_.New_by_ints(213,172)
		, byte_213_173 = Bry_.New_by_ints(213,173)
		, byte_213_174 = Bry_.New_by_ints(213,174)
		, byte_213_175 = Bry_.New_by_ints(213,175)
		, byte_213_176 = Bry_.New_by_ints(213,176)
		, byte_213_177 = Bry_.New_by_ints(213,177)
		, byte_213_178 = Bry_.New_by_ints(213,178)
		, byte_213_179 = Bry_.New_by_ints(213,179)
		, byte_213_180 = Bry_.New_by_ints(213,180)
		, byte_213_181 = Bry_.New_by_ints(213,181)
		, byte_213_182 = Bry_.New_by_ints(213,182)
		, byte_213_183 = Bry_.New_by_ints(213,183)
		, byte_213_184 = Bry_.New_by_ints(213,184)
		, byte_213_185 = Bry_.New_by_ints(213,185)
		, byte_213_186 = Bry_.New_by_ints(213,186)
		, byte_213_187 = Bry_.New_by_ints(213,187)
		, byte_213_188 = Bry_.New_by_ints(213,188)
		, byte_213_189 = Bry_.New_by_ints(213,189)
		, byte_213_190 = Bry_.New_by_ints(213,190)
		, byte_213_191 = Bry_.New_by_ints(213,191)
		, byte_214_128 = Bry_.New_by_ints(214,128)
		, byte_214_129 = Bry_.New_by_ints(214,129)
		, byte_214_130 = Bry_.New_by_ints(214,130)
		, byte_214_131 = Bry_.New_by_ints(214,131)
		, byte_214_132 = Bry_.New_by_ints(214,132)
		, byte_214_133 = Bry_.New_by_ints(214,133)
		, byte_214_134 = Bry_.New_by_ints(214,134)
		, byte_226_180_128 = Bry_.New_by_ints(226,180,128)
		, byte_226_180_129 = Bry_.New_by_ints(226,180,129)
		, byte_226_180_130 = Bry_.New_by_ints(226,180,130)
		, byte_226_180_131 = Bry_.New_by_ints(226,180,131)
		, byte_226_180_132 = Bry_.New_by_ints(226,180,132)
		, byte_226_180_133 = Bry_.New_by_ints(226,180,133)
		, byte_226_180_134 = Bry_.New_by_ints(226,180,134)
		, byte_226_180_135 = Bry_.New_by_ints(226,180,135)
		, byte_226_180_136 = Bry_.New_by_ints(226,180,136)
		, byte_226_180_137 = Bry_.New_by_ints(226,180,137)
		, byte_226_180_138 = Bry_.New_by_ints(226,180,138)
		, byte_226_180_139 = Bry_.New_by_ints(226,180,139)
		, byte_226_180_140 = Bry_.New_by_ints(226,180,140)
		, byte_226_180_141 = Bry_.New_by_ints(226,180,141)
		, byte_226_180_142 = Bry_.New_by_ints(226,180,142)
		, byte_226_180_143 = Bry_.New_by_ints(226,180,143)
		, byte_226_180_144 = Bry_.New_by_ints(226,180,144)
		, byte_226_180_145 = Bry_.New_by_ints(226,180,145)
		, byte_226_180_146 = Bry_.New_by_ints(226,180,146)
		, byte_226_180_147 = Bry_.New_by_ints(226,180,147)
		, byte_226_180_148 = Bry_.New_by_ints(226,180,148)
		, byte_226_180_149 = Bry_.New_by_ints(226,180,149)
		, byte_226_180_150 = Bry_.New_by_ints(226,180,150)
		, byte_226_180_151 = Bry_.New_by_ints(226,180,151)
		, byte_226_180_152 = Bry_.New_by_ints(226,180,152)
		, byte_226_180_153 = Bry_.New_by_ints(226,180,153)
		, byte_226_180_154 = Bry_.New_by_ints(226,180,154)
		, byte_226_180_155 = Bry_.New_by_ints(226,180,155)
		, byte_226_180_156 = Bry_.New_by_ints(226,180,156)
		, byte_226_180_157 = Bry_.New_by_ints(226,180,157)
		, byte_226_180_158 = Bry_.New_by_ints(226,180,158)
		, byte_226_180_159 = Bry_.New_by_ints(226,180,159)
		, byte_226_180_160 = Bry_.New_by_ints(226,180,160)
		, byte_226_180_161 = Bry_.New_by_ints(226,180,161)
		, byte_226_180_162 = Bry_.New_by_ints(226,180,162)
		, byte_226_180_163 = Bry_.New_by_ints(226,180,163)
		, byte_226_180_164 = Bry_.New_by_ints(226,180,164)
		, byte_226_180_165 = Bry_.New_by_ints(226,180,165)
		, byte_226_180_167 = Bry_.New_by_ints(226,180,167)
		, byte_226_180_173 = Bry_.New_by_ints(226,180,173)
		, byte_225_184_129 = Bry_.New_by_ints(225,184,129)
		, byte_225_184_131 = Bry_.New_by_ints(225,184,131)
		, byte_225_184_133 = Bry_.New_by_ints(225,184,133)
		, byte_225_184_135 = Bry_.New_by_ints(225,184,135)
		, byte_225_184_137 = Bry_.New_by_ints(225,184,137)
		, byte_225_184_139 = Bry_.New_by_ints(225,184,139)
		, byte_225_184_141 = Bry_.New_by_ints(225,184,141)
		, byte_225_184_143 = Bry_.New_by_ints(225,184,143)
		, byte_225_184_145 = Bry_.New_by_ints(225,184,145)
		, byte_225_184_147 = Bry_.New_by_ints(225,184,147)
		, byte_225_184_149 = Bry_.New_by_ints(225,184,149)
		, byte_225_184_151 = Bry_.New_by_ints(225,184,151)
		, byte_225_184_153 = Bry_.New_by_ints(225,184,153)
		, byte_225_184_155 = Bry_.New_by_ints(225,184,155)
		, byte_225_184_157 = Bry_.New_by_ints(225,184,157)
		, byte_225_184_159 = Bry_.New_by_ints(225,184,159)
		, byte_225_184_161 = Bry_.New_by_ints(225,184,161)
		, byte_225_184_163 = Bry_.New_by_ints(225,184,163)
		, byte_225_184_165 = Bry_.New_by_ints(225,184,165)
		, byte_225_184_167 = Bry_.New_by_ints(225,184,167)
		, byte_225_184_169 = Bry_.New_by_ints(225,184,169)
		, byte_225_184_171 = Bry_.New_by_ints(225,184,171)
		, byte_225_184_173 = Bry_.New_by_ints(225,184,173)
		, byte_225_184_175 = Bry_.New_by_ints(225,184,175)
		, byte_225_184_177 = Bry_.New_by_ints(225,184,177)
		, byte_225_184_179 = Bry_.New_by_ints(225,184,179)
		, byte_225_184_181 = Bry_.New_by_ints(225,184,181)
		, byte_225_184_183 = Bry_.New_by_ints(225,184,183)
		, byte_225_184_185 = Bry_.New_by_ints(225,184,185)
		, byte_225_184_187 = Bry_.New_by_ints(225,184,187)
		, byte_225_184_189 = Bry_.New_by_ints(225,184,189)
		, byte_225_184_191 = Bry_.New_by_ints(225,184,191)
		, byte_225_185_129 = Bry_.New_by_ints(225,185,129)
		, byte_225_185_131 = Bry_.New_by_ints(225,185,131)
		, byte_225_185_133 = Bry_.New_by_ints(225,185,133)
		, byte_225_185_135 = Bry_.New_by_ints(225,185,135)
		, byte_225_185_137 = Bry_.New_by_ints(225,185,137)
		, byte_225_185_139 = Bry_.New_by_ints(225,185,139)
		, byte_225_185_141 = Bry_.New_by_ints(225,185,141)
		, byte_225_185_143 = Bry_.New_by_ints(225,185,143)
		, byte_225_185_145 = Bry_.New_by_ints(225,185,145)
		, byte_225_185_147 = Bry_.New_by_ints(225,185,147)
		, byte_225_185_149 = Bry_.New_by_ints(225,185,149)
		, byte_225_185_151 = Bry_.New_by_ints(225,185,151)
		, byte_225_185_153 = Bry_.New_by_ints(225,185,153)
		, byte_225_185_155 = Bry_.New_by_ints(225,185,155)
		, byte_225_185_157 = Bry_.New_by_ints(225,185,157)
		, byte_225_185_159 = Bry_.New_by_ints(225,185,159)
		, byte_225_185_161 = Bry_.New_by_ints(225,185,161)
		, byte_225_185_163 = Bry_.New_by_ints(225,185,163)
		, byte_225_185_165 = Bry_.New_by_ints(225,185,165)
		, byte_225_185_167 = Bry_.New_by_ints(225,185,167)
		, byte_225_185_169 = Bry_.New_by_ints(225,185,169)
		, byte_225_185_171 = Bry_.New_by_ints(225,185,171)
		, byte_225_185_173 = Bry_.New_by_ints(225,185,173)
		, byte_225_185_175 = Bry_.New_by_ints(225,185,175)
		, byte_225_185_177 = Bry_.New_by_ints(225,185,177)
		, byte_225_185_179 = Bry_.New_by_ints(225,185,179)
		, byte_225_185_181 = Bry_.New_by_ints(225,185,181)
		, byte_225_185_183 = Bry_.New_by_ints(225,185,183)
		, byte_225_185_185 = Bry_.New_by_ints(225,185,185)
		, byte_225_185_187 = Bry_.New_by_ints(225,185,187)
		, byte_225_185_189 = Bry_.New_by_ints(225,185,189)
		, byte_225_185_191 = Bry_.New_by_ints(225,185,191)
		, byte_225_186_129 = Bry_.New_by_ints(225,186,129)
		, byte_225_186_131 = Bry_.New_by_ints(225,186,131)
		, byte_225_186_133 = Bry_.New_by_ints(225,186,133)
		, byte_225_186_135 = Bry_.New_by_ints(225,186,135)
		, byte_225_186_137 = Bry_.New_by_ints(225,186,137)
		, byte_225_186_139 = Bry_.New_by_ints(225,186,139)
		, byte_225_186_141 = Bry_.New_by_ints(225,186,141)
		, byte_225_186_143 = Bry_.New_by_ints(225,186,143)
		, byte_225_186_145 = Bry_.New_by_ints(225,186,145)
		, byte_225_186_147 = Bry_.New_by_ints(225,186,147)
		, byte_225_186_149 = Bry_.New_by_ints(225,186,149)
		, byte_195_159 = Bry_.New_by_ints(195,159)
		, byte_225_186_161 = Bry_.New_by_ints(225,186,161)
		, byte_225_186_163 = Bry_.New_by_ints(225,186,163)
		, byte_225_186_165 = Bry_.New_by_ints(225,186,165)
		, byte_225_186_167 = Bry_.New_by_ints(225,186,167)
		, byte_225_186_169 = Bry_.New_by_ints(225,186,169)
		, byte_225_186_171 = Bry_.New_by_ints(225,186,171)
		, byte_225_186_173 = Bry_.New_by_ints(225,186,173)
		, byte_225_186_175 = Bry_.New_by_ints(225,186,175)
		, byte_225_186_177 = Bry_.New_by_ints(225,186,177)
		, byte_225_186_179 = Bry_.New_by_ints(225,186,179)
		, byte_225_186_181 = Bry_.New_by_ints(225,186,181)
		, byte_225_186_183 = Bry_.New_by_ints(225,186,183)
		, byte_225_186_185 = Bry_.New_by_ints(225,186,185)
		, byte_225_186_187 = Bry_.New_by_ints(225,186,187)
		, byte_225_186_189 = Bry_.New_by_ints(225,186,189)
		, byte_225_186_191 = Bry_.New_by_ints(225,186,191)
		, byte_225_187_129 = Bry_.New_by_ints(225,187,129)
		, byte_225_187_131 = Bry_.New_by_ints(225,187,131)
		, byte_225_187_133 = Bry_.New_by_ints(225,187,133)
		, byte_225_187_135 = Bry_.New_by_ints(225,187,135)
		, byte_225_187_137 = Bry_.New_by_ints(225,187,137)
		, byte_225_187_139 = Bry_.New_by_ints(225,187,139)
		, byte_225_187_141 = Bry_.New_by_ints(225,187,141)
		, byte_225_187_143 = Bry_.New_by_ints(225,187,143)
		, byte_225_187_145 = Bry_.New_by_ints(225,187,145)
		, byte_225_187_147 = Bry_.New_by_ints(225,187,147)
		, byte_225_187_149 = Bry_.New_by_ints(225,187,149)
		, byte_225_187_151 = Bry_.New_by_ints(225,187,151)
		, byte_225_187_153 = Bry_.New_by_ints(225,187,153)
		, byte_225_187_155 = Bry_.New_by_ints(225,187,155)
		, byte_225_187_157 = Bry_.New_by_ints(225,187,157)
		, byte_225_187_159 = Bry_.New_by_ints(225,187,159)
		, byte_225_187_161 = Bry_.New_by_ints(225,187,161)
		, byte_225_187_163 = Bry_.New_by_ints(225,187,163)
		, byte_225_187_165 = Bry_.New_by_ints(225,187,165)
		, byte_225_187_167 = Bry_.New_by_ints(225,187,167)
		, byte_225_187_169 = Bry_.New_by_ints(225,187,169)
		, byte_225_187_171 = Bry_.New_by_ints(225,187,171)
		, byte_225_187_173 = Bry_.New_by_ints(225,187,173)
		, byte_225_187_175 = Bry_.New_by_ints(225,187,175)
		, byte_225_187_177 = Bry_.New_by_ints(225,187,177)
		, byte_225_187_179 = Bry_.New_by_ints(225,187,179)
		, byte_225_187_181 = Bry_.New_by_ints(225,187,181)
		, byte_225_187_183 = Bry_.New_by_ints(225,187,183)
		, byte_225_187_185 = Bry_.New_by_ints(225,187,185)
		, byte_225_187_187 = Bry_.New_by_ints(225,187,187)
		, byte_225_187_189 = Bry_.New_by_ints(225,187,189)
		, byte_225_187_191 = Bry_.New_by_ints(225,187,191)
		, byte_225_188_128 = Bry_.New_by_ints(225,188,128)
		, byte_225_188_129 = Bry_.New_by_ints(225,188,129)
		, byte_225_188_130 = Bry_.New_by_ints(225,188,130)
		, byte_225_188_131 = Bry_.New_by_ints(225,188,131)
		, byte_225_188_132 = Bry_.New_by_ints(225,188,132)
		, byte_225_188_133 = Bry_.New_by_ints(225,188,133)
		, byte_225_188_134 = Bry_.New_by_ints(225,188,134)
		, byte_225_188_135 = Bry_.New_by_ints(225,188,135)
		, byte_225_188_144 = Bry_.New_by_ints(225,188,144)
		, byte_225_188_145 = Bry_.New_by_ints(225,188,145)
		, byte_225_188_146 = Bry_.New_by_ints(225,188,146)
		, byte_225_188_147 = Bry_.New_by_ints(225,188,147)
		, byte_225_188_148 = Bry_.New_by_ints(225,188,148)
		, byte_225_188_149 = Bry_.New_by_ints(225,188,149)
		, byte_225_188_160 = Bry_.New_by_ints(225,188,160)
		, byte_225_188_161 = Bry_.New_by_ints(225,188,161)
		, byte_225_188_162 = Bry_.New_by_ints(225,188,162)
		, byte_225_188_163 = Bry_.New_by_ints(225,188,163)
		, byte_225_188_164 = Bry_.New_by_ints(225,188,164)
		, byte_225_188_165 = Bry_.New_by_ints(225,188,165)
		, byte_225_188_166 = Bry_.New_by_ints(225,188,166)
		, byte_225_188_167 = Bry_.New_by_ints(225,188,167)
		, byte_225_188_176 = Bry_.New_by_ints(225,188,176)
		, byte_225_188_177 = Bry_.New_by_ints(225,188,177)
		, byte_225_188_178 = Bry_.New_by_ints(225,188,178)
		, byte_225_188_179 = Bry_.New_by_ints(225,188,179)
		, byte_225_188_180 = Bry_.New_by_ints(225,188,180)
		, byte_225_188_181 = Bry_.New_by_ints(225,188,181)
		, byte_225_188_182 = Bry_.New_by_ints(225,188,182)
		, byte_225_188_183 = Bry_.New_by_ints(225,188,183)
		, byte_225_189_128 = Bry_.New_by_ints(225,189,128)
		, byte_225_189_129 = Bry_.New_by_ints(225,189,129)
		, byte_225_189_130 = Bry_.New_by_ints(225,189,130)
		, byte_225_189_131 = Bry_.New_by_ints(225,189,131)
		, byte_225_189_132 = Bry_.New_by_ints(225,189,132)
		, byte_225_189_133 = Bry_.New_by_ints(225,189,133)
		, byte_225_189_145 = Bry_.New_by_ints(225,189,145)
		, byte_225_189_147 = Bry_.New_by_ints(225,189,147)
		, byte_225_189_149 = Bry_.New_by_ints(225,189,149)
		, byte_225_189_151 = Bry_.New_by_ints(225,189,151)
		, byte_225_189_160 = Bry_.New_by_ints(225,189,160)
		, byte_225_189_161 = Bry_.New_by_ints(225,189,161)
		, byte_225_189_162 = Bry_.New_by_ints(225,189,162)
		, byte_225_189_163 = Bry_.New_by_ints(225,189,163)
		, byte_225_189_164 = Bry_.New_by_ints(225,189,164)
		, byte_225_189_165 = Bry_.New_by_ints(225,189,165)
		, byte_225_189_166 = Bry_.New_by_ints(225,189,166)
		, byte_225_189_167 = Bry_.New_by_ints(225,189,167)
		, byte_225_190_128 = Bry_.New_by_ints(225,190,128)
		, byte_225_190_129 = Bry_.New_by_ints(225,190,129)
		, byte_225_190_130 = Bry_.New_by_ints(225,190,130)
		, byte_225_190_131 = Bry_.New_by_ints(225,190,131)
		, byte_225_190_132 = Bry_.New_by_ints(225,190,132)
		, byte_225_190_133 = Bry_.New_by_ints(225,190,133)
		, byte_225_190_134 = Bry_.New_by_ints(225,190,134)
		, byte_225_190_135 = Bry_.New_by_ints(225,190,135)
		, byte_225_190_144 = Bry_.New_by_ints(225,190,144)
		, byte_225_190_145 = Bry_.New_by_ints(225,190,145)
		, byte_225_190_146 = Bry_.New_by_ints(225,190,146)
		, byte_225_190_147 = Bry_.New_by_ints(225,190,147)
		, byte_225_190_148 = Bry_.New_by_ints(225,190,148)
		, byte_225_190_149 = Bry_.New_by_ints(225,190,149)
		, byte_225_190_150 = Bry_.New_by_ints(225,190,150)
		, byte_225_190_151 = Bry_.New_by_ints(225,190,151)
		, byte_225_190_160 = Bry_.New_by_ints(225,190,160)
		, byte_225_190_161 = Bry_.New_by_ints(225,190,161)
		, byte_225_190_162 = Bry_.New_by_ints(225,190,162)
		, byte_225_190_163 = Bry_.New_by_ints(225,190,163)
		, byte_225_190_164 = Bry_.New_by_ints(225,190,164)
		, byte_225_190_165 = Bry_.New_by_ints(225,190,165)
		, byte_225_190_166 = Bry_.New_by_ints(225,190,166)
		, byte_225_190_167 = Bry_.New_by_ints(225,190,167)
		, byte_225_190_176 = Bry_.New_by_ints(225,190,176)
		, byte_225_190_177 = Bry_.New_by_ints(225,190,177)
		, byte_225_189_176 = Bry_.New_by_ints(225,189,176)
		, byte_225_189_177 = Bry_.New_by_ints(225,189,177)
		, byte_225_190_179 = Bry_.New_by_ints(225,190,179)
		, byte_225_189_178 = Bry_.New_by_ints(225,189,178)
		, byte_225_189_179 = Bry_.New_by_ints(225,189,179)
		, byte_225_189_180 = Bry_.New_by_ints(225,189,180)
		, byte_225_189_181 = Bry_.New_by_ints(225,189,181)
		, byte_225_191_131 = Bry_.New_by_ints(225,191,131)
		, byte_225_191_144 = Bry_.New_by_ints(225,191,144)
		, byte_225_191_145 = Bry_.New_by_ints(225,191,145)
		, byte_225_189_182 = Bry_.New_by_ints(225,189,182)
		, byte_225_189_183 = Bry_.New_by_ints(225,189,183)
		, byte_225_191_160 = Bry_.New_by_ints(225,191,160)
		, byte_225_191_161 = Bry_.New_by_ints(225,191,161)
		, byte_225_189_186 = Bry_.New_by_ints(225,189,186)
		, byte_225_189_187 = Bry_.New_by_ints(225,189,187)
		, byte_225_191_165 = Bry_.New_by_ints(225,191,165)
		, byte_225_189_184 = Bry_.New_by_ints(225,189,184)
		, byte_225_189_185 = Bry_.New_by_ints(225,189,185)
		, byte_225_189_188 = Bry_.New_by_ints(225,189,188)
		, byte_225_189_189 = Bry_.New_by_ints(225,189,189)
		, byte_225_191_179 = Bry_.New_by_ints(225,191,179)
		, byte_226_133_142 = Bry_.New_by_ints(226,133,142)
		, byte_226_133_176 = Bry_.New_by_ints(226,133,176)
		, byte_226_133_177 = Bry_.New_by_ints(226,133,177)
		, byte_226_133_178 = Bry_.New_by_ints(226,133,178)
		, byte_226_133_179 = Bry_.New_by_ints(226,133,179)
		, byte_226_133_180 = Bry_.New_by_ints(226,133,180)
		, byte_226_133_181 = Bry_.New_by_ints(226,133,181)
		, byte_226_133_182 = Bry_.New_by_ints(226,133,182)
		, byte_226_133_183 = Bry_.New_by_ints(226,133,183)
		, byte_226_133_184 = Bry_.New_by_ints(226,133,184)
		, byte_226_133_185 = Bry_.New_by_ints(226,133,185)
		, byte_226_133_186 = Bry_.New_by_ints(226,133,186)
		, byte_226_133_187 = Bry_.New_by_ints(226,133,187)
		, byte_226_133_188 = Bry_.New_by_ints(226,133,188)
		, byte_226_133_189 = Bry_.New_by_ints(226,133,189)
		, byte_226_133_190 = Bry_.New_by_ints(226,133,190)
		, byte_226_133_191 = Bry_.New_by_ints(226,133,191)
		, byte_226_134_132 = Bry_.New_by_ints(226,134,132)
		, byte_226_147_144 = Bry_.New_by_ints(226,147,144)
		, byte_226_147_145 = Bry_.New_by_ints(226,147,145)
		, byte_226_147_146 = Bry_.New_by_ints(226,147,146)
		, byte_226_147_147 = Bry_.New_by_ints(226,147,147)
		, byte_226_147_148 = Bry_.New_by_ints(226,147,148)
		, byte_226_147_149 = Bry_.New_by_ints(226,147,149)
		, byte_226_147_150 = Bry_.New_by_ints(226,147,150)
		, byte_226_147_151 = Bry_.New_by_ints(226,147,151)
		, byte_226_147_152 = Bry_.New_by_ints(226,147,152)
		, byte_226_147_153 = Bry_.New_by_ints(226,147,153)
		, byte_226_147_154 = Bry_.New_by_ints(226,147,154)
		, byte_226_147_155 = Bry_.New_by_ints(226,147,155)
		, byte_226_147_156 = Bry_.New_by_ints(226,147,156)
		, byte_226_147_157 = Bry_.New_by_ints(226,147,157)
		, byte_226_147_158 = Bry_.New_by_ints(226,147,158)
		, byte_226_147_159 = Bry_.New_by_ints(226,147,159)
		, byte_226_147_160 = Bry_.New_by_ints(226,147,160)
		, byte_226_147_161 = Bry_.New_by_ints(226,147,161)
		, byte_226_147_162 = Bry_.New_by_ints(226,147,162)
		, byte_226_147_163 = Bry_.New_by_ints(226,147,163)
		, byte_226_147_164 = Bry_.New_by_ints(226,147,164)
		, byte_226_147_165 = Bry_.New_by_ints(226,147,165)
		, byte_226_147_166 = Bry_.New_by_ints(226,147,166)
		, byte_226_147_167 = Bry_.New_by_ints(226,147,167)
		, byte_226_147_168 = Bry_.New_by_ints(226,147,168)
		, byte_226_147_169 = Bry_.New_by_ints(226,147,169)
		, byte_226_176_176 = Bry_.New_by_ints(226,176,176)
		, byte_226_176_177 = Bry_.New_by_ints(226,176,177)
		, byte_226_176_178 = Bry_.New_by_ints(226,176,178)
		, byte_226_176_179 = Bry_.New_by_ints(226,176,179)
		, byte_226_176_180 = Bry_.New_by_ints(226,176,180)
		, byte_226_176_181 = Bry_.New_by_ints(226,176,181)
		, byte_226_176_182 = Bry_.New_by_ints(226,176,182)
		, byte_226_176_183 = Bry_.New_by_ints(226,176,183)
		, byte_226_176_184 = Bry_.New_by_ints(226,176,184)
		, byte_226_176_185 = Bry_.New_by_ints(226,176,185)
		, byte_226_176_186 = Bry_.New_by_ints(226,176,186)
		, byte_226_176_187 = Bry_.New_by_ints(226,176,187)
		, byte_226_176_188 = Bry_.New_by_ints(226,176,188)
		, byte_226_176_189 = Bry_.New_by_ints(226,176,189)
		, byte_226_176_190 = Bry_.New_by_ints(226,176,190)
		, byte_226_176_191 = Bry_.New_by_ints(226,176,191)
		, byte_226_177_128 = Bry_.New_by_ints(226,177,128)
		, byte_226_177_129 = Bry_.New_by_ints(226,177,129)
		, byte_226_177_130 = Bry_.New_by_ints(226,177,130)
		, byte_226_177_131 = Bry_.New_by_ints(226,177,131)
		, byte_226_177_132 = Bry_.New_by_ints(226,177,132)
		, byte_226_177_133 = Bry_.New_by_ints(226,177,133)
		, byte_226_177_134 = Bry_.New_by_ints(226,177,134)
		, byte_226_177_135 = Bry_.New_by_ints(226,177,135)
		, byte_226_177_136 = Bry_.New_by_ints(226,177,136)
		, byte_226_177_137 = Bry_.New_by_ints(226,177,137)
		, byte_226_177_138 = Bry_.New_by_ints(226,177,138)
		, byte_226_177_139 = Bry_.New_by_ints(226,177,139)
		, byte_226_177_140 = Bry_.New_by_ints(226,177,140)
		, byte_226_177_141 = Bry_.New_by_ints(226,177,141)
		, byte_226_177_142 = Bry_.New_by_ints(226,177,142)
		, byte_226_177_143 = Bry_.New_by_ints(226,177,143)
		, byte_226_177_144 = Bry_.New_by_ints(226,177,144)
		, byte_226_177_145 = Bry_.New_by_ints(226,177,145)
		, byte_226_177_146 = Bry_.New_by_ints(226,177,146)
		, byte_226_177_147 = Bry_.New_by_ints(226,177,147)
		, byte_226_177_148 = Bry_.New_by_ints(226,177,148)
		, byte_226_177_149 = Bry_.New_by_ints(226,177,149)
		, byte_226_177_150 = Bry_.New_by_ints(226,177,150)
		, byte_226_177_151 = Bry_.New_by_ints(226,177,151)
		, byte_226_177_152 = Bry_.New_by_ints(226,177,152)
		, byte_226_177_153 = Bry_.New_by_ints(226,177,153)
		, byte_226_177_154 = Bry_.New_by_ints(226,177,154)
		, byte_226_177_155 = Bry_.New_by_ints(226,177,155)
		, byte_226_177_156 = Bry_.New_by_ints(226,177,156)
		, byte_226_177_157 = Bry_.New_by_ints(226,177,157)
		, byte_226_177_158 = Bry_.New_by_ints(226,177,158)
		, byte_226_177_161 = Bry_.New_by_ints(226,177,161)
		, byte_201_171 = Bry_.New_by_ints(201,171)
		, byte_225_181_189 = Bry_.New_by_ints(225,181,189)
		, byte_201_189 = Bry_.New_by_ints(201,189)
		, byte_226_177_168 = Bry_.New_by_ints(226,177,168)
		, byte_226_177_170 = Bry_.New_by_ints(226,177,170)
		, byte_226_177_172 = Bry_.New_by_ints(226,177,172)
		, byte_201_145 = Bry_.New_by_ints(201,145)
		, byte_201_177 = Bry_.New_by_ints(201,177)
		, byte_201_144 = Bry_.New_by_ints(201,144)
		, byte_201_146 = Bry_.New_by_ints(201,146)
		, byte_226_177_179 = Bry_.New_by_ints(226,177,179)
		, byte_226_177_182 = Bry_.New_by_ints(226,177,182)
		, byte_200_191 = Bry_.New_by_ints(200,191)
		, byte_201_128 = Bry_.New_by_ints(201,128)
		, byte_226_178_129 = Bry_.New_by_ints(226,178,129)
		, byte_226_178_131 = Bry_.New_by_ints(226,178,131)
		, byte_226_178_133 = Bry_.New_by_ints(226,178,133)
		, byte_226_178_135 = Bry_.New_by_ints(226,178,135)
		, byte_226_178_137 = Bry_.New_by_ints(226,178,137)
		, byte_226_178_139 = Bry_.New_by_ints(226,178,139)
		, byte_226_178_141 = Bry_.New_by_ints(226,178,141)
		, byte_226_178_143 = Bry_.New_by_ints(226,178,143)
		, byte_226_178_145 = Bry_.New_by_ints(226,178,145)
		, byte_226_178_147 = Bry_.New_by_ints(226,178,147)
		, byte_226_178_149 = Bry_.New_by_ints(226,178,149)
		, byte_226_178_151 = Bry_.New_by_ints(226,178,151)
		, byte_226_178_153 = Bry_.New_by_ints(226,178,153)
		, byte_226_178_155 = Bry_.New_by_ints(226,178,155)
		, byte_226_178_157 = Bry_.New_by_ints(226,178,157)
		, byte_226_178_159 = Bry_.New_by_ints(226,178,159)
		, byte_226_178_161 = Bry_.New_by_ints(226,178,161)
		, byte_226_178_163 = Bry_.New_by_ints(226,178,163)
		, byte_226_178_165 = Bry_.New_by_ints(226,178,165)
		, byte_226_178_167 = Bry_.New_by_ints(226,178,167)
		, byte_226_178_169 = Bry_.New_by_ints(226,178,169)
		, byte_226_178_171 = Bry_.New_by_ints(226,178,171)
		, byte_226_178_173 = Bry_.New_by_ints(226,178,173)
		, byte_226_178_175 = Bry_.New_by_ints(226,178,175)
		, byte_226_178_177 = Bry_.New_by_ints(226,178,177)
		, byte_226_178_179 = Bry_.New_by_ints(226,178,179)
		, byte_226_178_181 = Bry_.New_by_ints(226,178,181)
		, byte_226_178_183 = Bry_.New_by_ints(226,178,183)
		, byte_226_178_185 = Bry_.New_by_ints(226,178,185)
		, byte_226_178_187 = Bry_.New_by_ints(226,178,187)
		, byte_226_178_189 = Bry_.New_by_ints(226,178,189)
		, byte_226_178_191 = Bry_.New_by_ints(226,178,191)
		, byte_226_179_129 = Bry_.New_by_ints(226,179,129)
		, byte_226_179_131 = Bry_.New_by_ints(226,179,131)
		, byte_226_179_133 = Bry_.New_by_ints(226,179,133)
		, byte_226_179_135 = Bry_.New_by_ints(226,179,135)
		, byte_226_179_137 = Bry_.New_by_ints(226,179,137)
		, byte_226_179_139 = Bry_.New_by_ints(226,179,139)
		, byte_226_179_141 = Bry_.New_by_ints(226,179,141)
		, byte_226_179_143 = Bry_.New_by_ints(226,179,143)
		, byte_226_179_145 = Bry_.New_by_ints(226,179,145)
		, byte_226_179_147 = Bry_.New_by_ints(226,179,147)
		, byte_226_179_149 = Bry_.New_by_ints(226,179,149)
		, byte_226_179_151 = Bry_.New_by_ints(226,179,151)
		, byte_226_179_153 = Bry_.New_by_ints(226,179,153)
		, byte_226_179_155 = Bry_.New_by_ints(226,179,155)
		, byte_226_179_157 = Bry_.New_by_ints(226,179,157)
		, byte_226_179_159 = Bry_.New_by_ints(226,179,159)
		, byte_226_179_161 = Bry_.New_by_ints(226,179,161)
		, byte_226_179_163 = Bry_.New_by_ints(226,179,163)
		, byte_226_179_172 = Bry_.New_by_ints(226,179,172)
		, byte_226_179_174 = Bry_.New_by_ints(226,179,174)
		, byte_226_179_179 = Bry_.New_by_ints(226,179,179)
		, byte_234_153_129 = Bry_.New_by_ints(234,153,129)
		, byte_234_153_131 = Bry_.New_by_ints(234,153,131)
		, byte_234_153_133 = Bry_.New_by_ints(234,153,133)
		, byte_234_153_135 = Bry_.New_by_ints(234,153,135)
		, byte_234_153_137 = Bry_.New_by_ints(234,153,137)
		, byte_234_153_139 = Bry_.New_by_ints(234,153,139)
		, byte_234_153_141 = Bry_.New_by_ints(234,153,141)
		, byte_234_153_143 = Bry_.New_by_ints(234,153,143)
		, byte_234_153_145 = Bry_.New_by_ints(234,153,145)
		, byte_234_153_147 = Bry_.New_by_ints(234,153,147)
		, byte_234_153_149 = Bry_.New_by_ints(234,153,149)
		, byte_234_153_151 = Bry_.New_by_ints(234,153,151)
		, byte_234_153_153 = Bry_.New_by_ints(234,153,153)
		, byte_234_153_155 = Bry_.New_by_ints(234,153,155)
		, byte_234_153_157 = Bry_.New_by_ints(234,153,157)
		, byte_234_153_159 = Bry_.New_by_ints(234,153,159)
		, byte_234_153_161 = Bry_.New_by_ints(234,153,161)
		, byte_234_153_163 = Bry_.New_by_ints(234,153,163)
		, byte_234_153_165 = Bry_.New_by_ints(234,153,165)
		, byte_234_153_167 = Bry_.New_by_ints(234,153,167)
		, byte_234_153_169 = Bry_.New_by_ints(234,153,169)
		, byte_234_153_171 = Bry_.New_by_ints(234,153,171)
		, byte_234_153_173 = Bry_.New_by_ints(234,153,173)
		, byte_234_154_129 = Bry_.New_by_ints(234,154,129)
		, byte_234_154_131 = Bry_.New_by_ints(234,154,131)
		, byte_234_154_133 = Bry_.New_by_ints(234,154,133)
		, byte_234_154_135 = Bry_.New_by_ints(234,154,135)
		, byte_234_154_137 = Bry_.New_by_ints(234,154,137)
		, byte_234_154_139 = Bry_.New_by_ints(234,154,139)
		, byte_234_154_141 = Bry_.New_by_ints(234,154,141)
		, byte_234_154_143 = Bry_.New_by_ints(234,154,143)
		, byte_234_154_145 = Bry_.New_by_ints(234,154,145)
		, byte_234_154_147 = Bry_.New_by_ints(234,154,147)
		, byte_234_154_149 = Bry_.New_by_ints(234,154,149)
		, byte_234_154_151 = Bry_.New_by_ints(234,154,151)
		, byte_234_156_163 = Bry_.New_by_ints(234,156,163)
		, byte_234_156_165 = Bry_.New_by_ints(234,156,165)
		, byte_234_156_167 = Bry_.New_by_ints(234,156,167)
		, byte_234_156_169 = Bry_.New_by_ints(234,156,169)
		, byte_234_156_171 = Bry_.New_by_ints(234,156,171)
		, byte_234_156_173 = Bry_.New_by_ints(234,156,173)
		, byte_234_156_175 = Bry_.New_by_ints(234,156,175)
		, byte_234_156_179 = Bry_.New_by_ints(234,156,179)
		, byte_234_156_181 = Bry_.New_by_ints(234,156,181)
		, byte_234_156_183 = Bry_.New_by_ints(234,156,183)
		, byte_234_156_185 = Bry_.New_by_ints(234,156,185)
		, byte_234_156_187 = Bry_.New_by_ints(234,156,187)
		, byte_234_156_189 = Bry_.New_by_ints(234,156,189)
		, byte_234_156_191 = Bry_.New_by_ints(234,156,191)
		, byte_234_157_129 = Bry_.New_by_ints(234,157,129)
		, byte_234_157_131 = Bry_.New_by_ints(234,157,131)
		, byte_234_157_133 = Bry_.New_by_ints(234,157,133)
		, byte_234_157_135 = Bry_.New_by_ints(234,157,135)
		, byte_234_157_137 = Bry_.New_by_ints(234,157,137)
		, byte_234_157_139 = Bry_.New_by_ints(234,157,139)
		, byte_234_157_141 = Bry_.New_by_ints(234,157,141)
		, byte_234_157_143 = Bry_.New_by_ints(234,157,143)
		, byte_234_157_145 = Bry_.New_by_ints(234,157,145)
		, byte_234_157_147 = Bry_.New_by_ints(234,157,147)
		, byte_234_157_149 = Bry_.New_by_ints(234,157,149)
		, byte_234_157_151 = Bry_.New_by_ints(234,157,151)
		, byte_234_157_153 = Bry_.New_by_ints(234,157,153)
		, byte_234_157_155 = Bry_.New_by_ints(234,157,155)
		, byte_234_157_157 = Bry_.New_by_ints(234,157,157)
		, byte_234_157_159 = Bry_.New_by_ints(234,157,159)
		, byte_234_157_161 = Bry_.New_by_ints(234,157,161)
		, byte_234_157_163 = Bry_.New_by_ints(234,157,163)
		, byte_234_157_165 = Bry_.New_by_ints(234,157,165)
		, byte_234_157_167 = Bry_.New_by_ints(234,157,167)
		, byte_234_157_169 = Bry_.New_by_ints(234,157,169)
		, byte_234_157_171 = Bry_.New_by_ints(234,157,171)
		, byte_234_157_173 = Bry_.New_by_ints(234,157,173)
		, byte_234_157_175 = Bry_.New_by_ints(234,157,175)
		, byte_234_157_186 = Bry_.New_by_ints(234,157,186)
		, byte_234_157_188 = Bry_.New_by_ints(234,157,188)
		, byte_225_181_185 = Bry_.New_by_ints(225,181,185)
		, byte_234_157_191 = Bry_.New_by_ints(234,157,191)
		, byte_234_158_129 = Bry_.New_by_ints(234,158,129)
		, byte_234_158_131 = Bry_.New_by_ints(234,158,131)
		, byte_234_158_133 = Bry_.New_by_ints(234,158,133)
		, byte_234_158_135 = Bry_.New_by_ints(234,158,135)
		, byte_234_158_140 = Bry_.New_by_ints(234,158,140)
		, byte_201_165 = Bry_.New_by_ints(201,165)
		, byte_234_158_145 = Bry_.New_by_ints(234,158,145)
		, byte_234_158_147 = Bry_.New_by_ints(234,158,147)
		, byte_234_158_161 = Bry_.New_by_ints(234,158,161)
		, byte_234_158_163 = Bry_.New_by_ints(234,158,163)
		, byte_234_158_165 = Bry_.New_by_ints(234,158,165)
		, byte_234_158_167 = Bry_.New_by_ints(234,158,167)
		, byte_234_158_169 = Bry_.New_by_ints(234,158,169)
		, byte_201_166 = Bry_.New_by_ints(201,166)
		, byte_239_189_129 = Bry_.New_by_ints(239,189,129)
		, byte_239_189_130 = Bry_.New_by_ints(239,189,130)
		, byte_239_189_131 = Bry_.New_by_ints(239,189,131)
		, byte_239_189_132 = Bry_.New_by_ints(239,189,132)
		, byte_239_189_133 = Bry_.New_by_ints(239,189,133)
		, byte_239_189_134 = Bry_.New_by_ints(239,189,134)
		, byte_239_189_135 = Bry_.New_by_ints(239,189,135)
		, byte_239_189_136 = Bry_.New_by_ints(239,189,136)
		, byte_239_189_137 = Bry_.New_by_ints(239,189,137)
		, byte_239_189_138 = Bry_.New_by_ints(239,189,138)
		, byte_239_189_139 = Bry_.New_by_ints(239,189,139)
		, byte_239_189_140 = Bry_.New_by_ints(239,189,140)
		, byte_239_189_141 = Bry_.New_by_ints(239,189,141)
		, byte_239_189_142 = Bry_.New_by_ints(239,189,142)
		, byte_239_189_143 = Bry_.New_by_ints(239,189,143)
		, byte_239_189_144 = Bry_.New_by_ints(239,189,144)
		, byte_239_189_145 = Bry_.New_by_ints(239,189,145)
		, byte_239_189_146 = Bry_.New_by_ints(239,189,146)
		, byte_239_189_147 = Bry_.New_by_ints(239,189,147)
		, byte_239_189_148 = Bry_.New_by_ints(239,189,148)
		, byte_239_189_149 = Bry_.New_by_ints(239,189,149)
		, byte_239_189_150 = Bry_.New_by_ints(239,189,150)
		, byte_239_189_151 = Bry_.New_by_ints(239,189,151)
		, byte_239_189_152 = Bry_.New_by_ints(239,189,152)
		, byte_239_189_153 = Bry_.New_by_ints(239,189,153)
		, byte_239_189_154 = Bry_.New_by_ints(239,189,154)
		, byte_240_144_144_168 = Bry_.New_by_ints(240,144,144,168)
		, byte_240_144_144_169 = Bry_.New_by_ints(240,144,144,169)
		, byte_240_144_144_170 = Bry_.New_by_ints(240,144,144,170)
		, byte_240_144_144_171 = Bry_.New_by_ints(240,144,144,171)
		, byte_240_144_144_172 = Bry_.New_by_ints(240,144,144,172)
		, byte_240_144_144_173 = Bry_.New_by_ints(240,144,144,173)
		, byte_240_144_144_174 = Bry_.New_by_ints(240,144,144,174)
		, byte_240_144_144_175 = Bry_.New_by_ints(240,144,144,175)
		, byte_240_144_144_176 = Bry_.New_by_ints(240,144,144,176)
		, byte_240_144_144_177 = Bry_.New_by_ints(240,144,144,177)
		, byte_240_144_144_178 = Bry_.New_by_ints(240,144,144,178)
		, byte_240_144_144_179 = Bry_.New_by_ints(240,144,144,179)
		, byte_240_144_144_180 = Bry_.New_by_ints(240,144,144,180)
		, byte_240_144_144_181 = Bry_.New_by_ints(240,144,144,181)
		, byte_240_144_144_182 = Bry_.New_by_ints(240,144,144,182)
		, byte_240_144_144_183 = Bry_.New_by_ints(240,144,144,183)
		, byte_240_144_144_184 = Bry_.New_by_ints(240,144,144,184)
		, byte_240_144_144_185 = Bry_.New_by_ints(240,144,144,185)
		, byte_240_144_144_186 = Bry_.New_by_ints(240,144,144,186)
		, byte_240_144_144_187 = Bry_.New_by_ints(240,144,144,187)
		, byte_240_144_144_188 = Bry_.New_by_ints(240,144,144,188)
		, byte_240_144_144_189 = Bry_.New_by_ints(240,144,144,189)
		, byte_240_144_144_190 = Bry_.New_by_ints(240,144,144,190)
		, byte_240_144_144_191 = Bry_.New_by_ints(240,144,144,191)
		, byte_240_144_145_128 = Bry_.New_by_ints(240,144,145,128)
		, byte_240_144_145_129 = Bry_.New_by_ints(240,144,145,129)
		, byte_240_144_145_130 = Bry_.New_by_ints(240,144,145,130)
		, byte_240_144_145_131 = Bry_.New_by_ints(240,144,145,131)
		, byte_240_144_145_132 = Bry_.New_by_ints(240,144,145,132)
		, byte_240_144_145_133 = Bry_.New_by_ints(240,144,145,133)
		, byte_240_144_145_134 = Bry_.New_by_ints(240,144,145,134)
		, byte_240_144_145_135 = Bry_.New_by_ints(240,144,145,135)
		, byte_240_144_145_136 = Bry_.New_by_ints(240,144,145,136)
		, byte_240_144_145_137 = Bry_.New_by_ints(240,144,145,137)
		, byte_240_144_145_138 = Bry_.New_by_ints(240,144,145,138)
		, byte_240_144_145_139 = Bry_.New_by_ints(240,144,145,139)
		, byte_240_144_145_140 = Bry_.New_by_ints(240,144,145,140)
		, byte_240_144_145_141 = Bry_.New_by_ints(240,144,145,141)
		, byte_240_144_145_142 = Bry_.New_by_ints(240,144,145,142)
		, byte_240_144_145_143 = Bry_.New_by_ints(240,144,145,143)
	;
}
