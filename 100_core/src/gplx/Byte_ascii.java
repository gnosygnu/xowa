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
package gplx;
public class Byte_ascii {
	public static final byte
	      Null			=   0												 , Backfeed         =   8, Tab				=   9
		, Nl			=  10, Formfeed			=  12, Cr				=  13
		,					   Escape			=  27
													 , Space			=  32, Bang				=  33, Quote			=  34
		, Hash			=  35, Dollar           =  36, Percent			=  37, Amp				=  38, Apos				=  39
		, Paren_bgn		=  40, Paren_end		=  41, Star				=  42, Plus				=  43, Comma			=  44
		, Dash			=  45, Dot				=  46, Slash			=  47, Num_0			=  48, Num_1			=  49
		, Num_2			=  50, Num_3			=  51, Num_4			=  52, Num_5			=  53, Num_6			=  54
		, Num_7			=  55, Num_8			=  56, Num_9			=  57, Colon			=  58, Semic			=  59
		, Lt			=  60, Eq				=  61, Gt				=  62, Question			=  63, At				=  64
		, Ltr_A			=  65, Ltr_B			=  66, Ltr_C			=  67, Ltr_D			=  68, Ltr_E			=  69
		, Ltr_F			=  70, Ltr_G			=  71, Ltr_H			=  72, Ltr_I			=  73, Ltr_J			=  74
		, Ltr_K			=  75, Ltr_L			=  76, Ltr_M			=  77, Ltr_N			=  78, Ltr_O			=  79
		, Ltr_P			=  80, Ltr_Q			=  81, Ltr_R			=  82, Ltr_S			=  83, Ltr_T			=  84
		, Ltr_U			=  85, Ltr_V			=  86, Ltr_W			=  87, Ltr_X			=  88, Ltr_Y			=  89
		, Ltr_Z			=  90, Brack_bgn		=  91, Backslash		=  92, Brack_end		=  93, Pow				=  94	// Circumflex             
		, Underline     =  95, Tick				=  96, Ltr_a			=  97, Ltr_b            =  98, Ltr_c			=  99
		, Ltr_d			= 100, Ltr_e			= 101, Ltr_f			= 102, Ltr_g			= 103, Ltr_h			= 104
		, Ltr_i			= 105, Ltr_j			= 106, Ltr_k			= 107, Ltr_l			= 108, Ltr_m			= 109
		, Ltr_n			= 110, Ltr_o			= 111, Ltr_p			= 112, Ltr_q			= 113, Ltr_r			= 114
		, Ltr_s			= 115, Ltr_t			= 116, Ltr_u			= 117, Ltr_v			= 118, Ltr_w			= 119
		, Ltr_x			= 120, Ltr_y			= 121, Ltr_z			= 122, Curly_bgn		= 123, Pipe				= 124
		, Curly_end		= 125, Tilde			= 126, Delete           = 127
		;
	public static final byte
	  Angle_bgn = Lt, Angle_end = Gt
	;
	public static final byte Max_7_bit = (byte)127, Ascii_min = 0, Ascii_max = 127;
	public static boolean Is_sym(byte b) {
		switch (b) {
			case Byte_ascii.Bang: case Byte_ascii.Quote:
			case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Amp: case Byte_ascii.Apos:
			case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star: case Byte_ascii.Plus: case Byte_ascii.Comma:
			case Byte_ascii.Dash: case Byte_ascii.Dot: case Byte_ascii.Slash:
			case Byte_ascii.Colon: case Byte_ascii.Semic:
			case Byte_ascii.Lt: case Byte_ascii.Eq: case Byte_ascii.Gt: case Byte_ascii.Question: case Byte_ascii.At:
			case Byte_ascii.Brack_bgn: case Byte_ascii.Backslash: case Byte_ascii.Brack_end: case Byte_ascii.Pow:								
			case Byte_ascii.Underline: case Byte_ascii.Tick:
			case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe:
			case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
				return true;
			default:
				return false;
		}
	}
	public static boolean Is_ltr(byte b) {
		return (	b >= Byte_ascii.Ltr_a && b <= Byte_ascii.Ltr_z
				||	b >= Byte_ascii.Ltr_A && b <= Byte_ascii.Ltr_Z);
	}
	public static boolean Is_ws(byte b) {
		switch (b) {
			case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space: return true;
			default: return false;
		}
	}
	public static boolean Is_num(byte b) {
		return b > Byte_ascii.Slash && b < Byte_ascii.Colon;
	}
	public static byte	To_a7_int(byte b)		{return (byte)(b - Byte_ascii.Num_0);}
	public static byte	To_a7_str(int digit)	{
		switch (digit) {
			case 0: return Byte_ascii.Num_0; case 1: return Byte_ascii.Num_1; case 2: return Byte_ascii.Num_2; case 3: return Byte_ascii.Num_3; case 4: return Byte_ascii.Num_4;
			case 5: return Byte_ascii.Num_5; case 6: return Byte_ascii.Num_6; case 7: return Byte_ascii.Num_7; case 8: return Byte_ascii.Num_8; case 9: return Byte_ascii.Num_9;
			default: throw Err_.new_("Byte_ascii", "unknown digit", "digit", digit);
		}
	}
	public static String To_str(byte b) {return Char_.To_str((char)b);}
	public static byte Case_upper(byte b) {
		return b > 96 && b < 123
			? (byte)(b - 32)
			: b;
	}
	public static byte Case_lower(byte b) {
		return b > 64 && b < 91
			? (byte)(b + 32)
			: b;
	}
	public static final    byte[] Space_len2 = new byte[] {Space, Space}, Space_len4 = new byte[] {Space, Space, Space, Space};
	public static final    byte[]
	  Tab_bry				= new byte[] {Byte_ascii.Tab}
	, Nl_bry				= new byte[] {Byte_ascii.Nl}
	, Space_bry				= new byte[] {Byte_ascii.Space}
	, Bang_bry				= new byte[] {Byte_ascii.Bang}
	, Quote_bry				= new byte[] {Byte_ascii.Quote}
	, Hash_bry				= new byte[] {Byte_ascii.Hash}
	, Dot_bry				= new byte[] {Byte_ascii.Dot}
	, Angle_bgn_bry			= new byte[] {Byte_ascii.Angle_bgn}
	, Angle_end_bry			= new byte[] {Byte_ascii.Angle_end}
	, Comma_bry				= new byte[] {Byte_ascii.Comma}
	, Colon_bry				= new byte[] {Byte_ascii.Colon}
	, Semic_bry				= new byte[] {Byte_ascii.Semic}
	, Eq_bry				= new byte[] {Byte_ascii.Eq}
	, Amp_bry				= new byte[] {Byte_ascii.Amp}
	, Lt_bry				= new byte[] {Byte_ascii.Lt}
	, Gt_bry				= new byte[] {Byte_ascii.Gt}
	, Question_bry			= new byte[] {Byte_ascii.Question}
	, Brack_bgn_bry			= new byte[] {Byte_ascii.Brack_bgn}
	, Brack_end_bry			= new byte[] {Byte_ascii.Brack_end}
	, Apos_bry				= new byte[] {Byte_ascii.Apos}
	, Pipe_bry				= new byte[] {Byte_ascii.Pipe}
	, Underline_bry			= new byte[] {Byte_ascii.Underline}
	, Slash_bry				= new byte[] {Byte_ascii.Slash}
	, Star_bry				= new byte[] {Byte_ascii.Star}
	, Dash_bry				= new byte[] {Byte_ascii.Dash}
	, Cr_lf_bry				= new byte[] {Byte_ascii.Cr, Byte_ascii.Nl}
	, Num_0_bry				= new byte[] {Byte_ascii.Num_0}
	, Num_1_bry				= new byte[] {Byte_ascii.Num_1}
	;
}
