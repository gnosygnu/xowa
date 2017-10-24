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
package gplx.xowa.langs.cases; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.intls.*;
public class Xol_case_mgr_ {
        @gplx.Internal protected static Xol_case_mgr new_() {return new Xol_case_mgr(Gfo_case_mgr_.Tid_custom);}
	public static Xol_case_mgr A7()	{if (mgr_a7 == null) mgr_a7 = New__a7(); return mgr_a7;} private static Xol_case_mgr mgr_a7;
	public static Xol_case_mgr U8()	{if (mgr_u8 == null) mgr_u8 = New__u8(); return mgr_u8;} private static Xol_case_mgr mgr_u8;
	private static Xol_case_mgr New__a7() {
		Xol_case_mgr rv = new Xol_case_mgr(Gfo_case_mgr_.Tid_a7);
		Xol_case_itm[] itms = new Xol_case_itm[]
{ Xol_case_itm_.new_(0, "a", "A")
, Xol_case_itm_.new_(0, "b", "B")
, Xol_case_itm_.new_(0, "c", "C")
, Xol_case_itm_.new_(0, "d", "D")
, Xol_case_itm_.new_(0, "e", "E")
, Xol_case_itm_.new_(0, "f", "F")
, Xol_case_itm_.new_(0, "g", "G")
, Xol_case_itm_.new_(0, "h", "H")
, Xol_case_itm_.new_(0, "i", "I")
, Xol_case_itm_.new_(0, "j", "J")
, Xol_case_itm_.new_(0, "k", "K")
, Xol_case_itm_.new_(0, "l", "L")
, Xol_case_itm_.new_(0, "m", "M")
, Xol_case_itm_.new_(0, "n", "N")
, Xol_case_itm_.new_(0, "o", "O")
, Xol_case_itm_.new_(0, "p", "P")
, Xol_case_itm_.new_(0, "q", "Q")
, Xol_case_itm_.new_(0, "r", "R")
, Xol_case_itm_.new_(0, "s", "S")
, Xol_case_itm_.new_(0, "t", "T")
, Xol_case_itm_.new_(0, "u", "U")
, Xol_case_itm_.new_(0, "v", "V")
, Xol_case_itm_.new_(0, "w", "W")
, Xol_case_itm_.new_(0, "x", "X")
, Xol_case_itm_.new_(0, "y", "Y")
, Xol_case_itm_.new_(0, "z", "Z")
};
		rv.Add_bulk(itms);
		return rv;
	}
	private static Xol_case_mgr New__u8() {
		Xol_case_mgr rv = new Xol_case_mgr(Gfo_case_mgr_.Tid_u8);
		Xol_case_itm[] itms = new Xol_case_itm[]
{ Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(97), Bry_.New_by_ints(65)) // a -> A -- LATIN CAPITAL LETTER A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(98), Bry_.New_by_ints(66)) // b -> B -- LATIN CAPITAL LETTER B
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(99), Bry_.New_by_ints(67)) // c -> C -- LATIN CAPITAL LETTER C
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(100), Bry_.New_by_ints(68)) // d -> D -- LATIN CAPITAL LETTER D
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(101), Bry_.New_by_ints(69)) // e -> E -- LATIN CAPITAL LETTER E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(102), Bry_.New_by_ints(70)) // f -> F -- LATIN CAPITAL LETTER F
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(103), Bry_.New_by_ints(71)) // g -> G -- LATIN CAPITAL LETTER G
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(104), Bry_.New_by_ints(72)) // h -> H -- LATIN CAPITAL LETTER H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(105), Bry_.New_by_ints(73)) // i -> I -- LATIN CAPITAL LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(106), Bry_.New_by_ints(74)) // j -> J -- LATIN CAPITAL LETTER J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(107), Bry_.New_by_ints(75)) // k -> K -- LATIN CAPITAL LETTER K
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(108), Bry_.New_by_ints(76)) // l -> L -- LATIN CAPITAL LETTER L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(109), Bry_.New_by_ints(77)) // m -> M -- LATIN CAPITAL LETTER M
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(110), Bry_.New_by_ints(78)) // n -> N -- LATIN CAPITAL LETTER N
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(111), Bry_.New_by_ints(79)) // o -> O -- LATIN CAPITAL LETTER O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(112), Bry_.New_by_ints(80)) // p -> P -- LATIN CAPITAL LETTER P
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(113), Bry_.New_by_ints(81)) // q -> Q -- LATIN CAPITAL LETTER Q
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(114), Bry_.New_by_ints(82)) // r -> R -- LATIN CAPITAL LETTER R
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(115), Bry_.New_by_ints(83)) // s -> S -- LATIN CAPITAL LETTER S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(116), Bry_.New_by_ints(84)) // t -> T -- LATIN CAPITAL LETTER T
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(117), Bry_.New_by_ints(85)) // u -> U -- LATIN CAPITAL LETTER U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(118), Bry_.New_by_ints(86)) // v -> V -- LATIN CAPITAL LETTER V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(119), Bry_.New_by_ints(87)) // w -> W -- LATIN CAPITAL LETTER W
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(120), Bry_.New_by_ints(88)) // x -> X -- LATIN CAPITAL LETTER X
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(121), Bry_.New_by_ints(89)) // y -> Y -- LATIN CAPITAL LETTER Y
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(122), Bry_.New_by_ints(90)) // z -> Z -- LATIN CAPITAL LETTER Z
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,160), Bry_.New_by_ints(195,128)) // à -> À -- LATIN CAPITAL LETTER A GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,161), Bry_.New_by_ints(195,129)) // á -> Á -- LATIN CAPITAL LETTER A ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,162), Bry_.New_by_ints(195,130)) // â -> Â -- LATIN CAPITAL LETTER A CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,163), Bry_.New_by_ints(195,131)) // ã -> Ã -- LATIN CAPITAL LETTER A TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,164), Bry_.New_by_ints(195,132)) // ä -> Ä -- LATIN CAPITAL LETTER A DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,165), Bry_.New_by_ints(195,133)) // å -> Å -- LATIN CAPITAL LETTER A RING
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,166), Bry_.New_by_ints(195,134)) // æ -> Æ -- LATIN CAPITAL LETTER A E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,167), Bry_.New_by_ints(195,135)) // ç -> Ç -- LATIN CAPITAL LETTER C CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,168), Bry_.New_by_ints(195,136)) // è -> È -- LATIN CAPITAL LETTER E GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,169), Bry_.New_by_ints(195,137)) // é -> É -- LATIN CAPITAL LETTER E ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,170), Bry_.New_by_ints(195,138)) // ê -> Ê -- LATIN CAPITAL LETTER E CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,171), Bry_.New_by_ints(195,139)) // ë -> Ë -- LATIN CAPITAL LETTER E DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,172), Bry_.New_by_ints(195,140)) // ì -> Ì -- LATIN CAPITAL LETTER I GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,173), Bry_.New_by_ints(195,141)) // í -> Í -- LATIN CAPITAL LETTER I ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,174), Bry_.New_by_ints(195,142)) // î -> Î -- LATIN CAPITAL LETTER I CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,175), Bry_.New_by_ints(195,143)) // ï -> Ï -- LATIN CAPITAL LETTER I DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,176), Bry_.New_by_ints(195,144)) // ð -> Ð -- LATIN CAPITAL LETTER ETH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,177), Bry_.New_by_ints(195,145)) // ñ -> Ñ -- LATIN CAPITAL LETTER N TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,178), Bry_.New_by_ints(195,146)) // ò -> Ò -- LATIN CAPITAL LETTER O GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,179), Bry_.New_by_ints(195,147)) // ó -> Ó -- LATIN CAPITAL LETTER O ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,180), Bry_.New_by_ints(195,148)) // ô -> Ô -- LATIN CAPITAL LETTER O CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,181), Bry_.New_by_ints(195,149)) // õ -> Õ -- LATIN CAPITAL LETTER O TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,182), Bry_.New_by_ints(195,150)) // ö -> Ö -- LATIN CAPITAL LETTER O DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,184), Bry_.New_by_ints(195,152)) // ø -> Ø -- LATIN CAPITAL LETTER O SLASH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,185), Bry_.New_by_ints(195,153)) // ù -> Ù -- LATIN CAPITAL LETTER U GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,186), Bry_.New_by_ints(195,154)) // ú -> Ú -- LATIN CAPITAL LETTER U ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,187), Bry_.New_by_ints(195,155)) // û -> Û -- LATIN CAPITAL LETTER U CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,188), Bry_.New_by_ints(195,156)) // ü -> Ü -- LATIN CAPITAL LETTER U DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,189), Bry_.New_by_ints(195,157)) // ý -> Ý -- LATIN CAPITAL LETTER Y ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,190), Bry_.New_by_ints(195,158)) // þ -> Þ -- LATIN CAPITAL LETTER THORN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(195,191), Bry_.New_by_ints(197,184)) // ÿ -> Ÿ -- LATIN SMALL LETTER Y DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,129), Bry_.New_by_ints(196,128)) // ā -> Ā -- LATIN CAPITAL LETTER A MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,131), Bry_.New_by_ints(196,130)) // ă -> Ă -- LATIN CAPITAL LETTER A BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,133), Bry_.New_by_ints(196,132)) // ą -> Ą -- LATIN CAPITAL LETTER A OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,135), Bry_.New_by_ints(196,134)) // ć -> Ć -- LATIN CAPITAL LETTER C ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,137), Bry_.New_by_ints(196,136)) // ĉ -> Ĉ -- LATIN CAPITAL LETTER C CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,139), Bry_.New_by_ints(196,138)) // ċ -> Ċ -- LATIN CAPITAL LETTER C DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,141), Bry_.New_by_ints(196,140)) // č -> Č -- LATIN CAPITAL LETTER C HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,143), Bry_.New_by_ints(196,142)) // ď -> Ď -- LATIN CAPITAL LETTER D HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,145), Bry_.New_by_ints(196,144)) // đ -> Đ -- LATIN CAPITAL LETTER D BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,147), Bry_.New_by_ints(196,146)) // ē -> Ē -- LATIN CAPITAL LETTER E MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,149), Bry_.New_by_ints(196,148)) // ĕ -> Ĕ -- LATIN CAPITAL LETTER E BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,151), Bry_.New_by_ints(196,150)) // ė -> Ė -- LATIN CAPITAL LETTER E DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,153), Bry_.New_by_ints(196,152)) // ę -> Ę -- LATIN CAPITAL LETTER E OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,155), Bry_.New_by_ints(196,154)) // ě -> Ě -- LATIN CAPITAL LETTER E HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,157), Bry_.New_by_ints(196,156)) // ĝ -> Ĝ -- LATIN CAPITAL LETTER G CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,159), Bry_.New_by_ints(196,158)) // ğ -> Ğ -- LATIN CAPITAL LETTER G BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,161), Bry_.New_by_ints(196,160)) // ġ -> Ġ -- LATIN CAPITAL LETTER G DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,163), Bry_.New_by_ints(196,162)) // ģ -> Ģ -- LATIN CAPITAL LETTER G CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,165), Bry_.New_by_ints(196,164)) // ĥ -> Ĥ -- LATIN CAPITAL LETTER H CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,167), Bry_.New_by_ints(196,166)) // ħ -> Ħ -- LATIN CAPITAL LETTER H BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,169), Bry_.New_by_ints(196,168)) // ĩ -> Ĩ -- LATIN CAPITAL LETTER I TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,171), Bry_.New_by_ints(196,170)) // ī -> Ī -- LATIN CAPITAL LETTER I MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,173), Bry_.New_by_ints(196,172)) // ĭ -> Ĭ -- LATIN CAPITAL LETTER I BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,175), Bry_.New_by_ints(196,174)) // į -> Į -- LATIN CAPITAL LETTER I OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,179), Bry_.New_by_ints(196,178)) // ĳ -> Ĳ -- LATIN CAPITAL LETTER I J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,181), Bry_.New_by_ints(196,180)) // ĵ -> Ĵ -- LATIN CAPITAL LETTER J CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,183), Bry_.New_by_ints(196,182)) // ķ -> Ķ -- LATIN CAPITAL LETTER K CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,186), Bry_.New_by_ints(196,185)) // ĺ -> Ĺ -- LATIN CAPITAL LETTER L ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,188), Bry_.New_by_ints(196,187)) // ļ -> Ļ -- LATIN CAPITAL LETTER L CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(196,190), Bry_.New_by_ints(196,189)) // ľ -> Ľ -- LATIN CAPITAL LETTER L HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,128), Bry_.New_by_ints(196,191)) // ŀ -> Ŀ -- LATIN CAPITAL LETTER L WITH MIDDLE DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,130), Bry_.New_by_ints(197,129)) // ł -> Ł -- LATIN CAPITAL LETTER L SLASH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,132), Bry_.New_by_ints(197,131)) // ń -> Ń -- LATIN CAPITAL LETTER N ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,134), Bry_.New_by_ints(197,133)) // ņ -> Ņ -- LATIN CAPITAL LETTER N CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,136), Bry_.New_by_ints(197,135)) // ň -> Ň -- LATIN CAPITAL LETTER N HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,139), Bry_.New_by_ints(197,138)) // ŋ -> Ŋ -- LATIN CAPITAL LETTER ENG
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,141), Bry_.New_by_ints(197,140)) // ō -> Ō -- LATIN CAPITAL LETTER O MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,143), Bry_.New_by_ints(197,142)) // ŏ -> Ŏ -- LATIN CAPITAL LETTER O BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,145), Bry_.New_by_ints(197,144)) // ő -> Ő -- LATIN CAPITAL LETTER O DOUBLE ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,147), Bry_.New_by_ints(197,146)) // œ -> Œ -- LATIN CAPITAL LETTER O E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,149), Bry_.New_by_ints(197,148)) // ŕ -> Ŕ -- LATIN CAPITAL LETTER R ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,151), Bry_.New_by_ints(197,150)) // ŗ -> Ŗ -- LATIN CAPITAL LETTER R CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,153), Bry_.New_by_ints(197,152)) // ř -> Ř -- LATIN CAPITAL LETTER R HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,155), Bry_.New_by_ints(197,154)) // ś -> Ś -- LATIN CAPITAL LETTER S ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,157), Bry_.New_by_ints(197,156)) // ŝ -> Ŝ -- LATIN CAPITAL LETTER S CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,159), Bry_.New_by_ints(197,158)) // ş -> Ş -- LATIN CAPITAL LETTER S CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,161), Bry_.New_by_ints(197,160)) // š -> Š -- LATIN CAPITAL LETTER S HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,163), Bry_.New_by_ints(197,162)) // ţ -> Ţ -- LATIN CAPITAL LETTER T CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,165), Bry_.New_by_ints(197,164)) // ť -> Ť -- LATIN CAPITAL LETTER T HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,167), Bry_.New_by_ints(197,166)) // ŧ -> Ŧ -- LATIN CAPITAL LETTER T BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,169), Bry_.New_by_ints(197,168)) // ũ -> Ũ -- LATIN CAPITAL LETTER U TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,171), Bry_.New_by_ints(197,170)) // ū -> Ū -- LATIN CAPITAL LETTER U MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,173), Bry_.New_by_ints(197,172)) // ŭ -> Ŭ -- LATIN CAPITAL LETTER U BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,175), Bry_.New_by_ints(197,174)) // ů -> Ů -- LATIN CAPITAL LETTER U RING
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,177), Bry_.New_by_ints(197,176)) // ű -> Ű -- LATIN CAPITAL LETTER U DOUBLE ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,179), Bry_.New_by_ints(197,178)) // ų -> Ų -- LATIN CAPITAL LETTER U OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,181), Bry_.New_by_ints(197,180)) // ŵ -> Ŵ -- LATIN CAPITAL LETTER W CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,183), Bry_.New_by_ints(197,182)) // ŷ -> Ŷ -- LATIN CAPITAL LETTER Y CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,186), Bry_.New_by_ints(197,185)) // ź -> Ź -- LATIN CAPITAL LETTER Z ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,188), Bry_.New_by_ints(197,187)) // ż -> Ż -- LATIN CAPITAL LETTER Z DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(197,190), Bry_.New_by_ints(197,189)) // ž -> Ž -- LATIN CAPITAL LETTER Z HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,128), Bry_.New_by_ints(201,131)) // ƀ -> Ƀ -- LATIN SMALL LETTER B BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,147), Bry_.New_by_ints(198,129)) // ɓ -> Ɓ -- LATIN CAPITAL LETTER B HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,131), Bry_.New_by_ints(198,130)) // ƃ -> Ƃ -- LATIN CAPITAL LETTER B TOPBAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,133), Bry_.New_by_ints(198,132)) // ƅ -> Ƅ -- LATIN CAPITAL LETTER TONE SIX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,148), Bry_.New_by_ints(198,134)) // ɔ -> Ɔ -- LATIN CAPITAL LETTER OPEN O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,136), Bry_.New_by_ints(198,135)) // ƈ -> Ƈ -- LATIN CAPITAL LETTER C HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,150), Bry_.New_by_ints(198,137)) // ɖ -> Ɖ -- LATIN CAPITAL LETTER AFRICAN D
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,151), Bry_.New_by_ints(198,138)) // ɗ -> Ɗ -- LATIN CAPITAL LETTER D HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,140), Bry_.New_by_ints(198,139)) // ƌ -> Ƌ -- LATIN CAPITAL LETTER D TOPBAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,157), Bry_.New_by_ints(198,142)) // ǝ -> Ǝ -- LATIN CAPITAL LETTER TURNED E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,153), Bry_.New_by_ints(198,143)) // ə -> Ə -- LATIN CAPITAL LETTER SCHWA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,155), Bry_.New_by_ints(198,144)) // ɛ -> Ɛ -- LATIN CAPITAL LETTER EPSILON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,146), Bry_.New_by_ints(198,145)) // ƒ -> Ƒ -- LATIN CAPITAL LETTER F HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,160), Bry_.New_by_ints(198,147)) // ɠ -> Ɠ -- LATIN CAPITAL LETTER G HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,163), Bry_.New_by_ints(198,148)) // ɣ -> Ɣ -- LATIN CAPITAL LETTER GAMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,149), Bry_.New_by_ints(199,182)) // ƕ -> Ƕ -- LATIN SMALL LETTER H V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,169), Bry_.New_by_ints(198,150)) // ɩ -> Ɩ -- LATIN CAPITAL LETTER IOTA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,168), Bry_.New_by_ints(198,151)) // ɨ -> Ɨ -- LATIN CAPITAL LETTER BARRED I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,153), Bry_.New_by_ints(198,152)) // ƙ -> Ƙ -- LATIN CAPITAL LETTER K HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,154), Bry_.New_by_ints(200,189)) // ƚ -> Ƚ -- LATIN SMALL LETTER BARRED L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,175), Bry_.New_by_ints(198,156)) // ɯ -> Ɯ -- LATIN CAPITAL LETTER TURNED M
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,178), Bry_.New_by_ints(198,157)) // ɲ -> Ɲ -- LATIN CAPITAL LETTER N HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,158), Bry_.New_by_ints(200,160)) // ƞ -> Ƞ -- LATIN SMALL LETTER N WITH LONG RIGHT LEG
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,181), Bry_.New_by_ints(198,159)) // ɵ -> Ɵ -- LATIN CAPITAL LETTER BARRED O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,161), Bry_.New_by_ints(198,160)) // ơ -> Ơ -- LATIN CAPITAL LETTER O HORN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,163), Bry_.New_by_ints(198,162)) // ƣ -> Ƣ -- LATIN CAPITAL LETTER O I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,165), Bry_.New_by_ints(198,164)) // ƥ -> Ƥ -- LATIN CAPITAL LETTER P HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,128), Bry_.New_by_ints(198,166)) // ʀ -> Ʀ -- LATIN LETTER Y R
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,168), Bry_.New_by_ints(198,167)) // ƨ -> Ƨ -- LATIN CAPITAL LETTER TONE TWO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,131), Bry_.New_by_ints(198,169)) // ʃ -> Ʃ -- LATIN CAPITAL LETTER ESH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,173), Bry_.New_by_ints(198,172)) // ƭ -> Ƭ -- LATIN CAPITAL LETTER T HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,136), Bry_.New_by_ints(198,174)) // ʈ -> Ʈ -- LATIN CAPITAL LETTER T RETROFLEX HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,176), Bry_.New_by_ints(198,175)) // ư -> Ư -- LATIN CAPITAL LETTER U HORN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,138), Bry_.New_by_ints(198,177)) // ʊ -> Ʊ -- LATIN CAPITAL LETTER UPSILON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,139), Bry_.New_by_ints(198,178)) // ʋ -> Ʋ -- LATIN CAPITAL LETTER SCRIPT V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,180), Bry_.New_by_ints(198,179)) // ƴ -> Ƴ -- LATIN CAPITAL LETTER Y HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,182), Bry_.New_by_ints(198,181)) // ƶ -> Ƶ -- LATIN CAPITAL LETTER Z BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,146), Bry_.New_by_ints(198,183)) // ʒ -> Ʒ -- LATIN CAPITAL LETTER YOGH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,185), Bry_.New_by_ints(198,184)) // ƹ -> Ƹ -- LATIN CAPITAL LETTER REVERSED YOGH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,189), Bry_.New_by_ints(198,188)) // ƽ -> Ƽ -- LATIN CAPITAL LETTER TONE FIVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(198,191), Bry_.New_by_ints(199,183)) // ƿ -> Ƿ -- LATIN LETTER WYNN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,134), Bry_.New_by_ints(199,132)) // ǆ -> Ǆ -- LATIN CAPITAL LETTER D Z HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,137), Bry_.New_by_ints(199,135)) // ǉ -> Ǉ -- LATIN CAPITAL LETTER L J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,140), Bry_.New_by_ints(199,138)) // ǌ -> Ǌ -- LATIN CAPITAL LETTER N J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,142), Bry_.New_by_ints(199,141)) // ǎ -> Ǎ -- LATIN CAPITAL LETTER A HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,144), Bry_.New_by_ints(199,143)) // ǐ -> Ǐ -- LATIN CAPITAL LETTER I HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,146), Bry_.New_by_ints(199,145)) // ǒ -> Ǒ -- LATIN CAPITAL LETTER O HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,148), Bry_.New_by_ints(199,147)) // ǔ -> Ǔ -- LATIN CAPITAL LETTER U HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,150), Bry_.New_by_ints(199,149)) // ǖ -> Ǖ -- LATIN CAPITAL LETTER U DIAERESIS MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,152), Bry_.New_by_ints(199,151)) // ǘ -> Ǘ -- LATIN CAPITAL LETTER U DIAERESIS ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,154), Bry_.New_by_ints(199,153)) // ǚ -> Ǚ -- LATIN CAPITAL LETTER U DIAERESIS HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,156), Bry_.New_by_ints(199,155)) // ǜ -> Ǜ -- LATIN CAPITAL LETTER U DIAERESIS GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,159), Bry_.New_by_ints(199,158)) // ǟ -> Ǟ -- LATIN CAPITAL LETTER A DIAERESIS MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,161), Bry_.New_by_ints(199,160)) // ǡ -> Ǡ -- LATIN CAPITAL LETTER A DOT MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,163), Bry_.New_by_ints(199,162)) // ǣ -> Ǣ -- LATIN CAPITAL LETTER A E MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,165), Bry_.New_by_ints(199,164)) // ǥ -> Ǥ -- LATIN CAPITAL LETTER G BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,167), Bry_.New_by_ints(199,166)) // ǧ -> Ǧ -- LATIN CAPITAL LETTER G HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,169), Bry_.New_by_ints(199,168)) // ǩ -> Ǩ -- LATIN CAPITAL LETTER K HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,171), Bry_.New_by_ints(199,170)) // ǫ -> Ǫ -- LATIN CAPITAL LETTER O OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,173), Bry_.New_by_ints(199,172)) // ǭ -> Ǭ -- LATIN CAPITAL LETTER O OGONEK MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,175), Bry_.New_by_ints(199,174)) // ǯ -> Ǯ -- LATIN CAPITAL LETTER YOGH HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,179), Bry_.New_by_ints(199,177)) // ǳ -> Ǳ -- LATIN CAPITAL LETTER DZ
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,181), Bry_.New_by_ints(199,180)) // ǵ -> Ǵ -- LATIN CAPITAL LETTER G WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,185), Bry_.New_by_ints(199,184)) // ǹ -> Ǹ -- LATIN CAPITAL LETTER N WITH GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,187), Bry_.New_by_ints(199,186)) // ǻ -> Ǻ -- LATIN CAPITAL LETTER A WITH RING ABOVE AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,189), Bry_.New_by_ints(199,188)) // ǽ -> Ǽ -- LATIN CAPITAL LETTER AE WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(199,191), Bry_.New_by_ints(199,190)) // ǿ -> Ǿ -- LATIN CAPITAL LETTER O WITH STROKE AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,129), Bry_.New_by_ints(200,128)) // ȁ -> Ȁ -- LATIN CAPITAL LETTER A WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,131), Bry_.New_by_ints(200,130)) // ȃ -> Ȃ -- LATIN CAPITAL LETTER A WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,133), Bry_.New_by_ints(200,132)) // ȅ -> Ȅ -- LATIN CAPITAL LETTER E WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,135), Bry_.New_by_ints(200,134)) // ȇ -> Ȇ -- LATIN CAPITAL LETTER E WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,137), Bry_.New_by_ints(200,136)) // ȉ -> Ȉ -- LATIN CAPITAL LETTER I WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,139), Bry_.New_by_ints(200,138)) // ȋ -> Ȋ -- LATIN CAPITAL LETTER I WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,141), Bry_.New_by_ints(200,140)) // ȍ -> Ȍ -- LATIN CAPITAL LETTER O WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,143), Bry_.New_by_ints(200,142)) // ȏ -> Ȏ -- LATIN CAPITAL LETTER O WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,145), Bry_.New_by_ints(200,144)) // ȑ -> Ȑ -- LATIN CAPITAL LETTER R WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,147), Bry_.New_by_ints(200,146)) // ȓ -> Ȓ -- LATIN CAPITAL LETTER R WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,149), Bry_.New_by_ints(200,148)) // ȕ -> Ȕ -- LATIN CAPITAL LETTER U WITH DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,151), Bry_.New_by_ints(200,150)) // ȗ -> Ȗ -- LATIN CAPITAL LETTER U WITH INVERTED BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,153), Bry_.New_by_ints(200,152)) // ș -> Ș -- LATIN CAPITAL LETTER S WITH COMMA BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,155), Bry_.New_by_ints(200,154)) // ț -> Ț -- LATIN CAPITAL LETTER T WITH COMMA BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,157), Bry_.New_by_ints(200,156)) // ȝ -> Ȝ -- LATIN CAPITAL LETTER YOGH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,159), Bry_.New_by_ints(200,158)) // ȟ -> Ȟ -- LATIN CAPITAL LETTER H WITH CARON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,163), Bry_.New_by_ints(200,162)) // ȣ -> Ȣ -- LATIN CAPITAL LETTER OU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,165), Bry_.New_by_ints(200,164)) // ȥ -> Ȥ -- LATIN CAPITAL LETTER Z WITH HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,167), Bry_.New_by_ints(200,166)) // ȧ -> Ȧ -- LATIN CAPITAL LETTER A WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,169), Bry_.New_by_ints(200,168)) // ȩ -> Ȩ -- LATIN CAPITAL LETTER E WITH CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,171), Bry_.New_by_ints(200,170)) // ȫ -> Ȫ -- LATIN CAPITAL LETTER O WITH DIAERESIS AND MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,173), Bry_.New_by_ints(200,172)) // ȭ -> Ȭ -- LATIN CAPITAL LETTER O WITH TILDE AND MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,175), Bry_.New_by_ints(200,174)) // ȯ -> Ȯ -- LATIN CAPITAL LETTER O WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,177), Bry_.New_by_ints(200,176)) // ȱ -> Ȱ -- LATIN CAPITAL LETTER O WITH DOT ABOVE AND MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,179), Bry_.New_by_ints(200,178)) // ȳ -> Ȳ -- LATIN CAPITAL LETTER Y WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,165), Bry_.New_by_ints(200,186)) // ⱥ -> Ⱥ -- LATIN CAPITAL LETTER A WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,188), Bry_.New_by_ints(200,187)) // ȼ -> Ȼ -- LATIN CAPITAL LETTER C WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,166), Bry_.New_by_ints(200,190)) // ⱦ -> Ⱦ -- LATIN CAPITAL LETTER T WITH DIAGONAL STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(200,191), Bry_.New_by_ints(226,177,190)) // ȿ -> Ȿ -- LATIN SMALL LETTER S WITH SWASH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,128), Bry_.New_by_ints(226,177,191)) // ɀ -> Ɀ -- LATIN SMALL LETTER Z WITH SWASH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,130), Bry_.New_by_ints(201,129)) // ɂ -> Ɂ -- LATIN CAPITAL LETTER GLOTTAL STOP
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,137), Bry_.New_by_ints(201,132)) // ʉ -> Ʉ -- LATIN CAPITAL LETTER U BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(202,140), Bry_.New_by_ints(201,133)) // ʌ -> Ʌ -- LATIN CAPITAL LETTER TURNED V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,135), Bry_.New_by_ints(201,134)) // ɇ -> Ɇ -- LATIN CAPITAL LETTER E WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,137), Bry_.New_by_ints(201,136)) // ɉ -> Ɉ -- LATIN CAPITAL LETTER J WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,139), Bry_.New_by_ints(201,138)) // ɋ -> Ɋ -- LATIN CAPITAL LETTER SMALL Q WITH HOOK TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,141), Bry_.New_by_ints(201,140)) // ɍ -> Ɍ -- LATIN CAPITAL LETTER R WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,143), Bry_.New_by_ints(201,142)) // ɏ -> Ɏ -- LATIN CAPITAL LETTER Y WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,144), Bry_.New_by_ints(226,177,175)) // ɐ -> Ɐ -- LATIN SMALL LETTER TURNED A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,145), Bry_.New_by_ints(226,177,173)) // ɑ -> Ɑ -- LATIN SMALL LETTER SCRIPT A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,146), Bry_.New_by_ints(226,177,176)) // ɒ -> Ɒ -- LATIN SMALL LETTER TURNED SCRIPT A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,165), Bry_.New_by_ints(234,158,141)) // ɥ -> Ɥ -- LATIN SMALL LETTER TURNED H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,166), Bry_.New_by_ints(234,158,170)) // ɦ -> Ɦ -- LATIN SMALL LETTER H HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,171), Bry_.New_by_ints(226,177,162)) // ɫ -> Ɫ -- LATIN SMALL LETTER L WITH MIDDLE TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,177), Bry_.New_by_ints(226,177,174)) // ɱ -> Ɱ -- LATIN SMALL LETTER M HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(201,189), Bry_.New_by_ints(226,177,164)) // ɽ -> Ɽ -- LATIN SMALL LETTER R HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,177), Bry_.New_by_ints(205,176)) // ͱ -> Ͱ -- GREEK CAPITAL LETTER HETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,179), Bry_.New_by_ints(205,178)) // ͳ -> Ͳ -- GREEK CAPITAL LETTER ARCHAIC SAMPI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,183), Bry_.New_by_ints(205,182)) // ͷ -> Ͷ -- GREEK CAPITAL LETTER PAMPHYLIAN DIGAMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,187), Bry_.New_by_ints(207,189)) // ͻ -> Ͻ -- GREEK SMALL REVERSED LUNATE SIGMA SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,188), Bry_.New_by_ints(207,190)) // ͼ -> Ͼ -- GREEK SMALL DOTTED LUNATE SIGMA SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(205,189), Bry_.New_by_ints(207,191)) // ͽ -> Ͽ -- GREEK SMALL REVERSED DOTTED LUNATE SIGMA SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,172), Bry_.New_by_ints(206,134)) // ά -> Ά -- GREEK CAPITAL LETTER ALPHA TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,173), Bry_.New_by_ints(206,136)) // έ -> Έ -- GREEK CAPITAL LETTER EPSILON TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,174), Bry_.New_by_ints(206,137)) // ή -> Ή -- GREEK CAPITAL LETTER ETA TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,175), Bry_.New_by_ints(206,138)) // ί -> Ί -- GREEK CAPITAL LETTER IOTA TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,140), Bry_.New_by_ints(206,140)) // ό -> Ό -- GREEK CAPITAL LETTER OMICRON TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,141), Bry_.New_by_ints(206,142)) // ύ -> Ύ -- GREEK CAPITAL LETTER UPSILON TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,142), Bry_.New_by_ints(206,143)) // ώ -> Ώ -- GREEK CAPITAL LETTER OMEGA TONOS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,177), Bry_.New_by_ints(206,145)) // α -> Α -- GREEK CAPITAL LETTER ALPHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,178), Bry_.New_by_ints(206,146)) // β -> Β -- GREEK CAPITAL LETTER BETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,179), Bry_.New_by_ints(206,147)) // γ -> Γ -- GREEK CAPITAL LETTER GAMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,180), Bry_.New_by_ints(206,148)) // δ -> Δ -- GREEK CAPITAL LETTER DELTA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,181), Bry_.New_by_ints(206,149)) // ε -> Ε -- GREEK CAPITAL LETTER EPSILON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,182), Bry_.New_by_ints(206,150)) // ζ -> Ζ -- GREEK CAPITAL LETTER ZETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,183), Bry_.New_by_ints(206,151)) // η -> Η -- GREEK CAPITAL LETTER ETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,184), Bry_.New_by_ints(206,152)) // θ -> Θ -- GREEK CAPITAL LETTER THETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,186), Bry_.New_by_ints(206,154)) // κ -> Κ -- GREEK CAPITAL LETTER KAPPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,187), Bry_.New_by_ints(206,155)) // λ -> Λ -- GREEK CAPITAL LETTER LAMBDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,189), Bry_.New_by_ints(206,157)) // ν -> Ν -- GREEK CAPITAL LETTER NU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,190), Bry_.New_by_ints(206,158)) // ξ -> Ξ -- GREEK CAPITAL LETTER XI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,191), Bry_.New_by_ints(206,159)) // ο -> Ο -- GREEK CAPITAL LETTER OMICRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,128), Bry_.New_by_ints(206,160)) // π -> Π -- GREEK CAPITAL LETTER PI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,129), Bry_.New_by_ints(206,161)) // ρ -> Ρ -- GREEK CAPITAL LETTER RHO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,131), Bry_.New_by_ints(206,163)) // σ -> Σ -- GREEK CAPITAL LETTER SIGMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,132), Bry_.New_by_ints(206,164)) // τ -> Τ -- GREEK CAPITAL LETTER TAU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,133), Bry_.New_by_ints(206,165)) // υ -> Υ -- GREEK CAPITAL LETTER UPSILON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,134), Bry_.New_by_ints(206,166)) // φ -> Φ -- GREEK CAPITAL LETTER PHI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,135), Bry_.New_by_ints(206,167)) // χ -> Χ -- GREEK CAPITAL LETTER CHI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,136), Bry_.New_by_ints(206,168)) // ψ -> Ψ -- GREEK CAPITAL LETTER PSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,137), Bry_.New_by_ints(206,169)) // ω -> Ω -- GREEK CAPITAL LETTER OMEGA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,138), Bry_.New_by_ints(206,170)) // ϊ -> Ϊ -- GREEK CAPITAL LETTER IOTA DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,139), Bry_.New_by_ints(206,171)) // ϋ -> Ϋ -- GREEK CAPITAL LETTER UPSILON DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,151), Bry_.New_by_ints(207,143)) // ϗ -> Ϗ -- GREEK CAPITAL KAI SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,153), Bry_.New_by_ints(207,152)) // ϙ -> Ϙ -- GREEK LETTER ARCHAIC KOPPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,155), Bry_.New_by_ints(207,154)) // ϛ -> Ϛ -- GREEK CAPITAL LETTER STIGMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,157), Bry_.New_by_ints(207,156)) // ϝ -> Ϝ -- GREEK CAPITAL LETTER DIGAMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,159), Bry_.New_by_ints(207,158)) // ϟ -> Ϟ -- GREEK CAPITAL LETTER KOPPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,161), Bry_.New_by_ints(207,160)) // ϡ -> Ϡ -- GREEK CAPITAL LETTER SAMPI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,163), Bry_.New_by_ints(207,162)) // ϣ -> Ϣ -- GREEK CAPITAL LETTER SHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,165), Bry_.New_by_ints(207,164)) // ϥ -> Ϥ -- GREEK CAPITAL LETTER FEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,167), Bry_.New_by_ints(207,166)) // ϧ -> Ϧ -- GREEK CAPITAL LETTER KHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,169), Bry_.New_by_ints(207,168)) // ϩ -> Ϩ -- GREEK CAPITAL LETTER HORI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,171), Bry_.New_by_ints(207,170)) // ϫ -> Ϫ -- GREEK CAPITAL LETTER GANGIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,173), Bry_.New_by_ints(207,172)) // ϭ -> Ϭ -- GREEK CAPITAL LETTER SHIMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,175), Bry_.New_by_ints(207,174)) // ϯ -> Ϯ -- GREEK CAPITAL LETTER DEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,178), Bry_.New_by_ints(207,185)) // ϲ -> Ϲ -- GREEK SMALL LETTER LUNATE SIGMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,184), Bry_.New_by_ints(207,183)) // ϸ -> Ϸ -- GREEK CAPITAL LETTER SHO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(207,187), Bry_.New_by_ints(207,186)) // ϻ -> Ϻ -- GREEK CAPITAL LETTER SAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,144), Bry_.New_by_ints(208,128)) // ѐ -> Ѐ -- CYRILLIC CAPITAL LETTER IE WITH GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,145), Bry_.New_by_ints(208,129)) // ё -> Ё -- CYRILLIC CAPITAL LETTER IO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,146), Bry_.New_by_ints(208,130)) // ђ -> Ђ -- CYRILLIC CAPITAL LETTER DJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,147), Bry_.New_by_ints(208,131)) // ѓ -> Ѓ -- CYRILLIC CAPITAL LETTER GJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,148), Bry_.New_by_ints(208,132)) // є -> Є -- CYRILLIC CAPITAL LETTER E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,149), Bry_.New_by_ints(208,133)) // ѕ -> Ѕ -- CYRILLIC CAPITAL LETTER DZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,150), Bry_.New_by_ints(208,134)) // і -> І -- CYRILLIC CAPITAL LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,151), Bry_.New_by_ints(208,135)) // ї -> Ї -- CYRILLIC CAPITAL LETTER YI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,152), Bry_.New_by_ints(208,136)) // ј -> Ј -- CYRILLIC CAPITAL LETTER JE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,153), Bry_.New_by_ints(208,137)) // љ -> Љ -- CYRILLIC CAPITAL LETTER LJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,154), Bry_.New_by_ints(208,138)) // њ -> Њ -- CYRILLIC CAPITAL LETTER NJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,155), Bry_.New_by_ints(208,139)) // ћ -> Ћ -- CYRILLIC CAPITAL LETTER TSHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,156), Bry_.New_by_ints(208,140)) // ќ -> Ќ -- CYRILLIC CAPITAL LETTER KJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,157), Bry_.New_by_ints(208,141)) // ѝ -> Ѝ -- CYRILLIC CAPITAL LETTER I WITH GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,158), Bry_.New_by_ints(208,142)) // ў -> Ў -- CYRILLIC CAPITAL LETTER SHORT U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,159), Bry_.New_by_ints(208,143)) // џ -> Џ -- CYRILLIC CAPITAL LETTER DZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,176), Bry_.New_by_ints(208,144)) // а -> А -- CYRILLIC CAPITAL LETTER A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,177), Bry_.New_by_ints(208,145)) // б -> Б -- CYRILLIC CAPITAL LETTER BE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,178), Bry_.New_by_ints(208,146)) // в -> В -- CYRILLIC CAPITAL LETTER VE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,179), Bry_.New_by_ints(208,147)) // г -> Г -- CYRILLIC CAPITAL LETTER GE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,180), Bry_.New_by_ints(208,148)) // д -> Д -- CYRILLIC CAPITAL LETTER DE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,181), Bry_.New_by_ints(208,149)) // е -> Е -- CYRILLIC CAPITAL LETTER IE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,182), Bry_.New_by_ints(208,150)) // ж -> Ж -- CYRILLIC CAPITAL LETTER ZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,183), Bry_.New_by_ints(208,151)) // з -> З -- CYRILLIC CAPITAL LETTER ZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,184), Bry_.New_by_ints(208,152)) // и -> И -- CYRILLIC CAPITAL LETTER II
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,185), Bry_.New_by_ints(208,153)) // й -> Й -- CYRILLIC CAPITAL LETTER SHORT II
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,186), Bry_.New_by_ints(208,154)) // к -> К -- CYRILLIC CAPITAL LETTER KA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,187), Bry_.New_by_ints(208,155)) // л -> Л -- CYRILLIC CAPITAL LETTER EL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,188), Bry_.New_by_ints(208,156)) // м -> М -- CYRILLIC CAPITAL LETTER EM
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,189), Bry_.New_by_ints(208,157)) // н -> Н -- CYRILLIC CAPITAL LETTER EN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,190), Bry_.New_by_ints(208,158)) // о -> О -- CYRILLIC CAPITAL LETTER O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(208,191), Bry_.New_by_ints(208,159)) // п -> П -- CYRILLIC CAPITAL LETTER PE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,128), Bry_.New_by_ints(208,160)) // р -> Р -- CYRILLIC CAPITAL LETTER ER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,129), Bry_.New_by_ints(208,161)) // с -> С -- CYRILLIC CAPITAL LETTER ES
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,130), Bry_.New_by_ints(208,162)) // т -> Т -- CYRILLIC CAPITAL LETTER TE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,131), Bry_.New_by_ints(208,163)) // у -> У -- CYRILLIC CAPITAL LETTER U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,132), Bry_.New_by_ints(208,164)) // ф -> Ф -- CYRILLIC CAPITAL LETTER EF
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,133), Bry_.New_by_ints(208,165)) // х -> Х -- CYRILLIC CAPITAL LETTER KHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,134), Bry_.New_by_ints(208,166)) // ц -> Ц -- CYRILLIC CAPITAL LETTER TSE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,135), Bry_.New_by_ints(208,167)) // ч -> Ч -- CYRILLIC CAPITAL LETTER CHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,136), Bry_.New_by_ints(208,168)) // ш -> Ш -- CYRILLIC CAPITAL LETTER SHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,137), Bry_.New_by_ints(208,169)) // щ -> Щ -- CYRILLIC CAPITAL LETTER SHCHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,138), Bry_.New_by_ints(208,170)) // ъ -> Ъ -- CYRILLIC CAPITAL LETTER HARD SIGN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,139), Bry_.New_by_ints(208,171)) // ы -> Ы -- CYRILLIC CAPITAL LETTER YERI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,140), Bry_.New_by_ints(208,172)) // ь -> Ь -- CYRILLIC CAPITAL LETTER SOFT SIGN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,141), Bry_.New_by_ints(208,173)) // э -> Э -- CYRILLIC CAPITAL LETTER REVERSED E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,142), Bry_.New_by_ints(208,174)) // ю -> Ю -- CYRILLIC CAPITAL LETTER IU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,143), Bry_.New_by_ints(208,175)) // я -> Я -- CYRILLIC CAPITAL LETTER IA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,161), Bry_.New_by_ints(209,160)) // ѡ -> Ѡ -- CYRILLIC CAPITAL LETTER OMEGA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,163), Bry_.New_by_ints(209,162)) // ѣ -> Ѣ -- CYRILLIC CAPITAL LETTER YAT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,165), Bry_.New_by_ints(209,164)) // ѥ -> Ѥ -- CYRILLIC CAPITAL LETTER IOTIFIED E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,167), Bry_.New_by_ints(209,166)) // ѧ -> Ѧ -- CYRILLIC CAPITAL LETTER LITTLE YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,169), Bry_.New_by_ints(209,168)) // ѩ -> Ѩ -- CYRILLIC CAPITAL LETTER IOTIFIED LITTLE YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,171), Bry_.New_by_ints(209,170)) // ѫ -> Ѫ -- CYRILLIC CAPITAL LETTER BIG YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,173), Bry_.New_by_ints(209,172)) // ѭ -> Ѭ -- CYRILLIC CAPITAL LETTER IOTIFIED BIG YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,175), Bry_.New_by_ints(209,174)) // ѯ -> Ѯ -- CYRILLIC CAPITAL LETTER KSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,177), Bry_.New_by_ints(209,176)) // ѱ -> Ѱ -- CYRILLIC CAPITAL LETTER PSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,179), Bry_.New_by_ints(209,178)) // ѳ -> Ѳ -- CYRILLIC CAPITAL LETTER FITA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,181), Bry_.New_by_ints(209,180)) // ѵ -> Ѵ -- CYRILLIC CAPITAL LETTER IZHITSA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,183), Bry_.New_by_ints(209,182)) // ѷ -> Ѷ -- CYRILLIC CAPITAL LETTER IZHITSA DOUBLE GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,185), Bry_.New_by_ints(209,184)) // ѹ -> Ѹ -- CYRILLIC CAPITAL LETTER UK DIGRAPH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,187), Bry_.New_by_ints(209,186)) // ѻ -> Ѻ -- CYRILLIC CAPITAL LETTER ROUND OMEGA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,189), Bry_.New_by_ints(209,188)) // ѽ -> Ѽ -- CYRILLIC CAPITAL LETTER OMEGA TITLO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(209,191), Bry_.New_by_ints(209,190)) // ѿ -> Ѿ -- CYRILLIC CAPITAL LETTER OT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,129), Bry_.New_by_ints(210,128)) // ҁ -> Ҁ -- CYRILLIC CAPITAL LETTER KOPPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,139), Bry_.New_by_ints(210,138)) // ҋ -> Ҋ -- CYRILLIC CAPITAL LETTER SHORT I WITH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,141), Bry_.New_by_ints(210,140)) // ҍ -> Ҍ -- CYRILLIC CAPITAL LETTER SEMISOFT SIGN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,143), Bry_.New_by_ints(210,142)) // ҏ -> Ҏ -- CYRILLIC CAPITAL LETTER ER WITH TICK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,145), Bry_.New_by_ints(210,144)) // ґ -> Ґ -- CYRILLIC CAPITAL LETTER GE WITH UPTURN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,147), Bry_.New_by_ints(210,146)) // ғ -> Ғ -- CYRILLIC CAPITAL LETTER GE BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,149), Bry_.New_by_ints(210,148)) // ҕ -> Ҕ -- CYRILLIC CAPITAL LETTER GE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,151), Bry_.New_by_ints(210,150)) // җ -> Җ -- CYRILLIC CAPITAL LETTER ZHE WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,153), Bry_.New_by_ints(210,152)) // ҙ -> Ҙ -- CYRILLIC CAPITAL LETTER ZE CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,155), Bry_.New_by_ints(210,154)) // қ -> Қ -- CYRILLIC CAPITAL LETTER KA WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,157), Bry_.New_by_ints(210,156)) // ҝ -> Ҝ -- CYRILLIC CAPITAL LETTER KA VERTICAL BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,159), Bry_.New_by_ints(210,158)) // ҟ -> Ҟ -- CYRILLIC CAPITAL LETTER KA BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,161), Bry_.New_by_ints(210,160)) // ҡ -> Ҡ -- CYRILLIC CAPITAL LETTER REVERSED GE KA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,163), Bry_.New_by_ints(210,162)) // ң -> Ң -- CYRILLIC CAPITAL LETTER EN WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,165), Bry_.New_by_ints(210,164)) // ҥ -> Ҥ -- CYRILLIC CAPITAL LETTER EN GE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,167), Bry_.New_by_ints(210,166)) // ҧ -> Ҧ -- CYRILLIC CAPITAL LETTER PE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,169), Bry_.New_by_ints(210,168)) // ҩ -> Ҩ -- CYRILLIC CAPITAL LETTER O HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,171), Bry_.New_by_ints(210,170)) // ҫ -> Ҫ -- CYRILLIC CAPITAL LETTER ES CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,173), Bry_.New_by_ints(210,172)) // ҭ -> Ҭ -- CYRILLIC CAPITAL LETTER TE WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,175), Bry_.New_by_ints(210,174)) // ү -> Ү -- CYRILLIC CAPITAL LETTER STRAIGHT U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,177), Bry_.New_by_ints(210,176)) // ұ -> Ұ -- CYRILLIC CAPITAL LETTER STRAIGHT U BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,179), Bry_.New_by_ints(210,178)) // ҳ -> Ҳ -- CYRILLIC CAPITAL LETTER KHA WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,181), Bry_.New_by_ints(210,180)) // ҵ -> Ҵ -- CYRILLIC CAPITAL LETTER TE TSE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,183), Bry_.New_by_ints(210,182)) // ҷ -> Ҷ -- CYRILLIC CAPITAL LETTER CHE WITH RIGHT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,185), Bry_.New_by_ints(210,184)) // ҹ -> Ҹ -- CYRILLIC CAPITAL LETTER CHE VERTICAL BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,187), Bry_.New_by_ints(210,186)) // һ -> Һ -- CYRILLIC CAPITAL LETTER H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,189), Bry_.New_by_ints(210,188)) // ҽ -> Ҽ -- CYRILLIC CAPITAL LETTER IE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(210,191), Bry_.New_by_ints(210,190)) // ҿ -> Ҿ -- CYRILLIC CAPITAL LETTER IE HOOK OGONEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,143), Bry_.New_by_ints(211,128)) // ӏ -> Ӏ -- CYRILLIC LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,130), Bry_.New_by_ints(211,129)) // ӂ -> Ӂ -- CYRILLIC CAPITAL LETTER SHORT ZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,132), Bry_.New_by_ints(211,131)) // ӄ -> Ӄ -- CYRILLIC CAPITAL LETTER KA HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,134), Bry_.New_by_ints(211,133)) // ӆ -> Ӆ -- CYRILLIC CAPITAL LETTER EL WITH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,136), Bry_.New_by_ints(211,135)) // ӈ -> Ӈ -- CYRILLIC CAPITAL LETTER EN HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,138), Bry_.New_by_ints(211,137)) // ӊ -> Ӊ -- CYRILLIC CAPITAL LETTER EN WITH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,140), Bry_.New_by_ints(211,139)) // ӌ -> Ӌ -- CYRILLIC CAPITAL LETTER CHE WITH LEFT DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,142), Bry_.New_by_ints(211,141)) // ӎ -> Ӎ -- CYRILLIC CAPITAL LETTER EM WITH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,145), Bry_.New_by_ints(211,144)) // ӑ -> Ӑ -- CYRILLIC CAPITAL LETTER A WITH BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,147), Bry_.New_by_ints(211,146)) // ӓ -> Ӓ -- CYRILLIC CAPITAL LETTER A WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,149), Bry_.New_by_ints(211,148)) // ӕ -> Ӕ -- CYRILLIC CAPITAL LIGATURE A IE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,151), Bry_.New_by_ints(211,150)) // ӗ -> Ӗ -- CYRILLIC CAPITAL LETTER IE WITH BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,153), Bry_.New_by_ints(211,152)) // ә -> Ә -- CYRILLIC CAPITAL LETTER SCHWA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,155), Bry_.New_by_ints(211,154)) // ӛ -> Ӛ -- CYRILLIC CAPITAL LETTER SCHWA WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,157), Bry_.New_by_ints(211,156)) // ӝ -> Ӝ -- CYRILLIC CAPITAL LETTER ZHE WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,159), Bry_.New_by_ints(211,158)) // ӟ -> Ӟ -- CYRILLIC CAPITAL LETTER ZE WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,161), Bry_.New_by_ints(211,160)) // ӡ -> Ӡ -- CYRILLIC CAPITAL LETTER ABKHASIAN DZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,163), Bry_.New_by_ints(211,162)) // ӣ -> Ӣ -- CYRILLIC CAPITAL LETTER I WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,165), Bry_.New_by_ints(211,164)) // ӥ -> Ӥ -- CYRILLIC CAPITAL LETTER I WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,167), Bry_.New_by_ints(211,166)) // ӧ -> Ӧ -- CYRILLIC CAPITAL LETTER O WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,169), Bry_.New_by_ints(211,168)) // ө -> Ө -- CYRILLIC CAPITAL LETTER BARRED O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,171), Bry_.New_by_ints(211,170)) // ӫ -> Ӫ -- CYRILLIC CAPITAL LETTER BARRED O WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,173), Bry_.New_by_ints(211,172)) // ӭ -> Ӭ -- CYRILLIC CAPITAL LETTER E WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,175), Bry_.New_by_ints(211,174)) // ӯ -> Ӯ -- CYRILLIC CAPITAL LETTER U WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,177), Bry_.New_by_ints(211,176)) // ӱ -> Ӱ -- CYRILLIC CAPITAL LETTER U WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,179), Bry_.New_by_ints(211,178)) // ӳ -> Ӳ -- CYRILLIC CAPITAL LETTER U WITH DOUBLE ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,181), Bry_.New_by_ints(211,180)) // ӵ -> Ӵ -- CYRILLIC CAPITAL LETTER CHE WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,183), Bry_.New_by_ints(211,182)) // ӷ -> Ӷ -- CYRILLIC CAPITAL LETTER GHE WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,185), Bry_.New_by_ints(211,184)) // ӹ -> Ӹ -- CYRILLIC CAPITAL LETTER YERU WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,187), Bry_.New_by_ints(211,186)) // ӻ -> Ӻ -- CYRILLIC CAPITAL LETTER GHE WITH STROKE AND HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,189), Bry_.New_by_ints(211,188)) // ӽ -> Ӽ -- CYRILLIC CAPITAL LETTER HA WITH HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(211,191), Bry_.New_by_ints(211,190)) // ӿ -> Ӿ -- CYRILLIC CAPITAL LETTER HA WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,129), Bry_.New_by_ints(212,128)) // ԁ -> Ԁ -- CYRILLIC CAPITAL LETTER KOMI DE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,131), Bry_.New_by_ints(212,130)) // ԃ -> Ԃ -- CYRILLIC CAPITAL LETTER KOMI DJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,133), Bry_.New_by_ints(212,132)) // ԅ -> Ԅ -- CYRILLIC CAPITAL LETTER KOMI ZJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,135), Bry_.New_by_ints(212,134)) // ԇ -> Ԇ -- CYRILLIC CAPITAL LETTER KOMI DZJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,137), Bry_.New_by_ints(212,136)) // ԉ -> Ԉ -- CYRILLIC CAPITAL LETTER KOMI LJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,139), Bry_.New_by_ints(212,138)) // ԋ -> Ԋ -- CYRILLIC CAPITAL LETTER KOMI NJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,141), Bry_.New_by_ints(212,140)) // ԍ -> Ԍ -- CYRILLIC CAPITAL LETTER KOMI SJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,143), Bry_.New_by_ints(212,142)) // ԏ -> Ԏ -- CYRILLIC CAPITAL LETTER KOMI TJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,145), Bry_.New_by_ints(212,144)) // ԑ -> Ԑ -- CYRILLIC CAPITAL LETTER REVERSED ZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,147), Bry_.New_by_ints(212,146)) // ԓ -> Ԓ -- CYRILLIC CAPITAL LETTER EL WITH HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,149), Bry_.New_by_ints(212,148)) // ԕ -> Ԕ -- CYRILLIC CAPITAL LETTER LHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,151), Bry_.New_by_ints(212,150)) // ԗ -> Ԗ -- CYRILLIC CAPITAL LETTER RHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,153), Bry_.New_by_ints(212,152)) // ԙ -> Ԙ -- CYRILLIC CAPITAL LETTER YAE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,155), Bry_.New_by_ints(212,154)) // ԛ -> Ԛ -- CYRILLIC CAPITAL LETTER QA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,157), Bry_.New_by_ints(212,156)) // ԝ -> Ԝ -- CYRILLIC CAPITAL LETTER WE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,159), Bry_.New_by_ints(212,158)) // ԟ -> Ԟ -- CYRILLIC CAPITAL LETTER ALEUT KA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,161), Bry_.New_by_ints(212,160)) // ԡ -> Ԡ -- CYRILLIC CAPITAL LETTER EL WITH MIDDLE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,163), Bry_.New_by_ints(212,162)) // ԣ -> Ԣ -- CYRILLIC CAPITAL LETTER EN WITH MIDDLE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,165), Bry_.New_by_ints(212,164)) // ԥ -> Ԥ -- CYRILLIC CAPITAL LETTER PE WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(212,167), Bry_.New_by_ints(212,166)) // ԧ -> Ԧ -- CYRILLIC CAPITAL LETTER SHHA WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,161), Bry_.New_by_ints(212,177)) // ա -> Ա -- ARMENIAN CAPITAL LETTER AYB
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,162), Bry_.New_by_ints(212,178)) // բ -> Բ -- ARMENIAN CAPITAL LETTER BEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,163), Bry_.New_by_ints(212,179)) // գ -> Գ -- ARMENIAN CAPITAL LETTER GIM
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,164), Bry_.New_by_ints(212,180)) // դ -> Դ -- ARMENIAN CAPITAL LETTER DA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,165), Bry_.New_by_ints(212,181)) // ե -> Ե -- ARMENIAN CAPITAL LETTER ECH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,166), Bry_.New_by_ints(212,182)) // զ -> Զ -- ARMENIAN CAPITAL LETTER ZA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,167), Bry_.New_by_ints(212,183)) // է -> Է -- ARMENIAN CAPITAL LETTER EH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,168), Bry_.New_by_ints(212,184)) // ը -> Ը -- ARMENIAN CAPITAL LETTER ET
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,169), Bry_.New_by_ints(212,185)) // թ -> Թ -- ARMENIAN CAPITAL LETTER TO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,170), Bry_.New_by_ints(212,186)) // ժ -> Ժ -- ARMENIAN CAPITAL LETTER ZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,171), Bry_.New_by_ints(212,187)) // ի -> Ի -- ARMENIAN CAPITAL LETTER INI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,172), Bry_.New_by_ints(212,188)) // լ -> Լ -- ARMENIAN CAPITAL LETTER LIWN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,173), Bry_.New_by_ints(212,189)) // խ -> Խ -- ARMENIAN CAPITAL LETTER XEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,174), Bry_.New_by_ints(212,190)) // ծ -> Ծ -- ARMENIAN CAPITAL LETTER CA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,175), Bry_.New_by_ints(212,191)) // կ -> Կ -- ARMENIAN CAPITAL LETTER KEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,176), Bry_.New_by_ints(213,128)) // հ -> Հ -- ARMENIAN CAPITAL LETTER HO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,177), Bry_.New_by_ints(213,129)) // ձ -> Ձ -- ARMENIAN CAPITAL LETTER JA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,178), Bry_.New_by_ints(213,130)) // ղ -> Ղ -- ARMENIAN CAPITAL LETTER LAD
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,179), Bry_.New_by_ints(213,131)) // ճ -> Ճ -- ARMENIAN CAPITAL LETTER CHEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,180), Bry_.New_by_ints(213,132)) // մ -> Մ -- ARMENIAN CAPITAL LETTER MEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,181), Bry_.New_by_ints(213,133)) // յ -> Յ -- ARMENIAN CAPITAL LETTER YI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,182), Bry_.New_by_ints(213,134)) // ն -> Ն -- ARMENIAN CAPITAL LETTER NOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,183), Bry_.New_by_ints(213,135)) // շ -> Շ -- ARMENIAN CAPITAL LETTER SHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,184), Bry_.New_by_ints(213,136)) // ո -> Ո -- ARMENIAN CAPITAL LETTER VO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,185), Bry_.New_by_ints(213,137)) // չ -> Չ -- ARMENIAN CAPITAL LETTER CHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,186), Bry_.New_by_ints(213,138)) // պ -> Պ -- ARMENIAN CAPITAL LETTER PEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,187), Bry_.New_by_ints(213,139)) // ջ -> Ջ -- ARMENIAN CAPITAL LETTER JHEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,188), Bry_.New_by_ints(213,140)) // ռ -> Ռ -- ARMENIAN CAPITAL LETTER RA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,189), Bry_.New_by_ints(213,141)) // ս -> Ս -- ARMENIAN CAPITAL LETTER SEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,190), Bry_.New_by_ints(213,142)) // վ -> Վ -- ARMENIAN CAPITAL LETTER VEW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(213,191), Bry_.New_by_ints(213,143)) // տ -> Տ -- ARMENIAN CAPITAL LETTER TIWN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,128), Bry_.New_by_ints(213,144)) // ր -> Ր -- ARMENIAN CAPITAL LETTER REH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,129), Bry_.New_by_ints(213,145)) // ց -> Ց -- ARMENIAN CAPITAL LETTER CO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,130), Bry_.New_by_ints(213,146)) // ւ -> Ւ -- ARMENIAN CAPITAL LETTER YIWN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,131), Bry_.New_by_ints(213,147)) // փ -> Փ -- ARMENIAN CAPITAL LETTER PIWR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,132), Bry_.New_by_ints(213,148)) // ք -> Ք -- ARMENIAN CAPITAL LETTER KEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,133), Bry_.New_by_ints(213,149)) // օ -> Օ -- ARMENIAN CAPITAL LETTER OH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(214,134), Bry_.New_by_ints(213,150)) // ֆ -> Ֆ -- ARMENIAN CAPITAL LETTER FEH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,128), Bry_.New_by_ints(225,130,160)) // ⴀ -> Ⴀ -- GEORGIAN CAPITAL LETTER AN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,129), Bry_.New_by_ints(225,130,161)) // ⴁ -> Ⴁ -- GEORGIAN CAPITAL LETTER BAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,130), Bry_.New_by_ints(225,130,162)) // ⴂ -> Ⴂ -- GEORGIAN CAPITAL LETTER GAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,131), Bry_.New_by_ints(225,130,163)) // ⴃ -> Ⴃ -- GEORGIAN CAPITAL LETTER DON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,132), Bry_.New_by_ints(225,130,164)) // ⴄ -> Ⴄ -- GEORGIAN CAPITAL LETTER EN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,133), Bry_.New_by_ints(225,130,165)) // ⴅ -> Ⴅ -- GEORGIAN CAPITAL LETTER VIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,134), Bry_.New_by_ints(225,130,166)) // ⴆ -> Ⴆ -- GEORGIAN CAPITAL LETTER ZEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,135), Bry_.New_by_ints(225,130,167)) // ⴇ -> Ⴇ -- GEORGIAN CAPITAL LETTER TAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,136), Bry_.New_by_ints(225,130,168)) // ⴈ -> Ⴈ -- GEORGIAN CAPITAL LETTER IN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,137), Bry_.New_by_ints(225,130,169)) // ⴉ -> Ⴉ -- GEORGIAN CAPITAL LETTER KAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,138), Bry_.New_by_ints(225,130,170)) // ⴊ -> Ⴊ -- GEORGIAN CAPITAL LETTER LAS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,139), Bry_.New_by_ints(225,130,171)) // ⴋ -> Ⴋ -- GEORGIAN CAPITAL LETTER MAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,140), Bry_.New_by_ints(225,130,172)) // ⴌ -> Ⴌ -- GEORGIAN CAPITAL LETTER NAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,141), Bry_.New_by_ints(225,130,173)) // ⴍ -> Ⴍ -- GEORGIAN CAPITAL LETTER ON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,142), Bry_.New_by_ints(225,130,174)) // ⴎ -> Ⴎ -- GEORGIAN CAPITAL LETTER PAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,143), Bry_.New_by_ints(225,130,175)) // ⴏ -> Ⴏ -- GEORGIAN CAPITAL LETTER ZHAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,144), Bry_.New_by_ints(225,130,176)) // ⴐ -> Ⴐ -- GEORGIAN CAPITAL LETTER RAE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,145), Bry_.New_by_ints(225,130,177)) // ⴑ -> Ⴑ -- GEORGIAN CAPITAL LETTER SAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,146), Bry_.New_by_ints(225,130,178)) // ⴒ -> Ⴒ -- GEORGIAN CAPITAL LETTER TAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,147), Bry_.New_by_ints(225,130,179)) // ⴓ -> Ⴓ -- GEORGIAN CAPITAL LETTER UN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,148), Bry_.New_by_ints(225,130,180)) // ⴔ -> Ⴔ -- GEORGIAN CAPITAL LETTER PHAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,149), Bry_.New_by_ints(225,130,181)) // ⴕ -> Ⴕ -- GEORGIAN CAPITAL LETTER KHAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,150), Bry_.New_by_ints(225,130,182)) // ⴖ -> Ⴖ -- GEORGIAN CAPITAL LETTER GHAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,151), Bry_.New_by_ints(225,130,183)) // ⴗ -> Ⴗ -- GEORGIAN CAPITAL LETTER QAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,152), Bry_.New_by_ints(225,130,184)) // ⴘ -> Ⴘ -- GEORGIAN CAPITAL LETTER SHIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,153), Bry_.New_by_ints(225,130,185)) // ⴙ -> Ⴙ -- GEORGIAN CAPITAL LETTER CHIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,154), Bry_.New_by_ints(225,130,186)) // ⴚ -> Ⴚ -- GEORGIAN CAPITAL LETTER CAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,155), Bry_.New_by_ints(225,130,187)) // ⴛ -> Ⴛ -- GEORGIAN CAPITAL LETTER JIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,156), Bry_.New_by_ints(225,130,188)) // ⴜ -> Ⴜ -- GEORGIAN CAPITAL LETTER CIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,157), Bry_.New_by_ints(225,130,189)) // ⴝ -> Ⴝ -- GEORGIAN CAPITAL LETTER CHAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,158), Bry_.New_by_ints(225,130,190)) // ⴞ -> Ⴞ -- GEORGIAN CAPITAL LETTER XAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,159), Bry_.New_by_ints(225,130,191)) // ⴟ -> Ⴟ -- GEORGIAN CAPITAL LETTER JHAN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,160), Bry_.New_by_ints(225,131,128)) // ⴠ -> Ⴠ -- GEORGIAN CAPITAL LETTER HAE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,161), Bry_.New_by_ints(225,131,129)) // ⴡ -> Ⴡ -- GEORGIAN CAPITAL LETTER HE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,162), Bry_.New_by_ints(225,131,130)) // ⴢ -> Ⴢ -- GEORGIAN CAPITAL LETTER HIE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,163), Bry_.New_by_ints(225,131,131)) // ⴣ -> Ⴣ -- GEORGIAN CAPITAL LETTER WE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,164), Bry_.New_by_ints(225,131,132)) // ⴤ -> Ⴤ -- GEORGIAN CAPITAL LETTER HAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,165), Bry_.New_by_ints(225,131,133)) // ⴥ -> Ⴥ -- GEORGIAN CAPITAL LETTER HOE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,167), Bry_.New_by_ints(225,131,135)) // ⴧ -> Ⴧ -- GEORGIAN CAPITAL LETTER YN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,180,173), Bry_.New_by_ints(225,131,141)) // ⴭ -> Ⴭ -- GEORGIAN CAPITAL LETTER AEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,181,185), Bry_.New_by_ints(234,157,189)) // ᵹ -> Ᵹ -- LATIN SMALL LETTER INSULAR G
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,181,189), Bry_.New_by_ints(226,177,163)) // ᵽ -> Ᵽ -- LATIN SMALL LETTER P WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,129), Bry_.New_by_ints(225,184,128)) // ḁ -> Ḁ -- LATIN CAPITAL LETTER A WITH RING BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,131), Bry_.New_by_ints(225,184,130)) // ḃ -> Ḃ -- LATIN CAPITAL LETTER B WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,133), Bry_.New_by_ints(225,184,132)) // ḅ -> Ḅ -- LATIN CAPITAL LETTER B WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,135), Bry_.New_by_ints(225,184,134)) // ḇ -> Ḇ -- LATIN CAPITAL LETTER B WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,137), Bry_.New_by_ints(225,184,136)) // ḉ -> Ḉ -- LATIN CAPITAL LETTER C WITH CEDILLA AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,139), Bry_.New_by_ints(225,184,138)) // ḋ -> Ḋ -- LATIN CAPITAL LETTER D WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,141), Bry_.New_by_ints(225,184,140)) // ḍ -> Ḍ -- LATIN CAPITAL LETTER D WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,143), Bry_.New_by_ints(225,184,142)) // ḏ -> Ḏ -- LATIN CAPITAL LETTER D WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,145), Bry_.New_by_ints(225,184,144)) // ḑ -> Ḑ -- LATIN CAPITAL LETTER D WITH CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,147), Bry_.New_by_ints(225,184,146)) // ḓ -> Ḓ -- LATIN CAPITAL LETTER D WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,149), Bry_.New_by_ints(225,184,148)) // ḕ -> Ḕ -- LATIN CAPITAL LETTER E WITH MACRON AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,151), Bry_.New_by_ints(225,184,150)) // ḗ -> Ḗ -- LATIN CAPITAL LETTER E WITH MACRON AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,153), Bry_.New_by_ints(225,184,152)) // ḙ -> Ḙ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,155), Bry_.New_by_ints(225,184,154)) // ḛ -> Ḛ -- LATIN CAPITAL LETTER E WITH TILDE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,157), Bry_.New_by_ints(225,184,156)) // ḝ -> Ḝ -- LATIN CAPITAL LETTER E WITH CEDILLA AND BREVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,159), Bry_.New_by_ints(225,184,158)) // ḟ -> Ḟ -- LATIN CAPITAL LETTER F WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,161), Bry_.New_by_ints(225,184,160)) // ḡ -> Ḡ -- LATIN CAPITAL LETTER G WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,163), Bry_.New_by_ints(225,184,162)) // ḣ -> Ḣ -- LATIN CAPITAL LETTER H WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,165), Bry_.New_by_ints(225,184,164)) // ḥ -> Ḥ -- LATIN CAPITAL LETTER H WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,167), Bry_.New_by_ints(225,184,166)) // ḧ -> Ḧ -- LATIN CAPITAL LETTER H WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,169), Bry_.New_by_ints(225,184,168)) // ḩ -> Ḩ -- LATIN CAPITAL LETTER H WITH CEDILLA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,171), Bry_.New_by_ints(225,184,170)) // ḫ -> Ḫ -- LATIN CAPITAL LETTER H WITH BREVE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,173), Bry_.New_by_ints(225,184,172)) // ḭ -> Ḭ -- LATIN CAPITAL LETTER I WITH TILDE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,175), Bry_.New_by_ints(225,184,174)) // ḯ -> Ḯ -- LATIN CAPITAL LETTER I WITH DIAERESIS AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,177), Bry_.New_by_ints(225,184,176)) // ḱ -> Ḱ -- LATIN CAPITAL LETTER K WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,179), Bry_.New_by_ints(225,184,178)) // ḳ -> Ḳ -- LATIN CAPITAL LETTER K WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,181), Bry_.New_by_ints(225,184,180)) // ḵ -> Ḵ -- LATIN CAPITAL LETTER K WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,183), Bry_.New_by_ints(225,184,182)) // ḷ -> Ḷ -- LATIN CAPITAL LETTER L WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,185), Bry_.New_by_ints(225,184,184)) // ḹ -> Ḹ -- LATIN CAPITAL LETTER L WITH DOT BELOW AND MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,187), Bry_.New_by_ints(225,184,186)) // ḻ -> Ḻ -- LATIN CAPITAL LETTER L WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,189), Bry_.New_by_ints(225,184,188)) // ḽ -> Ḽ -- LATIN CAPITAL LETTER L WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,184,191), Bry_.New_by_ints(225,184,190)) // ḿ -> Ḿ -- LATIN CAPITAL LETTER M WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,129), Bry_.New_by_ints(225,185,128)) // ṁ -> Ṁ -- LATIN CAPITAL LETTER M WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,131), Bry_.New_by_ints(225,185,130)) // ṃ -> Ṃ -- LATIN CAPITAL LETTER M WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,133), Bry_.New_by_ints(225,185,132)) // ṅ -> Ṅ -- LATIN CAPITAL LETTER N WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,135), Bry_.New_by_ints(225,185,134)) // ṇ -> Ṇ -- LATIN CAPITAL LETTER N WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,137), Bry_.New_by_ints(225,185,136)) // ṉ -> Ṉ -- LATIN CAPITAL LETTER N WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,139), Bry_.New_by_ints(225,185,138)) // ṋ -> Ṋ -- LATIN CAPITAL LETTER N WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,141), Bry_.New_by_ints(225,185,140)) // ṍ -> Ṍ -- LATIN CAPITAL LETTER O WITH TILDE AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,143), Bry_.New_by_ints(225,185,142)) // ṏ -> Ṏ -- LATIN CAPITAL LETTER O WITH TILDE AND DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,145), Bry_.New_by_ints(225,185,144)) // ṑ -> Ṑ -- LATIN CAPITAL LETTER O WITH MACRON AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,147), Bry_.New_by_ints(225,185,146)) // ṓ -> Ṓ -- LATIN CAPITAL LETTER O WITH MACRON AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,149), Bry_.New_by_ints(225,185,148)) // ṕ -> Ṕ -- LATIN CAPITAL LETTER P WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,151), Bry_.New_by_ints(225,185,150)) // ṗ -> Ṗ -- LATIN CAPITAL LETTER P WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,153), Bry_.New_by_ints(225,185,152)) // ṙ -> Ṙ -- LATIN CAPITAL LETTER R WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,155), Bry_.New_by_ints(225,185,154)) // ṛ -> Ṛ -- LATIN CAPITAL LETTER R WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,157), Bry_.New_by_ints(225,185,156)) // ṝ -> Ṝ -- LATIN CAPITAL LETTER R WITH DOT BELOW AND MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,159), Bry_.New_by_ints(225,185,158)) // ṟ -> Ṟ -- LATIN CAPITAL LETTER R WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,161), Bry_.New_by_ints(225,185,160)) // ṡ -> Ṡ -- LATIN CAPITAL LETTER S WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,163), Bry_.New_by_ints(225,185,162)) // ṣ -> Ṣ -- LATIN CAPITAL LETTER S WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,165), Bry_.New_by_ints(225,185,164)) // ṥ -> Ṥ -- LATIN CAPITAL LETTER S WITH ACUTE AND DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,167), Bry_.New_by_ints(225,185,166)) // ṧ -> Ṧ -- LATIN CAPITAL LETTER S WITH CARON AND DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,169), Bry_.New_by_ints(225,185,168)) // ṩ -> Ṩ -- LATIN CAPITAL LETTER S WITH DOT BELOW AND DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,171), Bry_.New_by_ints(225,185,170)) // ṫ -> Ṫ -- LATIN CAPITAL LETTER T WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,173), Bry_.New_by_ints(225,185,172)) // ṭ -> Ṭ -- LATIN CAPITAL LETTER T WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,175), Bry_.New_by_ints(225,185,174)) // ṯ -> Ṯ -- LATIN CAPITAL LETTER T WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,177), Bry_.New_by_ints(225,185,176)) // ṱ -> Ṱ -- LATIN CAPITAL LETTER T WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,179), Bry_.New_by_ints(225,185,178)) // ṳ -> Ṳ -- LATIN CAPITAL LETTER U WITH DIAERESIS BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,181), Bry_.New_by_ints(225,185,180)) // ṵ -> Ṵ -- LATIN CAPITAL LETTER U WITH TILDE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,183), Bry_.New_by_ints(225,185,182)) // ṷ -> Ṷ -- LATIN CAPITAL LETTER U WITH CIRCUMFLEX BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,185), Bry_.New_by_ints(225,185,184)) // ṹ -> Ṹ -- LATIN CAPITAL LETTER U WITH TILDE AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,187), Bry_.New_by_ints(225,185,186)) // ṻ -> Ṻ -- LATIN CAPITAL LETTER U WITH MACRON AND DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,189), Bry_.New_by_ints(225,185,188)) // ṽ -> Ṽ -- LATIN CAPITAL LETTER V WITH TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,185,191), Bry_.New_by_ints(225,185,190)) // ṿ -> Ṿ -- LATIN CAPITAL LETTER V WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,129), Bry_.New_by_ints(225,186,128)) // ẁ -> Ẁ -- LATIN CAPITAL LETTER W WITH GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,131), Bry_.New_by_ints(225,186,130)) // ẃ -> Ẃ -- LATIN CAPITAL LETTER W WITH ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,133), Bry_.New_by_ints(225,186,132)) // ẅ -> Ẅ -- LATIN CAPITAL LETTER W WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,135), Bry_.New_by_ints(225,186,134)) // ẇ -> Ẇ -- LATIN CAPITAL LETTER W WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,137), Bry_.New_by_ints(225,186,136)) // ẉ -> Ẉ -- LATIN CAPITAL LETTER W WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,139), Bry_.New_by_ints(225,186,138)) // ẋ -> Ẋ -- LATIN CAPITAL LETTER X WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,141), Bry_.New_by_ints(225,186,140)) // ẍ -> Ẍ -- LATIN CAPITAL LETTER X WITH DIAERESIS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,143), Bry_.New_by_ints(225,186,142)) // ẏ -> Ẏ -- LATIN CAPITAL LETTER Y WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,145), Bry_.New_by_ints(225,186,144)) // ẑ -> Ẑ -- LATIN CAPITAL LETTER Z WITH CIRCUMFLEX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,147), Bry_.New_by_ints(225,186,146)) // ẓ -> Ẓ -- LATIN CAPITAL LETTER Z WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,149), Bry_.New_by_ints(225,186,148)) // ẕ -> Ẕ -- LATIN CAPITAL LETTER Z WITH LINE BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,161), Bry_.New_by_ints(225,186,160)) // ạ -> Ạ -- LATIN CAPITAL LETTER A WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,163), Bry_.New_by_ints(225,186,162)) // ả -> Ả -- LATIN CAPITAL LETTER A WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,165), Bry_.New_by_ints(225,186,164)) // ấ -> Ấ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,167), Bry_.New_by_ints(225,186,166)) // ầ -> Ầ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,169), Bry_.New_by_ints(225,186,168)) // ẩ -> Ẩ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,171), Bry_.New_by_ints(225,186,170)) // ẫ -> Ẫ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,173), Bry_.New_by_ints(225,186,172)) // ậ -> Ậ -- LATIN CAPITAL LETTER A WITH CIRCUMFLEX AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,175), Bry_.New_by_ints(225,186,174)) // ắ -> Ắ -- LATIN CAPITAL LETTER A WITH BREVE AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,177), Bry_.New_by_ints(225,186,176)) // ằ -> Ằ -- LATIN CAPITAL LETTER A WITH BREVE AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,179), Bry_.New_by_ints(225,186,178)) // ẳ -> Ẳ -- LATIN CAPITAL LETTER A WITH BREVE AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,181), Bry_.New_by_ints(225,186,180)) // ẵ -> Ẵ -- LATIN CAPITAL LETTER A WITH BREVE AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,183), Bry_.New_by_ints(225,186,182)) // ặ -> Ặ -- LATIN CAPITAL LETTER A WITH BREVE AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,185), Bry_.New_by_ints(225,186,184)) // ẹ -> Ẹ -- LATIN CAPITAL LETTER E WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,187), Bry_.New_by_ints(225,186,186)) // ẻ -> Ẻ -- LATIN CAPITAL LETTER E WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,189), Bry_.New_by_ints(225,186,188)) // ẽ -> Ẽ -- LATIN CAPITAL LETTER E WITH TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,186,191), Bry_.New_by_ints(225,186,190)) // ế -> Ế -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,129), Bry_.New_by_ints(225,187,128)) // ề -> Ề -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,131), Bry_.New_by_ints(225,187,130)) // ể -> Ể -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,133), Bry_.New_by_ints(225,187,132)) // ễ -> Ễ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,135), Bry_.New_by_ints(225,187,134)) // ệ -> Ệ -- LATIN CAPITAL LETTER E WITH CIRCUMFLEX AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,137), Bry_.New_by_ints(225,187,136)) // ỉ -> Ỉ -- LATIN CAPITAL LETTER I WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,139), Bry_.New_by_ints(225,187,138)) // ị -> Ị -- LATIN CAPITAL LETTER I WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,141), Bry_.New_by_ints(225,187,140)) // ọ -> Ọ -- LATIN CAPITAL LETTER O WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,143), Bry_.New_by_ints(225,187,142)) // ỏ -> Ỏ -- LATIN CAPITAL LETTER O WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,145), Bry_.New_by_ints(225,187,144)) // ố -> Ố -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,147), Bry_.New_by_ints(225,187,146)) // ồ -> Ồ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,149), Bry_.New_by_ints(225,187,148)) // ổ -> Ổ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,151), Bry_.New_by_ints(225,187,150)) // ỗ -> Ỗ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,153), Bry_.New_by_ints(225,187,152)) // ộ -> Ộ -- LATIN CAPITAL LETTER O WITH CIRCUMFLEX AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,155), Bry_.New_by_ints(225,187,154)) // ớ -> Ớ -- LATIN CAPITAL LETTER O WITH HORN AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,157), Bry_.New_by_ints(225,187,156)) // ờ -> Ờ -- LATIN CAPITAL LETTER O WITH HORN AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,159), Bry_.New_by_ints(225,187,158)) // ở -> Ở -- LATIN CAPITAL LETTER O WITH HORN AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,161), Bry_.New_by_ints(225,187,160)) // ỡ -> Ỡ -- LATIN CAPITAL LETTER O WITH HORN AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,163), Bry_.New_by_ints(225,187,162)) // ợ -> Ợ -- LATIN CAPITAL LETTER O WITH HORN AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,165), Bry_.New_by_ints(225,187,164)) // ụ -> Ụ -- LATIN CAPITAL LETTER U WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,167), Bry_.New_by_ints(225,187,166)) // ủ -> Ủ -- LATIN CAPITAL LETTER U WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,169), Bry_.New_by_ints(225,187,168)) // ứ -> Ứ -- LATIN CAPITAL LETTER U WITH HORN AND ACUTE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,171), Bry_.New_by_ints(225,187,170)) // ừ -> Ừ -- LATIN CAPITAL LETTER U WITH HORN AND GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,173), Bry_.New_by_ints(225,187,172)) // ử -> Ử -- LATIN CAPITAL LETTER U WITH HORN AND HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,175), Bry_.New_by_ints(225,187,174)) // ữ -> Ữ -- LATIN CAPITAL LETTER U WITH HORN AND TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,177), Bry_.New_by_ints(225,187,176)) // ự -> Ự -- LATIN CAPITAL LETTER U WITH HORN AND DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,179), Bry_.New_by_ints(225,187,178)) // ỳ -> Ỳ -- LATIN CAPITAL LETTER Y WITH GRAVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,181), Bry_.New_by_ints(225,187,180)) // ỵ -> Ỵ -- LATIN CAPITAL LETTER Y WITH DOT BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,183), Bry_.New_by_ints(225,187,182)) // ỷ -> Ỷ -- LATIN CAPITAL LETTER Y WITH HOOK ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,185), Bry_.New_by_ints(225,187,184)) // ỹ -> Ỹ -- LATIN CAPITAL LETTER Y WITH TILDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,187), Bry_.New_by_ints(225,187,186)) // ỻ -> Ỻ -- LATIN CAPITAL LETTER MIDDLE-WELSH LL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,189), Bry_.New_by_ints(225,187,188)) // ỽ -> Ỽ -- LATIN CAPITAL LETTER MIDDLE-WELSH V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,187,191), Bry_.New_by_ints(225,187,190)) // ỿ -> Ỿ -- LATIN CAPITAL LETTER Y WITH LOOP
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,128), Bry_.New_by_ints(225,188,136)) // ἀ -> Ἀ -- GREEK SMALL LETTER ALPHA WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,129), Bry_.New_by_ints(225,188,137)) // ἁ -> Ἁ -- GREEK SMALL LETTER ALPHA WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,130), Bry_.New_by_ints(225,188,138)) // ἂ -> Ἂ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,131), Bry_.New_by_ints(225,188,139)) // ἃ -> Ἃ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,132), Bry_.New_by_ints(225,188,140)) // ἄ -> Ἄ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,133), Bry_.New_by_ints(225,188,141)) // ἅ -> Ἅ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,134), Bry_.New_by_ints(225,188,142)) // ἆ -> Ἆ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,135), Bry_.New_by_ints(225,188,143)) // ἇ -> Ἇ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,144), Bry_.New_by_ints(225,188,152)) // ἐ -> Ἐ -- GREEK SMALL LETTER EPSILON WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,145), Bry_.New_by_ints(225,188,153)) // ἑ -> Ἑ -- GREEK SMALL LETTER EPSILON WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,146), Bry_.New_by_ints(225,188,154)) // ἒ -> Ἒ -- GREEK SMALL LETTER EPSILON WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,147), Bry_.New_by_ints(225,188,155)) // ἓ -> Ἓ -- GREEK SMALL LETTER EPSILON WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,148), Bry_.New_by_ints(225,188,156)) // ἔ -> Ἔ -- GREEK SMALL LETTER EPSILON WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,149), Bry_.New_by_ints(225,188,157)) // ἕ -> Ἕ -- GREEK SMALL LETTER EPSILON WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,160), Bry_.New_by_ints(225,188,168)) // ἠ -> Ἠ -- GREEK SMALL LETTER ETA WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,161), Bry_.New_by_ints(225,188,169)) // ἡ -> Ἡ -- GREEK SMALL LETTER ETA WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,162), Bry_.New_by_ints(225,188,170)) // ἢ -> Ἢ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,163), Bry_.New_by_ints(225,188,171)) // ἣ -> Ἣ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,164), Bry_.New_by_ints(225,188,172)) // ἤ -> Ἤ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,165), Bry_.New_by_ints(225,188,173)) // ἥ -> Ἥ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,166), Bry_.New_by_ints(225,188,174)) // ἦ -> Ἦ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,167), Bry_.New_by_ints(225,188,175)) // ἧ -> Ἧ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,176), Bry_.New_by_ints(225,188,184)) // ἰ -> Ἰ -- GREEK SMALL LETTER IOTA WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,177), Bry_.New_by_ints(225,188,185)) // ἱ -> Ἱ -- GREEK SMALL LETTER IOTA WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,178), Bry_.New_by_ints(225,188,186)) // ἲ -> Ἲ -- GREEK SMALL LETTER IOTA WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,179), Bry_.New_by_ints(225,188,187)) // ἳ -> Ἳ -- GREEK SMALL LETTER IOTA WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,180), Bry_.New_by_ints(225,188,188)) // ἴ -> Ἴ -- GREEK SMALL LETTER IOTA WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,181), Bry_.New_by_ints(225,188,189)) // ἵ -> Ἵ -- GREEK SMALL LETTER IOTA WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,182), Bry_.New_by_ints(225,188,190)) // ἶ -> Ἶ -- GREEK SMALL LETTER IOTA WITH PSILI AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,188,183), Bry_.New_by_ints(225,188,191)) // ἷ -> Ἷ -- GREEK SMALL LETTER IOTA WITH DASIA AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,128), Bry_.New_by_ints(225,189,136)) // ὀ -> Ὀ -- GREEK SMALL LETTER OMICRON WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,129), Bry_.New_by_ints(225,189,137)) // ὁ -> Ὁ -- GREEK SMALL LETTER OMICRON WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,130), Bry_.New_by_ints(225,189,138)) // ὂ -> Ὂ -- GREEK SMALL LETTER OMICRON WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,131), Bry_.New_by_ints(225,189,139)) // ὃ -> Ὃ -- GREEK SMALL LETTER OMICRON WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,132), Bry_.New_by_ints(225,189,140)) // ὄ -> Ὄ -- GREEK SMALL LETTER OMICRON WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,133), Bry_.New_by_ints(225,189,141)) // ὅ -> Ὅ -- GREEK SMALL LETTER OMICRON WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,145), Bry_.New_by_ints(225,189,153)) // ὑ -> Ὑ -- GREEK SMALL LETTER UPSILON WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,147), Bry_.New_by_ints(225,189,155)) // ὓ -> Ὓ -- GREEK SMALL LETTER UPSILON WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,149), Bry_.New_by_ints(225,189,157)) // ὕ -> Ὕ -- GREEK SMALL LETTER UPSILON WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,151), Bry_.New_by_ints(225,189,159)) // ὗ -> Ὗ -- GREEK SMALL LETTER UPSILON WITH DASIA AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,160), Bry_.New_by_ints(225,189,168)) // ὠ -> Ὠ -- GREEK SMALL LETTER OMEGA WITH PSILI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,161), Bry_.New_by_ints(225,189,169)) // ὡ -> Ὡ -- GREEK SMALL LETTER OMEGA WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,162), Bry_.New_by_ints(225,189,170)) // ὢ -> Ὢ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,163), Bry_.New_by_ints(225,189,171)) // ὣ -> Ὣ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,164), Bry_.New_by_ints(225,189,172)) // ὤ -> Ὤ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,165), Bry_.New_by_ints(225,189,173)) // ὥ -> Ὥ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,166), Bry_.New_by_ints(225,189,174)) // ὦ -> Ὦ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,167), Bry_.New_by_ints(225,189,175)) // ὧ -> Ὧ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,176), Bry_.New_by_ints(225,190,186)) // ὰ -> Ὰ -- GREEK SMALL LETTER ALPHA WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,177), Bry_.New_by_ints(225,190,187)) // ά -> Ά -- GREEK SMALL LETTER ALPHA WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,178), Bry_.New_by_ints(225,191,136)) // ὲ -> Ὲ -- GREEK SMALL LETTER EPSILON WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,179), Bry_.New_by_ints(225,191,137)) // έ -> Έ -- GREEK SMALL LETTER EPSILON WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,180), Bry_.New_by_ints(225,191,138)) // ὴ -> Ὴ -- GREEK SMALL LETTER ETA WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,181), Bry_.New_by_ints(225,191,139)) // ή -> Ή -- GREEK SMALL LETTER ETA WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,182), Bry_.New_by_ints(225,191,154)) // ὶ -> Ὶ -- GREEK SMALL LETTER IOTA WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,183), Bry_.New_by_ints(225,191,155)) // ί -> Ί -- GREEK SMALL LETTER IOTA WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,184), Bry_.New_by_ints(225,191,184)) // ὸ -> Ὸ -- GREEK SMALL LETTER OMICRON WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,185), Bry_.New_by_ints(225,191,185)) // ό -> Ό -- GREEK SMALL LETTER OMICRON WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,186), Bry_.New_by_ints(225,191,170)) // ὺ -> Ὺ -- GREEK SMALL LETTER UPSILON WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,187), Bry_.New_by_ints(225,191,171)) // ύ -> Ύ -- GREEK SMALL LETTER UPSILON WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,188), Bry_.New_by_ints(225,191,186)) // ὼ -> Ὼ -- GREEK SMALL LETTER OMEGA WITH VARIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,189,189), Bry_.New_by_ints(225,191,187)) // ώ -> Ώ -- GREEK SMALL LETTER OMEGA WITH OXIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,128), Bry_.New_by_ints(225,190,136)) // ᾀ -> ᾈ -- GREEK SMALL LETTER ALPHA WITH PSILI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,129), Bry_.New_by_ints(225,190,137)) // ᾁ -> ᾉ -- GREEK SMALL LETTER ALPHA WITH DASIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,130), Bry_.New_by_ints(225,190,138)) // ᾂ -> ᾊ -- GREEK SMALL LETTER ALPHA WITH PSILI AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,131), Bry_.New_by_ints(225,190,139)) // ᾃ -> ᾋ -- GREEK SMALL LETTER ALPHA WITH DASIA AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,132), Bry_.New_by_ints(225,190,140)) // ᾄ -> ᾌ -- GREEK SMALL LETTER ALPHA WITH PSILI AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,133), Bry_.New_by_ints(225,190,141)) // ᾅ -> ᾍ -- GREEK SMALL LETTER ALPHA WITH DASIA AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,134), Bry_.New_by_ints(225,190,142)) // ᾆ -> ᾎ -- GREEK SMALL LETTER ALPHA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,135), Bry_.New_by_ints(225,190,143)) // ᾇ -> ᾏ -- GREEK SMALL LETTER ALPHA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,144), Bry_.New_by_ints(225,190,152)) // ᾐ -> ᾘ -- GREEK SMALL LETTER ETA WITH PSILI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,145), Bry_.New_by_ints(225,190,153)) // ᾑ -> ᾙ -- GREEK SMALL LETTER ETA WITH DASIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,146), Bry_.New_by_ints(225,190,154)) // ᾒ -> ᾚ -- GREEK SMALL LETTER ETA WITH PSILI AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,147), Bry_.New_by_ints(225,190,155)) // ᾓ -> ᾛ -- GREEK SMALL LETTER ETA WITH DASIA AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,148), Bry_.New_by_ints(225,190,156)) // ᾔ -> ᾜ -- GREEK SMALL LETTER ETA WITH PSILI AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,149), Bry_.New_by_ints(225,190,157)) // ᾕ -> ᾝ -- GREEK SMALL LETTER ETA WITH DASIA AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,150), Bry_.New_by_ints(225,190,158)) // ᾖ -> ᾞ -- GREEK SMALL LETTER ETA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,151), Bry_.New_by_ints(225,190,159)) // ᾗ -> ᾟ -- GREEK SMALL LETTER ETA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,160), Bry_.New_by_ints(225,190,168)) // ᾠ -> ᾨ -- GREEK SMALL LETTER OMEGA WITH PSILI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,161), Bry_.New_by_ints(225,190,169)) // ᾡ -> ᾩ -- GREEK SMALL LETTER OMEGA WITH DASIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,162), Bry_.New_by_ints(225,190,170)) // ᾢ -> ᾪ -- GREEK SMALL LETTER OMEGA WITH PSILI AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,163), Bry_.New_by_ints(225,190,171)) // ᾣ -> ᾫ -- GREEK SMALL LETTER OMEGA WITH DASIA AND VARIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,164), Bry_.New_by_ints(225,190,172)) // ᾤ -> ᾬ -- GREEK SMALL LETTER OMEGA WITH PSILI AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,165), Bry_.New_by_ints(225,190,173)) // ᾥ -> ᾭ -- GREEK SMALL LETTER OMEGA WITH DASIA AND OXIA AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,166), Bry_.New_by_ints(225,190,174)) // ᾦ -> ᾮ -- GREEK SMALL LETTER OMEGA WITH PSILI AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,167), Bry_.New_by_ints(225,190,175)) // ᾧ -> ᾯ -- GREEK SMALL LETTER OMEGA WITH DASIA AND PERISPOMENI AND YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,176), Bry_.New_by_ints(225,190,184)) // ᾰ -> Ᾰ -- GREEK SMALL LETTER ALPHA WITH VRACHY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,177), Bry_.New_by_ints(225,190,185)) // ᾱ -> Ᾱ -- GREEK SMALL LETTER ALPHA WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,190,179), Bry_.New_by_ints(225,190,188)) // ᾳ -> ᾼ -- GREEK SMALL LETTER ALPHA WITH YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,131), Bry_.New_by_ints(225,191,140)) // ῃ -> ῌ -- GREEK SMALL LETTER ETA WITH YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,144), Bry_.New_by_ints(225,191,152)) // ῐ -> Ῐ -- GREEK SMALL LETTER IOTA WITH VRACHY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,145), Bry_.New_by_ints(225,191,153)) // ῑ -> Ῑ -- GREEK SMALL LETTER IOTA WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,160), Bry_.New_by_ints(225,191,168)) // ῠ -> Ῠ -- GREEK SMALL LETTER UPSILON WITH VRACHY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,161), Bry_.New_by_ints(225,191,169)) // ῡ -> Ῡ -- GREEK SMALL LETTER UPSILON WITH MACRON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,165), Bry_.New_by_ints(225,191,172)) // ῥ -> Ῥ -- GREEK SMALL LETTER RHO WITH DASIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(225,191,179), Bry_.New_by_ints(225,191,188)) // ῳ -> ῼ -- GREEK SMALL LETTER OMEGA WITH YPOGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,142), Bry_.New_by_ints(226,132,178)) // ⅎ -> Ⅎ -- TURNED F
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,176), Bry_.New_by_ints(226,133,160)) // ⅰ -> Ⅰ -- ROMAN NUMERAL ONE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,177), Bry_.New_by_ints(226,133,161)) // ⅱ -> Ⅱ -- ROMAN NUMERAL TWO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,178), Bry_.New_by_ints(226,133,162)) // ⅲ -> Ⅲ -- ROMAN NUMERAL THREE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,179), Bry_.New_by_ints(226,133,163)) // ⅳ -> Ⅳ -- ROMAN NUMERAL FOUR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,180), Bry_.New_by_ints(226,133,164)) // ⅴ -> Ⅴ -- ROMAN NUMERAL FIVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,181), Bry_.New_by_ints(226,133,165)) // ⅵ -> Ⅵ -- ROMAN NUMERAL SIX
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,182), Bry_.New_by_ints(226,133,166)) // ⅶ -> Ⅶ -- ROMAN NUMERAL SEVEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,183), Bry_.New_by_ints(226,133,167)) // ⅷ -> Ⅷ -- ROMAN NUMERAL EIGHT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,184), Bry_.New_by_ints(226,133,168)) // ⅸ -> Ⅸ -- ROMAN NUMERAL NINE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,185), Bry_.New_by_ints(226,133,169)) // ⅹ -> Ⅹ -- ROMAN NUMERAL TEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,186), Bry_.New_by_ints(226,133,170)) // ⅺ -> Ⅺ -- ROMAN NUMERAL ELEVEN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,187), Bry_.New_by_ints(226,133,171)) // ⅻ -> Ⅻ -- ROMAN NUMERAL TWELVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,188), Bry_.New_by_ints(226,133,172)) // ⅼ -> Ⅼ -- ROMAN NUMERAL FIFTY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,189), Bry_.New_by_ints(226,133,173)) // ⅽ -> Ⅽ -- ROMAN NUMERAL ONE HUNDRED
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,190), Bry_.New_by_ints(226,133,174)) // ⅾ -> Ⅾ -- ROMAN NUMERAL FIVE HUNDRED
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,133,191), Bry_.New_by_ints(226,133,175)) // ⅿ -> Ⅿ -- ROMAN NUMERAL ONE THOUSAND
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,134,132), Bry_.New_by_ints(226,134,131)) // ↄ -> Ↄ -- ROMAN NUMERAL REVERSED ONE HUNDRED
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,144), Bry_.New_by_ints(226,146,182)) // ⓐ -> Ⓐ -- CIRCLED LATIN CAPITAL LETTER A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,145), Bry_.New_by_ints(226,146,183)) // ⓑ -> Ⓑ -- CIRCLED LATIN CAPITAL LETTER B
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,146), Bry_.New_by_ints(226,146,184)) // ⓒ -> Ⓒ -- CIRCLED LATIN CAPITAL LETTER C
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,147), Bry_.New_by_ints(226,146,185)) // ⓓ -> Ⓓ -- CIRCLED LATIN CAPITAL LETTER D
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,148), Bry_.New_by_ints(226,146,186)) // ⓔ -> Ⓔ -- CIRCLED LATIN CAPITAL LETTER E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,149), Bry_.New_by_ints(226,146,187)) // ⓕ -> Ⓕ -- CIRCLED LATIN CAPITAL LETTER F
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,150), Bry_.New_by_ints(226,146,188)) // ⓖ -> Ⓖ -- CIRCLED LATIN CAPITAL LETTER G
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,151), Bry_.New_by_ints(226,146,189)) // ⓗ -> Ⓗ -- CIRCLED LATIN CAPITAL LETTER H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,152), Bry_.New_by_ints(226,146,190)) // ⓘ -> Ⓘ -- CIRCLED LATIN CAPITAL LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,153), Bry_.New_by_ints(226,146,191)) // ⓙ -> Ⓙ -- CIRCLED LATIN CAPITAL LETTER J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,154), Bry_.New_by_ints(226,147,128)) // ⓚ -> Ⓚ -- CIRCLED LATIN CAPITAL LETTER K
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,155), Bry_.New_by_ints(226,147,129)) // ⓛ -> Ⓛ -- CIRCLED LATIN CAPITAL LETTER L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,156), Bry_.New_by_ints(226,147,130)) // ⓜ -> Ⓜ -- CIRCLED LATIN CAPITAL LETTER M
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,157), Bry_.New_by_ints(226,147,131)) // ⓝ -> Ⓝ -- CIRCLED LATIN CAPITAL LETTER N
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,158), Bry_.New_by_ints(226,147,132)) // ⓞ -> Ⓞ -- CIRCLED LATIN CAPITAL LETTER O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,159), Bry_.New_by_ints(226,147,133)) // ⓟ -> Ⓟ -- CIRCLED LATIN CAPITAL LETTER P
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,160), Bry_.New_by_ints(226,147,134)) // ⓠ -> Ⓠ -- CIRCLED LATIN CAPITAL LETTER Q
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,161), Bry_.New_by_ints(226,147,135)) // ⓡ -> Ⓡ -- CIRCLED LATIN CAPITAL LETTER R
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,162), Bry_.New_by_ints(226,147,136)) // ⓢ -> Ⓢ -- CIRCLED LATIN CAPITAL LETTER S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,163), Bry_.New_by_ints(226,147,137)) // ⓣ -> Ⓣ -- CIRCLED LATIN CAPITAL LETTER T
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,164), Bry_.New_by_ints(226,147,138)) // ⓤ -> Ⓤ -- CIRCLED LATIN CAPITAL LETTER U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,165), Bry_.New_by_ints(226,147,139)) // ⓥ -> Ⓥ -- CIRCLED LATIN CAPITAL LETTER V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,166), Bry_.New_by_ints(226,147,140)) // ⓦ -> Ⓦ -- CIRCLED LATIN CAPITAL LETTER W
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,167), Bry_.New_by_ints(226,147,141)) // ⓧ -> Ⓧ -- CIRCLED LATIN CAPITAL LETTER X
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,168), Bry_.New_by_ints(226,147,142)) // ⓨ -> Ⓨ -- CIRCLED LATIN CAPITAL LETTER Y
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,147,169), Bry_.New_by_ints(226,147,143)) // ⓩ -> Ⓩ -- CIRCLED LATIN CAPITAL LETTER Z
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,176), Bry_.New_by_ints(226,176,128)) // ⰰ -> Ⰰ -- GLAGOLITIC CAPITAL LETTER AZU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,177), Bry_.New_by_ints(226,176,129)) // ⰱ -> Ⰱ -- GLAGOLITIC CAPITAL LETTER BUKY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,178), Bry_.New_by_ints(226,176,130)) // ⰲ -> Ⰲ -- GLAGOLITIC CAPITAL LETTER VEDE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,179), Bry_.New_by_ints(226,176,131)) // ⰳ -> Ⰳ -- GLAGOLITIC CAPITAL LETTER GLAGOLI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,180), Bry_.New_by_ints(226,176,132)) // ⰴ -> Ⰴ -- GLAGOLITIC CAPITAL LETTER DOBRO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,181), Bry_.New_by_ints(226,176,133)) // ⰵ -> Ⰵ -- GLAGOLITIC CAPITAL LETTER YESTU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,182), Bry_.New_by_ints(226,176,134)) // ⰶ -> Ⰶ -- GLAGOLITIC CAPITAL LETTER ZHIVETE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,183), Bry_.New_by_ints(226,176,135)) // ⰷ -> Ⰷ -- GLAGOLITIC CAPITAL LETTER DZELO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,184), Bry_.New_by_ints(226,176,136)) // ⰸ -> Ⰸ -- GLAGOLITIC CAPITAL LETTER ZEMLJA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,185), Bry_.New_by_ints(226,176,137)) // ⰹ -> Ⰹ -- GLAGOLITIC CAPITAL LETTER IZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,186), Bry_.New_by_ints(226,176,138)) // ⰺ -> Ⰺ -- GLAGOLITIC CAPITAL LETTER INITIAL IZHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,187), Bry_.New_by_ints(226,176,139)) // ⰻ -> Ⰻ -- GLAGOLITIC CAPITAL LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,188), Bry_.New_by_ints(226,176,140)) // ⰼ -> Ⰼ -- GLAGOLITIC CAPITAL LETTER DJERVI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,189), Bry_.New_by_ints(226,176,141)) // ⰽ -> Ⰽ -- GLAGOLITIC CAPITAL LETTER KAKO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,190), Bry_.New_by_ints(226,176,142)) // ⰾ -> Ⰾ -- GLAGOLITIC CAPITAL LETTER LJUDIJE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,176,191), Bry_.New_by_ints(226,176,143)) // ⰿ -> Ⰿ -- GLAGOLITIC CAPITAL LETTER MYSLITE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,128), Bry_.New_by_ints(226,176,144)) // ⱀ -> Ⱀ -- GLAGOLITIC CAPITAL LETTER NASHI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,129), Bry_.New_by_ints(226,176,145)) // ⱁ -> Ⱁ -- GLAGOLITIC CAPITAL LETTER ONU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,130), Bry_.New_by_ints(226,176,146)) // ⱂ -> Ⱂ -- GLAGOLITIC CAPITAL LETTER POKOJI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,131), Bry_.New_by_ints(226,176,147)) // ⱃ -> Ⱃ -- GLAGOLITIC CAPITAL LETTER RITSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,132), Bry_.New_by_ints(226,176,148)) // ⱄ -> Ⱄ -- GLAGOLITIC CAPITAL LETTER SLOVO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,133), Bry_.New_by_ints(226,176,149)) // ⱅ -> Ⱅ -- GLAGOLITIC CAPITAL LETTER TVRIDO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,134), Bry_.New_by_ints(226,176,150)) // ⱆ -> Ⱆ -- GLAGOLITIC CAPITAL LETTER UKU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,135), Bry_.New_by_ints(226,176,151)) // ⱇ -> Ⱇ -- GLAGOLITIC CAPITAL LETTER FRITU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,136), Bry_.New_by_ints(226,176,152)) // ⱈ -> Ⱈ -- GLAGOLITIC CAPITAL LETTER HERU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,137), Bry_.New_by_ints(226,176,153)) // ⱉ -> Ⱉ -- GLAGOLITIC CAPITAL LETTER OTU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,138), Bry_.New_by_ints(226,176,154)) // ⱊ -> Ⱊ -- GLAGOLITIC CAPITAL LETTER PE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,139), Bry_.New_by_ints(226,176,155)) // ⱋ -> Ⱋ -- GLAGOLITIC CAPITAL LETTER SHTA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,140), Bry_.New_by_ints(226,176,156)) // ⱌ -> Ⱌ -- GLAGOLITIC CAPITAL LETTER TSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,141), Bry_.New_by_ints(226,176,157)) // ⱍ -> Ⱍ -- GLAGOLITIC CAPITAL LETTER CHRIVI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,142), Bry_.New_by_ints(226,176,158)) // ⱎ -> Ⱎ -- GLAGOLITIC CAPITAL LETTER SHA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,143), Bry_.New_by_ints(226,176,159)) // ⱏ -> Ⱏ -- GLAGOLITIC CAPITAL LETTER YERU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,144), Bry_.New_by_ints(226,176,160)) // ⱐ -> Ⱐ -- GLAGOLITIC CAPITAL LETTER YERI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,145), Bry_.New_by_ints(226,176,161)) // ⱑ -> Ⱑ -- GLAGOLITIC CAPITAL LETTER YATI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,146), Bry_.New_by_ints(226,176,162)) // ⱒ -> Ⱒ -- GLAGOLITIC CAPITAL LETTER SPIDERY HA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,147), Bry_.New_by_ints(226,176,163)) // ⱓ -> Ⱓ -- GLAGOLITIC CAPITAL LETTER YU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,148), Bry_.New_by_ints(226,176,164)) // ⱔ -> Ⱔ -- GLAGOLITIC CAPITAL LETTER SMALL YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,149), Bry_.New_by_ints(226,176,165)) // ⱕ -> Ⱕ -- GLAGOLITIC CAPITAL LETTER SMALL YUS WITH TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,150), Bry_.New_by_ints(226,176,166)) // ⱖ -> Ⱖ -- GLAGOLITIC CAPITAL LETTER YO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,151), Bry_.New_by_ints(226,176,167)) // ⱗ -> Ⱗ -- GLAGOLITIC CAPITAL LETTER IOTATED SMALL YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,152), Bry_.New_by_ints(226,176,168)) // ⱘ -> Ⱘ -- GLAGOLITIC CAPITAL LETTER BIG YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,153), Bry_.New_by_ints(226,176,169)) // ⱙ -> Ⱙ -- GLAGOLITIC CAPITAL LETTER IOTATED BIG YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,154), Bry_.New_by_ints(226,176,170)) // ⱚ -> Ⱚ -- GLAGOLITIC CAPITAL LETTER FITA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,155), Bry_.New_by_ints(226,176,171)) // ⱛ -> Ⱛ -- GLAGOLITIC CAPITAL LETTER IZHITSA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,156), Bry_.New_by_ints(226,176,172)) // ⱜ -> Ⱜ -- GLAGOLITIC CAPITAL LETTER SHTAPIC
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,157), Bry_.New_by_ints(226,176,173)) // ⱝ -> Ⱝ -- GLAGOLITIC CAPITAL LETTER TROKUTASTI A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,158), Bry_.New_by_ints(226,176,174)) // ⱞ -> Ⱞ -- GLAGOLITIC CAPITAL LETTER LATINATE MYSLITE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,161), Bry_.New_by_ints(226,177,160)) // ⱡ -> Ⱡ -- LATIN CAPITAL LETTER L WITH DOUBLE BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,168), Bry_.New_by_ints(226,177,167)) // ⱨ -> Ⱨ -- LATIN CAPITAL LETTER H WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,170), Bry_.New_by_ints(226,177,169)) // ⱪ -> Ⱪ -- LATIN CAPITAL LETTER K WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,172), Bry_.New_by_ints(226,177,171)) // ⱬ -> Ⱬ -- LATIN CAPITAL LETTER Z WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,179), Bry_.New_by_ints(226,177,178)) // ⱳ -> Ⱳ -- LATIN CAPITAL LETTER W WITH HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,177,182), Bry_.New_by_ints(226,177,181)) // ⱶ -> Ⱶ -- LATIN CAPITAL LETTER HALF H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,129), Bry_.New_by_ints(226,178,128)) // ⲁ -> Ⲁ -- COPTIC CAPITAL LETTER ALFA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,131), Bry_.New_by_ints(226,178,130)) // ⲃ -> Ⲃ -- COPTIC CAPITAL LETTER VIDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,133), Bry_.New_by_ints(226,178,132)) // ⲅ -> Ⲅ -- COPTIC CAPITAL LETTER GAMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,135), Bry_.New_by_ints(226,178,134)) // ⲇ -> Ⲇ -- COPTIC CAPITAL LETTER DALDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,137), Bry_.New_by_ints(226,178,136)) // ⲉ -> Ⲉ -- COPTIC CAPITAL LETTER EIE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,139), Bry_.New_by_ints(226,178,138)) // ⲋ -> Ⲋ -- COPTIC CAPITAL LETTER SOU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,141), Bry_.New_by_ints(226,178,140)) // ⲍ -> Ⲍ -- COPTIC CAPITAL LETTER ZATA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,143), Bry_.New_by_ints(226,178,142)) // ⲏ -> Ⲏ -- COPTIC CAPITAL LETTER HATE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,145), Bry_.New_by_ints(226,178,144)) // ⲑ -> Ⲑ -- COPTIC CAPITAL LETTER THETHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,147), Bry_.New_by_ints(226,178,146)) // ⲓ -> Ⲓ -- COPTIC CAPITAL LETTER IAUDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,149), Bry_.New_by_ints(226,178,148)) // ⲕ -> Ⲕ -- COPTIC CAPITAL LETTER KAPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,151), Bry_.New_by_ints(226,178,150)) // ⲗ -> Ⲗ -- COPTIC CAPITAL LETTER LAULA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,153), Bry_.New_by_ints(226,178,152)) // ⲙ -> Ⲙ -- COPTIC CAPITAL LETTER MI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,155), Bry_.New_by_ints(226,178,154)) // ⲛ -> Ⲛ -- COPTIC CAPITAL LETTER NI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,157), Bry_.New_by_ints(226,178,156)) // ⲝ -> Ⲝ -- COPTIC CAPITAL LETTER KSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,159), Bry_.New_by_ints(226,178,158)) // ⲟ -> Ⲟ -- COPTIC CAPITAL LETTER O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,161), Bry_.New_by_ints(226,178,160)) // ⲡ -> Ⲡ -- COPTIC CAPITAL LETTER PI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,163), Bry_.New_by_ints(226,178,162)) // ⲣ -> Ⲣ -- COPTIC CAPITAL LETTER RO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,165), Bry_.New_by_ints(226,178,164)) // ⲥ -> Ⲥ -- COPTIC CAPITAL LETTER SIMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,167), Bry_.New_by_ints(226,178,166)) // ⲧ -> Ⲧ -- COPTIC CAPITAL LETTER TAU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,169), Bry_.New_by_ints(226,178,168)) // ⲩ -> Ⲩ -- COPTIC CAPITAL LETTER UA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,171), Bry_.New_by_ints(226,178,170)) // ⲫ -> Ⲫ -- COPTIC CAPITAL LETTER FI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,173), Bry_.New_by_ints(226,178,172)) // ⲭ -> Ⲭ -- COPTIC CAPITAL LETTER KHI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,175), Bry_.New_by_ints(226,178,174)) // ⲯ -> Ⲯ -- COPTIC CAPITAL LETTER PSI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,177), Bry_.New_by_ints(226,178,176)) // ⲱ -> Ⲱ -- COPTIC CAPITAL LETTER OOU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,179), Bry_.New_by_ints(226,178,178)) // ⲳ -> Ⲳ -- COPTIC CAPITAL LETTER DIALECT-P ALEF
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,181), Bry_.New_by_ints(226,178,180)) // ⲵ -> Ⲵ -- COPTIC CAPITAL LETTER OLD COPTIC AIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,183), Bry_.New_by_ints(226,178,182)) // ⲷ -> Ⲷ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC EIE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,185), Bry_.New_by_ints(226,178,184)) // ⲹ -> Ⲹ -- COPTIC CAPITAL LETTER DIALECT-P KAPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,187), Bry_.New_by_ints(226,178,186)) // ⲻ -> Ⲻ -- COPTIC CAPITAL LETTER DIALECT-P NI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,189), Bry_.New_by_ints(226,178,188)) // ⲽ -> Ⲽ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC NI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,178,191), Bry_.New_by_ints(226,178,190)) // ⲿ -> Ⲿ -- COPTIC CAPITAL LETTER OLD COPTIC OOU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,129), Bry_.New_by_ints(226,179,128)) // ⳁ -> Ⳁ -- COPTIC CAPITAL LETTER SAMPI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,131), Bry_.New_by_ints(226,179,130)) // ⳃ -> Ⳃ -- COPTIC CAPITAL LETTER CROSSED SHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,133), Bry_.New_by_ints(226,179,132)) // ⳅ -> Ⳅ -- COPTIC CAPITAL LETTER OLD COPTIC SHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,135), Bry_.New_by_ints(226,179,134)) // ⳇ -> Ⳇ -- COPTIC CAPITAL LETTER OLD COPTIC ESH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,137), Bry_.New_by_ints(226,179,136)) // ⳉ -> Ⳉ -- COPTIC CAPITAL LETTER AKHMIMIC KHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,139), Bry_.New_by_ints(226,179,138)) // ⳋ -> Ⳋ -- COPTIC CAPITAL LETTER DIALECT-P HORI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,141), Bry_.New_by_ints(226,179,140)) // ⳍ -> Ⳍ -- COPTIC CAPITAL LETTER OLD COPTIC HORI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,143), Bry_.New_by_ints(226,179,142)) // ⳏ -> Ⳏ -- COPTIC CAPITAL LETTER OLD COPTIC HA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,145), Bry_.New_by_ints(226,179,144)) // ⳑ -> Ⳑ -- COPTIC CAPITAL LETTER L-SHAPED HA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,147), Bry_.New_by_ints(226,179,146)) // ⳓ -> Ⳓ -- COPTIC CAPITAL LETTER OLD COPTIC HEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,149), Bry_.New_by_ints(226,179,148)) // ⳕ -> Ⳕ -- COPTIC CAPITAL LETTER OLD COPTIC HAT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,151), Bry_.New_by_ints(226,179,150)) // ⳗ -> Ⳗ -- COPTIC CAPITAL LETTER OLD COPTIC GANGIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,153), Bry_.New_by_ints(226,179,152)) // ⳙ -> Ⳙ -- COPTIC CAPITAL LETTER OLD COPTIC DJA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,155), Bry_.New_by_ints(226,179,154)) // ⳛ -> Ⳛ -- COPTIC CAPITAL LETTER OLD COPTIC SHIMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,157), Bry_.New_by_ints(226,179,156)) // ⳝ -> Ⳝ -- COPTIC CAPITAL LETTER OLD NUBIAN SHIMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,159), Bry_.New_by_ints(226,179,158)) // ⳟ -> Ⳟ -- COPTIC CAPITAL LETTER OLD NUBIAN NGI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,161), Bry_.New_by_ints(226,179,160)) // ⳡ -> Ⳡ -- COPTIC CAPITAL LETTER OLD NUBIAN NYI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,163), Bry_.New_by_ints(226,179,162)) // ⳣ -> Ⳣ -- COPTIC CAPITAL LETTER OLD NUBIAN WAU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,172), Bry_.New_by_ints(226,179,171)) // ⳬ -> Ⳬ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC SHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,174), Bry_.New_by_ints(226,179,173)) // ⳮ -> Ⳮ -- COPTIC CAPITAL LETTER CRYPTOGRAMMIC GANGIA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(226,179,179), Bry_.New_by_ints(226,179,178)) // ⳳ -> Ⳳ -- COPTIC CAPITAL LETTER BOHAIRIC KHEI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,129), Bry_.New_by_ints(234,153,128)) // ꙁ -> Ꙁ -- CYRILLIC CAPITAL LETTER ZEMLYA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,131), Bry_.New_by_ints(234,153,130)) // ꙃ -> Ꙃ -- CYRILLIC CAPITAL LETTER DZELO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,133), Bry_.New_by_ints(234,153,132)) // ꙅ -> Ꙅ -- CYRILLIC CAPITAL LETTER REVERSED DZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,135), Bry_.New_by_ints(234,153,134)) // ꙇ -> Ꙇ -- CYRILLIC CAPITAL LETTER IOTA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,137), Bry_.New_by_ints(234,153,136)) // ꙉ -> Ꙉ -- CYRILLIC CAPITAL LETTER DJERV
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,139), Bry_.New_by_ints(234,153,138)) // ꙋ -> Ꙋ -- CYRILLIC CAPITAL LETTER MONOGRAPH UK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,141), Bry_.New_by_ints(234,153,140)) // ꙍ -> Ꙍ -- CYRILLIC CAPITAL LETTER BROAD OMEGA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,143), Bry_.New_by_ints(234,153,142)) // ꙏ -> Ꙏ -- CYRILLIC CAPITAL LETTER NEUTRAL YER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,145), Bry_.New_by_ints(234,153,144)) // ꙑ -> Ꙑ -- CYRILLIC CAPITAL LETTER YERU WITH BACK YER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,147), Bry_.New_by_ints(234,153,146)) // ꙓ -> Ꙓ -- CYRILLIC CAPITAL LETTER IOTIFIED YAT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,149), Bry_.New_by_ints(234,153,148)) // ꙕ -> Ꙕ -- CYRILLIC CAPITAL LETTER REVERSED YU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,151), Bry_.New_by_ints(234,153,150)) // ꙗ -> Ꙗ -- CYRILLIC CAPITAL LETTER IOTIFIED A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,153), Bry_.New_by_ints(234,153,152)) // ꙙ -> Ꙙ -- CYRILLIC CAPITAL LETTER CLOSED LITTLE YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,155), Bry_.New_by_ints(234,153,154)) // ꙛ -> Ꙛ -- CYRILLIC CAPITAL LETTER BLENDED YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,157), Bry_.New_by_ints(234,153,156)) // ꙝ -> Ꙝ -- CYRILLIC CAPITAL LETTER IOTIFIED CLOSED LITTLE YUS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,159), Bry_.New_by_ints(234,153,158)) // ꙟ -> Ꙟ -- CYRILLIC CAPITAL LETTER YN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,161), Bry_.New_by_ints(234,153,160)) // ꙡ -> Ꙡ -- CYRILLIC CAPITAL LETTER REVERSED TSE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,163), Bry_.New_by_ints(234,153,162)) // ꙣ -> Ꙣ -- CYRILLIC CAPITAL LETTER SOFT DE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,165), Bry_.New_by_ints(234,153,164)) // ꙥ -> Ꙥ -- CYRILLIC CAPITAL LETTER SOFT EL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,167), Bry_.New_by_ints(234,153,166)) // ꙧ -> Ꙧ -- CYRILLIC CAPITAL LETTER SOFT EM
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,169), Bry_.New_by_ints(234,153,168)) // ꙩ -> Ꙩ -- CYRILLIC CAPITAL LETTER MONOCULAR O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,171), Bry_.New_by_ints(234,153,170)) // ꙫ -> Ꙫ -- CYRILLIC CAPITAL LETTER BINOCULAR O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,153,173), Bry_.New_by_ints(234,153,172)) // ꙭ -> Ꙭ -- CYRILLIC CAPITAL LETTER DOUBLE MONOCULAR O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,129), Bry_.New_by_ints(234,154,128)) // ꚁ -> Ꚁ -- CYRILLIC CAPITAL LETTER DWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,131), Bry_.New_by_ints(234,154,130)) // ꚃ -> Ꚃ -- CYRILLIC CAPITAL LETTER DZWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,133), Bry_.New_by_ints(234,154,132)) // ꚅ -> Ꚅ -- CYRILLIC CAPITAL LETTER ZHWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,135), Bry_.New_by_ints(234,154,134)) // ꚇ -> Ꚇ -- CYRILLIC CAPITAL LETTER CCHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,137), Bry_.New_by_ints(234,154,136)) // ꚉ -> Ꚉ -- CYRILLIC CAPITAL LETTER DZZE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,139), Bry_.New_by_ints(234,154,138)) // ꚋ -> Ꚋ -- CYRILLIC CAPITAL LETTER TE WITH MIDDLE HOOK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,141), Bry_.New_by_ints(234,154,140)) // ꚍ -> Ꚍ -- CYRILLIC CAPITAL LETTER TWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,143), Bry_.New_by_ints(234,154,142)) // ꚏ -> Ꚏ -- CYRILLIC CAPITAL LETTER TSWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,145), Bry_.New_by_ints(234,154,144)) // ꚑ -> Ꚑ -- CYRILLIC CAPITAL LETTER TSSE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,147), Bry_.New_by_ints(234,154,146)) // ꚓ -> Ꚓ -- CYRILLIC CAPITAL LETTER TCHE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,149), Bry_.New_by_ints(234,154,148)) // ꚕ -> Ꚕ -- CYRILLIC CAPITAL LETTER HWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,154,151), Bry_.New_by_ints(234,154,150)) // ꚗ -> Ꚗ -- CYRILLIC CAPITAL LETTER SHWE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,163), Bry_.New_by_ints(234,156,162)) // ꜣ -> Ꜣ -- LATIN CAPITAL LETTER EGYPTOLOGICAL ALEF
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,165), Bry_.New_by_ints(234,156,164)) // ꜥ -> Ꜥ -- LATIN CAPITAL LETTER EGYPTOLOGICAL AIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,167), Bry_.New_by_ints(234,156,166)) // ꜧ -> Ꜧ -- LATIN CAPITAL LETTER HENG
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,169), Bry_.New_by_ints(234,156,168)) // ꜩ -> Ꜩ -- LATIN CAPITAL LETTER TZ
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,171), Bry_.New_by_ints(234,156,170)) // ꜫ -> Ꜫ -- LATIN CAPITAL LETTER TRESILLO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,173), Bry_.New_by_ints(234,156,172)) // ꜭ -> Ꜭ -- LATIN CAPITAL LETTER CUATRILLO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,175), Bry_.New_by_ints(234,156,174)) // ꜯ -> Ꜯ -- LATIN CAPITAL LETTER CUATRILLO WITH COMMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,179), Bry_.New_by_ints(234,156,178)) // ꜳ -> Ꜳ -- LATIN CAPITAL LETTER AA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,181), Bry_.New_by_ints(234,156,180)) // ꜵ -> Ꜵ -- LATIN CAPITAL LETTER AO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,183), Bry_.New_by_ints(234,156,182)) // ꜷ -> Ꜷ -- LATIN CAPITAL LETTER AU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,185), Bry_.New_by_ints(234,156,184)) // ꜹ -> Ꜹ -- LATIN CAPITAL LETTER AV
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,187), Bry_.New_by_ints(234,156,186)) // ꜻ -> Ꜻ -- LATIN CAPITAL LETTER AV WITH HORIZONTAL BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,189), Bry_.New_by_ints(234,156,188)) // ꜽ -> Ꜽ -- LATIN CAPITAL LETTER AY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,156,191), Bry_.New_by_ints(234,156,190)) // ꜿ -> Ꜿ -- LATIN CAPITAL LETTER REVERSED C WITH DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,129), Bry_.New_by_ints(234,157,128)) // ꝁ -> Ꝁ -- LATIN CAPITAL LETTER K WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,131), Bry_.New_by_ints(234,157,130)) // ꝃ -> Ꝃ -- LATIN CAPITAL LETTER K WITH DIAGONAL STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,133), Bry_.New_by_ints(234,157,132)) // ꝅ -> Ꝅ -- LATIN CAPITAL LETTER K WITH STROKE AND DIAGONAL STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,135), Bry_.New_by_ints(234,157,134)) // ꝇ -> Ꝇ -- LATIN CAPITAL LETTER BROKEN L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,137), Bry_.New_by_ints(234,157,136)) // ꝉ -> Ꝉ -- LATIN CAPITAL LETTER L WITH HIGH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,139), Bry_.New_by_ints(234,157,138)) // ꝋ -> Ꝋ -- LATIN CAPITAL LETTER O WITH LONG STROKE OVERLAY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,141), Bry_.New_by_ints(234,157,140)) // ꝍ -> Ꝍ -- LATIN CAPITAL LETTER O WITH LOOP
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,143), Bry_.New_by_ints(234,157,142)) // ꝏ -> Ꝏ -- LATIN CAPITAL LETTER OO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,145), Bry_.New_by_ints(234,157,144)) // ꝑ -> Ꝑ -- LATIN CAPITAL LETTER P WITH STROKE THROUGH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,147), Bry_.New_by_ints(234,157,146)) // ꝓ -> Ꝓ -- LATIN CAPITAL LETTER P WITH FLOURISH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,149), Bry_.New_by_ints(234,157,148)) // ꝕ -> Ꝕ -- LATIN CAPITAL LETTER P WITH SQUIRREL TAIL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,151), Bry_.New_by_ints(234,157,150)) // ꝗ -> Ꝗ -- LATIN CAPITAL LETTER Q WITH STROKE THROUGH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,153), Bry_.New_by_ints(234,157,152)) // ꝙ -> Ꝙ -- LATIN CAPITAL LETTER Q WITH DIAGONAL STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,155), Bry_.New_by_ints(234,157,154)) // ꝛ -> Ꝛ -- LATIN CAPITAL LETTER R ROTUNDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,157), Bry_.New_by_ints(234,157,156)) // ꝝ -> Ꝝ -- LATIN CAPITAL LETTER RUM ROTUNDA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,159), Bry_.New_by_ints(234,157,158)) // ꝟ -> Ꝟ -- LATIN CAPITAL LETTER V WITH DIAGONAL STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,161), Bry_.New_by_ints(234,157,160)) // ꝡ -> Ꝡ -- LATIN CAPITAL LETTER VY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,163), Bry_.New_by_ints(234,157,162)) // ꝣ -> Ꝣ -- LATIN CAPITAL LETTER VISIGOTHIC Z
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,165), Bry_.New_by_ints(234,157,164)) // ꝥ -> Ꝥ -- LATIN CAPITAL LETTER THORN WITH STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,167), Bry_.New_by_ints(234,157,166)) // ꝧ -> Ꝧ -- LATIN CAPITAL LETTER THORN WITH STROKE THROUGH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,169), Bry_.New_by_ints(234,157,168)) // ꝩ -> Ꝩ -- LATIN CAPITAL LETTER VEND
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,171), Bry_.New_by_ints(234,157,170)) // ꝫ -> Ꝫ -- LATIN CAPITAL LETTER ET
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,173), Bry_.New_by_ints(234,157,172)) // ꝭ -> Ꝭ -- LATIN CAPITAL LETTER IS
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,175), Bry_.New_by_ints(234,157,174)) // ꝯ -> Ꝯ -- LATIN CAPITAL LETTER CON
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,186), Bry_.New_by_ints(234,157,185)) // ꝺ -> Ꝺ -- LATIN CAPITAL LETTER INSULAR D
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,188), Bry_.New_by_ints(234,157,187)) // ꝼ -> Ꝼ -- LATIN CAPITAL LETTER INSULAR F
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,157,191), Bry_.New_by_ints(234,157,190)) // ꝿ -> Ꝿ -- LATIN CAPITAL LETTER TURNED INSULAR G
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,129), Bry_.New_by_ints(234,158,128)) // ꞁ -> Ꞁ -- LATIN CAPITAL LETTER TURNED L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,131), Bry_.New_by_ints(234,158,130)) // ꞃ -> Ꞃ -- LATIN CAPITAL LETTER INSULAR R
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,133), Bry_.New_by_ints(234,158,132)) // ꞅ -> Ꞅ -- LATIN CAPITAL LETTER INSULAR S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,135), Bry_.New_by_ints(234,158,134)) // ꞇ -> Ꞇ -- LATIN CAPITAL LETTER INSULAR T
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,140), Bry_.New_by_ints(234,158,139)) // ꞌ -> Ꞌ -- LATIN CAPITAL LETTER SALTILLO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,145), Bry_.New_by_ints(234,158,144)) // ꞑ -> Ꞑ -- LATIN CAPITAL LETTER N WITH DESCENDER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,147), Bry_.New_by_ints(234,158,146)) // ꞓ -> Ꞓ -- LATIN CAPITAL LETTER C WITH BAR
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,161), Bry_.New_by_ints(234,158,160)) // ꞡ -> Ꞡ -- LATIN CAPITAL LETTER G WITH OBLIQUE STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,163), Bry_.New_by_ints(234,158,162)) // ꞣ -> Ꞣ -- LATIN CAPITAL LETTER K WITH OBLIQUE STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,165), Bry_.New_by_ints(234,158,164)) // ꞥ -> Ꞥ -- LATIN CAPITAL LETTER N WITH OBLIQUE STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,167), Bry_.New_by_ints(234,158,166)) // ꞧ -> Ꞧ -- LATIN CAPITAL LETTER R WITH OBLIQUE STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(234,158,169), Bry_.New_by_ints(234,158,168)) // ꞩ -> Ꞩ -- LATIN CAPITAL LETTER S WITH OBLIQUE STROKE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,129), Bry_.New_by_ints(239,188,161)) // ａ -> Ａ -- FULLWIDTH LATIN CAPITAL LETTER A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,130), Bry_.New_by_ints(239,188,162)) // ｂ -> Ｂ -- FULLWIDTH LATIN CAPITAL LETTER B
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,131), Bry_.New_by_ints(239,188,163)) // ｃ -> Ｃ -- FULLWIDTH LATIN CAPITAL LETTER C
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,132), Bry_.New_by_ints(239,188,164)) // ｄ -> Ｄ -- FULLWIDTH LATIN CAPITAL LETTER D
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,133), Bry_.New_by_ints(239,188,165)) // ｅ -> Ｅ -- FULLWIDTH LATIN CAPITAL LETTER E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,134), Bry_.New_by_ints(239,188,166)) // ｆ -> Ｆ -- FULLWIDTH LATIN CAPITAL LETTER F
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,135), Bry_.New_by_ints(239,188,167)) // ｇ -> Ｇ -- FULLWIDTH LATIN CAPITAL LETTER G
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,136), Bry_.New_by_ints(239,188,168)) // ｈ -> Ｈ -- FULLWIDTH LATIN CAPITAL LETTER H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,137), Bry_.New_by_ints(239,188,169)) // ｉ -> Ｉ -- FULLWIDTH LATIN CAPITAL LETTER I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,138), Bry_.New_by_ints(239,188,170)) // ｊ -> Ｊ -- FULLWIDTH LATIN CAPITAL LETTER J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,139), Bry_.New_by_ints(239,188,171)) // ｋ -> Ｋ -- FULLWIDTH LATIN CAPITAL LETTER K
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,140), Bry_.New_by_ints(239,188,172)) // ｌ -> Ｌ -- FULLWIDTH LATIN CAPITAL LETTER L
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,141), Bry_.New_by_ints(239,188,173)) // ｍ -> Ｍ -- FULLWIDTH LATIN CAPITAL LETTER M
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,142), Bry_.New_by_ints(239,188,174)) // ｎ -> Ｎ -- FULLWIDTH LATIN CAPITAL LETTER N
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,143), Bry_.New_by_ints(239,188,175)) // ｏ -> Ｏ -- FULLWIDTH LATIN CAPITAL LETTER O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,144), Bry_.New_by_ints(239,188,176)) // ｐ -> Ｐ -- FULLWIDTH LATIN CAPITAL LETTER P
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,145), Bry_.New_by_ints(239,188,177)) // ｑ -> Ｑ -- FULLWIDTH LATIN CAPITAL LETTER Q
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,146), Bry_.New_by_ints(239,188,178)) // ｒ -> Ｒ -- FULLWIDTH LATIN CAPITAL LETTER R
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,147), Bry_.New_by_ints(239,188,179)) // ｓ -> Ｓ -- FULLWIDTH LATIN CAPITAL LETTER S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,148), Bry_.New_by_ints(239,188,180)) // ｔ -> Ｔ -- FULLWIDTH LATIN CAPITAL LETTER T
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,149), Bry_.New_by_ints(239,188,181)) // ｕ -> Ｕ -- FULLWIDTH LATIN CAPITAL LETTER U
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,150), Bry_.New_by_ints(239,188,182)) // ｖ -> Ｖ -- FULLWIDTH LATIN CAPITAL LETTER V
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,151), Bry_.New_by_ints(239,188,183)) // ｗ -> Ｗ -- FULLWIDTH LATIN CAPITAL LETTER W
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,152), Bry_.New_by_ints(239,188,184)) // ｘ -> Ｘ -- FULLWIDTH LATIN CAPITAL LETTER X
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,153), Bry_.New_by_ints(239,188,185)) // ｙ -> Ｙ -- FULLWIDTH LATIN CAPITAL LETTER Y
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(239,189,154), Bry_.New_by_ints(239,188,186)) // ｚ -> Ｚ -- FULLWIDTH LATIN CAPITAL LETTER Z
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,168), Bry_.New_by_ints(240,144,144,128)) // 𐐨 -> 𐐀 -- DESERET CAPITAL LETTER LONG I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,169), Bry_.New_by_ints(240,144,144,129)) // 𐐩 -> 𐐁 -- DESERET CAPITAL LETTER LONG E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,170), Bry_.New_by_ints(240,144,144,130)) // 𐐪 -> 𐐂 -- DESERET CAPITAL LETTER LONG A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,171), Bry_.New_by_ints(240,144,144,131)) // 𐐫 -> 𐐃 -- DESERET CAPITAL LETTER LONG AH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,172), Bry_.New_by_ints(240,144,144,132)) // 𐐬 -> 𐐄 -- DESERET CAPITAL LETTER LONG O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,173), Bry_.New_by_ints(240,144,144,133)) // 𐐭 -> 𐐅 -- DESERET CAPITAL LETTER LONG OO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,174), Bry_.New_by_ints(240,144,144,134)) // 𐐮 -> 𐐆 -- DESERET CAPITAL LETTER SHORT I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,175), Bry_.New_by_ints(240,144,144,135)) // 𐐯 -> 𐐇 -- DESERET CAPITAL LETTER SHORT E
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,176), Bry_.New_by_ints(240,144,144,136)) // 𐐰 -> 𐐈 -- DESERET CAPITAL LETTER SHORT A
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,177), Bry_.New_by_ints(240,144,144,137)) // 𐐱 -> 𐐉 -- DESERET CAPITAL LETTER SHORT AH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,178), Bry_.New_by_ints(240,144,144,138)) // 𐐲 -> 𐐊 -- DESERET CAPITAL LETTER SHORT O
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,179), Bry_.New_by_ints(240,144,144,139)) // 𐐳 -> 𐐋 -- DESERET CAPITAL LETTER SHORT OO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,180), Bry_.New_by_ints(240,144,144,140)) // 𐐴 -> 𐐌 -- DESERET CAPITAL LETTER AY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,181), Bry_.New_by_ints(240,144,144,141)) // 𐐵 -> 𐐍 -- DESERET CAPITAL LETTER OW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,182), Bry_.New_by_ints(240,144,144,142)) // 𐐶 -> 𐐎 -- DESERET CAPITAL LETTER WU
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,183), Bry_.New_by_ints(240,144,144,143)) // 𐐷 -> 𐐏 -- DESERET CAPITAL LETTER YEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,184), Bry_.New_by_ints(240,144,144,144)) // 𐐸 -> 𐐐 -- DESERET CAPITAL LETTER H
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,185), Bry_.New_by_ints(240,144,144,145)) // 𐐹 -> 𐐑 -- DESERET CAPITAL LETTER PEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,186), Bry_.New_by_ints(240,144,144,146)) // 𐐺 -> 𐐒 -- DESERET CAPITAL LETTER BEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,187), Bry_.New_by_ints(240,144,144,147)) // 𐐻 -> 𐐓 -- DESERET CAPITAL LETTER TEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,188), Bry_.New_by_ints(240,144,144,148)) // 𐐼 -> 𐐔 -- DESERET CAPITAL LETTER DEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,189), Bry_.New_by_ints(240,144,144,149)) // 𐐽 -> 𐐕 -- DESERET CAPITAL LETTER CHEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,190), Bry_.New_by_ints(240,144,144,150)) // 𐐾 -> 𐐖 -- DESERET CAPITAL LETTER JEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,144,191), Bry_.New_by_ints(240,144,144,151)) // 𐐿 -> 𐐗 -- DESERET CAPITAL LETTER KAY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,128), Bry_.New_by_ints(240,144,144,152)) // 𐑀 -> 𐐘 -- DESERET CAPITAL LETTER GAY
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,129), Bry_.New_by_ints(240,144,144,153)) // 𐑁 -> 𐐙 -- DESERET CAPITAL LETTER EF
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,130), Bry_.New_by_ints(240,144,144,154)) // 𐑂 -> 𐐚 -- DESERET CAPITAL LETTER VEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,131), Bry_.New_by_ints(240,144,144,155)) // 𐑃 -> 𐐛 -- DESERET CAPITAL LETTER ETH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,132), Bry_.New_by_ints(240,144,144,156)) // 𐑄 -> 𐐜 -- DESERET CAPITAL LETTER THEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,133), Bry_.New_by_ints(240,144,144,157)) // 𐑅 -> 𐐝 -- DESERET CAPITAL LETTER ES
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,134), Bry_.New_by_ints(240,144,144,158)) // 𐑆 -> 𐐞 -- DESERET CAPITAL LETTER ZEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,135), Bry_.New_by_ints(240,144,144,159)) // 𐑇 -> 𐐟 -- DESERET CAPITAL LETTER ESH
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,136), Bry_.New_by_ints(240,144,144,160)) // 𐑈 -> 𐐠 -- DESERET CAPITAL LETTER ZHEE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,137), Bry_.New_by_ints(240,144,144,161)) // 𐑉 -> 𐐡 -- DESERET CAPITAL LETTER ER
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,138), Bry_.New_by_ints(240,144,144,162)) // 𐑊 -> 𐐢 -- DESERET CAPITAL LETTER EL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,139), Bry_.New_by_ints(240,144,144,163)) // 𐑋 -> 𐐣 -- DESERET CAPITAL LETTER EM
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,140), Bry_.New_by_ints(240,144,144,164)) // 𐑌 -> 𐐤 -- DESERET CAPITAL LETTER EN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,141), Bry_.New_by_ints(240,144,144,165)) // 𐑍 -> 𐐥 -- DESERET CAPITAL LETTER ENG
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,142), Bry_.New_by_ints(240,144,144,166)) // 𐑎 -> 𐐦 -- DESERET CAPITAL LETTER OI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(240,144,145,143), Bry_.New_by_ints(240,144,144,167)) // 𐑏 -> 𐐧 -- DESERET CAPITAL LETTER EW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(196,177), Bry_.New_by_ints(73)) // ı -> I -- LATIN SMALL LETTER DOTLESS I
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(197,191), Bry_.New_by_ints(83)) // ſ -> S -- LATIN SMALL LETTER LONG S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(205,133), Bry_.New_by_ints(206,153)) //  ͅ  -> Ι -- GREEK NON-SPACING IOTA BELOW
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(206,185), Bry_.New_by_ints(206,153)) // ι -> Ι -- GREEK SMALL LETTER IOTA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,130), Bry_.New_by_ints(206,163)) // ς -> Σ -- GREEK SMALL LETTER FINAL SIGMA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,144), Bry_.New_by_ints(206,146)) // ϐ -> Β -- GREEK SMALL LETTER CURLED BETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,145), Bry_.New_by_ints(206,152)) // ϑ -> Θ -- GREEK SMALL LETTER SCRIPT THETA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,149), Bry_.New_by_ints(206,166)) // ϕ -> Φ -- GREEK SMALL LETTER SCRIPT PHI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,150), Bry_.New_by_ints(206,160)) // ϖ -> Π -- GREEK SMALL LETTER OMEGA PI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,176), Bry_.New_by_ints(206,154)) // ϰ -> Κ -- GREEK SMALL LETTER SCRIPT KAPPA
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,177), Bry_.New_by_ints(206,161)) // ϱ -> Ρ -- GREEK SMALL LETTER TAILED RHO
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(207,181), Bry_.New_by_ints(206,149)) // ϵ -> Ε -- GREEK LUNATE EPSILON SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(225,186,155), Bry_.New_by_ints(225,185,160)) // ẛ -> Ṡ -- LATIN SMALL LETTER LONG S WITH DOT ABOVE
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(225,190,190), Bry_.New_by_ints(206,153)) // ι -> Ι -- GREEK PROSGEGRAMMENI
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(196,176), Bry_.New_by_ints(105)) // İ -> i -- LATIN CAPITAL LETTER I DOT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(199,133), Bry_.New_by_ints(199,134)) // ǅ -> ǆ -- LATIN LETTER CAPITAL D SMALL Z HACEK
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(199,136), Bry_.New_by_ints(199,137)) // ǈ -> ǉ -- LATIN LETTER CAPITAL L SMALL J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(199,139), Bry_.New_by_ints(199,140)) // ǋ -> ǌ -- LATIN LETTER CAPITAL N SMALL J
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(199,178), Bry_.New_by_ints(199,179)) // ǲ -> ǳ -- LATIN CAPITAL LETTER D WITH SMALL LETTER Z
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(206,153), Bry_.New_by_ints(206,185)) // Ι -> ι -- GREEK CAPITAL LETTER IOTA; NOTE: reversed; PAGE:en.d:ἀρχιερεύς DATE:2014-09-02
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(207,180), Bry_.New_by_ints(206,184)) // ϴ -> θ -- GREEK CAPITAL THETA SYMBOL
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(225,186,158), Bry_.New_by_ints(195,159)) // ẞ -> ß -- LATIN CAPITAL LETTER SHARP S
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(226,132,166), Bry_.New_by_ints(207,137)) // Ω -> ω -- OHM
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(226,132,170), Bry_.New_by_ints(107)) // K -> k -- DEGREES KELVIN
, Xol_case_itm_.new_(Xol_case_itm_.Tid_lower, Bry_.New_by_ints(226,132,171), Bry_.New_by_ints(195,165)) // Å -> å -- ANGSTROM UNIT
, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(194,181), Bry_.New_by_ints(206,156)) // µ -> Μ -- MICRO SIGN; SWAPPED
, Xol_case_itm_.new_(Xol_case_itm_.Tid_both, Bry_.New_by_ints(206,188), Bry_.New_by_ints(206,156)) // μ -> Μ -- GREEK CAPITAL LETTER MU; CONSOLIDATED
//, Xol_case_itm_.new_(Xol_case_itm_.Tid_upper, Bry_.New_by_ints(206,188), Bry_.New_by_ints(206,156)) // μ -> Μ -- GREEK SMALL LETTER MU
};
		rv.Add_bulk(itms);
		return rv;
	}
}
