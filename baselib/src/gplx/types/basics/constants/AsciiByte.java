/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.constants;
import gplx.types.errs.ErrUtl;
public class AsciiByte {
	public static final byte
		Null            =    0                                                , Backfeed         =   8, Tab              =   9,
		Nl              =   10, VerticalTab      =  11, Formfeed         =  12, Cr               =  13,
														Escape           =  27,
														Space            =  32, Bang             =  33, Quote            =  34,
		Hash             =  35, Dollar           =  36, Percent          =  37, Amp              =  38, Apos             =  39,
		ParenBgn         =  40, ParenEnd         =  41, Star             =  42, Plus             =  43, Comma            =  44,
		Dash             =  45, Dot              =  46, Slash            =  47, Num0             =  48, Num1             =  49,
		Num2             =  50, Num3             =  51, Num4             =  52, Num5             =  53, Num6             =  54,
		Num7             =  55, Num8             =  56, Num9             =  57, Colon            =  58, Semic            =  59,
		Lt               =  60, Eq               =  61, Gt               =  62, Question         =  63, At               =  64,
		Ltr_A            =  65, Ltr_B            =  66, Ltr_C            =  67, Ltr_D            =  68, Ltr_E            =  69,
		Ltr_F            =  70, Ltr_G            =  71, Ltr_H            =  72, Ltr_I            =  73, Ltr_J            =  74,
		Ltr_K            =  75, Ltr_L            =  76, Ltr_M            =  77, Ltr_N            =  78, Ltr_O            =  79,
		Ltr_P            =  80, Ltr_Q            =  81, Ltr_R            =  82, Ltr_S            =  83, Ltr_T            =  84,
		Ltr_U            =  85, Ltr_V            =  86, Ltr_W            =  87, Ltr_X            =  88, Ltr_Y            =  89,
		Ltr_Z            =  90, BrackBgn         =  91, Backslash        =  92, BrackEnd         =  93, Pow              =  94, // Circumflex
		Underline        =  95, Tick             =  96, Ltr_a            =  97, Ltr_b            =  98, Ltr_c            =  99,
		Ltr_d            = 100, Ltr_e            = 101, Ltr_f            = 102, Ltr_g            = 103, Ltr_h            = 104,
		Ltr_i            = 105, Ltr_j            = 106, Ltr_k            = 107, Ltr_l            = 108, Ltr_m            = 109,
		Ltr_n            = 110, Ltr_o            = 111, Ltr_p            = 112, Ltr_q            = 113, Ltr_r            = 114,
		Ltr_s            = 115, Ltr_t            = 116, Ltr_u            = 117, Ltr_v            = 118, Ltr_w            = 119,
		Ltr_x            = 120, Ltr_y            = 121, Ltr_z            = 122, CurlyBgn         = 123, Pipe             = 124,
		CurlyEnd         = 125, Tilde            = 126, Delete           = 127;

	public static final byte
		AngleBgn     = Lt,
		AngleEnd     = Gt;

	public static final int Len1 = 1;
	public static final byte Max7Bit = 127, AsciiMin = 0, AsciiMax = 127;
	public static boolean IsLtr(byte b) {
		return (      b >= Ltr_a && b <= Ltr_z
				||    b >= Ltr_A && b <= Ltr_Z);
	}
	public static boolean IsWs(byte b) {
		switch (b) {
			case Tab: case Nl: case Cr: case Space: return true;
			default: return false;
		}
	}
	public static boolean IsNum(byte b) {
		return b > Slash && b < Colon;
	}
	public static byte ToA7Int(byte b)        {return (byte)(b - Num0);}
	public static byte ToA7Str(int digit) {
		switch (digit) {
			case 0: return Num0; case 1: return Num1; case 2: return Num2; case 3: return Num3; case 4: return Num4;
			case 5: return Num5; case 6: return Num6; case 7: return Num7; case 8: return Num8; case 9: return Num9;
			default: throw ErrUtl.NewFmt("unknown digit; digit={0}", digit);
		}
	}
	public static String ToStr(byte b) {return String.valueOf((char)b);}
	public static byte CaseUpper(byte b) {
		return b > 96 && b < 123
			? (byte)(b - 32)
			: b;
	}
	public static byte CaseLower(byte b) {
		return b > 64 && b < 91
			? (byte)(b + 32)
			: b;
	}
	public static final byte[]
		TabBry = new byte[] {Tab},
		NlBry = new byte[] {Nl},
		SpaceBry = new byte[] {Space},
		BangBry = new byte[] {Bang},
		QuoteBry = new byte[] {Quote},
		HashBry = new byte[] {Hash},
		DotBry = new byte[] {Dot},
		AngleBgnBry = new byte[] {AngleBgn},
		AngleEndBry = new byte[] {AngleEnd},
		CommaBry = new byte[] {Comma},
		ColonBry = new byte[] {Colon},
		SemicBry = new byte[] {Semic},
		EqBry = new byte[] {Eq},
		AmpBry = new byte[] {Amp},
		LtBry = new byte[] {Lt},
		GtBry = new byte[] {Gt},
		QuestionBry = new byte[] {Question},
		BrackBgnBry = new byte[] {BrackBgn},
		BrackEndBry = new byte[] {BrackEnd},
		AposBry = new byte[] {Apos},
		PipeBry = new byte[] {Pipe},
		UnderlineBry = new byte[] {Underline},
		SlashBry = new byte[] {Slash},
		StarBry = new byte[] {Star},
		DashBry = new byte[] {Dash},
		CrLfBry = new byte[] {Cr, Nl},
		Num0Bry = new byte[] {Num0},
		Num1Bry = new byte[] {Num1};
}
/*
SYMBOLS
-------
Bang      | Slash |  33 ->  47 | !"#$%&'()*+,-./
Colon     | At    |  58 ->  64 | :;<=>?@
BrackBgn  | Tick  |  91 ->  96 | [\]^_`
CurlyBgn  | Tilde | 123 -> 126 | {|}~
*/
